/*
 * Copyright 2002-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.customers.web;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.samples.petclinic.customers.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Maciej Szarlinski
 */
@RestController
@Timed("petclinic.pet")
@RequiredArgsConstructor
@Slf4j
class PetResource {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    //TODO fix to use value objects
    @GetMapping("/petTypes")
    public List<PetType> getPetTypes() {
    	return Collections.emptyList();
   /* FIXME fix getting pet types
        return petRepository.findPetTypes();
        */
    }

    @PostMapping("/owners/{ownerId}/pets")
    @ResponseStatus(HttpStatus.CREATED)
    public Pet processCreationForm(
        @RequestBody PetRequest petRequest,
        @PathVariable("ownerId") int ownerId) {

        final Pet pet = new Pet(PetType.getPetType(petRequest.getType()));
        final Optional<Owner> optionalOwner = ownerRepository.findById(ownerId);
        Owner owner = optionalOwner.orElseThrow(() -> new ResourceNotFoundException("Owner "+ownerId+" not found"));
        owner.addPet(pet);

        return save(pet, petRequest);
    }

    @PutMapping("/owners/{ownerId}/pets/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void processUpdateForm(@PathVariable("ownerId") int ownerId, @PathVariable("petId") int petIdPath, @RequestBody PetRequest petRequest) {
        
    	int petId = petRequest.getId();
    	
    	if (petId != petIdPath) {
    		throw new ResourceNotFoundException("path ids mismatch");
    	}
    	
        Pet pet = findPetById(petId);
        checkPetOwner(ownerId, pet);
        save(pet, petRequest);
    }

    private Pet save(final Pet pet, final PetRequest petRequest) {

        pet.setName(petRequest.getName());
        pet.setBirthDate(petRequest.getBirthDate());

//        petRepository.findPetTypeById(petRequest.getTypeId())
//           .ifPresent(pet::setType);

        log.info("Saving pet {}", pet);
        return petRepository.save(pet);
    }

    @GetMapping("owners/{ownerId}/pets/{petId}")
    public PetDetails findPet(@PathVariable("ownerId") int ownerId, @PathVariable("petId") int petId) {
        Pet pet = findPetById(petId);
        checkPetOwner(ownerId, pet);
        
        	return new PetDetails(pet);
    }

	private void checkPetOwner(int ownerId, Pet pet) {
		if (!pet.getOwner().getId().equals(Integer.valueOf(ownerId))) {
        	throw new ResourceNotFoundException("Pet "+pet.getId().intValue()+" not found for owner" + ownerId);
        }
	}

	private Pet findPetById(int petId) {
        Optional<Pet> pet = petRepository.findById(petId);
        if (!pet.isPresent()) {
            throw new ResourceNotFoundException("Pet "+petId+" not found");
        }
        return pet.get();
    }

}
