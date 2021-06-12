package vn.nhantd.mycareer.service;

import vn.nhantd.mycareer.model.job.Job;

import java.util.List;

public interface JobService {
    List<Job> getJobForUser(String _id, int limit);

    List<Job> getJobOfEmployer(String employer_id, String status, int limit);
}
