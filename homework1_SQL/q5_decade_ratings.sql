SELECT
    t.premiered - t.premiered % 10 AS DECADE,
    ROUND(AVG(r.rating), 2) AS AVG_RATING,
    MAX(r.rating) AS TOP_RATING,
    MIN(r.rating) AS MIN_RATING,
    COUNT(*) AS NUM_RELEASES
FROM ratings AS r
INNER JOIN titles AS t USING(title_id)
WHERE t.premiered IS NOT NULL
GROUP BY DECADE
ORDER BY AVG_RATING DESC, DECADE ASC;