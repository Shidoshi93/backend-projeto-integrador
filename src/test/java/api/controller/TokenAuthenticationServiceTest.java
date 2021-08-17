package api.controller;

import Api.model.User;
import Api.security.AuthorizationFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TokenAuthenticationServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorizationFilter filter;

    @MockBean
    private User user;

    @Test
    public void shouldNotAllowAcessToUnanthenticatedUsers() throws Exception {
        mockMvc.perform(get("/user")).andExpect(status().isForbidden());
    }


   /* @Test
    public void shouldAuthenticateUsers() throws Exception {
        mockMvc.perform(formLogin("/auth").user("email", user.getEmail()).password("password", user.getPassword()))
                .andExpect(authenticated());
    }*/

}
