SELECT
    t.primary_title,
    r.votes
FROM titles AS t
INNER JOIN ratings AS r USING (title_id)
INNER JOIN crew AS c USING (title_id)
INNER JOIN people AS p ON p.person_id = c.person_id
WHERE p.name LIKE '%Cruise%' AND born = 1962
ORDER BY r.votes DESC
LIMIT 10;