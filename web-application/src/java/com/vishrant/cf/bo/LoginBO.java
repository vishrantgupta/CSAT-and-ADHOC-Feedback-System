package com.vishrant.cf.bo;

import com.vishrant.cf.bean.UserBean;
import com.vishrant.cf.constants.AppConstants;
import com.vishrant.cf.constants.OracleSQL;
import com.vishrant.cf.db.DBHelper;
import com.vishrant.cf.utils.DeEncrypter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class LoginBO {

    final Logger log = Logger.getLogger(LoginBO.class);

    public UserBean isValidUser(String userName, String password) {

        if (userName != null && password != null) {
            PreparedStatement statement = null;
            Connection connection = null;
            try {

                connection = DBHelper.getConnection();

                statement = connection.prepareStatement(OracleSQL.LOGIN_SQL);

                DeEncrypter encrypter = DeEncrypter.getInstance();

                statement.setString(1, userName);
                statement.setString(2, userName);
                statement.setString(3, encrypter.encrypt(password));

                ResultSet rs = statement.executeQuery();

                UserBean ub = new UserBean();
                int count = 0;
                if (rs != null) {
                    while (rs.next()) {
                        count++;
                        ub.setUserId(rs.getString("user_id"));
                        ub.setUserName(rs.getString("user_name"));
                        ub.setFirstName(rs.getString("first_name"));
                        ub.setLastName(rs.getString("last_name"));
                        ub.setOrganization(rs.getString("organization"));
                        ub.setLastLogin(rs.getTimestamp("last_login"));
                        ub.setUserRoleList(Arrays.asList(rs.getString("role_name").trim().toUpperCase().split(",")));
                    }
                }

                log.debug(count == 1 ? "user " + userName + " logged in." : "invalid user " + userName);

                if (count == 1) {
                    statement = connection.prepareStatement(OracleSQL.UPDATE_LAST_LOGIN);
                    statement.setTimestamp(1, new Timestamp(new Date().getTime()));
                    statement.setInt(2, Integer.parseInt(ub.getUserId()));

                    statement.execute();
                    connection.commit();

                    return ub;
                } else {
                    return null;
                }

                // return count == 1 ? ub : null;
            } catch (Exception e) {
                log.error(AppConstants.LOGGING_EXCEPTION + e);
                if (connection != null) {
                    try {
                        connection.rollback();
                    } catch (SQLException ex) {
                        log.error(AppConstants.LOGGING_EXCEPTION + ex);
                    }
                }
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException ex) {
                        log.error(AppConstants.LOGGING_EXCEPTION + ex);
                    }
                }
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException ex) {
                        log.error(AppConstants.LOGGING_EXCEPTION + ex);
                    }
                }
            }
        }
        return null;
    }
}