-- Counts the fruit in a given name.
select count(*) as count
from fruit
where name = :name