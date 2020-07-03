/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 20:48
 */

package fr.strow.persistence.data.redis;

public class RedisCredentials {

    private final String host;
    private final int port;
    private final int timeout;
    private final String password;

    public RedisCredentials(String host, int port, int timeout, String password) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public int getTimeout() {
        return timeout;
    }

    public String getPassword() {
        return password;
    }
}
