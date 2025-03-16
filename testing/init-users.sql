CREATE OR REPLACE FUNCTION init_db_for_load_test(user_count INT) RETURNS VOID AS $$
DECLARE
    i INT;
BEGIN
    EXECUTE('DELETE FROM notes_files');
    EXECUTE('DELETE FROM notes');
    EXECUTE('DELETE FROM users');

    FOR i IN 1..user_count LOOP
            INSERT INTO users(id, name) VALUES (i, 'user_' || i);
        END LOOP;
END;
$$ LANGUAGE plpgsql;