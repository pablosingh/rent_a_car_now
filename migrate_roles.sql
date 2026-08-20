BEGIN;

ALTER TABLE users ADD COLUMN IF NOT EXISTS verified boolean NOT NULL DEFAULT false;
ALTER TABLE users ADD COLUMN IF NOT EXISTS owner_id bigint;
ALTER TABLE car ADD COLUMN IF NOT EXISTS owner_id bigint;

INSERT INTO users (name, last_name, email, password, role, verified)
SELECT 'Flota', 'RentACarNow', 'flota@rentacarnow.com', '$2a$10$RkW1CaQpHHa7rOA4s0rsbuYQKJOL0kmFoNUHflLwt2yYY1wkh47wy', 'OWNER', true
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'flota@rentacarnow.com');

UPDATE car SET owner_id = (SELECT id FROM users WHERE email = 'flota@rentacarnow.com') WHERE owner_id IS NULL;

ALTER TABLE car ALTER COLUMN owner_id SET NOT NULL;

DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk_users_owner') THEN
    ALTER TABLE users ADD CONSTRAINT fk_users_owner FOREIGN KEY (owner_id) REFERENCES users(id);
  END IF;
  IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk_car_owner') THEN
    ALTER TABLE car ADD CONSTRAINT fk_car_owner FOREIGN KEY (owner_id) REFERENCES users(id);
  END IF;
END $$;

COMMIT;