package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.demo.model.Comment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@SpringBootTest
@AutoConfigureMockMvc


public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
	void contextLoads() {
		assertTrue(true);
	}

    @Test
  	void getCase1CommentAPI() throws Exception {
		mvc.perform(MockMvcRequestBuilders
        .get("/comments/1")
        .accept(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value("Comment 01"));        
	}

    @Test
    void getCase2CommentAPI() throws Exception {
      mvc.perform(MockMvcRequestBuilders
      .get("/comments/3")
      .accept(MediaType.APPLICATION_JSON))
      .andDo(MockMvcResultHandlers.print())
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value("Comment 03"));        
  }

      @Test
    void createCase1CommentAPI() throws Exception {
      mvc.perform(MockMvcRequestBuilders
      .post("/comments")
      .content(asJsonString(new Comment("Testing Create API")))
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isCreated())
      .andExpect(MockMvcResultMatchers.jsonPath("$.comment").exists());        
  }

    public static String asJsonString(final Object obj) {
        try{ ObjectMapper o = new ObjectMapper();
             o.registerModule(new JavaTimeModule()); 
            return o.writeValueAsString(obj);
            //return new ObjectMapper().writeValueAsString(obj);

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Test
    void updateCase1CommentAPI() throws Exception {
      mvc.perform(MockMvcRequestBuilders
      .put("/comments/{id}", 2)
      .content(asJsonString(new Comment("Updating API")))
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(MockMvcResultMatchers.status().isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.comment").value("Updating API"));               
  }

    @Test
    void deleteCommentAPI() throws Exception {
      mvc.perform(MockMvcRequestBuilders
      .delete("/comments/{id}", 1))
      .andExpect(MockMvcResultMatchers.status().isOk());
      
  }
    
}
