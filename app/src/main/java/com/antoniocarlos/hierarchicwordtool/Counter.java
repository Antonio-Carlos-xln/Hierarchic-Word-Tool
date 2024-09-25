package com.antoniocarlos.hierarchicwordtool;

import java.util.Map;
import java.util.List;

/**
 * Interface for Counters. altough it's outside of the parging framework,
 * composed of the Parser interface and its implementations,
 * This interface it's also ean to be called  once for every Token, delegating
 * the looping specifics to the client, allowing greater flexibility 
 */
public interface Counter<I,T>{
  /**
   * It does extra preparations for start counting.
   *  
   */
  void begin();
  /**
   * It does the cleanup after the counting is done.
   *  
   */
  void stop();
  /**
   * Counts every token of the String, according to the implemented policy.
   *  
   */
  void count(I word);
  /**
   * Returns the data of the counting in a prespecified format defined by the type T.
   *  
   * @return a List of instances of some class that represents the process of counting.
   * 
   */
  List<T> report();
}
