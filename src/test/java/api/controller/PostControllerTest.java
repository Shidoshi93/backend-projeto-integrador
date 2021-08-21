package api.controller;

import Api.controller.PostController;
import Api.model.Post;
import Api.model.User;
import Api.repository.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
@WithMockUser(username = "josefina@gmail.com")
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private  User user;

    @MockBean
    PostRepository postRepository;

    @Test
    public void shouldNotReturnPostWithoutParam() throws Exception {
        Optional<Post> post = Optional.of(new Post(2, "doador", "cesta básica", "Preciso de duas cestas básicas", 2, "ativo", user));
        when(postRepository.findById(0)).thenReturn(post);
        this.mockMvc.perform(get("/post/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldNotReturnUserWithDiferentParam() throws Exception {
        Post post = new Post(2, "doador", "cesta básica", "Preciso de duas cestas básicas", 2, "ativo", user);
        when(postRepository.findById(2)).thenReturn(Optional.of(post));
        this.mockMvc.perform(get("/post/getPost/4")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnAllPosts() throws Exception {
        List<Post> listPosts = new ArrayList<>();
        when(postRepository.findAll()).thenReturn(listPosts);
        this.mockMvc.perform(get("/post/listAll")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }


}
