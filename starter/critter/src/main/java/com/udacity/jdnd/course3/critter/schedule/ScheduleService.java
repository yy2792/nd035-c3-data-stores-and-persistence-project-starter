package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Entity.Schedule;
import com.udacity.jdnd.course3.critter.Error.CustomerNotFoundError;
import com.udacity.jdnd.course3.critter.Error.PetNotFoundError;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleService {
    @Autowired
    private final ScheduleRepository scheduleRepository;
    @Autowired
    private final EmployeeRepository employeeRepository;
    @Autowired
    private final PetRepository petRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository, PetRepository petRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
    }

    public Schedule save(Schedule schedule) {
        schedule = scheduleRepository.save(schedule);
        return schedule;
    }

    public ScheduleDTO convertEntityToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);
        if (schedule.getPetList() != null) {
            List<Long> pets = new ArrayList<>();
            schedule.getPetList().forEach(pet -> pets.add(pet.getId()));
            scheduleDTO.setPetIds(pets);
        }
        if (schedule.getEmployeeList() != null) {
            List<Long> employees = new ArrayList<>();
            schedule.getEmployeeList().forEach(employee -> employees.add(employee.getId()));
            scheduleDTO.setEmployeeIds(employees);
        }
        return scheduleDTO;
    }

    public Schedule convertScheduleDTOToEntity(ScheduleDTO scheduleDTO) throws CustomerNotFoundError, PetNotFoundError {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        if (scheduleDTO.getPetIds() != null) {
            List<Pet> pets = new ArrayList<>();
            scheduleDTO.getPetIds().forEach(petId -> pets.add(petRepository.findById(petId).
                    orElseThrow(() -> new PetNotFoundError(
                            "Pet " + String.valueOf(String.valueOf(petId) + "not found!")))));
        }

        if (scheduleDTO.getEmployeeIds() != null) {
            List<Employee> employees = new ArrayList<>();
            scheduleDTO.getEmployeeIds().forEach(employeeId -> employees.add(employeeRepository.findById(employeeId).
                            orElseThrow(() -> new CustomerNotFoundError(
                                    "Owner" + String.valueOf(String.valueOf(employeeId) + "not found!")))));
        }
        return schedule;
    }

}
