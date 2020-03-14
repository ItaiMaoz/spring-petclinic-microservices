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
package org.springframework.samples.petclinic.customers.model;


import javax.validation.constraints.NotBlank;

import org.springframework.validation.annotation.Validated;

import lombok.EqualsAndHashCode;

/**
 * @author Juergen Hoeller
 * Can be Cat, Dog, Hamster...
 * 
 * @author itaimaoz
 * Changing to value object
 */
//@Embeddable
@EqualsAndHashCode
@Validated
public class PetType {
    
	@NotBlank
	//FIXME use validation annotation
    private String name;


	private void setName(String name) {
		this.name = name;
	}
	
    public String getName() {
        return this.name;
    }
    
	//FIXME use validation annotations
    PetType(@NotBlank String petType) {
    	
    	if (petType == null) {
    		throw new IllegalArgumentException("Pet Type was null");
    	}
    	
    	petType = petType.trim().toLowerCase();
    	
    	if (petType.length() == 0 ) {
    		throw new IllegalArgumentException("Pet Type was empty");
    	}
    	
    	this.setName(petType);
    }
    
    public static PetType getPetType(String petTypeString) {
    	
    	return new PetType(petTypeString);
    }
    
    
 
}
