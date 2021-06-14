CREATE ROLE record_master WITH
  LOGIN
  NOSUPERUSER
  INHERIT
  NOCREATEDB
  NOCREATEROLE
  NOREPLICATION
  PASSWORD '11';

CREATE DATABASE records
    WITH
    OWNER = record_master
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

GRANT TEMPORARY, CONNECT ON DATABASE records TO PUBLIC;

GRANT ALL ON DATABASE records TO record_master;

CREATE TABLE public.sessions
(
    id serial,
    url character varying NOT NULL,
    filename character varying NOT NULL,
    CONSTRAINT sessions_pkey PRIMARY KEY (id),
    CONSTRAINT sessions_filename_key UNIQUE (filename)
);

ALTER TABLE public.sessions
    OWNER to record_master;

GRANT ALL ON TABLE public.sessions TO record_master;

CREATE TABLE public.unique_words
(
    session_id integer NOT NULL,
    token character varying NOT NULL,
    value integer NOT NULL,
    CONSTRAINT records_pkey PRIMARY KEY (token),
    CONSTRAINT records_session_id_fkey FOREIGN KEY (session_id)
        REFERENCES public.sessions (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT records_value_check CHECK (value > 0)
);

ALTER TABLE public.unique_words
    OWNER to record_master;

GRANT ALL ON TABLE public.unique_words TO record_master;