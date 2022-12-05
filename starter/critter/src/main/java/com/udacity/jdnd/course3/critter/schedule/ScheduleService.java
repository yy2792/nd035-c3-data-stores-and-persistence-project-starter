package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Entity.Schedule;
import com.udacity.jdnd.course3.critter.Error.CustomerNotFoundError;
import com.udacity.jdnd.course3.critter.Error.EmployeeNotFoundError;
import com.udacity.jdnd.course3.critter.Error.PetNotFoundError;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    private final ScheduleRepository scheduleRepository;
    @Autowired
    private final EmployeeRepository employeeRepository;
    @Autowired
    private final PetRepository petRepository;
    @Autowired
    private final CustomerRepository customerRepository;

    public ScheduleService(ScheduleRepository scheduleRepository, EmployeeRepository employeeRepository, PetRepository petRepository, CustomerRepository customerRepository) {
        this.scheduleRepository = scheduleRepository;
        this.employeeRepository = employeeRepository;
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public Schedule save(Schedule schedule) {
        schedule = scheduleRepository.save(schedule);
        return schedule;
    }

    public List<Schedule> getAllSchedules() {
        return (List<Schedule>) scheduleRepository.findAll();
    }

    public List<Schedule> findScheduleByPetId(long petId) {
        Optional<Pet> pet = petRepository.findById(petId);
        if (pet.isPresent()) {
            return scheduleRepository.findAllByPetListContaining(pet.get());
        }
        else {
            throw new PetNotFoundError("Pet " + String.valueOf(petId) + " not found!");
        }
    }

    public List<Schedule> findSchedulesForEmployee(Long employeeId){
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if(employee.isPresent()){
            return scheduleRepository.findAllByEmployeeListContaining(employee.get());
        }
        throw new EmployeeNotFoundError("Employee " + String.valueOf(employeeId) + " not found!");
    }

    public List<Schedule> findScheduleForCustomer(Long customerId){
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isPresent()){
            List<Pet> pets = customer.get().getPets();
            return scheduleRepository.findAllByPetListIn(pets);
        }
        throw new CustomerNotFoundError("customer " + String.valueOf(customerId) + " not found!");
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
