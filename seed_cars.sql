BEGIN;

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AB218CD', 'Fiat', 'Cronos', 2023, 42, 5.25, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1623869675781-80aa31012a5a?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AB218CD';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AC301BD', 'Fiat', 'Argo', 2022, 38, 4.75, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1590362891991-f776e747a588?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AC301BD';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AD412CE', 'Fiat', 'Toro', 2023, 85, 10.63, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1584345604476-8ec5e12e42dd?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AD412CE';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AE523DF', 'Fiat', 'Mobi', 2024, 30, 3.75, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1555215695-3004980ad54e?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AE523DF';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AF634EG', 'Fiat', 'Pulse', 2024, 55, 6.88, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1560958089-b8a1929cea89?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AF634EG';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AG745FH', 'VW', 'Gol Trend', 2021, 35, 4.38, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1546614042-7df3c24c9e5d?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AG745FH';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AH856GI', 'VW', 'Polo', 2023, 45, 5.63, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AH856GI';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AJ967HJ', 'VW', 'Amarok', 2024, 130, 16.25, false);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AJ967HJ';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AK078IK', 'VW', 'Taos', 2023, 70, 8.75, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AK078IK';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AL189JL', 'VW', 'Nivus', 2023, 60, 7.5, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1494976388531-d1058494cdd8?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AL189JL';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AM290KM', 'VW', 'Virtus', 2022, 48, 6, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1511919884226-fd3cad34687c?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AM290KM';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AN301LN', 'Toyota', 'Corolla', 2024, 65, 8.13, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AN301LN';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AP412MP', 'Toyota', 'Etios', 2022, 40, 5, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1553440569-bcc63803a83d?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AP412MP';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AQ523NQ', 'Toyota', 'Hilux', 2024, 120, 15, false);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1605559424843-9e4c228bf1c2?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AQ523NQ';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AR634OR', 'Toyota', 'Yaris', 2023, 45, 5.63, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1551830820-330a71b99659?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AR634OR';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AS745PS', 'Toyota', 'SW4', 2023, 140, 17.5, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1502877338535-766e1452684a?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AS745PS';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AT856QT', 'Chevrolet', 'Onix', 2023, 42, 5.25, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1493238792000-8113da705763?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AT856QT';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AU967RU', 'Chevrolet', 'Cruze', 2022, 50, 6.25, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1617469767053-d3b523a0b982?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AU967RU';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AV078SV', 'Chevrolet', 'Tracker', 2024, 62, 7.75, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1571607388263-1044f9ea01dd?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AV078SV';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AW189TW', 'Chevrolet', 'S10', 2023, 110, 13.75, false);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1580273916550-e323be2ae537?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AW189TW';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AX290UX', 'Ford', 'Focus', 2021, 46, 5.75, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1544636331-e26879cd4d9b?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AX290UX';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AY301VY', 'Ford', 'Ranger', 2024, 115, 14.38, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1541899481282-d53bffe3c35d?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AY301VY';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('AZ412WZ', 'Ford', 'Fiesta', 2020, 32, 4, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'AZ412WZ';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BA523XA', 'Ford', 'Territory', 2023, 75, 9.38, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1567521464027-f127ff144326?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BA523XA';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BB634YB', 'Renault', 'Clio', 2021, 30, 3.75, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BB634YB';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BC745ZC', 'Renault', 'Sandero', 2022, 34, 4.25, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1544829099-b9a0c07fad1a?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BC745ZC';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BD856AD', 'Renault', 'Logan', 2022, 33, 4.13, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BD856AD';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BE967BE', 'Renault', 'Duster', 2023, 65, 8.13, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1517245386807-bb43f82c33c4?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BE967BE';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BF078CF', 'Renault', 'Kwid', 2024, 28, 3.5, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1525609004556-c46c7d6cf023?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BF078CF';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BG189DG', 'Renault', 'Alaskan', 2022, 105, 13.13, false);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1600566753190-17f0baa2a6c3?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BG189DG';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BH290EH', 'Peugeot', '208', 2023, 43, 5.38, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1543269664-56d93c1b41a6?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BH290EH';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BJ401FJ', 'Peugeot', '2008', 2024, 58, 7.25, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1623869675781-80aa31012a5a?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BJ401FJ';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BK512GK', 'Peugeot', '408', 2023, 80, 10, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1590362891991-f776e747a588?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BK512GK';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BL623HL', 'Citroën', 'C3', 2023, 36, 4.5, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1584345604476-8ec5e12e42dd?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BL623HL';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BM734IM', 'Citroën', 'C4 Cactus', 2021, 44, 5.5, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1555215695-3004980ad54e?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BM734IM';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BN845JN', 'Nissan', 'Versa', 2023, 41, 5.13, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1560958089-b8a1929cea89?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BN845JN';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BP956KP', 'Nissan', 'Kicks', 2023, 55, 6.88, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1546614042-7df3c24c9e5d?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BP956KP';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BQ067LQ', 'Nissan', 'Frontier', 2024, 118, 14.75, false);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BQ067LQ';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BR178MR', 'Honda', 'Civic', 2023, 68, 8.5, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BR178MR';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BS289NS', 'Honda', 'HR-V', 2022, 62, 7.75, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BS289NS';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BT390OT', 'Honda', 'CR-V', 2023, 90, 11.25, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1494976388531-d1058494cdd8?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BT390OT';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BU401PU', 'Hyundai', 'Creta', 2024, 66, 8.25, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1511919884226-fd3cad34687c?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BU401PU';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BV512QV', 'Kia', 'Sportage', 2023, 72, 9, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BV512QV';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BW623RW', 'Jeep', 'Renegade', 2023, 64, 8, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1553440569-bcc63803a83d?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BW623RW';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BX734SX', 'Jeep', 'Compass', 2024, 88, 11, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1605559424843-9e4c228bf1c2?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BX734SX';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BY845TY', 'Mercedes-Benz', 'Clase A', 2023, 95, 11.88, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1551830820-330a71b99659?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BY845TY';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('BZ956UZ', 'Mercedes-Benz', 'Clase C', 2022, 130, 16.25, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1502877338535-766e1452684a?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'BZ956UZ';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('CA067VA', 'BMW', 'Serie 1', 2023, 105, 13.13, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1493238792000-8113da705763?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'CA067VA';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('CB178WB', 'Audi', 'A3', 2023, 100, 12.5, true);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1617469767053-d3b523a0b982?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'CB178WB';

INSERT INTO car (plate, brand, model, year, price_per_day, price_per_hour, available) VALUES ('CC289XC', 'Audi', 'Q3', 2022, 110, 13.75, false);
INSERT INTO car_images (car_id, image_path) SELECT id, 'https://images.unsplash.com/photo-1571607388263-1044f9ea01dd?w=800&h=600&fit=crop&q=70' FROM car WHERE plate = 'CC289XC';

COMMIT;
