package com.example.demo;
// this declares that this class belongs to the com.example.demo package 

public class Employee {
    // declares a public class named Employee-public means other classes can use it
    // class name must match filename
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    // declares four instance variables, private=only accesible within this class
    private String title;
    // another title instance variable

    public Employee() {
        // this is basically a constructur with a pass..creates an empty employee
    }

    public Employee(Integer id, String firstName, String lastName, String email, String title) {
        // an __init__ function basically
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.title = title;
        // assigns parameter values to instance variables
        // instance variable = parameter(this is like self in python)
    }

    public Integer getId() {
        return id;
        // this is a getter method that returns the id value
    }

    public void setId(Integer id) {
        this.id = id;
        // this is a setter method that sets the id value
        // void = returns nothing
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;

    }

    public String getLastName() {
        return lastName;

    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTtitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", firstName=" + firstName +
                ", lastName=" + lastName + ", email=" + email + ", title=" + title + "]";
    }
    // it overrides the default toString() method like def __str__(self): in python
    // @Override = "im overriding a parent method"
    // then it builds and returns a formatted string

}
