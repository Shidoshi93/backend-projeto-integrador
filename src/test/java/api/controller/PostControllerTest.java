package api.controller;

import Api.controller.PostController;
import Api.model.Post;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
@WithMockUser(username = "josefina@gmail.com")
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    PostRepository postRepository;

    @Test
    public void shouldSavePost() throws Exception {
        Post post = new Post(2, "doador", "cesta básica", "Preciso de duas cestas básicas", 2, "ativo",2);
        when(postRepository.save(post)).thenReturn(post);
        this.mockMvc.perform(post("/post/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(post)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("post_id").value(2))
                .andExpect(jsonPath("user_type").value("doador"))
                .andExpect(jsonPath("donation_type").value("cesta básica"))
                .andExpect(jsonPath("description").value("Preciso de duas cestas básicas"))
                .andExpect(jsonPath("qtd").value(2))
                .andExpect(jsonPath("user").value(2));
    }


}
