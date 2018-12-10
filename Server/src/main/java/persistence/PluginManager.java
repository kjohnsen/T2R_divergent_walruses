package persistence;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.ServerModel;

public class PluginManager {

    IPersistencePluginFactory iPersistencePluginFactory;

    Map<String, IPersistencePluginFactory> pluginName_pluginFactory;

    public PluginManager() {
        initializeAllPlugins();
    }

    private void initializeAllPlugins() {
        pluginName_pluginFactory = new HashMap<>();
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        File file = new File("plugins.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String name = scanner.next();
                String path = scanner.next();
                String jarName = scanner.next();
                String className = scanner.next();
                IPersistencePluginFactory plugin = createPlugin(className, path + "/" + jarName);
                pluginName_pluginFactory.put(name, plugin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //may as well do all the instantiating in the constructor...
        //should we instantiate all of the plugins?
        //maybe a map of all the plugins... and then just set the right plugin

        //TODO: for each name in JSON file..
        //createPlugin(json string)
        //add to Map

    }

    public IPersistencePluginFactory createPlugin(String className, String filePath) throws Exception {
        URL[] classLoaderUrls = new URL[]{new URL(filePath)};
        URLClassLoader loader = new URLClassLoader(classLoaderUrls);
        Class<?> pluginClass = loader.loadClass(className);
        Constructor<?> constructor = pluginClass.getConstructor();
        return (IPersistencePluginFactory)constructor.newInstance();
        //TODO: look into JSON file to instantiate the plugin
        //Use Java URLClassLoader
        //automatically set server iPersistencePluginFactory
    }

    public IPersistencePluginFactory getiPersistencePluginFactory() {
        return iPersistencePluginFactory;
    }

    //auto set server model whenever setter is used.
    public void setiPersistencePluginFactory(String pluginName) {
        IPersistencePluginFactory pluginFactory = pluginName_pluginFactory.get(pluginName);
        ServerModel.getInstance().setiPersistencePluginFactory(pluginFactory);
        this.iPersistencePluginFactory = pluginFactory;
    }
}