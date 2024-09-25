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
  /**
  * Whether or not a phrase has already parsed. 
  * 
  */
  protected boolean ready = true;
  /**
  * Holds the phrase, if already parrsed, or null, otherwise. 
  * 
  */
  protected String input;

  /**
   * Checks if and how this token should be consumed by this parser instance.
   *  
   * @param input the current token of the stream.
   * @param start the offset from start of the stream (not applicable).
   * @return Outcomes if and how the parser should consume this input.
   */
  @Override
  public Parser.Outcomes check(String input, int start){
    if(ready){
      this.input = input;
      ready = false;
      return Parser.Outcomes.ACCEPT_SINGLE;
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
    return false;
  }
    
  /** 
   * After the end of the processing, returns the final result.
   * 
   * @return Integer the parsed epth from the input stream (from the cli args).
   */
  @Override
  public String getParsedData(){
    return this.input;
  }
    
  /** 
   * Resets the state of the parser. Allowing its reuse.
   * 
   */
  @Override
  public void reset(){
    this.ready = false;
    this.input = null;
  }

  /** 
   * Retrives the data produced by this DataNode, i.e. the positional frase in the input.
   * 
   */
  @Override
  public String getData(){
    return this.getParsedData();
  }
}
