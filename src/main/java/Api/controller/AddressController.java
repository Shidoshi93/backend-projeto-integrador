package Api.controller;

import Api.model.Address;
import Api.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressRepository addressRepository;

    @PostMapping("/save")
    public Address saveAddress(@RequestBody Address address){
        return addressRepository.save(address);
    }

    @GetMapping("/list")
    public List<Address> getAddress(){
        return (List<Address>) addressRepository.findAll();
    }
}
