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

    //SALVA
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User saveUser(@RequestBody User newUser) {

        User userEmail = userRepository.findByEmail(newUser.getEmail());
        User userCpf = userRepository.findByCpf(newUser.getCpf());

        if ((userEmail != null && userCpf.getCpf().equals(newUser.getCpf())) ||
                (userCpf != null && userEmail.getEmail().equals(newUser.getEmail()))){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário já cadastrado!");
        } else {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            userRepository.save(newUser);
            return newUser;
        }
       /* String userEmail = userRepository.findByEmail(newUser.getEmail()).getEmail();
        String userCpf = userRepository.findByCpf(newUser.getCpf()).getCpf();

        if ((userEmail == null && userCpf.equals(newUser.getCpf())) ||
                (userCpf == null && userEmail.equals(newUser.getEmail()))){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuário já cadastrado!");
        } else {
            newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
            userRepository.save(newUser);
            return newUser;
        }*/
    }

    //LISTA TODOS
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<User> listUsers() {
        return (List<User>) userRepository.findAll();

    }
    //LISTA POR ID
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable("id") Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            return optionalUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }
    }
    //ATUALIZA
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable Integer id, @RequestBody User user) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            optionalUser.map(record -> {
                record.setName(user.getName());
                record.setEmail(user.getEmail());
                record.setCellphone(user.getCellphone());
                record.setUser_photo(user.getUser_photo());
                record.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                User updated = userRepository.save(record);
                return ResponseEntity.ok().body(updated);
            });
            return optionalUser.get();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }
    }
    //DELETA POR ID
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
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