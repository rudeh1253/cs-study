SELECT DISTINCT p.name AS name
FROM titles AS t
INNER JOIN crew AS c ON c.title_id = t.title_id
INNER JOIN people AS p ON p.person_id = c.person_id
WHERE t.title_id IN (
    SELECT t.title_id
    FROM titles AS t
    INNER JOIN crew AS c ON c.title_id = t.title_id
    INNER JOIN people AS p ON p.person_id = c.person_id
    WHERE p.name = 'Nicole Kidman' AND p.born = 1967
)
ORDER BY name ASC;