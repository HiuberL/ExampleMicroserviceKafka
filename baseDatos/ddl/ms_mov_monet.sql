
DROP SEQUENCE IF EXISTS secuencia_movimientos;

CREATE SEQUENCE secuencia_movimientos
  START WITH 1
  INCREMENT BY 1
  NO CYCLE
  MAXVALUE 99999999999;

DROP TABLE IF EXISTS ms_mov_monet;
CREATE TABLE IF NOT EXISTS ms_mov_monet (
    ms_secuencia BIGINT NOT NULL PRIMARY KEY,
    ms_fecha Date not null,
    ms_cod_cuenta int not null,
    ms_tipo CHAR(5) not null,
    ms_saldo MONEY NULL,
    ms_sec_reverso BIGINT NULL,
    ms_valor MONEY NOT NULL
);

CREATE INDEX idx_ms_mov_monet_1 ON ms_mov_monet (ms_cod_cuenta,ms_tipo);
CREATE INDEX idx_ms_mov_monet_2 ON ms_mov_monet (ms_fecha,ms_secuencia);