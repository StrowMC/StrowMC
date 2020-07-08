package fr.strow.api.configuration;

import fr.strow.api.StrowPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by Hokkaydo on 24-06-2020.
 */
public abstract class AbstractConfiguration {

    private final String path;
    FileConfiguration config = JavaPlugin.getPlugin(StrowPlugin.class).getConfig();

    public AbstractConfiguration(String path){
        this.path = path;
    }

    public void loadConfig(){
        for(Field field : Arrays.stream(this.getClass().getDeclaredFields()).filter(field -> field.isAnnotationPresent(Config.class)).collect(Collectors.toList())){
            Config annotation = field.getAnnotation(Config.class);
            boolean oldAccessible = field.isAccessible();
            field.setAccessible(true);
            try {
                field.set(field, config.get(path + "." + annotation.value()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(oldAccessible);
        }
    }
    public void saveConfig(){
        for(Field field : Arrays.stream(this.getClass().getDeclaredFields()).filter(field -> field.isAnnotationPresent(Config.class)).collect(Collectors.toList())){
            Config annotation = field.getAnnotation(Config.class);
            boolean oldAccessible = field.isAccessible();
            field.setAccessible(true);
            try {
                config.set(path + "." + annotation.value(), field.get(field));
                field.set(field, config.get(path + "." + annotation.value()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            field.setAccessible(oldAccessible);
        }
    }
}
