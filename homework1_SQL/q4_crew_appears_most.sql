SELECT p.name AS NAME, COUNT(c.title_id) AS NUM_APPEARANCES
FROM crew AS c
INNER JOIN people AS p ON p.person_id = c.person_id
GROUP BY p.person_id, p.name
ORDER BY NUM_APPEARANCES DESC
LIMIT 20;