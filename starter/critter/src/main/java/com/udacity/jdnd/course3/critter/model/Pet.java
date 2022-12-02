package com.udacity.jdnd.course3.critter.model;

import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
@Table(name="pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private PetType type;
    @Nationalized
    private String name;
    private LocalDate birthDate;
    @Nationalized
    private String notes;
    @ManyToOne(targetEntity = Customer.class, optional = false, fetch = FetchType.LAZY)
    private Customer customer;
}
