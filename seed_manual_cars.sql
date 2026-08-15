BEGIN;

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('ABC123', 'Toyota', 'Corolla', 2023, 120, 15, true);
INSERT INTO car_images (car_id, image_path) SELECT id, '/uploads/d2f3eee0-a1bd-403d-8b31-5210d1afe03d.jpg' FROM car WHERE plate = 'ABC123';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('ABC124', 'Honda', 'Civic', 2025, 90, 12, true);
INSERT INTO car_images (car_id, image_path) SELECT id, '/uploads/2b9d54af-9727-4721-b44b-493c54d6c98d.jpg' FROM car WHERE plate = 'ABC124';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('XYZ123', 'Toyota', 'Etios', 2024, 80, 10, true);
INSERT INTO car_images (car_id, image_path) SELECT id, '/uploads/35a27994-f959-4412-89d7-e1e7472fc6cd.jpg' FROM car WHERE plate = 'XYZ123';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('XYZ124', 'Chevrolet', 'Cruze', 2025, 95, 20, true);
INSERT INTO car_images (car_id, image_path) SELECT id, '/uploads/99090da3-b3ce-459d-9ad6-269165e2cad1.jpg' FROM car WHERE plate = 'XYZ124';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('XXY123', 'Ford', 'Fiesta', 2024, 85, 10, true);
INSERT INTO car_images (car_id, image_path) SELECT id, '/uploads/996fd76e-02b2-49d0-9324-8f4247ca222c.jpg' FROM car WHERE plate = 'XXY123';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('YYZ', 'Wolkswagen', 'Polo', 2024, 79, 11, true);
INSERT INTO car_images (car_id, image_path) SELECT id, '/uploads/d1ccb46d-f1cd-4cc4-841a-09ecc3782679.jpg' FROM car WHERE plate = 'YYZ';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('CCC123', 'Fiat', 'Cronos', 2025, 79, 10, true);
INSERT INTO car_images (car_id, image_path) SELECT id, '/uploads/e1b82a87-fb81-4f7c-bdf8-fcf7c5be0676.jpg' FROM car WHERE plate = 'CCC123';
INSERT INTO car_images (car_id, image_path) SELECT id, '/uploads/1fd88571-73ac-453d-bda7-0bcf0c1746d8.jpg' FROM car WHERE plate = 'CCC123';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('FFF123', 'Ford', 'Focus', 2025, 99, 18, true);
INSERT INTO car_images (car_id, image_path) SELECT id, '/uploads/1e8d564f-3dfd-413a-935d-464d5bef39b2.jpg' FROM car WHERE plate = 'FFF123';
INSERT INTO car_images (car_id, image_path) SELECT id, '/uploads/b3a684f6-1967-4b81-90e0-a3e7d9757b4a.jpg' FROM car WHERE plate = 'FFF123';

COMMIT;