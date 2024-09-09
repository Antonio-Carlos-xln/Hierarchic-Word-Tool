package com.antoniocarlos.hierarchydata;

import java.util.Map;
import java.util.List;

/**
 * Interface for Counters. altough it's outside of the parging framework,
 * composed of the Parser interface and its implementations,
 * This interface it's also ean to be called  once for every Token, delegating
 * the looping specifics to the client, allowing greater flexibility 
 */
public interface Counter<I,T>{
  void begin();
  void stop();
  void count(I word);
  List<T> report();
}
