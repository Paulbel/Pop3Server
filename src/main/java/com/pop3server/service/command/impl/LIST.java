package com.pop3server.service.command.impl;

import com.pop3server.dao.DAOFactory;
import com.pop3server.dao.MailDAO;
import com.pop3server.dao.exception.DAOException;
import com.pop3server.entity.Mail;
import com.pop3server.service.ServerThread;
import com.pop3server.service.command.Command;
import com.pop3server.service.exception.ServiceException;

import java.util.List;

public class LIST implements Command {
    @Override
    public String execute(ServerThread serverThread, String args) throws ServiceException {
        try {
            MailDAO mailDAO = DAOFactory.getInstance().getMailDAO();
            List<Mail> mailList = mailDAO.getAllMail(serverThread.getUserLogin());
            StringBuilder response = new StringBuilder();

            if (args.equals("")) {
                response.append("+OK ").append(mailList.size()).append(" messages\r\n");
                int index = 0;
                for (Mail mail : mailList) {
                    index++;
                    response.append(index).append(" ").append(mail.getContent().length()).append("\r\n");
                }
                response.append(".");

            } else {
                int index = Integer.parseInt(args);
                Mail mail = mailList.get(index - 1);
                int octet = mail.getContent().length();
                response.append("+OK ").append(index).append(" ").append(octet).append("\r\n");
            }
            return String.valueOf(response);

        } catch (NumberFormatException | DAOException e) {
            throw new ServiceException(e);
        }
    }
}
