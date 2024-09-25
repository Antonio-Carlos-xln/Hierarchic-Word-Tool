package com.antoniocarlos.hierarchicwordtool;

/**
 * Implementation of Parser and DataNode, meant to parse the depth option from the CLI options
 * all the data that can be retrieved through the DataNode interface is the result of the parsing,
 * if already called.
 * 
 */
public class DepthParser extends DataNode<String,Integer> implements Parser<String,Integer>{
  /**
  * The string to look for in the stream of tokens. 
  * 
  */
  public static final String DEPTH_FLAG = "--depth";
  /**
  * The current parsed value of depth, or -1 if not yet found. 
  * 
  */
  protected int depth  = -1;

  
  /**
   * Checks if and how this token should be consumed by this parser instance.
   *  
   * @param input the current token of the stream.
   * @param start the offset from start of the stream (not applicable).
   * @return Outcomes if and how the parser should consume this input.
   */
  @Override
  public Parser.Outcomes check(String input, int start){
    if(input.equals(DEPTH_FLAG)){
      return Parser.Outcomes.ACCEPT;
    }
    return Parser.Outcomes.REJECT;
  }
  
  /** 
   * Consumes the tokens of the stream.
   * 
   * @param input current token of the stream.
   * @param start offset from the start of the token.
   * @return boolean whether or not the parser shoul keep consuming tokens from  the current stream.
   */
  @Override
  public boolean consume(String input, int start){
    this.depth = Integer.parseInt(input) - 1;
    if(depth < 0){
      throw new IllegalArgumentException();
    }
    return false;
  }
  
  /** 
   * After the end of the processing, returns the final result.
   * 
   * @return Integer the parsed epth from the input stream (from the cli args).
   */
  @Override
  public Integer getParsedData(){
    return this.depth;
  }

  /** 
   * Resets the state of the parser. Allowing its reuse.
   * 
   */
  @Override
  public void reset(){
    this.depth = -1;
  }
  
  /** 
   * Retrives the information produced by this DataNode, i.e. the depth.
   * 
   * @return Integer the parsed depth.
   */
  @Override
  public Integer getData(){
    return this.getParsedData();
  }
  
}
