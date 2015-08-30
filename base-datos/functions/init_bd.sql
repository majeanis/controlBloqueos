CREATE OR REPLACE
FUNCTION init_bd()
  RETURNS void AS $$
DECLARE
    l_EstacionId    rcb_estc.estc_id%type;
    l_UbicacionId   rcb_ubic.ubic_id%type;
BEGIN
    --
    -- Bloqueos Departamentales
    --
    insert into rcb_func_bloq(fubl_id, fubl_nomb, fubl_maxi, fubl_vige, audi_fech_crea)
      values(1, 'RESP. DEPTO. ELECTRICO', 1, true, localtimestamp );

    insert into rcb_func_bloq(fubl_id, fubl_nomb, fubl_maxi, fubl_vige, audi_fech_crea)
      values(2, 'RESP. DEPTO. OPERACIONES', 1, true, localtimestamp );

    insert into rcb_func_bloq(fubl_id, fubl_nomb, fubl_maxi, fubl_vige, audi_fech_crea)
      values(3, 'RESP. DEPTO. EJECUTOR', 1, true, localtimestamp );

    insert into rcb_func_bloq(fubl_id, fubl_nomb, fubl_maxi, fubl_vige, audi_fech_crea)
      values(4, 'EJECUTOR TRABAJO', null, true, localtimestamp );
    ---
    
    delete from rcb_ubic;
    delete from rcb_estc;

    select get_global_id() into l_EstacionId;

    insert into RCB_ESTC (ESTC_ID, ESTC_NOMB, ESTC_VIGE, AUDI_FECH_CREA)
       values ( l_EstacionId
              ,'CHANCADOR SECUNDARIO PLANTA 2'
              ,true
              ,localtimestamp )
    ;

    select get_global_id() into l_UbicacionId;

    insert into rcb_ubic(ubic_id, ubic_nomb, ubic_desc, ubic_vige, estc_id, audi_fech_crea)
       values ( l_UbicacionId
              ,'CHANCADOR SECUNDARIO PLANTA 2'
              ,'CHANCADOR SECUNDARIO PLANTA 2'
              ,true
              ,l_EstacionId
              ,localtimestamp )
    ;

    --
    -- Función para generar el Token
    -- uuid_generate_v1()
    insert into rcb_tokn (tokn_id, ubic_id, audi_fech_crea)
        values( '9cc5882c-2dd9-11e5-ac59-080027465435' 
              , l_UbicacionId
              , localtimestamp );

END;
$$ LANGUAGE plpgsql;
