package com.MinicareStruts.action.member;

import com.MinicareStruts.model.Message;
import com.MinicareStruts.service.MemberService;
import com.MinicareStruts.service.SeekerService;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetMessagesAction extends Action {
    @Override
    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        //When user clicks on Message Him button,then we are forwarding the request. For that the below line. Now the below below line is for user sending messages and
        //redirecting to the same page. I am passing the conversationId as a url paramaeter.
        Integer conversationId = (Integer)request.getAttribute("conversationId");
        if(conversationId == null)
            conversationId=Integer.parseInt(request.getParameter("conversationId"));

        MemberService memberService = new MemberService();
        List<Message> list = memberService.getMessages(conversationId);

        //This is for if there are no messages,then in the jsp we cant write list[0].conversation.conversationId.
        request.setAttribute("conversationId",conversationId);
        request.setAttribute("listOfMessages",list);
        return mapping.findForward("listOfMessages");
    }
}