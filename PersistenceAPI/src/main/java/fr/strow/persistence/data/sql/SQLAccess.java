/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 20:48
 */

package fr.strow.persistence.data.sql;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class SQLAccess {

    private final SQLCredentials credentials;
    private HikariDataSource dataSource;

    public SQLAccess(SQLCredentials credentials) {
        this.credentials = credentials;
    }

    public void initPool() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(credentials.toURL());
        config.setUsername(credentials.getUsername());
        config.setPassword(credentials.getPassword());

        config.setMaximumPoolSize(16);

        dataSource = new HikariDataSource(config);
    }

    public void closePool() {
        dataSource.close();
    }

    public Connection getConnection() throws SQLException {
        if (isClosed()) {
            initPool();
        }

        return dataSource.getConnection();
    }

    private boolean isClosed() {
        return dataSource == null || !dataSource.isRunning();
    }
}
