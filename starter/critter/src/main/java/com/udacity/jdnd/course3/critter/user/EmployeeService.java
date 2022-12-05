package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Error.EmployeeNotFoundError;
import com.udacity.jdnd.course3.critter.Error.PetNotFoundError;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeService {
    @Autowired
    private final PetRepository petRepository;
    @Autowired
    private final EmployeeRepository employeeRepository;

    public EmployeeService(PetRepository repository, EmployeeRepository employeeRepository) {
        this.petRepository = repository;
        this.employeeRepository = employeeRepository;
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() ->
                new EmployeeNotFoundError("Employee " + id.toString() + " not found"));
    }

    public List<Employee> findEmployeesAvailableAndQualified(LocalDate date, Set<EmployeeSkill> skills) {
        return employeeRepository.getAllByDaysAvailable(date.getDayOfWeek())
                .stream()
                .filter(e -> e.getSkillSet().containsAll(skills))
                .collect(Collectors.toList());
    }

    public void setEmployeeAvailability(Set<DayOfWeek> daysAvailable, long employeeId) {
        Employee employee = findById(employeeId);
        employee.setDaysAvailable(daysAvailable);
        employeeRepository.save(employee);
    }

    public Employee saveEmployee(Employee employee) {
        employee = employeeRepository.save(employee);
        return employee;
    }

    public List<Employee> findEmployeesForService(LocalDate date, Set<EmployeeSkill> skills) {
        return employeeRepository.getAllByDaysAvailable(date.getDayOfWeek())
                .stream()
                .filter(e -> e.getSkillSet().containsAll(skills))
                .collect(Collectors.toList());
    }

    public EmployeeDTO convertEntityToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);
        employeeDTO.setSkills(employee.getSkillSet());

        return employeeDTO;
    }

    public Employee convertEmployeeDTOToEntity(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);
        employee.setSkillSet(employeeDTO.getSkills());
        return employee;
    }

}
