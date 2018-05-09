package com.infiniteskills.data.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "attendance")
public class Attendance {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "attendance_id")
  private long attendanceId;


  @Column(name = "student_id")
  private long studentId;

  @Column(name="attendance_date")
  private Date attendanceDate;

  @Column(name="isAttend")
  private boolean isAttend;

  public boolean isAttend() {
    return isAttend;
  }

  public void setAttend(boolean attend) {
    isAttend = attend;
  }

  public long getAttendanceId() {
    return attendanceId;
  }

  public void setAttendanceId(long attendanceId) {
    this.attendanceId = attendanceId;
  }


  public long getStudentId() {
    return studentId;
  }

  public void setStudentId(long studentId) {
    this.studentId = studentId;
  }


  public Date getAttendanceDate() {
    return attendanceDate;
  }

  public void setAttendanceDate(Date attendanceDate) {
    this.attendanceDate = attendanceDate;
  }

}
