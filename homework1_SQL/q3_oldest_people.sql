SELECT name AS NAME,
    CASE WHEN died IS NULL
    THEN DATE('now') - born
    ELSE died - born
    END AS AGE
FROM people
WHERE born >= 1900
ORDER BY AGE DESC, name ASC
LIMIT 20;