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

package net.longfalcon.newsj.service;

import net.longfalcon.newsj.job.JobConfigKeys;
import net.longfalcon.newsj.model.JobConfig;
import net.longfalcon.newsj.persistence.JobConfigDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

/**
 * User: Sten Martinez
 * Date: 9/9/16
 * Time: 2:17 PM
 */
public class SchedulerService {
    public static final String TRIGGER_GROUP = "NewsJ-1";
    public static final String UPDATE_TRIGGER_NAME = "updateTrigger";
    private static final Log _log = LogFactory.getLog(SchedulerService.class);
    private JobConfigDAO jobConfigDAO;
    private Scheduler scheduler;
    private JobDetail updateJobDetail;

    public void destroy() {
        try {
            scheduler.shutdown(false);
        } catch (SchedulerException e) {
            _log.error(e.toString(), e);
        }
    }

    public void init() {
        try {
            Trigger trigger = getUpdateJobTrigger();
            if (trigger != null) {
                scheduler.scheduleJob(updateJobDetail, trigger);
            }
        } catch (SchedulerException e) {
            _log.error(e.toString(), e);
        }
    }

    private Trigger getUpdateJobTrigger() {
        JobConfig updateJobConfig = jobConfigDAO.getJobConfigByJobName(JobConfigKeys.UPDATE_JOB_KEY);
        Trigger trigger;
        ScheduleBuilder scheduleBuilder;
        if (updateJobConfig.getFrequencyConfig().equals(JobConfigKeys.FREQ_SCHEDULED)) {
            String cronExpression = updateJobConfig.getFrequencyConfig();
            scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        } else if (updateJobConfig.getFrequencyConfig().equals(JobConfigKeys.FREQ_PERIODIC)) {
            int interval = Integer.parseInt(updateJobConfig.getFrequencyConfig());
            scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInMilliseconds(interval)
                    .repeatForever();
        } else {
            return null;
        }

        // always start 5 minutes after startup
        trigger = TriggerBuilder.newTrigger()
                .withIdentity(UPDATE_TRIGGER_NAME, TRIGGER_GROUP)
                .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.MINUTE))
                .withSchedule(scheduleBuilder)
                .build();
        return trigger;
    }

    public void schedule(String jobKey) {
        try {
            if (JobConfigKeys.UPDATE_JOB_KEY.equals(jobKey)) {
                Trigger trigger = getUpdateJobTrigger();
                if (trigger != null) {
                    scheduler.scheduleJob(updateJobDetail, trigger);
                }
            }
        } catch (SchedulerException e) {
            _log.error(e.toString(), e);
        }
    }

    public void stopUpdateTask() {
        try {
            scheduler.pauseJob(new JobKey(UPDATE_TRIGGER_NAME, TRIGGER_GROUP));

        } catch (SchedulerException e) {
            _log.error(e.toString(), e);
        }
    }

    public boolean isUpdateJobScheduled() {
        try {
            return scheduler.checkExists(new JobKey(UPDATE_TRIGGER_NAME, TRIGGER_GROUP));
        } catch (SchedulerException e) {
            _log.error(e.toString(), e);
            return false;
        }
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public JobDetail getUpdateJobDetail() {
        return updateJobDetail;
    }

    public void setUpdateJobDetail(JobDetail updateJobDetail) {
        this.updateJobDetail = updateJobDetail;
    }

    public JobConfigDAO getJobConfigDAO() {
        return jobConfigDAO;
    }

    public void setJobConfigDAO(JobConfigDAO jobConfigDAO) {
        this.jobConfigDAO = jobConfigDAO;
    }
}
