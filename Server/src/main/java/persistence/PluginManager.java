package persistence;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.Plugin;
import model.ServerModel;
import sun.rmi.log.LogInputStream;

public class PluginManager {

    IPersistencePluginFactory iPersistencePluginFactory;

    Map<String, Plugin> pluginName_pluginFactory;

    public PluginManager() {
        initializeAllPlugins();
    }

    private void initializeAllPlugins() {
        pluginName_pluginFactory = new HashMap<>();
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        File file = new File("plugins.txt");
        try {
            Scanner scanner = new Scanner(file);
            String newLine;
            while (scanner.hasNextLine()) {
                newLine = scanner.nextLine();

                Scanner scanner2 = new Scanner(newLine);
                Plugin plugin = new Plugin();
                String name = scanner2.next();
                plugin.setName(name);
                plugin.setClassName(scanner2.next());
                while(scanner2.hasNext()){
                    plugin.addPath(scanner2.next());
                }

                //IPersistencePluginFactory plugin = createPlugin(className, path + "/" + jarName);
                pluginName_pluginFactory.put(name, plugin);
            }

            //MongoDB MongoFactoryPlugin MongoDB/build/libs MongoDB.jar MongoDB/libs/mongo-java-driver-3.9.1.jar
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

    public IPersistencePluginFactory createPlugin(Plugin plugin) throws Exception {

        //Get a class loader and set it up to load the jar file


        //create an array of URLs
        ArrayList<URL> urlList = new ArrayList<>();
        for (String urlPath : plugin.getDependencies()) {
            File pluginJarFile= new File(urlPath);
            System.out.println(pluginJarFile.exists());
            urlList.add(pluginJarFile.toURI().toURL());
        }

        URLClassLoader loader = new URLClassLoader(urlList.toArray(new URL[urlList.size()]));
        // Load the jar file's plugin class, create and return an instance
        Class<?> messagePluginClass= (Class<IPersistencePluginFactory>)loader.loadClass("DaoFactory." + plugin.getClassName());

        return (IPersistencePluginFactory)messagePluginClass.getDeclaredConstructor(null).newInstance();


        /*
        URL[] classLoaderUrls = new URL[]{new URL(filePath)};
        URLClassLoader loader = new URLClassLoader(classLoaderUrls);

        Class<? extends > pluginClass1 = (Class<MessagePlugin>)loader.loadClass(className);

        Class<?> pluginClass = loader.loadClass(className);
        Constructor<?> constructor = pluginClass.getConstructor();
        return (IPersistencePluginFactory)constructor.newInstance();
        //TODO: look into JSON file to instantiate the plugin
        //Use Java URLClassLoader
        //automatically set server iPersistencePluginFactory

        */
    }

    public IPersistencePluginFactory getiPersistencePluginFactory() {
        return iPersistencePluginFactory;
    }

    //auto set server model whenever setter is used.
    //instantiates new plugin.
    public void setiPersistencePluginFactory(String pluginName) {

        IPersistencePluginFactory pluginFactory = null;

        try{
            pluginFactory = createPlugin(pluginName_pluginFactory.get(pluginName));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        ServerModel.getInstance().setiPersistencePluginFactory(pluginFactory);
        this.iPersistencePluginFactory = pluginFactory;
    }
}