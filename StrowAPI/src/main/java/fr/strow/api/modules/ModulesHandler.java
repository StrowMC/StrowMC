package fr.strow.api.modules;

/**
 * Created by Hokkaydo on 24-06-2020.
 */
public interface ModulesHandler {

    <T extends StrowModule> void enableModule(Class<T> clazz);

    void enableModules();

    <T extends StrowModule> void disableModule(Class<T> clazz);

    void disableModules();
}
