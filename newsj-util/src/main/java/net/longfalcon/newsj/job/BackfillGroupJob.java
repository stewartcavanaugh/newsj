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

import net.longfalcon.newsj.Backfill;
import net.longfalcon.newsj.model.JobLog;
import net.longfalcon.newsj.service.JobLogService;
import net.longfalcon.newsj.util.ValidatorUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * User: Sten Martinez
 * Date: 9/30/16
 * Time: 11:28 AM
 */
public class BackfillGroupJob implements Job {
    private static final Log _log = LogFactory.getLog(BackfillGroupJob.class);

    private String groupName;
    private Backfill backfill;
    private JobLogService jobLogService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap jobDataMap = context.getMergedJobDataMap();
        groupName = jobDataMap.getString("groupName");
        backfill = (Backfill) jobDataMap.get("backfill");
        jobLogService = (JobLogService) jobDataMap.get("jobLogService");

        executeInternal(context);
    }

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        if (backfill == null || jobLogService == null) {
            throw new JobExecutionException("services were null");
        }


        _log.info("Starting BackfillGroupJob");
        JobLog jobLog = jobLogService.createJobLog(JobConfigKeys.BACKFILL_JOB_KEY, new Date());
        try {
            doBackfillJob();
            jobLog.setResult("Success");
        } catch (Exception e) {
            _log.error(e.toString(), e);
            jobLog.setResult("Failed");
            jobLog.setNotes(e.toString());
        }
        _log.info("BackfillGroupJob complete");
        jobLog.setEndDate(new Date());
        jobLogService.updateJobLog(jobLog);
    }

    private void doBackfillJob() {
        if (ValidatorUtil.isNotNull(groupName)) {
            backfill.backfillSelectedGroup(groupName);
        } else {
            backfill.backfillAllGroups();
        }
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Backfill getBackfill() {
        return backfill;
    }

    public void setBackfill(Backfill backfill) {
        this.backfill = backfill;
    }

    public JobLogService getJobLogService() {
        return jobLogService;
    }

    public void setJobLogService(JobLogService jobLogService) {
        this.jobLogService = jobLogService;
    }
}
