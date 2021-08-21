package api.repository;

import Api.controller.AddressController;
import Api.model.Address;
import Api.model.User;
import Api.repository.AddressRepository;
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
@WebMvcTest(AddressController.class)
@AutoConfigureMockMvc
@WithMockUser(username = "josefina@gmail.com")
public class AddressRepositoryTest {
    @Autowired
    private MockMvc mockMvc;
    private User user;

    @MockBean
    AddressRepository addressRepository;

    @Test
    public void shouldSaveAddress() throws Exception {
        Address address = new Address(2, "Marechel Hermes", "Rio de Janeiro", "RJ", "21555-500", user);
        when(addressRepository.save(address)).thenReturn(address);
        this.mockMvc.perform(post("/address/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(address)))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldReturnOneAddressFindById() throws Exception {
        Optional<Address> address = Optional.of(new Address(2, "Marechel Hermes", "Rio de Janeiro", "RJ", "21555-500", user));
        when(addressRepository.findById(2)).thenReturn(address);
        this.mockMvc.perform(get("/address/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("address_id").value(2))
                .andExpect(jsonPath("neighborhood").value("Marechel Hermes"))
                .andExpect(jsonPath("city").value("Rio de Janeiro"))
                .andExpect(jsonPath("state").value("RJ"))
                .andExpect(jsonPath("postalCode").value("21555-500"))
                .andExpect(jsonPath("user").value(user));
    }

    @Test
    public void shouldUpdateAddressById() throws Exception {
        Optional<Address> address = Optional.of(new Address(2, "Marechel Hermes", "Rio de Janeiro", "RJ", "21555-500", user));
        when(addressRepository.findById(2)).thenReturn(address);
        this.mockMvc.perform(patch("/address/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(address)))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteAddressById() throws Exception {
        Optional<Address> address = Optional.of(new Address(2, "Marechel Hermes", "Rio de Janeiro", "RJ", "21555-500", user));
        when(addressRepository.findById(2)).thenReturn(address);
        this.mockMvc.perform(delete("/address/2")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldNotDeleteAddressNotFound() throws Exception {
        Optional<Address> address = Optional.of(new Address(2, "Marechel Hermes", "Rio de Janeiro", "RJ", "21555-500", user));
        when(addressRepository.findById(2)).thenReturn(address);
        this.mockMvc.perform(delete("/address/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
