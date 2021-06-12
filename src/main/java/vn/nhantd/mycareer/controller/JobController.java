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
import vn.nhantd.mycareer.model.job.Job;
import vn.nhantd.mycareer.model.user.User;
import vn.nhantd.mycareer.model.user.User_career_goals;
import vn.nhantd.mycareer.repository.JobRepository;
import vn.nhantd.mycareer.service.JobService;
import vn.nhantd.mycareer.service.UserService;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
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
        if (job.getTransactions() == null) {
            job.setTransactions(new ArrayList<>());
        }

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
        List<Job> list = jobService.getAllJobs(limit);
        return list;
    }

    @RequestMapping(value = "/all/{user_id}", method = RequestMethod.GET)
    public List<Job> getJobForUser(@RequestParam(value = "limit", defaultValue = "-1") int limit,
                                   @PathVariable(value = "user_id") String _id) { // _id of user

        List<Job> list = jobService.getJobForUser(_id, limit);
        if (list.isEmpty()) {
            list = jobService.getAllJobs(limit);
        }
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

    @RequestMapping(value = "/transaction/{id}", method = RequestMethod.POST)
    public String setJobTransaction(@Valid @PathVariable(value = "id") String _id,
                                    @RequestBody Transaction transaction) {
        // tạo query lọc ra document theo _id
        Query query = new Query(Criteria.where("_id").is(_id));

        // Update
        Update update = new Update();
        update = update.push("transactions", transaction);
        UpdateResult result = mongoTemplate.updateFirst(query, update, Job.class);
        if (result.getModifiedCount() > 0)
            return "OK";
        return "";

    }
}
