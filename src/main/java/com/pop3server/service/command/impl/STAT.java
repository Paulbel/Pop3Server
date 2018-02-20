package com.pop3server.service.command.impl;

import com.pop3server.service.ServerThread;
import com.pop3server.dao.DAOFactory;
import com.pop3server.dao.MailDAO;
import com.pop3server.dao.exception.DAOException;
import com.pop3server.entity.Mail;
import com.pop3server.service.command.Command;
import com.pop3server.service.exception.ServiceException;

import java.util.List;

public class STAT implements Command {
    @Override
    public String execute(ServerThread serverThread, String args) throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            MailDAO mailDAO = daoFactory.getMailDAO();
            List<Mail> mailList = mailDAO.getAllMail(serverThread.getUserLogin());
            int number = mailList.size();
            int size = 0;
            for (Mail mail : mailList) {
                size += mail.getContent().length();
            }
            return "+OK "+number+" "+size;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
