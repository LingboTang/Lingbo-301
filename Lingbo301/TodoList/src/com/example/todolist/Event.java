package com.example.todolist;

import java.io.Serializable;

public class Event implements Serializable{
	
	//Modified from lab Tweet.java
	private static final long serialVersionUID = 1L;
	private String todobody;
	private boolean clicked=false;
	private int archived;
	
	
	Event(String name){
		this.todobody=name;
	}
	public String getBody(){
		return this.todobody;
	}
	
	public int getArchived(){
		return archived;
	}
	
	public boolean getClick(){
		return this.clicked;
	}
	public void setClick(boolean click){
		this.clicked=click;
	}
	
	public void archive(){
		this.archived=1;
	}
	public void unarchive(){
		this.archived=0;
	}
	
}