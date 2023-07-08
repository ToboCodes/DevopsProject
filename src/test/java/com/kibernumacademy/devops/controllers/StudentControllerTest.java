package com.kibernumacademy.devops.controllers;

import com.kibernumacademy.devops.services.IStudentService;
import com.kibernumacademy.devops.entitys.Student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import java.util.Optional;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IStudentService service;

    private static final String REDIRECT_STUDENTS = "redirect:/students";
    private static final String USERNAME = "userdevops";
    private static final String PASSWORD = "devops";

    private static RequestPostProcessor authenticate() {
        return httpBasic(USERNAME, PASSWORD);
    }

    @Test
    void testGetStudents() throws Exception {
        mockMvc.perform(get("/students").with(authenticate()))
            .andExpect(status().isOk())
            .andExpect(view().name("students"));
    }

    @Test
    void testNewStudentForm() throws Exception {
        mockMvc.perform(get("/students/new").with(authenticate()))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("student"))
            .andExpect(view().name("create-student"));
    }

    @Test
    void testShowFormEditStudent() throws Exception {
        Student student = new Student();
        when(service.getStudentById(1L)).thenReturn(Optional.of(student));

        mockMvc.perform(get("/students/edit/1").with(authenticate()))
            .andExpect(status().isOk())
            .andExpect(model().attribute("student", student))
            .andExpect(view().name("edit_student"));
    }

    @Test
    void testDeleteStudent() throws Exception {
        mockMvc.perform(get("/students-delete/1").with(authenticate()))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name(REDIRECT_STUDENTS));

        verify(service).deleteStudentById(1L);
    }
}