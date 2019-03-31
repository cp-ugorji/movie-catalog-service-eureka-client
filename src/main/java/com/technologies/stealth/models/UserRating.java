/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.technologies.stealth.models;

import java.util.List;

/**
 *
 * @author Chux
 */
public class UserRating {
    private List<Rating> userRatings;

    public UserRating() {
    }

    public List<Rating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<Rating> userRatings) {
        this.userRatings = userRatings;
    }
    
    
}
