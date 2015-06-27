CREATE OR REPLACE
FUNCTION init_dominios()
  RETURNS void AS $$
BEGIN
    --
    -- Uso de los Candados
    --
    insert into rcb_domi_valo(dval_id, dval_codi, dval_nomb, dval_vige, dval_domi )
       values( 1, '1', 'PERSONAL', true, 'uso.candado' );
    insert into rcb_domi_valo(dval_id, dval_codi, dval_nomb, dval_vige, dval_domi )
       values( 2, '2', 'EVENTUAL', true, 'uso.candado' );
    insert into rcb_domi_valo(dval_id, dval_codi, dval_nomb, dval_vige, dval_domi )
       values( 3, '3', 'DEPARTAMENTAL', true, 'uso.candado' );
    --

    --
    -- Fuentes de Energía
    --
    insert into rcb_domi_valo(dval_id, dval_codi, dval_nomb, dval_vige, dval_domi )
       values( 4, '1', 'ELECTRICA', true, 'fuente.energia' );
    insert into rcb_domi_valo(dval_id, dval_codi, dval_nomb, dval_vige, dval_domi )
       values( 5, '2', 'HIDRAULICA', true, 'fuente.energia' );
    insert into rcb_domi_valo(dval_id, dval_codi, dval_nomb, dval_vige, dval_domi )
       values( 6, '3', 'NEUMATICA', true, 'fuente.energia' );
    insert into rcb_domi_valo(dval_id, dval_codi, dval_nomb, dval_vige, dval_domi )
       values( 7, '4', 'IONIZANTE', true, 'fuente.energia' );
    insert into rcb_domi_valo(dval_id, dval_codi, dval_nomb, dval_vige, dval_domi )
       values( 8, '5', 'OTRO', true, 'fuente.energia' );
    --
    
    --
    -- Tipo de Bloqueo
    --
    insert into rcb_domi_valo(dval_id, dval_codi, dval_nomb, dval_vige, dval_domi )
       values(  9, '1', 'DEPARTAMENTAL', true, 'tipo.bloqueo' );
    insert into rcb_domi_valo(dval_id, dval_codi, dval_nomb, dval_vige, dval_domi )
       values( 10, '2', 'PERSONAL', true, 'tipo.bloqueo' );
    insert into rcb_domi_valo(dval_id, dval_codi, dval_nomb, dval_vige, dval_domi )
       values( 11, '3', 'MULTIPLE', true, 'tipo.bloqueo' );
    insert into rcb_domi_valo(dval_id, dval_codi, dval_nomb, dval_vige, dval_domi )
       values( 12, '4', 'OTRO', true, 'tipo.bloqueo' );
    --
    
    --
    -- Dispositivos de Bloqueo
    --
    insert into rcb_domi_valo(dval_id, dval_codi, dval_nomb, dval_vige, dval_domi )
       values( 13, '1', 'CANDADO', true, 'dispositivo.bloqueo' );
    insert into rcb_domi_valo(dval_id, dval_codi, dval_nomb, dval_vige, dval_domi )
       values( 14, '2', 'CADENA', true, 'dispositivo.bloqueo' );
    insert into rcb_domi_valo(dval_id, dval_codi, dval_nomb, dval_vige, dval_domi )
       values( 15, '3', 'OTRO', true, 'dispositivo.bloqueo' );
    --
END;
$$ LANGUAGE plpgsql;
