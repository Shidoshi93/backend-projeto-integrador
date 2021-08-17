package api.controller;

import Api.controller.UserController;
import Api.model.User;
import Api.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@WithMockUser(username = "rachel@gmail.com")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;
    private User userTest;

    @Test
    public void shouldReturnOneUserFindById() throws Exception {
        Optional<User> user = Optional.of(new User(2,"Rachel", "rachel@gmail.com","1234567890","99999999", "123456"));
        when(userRepository.findById(2)).thenReturn(user);
        this.mockMvc.perform(get("/user/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("user_id").value(2));
    }

    @Test
    public void shouldNotReturnOneUserFindByIdWithoutParam() throws Exception {
        Optional<User> user = Optional.of(new User(2,"Rachel", "rachel@gmail.com","1234567890","99999999", "123456"));
        when(userRepository.findById(2)).thenReturn(user);
        this.mockMvc.perform(get("/user/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

   @Test
    public void shouldNotReturnOneUserFindByIdWithDiferentParam() throws Exception {
        this.mockMvc.perform(get("/user/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnAllUsers() throws Exception {
        List<User> listUsers = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(listUsers);
        this.mockMvc.perform(get("/user/list")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void shouldAddUser() throws Exception {
        User user = new User(2,"Rachel", "rachel@gmail.com","1234567890","99999999", "123456");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void shouldNotAddUserWithoutRequiredField() throws Exception {
        User user = new User(2,"Rachel", "rachel@gmail.com","","99999999", "123456");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

  /* public void shouldReturnUserNotFoundById() throws Exception {
        Optional<User> user = Optional.of(new User(2,"Rachel", "rachel@gmail.com","1234567890","99999999", "123456"));
        when(userRepository.findById(1)).thenReturn(user);
        this.mockMvc.perform(get("/user/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("user_id").value(1));
    }*/



}
