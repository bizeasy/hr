package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PartyContactMech;
import com.hr.domain.Party;
import com.hr.domain.ContactMech;
import com.hr.domain.ContactMechPurpose;
import com.hr.repository.PartyContactMechRepository;
import com.hr.service.PartyContactMechService;
import com.hr.service.dto.PartyContactMechCriteria;
import com.hr.service.PartyContactMechQueryService;

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
 * Integration tests for the {@link PartyContactMechResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyContactMechResourceIT {

    private static final ZonedDateTime DEFAULT_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FROM_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_THRU_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private PartyContactMechRepository partyContactMechRepository;

    @Autowired
    private PartyContactMechService partyContactMechService;

    @Autowired
    private PartyContactMechQueryService partyContactMechQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyContactMechMockMvc;

    private PartyContactMech partyContactMech;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyContactMech createEntity(EntityManager em) {
        PartyContactMech partyContactMech = new PartyContactMech()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE);
        return partyContactMech;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyContactMech createUpdatedEntity(EntityManager em) {
        PartyContactMech partyContactMech = new PartyContactMech()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);
        return partyContactMech;
    }

    @BeforeEach
    public void initTest() {
        partyContactMech = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyContactMech() throws Exception {
        int databaseSizeBeforeCreate = partyContactMechRepository.findAll().size();
        // Create the PartyContactMech
        restPartyContactMechMockMvc.perform(post("/api/party-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyContactMech)))
            .andExpect(status().isCreated());

        // Validate the PartyContactMech in the database
        List<PartyContactMech> partyContactMechList = partyContactMechRepository.findAll();
        assertThat(partyContactMechList).hasSize(databaseSizeBeforeCreate + 1);
        PartyContactMech testPartyContactMech = partyContactMechList.get(partyContactMechList.size() - 1);
        assertThat(testPartyContactMech.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testPartyContactMech.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
    }

    @Test
    @Transactional
    public void createPartyContactMechWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyContactMechRepository.findAll().size();

        // Create the PartyContactMech with an existing ID
        partyContactMech.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyContactMechMockMvc.perform(post("/api/party-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyContactMech)))
            .andExpect(status().isBadRequest());

        // Validate the PartyContactMech in the database
        List<PartyContactMech> partyContactMechList = partyContactMechRepository.findAll();
        assertThat(partyContactMechList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyContactMeches() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList
        restPartyContactMechMockMvc.perform(get("/api/party-contact-meches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyContactMech.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))));
    }
    
    @Test
    @Transactional
    public void getPartyContactMech() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get the partyContactMech
        restPartyContactMechMockMvc.perform(get("/api/party-contact-meches/{id}", partyContactMech.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyContactMech.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(sameInstant(DEFAULT_FROM_DATE)))
            .andExpect(jsonPath("$.thruDate").value(sameInstant(DEFAULT_THRU_DATE)));
    }


    @Test
    @Transactional
    public void getPartyContactMechesByIdFiltering() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        Long id = partyContactMech.getId();

        defaultPartyContactMechShouldBeFound("id.equals=" + id);
        defaultPartyContactMechShouldNotBeFound("id.notEquals=" + id);

        defaultPartyContactMechShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPartyContactMechShouldNotBeFound("id.greaterThan=" + id);

        defaultPartyContactMechShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPartyContactMechShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPartyContactMechesByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList where fromDate equals to DEFAULT_FROM_DATE
        defaultPartyContactMechShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the partyContactMechList where fromDate equals to UPDATED_FROM_DATE
        defaultPartyContactMechShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyContactMechesByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList where fromDate not equals to DEFAULT_FROM_DATE
        defaultPartyContactMechShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the partyContactMechList where fromDate not equals to UPDATED_FROM_DATE
        defaultPartyContactMechShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyContactMechesByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultPartyContactMechShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the partyContactMechList where fromDate equals to UPDATED_FROM_DATE
        defaultPartyContactMechShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyContactMechesByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList where fromDate is not null
        defaultPartyContactMechShouldBeFound("fromDate.specified=true");

        // Get all the partyContactMechList where fromDate is null
        defaultPartyContactMechShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartyContactMechesByFromDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList where fromDate is greater than or equal to DEFAULT_FROM_DATE
        defaultPartyContactMechShouldBeFound("fromDate.greaterThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the partyContactMechList where fromDate is greater than or equal to UPDATED_FROM_DATE
        defaultPartyContactMechShouldNotBeFound("fromDate.greaterThanOrEqual=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyContactMechesByFromDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList where fromDate is less than or equal to DEFAULT_FROM_DATE
        defaultPartyContactMechShouldBeFound("fromDate.lessThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the partyContactMechList where fromDate is less than or equal to SMALLER_FROM_DATE
        defaultPartyContactMechShouldNotBeFound("fromDate.lessThanOrEqual=" + SMALLER_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyContactMechesByFromDateIsLessThanSomething() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList where fromDate is less than DEFAULT_FROM_DATE
        defaultPartyContactMechShouldNotBeFound("fromDate.lessThan=" + DEFAULT_FROM_DATE);

        // Get all the partyContactMechList where fromDate is less than UPDATED_FROM_DATE
        defaultPartyContactMechShouldBeFound("fromDate.lessThan=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyContactMechesByFromDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList where fromDate is greater than DEFAULT_FROM_DATE
        defaultPartyContactMechShouldNotBeFound("fromDate.greaterThan=" + DEFAULT_FROM_DATE);

        // Get all the partyContactMechList where fromDate is greater than SMALLER_FROM_DATE
        defaultPartyContactMechShouldBeFound("fromDate.greaterThan=" + SMALLER_FROM_DATE);
    }


    @Test
    @Transactional
    public void getAllPartyContactMechesByThruDateIsEqualToSomething() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList where thruDate equals to DEFAULT_THRU_DATE
        defaultPartyContactMechShouldBeFound("thruDate.equals=" + DEFAULT_THRU_DATE);

        // Get all the partyContactMechList where thruDate equals to UPDATED_THRU_DATE
        defaultPartyContactMechShouldNotBeFound("thruDate.equals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyContactMechesByThruDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList where thruDate not equals to DEFAULT_THRU_DATE
        defaultPartyContactMechShouldNotBeFound("thruDate.notEquals=" + DEFAULT_THRU_DATE);

        // Get all the partyContactMechList where thruDate not equals to UPDATED_THRU_DATE
        defaultPartyContactMechShouldBeFound("thruDate.notEquals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyContactMechesByThruDateIsInShouldWork() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList where thruDate in DEFAULT_THRU_DATE or UPDATED_THRU_DATE
        defaultPartyContactMechShouldBeFound("thruDate.in=" + DEFAULT_THRU_DATE + "," + UPDATED_THRU_DATE);

        // Get all the partyContactMechList where thruDate equals to UPDATED_THRU_DATE
        defaultPartyContactMechShouldNotBeFound("thruDate.in=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyContactMechesByThruDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList where thruDate is not null
        defaultPartyContactMechShouldBeFound("thruDate.specified=true");

        // Get all the partyContactMechList where thruDate is null
        defaultPartyContactMechShouldNotBeFound("thruDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartyContactMechesByThruDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList where thruDate is greater than or equal to DEFAULT_THRU_DATE
        defaultPartyContactMechShouldBeFound("thruDate.greaterThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the partyContactMechList where thruDate is greater than or equal to UPDATED_THRU_DATE
        defaultPartyContactMechShouldNotBeFound("thruDate.greaterThanOrEqual=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyContactMechesByThruDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList where thruDate is less than or equal to DEFAULT_THRU_DATE
        defaultPartyContactMechShouldBeFound("thruDate.lessThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the partyContactMechList where thruDate is less than or equal to SMALLER_THRU_DATE
        defaultPartyContactMechShouldNotBeFound("thruDate.lessThanOrEqual=" + SMALLER_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyContactMechesByThruDateIsLessThanSomething() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList where thruDate is less than DEFAULT_THRU_DATE
        defaultPartyContactMechShouldNotBeFound("thruDate.lessThan=" + DEFAULT_THRU_DATE);

        // Get all the partyContactMechList where thruDate is less than UPDATED_THRU_DATE
        defaultPartyContactMechShouldBeFound("thruDate.lessThan=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyContactMechesByThruDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);

        // Get all the partyContactMechList where thruDate is greater than DEFAULT_THRU_DATE
        defaultPartyContactMechShouldNotBeFound("thruDate.greaterThan=" + DEFAULT_THRU_DATE);

        // Get all the partyContactMechList where thruDate is greater than SMALLER_THRU_DATE
        defaultPartyContactMechShouldBeFound("thruDate.greaterThan=" + SMALLER_THRU_DATE);
    }


    @Test
    @Transactional
    public void getAllPartyContactMechesByPartyIsEqualToSomething() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);
        Party party = PartyResourceIT.createEntity(em);
        em.persist(party);
        em.flush();
        partyContactMech.setParty(party);
        partyContactMechRepository.saveAndFlush(partyContactMech);
        Long partyId = party.getId();

        // Get all the partyContactMechList where party equals to partyId
        defaultPartyContactMechShouldBeFound("partyId.equals=" + partyId);

        // Get all the partyContactMechList where party equals to partyId + 1
        defaultPartyContactMechShouldNotBeFound("partyId.equals=" + (partyId + 1));
    }


    @Test
    @Transactional
    public void getAllPartyContactMechesByContactMechIsEqualToSomething() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);
        ContactMech contactMech = ContactMechResourceIT.createEntity(em);
        em.persist(contactMech);
        em.flush();
        partyContactMech.setContactMech(contactMech);
        partyContactMechRepository.saveAndFlush(partyContactMech);
        Long contactMechId = contactMech.getId();

        // Get all the partyContactMechList where contactMech equals to contactMechId
        defaultPartyContactMechShouldBeFound("contactMechId.equals=" + contactMechId);

        // Get all the partyContactMechList where contactMech equals to contactMechId + 1
        defaultPartyContactMechShouldNotBeFound("contactMechId.equals=" + (contactMechId + 1));
    }


    @Test
    @Transactional
    public void getAllPartyContactMechesByContactMechPurposeIsEqualToSomething() throws Exception {
        // Initialize the database
        partyContactMechRepository.saveAndFlush(partyContactMech);
        ContactMechPurpose contactMechPurpose = ContactMechPurposeResourceIT.createEntity(em);
        em.persist(contactMechPurpose);
        em.flush();
        partyContactMech.setContactMechPurpose(contactMechPurpose);
        partyContactMechRepository.saveAndFlush(partyContactMech);
        Long contactMechPurposeId = contactMechPurpose.getId();

        // Get all the partyContactMechList where contactMechPurpose equals to contactMechPurposeId
        defaultPartyContactMechShouldBeFound("contactMechPurposeId.equals=" + contactMechPurposeId);

        // Get all the partyContactMechList where contactMechPurpose equals to contactMechPurposeId + 1
        defaultPartyContactMechShouldNotBeFound("contactMechPurposeId.equals=" + (contactMechPurposeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPartyContactMechShouldBeFound(String filter) throws Exception {
        restPartyContactMechMockMvc.perform(get("/api/party-contact-meches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyContactMech.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))));

        // Check, that the count call also returns 1
        restPartyContactMechMockMvc.perform(get("/api/party-contact-meches/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPartyContactMechShouldNotBeFound(String filter) throws Exception {
        restPartyContactMechMockMvc.perform(get("/api/party-contact-meches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPartyContactMechMockMvc.perform(get("/api/party-contact-meches/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPartyContactMech() throws Exception {
        // Get the partyContactMech
        restPartyContactMechMockMvc.perform(get("/api/party-contact-meches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyContactMech() throws Exception {
        // Initialize the database
        partyContactMechService.save(partyContactMech);

        int databaseSizeBeforeUpdate = partyContactMechRepository.findAll().size();

        // Update the partyContactMech
        PartyContactMech updatedPartyContactMech = partyContactMechRepository.findById(partyContactMech.getId()).get();
        // Disconnect from session so that the updates on updatedPartyContactMech are not directly saved in db
        em.detach(updatedPartyContactMech);
        updatedPartyContactMech
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);

        restPartyContactMechMockMvc.perform(put("/api/party-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyContactMech)))
            .andExpect(status().isOk());

        // Validate the PartyContactMech in the database
        List<PartyContactMech> partyContactMechList = partyContactMechRepository.findAll();
        assertThat(partyContactMechList).hasSize(databaseSizeBeforeUpdate);
        PartyContactMech testPartyContactMech = partyContactMechList.get(partyContactMechList.size() - 1);
        assertThat(testPartyContactMech.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testPartyContactMech.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyContactMech() throws Exception {
        int databaseSizeBeforeUpdate = partyContactMechRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyContactMechMockMvc.perform(put("/api/party-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyContactMech)))
            .andExpect(status().isBadRequest());

        // Validate the PartyContactMech in the database
        List<PartyContactMech> partyContactMechList = partyContactMechRepository.findAll();
        assertThat(partyContactMechList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyContactMech() throws Exception {
        // Initialize the database
        partyContactMechService.save(partyContactMech);

        int databaseSizeBeforeDelete = partyContactMechRepository.findAll().size();

        // Delete the partyContactMech
        restPartyContactMechMockMvc.perform(delete("/api/party-contact-meches/{id}", partyContactMech.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyContactMech> partyContactMechList = partyContactMechRepository.findAll();
        assertThat(partyContactMechList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
