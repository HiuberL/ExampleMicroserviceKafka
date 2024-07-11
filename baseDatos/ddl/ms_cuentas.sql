
DROP SEQUENCE IF EXISTS secuencia_cuentas;

CREATE SEQUENCE secuencia_cuentas
  START WITH 100
  INCREMENT BY 1
  NO CYCLE
  MAXVALUE 9999999999;

DROP TABLE IF EXISTS ms_cuentas;
CREATE TABLE IF NOT EXISTS ms_cuentas (
    ms_cod_cuenta INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    ms_cuenta VARCHAR(20) not null,
    ms_tipo CHAR(3) not null,
    ms_cod_cliente INT NULL,
    ms_saldo_disponible MONEY NOT NULL,
    ms_estado CHAR(1) NOT NULL
);

CREATE INDEX idx_ms_cuentas_1 ON ms_cuentas (ms_cod_cliente);
CREATE INDEX idx_ms_cuentas_2 ON ms_cuentas (ms_cuenta);
CREATE INDEX idx_ms_cuentas_3 ON ms_cuentas (ms_cuenta,ms_cod_cliente);