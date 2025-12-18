package com.example.demo;

//imports JUnit 5's testing tools 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeManagerTest {
    private EmployeeManager employeeManager;
    // declares instance variable to hold the object we are testing

    @BeforeEach
    void setUp() {
        // this runs before each test and creates a fresh EmployeeManager instance
        employeeManager = new EmployeeManager();
    }

    @Test
    void testGetAllEmployees_ReturnsEmployeesObject()
    // this marks the method as a test
    {

        // Act - now we call the method we are testing
        Employees result = employeeManager.getAllEmployees();

        // assert - we verify the result
        // it asserts that the value is not null
        assertNotNull(result, "getAllEmployees should not return null");
        assertNotNull(result.getEmployeeList(), "Employee list should not be null");

    }

    @Test
    void testInitialEmployees_HasThreeEmployees() {
        // arrange and act
        Employees employees = employeeManager.getAllEmployees();

        // assert - verify the result
        // asserts that two values are equal
        assertEquals(3, employees.getEmployeeList().size(), "Initial employee list should contain 3 employees");
    }

    @Test
    void testInitialEmployees_ContainsCorrectData() {
        // Arrange and act
        Employees employees = employeeManager.getAllEmployees();
        Employee firstEmployee = employees.getEmployeeList().get(0);

        // assert - check first employee details
        assertEquals(1, firstEmployee.getId(), "First employee ID should be 1");
        assertEquals("Prem", firstEmployee.getFirstName(), "First employee first name should be Prem");
        assertEquals("Tiwari", firstEmployee.getLastName(), "First employee last name should be Tiwari");
        assertEquals("prem@gmail.com", firstEmployee.getEmail(), "First employee email should be prem@gmail.com");
    }

    @Test
    void testAddEmployee_IncreasesListSize() {
        // arrange
        int initialSize = employeeManager.getAllEmployees().getEmployeeList().size();
        Employee newEmployee = new Employee(99, "Test", "User", "test@example.com", "Tester");

        // act - add to the list of employees
        employeeManager.addEmployee(newEmployee);

        // assert
        int newSize = employeeManager.getAllEmployees().getEmployeeList().size();
        assertEquals(initialSize + 1, newSize, "Adding an employee should increase list size by 1");
    }

    @Test
    void testAddEmployee_StoresCorrectData() {
        // Arrange
        Employee newEmployee = new Employee(99, "Jane", "Doe", "jane@example.com", "Developer");

        // act
        employeeManager.addEmployee(newEmployee);

        // assert - find the added employee
        Employees employees = employeeManager.getAllEmployees();
        Employee addedEmployee = employees.getEmployeeList().stream()
                .filter(e -> e.getEmail().equals("jane@example.com")).findFirst().orElse(null);

        assertNotNull(addedEmployee, "Added employee should be found in the list");
        assertEquals("Jane", addedEmployee.getFirstName(), "Added employee should have correct first name");
        assertEquals("Developer", addedEmployee.getTitle(), "Added employee should have correct title");

    }
}