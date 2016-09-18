package net.longfalcon.newsj.persistence;

import net.longfalcon.newsj.model.JobLog;

import java.util.List;

/**
 * User: Sten Martinez
 * Date: 9/17/16
 * Time: 7:47 PM
 */
public interface JobLogDAO {
    void update(JobLog jobLog);
    void delete(JobLog jobLog);
    List<JobLog> getJobLogByJobName(String jobName);
    List<JobLog> getAllJobLogs();
}
