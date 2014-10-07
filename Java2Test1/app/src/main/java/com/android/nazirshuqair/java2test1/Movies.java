package com.android.nazirshuqair.java2test1;

import org.json.JSONArray;

import java.io.Serializable;

/**
 * Created by nazirshuqair on 10/6/14.
 */
public class Movies implements Serializable {

    private static final long serialVersionUID = 8736847634070552888L;

    private String title;
    private int rating;
    private int year;
    private String description;
    private String star;


    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
