package vn.nhantd.mycareer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.nhantd.mycareer.model.employeer.Employer;
import vn.nhantd.mycareer.repository.EmployerRepository;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/mycareer/api/v1/employeer")
public class EmployerController {
    @Autowired
    EmployerRepository employerRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Employer createEmployeer(@Valid @RequestBody Employer employer) {
        try {
            Optional<Employer> checkEmployeer = employerRepository.findById(employer.get_id());
            if (!checkEmployeer.isPresent()) {
                employerRepository.save(employer);
                return employer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public Employer initEmployeer() {
        try {

            Employer employer = new Employer();
            employerRepository.save(employer);
            return employer;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
