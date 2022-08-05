package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PartyClassification;
import com.hr.domain.Party;
import com.hr.domain.PartyClassificationGroup;
import com.hr.repository.PartyClassificationRepository;
import com.hr.service.PartyClassificationService;
import com.hr.service.dto.PartyClassificationCriteria;
import com.hr.service.PartyClassificationQueryService;

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
 * Integration tests for the {@link PartyClassificationResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyClassificationResourceIT {

    private static final ZonedDateTime DEFAULT_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FROM_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_THRU_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private PartyClassificationRepository partyClassificationRepository;

    @Autowired
    private PartyClassificationService partyClassificationService;

    @Autowired
    private PartyClassificationQueryService partyClassificationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyClassificationMockMvc;

    private PartyClassification partyClassification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyClassification createEntity(EntityManager em) {
        PartyClassification partyClassification = new PartyClassification()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE);
        return partyClassification;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyClassification createUpdatedEntity(EntityManager em) {
        PartyClassification partyClassification = new PartyClassification()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);
        return partyClassification;
    }

    @BeforeEach
    public void initTest() {
        partyClassification = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyClassification() throws Exception {
        int databaseSizeBeforeCreate = partyClassificationRepository.findAll().size();
        // Create the PartyClassification
        restPartyClassificationMockMvc.perform(post("/api/party-classifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyClassification)))
            .andExpect(status().isCreated());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeCreate + 1);
        PartyClassification testPartyClassification = partyClassificationList.get(partyClassificationList.size() - 1);
        assertThat(testPartyClassification.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testPartyClassification.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
    }

    @Test
    @Transactional
    public void createPartyClassificationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyClassificationRepository.findAll().size();

        // Create the PartyClassification with an existing ID
        partyClassification.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyClassificationMockMvc.perform(post("/api/party-classifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyClassification)))
            .andExpect(status().isBadRequest());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyClassifications() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList
        restPartyClassificationMockMvc.perform(get("/api/party-classifications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyClassification.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))));
    }
    
    @Test
    @Transactional
    public void getPartyClassification() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get the partyClassification
        restPartyClassificationMockMvc.perform(get("/api/party-classifications/{id}", partyClassification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyClassification.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(sameInstant(DEFAULT_FROM_DATE)))
            .andExpect(jsonPath("$.thruDate").value(sameInstant(DEFAULT_THRU_DATE)));
    }


    @Test
    @Transactional
    public void getPartyClassificationsByIdFiltering() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        Long id = partyClassification.getId();

        defaultPartyClassificationShouldBeFound("id.equals=" + id);
        defaultPartyClassificationShouldNotBeFound("id.notEquals=" + id);

        defaultPartyClassificationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPartyClassificationShouldNotBeFound("id.greaterThan=" + id);

        defaultPartyClassificationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPartyClassificationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPartyClassificationsByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList where fromDate equals to DEFAULT_FROM_DATE
        defaultPartyClassificationShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the partyClassificationList where fromDate equals to UPDATED_FROM_DATE
        defaultPartyClassificationShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyClassificationsByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList where fromDate not equals to DEFAULT_FROM_DATE
        defaultPartyClassificationShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the partyClassificationList where fromDate not equals to UPDATED_FROM_DATE
        defaultPartyClassificationShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyClassificationsByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultPartyClassificationShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the partyClassificationList where fromDate equals to UPDATED_FROM_DATE
        defaultPartyClassificationShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyClassificationsByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList where fromDate is not null
        defaultPartyClassificationShouldBeFound("fromDate.specified=true");

        // Get all the partyClassificationList where fromDate is null
        defaultPartyClassificationShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartyClassificationsByFromDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList where fromDate is greater than or equal to DEFAULT_FROM_DATE
        defaultPartyClassificationShouldBeFound("fromDate.greaterThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the partyClassificationList where fromDate is greater than or equal to UPDATED_FROM_DATE
        defaultPartyClassificationShouldNotBeFound("fromDate.greaterThanOrEqual=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyClassificationsByFromDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList where fromDate is less than or equal to DEFAULT_FROM_DATE
        defaultPartyClassificationShouldBeFound("fromDate.lessThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the partyClassificationList where fromDate is less than or equal to SMALLER_FROM_DATE
        defaultPartyClassificationShouldNotBeFound("fromDate.lessThanOrEqual=" + SMALLER_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyClassificationsByFromDateIsLessThanSomething() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList where fromDate is less than DEFAULT_FROM_DATE
        defaultPartyClassificationShouldNotBeFound("fromDate.lessThan=" + DEFAULT_FROM_DATE);

        // Get all the partyClassificationList where fromDate is less than UPDATED_FROM_DATE
        defaultPartyClassificationShouldBeFound("fromDate.lessThan=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyClassificationsByFromDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList where fromDate is greater than DEFAULT_FROM_DATE
        defaultPartyClassificationShouldNotBeFound("fromDate.greaterThan=" + DEFAULT_FROM_DATE);

        // Get all the partyClassificationList where fromDate is greater than SMALLER_FROM_DATE
        defaultPartyClassificationShouldBeFound("fromDate.greaterThan=" + SMALLER_FROM_DATE);
    }


    @Test
    @Transactional
    public void getAllPartyClassificationsByThruDateIsEqualToSomething() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList where thruDate equals to DEFAULT_THRU_DATE
        defaultPartyClassificationShouldBeFound("thruDate.equals=" + DEFAULT_THRU_DATE);

        // Get all the partyClassificationList where thruDate equals to UPDATED_THRU_DATE
        defaultPartyClassificationShouldNotBeFound("thruDate.equals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyClassificationsByThruDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList where thruDate not equals to DEFAULT_THRU_DATE
        defaultPartyClassificationShouldNotBeFound("thruDate.notEquals=" + DEFAULT_THRU_DATE);

        // Get all the partyClassificationList where thruDate not equals to UPDATED_THRU_DATE
        defaultPartyClassificationShouldBeFound("thruDate.notEquals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyClassificationsByThruDateIsInShouldWork() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList where thruDate in DEFAULT_THRU_DATE or UPDATED_THRU_DATE
        defaultPartyClassificationShouldBeFound("thruDate.in=" + DEFAULT_THRU_DATE + "," + UPDATED_THRU_DATE);

        // Get all the partyClassificationList where thruDate equals to UPDATED_THRU_DATE
        defaultPartyClassificationShouldNotBeFound("thruDate.in=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyClassificationsByThruDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList where thruDate is not null
        defaultPartyClassificationShouldBeFound("thruDate.specified=true");

        // Get all the partyClassificationList where thruDate is null
        defaultPartyClassificationShouldNotBeFound("thruDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartyClassificationsByThruDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList where thruDate is greater than or equal to DEFAULT_THRU_DATE
        defaultPartyClassificationShouldBeFound("thruDate.greaterThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the partyClassificationList where thruDate is greater than or equal to UPDATED_THRU_DATE
        defaultPartyClassificationShouldNotBeFound("thruDate.greaterThanOrEqual=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyClassificationsByThruDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList where thruDate is less than or equal to DEFAULT_THRU_DATE
        defaultPartyClassificationShouldBeFound("thruDate.lessThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the partyClassificationList where thruDate is less than or equal to SMALLER_THRU_DATE
        defaultPartyClassificationShouldNotBeFound("thruDate.lessThanOrEqual=" + SMALLER_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyClassificationsByThruDateIsLessThanSomething() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList where thruDate is less than DEFAULT_THRU_DATE
        defaultPartyClassificationShouldNotBeFound("thruDate.lessThan=" + DEFAULT_THRU_DATE);

        // Get all the partyClassificationList where thruDate is less than UPDATED_THRU_DATE
        defaultPartyClassificationShouldBeFound("thruDate.lessThan=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllPartyClassificationsByThruDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);

        // Get all the partyClassificationList where thruDate is greater than DEFAULT_THRU_DATE
        defaultPartyClassificationShouldNotBeFound("thruDate.greaterThan=" + DEFAULT_THRU_DATE);

        // Get all the partyClassificationList where thruDate is greater than SMALLER_THRU_DATE
        defaultPartyClassificationShouldBeFound("thruDate.greaterThan=" + SMALLER_THRU_DATE);
    }


    @Test
    @Transactional
    public void getAllPartyClassificationsByPartyIsEqualToSomething() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);
        Party party = PartyResourceIT.createEntity(em);
        em.persist(party);
        em.flush();
        partyClassification.setParty(party);
        partyClassificationRepository.saveAndFlush(partyClassification);
        Long partyId = party.getId();

        // Get all the partyClassificationList where party equals to partyId
        defaultPartyClassificationShouldBeFound("partyId.equals=" + partyId);

        // Get all the partyClassificationList where party equals to partyId + 1
        defaultPartyClassificationShouldNotBeFound("partyId.equals=" + (partyId + 1));
    }


    @Test
    @Transactional
    public void getAllPartyClassificationsByClassificationGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        partyClassificationRepository.saveAndFlush(partyClassification);
        PartyClassificationGroup classificationGroup = PartyClassificationGroupResourceIT.createEntity(em);
        em.persist(classificationGroup);
        em.flush();
        partyClassification.setClassificationGroup(classificationGroup);
        partyClassificationRepository.saveAndFlush(partyClassification);
        Long classificationGroupId = classificationGroup.getId();

        // Get all the partyClassificationList where classificationGroup equals to classificationGroupId
        defaultPartyClassificationShouldBeFound("classificationGroupId.equals=" + classificationGroupId);

        // Get all the partyClassificationList where classificationGroup equals to classificationGroupId + 1
        defaultPartyClassificationShouldNotBeFound("classificationGroupId.equals=" + (classificationGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPartyClassificationShouldBeFound(String filter) throws Exception {
        restPartyClassificationMockMvc.perform(get("/api/party-classifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyClassification.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))));

        // Check, that the count call also returns 1
        restPartyClassificationMockMvc.perform(get("/api/party-classifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPartyClassificationShouldNotBeFound(String filter) throws Exception {
        restPartyClassificationMockMvc.perform(get("/api/party-classifications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPartyClassificationMockMvc.perform(get("/api/party-classifications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPartyClassification() throws Exception {
        // Get the partyClassification
        restPartyClassificationMockMvc.perform(get("/api/party-classifications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyClassification() throws Exception {
        // Initialize the database
        partyClassificationService.save(partyClassification);

        int databaseSizeBeforeUpdate = partyClassificationRepository.findAll().size();

        // Update the partyClassification
        PartyClassification updatedPartyClassification = partyClassificationRepository.findById(partyClassification.getId()).get();
        // Disconnect from session so that the updates on updatedPartyClassification are not directly saved in db
        em.detach(updatedPartyClassification);
        updatedPartyClassification
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);

        restPartyClassificationMockMvc.perform(put("/api/party-classifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyClassification)))
            .andExpect(status().isOk());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeUpdate);
        PartyClassification testPartyClassification = partyClassificationList.get(partyClassificationList.size() - 1);
        assertThat(testPartyClassification.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testPartyClassification.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyClassification() throws Exception {
        int databaseSizeBeforeUpdate = partyClassificationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyClassificationMockMvc.perform(put("/api/party-classifications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyClassification)))
            .andExpect(status().isBadRequest());

        // Validate the PartyClassification in the database
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyClassification() throws Exception {
        // Initialize the database
        partyClassificationService.save(partyClassification);

        int databaseSizeBeforeDelete = partyClassificationRepository.findAll().size();

        // Delete the partyClassification
        restPartyClassificationMockMvc.perform(delete("/api/party-classifications/{id}", partyClassification.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyClassification> partyClassificationList = partyClassificationRepository.findAll();
        assertThat(partyClassificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
