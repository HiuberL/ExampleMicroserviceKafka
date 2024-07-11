DROP FUNCTION IF EXISTS cconsulta_movimiento;

CREATE OR REPLACE FUNCTION cconsulta_movimiento(
        e_codigo_cuenta INTEGER,
        e_fecha_inicial VARCHAR(10),
        e_fecha_final VARCHAR(10)
)
RETURNS TABLE(
        "sequence" BIGINT,
        "date" DATE, 
        "type" CHAR(5),
        "availableBalance" money,
        "value" money)
AS $$

BEGIN


    RETURN QUERY
    SELECT 
            a.ms_secuencia,
            a.ms_fecha,
            a.ms_tipo,
            a.ms_saldo,
            a.ms_valor
    FROM ms_mov_monet a
    WHERE a.ms_fecha >= to_date(e_fecha_inicial,'MM/DD/YYYY')  
    and a.ms_fecha <=to_date(e_fecha_final,'MM/DD/YYYY')
    and a.ms_cod_cuenta = e_codigo_cuenta;


END;
$$ LANGUAGE plpgsql;