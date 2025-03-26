package com.example.simpleblog.unit;


import com.example.simpleblog.application.usecases.CreatePostUseCase;
import com.example.simpleblog.application.usecases.GetPostByIdUseCase;
import com.example.simpleblog.domain.models.Post;
import com.example.simpleblog.domain.repositories.PostRepository;
import com.example.simpleblog.shared.PostNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Post Use Case Tests")
@ExtendWith(MockitoExtension.class)
class PostUseCaseTest {

    @Mock
    private PostRepository postRepository;

    private GetPostByIdUseCase getPostByIdUseCase;

    private CreatePostUseCase createPostUseCase;

    @BeforeEach
    void setUp() {
        getPostByIdUseCase = new GetPostByIdUseCase(postRepository); // Manual injection
        createPostUseCase = new CreatePostUseCase(postRepository);
    }

    // Тести для GetPostByIdUseCase
    @Test
    @DisplayName("GetPostById should return post when it exists")
    void getPostById_ShouldReturnPost_WhenPostExists() {
        // Arrange
        Long postId = 1L;
        Post post = Post.builder()
                .id(postId)
                .title("Test Title")
                .content("Test Content")
                .author("Test Author")
                .createdAt("2023-01-01")
                .updatedAt("2023-01-01")
                .build();
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        GetPostByIdUseCase.Input input = GetPostByIdUseCase.Input.builder().id(postId).build();

        // Act
        GetPostByIdUseCase.Output output = getPostByIdUseCase.execute(input);

        // Assert
        assertNotNull(output);
        assertEquals(postId, output.getId());
        assertEquals(post.getTitle(), output.getTitle());
        assertEquals(post.getContent(), output.getContent());
        assertEquals(post.getAuthor(), output.getAuthor());
        assertEquals(post.getCreatedAt(), output.getCreatedAt());
        assertEquals(post.getUpdatedAt(), output.getUpdatedAt());
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    @DisplayName("GetPostById should throw exception when post does not exist")
    void getPostById_ShouldThrowException_WhenPostDoesNotExist() {
        // Arrange
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());
        GetPostByIdUseCase.Input input = GetPostByIdUseCase.Input.builder().id(postId).build();

        // Act & Assert
        PostNotFoundException exception = assertThrows(PostNotFoundException.class, () -> getPostByIdUseCase.execute(input));
        assertEquals("Category with id 1 not found", exception.getMessage());
        verify(postRepository).findById(postId);
    }

    // Тести для CreatePostUseCase
    @Test
    @DisplayName("CreatePost should create and return post")
    void createPost_ShouldCreateAndReturnPost() {
        // Arrange
        CreatePostUseCase.Input input = CreatePostUseCase.Input.builder()
                .title("New Post")
                .content("Post Content")
                .author("Author Name")
                .build();

        Post savedPost = Post.builder()
                .id(1L)
                .title(input.getTitle())
                .content(input.getContent())
                .author(input.getAuthor())
                .createdAt("2023-01-01")
                .updatedAt("2023-01-01")
                .build();

        when(postRepository.save(any(Post.class))).thenReturn(savedPost);

        // Act
        CreatePostUseCase.Output output = createPostUseCase.execute(input);

        // Assert
        assertNotNull(output);
        assertEquals(savedPost.getId(), output.getId());
        assertEquals(savedPost.getTitle(), output.getTitle());
        assertEquals(savedPost.getContent(), output.getContent());
        assertEquals(savedPost.getAuthor(), output.getAuthor());
        assertNotNull(output.getCreatedAt());
        assertNotNull(output.getUpdatedAt());
        verify(postRepository, times(1)).save(any(Post.class));
    }
}
