--
-- PostgreSQL database dump
--

-- Dumped from database version 14.10 (Homebrew)
-- Dumped by pg_dump version 16.0

-- Started on 2023-12-22 20:18:58 CET

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
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -
--

-- *not* creating schema, since initdb creates it


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 210 (class 1259 OID 16386)
-- Name: argument_details; Type: TABLE; Schema: public
--

CREATE TABLE public.argument_details (
    id bigint NOT NULL,
    "position" integer NOT NULL,
    text character varying(255),
    argument_id bigint NOT NULL
);


--
-- TOC entry 209 (class 1259 OID 16385)
-- Name: argument_details_id_seq; Type: SEQUENCE; Schema: public
--

CREATE SEQUENCE public.argument_details_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3705 (class 0 OID 0)
-- Dependencies: 209
-- Name: argument_details_id_seq; Type: SEQUENCE OWNED BY; Schema: public
--

ALTER SEQUENCE public.argument_details_id_seq OWNED BY public.argument_details.id;


--
-- TOC entry 212 (class 1259 OID 16393)
-- Name: arguments; Type: TABLE; Schema: public
--

CREATE TABLE public.arguments (
    id bigint NOT NULL,
    creation_timestamp timestamp(6) without time zone,
    title character varying(100),
    author_id integer NOT NULL,
    discussion_id bigint NOT NULL
);


--
-- TOC entry 211 (class 1259 OID 16392)
-- Name: arguments_id_seq; Type: SEQUENCE; Schema: public
--

CREATE SEQUENCE public.arguments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3706 (class 0 OID 0)
-- Dependencies: 211
-- Name: arguments_id_seq; Type: SEQUENCE OWNED BY; Schema: public
--

ALTER SEQUENCE public.arguments_id_seq OWNED BY public.arguments.id;


--
-- TOC entry 214 (class 1259 OID 16400)
-- Name: discussions; Type: TABLE; Schema: public
--

CREATE TABLE public.discussions (
    id bigint NOT NULL,
    creation_timestamp timestamp(6) without time zone,
    description character varying(1000),
    status character varying(255) NOT NULL,
    time_limit timestamp(6) without time zone NOT NULL,
    topic character varying(100),
    creator_id integer NOT NULL,
    space_id bigint NOT NULL
);


--
-- TOC entry 213 (class 1259 OID 16399)
-- Name: discussions_id_seq; Type: SEQUENCE; Schema: public
--

CREATE SEQUENCE public.discussions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3707 (class 0 OID 0)
-- Dependencies: 213
-- Name: discussions_id_seq; Type: SEQUENCE OWNED BY; Schema: public
--

ALTER SEQUENCE public.discussions_id_seq OWNED BY public.discussions.id;

--
-- TOC entry 215 (class 1259 OID 16408)
-- Name: space_likes; Type: TABLE; Schema: public
--

CREATE TABLE public.space_likes (
    space_id bigint NOT NULL,
    user_id integer NOT NULL
);


--
-- TOC entry 217 (class 1259 OID 16414)
-- Name: spaces; Type: TABLE; Schema: public
--

CREATE TABLE public.spaces (
    id bigint NOT NULL,
    description character varying(200),
    name character varying(50)
);


--
-- TOC entry 216 (class 1259 OID 16413)
-- Name: spaces_id_seq; Type: SEQUENCE; Schema: public
--

CREATE SEQUENCE public.spaces_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3708 (class 0 OID 0)
-- Dependencies: 216
-- Name: spaces_id_seq; Type: SEQUENCE OWNED BY; Schema: public
--

ALTER SEQUENCE public.spaces_id_seq OWNED BY public.spaces.id;


--
-- TOC entry 219 (class 1259 OID 16421)
-- Name: suggestions; Type: TABLE; Schema: public
--

CREATE TABLE public.suggestions (
    id bigint NOT NULL,
    argument_version integer,
    comment character varying(255),
    created_timestamp timestamp(6) without time zone,
    "position" integer,
    section character varying(255),
    status character varying(32) DEFAULT 'ACTIVE'::character varying NOT NULL,
    text character varying(255),
    type character varying(255),
    argument_id bigint NOT NULL,
    user_id integer NOT NULL
);


--
-- TOC entry 218 (class 1259 OID 16420)
-- Name: suggestions_id_seq; Type: SEQUENCE; Schema: public
--

CREATE SEQUENCE public.suggestions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3709 (class 0 OID 0)
-- Dependencies: 218
-- Name: suggestions_id_seq; Type: SEQUENCE OWNED BY; Schema: public
--

ALTER SEQUENCE public.suggestions_id_seq OWNED BY public.suggestions.id;


--
-- TOC entry 220 (class 1259 OID 16430)
-- Name: users; Type: TABLE; Schema: public
--

CREATE TABLE public.users (
    id integer NOT NULL,
    biography character varying(255),
    email character varying(255),
    interests character varying(255),
    is_bot boolean DEFAULT false,
    password character varying(255),
    profile_picture character varying(255),
    receive_notifications boolean DEFAULT false,
    user_name character varying(255)
);


--
-- TOC entry 222 (class 1259 OID 16440)
-- Name: users_discussions; Type: TABLE; Schema: public
--

CREATE TABLE public.users_discussions (
    id bigint NOT NULL,
    side character varying(255) NOT NULL,
    discussion_id bigint NOT NULL,
    user_id integer NOT NULL
);


--
-- TOC entry 221 (class 1259 OID 16439)
-- Name: users_discussions_id_seq; Type: SEQUENCE; Schema: public
--

CREATE SEQUENCE public.users_discussions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3710 (class 0 OID 0)
-- Dependencies: 221
-- Name: users_discussions_id_seq; Type: SEQUENCE OWNED BY; Schema: public
--

ALTER SEQUENCE public.users_discussions_id_seq OWNED BY public.users_discussions.id;


--
-- TOC entry 227 (class 1259 OID 16471)
-- Name: users_seq; Type: SEQUENCE; Schema: public
--

CREATE SEQUENCE public.users_seq
    START WITH 1
    INCREMENT BY 50
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 224 (class 1259 OID 16447)
-- Name: users_spaces; Type: TABLE; Schema: public
--

CREATE TABLE public.users_spaces (
    id bigint NOT NULL,
    time_joined timestamp(6) without time zone,
    space_id bigint NOT NULL,
    user_id integer NOT NULL
);


--
-- TOC entry 223 (class 1259 OID 16446)
-- Name: users_spaces_id_seq; Type: SEQUENCE; Schema: public
--

CREATE SEQUENCE public.users_spaces_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3711 (class 0 OID 0)
-- Dependencies: 223
-- Name: users_spaces_id_seq; Type: SEQUENCE OWNED BY; Schema: public
--

ALTER SEQUENCE public.users_spaces_id_seq OWNED BY public.users_spaces.id;


--
-- TOC entry 226 (class 1259 OID 16454)
-- Name: votes; Type: TABLE; Schema: public
--

CREATE TABLE public.votes (
    id bigint NOT NULL,
    entity_id bigint NOT NULL,
    entity_type character varying(255) NOT NULL,
    vote_type smallint NOT NULL,
    user_id integer NOT NULL
);


--
-- TOC entry 225 (class 1259 OID 16453)
-- Name: votes_id_seq; Type: SEQUENCE; Schema: public
--

CREATE SEQUENCE public.votes_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 3712 (class 0 OID 0)
-- Dependencies: 225
-- Name: votes_id_seq; Type: SEQUENCE OWNED BY; Schema: public
--

ALTER SEQUENCE public.votes_id_seq OWNED BY public.votes.id;


--
-- TOC entry 3500 (class 2604 OID 16389)
-- Name: argument_details id; Type: DEFAULT; Schema: public
--

ALTER TABLE ONLY public.argument_details ALTER COLUMN id SET DEFAULT nextval('public.argument_details_id_seq'::regclass);


--
-- TOC entry 3501 (class 2604 OID 16396)
-- Name: arguments id; Type: DEFAULT; Schema: public
--

ALTER TABLE ONLY public.arguments ALTER COLUMN id SET DEFAULT nextval('public.arguments_id_seq'::regclass);


--
-- TOC entry 3502 (class 2604 OID 16403)
-- Name: discussions id; Type: DEFAULT; Schema: public
--

ALTER TABLE ONLY public.discussions ALTER COLUMN id SET DEFAULT nextval('public.discussions_id_seq'::regclass);


--
-- TOC entry 3503 (class 2604 OID 16417)
-- Name: spaces id; Type: DEFAULT; Schema: public
--

ALTER TABLE ONLY public.spaces ALTER COLUMN id SET DEFAULT nextval('public.spaces_id_seq'::regclass);


--
-- TOC entry 3504 (class 2604 OID 16424)
-- Name: suggestions id; Type: DEFAULT; Schema: public
--

ALTER TABLE ONLY public.suggestions ALTER COLUMN id SET DEFAULT nextval('public.suggestions_id_seq'::regclass);


--
-- TOC entry 3508 (class 2604 OID 16443)
-- Name: users_discussions id; Type: DEFAULT; Schema: public
--

ALTER TABLE ONLY public.users_discussions ALTER COLUMN id SET DEFAULT nextval('public.users_discussions_id_seq'::regclass);


--
-- TOC entry 3509 (class 2604 OID 16450)
-- Name: users_spaces id; Type: DEFAULT; Schema: public
--

ALTER TABLE ONLY public.users_spaces ALTER COLUMN id SET DEFAULT nextval('public.users_spaces_id_seq'::regclass);


--
-- TOC entry 3510 (class 2604 OID 16457)
-- Name: votes id; Type: DEFAULT; Schema: public
--

ALTER TABLE ONLY public.votes ALTER COLUMN id SET DEFAULT nextval('public.votes_id_seq'::regclass);


--
-- TOC entry 3513 (class 2606 OID 16391)
-- Name: argument_details argument_details_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.argument_details
    ADD CONSTRAINT argument_details_pkey PRIMARY KEY (id);


--
-- TOC entry 3515 (class 2606 OID 16398)
-- Name: arguments arguments_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.arguments
    ADD CONSTRAINT arguments_pkey PRIMARY KEY (id);


--
-- TOC entry 3517 (class 2606 OID 16407)
-- Name: discussions discussions_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.discussions
    ADD CONSTRAINT discussions_pkey PRIMARY KEY (id);

--
-- TOC entry 3519 (class 2606 OID 16412)
-- Name: space_likes space_likes_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.space_likes
    ADD CONSTRAINT space_likes_pkey PRIMARY KEY (space_id, user_id);


--
-- TOC entry 3521 (class 2606 OID 16419)
-- Name: spaces spaces_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.spaces
    ADD CONSTRAINT spaces_pkey PRIMARY KEY (id);


--
-- TOC entry 3524 (class 2606 OID 16429)
-- Name: suggestions suggestions_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.suggestions
    ADD CONSTRAINT suggestions_pkey PRIMARY KEY (id);


--
-- TOC entry 3532 (class 2606 OID 16466)
-- Name: users_discussions uk4hljpeprhqnb3cif8oltmeoc1; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.users_discussions
    ADD CONSTRAINT uk4hljpeprhqnb3cif8oltmeoc1 UNIQUE (user_id, discussion_id);


--
-- TOC entry 3536 (class 2606 OID 16468)
-- Name: users_spaces uk7yykxfu4l7gxbagjt87r7fd70; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.users_spaces
    ADD CONSTRAINT uk7yykxfu4l7gxbagjt87r7fd70 UNIQUE (user_id, space_id);


--
-- TOC entry 3526 (class 2606 OID 16462)
-- Name: users uk_6dotkott2kjsp8vw4d0m25fb7; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email);


--
-- TOC entry 3528 (class 2606 OID 16464)
-- Name: users uk_k8d0f2n7n88w1a16yhua64onx; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT uk_k8d0f2n7n88w1a16yhua64onx UNIQUE (user_name);


--
-- TOC entry 3540 (class 2606 OID 16470)
-- Name: votes uks5fbivowb8q4t1ajdh2i08ie0; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.votes
    ADD CONSTRAINT uks5fbivowb8q4t1ajdh2i08ie0 UNIQUE (user_id, entity_id, entity_type);


--
-- TOC entry 3534 (class 2606 OID 16445)
-- Name: users_discussions users_discussions_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.users_discussions
    ADD CONSTRAINT users_discussions_pkey PRIMARY KEY (id);


--
-- TOC entry 3530 (class 2606 OID 16438)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- TOC entry 3538 (class 2606 OID 16452)
-- Name: users_spaces users_spaces_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.users_spaces
    ADD CONSTRAINT users_spaces_pkey PRIMARY KEY (id);


--
-- TOC entry 3542 (class 2606 OID 16459)
-- Name: votes votes_pkey; Type: CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.votes
    ADD CONSTRAINT votes_pkey PRIMARY KEY (id);


--
-- TOC entry 3522 (class 1259 OID 16460)
-- Name: idxlxs9t8034o60pcrmq1kh4kygw; Type: INDEX; Schema: public
--

CREATE INDEX idxlxs9t8034o60pcrmq1kh4kygw ON public.suggestions USING btree (type, status);


--
-- TOC entry 3553 (class 2606 OID 16512)
-- Name: suggestions fk7yns86oadd04hrdcstg7mhirm; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.suggestions
    ADD CONSTRAINT fk7yns86oadd04hrdcstg7mhirm FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3554 (class 2606 OID 16507)
-- Name: suggestions fk82xdvxht5359hfhq6u2libd2y; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.suggestions
    ADD CONSTRAINT fk82xdvxht5359hfhq6u2libd2y FOREIGN KEY (argument_id) REFERENCES public.arguments(id);


--
-- TOC entry 3557 (class 2606 OID 16527)
-- Name: users_spaces fk8hjasr0kcig71ujpmfxte9atc; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.users_spaces
    ADD CONSTRAINT fk8hjasr0kcig71ujpmfxte9atc FOREIGN KEY (space_id) REFERENCES public.spaces(id);


--
-- TOC entry 3555 (class 2606 OID 16522)
-- Name: users_discussions fkg53640svr2vgpucxiip5uqrdd; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.users_discussions
    ADD CONSTRAINT fkg53640svr2vgpucxiip5uqrdd FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3558 (class 2606 OID 16532)
-- Name: users_spaces fklbqm6dliaje58i9yplkqiq4as; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.users_spaces
    ADD CONSTRAINT fklbqm6dliaje58i9yplkqiq4as FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3549 (class 2606 OID 16487)
-- Name: discussions fklc3nk9vv642p8ry6lvnfyji8f; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.discussions
    ADD CONSTRAINT fklc3nk9vv642p8ry6lvnfyji8f FOREIGN KEY (creator_id) REFERENCES public.users(id);


--
-- TOC entry 3559 (class 2606 OID 16537)
-- Name: votes fkli4uj3ic2vypf5pialchj925e; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.votes
    ADD CONSTRAINT fkli4uj3ic2vypf5pialchj925e FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3546 (class 2606 OID 16472)
-- Name: argument_details fklun08aioso15nsicbra5x7jdq; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.argument_details
    ADD CONSTRAINT fklun08aioso15nsicbra5x7jdq FOREIGN KEY (argument_id) REFERENCES public.arguments(id);


--
-- TOC entry 3551 (class 2606 OID 16497)
-- Name: space_likes fkm1mfxxgdbbu0c77w38mjevf8h; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.space_likes
    ADD CONSTRAINT fkm1mfxxgdbbu0c77w38mjevf8h FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- TOC entry 3547 (class 2606 OID 16482)
-- Name: arguments fkmgmhtan1bx50kequpx2mppf9x; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.arguments
    ADD CONSTRAINT fkmgmhtan1bx50kequpx2mppf9x FOREIGN KEY (discussion_id) REFERENCES public.discussions(id);


--
-- TOC entry 3552 (class 2606 OID 16502)
-- Name: space_likes fknjy3clie2he9347hvijojw45b; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.space_likes
    ADD CONSTRAINT fknjy3clie2he9347hvijojw45b FOREIGN KEY (space_id) REFERENCES public.spaces(id);


--
-- TOC entry 3550 (class 2606 OID 16492)
-- Name: discussions fkoiur6reg9tixr3yd0syy12njd; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.discussions
    ADD CONSTRAINT fkoiur6reg9tixr3yd0syy12njd FOREIGN KEY (space_id) REFERENCES public.spaces(id);


--
-- TOC entry 3556 (class 2606 OID 16517)
-- Name: users_discussions fkosg5ka1o59jut20alopd0skyo; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.users_discussions
    ADD CONSTRAINT fkosg5ka1o59jut20alopd0skyo FOREIGN KEY (discussion_id) REFERENCES public.discussions(id);


--
-- TOC entry 3548 (class 2606 OID 16477)
-- Name: arguments fkr63y7eybrkx273dcd1my5sp1x; Type: FK CONSTRAINT; Schema: public
--

ALTER TABLE ONLY public.arguments
    ADD CONSTRAINT fkr63y7eybrkx273dcd1my5sp1x FOREIGN KEY (author_id) REFERENCES public.users(id);


--
-- TOC entry 3704 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: ACL; Schema: -
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2023-12-22 20:18:58 CET

--
-- PostgreSQL database dump complete
--

