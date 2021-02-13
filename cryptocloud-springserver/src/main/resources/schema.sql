
DROP TABLE IF EXISTS USERFILE;  

CREATE TABLE USERFILE
( 
    id IDENTITY NOT NULL PRIMARY KEY ,  -- ⬅ `identity` = auto-incrementing long integer.
    username VARCHAR NOT NULL ,
    lastupdate DATE NOT NULL 
) 