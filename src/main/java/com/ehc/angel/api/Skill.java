
package com.ehc.angel.api;

import java.util.List;

public class Skill {
   	private String angellist_url;
   	private String display_name;
   	private Number id;
   	private String level;
   	private String name;
   	private String tag_type;

 	public String getAngellist_url(){
		return this.angellist_url;
	}
	public void setAngellist_url(String angellist_url){
		this.angellist_url = angellist_url;
	}
 	public String getDisplay_name(){
		return this.display_name;
	}
	public void setDisplay_name(String display_name){
		this.display_name = display_name;
	}
 	public Number getId(){
		return this.id;
	}
	public void setId(Number id){
		this.id = id;
	}
 	public String getLevel(){
		return this.level;
	}
	public void setLevel(String level){
		this.level = level;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public String getTag_type(){
		return this.tag_type;
	}
	public void setTag_type(String tag_type){
		this.tag_type = tag_type;
	}
}
