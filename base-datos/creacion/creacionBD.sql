CREATE USER recob
    WITH ENCRYPTED PASSWORD 'recob';

CREATE TABLESPACE recob_dat 
    OWNER recob 
    LOCATION '/data/pgdata/tbs/recob_dat'
;

CREATE TABLESPACE recob_ind
    OWNER recob 
    LOCATION '/data/pgdata/tbs/recob_ind'
;

CREATE DATABASE bd_recob WITH
    OWNER=recob 
    ENCODING='UTF8' 
    TABLESPACE=recob_dat
;

CREATE SCHEMA IF NOT EXISTS recob
    AUTHORIZATION recob
;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "adminpack";
