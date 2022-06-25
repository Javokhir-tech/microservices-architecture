CREATE DATABASE song;
CREATE DATABASE resource;

\c resource;
CREATE TABLE IF NOT EXISTS file (
    id serial PRIMARY KEY,
    name VARCHAR(255)
);

\c song;
CREATE TABLE IF NOT EXISTS metadata (
    id serial PRIMARY KEY,
    name VARCHAR(255),
    artist VARCHAR(255),
    album VARCHAR(255),
    leng VARCHAR(255),
    resourceId VARCHAR(255),
    yr bigint
);