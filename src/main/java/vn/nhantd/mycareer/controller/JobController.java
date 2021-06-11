package vn.nhantd.mycareer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;
import vn.nhantd.mycareer.model.job.Job;
import vn.nhantd.mycareer.model.user.User;
import vn.nhantd.mycareer.model.user.User_career_goals;
import vn.nhantd.mycareer.repository.JobRepository;
import vn.nhantd.mycareer.service.JobService;
import vn.nhantd.mycareer.service.UserService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mycareer/api/v1/job")
public class JobController {
    @Autowired
    JobRepository jobRepository;

    @Autowired
    MongoTemplate mongoTemplate;


    @Autowired
    JobService jobService;

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
    public List<Job> getAllJobs(@RequestParam(value = "limit", defaultValue = "-1") int limit) {
        Sort sortNew = new Sort(Sort.Direction.DESC, "dt");
        Query query = new Query();
        if (limit != -1) {
            query = query.with(sortNew).limit(limit);
        } else {
            query = query.with(sortNew);
        }
        query = query.addCriteria(Criteria.where("status").is("active"));

        return mongoTemplate.find(query, Job.class);
    }

    @RequestMapping(value = "/all/{user_id}", method = RequestMethod.GET)
    public List<Job> getJobForUser(@RequestParam(value = "limit", defaultValue = "-1") int limit,
                                   @PathVariable(value = "user_id") String _id) { // _id of user

        List<Job> list = jobService.getJobForUser(_id, limit);

        return list;
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
