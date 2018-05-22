package com.vishrant.cf.bean;

import com.vishrant.cf.constants.AppConstants;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import org.apache.log4j.Logger;

/**
 *
 * @author Vishrant Krishna Gupta vg00124233@vishrant.com
 */
public class UserBean implements Serializable, HttpSessionBindingListener {

    final Logger log = Logger.getLogger(UserBean.class);
    
    private static final long serialVersionUID = 1L;

    // All logins.
    private static Map<UserBean, HttpSession> logins = new HashMap<UserBean, HttpSession>();

    private String userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String organization;
    private Timestamp lastLogin;

    private List<String> userRoleList = null;

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    public List<String> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<String> userRoleList) {
        this.userRoleList = userRoleList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public boolean isRoleValidForUser(String roleName) {
        if (userRoleList.contains(roleName)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this != null
                ? this.getUserName() : "null";
    }   
    
    @Override
    public boolean equals(Object other) {
        return (other instanceof UserBean) && (userId != null) ? userId.equals(((UserBean) other).userId) : (other == this);
    }

    @Override
    public int hashCode() {
        return (userId != null) ? (this.getClass().hashCode() + userId.hashCode()) : super.hashCode();
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        HttpSession session = logins.remove(this);
        if (session != null) {
            try{
                session.invalidate();
            } catch(Exception e) {
                log.error(AppConstants.LOGGING_EXCEPTION + e);
            }
        }
        logins.put(this, event.getSession());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        logins.remove(this);
    }
}