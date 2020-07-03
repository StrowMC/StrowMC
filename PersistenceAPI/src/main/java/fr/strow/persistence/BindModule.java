/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 22/06/2020 20:48
 */

package fr.strow.persistence;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import fr.strow.persistence.dao.nosql.inventories.InventoryFolder;
import fr.strow.persistence.data.redis.RedisAccess;
import fr.strow.persistence.data.redis.RedisCredentials;
import fr.strow.persistence.data.sql.SQLAccess;
import fr.strow.persistence.data.sql.SQLCredentials;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class BindModule extends AbstractModule {

    private final Path dataFolder;

    @Provides
    @InventoryFolder
    public Path getInventoryFolder() { return dataFolder.resolve("storage/inventories");}

    public BindModule(Path dataFolder) {
        this.dataFolder = dataFolder;
    }

    @Override
    protected void configure() {
        try {
            InputStream in = Files.newInputStream(dataFolder.resolve("config.yml"));

            Yaml yaml = new Yaml();
            Map<String, ?> config = yaml.load(in);

            String sqlHost = (String) config.get("sql.host");
            String sqlUsername = (String) config.get("sql.username");
            String sqlPassword = (String) config.get("sql.password");
            int sqlPort = (Integer) config.get("sql.port");
            String sqlDatabase = (String) config.get("sql.database");

            SQLCredentials sqlCredentials = new SQLCredentials(
                    sqlHost,
                    sqlUsername,
                    sqlPassword,
                    sqlPort,
                    sqlDatabase
            );

            SQLAccess sqlAccess = new SQLAccess(sqlCredentials);

            bind(SQLAccess.class).toInstance(sqlAccess);

            String redisHost = (String) config.get("redis.host");
            int redisPort = (Integer) config.get("redis.port");
            int redisTimeout = (Integer) config.get("redis.timeout");
            String redisPassword = (String) config.get("redis.password");

            RedisCredentials redisCredentials = new RedisCredentials(
                    redisHost,
                    redisPort,
                    redisTimeout,
                    redisPassword
            );

            RedisAccess redisAccess = new RedisAccess(redisCredentials);

            bind(RedisAccess.class).toInstance(redisAccess);
        } catch (IOException e) {
            e.printStackTrace();
        }

        bind(Gson.class).toInstance(new Gson());
    }
}
