
package com.ehc.angel.api;

import java.util.List;

public class StartUp {
  private Number last_page;
  private Number page;
  private Number per_page;
  private List startups;
  private Number total;

  public Number getLast_page() {
    return this.last_page;
  }

  public void setLast_page(Number last_page) {
    this.last_page = last_page;
  }

  public Number getPage() {
    return this.page;
  }

  public void setPage(Number page) {
    this.page = page;
  }

  public Number getPer_page() {
    return this.per_page;
  }

  public void setPer_page(Number per_page) {
    this.per_page = per_page;
  }

  public List getStartups() {
    System.out.println("Method call");
    System.out.println(startups);
    return this.startups;
  }

  public void setStartups(List startups) {
    this.startups = startups;
  }

  public Number getTotal() {
    return this.total;
  }

  public void setTotal(Number total) {
    this.total = total;
  }
}
