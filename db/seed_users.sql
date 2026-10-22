-- RentACarNow - usuarios demo (SOLO DESARROLLO)
-- Requiere el schema creado (ddl-auto=update o db/seed_full.sql) y los roles seedeados (RbacSeeder).
-- Idempotente: se puede ejecutar varias veces sin duplicar usuarios.
--
-- Uso:
--   docker cp db/seed_users.sql postgres-rentacar:/tmp/seed_users.sql
--   docker exec postgres-rentacar psql -U rentacarnow -d rentacarnow -v ON_ERROR_STOP=1 -f /tmp/seed_users.sql
--
-- Credenciales:
--   admin@rentacarnow.com     / Admin123!
--   owner@rentacarnow.com     / Owner123!
--   employee@rentacarnow.com  / Employee123!
--   user@rentacarnow.com      / User123!
--   flota@rentacarnow.com     / Flota123!

BEGIN;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ADMIN') THEN
        RAISE EXCEPTION 'Faltan los roles. Arrancá la app una vez o restaurá db/seed_full.sql antes de correr este seed.';
    END IF;
END $$;

INSERT INTO users (name, last_name, email, password, role_id, verified)
SELECT 'Admin', 'Prueba', 'admin@rentacarnow.com',
       '$2a$10$I2dnO9ZLq.z91TxDViHYOOFuKYibLl8KJ.T/4ROZlmMEbhoihpIDq',
       r.id, true
FROM roles r
WHERE r.name = 'ADMIN'
  AND NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@rentacarnow.com');

INSERT INTO users (name, last_name, email, password, role_id, verified)
SELECT 'Owner', 'Prueba', 'owner@rentacarnow.com',
       '$2a$10$Ig4Zxvk1SIe2QuLGgh7tjuiGhFKk3c/WJSxxoR0dv7Mw/XS2QJzL.',
       r.id, true
FROM roles r
WHERE r.name = 'OWNER'
  AND NOT EXISTS (SELECT 1 FROM users WHERE email = 'owner@rentacarnow.com');

INSERT INTO users (name, last_name, email, password, role_id, verified)
SELECT 'Flota', 'RentACarNow', 'flota@rentacarnow.com',
       '$2a$10$RkW1CaQpHHa7rOA4s0rsbuYQKJOL0kmFoNUHflLwt2yYY1wkh47wy',
       r.id, true
FROM roles r
WHERE r.name = 'OWNER'
  AND NOT EXISTS (SELECT 1 FROM users WHERE email = 'flota@rentacarnow.com');

INSERT INTO users (name, last_name, email, password, role_id, verified)
SELECT 'User', 'Prueba', 'user@rentacarnow.com',
       '$2a$10$csu5BWd/sCseji8wHtVFduXUeDMg1Qswm15i3E0vO7kjMzMqAFbIS',
       r.id, true
FROM roles r
WHERE r.name = 'USER'
  AND NOT EXISTS (SELECT 1 FROM users WHERE email = 'user@rentacarnow.com');

INSERT INTO users (name, last_name, email, password, role_id, verified)
SELECT 'Employee', 'Prueba', 'employee@rentacarnow.com',
       '$2a$10$nu8XCNR35Yg0RpWNL.BxJ.JkUtFoy6sk6YK/mqJh6AV.xc58WqqRK',
       r.id, true
FROM roles r
WHERE r.name = 'EMPLOYEE'
  AND NOT EXISTS (SELECT 1 FROM users WHERE email = 'employee@rentacarnow.com');

UPDATE users
SET owner_id = (SELECT id FROM users WHERE email = 'owner@rentacarnow.com')
WHERE email = 'employee@rentacarnow.com'
  AND owner_id IS NULL;

COMMIT;
