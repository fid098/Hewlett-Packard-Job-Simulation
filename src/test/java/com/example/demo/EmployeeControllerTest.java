package com.example.demo;

//this imports JUnit and Mockito tools for testing 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
// this tells JUnit 5 to use Mockito's extension
class EmployeeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EmployeeManager employeeManager;
    // creates a mock(fake EmployeeManager)

    @InjectMocks
    private EmployeeController employeeController;
    // this creates a real controller instance and injects the @mock dependencies
    // into it
    // because it simulates the spring's dependency injection manually so there is
    // no need for spring context

    @BeforeEach
    void setUp() {
        // Manually set up MockMvc with the controller
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();

    }

    @Test
    void testGetEmployees_ReturnsOkStatus() throws Exception {
        // arrange - create mock data
        Employees mockEmployees = new Employees();
        mockEmployees.getEmployeeList().add(new Employee(1, "John", "Doe", "john@example.com", "Developer"));

        // tell mockito: when getAllEmployees() is called, return mockEmployees
        when(employeeManager.getAllEmployees()).thenReturn(mockEmployees);

        // Act and Assert - perform GET request and check response
        mockMvc.perform(get("/employees/")).andExpect(status().isOk())// expect 200
                .andExpect(jsonPath("$.employeeList").exists()) // expect json to have employee list
                .andExpect(jsonPath("$.employeeList[0].firstName").value("John")); // check the first name of the
                                                                                   // employee

        // verify that getAllEmployees() was called exactly once, this ensures the
        // controller actually uses the manager
        verify(employeeManager, times(1)).getAllEmployees();
    }

    @Test
    void testGetEmployees_ReturnsCorrectJsonStructure() throws Exception {
        // Arrange - create a new mockemployees list and add alice smith and bob jones
        // to the list
        Employees mockEmployees = new Employees();
        mockEmployees.getEmployeeList().add(
                new Employee(1, "Alice", "Smith", "alice@example.com", "Manager"));
        mockEmployees.getEmployeeList().add(
                new Employee(2, "Bob", "Jones", "bob@example.com", "Designer"));

        // tell mockito: when getAllEmployees() is called, return mockEmployees
        when(employeeManager.getAllEmployees()).thenReturn(mockEmployees);

        // Act & Assert
        mockMvc.perform(get("/employees/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeList").isArray()) // employee list should be an array
                .andExpect(jsonPath("$.employeeList.length()").value(2)) // Should have 2 items
                .andExpect(jsonPath("$.employeeList[0].id").value(1))
                .andExpect(jsonPath("$.employeeList[1].lastName").value("Jones")); // checks if the bobs details have
                                                                                   // been entered correctly
    }

    @Test
    void testAddEmployee_ReturnsCreatedStatus() throws Exception {
        // Arrange - create mock existing employees
        Employees mockEmployees = new Employees();
        mockEmployees.getEmployeeList().add(
                new Employee(1, "Existing", "Employee", "existing@example.com", "Dev"));

        when(employeeManager.getAllEmployees()).thenReturn(mockEmployees);

        // JSON body to send in POST request
        String newEmployeeJson = """
                {
                    "firstName": "New",
                    "lastName": "Employee",
                    "email": "new@example.com",
                    "title": "Engineer"
                }
                """;

        // get mediaType and ensure it is not null
        MediaType jsonType = MediaType.APPLICATION_JSON;
        if (jsonType == null) {
            throw new AssertionError("MediaType.APPLICATION_JSON should never be null");
        }

        // Act & Assert - this simulates a POST request
        mockMvc.perform(post("/employees/")
                .contentType(jsonType)
                .content(newEmployeeJson))
                .andExpect(status().isCreated()) // this expects HTTP 201 (created), REST standard for successful
                                                 // resource creation
                .andExpect(header().exists("Location")); // checks if the resource has a header

        // Verify addEmployee was called once with any employee object, ensures the
        // controller actually saved the employee
        verify(employeeManager, times(1)).addEmployee(any(Employee.class));
    }

    @Test
    void testAddEmployee_SetsCorrectLocationHeader() throws Exception {
        // Arrange
        Employees mockEmployees = new Employees();
        mockEmployees.getEmployeeList().add(
                new Employee(1, "First", "Employee", "first@example.com", "Dev"));
        mockEmployees.getEmployeeList().add(
                new Employee(2, "Second", "Employee", "second@example.com", "Dev"));

        when(employeeManager.getAllEmployees()).thenReturn(mockEmployees);

        String newEmployeeJson = """
                {
                    "firstName": "Third",
                    "lastName": "Employee",
                    "email": "third@example.com",
                    "title": "Manager"
                }
                """;

        // Get MediaType and ensure it's not null
        MediaType jsonType = MediaType.APPLICATION_JSON;
        if (jsonType == null) {
            throw new AssertionError("MediaType.APPLICATION_JSON should never be null");
        }

        // Act & Assert
        mockMvc.perform(post("/employees/")
                .contentType(jsonType)
                .content(newEmployeeJson))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",
                        "http://localhost/employees/3")); // ID should be 3 (size 2 + 1)
    }

    @Test
    void testAddEmployee_CallEmployeeManagerWithCorrectData() throws Exception {
        // Arrange
        Employees mockEmployees = new Employees();
        when(employeeManager.getAllEmployees()).thenReturn(mockEmployees);

        String newEmployeeJson = """
                {
                    "firstName": "TestFirst",
                    "lastName": "TestLast",
                    "email": "test@example.com",
                    "title": "TestTitle"
                }
                """;

        // Get MediaType and ensure it's not null
        MediaType jsonType = MediaType.APPLICATION_JSON;
        if (jsonType == null) {
            throw new AssertionError("MediaType.APPLICATION_JSON should never be null");
        }

        // act
        mockMvc.perform(post("/employees/")
                .contentType(jsonType)
                .content(newEmployeeJson))
                .andExpect(status().isCreated());

        // assert - verify addemployee was called with the correct data
        verify(employeeManager).addEmployee(argThat(employee -> employee.getFirstName().equals("TestFirst") &&
                employee.getLastName().equals("TestLast") &&
                employee.getEmail().equals("test@example.com") &&
                employee.getTitle().equals("TestTitle")));
    }

}
