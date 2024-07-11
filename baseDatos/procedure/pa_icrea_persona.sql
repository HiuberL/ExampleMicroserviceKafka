DROP PROCEDURE IF EXISTS  pa_icrea_persona ;

CREATE PROCEDURE pa_icrea_persona (
    e_identificacion VARCHAR(20),
    e_nombre VARCHAR(40),
    e_apellido_p VARCHAR(30),
    e_apellido_m VARCHAR(30),
    e_genero CHAR(1),
    e_edad INT,
    e_direccion TEXT,
    e_telefono VARCHAR(10),
    e_contrasena TEXT,
    o_codigo INOUT INTEGER
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

    IF v_coincidencia <> 0 THEN
           RAISE EXCEPTION '/CLIENTE CON IDENTIFICACION % YA HA SIDO REGISTRADA', e_identificacion;
    END IF;


    v_cuenta_sec := cuentas.generar_cuenta();
    BEGIN
        INSERT INTO ms_persona (
            ms_nombre,
            ms_apellido_paterno,
            ms_apellido_materno,
            ms_genero,
            ms_edad,
            ms_identificacion,
            ms_direccion,
            ms_telefono,    
            ms_fecha_reg,
            ms_fecha_mod
        )
        VALUES(
        e_nombre,
        e_apellido_p,
        e_apellido_m,
        e_genero,
        e_edad,
        e_identificacion,
        e_direccion,
        e_telefono,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
        );
            

    EXCEPTION
        WHEN OTHERS THEN
            RAISE EXCEPTION USING ERRCODE = SQLSTATE, MESSAGE =  'HA OCURRIDO UN ERROR INTERNO AL INSERTAR EN INFORMACION '||  SQLERRM  ;
    END;

    SELECT ms_cod_persona
    INTO v_persona
    FROM ms_persona
    WHERE ms_identificacion = e_identificacion;    


        BEGIN
        INSERT INTO ms_cliente (
            ms_cod_cliente,
            ms_contrasenia,
            ms_estado,
            ms_fecha_reg,
            ms_fecha_mod
        )
        VALUES(
        v_persona,
        MD5(e_contrasena),
        'V', --VIGENTE
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
        );
            

    EXCEPTION
        WHEN OTHERS THEN
            RAISE EXCEPTION USING ERRCODE = SQLSTATE, MESSAGE =  'HA OCURRIDO UN ERROR INTERNO AL INSERTAR EN INFORMACION '||  SQLERRM  ;
    END;
    o_codigo := v_persona;
EXCEPTION
    WHEN OTHERS THEN
        RAISE EXCEPTION USING ERRCODE = SQLSTATE, MESSAGE = SQLERRM;
END;
$$ LANGUAGE plpgsql;