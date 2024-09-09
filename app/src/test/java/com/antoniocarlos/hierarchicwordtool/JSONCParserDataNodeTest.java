package com.antoniocarlos.hierarchydata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.atIndex;
import static org.assertj.core.api.Assertions.entry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.json.JsonArray;
                
class JSONCParserDataNodeTest{
  @Test
  public void loadDataTest(){
    var j = new JSONCParserDataNode("./dicts/data.json");
    JsonObject obj = j.loadData();
    assertThat(obj.containsKey("data-leaf"))
      .isTrue();
    assertThat(obj.isNull("data-leaf"))
      .isTrue();
  }
  @Test
  public void simplegetDataTest(){
    var j = new JSONCParserDataNode("./dicts/data.json");
    j.getData();
    assertThat(j.data.containsKey("data-leaf"))
      .isTrue();
    assertThat((List)j.data.get(0))
      .isNotNull()
      .isNotEmpty();
    assertThat(((TreeCounter.TreeNode)j.data.get("data-leaf")))
      .isNotNull();
    assertThat(((TreeCounter.TreeNode)j.data.get("data-leaf")) == ((List)j.data.get(0)).get(0))
      .isTrue();
    assertThat(((TreeCounter.TreeNode)j.data.get("data-leaf")).getName())
      .isEqualTo("data-leaf");
  }
  @Test
  public void getDataTest(){
    var j = new JSONCParserDataNode("./dicts/hierarchy.json");
    j.getData();
    assertThat(j.data.size())
      .isEqualTo(10);
  }
}
