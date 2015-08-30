CREATE OR REPLACE
FUNCTION get_global_id()
    RETURNS numeric AS $$
DECLARE
    l_Time  timestamp := localtimestamp;
    l_Id    bigint;
BEGIN
    return to_char( l_Time, 'yyyymmddhh24miss' ) || to_char( nextval( 'SEQ_GID' ), 'FM00000' );
END;
$$ LANGUAGE plpgsql;
