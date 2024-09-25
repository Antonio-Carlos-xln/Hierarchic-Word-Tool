package com.antoniocarlos.hierarchicwordtool;

import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.time.Instant;
import java.time.Duration;

/**
 * Implementation of DataNode, meant for performing the actual counting as well
 * as well as the timing in milliseconds of such operation 
 */
public class CounterContextDataNode extends DataNode<String,Map<String,Object>>{
  /**
   * Holds the list of nodes that represent a token, a string to be counted.
   *  
   */
  protected List<TreeCounter.TreeNode> counts;
  
  /** 
   * Produces and returns the data this DataNode is responsable for,
   * requiring from other connected nodes the pieces of information required for performing said computation
   * 
   * @return Map<String, Object>
   */
  @Override
  public Map<String,Object> getData(){
    long t0 = (long) this.ask("t0");
    Map hierarchyData = (Map) this.from("json").get("hierarchy-table");
    String phrase   = (String)  this.ask("phrase");
    int depth =  (int) this.ask("depth");
    long t1 = (long) this.from("json").get("t1");
    TreeCounter counter = new TreeCounter(hierarchyData, depth);
    Instant start = Instant.now();
    Arrays
      .stream(phrase.split(" "))
      .forEach(w -> counter.count(w));
    counts = counter.report();
    long interv = Duration.between(
                                    start,
                                    Instant.now()
                                  ).toMillis();
    tell("counts",counts);
    tell("times",Arrays.asList(t0+t1,interv));
    return context();
  }
}
