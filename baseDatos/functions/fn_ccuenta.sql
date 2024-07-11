DROP FUNCTION IF EXISTS cconsulta_cuenta;

CREATE OR REPLACE FUNCTION cconsulta_cuenta(
        e_code INTEGER
)
RETURNS TABLE(
        "accountCode" INTEGER,
        "accountNumber" VARCHAR(20), 
        "accountType" CHAR(3),
        "availableBalance" money,
        "status" CHAR(1))
AS $$
DECLARE 
    v_coincidencia int;
    v_persona int;
BEGIN

    SELECT COUNT(*) 
    INTO v_coincidencia
    FROM ms_persona a
    INNER JOIN ms_cliente b
    ON a.ms_cod_persona = b.ms_cod_cliente
    WHERE a.ms_cod_persona = e_code   
    and b.ms_estado = 'V';

    IF v_coincidencia = 0 THEN
        RAISE EXCEPTION '/CLIENTE  NO EXISTE O EN ESTADO INACTIVO O ELIMINADO';
    END IF;


    RETURN QUERY
    SELECT 
        c.ms_cod_cuenta,
        c.ms_cuenta,
        c.ms_tipo,
        c.ms_saldo_disponible,
        c.ms_estado
    FROM ms_persona a
    INNER JOIN ms_cliente b
    on a.ms_cod_persona = b.ms_cod_cliente
    INNER JOIN ms_cuentas c
    on b.ms_cod_cliente = c.ms_cod_cliente
    WHERE a.ms_cod_persona = e_code   
    and b.ms_estado = 'V';


END;
$$ LANGUAGE plpgsql;