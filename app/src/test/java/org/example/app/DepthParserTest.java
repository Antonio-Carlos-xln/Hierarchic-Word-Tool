package com.antoniocarlos.hierarchicwordtool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class DepthParserTest{
  @Test
  public void checkTest(){
    var parser = new DepthParser();
    assertThat(parser.check("depth",0) == Parser.Outcomes.REJECT )
      .isTrue();
    assertThat(parser.check("depth--",0) == Parser.Outcomes.REJECT )
      .isTrue();
    assertThat(parser.check("---depth",0) == Parser.Outcomes.REJECT )
      .isTrue();
    assertThat(parser.check("--depp",0) == Parser.Outcomes.REJECT )
      .isTrue();
    assertThat(parser.check("--depth",0) == Parser.Outcomes.ACCEPT)
      .isTrue();
  }

  @Test
  public void consumeTest(){
    var parser = new DepthParser();
    assertThat(parser.consume("8",0))
      .isFalse();
    assertThat(parser.consume("1",0))
      .isFalse();
    assertThatThrownBy(() -> parser.consume("--depth",0))
      .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> parser.consume("-9",0))
      .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> parser.consume("0",0))
      .isInstanceOf(IllegalArgumentException.class);
  }
  @Test
  public void getDataTest(){
    var parser = new DepthParser();
    parser.consume("8",0);
    assertThat(parser.getData())
      .isEqualTo(7);
    parser.consume("1",0);
    assertThat(parser.getData())
      .isEqualTo(0);
  }
  
}
