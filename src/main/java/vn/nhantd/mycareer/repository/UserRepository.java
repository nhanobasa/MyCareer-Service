package vn.nhantd.mycareer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.nhantd.mycareer.model.user.User;

public interface UserRepository extends MongoRepository<User, String> {
}
