package com.antoniocarlos.hierarchicwordtool;

/**
 * This class is a concrete Implmentantion of the parser implementation meant to consume the "analyze" token
 * that marks the begining of this command in the CLI application.
 * It also extends and provide a concrete implementation for the DataNode abstract class, where it's meant 
 * to tell the client code whether the analyze token was consumed or not
 */
public class AnalyzeParser extends DataNode<String,Boolean> implements Parser<String,Boolean>{
  /**
   * Special String this Instance should look for.
   * 
   */
  public static final String FLAG = "analyze";//the token to watch for
  /**
   * Tells if the parser is still waiting for the defined token to show up in the stream.
   *  
   */
  protected boolean ready = true;
  
  
  /**
   * Checks if the current token in the stream should be consumed by this parser.
   * If the return is ACCEPT_SINGLE, then the required input will already have been consumed
   * and the consume method won't be invoked.
   *    
   * @param input the current input token.
   * @param start the offset from start of the token.
   * @return Outcomes The reaction of this parser instance wrt the current input.
   */
  @Override
  public Parser.Outcomes check(String input, int start){
    if(input.equals(FLAG) && ready){
      ready = false;
      return Parser.Outcomes.ACCEPT_SINGLE;
    }
    return Parser.Outcomes.REJECT;
  }
  
  
  /** 
   * Processess the supplyed token.
   *  
   * @param input the current token of the stream.
   * @param start the offset from the start of the token.
   * @return boolean if the parser instance will or not keep consuming tokens from the stream.
   */
  @Override
  public boolean consume(String input, int start){
    return false;
  }

  
  /**
   * After parsing is complete, returns the final result.
   *  
   * @return Boolean
   */
  @Override
  public Boolean getParsedData(){
    return !this.ready;
  }
  /** 
   * Resets the state of this parser, alowing its reuse.
   * 
   */
  @Override
  public void reset(){
    this.ready = true;
  }

  
  /** 
   * Returns the data produced by this DataNode.
   * 
   * @return Boolean
   */
  @Override
  public Boolean getData(){
    return this.getParsedData();
  }
}
