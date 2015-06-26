CREATE OR REPLACE
FUNCTION init_bd( i_FechaInit timestamp )
  RETURNS void AS $$
DECLARE
    l_EstacionId    rcb_estc.estc_id%type;
    l_UbicacionId   rcb_ubic.ubic_id%type;
BEGIN

    delete from rcb_ubic;
    delete from rcb_estc;

    select nextval( 'SEQ_ID' ) into l_EstacionId;

    insert into RCB_ESTC (ESTC_ID, ESTC_NOMB, ESTC_VIGE, AUDI_FECH_CREA)
       values ( l_EstacionId
              ,'CHANCADOR SECUNDARIO PLANTA 2'
              ,true
              ,i_FechaInit )
    ;

    select nextval( 'SEQ_ID' ) into l_UbicacionId;

    insert into rcb_ubic(ubic_id, ubic_nomb, ubic_desc, ubic_vige, estc_id, audi_fech_crea)
       values ( l_UbicacionId
              ,'CHANCADOR SECUNDARIO PLANTA 2'
              ,'CHANCADOR SECUNDARIO PLANTA 2'
              ,true
              ,l_EstacionId
              ,i_FechaInit )
    ;

    insert into rcb_tokn (tokn_id, ubic_id, audi_fech_crea)
        values( uuid_generate_v1()
              , l_UbicacionId
              , localtimestamp );
END;
$$ LANGUAGE plpgsql;
