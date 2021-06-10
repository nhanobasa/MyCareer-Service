package vn.nhantd.mycareer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.nhantd.mycareer.model.job.Job;

public interface JobRepository extends MongoRepository<Job, String> {
}
