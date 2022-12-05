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
import java.util.ArrayList;
import java.util.List;

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

    public Employee saveEmployee(Employee employee) {
        employee = employeeRepository.save(employee);
        return employee;
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
