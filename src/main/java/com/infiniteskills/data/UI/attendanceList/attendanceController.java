package com.infiniteskills.data.UI.attendanceList;

import com.infiniteskills.data.HibernateUtil;
import com.infiniteskills.data.entities.Attendance;
import com.infiniteskills.data.entities.StudentImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class attendanceController implements Initializable {
    private String att;
    private ObservableList<students> students = FXCollections.observableArrayList();


    @FXML
    private TableView<students> table = new TableView<students>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    public static class students{
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getProgramme() {
            return programme;
        }

        public void setProgramme(String programme) {
            this.programme = programme;
        }

        public String getAttend() {
            return attend;
        }

        public void setAttend(String attend) {
            this.attend = attend;
        }

        public String name;
        public String programme;
        public String attend;

        public students(String name, String programme, String attend) {
            this.name = name;
            this.programme = programme;
            this.attend = attend;

        }

    }

    public void initTable(){
        getStudents();

        TableColumn firstNameCol = new TableColumn("Name");
        firstNameCol.setMinWidth(200);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<students, String>("Name"));



        TableColumn programme = new TableColumn("Programme");
        programme.setMinWidth(200);
        programme.setCellValueFactory(
                new PropertyValueFactory<students, String>("Programme"));


        TableColumn status = new TableColumn("Attend");
        status.setMinWidth(200);
        status.setCellValueFactory(
                new PropertyValueFactory<students, String>("Attend"));

        table.setItems(students);
        table.getColumns().addAll(firstNameCol, programme,status);

    }





    public void getStudents(){

        SessionFactory factory;
        Session ses;
        org.hibernate.Transaction tx = null;

        try{
            factory = HibernateUtil.getSessionFactory();
            ses = factory.openSession();
            tx = ses.beginTransaction();

            Query query = ses.createQuery("select t from StudentImpl t");
            List<StudentImpl> student = query.list();

            int i =0;
            for(StudentImpl t:student){
                Query q = ses.createQuery("SELECT a FROM Attendance a WHERE a.studentId IN:id").setParameter("id", t.getStudentId());
                List<Attendance> attendance = q.list();

                if(q.list().isEmpty()){
                    students.add(new students(
                            t.getStudentName(),
                            t.getStudentProgramme(),
                            "Not Responded"));
                }

                if(attendance.get(0).isAttend()){
                    att ="Attending";
                    students.add(new students(
                            t.getStudentName(),
                            t.getStudentProgramme(),
                            att));
                }
                else{
                    att="Not Attending";
                    students.add(new students(
                            t.getStudentName(),
                            t.getStudentProgramme(),
                            att));
                }

            }

            tx.commit();
        }catch(Exception e){
            tx.rollback();
        }
    }







}
