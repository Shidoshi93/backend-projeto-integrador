package api.repository;

import Api.controller.PostController;
import Api.model.Address;
import Api.model.Post;
import Api.model.User;
import Api.repository.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
@WithMockUser(username = "josefina@gmail.com")
public class PostRepositoryTest {

    @Autowired
    private MockMvc mockMvc;
    private  User user;
    private Address address;

    @MockBean
    PostRepository postRepository;

    @Test
    public void shouldSavePost() throws Exception {
        Post post = new Post(2, "doador", "cesta básica", "Preciso de duas cestas básicas", 2, "ativo",user, address);
        when(postRepository.save(post)).thenReturn(post);
        this.mockMvc.perform(post("/post/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(post)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnOnePostFindById() throws Exception {
        Optional<Post> post = Optional.of(new Post(2, "doador", "cesta básica", "Preciso de duas cestas básicas", 2, "ativo",user, address));
        when(postRepository.findById(2)).thenReturn(post);
        this.mockMvc.perform(get("/post/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("post_id").value(2))
                .andExpect(jsonPath("user_type").value("doador"))
                .andExpect(jsonPath("donation_type").value("cesta básica"))
                .andExpect(jsonPath("description").value("Preciso de duas cestas básicas"))
                .andExpect(jsonPath("qtd").value(2))
                .andExpect(jsonPath("status").value("ativo"))
                .andExpect(jsonPath("user").value(user));
    }


    @Test
    public void shouldUpdatePostById() throws Exception {
        Post post = new Post(2, "doador", "cesta básica", "Preciso de duas cestas básicas", 2, "ativo",user, address);
        when(postRepository.findById(2)).thenReturn(Optional.of(post));
        this.mockMvc.perform(patch("/post/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(post)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateOnlyPostStatusById() throws Exception {
        Post post = new Post(2, "doador", "cesta básica", "Preciso de duas cestas básicas", 2, "ativo",user, address);
        when(postRepository.save(post)).thenReturn(post);
        this.mockMvc.perform(post("/post/save")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(post)));
        Post post2 = new Post(2, "doador", "cesta básica", "Preciso de duas cestas básicas", 2, "inativo",user, address);
        when(postRepository.findById(2)).thenReturn(Optional.of(post2));
        this.mockMvc.perform(patch("/post/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(post2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("post_id").value(2))
                .andExpect(jsonPath("user_type").value("doador"))
                .andExpect(jsonPath("donation_type").value("cesta básica"))
                .andExpect(jsonPath("description").value("Preciso de duas cestas básicas"))
                .andExpect(jsonPath("qtd").value(2))
                .andExpect(jsonPath("status").value("inativo"))
                .andExpect(jsonPath("user").value(user))
                .andReturn();
    }

    @Test
    public void shouldDeletePostById() throws Exception {
        Optional<Post> post = Optional.of(new Post(2, "doador", "cesta básica", "Preciso de duas cestas básicas", 2, "ativo",user, address));
        when(postRepository.findById(2)).thenReturn(post);
        this.mockMvc.perform(delete("/post/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldNotDeletePostNotFound() throws Exception {
        Optional<Post> post = Optional.of(new Post(2, "doador", "cesta básica", "Preciso de duas cestas básicas", 2, "ativo",user, address));
        when(postRepository.findById(2)).thenReturn(post);
        this.mockMvc.perform(delete("/post/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
