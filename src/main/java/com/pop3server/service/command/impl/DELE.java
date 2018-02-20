package com.pop3server.service.command.impl;

import com.pop3server.dao.DAOFactory;
import com.pop3server.dao.MailDAO;
import com.pop3server.dao.exception.DAOException;
import com.pop3server.entity.Mail;
import com.pop3server.entity.MailState;
import com.pop3server.service.ServerThread;
import com.pop3server.service.command.Command;
import com.pop3server.service.exception.ServiceException;

public class DELE implements Command {
    @Override
    public String execute(ServerThread serverThread, String args) throws ServiceException {
        try {
            int index = Integer.parseInt(args);
            MailDAO mailDAO = DAOFactory.getInstance().getMailDAO();

            Mail mail = mailDAO.getMail(serverThread.getUserLogin(), index);
            StringBuilder stringBuilder = new StringBuilder();

            if (mail.getState() == MailState.DELETED) {
                stringBuilder.append("-ERR message ").append(index).append(" already deleted");
            } else {
                mailDAO.setDeletedStateToMessage(mail.getId());
                stringBuilder.append("+OK message ").append(index).append(" deleted");

            }
            return String.valueOf(stringBuilder);

        } catch (NumberFormatException | DAOException e) {
            throw new ServiceException(e);
        }
    }
}
