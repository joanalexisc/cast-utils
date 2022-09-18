package com.castillo.utils.pojos;

public class Target {
    private Long id;
    private String name;
    private Integer age;
    private Target inner;

    public Target(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Target() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Target getInner() {
        return inner;
    }

    public void setInner(Target inner) {
        this.inner = inner;
    }
}
