CREATE TABLE users (
    id serial PRIMARY KEY,
    name VARCHAR (255) UNIQUE NOT NULL,
    password VARCHAR (255) UNIQUE NOT NULL
);

CREATE TABLE messages (
    id serial PRIMARY KEY,
    id_user INTEGER NOT NULL,
    message text NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (id_user) REFERENCES users (id)
);