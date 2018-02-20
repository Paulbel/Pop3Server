package com.pop3server.service.command;

import com.pop3server.service.ServerThread;
import com.pop3server.service.exception.ServiceException;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.Map;

public final class CommandInvoker {
    private static final CommandInvoker instance = new CommandInvoker();
    private Map<String, Command> commandMap;

    private CommandInvoker() {
        XMLReader reader = null;
        try {
            reader = XMLReaderFactory.createXMLReader();
            CommandSaxHandler commandSaxHandler = new CommandSaxHandler();
            reader.setContentHandler(commandSaxHandler);
            reader.parse(new InputSource("src/main/resources/command.xml"));
            this.setCommandMap(commandSaxHandler.getCommandMap());

        } catch (SAXException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String executeCommand(ServerThread serverThread, String request) throws ServiceException {
        String[] requestParts = request.split("\\s", 2);
        String args = "";
        if (requestParts.length == 2) {
            args = requestParts[1];
        }
        return commandMap.get(requestParts[0]).execute(serverThread, args);
    }

    public void setCommandMap(Map<String, Command> commandMap) {
        this.commandMap = commandMap;
    }

    public static CommandInvoker getInstance() {
        return instance;
    }
}
