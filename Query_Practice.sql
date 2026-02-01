create database userentity;


#This command list down every connected session which is assocaited with the database;
SELECT pid, usename, application_name, client_addr
FROM pg_stat_activity
WHERE datname = 'userentity';

# This command terminate every session which is associated with database;
SELECT pg_terminate_backend(pid)
FROM pg_stat_activity
WHERE datname = 'demo'
AND pid <> pg_backend_pid();

#to List tables;
SELECT tablename
FROM pg_tables
WHERE schemaname = 'public';


delete from user_tbl where id= 'b2eb237a-084b-4d87-8fe2-49395c8d2af6';

ad56dfe6-e34a-40f4-8616-0fb506b81014;
select * from user_tbl;
select * from user_roles;
select * from role_tbl;

select * from address_tbl t;

select u.id        AS user_id, u.username  AS username,  r.role_name AS role_name FROM user_tbl u
JOIN user_roles ur
    ON u.id = ur.user_id
JOIN role_tbl r
    ON r.id = ur.role_id
WHERE r.role_name = 'ADMIN';


SELECT CONCAT(ut.first_name, ' ', ut.last_name) AS full_name FROM user_tbl ut
inner JOIN user_roles ur
    ON ut.id = ur.user_id 
inner join role_tbl rt 
on rt.id= ur.role_id
where rt.role_name ='ADMIN';
;

select * from user_tbl;
select * from address_tbl t;

select * from user_tbl ut inner join user_roles ur on ut.id =  ur.user_id  inner join role_tbl rt on rt.id = ur.role_id;

select count(*) from user_tbl ut inner join user_roles ur on ut.id =  ur.user_id  inner join role_tbl rt on rt.id = ur.role_id;

select count(*),rt.role_name  from user_tbl ut inner join user_roles ur on ut.id =  ur.user_id  inner join role_tbl rt on rt.id = ur.role_id group by rt.role_name ;


SELECT username, COUNT(*) FROM user_tbl GROUP BY username ORDER BY username ASC;

select country, count(*) from user_tbl ut inner join address_tbl t on t.id=ut.address_id group by t.country ;

select * from address_tbl where country= 133;

select username from user_tbl ut where exists(select country from address_tbl where country= 133);
SELECT ut.username
FROM user_tbl ut
WHERE EXISTS (
    SELECT 1
    FROM address_tbl at
    WHERE at.id = ut.address_id
      AND at.country = 133
);


