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


    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody User newUser) {

        if (userRepository.findByCpf(newUser.getCpf()) != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já cadastrado!");
        } else {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            userRepository.save(newUser);
            return newUser;
        }
    }


    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> listUsers() {
        return (List<User>) userRepository.findAll();

    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getUserById(@PathVariable("id") Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public User getUserByEmail(@RequestParam("email") String email) {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByEmail(email));
        if (optionalUser.isPresent()){
            return optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable Integer id, @RequestBody User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            optionalUser.map(record -> {
                record.setUser_name(user.getUser_name());
                record.setEmail(user.getEmail());
                record.setCellphone(user.getCellphone());
                record.setUser_img(user.getUser_img());
                record.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                User updated = userRepository.save(record);
                return ResponseEntity.ok().body(updated);
            });
            return optionalUser.get();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            userRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }
    }
}