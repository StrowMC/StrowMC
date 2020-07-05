/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:33
 */

package fr.strow.core;

import com.google.gson.Gson;
import com.google.inject.AbstractModule;
import fr.strow.api.StrowPlugin;
import fr.strow.api.commands.CommandsManager;
import fr.strow.api.game.factions.FactionManager;
import fr.strow.api.game.players.PlayerManager;
import fr.strow.api.modules.ModulesHandler;
import fr.strow.api.properties.PropertiesHandler;
import fr.strow.api.utils.Scheduler;
import fr.strow.core.api.commands.CommandsManagerImpl;
import fr.strow.core.api.utils.SchedulerImpl;
import fr.strow.core.module.faction.FactionManagerImpl;
import fr.strow.core.module.player.PlayerManagerImpl;
import fr.strow.persistence.data.redis.RedisAccess;
import fr.strow.persistence.data.redis.RedisCredentials;
import fr.strow.persistence.data.sql.SQLAccess;
import fr.strow.persistence.data.sql.SQLCredentials;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class SampleBindModule extends AbstractModule {

    private final StrowPlugin plugin;

    public SampleBindModule(StrowPlugin plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void configure() {
        InputStream in = getClass().getClassLoader().getResourceAsStream("credentials.yml");

        Yaml yaml = new Yaml();
        Map<String, ?> config = (Map<String, ?>) yaml.load(in);

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

        bind(Gson.class).toInstance(new Gson());

        bind(JavaPlugin.class).toInstance(plugin);
        bind(PluginManager.class).toInstance(plugin.getServer().getPluginManager());

        bind(ModulesHandler.class).to(SampleModulesHandler.class);
        bind(PropertiesHandler.class).to(SamplePropertiesHandler.class);

        bind(CommandsManager.class).to(CommandsManagerImpl.class);
        bind(FactionManager.class).to(FactionManagerImpl.class);
        bind(PlayerManager.class).to(PlayerManagerImpl.class);
        bind(Scheduler.class).to(SchedulerImpl.class);
    }
}
