package com.antoniocarlos.hierarchicwordtool;

/**
 * This class is a concrete Implmentantion of the parser implementation meant to consume the "analyze" token
 * that marks the begining of this command in the CLI application.
 * It also extends and provide a concrete implementation for the DataNode abstract class, where it's meant 
 * to tell the client code whether the analyze token was consumed or not
 */
public class AnalyzeParser extends DataNode<String,Boolean> implements Parser<String,Boolean>{
  public static final String FLAG = "analyze";//the token to watch for
  protected boolean ready = true;//the variable that marks if the instance it's still waiting for the token
  
  @Override
  public Parser.Outcomes check(String input, int start){
    if(input.equals(FLAG) && ready){
      ready = false;
      return Parser.Outcomes.ACCEPT_SINGLE;
    }
    return Parser.Outcomes.REJECT;
  }
  
  @Override
  public boolean consume(String input, int start){
    return false;
  }

  @Override
  public Boolean getParsedData(){
    return !this.ready;
  }

  @Override
  public void reset(){
    this.ready = true;
  }

  @Override
  public Boolean getData(){
    return this.getParsedData();
  }
}
