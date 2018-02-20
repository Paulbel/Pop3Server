package com.pop3server.service.command.impl;

import com.pop3server.dao.DAOFactory;
import com.pop3server.dao.MailDAO;
import com.pop3server.dao.exception.DAOException;
import com.pop3server.service.ServerThread;
import com.pop3server.service.command.Command;
import com.pop3server.service.exception.ServiceException;

public class UIDL implements Command {
    @Override
    public String execute(ServerThread serverThread, String args) throws ServiceException {
        try {
            StringBuilder response = new StringBuilder();

            MailDAO mailDAO = DAOFactory.getInstance().getMailDAO();
            if (!args.equals("")) {
                int index = Integer.parseInt(args);
                response.append("+OK ").append(mailDAO.getUID(serverThread.getUserLogin(), index)).append("\r\n");
            } else {
                response.append("+OK").append("\r\n");
                int number = 0;
                for (String uid : mailDAO.getUIDL(serverThread.getUserLogin())) {
                    number++;
                    response.append(number).append(" ").append(uid).append("ASDfasf\r\n");
                }
                response.append(".");
            }
            return String.valueOf(response);
        } catch (NumberFormatException | DAOException e) {
            throw new ServiceException(e);
        }
    }
}
