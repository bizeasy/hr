package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.FacilityParty;
import com.hr.domain.Facility;
import com.hr.domain.Party;
import com.hr.domain.RoleType;
import com.hr.repository.FacilityPartyRepository;
import com.hr.service.FacilityPartyService;
import com.hr.service.dto.FacilityPartyCriteria;
import com.hr.service.FacilityPartyQueryService;

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
 * Integration tests for the {@link FacilityPartyResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FacilityPartyResourceIT {

    private static final ZonedDateTime DEFAULT_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FROM_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_THRU_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private FacilityPartyRepository facilityPartyRepository;

    @Autowired
    private FacilityPartyService facilityPartyService;

    @Autowired
    private FacilityPartyQueryService facilityPartyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacilityPartyMockMvc;

    private FacilityParty facilityParty;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityParty createEntity(EntityManager em) {
        FacilityParty facilityParty = new FacilityParty()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE);
        return facilityParty;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityParty createUpdatedEntity(EntityManager em) {
        FacilityParty facilityParty = new FacilityParty()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);
        return facilityParty;
    }

    @BeforeEach
    public void initTest() {
        facilityParty = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacilityParty() throws Exception {
        int databaseSizeBeforeCreate = facilityPartyRepository.findAll().size();
        // Create the FacilityParty
        restFacilityPartyMockMvc.perform(post("/api/facility-parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityParty)))
            .andExpect(status().isCreated());

        // Validate the FacilityParty in the database
        List<FacilityParty> facilityPartyList = facilityPartyRepository.findAll();
        assertThat(facilityPartyList).hasSize(databaseSizeBeforeCreate + 1);
        FacilityParty testFacilityParty = facilityPartyList.get(facilityPartyList.size() - 1);
        assertThat(testFacilityParty.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testFacilityParty.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
    }

    @Test
    @Transactional
    public void createFacilityPartyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityPartyRepository.findAll().size();

        // Create the FacilityParty with an existing ID
        facilityParty.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityPartyMockMvc.perform(post("/api/facility-parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityParty)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityParty in the database
        List<FacilityParty> facilityPartyList = facilityPartyRepository.findAll();
        assertThat(facilityPartyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFacilityParties() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList
        restFacilityPartyMockMvc.perform(get("/api/facility-parties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityParty.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))));
    }
    
    @Test
    @Transactional
    public void getFacilityParty() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get the facilityParty
        restFacilityPartyMockMvc.perform(get("/api/facility-parties/{id}", facilityParty.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facilityParty.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(sameInstant(DEFAULT_FROM_DATE)))
            .andExpect(jsonPath("$.thruDate").value(sameInstant(DEFAULT_THRU_DATE)));
    }


    @Test
    @Transactional
    public void getFacilityPartiesByIdFiltering() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        Long id = facilityParty.getId();

        defaultFacilityPartyShouldBeFound("id.equals=" + id);
        defaultFacilityPartyShouldNotBeFound("id.notEquals=" + id);

        defaultFacilityPartyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFacilityPartyShouldNotBeFound("id.greaterThan=" + id);

        defaultFacilityPartyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFacilityPartyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFacilityPartiesByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList where fromDate equals to DEFAULT_FROM_DATE
        defaultFacilityPartyShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the facilityPartyList where fromDate equals to UPDATED_FROM_DATE
        defaultFacilityPartyShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityPartiesByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList where fromDate not equals to DEFAULT_FROM_DATE
        defaultFacilityPartyShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the facilityPartyList where fromDate not equals to UPDATED_FROM_DATE
        defaultFacilityPartyShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityPartiesByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultFacilityPartyShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the facilityPartyList where fromDate equals to UPDATED_FROM_DATE
        defaultFacilityPartyShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityPartiesByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList where fromDate is not null
        defaultFacilityPartyShouldBeFound("fromDate.specified=true");

        // Get all the facilityPartyList where fromDate is null
        defaultFacilityPartyShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacilityPartiesByFromDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList where fromDate is greater than or equal to DEFAULT_FROM_DATE
        defaultFacilityPartyShouldBeFound("fromDate.greaterThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the facilityPartyList where fromDate is greater than or equal to UPDATED_FROM_DATE
        defaultFacilityPartyShouldNotBeFound("fromDate.greaterThanOrEqual=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityPartiesByFromDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList where fromDate is less than or equal to DEFAULT_FROM_DATE
        defaultFacilityPartyShouldBeFound("fromDate.lessThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the facilityPartyList where fromDate is less than or equal to SMALLER_FROM_DATE
        defaultFacilityPartyShouldNotBeFound("fromDate.lessThanOrEqual=" + SMALLER_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityPartiesByFromDateIsLessThanSomething() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList where fromDate is less than DEFAULT_FROM_DATE
        defaultFacilityPartyShouldNotBeFound("fromDate.lessThan=" + DEFAULT_FROM_DATE);

        // Get all the facilityPartyList where fromDate is less than UPDATED_FROM_DATE
        defaultFacilityPartyShouldBeFound("fromDate.lessThan=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityPartiesByFromDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList where fromDate is greater than DEFAULT_FROM_DATE
        defaultFacilityPartyShouldNotBeFound("fromDate.greaterThan=" + DEFAULT_FROM_DATE);

        // Get all the facilityPartyList where fromDate is greater than SMALLER_FROM_DATE
        defaultFacilityPartyShouldBeFound("fromDate.greaterThan=" + SMALLER_FROM_DATE);
    }


    @Test
    @Transactional
    public void getAllFacilityPartiesByThruDateIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList where thruDate equals to DEFAULT_THRU_DATE
        defaultFacilityPartyShouldBeFound("thruDate.equals=" + DEFAULT_THRU_DATE);

        // Get all the facilityPartyList where thruDate equals to UPDATED_THRU_DATE
        defaultFacilityPartyShouldNotBeFound("thruDate.equals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityPartiesByThruDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList where thruDate not equals to DEFAULT_THRU_DATE
        defaultFacilityPartyShouldNotBeFound("thruDate.notEquals=" + DEFAULT_THRU_DATE);

        // Get all the facilityPartyList where thruDate not equals to UPDATED_THRU_DATE
        defaultFacilityPartyShouldBeFound("thruDate.notEquals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityPartiesByThruDateIsInShouldWork() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList where thruDate in DEFAULT_THRU_DATE or UPDATED_THRU_DATE
        defaultFacilityPartyShouldBeFound("thruDate.in=" + DEFAULT_THRU_DATE + "," + UPDATED_THRU_DATE);

        // Get all the facilityPartyList where thruDate equals to UPDATED_THRU_DATE
        defaultFacilityPartyShouldNotBeFound("thruDate.in=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityPartiesByThruDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList where thruDate is not null
        defaultFacilityPartyShouldBeFound("thruDate.specified=true");

        // Get all the facilityPartyList where thruDate is null
        defaultFacilityPartyShouldNotBeFound("thruDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacilityPartiesByThruDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList where thruDate is greater than or equal to DEFAULT_THRU_DATE
        defaultFacilityPartyShouldBeFound("thruDate.greaterThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the facilityPartyList where thruDate is greater than or equal to UPDATED_THRU_DATE
        defaultFacilityPartyShouldNotBeFound("thruDate.greaterThanOrEqual=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityPartiesByThruDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList where thruDate is less than or equal to DEFAULT_THRU_DATE
        defaultFacilityPartyShouldBeFound("thruDate.lessThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the facilityPartyList where thruDate is less than or equal to SMALLER_THRU_DATE
        defaultFacilityPartyShouldNotBeFound("thruDate.lessThanOrEqual=" + SMALLER_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityPartiesByThruDateIsLessThanSomething() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList where thruDate is less than DEFAULT_THRU_DATE
        defaultFacilityPartyShouldNotBeFound("thruDate.lessThan=" + DEFAULT_THRU_DATE);

        // Get all the facilityPartyList where thruDate is less than UPDATED_THRU_DATE
        defaultFacilityPartyShouldBeFound("thruDate.lessThan=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityPartiesByThruDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);

        // Get all the facilityPartyList where thruDate is greater than DEFAULT_THRU_DATE
        defaultFacilityPartyShouldNotBeFound("thruDate.greaterThan=" + DEFAULT_THRU_DATE);

        // Get all the facilityPartyList where thruDate is greater than SMALLER_THRU_DATE
        defaultFacilityPartyShouldBeFound("thruDate.greaterThan=" + SMALLER_THRU_DATE);
    }


    @Test
    @Transactional
    public void getAllFacilityPartiesByFacilityIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);
        Facility facility = FacilityResourceIT.createEntity(em);
        em.persist(facility);
        em.flush();
        facilityParty.setFacility(facility);
        facilityPartyRepository.saveAndFlush(facilityParty);
        Long facilityId = facility.getId();

        // Get all the facilityPartyList where facility equals to facilityId
        defaultFacilityPartyShouldBeFound("facilityId.equals=" + facilityId);

        // Get all the facilityPartyList where facility equals to facilityId + 1
        defaultFacilityPartyShouldNotBeFound("facilityId.equals=" + (facilityId + 1));
    }


    @Test
    @Transactional
    public void getAllFacilityPartiesByPartyIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);
        Party party = PartyResourceIT.createEntity(em);
        em.persist(party);
        em.flush();
        facilityParty.setParty(party);
        facilityPartyRepository.saveAndFlush(facilityParty);
        Long partyId = party.getId();

        // Get all the facilityPartyList where party equals to partyId
        defaultFacilityPartyShouldBeFound("partyId.equals=" + partyId);

        // Get all the facilityPartyList where party equals to partyId + 1
        defaultFacilityPartyShouldNotBeFound("partyId.equals=" + (partyId + 1));
    }


    @Test
    @Transactional
    public void getAllFacilityPartiesByRoleTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityPartyRepository.saveAndFlush(facilityParty);
        RoleType roleType = RoleTypeResourceIT.createEntity(em);
        em.persist(roleType);
        em.flush();
        facilityParty.setRoleType(roleType);
        facilityPartyRepository.saveAndFlush(facilityParty);
        Long roleTypeId = roleType.getId();

        // Get all the facilityPartyList where roleType equals to roleTypeId
        defaultFacilityPartyShouldBeFound("roleTypeId.equals=" + roleTypeId);

        // Get all the facilityPartyList where roleType equals to roleTypeId + 1
        defaultFacilityPartyShouldNotBeFound("roleTypeId.equals=" + (roleTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFacilityPartyShouldBeFound(String filter) throws Exception {
        restFacilityPartyMockMvc.perform(get("/api/facility-parties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityParty.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))));

        // Check, that the count call also returns 1
        restFacilityPartyMockMvc.perform(get("/api/facility-parties/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFacilityPartyShouldNotBeFound(String filter) throws Exception {
        restFacilityPartyMockMvc.perform(get("/api/facility-parties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFacilityPartyMockMvc.perform(get("/api/facility-parties/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFacilityParty() throws Exception {
        // Get the facilityParty
        restFacilityPartyMockMvc.perform(get("/api/facility-parties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityParty() throws Exception {
        // Initialize the database
        facilityPartyService.save(facilityParty);

        int databaseSizeBeforeUpdate = facilityPartyRepository.findAll().size();

        // Update the facilityParty
        FacilityParty updatedFacilityParty = facilityPartyRepository.findById(facilityParty.getId()).get();
        // Disconnect from session so that the updates on updatedFacilityParty are not directly saved in db
        em.detach(updatedFacilityParty);
        updatedFacilityParty
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);

        restFacilityPartyMockMvc.perform(put("/api/facility-parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFacilityParty)))
            .andExpect(status().isOk());

        // Validate the FacilityParty in the database
        List<FacilityParty> facilityPartyList = facilityPartyRepository.findAll();
        assertThat(facilityPartyList).hasSize(databaseSizeBeforeUpdate);
        FacilityParty testFacilityParty = facilityPartyList.get(facilityPartyList.size() - 1);
        assertThat(testFacilityParty.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testFacilityParty.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFacilityParty() throws Exception {
        int databaseSizeBeforeUpdate = facilityPartyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacilityPartyMockMvc.perform(put("/api/facility-parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityParty)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityParty in the database
        List<FacilityParty> facilityPartyList = facilityPartyRepository.findAll();
        assertThat(facilityPartyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacilityParty() throws Exception {
        // Initialize the database
        facilityPartyService.save(facilityParty);

        int databaseSizeBeforeDelete = facilityPartyRepository.findAll().size();

        // Delete the facilityParty
        restFacilityPartyMockMvc.perform(delete("/api/facility-parties/{id}", facilityParty.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FacilityParty> facilityPartyList = facilityPartyRepository.findAll();
        assertThat(facilityPartyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
