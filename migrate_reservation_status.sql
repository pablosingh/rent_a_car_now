-- Migración estado de reservas (2026-09-21) — flujo PENDING -> DISPATCHED -> COMPLETED, CANCELLED, reversible
ALTER TABLE reservation ADD COLUMN IF NOT EXISTS status VARCHAR(20) NOT NULL DEFAULT 'PENDING';
ALTER TABLE reservation ADD COLUMN IF NOT EXISTS dispatched_at TIMESTAMPTZ;
ALTER TABLE reservation ADD COLUMN IF NOT EXISTS dispatched_by_id BIGINT REFERENCES users(id) ON DELETE SET NULL;
ALTER TABLE reservation ADD COLUMN IF NOT EXISTS completed_at TIMESTAMPTZ;
ALTER TABLE reservation ADD COLUMN IF NOT EXISTS completed_by_id BIGINT REFERENCES users(id) ON DELETE SET NULL;
ALTER TABLE reservation ADD COLUMN IF NOT EXISTS cancelled_at TIMESTAMPTZ;
-- backfill
UPDATE reservation SET status='PENDING' WHERE status IS NULL;
CREATE INDEX IF NOT EXISTS idx_reservation_status ON reservation(status);
