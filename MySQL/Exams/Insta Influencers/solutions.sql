#INSERT 

INSERT INTO likes(article_id,comment_id,user_id)
SELECT IF(u.id % 2 = 0,char_length(u.username),null),
		IF(u.id % 2 = 1,char_length(u.email),null),
u.id
FROM users AS u
WHERE u.id BETWEEN 16 AND 20;


#UPDATE
UPDATE comments AS c
SET comment = (
	CASE
		WHEN c.id % 2 = 0 THEN 'Very good article.'
		WHEN c.id % 3 = 0 THEN 'This is interesting.'
		WHEN c.id % 5 = 0 THEN 'I definitely will read the article again.'
		WHEN c.id % 7 = 0 THEN 'The universe is such an amazing thing.'
        ELSE c.comment
    END
)
WHERE c.id BETWEEN 1 AND 15;

#DELETE
DELETE FROM articles
WHERE category_id IS NULL;

#5

SELECT nt.title, nt.summary FROM
(SELECT a.id, a.title,CONCAT(LEFT(a.content, 20),'...') AS summary
FROM articles AS a
ORDER BY char_length(content) DESC
LIMIT 3) AS nt
ORDER BY nt.id;

#6
SELECT a.id, a.title
FROM articles AS a
JOIN users_articles AS ua 
ON a.id = ua.article_id
JOIN users AS u
ON u.id = ua.user_id
WHERE ua.article_id = ua.user_id
ORDER BY ua.article_id;

#7
SELECT c.category, count(DISTINCT a.id) AS article_count 
, count(l.id) AS likes_count
FROM categories AS c
LEFT JOIN articles AS a
ON c.id = a.category_id
LEFT JOIN likes AS l
ON a.id = l.article_id
GROUP BY c.category
ORDER BY likes_count DESC, article_count DESC, c.id;
