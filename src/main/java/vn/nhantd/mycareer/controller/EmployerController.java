package vn.nhantd.mycareer.controller;

import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.*;
import vn.nhantd.mycareer.model.Transaction;
import vn.nhantd.mycareer.model.employeer.Employer;
import vn.nhantd.mycareer.model.job.Job;
import vn.nhantd.mycareer.model.user.User;
import vn.nhantd.mycareer.repository.EmployerRepository;
import vn.nhantd.mycareer.service.JobService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mycareer/api/v1/employer")
public class EmployerController {
    @Autowired
    EmployerRepository employerRepository;

    @Autowired
    JobService jobService;

    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Employer createEmployer(@Valid @RequestBody Employer employer) {
        try {
            Optional<Employer> checkEmployer = employerRepository.findById(employer.get_id());
            if (!checkEmployer.isPresent()) {
                employerRepository.save(employer);
                return employer;
            }
        } catch (Exception e) {
            if (e.getMessage().equals("The given id must not be null!")) {
                employerRepository.save(employer);
                return employer;
            }
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public Employer initEmployer() {
        try {

            Employer employer = new Employer();
            employerRepository.save(employer);
            return employer;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Employer> getAllEmployers(@RequestParam(value = "limit", defaultValue = "-1") int limit) {
        List<Employer> list = new ArrayList<>();
        Sort sortNew = new Sort(Sort.Direction.DESC, "dt");
        Query query = new Query();
        if (limit != -1) {
            query = query.with(sortNew).limit(limit);
        } else {
            query = query.with(sortNew);
        }
        query = query.addCriteria(Criteria.where("status").is("active"));

        return mongoTemplate.find(query, Employer.class);
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public Employer getEmployerById(@RequestParam String id) {
        Optional<Employer> employer = null;
        employer = employerRepository.findById(id);
        if (employer.isPresent()) {
            return employer.get();
        }
        return null;
    }

    @RequestMapping(value = "/job/{id}", method = RequestMethod.GET)
    public List<Job> getAllEmployers(@PathVariable(value = "id") String employer_id,
                                     @RequestParam(value = "status", defaultValue = "") String status,
                                     @RequestParam(value = "limit", defaultValue = "-1") int limit) {
        List<Job> list = jobService.getJobOfEmployer(employer_id, status, limit);

        return list;
    }

    @RequestMapping(value = "/transaction/{id}", method = RequestMethod.POST)
    public String setEmployerTransaction(@Valid @PathVariable(value = "id") String _id,
                                    @RequestBody Transaction transaction) {
        // tạo query lọc ra document theo _id
        Query query = new Query(Criteria.where("_id").is(_id));

        // Update
        Update update = new Update();
        update = update.push("transactions", transaction);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Employer.class);
        if (result.getModifiedCount() > 0)
            return "OK";
        return "";

    }


}
