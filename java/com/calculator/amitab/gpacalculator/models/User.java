package com.calculator.amitab.gpacalculator.models;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by amitab on 20/7/14.
 */
public class User implements Serializable {
    public Long userId;
    public String userName;

    public Double cgpa;
    public Integer totalCreditsEarned;

    public User() {
    }

    public static double round(double unrounded, int precision, int roundingMode)
    {
        BigDecimal bd = new BigDecimal(unrounded);
        BigDecimal rounded = bd.setScale(precision, roundingMode);
        return rounded.doubleValue();
    }

    public User(Long userId, String userName) {
        this.userName = userName;
        this.userId = userId;
    }

    public Integer getTotalCreditsEarned() {
        return totalCreditsEarned;
    }

    public void setTotalCreditsEarned(Integer totalCreditsEarned) {
        this.totalCreditsEarned = totalCreditsEarned;
    }

    public Double getCgpa() {
        return cgpa;
    }

    public void setCgpa(Double cgpa) {
        this.cgpa = cgpa;
        this.cgpa = round(this.cgpa, 2, BigDecimal.ROUND_DOWN);
    }

    public User(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
