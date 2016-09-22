/*
 * Copyright (c) 2016. Sten Martinez
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package net.longfalcon.newsj.job;

import net.longfalcon.newsj.Binaries;
import net.longfalcon.newsj.Releases;
import net.longfalcon.newsj.model.JobLog;
import net.longfalcon.newsj.service.JobLogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;

/**
 * User: Sten Martinez
 * Date: 9/9/16
 * Time: 2:19 PM
 */
public class NewsUpdateJob extends QuartzJobBean {
    private static final Log _log = LogFactory.getLog(NewsUpdateJob.class);

    private Binaries binaries;
    private Releases releases;
    private JobLogService jobLogService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        try {
            List<JobExecutionContext> jobs = context.getScheduler().getCurrentlyExecutingJobs();
            for (JobExecutionContext job : jobs) {
                if (job.getTrigger().equals(context.getTrigger()) && !job.getJobInstance().equals(this)) {
                    _log.warn("UpdateJob is still running, sleeping");
                    return;
                }

            }
        } catch (SchedulerException e) {
            _log.error(e.toString(), e);
        }

        if (binaries == null || releases == null) {
            throw new JobExecutionException("services were null");
        }
        _log.info("Starting UpdateJob");
        JobLog jobLog = jobLogService.createJobLog(JobConfigKeys.UPDATE_JOB_KEY, new Date());
        try {
            doBinariesUpdate();
            doReleasesUpdate();
            jobLog.setResult("Success");
        } catch (Exception e) {
            _log.error(e.toString(), e);
            jobLog.setResult("Failed");
            jobLog.setNotes(e.toString());
        }
        _log.info("UpdateJob complete");
        jobLog.setEndDate(new Date());
        jobLogService.updateJobLog(jobLog);
    }

    private void doBinariesUpdate() {
        binaries.updateAllGroups();
    }

    private void doReleasesUpdate() {
        releases.processReleases();
    }

    public Binaries getBinaries() {
        return binaries;
    }

    public void setBinaries(Binaries binaries) {
        this.binaries = binaries;
    }

    public Releases getReleases() {
        return releases;
    }

    public void setReleases(Releases releases) {
        this.releases = releases;
    }

    public JobLogService getJobLogService() {
        return jobLogService;
    }

    public void setJobLogService(JobLogService jobLogService) {
        this.jobLogService = jobLogService;
    }
}
