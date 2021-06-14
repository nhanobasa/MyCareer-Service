package vn.nhantd.mycareer.service;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.nhantd.mycareer.model.Transaction;
import vn.nhantd.mycareer.model.ViewCV;
import vn.nhantd.mycareer.model.employeer.Employer;
import vn.nhantd.mycareer.model.firebase.mesaging.MyCareerNotification;
import vn.nhantd.mycareer.model.firebase.mesaging.Note;
import vn.nhantd.mycareer.model.firebase.mesaging.Notification;
import vn.nhantd.mycareer.model.job.Job;
import vn.nhantd.mycareer.model.user.User;
import vn.nhantd.mycareer.model.user.User_career_goals;
import vn.nhantd.mycareer.repository.JobRepository;
import vn.nhantd.mycareer.service.firebase.FirebaseMessagingService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    @Autowired
    UserService userService;

    @Autowired
    JobRepository jobRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    FirebaseMessagingService messagingService;

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
                        .orOperator(Criteria.where("level").is(careerGoals.getLevel()))

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

        List<Job> jobList = mongoTemplate.find(query, Job.class);

        jobList.sort(new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                return calculateTotalLike(o2).compareTo(calculateTotalLike(o1));
            }
        });

        return jobList;
    }

    private Long calculateTotalLike(Job job) {
        // TÍNH TOÁN SỐ LƯỢT QUAN TÂM ĐẾN NHÀ TUYỂN DỤNG
        //lọc lấy lượt yêu thích ??? lỗi chỗ này ???
        List<Transaction> likeList = job.getTransactions().stream()
                .distinct()
                .collect(Collectors.toList());

        //sort by dt DESC
        likeList.sort(new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return o2.getDt().compareTo(o1.getDt());
            }
        });

        // distinct theo user_id
        HashSet<Object> seen = new HashSet<>();

        // tính số lượt quan tâm
        long totalLike = 0;
        likeList.removeIf(e -> !seen.add(e.getUser_id()));
        for (Transaction transaction : likeList) {
            if (transaction.getTransaction_code().equals("like")) {
                totalLike += 1;
            }
        }
        return totalLike;
    }

    @Override
    public ViewCV viewCV(String job_id, Long trans_id) {
        ViewCV viewCV = new ViewCV();

        // Tim job theo id
        Job job = new Job();
        Optional<Job> jobOptional = null;
        jobOptional = jobRepository.findById(job_id);
        if (jobOptional.isPresent()) {
            job = jobOptional.get();
        }

        String user_id = "";
        List<String> cv_path = new ArrayList<>();

        List<Transaction> transactionList = job.getTransactions();
        for (Transaction transaction : transactionList) {
            long t_id = transaction.getDt();
            if (t_id == trans_id) {
                user_id = transaction.getUser_id();
                cv_path = transaction.getCv_path();

                // đánh dấu là đã xem.
                transaction.setViewed(true);
            }
        }

        job.setTransactions(transactionList);

        jobRepository.save(job);

        User user = userService.getUserById(user_id);
        viewCV.setUser(user);
        viewCV.setCv_path(cv_path);

        // bắn noti
        String token = user.getFgm_token();
        String jobName = job.getName();
        Note note = new Note("MyCareer", "Hồ sơ ứng tuyển " + jobName + "đã được xem bởi nhà tuyển dụng!", null, null);

        try {
            messagingService.sendNotificationToToken(new MyCareerNotification(token, note));
            mongoTemplate.save(new Notification(note.getSubject(), note.getContent(), user_id, token));
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }

        return viewCV;
    }
}
