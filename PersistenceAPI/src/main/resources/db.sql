CREATE DATABASE IF NOT EXISTS Strow;

DROP TABLE IF EXISTS ctf_stats;
CREATE TABLE ctf_stats
(
    uuid VARCHAR(36),

    PRIMARY KEY (uuid)
);

DROP TABLE IF EXISTS faction_claims;
CREATE TABLE faction_claims
(
    faction_uuid VARCHAR(255),
    location_id  INT,

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
VALUES (1, 0),
       (2, 0),
       (3, 1);

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

DROP TABLE IF EXISTS faction_roles;
CREATE TABLE faction_roles
(
    id   SMALLINT,
    name VARCHAR(255),

    PRIMARY KEY (id)
);

INSERT INTO faction_roles(id, name)
VALUES (1, 'member'),
       (2, 'officer'),
       (3, 'leader');

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
    description TEXT,
    leader_uuid VARCHAR(36),
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

    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS proxy_permissions;
CREATE TABLE proxy_permissions
(
    role_id    SMALLINT,
    proxy_join BOOLEAN,

    PRIMARY KEY (role_id)
);

/*INSERT INTO proxy_permissions
VALUES ();*/

DROP TABLE IF EXISTS players;
CREATE TABLE players
(
    uuid    VARCHAR(36),
    name    VARCHAR(255),
    role_id SMALLINT,
    coins   INT,

    PRIMARY KEY (uuid)
);

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles`
(
    id   SMALLINT,
    name VARCHAR(255),

    PRIMARY KEY (id)
);

INSERT INTO `roles`(id, name)
VALUES (1, 'player'),
       (2, 'vip'),
       (3, 'vip_plus'),
       (4, 'builder'),
       (5, 'developer'),
       (6, 'moderator'),
       (7, 'administrator');

DROP TABLE IF EXISTS totem_stats;
CREATE TABLE totem_stats
(
    uuid VARCHAR(36),

    PRIMARY KEY (uuid)
);