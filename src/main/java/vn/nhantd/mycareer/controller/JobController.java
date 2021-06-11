package vn.nhantd.mycareer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;
import vn.nhantd.mycareer.model.job.Job;
import vn.nhantd.mycareer.repository.JobRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mycareer/api/v1/job")
public class JobController {
    @Autowired
    JobRepository jobRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Job createJob(@Valid @RequestBody Job job) {
        job.setDt(System.currentTimeMillis());

        try {
            Optional<Job> checkJob = jobRepository.findById(job.get_id());
            if (!checkJob.isPresent()) {
                jobRepository.save(job);
                return job;
            }
        } catch (Exception e) {
            if (e.getMessage().equals("The given id must not be null!")) {
                jobRepository.save(job);
                return job;
            }
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public Job initJob() {
        try {

            Job job = new Job();
            jobRepository.save(job);
            return job;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public Job getJobById(@Valid @RequestParam String id) {
        Optional<Job> job = null;
        job = jobRepository.findById(id);
        if (job.isPresent()) {
            return job.get();
        }
        return null;
    }
}
