package com.antoniocarlos.hierarchicwordtool;

import java.util.Map;
import java.util.HashMap;

/**
 * This class allows data to be read through invokation on-demand
 * of the getData method of the implementations. It's conceptually based in the reader monad (ask method)
 * and the writter monad (tell method). The main idea is to allow data to e requested when needed,so the network
 * only works in one direction, the dataflows toward those who requesteed it.
 * 
 */
public abstract class DataNode<I,T>{
  //the nodes where we can get the data
  protected Map<I,DataNode> relateds = new HashMap<I,DataNode>();
  //cache of requested values, following some rules
  protected Map<I,Object> cache = new HashMap<I,Object>();
  //aggregator ofvalues to be returned by this instance
  protected Map<I,Object> values = new HashMap<I,Object>();

  /**
   * Called by other instances connected 
   * to this one when they need the data
   */
  public abstract T getData();

  /**
   * Requests the data from another instance 
   * previously connected to this one.
   * Cache of calls means to reduce unnecessary recalculations
   * of the values in case of heavy operations. If needed te cache can be cleaned, making
   * it possible to perform another call.
   */
  public <X> X ask(I key){
    if(cache.containsKey(key)){
      return (X)cache.get(key);
    }else{
      X val = ((DataNode<I,X>)this.get(key)).getData();
      cache.put(key,val);
      return val;
    }
  }

  /**
   * Helper version of (@code ask) optimized for dealing with maps,
   * keeping the semantics, and avoiding a in-between node to extract
   * the data of interest. This method strongely benefits from the caching
   */
  public Map from(I key){
    if(cache.containsKey(key)){
      return ((Map)cache.get(key));
    }else{
      Map val = (Map)this.get(key).getData();
      cache.put(key,val);
      return val;
    }
  }


  /**
   * Ckean th cache, allowing that the next call will trigger a fresh value 
   */
  public void cleanCache(I key){
    this.cache.remove(key);
  }

  /**
   * Returns if there's already a cached value
   */
  public boolean hasCachedValue(I key){
    return this.cache.containsKey(key);
  }

  /**
   * Returns the DataNode associated with this 
   * one through the "key" identifier
   */
  public DataNode get(I key){
    return this.relateds.get(key);
  }

  /**
   * Relates another DataNode to this one,
   * allowing this one to request data from
   * the specified key
   */
  public void set(I key,DataNode d){
    this.relateds.put(key,d);
  }

  /**
   * Adds a value to the aggregation that can be returned
   * by this instance when its data are requested. The returning
   * of this aggregation it's neither automatic nor obligatory
   * the corresponding method shoud be called
   */
  public void tell(I key, Object o){
    this.values.put(key,o);
  }
  /**
   * Clears the aggregtion of data
   */  
  public void clearContext(){
    this.values.clear();
  }

  /**
   * Returns a reference to the aggregation, allowinng it to be
   * returned when data is requested 
   */
  public Map<I,Object> context(){
    return values;
  }
  
  
}
