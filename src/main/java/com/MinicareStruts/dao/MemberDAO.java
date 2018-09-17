package com.MinicareStruts.dao;

import com.MinicareStruts.filter.HibernateSessionFilter;
import com.MinicareStruts.form.LoginForm;
import com.MinicareStruts.listener.SessionFactoryListener;
import com.MinicareStruts.model.Member;
import com.MinicareStruts.model.Seeker;
import com.MinicareStruts.model.Sitter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.List;


public class MemberDAO {
    //Do Password Hashing.....
    //Chnage the variable Names .

    /*
    public boolean isEmailRegistered(String email) {
        Session session = HibernateSessionFilter.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("SELECT MemberId FROM Member where email=?");
        query.setString(0,email);
        Integer memberId = (Integer)query.uniqueResult();
        if(memberId!=null)
            return true;
        else
            return false;
    }
    */

    public boolean isEmailRegistered(String email) {
        boolean status = false;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/care");
            Connection con = ds.getConnection();
            PreparedStatement pst = con.prepareStatement("select MemberId from member where Email=?");
            pst.setString(1,email);
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.next()) {
                status = true;
            }
        }
        catch (SQLException|NamingException e) {
            System.out.println(e);
        }
        return status;
    }

    /*
    public int registerUser(Member member) {
        int memberId = -1;
        Session session = HibernateSessionFilter.getSession();
        Transaction transaction = session.beginTransaction();
        if(member.getType().toString().equalsIgnoreCase("seeker")) {
            Seeker seeker = (Seeker)member;
            memberId = (int)session.save(seeker);
        }
        else {
            Sitter sitter = (Sitter)member;
            memberId = (int)session.save(sitter);
        }
        transaction.commit();
        return memberId;
    }
    */

    public int registerUser(Member member) {
        int status=0;
        int memberId = -1;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/care");
            Connection con = ds.getConnection();
            PreparedStatement pst = con.prepareStatement("insert into member (FirstName, LastName, Email, PhoneNumber, Password, Address, Type) " +
                                                              "values(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, member.getFirstName());
            pst.setString(2, member.getLastName());
            pst.setString(3, member.getEmail());
            pst.setString(4, member.getPhoneNumber());
            pst.setString(5, member.getPassword());
            pst.setString(6, member.getAddress());
            pst.setString(7, member.getType().toString());
            status = pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                memberId = rs.getInt(1);
            }

            //Should we write if(status > 0) here

            if (member.getType() == Member.MemberType.SEEKER) {
                PreparedStatement pst2 = con.prepareStatement("insert into seeker (SeekerId, NoOfChildren, SpouseName) values(?,?,?)");
                pst2.setInt(1,memberId);
                pst2.setInt(2,((Seeker)member).getNoOfChildren());
                pst2.setString(3,((Seeker)member).getSpouseName());
                int status1 = pst2.executeUpdate();

            }
            else {
                PreparedStatement pst3 = con.prepareStatement("insert into sitter (SitterId, Experience, ExpectedPay) values(?,?,?)");
                pst3.setInt(1,memberId);
                pst3.setDouble(2,((Sitter)member).getExperience());
                pst3.setDouble(3,((Sitter)member).getExpectedPay());
                int status1 = pst3.executeUpdate();

            }

        }
        catch (SQLException|NamingException e) {
            System.out.println(e);
        }
        return memberId;
    }

    /*
    public Member fetchMember(String email, String password) {
        Session session = HibernateSessionFilter.getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("FROM Member where email=? and password=? and status=?");
        query.setString(0,email);
        query.setString(1,password);
        query.setString(2,"ACTIVE");
        List<Member> list = query.list();
        return list.get(0);
    }
    */

    public Member fetchMember(String email, String password) {
        Member member=null;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/care");
            Connection con = ds.getConnection();
            PreparedStatement pst = con.prepareStatement("select * from member where Email = ? and Password = ? and Status = ?");
            pst.setString(1, email);
            pst.setString(2, password);
            pst.setString(3,"ACTIVE");
            ResultSet resultSet = pst.executeQuery();
            if(resultSet.next()) {
                member = new Member();
                member.setMemberId(resultSet.getInt(1));
                member.setFirstName(resultSet.getString(2));
                member.setLastName(resultSet.getString(3));
                member.setEmail(resultSet.getString(4));
                member.setPhoneNumber(resultSet.getString(5));
                member.setPassword(resultSet.getString(6));
                member.setAddress(resultSet.getString(7));
                member.setType(Member.MemberType.valueOf(resultSet.getString(8)));
                member.setStatus(Member.Status.valueOf(resultSet.getString(9)));
            }
        }
        catch (SQLException|NamingException e) {
            System.out.println(e);
        }
        return member;
    }

    /*
    public Member alterUser(Member member) {
        Session session = HibernateSessionFilter.getSession();
        Transaction transaction = session.beginTransaction();
        //Check the results of these queries.
        //So before sending the Member object here Do Database se get() and then overwrite the fields and then pass that full
        //object here.

        if(member.getType().toString().equalsIgnoreCase("seeker")) {
            Seeker seeker = (Seeker)member;
            session.update(seeker);
        }
        else {
            Sitter sitter = (Sitter)member;
            session.update(sitter);
        }
        transaction.commit();
        return member;
    }
    */


    public Member alterUser(Member member) {
        int status = -1;
        int memberId = -1;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/care");
            Connection con = ds.getConnection();
            PreparedStatement pst = con.prepareStatement("update member set FirstName=?, LastName=?, PhoneNumber=?, Address=? where " +
                    "MemberId=?");
            pst.setString(1, member.getFirstName());
            pst.setString(2, member.getLastName());
            pst.setString(3, member.getPhoneNumber());
            pst.setString(4, member.getAddress());
            pst.setInt(5, member.getMemberId());
            status = pst.executeUpdate();

            //Should we write if(status > 0) here

            if (member.getType() == Member.MemberType.SEEKER) {
                PreparedStatement pst2 = con.prepareStatement("update seeker set NoOfChildren=?, SpouseName=? where SeekerId=?");
                pst2.setInt(1,((Seeker)member).getNoOfChildren());
                pst2.setString(2,((Seeker)member).getSpouseName());
                pst2.setInt(3,member.getMemberId());
                int status1 = pst2.executeUpdate();
            }
            else {
                PreparedStatement pst3 = con.prepareStatement("update sitter set Experience=?, ExpectedPay=? where SitterId=?");
                pst3.setDouble(1,((Sitter)member).getExperience());
                pst3.setDouble(2,((Sitter)member).getExpectedPay());
                pst3.setInt(3,member.getMemberId());
                int status1 = pst3.executeUpdate();
            }
        }
        catch (SQLException|NamingException e) {
            System.out.println(e);
        }
        return member;
    }

    public boolean closeAccount1(int memberId) {
        Session session = HibernateSessionFilter.getSession();
        Transaction transaction = session.beginTransaction();
        //Write Remaining
        //For update first get The object by using get() and then update the fields using setters and then write session.update()
        //or session.saveOrUpdate().
        return true;
    }

    public boolean closeAccount(int memberId) {
        boolean status = false;
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/care");
            Connection con = ds.getConnection();
            PreparedStatement pst = con.prepareStatement("update member set Status=? where MemberId=?");
            pst.setString(1,"INACTIVE");
            pst.setInt(2,memberId);
            if(pst.executeUpdate()>0) {
                status = true;
            }
        }
        catch (SQLException|NamingException e) {
            System.out.println(e);
        }
        return status;
    }
}