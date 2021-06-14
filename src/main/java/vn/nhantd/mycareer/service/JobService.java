package vn.nhantd.mycareer.service;

import vn.nhantd.mycareer.model.ViewCV;
import vn.nhantd.mycareer.model.job.Job;
import vn.nhantd.mycareer.model.user.User;

import java.util.List;

public interface JobService {
    List<Job> getJobForUser(String _id, int limit);

    List<Job> getJobOfEmployer(String employer_id, String status, int limit);

    List<Job> getAllJobs(int limit);

    ViewCV viewCV(String job_id, Long trans_id);
}
