CREATE SCHEMA IF NOT EXISTS waffleschema;

CREATE SEQUENCE IF NOT EXISTS waffleschema.kullanici_id_seq START WITH 1000 INCREMENT BY 1;

DO $$
BEGIN
    IF EXISTS (
        SELECT 1 FROM information_schema.tables
        WHERE table_schema = 'waffleschema' AND table_name = 'kullanicilar'
    ) THEN
        PERFORM setval(
            'waffleschema.kullanici_id_seq',
            GREATEST(999, COALESCE((SELECT MAX(id) FROM waffleschema.kullanicilar), 0)),
            true
        );
    END IF;
END $$;
