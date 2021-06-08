package vn.nhantd.mycareer.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import vn.nhantd.mycareer.model.user.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUid(String uid);

    User findBy_id(ObjectId _id);
}
