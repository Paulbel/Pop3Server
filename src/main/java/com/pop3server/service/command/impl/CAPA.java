package com.pop3server.service.command.impl;

import com.pop3server.service.ServerThread;
import com.pop3server.service.command.Command;
import com.pop3server.service.exception.ServiceException;

public class CAPA implements Command {
    @Override
    public String execute(ServerThread serverThread, String args) throws ServiceException {
        return "-ERR\r\n";
    }
}
