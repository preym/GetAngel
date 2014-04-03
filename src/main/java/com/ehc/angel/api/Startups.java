
package com.ehc.angel.api;

import java.util.List;

public class Startups {
  private String angellist_url;
  private String blog_url;
  private boolean community_profile;
  private List<CompanyType> company_type;
  private String company_url;
  private String created_at;
  private String crunchbase_url;
  private Number follower_count;
  private boolean hidden;
  private String high_concept;
  private Number id;
  private List<Location> locations;
  private String logo_url;
  private List<Markets> markets;
  private String name;
  private String product_desc;
  private Number quality;
  private List screenshots;
  private String status;
  private String thumb_url;
  private String twitter_url;
  private String updated_at;
  private String video_url;
  private String location;

  private String market;

  private String companyType;

  public String getCompanyType() {
    if (company_type != null && company_type.size() > 0) {
      StringBuffer companyName = new StringBuffer();
      for (CompanyType comObj : company_type) {
        companyName.append(comObj.getDisplay_name() + ",");
      }
      companyName.deleteCharAt(companyName.length() - 1);
      companyType = companyName.toString();
    }
    return companyType;
  }

  public void setCompanyType(String companyType) {
    this.companyType = companyType;
  }

  public String getMarket() {
    if (markets != null && markets.size() > 0) {
      StringBuffer marketTag = new StringBuffer();
      for (Markets markObj : markets) {
        marketTag.append(markObj.getDisplay_name() + ",");
      }
      marketTag.deleteCharAt(marketTag.length() - 1);
      market = marketTag.toString();
    }
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getLocation() {
    if (locations != null && locations.size() > 0) {
      System.out.println("get location");
      StringBuffer locationSet = new StringBuffer();
      for (Location locObj : locations) {
        locationSet.append(locObj.getDisplay_name() + ",");
      }
      locationSet.deleteCharAt(locationSet.length() - 1);
      location = locationSet.toString();
    }
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }


  public List getLocations() {
    System.out.println("get location");
    return this.locations;
  }

  public String getAngellist_url() {
    return this.angellist_url;
  }

  public void setAngellist_url(String angellist_url) {
    this.angellist_url = angellist_url;
  }

  public String getBlog_url() {
    return this.blog_url;
  }

  public void setBlog_url(String blog_url) {
    this.blog_url = blog_url;
  }

  public boolean getCommunity_profile() {
    return this.community_profile;
  }

  public void setCommunity_profile(boolean community_profile) {
    this.community_profile = community_profile;
  }

  public List getCompany_type() {
    return this.company_type;
  }

  public void setCompany_type(List company_type) {
    this.company_type = company_type;
  }

  public String getCompany_url() {
    return this.company_url;
  }

  public void setCompany_url(String company_url) {
    this.company_url = company_url;
  }

  public String getCreated_at() {
    return this.created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public String getCrunchbase_url() {
    return this.crunchbase_url;
  }

  public void setCrunchbase_url(String crunchbase_url) {
    this.crunchbase_url = crunchbase_url;
  }

  public Number getFollower_count() {
    return this.follower_count;
  }

  public void setFollower_count(Number follower_count) {
    this.follower_count = follower_count;
  }

  public boolean getHidden() {
    return this.hidden;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }

  public String getHigh_concept() {
    return this.high_concept;
  }

  public void setHigh_concept(String high_concept) {
    this.high_concept = high_concept;
  }

  public Number getId() {
    return this.id;
  }

  public void setId(Number id) {
    this.id = id;
  }

  public void setLocations(List locations) {
    System.out.println("set location");
    this.locations = locations;
  }

  public String getLogo_url() {
    return this.logo_url;
  }

  public void setLogo_url(String logo_url) {
    this.logo_url = logo_url;
  }

  public List getMarkets() {
    return this.markets;
  }

  public void setMarkets(List markets) {
    this.markets = markets;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getProduct_desc() {
    return this.product_desc;
  }

  public void setProduct_desc(String product_desc) {
    this.product_desc = product_desc;
  }

  public Number getQuality() {
    return this.quality;
  }

  public void setQuality(Number quality) {
    this.quality = quality;
  }

  public List getScreenshots() {
    return this.screenshots;
  }

  public void setScreenshots(List screenshots) {
    this.screenshots = screenshots;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getThumb_url() {
    return this.thumb_url;
  }

  public void setThumb_url(String thumb_url) {
    this.thumb_url = thumb_url;
  }

  public String getTwitter_url() {
    return this.twitter_url;
  }

  public void setTwitter_url(String twitter_url) {
    this.twitter_url = twitter_url;
  }

  public String getUpdated_at() {
    return this.updated_at;
  }

  public void setUpdated_at(String updated_at) {
    this.updated_at = updated_at;
  }

  public String getVideo_url() {
    return this.video_url;
  }

  public void setVideo_url(String video_url) {
    this.video_url = video_url;
  }
}
