DROP TABLE IF EXISTS ms_cliente;
CREATE TABLE IF NOT EXISTS ms_cliente (
    ms_cod_cliente INT not null PRIMARY KEY,
    ms_contrasenia TEXT not null,
    ms_estado char(1) not null,
    ms_fecha_reg TIMESTAMP NULL,
    ms_fecha_mod TIMESTAMP,
    FOREIGN KEY (ms_cod_cliente) REFERENCES ms_persona(ms_cod_persona) ON DELETE CASCADE
);

