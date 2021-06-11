package vn.nhantd.mycareer.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import vn.nhantd.mycareer.model.job.CareerCategory;

import java.util.Optional;

public interface CareerCategoryRepository extends MongoRepository<CareerCategory, String> {
    Optional<CareerCategory> findByName(String name);
}
