DELETE FROM week_menu
WHERE EXISTS (
    SELECT 1
    FROM week_menu newer
    WHERE newer.restaurant_id = week_menu.restaurant_id
      AND (newer.week_start > week_menu.week_start
        OR (newer.week_start = week_menu.week_start AND newer.id > week_menu.id))
);

ALTER TABLE week_menu
    ADD CONSTRAINT week_menu_restaurant_unique UNIQUE (restaurant_id);