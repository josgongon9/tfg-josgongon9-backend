package com.josgongon9.tfgwebbackend.model.response;

import com.josgongon9.tfgwebbackend.model.User;
import com.josgongon9.tfgwebbackend.model.Vacation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.stream.Collectors;

@Document(collection = "vacations")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class VacationResponse {
  @Id
  private String id;

  private String title;
  private String description;
  private boolean published;
  private Date startDate;
  private Date endDate;
  private String state;
  private Integer resultDate;


  public VacationResponse(Vacation vacation) {
    this.id = vacation.getId();
    this.title = vacation.getTitle();
    this.description = vacation.getDescription();
    this.startDate = vacation.getStartDate();
    this.endDate = vacation.getEndDate();
    this.state = vacation.getState();


  }



}