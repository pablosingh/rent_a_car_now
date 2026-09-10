BEGIN;

-- 1) Asegurar que la tabla category exista y tenga las 9 categorías base
CREATE TABLE IF NOT EXISTS category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description VARCHAR(255)
);

INSERT INTO category (name) VALUES
    ('Económico'), ('Compacto'), ('Mediano'), ('SUV'), ('Pickup'),
    ('Familiar'), ('Premium'), ('Utilitario'), ('Eléctrico')
ON CONFLICT (name) DO NOTHING;

-- 2) Agregar columna FK si no existe
ALTER TABLE car ADD COLUMN IF NOT EXISTS category_id bigint;

-- 3) Backfill: mapear el viejo varchar car.category -> category.id
-- 3a) Si aún existe la columna category (varchar), usarla para el backfill
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name='car' AND column_name='category'
    ) THEN
        UPDATE car SET category_id = category.id
        FROM category
        WHERE car.category = category.name
          AND car.category_id IS NULL;
    END IF;
END $$;

-- 3b) Fallback para filas que quedaron sin match (ej. categoría con typo) -> asignar 'Mediano'
UPDATE car SET category_id = (SELECT id FROM category WHERE name='Mediano' LIMIT 1)
WHERE category_id IS NULL;

-- 4) NOT NULL
ALTER TABLE car ALTER COLUMN category_id SET NOT NULL;

-- 5) FK constraint
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'fk_car_category') THEN
        ALTER TABLE car ADD CONSTRAINT fk_car_category FOREIGN KEY (category_id) REFERENCES category(id);
    END IF;
END $$;

-- 6) Eliminar columna vieja category (varchar) si existe
DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name='car' AND column_name='category'
    ) THEN
        ALTER TABLE car DROP COLUMN category;
    END IF;
END $$;

COMMIT;
