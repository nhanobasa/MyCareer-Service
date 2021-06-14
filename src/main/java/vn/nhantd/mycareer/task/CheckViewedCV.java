package vn.nhantd.mycareer.task;

import vn.nhantd.mycareer.config.StaticApplicationContext;
import vn.nhantd.mycareer.model.Transaction;
import vn.nhantd.mycareer.model.job.Job;
import vn.nhantd.mycareer.service.JobService;
import vn.nhantd.mycareer.service.JobServiceImpl;

import java.util.Comparator;
import java.util.List;

public class CheckViewedCV implements Runnable {
    @Override
    public void run() {
        try {
            JobServiceImpl jobService = (JobServiceImpl) StaticApplicationContext.getContext().getBean(JobService.class);
            List<Job> jobList = jobService.getAllJobs(-1);

            for (Job job: jobList){
                job.getTransactions().sort(new Comparator<Transaction>() {
                    @Override
                    public int compare(Transaction o1, Transaction o2) {
                        return 0;
                    }
                });
            }

            // 30s check lại một lần
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
