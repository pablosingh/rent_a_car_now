CREATE TABLE IF NOT EXISTS rating (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    car_id BIGINT NOT NULL REFERENCES car(id) ON DELETE CASCADE,
    reservation_id BIGINT NOT NULL UNIQUE REFERENCES reservation(id) ON DELETE CASCADE,
    score INT NOT NULL CHECK (score BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX IF NOT EXISTS idx_rating_car ON rating(car_id);
CREATE INDEX IF NOT EXISTS idx_rating_user ON rating(user_id);
CREATE INDEX IF NOT EXISTS idx_rating_reservation ON rating(reservation_id);
