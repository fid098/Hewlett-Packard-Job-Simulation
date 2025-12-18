package com.example.demo;

//imports spring classes needed for REST API
import org.springframework.beans.factory.annotation.Autowired;
//for dependency injection
import org.springframework.http.ResponseEntity;
//to build HTTP responses
import org.springframework.web.bind.annotation.*;
//for routing
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
//to build URLs

import java.net.URI;
//imports java's URI 
//its needed to create location headers for new resources

@RestController
// marks this as a REST controller
@RequestMapping("/employees")
// sets the base path for all routes in this controller, all methods here will
// be under /employees
public class EmployeeController {
    @Autowired
    private EmployeeManager employeeDao;
    // Spring injects the EmployeeManager instance

    @GetMapping("/")
    // this maps Get requests to /employees/ to the next method
    // basically flask @app.route('/...../', methods=['GET'])
    public Employees getEmployees() {
        // Handles GET requests
        // Returns employees object(spring coverts to JSON)
        return employeeDao.getAllEmployees();

    }

    @PostMapping("/")
    // this maps Post requests to /employees/ to the next method
    public ResponseEntity<Object> addEmployee(@RequestBody Employee employee) {
        // this handles post requests
        // ResponseEntity<object> returns type allowing custom HTTP response
        // addEmployee - method name
        // @RequestBody - tells spring to deserialize JSON body into Employee object

        Integer id = employeeDao.getAllEmployees().getEmployeeList().size() + 1;
        // calculates the next ID
        employee.setId(id);

        employeeDao.addEmployee(employee);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(employee.getId())
                .toUri();
        // builds a URI pointing to the new resource

        return ResponseEntity.created(location).build();
    }
}
