package com.udacity.jdnd.course3.critter.Entity;

import com.udacity.jdnd.course3.critter.pet.PetType;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


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
    @ManyToMany(mappedBy="petList")
    private Set<Schedule> petSchedules;
}
