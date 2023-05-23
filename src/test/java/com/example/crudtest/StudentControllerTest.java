package com.example.crudtest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateStudent() throws Exception {
        String studentJson = "{\"name\":\"John\",\"surname\":\"Doe\",\"working\":\"true\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/students")
                        .content(studentJson)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.working").value(true))
                .andDo(print());
    }

    @Test
    public void testGetAllStudents() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/students"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    public void testGetStudentById() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/students/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andDo(print());
    }

    @Test
    public void testUpdateStudent() throws Exception {
        String updatedStudentJson = "{\"name\":\"John\",\"surname\":\"Smith\",\"working\":false}";

        mockMvc.perform(MockMvcRequestBuilders.put("/students/{id}", 1L)
                        .content(updatedStudentJson)
                        .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("John"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Smith"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.working").value(false))
                .andDo(print());
    }

    @Test
    public void testUpdateIsWorking() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/students/{id}", 1L)
                        .param("working", "true"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.working").value(true))
                .andDo(print());
    }

    @Test
    public void testDeleteStudent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/students/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }
}

