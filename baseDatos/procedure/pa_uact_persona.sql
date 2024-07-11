DROP PROCEDURE IF EXISTS  pa_uact_persona ;

CREATE PROCEDURE pa_uact_persona (
    e_identificacion VARCHAR(20),
    e_nombre VARCHAR(40),
    e_apellido_p VARCHAR(30),
    e_apellido_m VARCHAR(30),
    e_genero CHAR(1),
    e_edad INT,
    e_direccion TEXT,
    e_telefono VARCHAR(10),
    e_contrasena TEXT,
    e_estado CHAR(1)
)
AS $$
DECLARE 
    v_coincidencia int;
    v_persona int;
    v_nombre VARCHAR(40);
    v_apellido_p VARCHAR(30);
    v_apellido_m VARCHAR(30);
    v_genero CHAR(1);
    v_edad INT;
    v_direccion TEXT;
    v_telefono VARCHAR(10);
    v_contrasena TEXT;
    v_estado CHAR(1);
BEGIN

    /*  VALIDACIONES INICIALES */
    SELECT COUNT(*) 
    INTO v_coincidencia
    FROM ms_persona
    WHERE ms_identificacion = e_identificacion;    

    IF v_coincidencia = 0 THEN
           RAISE EXCEPTION '/CLIENTE CON IDENTIFICACION % NO HA SIDO REGISTRADA', e_identificacion;
    END IF;

    SELECT  a.ms_cod_persona,
            a.ms_nombre,
            a.ms_apellido_paterno,
            a.ms_apellido_materno,
            a.ms_genero,
            a.ms_edad,
            a.ms_direccion,
            a.ms_telefono,   
            b.ms_contrasenia,
            b.ms_estado
    INTO
        v_persona,
        v_nombre,
        v_apellido_p,
        v_apellido_m,
        v_genero,
        v_edad,
        v_direccion,
        v_telefono,
        v_contrasena,
        v_estado
    FROM ms_persona a
    INNER JOIN ms_cliente b
    ON a.ms_cod_persona = b.ms_cod_cliente  
    WHERE ms_identificacion = e_identificacion;    

    e_nombre := COALESCE(e_nombre,v_nombre);
    e_apellido_p := COALESCE(e_apellido_p,v_apellido_p);
    e_apellido_m := COALESCE(e_apellido_m,v_apellido_m);
    e_genero := COALESCE(e_genero,v_genero);
    e_edad := COALESCE(e_edad,v_edad);
    e_direccion := COALESCE(e_direccion,v_direccion);
    e_telefono := COALESCE(e_telefono,v_telefono);
    e_contrasena := COALESCE(e_contrasena,MD5(v_contrasena));
    e_estado := COALESCE(e_estado,v_estado);

    BEGIN
        UPDATE ms_persona
        SET
            ms_nombre = e_nombre,
            ms_apellido_paterno = e_apellido_p,
            ms_apellido_materno = e_apellido_m,
            ms_genero = e_genero,
            ms_edad = e_edad,
            ms_direccion = e_direccion,
            ms_telefono = e_telefono,    
            ms_fecha_mod =  CURRENT_TIMESTAMP
        WHERE ms_cod_persona = v_persona;             

    EXCEPTION
        WHEN OTHERS THEN
            RAISE EXCEPTION USING ERRCODE = SQLSTATE, MESSAGE =  'HA OCURRIDO UN ERROR INTERNO AL ACTUALIZAR EN INFORMACION '||  SQLERRM  ;
    END;


    BEGIN
        UPDATE ms_cliente 
        SET
            ms_contrasenia = e_contrasena,
            ms_estado = e_estado,
            ms_fecha_mod = CURRENT_TIMESTAMP
        WHERE ms_cod_cliente = v_persona;
            

    EXCEPTION
        WHEN OTHERS THEN
            RAISE EXCEPTION USING ERRCODE = SQLSTATE, MESSAGE =  'HA OCURRIDO UN ERROR INTERNO AL ACTUALIZAR EN INFORMACION '||  SQLERRM  ;
    END;

EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION USING ERRCODE = SQLSTATE, MESSAGE = SQLERRM;
END;
$$ LANGUAGE plpgsql;