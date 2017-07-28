package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mayurlathkar on 25/07/17.
 */
@Entity
@Table(name = "teacher")
public class Teacher extends User implements Serializable {

    private ArrayList<String> subjects;

    private int[] classes;

    private Long reposting_hod_id;

    private String dept_name;

    @Override
    public String getUser_type() {
        return super.getUser_type();
    }

    @Override
    public void setUser_type(String user_type) {
        super.setUser_type(user_type);
    }

    @Override
    public Long getId() {
        return super.getId();
    }

    @Override
    public void setId(Long id) {
        super.setId(id);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public int[] getClasses() {
        return classes;
    }

    public void setClasses(int[] classes) {
        this.classes = classes;
    }

    public Long getReposting_hod_id() {
        return reposting_hod_id;
    }

    public void setReposting_hod_id(Long reposting_hod_id) {
        this.reposting_hod_id = reposting_hod_id;
    }

    public String getDept_name() {
        return dept_name;
    }

    public void setDept_name(String dept_name) {
        this.dept_name = dept_name;
    }
}
