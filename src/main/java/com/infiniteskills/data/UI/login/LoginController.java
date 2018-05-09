package com.infiniteskills.data.UI.login;

import com.infiniteskills.data.HibernateUtil;
import com.infiniteskills.data.entities.User;
import com.infiniteskills.data.entities.UserImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Iterator;
import java.util.List;


public class LoginController {

   private Session session ;

   @FXML
    private TextField usernameTF = new TextField();
    @FXML
    private PasswordField passwordTF = new PasswordField();
    @FXML
    private Label passValid = new Label();
    @FXML
    private Label nameValid = new Label();

    @FXML
    private Button loginBTN = new Button();

    @FXML
    private ProgressIndicator load = new ProgressIndicator();


    @FXML
    public void login(){
      session  = HibernateUtil.getSessionFactory().openSession();

        if(usernameTF.getText().toString().equals("")){
            usernameTF.requestFocus();
            nameValid.setVisible(true);
        }
            else if (passwordTF.getText().toString().equals("")){
            passwordTF.requestFocus();
            passValid.setVisible(true);
        }

        else{
            load.setVisible(true);

                Authenticate(usernameTF.getText(),passwordTF.getText());

        }
    }


    private void Authenticate(String username, String password){



        loginBTN.setDisable(true);
              session.beginTransaction();


            try {
                Query users = session.createQuery("select e from UserImpl e where e.userName = :username and e.password=:password")
                                                .setParameter("username",username)
                                                .setParameter("password",password);


                List<UserImpl> userList = users.list();

                System.out.println(userList.get(0).getEmailAddress());

            } catch (HibernateException e) {

                e.printStackTrace();
            }
            finally {
                session.close();
            }

    }


    public void reset(){
        if(nameValid.isVisible()||passValid.isVisible()){
            nameValid.setVisible(false);
            passValid.setVisible(false);
        }


    }

}
