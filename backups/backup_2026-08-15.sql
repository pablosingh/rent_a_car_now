--
-- PostgreSQL database dump
--

\restrict rLARXfcZ1d6Zy2QdfOvUbL4WaF43hbqRgZVKJQ2YY9Pla49WLEkP3kea1wcZeUE

-- Dumped from database version 16.14 (Debian 16.14-1.pgdg13+1)
-- Dumped by pg_dump version 16.14 (Debian 16.14-1.pgdg13+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Data for Name: car; Type: TABLE DATA; Schema: public; Owner: rentacarnow
--

COPY public.car (id, available, brand, model, plate, price_per_day, price_per_hour, year, category) FROM stdin;
1	t	Toyota	Corolla	ABC123	120	15	2023	\N
2	t	Honda	Civic	ABC124	90	12	2025	\N
3	t	Toyota	Etios	XYZ123	80	10	2024	\N
4	t	Chevrolet	Cruze	XYZ124	95	20	2025	\N
5	t	Ford	Fiesta	XXY123	85	10	2024	\N
6	t	Wolkswagen	Polo	YYZ	79	11	2024	\N
7	t	Fiat	Cronos	CCC123	79	10	2025	\N
8	t	Ford	Focus	FFF123	99	18	2025	\N
9	t	Fiat	Cronos	AB218CD	42	5.25	2023	\N
10	t	Fiat	Argo	AC301BD	38	4.75	2022	\N
11	t	Fiat	Toro	AD412CE	85	10.63	2023	\N
12	t	Fiat	Mobi	AE523DF	30	3.75	2024	\N
13	t	Fiat	Pulse	AF634EG	55	6.88	2024	\N
14	t	VW	Gol Trend	AG745FH	35	4.38	2021	\N
15	t	VW	Polo	AH856GI	45	5.63	2023	\N
16	f	VW	Amarok	AJ967HJ	130	16.25	2024	\N
17	t	VW	Taos	AK078IK	70	8.75	2023	\N
18	t	VW	Nivus	AL189JL	60	7.5	2023	\N
19	t	VW	Virtus	AM290KM	48	6	2022	\N
20	t	Toyota	Corolla	AN301LN	65	8.13	2024	\N
21	t	Toyota	Etios	AP412MP	40	5	2022	\N
22	f	Toyota	Hilux	AQ523NQ	120	15	2024	\N
23	t	Toyota	Yaris	AR634OR	45	5.63	2023	\N
24	t	Toyota	SW4	AS745PS	140	17.5	2023	\N
25	t	Chevrolet	Onix	AT856QT	42	5.25	2023	\N
26	t	Chevrolet	Cruze	AU967RU	50	6.25	2022	\N
27	t	Chevrolet	Tracker	AV078SV	62	7.75	2024	\N
28	f	Chevrolet	S10	AW189TW	110	13.75	2023	\N
29	t	Ford	Focus	AX290UX	46	5.75	2021	\N
30	t	Ford	Ranger	AY301VY	115	14.38	2024	\N
31	t	Ford	Fiesta	AZ412WZ	32	4	2020	\N
32	t	Ford	Territory	BA523XA	75	9.38	2023	\N
33	t	Renault	Clio	BB634YB	30	3.75	2021	\N
34	t	Renault	Sandero	BC745ZC	34	4.25	2022	\N
35	t	Renault	Logan	BD856AD	33	4.13	2022	\N
36	t	Renault	Duster	BE967BE	65	8.13	2023	\N
37	t	Renault	Kwid	BF078CF	28	3.5	2024	\N
38	f	Renault	Alaskan	BG189DG	105	13.13	2022	\N
39	t	Peugeot	208	BH290EH	43	5.38	2023	\N
40	t	Peugeot	2008	BJ401FJ	58	7.25	2024	\N
41	t	Peugeot	408	BK512GK	80	10	2023	\N
42	t	Citro?n	C3	BL623HL	36	4.5	2023	\N
43	t	Citro?n	C4 Cactus	BM734IM	44	5.5	2021	\N
44	t	Nissan	Versa	BN845JN	41	5.13	2023	\N
45	t	Nissan	Kicks	BP956KP	55	6.88	2023	\N
46	f	Nissan	Frontier	BQ067LQ	118	14.75	2024	\N
47	t	Honda	Civic	BR178MR	68	8.5	2023	\N
48	t	Honda	HR-V	BS289NS	62	7.75	2022	\N
49	t	Honda	CR-V	BT390OT	90	11.25	2023	\N
50	t	Hyundai	Creta	BU401PU	66	8.25	2024	\N
51	t	Kia	Sportage	BV512QV	72	9	2023	\N
52	t	Jeep	Renegade	BW623RW	64	8	2023	\N
53	t	Jeep	Compass	BX734SX	88	11	2024	\N
54	t	Mercedes-Benz	Clase A	BY845TY	95	11.88	2023	\N
55	t	Mercedes-Benz	Clase C	BZ956UZ	130	16.25	2022	\N
56	t	BMW	Serie 1	CA067VA	105	13.13	2023	\N
57	t	Audi	A3	CB178WB	100	12.5	2023	\N
58	f	Audi	Q3	CC289XC	110	13.75	2022	\N
\.


--
-- Data for Name: car_images; Type: TABLE DATA; Schema: public; Owner: rentacarnow
--

COPY public.car_images (car_id, image_path) FROM stdin;
2	/uploads/2b9d54af-9727-4721-b44b-493c54d6c98d.jpg
1	/uploads/d2f3eee0-a1bd-403d-8b31-5210d1afe03d.jpg
3	/uploads/35a27994-f959-4412-89d7-e1e7472fc6cd.jpg
4	/uploads/99090da3-b3ce-459d-9ad6-269165e2cad1.jpg
5	/uploads/996fd76e-02b2-49d0-9324-8f4247ca222c.jpg
6	/uploads/d1ccb46d-f1cd-4cc4-841a-09ecc3782679.jpg
7	/uploads/e1b82a87-fb81-4f7c-bdf8-fcf7c5be0676.jpg
7	/uploads/1fd88571-73ac-453d-bda7-0bcf0c1746d8.jpg
8	/uploads/1e8d564f-3dfd-413a-935d-464d5bef39b2.jpg
8	/uploads/b3a684f6-1967-4b81-90e0-a3e7d9757b4a.jpg
9	https://images.unsplash.com/photo-1623869675781-80aa31012a5a?w=800&h=600&fit=crop&q=70
10	https://images.unsplash.com/photo-1590362891991-f776e747a588?w=800&h=600&fit=crop&q=70
11	https://images.unsplash.com/photo-1584345604476-8ec5e12e42dd?w=800&h=600&fit=crop&q=70
12	https://images.unsplash.com/photo-1555215695-3004980ad54e?w=800&h=600&fit=crop&q=70
13	https://images.unsplash.com/photo-1560958089-b8a1929cea89?w=800&h=600&fit=crop&q=70
14	https://images.unsplash.com/photo-1546614042-7df3c24c9e5d?w=800&h=600&fit=crop&q=70
15	https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?w=800&h=600&fit=crop&q=70
16	https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=800&h=600&fit=crop&q=70
17	https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=800&h=600&fit=crop&q=70
18	https://images.unsplash.com/photo-1494976388531-d1058494cdd8?w=800&h=600&fit=crop&q=70
19	https://images.unsplash.com/photo-1511919884226-fd3cad34687c?w=800&h=600&fit=crop&q=70
20	https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?w=800&h=600&fit=crop&q=70
21	https://images.unsplash.com/photo-1553440569-bcc63803a83d?w=800&h=600&fit=crop&q=70
22	https://images.unsplash.com/photo-1605559424843-9e4c228bf1c2?w=800&h=600&fit=crop&q=70
23	https://images.unsplash.com/photo-1551830820-330a71b99659?w=800&h=600&fit=crop&q=70
24	https://images.unsplash.com/photo-1502877338535-766e1452684a?w=800&h=600&fit=crop&q=70
25	https://images.unsplash.com/photo-1493238792000-8113da705763?w=800&h=600&fit=crop&q=70
26	https://images.unsplash.com/photo-1617469767053-d3b523a0b982?w=800&h=600&fit=crop&q=70
27	https://images.unsplash.com/photo-1571607388263-1044f9ea01dd?w=800&h=600&fit=crop&q=70
28	https://images.unsplash.com/photo-1580273916550-e323be2ae537?w=800&h=600&fit=crop&q=70
29	https://images.unsplash.com/photo-1544636331-e26879cd4d9b?w=800&h=600&fit=crop&q=70
30	https://images.unsplash.com/photo-1541899481282-d53bffe3c35d?w=800&h=600&fit=crop&q=70
31	https://images.unsplash.com/photo-1568605117036-5fe5e7bab0b7?w=800&h=600&fit=crop&q=70
32	https://images.unsplash.com/photo-1567521464027-f127ff144326?w=800&h=600&fit=crop&q=70
33	https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=800&h=600&fit=crop&q=70
34	https://images.unsplash.com/photo-1544829099-b9a0c07fad1a?w=800&h=600&fit=crop&q=70
35	https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=800&h=600&fit=crop&q=70
36	https://images.unsplash.com/photo-1517245386807-bb43f82c33c4?w=800&h=600&fit=crop&q=70
37	https://images.unsplash.com/photo-1525609004556-c46c7d6cf023?w=800&h=600&fit=crop&q=70
38	https://images.unsplash.com/photo-1600566753190-17f0baa2a6c3?w=800&h=600&fit=crop&q=70
39	https://images.unsplash.com/photo-1543269664-56d93c1b41a6?w=800&h=600&fit=crop&q=70
40	https://images.unsplash.com/photo-1623869675781-80aa31012a5a?w=800&h=600&fit=crop&q=70
41	https://images.unsplash.com/photo-1590362891991-f776e747a588?w=800&h=600&fit=crop&q=70
42	https://images.unsplash.com/photo-1584345604476-8ec5e12e42dd?w=800&h=600&fit=crop&q=70
43	https://images.unsplash.com/photo-1555215695-3004980ad54e?w=800&h=600&fit=crop&q=70
44	https://images.unsplash.com/photo-1560958089-b8a1929cea89?w=800&h=600&fit=crop&q=70
45	https://images.unsplash.com/photo-1546614042-7df3c24c9e5d?w=800&h=600&fit=crop&q=70
46	https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?w=800&h=600&fit=crop&q=70
47	https://images.unsplash.com/photo-1552519507-da3b142c6e3d?w=800&h=600&fit=crop&q=70
48	https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=800&h=600&fit=crop&q=70
49	https://images.unsplash.com/photo-1494976388531-d1058494cdd8?w=800&h=600&fit=crop&q=70
50	https://images.unsplash.com/photo-1511919884226-fd3cad34687c?w=800&h=600&fit=crop&q=70
51	https://images.unsplash.com/photo-1549317661-bd32c8ce0db2?w=800&h=600&fit=crop&q=70
52	https://images.unsplash.com/photo-1553440569-bcc63803a83d?w=800&h=600&fit=crop&q=70
53	https://images.unsplash.com/photo-1605559424843-9e4c228bf1c2?w=800&h=600&fit=crop&q=70
54	https://images.unsplash.com/photo-1551830820-330a71b99659?w=800&h=600&fit=crop&q=70
55	https://images.unsplash.com/photo-1502877338535-766e1452684a?w=800&h=600&fit=crop&q=70
56	https://images.unsplash.com/photo-1493238792000-8113da705763?w=800&h=600&fit=crop&q=70
57	https://images.unsplash.com/photo-1617469767053-d3b523a0b982?w=800&h=600&fit=crop&q=70
58	https://images.unsplash.com/photo-1571607388263-1044f9ea01dd?w=800&h=600&fit=crop&q=70
\.


--
-- Name: car_id_seq; Type: SEQUENCE SET; Schema: public; Owner: rentacarnow
--

SELECT pg_catalog.setval('public.car_id_seq', 58, true);


--
-- PostgreSQL database dump complete
--

\unrestrict rLARXfcZ1d6Zy2QdfOvUbL4WaF43hbqRgZVKJQ2YY9Pla49WLEkP3kea1wcZeUE

