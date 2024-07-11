DROP PROCEDURE IF EXISTS  pa_delim_movimiento ;

CREATE PROCEDURE pa_delim_movimiento (
    e_secuencia NUMERIC
)
AS $$
DECLARE 
    v_coincidencia INTEGER;
    v_movimiento_sec BIGINT;
    v_valor NUMERIC(10,2);
    v_saldo NUMERIC(10,2);
    v_estado CHAR(1);
    v_cuenta INTEGER;
    v_sec_reverso BIGINT;
BEGIN

    /*  VALIDACIONES INICIALES */
    SELECT COUNT(*) 
    INTO v_coincidencia
    FROM ms_mov_monet
    WHERE ms_secuencia = e_secuencia;    

    IF v_coincidencia =  0 THEN
           RAISE EXCEPTION '/MOVIMIENTO NO EXISTE';
    END IF;

    SELECT 
        b.ms_cod_cuenta,
        a.ms_valor,
        b.ms_saldo_disponible,
        a.ms_sec_reverso
    INTO 
        v_cuenta,
        v_valor,
        v_saldo,
        v_sec_reverso
    FROM ms_mov_monet a
    INNER JOIN ms_cuentas b
    on a.ms_cod_cuenta = b.ms_cod_cuenta
    WHERE a.ms_secuencia = e_secuencia;
    
    
    if v_sec_reverso is not null THEN
        RAISE EXCEPTION '/TRANSACCION DE MOVIMIENTO REVERSADA PREVIAMENTE';
    end if;

    if v_saldo - v_valor < 0 THEN
        RAISE EXCEPTION '/MOVIMIENTO NO PUEDE SER REVERSADO POR QUE EL CLIENTE NO TIENE DINERO';
    end if;


    BEGIN
        UPDATE ms_cuentas
        SET
            ms_saldo_disponible = v_saldo - v_valor
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
            'REV',
            v_saldo - v_valor,
            1*-v_valor
        );

        UPDATE ms_mov_monet
        SET 
            ms_sec_reverso = v_movimiento_sec
        WHERE ms_secuencia = e_secuencia;

    EXCEPTION
        WHEN OTHERS THEN
            RAISE EXCEPTION USING ERRCODE = SQLSTATE, MESSAGE =  'HA OCURRIDO UN ERROR INTERNO AL REVERSAR TRANSACCION DE MOVIMIENTO ' || SQLERRM  ;
    END;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION USING ERRCODE = SQLSTATE, MESSAGE = SQLERRM;
END;
$$ LANGUAGE plpgsql;