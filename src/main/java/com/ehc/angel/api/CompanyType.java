package com.ehc.angel.api;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 3/4/14
 * Time: 5:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class CompanyType {
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getTag_type() {
    return tag_type;
  }

  public void setTag_type(String tag_type) {
    this.tag_type = tag_type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDisplay_name() {
    return display_name;
  }

  public void setDisplay_name(String display_name) {
    this.display_name = display_name;
  }

  public String getAngellist_url() {
    return angellist_url;
  }

  public void setAngellist_url(String angellist_url) {
    this.angellist_url = angellist_url;
  }

  private int id;
  private String tag_type;
  private String name;
  private String display_name;
  private String angellist_url;
}
