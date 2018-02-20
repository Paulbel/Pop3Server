package com.pop3server.service.command.impl;

import com.pop3server.dao.DAOFactory;
import com.pop3server.dao.MaildropDAO;
import com.pop3server.dao.exception.DAOException;
import com.pop3server.entity.MaildropState;
import com.pop3server.service.ServerThread;
import com.pop3server.service.command.Command;
import com.pop3server.service.exception.ServiceException;

public class PASS implements Command {
    @Override
    public String execute(ServerThread serverThread, String arg) throws ServiceException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        MaildropDAO userDAO = daoFactory.getMaildropDAO();
        String response;
        try {
            if (userDAO.checkPassword(serverThread.getUserLogin(), arg)) {
                if (userDAO.checkIfMaildropUnlocked(serverThread.getUserLogin())) {
                    userDAO.setMaildropState(serverThread.getUserLogin(), MaildropState.LOCKED);
                    serverThread.setSessionState(ServerThread.SessionState.TRANSACTION);
                    response = "+OK user authorised";
                } else {
                    response = "-ERR this maildrop is currently locked";
                }
            } else {
                response = "-ERR password incorrect";
            }
            return response;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
