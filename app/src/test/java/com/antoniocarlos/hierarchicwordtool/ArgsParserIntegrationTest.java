package com.antoniocarlos.hierarchicwordtool;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;


public class ArgsParserIntegrationTest{
  @Test
  public void processArgsTestGoodInput(){
    var analyzeParser = new AnalyzeParser();
    var phraseParser = new PhraseParser();
    var depthParser = new DepthParser();
    var verboseParser = new VerboseParser();
    String[] args = new String[]{"analyze","the phrase","--depth","5","--verbose"};
    var parser = new ArgsParser(args,Arrays.asList(analyzeParser,depthParser,verboseParser,phraseParser));
    parser.processArgs();
    
  }
  @Test
  public void processArgsTestBadInput(){
    var analyzeParser = new AnalyzeParser();
    var phraseParser = new PhraseParser();
    var depthParser = new DepthParser();
    var verboseParser = new VerboseParser();
    String[] args = new String[]{"git","the phrase","--depth","5","--verbose"};
    var parser = new ArgsParser(args,Arrays.asList(analyzeParser,depthParser,verboseParser,phraseParser));
    assertThatThrownBy(() -> parser.processArgs())
    .isInstanceOf(IllegalStateException.class);
  }

  
}
