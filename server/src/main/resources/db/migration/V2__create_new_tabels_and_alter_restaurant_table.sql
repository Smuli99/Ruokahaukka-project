-- Mutetaan ravintolan nimi kenttä uniikiksi
ALTER TABLE restaurant ADD CONSTRAINT restaurant_name_unique UNIQUE (name);

-- Viikkomenu: yhden ravintolan yksi viikko
CREATE TABLE week_menu (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    restaurant_id UUID NOT NULL REFERENCES restaurant(id) ON DELETE CASCADE,
    week_start    DATE NOT NULL,       -- viikon maanantai
    UNIQUE (restaurant_id, week_start)
);

-- Päivämenu: yhden viikon yksi päivä
CREATE TABLE day_menu (
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    week_menu_id UUID NOT NULL REFERENCES week_menu(id) ON DELETE CASCADE,
    date         DATE NOT NULL,
    weekday      VARCHAR(2) NOT NULL, -- ma, ti, ke...
    lunch_time   VARCHAR(20),         -- esim 12:00 - 16:30
    UNIQUE (week_menu_id, date)
);

-- Ruoka: riippumaton taulu, jokainen ruoka vain kerran
CREATE TABLE food (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name            TEXT NOT NULL,       -- siivottu nimi
    normalized_name TEXT NOT NULL UNIQUE -- pienet kirjaimet, tunniste suosikeille
);

-- Allergia / ruokavaliomerkintä: riippumaton taulu
CREATE TABLE allergy (
    id      UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    tag     VARCHAR(20) NOT NULL UNIQUE, -- tagi esim: L, G, M
    name_fi VARCHAR(100)                 -- oikea nimi esim: Laktoosi, Gluteeni
);

-- Menu-rivi: ryhmä ruokia joita tarjoillaan yhdessä
CREATE TABLE menu_item (
    id          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    day_menu_id UUID NOT NULL REFERENCES day_menu(id) ON DELETE CASCADE,
    price       TEXT,
    info        TEXT, -- esim. Lounas, From The Table, keitto
    sort_order  INT NOT NULL DEFAULT 0
);

CREATE INDEX idx_menu_item_day_menu_id ON menu_item(day_menu_id);

-- Yksittäinen ruoka, joka kuuluu tiettyyn annos ryhmään
CREATE TABLE menu_item_food (
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    menu_item_id UUID NOT NULL REFERENCES menu_item(id) ON DELETE CASCADE,
    food_id      UUID NOT NULL REFERENCES food(id),
    sort_order   INT NOT NULL DEFAULT 0,
    UNIQUE (menu_item_id, sort_order)
);

CREATE INDEX inx_menu_item_food_food_id ON menu_item_food(food_id);

-- Ruoka-allergia: liitos ruuan ja allergian välillä
CREATE TABLE menu_item_food_tag (
    menu_item_food_id UUID NOT NULL REFERENCES menu_item_food(id) ON DELETE CASCADE,
    allergy_id        UUID NOT NULL REFERENCES allergy(id) ON DELETE CASCADE,
    PRIMARY KEY (menu_item_food_id, allergy_id)
);

CREATE INDEX idx_menu_item_food_tag_allergy_id ON menu_item_food_tag(allergy_id);