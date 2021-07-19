package Api.controller;

import Api.model.User;
import Api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public User saveUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<User> getUsers(){
        return (List<User>) userRepository.findAll();
    }

}
