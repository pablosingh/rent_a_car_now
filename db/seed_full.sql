--
-- RentACarNow - seed completo (schema + datos)
-- Imagenes 100% web: no requiere la carpeta uploads.
-- Uso en una PC nueva:
--   docker compose up -d postgres
--   docker cp db/seed_full.sql postgres-rentacar:/tmp/seed_full.sql
--   docker exec postgres-rentacar psql -U rentacarnow -d rentacarnow -v ON_ERROR_STOP=1 -f /tmp/seed_full.sql
--   docker compose up -d app
--
--
-- PostgreSQL database dump
--


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

ALTER TABLE IF EXISTS ONLY public.role_permission DROP CONSTRAINT IF EXISTS role_permission_role_id_fkey;
ALTER TABLE IF EXISTS ONLY public.role_permission DROP CONSTRAINT IF EXISTS role_permission_permission_id_fkey;
ALTER TABLE IF EXISTS ONLY public.rating DROP CONSTRAINT IF EXISTS fktk5oym7po28cvokyv25jet7nx;
ALTER TABLE IF EXISTS ONLY public.reservation DROP CONSTRAINT IF EXISTS fkrea93581tgkq61mdl13hehami;
ALTER TABLE IF EXISTS ONLY public.users DROP CONSTRAINT IF EXISTS fkntyuh06i5y3y6ir598luxy3k9;
ALTER TABLE IF EXISTS ONLY public.car_images DROP CONSTRAINT IF EXISTS fkm398nw5sxpjn118loitbdbuw4;
ALTER TABLE IF EXISTS ONLY public.reservation DROP CONSTRAINT IF EXISTS fki9nlvyg39r571da6parogahe;
ALTER TABLE IF EXISTS ONLY public.car_features DROP CONSTRAINT IF EXISTS fkhtkgcnbe4l0pmtrhfcg1nlg8s;
ALTER TABLE IF EXISTS ONLY public.favorite DROP CONSTRAINT IF EXISTS fkgng78lym9ghokl8n4ge710mcu;
ALTER TABLE IF EXISTS ONLY public.reservation DROP CONSTRAINT IF EXISTS fkgkmbspv7rljixxoxo1af80lpp;
ALTER TABLE IF EXISTS ONLY public.rating DROP CONSTRAINT IF EXISTS fkf68lgbsbxl310n0jifwpfqgfh;
ALTER TABLE IF EXISTS ONLY public.rating DROP CONSTRAINT IF EXISTS fkej64wrel22rywge9grqeegafb;
ALTER TABLE IF EXISTS ONLY public.favorite DROP CONSTRAINT IF EXISTS fka2lwa7bjrnbti5v12mga2et1y;
ALTER TABLE IF EXISTS ONLY public.users DROP CONSTRAINT IF EXISTS fk_users_role;
ALTER TABLE IF EXISTS ONLY public.users DROP CONSTRAINT IF EXISTS fk_users_owner;
ALTER TABLE IF EXISTS ONLY public.car DROP CONSTRAINT IF EXISTS fk_car_owner;
ALTER TABLE IF EXISTS ONLY public.car DROP CONSTRAINT IF EXISTS fk_car_category;
ALTER TABLE IF EXISTS ONLY public.car_features DROP CONSTRAINT IF EXISTS fk3m8y2ydyje8kndvty5pxo6qy;
ALTER TABLE IF EXISTS ONLY public.reservation DROP CONSTRAINT IF EXISTS fk13bqwv27f434tg1kaiw2w32a4;
DROP INDEX IF EXISTS public.idx_reservation_user;
DROP INDEX IF EXISTS public.idx_reservation_status;
DROP INDEX IF EXISTS public.idx_reservation_car_dates;
DROP INDEX IF EXISTS public.idx_favorite_user;
DROP INDEX IF EXISTS public.idx_favorite_car;
ALTER TABLE IF EXISTS ONLY public.users DROP CONSTRAINT IF EXISTS users_pkey;
ALTER TABLE IF EXISTS ONLY public.favorite DROP CONSTRAINT IF EXISTS ukrjrbt0u8fvf9ss2mvy9hbbjtq;
ALTER TABLE IF EXISTS ONLY public.car DROP CONSTRAINT IF EXISTS ukpvp69n7acjyr5racokd4pdr4t;
ALTER TABLE IF EXISTS ONLY public.policy DROP CONSTRAINT IF EXISTS ukkr4rgboc28in15rgxlmgl7st;
ALTER TABLE IF EXISTS ONLY public.users DROP CONSTRAINT IF EXISTS ukkpeyao30ym7l5vf8wsterwase;
ALTER TABLE IF EXISTS ONLY public.feature DROP CONSTRAINT IF EXISTS ukjhueeftkn8ve8th8m8a2878dr;
ALTER TABLE IF EXISTS ONLY public.rating DROP CONSTRAINT IF EXISTS ukb1b6b250xrqva3wb9bre0gwsp;
ALTER TABLE IF EXISTS ONLY public.policy DROP CONSTRAINT IF EXISTS ukaa409ngsd4hajgpkaydedve7u;
ALTER TABLE IF EXISTS ONLY public.category DROP CONSTRAINT IF EXISTS uk46ccwnsi9409t36lurvtyljak;
ALTER TABLE IF EXISTS ONLY public.roles DROP CONSTRAINT IF EXISTS roles_pkey;
ALTER TABLE IF EXISTS ONLY public.roles DROP CONSTRAINT IF EXISTS roles_name_key;
ALTER TABLE IF EXISTS ONLY public.role_permission DROP CONSTRAINT IF EXISTS role_permission_pkey;
ALTER TABLE IF EXISTS ONLY public.reservation DROP CONSTRAINT IF EXISTS reservation_pkey;
ALTER TABLE IF EXISTS ONLY public.rating DROP CONSTRAINT IF EXISTS rating_pkey;
ALTER TABLE IF EXISTS ONLY public.policy DROP CONSTRAINT IF EXISTS policy_pkey;
ALTER TABLE IF EXISTS ONLY public.permissions DROP CONSTRAINT IF EXISTS permissions_pkey;
ALTER TABLE IF EXISTS ONLY public.permissions DROP CONSTRAINT IF EXISTS permissions_code_key;
ALTER TABLE IF EXISTS ONLY public.feature DROP CONSTRAINT IF EXISTS feature_pkey;
ALTER TABLE IF EXISTS ONLY public.favorite DROP CONSTRAINT IF EXISTS favorite_pkey;
ALTER TABLE IF EXISTS ONLY public.category DROP CONSTRAINT IF EXISTS category_pkey;
ALTER TABLE IF EXISTS ONLY public.car DROP CONSTRAINT IF EXISTS car_pkey;
ALTER TABLE IF EXISTS ONLY public.car_features DROP CONSTRAINT IF EXISTS car_features_pkey;
ALTER TABLE IF EXISTS public.roles ALTER COLUMN id DROP DEFAULT;
ALTER TABLE IF EXISTS public.permissions ALTER COLUMN id DROP DEFAULT;
DROP TABLE IF EXISTS public.users;
DROP SEQUENCE IF EXISTS public.roles_id_seq;
DROP TABLE IF EXISTS public.roles;
DROP TABLE IF EXISTS public.role_permission;
DROP TABLE IF EXISTS public.reservation;
DROP TABLE IF EXISTS public.rating;
DROP TABLE IF EXISTS public.policy;
DROP SEQUENCE IF EXISTS public.permissions_id_seq;
DROP TABLE IF EXISTS public.permissions;
DROP TABLE IF EXISTS public.feature;
DROP TABLE IF EXISTS public.favorite;
DROP TABLE IF EXISTS public.category;
DROP TABLE IF EXISTS public.car_images;
DROP TABLE IF EXISTS public.car_features;
DROP TABLE IF EXISTS public.car;
SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: car; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.car (
    id bigint NOT NULL,
    available boolean NOT NULL,
    brand character varying(255) NOT NULL,
    model character varying(255) NOT NULL,
    plate character varying(255) NOT NULL,
    price_per_day double precision NOT NULL,
    price_per_hour double precision NOT NULL,
    year integer NOT NULL,
    owner_id bigint NOT NULL,
    category_id bigint NOT NULL
);


--
-- Name: car_features; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.car_features (
    car_id bigint NOT NULL,
    feature_id bigint NOT NULL
);


--
-- Name: car_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.car ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.car_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: car_images; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.car_images (
    car_id bigint NOT NULL,
    image_path character varying(255)
);


--
-- Name: category; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.category (
    id bigint NOT NULL,
    description character varying(255),
    name character varying(255) NOT NULL
);


--
-- Name: category_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.category ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: favorite; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.favorite (
    id bigint NOT NULL,
    created_at timestamp with time zone NOT NULL,
    car_id bigint NOT NULL,
    user_id bigint NOT NULL
);


--
-- Name: favorite_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.favorite ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.favorite_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: feature; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.feature (
    id bigint NOT NULL,
    icon character varying(255),
    name character varying(255) NOT NULL
);


--
-- Name: feature_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.feature ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.feature_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: permissions; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.permissions (
    id bigint NOT NULL,
    code character varying(100) NOT NULL,
    description character varying(255)
);


--
-- Name: permissions_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.permissions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: permissions_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.permissions_id_seq OWNED BY public.permissions.id;


--
-- Name: policy; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.policy (
    id bigint NOT NULL,
    content text NOT NULL,
    display_order integer NOT NULL,
    slug character varying(255) NOT NULL,
    title character varying(255) NOT NULL,
    updated_at timestamp(6) with time zone NOT NULL
);


--
-- Name: policy_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.policy ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.policy_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: rating; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.rating (
    id bigint NOT NULL,
    comment text,
    created_at timestamp with time zone NOT NULL,
    score integer NOT NULL,
    updated_at timestamp with time zone NOT NULL,
    car_id bigint NOT NULL,
    reservation_id bigint NOT NULL,
    user_id bigint NOT NULL,
    CONSTRAINT rating_score_check CHECK (((score <= 5) AND (score >= 1)))
);


--
-- Name: rating_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.rating ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.rating_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: reservation; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.reservation (
    id bigint NOT NULL,
    car_id bigint,
    user_id bigint,
    start_at timestamp with time zone NOT NULL,
    end_at timestamp with time zone NOT NULL,
    total_price numeric(10,2) NOT NULL,
    created_at timestamp with time zone DEFAULT now() NOT NULL,
    cancelled_at timestamp with time zone,
    completed_at timestamp with time zone,
    dispatched_at timestamp with time zone,
    completed_by_id bigint,
    dispatched_by_id bigint,
    status character varying(255) DEFAULT 'PENDING'::character varying NOT NULL
);


--
-- Name: reservation_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.reservation ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.reservation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: role_permission; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.role_permission (
    role_id bigint NOT NULL,
    permission_id bigint NOT NULL
);


--
-- Name: roles; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.roles (
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(255)
);


--
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- Name: roles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.roles_id_seq OWNED BY public.roles.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    last_name character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    password character varying(255) NOT NULL,
    photo_path character varying(255),
    owner_id bigint,
    verified boolean DEFAULT false NOT NULL,
    phone character varying(20),
    reset_token character varying(255),
    reset_token_expiry timestamp(6) with time zone,
    role_id bigint NOT NULL
);


--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

ALTER TABLE public.users ALTER COLUMN id ADD GENERATED BY DEFAULT AS IDENTITY (
    SEQUENCE NAME public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);


--
-- Name: permissions id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.permissions ALTER COLUMN id SET DEFAULT nextval('public.permissions_id_seq'::regclass);


--
-- Name: roles id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.roles ALTER COLUMN id SET DEFAULT nextval('public.roles_id_seq'::regclass);


--
-- Data for Name: car; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.car (id, available, brand, model, plate, price_per_day, price_per_hour, year, owner_id, category_id) FROM stdin;
9	t	Fiat	Cronos	AB218CD	42	5.25	2023	3	2
11	t	Fiat	Toro	AD412CE	85	10.63	2023	3	5
12	t	Fiat	Mobi	AE523DF	30	3.75	2024	3	1
13	t	Fiat	Pulse	AF634EG	55	6.88	2024	3	4
14	t	VW	Gol Trend	AG745FH	35	4.38	2021	3	2
15	t	VW	Polo	AH856GI	45	5.63	2023	3	2
16	f	VW	Amarok	AJ967HJ	130	16.25	2024	3	5
17	t	VW	Taos	AK078IK	70	8.75	2023	3	4
18	t	VW	Nivus	AL189JL	60	7.5	2023	3	4
19	t	VW	Virtus	AM290KM	48	6	2022	3	3
20	t	Toyota	Corolla	AN301LN	65	8.13	2024	3	3
22	f	Toyota	Hilux	AQ523NQ	120	15	2024	3	5
23	t	Toyota	Yaris	AR634OR	45	5.63	2023	3	2
24	t	Toyota	SW4	AS745PS	140	17.5	2023	3	4
25	t	Chevrolet	Onix	AT856QT	42	5.25	2023	3	2
26	t	Chevrolet	Cruze	AU967RU	50	6.25	2022	3	3
27	t	Chevrolet	Tracker	AV078SV	62	7.75	2024	3	4
28	f	Chevrolet	S10	AW189TW	110	13.75	2023	3	5
29	t	Ford	Focus	AX290UX	46	5.75	2021	3	3
30	t	Ford	Ranger	AY301VY	115	14.38	2024	3	5
31	t	Ford	Fiesta	AZ412WZ	32	4	2020	3	1
32	t	Ford	Territory	BA523XA	75	9.38	2023	3	4
33	t	Renault	Clio	BB634YB	30	3.75	2021	3	1
34	t	Renault	Sandero	BC745ZC	34	4.25	2022	3	2
35	t	Renault	Logan	BD856AD	33	4.13	2022	3	1
36	t	Renault	Duster	BE967BE	65	8.13	2023	3	4
37	t	Renault	Kwid	BF078CF	28	3.5	2024	3	1
38	f	Renault	Alaskan	BG189DG	105	13.13	2022	3	5
39	t	Peugeot	208	BH290EH	43	5.38	2023	3	2
40	t	Peugeot	2008	BJ401FJ	58	7.25	2024	3	4
60	t	Auto	Propio	AAA111	100	10	2026	1	7
41	t	Peugeot	408	BK512GK	80	10	2023	3	3
42	t	Citroën	C3	BL623HL	36	4.5	2023	3	2
43	t	Citroën	C4 Cactus	BM734IM	44	5.5	2021	3	4
1	t	Toyota	Corolla	ABC123	120	15	2023	3	3
2	t	Honda	Civic	ABC124	90	12	2025	3	3
3	t	Toyota	Etios	XYZ123	80	10	2024	3	1
4	t	Chevrolet	Cruze	XYZ124	95	20	2025	3	3
5	t	Ford	Fiesta	XXY123	85	10	2024	3	1
6	t	Wolkswagen	Polo	YYZ	79	11	2024	3	2
7	t	Fiat	Cronos	CCC123	79	10	2025	3	2
8	t	Ford	Focus	FFF123	99	18	2025	3	3
10	t	Fiat	Argo	AC301BD	38	4.77	2022	3	2
21	t	Toyota	Etios	AP412MP	40	5.01	2022	3	1
44	t	Nissan	Versa	BN845JN	41	5.13	2023	3	3
45	t	Nissan	Kicks	BP956KP	55	6.88	2023	3	4
46	f	Nissan	Frontier	BQ067LQ	118	14.75	2024	3	5
47	t	Honda	Civic	BR178MR	68	8.5	2023	3	3
48	t	Honda	HR-V	BS289NS	62	7.75	2022	3	4
49	t	Honda	CR-V	BT390OT	90	11.25	2023	3	4
50	t	Hyundai	Creta	BU401PU	66	8.25	2024	3	4
51	t	Kia	Sportage	BV512QV	72	9	2023	3	4
52	t	Jeep	Renegade	BW623RW	64	8	2023	3	4
53	t	Jeep	Compass	BX734SX	88	11	2024	3	4
54	t	Mercedes-Benz	Clase A	BY845TY	95	11.88	2023	3	7
55	t	Mercedes-Benz	Clase C	BZ956UZ	130	16.25	2022	3	7
56	t	BMW	Serie 1	CA067VA	105	13.13	2023	3	7
57	t	Audi	A3	CB178WB	100	12.5	2023	3	7
58	f	Audi	Q3	CC289XC	110	13.75	2022	3	7
\.


--
-- Data for Name: car_features; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.car_features (car_id, feature_id) FROM stdin;
9	5
11	5
12	5
13	5
14	5
15	5
16	5
17	5
18	5
19	5
20	5
22	5
23	5
24	5
25	5
26	5
27	5
28	5
29	5
30	5
31	5
32	5
33	5
34	5
35	5
36	5
37	5
38	5
39	5
40	5
41	5
42	5
43	5
1	5
2	5
3	5
4	5
5	5
6	5
7	5
8	5
10	5
21	5
44	5
45	5
46	5
47	5
48	5
49	5
50	5
51	5
52	5
53	5
54	5
55	5
56	5
57	5
58	5
60	1
60	2
60	5
\.


--
-- Data for Name: car_images; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.car_images (car_id, image_path) FROM stdin;
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
2	https://images.unsplash.com/photo-1502877338535-766e1452684a?w=800&h=600&fit=crop&q=70
1	https://images.unsplash.com/photo-1543269664-56d93c1b41a6?w=800&h=600&fit=crop&q=70
3	https://images.unsplash.com/photo-1503376780353-7e6692767b70?w=800&h=600&fit=crop&q=70
4	https://images.unsplash.com/photo-1517245386807-bb43f82c33c4?w=800&h=600&fit=crop&q=70
5	https://images.unsplash.com/photo-1525609004556-c46c7d6cf023?w=800&h=600&fit=crop&q=70
6	https://images.unsplash.com/photo-1541899481282-d53bffe3c35d?w=800&h=600&fit=crop&q=70
7	https://images.unsplash.com/photo-1544636331-e26879cd4d9b?w=800&h=600&fit=crop&q=70
7	https://images.unsplash.com/photo-1493238792000-8113da705763?w=800&h=600&fit=crop&q=70
8	https://images.unsplash.com/photo-1494976388531-d1058494cdd8?w=800&h=600&fit=crop&q=70
8	https://images.unsplash.com/photo-1533473359331-0135ef1b58bf?w=800&h=600&fit=crop&q=70
60	https://images.unsplash.com/photo-1511919884226-fd3cad34687c?w=800&h=600&fit=crop&q=70
\.


--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.category (id, description, name) FROM stdin;
1	\N	Económico
2	\N	Compacto
3	\N	Mediano
4	\N	SUV
5	\N	Pickup
6	\N	Familiar
7	\N	Premium
8	\N	Utilitario
\.


--
-- Data for Name: favorite; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.favorite (id, created_at, car_id, user_id) FROM stdin;
2	2026-09-12 14:57:40.860748+00	10	1
4	2026-09-14 14:57:50.025682+00	17	1
5	2026-09-14 14:57:50.846137+00	50	1
6	2026-09-14 14:58:07.536252+00	49	1
7	2026-09-17 02:08:57.564236+00	54	1
8	2026-09-17 02:08:58.606688+00	57	1
9	2026-09-21 16:08:57.022616+00	25	1
10	2026-09-29 23:42:04.749008+00	3	10
\.


--
-- Data for Name: feature; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.feature (id, icon, name) FROM stdin;
1	FaBluetooth	Bluetooth
2	FaCamera	Camara Trasera
3	FaKey	Cierre Centralizado
4	FaSun	Techo solar
5	FaShieldAlt	Alarma
\.


--
-- Data for Name: permissions; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.permissions (id, code, description) FROM stdin;
1	RESERVATION_CREATE	Crear reservas propias
2	RESERVATION_VIEW_SCOPE	Ver reservas según alcance del rol
3	CAR_CREATE	Crear autos
4	CAR_EDIT	Editar autos
5	CAR_DELETE	Borrar autos
6	DISPATCH_RESERVATION	Despachar reservas
7	COMPLETE_RESERVATION	Completar reservas
8	CANCEL_ANY	Cancelar cualquier reserva PENDING
9	REVERT_RESERVATION	Revertir estados de reservas
10	EMPLOYEE_MANAGE	Crear y gestionar empleados
11	OWNER_VERIFY	Verificar cuentas OWNER
12	USER_ROLE_ASSIGN	Asignar roles a usuarios
13	CATALOG_MANAGE	Gestionar categorías y features
14	POLICY_MANAGE	Gestionar políticas
15	RATING_MODERATE	Moderar puntuaciones
\.


--
-- Data for Name: policy; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.policy (id, content, display_order, slug, title, updated_at) FROM stdin;
1	Las reservas se realizan exclusivamente a través de la plataforma con autenticación. El precio se calcula por hora: se cobra la hora completa (ceil) según la tarifa por hora del vehículo. Toda reserva debe durar al menos 1 hora y sus horarios de inicio y fin deben caer en intervalos de 30 minutos (00 o 30). Si la duración supera las 48 horas, se aplica un 10% de descuento sobre el total. Entre reservas del mismo vehículo se respeta un buffer de 1 hora: no se puede solapar ni dejar menos de 60 minutos libres. El vehículo debe estar marcado como disponible; de lo contrario la reserva es rechazada. Los horarios se guardan en UTC y se muestran al usuario en hora local de Argentina (America/Argentina/Buenos_Aires).	1	reservas-tarifas	Reservas y Tarifas	2026-09-21 16:04:03.873755+00
2	Podés cancelar tu reserva hasta 24 horas antes del inicio sin cargo. Cancelaciones con menos de 24 h de anticipación tienen una retención del 30% del total. Si no te presentás (no-show), se cobra el 100% de la reserva. Las cancelaciones por causa del proveedor (vehículo no disponible, falla mecánica) generan reembolso total sin retenciones. Para solicitar cancelación, contactanos desde tu panel en Mis Reservas o por los canales de contacto. Los reembolsos se acreditan dentro de los 5 a 10 días hábiles por el mismo medio de pago.	2	cancelaciones	Cancelaciones y Reembolsos	2026-09-21 16:04:03.978138+00
3	El conductor debe presentar licencia vigente, DNI y ser mayor de 21 años. El vehículo se entrega con tanque y condiciones documentadas con fotos; debe devolverse en el mismo estado, con tolerancia de 1 hora de buffer entre reservas. Está prohibido fumar, transportar sustancias ilegales o exceder la capacidad del vehículo. Multas, peajes y daños por mal uso son responsabilidad del titular de la reserva. Ante siniestro, el usuario debe dar aviso inmediato y realizar la denuncia correspondiente; el seguro básico está incluido y la franquicia varía por categoría. Kilometraje libre dentro del territorio argentino salvo que la categoría indique lo contrario.	3	uso-responsabilidades	Uso del Vehículo y Responsabilidades	2026-09-21 16:04:03.979712+00
4	Tratamos tus datos (nombre, email, reservas, fotos de perfil) conforme a la Ley 25.326 de Protección de Datos Personales. Usamos tu información para gestionar reservas, autenticación JWT y comunicaciones del servicio. No compartimos datos con terceros salvo obligación legal o proveedores esenciales (pagos, hosting). Las contraseñas se almacenan hasheadas con BCrypt y nunca se exponen vía API. Podés solicitar acceso, rectificación o eliminación de tus datos escribiendo a privacidad@rentacarnow.com. Conservamos reservas y facturación por 5 años por obligaciones legales.	4	privacidad	Privacidad y Protección de Datos	2026-09-21 16:04:03.980665+00
5	Cada vehículo pertenece a un propietario (OWNER) verificado por un ADMIN; los empleados (EMPLOYEE) gestionan los autos de su OWNER. Las categorías (Económico, Compacto, Mediano, SUV, Pickup, Familiar, Premium, Utilitario, Eléctrico) son administradas por ADMIN; eliminar una categoría elimina en cascada todos sus vehículos, reservas, favoritos y archivos asociados. Las características (features) describen equipamiento y se gestionan por ADMIN. Las imágenes se almacenan en el servidor y se sirven desde /uploads; el contenido subido debe respetar derechos de terceros.	5	propietarios-contenido	Propietarios, Categorías y Contenido	2026-09-21 16:04:03.981539+00
6	RentaCarNow — Tu viaje empieza aquí. Soporte: soporte@rentacarnow.com | Tel: +54 11 5555-0123 | Lun a Vie 9 a 18 h (America/Argentina/Buenos_Aires). Domicilio: Av. Corrientes 1234, CABA, Argentina. Para consultas sobre reservas, verificaciones de OWNER o reclamos, usá tu panel o escribinos por estos canales. Términos vigentes desde septiembre de 2026; nos reservamos el derecho de actualizar estas políticas con aviso previo en la plataforma.	6	contacto	Contacto y Soporte	2026-09-21 16:04:03.984257+00
\.


--
-- Data for Name: rating; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.rating (id, comment, created_at, score, updated_at, car_id, reservation_id, user_id) FROM stdin;
1		2026-09-29 23:23:58.103866+00	5	2026-09-29 23:23:58.104966+00	26	3	10
2	Excelente servicio! muy lindo el auto	2026-10-01 01:54:54.419023+00	5	2026-10-01 01:55:15.063449+00	1	10	10
\.


--
-- Data for Name: reservation; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.reservation (id, car_id, user_id, start_at, end_at, total_price, created_at, cancelled_at, completed_at, dispatched_at, completed_by_id, dispatched_by_id, status) FROM stdin;
4	49	4	2026-09-18 02:46:58.122147+00	2026-09-18 05:46:58.132007+00	33.75	2026-09-18 00:46:58.166804+00	\N	\N	\N	\N	\N	PENDING
6	12	4	2026-09-19 00:47:05.752874+00	2026-09-21 02:47:05.762042+00	168.75	2026-09-18 00:47:05.782438+00	\N	\N	\N	\N	\N	PENDING
7	2	4	2026-09-18 01:00:00+00	2026-09-21 20:00:00+00	982.80	2026-09-18 00:53:15.344237+00	\N	\N	\N	\N	\N	PENDING
8	47	4	2026-09-18 05:00:00.000761+00	2026-09-18 06:00:00.000761+00	8.50	2026-09-18 01:00:46.339735+00	\N	\N	\N	\N	\N	PENDING
9	50	1	2026-09-29 23:00:00+00	2026-10-01 00:00:00+00	206.25	2026-09-29 22:56:11.820445+00	\N	\N	\N	\N	\N	PENDING
3	26	10	2026-09-18 00:46:52.038656+00	2026-09-19 00:46:52.038656+00	0.00	2026-09-18 00:46:52.038656+00	\N	2026-09-29 23:23:29.2512+00	2026-09-29 23:23:24.266125+00	1	1	COMPLETED
10	1	10	2026-10-01 02:00:00+00	2026-10-01 13:00:00+00	165.00	2026-10-01 01:52:15.360643+00	\N	2026-10-01 01:54:38.278901+00	2026-10-01 01:54:34.769235+00	3	3	COMPLETED
11	30	10	2026-10-01 12:00:00+00	2026-10-01 13:00:00+00	14.38	2026-10-01 01:56:06.533289+00	\N	\N	\N	\N	\N	PENDING
12	60	4	2026-10-14 16:00:00+00	2026-10-14 19:00:00+00	30.00	2026-10-13 23:37:34.935035+00	\N	\N	\N	\N	\N	PENDING
5	49	4	2026-09-18 07:37:02.268774+00	2026-09-18 08:47:02.268774+00	22.50	2026-09-18 00:47:02.280222+00	2026-10-14 01:40:44.932023+00	\N	\N	\N	\N	CANCELLED
\.


--
-- Data for Name: role_permission; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.role_permission (role_id, permission_id) FROM stdin;
1	1
2	1
3	1
4	1
1	2
2	2
3	2
4	2
2	3
3	3
4	3
2	4
3	4
4	4
2	5
3	5
4	5
2	6
3	6
4	6
2	7
3	7
4	7
2	8
3	8
4	8
2	9
3	9
4	9
3	10
4	10
4	11
4	12
4	13
4	14
4	15
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.roles (id, name, description) FROM stdin;
1	USER	Reserva autos
2	EMPLOYEE	Gestiona autos y reservas de su OWNER
3	OWNER	Gestiona sus autos, reservas y empleados
4	ADMIN	Control total y asignación de roles
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

COPY public.users (id, email, last_name, name, password, photo_path, owner_id, verified, phone, reset_token, reset_token_expiry, role_id) FROM stdin;
3	flota@rentacarnow.com	RentACarNow	Flota	$2a$10$RkW1CaQpHHa7rOA4s0rsbuYQKJOL0kmFoNUHflLwt2yYY1wkh47wy	\N	\N	t	\N	\N	\N	3
4	user@rentacarnow.com	Prueba	User	$2a$10$csu5BWd/sCseji8wHtVFduXUeDMg1Qswm15i3E0vO7kjMzMqAFbIS	\N	\N	t	\N	\N	\N	1
6	admin@rentacarnow.com	Prueba	Admin	$2a$10$I2dnO9ZLq.z91TxDViHYOOFuKYibLl8KJ.T/4ROZlmMEbhoihpIDq	\N	\N	t	\N	\N	\N	4
5	owner@rentacarnow.com	Prueba	Owner	$2a$10$Ig4Zxvk1SIe2QuLGgh7tjuiGhFKk3c/WJSxxoR0dv7Mw/XS2QJzL.	\N	\N	t	\N	\N	\N	3
7	employee@rentacarnow.com	Prueba	Employee	$2a$10$nu8XCNR35Yg0RpWNL.BxJ.JkUtFoy6sk6YK/mqJh6AV.xc58WqqRK	\N	5	t	\N	\N	\N	2
9	empleado_1@gmail.com	1	empleado	$2a$10$I2dnO9ZLq.z91TxDViHYOOFuKYibLl8KJ.T/4ROZlmMEbhoihpIDq	\N	8	t	\N	\N	\N	2
10	cliente@gmail.com	cliente	cliente	$2a$10$I2dnO9ZLq.z91TxDViHYOOFuKYibLl8KJ.T/4ROZlmMEbhoihpIDq	\N	\N	t	\N	\N	\N	1
1	pablo.rm.singh@gmail.com	Singh	Pablo	$2a$10$I2dnO9ZLq.z91TxDViHYOOFuKYibLl8KJ.T/4ROZlmMEbhoihpIDq	\N	\N	f	1124563833	\N	\N	4
8	owner_1@gmail.com	1	Owner	$2a$10$I2dnO9ZLq.z91TxDViHYOOFuKYibLl8KJ.T/4ROZlmMEbhoihpIDq	\N	\N	t	\N	\N	\N	3
2	pablo.rm.singh2@gmail.com	Singh	Pablo	$2a$10$I2dnO9ZLq.z91TxDViHYOOFuKYibLl8KJ.T/4ROZlmMEbhoihpIDq	\N	\N	f	\N	\N	\N	4
\.


--
-- Name: car_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.car_id_seq', 61, true);


--
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.category_id_seq', 18, true);


--
-- Name: favorite_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.favorite_id_seq', 10, true);


--
-- Name: feature_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.feature_id_seq', 5, true);


--
-- Name: permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.permissions_id_seq', 30, true);


--
-- Name: policy_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.policy_id_seq', 6, true);


--
-- Name: rating_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.rating_id_seq', 2, true);


--
-- Name: reservation_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.reservation_id_seq', 12, true);


--
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.roles_id_seq', 8, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.users_id_seq', 10, true);


--
-- Name: car_features car_features_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.car_features
    ADD CONSTRAINT car_features_pkey PRIMARY KEY (car_id, feature_id);


--
-- Name: car car_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.car
    ADD CONSTRAINT car_pkey PRIMARY KEY (id);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: favorite favorite_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.favorite
    ADD CONSTRAINT favorite_pkey PRIMARY KEY (id);


--
-- Name: feature feature_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.feature
    ADD CONSTRAINT feature_pkey PRIMARY KEY (id);


--
-- Name: permissions permissions_code_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.permissions
    ADD CONSTRAINT permissions_code_key UNIQUE (code);


--
-- Name: permissions permissions_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.permissions
    ADD CONSTRAINT permissions_pkey PRIMARY KEY (id);


--
-- Name: policy policy_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.policy
    ADD CONSTRAINT policy_pkey PRIMARY KEY (id);


--
-- Name: rating rating_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rating
    ADD CONSTRAINT rating_pkey PRIMARY KEY (id);


--
-- Name: reservation reservation_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (id);


--
-- Name: role_permission role_permission_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.role_permission
    ADD CONSTRAINT role_permission_pkey PRIMARY KEY (role_id, permission_id);


--
-- Name: roles roles_name_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_name_key UNIQUE (name);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);


--
-- Name: category uk46ccwnsi9409t36lurvtyljak; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT uk46ccwnsi9409t36lurvtyljak UNIQUE (name);


--
-- Name: policy ukaa409ngsd4hajgpkaydedve7u; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.policy
    ADD CONSTRAINT ukaa409ngsd4hajgpkaydedve7u UNIQUE (title);


--
-- Name: rating ukb1b6b250xrqva3wb9bre0gwsp; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rating
    ADD CONSTRAINT ukb1b6b250xrqva3wb9bre0gwsp UNIQUE (reservation_id);


--
-- Name: feature ukjhueeftkn8ve8th8m8a2878dr; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.feature
    ADD CONSTRAINT ukjhueeftkn8ve8th8m8a2878dr UNIQUE (name);


--
-- Name: users ukkpeyao30ym7l5vf8wsterwase; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT ukkpeyao30ym7l5vf8wsterwase UNIQUE (reset_token);


--
-- Name: policy ukkr4rgboc28in15rgxlmgl7st; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.policy
    ADD CONSTRAINT ukkr4rgboc28in15rgxlmgl7st UNIQUE (slug);


--
-- Name: car ukpvp69n7acjyr5racokd4pdr4t; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.car
    ADD CONSTRAINT ukpvp69n7acjyr5racokd4pdr4t UNIQUE (plate);


--
-- Name: favorite ukrjrbt0u8fvf9ss2mvy9hbbjtq; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.favorite
    ADD CONSTRAINT ukrjrbt0u8fvf9ss2mvy9hbbjtq UNIQUE (user_id, car_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: idx_favorite_car; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_favorite_car ON public.favorite USING btree (car_id);


--
-- Name: idx_favorite_user; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_favorite_user ON public.favorite USING btree (user_id);


--
-- Name: idx_reservation_car_dates; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_reservation_car_dates ON public.reservation USING btree (car_id, start_at, end_at);


--
-- Name: idx_reservation_status; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_reservation_status ON public.reservation USING btree (status);


--
-- Name: idx_reservation_user; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_reservation_user ON public.reservation USING btree (user_id);


--
-- Name: reservation fk13bqwv27f434tg1kaiw2w32a4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fk13bqwv27f434tg1kaiw2w32a4 FOREIGN KEY (dispatched_by_id) REFERENCES public.users(id);


--
-- Name: car_features fk3m8y2ydyje8kndvty5pxo6qy; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.car_features
    ADD CONSTRAINT fk3m8y2ydyje8kndvty5pxo6qy FOREIGN KEY (car_id) REFERENCES public.car(id);


--
-- Name: car fk_car_category; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.car
    ADD CONSTRAINT fk_car_category FOREIGN KEY (category_id) REFERENCES public.category(id);


--
-- Name: car fk_car_owner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.car
    ADD CONSTRAINT fk_car_owner FOREIGN KEY (owner_id) REFERENCES public.users(id);


--
-- Name: users fk_users_owner; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk_users_owner FOREIGN KEY (owner_id) REFERENCES public.users(id);


--
-- Name: users fk_users_role; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk_users_role FOREIGN KEY (role_id) REFERENCES public.roles(id);


--
-- Name: favorite fka2lwa7bjrnbti5v12mga2et1y; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.favorite
    ADD CONSTRAINT fka2lwa7bjrnbti5v12mga2et1y FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: rating fkej64wrel22rywge9grqeegafb; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rating
    ADD CONSTRAINT fkej64wrel22rywge9grqeegafb FOREIGN KEY (reservation_id) REFERENCES public.reservation(id);


--
-- Name: rating fkf68lgbsbxl310n0jifwpfqgfh; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rating
    ADD CONSTRAINT fkf68lgbsbxl310n0jifwpfqgfh FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: reservation fkgkmbspv7rljixxoxo1af80lpp; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fkgkmbspv7rljixxoxo1af80lpp FOREIGN KEY (car_id) REFERENCES public.car(id);


--
-- Name: favorite fkgng78lym9ghokl8n4ge710mcu; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.favorite
    ADD CONSTRAINT fkgng78lym9ghokl8n4ge710mcu FOREIGN KEY (car_id) REFERENCES public.car(id);


--
-- Name: car_features fkhtkgcnbe4l0pmtrhfcg1nlg8s; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.car_features
    ADD CONSTRAINT fkhtkgcnbe4l0pmtrhfcg1nlg8s FOREIGN KEY (feature_id) REFERENCES public.feature(id);


--
-- Name: reservation fki9nlvyg39r571da6parogahe; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fki9nlvyg39r571da6parogahe FOREIGN KEY (completed_by_id) REFERENCES public.users(id);


--
-- Name: car_images fkm398nw5sxpjn118loitbdbuw4; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.car_images
    ADD CONSTRAINT fkm398nw5sxpjn118loitbdbuw4 FOREIGN KEY (car_id) REFERENCES public.car(id);


--
-- Name: users fkntyuh06i5y3y6ir598luxy3k9; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fkntyuh06i5y3y6ir598luxy3k9 FOREIGN KEY (owner_id) REFERENCES public.users(id);


--
-- Name: reservation fkrea93581tgkq61mdl13hehami; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT fkrea93581tgkq61mdl13hehami FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- Name: rating fktk5oym7po28cvokyv25jet7nx; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.rating
    ADD CONSTRAINT fktk5oym7po28cvokyv25jet7nx FOREIGN KEY (car_id) REFERENCES public.car(id);


--
-- Name: role_permission role_permission_permission_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.role_permission
    ADD CONSTRAINT role_permission_permission_id_fkey FOREIGN KEY (permission_id) REFERENCES public.permissions(id) ON DELETE CASCADE;


--
-- Name: role_permission role_permission_role_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.role_permission
    ADD CONSTRAINT role_permission_role_id_fkey FOREIGN KEY (role_id) REFERENCES public.roles(id) ON DELETE CASCADE;


--
-- PostgreSQL database dump complete
--


