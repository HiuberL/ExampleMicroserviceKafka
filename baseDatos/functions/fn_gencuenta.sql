DROP FUNCTION IF EXISTS generar_cuenta;
CREATE OR REPLACE FUNCTION generar_cuenta() 
RETURNS VARCHAR(20) AS $$
DECLARE
    v_numero_cuenta VARCHAR(20);
BEGIN
    v_numero_cuenta := LPAD(CAST(nextval('secuencia_cuentas') AS VARCHAR), 12, '0');
    RETURN v_numero_cuenta;
END;
$$ LANGUAGE plpgsql;