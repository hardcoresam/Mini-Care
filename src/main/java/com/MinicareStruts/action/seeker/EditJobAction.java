package com.MinicareStruts.action.seeker;

import com.MinicareStruts.form.PostJobForm;
import com.MinicareStruts.service.SeekerService;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.sql.Time;

public class EditJobAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        PostJobForm postJobForm = (PostJobForm) form;

        String startTime = postJobForm.getStartTime();
        String endTime = postJobForm.getEndTime();

        if(startTime.length()==5)
            startTime = startTime +":00";
        if(endTime.length()==5)
            endTime = endTime +":00";

        SeekerService seekerService = new SeekerService();
        seekerService.alterJob(postJobForm.getJobId(), postJobForm.getTitle(), Double.parseDouble(postJobForm.getPayPerHour()),
                Time.valueOf(startTime), Time.valueOf(endTime),
                Date.valueOf(postJobForm.getStartDate()), Date.valueOf(postJobForm.getEndDate()));

        request.setAttribute("successMsg","Job Was Edited Successfully");
        return mapping.findForward("successMsg");
    }
}