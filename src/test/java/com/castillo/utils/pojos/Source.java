package com.castillo.utils.pojos;

public class Source {
    private Long id;
    private String name;
    private String age;

    private Source inner;

    public Source(Long id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Source getInner() {
        return inner;
    }

    public void setInner(Source inner) {
        this.inner = inner;
    }
}
