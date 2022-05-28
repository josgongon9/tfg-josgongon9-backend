package com.josgongon9.tfgwebbackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "alerts")
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Alert {
    @Id
    @NotNull
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String description;
    @NotNull
    private Boolean show;
    @NotNull
    private Date f_alta;

}
