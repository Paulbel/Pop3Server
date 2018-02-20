package com.pop3server.service.command;

import com.pop3server.service.ServerThread;
import com.pop3server.service.exception.ServiceException;

public interface Command {
    String execute(ServerThread serverThread, String args) throws ServiceException;
}
