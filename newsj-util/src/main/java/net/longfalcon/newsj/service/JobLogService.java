package net.longfalcon.newsj.service;

import net.longfalcon.newsj.model.JobLog;
import net.longfalcon.newsj.persistence.JobLogDAO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * User: Sten Martinez
 * Date: 9/17/16
 * Time: 7:55 PM
 */
@Service
public class JobLogService {
    private JobLogDAO jobLogDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public JobLog createJobLog(String jobName, Date startDate) {
        JobLog jobLog = new JobLog();
        jobLog.setJobName(jobName);
        if (startDate == null) {
            startDate = new Date();
        }
        jobLog.setStartDate(startDate);
        jobLog.setResult("Running");
        jobLogDAO.update(jobLog);

        return jobLog;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateJobLog(JobLog jobLog) {
        jobLogDAO.update(jobLog);
    }


    public JobLogDAO getJobLogDAO() {
        return jobLogDAO;
    }

    public void setJobLogDAO(JobLogDAO jobLogDAO) {
        this.jobLogDAO = jobLogDAO;
    }
}
