package com.josgongon9.tfgwebbackend.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "vacations")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class Vacation {
  @Id
  private String id;

  private String title;
  private String description;
  private boolean published;
  private Date startDate;
  private Date endDate;
  private String state;




}