package com.infiniteskills.data.UI.login;

import com.infiniteskills.data.HibernateUtil;
import com.infiniteskills.data.UI.home.HomeController;
import com.infiniteskills.data.entities.StudentImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.IOException;
import java.util.Date;
import java.util.List;


public class LoginController {

    private String name = "";
    private String matric = "";
    private String programme = "";


    private Session session = HibernateUtil.getSessionFactory().openSession();


    //<editor-fold desc="LOGIN">
    @FXML
    private Pane loginPANE = new Pane();

    @FXML
    private Label regNEW = new Label();

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
    //</editor-fold>

    //<editor-fold desc="REGISTER">
    @FXML
    private Pane regPANE = new Pane();

    @FXML
    private ComboBox courseREG = new ComboBox();

    @FXML
    private TextField nameREG = new TextField();
    @FXML
    private TextField matricREG = new TextField();
    @FXML
    private PasswordField passREG = new PasswordField();
    @FXML
    private PasswordField confirmREG = new PasswordField();
    @FXML
    private Label nameVALID = new Label();
    @FXML
    private Label matricVALID = new Label();
    @FXML
    private Label progVALID = new Label();
    @FXML
    private Label passVALID = new Label();
    @FXML
    private Label confirmVALID = new Label();
    @FXML
    private Label success = new Label();
    @FXML
    private Label loginTXT = new Label();

    @FXML
    private Button registerBTN = new Button();



    @FXML
    private HBox HB = new HBox();

    @FXML
    private VBox VB = new VBox();
    //</editor-fold>

    StudentImpl currentUser = new StudentImpl();


    @FXML
    public void register() {
        String match = passREG.getText();
        String match2 = confirmREG.getText();


        if (nameREG.getText().equals("")) {
            nameREG.requestFocus();
            nameVALID.setVisible(true);
        }
        if (matricREG.getText().equals("")) {
            matricREG.requestFocus();
            matricVALID.setVisible(true);
        }
        if (courseREG.getSelectionModel().getSelectedItem() == null) {
            progVALID.setVisible(true);
        }
        if (passREG.getText().equals("")) {
            passREG.requestFocus();
            passVALID.setVisible(true);
        }
        if (confirmREG.getText().equals("")) {
            confirmREG.requestFocus();
            confirmVALID.setText("Required !");
            confirmVALID.setVisible(true);
        } else if (!match.equals(match2)) {
            passREG.requestFocus();
            confirmVALID.setText("Password Not Match !");
            passVALID.setVisible(false);
            confirmVALID.setVisible(true);
        } else {
            registerStudent(nameREG.getText(), matricREG.getText(), (String) courseREG.getValue(), passREG.getText());
        }
    }

    @FXML
    public void resetREG() {
        if (passVALID.isVisible()) {
            passVALID.setVisible(false);
        } else if (nameVALID.isVisible()) {
            nameVALID.setVisible(false);
        } else if (confirmVALID.isVisible()) {
            confirmVALID.setVisible(false);
        } else if (matricVALID.isVisible()) {
            matricVALID.setVisible(false);
        }
    }


    public void registerStudent(String n, String m, String p, String x) {
        Session attendSession = HibernateUtil.getSessionFactory().openSession();

        StudentImpl student = new StudentImpl();


        try {
            student.setStudentName(n);
            student.setStudentMatric(m);
            student.setStudentProgramme(p);
            student.setPassword(x);
            attendSession.getTransaction().begin();
            attendSession.save(student);
            attendSession.getTransaction().commit();
            hideReg();

        } catch (Exception e) {
            matricVALID.setText("Duplicate User");
            if (!nameVALID.isVisible()) {
                matricVALID.setVisible(true);
            }
            hideLog();

            attendSession.close();
        }
    }

    @FXML
    public void hideLog(){
        regPANE.setVisible(true);
        VB.setVisible(true);
        HB.setVisible(true);
        loginPANE.setVisible(false);
    }

    @FXML
    public void hideReg(){
        regPANE.setVisible(false);
        VB.setVisible(false);
        HB.setVisible(false);

        loginPANE.setVisible(true);
    }

    @FXML
    public void login() {


        if (usernameTF.getText().toString().equals("")) {
            usernameTF.requestFocus();
            nameValid.setVisible(true);
        } else if (passwordTF.getText().toString().equals("")) {
            passwordTF.requestFocus();
            passValid.setVisible(true);
        } else {
            load.setVisible(true);

            Authenticate(usernameTF.getText(), passwordTF.getText());

        }
    }


    private void Authenticate(String username, String password) {
        List<StudentImpl> userList = null;
        Transaction transaction = session.beginTransaction();

        Query users = session.createQuery("select e from StudentImpl e where e.studentMatric = :username and e.password=:password")
                .setParameter("username", username)
                .setParameter("password", password);

        try {


            if (users.list().size() == 0) {
                System.out.println("No User");
                load.setVisible(false);
                nameValid.setText("User not found");
                passValid.setText("Or Incorrect Password");
                passValid.setVisible(true);
                nameValid.setVisible(true);
                transaction.rollback(); //
            } else {
                load.setVisible(false);
                userList = users.list();
                transaction.commit();
                nextPage(userList.get(0).getStudentName(), userList.get(0).getStudentMatric(), userList.get(0).getStudentProgramme(), userList.get(0).getStudentId());

                session.close();
            }

        } catch (HibernateException e1) {
            transaction.rollback();
            e1.printStackTrace();
        }


    }


    public void nextPage(String A, String B, String C, long D) {
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = (Parent) loader.load();

            HomeController homeController = loader.getController();
            homeController.setCurrentUser(A, B, C, D);
            Stage stage = new Stage();
            stage.setTitle("Invitation System");
            stage.setScene(new Scene(root));
            stage.show();

            nameValid.getScene().getWindow().hide();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    public void reset() {
        if (nameValid.isVisible() || passValid.isVisible()) {
            nameValid.setVisible(false);
            passValid.setVisible(false);
        }


    }

}
