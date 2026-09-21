-- Migración políticas de la empresa (2026-09-21)
-- ddl-auto=update crea la tabla automáticamente; este SQL es para DBs existentes si se quiere verificar/seed manual
CREATE TABLE IF NOT EXISTS policy (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL UNIQUE,
    slug VARCHAR(255) NOT NULL UNIQUE,
    content TEXT NOT NULL,
    display_order INT NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
);
CREATE INDEX IF NOT EXISTS idx_policy_slug ON policy(slug);
CREATE INDEX IF NOT EXISTS idx_policy_order ON policy(display_order);
