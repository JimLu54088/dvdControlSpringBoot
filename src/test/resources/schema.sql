CREATE TABLE IF NOT EXISTS dvdsystem (
   dvdid INT,
    dvdname VARCHAR,
    borroweddate DATETIME,
    borrowedcount INT,
    status INT,
    ins_dt DATETIME,
    ins_by VARCHAR,
    upd_dt DATETIME,
    upd_by VARCHAR
);
DELETE FROM dvdsystem;

 INSERT INTO dvdsystem
 SELECT * FROM CSVREAD('classpath:csv/dvdSelect.csv',null,'charset=UTF-8');
 
--CREATE TABLE IF NOT EXISTS GOODWAY (
--   goodway_id INT,
--    goodway_name VARCHAR,
--    goodway_content VARCHAR,
--    goodway_create_datetime DATETIME,
--     goodway_update_datetime DATETIME,
--      goodway_creator VARCHAR,
--       goodway_updator VARCHAR,
--        create DATETIME,
--         create_by VARCHAR,
--          modified DATETIME,
--    modified_by VARCHAR
--);
--DELETE FROM GOODWAY;
--INSERT INTO GOODWAY
-- SELECT * FROM CSVREAD('classpath:csv/goodwaySelect.csv',null,'charset=UTF-8');