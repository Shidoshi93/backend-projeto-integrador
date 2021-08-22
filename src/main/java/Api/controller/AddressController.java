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
    @ResponseStatus(HttpStatus.CREATED)
    public String saveAddress(@RequestBody Address address){
        addressRepository.save(address);
        return "Endereço salvo com sucesso.";
    }

    @GetMapping("/listAll")
    @ResponseStatus(HttpStatus.OK)
    public List<Address> getAddress(){
        return (List<Address>) addressRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Address getAddressById(@PathVariable("id") Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()){
            return optionalAddress.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado!");
        }
    }

    @RequestMapping(value = "/userId/{user_id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Address getAddresByUserId(@PathVariable("user_id") Integer user_id) {
        Optional<Address> optionalUser = Optional.ofNullable(addressRepository.findByUserId(user_id));
        if (optionalUser.isPresent()){
            return optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address não encontrado!");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public Address updateAddress(@PathVariable Integer id, @RequestBody Address address) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isPresent()) {
            optionalAddress.map(record -> {
                record.setCity(address.getCity());
                record.setNeighborhood(address.getNeighborhood());
                record.setState(address.getState());
                record.setPostalCode(address.getPostalCode());
                Address updated = addressRepository.save(record);
                return ResponseEntity.ok().body(updated);
            });
            return optionalAddress.get();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
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
