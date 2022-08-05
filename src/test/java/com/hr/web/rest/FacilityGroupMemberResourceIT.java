package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.FacilityGroupMember;
import com.hr.domain.Facility;
import com.hr.domain.FacilityGroup;
import com.hr.repository.FacilityGroupMemberRepository;
import com.hr.service.FacilityGroupMemberService;
import com.hr.service.dto.FacilityGroupMemberCriteria;
import com.hr.service.FacilityGroupMemberQueryService;

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
 * Integration tests for the {@link FacilityGroupMemberResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FacilityGroupMemberResourceIT {

    private static final ZonedDateTime DEFAULT_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FROM_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_THRU_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;
    private static final Integer SMALLER_SEQUENCE_NO = 1 - 1;

    @Autowired
    private FacilityGroupMemberRepository facilityGroupMemberRepository;

    @Autowired
    private FacilityGroupMemberService facilityGroupMemberService;

    @Autowired
    private FacilityGroupMemberQueryService facilityGroupMemberQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacilityGroupMemberMockMvc;

    private FacilityGroupMember facilityGroupMember;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityGroupMember createEntity(EntityManager em) {
        FacilityGroupMember facilityGroupMember = new FacilityGroupMember()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .sequenceNo(DEFAULT_SEQUENCE_NO);
        return facilityGroupMember;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FacilityGroupMember createUpdatedEntity(EntityManager em) {
        FacilityGroupMember facilityGroupMember = new FacilityGroupMember()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .sequenceNo(UPDATED_SEQUENCE_NO);
        return facilityGroupMember;
    }

    @BeforeEach
    public void initTest() {
        facilityGroupMember = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacilityGroupMember() throws Exception {
        int databaseSizeBeforeCreate = facilityGroupMemberRepository.findAll().size();
        // Create the FacilityGroupMember
        restFacilityGroupMemberMockMvc.perform(post("/api/facility-group-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityGroupMember)))
            .andExpect(status().isCreated());

        // Validate the FacilityGroupMember in the database
        List<FacilityGroupMember> facilityGroupMemberList = facilityGroupMemberRepository.findAll();
        assertThat(facilityGroupMemberList).hasSize(databaseSizeBeforeCreate + 1);
        FacilityGroupMember testFacilityGroupMember = facilityGroupMemberList.get(facilityGroupMemberList.size() - 1);
        assertThat(testFacilityGroupMember.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testFacilityGroupMember.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testFacilityGroupMember.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void createFacilityGroupMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityGroupMemberRepository.findAll().size();

        // Create the FacilityGroupMember with an existing ID
        facilityGroupMember.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityGroupMemberMockMvc.perform(post("/api/facility-group-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityGroupMember)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityGroupMember in the database
        List<FacilityGroupMember> facilityGroupMemberList = facilityGroupMemberRepository.findAll();
        assertThat(facilityGroupMemberList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFacilityGroupMembers() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList
        restFacilityGroupMemberMockMvc.perform(get("/api/facility-group-members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityGroupMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)));
    }
    
    @Test
    @Transactional
    public void getFacilityGroupMember() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get the facilityGroupMember
        restFacilityGroupMemberMockMvc.perform(get("/api/facility-group-members/{id}", facilityGroupMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facilityGroupMember.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(sameInstant(DEFAULT_FROM_DATE)))
            .andExpect(jsonPath("$.thruDate").value(sameInstant(DEFAULT_THRU_DATE)))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO));
    }


    @Test
    @Transactional
    public void getFacilityGroupMembersByIdFiltering() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        Long id = facilityGroupMember.getId();

        defaultFacilityGroupMemberShouldBeFound("id.equals=" + id);
        defaultFacilityGroupMemberShouldNotBeFound("id.notEquals=" + id);

        defaultFacilityGroupMemberShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFacilityGroupMemberShouldNotBeFound("id.greaterThan=" + id);

        defaultFacilityGroupMemberShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFacilityGroupMemberShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFacilityGroupMembersByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where fromDate equals to DEFAULT_FROM_DATE
        defaultFacilityGroupMemberShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the facilityGroupMemberList where fromDate equals to UPDATED_FROM_DATE
        defaultFacilityGroupMemberShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where fromDate not equals to DEFAULT_FROM_DATE
        defaultFacilityGroupMemberShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the facilityGroupMemberList where fromDate not equals to UPDATED_FROM_DATE
        defaultFacilityGroupMemberShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultFacilityGroupMemberShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the facilityGroupMemberList where fromDate equals to UPDATED_FROM_DATE
        defaultFacilityGroupMemberShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where fromDate is not null
        defaultFacilityGroupMemberShouldBeFound("fromDate.specified=true");

        // Get all the facilityGroupMemberList where fromDate is null
        defaultFacilityGroupMemberShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersByFromDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where fromDate is greater than or equal to DEFAULT_FROM_DATE
        defaultFacilityGroupMemberShouldBeFound("fromDate.greaterThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the facilityGroupMemberList where fromDate is greater than or equal to UPDATED_FROM_DATE
        defaultFacilityGroupMemberShouldNotBeFound("fromDate.greaterThanOrEqual=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersByFromDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where fromDate is less than or equal to DEFAULT_FROM_DATE
        defaultFacilityGroupMemberShouldBeFound("fromDate.lessThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the facilityGroupMemberList where fromDate is less than or equal to SMALLER_FROM_DATE
        defaultFacilityGroupMemberShouldNotBeFound("fromDate.lessThanOrEqual=" + SMALLER_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersByFromDateIsLessThanSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where fromDate is less than DEFAULT_FROM_DATE
        defaultFacilityGroupMemberShouldNotBeFound("fromDate.lessThan=" + DEFAULT_FROM_DATE);

        // Get all the facilityGroupMemberList where fromDate is less than UPDATED_FROM_DATE
        defaultFacilityGroupMemberShouldBeFound("fromDate.lessThan=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersByFromDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where fromDate is greater than DEFAULT_FROM_DATE
        defaultFacilityGroupMemberShouldNotBeFound("fromDate.greaterThan=" + DEFAULT_FROM_DATE);

        // Get all the facilityGroupMemberList where fromDate is greater than SMALLER_FROM_DATE
        defaultFacilityGroupMemberShouldBeFound("fromDate.greaterThan=" + SMALLER_FROM_DATE);
    }


    @Test
    @Transactional
    public void getAllFacilityGroupMembersByThruDateIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where thruDate equals to DEFAULT_THRU_DATE
        defaultFacilityGroupMemberShouldBeFound("thruDate.equals=" + DEFAULT_THRU_DATE);

        // Get all the facilityGroupMemberList where thruDate equals to UPDATED_THRU_DATE
        defaultFacilityGroupMemberShouldNotBeFound("thruDate.equals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersByThruDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where thruDate not equals to DEFAULT_THRU_DATE
        defaultFacilityGroupMemberShouldNotBeFound("thruDate.notEquals=" + DEFAULT_THRU_DATE);

        // Get all the facilityGroupMemberList where thruDate not equals to UPDATED_THRU_DATE
        defaultFacilityGroupMemberShouldBeFound("thruDate.notEquals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersByThruDateIsInShouldWork() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where thruDate in DEFAULT_THRU_DATE or UPDATED_THRU_DATE
        defaultFacilityGroupMemberShouldBeFound("thruDate.in=" + DEFAULT_THRU_DATE + "," + UPDATED_THRU_DATE);

        // Get all the facilityGroupMemberList where thruDate equals to UPDATED_THRU_DATE
        defaultFacilityGroupMemberShouldNotBeFound("thruDate.in=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersByThruDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where thruDate is not null
        defaultFacilityGroupMemberShouldBeFound("thruDate.specified=true");

        // Get all the facilityGroupMemberList where thruDate is null
        defaultFacilityGroupMemberShouldNotBeFound("thruDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersByThruDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where thruDate is greater than or equal to DEFAULT_THRU_DATE
        defaultFacilityGroupMemberShouldBeFound("thruDate.greaterThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the facilityGroupMemberList where thruDate is greater than or equal to UPDATED_THRU_DATE
        defaultFacilityGroupMemberShouldNotBeFound("thruDate.greaterThanOrEqual=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersByThruDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where thruDate is less than or equal to DEFAULT_THRU_DATE
        defaultFacilityGroupMemberShouldBeFound("thruDate.lessThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the facilityGroupMemberList where thruDate is less than or equal to SMALLER_THRU_DATE
        defaultFacilityGroupMemberShouldNotBeFound("thruDate.lessThanOrEqual=" + SMALLER_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersByThruDateIsLessThanSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where thruDate is less than DEFAULT_THRU_DATE
        defaultFacilityGroupMemberShouldNotBeFound("thruDate.lessThan=" + DEFAULT_THRU_DATE);

        // Get all the facilityGroupMemberList where thruDate is less than UPDATED_THRU_DATE
        defaultFacilityGroupMemberShouldBeFound("thruDate.lessThan=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersByThruDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where thruDate is greater than DEFAULT_THRU_DATE
        defaultFacilityGroupMemberShouldNotBeFound("thruDate.greaterThan=" + DEFAULT_THRU_DATE);

        // Get all the facilityGroupMemberList where thruDate is greater than SMALLER_THRU_DATE
        defaultFacilityGroupMemberShouldBeFound("thruDate.greaterThan=" + SMALLER_THRU_DATE);
    }


    @Test
    @Transactional
    public void getAllFacilityGroupMembersBySequenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where sequenceNo equals to DEFAULT_SEQUENCE_NO
        defaultFacilityGroupMemberShouldBeFound("sequenceNo.equals=" + DEFAULT_SEQUENCE_NO);

        // Get all the facilityGroupMemberList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultFacilityGroupMemberShouldNotBeFound("sequenceNo.equals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersBySequenceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where sequenceNo not equals to DEFAULT_SEQUENCE_NO
        defaultFacilityGroupMemberShouldNotBeFound("sequenceNo.notEquals=" + DEFAULT_SEQUENCE_NO);

        // Get all the facilityGroupMemberList where sequenceNo not equals to UPDATED_SEQUENCE_NO
        defaultFacilityGroupMemberShouldBeFound("sequenceNo.notEquals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersBySequenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where sequenceNo in DEFAULT_SEQUENCE_NO or UPDATED_SEQUENCE_NO
        defaultFacilityGroupMemberShouldBeFound("sequenceNo.in=" + DEFAULT_SEQUENCE_NO + "," + UPDATED_SEQUENCE_NO);

        // Get all the facilityGroupMemberList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultFacilityGroupMemberShouldNotBeFound("sequenceNo.in=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersBySequenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where sequenceNo is not null
        defaultFacilityGroupMemberShouldBeFound("sequenceNo.specified=true");

        // Get all the facilityGroupMemberList where sequenceNo is null
        defaultFacilityGroupMemberShouldNotBeFound("sequenceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersBySequenceNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where sequenceNo is greater than or equal to DEFAULT_SEQUENCE_NO
        defaultFacilityGroupMemberShouldBeFound("sequenceNo.greaterThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the facilityGroupMemberList where sequenceNo is greater than or equal to UPDATED_SEQUENCE_NO
        defaultFacilityGroupMemberShouldNotBeFound("sequenceNo.greaterThanOrEqual=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersBySequenceNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where sequenceNo is less than or equal to DEFAULT_SEQUENCE_NO
        defaultFacilityGroupMemberShouldBeFound("sequenceNo.lessThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the facilityGroupMemberList where sequenceNo is less than or equal to SMALLER_SEQUENCE_NO
        defaultFacilityGroupMemberShouldNotBeFound("sequenceNo.lessThanOrEqual=" + SMALLER_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersBySequenceNoIsLessThanSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where sequenceNo is less than DEFAULT_SEQUENCE_NO
        defaultFacilityGroupMemberShouldNotBeFound("sequenceNo.lessThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the facilityGroupMemberList where sequenceNo is less than UPDATED_SEQUENCE_NO
        defaultFacilityGroupMemberShouldBeFound("sequenceNo.lessThan=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllFacilityGroupMembersBySequenceNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);

        // Get all the facilityGroupMemberList where sequenceNo is greater than DEFAULT_SEQUENCE_NO
        defaultFacilityGroupMemberShouldNotBeFound("sequenceNo.greaterThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the facilityGroupMemberList where sequenceNo is greater than SMALLER_SEQUENCE_NO
        defaultFacilityGroupMemberShouldBeFound("sequenceNo.greaterThan=" + SMALLER_SEQUENCE_NO);
    }


    @Test
    @Transactional
    public void getAllFacilityGroupMembersByFacilityIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);
        Facility facility = FacilityResourceIT.createEntity(em);
        em.persist(facility);
        em.flush();
        facilityGroupMember.setFacility(facility);
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);
        Long facilityId = facility.getId();

        // Get all the facilityGroupMemberList where facility equals to facilityId
        defaultFacilityGroupMemberShouldBeFound("facilityId.equals=" + facilityId);

        // Get all the facilityGroupMemberList where facility equals to facilityId + 1
        defaultFacilityGroupMemberShouldNotBeFound("facilityId.equals=" + (facilityId + 1));
    }


    @Test
    @Transactional
    public void getAllFacilityGroupMembersByFacilityGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);
        FacilityGroup facilityGroup = FacilityGroupResourceIT.createEntity(em);
        em.persist(facilityGroup);
        em.flush();
        facilityGroupMember.setFacilityGroup(facilityGroup);
        facilityGroupMemberRepository.saveAndFlush(facilityGroupMember);
        Long facilityGroupId = facilityGroup.getId();

        // Get all the facilityGroupMemberList where facilityGroup equals to facilityGroupId
        defaultFacilityGroupMemberShouldBeFound("facilityGroupId.equals=" + facilityGroupId);

        // Get all the facilityGroupMemberList where facilityGroup equals to facilityGroupId + 1
        defaultFacilityGroupMemberShouldNotBeFound("facilityGroupId.equals=" + (facilityGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFacilityGroupMemberShouldBeFound(String filter) throws Exception {
        restFacilityGroupMemberMockMvc.perform(get("/api/facility-group-members?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facilityGroupMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)));

        // Check, that the count call also returns 1
        restFacilityGroupMemberMockMvc.perform(get("/api/facility-group-members/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFacilityGroupMemberShouldNotBeFound(String filter) throws Exception {
        restFacilityGroupMemberMockMvc.perform(get("/api/facility-group-members?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFacilityGroupMemberMockMvc.perform(get("/api/facility-group-members/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFacilityGroupMember() throws Exception {
        // Get the facilityGroupMember
        restFacilityGroupMemberMockMvc.perform(get("/api/facility-group-members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacilityGroupMember() throws Exception {
        // Initialize the database
        facilityGroupMemberService.save(facilityGroupMember);

        int databaseSizeBeforeUpdate = facilityGroupMemberRepository.findAll().size();

        // Update the facilityGroupMember
        FacilityGroupMember updatedFacilityGroupMember = facilityGroupMemberRepository.findById(facilityGroupMember.getId()).get();
        // Disconnect from session so that the updates on updatedFacilityGroupMember are not directly saved in db
        em.detach(updatedFacilityGroupMember);
        updatedFacilityGroupMember
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .sequenceNo(UPDATED_SEQUENCE_NO);

        restFacilityGroupMemberMockMvc.perform(put("/api/facility-group-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFacilityGroupMember)))
            .andExpect(status().isOk());

        // Validate the FacilityGroupMember in the database
        List<FacilityGroupMember> facilityGroupMemberList = facilityGroupMemberRepository.findAll();
        assertThat(facilityGroupMemberList).hasSize(databaseSizeBeforeUpdate);
        FacilityGroupMember testFacilityGroupMember = facilityGroupMemberList.get(facilityGroupMemberList.size() - 1);
        assertThat(testFacilityGroupMember.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testFacilityGroupMember.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testFacilityGroupMember.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void updateNonExistingFacilityGroupMember() throws Exception {
        int databaseSizeBeforeUpdate = facilityGroupMemberRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacilityGroupMemberMockMvc.perform(put("/api/facility-group-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facilityGroupMember)))
            .andExpect(status().isBadRequest());

        // Validate the FacilityGroupMember in the database
        List<FacilityGroupMember> facilityGroupMemberList = facilityGroupMemberRepository.findAll();
        assertThat(facilityGroupMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacilityGroupMember() throws Exception {
        // Initialize the database
        facilityGroupMemberService.save(facilityGroupMember);

        int databaseSizeBeforeDelete = facilityGroupMemberRepository.findAll().size();

        // Delete the facilityGroupMember
        restFacilityGroupMemberMockMvc.perform(delete("/api/facility-group-members/{id}", facilityGroupMember.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FacilityGroupMember> facilityGroupMemberList = facilityGroupMemberRepository.findAll();
        assertThat(facilityGroupMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
