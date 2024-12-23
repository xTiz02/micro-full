CREATE EXTENSION IF NOT EXISTS dblink;

DO $$
BEGIN

    IF NOT EXISTS (
        SELECT FROM pg_database WHERE datname = 'orderdb'
    ) THEN

        PERFORM dblink_exec(
            'dbname=postgres user=postgres password=ImNotlol12msvc',
            'CREATE DATABASE orderdb'
        );
END IF;
END$$;