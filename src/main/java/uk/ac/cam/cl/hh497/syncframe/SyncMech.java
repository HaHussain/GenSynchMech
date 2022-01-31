package uk.ac.cam.cl.hh497.syncframe;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author icerus
 * @param <T>
 */
public abstract class SyncMech<T extends Object> {
    private final T SyncedObject;
    
    SyncMech(T t){
        this.SyncedObject = t;
    }
    
    abstract T Read();
    
    abstract boolean Write(T o, T n);
    
    abstract boolean CleanUp();
    
    
}
