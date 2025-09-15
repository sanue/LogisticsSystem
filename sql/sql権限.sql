-- 给logistics_app用户授予表空间权限
ALTER USER logistics_app QUOTA UNLIMITED ON USERS;

-- 确保用户有所有必要的权限
GRANT CREATE SESSION TO logistics_app;
GRANT CREATE TABLE TO logistics_app;
GRANT CREATE VIEW TO logistics_app;
GRANT CREATE PROCEDURE TO logistics_app;
GRANT CREATE SEQUENCE TO logistics_app;
GRANT CREATE TRIGGER TO logistics_app;
GRANT CREATE INDEX TO logistics_app;

-- 提交
COMMIT;