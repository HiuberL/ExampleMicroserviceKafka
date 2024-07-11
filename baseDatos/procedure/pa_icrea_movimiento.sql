DROP PROCEDURE IF EXISTS  pa_icrea_movimiento ;

CREATE PROCEDURE pa_icrea_movimiento (
    e_cliente INT,
    e_cuenta VARCHAR(20),
    e_valor NUMERIC(10,2),
    e_tipo CHAR(5),
    o_secuencia INOUT BIGINT
)
AS $$
DECLARE 
    v_coincidencia INTEGER;
    v_movimiento_sec BIGINT;
    v_saldo NUMERIC(10,2);
    v_estado CHAR(1);
    v_cuenta INTEGER;
BEGIN

    /*  VALIDACIONES INICIALES */
    SELECT COUNT(*) 
    INTO v_coincidencia
    FROM ms_persona
    WHERE ms_cod_persona = e_cliente;    

    IF v_coincidencia =  0 THEN
           RAISE EXCEPTION '/CLIENTE NO HA SIDO REGISTRADO';
    END IF;

    SELECT 
        c.ms_estado,
        c.ms_saldo_disponible,
        c.ms_cod_cuenta
    INTO v_estado,
        v_saldo,
        v_cuenta
    FROM ms_persona a
    INNER JOIN ms_cliente b
    on a.ms_cod_persona = b.ms_cod_cliente
    INNER JOIN ms_cuentas c
    on b.ms_cod_cliente = c.ms_cod_cliente
    WHERE a.ms_cod_persona = e_cliente   
    AND c.ms_cuenta = e_cuenta
    AND b.ms_estado = 'V';
    
    IF v_estado IS NULL THEN
           RAISE EXCEPTION '/CUENTA NO PERTENECE A CLIENTE O CLIENTE ELIMINADO';
    END IF;

    IF v_estado = 'E' THEN
           RAISE EXCEPTION '/CUENTA EN ESTADO ELIMINADO NO PUEDE GENERAR MOVIMIENTO';
    END IF;

    IF v_estado = 'I' and e_valor < 0 THEN
           RAISE EXCEPTION '/CUENTA EN ESTADO INACTIVO NO PUEDE GENERAR MOVIMIENTOS DE DÉBITO';
    END IF;

    IF (v_saldo + e_valor) < 0 THEN
           RAISE EXCEPTION '/CUENTA NO DISPONE DE VALORES, SALDO NO DISPONIBLE';
    END IF;

    e_tipo := COALESCE(e_tipo,'DEPOS');

    BEGIN
        UPDATE ms_cuentas
        SET
            ms_saldo_disponible = v_saldo + e_valor
        WHERE ms_cod_cuenta = v_cuenta;
        
        v_movimiento_sec := generar_movimiento();
        INSERT INTO ms_mov_monet (
            ms_secuencia,
            ms_fecha,
            ms_cod_cuenta,
            ms_tipo,
            ms_saldo,
            ms_valor
        )
        VALUES(
            v_movimiento_sec,
            CURRENT_TIMESTAMP,
            v_cuenta,
            e_tipo,
            v_saldo + e_valor,
            e_valor
        );
            

    EXCEPTION
        WHEN OTHERS THEN
            RAISE EXCEPTION USING ERRCODE = SQLSTATE, MESSAGE =  'HA OCURRIDO UN ERROR INTERNO AL INSERTAR TRANSACCION DE MOVIMIENTO ' || SQLERRM  ;
    END;
    o_secuencia:= v_movimiento_sec;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION USING ERRCODE = SQLSTATE, MESSAGE = SQLERRM;
END;
$$ LANGUAGE plpgsql;