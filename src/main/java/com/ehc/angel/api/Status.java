package com.ehc.angel.api;

/**
 * Created with IntelliJ IDEA.
 * User: ehc
 * Date: 10/4/14
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class Status {
  private int id;
  private String message;

  public String getCreated_at() {
    return created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  private String created_at;
}
