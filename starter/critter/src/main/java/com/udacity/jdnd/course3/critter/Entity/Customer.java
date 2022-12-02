package com.udacity.jdnd.course3.critter.Entity;
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
    private String phoneNumber;
    @Nationalized
    private String name;
    @Nationalized
    private String notes;
    @OneToMany(targetEntity = Pet.class, mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Pet> pets;

    public void addPet(Pet pet){
        pets.add(pet);
    }
}
