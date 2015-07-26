--
-- ER/Studio Data Architect 10.0 SQL Code Generation
-- Project :      Registro y Control de Bloqueos
--
-- Date Created : Saturday, July 25, 2015 21:08:52
-- Target DBMS : PostgreSQL 9.x
--

-- 
-- TABLE: rcb_caja_bloq 
--

CREATE TABLE rcb_caja_bloq(
    cabl_id           numeric(14, 0)    NOT NULL,
    cabl_nume         numeric(3, 0)     NOT NULL,
    cabl_nomb         varchar(40)       NOT NULL,
    cabl_vige         boolean           NOT NULL,
    ubic_id           numeric(14, 0)    NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp
)
;



COMMENT ON COLUMN rcb_caja_bloq.audi_fech_modi IS 'Fecha de la última modificación del registro en la tabla'
;
COMMENT ON TABLE rcb_caja_bloq IS 'Cajas de Bloqueo'
;

-- 
-- TABLE: rcb_cand 
--

CREATE TABLE rcb_cand(
    cand_id           numeric(14, 0)    NOT NULL,
    cand_nume         numeric(4, 0)     NOT NULL,
    cand_seri         varchar(30)       NOT NULL,
    cand_vige         boolean           NOT NULL,
    dval_id_uso       numeric(14, 0)    NOT NULL,
    pers_id           numeric(14, 0)    NOT NULL,
    ubic_id           numeric(14, 0)    NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp
)
;



COMMENT ON COLUMN rcb_cand.cand_nume IS 'Número del Candado'
;
COMMENT ON COLUMN rcb_cand.audi_fech_modi IS 'Fecha de la última modificación del registro en la tabla'
;
COMMENT ON TABLE rcb_cand IS 'Candados'
;

-- 
-- TABLE: rcb_domi_valo 
--

CREATE TABLE rcb_domi_valo(
    dval_id      numeric(14, 0)    NOT NULL,
    dval_codi    varchar(40),
    dval_nomb    varchar(60)       NOT NULL,
    dval_vige    boolean           NOT NULL,
    dval_domi    varchar(40)       NOT NULL
)
;



COMMENT ON TABLE rcb_domi_valo IS 'Dominio de Valores'
;

-- 
-- TABLE: rcb_dota_lcbl 
--

CREATE TABLE rcb_dota_lcbl(
    lcbl_id           varchar(20)       NOT NULL,
    fubl_id           numeric(14, 0)    NOT NULL,
    empr_id           numeric(14, 0)    NOT NULL,
    pers_id           numeric(14, 0)    NOT NULL,
    cand_id           numeric(14, 0),
    pers_id_bloq      numeric(14, 0)    NOT NULL,
    pers_id_desb      numeric(14, 0)    NOT NULL,
    dolc_fech_bloq    timestamp,
    dolc_fech_desb    timestamp,
    aud_fech_crea     timestamp         NOT NULL,
    aud_fech_modi     timestamp
)
;



COMMENT ON COLUMN rcb_dota_lcbl.aud_fech_modi IS 'Fecha de la última modificación del registro en la tabla'
;
COMMENT ON TABLE rcb_dota_lcbl IS 'Dotación registrada en el Libro de Control de Bloqueos'
;

-- 
-- TABLE: rcb_empr 
--

CREATE TABLE rcb_empr(
    empr_id           numeric(14, 0)    NOT NULL,
    empr_rut          varchar(11)       NOT NULL,
    empr_nomb         varchar(60)       NOT NULL,
    empr_vige         boolean           NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp
)
;



COMMENT ON COLUMN rcb_empr.audi_fech_modi IS 'Fecha de la última modificación del registro en la tabla'
;
COMMENT ON TABLE rcb_empr IS 'Empresas'
;

-- 
-- TABLE: rcb_enrg_lcbl 
--

CREATE TABLE rcb_enrg_lcbl(
    lcbl_id          varchar(20)       NOT NULL,
    dval_id_item     numeric(14, 0)    NOT NULL,
    aud_fech_crea    timestamp         NOT NULL
)
;




-- 
-- TABLE: rcb_eqpo 
--

CREATE TABLE rcb_eqpo(
    eqpo_id           numeric(14, 0)    NOT NULL,
    eqpo_codi         varchar(20)       NOT NULL,
    eqpo_vige         boolean           NOT NULL,
    ubic_id           numeric(14, 0)    NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp
)
;



COMMENT ON COLUMN rcb_eqpo.audi_fech_modi IS 'Fecha de la última modificación del registro en la tabla'
;
COMMENT ON TABLE rcb_eqpo IS 'Equipos'
;

-- 
-- TABLE: rcb_estc 
--

CREATE TABLE rcb_estc(
    estc_id           numeric(14, 0)    NOT NULL,
    estc_nomb         varchar(60)       NOT NULL,
    estc_vige         boolean           NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp
)
;



COMMENT ON COLUMN rcb_estc.audi_fech_modi IS 'Fecha de la última modificación del registro en la tabla'
;
COMMENT ON TABLE rcb_estc IS 'Estaciones'
;

-- 
-- TABLE: rcb_func_bloq 
--

CREATE TABLE rcb_func_bloq(
    fubl_id           numeric(14, 0)    NOT NULL,
    fubl_nomb         varchar(40)       NOT NULL,
    fubl_maxi         numeric(2, 0),
    fubl_vige         boolean           NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp
)
;



COMMENT ON COLUMN rcb_func_bloq.audi_fech_modi IS 'Fecha de la última modificación del registro en la tabla'
;
COMMENT ON TABLE rcb_func_bloq IS 'Bloqueos Departamentales'
;

-- 
-- TABLE: rcb_libr_ctrl_bloq 
--

CREATE TABLE rcb_libr_ctrl_bloq(
    lcbl_id           varchar(20)       NOT NULL,
    lcbl_nume         numeric(6, 0)     NOT NULL,
    lcbl_fech         timestamp         NOT NULL,
    lcbl_cerr         boolean           NOT NULL,
    lcbl_fech_cerr    timestamp,
    cabl_id           numeric(14, 0),
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp
)
;



COMMENT ON COLUMN rcb_libr_ctrl_bloq.lcbl_nume IS 'Número del Libro'
;
COMMENT ON COLUMN rcb_libr_ctrl_bloq.audi_fech_modi IS 'Fecha de la última modificación del registro en la tabla'
;
COMMENT ON TABLE rcb_libr_ctrl_bloq IS 'Libros de Control de Bloqueos'
;

-- 
-- TABLE: rcb_pers 
--

CREATE TABLE rcb_pers(
    pers_id           numeric(14, 0)    NOT NULL,
    pers_rut          varchar(11)       NOT NULL,
    pers_nomb         varchar(60)       NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp
)
;



COMMENT ON COLUMN rcb_pers.audi_fech_modi IS 'Fecha de la última modificación del registro en la tabla'
;
COMMENT ON TABLE rcb_pers IS 'Personas'
;

-- 
-- TABLE: rcb_resp 
--

CREATE TABLE rcb_resp(
    resp_id           numeric(14, 0)    NOT NULL,
    ubic_id           numeric(14, 0)    NOT NULL,
    pers_id           numeric(14, 0)    NOT NULL,
    empr_id           numeric(14, 0)    NOT NULL,
    resp_fech_ingr    timestamp         NOT NULL,
    resp_fech_sali    timestamp
)
;



COMMENT ON TABLE rcb_resp IS 'Registro Históricos de Responsables'
;

-- 
-- TABLE: rcb_resp_lcbl 
--

CREATE TABLE rcb_resp_lcbl(
    relc_id           numeric(14, 0)    NOT NULL,
    lcbl_id           varchar(20)       NOT NULL,
    pers_id           numeric(14, 0)    NOT NULL,
    empr_id           numeric(14, 0)    NOT NULL,
    relc_fech_ingr    timestamp         NOT NULL,
    relc_fech_sali    timestamp,
    audi_fech_crea    timestamp         NOT NULL,
    aud_fech_modi     timestamp
)
;



COMMENT ON COLUMN rcb_resp_lcbl.aud_fech_modi IS 'Fecha de la última modificación del registro en la tabla'
;
COMMENT ON TABLE rcb_resp_lcbl IS 'Responsables asociados al Libro de Control de Bloqueos'
;

-- 
-- TABLE: rcb_taeq_lcbl 
--

CREATE TABLE rcb_taeq_lcbl(
    lcbl_id           varchar(20)       NOT NULL,
    taeq_id           numeric(14, 0)    NOT NULL,
    audi_fech_crea    timestamp         NOT NULL
)
;



COMMENT ON TABLE rcb_taeq_lcbl IS 'Equipos registrados en el Libro de Control de Bloqueos'
;

-- 
-- TABLE: rcb_tags_eqpo 
--

CREATE TABLE rcb_tags_eqpo(
    taeq_id           numeric(14, 0)    NOT NULL,
    taeq_nume         numeric(4, 0)     NOT NULL,
    taeq_nomb         varchar(40)       NOT NULL,
    taeq_desc         varchar(150),
    taeq_enrg_cero    boolean           NOT NULL,
    taeq_vige         boolean           NOT NULL,
    eqpo_id           numeric(14, 0)    NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp
)
;



COMMENT ON COLUMN rcb_tags_eqpo.audi_fech_modi IS 'Fecha de la última modificación del registro en la tabla'
;
COMMENT ON TABLE rcb_tags_eqpo IS 'TAGs de los Equipos'
;

-- 
-- TABLE: rcb_tokn 
--

CREATE TABLE rcb_tokn(
    tokn_id           varchar(40)       NOT NULL,
    ubic_id           numeric(14, 0)    NOT NULL,
    audi_fech_crea    timestamp         NOT NULL
)
;



COMMENT ON TABLE rcb_tokn IS 'Toke para identificación de Ubicaciones'
;

-- 
-- TABLE: rcb_trab 
--

CREATE TABLE rcb_trab(
    pers_id           numeric(14, 0)    NOT NULL,
    empr_id           numeric(14, 0)    NOT NULL,
    trab_carg         varchar(60)       NOT NULL,
    trab_curs_bloq    boolean           NOT NULL,
    trab_vige         boolean           NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp
)
;



COMMENT ON COLUMN rcb_trab.audi_fech_modi IS 'Fecha de la última modificación del registro en la tabla'
;
COMMENT ON TABLE rcb_trab IS 'Trabajadores'
;

-- 
-- TABLE: rcb_ubic 
--

CREATE TABLE rcb_ubic(
    ubic_id           numeric(14, 0)    NOT NULL,
    ubic_nomb         varchar(40)       NOT NULL,
    ubic_desc         varchar(150),
    ubic_vige         boolean           NOT NULL,
    estc_id           numeric(14, 0),
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp
)
;



COMMENT ON COLUMN rcb_ubic.audi_fech_modi IS 'Fecha de la última modificación del registro en la tabla'
;
COMMENT ON TABLE rcb_ubic IS 'Estación'
;

-- 
-- INDEX: cabl_uk 
--

CREATE UNIQUE INDEX cabl_uk ON rcb_caja_bloq(ubic_id, cabl_nume)
;
-- 
-- INDEX: cand_uk 
--

CREATE UNIQUE INDEX cand_uk ON rcb_cand(cand_seri)
;
-- 
-- INDEX: cand_nume_uk 
--

CREATE UNIQUE INDEX cand_nume_uk ON rcb_cand(cand_nume, ubic_id)
;
-- 
-- INDEX: dval_uk 
--

CREATE UNIQUE INDEX dval_uk ON rcb_domi_valo(dval_domi, dval_codi)
;
-- 
-- INDEX: empr_uk 
--

CREATE UNIQUE INDEX empr_uk ON rcb_empr(empr_rut)
;
-- 
-- INDEX: eqpo_uk 
--

CREATE UNIQUE INDEX eqpo_uk ON rcb_eqpo(ubic_id, eqpo_codi)
;
-- 
-- INDEX: lcbl_uk 
--

CREATE UNIQUE INDEX lcbl_uk ON rcb_libr_ctrl_bloq(lcbl_nume, cabl_id)
;
-- 
-- INDEX: pers_uk 
--

CREATE UNIQUE INDEX pers_uk ON rcb_pers(pers_rut)
;
-- 
-- INDEX: resp_uk 
--

CREATE UNIQUE INDEX resp_uk ON rcb_resp(pers_id, empr_id, resp_fech_ingr)
;
-- 
-- INDEX: relc_uk 
--

CREATE UNIQUE INDEX relc_uk ON rcb_resp_lcbl(lcbl_id, empr_id, pers_id, relc_fech_ingr)
;
-- 
-- INDEX: taeq_uk 
--

CREATE UNIQUE INDEX taeq_uk ON rcb_tags_eqpo(eqpo_id, taeq_nume)
;
-- 
-- TABLE: rcb_caja_bloq 
--

ALTER TABLE rcb_caja_bloq ADD 
    CONSTRAINT cabl_pk PRIMARY KEY (cabl_id) USING INDEX TABLESPACE recob_ind 
;

-- 
-- TABLE: rcb_cand 
--

ALTER TABLE rcb_cand ADD 
    CONSTRAINT cand_pk PRIMARY KEY (cand_id) USING INDEX TABLESPACE recob_ind 
;

-- 
-- TABLE: rcb_domi_valo 
--

ALTER TABLE rcb_domi_valo ADD 
    CONSTRAINT dval_id PRIMARY KEY (dval_id) USING INDEX TABLESPACE recob_ind 
;

-- 
-- TABLE: rcb_dota_lcbl 
--

ALTER TABLE rcb_dota_lcbl ADD 
    CONSTRAINT dblq_pk PRIMARY KEY (lcbl_id, pers_id, empr_id, fubl_id) USING INDEX TABLESPACE recob_ind 
;

-- 
-- TABLE: rcb_empr 
--

ALTER TABLE rcb_empr ADD 
    CONSTRAINT empr_pk PRIMARY KEY (empr_id) USING INDEX TABLESPACE recob_ind 
;

-- 
-- TABLE: rcb_enrg_lcbl 
--

ALTER TABLE rcb_enrg_lcbl ADD 
    CONSTRAINT enlc_pk PRIMARY KEY (lcbl_id, dval_id_item) USING INDEX TABLESPACE recob_ind 
;

-- 
-- TABLE: rcb_eqpo 
--

ALTER TABLE rcb_eqpo ADD 
    CONSTRAINT eqpo_pk PRIMARY KEY (eqpo_id) USING INDEX TABLESPACE recob_ind 
;

-- 
-- TABLE: rcb_estc 
--

ALTER TABLE rcb_estc ADD 
    CONSTRAINT estc_pk PRIMARY KEY (estc_id) USING INDEX TABLESPACE recob_ind 
;

-- 
-- TABLE: rcb_func_bloq 
--

ALTER TABLE rcb_func_bloq ADD 
    CONSTRAINT fubl_pk PRIMARY KEY (fubl_id)
;

-- 
-- TABLE: rcb_libr_ctrl_bloq 
--

ALTER TABLE rcb_libr_ctrl_bloq ADD 
    CONSTRAINT lcbl_pk PRIMARY KEY (lcbl_id) USING INDEX TABLESPACE recob_ind 
;

-- 
-- TABLE: rcb_pers 
--

ALTER TABLE rcb_pers ADD 
    CONSTRAINT pers_pk PRIMARY KEY (pers_id) USING INDEX TABLESPACE recob_ind 
;

-- 
-- TABLE: rcb_resp 
--

ALTER TABLE rcb_resp ADD 
    CONSTRAINT resp_pk PRIMARY KEY (resp_id)
;

-- 
-- TABLE: rcb_resp_lcbl 
--

ALTER TABLE rcb_resp_lcbl ADD 
    CONSTRAINT relc_pk PRIMARY KEY (relc_id) USING INDEX TABLESPACE recob_ind 
;

-- 
-- TABLE: rcb_taeq_lcbl 
--

ALTER TABLE rcb_taeq_lcbl ADD 
    CONSTRAINT eqlc_pk PRIMARY KEY (lcbl_id, taeq_id)
;

-- 
-- TABLE: rcb_tags_eqpo 
--

ALTER TABLE rcb_tags_eqpo ADD 
    CONSTRAINT taeq_pk PRIMARY KEY (taeq_id) USING INDEX TABLESPACE recob_ind 
;

-- 
-- TABLE: rcb_tokn 
--

ALTER TABLE rcb_tokn ADD 
    CONSTRAINT tokn_pk PRIMARY KEY (tokn_id)
;

-- 
-- TABLE: rcb_trab 
--

ALTER TABLE rcb_trab ADD 
    CONSTRAINT trab_pk PRIMARY KEY (pers_id, empr_id) USING INDEX TABLESPACE recob_ind 
;

-- 
-- TABLE: rcb_ubic 
--

ALTER TABLE rcb_ubic ADD 
    CONSTRAINT ubic_pk PRIMARY KEY (ubic_id) USING INDEX TABLESPACE recob_ind 
;

-- 
-- TABLE: rcb_caja_bloq 
--

ALTER TABLE rcb_caja_bloq ADD CONSTRAINT cabl_ubic_fk 
    FOREIGN KEY (ubic_id)
    REFERENCES rcb_ubic(ubic_id)
;


-- 
-- TABLE: rcb_cand 
--

ALTER TABLE rcb_cand ADD CONSTRAINT cand_pers_fk 
    FOREIGN KEY (pers_id)
    REFERENCES rcb_pers(pers_id)
;

ALTER TABLE rcb_cand ADD CONSTRAINT cand_ubic_fk 
    FOREIGN KEY (ubic_id)
    REFERENCES rcb_ubic(ubic_id)
;

ALTER TABLE rcb_cand ADD CONSTRAINT cand_usoc_fk 
    FOREIGN KEY (dval_id_uso)
    REFERENCES rcb_domi_valo(dval_id) ON DELETE RESTRICT ON UPDATE RESTRICT
;


-- 
-- TABLE: rcb_dota_lcbl 
--

ALTER TABLE rcb_dota_lcbl ADD CONSTRAINT dolc_cand_fk 
    FOREIGN KEY (cand_id)
    REFERENCES rcb_cand(cand_id)
;

ALTER TABLE rcb_dota_lcbl ADD CONSTRAINT dolc_fubl_fk 
    FOREIGN KEY (fubl_id)
    REFERENCES rcb_func_bloq(fubl_id)
;

ALTER TABLE rcb_dota_lcbl ADD CONSTRAINT dolc_lcbl_fk 
    FOREIGN KEY (lcbl_id)
    REFERENCES rcb_libr_ctrl_bloq(lcbl_id)
;

ALTER TABLE rcb_dota_lcbl ADD CONSTRAINT dolc_pers_fk 
    FOREIGN KEY (pers_id_bloq)
    REFERENCES rcb_pers(pers_id)
;

ALTER TABLE rcb_dota_lcbl ADD CONSTRAINT dolc_pers_fk 
    FOREIGN KEY (pers_id_desb)
    REFERENCES rcb_pers(pers_id)
;

ALTER TABLE rcb_dota_lcbl ADD CONSTRAINT dolc_trab_fk 
    FOREIGN KEY (pers_id, empr_id)
    REFERENCES rcb_trab(pers_id, empr_id)
;


-- 
-- TABLE: rcb_enrg_lcbl 
--

ALTER TABLE rcb_enrg_lcbl ADD CONSTRAINT enlc_item_fk 
    FOREIGN KEY (dval_id_item)
    REFERENCES rcb_domi_valo(dval_id)
;

ALTER TABLE rcb_enrg_lcbl ADD CONSTRAINT enlc_lcbl_fk 
    FOREIGN KEY (lcbl_id)
    REFERENCES rcb_libr_ctrl_bloq(lcbl_id)
;


-- 
-- TABLE: rcb_eqpo 
--

ALTER TABLE rcb_eqpo ADD CONSTRAINT eqpo_ubic_fk 
    FOREIGN KEY (ubic_id)
    REFERENCES rcb_ubic(ubic_id)
;


-- 
-- TABLE: rcb_libr_ctrl_bloq 
--

ALTER TABLE rcb_libr_ctrl_bloq ADD CONSTRAINT lcbl_cabl_fk 
    FOREIGN KEY (cabl_id)
    REFERENCES rcb_caja_bloq(cabl_id)
;


-- 
-- TABLE: rcb_resp 
--

ALTER TABLE rcb_resp ADD CONSTRAINT resp_trab_fk 
    FOREIGN KEY (pers_id, empr_id)
    REFERENCES rcb_trab(pers_id, empr_id)
;

ALTER TABLE rcb_resp ADD CONSTRAINT resp_ubic_fk 
    FOREIGN KEY (ubic_id)
    REFERENCES rcb_ubic(ubic_id)
;


-- 
-- TABLE: rcb_resp_lcbl 
--

ALTER TABLE rcb_resp_lcbl ADD CONSTRAINT relc_lcbl_fk 
    FOREIGN KEY (lcbl_id)
    REFERENCES rcb_libr_ctrl_bloq(lcbl_id)
;

ALTER TABLE rcb_resp_lcbl ADD CONSTRAINT relc_trab_fk 
    FOREIGN KEY (pers_id, empr_id)
    REFERENCES rcb_trab(pers_id, empr_id)
;


-- 
-- TABLE: rcb_taeq_lcbl 
--

ALTER TABLE rcb_taeq_lcbl ADD CONSTRAINT eqlc_lcbl_fk 
    FOREIGN KEY (lcbl_id)
    REFERENCES rcb_libr_ctrl_bloq(lcbl_id)
;

ALTER TABLE rcb_taeq_lcbl ADD CONSTRAINT talc_taeq_fk 
    FOREIGN KEY (taeq_id)
    REFERENCES rcb_tags_eqpo(taeq_id)
;


-- 
-- TABLE: rcb_tags_eqpo 
--

ALTER TABLE rcb_tags_eqpo ADD CONSTRAINT taeq_eqpo_fk 
    FOREIGN KEY (eqpo_id)
    REFERENCES rcb_eqpo(eqpo_id)
;


-- 
-- TABLE: rcb_tokn 
--

ALTER TABLE rcb_tokn ADD CONSTRAINT tokn_ubic_fk 
    FOREIGN KEY (ubic_id)
    REFERENCES rcb_ubic(ubic_id)
;


-- 
-- TABLE: rcb_trab 
--

ALTER TABLE rcb_trab ADD CONSTRAINT trab_empr_fk 
    FOREIGN KEY (empr_id)
    REFERENCES rcb_empr(empr_id)
;

ALTER TABLE rcb_trab ADD CONSTRAINT trab_pers_fk 
    FOREIGN KEY (pers_id)
    REFERENCES rcb_pers(pers_id)
;


-- 
-- TABLE: rcb_ubic 
--

ALTER TABLE rcb_ubic ADD CONSTRAINT ubic_estc_fk 
    FOREIGN KEY (estc_id)
    REFERENCES rcb_estc(estc_id)
;


