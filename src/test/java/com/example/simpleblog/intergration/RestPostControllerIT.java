package com.example.simpleblog.intergration;

import com.example.simpleblog.adapter.in.dto.CreatePostRequest;
import com.example.simpleblog.domain.models.Post;
import com.example.simpleblog.domain.repositories.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Rest Post Controller Integration Tests")
@DirtiesContext
class RestPostControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("Create post should return created post when request is valid")
    void createPost_ShouldReturnCreatedPost_WhenRequestIsValid() throws Exception {
        // Arrange
        CreatePostRequest request = new CreatePostRequest("Valid Title", "Valid Content Here", "Valid Author");

        // Act & Assert
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.title").value("Valid Title"))
                .andExpect(jsonPath("$.content").value("Valid Content Here"))
                .andExpect(jsonPath("$.author").value("Valid Author"))
                .andExpect(jsonPath("$.createdAt").exists())
                .andExpect(jsonPath("$.updatedAt").exists());
    }

    @Test
    @DisplayName("Create post should return bad request when request is invalid")
    void createPost_ShouldReturnBadRequest_WhenRequestIsInvalid() throws Exception {
        // Arrange
        CreatePostRequest invalidRequest = new CreatePostRequest("", "short", "");

        // Act & Assert
        mockMvc.perform(post("/api/v1/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Request validation failed"))
                .andExpect(jsonPath("$.invalidParams", hasSize(5)))
                .andExpect(jsonPath("$.invalidParams[*].fieldName").value(containsInAnyOrder("title", "title", "content", "author", "author")));
    }

    @Test
    @DisplayName("Get post by ID should return post when it exists")
    void getPostById_ShouldReturnPost_WhenPostExists() throws Exception {
        // Arrange
        Post post = Post.builder()
                .title("Existing Post")
                .content("Existing Content")
                .author("Existing Author")
                .createdAt("1743029604534")
                .updatedAt("1743029604534")
                .build();
        Post savedPost = postRepository.save(post);

        // Act & Assert
        mockMvc.perform(get("/api/v1/posts/" + savedPost.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedPost.getId()))
                .andExpect(jsonPath("$.title").value("Existing Post"))
                .andExpect(jsonPath("$.content").value("Existing Content"))
                .andExpect(jsonPath("$.author").value("Existing Author"))
                .andExpect(jsonPath("$.createdAt").value("1743029604534"))
                .andExpect(jsonPath("$.updatedAt").value("1743029604534"));
    }

    @Test
    @DisplayName("Get post by ID should return not found when post does not exist")
    void getPostById_ShouldReturnNotFound_WhenPostDoesNotExist() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/api/v1/posts/999")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.detail").value("Category with id 999 not found"))
                .andExpect(jsonPath("$.title").value("Post Not Found"));
    }
}