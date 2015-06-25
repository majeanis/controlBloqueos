--
-- ER/Studio Data Architect 10.0 SQL Code Generation
-- Project :      ModeloLogico-ControlDeBloqueos.dm1
--
-- Date Created : Thursday, June 25, 2015 14:30:52
-- Target DBMS : PostgreSQL 9.x
--

-- 
-- TABLE: rcb_bloq_depa 
--

CREATE TABLE rcb_bloq_depa(
    blde_id           numeric(14, 0)    NOT NULL,
    blde_nomb         varchar(40)       NOT NULL,
    blde_maxi         numeric(2, 0),
    blde_vige         numeric(1, 0)     NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp,
    CONSTRAINT blde_pk PRIMARY KEY (blde_id) USING INDEX TABLESPACE recob_ind 
)
;



-- 
-- TABLE: rcb_caja_bloq 
--

CREATE TABLE rcb_caja_bloq(
    cabl_id           numeric(14, 0)    NOT NULL,
    cabl_nume         numeric(3, 0)     NOT NULL,
    cabl_nomb         varchar(40)       NOT NULL,
    cabl_vige         numeric(1, 0)     NOT NULL,
    ubic_id           numeric(14, 0)    NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp,
    CONSTRAINT cabl_pk PRIMARY KEY (cabl_id) USING INDEX TABLESPACE recob_ind 
)
;



-- 
-- TABLE: rcb_cand 
--

CREATE TABLE rcb_cand(
    cand_id           numeric(14, 0)    NOT NULL,
    cand_nume         numeric(3, 0)     NOT NULL,
    cand_vige         numeric(1, 0)     NOT NULL,
    cabl_id           numeric(14, 0)    NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp,
    CONSTRAINT cand_pk PRIMARY KEY (cand_id) USING INDEX TABLESPACE recob_ind 
)
;



-- 
-- TABLE: rcb_domi_valo 
--

CREATE TABLE rcb_domi_valo(
    dval_id      numeric(14, 0)    NOT NULL,
    dval_codi    varchar(40),
    dval_nomb    varchar(60)       NOT NULL,
    dval_vige    numeric(1, 0)     NOT NULL,
    dval_domi    varchar(40)       NOT NULL,
    CONSTRAINT dval_id PRIMARY KEY (dval_id) USING INDEX TABLESPACE recob_ind 
)
;



-- 
-- TABLE: rcb_dota_lcbl 
--

CREATE TABLE rcb_dota_lcbl(
    lcbl_id           numeric(18, 0)    NOT NULL,
    blde_id           numeric(14, 0)    NOT NULL,
    empr_id           numeric(14, 0)    NOT NULL,
    pers_id           numeric(14, 0)    NOT NULL,
    cand_id           numeric(14, 0),
    pers_id_bloq      numeric(14, 0)    NOT NULL,
    pers_id_desb      numeric(14, 0)    NOT NULL,
    dolc_fech_bloq    timestamp,
    dolc_fech_desb    timestamp,
    CONSTRAINT dblq_pk PRIMARY KEY (lcbl_id, pers_id, empr_id, blde_id) USING INDEX TABLESPACE recob_ind 
)
;



-- 
-- TABLE: rcb_empr 
--

CREATE TABLE rcb_empr(
    empr_id           numeric(14, 0)    NOT NULL,
    empr_rut          varchar(11)       NOT NULL,
    empr_nomb         varchar(60)       NOT NULL,
    empr_vige         numeric(1, 0)     NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp,
    CONSTRAINT empr_pk PRIMARY KEY (empr_id) USING INDEX TABLESPACE recob_ind 
)
;



-- 
-- TABLE: rcb_enrg_lcbl 
--

CREATE TABLE rcb_enrg_lcbl(
    lcbl_id         numeric(18, 0)    NOT NULL,
    dval_id_item    numeric(14, 0)    NOT NULL,
    CONSTRAINT enlc_pk PRIMARY KEY (lcbl_id, dval_id_item) USING INDEX TABLESPACE recob_ind 
)
;



-- 
-- TABLE: rcb_eqpo 
--

CREATE TABLE rcb_eqpo(
    eqpo_id           numeric(14, 0)    NOT NULL,
    eqpo_codi         varchar(20)       NOT NULL,
    eqpo_vige         numeric(1, 0)     NOT NULL
                      CHECK (eqpo_vige IN (0)),
    ubic_id           numeric(14, 0)    NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp,
    CONSTRAINT eqpo_pk PRIMARY KEY (eqpo_id) USING INDEX TABLESPACE recob_ind 
)
;



-- 
-- TABLE: rcb_eqpo_lcbl 
--

CREATE TABLE rcb_eqpo_lcbl(
    lcbl_id           numeric(18, 0)    NOT NULL,
    eqpo_id           numeric(14, 0)    NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    CONSTRAINT eqlc_pk PRIMARY KEY (lcbl_id, eqpo_id) USING INDEX TABLESPACE recob_ind 
)
;



-- 
-- TABLE: rcb_estc 
--

CREATE TABLE rcb_estc(
    estc_id           numeric(14, 0)    NOT NULL,
    estc_nomb         varchar(60)       NOT NULL,
    estc_vige         numeric(1, 0)     NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp,
    CONSTRAINT estc_pk PRIMARY KEY (estc_id) USING INDEX TABLESPACE recob_ind 
)
;



-- 
-- TABLE: rcb_libr_ctrl_bloq 
--

CREATE TABLE rcb_libr_ctrl_bloq(
    lcbl_id           numeric(18, 0)    NOT NULL,
    lcbl_fech         timestamp         NOT NULL,
    lcbl_cerr         numeric(1, 0)     NOT NULL,
    lcbl_fech_cerr    timestamp,
    cabl_id           numeric(14, 0),
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp,
    CONSTRAINT lcbl_pk PRIMARY KEY (lcbl_id) USING INDEX TABLESPACE recob_ind 
)
;



-- 
-- TABLE: rcb_pers 
--

CREATE TABLE rcb_pers(
    pers_id           numeric(14, 0)    NOT NULL,
    pers_rut          varchar(11)       NOT NULL,
    pers_nomb         varchar(60)       NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp,
    CONSTRAINT pers_pk PRIMARY KEY (pers_id) USING INDEX TABLESPACE recob_ind 
)
;



-- 
-- TABLE: rcb_resp_lcbl 
--

CREATE TABLE rcb_resp_lcbl(
    lcbl_id           numeric(18, 0)    NOT NULL,
    pers_id           numeric(14, 0)    NOT NULL,
    empr_id           numeric(14, 0)    NOT NULL,
    relc_fech_ingr    timestamp         NOT NULL,
    relc_fech_sali    timestamp,
    CONSTRAINT relc_pk PRIMARY KEY (lcbl_id, pers_id, empr_id, relc_fech_ingr) USING INDEX TABLESPACE recob_ind 
)
;



-- 
-- TABLE: rcb_tags_eqpo 
--

CREATE TABLE rcb_tags_eqpo(
    taeq_id           numeric(14, 0)    NOT NULL,
    taeq_nume         numeric(4, 0)     NOT NULL,
    taeq_nomb         varchar(40)       NOT NULL,
    taeq_desc         varchar(150),
    taeq_enrg_cero    numeric(1, 0)     NOT NULL,
    taeq_vige         numeric(1, 0)     NOT NULL,
    eqpo_id           numeric(14, 0)    NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp,
    CONSTRAINT taeq_pk PRIMARY KEY (taeq_id) USING INDEX TABLESPACE recob_ind 
)
;



-- 
-- TABLE: rcb_trab 
--

CREATE TABLE rcb_trab(
    pers_id           numeric(14, 0)    NOT NULL,
    empr_id           numeric(14, 0)    NOT NULL,
    trab_cargo        varchar(60)       NOT NULL,
    trab_curs_bloq    numeric(1, 0)     NOT NULL,
    trab_vige         numeric(1, 0)     NOT NULL,
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp,
    CONSTRAINT trab_pk PRIMARY KEY (pers_id, empr_id) USING INDEX TABLESPACE recob_ind 
)
;



-- 
-- TABLE: rcb_ubic 
--

CREATE TABLE rcb_ubic(
    ubic_id           numeric(14, 0)    NOT NULL,
    ubic_nomb         varchar(40)       NOT NULL,
    ubic_desc         varchar(150),
    ubic_vige         numeric(1, 0)     NOT NULL,
    estc_id           numeric(14, 0),
    audi_fech_crea    timestamp         NOT NULL,
    audi_fech_modi    timestamp,
    CONSTRAINT ubic_pk PRIMARY KEY (ubic_id) USING INDEX TABLESPACE recob_ind 
)
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

ALTER TABLE rcb_cand ADD CONSTRAINT cand_cabl_fk 
    FOREIGN KEY (cabl_id)
    REFERENCES rcb_caja_bloq(cabl_id)
;


-- 
-- TABLE: rcb_dota_lcbl 
--

ALTER TABLE rcb_dota_lcbl ADD CONSTRAINT dolc_blde_fk 
    FOREIGN KEY (blde_id)
    REFERENCES rcb_bloq_depa(blde_id)
;

ALTER TABLE rcb_dota_lcbl ADD CONSTRAINT dolc_cand_fk 
    FOREIGN KEY (cand_id)
    REFERENCES rcb_cand(cand_id)
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
-- TABLE: rcb_eqpo_lcbl 
--

ALTER TABLE rcb_eqpo_lcbl ADD CONSTRAINT eqlc_eqpo_fk 
    FOREIGN KEY (eqpo_id)
    REFERENCES rcb_eqpo(eqpo_id)
;

ALTER TABLE rcb_eqpo_lcbl ADD CONSTRAINT eqlc_lcbl_fk 
    FOREIGN KEY (lcbl_id)
    REFERENCES rcb_libr_ctrl_bloq(lcbl_id)
;


-- 
-- TABLE: rcb_libr_ctrl_bloq 
--

ALTER TABLE rcb_libr_ctrl_bloq ADD CONSTRAINT lcbl_cabl_fk 
    FOREIGN KEY (cabl_id)
    REFERENCES rcb_caja_bloq(cabl_id)
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
-- TABLE: rcb_tags_eqpo 
--

ALTER TABLE rcb_tags_eqpo ADD CONSTRAINT taeq_eqpo_fk 
    FOREIGN KEY (eqpo_id)
    REFERENCES rcb_eqpo(eqpo_id)
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


