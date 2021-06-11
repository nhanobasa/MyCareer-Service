package vn.nhantd.mycareer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import vn.nhantd.mycareer.model.job.CareerCategory;
import vn.nhantd.mycareer.repository.CareerCategoryRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mycareer/api/v1/category")
public class CareerCategoryController {

    @Autowired
    CareerCategoryRepository categoryRepository;
    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CareerCategory createCareerCategory(@Valid @RequestBody CareerCategory job) {

        try {
            Optional<CareerCategory> checkCareerCategory = categoryRepository.findById(job.get_id());
            if (!checkCareerCategory.isPresent()) {
                categoryRepository.save(job);
                return job;
            }
        } catch (Exception e) {
            if (e.getMessage().equals("The given id must not be null!")) {
                categoryRepository.save(job);
                return job;
            }
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<CareerCategory> getAllCategory() {
        return categoryRepository.findAll();
    }

    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public List<CareerCategory> getTopCategory() {
        Sort sortJob = new Sort(Sort.Direction.DESC, "total_job");
        Sort sortUser = new Sort(Sort.Direction.DESC, "total_user");
        Query query = new Query();
        query = query.with(sortJob).with(sortUser);
        
        List<CareerCategory> list = mongoTemplate.find(query, CareerCategory.class);
        return list;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public CareerCategory getCategoryById(@RequestParam(value = "id", defaultValue = "") String id,
                                          @RequestParam(value = "name", defaultValue = "") String name) {
        Optional<CareerCategory> careerCategory = null;
        careerCategory = categoryRepository.findById(id);
        if (careerCategory.isPresent()) {
            return careerCategory.get();
        } else {
            careerCategory = categoryRepository.findByName(name);
            if (careerCategory.isPresent()) {
                return careerCategory.get();
            }
        }
        return null;
    }
}
