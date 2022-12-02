package com.udacity.jdnd.course3.critter.model;
import org.hibernate.annotations.Nationalized;
import javax.persistence.*;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;
    @Nationalized
    private String name;
    @Nationalized
    private String notes;
    @OneToMany(targetEntity = Pet.class, mappedBy = "owner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pet> pets;
}
