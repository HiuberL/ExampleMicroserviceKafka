DROP TABLE IF EXISTS ms_persona;

CREATE TABLE IF NOT EXISTS ms_persona (
    ms_cod_persona INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ms_nombre VARCHAR(40) NOT NULL,
    ms_apellido_paterno VARCHAR(30) NOT NULL,
    ms_apellido_materno VARCHAR(30) NULL,
    ms_genero CHAR(1) NOT NULL,
    ms_edad INT NOT NULL,
    ms_identificacion VARCHAR(13) NOT NULL,
    ms_direccion TEXT NULL,
    ms_telefono VARCHAR(10) NULL,    
    ms_fecha_reg TIMESTAMP NULL,
    ms_fecha_mod TIMESTAMP
);

CREATE INDEX idx_ms_persona_1 ON ms_persona (ms_identificacion);
