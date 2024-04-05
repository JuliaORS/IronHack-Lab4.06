package com.ironhack.demo.repository;

import com.ironhack.demo.model.Employee;
import com.ironhack.demo.model.Status;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp(){

        Employee employee1 = new Employee("001", "cardiology", "Julia", Status.ON);
        Employee employee2 = new Employee("002", "radiology", "James", Status.OFF);
        employeeRepository.saveAll(List.of(employee1, employee2));
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void getDoctorByIdTest() {
        Optional<Employee> optionalEmployee = employeeRepository.findById("001");
        assertTrue(optionalEmployee.isPresent());
        Employee employee = optionalEmployee.get();
        assertEquals("cardiology", employee.getDepartment());
    }

}
