
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
  private List<ScreenShots> screenshots;
  private List<Status> status;
  private String thumb_url;
  private String twitter_url;
  private String updated_at;
  private String video_url;
  private String location;

  private String market;

  private String companyType;

  public String getAngellist_url() {
    return angellist_url;
  }

  public void setAngellist_url(String angellist_url) {
    this.angellist_url = angellist_url;
  }

  public String getBlog_url() {
    return blog_url;
  }

  public void setBlog_url(String blog_url) {
    this.blog_url = blog_url;
  }

  public boolean isCommunity_profile() {
    return community_profile;
  }

  public void setCommunity_profile(boolean community_profile) {
    this.community_profile = community_profile;
  }

  public List<CompanyType> getCompany_type() {
    return company_type;
  }

  public void setCompany_type(List<CompanyType> company_type) {
    this.company_type = company_type;
  }

  public String getCompany_url() {
    return company_url;
  }

  public void setCompany_url(String company_url) {
    this.company_url = company_url;
  }

  public String getCreated_at() {
    return created_at;
  }

  public void setCreated_at(String created_at) {
    this.created_at = created_at;
  }

  public String getCrunchbase_url() {
    return crunchbase_url;
  }

  public void setCrunchbase_url(String crunchbase_url) {
    this.crunchbase_url = crunchbase_url;
  }

  public Number getFollower_count() {
    return follower_count;
  }

  public void setFollower_count(Number follower_count) {
    this.follower_count = follower_count;
  }

  public boolean isHidden() {
    return hidden;
  }

  public void setHidden(boolean hidden) {
    this.hidden = hidden;
  }

  public String getHigh_concept() {
    return high_concept;
  }

  public void setHigh_concept(String high_concept) {
    this.high_concept = high_concept;
  }

  public Number getId() {
    return id;
  }

  public void setId(Number id) {
    this.id = id;
  }

  public List<Location> getLocations() {
    System.out.println("Loaction");
    return locations;
  }

  public void setLocations(List<Location> locations) {
    System.out.println("Loaction");
    this.locations = locations;
  }

  public String getLogo_url() {
    return logo_url;
  }

  public void setLogo_url(String logo_url) {
    this.logo_url = logo_url;
  }

  public List<Markets> getMarkets() {
    return markets;
  }

  public void setMarkets(List<Markets> markets) {
    this.markets = markets;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getProduct_desc() {
    return product_desc;
  }

  public void setProduct_desc(String product_desc) {
    this.product_desc = product_desc;
  }

  public Number getQuality() {
    return quality;
  }

  public void setQuality(Number quality) {
    this.quality = quality;
  }

  public List getScreenshots() {
    return screenshots;
  }

  public void setScreenshots(List screenshots) {
    this.screenshots = screenshots;
  }

  public List getStatus() {
    return status;
  }

  public void setStatus(List status) {
    this.status = status;
  }

  public String getThumb_url() {
    return thumb_url;
  }

  public void setThumb_url(String thumb_url) {
    this.thumb_url = thumb_url;
  }

  public String getTwitter_url() {
    return twitter_url;
  }

  public void setTwitter_url(String twitter_url) {
    this.twitter_url = twitter_url;
  }

  public String getUpdated_at() {
    return updated_at;
  }

  public void setUpdated_at(String updated_at) {
    this.updated_at = updated_at;
  }

  public String getVideo_url() {
    return video_url;
  }

  public void setVideo_url(String video_url) {
    this.video_url = video_url;
  }

  public String getLocation() {
    System.out.println("Loaction");
    if (locations != null && locations.size() > 0) {
      System.out.println("Loaction");
      StringBuffer locationSet = new StringBuffer();
      for (Location locObj : locations) {
        locationSet.append(locObj.getDisplay_name() + ",");
        System.out.println("Loaction");
      }
      locationSet.deleteCharAt(locationSet.length() - 1);
      location = locationSet.toString();
    }
    System.out.println("Loaction" + location);
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getMarket() {
    return market;
  }

  public void setMarket(String market) {
    this.market = market;
  }

  public String getCompanyType() {
    return companyType;
  }

  public void setCompanyType(String companyType) {
    this.companyType = companyType;
  }


}
