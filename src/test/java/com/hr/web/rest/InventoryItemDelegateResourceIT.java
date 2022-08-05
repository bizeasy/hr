package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.InventoryItemDelegate;
import com.hr.domain.Invoice;
import com.hr.domain.InvoiceItem;
import com.hr.domain.Order;
import com.hr.domain.OrderItem;
import com.hr.domain.ItemIssuance;
import com.hr.domain.InventoryTransfer;
import com.hr.domain.InventoryItemVariance;
import com.hr.domain.InventoryItem;
import com.hr.repository.InventoryItemDelegateRepository;
import com.hr.service.InventoryItemDelegateService;
import com.hr.service.dto.InventoryItemDelegateCriteria;
import com.hr.service.InventoryItemDelegateQueryService;

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
import java.math.BigDecimal;
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
 * Integration tests for the {@link InventoryItemDelegateResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InventoryItemDelegateResourceIT {

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;
    private static final Integer SMALLER_SEQUENCE_NO = 1 - 1;

    private static final ZonedDateTime DEFAULT_EFFECTIVE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EFFECTIVE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_EFFECTIVE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final BigDecimal DEFAULT_QUANTITY_ON_HAND_DIFF = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY_ON_HAND_DIFF = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY_ON_HAND_DIFF = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_AVAILABLE_TO_PROMISE_DIFF = new BigDecimal(1);
    private static final BigDecimal UPDATED_AVAILABLE_TO_PROMISE_DIFF = new BigDecimal(2);
    private static final BigDecimal SMALLER_AVAILABLE_TO_PROMISE_DIFF = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ACCOUNTING_QUANTITY_DIFF = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACCOUNTING_QUANTITY_DIFF = new BigDecimal(2);
    private static final BigDecimal SMALLER_ACCOUNTING_QUANTITY_DIFF = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_UNIT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_COST = new BigDecimal(2);
    private static final BigDecimal SMALLER_UNIT_COST = new BigDecimal(1 - 1);

    private static final String DEFAULT_REMARKS = "AAAAAAAAAA";
    private static final String UPDATED_REMARKS = "BBBBBBBBBB";

    @Autowired
    private InventoryItemDelegateRepository inventoryItemDelegateRepository;

    @Autowired
    private InventoryItemDelegateService inventoryItemDelegateService;

    @Autowired
    private InventoryItemDelegateQueryService inventoryItemDelegateQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryItemDelegateMockMvc;

    private InventoryItemDelegate inventoryItemDelegate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryItemDelegate createEntity(EntityManager em) {
        InventoryItemDelegate inventoryItemDelegate = new InventoryItemDelegate()
            .sequenceNo(DEFAULT_SEQUENCE_NO)
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .quantityOnHandDiff(DEFAULT_QUANTITY_ON_HAND_DIFF)
            .availableToPromiseDiff(DEFAULT_AVAILABLE_TO_PROMISE_DIFF)
            .accountingQuantityDiff(DEFAULT_ACCOUNTING_QUANTITY_DIFF)
            .unitCost(DEFAULT_UNIT_COST)
            .remarks(DEFAULT_REMARKS);
        return inventoryItemDelegate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryItemDelegate createUpdatedEntity(EntityManager em) {
        InventoryItemDelegate inventoryItemDelegate = new InventoryItemDelegate()
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .quantityOnHandDiff(UPDATED_QUANTITY_ON_HAND_DIFF)
            .availableToPromiseDiff(UPDATED_AVAILABLE_TO_PROMISE_DIFF)
            .accountingQuantityDiff(UPDATED_ACCOUNTING_QUANTITY_DIFF)
            .unitCost(UPDATED_UNIT_COST)
            .remarks(UPDATED_REMARKS);
        return inventoryItemDelegate;
    }

    @BeforeEach
    public void initTest() {
        inventoryItemDelegate = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventoryItemDelegate() throws Exception {
        int databaseSizeBeforeCreate = inventoryItemDelegateRepository.findAll().size();
        // Create the InventoryItemDelegate
        restInventoryItemDelegateMockMvc.perform(post("/api/inventory-item-delegates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryItemDelegate)))
            .andExpect(status().isCreated());

        // Validate the InventoryItemDelegate in the database
        List<InventoryItemDelegate> inventoryItemDelegateList = inventoryItemDelegateRepository.findAll();
        assertThat(inventoryItemDelegateList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryItemDelegate testInventoryItemDelegate = inventoryItemDelegateList.get(inventoryItemDelegateList.size() - 1);
        assertThat(testInventoryItemDelegate.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
        assertThat(testInventoryItemDelegate.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testInventoryItemDelegate.getQuantityOnHandDiff()).isEqualTo(DEFAULT_QUANTITY_ON_HAND_DIFF);
        assertThat(testInventoryItemDelegate.getAvailableToPromiseDiff()).isEqualTo(DEFAULT_AVAILABLE_TO_PROMISE_DIFF);
        assertThat(testInventoryItemDelegate.getAccountingQuantityDiff()).isEqualTo(DEFAULT_ACCOUNTING_QUANTITY_DIFF);
        assertThat(testInventoryItemDelegate.getUnitCost()).isEqualTo(DEFAULT_UNIT_COST);
        assertThat(testInventoryItemDelegate.getRemarks()).isEqualTo(DEFAULT_REMARKS);
    }

    @Test
    @Transactional
    public void createInventoryItemDelegateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryItemDelegateRepository.findAll().size();

        // Create the InventoryItemDelegate with an existing ID
        inventoryItemDelegate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryItemDelegateMockMvc.perform(post("/api/inventory-item-delegates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryItemDelegate)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryItemDelegate in the database
        List<InventoryItemDelegate> inventoryItemDelegateList = inventoryItemDelegateRepository.findAll();
        assertThat(inventoryItemDelegateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInventoryItemDelegates() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList
        restInventoryItemDelegateMockMvc.perform(get("/api/inventory-item-delegates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryItemDelegate.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(sameInstant(DEFAULT_EFFECTIVE_DATE))))
            .andExpect(jsonPath("$.[*].quantityOnHandDiff").value(hasItem(DEFAULT_QUANTITY_ON_HAND_DIFF.intValue())))
            .andExpect(jsonPath("$.[*].availableToPromiseDiff").value(hasItem(DEFAULT_AVAILABLE_TO_PROMISE_DIFF.intValue())))
            .andExpect(jsonPath("$.[*].accountingQuantityDiff").value(hasItem(DEFAULT_ACCOUNTING_QUANTITY_DIFF.intValue())))
            .andExpect(jsonPath("$.[*].unitCost").value(hasItem(DEFAULT_UNIT_COST.intValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));
    }
    
    @Test
    @Transactional
    public void getInventoryItemDelegate() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get the inventoryItemDelegate
        restInventoryItemDelegateMockMvc.perform(get("/api/inventory-item-delegates/{id}", inventoryItemDelegate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryItemDelegate.getId().intValue()))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO))
            .andExpect(jsonPath("$.effectiveDate").value(sameInstant(DEFAULT_EFFECTIVE_DATE)))
            .andExpect(jsonPath("$.quantityOnHandDiff").value(DEFAULT_QUANTITY_ON_HAND_DIFF.intValue()))
            .andExpect(jsonPath("$.availableToPromiseDiff").value(DEFAULT_AVAILABLE_TO_PROMISE_DIFF.intValue()))
            .andExpect(jsonPath("$.accountingQuantityDiff").value(DEFAULT_ACCOUNTING_QUANTITY_DIFF.intValue()))
            .andExpect(jsonPath("$.unitCost").value(DEFAULT_UNIT_COST.intValue()))
            .andExpect(jsonPath("$.remarks").value(DEFAULT_REMARKS));
    }


    @Test
    @Transactional
    public void getInventoryItemDelegatesByIdFiltering() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        Long id = inventoryItemDelegate.getId();

        defaultInventoryItemDelegateShouldBeFound("id.equals=" + id);
        defaultInventoryItemDelegateShouldNotBeFound("id.notEquals=" + id);

        defaultInventoryItemDelegateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInventoryItemDelegateShouldNotBeFound("id.greaterThan=" + id);

        defaultInventoryItemDelegateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInventoryItemDelegateShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInventoryItemDelegatesBySequenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where sequenceNo equals to DEFAULT_SEQUENCE_NO
        defaultInventoryItemDelegateShouldBeFound("sequenceNo.equals=" + DEFAULT_SEQUENCE_NO);

        // Get all the inventoryItemDelegateList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultInventoryItemDelegateShouldNotBeFound("sequenceNo.equals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesBySequenceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where sequenceNo not equals to DEFAULT_SEQUENCE_NO
        defaultInventoryItemDelegateShouldNotBeFound("sequenceNo.notEquals=" + DEFAULT_SEQUENCE_NO);

        // Get all the inventoryItemDelegateList where sequenceNo not equals to UPDATED_SEQUENCE_NO
        defaultInventoryItemDelegateShouldBeFound("sequenceNo.notEquals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesBySequenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where sequenceNo in DEFAULT_SEQUENCE_NO or UPDATED_SEQUENCE_NO
        defaultInventoryItemDelegateShouldBeFound("sequenceNo.in=" + DEFAULT_SEQUENCE_NO + "," + UPDATED_SEQUENCE_NO);

        // Get all the inventoryItemDelegateList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultInventoryItemDelegateShouldNotBeFound("sequenceNo.in=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesBySequenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where sequenceNo is not null
        defaultInventoryItemDelegateShouldBeFound("sequenceNo.specified=true");

        // Get all the inventoryItemDelegateList where sequenceNo is null
        defaultInventoryItemDelegateShouldNotBeFound("sequenceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesBySequenceNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where sequenceNo is greater than or equal to DEFAULT_SEQUENCE_NO
        defaultInventoryItemDelegateShouldBeFound("sequenceNo.greaterThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the inventoryItemDelegateList where sequenceNo is greater than or equal to UPDATED_SEQUENCE_NO
        defaultInventoryItemDelegateShouldNotBeFound("sequenceNo.greaterThanOrEqual=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesBySequenceNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where sequenceNo is less than or equal to DEFAULT_SEQUENCE_NO
        defaultInventoryItemDelegateShouldBeFound("sequenceNo.lessThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the inventoryItemDelegateList where sequenceNo is less than or equal to SMALLER_SEQUENCE_NO
        defaultInventoryItemDelegateShouldNotBeFound("sequenceNo.lessThanOrEqual=" + SMALLER_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesBySequenceNoIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where sequenceNo is less than DEFAULT_SEQUENCE_NO
        defaultInventoryItemDelegateShouldNotBeFound("sequenceNo.lessThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the inventoryItemDelegateList where sequenceNo is less than UPDATED_SEQUENCE_NO
        defaultInventoryItemDelegateShouldBeFound("sequenceNo.lessThan=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesBySequenceNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where sequenceNo is greater than DEFAULT_SEQUENCE_NO
        defaultInventoryItemDelegateShouldNotBeFound("sequenceNo.greaterThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the inventoryItemDelegateList where sequenceNo is greater than SMALLER_SEQUENCE_NO
        defaultInventoryItemDelegateShouldBeFound("sequenceNo.greaterThan=" + SMALLER_SEQUENCE_NO);
    }


    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByEffectiveDateIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where effectiveDate equals to DEFAULT_EFFECTIVE_DATE
        defaultInventoryItemDelegateShouldBeFound("effectiveDate.equals=" + DEFAULT_EFFECTIVE_DATE);

        // Get all the inventoryItemDelegateList where effectiveDate equals to UPDATED_EFFECTIVE_DATE
        defaultInventoryItemDelegateShouldNotBeFound("effectiveDate.equals=" + UPDATED_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByEffectiveDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where effectiveDate not equals to DEFAULT_EFFECTIVE_DATE
        defaultInventoryItemDelegateShouldNotBeFound("effectiveDate.notEquals=" + DEFAULT_EFFECTIVE_DATE);

        // Get all the inventoryItemDelegateList where effectiveDate not equals to UPDATED_EFFECTIVE_DATE
        defaultInventoryItemDelegateShouldBeFound("effectiveDate.notEquals=" + UPDATED_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByEffectiveDateIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where effectiveDate in DEFAULT_EFFECTIVE_DATE or UPDATED_EFFECTIVE_DATE
        defaultInventoryItemDelegateShouldBeFound("effectiveDate.in=" + DEFAULT_EFFECTIVE_DATE + "," + UPDATED_EFFECTIVE_DATE);

        // Get all the inventoryItemDelegateList where effectiveDate equals to UPDATED_EFFECTIVE_DATE
        defaultInventoryItemDelegateShouldNotBeFound("effectiveDate.in=" + UPDATED_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByEffectiveDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where effectiveDate is not null
        defaultInventoryItemDelegateShouldBeFound("effectiveDate.specified=true");

        // Get all the inventoryItemDelegateList where effectiveDate is null
        defaultInventoryItemDelegateShouldNotBeFound("effectiveDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByEffectiveDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where effectiveDate is greater than or equal to DEFAULT_EFFECTIVE_DATE
        defaultInventoryItemDelegateShouldBeFound("effectiveDate.greaterThanOrEqual=" + DEFAULT_EFFECTIVE_DATE);

        // Get all the inventoryItemDelegateList where effectiveDate is greater than or equal to UPDATED_EFFECTIVE_DATE
        defaultInventoryItemDelegateShouldNotBeFound("effectiveDate.greaterThanOrEqual=" + UPDATED_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByEffectiveDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where effectiveDate is less than or equal to DEFAULT_EFFECTIVE_DATE
        defaultInventoryItemDelegateShouldBeFound("effectiveDate.lessThanOrEqual=" + DEFAULT_EFFECTIVE_DATE);

        // Get all the inventoryItemDelegateList where effectiveDate is less than or equal to SMALLER_EFFECTIVE_DATE
        defaultInventoryItemDelegateShouldNotBeFound("effectiveDate.lessThanOrEqual=" + SMALLER_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByEffectiveDateIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where effectiveDate is less than DEFAULT_EFFECTIVE_DATE
        defaultInventoryItemDelegateShouldNotBeFound("effectiveDate.lessThan=" + DEFAULT_EFFECTIVE_DATE);

        // Get all the inventoryItemDelegateList where effectiveDate is less than UPDATED_EFFECTIVE_DATE
        defaultInventoryItemDelegateShouldBeFound("effectiveDate.lessThan=" + UPDATED_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByEffectiveDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where effectiveDate is greater than DEFAULT_EFFECTIVE_DATE
        defaultInventoryItemDelegateShouldNotBeFound("effectiveDate.greaterThan=" + DEFAULT_EFFECTIVE_DATE);

        // Get all the inventoryItemDelegateList where effectiveDate is greater than SMALLER_EFFECTIVE_DATE
        defaultInventoryItemDelegateShouldBeFound("effectiveDate.greaterThan=" + SMALLER_EFFECTIVE_DATE);
    }


    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByQuantityOnHandDiffIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where quantityOnHandDiff equals to DEFAULT_QUANTITY_ON_HAND_DIFF
        defaultInventoryItemDelegateShouldBeFound("quantityOnHandDiff.equals=" + DEFAULT_QUANTITY_ON_HAND_DIFF);

        // Get all the inventoryItemDelegateList where quantityOnHandDiff equals to UPDATED_QUANTITY_ON_HAND_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("quantityOnHandDiff.equals=" + UPDATED_QUANTITY_ON_HAND_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByQuantityOnHandDiffIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where quantityOnHandDiff not equals to DEFAULT_QUANTITY_ON_HAND_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("quantityOnHandDiff.notEquals=" + DEFAULT_QUANTITY_ON_HAND_DIFF);

        // Get all the inventoryItemDelegateList where quantityOnHandDiff not equals to UPDATED_QUANTITY_ON_HAND_DIFF
        defaultInventoryItemDelegateShouldBeFound("quantityOnHandDiff.notEquals=" + UPDATED_QUANTITY_ON_HAND_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByQuantityOnHandDiffIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where quantityOnHandDiff in DEFAULT_QUANTITY_ON_HAND_DIFF or UPDATED_QUANTITY_ON_HAND_DIFF
        defaultInventoryItemDelegateShouldBeFound("quantityOnHandDiff.in=" + DEFAULT_QUANTITY_ON_HAND_DIFF + "," + UPDATED_QUANTITY_ON_HAND_DIFF);

        // Get all the inventoryItemDelegateList where quantityOnHandDiff equals to UPDATED_QUANTITY_ON_HAND_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("quantityOnHandDiff.in=" + UPDATED_QUANTITY_ON_HAND_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByQuantityOnHandDiffIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where quantityOnHandDiff is not null
        defaultInventoryItemDelegateShouldBeFound("quantityOnHandDiff.specified=true");

        // Get all the inventoryItemDelegateList where quantityOnHandDiff is null
        defaultInventoryItemDelegateShouldNotBeFound("quantityOnHandDiff.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByQuantityOnHandDiffIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where quantityOnHandDiff is greater than or equal to DEFAULT_QUANTITY_ON_HAND_DIFF
        defaultInventoryItemDelegateShouldBeFound("quantityOnHandDiff.greaterThanOrEqual=" + DEFAULT_QUANTITY_ON_HAND_DIFF);

        // Get all the inventoryItemDelegateList where quantityOnHandDiff is greater than or equal to UPDATED_QUANTITY_ON_HAND_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("quantityOnHandDiff.greaterThanOrEqual=" + UPDATED_QUANTITY_ON_HAND_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByQuantityOnHandDiffIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where quantityOnHandDiff is less than or equal to DEFAULT_QUANTITY_ON_HAND_DIFF
        defaultInventoryItemDelegateShouldBeFound("quantityOnHandDiff.lessThanOrEqual=" + DEFAULT_QUANTITY_ON_HAND_DIFF);

        // Get all the inventoryItemDelegateList where quantityOnHandDiff is less than or equal to SMALLER_QUANTITY_ON_HAND_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("quantityOnHandDiff.lessThanOrEqual=" + SMALLER_QUANTITY_ON_HAND_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByQuantityOnHandDiffIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where quantityOnHandDiff is less than DEFAULT_QUANTITY_ON_HAND_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("quantityOnHandDiff.lessThan=" + DEFAULT_QUANTITY_ON_HAND_DIFF);

        // Get all the inventoryItemDelegateList where quantityOnHandDiff is less than UPDATED_QUANTITY_ON_HAND_DIFF
        defaultInventoryItemDelegateShouldBeFound("quantityOnHandDiff.lessThan=" + UPDATED_QUANTITY_ON_HAND_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByQuantityOnHandDiffIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where quantityOnHandDiff is greater than DEFAULT_QUANTITY_ON_HAND_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("quantityOnHandDiff.greaterThan=" + DEFAULT_QUANTITY_ON_HAND_DIFF);

        // Get all the inventoryItemDelegateList where quantityOnHandDiff is greater than SMALLER_QUANTITY_ON_HAND_DIFF
        defaultInventoryItemDelegateShouldBeFound("quantityOnHandDiff.greaterThan=" + SMALLER_QUANTITY_ON_HAND_DIFF);
    }


    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByAvailableToPromiseDiffIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where availableToPromiseDiff equals to DEFAULT_AVAILABLE_TO_PROMISE_DIFF
        defaultInventoryItemDelegateShouldBeFound("availableToPromiseDiff.equals=" + DEFAULT_AVAILABLE_TO_PROMISE_DIFF);

        // Get all the inventoryItemDelegateList where availableToPromiseDiff equals to UPDATED_AVAILABLE_TO_PROMISE_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("availableToPromiseDiff.equals=" + UPDATED_AVAILABLE_TO_PROMISE_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByAvailableToPromiseDiffIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where availableToPromiseDiff not equals to DEFAULT_AVAILABLE_TO_PROMISE_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("availableToPromiseDiff.notEquals=" + DEFAULT_AVAILABLE_TO_PROMISE_DIFF);

        // Get all the inventoryItemDelegateList where availableToPromiseDiff not equals to UPDATED_AVAILABLE_TO_PROMISE_DIFF
        defaultInventoryItemDelegateShouldBeFound("availableToPromiseDiff.notEquals=" + UPDATED_AVAILABLE_TO_PROMISE_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByAvailableToPromiseDiffIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where availableToPromiseDiff in DEFAULT_AVAILABLE_TO_PROMISE_DIFF or UPDATED_AVAILABLE_TO_PROMISE_DIFF
        defaultInventoryItemDelegateShouldBeFound("availableToPromiseDiff.in=" + DEFAULT_AVAILABLE_TO_PROMISE_DIFF + "," + UPDATED_AVAILABLE_TO_PROMISE_DIFF);

        // Get all the inventoryItemDelegateList where availableToPromiseDiff equals to UPDATED_AVAILABLE_TO_PROMISE_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("availableToPromiseDiff.in=" + UPDATED_AVAILABLE_TO_PROMISE_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByAvailableToPromiseDiffIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where availableToPromiseDiff is not null
        defaultInventoryItemDelegateShouldBeFound("availableToPromiseDiff.specified=true");

        // Get all the inventoryItemDelegateList where availableToPromiseDiff is null
        defaultInventoryItemDelegateShouldNotBeFound("availableToPromiseDiff.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByAvailableToPromiseDiffIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where availableToPromiseDiff is greater than or equal to DEFAULT_AVAILABLE_TO_PROMISE_DIFF
        defaultInventoryItemDelegateShouldBeFound("availableToPromiseDiff.greaterThanOrEqual=" + DEFAULT_AVAILABLE_TO_PROMISE_DIFF);

        // Get all the inventoryItemDelegateList where availableToPromiseDiff is greater than or equal to UPDATED_AVAILABLE_TO_PROMISE_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("availableToPromiseDiff.greaterThanOrEqual=" + UPDATED_AVAILABLE_TO_PROMISE_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByAvailableToPromiseDiffIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where availableToPromiseDiff is less than or equal to DEFAULT_AVAILABLE_TO_PROMISE_DIFF
        defaultInventoryItemDelegateShouldBeFound("availableToPromiseDiff.lessThanOrEqual=" + DEFAULT_AVAILABLE_TO_PROMISE_DIFF);

        // Get all the inventoryItemDelegateList where availableToPromiseDiff is less than or equal to SMALLER_AVAILABLE_TO_PROMISE_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("availableToPromiseDiff.lessThanOrEqual=" + SMALLER_AVAILABLE_TO_PROMISE_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByAvailableToPromiseDiffIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where availableToPromiseDiff is less than DEFAULT_AVAILABLE_TO_PROMISE_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("availableToPromiseDiff.lessThan=" + DEFAULT_AVAILABLE_TO_PROMISE_DIFF);

        // Get all the inventoryItemDelegateList where availableToPromiseDiff is less than UPDATED_AVAILABLE_TO_PROMISE_DIFF
        defaultInventoryItemDelegateShouldBeFound("availableToPromiseDiff.lessThan=" + UPDATED_AVAILABLE_TO_PROMISE_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByAvailableToPromiseDiffIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where availableToPromiseDiff is greater than DEFAULT_AVAILABLE_TO_PROMISE_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("availableToPromiseDiff.greaterThan=" + DEFAULT_AVAILABLE_TO_PROMISE_DIFF);

        // Get all the inventoryItemDelegateList where availableToPromiseDiff is greater than SMALLER_AVAILABLE_TO_PROMISE_DIFF
        defaultInventoryItemDelegateShouldBeFound("availableToPromiseDiff.greaterThan=" + SMALLER_AVAILABLE_TO_PROMISE_DIFF);
    }


    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByAccountingQuantityDiffIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where accountingQuantityDiff equals to DEFAULT_ACCOUNTING_QUANTITY_DIFF
        defaultInventoryItemDelegateShouldBeFound("accountingQuantityDiff.equals=" + DEFAULT_ACCOUNTING_QUANTITY_DIFF);

        // Get all the inventoryItemDelegateList where accountingQuantityDiff equals to UPDATED_ACCOUNTING_QUANTITY_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("accountingQuantityDiff.equals=" + UPDATED_ACCOUNTING_QUANTITY_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByAccountingQuantityDiffIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where accountingQuantityDiff not equals to DEFAULT_ACCOUNTING_QUANTITY_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("accountingQuantityDiff.notEquals=" + DEFAULT_ACCOUNTING_QUANTITY_DIFF);

        // Get all the inventoryItemDelegateList where accountingQuantityDiff not equals to UPDATED_ACCOUNTING_QUANTITY_DIFF
        defaultInventoryItemDelegateShouldBeFound("accountingQuantityDiff.notEquals=" + UPDATED_ACCOUNTING_QUANTITY_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByAccountingQuantityDiffIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where accountingQuantityDiff in DEFAULT_ACCOUNTING_QUANTITY_DIFF or UPDATED_ACCOUNTING_QUANTITY_DIFF
        defaultInventoryItemDelegateShouldBeFound("accountingQuantityDiff.in=" + DEFAULT_ACCOUNTING_QUANTITY_DIFF + "," + UPDATED_ACCOUNTING_QUANTITY_DIFF);

        // Get all the inventoryItemDelegateList where accountingQuantityDiff equals to UPDATED_ACCOUNTING_QUANTITY_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("accountingQuantityDiff.in=" + UPDATED_ACCOUNTING_QUANTITY_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByAccountingQuantityDiffIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where accountingQuantityDiff is not null
        defaultInventoryItemDelegateShouldBeFound("accountingQuantityDiff.specified=true");

        // Get all the inventoryItemDelegateList where accountingQuantityDiff is null
        defaultInventoryItemDelegateShouldNotBeFound("accountingQuantityDiff.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByAccountingQuantityDiffIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where accountingQuantityDiff is greater than or equal to DEFAULT_ACCOUNTING_QUANTITY_DIFF
        defaultInventoryItemDelegateShouldBeFound("accountingQuantityDiff.greaterThanOrEqual=" + DEFAULT_ACCOUNTING_QUANTITY_DIFF);

        // Get all the inventoryItemDelegateList where accountingQuantityDiff is greater than or equal to UPDATED_ACCOUNTING_QUANTITY_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("accountingQuantityDiff.greaterThanOrEqual=" + UPDATED_ACCOUNTING_QUANTITY_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByAccountingQuantityDiffIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where accountingQuantityDiff is less than or equal to DEFAULT_ACCOUNTING_QUANTITY_DIFF
        defaultInventoryItemDelegateShouldBeFound("accountingQuantityDiff.lessThanOrEqual=" + DEFAULT_ACCOUNTING_QUANTITY_DIFF);

        // Get all the inventoryItemDelegateList where accountingQuantityDiff is less than or equal to SMALLER_ACCOUNTING_QUANTITY_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("accountingQuantityDiff.lessThanOrEqual=" + SMALLER_ACCOUNTING_QUANTITY_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByAccountingQuantityDiffIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where accountingQuantityDiff is less than DEFAULT_ACCOUNTING_QUANTITY_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("accountingQuantityDiff.lessThan=" + DEFAULT_ACCOUNTING_QUANTITY_DIFF);

        // Get all the inventoryItemDelegateList where accountingQuantityDiff is less than UPDATED_ACCOUNTING_QUANTITY_DIFF
        defaultInventoryItemDelegateShouldBeFound("accountingQuantityDiff.lessThan=" + UPDATED_ACCOUNTING_QUANTITY_DIFF);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByAccountingQuantityDiffIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where accountingQuantityDiff is greater than DEFAULT_ACCOUNTING_QUANTITY_DIFF
        defaultInventoryItemDelegateShouldNotBeFound("accountingQuantityDiff.greaterThan=" + DEFAULT_ACCOUNTING_QUANTITY_DIFF);

        // Get all the inventoryItemDelegateList where accountingQuantityDiff is greater than SMALLER_ACCOUNTING_QUANTITY_DIFF
        defaultInventoryItemDelegateShouldBeFound("accountingQuantityDiff.greaterThan=" + SMALLER_ACCOUNTING_QUANTITY_DIFF);
    }


    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByUnitCostIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where unitCost equals to DEFAULT_UNIT_COST
        defaultInventoryItemDelegateShouldBeFound("unitCost.equals=" + DEFAULT_UNIT_COST);

        // Get all the inventoryItemDelegateList where unitCost equals to UPDATED_UNIT_COST
        defaultInventoryItemDelegateShouldNotBeFound("unitCost.equals=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByUnitCostIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where unitCost not equals to DEFAULT_UNIT_COST
        defaultInventoryItemDelegateShouldNotBeFound("unitCost.notEquals=" + DEFAULT_UNIT_COST);

        // Get all the inventoryItemDelegateList where unitCost not equals to UPDATED_UNIT_COST
        defaultInventoryItemDelegateShouldBeFound("unitCost.notEquals=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByUnitCostIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where unitCost in DEFAULT_UNIT_COST or UPDATED_UNIT_COST
        defaultInventoryItemDelegateShouldBeFound("unitCost.in=" + DEFAULT_UNIT_COST + "," + UPDATED_UNIT_COST);

        // Get all the inventoryItemDelegateList where unitCost equals to UPDATED_UNIT_COST
        defaultInventoryItemDelegateShouldNotBeFound("unitCost.in=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByUnitCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where unitCost is not null
        defaultInventoryItemDelegateShouldBeFound("unitCost.specified=true");

        // Get all the inventoryItemDelegateList where unitCost is null
        defaultInventoryItemDelegateShouldNotBeFound("unitCost.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByUnitCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where unitCost is greater than or equal to DEFAULT_UNIT_COST
        defaultInventoryItemDelegateShouldBeFound("unitCost.greaterThanOrEqual=" + DEFAULT_UNIT_COST);

        // Get all the inventoryItemDelegateList where unitCost is greater than or equal to UPDATED_UNIT_COST
        defaultInventoryItemDelegateShouldNotBeFound("unitCost.greaterThanOrEqual=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByUnitCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where unitCost is less than or equal to DEFAULT_UNIT_COST
        defaultInventoryItemDelegateShouldBeFound("unitCost.lessThanOrEqual=" + DEFAULT_UNIT_COST);

        // Get all the inventoryItemDelegateList where unitCost is less than or equal to SMALLER_UNIT_COST
        defaultInventoryItemDelegateShouldNotBeFound("unitCost.lessThanOrEqual=" + SMALLER_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByUnitCostIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where unitCost is less than DEFAULT_UNIT_COST
        defaultInventoryItemDelegateShouldNotBeFound("unitCost.lessThan=" + DEFAULT_UNIT_COST);

        // Get all the inventoryItemDelegateList where unitCost is less than UPDATED_UNIT_COST
        defaultInventoryItemDelegateShouldBeFound("unitCost.lessThan=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByUnitCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where unitCost is greater than DEFAULT_UNIT_COST
        defaultInventoryItemDelegateShouldNotBeFound("unitCost.greaterThan=" + DEFAULT_UNIT_COST);

        // Get all the inventoryItemDelegateList where unitCost is greater than SMALLER_UNIT_COST
        defaultInventoryItemDelegateShouldBeFound("unitCost.greaterThan=" + SMALLER_UNIT_COST);
    }


    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByRemarksIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where remarks equals to DEFAULT_REMARKS
        defaultInventoryItemDelegateShouldBeFound("remarks.equals=" + DEFAULT_REMARKS);

        // Get all the inventoryItemDelegateList where remarks equals to UPDATED_REMARKS
        defaultInventoryItemDelegateShouldNotBeFound("remarks.equals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByRemarksIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where remarks not equals to DEFAULT_REMARKS
        defaultInventoryItemDelegateShouldNotBeFound("remarks.notEquals=" + DEFAULT_REMARKS);

        // Get all the inventoryItemDelegateList where remarks not equals to UPDATED_REMARKS
        defaultInventoryItemDelegateShouldBeFound("remarks.notEquals=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByRemarksIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where remarks in DEFAULT_REMARKS or UPDATED_REMARKS
        defaultInventoryItemDelegateShouldBeFound("remarks.in=" + DEFAULT_REMARKS + "," + UPDATED_REMARKS);

        // Get all the inventoryItemDelegateList where remarks equals to UPDATED_REMARKS
        defaultInventoryItemDelegateShouldNotBeFound("remarks.in=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByRemarksIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where remarks is not null
        defaultInventoryItemDelegateShouldBeFound("remarks.specified=true");

        // Get all the inventoryItemDelegateList where remarks is null
        defaultInventoryItemDelegateShouldNotBeFound("remarks.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoryItemDelegatesByRemarksContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where remarks contains DEFAULT_REMARKS
        defaultInventoryItemDelegateShouldBeFound("remarks.contains=" + DEFAULT_REMARKS);

        // Get all the inventoryItemDelegateList where remarks contains UPDATED_REMARKS
        defaultInventoryItemDelegateShouldNotBeFound("remarks.contains=" + UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByRemarksNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);

        // Get all the inventoryItemDelegateList where remarks does not contain DEFAULT_REMARKS
        defaultInventoryItemDelegateShouldNotBeFound("remarks.doesNotContain=" + DEFAULT_REMARKS);

        // Get all the inventoryItemDelegateList where remarks does not contain UPDATED_REMARKS
        defaultInventoryItemDelegateShouldBeFound("remarks.doesNotContain=" + UPDATED_REMARKS);
    }


    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByInvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);
        Invoice invoice = InvoiceResourceIT.createEntity(em);
        em.persist(invoice);
        em.flush();
        inventoryItemDelegate.setInvoice(invoice);
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);
        Long invoiceId = invoice.getId();

        // Get all the inventoryItemDelegateList where invoice equals to invoiceId
        defaultInventoryItemDelegateShouldBeFound("invoiceId.equals=" + invoiceId);

        // Get all the inventoryItemDelegateList where invoice equals to invoiceId + 1
        defaultInventoryItemDelegateShouldNotBeFound("invoiceId.equals=" + (invoiceId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByInvoiceItemIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);
        InvoiceItem invoiceItem = InvoiceItemResourceIT.createEntity(em);
        em.persist(invoiceItem);
        em.flush();
        inventoryItemDelegate.setInvoiceItem(invoiceItem);
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);
        Long invoiceItemId = invoiceItem.getId();

        // Get all the inventoryItemDelegateList where invoiceItem equals to invoiceItemId
        defaultInventoryItemDelegateShouldBeFound("invoiceItemId.equals=" + invoiceItemId);

        // Get all the inventoryItemDelegateList where invoiceItem equals to invoiceItemId + 1
        defaultInventoryItemDelegateShouldNotBeFound("invoiceItemId.equals=" + (invoiceItemId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);
        Order order = OrderResourceIT.createEntity(em);
        em.persist(order);
        em.flush();
        inventoryItemDelegate.setOrder(order);
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);
        Long orderId = order.getId();

        // Get all the inventoryItemDelegateList where order equals to orderId
        defaultInventoryItemDelegateShouldBeFound("orderId.equals=" + orderId);

        // Get all the inventoryItemDelegateList where order equals to orderId + 1
        defaultInventoryItemDelegateShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByOrderItemIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);
        OrderItem orderItem = OrderItemResourceIT.createEntity(em);
        em.persist(orderItem);
        em.flush();
        inventoryItemDelegate.setOrderItem(orderItem);
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);
        Long orderItemId = orderItem.getId();

        // Get all the inventoryItemDelegateList where orderItem equals to orderItemId
        defaultInventoryItemDelegateShouldBeFound("orderItemId.equals=" + orderItemId);

        // Get all the inventoryItemDelegateList where orderItem equals to orderItemId + 1
        defaultInventoryItemDelegateShouldNotBeFound("orderItemId.equals=" + (orderItemId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByItemIssuanceIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);
        ItemIssuance itemIssuance = ItemIssuanceResourceIT.createEntity(em);
        em.persist(itemIssuance);
        em.flush();
        inventoryItemDelegate.setItemIssuance(itemIssuance);
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);
        Long itemIssuanceId = itemIssuance.getId();

        // Get all the inventoryItemDelegateList where itemIssuance equals to itemIssuanceId
        defaultInventoryItemDelegateShouldBeFound("itemIssuanceId.equals=" + itemIssuanceId);

        // Get all the inventoryItemDelegateList where itemIssuance equals to itemIssuanceId + 1
        defaultInventoryItemDelegateShouldNotBeFound("itemIssuanceId.equals=" + (itemIssuanceId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByInventoryTransferIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);
        InventoryTransfer inventoryTransfer = InventoryTransferResourceIT.createEntity(em);
        em.persist(inventoryTransfer);
        em.flush();
        inventoryItemDelegate.setInventoryTransfer(inventoryTransfer);
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);
        Long inventoryTransferId = inventoryTransfer.getId();

        // Get all the inventoryItemDelegateList where inventoryTransfer equals to inventoryTransferId
        defaultInventoryItemDelegateShouldBeFound("inventoryTransferId.equals=" + inventoryTransferId);

        // Get all the inventoryItemDelegateList where inventoryTransfer equals to inventoryTransferId + 1
        defaultInventoryItemDelegateShouldNotBeFound("inventoryTransferId.equals=" + (inventoryTransferId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByInventoryItemVarianceIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);
        InventoryItemVariance inventoryItemVariance = InventoryItemVarianceResourceIT.createEntity(em);
        em.persist(inventoryItemVariance);
        em.flush();
        inventoryItemDelegate.setInventoryItemVariance(inventoryItemVariance);
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);
        Long inventoryItemVarianceId = inventoryItemVariance.getId();

        // Get all the inventoryItemDelegateList where inventoryItemVariance equals to inventoryItemVarianceId
        defaultInventoryItemDelegateShouldBeFound("inventoryItemVarianceId.equals=" + inventoryItemVarianceId);

        // Get all the inventoryItemDelegateList where inventoryItemVariance equals to inventoryItemVarianceId + 1
        defaultInventoryItemDelegateShouldNotBeFound("inventoryItemVarianceId.equals=" + (inventoryItemVarianceId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryItemDelegatesByInventoryItemIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);
        InventoryItem inventoryItem = InventoryItemResourceIT.createEntity(em);
        em.persist(inventoryItem);
        em.flush();
        inventoryItemDelegate.setInventoryItem(inventoryItem);
        inventoryItemDelegateRepository.saveAndFlush(inventoryItemDelegate);
        Long inventoryItemId = inventoryItem.getId();

        // Get all the inventoryItemDelegateList where inventoryItem equals to inventoryItemId
        defaultInventoryItemDelegateShouldBeFound("inventoryItemId.equals=" + inventoryItemId);

        // Get all the inventoryItemDelegateList where inventoryItem equals to inventoryItemId + 1
        defaultInventoryItemDelegateShouldNotBeFound("inventoryItemId.equals=" + (inventoryItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInventoryItemDelegateShouldBeFound(String filter) throws Exception {
        restInventoryItemDelegateMockMvc.perform(get("/api/inventory-item-delegates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryItemDelegate.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(sameInstant(DEFAULT_EFFECTIVE_DATE))))
            .andExpect(jsonPath("$.[*].quantityOnHandDiff").value(hasItem(DEFAULT_QUANTITY_ON_HAND_DIFF.intValue())))
            .andExpect(jsonPath("$.[*].availableToPromiseDiff").value(hasItem(DEFAULT_AVAILABLE_TO_PROMISE_DIFF.intValue())))
            .andExpect(jsonPath("$.[*].accountingQuantityDiff").value(hasItem(DEFAULT_ACCOUNTING_QUANTITY_DIFF.intValue())))
            .andExpect(jsonPath("$.[*].unitCost").value(hasItem(DEFAULT_UNIT_COST.intValue())))
            .andExpect(jsonPath("$.[*].remarks").value(hasItem(DEFAULT_REMARKS)));

        // Check, that the count call also returns 1
        restInventoryItemDelegateMockMvc.perform(get("/api/inventory-item-delegates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInventoryItemDelegateShouldNotBeFound(String filter) throws Exception {
        restInventoryItemDelegateMockMvc.perform(get("/api/inventory-item-delegates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventoryItemDelegateMockMvc.perform(get("/api/inventory-item-delegates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryItemDelegate() throws Exception {
        // Get the inventoryItemDelegate
        restInventoryItemDelegateMockMvc.perform(get("/api/inventory-item-delegates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryItemDelegate() throws Exception {
        // Initialize the database
        inventoryItemDelegateService.save(inventoryItemDelegate);

        int databaseSizeBeforeUpdate = inventoryItemDelegateRepository.findAll().size();

        // Update the inventoryItemDelegate
        InventoryItemDelegate updatedInventoryItemDelegate = inventoryItemDelegateRepository.findById(inventoryItemDelegate.getId()).get();
        // Disconnect from session so that the updates on updatedInventoryItemDelegate are not directly saved in db
        em.detach(updatedInventoryItemDelegate);
        updatedInventoryItemDelegate
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .quantityOnHandDiff(UPDATED_QUANTITY_ON_HAND_DIFF)
            .availableToPromiseDiff(UPDATED_AVAILABLE_TO_PROMISE_DIFF)
            .accountingQuantityDiff(UPDATED_ACCOUNTING_QUANTITY_DIFF)
            .unitCost(UPDATED_UNIT_COST)
            .remarks(UPDATED_REMARKS);

        restInventoryItemDelegateMockMvc.perform(put("/api/inventory-item-delegates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventoryItemDelegate)))
            .andExpect(status().isOk());

        // Validate the InventoryItemDelegate in the database
        List<InventoryItemDelegate> inventoryItemDelegateList = inventoryItemDelegateRepository.findAll();
        assertThat(inventoryItemDelegateList).hasSize(databaseSizeBeforeUpdate);
        InventoryItemDelegate testInventoryItemDelegate = inventoryItemDelegateList.get(inventoryItemDelegateList.size() - 1);
        assertThat(testInventoryItemDelegate.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
        assertThat(testInventoryItemDelegate.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testInventoryItemDelegate.getQuantityOnHandDiff()).isEqualTo(UPDATED_QUANTITY_ON_HAND_DIFF);
        assertThat(testInventoryItemDelegate.getAvailableToPromiseDiff()).isEqualTo(UPDATED_AVAILABLE_TO_PROMISE_DIFF);
        assertThat(testInventoryItemDelegate.getAccountingQuantityDiff()).isEqualTo(UPDATED_ACCOUNTING_QUANTITY_DIFF);
        assertThat(testInventoryItemDelegate.getUnitCost()).isEqualTo(UPDATED_UNIT_COST);
        assertThat(testInventoryItemDelegate.getRemarks()).isEqualTo(UPDATED_REMARKS);
    }

    @Test
    @Transactional
    public void updateNonExistingInventoryItemDelegate() throws Exception {
        int databaseSizeBeforeUpdate = inventoryItemDelegateRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryItemDelegateMockMvc.perform(put("/api/inventory-item-delegates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryItemDelegate)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryItemDelegate in the database
        List<InventoryItemDelegate> inventoryItemDelegateList = inventoryItemDelegateRepository.findAll();
        assertThat(inventoryItemDelegateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventoryItemDelegate() throws Exception {
        // Initialize the database
        inventoryItemDelegateService.save(inventoryItemDelegate);

        int databaseSizeBeforeDelete = inventoryItemDelegateRepository.findAll().size();

        // Delete the inventoryItemDelegate
        restInventoryItemDelegateMockMvc.perform(delete("/api/inventory-item-delegates/{id}", inventoryItemDelegate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InventoryItemDelegate> inventoryItemDelegateList = inventoryItemDelegateRepository.findAll();
        assertThat(inventoryItemDelegateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
