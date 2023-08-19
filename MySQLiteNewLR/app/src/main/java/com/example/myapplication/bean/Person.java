package com.example.myapplication.bean;

public class Person {
    int id;
    String name;
    int age;
    int tall;

    public Person(int id, String name, int age, int tall) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.tall = tall;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getTall() {
        return tall;
    }

    public void setTall(int tall) {
        this.tall = tall;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", tall=" + tall +
                '}';
    }
}
