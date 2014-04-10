
package com.ehc.angel.api;

public class Location {
  private Number id;
  private String tag_type;
  private String name;
  private String display_name;
  private String angellist_url;


  public String getAngellist_url() {
    return this.angellist_url;
  }

  public void setAngellist_url(String angellist_url) {
    this.angellist_url = angellist_url;
  }

  public String getDisplay_name() {
    return this.display_name;
  }

  public void setDisplay_name(String display_name) {
    this.display_name = display_name;
  }

  public Number getId() {
    return this.id;
  }

  public void setId(Number id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTag_type() {
    return this.tag_type;
  }

  public void setTag_type(String tag_type) {
    this.tag_type = tag_type;
  }
}
