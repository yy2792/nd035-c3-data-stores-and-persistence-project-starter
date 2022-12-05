package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Error.PetNotFoundError;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class CustomerService {
    @Autowired
    private final PetRepository petRepository;
    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerService(PetRepository repository, CustomerRepository customerRepository) {
        this.petRepository = repository;
        this.customerRepository = customerRepository;
    }

    public Customer saveCustomer(Customer customer) {
        customer = customerRepository.save(customer);
        return customer;
    }

    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new PetNotFoundError("Customer " + id.toString() + " not found"));
    }

    public CustomerDTO convertEntityToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);
        if(customer.getPets() != null){
            List<Long> petIds = new ArrayList<>();
            customer.getPets().forEach(pet -> petIds.add(pet.getId()));
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    public Customer convertCustomerDTOToEntity(CustomerDTO customerDTO) throws PetNotFoundError{
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        if(customerDTO.getPetIds() != null){
            List<Pet> pets = new ArrayList<>();
            customerDTO.getPetIds().forEach(
                    petId -> pets.add(petRepository.findById(petId).orElseThrow(() -> new PetNotFoundError("Pet " + String.valueOf(petId) + "not found!"))));
            customer.setPets(pets);
        }
        return customer;
    }





}
