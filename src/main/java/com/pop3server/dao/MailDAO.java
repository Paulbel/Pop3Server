package com.pop3server.dao;

import com.pop3server.dao.exception.DAOException;
import com.pop3server.entity.Mail;

import java.util.List;

public interface MailDAO {
    List<Mail> getAllMail(String login) throws DAOException;

    Mail getMail(String login, int index) throws DAOException;

    String getUID(String login, int index) throws DAOException;

    List<String> getUIDL(String login) throws DAOException;

    void setDeletedStateToMessage(int message) throws DAOException;

    void removeDeletedMessages(String login) throws DAOException;
}
