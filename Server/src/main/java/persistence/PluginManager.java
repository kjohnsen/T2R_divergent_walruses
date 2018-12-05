package persistence;

import java.util.HashMap;
import java.util.Map;

public class PluginManager {

    IPersistencePluginFactory iPersistencePluginFactory;

    Map<String, IPersistencePluginFactory> pluginName_pluginFactory = new HashMap<>();

    public PluginManager() {

        //may as well do all the instantiating in the constructor...
        //should we instantiate all of the plugins?
        //maybe a map of all the plugins... and then just set the right plugin

        //TODO: for each name in JSON file..
        //createPlugin(json string)
        //add to Map

    }

    public IPersistencePluginFactory createPlugin(String pluginName) {

        //TODO: look into JSON file to instantiate the plugin

        //Use Java URLClassLoader

        //automatically set server iPersistencePluginFactory

        return null;
    }

    public IPersistencePluginFactory getiPersistencePluginFactory() {
        return iPersistencePluginFactory;
    }

    public void setiPersistencePluginFactory(IPersistencePluginFactory iPersistencePluginFactory) {
        this.iPersistencePluginFactory = iPersistencePluginFactory;
    }
}