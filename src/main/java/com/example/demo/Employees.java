package com.example.demo;
//same package as Employee

import java.util.ArrayList;
import java.util.List;
//this imports classes from Java's utility library
//List = interface
//ArrayList = actual implementation of List

public class Employees {
    // declares the class Employees

    private List<Employee> employeeList;
    // declares a private list that holds Employee objects
    // in python: self.employee_list: list[Employee] = []
    // List<Employee> = generic type(list of employees specifically)

    public List<Employee> getEmployeeList() {
        // Getter for the employee list
        // Returns: The list of employees
        if (employeeList == null) {
            employeeList = new ArrayList<>();
            // this creates a list if it doesnt exist
            // new ArrayList<>() creates an empty list
            // <>=diamond operator
        }
        return employeeList;

    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
        // setter to replace the entire list
        // parameter: new list to use
    }
}