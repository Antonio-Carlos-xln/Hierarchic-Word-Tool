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
  /**
   * The nodes where we can get the datathe nodes where we can get the data.
   *  
   */
  protected Map<I,DataNode> relateds = new HashMap<I,DataNode>();
  /**
   * Cache of requested values, following some rules.
   *  
   */
  protected Map<I,Object> cache = new HashMap<I,Object>();
  /**
   * Aggregator of values to be returned by this instance.
   *  
   */
  protected Map<I,Object> values = new HashMap<I,Object>();

  /**
   * Called by other instances connected 
   * to this one when they need the data.
   * 
   * @return the produced data.
   */
  public abstract T getData();

  /**
   * Requests the data from another instance 
   * previously connected to this one.
   * Cache of calls means to reduce unnecessary recalculations
   * of the values in case of heavy operations. If needed te cache can be cleaned, making
   * it possible to perform another call.
   * 
   * @param key a token of some kind that describes what node should be acessed.
   * @return the required piece of information from the registered DataNode.
   * @throws NullPointerException if a DataNodewas not registered with given key.
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
   * Helper version of (@see ask) optimized for dealing with maps,
   * keeping the semantics, and avoiding a in-between node to extract
   * the data of interest. This method strongely benefits from the caching
   * 
   * @param key a token of some kind that describes what node should be acessed.
   * @return a map containg the required piece of information from the registered DataNode.
   * @throws NullPointerException if a DataNode was not registered with given key.
   * @throws ClassCastException If the specified DataNode does not return a map. 
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
   * Clean th cache, allowing that the next call will trigger a fresh value.
   * 
   * @param key the key to be removed of the cache, what will cause the next data reqiest to invoke the node pointed by the given key.
   */
  public void cleanCache(I key){
    this.cache.remove(key);
  }

  /**
   * Returns if there's already a cached value
   * 
   * @param key what to look for in the cache.
   * @return whether the underlying map has the given key, i.e. a registered node for this key, or not. 
   * 
   */
  public boolean hasCachedValue(I key){
    return this.cache.containsKey(key);
  }

  /**
   * Returns the DataNode associated with this one through the "key" identifier
   * 
   * @param key the key to look for registered DataNodes.
   * @return the registered DataNode or null, if there's no one registered for given key.
   * 
   */
  public DataNode get(I key){
    return this.relateds.get(key);
  }

  /**
   * Relates another DataNode to this one, allowing this one to request data from
   * the specified key.
   * 
   * @param key the key to register the DataNode for.
   * @param node the node to be registered.
   */
  public void set(I key,DataNode node){
    this.relateds.put(key,node);
  }

  /**
   * Adds a value to the aggregation that can be returned by this instance when its data are requested. The returning
   * of this aggregation it's neither automatic nor obligatory the corresponding method shoud be called
   * 
   * @param the key to register the information for.
   * @param o the object to register, i.e. the data.
   * 
   */
  public void tell(I key, Object o){
    this.values.put(key,o);
  }
  /**
   * Clears the aggregtion of data.
   * 
   */  
  public void clearContext(){
    this.values.clear();
  }

  /**
   * Returns a reference to the aggregation, allowinng it to be returned when data is requested. 
   */
  public Map<I,Object> context(){
    return values;
  }
  
  
}
