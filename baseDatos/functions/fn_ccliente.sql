DROP FUNCTION IF EXISTS cconsulta_cliente;

CREATE OR REPLACE FUNCTION cconsulta_cliente(
        e_identificacion VARCHAR,
        e_tipo VARCHAR,
        e_ultimo INTEGER
)
RETURNS TABLE(
        "personCode" INTEGER,
        "name" VARCHAR(40), 
        "lastName" VARCHAR(30),
        "sLastName" VARCHAR(30),
        "gender" CHAR(1),
        "age" INT,
        "dni" VARCHAR(13),
        "address" TEXT,
        "phone" VARCHAR(10),
        "dateRegister" TIMESTAMP,
        "dateModify" TIMESTAMP,
        "state" CHAR(1))
AS $$
DECLARE 
    v_coincidencia int;
    v_persona int;
BEGIN

    IF e_tipo = 'S' THEN
        /*  VALIDACIONES INICIALES */
        SELECT COUNT(*) 
        INTO v_coincidencia
        FROM ms_persona
        WHERE ms_identificacion = e_identificacion;    

        IF v_coincidencia = 0 THEN
            RAISE EXCEPTION '/CLIENTE % NO EXISTE', e_identificacion;
        END IF;

        RETURN QUERY
        SELECT 
            a.ms_cod_persona,
            a.ms_nombre,
            a.ms_apellido_paterno,
            a.ms_apellido_materno,
            a.ms_genero,
            a.ms_edad,
            a.ms_identificacion,
            a.ms_direccion,
            a.ms_telefono,    
            a.ms_fecha_reg,
            a.ms_fecha_mod,
            b.ms_estado
        FROM ms_persona a
        INNER JOIN ms_cliente b
        on a.ms_cod_persona = b.ms_cod_cliente
        WHERE a.ms_identificacion = e_identificacion;

    END IF;

    IF e_tipo = 'T' THEN
        
        e_ultimo := COALESCE(e_ultimo,0);

        RETURN QUERY
        SELECT 
            a.ms_cod_persona,
            a.ms_nombre,
            a.ms_apellido_paterno,
            a.ms_apellido_materno,
            a.ms_genero,
            a.ms_edad,
            a.ms_identificacion,
            a.ms_direccion,
            a.ms_telefono,
            a.ms_fecha_reg,
            a.ms_fecha_mod,
            b.ms_estado
        FROM ms_persona a
        INNER JOIN ms_cliente b
        ON a.ms_cod_persona = b.ms_cod_cliente
        WHERE a.ms_cod_persona > e_ultimo
        ORDER BY a.ms_cod_persona ASC
        LIMIT 10;

    END IF;
END;
$$ LANGUAGE plpgsql;