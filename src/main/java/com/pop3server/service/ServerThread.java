package com.pop3server.service;

import com.pop3server.service.command.CommandInvoker;
import com.pop3server.service.exception.ServiceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerThread extends Thread {
    private static final String SERVER_WELCOME = "+OK POP3 server ready";
    private final Socket socket;
    private BufferedReader streamReader;
    private PrintWriter streamWriter;
    private String userLogin;
    private SessionState sessionState;
    private final static Logger logger = Logger.getLogger(ServerThread.class.getName());

    public enum SessionState {
        AUTHORIZATION, TRANSACTION, UPDATE
    }


    public SessionState getSessionState() {
        return sessionState;
    }

    public void setSessionState(SessionState sessionState) {
        this.sessionState = sessionState;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public ServerThread(Socket socket) {
        super();
        this.socket = socket;
        this.sessionState = SessionState.AUTHORIZATION;
    }


    @Override
    public void run() {
        String input;
        String output;
        try {
            init();
            streamWriter.println(SERVER_WELCOME);
            while ((input = streamReader.readLine()) != null) {
                logger.info("C: " + input);

                CommandInvoker commandInvoker = CommandInvoker.getInstance();
                output = commandInvoker.executeCommand(this, input);

                streamWriter.println(output);

                logger.info("S: " + output);
            }

        } catch (ServiceException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (streamReader != null) {
                    streamReader.close();
                }
                if (streamWriter != null) {
                    streamWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void init() throws IOException {
        this.streamWriter = new PrintWriter(socket.getOutputStream(), true);
        this.streamReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }
}
