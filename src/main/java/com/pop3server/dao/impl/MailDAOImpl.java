package com.pop3server.dao.impl;

import com.pop3server.dao.MailDAO;
import com.pop3server.dao.connectionprovider.ConnectionProvider;
import com.pop3server.dao.exception.DAOException;
import com.pop3server.entity.Mail;
import com.pop3server.entity.MailState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class MailDAOImpl implements MailDAO {
    private final String GET_ALL_MAIL_QUERY = "SELECT * FROM maildrop INNER JOIN mail ON maildrop.login = mail.receiver OR maildrop.login = mail.sender WHERE login = ? AND mail.state = 'actual'";

    private final String GET_MESSAGE = "SELECT * FROM maildrop INNER JOIN mail ON maildrop.login = mail.receiver OR maildrop.login = mail.sender WHERE login = ? AND mail.state = 'actual' LIMIT 1 OFFSET ?";


    private final String GET_UID_OF_MESSAGE = "SELECT MD5(mail.id) AS 'UID' FROM maildrop INNER JOIN mail ON maildrop.login = mail.receiver OR maildrop.login = mail.sender WHERE login = ? AND mail.state = 'actual' LIMIT 1 OFFSET ?";
    private final String GET_UIDL = "SELECT MD5(mail.id) AS 'UID' FROM maildrop INNER JOIN mail ON maildrop.login = mail.receiver OR maildrop.login = mail.sender WHERE login = ? AND mail.state = 'actual'";
    private final String GET_MAIL_COUNT_QUERY = "SELECT COUNT(mail.id) AS 'count' FROM maildrop INNER JOIN mail ON maildrop.login = mail.receiver OR maildrop.login = mail.sender WHERE login = ? AND mail.state = 'actual'";
    private final String SET_DELETED_MAIL_STATE_QUERY = "UPDATE mail SET state = 'deleted' WHERE mail.id = ?";
    private final String REMOVE_DELETED_MESSAGES = "DELETE FROM mail WHERE mail.sender = ? OR mail.receiver = ?";


    @Override
    public List<Mail> getAllMail(String login) throws DAOException {
        try (Connection connection = ConnectionProvider.instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_ALL_MAIL_QUERY)) {
            statement.setString(1, login);
            List<Mail> mailList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                mailList.add(createMail(resultSet));
            }
            return mailList;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Mail getMail(String login, int index) throws DAOException {
        try (Connection connection = ConnectionProvider.instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_MESSAGE)) {
            statement.setString(1, login);
            statement.setInt(2, index - 1);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return createMail(resultSet);
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public int getMailCount(String login) throws DAOException {
        try (Connection connection = ConnectionProvider.instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_MAIL_COUNT_QUERY)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new DAOException("Can't get mail count from database");
            }
            return resultSet.getInt("count");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public String getUID(String login, int index) throws DAOException {
        try (Connection connection = ConnectionProvider.instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_UID_OF_MESSAGE)) {
            statement.setString(1, login);
            statement.setInt(2, index + 1);

            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return "";
            }
            return resultSet.getString("UID");
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<String> getUIDL(String login) throws DAOException {
        try (Connection connection = ConnectionProvider.instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_UIDL)) {
            statement.setString(1, login);

            List<String> uidl = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                uidl.add(resultSet.getString("UID"));
            }
            return uidl;
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void setDeletedStateToMessage(int messageId) throws DAOException {
        try (Connection connection = ConnectionProvider.instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_DELETED_MAIL_STATE_QUERY)) {
            statement.setInt(1, messageId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void removeDeletedMessages(String login) throws DAOException {
        try (Connection connection = ConnectionProvider.instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(REMOVE_DELETED_MESSAGES)) {
            statement.setString(1, login);
            statement.setString(2, login);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


    private Mail createMail(ResultSet resultSet) throws SQLException {
        Mail mail = new Mail();
        mail.setState(MailState.valueOf(resultSet.getString("mail.state").toUpperCase()));
        mail.setId(resultSet.getInt("mail.id"));
        mail.setReceiverLogin(resultSet.getString("mail.receiver"));
        mail.setSenderLogin(resultSet.getString("mail.sender"));
        mail.setContent(resultSet.getString("mail.body"));
        mail.setDate(resultSet.getDate("mail.date"));
        mail.setSubject(resultSet.getString("mail.subject"));
        return mail;
    }
}
