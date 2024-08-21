package br.com.lmelgarejo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserPojo {

    private Long id;
    private String name;
    private Integer age;
    private Double salary;

    public UserPojo(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public UserPojo() {
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

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

}
