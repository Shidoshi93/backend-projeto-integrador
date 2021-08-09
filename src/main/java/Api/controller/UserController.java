package Api.controller;

import Api.model.User;
import Api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public User saveUser(@RequestBody User user){
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<User> getUsers(){
        return (List<User>) userRepository.findAll();
    }

}
