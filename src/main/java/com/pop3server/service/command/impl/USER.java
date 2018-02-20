package com.pop3server.service.command.impl;

import com.pop3server.dao.DAOFactory;
import com.pop3server.dao.MaildropDAO;
import com.pop3server.dao.exception.DAOException;
import com.pop3server.service.ServerThread;
import com.pop3server.service.command.Command;
import com.pop3server.service.exception.ServiceException;

public class USER implements Command {

    @Override
    public String execute(ServerThread serverThread, String arg) throws ServiceException {
        try {
            String response;
            DAOFactory daoFactory = DAOFactory.getInstance();
            MaildropDAO userDAO = daoFactory.getMaildropDAO();
            if (serverThread.getSessionState() != ServerThread.SessionState.AUTHORIZATION) {
                response = "-ERR command invalid in the current state";
            } else if (userDAO.checkIfMaildropExists(arg)) {
                serverThread.setUserLogin(arg);
                response = "+OK User name accepted, password please";
            } else {
                response = "-ERR no maildrop with this name";
            }
            return response;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
