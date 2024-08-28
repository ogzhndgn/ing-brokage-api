INSERT INTO brokage_admins(name, surname, email, status, password, admin_token, created_date)
VALUES ('Oguzhan', 'Dogan', 'dogan_oguzhan@hotmail.com', 'ACTIVE',
        '$argon2id$v=19$m=16,t=2,p=1$3UM+cqGA3iwUf57LGW+U5g$wJeJ2szHRZt9flKMTq17JBW5d3rWAKb95cYbwUx9k6k', '530e9f5e-4e46-423d-aa19-217475b26fa4',
        now());

INSERT INTO brokage_admins(name, surname, email, status, password, admin_token, created_date)
VALUES ('John', 'Doe', 'john.doe@yopmail.com', 'ACTIVE',
        '$argon2id$v=19$m=16,t=2,p=1$3UM+cqGA3iwUf57LGW+U5g$wJeJ2szHRZt9flKMTq17JBW5d3rWAKb95cYbwUx9k6k', '530e9f5e-4e46-423d-aa19-217475b26fa4',
        now());


INSERT INTO customers(id, customer_id, name, email, created_date)
values (1, 'e2a81c0d-a1c2-420f-9d0d-e7352226a01e', 'Ali Veli', 'ali.veli@yopmail.com', now());

INSERT INTO customers(id, customer_id, name, email, created_date)
values (2, '929e1428-64bd-4fc3-b1bc-e441695e0977', 'John Doe', 'john.doe@yopmail.com', now());

INSERT INTO assets(id, name, size, usable_size, customer_id, created_date)
VALUES (1, 'Asset 1', 100, 100, 1, now());
INSERT INTO assets(id, name, size, usable_size, customer_id, created_date)
VALUES (2, 'Asset 2', 80, 80, 1, now());
INSERT INTO assets(id, name, size, usable_size, customer_id, created_date)
VALUES (3, 'Asset 3', 120, 120, 1, now());
INSERT INTO assets(id, name, size, usable_size, customer_id, created_date)
VALUES (4, 'Asset 44', 158, 158, 2, now());
INSERT INTO assets(id, name, size, usable_size, customer_id, created_date)
VALUES (5, 'Asset 55', 95, 95, 2, now());