package com.infiniteskills.data.UI.home;


import com.infiniteskills.data.HibernateUtil;
import com.infiniteskills.data.UI.attendanceList.attendanceController;
import com.infiniteskills.data.entities.Attendance;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private String curName;
    private String curMatric;
    private String curProgramme;
    private long id;

    @FXML
    private Label name = new Label();

    @FXML
    private Label view = new Label();

    @FXML
    private Label feed = new Label();

    @FXML
    private Button accept = new Button();

    @FXML
    private Button reject = new Button();

    public  void setCurrentUser(String thisName, String thisMmatric, String thisProgramme, long thisId){
        curName =thisName;
        curMatric = thisMmatric;
        curProgramme = thisProgramme;
        name.setText("Dear "+curName+",");
        id = thisId;
    }

    public void attend(Boolean a){

        Session attendSession = HibernateUtil.getSessionFactory().openSession();
        Attendance attendance = new Attendance();

        try {
            attendance.setStudentId(id);
            attendance.setAttend(a);
            attendance.setAttendanceDate(new Date());
            attendSession.getTransaction().begin();
            attendSession.save(attendance);
            attendSession.getTransaction().commit();
        } catch (Exception e) {

            feed.setText("You have already responded to this invitation");
            feed.setVisible(true);
            attendSession.close();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        accept.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                attend(true);
            }
        });
        reject.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                attend(false);
            }
        });

    }

    public void nextPage(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/listView.fxml"));
            Parent root= (Parent)loader.load();

           attendanceController attendanceController = loader.getController();
           attendanceController.initTable();
            Stage stage = new Stage();
            stage.setTitle("Invitation System");
            stage.setScene(new Scene(root));
            stage.show();

//            nameValid.getScene().getWindow().hide();

        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}
