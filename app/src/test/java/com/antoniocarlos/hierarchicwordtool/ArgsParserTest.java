package com.antoniocarlos.hierarchydata;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;


public class ArgsParserTest{
  @Test
  public void processArgsTestGoodInput(){
    var analyzeParser = Mockito.mock(AnalyzeParser.class);
    Mockito.when(analyzeParser.check(Mockito.anyString(),Mockito.anyInt()))
                            .thenReturn(Parser.Outcomes.ACCEPT_SINGLE)
                            .thenReturn(Parser.Outcomes.REJECT)
                            .thenReturn(Parser.Outcomes.REJECT)
                            .thenReturn(Parser.Outcomes.REJECT);
    var depthParser = Mockito.mock(DepthParser.class);
    Mockito.when(depthParser.check(Mockito.anyString(),Mockito.anyInt()))
                            .thenReturn(Parser.Outcomes.REJECT)
                            .thenReturn(Parser.Outcomes.ACCEPT)
                            .thenReturn(Parser.Outcomes.REJECT);
    Mockito.when(depthParser.consume(Mockito.anyString(),Mockito.anyInt()))
                            .thenReturn(false);
    var verboseParser = Mockito.mock(VerboseParser.class);
    Mockito.when(verboseParser.check(Mockito.anyString(),Mockito.anyInt()))
                            .thenReturn(Parser.Outcomes.REJECT)
                            .thenReturn(Parser.Outcomes.ACCEPT_SINGLE);
    var phraseParser = Mockito.mock(PhraseParser.class);
    Mockito.when(phraseParser.check(Mockito.anyString(),Mockito.anyInt()))
                            .thenReturn(Parser.Outcomes.ACCEPT_SINGLE);
    String[] args = new String[]{"analyze","the phrase","--depth","5","--verbose"};
    var parser = new ArgsParser(args,Arrays.asList(analyzeParser,depthParser,verboseParser,phraseParser));
    parser.processArgs();
    Mockito.verify(analyzeParser,Mockito.times(4)).check(Mockito.anyString(),Mockito.anyInt());
    Mockito.verify(phraseParser,Mockito.times(1)).check(Mockito.anyString(),Mockito.anyInt());
    Mockito.verify(depthParser,Mockito.times(3)).check(Mockito.anyString(),Mockito.anyInt());
    Mockito.verify(depthParser,Mockito.times(1)).consume(Mockito.anyString(),Mockito.anyInt());
    Mockito.verify(verboseParser,Mockito.times(2)).check(Mockito.anyString(),Mockito.anyInt());
  }
  @Test
  public void processArgsTestBadInput(){
    var analyzeParser = Mockito.mock(AnalyzeParser.class);
    Mockito.when(analyzeParser.check(Mockito.anyString(),Mockito.anyInt()))
                            .thenReturn(Parser.Outcomes.ACCEPT_SINGLE)
                            .thenReturn(Parser.Outcomes.REJECT)
                            .thenReturn(Parser.Outcomes.REJECT)
                            .thenReturn(Parser.Outcomes.REJECT);

    var phraseParser = Mockito.mock(PhraseParser.class);
    Mockito.when(phraseParser.check(Mockito.anyString(),Mockito.anyInt()))
                            .thenReturn(Parser.Outcomes.REJECT)
                            .thenReturn(Parser.Outcomes.ACCEPT_SINGLE)
                            .thenReturn(Parser.Outcomes.REJECT)
                            .thenReturn(Parser.Outcomes.REJECT);
    var depthParser = Mockito.mock(DepthParser.class);
    Mockito.when(depthParser.check(Mockito.anyString(),Mockito.anyInt()))
                            .thenReturn(Parser.Outcomes.REJECT)
                            .thenReturn(Parser.Outcomes.REJECT)
                            .thenReturn(Parser.Outcomes.ACCEPT)
                            .thenReturn(Parser.Outcomes.REJECT);
    Mockito.when(depthParser.consume(Mockito.anyString(),Mockito.anyInt()))
                            .thenReturn(false);
    var verboseParser = Mockito.mock(VerboseParser.class);
    Mockito.when(verboseParser.check(Mockito.anyString(),Mockito.anyInt()))
                            .thenReturn(Parser.Outcomes.REJECT)
                            .thenReturn(Parser.Outcomes.REJECT)
                            .thenReturn(Parser.Outcomes.REJECT)
                            .thenReturn(Parser.Outcomes.ACCEPT_SINGLE);
    
    String[] args = new String[]{"git","the phrase","--depth","5","--verbose"};
    var parser = new ArgsParser(args,Arrays.asList(analyzeParser,depthParser,verboseParser,phraseParser));
    assertThatThrownBy(() -> parser.processArgs())
    .isInstanceOf(IllegalStateException.class);
  }

  
}
