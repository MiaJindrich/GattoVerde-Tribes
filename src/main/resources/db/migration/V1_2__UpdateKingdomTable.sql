alter table kingdoms ADD kingdom_food_production int NOT NULL;
alter table kingdoms ADD kingdom_gold_production int NOT NULL;
alter table kingdoms ADD max_storage int NOT NULL;

INSERT INTO kingdoms (`id`, `name`, `kingdom_food_production`, `kingdom_gold_production`,
                      `max_storage`)
VALUES ('1', 'DummyKingdom', '0', '0', '0');

INSERT INTO buildings (`id`, `type`, `level`, `kingdom_id`)
VALUES ('1', 'Farm', '1', '1'),
       ('2', 'Mine', '1', '1'),
       ('3', 'TownHall', '1', '1');

INSERT INTO resources (`id`, `type`, `amount`, `kingdom_id`)
VALUES ('1', 'Food', '0', '1');
INSERT INTO resources (`id`, `type`, `amount`, `kingdom_id`)
VALUES ('2', 'Gold', '0', '1');