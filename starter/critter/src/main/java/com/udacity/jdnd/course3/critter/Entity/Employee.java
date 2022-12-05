package com.udacity.jdnd.course3.critter.Entity;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.util.Set;

@Data
@Entity
@Table(name="employee")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Nationalized
    private String name;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<EmployeeSkill> skillSet;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private Set<DayOfWeek> daysAvailable;
    @ManyToMany(mappedBy="employeeList")
    private Set<Schedule> employeeSchedules;
}
