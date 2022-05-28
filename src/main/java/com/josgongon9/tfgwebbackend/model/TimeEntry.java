package com.josgongon9.tfgwebbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.Date;

@Document(collection = "timeEntries")
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class TimeEntry {
    @Id
    private String id;

    private Date date;
    private String comment;
    private LocalTime startTime;
    private LocalTime endTime;
    private String totalTime;

    public TimeEntry() {

    }

    public TimeEntry(Date date, String comment, LocalTime startTime, LocalTime endTime, String totalTime) {
        this.date = date;
        this.comment = comment;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = totalTime;
    }

}