package com.antoniocarlos.hierarchicwordtool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class VerboseParserTest{
  @Test
  public void checkTest(){
    var parser = new VerboseParser();
    assertThat(parser.check("--depth",0) == Parser.Outcomes.REJECT )
      .isTrue();
    assertThat(parser.check("analyze",0) == Parser.Outcomes.REJECT )
      .isTrue();
    assertThat(parser.check("-verbose",0) == Parser.Outcomes.REJECT )
      .isTrue();
    assertThat(parser.check("--verbose",0) == Parser.Outcomes.REJECT )
      .isFalse();
    assertThat(parser.check("--verbose",0) == Parser.Outcomes.ACCEPT_SINGLE)
      .isTrue();
  }

  @Test
  public void consumeTest(){
    var parser = new PhraseParser();
    assertThat(parser.consume("--verbose",0))
      .isFalse();
    assertThat(parser.consume("-f",0))
      .isFalse();
  }
  
}
