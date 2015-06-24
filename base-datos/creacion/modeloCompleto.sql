/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     21-06-2015 0:55:37                           */
/*==============================================================*/


drop table if exists RCB_BLOQ_DEPA;

drop table if exists RCB_CAJA_BLOQ;

drop table if exists RCB_CAND;

drop table if exists RCB_DOMI_VALO;

drop table if exists RCB_DOTA_LCBL;

drop table if exists RCB_EMPR;

drop table if exists RCB_ENRG_LCBL;

drop table if exists RCB_EQPO;

drop table if exists RCB_EQPO_LCBL;

drop table if exists RCB_ESTC;

drop table if exists RCB_LIBR_CTRL_BLOQ;

drop table if exists RCB_PERS;

drop table if exists RCB_RESP_LCBL;

drop table if exists RCB_TAGS_EQPO;

drop table if exists RCB_TRAB;

drop table if exists RCB_UBIC;

/*==============================================================*/
/* Table: RCB_BLOQ_DEPA                                         */
/*==============================================================*/
create table RCB_BLOQ_DEPA
(
   BLDE_ID              smallint not null,
   BLDE_NOMB            varchar(50) not null,
   BLDE_MAXI            numeric(2,0),
   BLDE_VIGE            smallint not null,
   AUDI_FECH_CREA       timestamp,
   AUDI_FECH_MODI       timestamp,
   primary key (BLDE_ID)
);

alter table RCB_BLOQ_DEPA comment 'Bloqueos Departamentales';

/*==============================================================*/
/* Table: RCB_CAJA_BLOQ                                         */
/*==============================================================*/
create table RCB_CAJA_BLOQ
(
   CAJA_ID              smallint not null,
   CAJA_NUME            numeric(3,0) not null,
   CAJA_NOMB            varchar(50) not null,
   CAJA_VIGE            smallint not null,
   ESTC_ID              smallint not null,
   AUDI_FECH_CREA       timestamp,
   AUDI_FECH_MODI       timestamp,
   primary key (CAJA_ID)
);

alter table RCB_CAJA_BLOQ comment 'Cajas de Bloqueo';

/*==============================================================*/
/* Table: RCB_CAND                                              */
/*==============================================================*/
create table RCB_CAND
(
   CAND_ID              smallint not null,
   CAND_NUME            numeric(3,0) not null,
   CAND_VIGE            smallint not null,
   CAJA_ID              smallint not null,
   AUDI_FECH_CREA       timestamp,
   AUDI_FECH_MODI       timestamp,
   primary key (CAND_ID)
);

alter table RCB_CAND comment 'Candados';

/*==============================================================*/
/* Table: RCB_DOMI_VALO                                         */
/*==============================================================*/
create table RCB_DOMI_VALO
(
   DVAL_ID              smallint not null,
   DVAL_CODI            varchar(40),
   DVAL_NOMB            varchar(60) not null,
   DVAL_VIGE            smallint not null,
   DVAL_DOMI            varchar(50) not null,
   primary key (DVAL_ID)
);

alter table RCB_DOMI_VALO comment 'Dominio de Valores';

/*==============================================================*/
/* Table: RCB_DOTA_LCBL                                         */
/*==============================================================*/
create table RCB_DOTA_LCBL
(
   LCBL_ID              numeric(18,0) not null,
   BLDE_ID              smallint not null,
   EMPR_ID              smallint not null,
   PERS_ID              bigint not null,
   CAND_ID              smallint,
   PERS_ID_BLOQ         bigint,
   PERS_ID_DESB         bigint,
   DOLC_FECH_BLOQ       timestamp,
   DOLC_FECH_DESB       timestamp,
   primary key (LCBL_ID, PERS_ID, EMPR_ID, BLDE_ID)
);

alter table RCB_DOTA_LCBL comment 'Dotación registrada en el Libro de Control de Bloqueos';

/*==============================================================*/
/* Table: RCB_EMPR                                              */
/*==============================================================*/
create table RCB_EMPR
(
   EMPR_ID              smallint not null,
   EMPR_RUT             varchar(11) not null,
   EMPR_NOMB            varchar(60) not null,
   EMPR_VIGE            smallint not null,
   AUDI_FECH_CREA       timestamp,
   AUDI_FECH_MODI       timestamp,
   primary key (EMPR_ID)
);

alter table RCB_EMPR comment 'Empresas';

/*==============================================================*/
/* Table: RCB_ENRG_LCBL                                         */
/*==============================================================*/
create table RCB_ENRG_LCBL
(
   LCBL_ID              numeric(18,0) not null,
   DVAL_ID_ITEM         smallint not null,
   primary key (LCBL_ID, DVAL_ID_ITEM)
);

/*==============================================================*/
/* Table: RCB_EQPO                                              */
/*==============================================================*/
create table RCB_EQPO
(
   EQPO_ID              smallint not null,
   ESTC_ID              smallint not null,
   EQPO_CODI            varchar(20) not null,
   EQPO_VIGE            smallint not null,
   AUDI_FECH_CREA       timestamp,
   AUDI_FECH_MODI       timestamp,
   primary key (EQPO_ID)
);

alter table RCB_EQPO comment 'Equipos';

/*==============================================================*/
/* Table: RCB_EQPO_LCBL                                         */
/*==============================================================*/
create table RCB_EQPO_LCBL
(
   LCBL_ID              numeric(18,0) not null,
   EQPO_ID              smallint not null,
   AUDI_FECH_CREA       timestamp not null,
   primary key (LCBL_ID, EQPO_ID)
);

alter table RCB_EQPO_LCBL comment 'Equipos registrados en el Libro de Control de Bloqueos';

/*==============================================================*/
/* Table: RCB_ESTC                                              */
/*==============================================================*/
create table RCB_ESTC
(
   ESTC_ID              smallint not null,
   ESTC_NOMB            varchar(50) not null,
   ESTC_DESC            varchar(150),
   ESTC_VIGE            smallint not null,
   UBIC_ID              smallint,
   AUDI_FECH_CREA       timestamp,
   AUDI_FECH_MODI       timestamp,
   primary key (ESTC_ID)
);

alter table RCB_ESTC comment 'Estación';

/*==============================================================*/
/* Table: RCB_LIBR_CTRL_BLOQ                                    */
/*==============================================================*/
create table RCB_LIBR_CTRL_BLOQ
(
   LCBL_ID              numeric(18,0) not null,
   LCBL_FECH            datetime not null,
   LCBL_CERR            smallint not null,
   LCBL_FECH_CERR       datetime,
   CAJA_ID              smallint,
   AUDI_FECH_CREA       timestamp,
   AUDI_FECH_MODI       timestamp,
   primary key (LCBL_ID)
);

alter table RCB_LIBR_CTRL_BLOQ comment 'Libros de Control de Bloqueos';

/*==============================================================*/
/* Table: RCB_PERS                                              */
/*==============================================================*/
create table RCB_PERS
(
   PERS_ID              bigint not null,
   PERS_RUT             varchar(11) not null,
   PERS_NOMB            varchar(60) not null,
   AUDI_FECH_CREA       timestamp not null,
   AUDI_FECH_MODI       timestamp,
   primary key (PERS_ID)
);

alter table RCB_PERS comment 'Personas';

/*==============================================================*/
/* Table: RCB_RESP_LCBL                                         */
/*==============================================================*/
create table RCB_RESP_LCBL
(
   LCBL_ID              numeric(18,0) not null,
   PERS_ID              bigint not null,
   EMPR_ID              smallint not null,
   RELC_FECH_INGR       datetime not null,
   RELC_FECH_SALI       datetime,
   primary key (LCBL_ID, PERS_ID, EMPR_ID, RELC_FECH_INGR)
);

alter table RCB_RESP_LCBL comment 'Responsables asociados al Libro de Control de Bloqueos';

/*==============================================================*/
/* Table: RCB_TAGS_EQPO                                         */
/*==============================================================*/
create table RCB_TAGS_EQPO
(
   TAEQ_ID              smallint not null,
   TAEQ_NUME            numeric(4,0) not null,
   TAEQ_NOMB            varchar(50) not null,
   TAEQ_DESC            varchar(150),
   TAEQ_ENRG_CERO       smallint not null,
   TAEQ_VIGE            smallint not null,
   EQPO_ID              smallint not null,
   AUDI_FECH_CREA       timestamp not null,
   AUDI_FECH_MODI       timestamp,
   primary key (TAEQ_ID)
);

alter table RCB_TAGS_EQPO comment 'TAGs de los Equipos';

/*==============================================================*/
/* Table: RCB_TRAB                                              */
/*==============================================================*/
create table RCB_TRAB
(
   PERS_ID              bigint not null,
   EMPR_ID              smallint not null,
   TRAB_CARGO           varchar(60) not null,
   TRAB_CURS_BLOQ       smallint not null,
   TRAB_VIGE            smallint not null,
   AUDI_FECH_CREA       timestamp not null,
   AUDI_FECH_MODI       timestamp,
   primary key (PERS_ID, EMPR_ID)
);

alter table RCB_TRAB comment 'Trabajadores';

/*==============================================================*/
/* Table: RCB_UBIC                                              */
/*==============================================================*/
create table RCB_UBIC
(
   UBIC_ID              smallint not null,
   UBIC_NOMB            varchar(60) not null,
   UBIC_VIGE            smallint not null,
   AUDI_FECH_CREA       timestamp,
   AUDI_FECH_MODI       timestamp,
   primary key (UBIC_ID)
);

alter table RCB_UBIC comment 'Ubicaciones';

alter table RCB_CAJA_BLOQ add constraint FK_CABL_ESTA_FK foreign key (ESTC_ID)
      references RCB_ESTC (ESTC_ID) on delete restrict on update restrict;

alter table RCB_CAND add constraint FK_CAND_CABL_FK foreign key (CAJA_ID)
      references RCB_CAJA_BLOQ (CAJA_ID) on delete restrict on update restrict;

alter table RCB_DOTA_LCBL add constraint FK_DBLQ_BLDE_FK foreign key (BLDE_ID)
      references RCB_BLOQ_DEPA (BLDE_ID) on delete restrict on update restrict;

alter table RCB_DOTA_LCBL add constraint FK_DBLQ_CAND_FK foreign key (CAND_ID)
      references RCB_CAND (CAND_ID) on delete restrict on update restrict;

alter table RCB_DOTA_LCBL add constraint FK_DBLQ_LBLQ_FK foreign key (LCBL_ID)
      references RCB_LIBR_CTRL_BLOQ (LCBL_ID) on delete restrict on update restrict;

alter table RCB_DOTA_LCBL add constraint FK_DBLQ_PERB_FK foreign key (PERS_ID_BLOQ)
      references RCB_PERS (PERS_ID) on delete restrict on update restrict;

alter table RCB_DOTA_LCBL add constraint FK_DBLQ_PERD_FK foreign key (PERS_ID_DESB)
      references RCB_PERS (PERS_ID) on delete restrict on update restrict;

alter table RCB_DOTA_LCBL add constraint FK_DBLQ_TRAB_FK foreign key (PERS_ID, EMPR_ID)
      references RCB_TRAB (PERS_ID, EMPR_ID) on delete restrict on update restrict;

alter table RCB_ENRG_LCBL add constraint FK_EBLQ_ITEM_FK foreign key (DVAL_ID_ITEM)
      references RCB_DOMI_VALO (DVAL_ID) on delete restrict on update restrict;

alter table RCB_ENRG_LCBL add constraint FK_ENLC_LBLQ_FK foreign key (LCBL_ID)
      references RCB_LIBR_CTRL_BLOQ (LCBL_ID) on delete restrict on update restrict;

alter table RCB_EQPO add constraint FK_EQPO_ESTA_FK foreign key (ESTC_ID)
      references RCB_ESTC (ESTC_ID) on delete restrict on update restrict;

alter table RCB_EQPO_LCBL add constraint FK_EQLC_EQPO_FK foreign key (EQPO_ID)
      references RCB_EQPO (EQPO_ID) on delete restrict on update restrict;

alter table RCB_EQPO_LCBL add constraint FK_EQLC_LCBL_FK foreign key (LCBL_ID)
      references RCB_LIBR_CTRL_BLOQ (LCBL_ID) on delete restrict on update restrict;

alter table RCB_ESTC add constraint FK_ESTA_UBIC_FK foreign key (UBIC_ID)
      references RCB_UBIC (UBIC_ID) on delete restrict on update restrict;

alter table RCB_LIBR_CTRL_BLOQ add constraint FK_LCBL_CAJA_FK foreign key (CAJA_ID)
      references RCB_CAJA_BLOQ (CAJA_ID) on delete restrict on update restrict;

alter table RCB_RESP_LCBL add constraint FK_RELC_LBLQ_FK foreign key (LCBL_ID)
      references RCB_LIBR_CTRL_BLOQ (LCBL_ID) on delete restrict on update restrict;

alter table RCB_RESP_LCBL add constraint FK_RELC_TRAB_FK foreign key (PERS_ID, EMPR_ID)
      references RCB_TRAB (PERS_ID, EMPR_ID) on delete restrict on update restrict;

alter table RCB_TAGS_EQPO add constraint FK_TAEQ_EQPO_FK foreign key (EQPO_ID)
      references RCB_EQPO (EQPO_ID) on delete restrict on update restrict;

alter table RCB_TRAB add constraint FK_TRAB_EMPR_FK foreign key (EMPR_ID)
      references RCB_EMPR (EMPR_ID) on delete restrict on update restrict;

alter table RCB_TRAB add constraint FK_TRAB_PERS_FK foreign key (PERS_ID)
      references RCB_PERS (PERS_ID) on delete restrict on update restrict;

