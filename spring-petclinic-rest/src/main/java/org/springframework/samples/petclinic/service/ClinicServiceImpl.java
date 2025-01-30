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
package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.PetTypeRepository;
import org.springframework.samples.petclinic.repository.SpecialtyRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers
 * Also a placeholder for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 * @author Vitaliy Fedoriv
 */
@Service

public class ClinicServiceImpl implements ClinicService {
	
	private Logger logger = LoggerFactory.getLogger(ClinicServiceImpl.class);

    private PetRepository petRepository;
    private VetRepository vetRepository;
    private OwnerRepository ownerRepository;
    private VisitRepository visitRepository;
    private SpecialtyRepository specialtyRepository;
	private PetTypeRepository petTypeRepository;

    @Autowired
     public ClinicServiceImpl(
       		 PetRepository petRepository,
    		 VetRepository vetRepository,
    		 OwnerRepository ownerRepository,
    		 VisitRepository visitRepository,
    		 SpecialtyRepository specialtyRepository,
			 PetTypeRepository petTypeRepository) {
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
        this.specialtyRepository = specialtyRepository; 
		this.petTypeRepository = petTypeRepository;
    }

	@Override
	@Transactional(readOnly = true)
	public Collection<Pet> findAllPets() throws DataAccessException {
		logger.info("Find all pets.");
		return petRepository.findAll();
	}

	@Override
	@Transactional
	public void deletePet(Pet pet) throws DataAccessException {
		logger.info("Delete pet. ID = " + pet.getId());
		petRepository.delete(pet);
	}

	@Override
	@Transactional(readOnly = true)
	public Visit findVisitById(int visitId) throws DataAccessException {
		logger.info("Find visit by ID. ID = " + visitId);
		Visit visit = null;
		try {
			visit = visitRepository.findById(visitId);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return visit;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Visit> findAllVisits() throws DataAccessException {
		logger.info("Find all visits.");
		return visitRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteVisit(Visit visit) throws DataAccessException {
		logger.info("Delete visit. ID = " + visit.getId());
		visitRepository.delete(visit);
	}

	@Override
	@Transactional(readOnly = true)
	public Vet findVetById(int id) throws DataAccessException {
		logger.info("Find vet by ID. ID = " + id);
		Vet vet = null;
		try {
			vet = vetRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return vet;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Vet> findAllVets() throws DataAccessException {
		logger.info("Find all vets.");
		Random rnd = new Random();
		int millis = rnd.nextInt(40);
		for (int i = 0; i < 120; i++) {
			dummyMethod(millis);
		}
		return vetRepository.findAll();
	}
	
	private void dummyMethod(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void saveVet(Vet vet) throws DataAccessException {
		logger.info("Save vet. ID = " + vet.getId());
		vetRepository.save(vet);
	}

	@Override
	@Transactional
	public void deleteVet(Vet vet) throws DataAccessException {
		logger.info("Delete vet. ID = " + vet.getId());
		vetRepository.delete(vet);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Owner> findAllOwners() throws DataAccessException {
		logger.info("Find all owners.");
		return ownerRepository.findAll();
	}

	@Override
	@Transactional
	public void deleteOwner(Owner owner) throws DataAccessException {
		logger.info("Delete owner. ID = " + owner.getId());
		ownerRepository.delete(owner);
	}

	@Override
	@Transactional(readOnly = true)
	public PetType findPetTypeById(int petTypeId) {
		logger.info("Find pet type by ID. ID = " + petTypeId);
		PetType petType = null;
		try {
			petType = petTypeRepository.findById(petTypeId);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return petType;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<PetType> findAllPetTypes() throws DataAccessException {
		logger.info("Find all pet types.");
		return petTypeRepository.findAll();
	}

	@Override
	@Transactional
	public void savePetType(PetType petType) throws DataAccessException {
		logger.info("Save pet type. ID = " + petType.getId());
		petTypeRepository.save(petType);
	}

	@Override
	@Transactional
	public void deletePetType(PetType petType) throws DataAccessException {
		logger.info("Delete pet type.");
		petTypeRepository.delete(petType);
	}

	@Override
	@Transactional(readOnly = true)
	public Specialty findSpecialtyById(int specialtyId) {
		logger.info("Find specialty by ID. ID = " + specialtyId);
		Specialty specialty = null;
		try {
			specialty = specialtyRepository.findById(specialtyId);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return specialty;
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Specialty> findAllSpecialties() throws DataAccessException {
		logger.info("Find all specialties.");
		return specialtyRepository.findAll();
	}

	@Override
	@Transactional
	public void saveSpecialty(Specialty specialty) throws DataAccessException {
		logger.info("Save specialty. ID = " + specialty.getId());
		specialtyRepository.save(specialty);
	}

	@Override
	@Transactional
	public void deleteSpecialty(Specialty specialty) throws DataAccessException {
		logger.info("Delete specialty. ID = " + specialty.getId());
		specialtyRepository.delete(specialty);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		logger.info("Find pet types.");
		return petRepository.findPetTypes();
	}

	@Override
	@Transactional(readOnly = true)
	public Owner findOwnerById(int id) throws DataAccessException {
		logger.info("Find owner by ID. ID = " + id);
		Owner owner = null;
		try {
			owner = ownerRepository.findById(id);
			longMethod();
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return owner;
	}
	
	private void longMethod() {
		try {
			Random rnd = new Random();
			Thread.sleep(rnd.nextInt(6000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Pet findPetById(int id) throws DataAccessException {
		logger.info("Find pet by ID. ID = " + id);
		Pet pet = null;
		try {
			pet = petRepository.findById(id);
		} catch (ObjectRetrievalFailureException|EmptyResultDataAccessException e) {
		// just ignore not found exceptions for Jdbc/Jpa realization
			return null;
		}
		return pet;
	}

	@Override
	@Transactional
	public void savePet(Pet pet) throws DataAccessException {
		logger.info("Save pet. ID = " + pet.getId());
		petRepository.save(pet);
		
	}

	@Override
	@Transactional
	public void saveVisit(Visit visit) throws DataAccessException {
		logger.info("Save visit. ID = " + visit.getId());
		visitRepository.save(visit);
		
	}

	@Override
	@Transactional(readOnly = true)
    @Cacheable(value = "vets")
	public Collection<Vet> findVets() throws DataAccessException {
		logger.info("Find vets.");
		return vetRepository.findAll();
	}

	@Override
	@Transactional
	public void saveOwner(Owner owner) throws DataAccessException {
		logger.info("Save owner. ID = " + owner.getId());
		ownerRepository.save(owner);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException {
		logger.info("Find owner by last name. Last name = " + lastName);
		return ownerRepository.findByLastName(lastName);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<Visit> findVisitsByPetId(int petId) {
		logger.info("Find visits by pet ID. ID = " + petId);
		return visitRepository.findByPetId(petId);
	}
	
	


}
