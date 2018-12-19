package com.example.admin.moment_maker;

public class UserDetails {
    private String title;
    private String description;
    private String date;
    private String hugId;
    UserDetails(){

    }

    UserDetails(String hugId,String title,String description,String date){
        this.hugId=hugId;
        this.title=title;
        this.description=description;
        this.date=date;

    }

    public String getHugId() {
        return hugId;
    }

    public void setHugId(String hugId) {
        this.hugId = hugId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
