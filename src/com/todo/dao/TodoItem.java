package com.todo.dao;

import java.util.Date;
import java.text.SimpleDateFormat;

public class TodoItem {
	private int id;
	private int is_completed;
	private int importance;
    private String title;
    private String desc;
    private String current_date;
    private String category;
    private String due_date;
    private String day;

	public TodoItem(String title, String category, String desc, String day, String due_date, int importance){
    	this.title=title;
        this.category=category;
        this.desc=desc;
        this.day=day;
        this.due_date=due_date;
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk:mm:ss");
        this.current_date=f.format(new Date());
        this.is_completed=0;
        this.importance=importance;
    }
    
    public TodoItem(int id, String title, String category, String desc, String day, String due_date, String current_date, int is_completed, int importance){
        this.id=id;
    	this.title=title;
        this.category = category;
        this.desc=desc;
        this.day=day;
        this.due_date=due_date;
        this.current_date=current_date;
        this.is_completed=is_completed;
        this.importance=importance;
    }
	
    public String toString() {
    	if(this.is_completed==1)
    		return (this.id + " [" + this.category + "] " + this.title + "[V] - " + this.desc + " - " + this.day + " - " + this.due_date + " - " + this.current_date + " - " + this.importance);
    	else
    		return (this.id + " [" + this.category + "] " + this.title + " - " + this.desc + " - " + this.day + " - " + this.due_date + " - " + this.current_date + " - " + this.importance);
    }
    
    public int getImportance() {
		return importance;
	}

	public void setImportance(int importance) {
		this.importance = importance;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}
    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public String getCurrent_date() {
        return current_date;
    }

    public void setCurrent_date(String current_date) {
        this.current_date = current_date;
    }
}
