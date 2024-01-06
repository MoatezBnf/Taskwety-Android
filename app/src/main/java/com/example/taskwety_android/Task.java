package com.example.taskwety_android;

public class Task {
    private int id;
    private String Uid;
    private String title;
    private String description;
    public Task(){}
    public Task(int id,String Uid, String name, String _phone_number){
        this.id = id;
        this.Uid = Uid;
        this.title=title;
        this.description=description;
    }
    public Task(String Uid,String title,String description){
        this.Uid=Uid;
        this.title=title;
        this.description=description;
    }
    public int getID(){
        return this.id;
    }
    public void setID(int id){
        this.id = id;
    }
    public String getUid(){
        return this.Uid;
    }
    public void setUid(String Uid){
        this.Uid=Uid;
    }
    public String getTitle(){
        return this.title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String description){
        this.description = description;
    }
}
