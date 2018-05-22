/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vishrant.cf.bo;

import com.vishrant.cf.constants.AppConstants;
import com.vishrant.cf.constants.OracleSQL;
import com.vishrant.cf.db.DBHelper;
import com.vishrant.cf.utils.DeEncrypter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrantahindar.com
 */
public class ChangePasswordBO {

    final Logger log = Logger.getLogger(ChangePasswordBO.class);

    public boolean isPasswordChanged(String oldPassword, String newPassword, String userId, String userName) {

        if (oldPassword != null && newPassword != null && userId != null && userName != null && checkPasswordStrength(newPassword)) {
            try (Connection connection = DBHelper.getConnection(); PreparedStatement statement = connection.prepareStatement(OracleSQL.CHANGE_PASSWORD_SQL);) {

                DeEncrypter encrypter = DeEncrypter.getInstance();

                statement.setString(1, encrypter.encrypt(oldPassword));
                statement.setString(2, encrypter.encrypt(newPassword));
                statement.setString(3, userId);
                statement.setString(4, userName);
                
                statement.executeQuery();
                
                return true;
            } catch (Exception e) {
                log.error(AppConstants.LOGGING_EXCEPTION + e);
            }
        }
        
        return false;
    }
    
    private boolean checkPasswordStrength(String password) {
        Pattern pswNamePtrn = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[_@#$%]).{6,15})");
        Matcher mtch = pswNamePtrn.matcher(password);
        return mtch.matches();
    }
    
}