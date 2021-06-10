package vn.nhantd.mycareer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import vn.nhantd.mycareer.model.user.User;
import vn.nhantd.mycareer.model.user.WorkProgress;
import vn.nhantd.mycareer.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;
import vn.nhantd.mycareer.repository.WPRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mycareer/api/v1/user")
public class UserController {

    private final UserRepository userRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;

    }


    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public User getUserById(@Valid @RequestParam String id) {
        Optional<User> user = null;
        user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
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
