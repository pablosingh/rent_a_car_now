-- Migración reservas por horas: reemplaza durationInDays por startAt/endAt/totalPrice
-- Ejecutar ANTES de levantar con nuevo código si DB ya tiene datos
-- Uso: docker cp migrate_reservation_dates.sql postgres-rentacar:/tmp/... + psql -f /tmp/migrate_reservation_dates.sql

BEGIN;

ALTER TABLE reservation ADD COLUMN IF NOT EXISTS start_at TIMESTAMPTZ;
ALTER TABLE reservation ADD COLUMN IF NOT EXISTS end_at TIMESTAMPTZ;
ALTER TABLE reservation ADD COLUMN IF NOT EXISTS total_price NUMERIC(10,2);
ALTER TABLE reservation ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ DEFAULT now();

-- Backfill para filas viejas con duration_in_days (asume inicio now y fin now+duration)
UPDATE reservation
SET start_at = now(),
    end_at = now() + (duration_in_days || ' days')::interval,
    total_price = 0,
    created_at = COALESCE(created_at, now())
WHERE start_at IS NULL AND duration_in_days IS NOT NULL;

-- Para DB nueva sin datos, no hace nada; setear NOT NULL solo si hay datos migrados
-- Si hay filas aún con NULL, fallará y hay que revisar
ALTER TABLE reservation ALTER COLUMN start_at SET NOT NULL;
ALTER TABLE reservation ALTER COLUMN end_at SET NOT NULL;
ALTER TABLE reservation ALTER COLUMN total_price SET NOT NULL;
ALTER TABLE reservation ALTER COLUMN created_at SET NOT NULL;

-- Eliminar columna vieja
ALTER TABLE reservation DROP COLUMN IF EXISTS duration_in_days;

CREATE INDEX IF NOT EXISTS idx_reservation_car_dates ON reservation(car_id, start_at, end_at);
CREATE INDEX IF NOT EXISTS idx_reservation_user ON reservation(user_id);

COMMIT;
