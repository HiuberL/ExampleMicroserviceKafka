DROP PROCEDURE IF EXISTS  pa_delim_persona ;

CREATE PROCEDURE pa_delim_persona (
    e_identificacion VARCHAR(20)
)
AS $$
DECLARE 
    v_coincidencia int;
    v_persona int;
BEGIN

    /*  VALIDACIONES INICIALES */
    SELECT COUNT(*) 
    INTO v_coincidencia
    FROM ms_persona
    WHERE ms_identificacion = e_identificacion;    

    IF v_coincidencia = 0 THEN
           RAISE EXCEPTION '/CLIENTE CON IDENTIFICACION % NO HA SIDO REGISTRADA', e_identificacion;
    END IF;

    SELECT  a.ms_cod_persona
    INTO
        v_persona
    FROM ms_persona a
    INNER JOIN ms_cliente b
    ON a.ms_cod_persona = b.ms_cod_cliente  
    WHERE ms_identificacion = e_identificacion;    

    BEGIN
        UPDATE ms_cliente
        SET ms_estado = 'E'
        WHERE ms_cod_cliente = v_persona;

    EXCEPTION
        WHEN OTHERS THEN
            RAISE EXCEPTION USING ERRCODE = SQLSTATE, MESSAGE =  'HA OCURRIDO UN ERROR INTERNO AL ELIMINAR EN INFORMACION '||  SQLERRM  ;
    END;

EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION USING ERRCODE = SQLSTATE, MESSAGE = SQLERRM;
END;
$$ LANGUAGE plpgsql;