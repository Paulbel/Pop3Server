package com.pop3server;

import com.pop3server.service.Server;

public class EntryPoint {
    private static final String ERROR_INVALID_NUMBER_OF_ARGUMENTS = "An invalid number of arguments were specified.";
    private static final String ERROR_INVALID_ARGUMENT = "An invalid argument was specified.";

    private static final int ERROR_STATUS = 1;
    public static void main(String [] args){
        int port = 110;
        int timeout = 600;

        try {
            if (args.length == 1) {
                port = Integer.parseInt(args[0]);
            } else if (args.length == 2) {
                port = Integer.parseInt(args[0]);
                timeout = Integer.parseInt(args[1]);
            } else {
                System.err.println(ERROR_INVALID_NUMBER_OF_ARGUMENTS);
                System.exit(ERROR_STATUS);
            }

            Server server = new Server(port, timeout);
            server.run();
        } catch (NumberFormatException e) {
            System.err.println(ERROR_INVALID_ARGUMENT);
            System.exit(ERROR_STATUS);
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(ERROR_STATUS);
        }
    }
}
