package com.antoniocarlos.hierarchicwordtool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class PhraseParserTest{
  @Test
  public void firstCheckTest(){
    var parser = new PhraseParser();
    assertThat(parser.check("depth",0) == Parser.Outcomes.ACCEPT_SINGLE)
      .isTrue();
    assertThat(parser.check("--command",0) == Parser.Outcomes.ACCEPT_SINGLE)
      .isFalse();
    parser = new PhraseParser();
    assertThat(parser.check("-command",0) == Parser.Outcomes.ACCEPT_SINGLE)
      .isTrue();
    assertThat(parser.check("analyze",0) == Parser.Outcomes.ACCEPT_SINGLE)
      .isFalse();
    parser = new PhraseParser();
    assertThat(parser.check("analyze",0) == Parser.Outcomes.ACCEPT_SINGLE)
      .isTrue();
    assertThat(parser.check("analyze",0) == Parser.Outcomes.ACCEPT_SINGLE)
      .isFalse();
  }

  @Test
  public void secondCheckTest(){
    var parser = new PhraseParser();
    parser.check("content",0);
    assertThat(parser.check("depth",0) == Parser.Outcomes.REJECT )
      .isTrue();
    assertThat(parser.check("--some-comand",0) == Parser.Outcomes.REJECT )
      .isTrue();
    assertThat(parser.check("-comand",0) == Parser.Outcomes.REJECT )
      .isTrue();
    assertThat(parser.check("analyze",0) == Parser.Outcomes.REJECT )
      .isTrue();
    assertThat(parser.check("analyze",0) == Parser.Outcomes.REJECT)
      .isTrue();
  }

  
  @Test
  public void getDataTest(){
  var parser = new PhraseParser();
  parser.check("some very long text \"that also contains some escaped signs \"",0);
  assertThat(parser.getData())
    .isEqualTo("some very long text \"that also contains some escaped signs \"");
  parser = new PhraseParser();
  parser.check("",0);
  assertThat(parser.getData())
    .isEqualTo("");

  }
  
}
