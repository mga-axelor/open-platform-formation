DO
$$
DECLARE
  tableName varchar;
BEGIN
    FOR tableName IN 
      SELECT table_name
      FROM information_schema.tables
      WHERE table_type = 'BASE TABLE'
      AND (
        table_name LIKE ('base_%')
        OR table_name LIKE ('sale_%')
        OR table_name LIKE ('meta_%')
      )
    LOOP
        EXECUTE 'DROP TABLE IF EXISTS ' || tableName || ' CASCADE ';
    END LOOP;
END
$$;