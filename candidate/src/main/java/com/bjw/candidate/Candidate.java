//Candidate.java
package com.bjw.candidate;

public class Candidate {
    private int idx;
    private String sex;
    private int age;
    private String vote;


    public Candidate() {
    }

    public Candidate(int idx, String sex, int age, String vote) {
        this.idx = idx;
        this.sex = sex;
        this.age = age;
        this.vote = vote;

    }

    public int getIdx() {
        return idx;
    }

    public String getSex() {
        return sex;
    }


    public int getAge() {
        return age;
    }

    public String getVote() {
        return vote;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public void setAge(int age) {
        this.age = age;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

}
