package com.infiniteskills.data.entities;

public interface User {

     void setEmailAddress(String newEmail);
     String getEmailAddress();

     void setPassword(String newPassword);
     String getPassword();

     void setUserName(String newName);
     String getUserName();


     void setUserId(long id);
     long getUserId();

}
