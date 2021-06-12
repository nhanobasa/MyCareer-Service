package vn.nhantd.mycareer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.nhantd.mycareer.model.job.Job;
import vn.nhantd.mycareer.model.user.User;
import vn.nhantd.mycareer.model.user.User_career_goals;
import vn.nhantd.mycareer.repository.JobRepository;

import java.util.List;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    UserService userService;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Job> getJobForUser(String _id, int limit) {
        Sort sortNew = new Sort(Sort.Direction.DESC, "dt");
        Query query = new Query();
        if (limit != -1) {
            query = query.with(sortNew).limit(limit);
        } else {
            query = query.with(sortNew);
        }

        // Lấy thông tin của user
        User user = userService.getUserById(_id);
        User_career_goals careerGoals = user.getCareer_goals();

        // Lọc theo tiêu chí tuyển dụng của job
        query = query.addCriteria(Criteria
                        .where("category").is(careerGoals.getCategory())
                        .and("level").is(careerGoals.getLevel())
                        .and("address").is(careerGoals.getAddress())
//                .and("salary").regex(".*" + careerGoals.getSalary().toString() + ".*")
        );

        return mongoTemplate.find(query, Job.class);
    }

    @Override
    public List<Job> getJobOfEmployer(String employer_id, String status, int limit) {
        Sort sortNew = new Sort(Sort.Direction.DESC, "dt");
        Query query = new Query();
        if (limit != -1) {
            query = query.with(sortNew).limit(limit);
        } else {
            query = query.with(sortNew);
        }

        // Lấy các bản ghi có employer_id thỏa mãn
        query = query.addCriteria(Criteria
                .where("employer_id").is(employer_id)
        );

        // Kiểm tra xem có lọc theo job status hay ko?
        if (!status.equals("")) {
            query = query.addCriteria(Criteria.where("status").is(status));
        }

        return mongoTemplate.find(query, Job.class);
    }

    @Override
    public List<Job> getAllJobs(int limit) {
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
}
