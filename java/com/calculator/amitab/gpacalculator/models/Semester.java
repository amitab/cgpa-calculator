package com.calculator.amitab.gpacalculator.models;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by amitab on 21/7/14.
 */
public class Semester implements Serializable {
    Integer semester;
    Double sgpa;
    Long userId;
    Integer creditsEarned;

    public static double round(double unrounded, int precision, int roundingMode)
    {
        BigDecimal bd = new BigDecimal(unrounded);
        BigDecimal rounded = bd.setScale(precision, roundingMode);
        return rounded.doubleValue();
    }

    public Integer getCreditsEarned() {
        return creditsEarned;
    }

    public void setCreditsEarned(Integer creditsEarned) {
        this.creditsEarned = creditsEarned;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Double getSgpa() {
        return sgpa;
    }

    public void setSgpa(Double sgpa) {
        this.sgpa = sgpa;
        this.sgpa = round(this.sgpa, 2, BigDecimal.ROUND_HALF_UP);
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
