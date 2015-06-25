CREATE OR REPLACE
FUNCTION init_bd( i_FechaInit timestamp )
  RETURNS void AS $$
DECLARE
    l_EstacionId    rcb_estc.estc_id%type;
BEGIN

    select nextval( 'SEQ_ID' ) into l_EstacionId;

    insert into RCB_ESTC (ESTC_ID, ESTC_NOMB, ESTC_VIGE, AUDI_FECH_CREA)
       values ( l_EstacionId
              ,'CHANCADOR SECUNDARIO PLANTA 2'
              ,1
              ,i_FechaInit )
    ;

    insert into rcb_ubic(ubic_id, ubic_nomb, ubic_desc, ubic_vige, estc_id, audi_fech_crea)
       values ( nextval( 'SEQ_ID' )
              ,'CHANCADOR SECUNDARIO PLANTA 2'
              ,'CHANCADOR SECUNDARIO PLANTA 2'
              ,1
              ,l_EstacionId
              ,i_FechaInit )
    ;

END;
$$ LANGUAGE plpgsql;
