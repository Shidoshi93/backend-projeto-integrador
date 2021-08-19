package Api.controller;

import Api.model.User;
import Api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody User user) {
        if(user.getEmail().equals(userRepository.findByEmail(user.getEmail()))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário já cadastrado.");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> listUsers() {
        return (List<User>) userRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") Integer id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado0");
        }
    }

    @RequestMapping(value = "/updateUser/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@PathVariable Integer id, @RequestBody User user) {
        userRepository.findById(id).map(record -> {
            record.setName(user.getName());
            record.setEmail(user.getEmail());
            record.setCellphone(user.getCellphone());
            record.setUser_photo(user.getUser_photo());
            record.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            User updated = userRepository.save(record);
            return ResponseEntity.ok().body(updated);
        }).orElse(ResponseEntity.notFound().build());
    }
}