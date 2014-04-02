
package com.ehc.angel.api;

import java.util.List;

public class User {
    private String aboutme_url;
    private String angellist_url;
    private String behance_url;
    private String bio;
    private String blog_url;
    private String dribbble_url;
    private String facebook_url;
    private Number follower_count;
    private String github_url;
    private Number id;
    private String image;
    private boolean investor;
    private String linkedin_url;
    private List<Location> locations;
    private String name;
    private String location;
    private String role;
    private String Skill;
    private String online_bio_url;
    private List<Role> roles;
    private List<Skill> skills;
    private String twitter_url;
    private String what_ive_built;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        System.out.println("set Location called:" + location);
        this.location = location;
    }


    public String getAboutme_url() {
        return this.aboutme_url;
    }

    public void setAboutme_url(String aboutme_url) {
        this.aboutme_url = aboutme_url;
    }

    public String getAngellist_url() {
        return this.angellist_url;
    }

    public void setAngellist_url(String angellist_url) {
        this.angellist_url = angellist_url;
    }

    public String getBehance_url() {
        return this.behance_url;
    }

    public void setBehance_url(String behance_url) {
        this.behance_url = behance_url;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBlog_url() {
        return this.blog_url;
    }

    public void setBlog_url(String blog_url) {
        this.blog_url = blog_url;
    }

    public String getDribbble_url() {
        return this.dribbble_url;
    }

    public void setDribbble_url(String dribbble_url) {
        this.dribbble_url = dribbble_url;
    }

    public String getFacebook_url() {
        return this.facebook_url;
    }

    public void setFacebook_url(String facebook_url) {
        this.facebook_url = facebook_url;
    }

    public Number getFollower_count() {
        return this.follower_count;
    }

    public void setFollower_count(Number follower_count) {
        this.follower_count = follower_count;
    }

    public String getGithub_url() {
        return this.github_url;
    }

    public void setGithub_url(String github_url) {
        this.github_url = github_url;
    }

    public Number getId() {
        return this.id;
    }

    public void setId(Number id) {
        this.id = id;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getInvestor() {
        return this.investor;
    }

    public void setInvestor(boolean investor) {
        this.investor = investor;
    }

    public String getLinkedin_url() {
        return this.linkedin_url;
    }

    public void setLinkedin_url(String linkedin_url) {
        this.linkedin_url = linkedin_url;
    }

    public List getLocations() {
        return this.locations;
    }

    public void setLocations(List locations) {
        this.locations = locations;
        if (this.locations != null && this.locations.size() > 0) {
            setLocation(((Location) this.locations.get(0)).getDisplay_name());

//            StringBuffer locationString = new StringBuffer();
//            for (Location location : this.locations) {
//                locationString.append(location.getDisplay_name() + " , ");
//            }
//            setLocation(locationString.toString());

        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOnline_bio_url() {
        return this.online_bio_url;
    }

    public void setOnline_bio_url(String online_bio_url) {
        this.online_bio_url = online_bio_url;
    }

    public List getRoles() {
        return this.roles;
    }

    public void setRoles(List roles) {
        this.roles = roles;

        if (this.roles != null && this.roles.size() > 0) {
//            setRole(((Role) this.roles.get(0)).getDisplay_name());

            StringBuffer roleString = new StringBuffer();
            for (Role role : this.roles) {
                roleString.append(role.getDisplay_name() + " , ");
            }
            roleString.deleteCharAt(roleString.length() - 1);
            setLocation(roleString.toString());

        }


    }

    public List getSkills() {
        return this.skills;
    }

    public void setSkills(List skills) {
        this.skills = skills;

        if (this.skills != null && this.skills.size() > 0) {
            StringBuffer skillSet = new StringBuffer("");
            for (Skill skill : this.skills) {
                skillSet.append(skill.getDisplay_name() + " , ");
            }
//            skillSet.setCharAt(skillSet.lastIndexOf(","), ' ');
            setSkill(skillSet.toString());
        }


    }

    public String getTwitter_url() {
        return this.twitter_url;
    }

    public void setTwitter_url(String twitter_url) {
        this.twitter_url = twitter_url;
    }

    public String getWhat_ive_built() {
        return this.what_ive_built;
    }

    public void setWhat_ive_built(String what_ive_built) {
        this.what_ive_built = what_ive_built;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSkill() {
        return Skill;
    }

    public void setSkill(String skill) {
        Skill = skill;
    }
}
