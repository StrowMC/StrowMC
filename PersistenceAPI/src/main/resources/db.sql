CREATE DATABASE IF NOT EXISTS Strow;

DROP TABLE IF EXISTS bans;
CREATE TABLE bans
(
    uuid               VARCHAR(36),
    reason             VARCHAR(255),
    sanctioner_uuid    VARCHAR(36),
    starting_timestamp TIMESTAMP NULL,
    ending_timestamp   TIMESTAMP NULL,

    PRIMARY KEY (uuid)
);

DROP TABLE IF EXISTS ctf_stats;
CREATE TABLE ctf_stats
(
    uuid VARCHAR(36),

    PRIMARY KEY (uuid)
);

DROP TABLE IF EXISTS faction_claims;
CREATE TABLE faction_claims
(
    faction_uuid VARCHAR(36),
    world        VARCHAR(255),
    x            INTEGER,
    z            INTEGER,

    PRIMARY KEY (faction_uuid)
);

DROP TABLE IF EXISTS faction_chests;
CREATE TABLE faction_chests
(
    faction_uuid VARCHAR(36),
    content      TEXT,

    PRIMARY KEY (faction_uuid)
);

DROP TABLE IF EXISTS faction_homes;
CREATE TABLE faction_homes
(
    faction_uuid VARCHAR(255),
    location_id  INT,

    PRIMARY KEY (faction_uuid)
);

DROP TABLE IF EXISTS faction_permissions;
CREATE TABLE faction_permissions
(
    role_id         SMALLINT,
    faction_disband BOOLEAN,

    PRIMARY KEY (role_id)
);

INSERT INTO faction_permissions(role_id, faction_disband)
VALUES (0, 0),
       (1, 0),
       (2, 1);

DROP TABLE IF EXISTS faction_profiles;
CREATE TABLE faction_profiles
(
    uuid         VARCHAR(36),
    faction_uuid VARCHAR(36),
    role_id      SMALLINT,
    power        INT,
    claimer      BOOLEAN,

    PRIMARY KEY (uuid)
);

DROP TABLE IF EXISTS faction_warps;
CREATE TABLE faction_warps
(
    faction_uuid VARCHAR(36),
    name         VARCHAR(255),
    location_id  INT
);

DROP TABLE IF EXISTS factions;
CREATE TABLE factions
(
    uuid        VARCHAR(36),
    name        VARCHAR(255) UNIQUE,
    prefix      VARCHAR(3) UNIQUE,
    leader_uuid VARCHAR(36),
    description TEXT,
    points      INT,

    PRIMARY KEY (uuid)
);

DROP TABLE IF EXISTS fishing_stats;
CREATE TABLE fishing_stats
(
    uuid VARCHAR(36),

    PRIMARY KEY (uuid)
);

DROP TABLE IF EXISTS koth_stats;
CREATE TABLE koth_stats
(
    uuid VARCHAR(36),

    PRIMARY KEY (uuid)
);

DROP TABLE IF EXISTS locations;
CREATE TABLE locations
(
    id    INT AUTO_INCREMENT,
    world VARCHAR(255),
    x     DOUBLE,
    y     DOUBLE,
    z     DOUBLE,
    yaw   DOUBLE,
    pitch DOUBLE,

    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS mutes;
CREATE TABLE mutes
(
    uuid               VARCHAR(36),
    reason             VARCHAR(255),
    sanctioner_uuid    VARCHAR(36),
    starting_timestamp TIMESTAMP NULL,
    ending_timestamp   TIMESTAMP NULL,

    PRIMARY KEY (uuid)
);

DROP TABLE IF EXISTS proxy_permissions;
CREATE TABLE proxy_permissions
(
    role_id    SMALLINT,
    proxy_join BOOLEAN,

    PRIMARY KEY (role_id)
);

INSERT INTO proxy_permissions
VALUES (0, 1),
       (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 1),
       (6, 1);

DROP TABLE IF EXISTS players;
CREATE TABLE players
(
    uuid    VARCHAR(36),
    pseudo  VARCHAR(255) UNIQUE,
    role_id SMALLINT,
    coins   INT,

    PRIMARY KEY (uuid)
);

DROP TABLE IF EXISTS quest_progresses;
CREATE TABLE quest_progresses
(
    uuid     VARCHAR(36),
    id       INT,
    progress INT,

    PRIMARY KEY (uuid)
);

DROP TABLE IF EXISTS totem_stats;
CREATE TABLE totem_stats
(
    uuid VARCHAR(36),

    PRIMARY KEY (uuid)
);