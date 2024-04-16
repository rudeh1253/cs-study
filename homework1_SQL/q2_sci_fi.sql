-- solution 1
SELECT primary_title, premiered, runtime_minutes || ' (mins)'
FROM titles
WHERE LOWER(genres) LIKE '%sci-fi%'
ORDER BY runtime_minutes DESC
LIMIT 10;

-- solution 2
SELECT primary_title, premiered, runtime_minutes || ' (mins)'
FROM (
    SELECT *, ROW_NUMBER() OVER (ORDER BY runtime_minutes DESC) AS order_by_runtime
    FROM titles
    WHERE LOWER(genres) LIKE '%sci-fi%'
)
WHERE order_by_runtime BETWEEN 1 AND 10
ORDER BY runtime_minutes DESC;