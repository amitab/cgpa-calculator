package com.calculator.amitab.gpacalculator.models;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by amitab on 20/7/14.
 */
public class Subject implements Serializable {
    public String subjectCode;
    public String subjectName;
    public Integer subjectCredits;
    public Integer semester;

    public boolean isDirty() {
        return dirty;
    }

    public void clean() {
        this.dirty = false;
    }

    public boolean dirty;

    public static final String[] GRADES = {"S", "A", "B", "C", "D", "E", "F", "X", "-"};
    public static final Integer[] SCORES = {10, 9, 8, 7, 5, 4, 0, -1, -99};

    public Integer score;

    public String getGrade(Integer score) {
        int index = Arrays.asList(SCORES).indexOf(score);
        return GRADES[index];
    }

    public Integer getScore(String grade) {
        int index = Arrays.asList(GRADES).indexOf(grade);
        return SCORES[index];
    }

    public Subject() {
        this.score = -99;
        this.dirty = false;
    }

    public Subject(String subjectCode, String subjectName, Integer subjectCredits, Integer semester) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectCredits = subjectCredits;
        this.semester = semester;
        this.score = -99;
        this.dirty = false;
    }

    public Subject(String subjectCode, String subjectName, Integer subjectCredits, Integer semester, Integer Score) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.subjectCredits = subjectCredits;
        this.semester = semester;
        this.score = score;
        this.dirty = false;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
        this.dirty = true;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
        this.dirty = true;
    }

    public Integer getSubjectCredits() {
        return subjectCredits;
    }

    public void setSubjectCredits(Integer subjectCredits) {
        this.subjectCredits = subjectCredits;
        this.dirty = true;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
        this.dirty = true;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
        this.dirty = true;
    }
}
