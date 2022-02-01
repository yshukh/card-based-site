--
-- PostgreSQL database dump
--

-- Dumped from database version 14.1
-- Dumped by pg_dump version 14.1

-- Started on 2022-02-01 16:32:04

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
-- TOC entry 3375 (class 1262 OID 16394)
-- Name: blog; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE blog WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Ukrainian_Ukraine.1252';


\connect blog

BEGIN;

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

SET default_table_access_method = heap;

--
-- TOC entry 209 (class 1259 OID 16395)
-- Name: posts; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.posts (
    id_post integer NOT NULL,
    title character varying,
    text text,
    published boolean,
    image_link character varying,
    date timestamp without time zone,
    author character varying NOT NULL
);


--
-- TOC entry 210 (class 1259 OID 16400)
-- Name: Blog_Id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."Blog_Id_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3376 (class 0 OID 0)
-- Dependencies: 210
-- Name: Blog_Id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."Blog_Id_seq" OWNED BY public.posts.id_post;


--
-- TOC entry 211 (class 1259 OID 16401)
-- Name: comments; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.comments (
    id_comment integer NOT NULL,
    text text,
    date timestamp without time zone,
    author_login character varying NOT NULL,
    id_post integer NOT NULL
);


--
-- TOC entry 212 (class 1259 OID 16406)
-- Name: Comment_Id_comment_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."Comment_Id_comment_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3377 (class 0 OID 0)
-- Dependencies: 212
-- Name: Comment_Id_comment_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."Comment_Id_comment_seq" OWNED BY public.comments.id_comment;


--
-- TOC entry 213 (class 1259 OID 16407)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users (
    password character varying NOT NULL,
    id_user integer NOT NULL,
    privileged boolean DEFAULT false,
    login character varying
);


--
-- TOC entry 214 (class 1259 OID 16413)
-- Name: Users_Id_user_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public."Users_Id_user_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3378 (class 0 OID 0)
-- Dependencies: 214
-- Name: Users_Id_user_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public."Users_Id_user_seq" OWNED BY public.users.id_user;


--
-- TOC entry 215 (class 1259 OID 16414)
-- Name: comments_id_post_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.comments_id_post_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3379 (class 0 OID 0)
-- Dependencies: 215
-- Name: comments_id_post_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.comments_id_post_seq OWNED BY public.comments.id_post;


--
-- TOC entry 216 (class 1259 OID 16415)
-- Name: posts_tags; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.posts_tags (
    post_id integer NOT NULL,
    tag_id integer NOT NULL
);


--
-- TOC entry 217 (class 1259 OID 16418)
-- Name: posts_tags_post_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.posts_tags_post_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3380 (class 0 OID 0)
-- Dependencies: 217
-- Name: posts_tags_post_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.posts_tags_post_id_seq OWNED BY public.posts_tags.post_id;


--
-- TOC entry 218 (class 1259 OID 16419)
-- Name: posts_tags_tag_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.posts_tags_tag_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3381 (class 0 OID 0)
-- Dependencies: 218
-- Name: posts_tags_tag_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.posts_tags_tag_id_seq OWNED BY public.posts_tags.tag_id;


--
-- TOC entry 219 (class 1259 OID 16420)
-- Name: tags; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.tags (
    tag_id integer NOT NULL,
    tag_title character varying
);


--
-- TOC entry 220 (class 1259 OID 16425)
-- Name: tags_tag_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.tags_tag_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3382 (class 0 OID 0)
-- Dependencies: 220
-- Name: tags_tag_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.tags_tag_id_seq OWNED BY public.tags.tag_id;


--
-- TOC entry 221 (class 1259 OID 16426)
-- Name: users_posts; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE public.users_posts (
    id_post integer NOT NULL,
    id_user integer NOT NULL
);


--
-- TOC entry 222 (class 1259 OID 16429)
-- Name: users_posts_id_post_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.users_posts_id_post_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3383 (class 0 OID 0)
-- Dependencies: 222
-- Name: users_posts_id_post_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.users_posts_id_post_seq OWNED BY public.users_posts.id_post;


--
-- TOC entry 223 (class 1259 OID 16430)
-- Name: users_posts_id_user_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE public.users_posts_id_user_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3384 (class 0 OID 0)
-- Dependencies: 223
-- Name: users_posts_id_user_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE public.users_posts_id_user_seq OWNED BY public.users_posts.id_user;


--
-- TOC entry 3193 (class 2604 OID 16431)
-- Name: comments id_comment; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comments ALTER COLUMN id_comment SET DEFAULT nextval('public."Comment_Id_comment_seq"'::regclass);


--
-- TOC entry 3194 (class 2604 OID 16432)
-- Name: comments id_post; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comments ALTER COLUMN id_post SET DEFAULT nextval('public.comments_id_post_seq'::regclass);


--
-- TOC entry 3192 (class 2604 OID 16433)
-- Name: posts id_post; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.posts ALTER COLUMN id_post SET DEFAULT nextval('public."Blog_Id_seq"'::regclass);


--
-- TOC entry 3197 (class 2604 OID 16434)
-- Name: posts_tags post_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.posts_tags ALTER COLUMN post_id SET DEFAULT nextval('public.posts_tags_post_id_seq'::regclass);


--
-- TOC entry 3198 (class 2604 OID 16435)
-- Name: posts_tags tag_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.posts_tags ALTER COLUMN tag_id SET DEFAULT nextval('public.posts_tags_tag_id_seq'::regclass);


--
-- TOC entry 3199 (class 2604 OID 16436)
-- Name: tags tag_id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tags ALTER COLUMN tag_id SET DEFAULT nextval('public.tags_tag_id_seq'::regclass);


--
-- TOC entry 3196 (class 2604 OID 16437)
-- Name: users id_user; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users ALTER COLUMN id_user SET DEFAULT nextval('public."Users_Id_user_seq"'::regclass);


--
-- TOC entry 3200 (class 2604 OID 16438)
-- Name: users_posts id_post; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users_posts ALTER COLUMN id_post SET DEFAULT nextval('public.users_posts_id_post_seq'::regclass);


--
-- TOC entry 3201 (class 2604 OID 16439)
-- Name: users_posts id_user; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users_posts ALTER COLUMN id_user SET DEFAULT nextval('public.users_posts_id_user_seq'::regclass);


--
-- TOC entry 3357 (class 0 OID 16401)
-- Dependencies: 211
-- Data for Name: comments; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.comments VALUES (4, 'You know nothing about cold eyes!', '2017-12-03 00:55:35', 'fartt', 30);
INSERT INTO public.comments VALUES (5, 'Mask off!', '2017-12-03 11:19:45', 'fartt', 25);
INSERT INTO public.comments VALUES (6, 'Go around!', '2017-12-03 11:20:17', 'admin', 29);
INSERT INTO public.comments VALUES (7, 'Wait-wait! Unreality? HAHA It is not actually', '2017-12-03 11:25:01', 'fartt', 29);
INSERT INTO public.comments VALUES (8, 'NO HASH_TAGS???? WHY??', '2017-12-03 18:55:12', 'fartt', 30);
INSERT INTO public.comments VALUES (9, 'Tractor!', '2017-12-12 13:46:25', 'admin', 38);
INSERT INTO public.comments VALUES (10, 'Annoying haters!', '2017-12-12 16:47:29', 'denia', 51);


--
-- TOC entry 3355 (class 0 OID 16395)
-- Dependencies: 209
-- Data for Name: posts; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.posts VALUES (42, 'adfasdf', 'asdfasdfasdf', true, '../pictures/java.png', '2017-12-04 14:15:00', 'admin');
INSERT INTO public.posts VALUES (41, 'Anna', 'Exatly I wanna be with you', true, '../pictures/java.png', '2017-12-10 12:50:21', 'admin');
INSERT INTO public.posts VALUES (39, 'Slava zapara', 'ghjk', true, '../pictures/error.png', '2017-12-11 19:44:30', 'fartt');
INSERT INTO public.posts VALUES (28, 'Pink glasses', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', true, '../pictures/ff2e54f2ca5c09a877fb04d84bc562a4--lisa-frank-rainbow-unicorn.jpg', '2017-11-29 14:24:15', 'fartt');
INSERT INTO public.posts VALUES (27, 'Pizza receipt', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', true, '../pictures/pizzabackground.jpg', '2017-11-29 14:23:58', 'fartt');
INSERT INTO public.posts VALUES (29, 'Blade of unreality', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', true, '../pictures/images.jpg', '2017-11-29 14:25:08', 'fartt');
INSERT INTO public.posts VALUES (24, 'Ghost dreams', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', true, '../pictures/7BNxNtF.jpg', '2017-12-05 19:46:08', 'fartt');
INSERT INTO public.posts VALUES (26, 'Real lion', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', true, '../pictures/lion-1118467_960_720.jpg', '2017-12-05 19:33:11', 'fartt');
INSERT INTO public.posts VALUES (25, 'Robot is future', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', true, '../pictures/emerging-tech.jpg', '2017-12-05 17:53:53', 'fartt');
INSERT INTO public.posts VALUES (45, 'qwerty', 'qwerty', true, '../pictures/2016-03-04.png', '2017-12-04 16:01:50', 'admin');
INSERT INTO public.posts VALUES (36, 'Find me', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', true, '../pictures/back1.jpg', '2017-11-29 16:26:12', 'fartt');
INSERT INTO public.posts VALUES (40, 'adfasdf', 'asdfasdfasf', true, '../pictures/at-6rvxV3Zo.jpg', '2017-12-04 14:05:27', 'admin');
INSERT INTO public.posts VALUES (43, 'goood MO', 'Hi Niger!', true, '../pictures/cq5dam.resized.img.890.medium.time1487343862813.jpg', '2017-12-05 17:54:35', 'fartt');
INSERT INTO public.posts VALUES (44, 'qwerty', 'qwerty', true, '../pictures/trolltunga.jpg', '2017-12-05 17:54:52', 'fartt');
INSERT INTO public.posts VALUES (30, 'Cold eyes', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', true, '../pictures/cat-1285634_960_720.png', '2017-11-29 14:26:03', 'fartt');
INSERT INTO public.posts VALUES (32, 'Downshifting - lifestyle', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', true, '../pictures/trolltunga.jpg', '2017-11-29 14:27:14', 'fartt');
INSERT INTO public.posts VALUES (34, 'Adrenaline form races', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', true, '../pictures/cq5dam.resized.img.890.medium.time1487343862813.jpg', '2017-11-29 14:27:44', 'fartt');
INSERT INTO public.posts VALUES (33, 'Google', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', true, '../pictures/unnamed.png', '2017-11-29 14:27:22', 'fartt');
INSERT INTO public.posts VALUES (35, 'Winter bird', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', true, '../pictures/97e1dd3f8a3ecb81356fe754a1a113f31b6dbfd4-stock-photo-photo-of-a-common-kingfisher-alcedo-atthis-adult-male-perched-on-a-lichen-covered-branch-107647640.jpg', '2017-11-29 14:28:12', 'fartt');
INSERT INTO public.posts VALUES (37, 'Smoke', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', true, '../pictures/smoke-649963_960_720.jpg', '2017-11-29 16:32:00', 'admin');
INSERT INTO public.posts VALUES (47, 'UNPUBLISHED', 'sadfasfadQWERTY', false, '../pictures/2016-03-04.png', '2017-12-04 19:21:14', 'fartt');
INSERT INTO public.posts VALUES (46, 'UNPUBLISHED', 'sadfadsfasdfasdfasdfasdfasdf', false, '../pictures/2016-07-04.png', '2017-12-04 19:13:51', 'admin');
INSERT INTO public.posts VALUES (38, 'Very long title must look alse fine. Pretty front-end', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.', true, '../pictures/Emblema.png', '2017-12-12 13:47:04', 'admin');
INSERT INTO public.posts VALUES (48, 'Come back', 'I''m taking it easy, hohohohoho))', true, '../pictures/maxresdefault.jpg', '2017-12-05 13:22:46', 'fartt');
INSERT INTO public.posts VALUES (49, 'Go up', 'Go up asdasdasd', true, '../pictures/20151020_223424.jpg', '2017-12-05 13:44:37', 'fartt');
INSERT INTO public.posts VALUES (50, 'good start', 'fsfmsok;lfaf,', true, '../pictures/20161025_210759.jpg', '2017-12-05 13:50:36', 'fartt');
INSERT INTO public.posts VALUES (23, 'Simple error ', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', true, '../pictures/error.png', '2017-12-05 19:30:18', 'fartt');
INSERT INTO public.posts VALUES (31, 'Phiolet rose', 'Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui dolorem eum fugiat quo voluptas nulla pariatur?', true, '../pictures/rose-blue-flower-rose-blooms-67636.jpeg', '2017-12-12 13:47:54', 'admin');
INSERT INTO public.posts VALUES (51, 'Slava is kekboxer', 'He can do keking and hating


Hate hate hate', true, '../pictures/onoraki.png', '2017-12-12 16:44:25', 'denia');


--
-- TOC entry 3362 (class 0 OID 16415)
-- Dependencies: 216
-- Data for Name: posts_tags; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.posts_tags VALUES (31, 1);
INSERT INTO public.posts_tags VALUES (24, 1);
INSERT INTO public.posts_tags VALUES (24, 5);
INSERT INTO public.posts_tags VALUES (42, 7);
INSERT INTO public.posts_tags VALUES (42, 8);
INSERT INTO public.posts_tags VALUES (43, 7);
INSERT INTO public.posts_tags VALUES (43, 1);
INSERT INTO public.posts_tags VALUES (43, 9);
INSERT INTO public.posts_tags VALUES (44, 1);
INSERT INTO public.posts_tags VALUES (44, 5);
INSERT INTO public.posts_tags VALUES (44, 11);
INSERT INTO public.posts_tags VALUES (45, 12);
INSERT INTO public.posts_tags VALUES (45, 5);
INSERT INTO public.posts_tags VALUES (24, 13);
INSERT INTO public.posts_tags VALUES (47, 14);
INSERT INTO public.posts_tags VALUES (31, 15);
INSERT INTO public.posts_tags VALUES (31, 16);
INSERT INTO public.posts_tags VALUES (31, 9);
INSERT INTO public.posts_tags VALUES (32, 17);
INSERT INTO public.posts_tags VALUES (48, 18);
INSERT INTO public.posts_tags VALUES (48, 17);
INSERT INTO public.posts_tags VALUES (50, 19);
INSERT INTO public.posts_tags VALUES (50, 5);
INSERT INTO public.posts_tags VALUES (50, 16);
INSERT INTO public.posts_tags VALUES (39, 20);
INSERT INTO public.posts_tags VALUES (26, 20);
INSERT INTO public.posts_tags VALUES (23, 20);
INSERT INTO public.posts_tags VALUES (39, 21);
INSERT INTO public.posts_tags VALUES (41, 22);
INSERT INTO public.posts_tags VALUES (30, 14);
INSERT INTO public.posts_tags VALUES (40, 14);
INSERT INTO public.posts_tags VALUES (38, 23);
INSERT INTO public.posts_tags VALUES (31, 23);
INSERT INTO public.posts_tags VALUES (51, 20);


--
-- TOC entry 3365 (class 0 OID 16420)
-- Dependencies: 219
-- Data for Name: tags; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.tags VALUES (1, 'adrenaline');
INSERT INTO public.tags VALUES (5, 'good');
INSERT INTO public.tags VALUES (6, 'congratulations');
INSERT INTO public.tags VALUES (7, 'love');
INSERT INTO public.tags VALUES (8, 'feelings');
INSERT INTO public.tags VALUES (9, 'you');
INSERT INTO public.tags VALUES (10, '');
INSERT INTO public.tags VALUES (11, 'my');
INSERT INTO public.tags VALUES (12, 'adrenaline ');
INSERT INTO public.tags VALUES (13, 'DENIANEKRASAVA');
INSERT INTO public.tags VALUES (15, 'newtag');
INSERT INTO public.tags VALUES (16, 'hello');
INSERT INTO public.tags VALUES (17, 'wickedgame');
INSERT INTO public.tags VALUES (18, 'goup');
INSERT INTO public.tags VALUES (19, 'REALLY');
INSERT INTO public.tags VALUES (20, 'denia');
INSERT INTO public.tags VALUES (21, 'decision');
INSERT INTO public.tags VALUES (22, 'fight');
INSERT INTO public.tags VALUES (14, 'hooray');
INSERT INTO public.tags VALUES (23, 'atractor');


--
-- TOC entry 3359 (class 0 OID 16407)
-- Dependencies: 213
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO public.users VALUES ('admin', 3, true, 'admin');
INSERT INTO public.users VALUES ('denia', 5, true, 'denia');
INSERT INTO public.users VALUES ('fartt', 2, true, 'fartt');
INSERT INTO public.users VALUES ('Qwerty8=', 12, false, 'qwerty');


--
-- TOC entry 3367 (class 0 OID 16426)
-- Dependencies: 221
-- Data for Name: users_posts; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 3385 (class 0 OID 0)
-- Dependencies: 210
-- Name: Blog_Id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."Blog_Id_seq"', 51, true);


--
-- TOC entry 3386 (class 0 OID 0)
-- Dependencies: 212
-- Name: Comment_Id_comment_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."Comment_Id_comment_seq"', 10, true);


--
-- TOC entry 3387 (class 0 OID 0)
-- Dependencies: 214
-- Name: Users_Id_user_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public."Users_Id_user_seq"', 12, true);


--
-- TOC entry 3388 (class 0 OID 0)
-- Dependencies: 215
-- Name: comments_id_post_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.comments_id_post_seq', 1, true);


--
-- TOC entry 3389 (class 0 OID 0)
-- Dependencies: 217
-- Name: posts_tags_post_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.posts_tags_post_id_seq', 1, false);


--
-- TOC entry 3390 (class 0 OID 0)
-- Dependencies: 218
-- Name: posts_tags_tag_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.posts_tags_tag_id_seq', 1, false);


--
-- TOC entry 3391 (class 0 OID 0)
-- Dependencies: 220
-- Name: tags_tag_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.tags_tag_id_seq', 23, true);


--
-- TOC entry 3392 (class 0 OID 0)
-- Dependencies: 222
-- Name: users_posts_id_post_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.users_posts_id_post_seq', 1, false);


--
-- TOC entry 3393 (class 0 OID 0)
-- Dependencies: 223
-- Name: users_posts_id_user_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('public.users_posts_id_user_seq', 1, false);


--
-- TOC entry 3205 (class 2606 OID 16441)
-- Name: comments Comment_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.comments
    ADD CONSTRAINT "Comment_pkey" PRIMARY KEY (id_comment);


--
-- TOC entry 3203 (class 2606 OID 16443)
-- Name: posts Posts_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.posts
    ADD CONSTRAINT "Posts_pkey" PRIMARY KEY (id_post);


--
-- TOC entry 3207 (class 2606 OID 16445)
-- Name: users Users_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT "Users_pkey" PRIMARY KEY (id_user);


--
-- TOC entry 3209 (class 2606 OID 16447)
-- Name: users login; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT login UNIQUE (login);


--
-- TOC entry 3211 (class 2606 OID 16449)
-- Name: tags tags_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.tags
    ADD CONSTRAINT tags_pkey PRIMARY KEY (tag_id);


--
-- TOC entry 3214 (class 2606 OID 16450)
-- Name: users_posts post; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users_posts
    ADD CONSTRAINT post FOREIGN KEY (id_post) REFERENCES public.posts(id_post);


--
-- TOC entry 3212 (class 2606 OID 16455)
-- Name: posts_tags post; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.posts_tags
    ADD CONSTRAINT post FOREIGN KEY (post_id) REFERENCES public.posts(id_post);


--
-- TOC entry 3213 (class 2606 OID 16460)
-- Name: posts_tags tag; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.posts_tags
    ADD CONSTRAINT tag FOREIGN KEY (tag_id) REFERENCES public.tags(tag_id);


--
-- TOC entry 3215 (class 2606 OID 16465)
-- Name: users_posts user; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY public.users_posts
    ADD CONSTRAINT "user" FOREIGN KEY (id_user) REFERENCES public.users(id_user);


-- Completed on 2022-02-01 16:32:04

--
-- PostgreSQL database dump complete
--

COMMIT;