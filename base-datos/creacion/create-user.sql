create user recob 
    with password 'recob' encrypted createdb;

create tablespace recob_dat 
    owner recob 
    location '/data/pgdata/tbs/recob_dat'
;

create database recob with 
    owner=recob 
    encoding='UTF8' 
    tablespace=recob_dat
;
    
