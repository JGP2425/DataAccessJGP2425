-- DataBase Creation Script --
CREATE DATABASE "VTInstitute"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LOCALE_PROVIDER = 'libc'
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
	
-- Create Table Subjects Script --
CREATE TABLE public.subjects
(
    code integer NOT NULL,
    name character varying(50) NOT NULL,
    year integer NOT NULL,
    PRIMARY KEY (code)
);

ALTER TABLE IF EXISTS public.subjects
    OWNER to postgres;
	
-- Create CodeSequence Script --
CREATE SEQUENCE public."codeSequence"
    INCREMENT 1
    START 1;
	
ALTER TABLE public.subjects
ALTER COLUMN code SET DEFAULT nextval('public."codeSequence"')

ALTER SEQUENCE public."codeSequence"
    OWNED BY public.subjects.code;

ALTER SEQUENCE public."codeSequence"
    OWNER TO postgres;