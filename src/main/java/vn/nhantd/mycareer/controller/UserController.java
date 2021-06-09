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
    public User getUserById(@Valid @RequestParam String u) {
        Optional<User> user = null;
        if (u != null)
            user = userRepository.findById(u);
//            user = userRepository.findByUid(u.getUid());
        else
            user = userRepository.findById(u);

        return user.get();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void modifyUserById(@PathVariable("id") ObjectId id, @Valid @RequestBody User user) {
//        user.set_id(id);
        try {
            userRepository.delete(userRepository.findById(user.getId()).get());
            userRepository.save(user);
        } catch (Exception e) {
            userRepository.save(user);
        }

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public User createUser(@Valid @RequestBody User user) {
        User checkUser = userRepository.findById(user.getId()).get();
        if (checkUser == null) {
            userRepository.save(user);
        }
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable String id) {
        userRepository.delete(userRepository.findById(id).get());
    }


}
