package com.pop3server.dao.impl;

import com.pop3server.dao.DAOFactory;
import com.pop3server.dao.MailDAO;
import org.junit.Test;

public class MailDAOImplTest {

    @Test
    public void getAllMail() throws Exception {
        DAOFactory daoFactory = DAOFactory.getInstance();
        MailDAO mailDAO = daoFactory.getMailDAO();
        System.out.println(mailDAO.getAllMail("login1"));
    }

    @Test
    public void getMailCount() throws Exception {
    }

    @Test
    public void getMail() throws Exception {
        DAOFactory daoFactory = DAOFactory.getInstance();
        MailDAO mailDAO = daoFactory.getMailDAO();
        System.out.println(mailDAO.getMail("Pavel", 0));
    }

    @Test
    public void getUIDL() throws Exception {
        DAOFactory daoFactory = DAOFactory.getInstance();
        MailDAO mailDAO = daoFactory.getMailDAO();
        System.out.println(mailDAO.getUIDL("Pavel"));
    }
}