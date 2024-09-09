package com.antoniocarlos.hierarchicwordtool;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;
import jakarta.json.JsonArray;
import java.io.StringReader;
import java.time.Instant;
import java.time.Duration;

/**
 * This class does the loading and processing  of the hierarchy data,
 * originally saved as a json. The processing consists of turning 
 * every key of the nested table got from loading the data and 
 * turning evey key in a tree node that has a name, a value 
 * (used to keep the number of occurences when counting) and a list
 * with the name of the children in the original tree (if any).
 * This structure allows the recording of an occurnce in O(1) and
 * the creation of the reporting list in mO(t) where m is the 
 * numbers of nodes at te specified depth and  t is the eight of 
 * each subtree, but since both are constants irrelated to the 
 * input length, it can be said that it is O(1)
 */
public class JSONCParserDataNode extends DataNode<String,Map>{
  protected Map data;
  protected String content;
  protected int level = 0;
  public JSONCParserDataNode(String content){
    this.content = content;
  }
  
  public JsonObject loadData(){
    JsonObject jsonObject = null;
    try(
    FileInputStream fis = new FileInputStream(content);
    InputStreamReader reader = new InputStreamReader(fis,"UTF-8");
    JsonReader jsonReader = Json.createReader(reader)){
    jsonObject = jsonReader.readObject();
    }catch(IOException e){}
    
    return jsonObject;
  }
  
  @Override
  public Map getData(){
    data = new HashMap();
    data.put(level,new ArrayList<TreeCounter.TreeNode>());
    var start = Instant.now();
    JsonObject jsonObject = this.loadData();
    traverseJsonObject(jsonObject);
    long dur = Duration.between(start,Instant.now()).toMillis();
    tell("t1",dur);
    tell("hierarchy-table",data);
    return context();
  }
  
  public void traverseJsonObject(JsonObject jsonObject){
    for (Map.Entry<String, JsonValue> entry : jsonObject.entrySet()) {
      String key = entry.getKey();
      TreeCounter.TreeNode node = new TreeCounter.TreeNode(key);
      data.put(key,node);
      ((List<TreeCounter.TreeNode>)data.get(level)).add(node);
      JsonValue value = entry.getValue();      
      switch (value.getValueType()) {
        case OBJECT:
          this.level++;
            if(data.get(level) == null){
              data.put(level, new ArrayList<TreeCounter.TreeNode>());
            }
            if(node.getChildren().isEmpty()){
              node.getChildren().addAll(value.asJsonObject().keySet());
            }
            traverseJsonObject(value.asJsonObject());
            this.level--;
          break;
        }
      }
  }
}
