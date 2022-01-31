/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.cam.cl.hh497.syncframe;

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
    //private static final Map SyncMechList = new HashMap<String, Constructor>();
    private static final List SyncMechList = new ArrayList<String>();
    private static final Map SyncedObjects = new HashMap<Object, SyncMech>();
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
    
    static boolean Initialise(Object o, SyncMech s){
        if(SyncedObjects.containsKey(o)){
            return false;
        }else{
            SyncedObjects.put(o, s);
            return true;
        }
    }
    
    static boolean Write(Object o, Object n){
        if(((SyncMech)SyncedObjects.get(o)).Write(o, n)){
            return true;
        }else{
            return false; 
        }
    }
    
    static Object Read(Object o){
        return ((SyncMech)SyncedObjects.get(o)).Read();
    }
    
    static boolean DeSync(Object o){
        if(((SyncMech)SyncedObjects.get(o)).CleanUp()){
            SyncedObjects.remove(o);
            return true;
        }else{
            return false;
        }
    }
}
