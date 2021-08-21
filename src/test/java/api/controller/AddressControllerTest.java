package api.controller;

import Api.controller.AddressController;
import Api.model.Address;
import Api.model.User;
import Api.repository.AddressRepository;
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
@WebMvcTest(AddressController.class)
@AutoConfigureMockMvc
@WithMockUser(username = "josefina@gmail.com")
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private User user;

    @MockBean
    AddressRepository addressRepository;

    @Test
    public void shouldNotReturnAddressWithoutParam() throws Exception {
        Optional<Address> address = Optional.of(new Address(2, "Marechel Hermes", "Rio de Janeiro", "RJ", "21555-500", user));
        when(addressRepository.findById(0)).thenReturn(address);
        this.mockMvc.perform(get("/address/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    public void shouldNotReturnAddressWithDiferentParam() throws Exception {
        Optional<Address> address = Optional.of(new Address(2, "Marechel Hermes", "Rio de Janeiro", "RJ", "21555-500", user));
        when(addressRepository.findById(2)).thenReturn(address);
        this.mockMvc.perform(get("/address/4")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnAllAddress() throws Exception {
        List<Address> listAddress = new ArrayList<>();
        when(addressRepository.findAll()).thenReturn(listAddress);
        this.mockMvc.perform(get("/address/listAll")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
    }

}
