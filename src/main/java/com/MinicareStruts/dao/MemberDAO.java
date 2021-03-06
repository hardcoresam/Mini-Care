package com.MinicareStruts.dao;

import com.MinicareStruts.filter.HibernateSessionFilter;
import com.MinicareStruts.model.Member;
import com.MinicareStruts.model.Seeker;
import com.MinicareStruts.model.Sitter;
import org.hibernate.Query;
import org.hibernate.Session;

public class MemberDAO {
    public boolean isEmailRegistered(String email) {
        Session session = HibernateSessionFilter.getSession();
        Query query = session.createQuery("SELECT memberId FROM Member where email=?");
        query.setString(0,email);
        Integer memberId = (Integer)query.uniqueResult();
        if(memberId!=null)
            return true;
        else
            return false;
    }

    public int registerUser(Member member) {
        int memberId = -1;
        Session session = HibernateSessionFilter.getSession();
        if(member.getType().name().equalsIgnoreCase("seeker")) {
            Seeker seeker = (Seeker)member;
            memberId = (int)session.save(seeker);
        }
        else {
            Sitter sitter = (Sitter)member;
            memberId = (int)session.save(sitter);
        }
        return memberId;
    }

    public Member fetchMember(String email, String password) {
        Session session = HibernateSessionFilter.getSession();
        Query query = session.createQuery("FROM Member where email=? and password=? and status=?");
        query.setString(0, email);
        query.setString(1, password);
        query.setString(2, "ACTIVE");
        Member member = (Member) query.uniqueResult();
        return member;
    }

    public Member alterUser(Member member) {
        Session session = HibernateSessionFilter.getSession();
        if(member.getType().name().equalsIgnoreCase("seeker")) {
            Seeker seeker = (Seeker)member;
            session.update(seeker);
        }
        else {
            Sitter sitter = (Sitter)member;
            session.update(sitter);
        }
        return member;
    }
}