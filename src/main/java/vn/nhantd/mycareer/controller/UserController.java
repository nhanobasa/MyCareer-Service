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

    @RequestMapping(value = "/profile", method = RequestMethod.POST)
    public User getUserById(@Valid @RequestBody User u) {
        User user = null;
        if (u.get_id() != null)
//            user = userRepository.findBy_id(u.get_id());
            user = userRepository.findByUid(u.getUid());
        else
            user = userRepository.findByUid(u.getUid());

        return user;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void modifyUserById(@PathVariable("id") ObjectId id, @Valid @RequestBody User user) {
//        user.set_id(id);
        try {
            userRepository.delete(userRepository.findByUid(user.getUid()));
            userRepository.save(user);
        } catch (Exception e) {
            userRepository.save(user);
        }

    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public User createUser(@Valid @RequestBody User user) {
        User checkUser = userRepository.findByUid(user.getUid());
        if (checkUser == null) {
            userRepository.save(user);
        }
        return null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable ObjectId id) {
        userRepository.delete(userRepository.findBy_id(id));
    }


}
