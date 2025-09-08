CREATE TABLE IF NOT EXISTS templates
(
    id_template VARCHAR(255) NOT NULL,
    time        VARCHAR(255) NOT NULL,
    html        TEXT         NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    updated_at  TIMESTAMP    NULL DEFAULT NULL,
    CONSTRAINT pk_template PRIMARY KEY (id_template)
);