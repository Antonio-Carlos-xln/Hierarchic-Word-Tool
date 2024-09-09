package com.antoniocarlos.hierarchydata;

/**
 * This class does the parsing of the verbose option from the CLI
 */
public class VerboseParser extends DataNode<String,Boolean> implements Parser<String,Boolean>{
  public static final String VERBOSE_FLAG = "--verbose";
  protected boolean verbose =  false;
  @Override
  public Parser.Outcomes check(String input, int start){
    if(input.equals(VERBOSE_FLAG)){
      this.verbose = true;
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
    return this.verbose;
  }
  @Override
  public void reset(){
    this.verbose = false;
  }
  @Override
  public Boolean getData(){
    return this.getParsedData();
  }
  
}
