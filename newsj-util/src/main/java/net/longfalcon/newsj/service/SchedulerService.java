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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.DateBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
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
    public static final String UPDATE_TRIGGER_NAME = "updateTrigger";
    public static final String TRIGGER_GROUP = "newsJ-1";
    private static final Log _log = LogFactory.getLog(SchedulerService.class);

    private Scheduler scheduler;
    private JobDetail updateJobDetail;

    public void init() {
        try {
            scheduler.scheduleJob(updateJobDetail, getTrigger());
        } catch (SchedulerException e) {
            _log.error(e.toString(), e);
        }
    }

    public void destroy() {
        try {
            scheduler.shutdown(false);
        } catch (SchedulerException e) {
            _log.error(e.toString(), e);
        }
    }

    private Trigger getTrigger() {
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(UPDATE_TRIGGER_NAME, TRIGGER_GROUP)
                .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.MINUTE))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(2)
                        .repeatForever())
                .build();
        return trigger;
    }

    public void stopUpdateTask() {
        try {
            scheduler.pauseJob(new JobKey(UPDATE_TRIGGER_NAME, TRIGGER_GROUP));

        } catch (SchedulerException e) {
            _log.error(e.toString(), e);
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
}
