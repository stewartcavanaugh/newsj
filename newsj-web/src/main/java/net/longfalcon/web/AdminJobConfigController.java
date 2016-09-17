/*
 * Copyright (c) 2016. Sten Martinez
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

package net.longfalcon.web;

import net.longfalcon.newsj.job.JobConfigKeys;
import net.longfalcon.newsj.model.JobConfig;
import net.longfalcon.newsj.persistence.JobConfigDAO;
import net.longfalcon.newsj.service.SchedulerService;
import net.longfalcon.newsj.util.ArrayUtil;
import net.longfalcon.newsj.util.Time;
import net.longfalcon.view.JobConfigView;
import net.longfalcon.view.JobStatusView;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: longfalcon
 * Date: 9/16/2016
 * Time: 11:58 AM
 */
@Controller
@SessionAttributes({"jobConfigView"})
public class AdminJobConfigController extends BaseController {

    @Autowired
    JobConfigDAO jobConfigDAO;

    @Autowired
    SchedulerService schedulerService;

    @RequestMapping("/admin/job-list")
    public String listJobView(Model model) {
        List<JobStatusView> jobStatusViewList = new ArrayList<>();

        List<JobConfig> jobConfigList = jobConfigDAO.getAllJobConfig();
        for (JobConfig jobConfig : jobConfigList) {
            String jobKey = jobConfig.getJobName();
            if (jobKey.equals(JobConfigKeys.UPDATE_JOB_KEY)) {
                JobStatusView jobStatusView = new JobStatusView();
                jobStatusView.setJobKey(jobKey);
                jobStatusView.setJobName("Update Job");

                boolean isScheduled = schedulerService.isUpdateJobScheduled();

                jobStatusView.setStatus(isScheduled ? "Scheduled" : "Not Scheduled");

                jobStatusViewList.add(jobStatusView);
            }
        }

        model.addAttribute("title", "Scheduled Jobs");
        model.addAttribute("jobStatusViewList", jobStatusViewList);
        model.addAttribute("pageMetaTitle", "Scheduled Jobs");
        model.addAttribute("pageMetaKeywords", "scheduled,jobs,update");
        model.addAttribute("pageMetaDescription", "List Scheduled Jobs");
        return "admin/job-list";
    }

    @RequestMapping(value = "/admin/job-edit", method = RequestMethod.GET)
    public String editJobView(@RequestParam("name") String jobName, Model model) {
        JobConfig jobConfig = jobConfigDAO.getJobConfigByJobName(jobName);
        String jobKey = jobConfig.getJobName();
        String jobDisplayName = "";
        if (jobKey.equals(JobConfigKeys.UPDATE_JOB_KEY)) {
            jobDisplayName = "Update Job";
        }
        JobConfigView jobConfigView = getJobConfigView(jobConfig);

        Map<String,String> frequencyOptionsMap = new HashMap<>();
        frequencyOptionsMap.put(JobConfigKeys.FREQ_NEVER,"Never");
        frequencyOptionsMap.put(JobConfigKeys.FREQ_PERIODIC,"Periodic");
        frequencyOptionsMap.put(JobConfigKeys.FREQ_SCHEDULED,"Scheduled");

        String title = "Configure " + jobDisplayName;
        model.addAttribute("jobConfigView", jobConfigView);
        model.addAttribute("jobDisplayName", jobDisplayName);
        model.addAttribute("frequencyOptionsMap", frequencyOptionsMap);
        model.addAttribute("title", title);
        model.addAttribute("pageMetaDescription", title);
        model.addAttribute("pageMetaTitle", title);
        return "admin/job-edit";
    }

    @RequestMapping(value = "/admin/job-edit", method = RequestMethod.POST)
    public View editJobPost(@ModelAttribute("jobConfigView") JobConfigView jobConfigView, Model model) {
        JobConfig jobConfig = getJobConfig(jobConfigView);
        jobConfigDAO.update(jobConfig);
        schedulerService.reset(jobConfig.getJobName());
        return safeRedirect("/admin/job-list");
    }

    private JobConfigView getJobConfigView(JobConfig jobConfig) {
        JobConfigView jobConfigView = new JobConfigView();
        jobConfigView.setJobKey(jobConfig.getJobName());
        String jobFrequency = jobConfig.getJobFrequency();
        jobConfigView.setFrequencyType(jobFrequency);

        if (JobConfigKeys.FREQ_PERIODIC.equals(jobFrequency)) {
            int periodMillis = Integer.parseInt(jobConfig.getFrequencyConfig());
            Period period = new Period(periodMillis);
            jobConfigView.setPeriodHours(period.getHours());
            jobConfigView.setPeriodMins(period.getMinutes());
        } else if (JobConfigKeys.FREQ_SCHEDULED.equals(jobFrequency)) {
            String crontab = jobConfig.getFrequencyConfig();
            String[] cronElements = crontab.split(" ");
            // there will be six.
            String minutesString = cronElements[1];
            jobConfigView.setScheduledMinutes(Integer.parseInt(minutesString));
            String hoursString = cronElements[2];
            jobConfigView.setScheduledHours(Integer.parseInt(hoursString));
            // skip the others right now
            String days = cronElements[5];
            // days are always set as comma list in Newsj.
            String[] dayList = days.split(",");
            for (String dayString : dayList) {
                if ("1".equals(dayString)) {
                    jobConfigView.setSunday(true);
                }
                if ("2".equals(dayString)) {
                    jobConfigView.setMonday(true);
                }
                if ("3".equals(dayString)) {
                    jobConfigView.setTuesday(true);
                }
                if ("4".equals(dayString)) {
                    jobConfigView.setWednesday(true);
                }
                if ("5".equals(dayString)) {
                    jobConfigView.setThursday(true);
                }
                if ("6".equals(dayString)) {
                    jobConfigView.setFriday(true);
                }
                if ("7".equals(dayString)) {
                    jobConfigView.setSaturday(true);
                }
            }
        }

        return jobConfigView;
    }

    private JobConfig getJobConfig(JobConfigView jobConfigView) {
        JobConfig jobConfig = jobConfigDAO.getJobConfigByJobName(jobConfigView.getJobKey());

        String jobFrequency = jobConfigView.getFrequencyType();
        if (JobConfigKeys.FREQ_PERIODIC.equals(jobFrequency)) {
            jobConfig.setJobFrequency(jobFrequency);
            long period = (jobConfigView.getPeriodHours() * Time.HOUR) + (jobConfigView.getPeriodMins() * Time.MINUTE);
            jobConfig.setFrequencyConfig(String.valueOf(period));
        } else if (JobConfigKeys.FREQ_SCHEDULED.equals(jobFrequency)) {
            jobConfig.setJobFrequency(jobFrequency);
            String[] cronElements = new String[5];
            // there will be six.
            cronElements[0] = "0";
            cronElements[1] = String.valueOf(jobConfigView.getScheduledMinutes());
            cronElements[2] = String.valueOf(jobConfigView.getScheduledHours());
            cronElements[3] = "?";
            cronElements[4] = "*";
            List<String> days = new ArrayList<>();
            if (jobConfigView.isSunday()) {
                days.add("1");
            }
            if (jobConfigView.isMonday()) {
                days.add("2");
            }
            if (jobConfigView.isTuesday()) {
                days.add("3");
            }
            if (jobConfigView.isWednesday()) {
                days.add("4");
            }
            if (jobConfigView.isThursday()) {
                days.add("5");
            }
            if (jobConfigView.isFriday()) {
                days.add("6");
            }
            if (jobConfigView.isSaturday()) {
                days.add("7");
            }
            String daysString = ArrayUtil.stringify(days);
            cronElements[5] = daysString;
            jobConfig.setFrequencyConfig(ArrayUtil.stringify(cronElements, " "));
        } else if (JobConfigKeys.FREQ_NEVER.equals(jobFrequency)) {
            jobConfig.setJobFrequency(jobFrequency);
            jobConfig.setFrequencyConfig("N/A");
        }

        return jobConfig;
    }
}
