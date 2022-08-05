package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.FacilityContactMech;
import com.hr.domain.Facility;
import com.hr.domain.ContactMech;
import com.hr.domain.ContactMechPurpose;
import com.hr.repository.FacilityContactMechRepository;
import com.hr.service.FacilityContactMechService;
import com.hr.service.dto.FacilityContactMechCriteria;
import com.hr.service.FacilityContactMechQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.hr.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link FacilityContactMechResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FacilityContactMechResourceIT {

    private static final ZonedDateTime DEFAULT_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FROM_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_THRU_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private FacilityContactMechRepository facilityContactMechRepository;

    @Autowired
    private FacilityContactMechService facilityContactMechService;

    @Autowired
    private FacilityContactMechQueryService facilityContactMechQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacilityContactMechMockMvc;

    private FacilityContactMech facilityContactMech;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityContactMech createEntity(EntityManager em) {
        FacilityContactMech facilityContactMech = new FacilityContactMech()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE);
        return facilityContactMech;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityContactMech createUpdatedEntity(EntityManager em) {
        FacilityContactMech facilityContactMech = new FacilityContactMech()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);
        return facilityContactMech;
    }

    @BeforeEach
    public void initTest() {
        facilityContactMech = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacilityContactMech() throws Exception {
        int databaseSizeBeforeCreate = facilityContactMechRepository.findAll().size();
        // Create the FacilityContactMech
        restFacilityContactMechMockMvc.perform(post("/api/facility-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityContactMech)))
            .andExpect(status().isCreated());

        // Validate the FacilityContactMech in the database
        List<FacilityContactMech> facilityContactMechList = facilityContactMechRepository.findAll();
        assertThat(facilityContactMechList).hasSize(databaseSizeBeforeCreate + 1);
        FacilityContactMech testFacilityContactMech = facilityContactMechList.get(facilityContactMechList.size() - 1);
        assertThat(testFacilityContactMech.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testFacilityContactMech.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
    }

    @Test
    @Transactional
    public void createFacilityContactMechWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityContactMechRepository.findAll().size();

        // Create the FacilityContactMech with an existing ID
        facilityContactMech.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityContactMechMockMvc.perform(post("/api/facility-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityContactMech)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityContactMech in the database
        List<FacilityContactMech> facilityContactMechList = facilityContactMechRepository.findAll();
        assertThat(facilityContactMechList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFacilityContactMeches() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList
        restFacilityContactMechMockMvc.perform(get("/api/facility-contact-meches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityContactMech.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))));
    }
    
    @Test
    @Transactional
    public void getFacilityContactMech() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get the facilityContactMech
        restFacilityContactMechMockMvc.perform(get("/api/facility-contact-meches/{id}", facilityContactMech.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facilityContactMech.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(sameInstant(DEFAULT_FROM_DATE)))
            .andExpect(jsonPath("$.thruDate").value(sameInstant(DEFAULT_THRU_DATE)));
    }


    @Test
    @Transactional
    public void getFacilityContactMechesByIdFiltering() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        Long id = facilityContactMech.getId();

        defaultFacilityContactMechShouldBeFound("id.equals=" + id);
        defaultFacilityContactMechShouldNotBeFound("id.notEquals=" + id);

        defaultFacilityContactMechShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFacilityContactMechShouldNotBeFound("id.greaterThan=" + id);

        defaultFacilityContactMechShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFacilityContactMechShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFacilityContactMechesByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList where fromDate equals to DEFAULT_FROM_DATE
        defaultFacilityContactMechShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the facilityContactMechList where fromDate equals to UPDATED_FROM_DATE
        defaultFacilityContactMechShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityContactMechesByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList where fromDate not equals to DEFAULT_FROM_DATE
        defaultFacilityContactMechShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the facilityContactMechList where fromDate not equals to UPDATED_FROM_DATE
        defaultFacilityContactMechShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityContactMechesByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultFacilityContactMechShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the facilityContactMechList where fromDate equals to UPDATED_FROM_DATE
        defaultFacilityContactMechShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityContactMechesByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList where fromDate is not null
        defaultFacilityContactMechShouldBeFound("fromDate.specified=true");

        // Get all the facilityContactMechList where fromDate is null
        defaultFacilityContactMechShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacilityContactMechesByFromDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList where fromDate is greater than or equal to DEFAULT_FROM_DATE
        defaultFacilityContactMechShouldBeFound("fromDate.greaterThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the facilityContactMechList where fromDate is greater than or equal to UPDATED_FROM_DATE
        defaultFacilityContactMechShouldNotBeFound("fromDate.greaterThanOrEqual=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityContactMechesByFromDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList where fromDate is less than or equal to DEFAULT_FROM_DATE
        defaultFacilityContactMechShouldBeFound("fromDate.lessThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the facilityContactMechList where fromDate is less than or equal to SMALLER_FROM_DATE
        defaultFacilityContactMechShouldNotBeFound("fromDate.lessThanOrEqual=" + SMALLER_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityContactMechesByFromDateIsLessThanSomething() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList where fromDate is less than DEFAULT_FROM_DATE
        defaultFacilityContactMechShouldNotBeFound("fromDate.lessThan=" + DEFAULT_FROM_DATE);

        // Get all the facilityContactMechList where fromDate is less than UPDATED_FROM_DATE
        defaultFacilityContactMechShouldBeFound("fromDate.lessThan=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityContactMechesByFromDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList where fromDate is greater than DEFAULT_FROM_DATE
        defaultFacilityContactMechShouldNotBeFound("fromDate.greaterThan=" + DEFAULT_FROM_DATE);

        // Get all the facilityContactMechList where fromDate is greater than SMALLER_FROM_DATE
        defaultFacilityContactMechShouldBeFound("fromDate.greaterThan=" + SMALLER_FROM_DATE);
    }


    @Test
    @Transactional
    public void getAllFacilityContactMechesByThruDateIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList where thruDate equals to DEFAULT_THRU_DATE
        defaultFacilityContactMechShouldBeFound("thruDate.equals=" + DEFAULT_THRU_DATE);

        // Get all the facilityContactMechList where thruDate equals to UPDATED_THRU_DATE
        defaultFacilityContactMechShouldNotBeFound("thruDate.equals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityContactMechesByThruDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList where thruDate not equals to DEFAULT_THRU_DATE
        defaultFacilityContactMechShouldNotBeFound("thruDate.notEquals=" + DEFAULT_THRU_DATE);

        // Get all the facilityContactMechList where thruDate not equals to UPDATED_THRU_DATE
        defaultFacilityContactMechShouldBeFound("thruDate.notEquals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityContactMechesByThruDateIsInShouldWork() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList where thruDate in DEFAULT_THRU_DATE or UPDATED_THRU_DATE
        defaultFacilityContactMechShouldBeFound("thruDate.in=" + DEFAULT_THRU_DATE + "," + UPDATED_THRU_DATE);

        // Get all the facilityContactMechList where thruDate equals to UPDATED_THRU_DATE
        defaultFacilityContactMechShouldNotBeFound("thruDate.in=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityContactMechesByThruDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList where thruDate is not null
        defaultFacilityContactMechShouldBeFound("thruDate.specified=true");

        // Get all the facilityContactMechList where thruDate is null
        defaultFacilityContactMechShouldNotBeFound("thruDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacilityContactMechesByThruDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList where thruDate is greater than or equal to DEFAULT_THRU_DATE
        defaultFacilityContactMechShouldBeFound("thruDate.greaterThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the facilityContactMechList where thruDate is greater than or equal to UPDATED_THRU_DATE
        defaultFacilityContactMechShouldNotBeFound("thruDate.greaterThanOrEqual=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityContactMechesByThruDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList where thruDate is less than or equal to DEFAULT_THRU_DATE
        defaultFacilityContactMechShouldBeFound("thruDate.lessThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the facilityContactMechList where thruDate is less than or equal to SMALLER_THRU_DATE
        defaultFacilityContactMechShouldNotBeFound("thruDate.lessThanOrEqual=" + SMALLER_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityContactMechesByThruDateIsLessThanSomething() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList where thruDate is less than DEFAULT_THRU_DATE
        defaultFacilityContactMechShouldNotBeFound("thruDate.lessThan=" + DEFAULT_THRU_DATE);

        // Get all the facilityContactMechList where thruDate is less than UPDATED_THRU_DATE
        defaultFacilityContactMechShouldBeFound("thruDate.lessThan=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityContactMechesByThruDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);

        // Get all the facilityContactMechList where thruDate is greater than DEFAULT_THRU_DATE
        defaultFacilityContactMechShouldNotBeFound("thruDate.greaterThan=" + DEFAULT_THRU_DATE);

        // Get all the facilityContactMechList where thruDate is greater than SMALLER_THRU_DATE
        defaultFacilityContactMechShouldBeFound("thruDate.greaterThan=" + SMALLER_THRU_DATE);
    }


    @Test
    @Transactional
    public void getAllFacilityContactMechesByFacilityIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);
        Facility facility = FacilityResourceIT.createEntity(em);
        em.persist(facility);
        em.flush();
        facilityContactMech.setFacility(facility);
        facilityContactMechRepository.saveAndFlush(facilityContactMech);
        Long facilityId = facility.getId();

        // Get all the facilityContactMechList where facility equals to facilityId
        defaultFacilityContactMechShouldBeFound("facilityId.equals=" + facilityId);

        // Get all the facilityContactMechList where facility equals to facilityId + 1
        defaultFacilityContactMechShouldNotBeFound("facilityId.equals=" + (facilityId + 1));
    }


    @Test
    @Transactional
    public void getAllFacilityContactMechesByContactMechIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);
        ContactMech contactMech = ContactMechResourceIT.createEntity(em);
        em.persist(contactMech);
        em.flush();
        facilityContactMech.setContactMech(contactMech);
        facilityContactMechRepository.saveAndFlush(facilityContactMech);
        Long contactMechId = contactMech.getId();

        // Get all the facilityContactMechList where contactMech equals to contactMechId
        defaultFacilityContactMechShouldBeFound("contactMechId.equals=" + contactMechId);

        // Get all the facilityContactMechList where contactMech equals to contactMechId + 1
        defaultFacilityContactMechShouldNotBeFound("contactMechId.equals=" + (contactMechId + 1));
    }


    @Test
    @Transactional
    public void getAllFacilityContactMechesByContactMechPurposeIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityContactMechRepository.saveAndFlush(facilityContactMech);
        ContactMechPurpose contactMechPurpose = ContactMechPurposeResourceIT.createEntity(em);
        em.persist(contactMechPurpose);
        em.flush();
        facilityContactMech.setContactMechPurpose(contactMechPurpose);
        facilityContactMechRepository.saveAndFlush(facilityContactMech);
        Long contactMechPurposeId = contactMechPurpose.getId();

        // Get all the facilityContactMechList where contactMechPurpose equals to contactMechPurposeId
        defaultFacilityContactMechShouldBeFound("contactMechPurposeId.equals=" + contactMechPurposeId);

        // Get all the facilityContactMechList where contactMechPurpose equals to contactMechPurposeId + 1
        defaultFacilityContactMechShouldNotBeFound("contactMechPurposeId.equals=" + (contactMechPurposeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFacilityContactMechShouldBeFound(String filter) throws Exception {
        restFacilityContactMechMockMvc.perform(get("/api/facility-contact-meches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityContactMech.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))));

        // Check, that the count call also returns 1
        restFacilityContactMechMockMvc.perform(get("/api/facility-contact-meches/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFacilityContactMechShouldNotBeFound(String filter) throws Exception {
        restFacilityContactMechMockMvc.perform(get("/api/facility-contact-meches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFacilityContactMechMockMvc.perform(get("/api/facility-contact-meches/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFacilityContactMech() throws Exception {
        // Get the facilityContactMech
        restFacilityContactMechMockMvc.perform(get("/api/facility-contact-meches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityContactMech() throws Exception {
        // Initialize the database
        facilityContactMechService.save(facilityContactMech);

        int databaseSizeBeforeUpdate = facilityContactMechRepository.findAll().size();

        // Update the facilityContactMech
        FacilityContactMech updatedFacilityContactMech = facilityContactMechRepository.findById(facilityContactMech.getId()).get();
        // Disconnect from session so that the updates on updatedFacilityContactMech are not directly saved in db
        em.detach(updatedFacilityContactMech);
        updatedFacilityContactMech
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);

        restFacilityContactMechMockMvc.perform(put("/api/facility-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFacilityContactMech)))
            .andExpect(status().isOk());

        // Validate the FacilityContactMech in the database
        List<FacilityContactMech> facilityContactMechList = facilityContactMechRepository.findAll();
        assertThat(facilityContactMechList).hasSize(databaseSizeBeforeUpdate);
        FacilityContactMech testFacilityContactMech = facilityContactMechList.get(facilityContactMechList.size() - 1);
        assertThat(testFacilityContactMech.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testFacilityContactMech.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFacilityContactMech() throws Exception {
        int databaseSizeBeforeUpdate = facilityContactMechRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacilityContactMechMockMvc.perform(put("/api/facility-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityContactMech)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityContactMech in the database
        List<FacilityContactMech> facilityContactMechList = facilityContactMechRepository.findAll();
        assertThat(facilityContactMechList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacilityContactMech() throws Exception {
        // Initialize the database
        facilityContactMechService.save(facilityContactMech);

        int databaseSizeBeforeDelete = facilityContactMechRepository.findAll().size();

        // Delete the facilityContactMech
        restFacilityContactMechMockMvc.perform(delete("/api/facility-contact-meches/{id}", facilityContactMech.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FacilityContactMech> facilityContactMechList = facilityContactMechRepository.findAll();
        assertThat(facilityContactMechList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
