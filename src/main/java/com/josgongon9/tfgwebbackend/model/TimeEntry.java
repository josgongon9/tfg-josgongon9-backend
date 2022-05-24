package com.josgongon9.tfgwebbackend.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "timeEntries")
@Data public class TimeEntry {
  @Id
  private String id;

  private Date date;
  private Double totalTime;
  private String comment;
  //@DBRef
  //private User user;

  public TimeEntry() {

  }

  public TimeEntry(Date date, Double totalTime, String comment/*, User user*/) {
    this.date = date;
    this.totalTime = totalTime;
    this.comment = comment;
    //this.user = user;
  }


}