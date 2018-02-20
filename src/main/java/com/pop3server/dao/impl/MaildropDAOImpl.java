package com.pop3server.dao.impl;

import com.pop3server.dao.MaildropDAO;
import com.pop3server.dao.connectionprovider.ConnectionProvider;
import com.pop3server.dao.exception.DAOException;
import com.pop3server.entity.MaildropState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class MaildropDAOImpl implements MaildropDAO {
    private final static String GET_USER = "SELECT maildrop.login, maildrop.password FROM maildrop WHERE login = ?";
    private final static String CHECK_PASSWORD = "SELECT maildrop.login FROM maildrop WHERE login = ? AND password = ?";

    private final static String CHECK_UNLOCKED_QUERY = "SELECT maildrop.login FROM maildrop WHERE login = ? AND state='unlocked'";

    private final static String SET_MAILDROP_STATE_QUERY = "UPDATE maildrop SET state = ? WHERE login = ?";

    @Override
    public boolean checkIfMaildropExists(String login) throws DAOException {
        try (Connection connection = ConnectionProvider.instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(GET_USER)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean checkPassword(String login, String password) throws DAOException {
        try (Connection connection = ConnectionProvider.instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean checkIfMaildropUnlocked(String login) throws DAOException {
        try (Connection connection = ConnectionProvider.instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(CHECK_UNLOCKED_QUERY)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void setMaildropState(String login, MaildropState state) throws DAOException {
        try (Connection connection = ConnectionProvider.instance.getConnection();
             PreparedStatement statement = connection.prepareStatement(SET_MAILDROP_STATE_QUERY)) {
            statement.setString(1, state.toString().toLowerCase());
            statement.setString(2, login);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }


}
