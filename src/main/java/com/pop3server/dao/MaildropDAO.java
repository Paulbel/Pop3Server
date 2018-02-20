package com.pop3server.dao;

import com.pop3server.dao.exception.DAOException;
import com.pop3server.entity.MaildropState;

public interface MaildropDAO {
    boolean checkIfMaildropExists(String login) throws DAOException;

    boolean checkPassword(String login, String password) throws DAOException;

    boolean checkIfMaildropUnlocked(String login) throws DAOException;

    void setMaildropState(String login, MaildropState state) throws DAOException;
}
