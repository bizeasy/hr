package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.WorkEffort;
import com.hr.domain.Product;
import com.hr.domain.WorkEffortType;
import com.hr.domain.WorkEffortPurpose;
import com.hr.domain.Status;
import com.hr.domain.Facility;
import com.hr.domain.Uom;
import com.hr.domain.ProductCategory;
import com.hr.domain.ProductStore;
import com.hr.repository.WorkEffortRepository;
import com.hr.service.WorkEffortService;
import com.hr.service.dto.WorkEffortCriteria;
import com.hr.service.WorkEffortQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link WorkEffortResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkEffortResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Double DEFAULT_BATCH_SIZE = 1D;
    private static final Double UPDATED_BATCH_SIZE = 2D;
    private static final Double SMALLER_BATCH_SIZE = 1D - 1D;

    private static final Double DEFAULT_MIN_YIELD_RANGE = 1D;
    private static final Double UPDATED_MIN_YIELD_RANGE = 2D;
    private static final Double SMALLER_MIN_YIELD_RANGE = 1D - 1D;

    private static final Double DEFAULT_MAX_YIELD_RANGE = 1D;
    private static final Double UPDATED_MAX_YIELD_RANGE = 2D;
    private static final Double SMALLER_MAX_YIELD_RANGE = 1D - 1D;

    private static final Double DEFAULT_PERCENT_COMPLETE = 1D;
    private static final Double UPDATED_PERCENT_COMPLETE = 2D;
    private static final Double SMALLER_PERCENT_COMPLETE = 1D - 1D;

    private static final String DEFAULT_VALIDATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SHELF_LIFE = 1;
    private static final Integer UPDATED_SHELF_LIFE = 2;
    private static final Integer SMALLER_SHELF_LIFE = 1 - 1;

    private static final Double DEFAULT_OUTPUT_QTY = 1D;
    private static final Double UPDATED_OUTPUT_QTY = 2D;
    private static final Double SMALLER_OUTPUT_QTY = 1D - 1D;

    @Autowired
    private WorkEffortRepository workEffortRepository;

    @Autowired
    private WorkEffortService workEffortService;

    @Autowired
    private WorkEffortQueryService workEffortQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkEffortMockMvc;

    private WorkEffort workEffort;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffort createEntity(EntityManager em) {
        WorkEffort workEffort = new WorkEffort()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .code(DEFAULT_CODE)
            .batchSize(DEFAULT_BATCH_SIZE)
            .minYieldRange(DEFAULT_MIN_YIELD_RANGE)
            .maxYieldRange(DEFAULT_MAX_YIELD_RANGE)
            .percentComplete(DEFAULT_PERCENT_COMPLETE)
            .validationType(DEFAULT_VALIDATION_TYPE)
            .shelfLife(DEFAULT_SHELF_LIFE)
            .outputQty(DEFAULT_OUTPUT_QTY);
        return workEffort;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffort createUpdatedEntity(EntityManager em) {
        WorkEffort workEffort = new WorkEffort()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE)
            .batchSize(UPDATED_BATCH_SIZE)
            .minYieldRange(UPDATED_MIN_YIELD_RANGE)
            .maxYieldRange(UPDATED_MAX_YIELD_RANGE)
            .percentComplete(UPDATED_PERCENT_COMPLETE)
            .validationType(UPDATED_VALIDATION_TYPE)
            .shelfLife(UPDATED_SHELF_LIFE)
            .outputQty(UPDATED_OUTPUT_QTY);
        return workEffort;
    }

    @BeforeEach
    public void initTest() {
        workEffort = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkEffort() throws Exception {
        int databaseSizeBeforeCreate = workEffortRepository.findAll().size();
        // Create the WorkEffort
        restWorkEffortMockMvc.perform(post("/api/work-efforts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffort)))
            .andExpect(status().isCreated());

        // Validate the WorkEffort in the database
        List<WorkEffort> workEffortList = workEffortRepository.findAll();
        assertThat(workEffortList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEffort testWorkEffort = workEffortList.get(workEffortList.size() - 1);
        assertThat(testWorkEffort.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkEffort.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testWorkEffort.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testWorkEffort.getBatchSize()).isEqualTo(DEFAULT_BATCH_SIZE);
        assertThat(testWorkEffort.getMinYieldRange()).isEqualTo(DEFAULT_MIN_YIELD_RANGE);
        assertThat(testWorkEffort.getMaxYieldRange()).isEqualTo(DEFAULT_MAX_YIELD_RANGE);
        assertThat(testWorkEffort.getPercentComplete()).isEqualTo(DEFAULT_PERCENT_COMPLETE);
        assertThat(testWorkEffort.getValidationType()).isEqualTo(DEFAULT_VALIDATION_TYPE);
        assertThat(testWorkEffort.getShelfLife()).isEqualTo(DEFAULT_SHELF_LIFE);
        assertThat(testWorkEffort.getOutputQty()).isEqualTo(DEFAULT_OUTPUT_QTY);
    }

    @Test
    @Transactional
    public void createWorkEffortWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workEffortRepository.findAll().size();

        // Create the WorkEffort with an existing ID
        workEffort.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkEffortMockMvc.perform(post("/api/work-efforts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffort)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffort in the database
        List<WorkEffort> workEffortList = workEffortRepository.findAll();
        assertThat(workEffortList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkEfforts() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList
        restWorkEffortMockMvc.perform(get("/api/work-efforts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEffort.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].batchSize").value(hasItem(DEFAULT_BATCH_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].minYieldRange").value(hasItem(DEFAULT_MIN_YIELD_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxYieldRange").value(hasItem(DEFAULT_MAX_YIELD_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].percentComplete").value(hasItem(DEFAULT_PERCENT_COMPLETE.doubleValue())))
            .andExpect(jsonPath("$.[*].validationType").value(hasItem(DEFAULT_VALIDATION_TYPE)))
            .andExpect(jsonPath("$.[*].shelfLife").value(hasItem(DEFAULT_SHELF_LIFE)))
            .andExpect(jsonPath("$.[*].outputQty").value(hasItem(DEFAULT_OUTPUT_QTY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getWorkEffort() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get the workEffort
        restWorkEffortMockMvc.perform(get("/api/work-efforts/{id}", workEffort.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workEffort.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.batchSize").value(DEFAULT_BATCH_SIZE.doubleValue()))
            .andExpect(jsonPath("$.minYieldRange").value(DEFAULT_MIN_YIELD_RANGE.doubleValue()))
            .andExpect(jsonPath("$.maxYieldRange").value(DEFAULT_MAX_YIELD_RANGE.doubleValue()))
            .andExpect(jsonPath("$.percentComplete").value(DEFAULT_PERCENT_COMPLETE.doubleValue()))
            .andExpect(jsonPath("$.validationType").value(DEFAULT_VALIDATION_TYPE))
            .andExpect(jsonPath("$.shelfLife").value(DEFAULT_SHELF_LIFE))
            .andExpect(jsonPath("$.outputQty").value(DEFAULT_OUTPUT_QTY.doubleValue()));
    }


    @Test
    @Transactional
    public void getWorkEffortsByIdFiltering() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        Long id = workEffort.getId();

        defaultWorkEffortShouldBeFound("id.equals=" + id);
        defaultWorkEffortShouldNotBeFound("id.notEquals=" + id);

        defaultWorkEffortShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkEffortShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkEffortShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkEffortShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where name equals to DEFAULT_NAME
        defaultWorkEffortShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the workEffortList where name equals to UPDATED_NAME
        defaultWorkEffortShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where name not equals to DEFAULT_NAME
        defaultWorkEffortShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the workEffortList where name not equals to UPDATED_NAME
        defaultWorkEffortShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where name in DEFAULT_NAME or UPDATED_NAME
        defaultWorkEffortShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the workEffortList where name equals to UPDATED_NAME
        defaultWorkEffortShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where name is not null
        defaultWorkEffortShouldBeFound("name.specified=true");

        // Get all the workEffortList where name is null
        defaultWorkEffortShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllWorkEffortsByNameContainsSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where name contains DEFAULT_NAME
        defaultWorkEffortShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the workEffortList where name contains UPDATED_NAME
        defaultWorkEffortShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where name does not contain DEFAULT_NAME
        defaultWorkEffortShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the workEffortList where name does not contain UPDATED_NAME
        defaultWorkEffortShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where description equals to DEFAULT_DESCRIPTION
        defaultWorkEffortShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the workEffortList where description equals to UPDATED_DESCRIPTION
        defaultWorkEffortShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where description not equals to DEFAULT_DESCRIPTION
        defaultWorkEffortShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the workEffortList where description not equals to UPDATED_DESCRIPTION
        defaultWorkEffortShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultWorkEffortShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the workEffortList where description equals to UPDATED_DESCRIPTION
        defaultWorkEffortShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where description is not null
        defaultWorkEffortShouldBeFound("description.specified=true");

        // Get all the workEffortList where description is null
        defaultWorkEffortShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllWorkEffortsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where description contains DEFAULT_DESCRIPTION
        defaultWorkEffortShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the workEffortList where description contains UPDATED_DESCRIPTION
        defaultWorkEffortShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where description does not contain DEFAULT_DESCRIPTION
        defaultWorkEffortShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the workEffortList where description does not contain UPDATED_DESCRIPTION
        defaultWorkEffortShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where code equals to DEFAULT_CODE
        defaultWorkEffortShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the workEffortList where code equals to UPDATED_CODE
        defaultWorkEffortShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where code not equals to DEFAULT_CODE
        defaultWorkEffortShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the workEffortList where code not equals to UPDATED_CODE
        defaultWorkEffortShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where code in DEFAULT_CODE or UPDATED_CODE
        defaultWorkEffortShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the workEffortList where code equals to UPDATED_CODE
        defaultWorkEffortShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where code is not null
        defaultWorkEffortShouldBeFound("code.specified=true");

        // Get all the workEffortList where code is null
        defaultWorkEffortShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllWorkEffortsByCodeContainsSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where code contains DEFAULT_CODE
        defaultWorkEffortShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the workEffortList where code contains UPDATED_CODE
        defaultWorkEffortShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where code does not contain DEFAULT_CODE
        defaultWorkEffortShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the workEffortList where code does not contain UPDATED_CODE
        defaultWorkEffortShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByBatchSizeIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where batchSize equals to DEFAULT_BATCH_SIZE
        defaultWorkEffortShouldBeFound("batchSize.equals=" + DEFAULT_BATCH_SIZE);

        // Get all the workEffortList where batchSize equals to UPDATED_BATCH_SIZE
        defaultWorkEffortShouldNotBeFound("batchSize.equals=" + UPDATED_BATCH_SIZE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByBatchSizeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where batchSize not equals to DEFAULT_BATCH_SIZE
        defaultWorkEffortShouldNotBeFound("batchSize.notEquals=" + DEFAULT_BATCH_SIZE);

        // Get all the workEffortList where batchSize not equals to UPDATED_BATCH_SIZE
        defaultWorkEffortShouldBeFound("batchSize.notEquals=" + UPDATED_BATCH_SIZE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByBatchSizeIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where batchSize in DEFAULT_BATCH_SIZE or UPDATED_BATCH_SIZE
        defaultWorkEffortShouldBeFound("batchSize.in=" + DEFAULT_BATCH_SIZE + "," + UPDATED_BATCH_SIZE);

        // Get all the workEffortList where batchSize equals to UPDATED_BATCH_SIZE
        defaultWorkEffortShouldNotBeFound("batchSize.in=" + UPDATED_BATCH_SIZE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByBatchSizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where batchSize is not null
        defaultWorkEffortShouldBeFound("batchSize.specified=true");

        // Get all the workEffortList where batchSize is null
        defaultWorkEffortShouldNotBeFound("batchSize.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByBatchSizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where batchSize is greater than or equal to DEFAULT_BATCH_SIZE
        defaultWorkEffortShouldBeFound("batchSize.greaterThanOrEqual=" + DEFAULT_BATCH_SIZE);

        // Get all the workEffortList where batchSize is greater than or equal to UPDATED_BATCH_SIZE
        defaultWorkEffortShouldNotBeFound("batchSize.greaterThanOrEqual=" + UPDATED_BATCH_SIZE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByBatchSizeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where batchSize is less than or equal to DEFAULT_BATCH_SIZE
        defaultWorkEffortShouldBeFound("batchSize.lessThanOrEqual=" + DEFAULT_BATCH_SIZE);

        // Get all the workEffortList where batchSize is less than or equal to SMALLER_BATCH_SIZE
        defaultWorkEffortShouldNotBeFound("batchSize.lessThanOrEqual=" + SMALLER_BATCH_SIZE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByBatchSizeIsLessThanSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where batchSize is less than DEFAULT_BATCH_SIZE
        defaultWorkEffortShouldNotBeFound("batchSize.lessThan=" + DEFAULT_BATCH_SIZE);

        // Get all the workEffortList where batchSize is less than UPDATED_BATCH_SIZE
        defaultWorkEffortShouldBeFound("batchSize.lessThan=" + UPDATED_BATCH_SIZE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByBatchSizeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where batchSize is greater than DEFAULT_BATCH_SIZE
        defaultWorkEffortShouldNotBeFound("batchSize.greaterThan=" + DEFAULT_BATCH_SIZE);

        // Get all the workEffortList where batchSize is greater than SMALLER_BATCH_SIZE
        defaultWorkEffortShouldBeFound("batchSize.greaterThan=" + SMALLER_BATCH_SIZE);
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByMinYieldRangeIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where minYieldRange equals to DEFAULT_MIN_YIELD_RANGE
        defaultWorkEffortShouldBeFound("minYieldRange.equals=" + DEFAULT_MIN_YIELD_RANGE);

        // Get all the workEffortList where minYieldRange equals to UPDATED_MIN_YIELD_RANGE
        defaultWorkEffortShouldNotBeFound("minYieldRange.equals=" + UPDATED_MIN_YIELD_RANGE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByMinYieldRangeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where minYieldRange not equals to DEFAULT_MIN_YIELD_RANGE
        defaultWorkEffortShouldNotBeFound("minYieldRange.notEquals=" + DEFAULT_MIN_YIELD_RANGE);

        // Get all the workEffortList where minYieldRange not equals to UPDATED_MIN_YIELD_RANGE
        defaultWorkEffortShouldBeFound("minYieldRange.notEquals=" + UPDATED_MIN_YIELD_RANGE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByMinYieldRangeIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where minYieldRange in DEFAULT_MIN_YIELD_RANGE or UPDATED_MIN_YIELD_RANGE
        defaultWorkEffortShouldBeFound("minYieldRange.in=" + DEFAULT_MIN_YIELD_RANGE + "," + UPDATED_MIN_YIELD_RANGE);

        // Get all the workEffortList where minYieldRange equals to UPDATED_MIN_YIELD_RANGE
        defaultWorkEffortShouldNotBeFound("minYieldRange.in=" + UPDATED_MIN_YIELD_RANGE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByMinYieldRangeIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where minYieldRange is not null
        defaultWorkEffortShouldBeFound("minYieldRange.specified=true");

        // Get all the workEffortList where minYieldRange is null
        defaultWorkEffortShouldNotBeFound("minYieldRange.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByMinYieldRangeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where minYieldRange is greater than or equal to DEFAULT_MIN_YIELD_RANGE
        defaultWorkEffortShouldBeFound("minYieldRange.greaterThanOrEqual=" + DEFAULT_MIN_YIELD_RANGE);

        // Get all the workEffortList where minYieldRange is greater than or equal to UPDATED_MIN_YIELD_RANGE
        defaultWorkEffortShouldNotBeFound("minYieldRange.greaterThanOrEqual=" + UPDATED_MIN_YIELD_RANGE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByMinYieldRangeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where minYieldRange is less than or equal to DEFAULT_MIN_YIELD_RANGE
        defaultWorkEffortShouldBeFound("minYieldRange.lessThanOrEqual=" + DEFAULT_MIN_YIELD_RANGE);

        // Get all the workEffortList where minYieldRange is less than or equal to SMALLER_MIN_YIELD_RANGE
        defaultWorkEffortShouldNotBeFound("minYieldRange.lessThanOrEqual=" + SMALLER_MIN_YIELD_RANGE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByMinYieldRangeIsLessThanSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where minYieldRange is less than DEFAULT_MIN_YIELD_RANGE
        defaultWorkEffortShouldNotBeFound("minYieldRange.lessThan=" + DEFAULT_MIN_YIELD_RANGE);

        // Get all the workEffortList where minYieldRange is less than UPDATED_MIN_YIELD_RANGE
        defaultWorkEffortShouldBeFound("minYieldRange.lessThan=" + UPDATED_MIN_YIELD_RANGE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByMinYieldRangeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where minYieldRange is greater than DEFAULT_MIN_YIELD_RANGE
        defaultWorkEffortShouldNotBeFound("minYieldRange.greaterThan=" + DEFAULT_MIN_YIELD_RANGE);

        // Get all the workEffortList where minYieldRange is greater than SMALLER_MIN_YIELD_RANGE
        defaultWorkEffortShouldBeFound("minYieldRange.greaterThan=" + SMALLER_MIN_YIELD_RANGE);
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByMaxYieldRangeIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where maxYieldRange equals to DEFAULT_MAX_YIELD_RANGE
        defaultWorkEffortShouldBeFound("maxYieldRange.equals=" + DEFAULT_MAX_YIELD_RANGE);

        // Get all the workEffortList where maxYieldRange equals to UPDATED_MAX_YIELD_RANGE
        defaultWorkEffortShouldNotBeFound("maxYieldRange.equals=" + UPDATED_MAX_YIELD_RANGE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByMaxYieldRangeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where maxYieldRange not equals to DEFAULT_MAX_YIELD_RANGE
        defaultWorkEffortShouldNotBeFound("maxYieldRange.notEquals=" + DEFAULT_MAX_YIELD_RANGE);

        // Get all the workEffortList where maxYieldRange not equals to UPDATED_MAX_YIELD_RANGE
        defaultWorkEffortShouldBeFound("maxYieldRange.notEquals=" + UPDATED_MAX_YIELD_RANGE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByMaxYieldRangeIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where maxYieldRange in DEFAULT_MAX_YIELD_RANGE or UPDATED_MAX_YIELD_RANGE
        defaultWorkEffortShouldBeFound("maxYieldRange.in=" + DEFAULT_MAX_YIELD_RANGE + "," + UPDATED_MAX_YIELD_RANGE);

        // Get all the workEffortList where maxYieldRange equals to UPDATED_MAX_YIELD_RANGE
        defaultWorkEffortShouldNotBeFound("maxYieldRange.in=" + UPDATED_MAX_YIELD_RANGE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByMaxYieldRangeIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where maxYieldRange is not null
        defaultWorkEffortShouldBeFound("maxYieldRange.specified=true");

        // Get all the workEffortList where maxYieldRange is null
        defaultWorkEffortShouldNotBeFound("maxYieldRange.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByMaxYieldRangeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where maxYieldRange is greater than or equal to DEFAULT_MAX_YIELD_RANGE
        defaultWorkEffortShouldBeFound("maxYieldRange.greaterThanOrEqual=" + DEFAULT_MAX_YIELD_RANGE);

        // Get all the workEffortList where maxYieldRange is greater than or equal to UPDATED_MAX_YIELD_RANGE
        defaultWorkEffortShouldNotBeFound("maxYieldRange.greaterThanOrEqual=" + UPDATED_MAX_YIELD_RANGE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByMaxYieldRangeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where maxYieldRange is less than or equal to DEFAULT_MAX_YIELD_RANGE
        defaultWorkEffortShouldBeFound("maxYieldRange.lessThanOrEqual=" + DEFAULT_MAX_YIELD_RANGE);

        // Get all the workEffortList where maxYieldRange is less than or equal to SMALLER_MAX_YIELD_RANGE
        defaultWorkEffortShouldNotBeFound("maxYieldRange.lessThanOrEqual=" + SMALLER_MAX_YIELD_RANGE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByMaxYieldRangeIsLessThanSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where maxYieldRange is less than DEFAULT_MAX_YIELD_RANGE
        defaultWorkEffortShouldNotBeFound("maxYieldRange.lessThan=" + DEFAULT_MAX_YIELD_RANGE);

        // Get all the workEffortList where maxYieldRange is less than UPDATED_MAX_YIELD_RANGE
        defaultWorkEffortShouldBeFound("maxYieldRange.lessThan=" + UPDATED_MAX_YIELD_RANGE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByMaxYieldRangeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where maxYieldRange is greater than DEFAULT_MAX_YIELD_RANGE
        defaultWorkEffortShouldNotBeFound("maxYieldRange.greaterThan=" + DEFAULT_MAX_YIELD_RANGE);

        // Get all the workEffortList where maxYieldRange is greater than SMALLER_MAX_YIELD_RANGE
        defaultWorkEffortShouldBeFound("maxYieldRange.greaterThan=" + SMALLER_MAX_YIELD_RANGE);
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByPercentCompleteIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where percentComplete equals to DEFAULT_PERCENT_COMPLETE
        defaultWorkEffortShouldBeFound("percentComplete.equals=" + DEFAULT_PERCENT_COMPLETE);

        // Get all the workEffortList where percentComplete equals to UPDATED_PERCENT_COMPLETE
        defaultWorkEffortShouldNotBeFound("percentComplete.equals=" + UPDATED_PERCENT_COMPLETE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByPercentCompleteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where percentComplete not equals to DEFAULT_PERCENT_COMPLETE
        defaultWorkEffortShouldNotBeFound("percentComplete.notEquals=" + DEFAULT_PERCENT_COMPLETE);

        // Get all the workEffortList where percentComplete not equals to UPDATED_PERCENT_COMPLETE
        defaultWorkEffortShouldBeFound("percentComplete.notEquals=" + UPDATED_PERCENT_COMPLETE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByPercentCompleteIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where percentComplete in DEFAULT_PERCENT_COMPLETE or UPDATED_PERCENT_COMPLETE
        defaultWorkEffortShouldBeFound("percentComplete.in=" + DEFAULT_PERCENT_COMPLETE + "," + UPDATED_PERCENT_COMPLETE);

        // Get all the workEffortList where percentComplete equals to UPDATED_PERCENT_COMPLETE
        defaultWorkEffortShouldNotBeFound("percentComplete.in=" + UPDATED_PERCENT_COMPLETE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByPercentCompleteIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where percentComplete is not null
        defaultWorkEffortShouldBeFound("percentComplete.specified=true");

        // Get all the workEffortList where percentComplete is null
        defaultWorkEffortShouldNotBeFound("percentComplete.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByPercentCompleteIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where percentComplete is greater than or equal to DEFAULT_PERCENT_COMPLETE
        defaultWorkEffortShouldBeFound("percentComplete.greaterThanOrEqual=" + DEFAULT_PERCENT_COMPLETE);

        // Get all the workEffortList where percentComplete is greater than or equal to UPDATED_PERCENT_COMPLETE
        defaultWorkEffortShouldNotBeFound("percentComplete.greaterThanOrEqual=" + UPDATED_PERCENT_COMPLETE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByPercentCompleteIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where percentComplete is less than or equal to DEFAULT_PERCENT_COMPLETE
        defaultWorkEffortShouldBeFound("percentComplete.lessThanOrEqual=" + DEFAULT_PERCENT_COMPLETE);

        // Get all the workEffortList where percentComplete is less than or equal to SMALLER_PERCENT_COMPLETE
        defaultWorkEffortShouldNotBeFound("percentComplete.lessThanOrEqual=" + SMALLER_PERCENT_COMPLETE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByPercentCompleteIsLessThanSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where percentComplete is less than DEFAULT_PERCENT_COMPLETE
        defaultWorkEffortShouldNotBeFound("percentComplete.lessThan=" + DEFAULT_PERCENT_COMPLETE);

        // Get all the workEffortList where percentComplete is less than UPDATED_PERCENT_COMPLETE
        defaultWorkEffortShouldBeFound("percentComplete.lessThan=" + UPDATED_PERCENT_COMPLETE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByPercentCompleteIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where percentComplete is greater than DEFAULT_PERCENT_COMPLETE
        defaultWorkEffortShouldNotBeFound("percentComplete.greaterThan=" + DEFAULT_PERCENT_COMPLETE);

        // Get all the workEffortList where percentComplete is greater than SMALLER_PERCENT_COMPLETE
        defaultWorkEffortShouldBeFound("percentComplete.greaterThan=" + SMALLER_PERCENT_COMPLETE);
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByValidationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where validationType equals to DEFAULT_VALIDATION_TYPE
        defaultWorkEffortShouldBeFound("validationType.equals=" + DEFAULT_VALIDATION_TYPE);

        // Get all the workEffortList where validationType equals to UPDATED_VALIDATION_TYPE
        defaultWorkEffortShouldNotBeFound("validationType.equals=" + UPDATED_VALIDATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByValidationTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where validationType not equals to DEFAULT_VALIDATION_TYPE
        defaultWorkEffortShouldNotBeFound("validationType.notEquals=" + DEFAULT_VALIDATION_TYPE);

        // Get all the workEffortList where validationType not equals to UPDATED_VALIDATION_TYPE
        defaultWorkEffortShouldBeFound("validationType.notEquals=" + UPDATED_VALIDATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByValidationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where validationType in DEFAULT_VALIDATION_TYPE or UPDATED_VALIDATION_TYPE
        defaultWorkEffortShouldBeFound("validationType.in=" + DEFAULT_VALIDATION_TYPE + "," + UPDATED_VALIDATION_TYPE);

        // Get all the workEffortList where validationType equals to UPDATED_VALIDATION_TYPE
        defaultWorkEffortShouldNotBeFound("validationType.in=" + UPDATED_VALIDATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByValidationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where validationType is not null
        defaultWorkEffortShouldBeFound("validationType.specified=true");

        // Get all the workEffortList where validationType is null
        defaultWorkEffortShouldNotBeFound("validationType.specified=false");
    }
                @Test
    @Transactional
    public void getAllWorkEffortsByValidationTypeContainsSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where validationType contains DEFAULT_VALIDATION_TYPE
        defaultWorkEffortShouldBeFound("validationType.contains=" + DEFAULT_VALIDATION_TYPE);

        // Get all the workEffortList where validationType contains UPDATED_VALIDATION_TYPE
        defaultWorkEffortShouldNotBeFound("validationType.contains=" + UPDATED_VALIDATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByValidationTypeNotContainsSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where validationType does not contain DEFAULT_VALIDATION_TYPE
        defaultWorkEffortShouldNotBeFound("validationType.doesNotContain=" + DEFAULT_VALIDATION_TYPE);

        // Get all the workEffortList where validationType does not contain UPDATED_VALIDATION_TYPE
        defaultWorkEffortShouldBeFound("validationType.doesNotContain=" + UPDATED_VALIDATION_TYPE);
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByShelfLifeIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where shelfLife equals to DEFAULT_SHELF_LIFE
        defaultWorkEffortShouldBeFound("shelfLife.equals=" + DEFAULT_SHELF_LIFE);

        // Get all the workEffortList where shelfLife equals to UPDATED_SHELF_LIFE
        defaultWorkEffortShouldNotBeFound("shelfLife.equals=" + UPDATED_SHELF_LIFE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByShelfLifeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where shelfLife not equals to DEFAULT_SHELF_LIFE
        defaultWorkEffortShouldNotBeFound("shelfLife.notEquals=" + DEFAULT_SHELF_LIFE);

        // Get all the workEffortList where shelfLife not equals to UPDATED_SHELF_LIFE
        defaultWorkEffortShouldBeFound("shelfLife.notEquals=" + UPDATED_SHELF_LIFE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByShelfLifeIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where shelfLife in DEFAULT_SHELF_LIFE or UPDATED_SHELF_LIFE
        defaultWorkEffortShouldBeFound("shelfLife.in=" + DEFAULT_SHELF_LIFE + "," + UPDATED_SHELF_LIFE);

        // Get all the workEffortList where shelfLife equals to UPDATED_SHELF_LIFE
        defaultWorkEffortShouldNotBeFound("shelfLife.in=" + UPDATED_SHELF_LIFE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByShelfLifeIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where shelfLife is not null
        defaultWorkEffortShouldBeFound("shelfLife.specified=true");

        // Get all the workEffortList where shelfLife is null
        defaultWorkEffortShouldNotBeFound("shelfLife.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByShelfLifeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where shelfLife is greater than or equal to DEFAULT_SHELF_LIFE
        defaultWorkEffortShouldBeFound("shelfLife.greaterThanOrEqual=" + DEFAULT_SHELF_LIFE);

        // Get all the workEffortList where shelfLife is greater than or equal to UPDATED_SHELF_LIFE
        defaultWorkEffortShouldNotBeFound("shelfLife.greaterThanOrEqual=" + UPDATED_SHELF_LIFE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByShelfLifeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where shelfLife is less than or equal to DEFAULT_SHELF_LIFE
        defaultWorkEffortShouldBeFound("shelfLife.lessThanOrEqual=" + DEFAULT_SHELF_LIFE);

        // Get all the workEffortList where shelfLife is less than or equal to SMALLER_SHELF_LIFE
        defaultWorkEffortShouldNotBeFound("shelfLife.lessThanOrEqual=" + SMALLER_SHELF_LIFE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByShelfLifeIsLessThanSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where shelfLife is less than DEFAULT_SHELF_LIFE
        defaultWorkEffortShouldNotBeFound("shelfLife.lessThan=" + DEFAULT_SHELF_LIFE);

        // Get all the workEffortList where shelfLife is less than UPDATED_SHELF_LIFE
        defaultWorkEffortShouldBeFound("shelfLife.lessThan=" + UPDATED_SHELF_LIFE);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByShelfLifeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where shelfLife is greater than DEFAULT_SHELF_LIFE
        defaultWorkEffortShouldNotBeFound("shelfLife.greaterThan=" + DEFAULT_SHELF_LIFE);

        // Get all the workEffortList where shelfLife is greater than SMALLER_SHELF_LIFE
        defaultWorkEffortShouldBeFound("shelfLife.greaterThan=" + SMALLER_SHELF_LIFE);
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByOutputQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where outputQty equals to DEFAULT_OUTPUT_QTY
        defaultWorkEffortShouldBeFound("outputQty.equals=" + DEFAULT_OUTPUT_QTY);

        // Get all the workEffortList where outputQty equals to UPDATED_OUTPUT_QTY
        defaultWorkEffortShouldNotBeFound("outputQty.equals=" + UPDATED_OUTPUT_QTY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByOutputQtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where outputQty not equals to DEFAULT_OUTPUT_QTY
        defaultWorkEffortShouldNotBeFound("outputQty.notEquals=" + DEFAULT_OUTPUT_QTY);

        // Get all the workEffortList where outputQty not equals to UPDATED_OUTPUT_QTY
        defaultWorkEffortShouldBeFound("outputQty.notEquals=" + UPDATED_OUTPUT_QTY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByOutputQtyIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where outputQty in DEFAULT_OUTPUT_QTY or UPDATED_OUTPUT_QTY
        defaultWorkEffortShouldBeFound("outputQty.in=" + DEFAULT_OUTPUT_QTY + "," + UPDATED_OUTPUT_QTY);

        // Get all the workEffortList where outputQty equals to UPDATED_OUTPUT_QTY
        defaultWorkEffortShouldNotBeFound("outputQty.in=" + UPDATED_OUTPUT_QTY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByOutputQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where outputQty is not null
        defaultWorkEffortShouldBeFound("outputQty.specified=true");

        // Get all the workEffortList where outputQty is null
        defaultWorkEffortShouldNotBeFound("outputQty.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByOutputQtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where outputQty is greater than or equal to DEFAULT_OUTPUT_QTY
        defaultWorkEffortShouldBeFound("outputQty.greaterThanOrEqual=" + DEFAULT_OUTPUT_QTY);

        // Get all the workEffortList where outputQty is greater than or equal to UPDATED_OUTPUT_QTY
        defaultWorkEffortShouldNotBeFound("outputQty.greaterThanOrEqual=" + UPDATED_OUTPUT_QTY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByOutputQtyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where outputQty is less than or equal to DEFAULT_OUTPUT_QTY
        defaultWorkEffortShouldBeFound("outputQty.lessThanOrEqual=" + DEFAULT_OUTPUT_QTY);

        // Get all the workEffortList where outputQty is less than or equal to SMALLER_OUTPUT_QTY
        defaultWorkEffortShouldNotBeFound("outputQty.lessThanOrEqual=" + SMALLER_OUTPUT_QTY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByOutputQtyIsLessThanSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where outputQty is less than DEFAULT_OUTPUT_QTY
        defaultWorkEffortShouldNotBeFound("outputQty.lessThan=" + DEFAULT_OUTPUT_QTY);

        // Get all the workEffortList where outputQty is less than UPDATED_OUTPUT_QTY
        defaultWorkEffortShouldBeFound("outputQty.lessThan=" + UPDATED_OUTPUT_QTY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortsByOutputQtyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);

        // Get all the workEffortList where outputQty is greater than DEFAULT_OUTPUT_QTY
        defaultWorkEffortShouldNotBeFound("outputQty.greaterThan=" + DEFAULT_OUTPUT_QTY);

        // Get all the workEffortList where outputQty is greater than SMALLER_OUTPUT_QTY
        defaultWorkEffortShouldBeFound("outputQty.greaterThan=" + SMALLER_OUTPUT_QTY);
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        workEffort.setProduct(product);
        workEffortRepository.saveAndFlush(workEffort);
        Long productId = product.getId();

        // Get all the workEffortList where product equals to productId
        defaultWorkEffortShouldBeFound("productId.equals=" + productId);

        // Get all the workEffortList where product equals to productId + 1
        defaultWorkEffortShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByKsmIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);
        Product ksm = ProductResourceIT.createEntity(em);
        em.persist(ksm);
        em.flush();
        workEffort.setKsm(ksm);
        workEffortRepository.saveAndFlush(workEffort);
        Long ksmId = ksm.getId();

        // Get all the workEffortList where ksm equals to ksmId
        defaultWorkEffortShouldBeFound("ksmId.equals=" + ksmId);

        // Get all the workEffortList where ksm equals to ksmId + 1
        defaultWorkEffortShouldNotBeFound("ksmId.equals=" + (ksmId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);
        WorkEffortType type = WorkEffortTypeResourceIT.createEntity(em);
        em.persist(type);
        em.flush();
        workEffort.setType(type);
        workEffortRepository.saveAndFlush(workEffort);
        Long typeId = type.getId();

        // Get all the workEffortList where type equals to typeId
        defaultWorkEffortShouldBeFound("typeId.equals=" + typeId);

        // Get all the workEffortList where type equals to typeId + 1
        defaultWorkEffortShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByPurposeIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);
        WorkEffortPurpose purpose = WorkEffortPurposeResourceIT.createEntity(em);
        em.persist(purpose);
        em.flush();
        workEffort.setPurpose(purpose);
        workEffortRepository.saveAndFlush(workEffort);
        Long purposeId = purpose.getId();

        // Get all the workEffortList where purpose equals to purposeId
        defaultWorkEffortShouldBeFound("purposeId.equals=" + purposeId);

        // Get all the workEffortList where purpose equals to purposeId + 1
        defaultWorkEffortShouldNotBeFound("purposeId.equals=" + (purposeId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        workEffort.setStatus(status);
        workEffortRepository.saveAndFlush(workEffort);
        Long statusId = status.getId();

        // Get all the workEffortList where status equals to statusId
        defaultWorkEffortShouldBeFound("statusId.equals=" + statusId);

        // Get all the workEffortList where status equals to statusId + 1
        defaultWorkEffortShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByFacilityIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);
        Facility facility = FacilityResourceIT.createEntity(em);
        em.persist(facility);
        em.flush();
        workEffort.setFacility(facility);
        workEffortRepository.saveAndFlush(workEffort);
        Long facilityId = facility.getId();

        // Get all the workEffortList where facility equals to facilityId
        defaultWorkEffortShouldBeFound("facilityId.equals=" + facilityId);

        // Get all the workEffortList where facility equals to facilityId + 1
        defaultWorkEffortShouldNotBeFound("facilityId.equals=" + (facilityId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByShelfListUomIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);
        Uom shelfListUom = UomResourceIT.createEntity(em);
        em.persist(shelfListUom);
        em.flush();
        workEffort.setShelfListUom(shelfListUom);
        workEffortRepository.saveAndFlush(workEffort);
        Long shelfListUomId = shelfListUom.getId();

        // Get all the workEffortList where shelfListUom equals to shelfListUomId
        defaultWorkEffortShouldBeFound("shelfListUomId.equals=" + shelfListUomId);

        // Get all the workEffortList where shelfListUom equals to shelfListUomId + 1
        defaultWorkEffortShouldNotBeFound("shelfListUomId.equals=" + (shelfListUomId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);
        ProductCategory productCategory = ProductCategoryResourceIT.createEntity(em);
        em.persist(productCategory);
        em.flush();
        workEffort.setProductCategory(productCategory);
        workEffortRepository.saveAndFlush(workEffort);
        Long productCategoryId = productCategory.getId();

        // Get all the workEffortList where productCategory equals to productCategoryId
        defaultWorkEffortShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the workEffortList where productCategory equals to productCategoryId + 1
        defaultWorkEffortShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkEffortsByProductStoreIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortRepository.saveAndFlush(workEffort);
        ProductStore productStore = ProductStoreResourceIT.createEntity(em);
        em.persist(productStore);
        em.flush();
        workEffort.setProductStore(productStore);
        workEffortRepository.saveAndFlush(workEffort);
        Long productStoreId = productStore.getId();

        // Get all the workEffortList where productStore equals to productStoreId
        defaultWorkEffortShouldBeFound("productStoreId.equals=" + productStoreId);

        // Get all the workEffortList where productStore equals to productStoreId + 1
        defaultWorkEffortShouldNotBeFound("productStoreId.equals=" + (productStoreId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkEffortShouldBeFound(String filter) throws Exception {
        restWorkEffortMockMvc.perform(get("/api/work-efforts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEffort.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].batchSize").value(hasItem(DEFAULT_BATCH_SIZE.doubleValue())))
            .andExpect(jsonPath("$.[*].minYieldRange").value(hasItem(DEFAULT_MIN_YIELD_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].maxYieldRange").value(hasItem(DEFAULT_MAX_YIELD_RANGE.doubleValue())))
            .andExpect(jsonPath("$.[*].percentComplete").value(hasItem(DEFAULT_PERCENT_COMPLETE.doubleValue())))
            .andExpect(jsonPath("$.[*].validationType").value(hasItem(DEFAULT_VALIDATION_TYPE)))
            .andExpect(jsonPath("$.[*].shelfLife").value(hasItem(DEFAULT_SHELF_LIFE)))
            .andExpect(jsonPath("$.[*].outputQty").value(hasItem(DEFAULT_OUTPUT_QTY.doubleValue())));

        // Check, that the count call also returns 1
        restWorkEffortMockMvc.perform(get("/api/work-efforts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkEffortShouldNotBeFound(String filter) throws Exception {
        restWorkEffortMockMvc.perform(get("/api/work-efforts?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkEffortMockMvc.perform(get("/api/work-efforts/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWorkEffort() throws Exception {
        // Get the workEffort
        restWorkEffortMockMvc.perform(get("/api/work-efforts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkEffort() throws Exception {
        // Initialize the database
        workEffortService.save(workEffort);

        int databaseSizeBeforeUpdate = workEffortRepository.findAll().size();

        // Update the workEffort
        WorkEffort updatedWorkEffort = workEffortRepository.findById(workEffort.getId()).get();
        // Disconnect from session so that the updates on updatedWorkEffort are not directly saved in db
        em.detach(updatedWorkEffort);
        updatedWorkEffort
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .code(UPDATED_CODE)
            .batchSize(UPDATED_BATCH_SIZE)
            .minYieldRange(UPDATED_MIN_YIELD_RANGE)
            .maxYieldRange(UPDATED_MAX_YIELD_RANGE)
            .percentComplete(UPDATED_PERCENT_COMPLETE)
            .validationType(UPDATED_VALIDATION_TYPE)
            .shelfLife(UPDATED_SHELF_LIFE)
            .outputQty(UPDATED_OUTPUT_QTY);

        restWorkEffortMockMvc.perform(put("/api/work-efforts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkEffort)))
            .andExpect(status().isOk());

        // Validate the WorkEffort in the database
        List<WorkEffort> workEffortList = workEffortRepository.findAll();
        assertThat(workEffortList).hasSize(databaseSizeBeforeUpdate);
        WorkEffort testWorkEffort = workEffortList.get(workEffortList.size() - 1);
        assertThat(testWorkEffort.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkEffort.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testWorkEffort.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testWorkEffort.getBatchSize()).isEqualTo(UPDATED_BATCH_SIZE);
        assertThat(testWorkEffort.getMinYieldRange()).isEqualTo(UPDATED_MIN_YIELD_RANGE);
        assertThat(testWorkEffort.getMaxYieldRange()).isEqualTo(UPDATED_MAX_YIELD_RANGE);
        assertThat(testWorkEffort.getPercentComplete()).isEqualTo(UPDATED_PERCENT_COMPLETE);
        assertThat(testWorkEffort.getValidationType()).isEqualTo(UPDATED_VALIDATION_TYPE);
        assertThat(testWorkEffort.getShelfLife()).isEqualTo(UPDATED_SHELF_LIFE);
        assertThat(testWorkEffort.getOutputQty()).isEqualTo(UPDATED_OUTPUT_QTY);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkEffort() throws Exception {
        int databaseSizeBeforeUpdate = workEffortRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkEffortMockMvc.perform(put("/api/work-efforts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffort)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffort in the database
        List<WorkEffort> workEffortList = workEffortRepository.findAll();
        assertThat(workEffortList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkEffort() throws Exception {
        // Initialize the database
        workEffortService.save(workEffort);

        int databaseSizeBeforeDelete = workEffortRepository.findAll().size();

        // Delete the workEffort
        restWorkEffortMockMvc.perform(delete("/api/work-efforts/{id}", workEffort.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkEffort> workEffortList = workEffortRepository.findAll();
        assertThat(workEffortList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
