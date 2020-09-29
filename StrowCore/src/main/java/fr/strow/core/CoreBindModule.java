/*
 * Copyright (c) 2020 Choukas - All rights reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Choukas <juan.vlroo@gmail.com>, 21/06/2020 16:33
 */

package fr.strow.core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import fr.strow.api.StrowPlugin;
import fr.strow.api.commands.CommandService;
import fr.strow.api.game.faction.FactionManager;
import fr.strow.api.game.permissions.PermissionsManager;
import fr.strow.api.game.player.PlayerManager;
import fr.strow.api.module.ModulesHandler;
import fr.strow.api.property.PropertiesHandler;
import fr.strow.api.services.GameUtilsService;
import fr.strow.api.services.Messaging;
import fr.strow.api.services.Scheduler;
import fr.strow.core.api.commands.CommandServiceImpl;
import fr.strow.core.api.services.GameUtilsServiceImpl;
import fr.strow.core.api.services.MessagingImpl;
import fr.strow.core.api.utils.SchedulerImpl;
import fr.strow.core.modules.faction.managers.FactionManagerImpl;
import fr.strow.core.modules.permissions.managers.PermissionsManagerImpl;
import fr.strow.core.modules.player.managers.PlayerManagerImpl;
import fr.strow.persistence.data.sql.SQLAccess;
import fr.strow.persistence.data.sql.SQLCredentials;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class CoreBindModule extends AbstractModule {

    private final StrowPlugin plugin;

    public CoreBindModule(StrowPlugin plugin) {
        this.plugin = plugin;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void configure() {
        try {
            InputStream in = Files.newInputStream(Paths.get(plugin.getDataFolder().toURI()).resolve("config.yml"));

            Yaml yaml = new Yaml();
            Map<String, ?> config = (Map<String, ?>) yaml.load(in);

            String sqlHost = (String) config.get("sql-host");
            String sqlUsername = (String) config.get("sql-username");
            String sqlPassword = (String) config.get("sql-password");
            int sqlPort = (Integer) config.get("sql-port");
            String sqlDatabase = (String) config.get("sql-database");

            SQLCredentials sqlCredentials = new SQLCredentials(
                    sqlHost,
                    sqlUsername,
                    sqlPassword,
                    sqlPort,
                    sqlDatabase
            );

            SQLAccess sqlAccess = new SQLAccess(sqlCredentials);

            bind(SQLAccess.class).toInstance(sqlAccess);

            /*String redisHost = (String) config.get("redis-host");
            int redisPort = (Integer) config.get("redis-port");
            int redisTimeout = (Integer) config.get("redis-timeout");
            String redisPassword = (String) config.get("redis-password");

            RedisCredentials redisCredentials = new RedisCredentials(
                    redisHost,
                    redisPort,
                    redisTimeout,
                    redisPassword
            );

            RedisAccess redisAccess = new RedisAccess(redisCredentials);

            bind(RedisAccess.class).toInstance(redisAccess);*/

            bind(Gson.class).toInstance(
                    new GsonBuilder()
                    .serializeNulls()
                    .create()
            );

            bind(JavaPlugin.class).toInstance(plugin);
            bind(PluginManager.class).toInstance(plugin.getServer().getPluginManager());

            bind(ModulesHandler.class).to(CoreModulesHandler.class);
            bind(PropertiesHandler.class).to(CorePropertiesHandler.class);

            bind(FactionManager.class).to(FactionManagerImpl.class);
            bind(PermissionsManager.class).to(PermissionsManagerImpl.class);
            bind(PlayerManager.class).to(PlayerManagerImpl.class);

            bind(CommandService.class).to(CommandServiceImpl.class);
            bind(Messaging.class).to(MessagingImpl.class);
            bind(GameUtilsService.class).to(GameUtilsServiceImpl.class);
            bind(Scheduler.class).to(SchedulerImpl.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
