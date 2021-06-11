package vn.nhantd.mycareer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import vn.nhantd.mycareer.model.user.User;
import vn.nhantd.mycareer.repository.UserRepository;
import org.springframework.web.bind.annotation.*;
import vn.nhantd.mycareer.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mycareer/api/v1/user")
public class UserController {

    private final UserRepository userRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserService userService;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public User getUserById(@Valid @RequestParam String id) {
        User user = userService.getUserById(id);

        return user;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String modifyUserById(@PathVariable("id") String id, @Valid @RequestBody User user) {
        try {
            userRepository.save(user);
            return "OK";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public User createUser(@Valid @RequestBody User user) {
        try {
            Optional<User> checkUser = userRepository.findById(user.getId());
            if (!checkUser.isPresent()) {
                userRepository.save(user);
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable String id) {
        userRepository.delete(userRepository.findById(id).get());
    }


}
