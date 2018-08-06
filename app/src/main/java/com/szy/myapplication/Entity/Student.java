package com.szy.myapplication.Entity;

import android.view.View;

import java.util.Observable;

public class Student extends Observable implements View.OnClickListener{
    private volatile static Student instance = null;


    private Student() {
    }

    public  static Student getInstance() {
        synchronized (Student.class) {
            if (instance == null) {
                instance = new Student();
            }
        }
        return instance;
    }

    @Override
    public void onClick(View v) {

    }

    public static class Address {
        private String name;
        private String age;
        private String sex;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }
    }
}
