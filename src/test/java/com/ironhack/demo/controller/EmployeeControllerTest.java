package com.ironhack.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demo.model.Employee;
import com.ironhack.demo.model.Status;
import com.ironhack.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class EmployeeControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private EmployeeRepository employeeRepository;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Employee employee1 = new Employee("001", "cardiology", "Julia", Status.ON);
        Employee employee2 = new Employee("002", "radiology", "James", Status.OFF);
        employeeRepository.saveAll(List.of(employee1, employee2));
    }

    @AfterEach
    void tearDown() {
        employeeRepository.deleteAll();
    }

    @Test
    void getAllEmployeeTest() throws Exception {
        List<Employee> employees = employeeRepository.findAll();
        String expectedJson = objectMapper.writeValueAsString(employees);

        mockMvc.perform(get("/doctors").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void getDoctorByIdPositiveResponseTest() throws Exception{
        String expectedJson = objectMapper.writeValueAsString(employeeRepository.findById("001"));

        mockMvc.perform(get("/doctors/{id}", "001").contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getDoctorByIdNegativeResponseTest() throws Exception{
        mockMvc.perform(get("/doctors/{id}", "004").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400))
                .andExpect(status().reason(containsString("not found")));
    }

    @Test
    void getListDoctorByStatusPositiveResponseTest() throws Exception{
        String expectedJson = objectMapper.writeValueAsString(employeeRepository.findByStatus(Status.ON));
        mockMvc.perform(get("/doctors/status/{status}", "ON").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    /*@Test
    void getListDoctorByStatusNegativeResponseTest() throws Exception{
        mockMvc.perform(get("/doctors/status/{status}", "o").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(status().reason(containsString("not found")));
    }*/

    @Test
    void getListDoctorByDepartmentPositiveResponseTest() throws Exception{
        String expectedJson = objectMapper.writeValueAsString(employeeRepository.findByDepartment("cardiology"));
        mockMvc.perform(get("/doctors/department/{department}", "cardiology")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void getListDoctorByDepartmentNegativeResponseTest() throws Exception{
        mockMvc.perform(get("/doctors/department/{department}", "car")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(status().reason(containsString("not found")));
    }

    @Test
    void storeValidCreatedEmployeeTest() throws Exception {
        Employee employee = new Employee("003", "cardiology", "John", Status.OFF);
        String body = objectMapper.writeValueAsString(employee);

        MvcResult mvcResult = mockMvc.perform(post("/doctors")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated()).andReturn();

        String responseBody = mvcResult.getResponse().getContentAsString();
        assertEquals(responseBody, body);
    }

    @Test
    void deleteEmployeeTest() throws Exception {
        long nbResources = employeeRepository.count();
        MvcResult mvcResult = mockMvc.perform(delete("/doctors/{id}", "001")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        assertEquals(employeeRepository.count(), nbResources-1);
    }
}
