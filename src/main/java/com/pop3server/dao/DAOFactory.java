package com.pop3server.dao;

import com.pop3server.dao.impl.MailDAOImpl;
import com.pop3server.dao.impl.MaildropDAOImpl;

public class DAOFactory {
    private static DAOFactory instance = new DAOFactory();
    private MaildropDAO maildropDAO = new MaildropDAOImpl();
    private MailDAO mailDAO = new MailDAOImpl();

    public MaildropDAO getMaildropDAO() {
        return maildropDAO;
    }

    public MailDAO getMailDAO() {
        return mailDAO;
    }

    public static DAOFactory getInstance() {
        return instance;
    }
}
