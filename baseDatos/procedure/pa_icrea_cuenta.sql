DROP PROCEDURE IF EXISTS  pa_icrea_cuenta ;

CREATE PROCEDURE pa_icrea_cuenta (
    e_cliente INT,
    e_tipo CHAR(3)
)
AS $$
DECLARE 
    v_coincidencia int;
    v_cuenta_sec varchar(20);

BEGIN

    /*  VALIDACIONES INICIALES */
    SELECT COUNT(*) 
    INTO v_coincidencia
    FROM ms_persona
    WHERE ms_cod_persona = e_cliente;    

    IF v_coincidencia =  0 THEN
           RAISE EXCEPTION '/CLIENTE NO HA SIDO REGISTRADO';
    END IF;


    BEGIN
        v_cuenta_sec := generar_cuenta();
        INSERT INTO ms_cuentas (
            ms_cuenta,
            ms_tipo,
            ms_cod_cliente,
            ms_saldo_disponible,
            ms_estado
        )
        VALUES(
            v_cuenta_sec,
            e_tipo,
            e_cliente,
            0,
            'A'
        );
            

    EXCEPTION
        WHEN OTHERS THEN
            RAISE EXCEPTION USING ERRCODE = SQLSTATE, MESSAGE =  'HA OCURRIDO UN ERROR INTERNO AL INSERTAR EN INFORMACION '||  SQLERRM  ;
    END;

EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION USING ERRCODE = SQLSTATE, MESSAGE = SQLERRM;
END;
$$ LANGUAGE plpgsql;