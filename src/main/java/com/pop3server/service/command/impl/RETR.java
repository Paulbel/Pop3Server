package com.pop3server.service.command.impl;

import com.pop3server.dao.DAOFactory;
import com.pop3server.dao.MailDAO;
import com.pop3server.dao.exception.DAOException;
import com.pop3server.entity.Mail;
import com.pop3server.service.ServerThread;
import com.pop3server.service.command.Command;
import com.pop3server.service.exception.ServiceException;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class RETR implements Command {
    @Override
    public String execute(ServerThread serverThread, String args) throws ServiceException {
        try {
            int index = Integer.parseInt(args);
            MailDAO mailDAO = DAOFactory.getInstance().getMailDAO();
            Mail mail = mailDAO.getMail(serverThread.getUserLogin(), index);

            return formResponse(mail);
        } catch (NumberFormatException | DAOException e) {
            throw new ServiceException(e);
        }
    }

    private String formResponse(Mail mail) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("+OK ").append(mail.getContent().length()).append(" octets\r\n");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        stringBuilder.append("Date: ").append(simpleDateFormat.format(mail.getDate())).append("\r\n");
        stringBuilder.append("From: ").append(mail.getSenderLogin()).append("\r\n");
        stringBuilder.append("To: ").append(mail.getReceiverLogin()).append("\r\n");
        stringBuilder.append("Subject: ").append(mail.getSubject()).append("\r\n");
        stringBuilder.append("MIME-Version: 1.0\r\n");
        stringBuilder.append("Content-Type: text/plain; charset=utf-8\r\n");
        stringBuilder.append("Content-Disposition: inline\r\n\r\n");
        stringBuilder.append(mail.getContent()).append("\r\n.");
        return String.valueOf(stringBuilder);
    }
}
