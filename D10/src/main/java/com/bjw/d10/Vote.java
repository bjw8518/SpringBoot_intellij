package com.bjw.d10;

public class Vote {
    int idx;
    String target;
    int age;
    String sex;
    Vote() {

    }

    Vote(String target, String sex, int age) {
        this.target = target;
        this.sex = sex;
        this.age = age;
    }
}
