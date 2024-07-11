DROP PROCEDURE IF EXISTS  pa_delim_cuenta ;

CREATE PROCEDURE pa_delim_cuenta (
    e_cliente INT,
    e_cuenta VARCHAR(20)
)
AS $$
DECLARE 
    v_coincidencia int;
    v_estado CHAR(1);
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
        c.ms_estado
    INTO v_estado
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

    BEGIN
        UPDATE ms_cuentas
        SET
            ms_estado = 'E'
        WHERE ms_cod_cliente = e_cliente   
        AND ms_cuenta = e_cuenta;

    EXCEPTION
        WHEN OTHERS THEN
            RAISE EXCEPTION USING ERRCODE = SQLSTATE, MESSAGE =  'HA OCURRIDO UN ERROR INTERNO AL ELIMINAR EN INFORMACION '||  SQLERRM  ;
    END;

EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION USING ERRCODE = SQLSTATE, MESSAGE = SQLERRM;
END;
$$ LANGUAGE plpgsql;