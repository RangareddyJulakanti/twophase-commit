package com.example.twophasecommit.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "MY_SCORE")
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = Score.ScoreBuilder.class)
public class Score implements Serializable {
    private static final long serialVersionUID = 7614293838845782729L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column
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

    @JsonPOJOBuilder(buildMethodName = "build",withPrefix = "")
    public  static  class ScoreBuilder{

    }
}
