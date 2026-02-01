



show databases;ALTER 

use employee_db;

show tables;

SELECT COALESCE(SUM(amount), 0) AS balance
FROM account_ledger
WHERE account_no = '9790274';

Select * from user_tbl;

truncate payment_tbl;
truncate audit_tbl;

select * from payment_tbl where id=1082;
select * from payment_tbl order by id desc;

select * from account_ledger;
select * from account_tbl order by id desc;
ALTER TABLE audit_tbl DROP CHECK audit_tbl_chk_1;
select * from audit_tbl order by id desc;
alter table audit_tbl 
drop column response_json;