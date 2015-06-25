CREATE OR REPLACE
FUNCTION get_nuevo_id()
    RETURNS varchar AS $$
BEGIN
    return nextval( 'SEQ_ID' );
END;
$$ LANGUAGE plpgsql;
