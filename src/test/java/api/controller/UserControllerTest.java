package api.controller;

import Api.controller.UserController;
import Api.model.User;
import Api.repository.UserRepository;
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
    public void shouldSaveUser() throws Exception {
        User user = new User(2,"Rachel", "rachel@gmail.com","1234567890","99999999", "123456", "ajkhskaujoaqas");
        when(userRepository.save(user)).thenReturn(user);
        this.mockMvc.perform(post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(2))
                .andExpect(jsonPath("name").value("Rachel"))
                .andExpect(jsonPath("email").value("rachel@gmail.com"))
                .andExpect(jsonPath("cpf").value("1234567890"))
                .andExpect(jsonPath("cellphone").value("99999999"));
    }

    @Test
    public void shouldNotSaveUserWithSameEmail() throws Exception {
        User user = new User(2,"Rachel", "rachel@gmail.com","1234567890","99999999", "123456", "ajkhskaujoaqas");
        User user3 = new User(3,"Josefina", "rachel@gmail.com","0123456789","88888888", "123456", "ajkhskaujoaqas");

        when(userRepository.save(user)).thenReturn(user);
        this.mockMvc.perform(post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(2))
                .andExpect(jsonPath("name").value("Rachel"))
                .andExpect(jsonPath("email").value("rachel@gmail.com"))
                .andExpect(jsonPath("cpf").value("1234567890"))
                .andExpect(jsonPath("cellphone").value("99999999"));


        when(userRepository.save(user3)).thenReturn(user3);
        this.mockMvc.perform(post("/user/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user3)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnOneUserFindById() throws Exception {
        Optional<User> user = Optional.of(new User(2,"Rachel", "rachel@gmail.com","1234567890","99999999", "123456", "ajkhskaujoaqas"));
        when(userRepository.findById(2)).thenReturn(user);
        this.mockMvc.perform(get("/user/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(2))
                .andExpect(jsonPath("name").value("Rachel"))
                .andExpect(jsonPath("email").value("rachel@gmail.com"))
                .andExpect(jsonPath("cpf").value("1234567890"))
                .andExpect(jsonPath("cellphone").value("99999999"))
                .andExpect(jsonPath("password").value("123456"));
    }

    @Test
    public void shouldNotReturnUserWithoutParam() throws Exception {
        Optional<User> user = Optional.of(new User(2,"Rachel", "rachel@gmail.com","1234567890","99999999", "123456", "ajkhskaujoaqas"));
        when(userRepository.findById(0)).thenReturn(user);
        this.mockMvc.perform(get("/user/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldNotReturnUserWithDiferentParam() throws Exception {
       User user2 = new User(2,"Rachel", "rachel@gmail.com","1234567890","99999999", "123456", "ajkhskaujoaqas");
       when(userRepository.findById(2)).thenReturn(Optional.of(user2));
       this.mockMvc.perform(get("/user/4")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
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
    public void shouldDeleteUserById() throws Exception {
        Optional<User> user = Optional.of(new User(2,"Rachel", "rachel@gmail.com","1234567890","99999999", "123456", "ajkhskaujoaqas"));
        when(userRepository.findById(2)).thenReturn(user);
        this.mockMvc.perform(delete("/user/delete/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldNotDeleteNotFoundUser() throws Exception {
        Optional<User> user = Optional.of(new User(2,"Rachel", "rachel@gmail.com","1234567890","99999999", "123456", "ajkhskaujoaqas"));
        when(userRepository.findById(2)).thenReturn(user);
        this.mockMvc.perform(delete("/user/delete/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldUpdateUserById() throws Exception {
        User user = new User(2,"Rachel", "rachel@gmail.com","1234567890","99999999", "a1b2c3", "kwshjrjwhreiheiwhfdiwjeiwjehjehjiwekfjojrejeorj");
        when(userRepository.findById(2)).thenReturn(Optional.of(user));
        this.mockMvc.perform(patch("/user/update/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateOnlyUserNameById() throws Exception {
        User user = new User(2,"Rachel", "rachel@gmail.com","1234567890","99999999", "123456", "ajkhskaujoaqas");
        when(userRepository.save(user)).thenReturn(user);
        this.mockMvc.perform(post("/user/add")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)));
        User user3 = new User(2,"Josefina", "rachel@gmail.com","1234567890","99999999", "123456", "ajkhskaujoaqas");
        when(userRepository.findById(2)).thenReturn(Optional.of(user3));
        this.mockMvc.perform(patch("/user/update/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user3)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(2))
                .andExpect(jsonPath("name").value("Josefina"))
                .andExpect(jsonPath("email").value("rachel@gmail.com"))
                .andExpect(jsonPath("cpf").value("1234567890"))
                .andExpect(jsonPath("cellphone").value("99999999"))
                .andReturn();
    }

}




