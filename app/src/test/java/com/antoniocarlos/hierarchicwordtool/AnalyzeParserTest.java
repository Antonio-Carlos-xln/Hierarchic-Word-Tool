package com.antoniocarlos.hierarchydata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class AnalyzeParserTest{
  @Test
  public void checkTest(){
    var parser = new AnalyzeParser();
    assertThat(parser.check("depth",0) == Parser.Outcomes.REJECT)
      .isTrue();
    assertThat(parser.check("--analyze",0) == Parser.Outcomes.REJECT)
      .isTrue();
    assertThat(parser.check("-analyze",0) == Parser.Outcomes.REJECT)
      .isTrue();
    assertThat(parser.check("analyze",0) == Parser.Outcomes.ACCEPT_SINGLE)
      .isTrue();
    assertThat(parser.check("analyze",0) == Parser.Outcomes.REJECT)
      .isTrue();
  }
  
  @Test
  public void getDataTest(){
  var parser = new AnalyzeParser();
  parser.check("some very long text \"that also contains some escaped signs \"",0);
  assertThat(parser.getData())
    .isFalse();
  parser.check("some-command",0);
  assertThat(parser.getData())
    .isFalse();
  parser.check("analyze",0);
  assertThat(parser.getData())
    .isTrue();
  }
  
}
