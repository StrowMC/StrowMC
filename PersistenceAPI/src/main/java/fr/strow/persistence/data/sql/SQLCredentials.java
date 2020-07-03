/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 20:48
 */

package fr.strow.persistence.data.sql;

public class SQLCredentials {

    private final String host;
    private final String username;
    private final String password;
    private final int port;
    private final String database;

    public SQLCredentials(String host, String username, String password, int port, String database) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.port = port;
        this.database = database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String toURL() {
        return "jdbc:mysql://" + host + ":" + port + "/" + database;
    }
}
