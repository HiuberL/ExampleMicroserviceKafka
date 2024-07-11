DROP FUNCTION IF EXISTS generar_movimiento;
CREATE OR REPLACE FUNCTION generar_movimiento() 
RETURNS BIGINT AS $$
DECLARE
    v_numero_cuenta BIGINT;
BEGIN
    v_numero_cuenta := CAST(nextval('secuencia_movimientos') AS BIGINT);
    RETURN v_numero_cuenta;
END;
$$ LANGUAGE plpgsql;