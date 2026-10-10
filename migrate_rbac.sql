BEGIN;

CREATE TABLE IF NOT EXISTS roles (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(50) UNIQUE NOT NULL,
  description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS permissions (
  id BIGSERIAL PRIMARY KEY,
  code VARCHAR(100) UNIQUE NOT NULL,
  description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS role_permission (
  role_id BIGINT NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
  permission_id BIGINT NOT NULL REFERENCES permissions(id) ON DELETE CASCADE,
  PRIMARY KEY (role_id, permission_id)
);

INSERT INTO roles (name, description) VALUES
  ('USER', 'Reserva autos'),
  ('EMPLOYEE', 'Gestiona autos y reservas de su OWNER'),
  ('OWNER', 'Gestiona sus autos, reservas y empleados'),
  ('ADMIN', 'Control total y asignación de roles')
ON CONFLICT (name) DO NOTHING;

INSERT INTO permissions (code, description) VALUES
  ('RESERVATION_CREATE', 'Crear reservas propias'),
  ('RESERVATION_VIEW_SCOPE', 'Ver reservas según alcance del rol'),
  ('CAR_CREATE', 'Crear autos'),
  ('CAR_EDIT', 'Editar autos'),
  ('CAR_DELETE', 'Borrar autos'),
  ('DISPATCH_RESERVATION', 'Despachar reservas'),
  ('COMPLETE_RESERVATION', 'Completar reservas'),
  ('CANCEL_ANY', 'Cancelar cualquier reserva PENDING'),
  ('REVERT_RESERVATION', 'Revertir estados de reservas'),
  ('EMPLOYEE_MANAGE', 'Crear y gestionar empleados'),
  ('OWNER_VERIFY', 'Verificar cuentas OWNER'),
  ('USER_ROLE_ASSIGN', 'Asignar roles a usuarios'),
  ('CATALOG_MANAGE', 'Gestionar categorías y features'),
  ('POLICY_MANAGE', 'Gestionar políticas'),
  ('RATING_MODERATE', 'Moderar puntuaciones')
ON CONFLICT (code) DO NOTHING;

INSERT INTO role_permission (role_id, permission_id)
SELECT r.id, p.id FROM roles r, permissions p
WHERE (r.name = 'USER' AND p.code IN ('RESERVATION_CREATE', 'RESERVATION_VIEW_SCOPE'))
   OR (r.name = 'EMPLOYEE' AND p.code IN ('RESERVATION_CREATE', 'RESERVATION_VIEW_SCOPE',
       'CAR_CREATE', 'CAR_EDIT', 'CAR_DELETE',
       'DISPATCH_RESERVATION', 'COMPLETE_RESERVATION', 'CANCEL_ANY', 'REVERT_RESERVATION'))
   OR (r.name = 'OWNER' AND p.code IN ('RESERVATION_CREATE', 'RESERVATION_VIEW_SCOPE',
       'CAR_CREATE', 'CAR_EDIT', 'CAR_DELETE',
       'DISPATCH_RESERVATION', 'COMPLETE_RESERVATION', 'CANCEL_ANY', 'REVERT_RESERVATION',
       'EMPLOYEE_MANAGE'))
   OR (r.name = 'ADMIN' AND p.code IN ('RESERVATION_CREATE', 'RESERVATION_VIEW_SCOPE',
       'CAR_CREATE', 'CAR_EDIT', 'CAR_DELETE',
       'DISPATCH_RESERVATION', 'COMPLETE_RESERVATION', 'CANCEL_ANY', 'REVERT_RESERVATION',
       'EMPLOYEE_MANAGE', 'OWNER_VERIFY', 'USER_ROLE_ASSIGN',
       'CATALOG_MANAGE', 'POLICY_MANAGE', 'RATING_MODERATE'))
ON CONFLICT DO NOTHING;

ALTER TABLE users ADD COLUMN IF NOT EXISTS role_id BIGINT;

DO $$
BEGIN
  IF EXISTS (SELECT 1 FROM information_schema.columns
             WHERE table_name = 'users' AND column_name = 'role') THEN
    UPDATE users SET role_id = (SELECT id FROM roles WHERE roles.name = users.role)
    WHERE role_id IS NULL;

    UPDATE users SET owner_id = (SELECT id FROM users f WHERE f.email = 'flota@rentacarnow.com')
    WHERE role = 'EMPLOYEE' AND owner_id IS NULL;
  END IF;
END $$;

ALTER TABLE users ALTER COLUMN role_id SET NOT NULL;

DO $$
BEGIN
  IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk_users_role') THEN
    ALTER TABLE users ADD CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES roles(id);
  END IF;
END $$;

ALTER TABLE users DROP COLUMN IF EXISTS role;

COMMIT;
