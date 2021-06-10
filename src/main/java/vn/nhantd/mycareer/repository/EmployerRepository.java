package vn.nhantd.mycareer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.nhantd.mycareer.model.employeer.Employer;

public interface EmployerRepository extends MongoRepository<Employer, String> {
}
