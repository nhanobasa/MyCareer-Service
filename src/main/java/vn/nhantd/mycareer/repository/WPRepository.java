package vn.nhantd.mycareer.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import vn.nhantd.mycareer.model.user.WorkProgress;

import java.util.List;

public interface WPRepository extends MongoRepository<WorkProgress, ObjectId> {

    List<WorkProgress> findAllQuery(Query query);
}
