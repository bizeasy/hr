package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.InventoryTransfer;
import com.hr.domain.Status;
import com.hr.domain.InventoryItem;
import com.hr.domain.Facility;
import com.hr.domain.ItemIssuance;
import com.hr.repository.InventoryTransferRepository;
import com.hr.service.InventoryTransferService;
import com.hr.service.dto.InventoryTransferCriteria;
import com.hr.service.InventoryTransferQueryService;

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
 * Integration tests for the {@link InventoryTransferResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InventoryTransferResourceIT {

    private static final ZonedDateTime DEFAULT_SENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SENT_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_SENT_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_RECEIVED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RECEIVED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_RECEIVED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private InventoryTransferRepository inventoryTransferRepository;

    @Autowired
    private InventoryTransferService inventoryTransferService;

    @Autowired
    private InventoryTransferQueryService inventoryTransferQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryTransferMockMvc;

    private InventoryTransfer inventoryTransfer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryTransfer createEntity(EntityManager em) {
        InventoryTransfer inventoryTransfer = new InventoryTransfer()
            .sentDate(DEFAULT_SENT_DATE)
            .receivedDate(DEFAULT_RECEIVED_DATE)
            .comments(DEFAULT_COMMENTS);
        return inventoryTransfer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryTransfer createUpdatedEntity(EntityManager em) {
        InventoryTransfer inventoryTransfer = new InventoryTransfer()
            .sentDate(UPDATED_SENT_DATE)
            .receivedDate(UPDATED_RECEIVED_DATE)
            .comments(UPDATED_COMMENTS);
        return inventoryTransfer;
    }

    @BeforeEach
    public void initTest() {
        inventoryTransfer = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventoryTransfer() throws Exception {
        int databaseSizeBeforeCreate = inventoryTransferRepository.findAll().size();
        // Create the InventoryTransfer
        restInventoryTransferMockMvc.perform(post("/api/inventory-transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryTransfer)))
            .andExpect(status().isCreated());

        // Validate the InventoryTransfer in the database
        List<InventoryTransfer> inventoryTransferList = inventoryTransferRepository.findAll();
        assertThat(inventoryTransferList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryTransfer testInventoryTransfer = inventoryTransferList.get(inventoryTransferList.size() - 1);
        assertThat(testInventoryTransfer.getSentDate()).isEqualTo(DEFAULT_SENT_DATE);
        assertThat(testInventoryTransfer.getReceivedDate()).isEqualTo(DEFAULT_RECEIVED_DATE);
        assertThat(testInventoryTransfer.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createInventoryTransferWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryTransferRepository.findAll().size();

        // Create the InventoryTransfer with an existing ID
        inventoryTransfer.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryTransferMockMvc.perform(post("/api/inventory-transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryTransfer)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryTransfer in the database
        List<InventoryTransfer> inventoryTransferList = inventoryTransferRepository.findAll();
        assertThat(inventoryTransferList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInventoryTransfers() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList
        restInventoryTransferMockMvc.perform(get("/api/inventory-transfers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryTransfer.getId().intValue())))
            .andExpect(jsonPath("$.[*].sentDate").value(hasItem(sameInstant(DEFAULT_SENT_DATE))))
            .andExpect(jsonPath("$.[*].receivedDate").value(hasItem(sameInstant(DEFAULT_RECEIVED_DATE))))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
    
    @Test
    @Transactional
    public void getInventoryTransfer() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get the inventoryTransfer
        restInventoryTransferMockMvc.perform(get("/api/inventory-transfers/{id}", inventoryTransfer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryTransfer.getId().intValue()))
            .andExpect(jsonPath("$.sentDate").value(sameInstant(DEFAULT_SENT_DATE)))
            .andExpect(jsonPath("$.receivedDate").value(sameInstant(DEFAULT_RECEIVED_DATE)))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }


    @Test
    @Transactional
    public void getInventoryTransfersByIdFiltering() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        Long id = inventoryTransfer.getId();

        defaultInventoryTransferShouldBeFound("id.equals=" + id);
        defaultInventoryTransferShouldNotBeFound("id.notEquals=" + id);

        defaultInventoryTransferShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInventoryTransferShouldNotBeFound("id.greaterThan=" + id);

        defaultInventoryTransferShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInventoryTransferShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInventoryTransfersBySentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where sentDate equals to DEFAULT_SENT_DATE
        defaultInventoryTransferShouldBeFound("sentDate.equals=" + DEFAULT_SENT_DATE);

        // Get all the inventoryTransferList where sentDate equals to UPDATED_SENT_DATE
        defaultInventoryTransferShouldNotBeFound("sentDate.equals=" + UPDATED_SENT_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersBySentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where sentDate not equals to DEFAULT_SENT_DATE
        defaultInventoryTransferShouldNotBeFound("sentDate.notEquals=" + DEFAULT_SENT_DATE);

        // Get all the inventoryTransferList where sentDate not equals to UPDATED_SENT_DATE
        defaultInventoryTransferShouldBeFound("sentDate.notEquals=" + UPDATED_SENT_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersBySentDateIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where sentDate in DEFAULT_SENT_DATE or UPDATED_SENT_DATE
        defaultInventoryTransferShouldBeFound("sentDate.in=" + DEFAULT_SENT_DATE + "," + UPDATED_SENT_DATE);

        // Get all the inventoryTransferList where sentDate equals to UPDATED_SENT_DATE
        defaultInventoryTransferShouldNotBeFound("sentDate.in=" + UPDATED_SENT_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersBySentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where sentDate is not null
        defaultInventoryTransferShouldBeFound("sentDate.specified=true");

        // Get all the inventoryTransferList where sentDate is null
        defaultInventoryTransferShouldNotBeFound("sentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersBySentDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where sentDate is greater than or equal to DEFAULT_SENT_DATE
        defaultInventoryTransferShouldBeFound("sentDate.greaterThanOrEqual=" + DEFAULT_SENT_DATE);

        // Get all the inventoryTransferList where sentDate is greater than or equal to UPDATED_SENT_DATE
        defaultInventoryTransferShouldNotBeFound("sentDate.greaterThanOrEqual=" + UPDATED_SENT_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersBySentDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where sentDate is less than or equal to DEFAULT_SENT_DATE
        defaultInventoryTransferShouldBeFound("sentDate.lessThanOrEqual=" + DEFAULT_SENT_DATE);

        // Get all the inventoryTransferList where sentDate is less than or equal to SMALLER_SENT_DATE
        defaultInventoryTransferShouldNotBeFound("sentDate.lessThanOrEqual=" + SMALLER_SENT_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersBySentDateIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where sentDate is less than DEFAULT_SENT_DATE
        defaultInventoryTransferShouldNotBeFound("sentDate.lessThan=" + DEFAULT_SENT_DATE);

        // Get all the inventoryTransferList where sentDate is less than UPDATED_SENT_DATE
        defaultInventoryTransferShouldBeFound("sentDate.lessThan=" + UPDATED_SENT_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersBySentDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where sentDate is greater than DEFAULT_SENT_DATE
        defaultInventoryTransferShouldNotBeFound("sentDate.greaterThan=" + DEFAULT_SENT_DATE);

        // Get all the inventoryTransferList where sentDate is greater than SMALLER_SENT_DATE
        defaultInventoryTransferShouldBeFound("sentDate.greaterThan=" + SMALLER_SENT_DATE);
    }


    @Test
    @Transactional
    public void getAllInventoryTransfersByReceivedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where receivedDate equals to DEFAULT_RECEIVED_DATE
        defaultInventoryTransferShouldBeFound("receivedDate.equals=" + DEFAULT_RECEIVED_DATE);

        // Get all the inventoryTransferList where receivedDate equals to UPDATED_RECEIVED_DATE
        defaultInventoryTransferShouldNotBeFound("receivedDate.equals=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersByReceivedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where receivedDate not equals to DEFAULT_RECEIVED_DATE
        defaultInventoryTransferShouldNotBeFound("receivedDate.notEquals=" + DEFAULT_RECEIVED_DATE);

        // Get all the inventoryTransferList where receivedDate not equals to UPDATED_RECEIVED_DATE
        defaultInventoryTransferShouldBeFound("receivedDate.notEquals=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersByReceivedDateIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where receivedDate in DEFAULT_RECEIVED_DATE or UPDATED_RECEIVED_DATE
        defaultInventoryTransferShouldBeFound("receivedDate.in=" + DEFAULT_RECEIVED_DATE + "," + UPDATED_RECEIVED_DATE);

        // Get all the inventoryTransferList where receivedDate equals to UPDATED_RECEIVED_DATE
        defaultInventoryTransferShouldNotBeFound("receivedDate.in=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersByReceivedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where receivedDate is not null
        defaultInventoryTransferShouldBeFound("receivedDate.specified=true");

        // Get all the inventoryTransferList where receivedDate is null
        defaultInventoryTransferShouldNotBeFound("receivedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersByReceivedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where receivedDate is greater than or equal to DEFAULT_RECEIVED_DATE
        defaultInventoryTransferShouldBeFound("receivedDate.greaterThanOrEqual=" + DEFAULT_RECEIVED_DATE);

        // Get all the inventoryTransferList where receivedDate is greater than or equal to UPDATED_RECEIVED_DATE
        defaultInventoryTransferShouldNotBeFound("receivedDate.greaterThanOrEqual=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersByReceivedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where receivedDate is less than or equal to DEFAULT_RECEIVED_DATE
        defaultInventoryTransferShouldBeFound("receivedDate.lessThanOrEqual=" + DEFAULT_RECEIVED_DATE);

        // Get all the inventoryTransferList where receivedDate is less than or equal to SMALLER_RECEIVED_DATE
        defaultInventoryTransferShouldNotBeFound("receivedDate.lessThanOrEqual=" + SMALLER_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersByReceivedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where receivedDate is less than DEFAULT_RECEIVED_DATE
        defaultInventoryTransferShouldNotBeFound("receivedDate.lessThan=" + DEFAULT_RECEIVED_DATE);

        // Get all the inventoryTransferList where receivedDate is less than UPDATED_RECEIVED_DATE
        defaultInventoryTransferShouldBeFound("receivedDate.lessThan=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersByReceivedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where receivedDate is greater than DEFAULT_RECEIVED_DATE
        defaultInventoryTransferShouldNotBeFound("receivedDate.greaterThan=" + DEFAULT_RECEIVED_DATE);

        // Get all the inventoryTransferList where receivedDate is greater than SMALLER_RECEIVED_DATE
        defaultInventoryTransferShouldBeFound("receivedDate.greaterThan=" + SMALLER_RECEIVED_DATE);
    }


    @Test
    @Transactional
    public void getAllInventoryTransfersByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where comments equals to DEFAULT_COMMENTS
        defaultInventoryTransferShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the inventoryTransferList where comments equals to UPDATED_COMMENTS
        defaultInventoryTransferShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where comments not equals to DEFAULT_COMMENTS
        defaultInventoryTransferShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the inventoryTransferList where comments not equals to UPDATED_COMMENTS
        defaultInventoryTransferShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultInventoryTransferShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the inventoryTransferList where comments equals to UPDATED_COMMENTS
        defaultInventoryTransferShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where comments is not null
        defaultInventoryTransferShouldBeFound("comments.specified=true");

        // Get all the inventoryTransferList where comments is null
        defaultInventoryTransferShouldNotBeFound("comments.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoryTransfersByCommentsContainsSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where comments contains DEFAULT_COMMENTS
        defaultInventoryTransferShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the inventoryTransferList where comments contains UPDATED_COMMENTS
        defaultInventoryTransferShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInventoryTransfersByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);

        // Get all the inventoryTransferList where comments does not contain DEFAULT_COMMENTS
        defaultInventoryTransferShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the inventoryTransferList where comments does not contain UPDATED_COMMENTS
        defaultInventoryTransferShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllInventoryTransfersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        inventoryTransfer.setStatus(status);
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);
        Long statusId = status.getId();

        // Get all the inventoryTransferList where status equals to statusId
        defaultInventoryTransferShouldBeFound("statusId.equals=" + statusId);

        // Get all the inventoryTransferList where status equals to statusId + 1
        defaultInventoryTransferShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryTransfersByInventoryItemIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);
        InventoryItem inventoryItem = InventoryItemResourceIT.createEntity(em);
        em.persist(inventoryItem);
        em.flush();
        inventoryTransfer.setInventoryItem(inventoryItem);
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);
        Long inventoryItemId = inventoryItem.getId();

        // Get all the inventoryTransferList where inventoryItem equals to inventoryItemId
        defaultInventoryTransferShouldBeFound("inventoryItemId.equals=" + inventoryItemId);

        // Get all the inventoryTransferList where inventoryItem equals to inventoryItemId + 1
        defaultInventoryTransferShouldNotBeFound("inventoryItemId.equals=" + (inventoryItemId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryTransfersByFacilityIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);
        Facility facility = FacilityResourceIT.createEntity(em);
        em.persist(facility);
        em.flush();
        inventoryTransfer.setFacility(facility);
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);
        Long facilityId = facility.getId();

        // Get all the inventoryTransferList where facility equals to facilityId
        defaultInventoryTransferShouldBeFound("facilityId.equals=" + facilityId);

        // Get all the inventoryTransferList where facility equals to facilityId + 1
        defaultInventoryTransferShouldNotBeFound("facilityId.equals=" + (facilityId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryTransfersByFacilityToIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);
        Facility facilityTo = FacilityResourceIT.createEntity(em);
        em.persist(facilityTo);
        em.flush();
        inventoryTransfer.setFacilityTo(facilityTo);
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);
        Long facilityToId = facilityTo.getId();

        // Get all the inventoryTransferList where facilityTo equals to facilityToId
        defaultInventoryTransferShouldBeFound("facilityToId.equals=" + facilityToId);

        // Get all the inventoryTransferList where facilityTo equals to facilityToId + 1
        defaultInventoryTransferShouldNotBeFound("facilityToId.equals=" + (facilityToId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryTransfersByIssuanceIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);
        ItemIssuance issuance = ItemIssuanceResourceIT.createEntity(em);
        em.persist(issuance);
        em.flush();
        inventoryTransfer.setIssuance(issuance);
        inventoryTransferRepository.saveAndFlush(inventoryTransfer);
        Long issuanceId = issuance.getId();

        // Get all the inventoryTransferList where issuance equals to issuanceId
        defaultInventoryTransferShouldBeFound("issuanceId.equals=" + issuanceId);

        // Get all the inventoryTransferList where issuance equals to issuanceId + 1
        defaultInventoryTransferShouldNotBeFound("issuanceId.equals=" + (issuanceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInventoryTransferShouldBeFound(String filter) throws Exception {
        restInventoryTransferMockMvc.perform(get("/api/inventory-transfers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryTransfer.getId().intValue())))
            .andExpect(jsonPath("$.[*].sentDate").value(hasItem(sameInstant(DEFAULT_SENT_DATE))))
            .andExpect(jsonPath("$.[*].receivedDate").value(hasItem(sameInstant(DEFAULT_RECEIVED_DATE))))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));

        // Check, that the count call also returns 1
        restInventoryTransferMockMvc.perform(get("/api/inventory-transfers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInventoryTransferShouldNotBeFound(String filter) throws Exception {
        restInventoryTransferMockMvc.perform(get("/api/inventory-transfers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventoryTransferMockMvc.perform(get("/api/inventory-transfers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryTransfer() throws Exception {
        // Get the inventoryTransfer
        restInventoryTransferMockMvc.perform(get("/api/inventory-transfers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryTransfer() throws Exception {
        // Initialize the database
        inventoryTransferService.save(inventoryTransfer);

        int databaseSizeBeforeUpdate = inventoryTransferRepository.findAll().size();

        // Update the inventoryTransfer
        InventoryTransfer updatedInventoryTransfer = inventoryTransferRepository.findById(inventoryTransfer.getId()).get();
        // Disconnect from session so that the updates on updatedInventoryTransfer are not directly saved in db
        em.detach(updatedInventoryTransfer);
        updatedInventoryTransfer
            .sentDate(UPDATED_SENT_DATE)
            .receivedDate(UPDATED_RECEIVED_DATE)
            .comments(UPDATED_COMMENTS);

        restInventoryTransferMockMvc.perform(put("/api/inventory-transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventoryTransfer)))
            .andExpect(status().isOk());

        // Validate the InventoryTransfer in the database
        List<InventoryTransfer> inventoryTransferList = inventoryTransferRepository.findAll();
        assertThat(inventoryTransferList).hasSize(databaseSizeBeforeUpdate);
        InventoryTransfer testInventoryTransfer = inventoryTransferList.get(inventoryTransferList.size() - 1);
        assertThat(testInventoryTransfer.getSentDate()).isEqualTo(UPDATED_SENT_DATE);
        assertThat(testInventoryTransfer.getReceivedDate()).isEqualTo(UPDATED_RECEIVED_DATE);
        assertThat(testInventoryTransfer.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingInventoryTransfer() throws Exception {
        int databaseSizeBeforeUpdate = inventoryTransferRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryTransferMockMvc.perform(put("/api/inventory-transfers")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryTransfer)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryTransfer in the database
        List<InventoryTransfer> inventoryTransferList = inventoryTransferRepository.findAll();
        assertThat(inventoryTransferList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventoryTransfer() throws Exception {
        // Initialize the database
        inventoryTransferService.save(inventoryTransfer);

        int databaseSizeBeforeDelete = inventoryTransferRepository.findAll().size();

        // Delete the inventoryTransfer
        restInventoryTransferMockMvc.perform(delete("/api/inventory-transfers/{id}", inventoryTransfer.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InventoryTransfer> inventoryTransferList = inventoryTransferRepository.findAll();
        assertThat(inventoryTransferList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
