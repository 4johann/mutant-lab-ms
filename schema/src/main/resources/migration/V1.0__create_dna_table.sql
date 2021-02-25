CREATE TABLE public.dna
(
    id                      SERIAL PRIMARY KEY NOT NULL,
    dna                     VARCHAR(1000) UNIQUE NOT NULL,
    is_mutant               BOOLEAN DEFAULT FALSE,
    updated_at              TIMESTAMP NOT NULL,
    created_at              TIMESTAMP NOT NULL
);