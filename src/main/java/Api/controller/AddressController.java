package Api.controller;

import Api.model.Address;
import Api.model.User;
import Api.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressRepository addressRepository;

    @PostMapping("/save")
    public String saveAddress(@RequestBody Address address){

        Address newAddress = addressRepository.save(address);
        return "Endereço salvo com sucesso.";
    }

    @GetMapping("/listAll")
    public List<Address> getAddress(){
        return (List<Address>) addressRepository.findAll();
    }

    //LISTA POR ID
    @RequestMapping(value = "/address/{id}", method = RequestMethod.GET)
    public Address getAddressById(@PathVariable("id") Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()){
            return optionalAddress.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado!");
        }
    }
    //ATUALIZA
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public Address updateAddress(@PathVariable Integer id, @RequestBody Address address) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            optionalAddress.map(record -> {
                record.setCity(address.getCity());
                record.setNeighborhood(address.getNeighborhood());
                record.setState(address.getState());
                record.setPostal_code(address.getPostal_code());
                Address updated = addressRepository.save(record);
                return ResponseEntity.ok().body(updated);
            });
            return optionalAddress.get();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }
    }
    //DELETA POR ID
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddress(@PathVariable("id") Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()){
            addressRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }
    }
}
