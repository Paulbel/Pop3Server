package com.pop3server.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.channels.IllegalBlockingModeException;

public class Server {
    private static final String ERROR_INVALID_PORT = "An invalid port was specified. Port must be between 0 and 65535 inclusive.";
    private static final String ERROR_INVALID_TIMEOUT = "An invalid timeout was specified. Timeout must be greater than zero.";
    private static final String ERROR_UNABLE_TO_ESTABLISH_SOCKET = "An error occurred while establishing a socket or thread.";
    private final int port;


    private boolean serverRunning;

    public Server(int port, int timeout) throws IllegalArgumentException {
        this.port = port;


        if (timeout <= 0) {
            throw new IllegalArgumentException(ERROR_INVALID_TIMEOUT);
        }

        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException(ERROR_INVALID_PORT);
        }
    }

    public void run() {
        serverRunning = true;

        try (ServerSocket socket = new ServerSocket(port)) {
            while (serverRunning) {
                new ServerThread(socket.accept()).start();
            }
        } catch (IOException | SecurityException | IllegalBlockingModeException
                | IllegalArgumentException ex) {
            System.err.println(ERROR_UNABLE_TO_ESTABLISH_SOCKET);
        }
    }
}



