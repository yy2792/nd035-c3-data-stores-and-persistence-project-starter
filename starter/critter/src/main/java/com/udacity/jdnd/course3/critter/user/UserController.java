package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Employee;
import com.udacity.jdnd.course3.critter.Error.CustomerNotFoundError;
import com.udacity.jdnd.course3.critter.Error.EmployeeNotFoundError;
import com.udacity.jdnd.course3.critter.Error.PetNotFoundError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final CustomerService customerService;
    private final EmployeeService employeeService;

    public UserController(CustomerService customerService, EmployeeService employeeService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
    }

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
        try {
            Customer customer = customerService.convertCustomerDTOToEntity(customerDTO);
            customer = customerService.saveCustomer(customer);
            customerDTO = customerService.convertEntityToCustomerDTO(customer);
            return customerDTO;
        } catch (
                PetNotFoundError e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {
        try {
            List<Customer> customerList = customerService.getAllCustomers();
            List<CustomerDTO> customerDTOList = new ArrayList<>();
            customerList.forEach(customer -> customerDTOList.add(
                            customerService.convertEntityToCustomerDTO(customer)));
            return customerDTOList;
        }catch (
                PetNotFoundError e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            Employee employee = employeeService.convertEmployeeDTOToEntity(employeeDTO);
            employee = employeeService.saveEmployee(employee);
            employeeDTO = employeeService.convertEntityToEmployeeDTO(employee);
            return employeeDTO;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        try {
            Employee employee = employeeService.findById(employeeId);
            EmployeeDTO employeeDTO = employeeService.convertEntityToEmployeeDTO(employee);
            return employeeDTO;
        } catch (EmployeeNotFoundError e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        try {
            employeeService.setEmployeeAvailability(daysAvailable, employeeId);
        } catch (EmployeeNotFoundError e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeRequestDTO) {
        try {
            List<Employee> employees = employeeService.findEmployeesAvailableAndQualified(
                    employeeRequestDTO.getDate(), employeeRequestDTO.getSkills());
            List<EmployeeDTO> employeeDTOList = new ArrayList<>();
            employees.forEach(employee -> employeeDTOList.add(
                    employeeService.convertEntityToEmployeeDTO(employee)));
            return employeeDTOList;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

}
