CREATE TABLE IF NOT EXISTS list_entity (
    list_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS item_entity (
    item_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL default 0,
    amount INT NOT NULL default 1,
    category VARCHAR(255),
    list_id BIGINT,
    FOREIGN KEY (list_id) REFERENCES list_entity (list_id),
    weight BIGINT NOT NULL DEFAULT 0,
    time_created TIMESTAMP WITHOUT TIME ZONE
    );