package com.antoniocarlos.hierarchicwordtool;

/**
 * This class does the parsing of the verbose option from the CLI
 */
public class VerboseParser extends DataNode<String,Boolean> implements Parser<String,Boolean>{
  /**
  * The token to watch for at the token stream. 
  * 
  */
  public static final String VERBOSE_FLAG = "--verbose";
  /**
  * the parsed boolean value from the stream. 
  * 
  */
  protected boolean verbose =  false;

  /**
   * Checks if and how this token should be consumed by this parser instance.
   *  
   * @param input the current token of the stream.
   * @param start the offset from start of the stream (not applicable).
   * @return Outcomes if and how the parser should consume this input.
   */
  @Override
  public Parser.Outcomes check(String input, int start){
    if(input.equals(VERBOSE_FLAG)){
      this.verbose = true;
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
  public Boolean getParsedData(){
    return this.verbose;
  }
  /** 
   * Resets the state of the parser. Allowing its reuse.
   * 
   */
  @Override
  public void reset(){
    this.verbose = false;
  }

  /** 
   * Retrives the information produced by this DataNode, i.e. the verbose option.
   * 
   * @return Boolean the parsed verbose option.
   */
  @Override
  public Boolean getData(){
    return this.getParsedData();
  }
  
}
