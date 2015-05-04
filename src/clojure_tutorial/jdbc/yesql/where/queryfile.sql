--name: fruit-by-name
-- Counts the fruit in a given name.
select count(*) as count
from fruit
where name = :name

--name: fruit-count
--Count all fruit
select count(*) as count
from fruit

-- name: find-fruits
-- Find the fruits with the given ID(s).
SELECT *
FROM fruit
WHERE id IN (:id)
AND cost > :min_cost

-- name: save-fruit!
UPDATE fruit
SET cost = :cost
WHERE id = :id

-- name: create-fruit<!
INSERT INTO fruit ( name ) VALUES ( :name )

-- name: fruit-cost
SELECT *
FROM fruit
WHERE (
  name = ?
  OR
  name = ?
)
AND cost > :min_cost