package model;

import java.util.ArrayList;

public class Plugin {
    String name;
    String className;
    ArrayList<String> dependencies;

    public Plugin(){
        dependencies = new ArrayList<>();
    }

    public void addPath(String path){
        dependencies.add(path);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public ArrayList<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(ArrayList<String> dependencies) {
        this.dependencies = dependencies;
    }
}
