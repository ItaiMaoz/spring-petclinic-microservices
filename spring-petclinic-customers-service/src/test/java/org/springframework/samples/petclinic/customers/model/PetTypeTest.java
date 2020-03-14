package org.springframework.samples.petclinic.customers.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.samples.petclinic.customers.model.PetType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class PetTypeTest {

	
	@Test
	void shouldCompareOnlyValue() throws Exception{
		PetType dog1 = new PetType("dog");
		PetType dog2 = new PetType("dog");
		
		assertEquals(dog1, dog2);
		assertFalse(dog1 == dog2);
		
	}
	
	
	@Test
	void shouldIgnoreCase() throws Exception{
		PetType dog = new PetType("dog");
		PetType DoG = new PetType("DoG");
		
		assertEquals(dog, DoG);
	}
	
	@Test
	void shouldNotCreateEmptyOrNulls() throws Exception{

		assertThrows(IllegalArgumentException.class, () -> {
			new PetType(null);});
		
		
		assertThrows(IllegalArgumentException.class, () -> {
			new PetType("");});
		
		assertThrows(IllegalArgumentException.class, () -> {
			new PetType(" ");});
		
		

	}
	
	@Test
	void shouldTrimLeadingAndTrailingWhiteSpaces() throws Exception {
		PetType dog = new PetType("dog");
		PetType dog_ = new PetType("dog ");
		PetType _dog = new PetType(" dog");
		
		assertEquals(dog, dog_);
		assertEquals(dog, _dog);
		
		
		//Keep internal whitespaces
		PetType cat_1 = new PetType ("Cat 1");
		PetType cat1 = new PetType ("Cat1");
		
		assertNotEquals(cat1, cat_1);

	}

}
