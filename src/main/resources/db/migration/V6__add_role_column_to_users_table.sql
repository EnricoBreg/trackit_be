alter table users
    add global_role varchar(255);

update users set global_role = 'ROLE_SUPER_ADMIN' where username = 'super';
update users set global_role = 'ROLE_USER' where username <> 'super';
