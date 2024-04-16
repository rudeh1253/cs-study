WITH house_of_dragons (ord, orig_title) AS (
    SELECT
        ROW_NUMBER() OVER (),
        title
    FROM (
        SELECT
        DISTINCT a.title AS title
        FROM akas AS a
        INNER JOIN titles AS t ON t.title_id = a.title_id
        WHERE t.primary_title = 'House of the Dragon'
        ORDER BY title
    )
), single_title (title, counter) AS (
    SELECT (
        SELECT orig_title
        from house_of_dragons
        LIMIT 1
    ), 1
    UNION ALL
    SELECT s.title || ',' || h.orig_title, counter + 1
    FROM single_title AS s
    INNER JOIN house_of_dragons AS h ON h.ord = s.counter
    WHERE counter <= (
        SELECT COUNT (*)
        FROM house_of_dragons
    )
)
SELECT title FROM single_title ORDER BY title DESC LIMIT 1;