package com.example.demo;
//same package

import org.springframework.stereotype.Repository;
//this imports Spring's @repository annotation
//why: need to iport annotations before using them

@Repository
// marks this class as a spring repository
// tells spring: "this manages data", spring will create one instance and manage
// it, and it makes it available for @Autowired injection elsewhere
public class EmployeeManager {
    private Employees employees = new Employees();
    // creates a static(class-level) Employees object "employees"
    {
        // this is a static initialization block- it runs once when the class loads
        employees.getEmployeeList().add(new Employee(1, "Prem", "Tiwari", "prem@gmail.com", "Developer"));
        employees.getEmployeeList().add(new Employee(2, "Vikash", "Kumar", "vikash@gmail.com", "Manager"));
        employees.getEmployeeList().add(new Employee(3, "Ritesh", "Ojha", "ritesh@gmail.com", "Designer"));
    }

    public Employees getAllEmployees() {
        return employees;
    }

    public void addEmployee(Employee employee) {
        employees.getEmployeeList().add(employee);
    }
}