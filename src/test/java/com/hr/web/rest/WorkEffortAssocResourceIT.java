package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.WorkEffortAssoc;
import com.hr.domain.WorkEffortAssocType;
import com.hr.domain.WorkEffort;
import com.hr.repository.WorkEffortAssocRepository;
import com.hr.service.WorkEffortAssocService;
import com.hr.service.dto.WorkEffortAssocCriteria;
import com.hr.service.WorkEffortAssocQueryService;

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
 * Integration tests for the {@link WorkEffortAssocResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkEffortAssocResourceIT {

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;
    private static final Integer SMALLER_SEQUENCE_NO = 1 - 1;

    private static final ZonedDateTime DEFAULT_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FROM_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_THRU_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private WorkEffortAssocRepository workEffortAssocRepository;

    @Autowired
    private WorkEffortAssocService workEffortAssocService;

    @Autowired
    private WorkEffortAssocQueryService workEffortAssocQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkEffortAssocMockMvc;

    private WorkEffortAssoc workEffortAssoc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffortAssoc createEntity(EntityManager em) {
        WorkEffortAssoc workEffortAssoc = new WorkEffortAssoc()
            .sequenceNo(DEFAULT_SEQUENCE_NO)
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE);
        return workEffortAssoc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffortAssoc createUpdatedEntity(EntityManager em) {
        WorkEffortAssoc workEffortAssoc = new WorkEffortAssoc()
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);
        return workEffortAssoc;
    }

    @BeforeEach
    public void initTest() {
        workEffortAssoc = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkEffortAssoc() throws Exception {
        int databaseSizeBeforeCreate = workEffortAssocRepository.findAll().size();
        // Create the WorkEffortAssoc
        restWorkEffortAssocMockMvc.perform(post("/api/work-effort-assocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortAssoc)))
            .andExpect(status().isCreated());

        // Validate the WorkEffortAssoc in the database
        List<WorkEffortAssoc> workEffortAssocList = workEffortAssocRepository.findAll();
        assertThat(workEffortAssocList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEffortAssoc testWorkEffortAssoc = workEffortAssocList.get(workEffortAssocList.size() - 1);
        assertThat(testWorkEffortAssoc.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
        assertThat(testWorkEffortAssoc.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testWorkEffortAssoc.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
    }

    @Test
    @Transactional
    public void createWorkEffortAssocWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workEffortAssocRepository.findAll().size();

        // Create the WorkEffortAssoc with an existing ID
        workEffortAssoc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkEffortAssocMockMvc.perform(post("/api/work-effort-assocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortAssoc)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffortAssoc in the database
        List<WorkEffortAssoc> workEffortAssocList = workEffortAssocRepository.findAll();
        assertThat(workEffortAssocList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkEffortAssocs() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList
        restWorkEffortAssocMockMvc.perform(get("/api/work-effort-assocs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEffortAssoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))));
    }
    
    @Test
    @Transactional
    public void getWorkEffortAssoc() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get the workEffortAssoc
        restWorkEffortAssocMockMvc.perform(get("/api/work-effort-assocs/{id}", workEffortAssoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workEffortAssoc.getId().intValue()))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO))
            .andExpect(jsonPath("$.fromDate").value(sameInstant(DEFAULT_FROM_DATE)))
            .andExpect(jsonPath("$.thruDate").value(sameInstant(DEFAULT_THRU_DATE)));
    }


    @Test
    @Transactional
    public void getWorkEffortAssocsByIdFiltering() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        Long id = workEffortAssoc.getId();

        defaultWorkEffortAssocShouldBeFound("id.equals=" + id);
        defaultWorkEffortAssocShouldNotBeFound("id.notEquals=" + id);

        defaultWorkEffortAssocShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkEffortAssocShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkEffortAssocShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkEffortAssocShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWorkEffortAssocsBySequenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where sequenceNo equals to DEFAULT_SEQUENCE_NO
        defaultWorkEffortAssocShouldBeFound("sequenceNo.equals=" + DEFAULT_SEQUENCE_NO);

        // Get all the workEffortAssocList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultWorkEffortAssocShouldNotBeFound("sequenceNo.equals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsBySequenceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where sequenceNo not equals to DEFAULT_SEQUENCE_NO
        defaultWorkEffortAssocShouldNotBeFound("sequenceNo.notEquals=" + DEFAULT_SEQUENCE_NO);

        // Get all the workEffortAssocList where sequenceNo not equals to UPDATED_SEQUENCE_NO
        defaultWorkEffortAssocShouldBeFound("sequenceNo.notEquals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsBySequenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where sequenceNo in DEFAULT_SEQUENCE_NO or UPDATED_SEQUENCE_NO
        defaultWorkEffortAssocShouldBeFound("sequenceNo.in=" + DEFAULT_SEQUENCE_NO + "," + UPDATED_SEQUENCE_NO);

        // Get all the workEffortAssocList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultWorkEffortAssocShouldNotBeFound("sequenceNo.in=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsBySequenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where sequenceNo is not null
        defaultWorkEffortAssocShouldBeFound("sequenceNo.specified=true");

        // Get all the workEffortAssocList where sequenceNo is null
        defaultWorkEffortAssocShouldNotBeFound("sequenceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsBySequenceNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where sequenceNo is greater than or equal to DEFAULT_SEQUENCE_NO
        defaultWorkEffortAssocShouldBeFound("sequenceNo.greaterThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the workEffortAssocList where sequenceNo is greater than or equal to UPDATED_SEQUENCE_NO
        defaultWorkEffortAssocShouldNotBeFound("sequenceNo.greaterThanOrEqual=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsBySequenceNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where sequenceNo is less than or equal to DEFAULT_SEQUENCE_NO
        defaultWorkEffortAssocShouldBeFound("sequenceNo.lessThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the workEffortAssocList where sequenceNo is less than or equal to SMALLER_SEQUENCE_NO
        defaultWorkEffortAssocShouldNotBeFound("sequenceNo.lessThanOrEqual=" + SMALLER_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsBySequenceNoIsLessThanSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where sequenceNo is less than DEFAULT_SEQUENCE_NO
        defaultWorkEffortAssocShouldNotBeFound("sequenceNo.lessThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the workEffortAssocList where sequenceNo is less than UPDATED_SEQUENCE_NO
        defaultWorkEffortAssocShouldBeFound("sequenceNo.lessThan=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsBySequenceNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where sequenceNo is greater than DEFAULT_SEQUENCE_NO
        defaultWorkEffortAssocShouldNotBeFound("sequenceNo.greaterThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the workEffortAssocList where sequenceNo is greater than SMALLER_SEQUENCE_NO
        defaultWorkEffortAssocShouldBeFound("sequenceNo.greaterThan=" + SMALLER_SEQUENCE_NO);
    }


    @Test
    @Transactional
    public void getAllWorkEffortAssocsByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where fromDate equals to DEFAULT_FROM_DATE
        defaultWorkEffortAssocShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the workEffortAssocList where fromDate equals to UPDATED_FROM_DATE
        defaultWorkEffortAssocShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where fromDate not equals to DEFAULT_FROM_DATE
        defaultWorkEffortAssocShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the workEffortAssocList where fromDate not equals to UPDATED_FROM_DATE
        defaultWorkEffortAssocShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultWorkEffortAssocShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the workEffortAssocList where fromDate equals to UPDATED_FROM_DATE
        defaultWorkEffortAssocShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where fromDate is not null
        defaultWorkEffortAssocShouldBeFound("fromDate.specified=true");

        // Get all the workEffortAssocList where fromDate is null
        defaultWorkEffortAssocShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsByFromDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where fromDate is greater than or equal to DEFAULT_FROM_DATE
        defaultWorkEffortAssocShouldBeFound("fromDate.greaterThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the workEffortAssocList where fromDate is greater than or equal to UPDATED_FROM_DATE
        defaultWorkEffortAssocShouldNotBeFound("fromDate.greaterThanOrEqual=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsByFromDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where fromDate is less than or equal to DEFAULT_FROM_DATE
        defaultWorkEffortAssocShouldBeFound("fromDate.lessThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the workEffortAssocList where fromDate is less than or equal to SMALLER_FROM_DATE
        defaultWorkEffortAssocShouldNotBeFound("fromDate.lessThanOrEqual=" + SMALLER_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsByFromDateIsLessThanSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where fromDate is less than DEFAULT_FROM_DATE
        defaultWorkEffortAssocShouldNotBeFound("fromDate.lessThan=" + DEFAULT_FROM_DATE);

        // Get all the workEffortAssocList where fromDate is less than UPDATED_FROM_DATE
        defaultWorkEffortAssocShouldBeFound("fromDate.lessThan=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsByFromDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where fromDate is greater than DEFAULT_FROM_DATE
        defaultWorkEffortAssocShouldNotBeFound("fromDate.greaterThan=" + DEFAULT_FROM_DATE);

        // Get all the workEffortAssocList where fromDate is greater than SMALLER_FROM_DATE
        defaultWorkEffortAssocShouldBeFound("fromDate.greaterThan=" + SMALLER_FROM_DATE);
    }


    @Test
    @Transactional
    public void getAllWorkEffortAssocsByThruDateIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where thruDate equals to DEFAULT_THRU_DATE
        defaultWorkEffortAssocShouldBeFound("thruDate.equals=" + DEFAULT_THRU_DATE);

        // Get all the workEffortAssocList where thruDate equals to UPDATED_THRU_DATE
        defaultWorkEffortAssocShouldNotBeFound("thruDate.equals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsByThruDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where thruDate not equals to DEFAULT_THRU_DATE
        defaultWorkEffortAssocShouldNotBeFound("thruDate.notEquals=" + DEFAULT_THRU_DATE);

        // Get all the workEffortAssocList where thruDate not equals to UPDATED_THRU_DATE
        defaultWorkEffortAssocShouldBeFound("thruDate.notEquals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsByThruDateIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where thruDate in DEFAULT_THRU_DATE or UPDATED_THRU_DATE
        defaultWorkEffortAssocShouldBeFound("thruDate.in=" + DEFAULT_THRU_DATE + "," + UPDATED_THRU_DATE);

        // Get all the workEffortAssocList where thruDate equals to UPDATED_THRU_DATE
        defaultWorkEffortAssocShouldNotBeFound("thruDate.in=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsByThruDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where thruDate is not null
        defaultWorkEffortAssocShouldBeFound("thruDate.specified=true");

        // Get all the workEffortAssocList where thruDate is null
        defaultWorkEffortAssocShouldNotBeFound("thruDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsByThruDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where thruDate is greater than or equal to DEFAULT_THRU_DATE
        defaultWorkEffortAssocShouldBeFound("thruDate.greaterThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the workEffortAssocList where thruDate is greater than or equal to UPDATED_THRU_DATE
        defaultWorkEffortAssocShouldNotBeFound("thruDate.greaterThanOrEqual=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsByThruDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where thruDate is less than or equal to DEFAULT_THRU_DATE
        defaultWorkEffortAssocShouldBeFound("thruDate.lessThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the workEffortAssocList where thruDate is less than or equal to SMALLER_THRU_DATE
        defaultWorkEffortAssocShouldNotBeFound("thruDate.lessThanOrEqual=" + SMALLER_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsByThruDateIsLessThanSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where thruDate is less than DEFAULT_THRU_DATE
        defaultWorkEffortAssocShouldNotBeFound("thruDate.lessThan=" + DEFAULT_THRU_DATE);

        // Get all the workEffortAssocList where thruDate is less than UPDATED_THRU_DATE
        defaultWorkEffortAssocShouldBeFound("thruDate.lessThan=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortAssocsByThruDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);

        // Get all the workEffortAssocList where thruDate is greater than DEFAULT_THRU_DATE
        defaultWorkEffortAssocShouldNotBeFound("thruDate.greaterThan=" + DEFAULT_THRU_DATE);

        // Get all the workEffortAssocList where thruDate is greater than SMALLER_THRU_DATE
        defaultWorkEffortAssocShouldBeFound("thruDate.greaterThan=" + SMALLER_THRU_DATE);
    }


    @Test
    @Transactional
    public void getAllWorkEffortAssocsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);
        WorkEffortAssocType type = WorkEffortAssocTypeResourceIT.createEntity(em);
        em.persist(type);
        em.flush();
        workEffortAssoc.setType(type);
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);
        Long typeId = type.getId();

        // Get all the workEffortAssocList where type equals to typeId
        defaultWorkEffortAssocShouldBeFound("typeId.equals=" + typeId);

        // Get all the workEffortAssocList where type equals to typeId + 1
        defaultWorkEffortAssocShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkEffortAssocsByWeIdFromIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);
        WorkEffort weIdFrom = WorkEffortResourceIT.createEntity(em);
        em.persist(weIdFrom);
        em.flush();
        workEffortAssoc.setWeIdFrom(weIdFrom);
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);
        Long weIdFromId = weIdFrom.getId();

        // Get all the workEffortAssocList where weIdFrom equals to weIdFromId
        defaultWorkEffortAssocShouldBeFound("weIdFromId.equals=" + weIdFromId);

        // Get all the workEffortAssocList where weIdFrom equals to weIdFromId + 1
        defaultWorkEffortAssocShouldNotBeFound("weIdFromId.equals=" + (weIdFromId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkEffortAssocsByWeIdToIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);
        WorkEffort weIdTo = WorkEffortResourceIT.createEntity(em);
        em.persist(weIdTo);
        em.flush();
        workEffortAssoc.setWeIdTo(weIdTo);
        workEffortAssocRepository.saveAndFlush(workEffortAssoc);
        Long weIdToId = weIdTo.getId();

        // Get all the workEffortAssocList where weIdTo equals to weIdToId
        defaultWorkEffortAssocShouldBeFound("weIdToId.equals=" + weIdToId);

        // Get all the workEffortAssocList where weIdTo equals to weIdToId + 1
        defaultWorkEffortAssocShouldNotBeFound("weIdToId.equals=" + (weIdToId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkEffortAssocShouldBeFound(String filter) throws Exception {
        restWorkEffortAssocMockMvc.perform(get("/api/work-effort-assocs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEffortAssoc.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))));

        // Check, that the count call also returns 1
        restWorkEffortAssocMockMvc.perform(get("/api/work-effort-assocs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkEffortAssocShouldNotBeFound(String filter) throws Exception {
        restWorkEffortAssocMockMvc.perform(get("/api/work-effort-assocs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkEffortAssocMockMvc.perform(get("/api/work-effort-assocs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWorkEffortAssoc() throws Exception {
        // Get the workEffortAssoc
        restWorkEffortAssocMockMvc.perform(get("/api/work-effort-assocs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkEffortAssoc() throws Exception {
        // Initialize the database
        workEffortAssocService.save(workEffortAssoc);

        int databaseSizeBeforeUpdate = workEffortAssocRepository.findAll().size();

        // Update the workEffortAssoc
        WorkEffortAssoc updatedWorkEffortAssoc = workEffortAssocRepository.findById(workEffortAssoc.getId()).get();
        // Disconnect from session so that the updates on updatedWorkEffortAssoc are not directly saved in db
        em.detach(updatedWorkEffortAssoc);
        updatedWorkEffortAssoc
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);

        restWorkEffortAssocMockMvc.perform(put("/api/work-effort-assocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkEffortAssoc)))
            .andExpect(status().isOk());

        // Validate the WorkEffortAssoc in the database
        List<WorkEffortAssoc> workEffortAssocList = workEffortAssocRepository.findAll();
        assertThat(workEffortAssocList).hasSize(databaseSizeBeforeUpdate);
        WorkEffortAssoc testWorkEffortAssoc = workEffortAssocList.get(workEffortAssocList.size() - 1);
        assertThat(testWorkEffortAssoc.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
        assertThat(testWorkEffortAssoc.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testWorkEffortAssoc.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkEffortAssoc() throws Exception {
        int databaseSizeBeforeUpdate = workEffortAssocRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkEffortAssocMockMvc.perform(put("/api/work-effort-assocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortAssoc)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffortAssoc in the database
        List<WorkEffortAssoc> workEffortAssocList = workEffortAssocRepository.findAll();
        assertThat(workEffortAssocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkEffortAssoc() throws Exception {
        // Initialize the database
        workEffortAssocService.save(workEffortAssoc);

        int databaseSizeBeforeDelete = workEffortAssocRepository.findAll().size();

        // Delete the workEffortAssoc
        restWorkEffortAssocMockMvc.perform(delete("/api/work-effort-assocs/{id}", workEffortAssoc.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkEffortAssoc> workEffortAssocList = workEffortAssocRepository.findAll();
        assertThat(workEffortAssocList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
