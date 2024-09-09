package com.antoniocarlos.hierarchydata;

/**
 * Implementation of Parser and DataNode, meant to parse the depth option from the CLI options
 * all the data that can be retrieved through the DataNode interface is the result of the parsing,
 * if already called
 */
public class DepthParser extends DataNode<String,Integer> implements Parser<String,Integer>{
  public static final String DEPTH_FLAG = "--depth";
  protected int depth  = -1;
  @Override
  public Parser.Outcomes check(String input, int start){
    if(input.equals(DEPTH_FLAG)){
      return Parser.Outcomes.ACCEPT;
    }
    return Parser.Outcomes.REJECT;
  }
  @Override
  public boolean consume(String input, int start){
    this.depth = Integer.parseInt(input) - 1;
    if(depth < 0){
      throw new IllegalArgumentException();
    }
    return false;
  }
  @Override
  public Integer getParsedData(){
    return this.depth;
  }
  @Override
  public void reset(){
    this.depth = -1;
  }
  @Override
  public Integer getData(){
    return this.getParsedData();
  }
  
}
