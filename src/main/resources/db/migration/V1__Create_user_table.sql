CREATE TABLE USER
{
    ID int AUTO_INCREMENT PRIMARY KEY NOT NULL,
    ACCOUNT_ID VARCHAR(100),
    NAME varchar(50),
    TOKEN varchar(36),
    GMT_CREATE BIGINT,
    GMT_MODIFILED BIGINT
};