package com.antoniocarlos.hierarchicwordtool;

/**
 * This class does the parsing of a phrase in the input, 
 * since it has no criteria, it should come at the last position 
 * in the list of parsers applied to the input, since the parsing 
 * order is relevant. If it ever omes the need to parse more 
 * string in this fashion, the a sufficient umber of instances 
 * of this parser should be supplied to the client, always at 
 * last positionof the lists and in the expected order among 
 * the unnamed arguments.
 */
public class PhraseParser extends DataNode<String,String> implements Parser<String,String>{
  protected boolean ready = true;
  protected String input;
  @Override
  public Parser.Outcomes check(String input, int start){
    if(ready){
      this.input = input;
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
  public String getParsedData(){
    return this.input;
  }
  @Override
  public void reset(){
    this.ready = false;
    this.input = null;
  }
  @Override
  public String getData(){
    return this.getParsedData();
  }
}
