/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.syncframe;

//import java.lang.reflect.Constructor;
//import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author icerus
 */
public class Synchroniser {
    //private static final Map SyncConsList<String, Constructor> = new HashMap<>();
    private static final List<String> SyncMechList = new ArrayList<>();
    private static final Map<Object, SyncMech> SyncedObjects = new HashMap<>();
    private static final boolean debug = true;
    
    static void RegisterNewSyncMech(String SyncClassName){
        SyncMechList.add(SyncClassName);
        /*if(debug){
            System.out.println(SyncClass.getSimpleName() + " registered as " + SyncClass.getName());
            SyncClass.getConstructor(Class[Object]).newInstance(Object[o]);
        }*/
    }
    
   /* static boolean Initialise(Object o, String s){
        try {
            SyncedObjects.put(o, ((Constructor) SyncMechList.get(s)).newInstance(o));
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            System.err.println(ex);
        }
        return true;
    }*/
    
    public static List ListSyncMechs(){
        return SyncMechList;
    }
    
    public static boolean Initialise(Object o, SyncMech s){
        if(SyncedObjects.containsKey(o)){
            return false;
        }else{
            SyncedObjects.put(o, s);
            return true;
        }
    }
    
    public static SyncMech getSyncMech(Object o){
        return SyncedObjects.getOrDefault(o, null);
    }
    
    public static boolean Write(Object o, Object n){
        return (SyncedObjects.get(o)).Write(o, n);
    }
    
    public static Object Read(Object o){
        return (SyncedObjects.get(o)).Read();
    }
    
    public static boolean DeSync(Object o){
        if((SyncedObjects.get(o)).CleanUp()){
            SyncedObjects.remove(o);
            return true;
        }
        return false;
    }
    
}
