package com.example.twophasecommit.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Data
@ToString
@Entity
@Table(name = "MY_SCORE",schema = "SYSTEM")

public class Score implements Serializable {

    private static final long serialVersionUID = 7614293838845782729L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonProperty
    private Long id;
    @Column
    @JsonProperty
    private String sname;
    @Column
    @JsonProperty
    private String course;
    @Column
    @JsonProperty
    private Integer score;

}
