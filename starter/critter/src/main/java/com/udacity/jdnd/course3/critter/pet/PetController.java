package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.Entity.Customer;
import com.udacity.jdnd.course3.critter.Entity.Pet;
import com.udacity.jdnd.course3.critter.Error.CustomerNotFoundError;
import com.udacity.jdnd.course3.critter.Error.PetNotFoundError;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import com.udacity.jdnd.course3.critter.user.CustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;
    private final CustomerService customerService;

    public PetController(PetService petService, CustomerService customerService) {
        this.petService = petService;
        this.customerService = customerService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {
        try {
            Pet pet = petService.convertPetDTOToEntity(petDTO);
            pet = petService.savePet(pet);
            petDTO = petService.convertEntityToPetDTO(pet);
            return petDTO;
        } catch (CustomerNotFoundError e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        try {
            Pet pet = petService.getPetById(petId);
            return petService.convertEntityToPetDTO(pet);
        } catch (PetNotFoundError e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping
    public List<PetDTO> getPets() {
        try {
            List<Pet> petList = petService.getAllPets();
            List<PetDTO> petDTOList = new ArrayList<>();
            petList.forEach(pet -> petDTOList.add(
                    petService.convertEntityToPetDTO(pet)));

            return petDTOList;
        } catch (
                PetNotFoundError e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        throw new UnsupportedOperationException();
    }

}
