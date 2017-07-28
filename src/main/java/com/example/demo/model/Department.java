package com.example.demo.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mayurlathkar on 25/07/17.
 */
public class Department implements Serializable {

    private int id;

    private String dept_name;

    private String head_of_dept;

    private ArrayList<Teacher> teachers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }

    public ArrayList<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }

    public String getHead_of_dept() {
        return head_of_dept;
    }

    public void setHead_of_dept(String head_of_dept) {
        this.head_of_dept = head_of_dept;
    }
}
