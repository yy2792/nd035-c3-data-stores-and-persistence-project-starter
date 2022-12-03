package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Entity.Schedule;
import com.udacity.jdnd.course3.critter.Error.CustomerNotFoundError;
import com.udacity.jdnd.course3.critter.Error.PetNotFoundError;
import com.udacity.jdnd.course3.critter.pet.PetService;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        try {
            Schedule schedule = scheduleService.convertScheduleDTOToEntity(scheduleDTO);
            schedule = scheduleService.save(schedule);
            scheduleDTO = scheduleService.convertEntityToScheduleDTO(schedule);
            return scheduleDTO;
        } catch (CustomerNotFoundError | PetNotFoundError e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        try {
            List<Schedule> schedules = scheduleService.getAllSchedules();
            List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
            schedules.forEach(schedule -> scheduleDTOs.add(scheduleService.convertEntityToScheduleDTO(schedule)));
            return scheduleDTOs;
        } catch (CustomerNotFoundError | PetNotFoundError e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        try {
            List<Schedule> schedules = scheduleService.findScheduleByPetId(petId);
            List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
            schedules.forEach(schedule -> scheduleDTOs.add(scheduleService.convertEntityToScheduleDTO(schedule)));
            return scheduleDTOs;
        } catch (CustomerNotFoundError | PetNotFoundError e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        throw new UnsupportedOperationException();
    }
}
