package vn.nhantd.mycareer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import vn.nhantd.mycareer.model.employeer.Employer;
import vn.nhantd.mycareer.model.user.User;
import vn.nhantd.mycareer.repository.EmployerRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mycareer/api/v1/employer")
public class EmployerController {
    @Autowired
    EmployerRepository employerRepository;

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
    public List<Employer> getAllEmployers() {
        return employerRepository.findAll();
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
}
