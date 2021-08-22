package api.controller;

import Api.controller.UserController;
import Api.model.User;
import Api.repository.UserRepository;
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
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
@WithMockUser(username = "josefina@gmail.com")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;
    private UserController userController;

    @Test
    public void shouldNotReturnUserWithoutParam() throws Exception {
        Optional<User> user = Optional.of(new User(2,"Rachel", "rachel@gmail.com","1234567890","99999999", "123456", ""));
        when(userRepository.findById(0)).thenReturn(user);
        this.mockMvc.perform(get("/user/id/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());

    }

    @Test
    public void shouldNotReturnUserWithDiferentParam() throws Exception {
       User user2 = new User(2,"Rachel", "rachel@gmail.com","1234567890","99999999", "123456", "");
       when(userRepository.findById(2)).thenReturn(Optional.of(user2));
       this.mockMvc.perform(get("/user/id/4")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnAllUsers() throws Exception {
        List<User> listUsers = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(listUsers);
        this.mockMvc.perform(get("/user/listAll")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

}




