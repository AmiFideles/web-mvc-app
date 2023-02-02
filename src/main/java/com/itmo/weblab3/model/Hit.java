package com.itmo.weblab3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hit")
public class Hit implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private double xValue;
    @Column
    private double yValue;
    @Column
    private double rValue;
    @Column
    private boolean result;

}
