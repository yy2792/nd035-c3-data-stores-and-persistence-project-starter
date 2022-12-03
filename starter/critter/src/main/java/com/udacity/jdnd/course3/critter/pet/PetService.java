package com.udacity.jdnd.course3.critter.pet;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Error.CustomerNotFoundError;
import com.udacity.jdnd.course3.critter.Error.PetNotFoundError;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class PetService {
    @Autowired
    private final PetRepository petRepository;
    @Autowired
    private final CustomerRepository customerRepository;

    public PetService(PetRepository repository, CustomerRepository customerRepository) {
        this.petRepository = repository;
        this.customerRepository = customerRepository;
    }

    public Pet savePet(Pet pet) {
        pet = petRepository.save(pet);
        return pet;
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id).orElseThrow(() ->
                new PetNotFoundError("Pet " + id.toString() + " not found"));
    }

    public PetDTO convertEntityToPetDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }

    public Pet convertPetDTOToEntity(PetDTO petDTO) throws CustomerNotFoundError {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        if (petDTO.getOwnerId() != 0) {
            pet.setCustomer(customerRepository.findById(petDTO.getOwnerId()).
                    orElseThrow(() -> new CustomerNotFoundError(
                            "Owner" + String.valueOf(petDTO.getOwnerId()) + "not found!")));
        }
        return pet;
    }
}

