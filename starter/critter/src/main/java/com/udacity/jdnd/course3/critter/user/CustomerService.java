package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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



}
