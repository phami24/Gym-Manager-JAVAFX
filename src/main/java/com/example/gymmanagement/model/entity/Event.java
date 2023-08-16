package com.example.gymmanagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    private int event_id;
    private String event_name;
    private String start_date;
    private String end_date;
    private BigDecimal discount_percent;
    private String description;
    private int member_id;
    private int status;

}
