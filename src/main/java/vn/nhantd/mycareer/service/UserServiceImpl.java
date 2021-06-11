package vn.nhantd.mycareer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.nhantd.mycareer.model.user.User;
import vn.nhantd.mycareer.repository.UserRepository;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getUserById(String id) {
        Optional<User> user = null;
        user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }
}
