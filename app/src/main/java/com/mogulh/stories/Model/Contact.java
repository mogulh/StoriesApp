package com.mogulh.stories.Model;

public class Contact {
    private String id;
    private String username;
    private String imageurl;
    private String bio;

    public Contact(String id, String username, String imageurl, String bio) {
        this.id = id;
        this.username = username;
        this.imageurl = imageurl;
        this.bio = bio;
    }

    public Contact() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
