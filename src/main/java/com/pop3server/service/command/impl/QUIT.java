package com.pop3server.service.command.impl;

import com.pop3server.dao.DAOFactory;
import com.pop3server.dao.MailDAO;
import com.pop3server.dao.exception.DAOException;
import com.pop3server.service.ServerThread;
import com.pop3server.service.command.Command;
import com.pop3server.service.exception.ServiceException;

public class QUIT implements Command {
    @Override
    public String execute(ServerThread serverThread, String args) throws ServiceException {
        String response = "-ERR";

        try {
            switch (serverThread.getSessionState()) {
                case AUTHORIZATION:
                    response = "+OK dewey POP3 server signing off";
                    break;
                case TRANSACTION:
                    response = "+OK dewey POP3 server signing off";

                    serverThread.setSessionState(ServerThread.SessionState.UPDATE);
                    DAOFactory daoFactory = DAOFactory.getInstance();
                    MailDAO mailDAO = daoFactory.getMailDAO();
                    mailDAO.removeDeletedMessages(serverThread.getUserLogin());
                    break;
            }
        } catch (DAOException e) {
            response = "-ERR some deleted messages not removed";
        }
        return response;
    }
}
