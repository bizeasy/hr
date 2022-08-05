package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.InventoryItem;
import com.hr.domain.InventoryItemType;
import com.hr.domain.Product;
import com.hr.domain.Party;
import com.hr.domain.Status;
import com.hr.domain.Facility;
import com.hr.domain.Uom;
import com.hr.domain.Lot;
import com.hr.repository.InventoryItemRepository;
import com.hr.service.InventoryItemService;
import com.hr.service.dto.InventoryItemCriteria;
import com.hr.service.InventoryItemQueryService;

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
 * Integration tests for the {@link InventoryItemResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InventoryItemResourceIT {

    private static final ZonedDateTime DEFAULT_RECEIVED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RECEIVED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_RECEIVED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_MANUFACTURED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_MANUFACTURED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_MANUFACTURED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_EXPIRY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_EXPIRY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_RETEST_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RETEST_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_RETEST_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_CONTAINER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONTAINER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BATCH_NO = "AAAAAAAAAA";
    private static final String UPDATED_BATCH_NO = "BBBBBBBBBB";

    private static final String DEFAULT_MFG_BATCH_NO = "AAAAAAAAAA";
    private static final String UPDATED_MFG_BATCH_NO = "BBBBBBBBBB";

    private static final String DEFAULT_LOT_NO_STR = "AAAAAAAAAA";
    private static final String UPDATED_LOT_NO_STR = "BBBBBBBBBB";

    private static final String DEFAULT_BIN_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_BIN_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_QUANTITY_ON_HAND_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY_ON_HAND_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY_ON_HAND_TOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_AVAILABLE_TO_PROMISE_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_AVAILABLE_TO_PROMISE_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_AVAILABLE_TO_PROMISE_TOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_ACCOUNTING_QUANTITY_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACCOUNTING_QUANTITY_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_ACCOUNTING_QUANTITY_TOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_OLD_QUANTITY_ON_HAND = new BigDecimal(1);
    private static final BigDecimal UPDATED_OLD_QUANTITY_ON_HAND = new BigDecimal(2);
    private static final BigDecimal SMALLER_OLD_QUANTITY_ON_HAND = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_OLD_AVAILABLE_TO_PROMISE = new BigDecimal(1);
    private static final BigDecimal UPDATED_OLD_AVAILABLE_TO_PROMISE = new BigDecimal(2);
    private static final BigDecimal SMALLER_OLD_AVAILABLE_TO_PROMISE = new BigDecimal(1 - 1);

    private static final String DEFAULT_SERIAL_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SERIAL_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_SOFT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_SOFT_IDENTIFIER = "BBBBBBBBBB";

    private static final String DEFAULT_ACTIVATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ACTIVATION_NUMBER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ACTIVATION_VALID_TRUE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ACTIVATION_VALID_TRUE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ACTIVATION_VALID_TRUE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final BigDecimal DEFAULT_UNIT_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_COST = new BigDecimal(2);
    private static final BigDecimal SMALLER_UNIT_COST = new BigDecimal(1 - 1);

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private InventoryItemService inventoryItemService;

    @Autowired
    private InventoryItemQueryService inventoryItemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryItemMockMvc;

    private InventoryItem inventoryItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryItem createEntity(EntityManager em) {
        InventoryItem inventoryItem = new InventoryItem()
            .receivedDate(DEFAULT_RECEIVED_DATE)
            .manufacturedDate(DEFAULT_MANUFACTURED_DATE)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .retestDate(DEFAULT_RETEST_DATE)
            .containerId(DEFAULT_CONTAINER_ID)
            .batchNo(DEFAULT_BATCH_NO)
            .mfgBatchNo(DEFAULT_MFG_BATCH_NO)
            .lotNoStr(DEFAULT_LOT_NO_STR)
            .binNumber(DEFAULT_BIN_NUMBER)
            .comments(DEFAULT_COMMENTS)
            .quantityOnHandTotal(DEFAULT_QUANTITY_ON_HAND_TOTAL)
            .availableToPromiseTotal(DEFAULT_AVAILABLE_TO_PROMISE_TOTAL)
            .accountingQuantityTotal(DEFAULT_ACCOUNTING_QUANTITY_TOTAL)
            .oldQuantityOnHand(DEFAULT_OLD_QUANTITY_ON_HAND)
            .oldAvailableToPromise(DEFAULT_OLD_AVAILABLE_TO_PROMISE)
            .serialNumber(DEFAULT_SERIAL_NUMBER)
            .softIdentifier(DEFAULT_SOFT_IDENTIFIER)
            .activationNumber(DEFAULT_ACTIVATION_NUMBER)
            .activationValidTrue(DEFAULT_ACTIVATION_VALID_TRUE)
            .unitCost(DEFAULT_UNIT_COST);
        return inventoryItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryItem createUpdatedEntity(EntityManager em) {
        InventoryItem inventoryItem = new InventoryItem()
            .receivedDate(UPDATED_RECEIVED_DATE)
            .manufacturedDate(UPDATED_MANUFACTURED_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .retestDate(UPDATED_RETEST_DATE)
            .containerId(UPDATED_CONTAINER_ID)
            .batchNo(UPDATED_BATCH_NO)
            .mfgBatchNo(UPDATED_MFG_BATCH_NO)
            .lotNoStr(UPDATED_LOT_NO_STR)
            .binNumber(UPDATED_BIN_NUMBER)
            .comments(UPDATED_COMMENTS)
            .quantityOnHandTotal(UPDATED_QUANTITY_ON_HAND_TOTAL)
            .availableToPromiseTotal(UPDATED_AVAILABLE_TO_PROMISE_TOTAL)
            .accountingQuantityTotal(UPDATED_ACCOUNTING_QUANTITY_TOTAL)
            .oldQuantityOnHand(UPDATED_OLD_QUANTITY_ON_HAND)
            .oldAvailableToPromise(UPDATED_OLD_AVAILABLE_TO_PROMISE)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .softIdentifier(UPDATED_SOFT_IDENTIFIER)
            .activationNumber(UPDATED_ACTIVATION_NUMBER)
            .activationValidTrue(UPDATED_ACTIVATION_VALID_TRUE)
            .unitCost(UPDATED_UNIT_COST);
        return inventoryItem;
    }

    @BeforeEach
    public void initTest() {
        inventoryItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventoryItem() throws Exception {
        int databaseSizeBeforeCreate = inventoryItemRepository.findAll().size();
        // Create the InventoryItem
        restInventoryItemMockMvc.perform(post("/api/inventory-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryItem)))
            .andExpect(status().isCreated());

        // Validate the InventoryItem in the database
        List<InventoryItem> inventoryItemList = inventoryItemRepository.findAll();
        assertThat(inventoryItemList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryItem testInventoryItem = inventoryItemList.get(inventoryItemList.size() - 1);
        assertThat(testInventoryItem.getReceivedDate()).isEqualTo(DEFAULT_RECEIVED_DATE);
        assertThat(testInventoryItem.getManufacturedDate()).isEqualTo(DEFAULT_MANUFACTURED_DATE);
        assertThat(testInventoryItem.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testInventoryItem.getRetestDate()).isEqualTo(DEFAULT_RETEST_DATE);
        assertThat(testInventoryItem.getContainerId()).isEqualTo(DEFAULT_CONTAINER_ID);
        assertThat(testInventoryItem.getBatchNo()).isEqualTo(DEFAULT_BATCH_NO);
        assertThat(testInventoryItem.getMfgBatchNo()).isEqualTo(DEFAULT_MFG_BATCH_NO);
        assertThat(testInventoryItem.getLotNoStr()).isEqualTo(DEFAULT_LOT_NO_STR);
        assertThat(testInventoryItem.getBinNumber()).isEqualTo(DEFAULT_BIN_NUMBER);
        assertThat(testInventoryItem.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testInventoryItem.getQuantityOnHandTotal()).isEqualTo(DEFAULT_QUANTITY_ON_HAND_TOTAL);
        assertThat(testInventoryItem.getAvailableToPromiseTotal()).isEqualTo(DEFAULT_AVAILABLE_TO_PROMISE_TOTAL);
        assertThat(testInventoryItem.getAccountingQuantityTotal()).isEqualTo(DEFAULT_ACCOUNTING_QUANTITY_TOTAL);
        assertThat(testInventoryItem.getOldQuantityOnHand()).isEqualTo(DEFAULT_OLD_QUANTITY_ON_HAND);
        assertThat(testInventoryItem.getOldAvailableToPromise()).isEqualTo(DEFAULT_OLD_AVAILABLE_TO_PROMISE);
        assertThat(testInventoryItem.getSerialNumber()).isEqualTo(DEFAULT_SERIAL_NUMBER);
        assertThat(testInventoryItem.getSoftIdentifier()).isEqualTo(DEFAULT_SOFT_IDENTIFIER);
        assertThat(testInventoryItem.getActivationNumber()).isEqualTo(DEFAULT_ACTIVATION_NUMBER);
        assertThat(testInventoryItem.getActivationValidTrue()).isEqualTo(DEFAULT_ACTIVATION_VALID_TRUE);
        assertThat(testInventoryItem.getUnitCost()).isEqualTo(DEFAULT_UNIT_COST);
    }

    @Test
    @Transactional
    public void createInventoryItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryItemRepository.findAll().size();

        // Create the InventoryItem with an existing ID
        inventoryItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryItemMockMvc.perform(post("/api/inventory-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryItem)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryItem in the database
        List<InventoryItem> inventoryItemList = inventoryItemRepository.findAll();
        assertThat(inventoryItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInventoryItems() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList
        restInventoryItemMockMvc.perform(get("/api/inventory-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].receivedDate").value(hasItem(sameInstant(DEFAULT_RECEIVED_DATE))))
            .andExpect(jsonPath("$.[*].manufacturedDate").value(hasItem(sameInstant(DEFAULT_MANUFACTURED_DATE))))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(sameInstant(DEFAULT_EXPIRY_DATE))))
            .andExpect(jsonPath("$.[*].retestDate").value(hasItem(sameInstant(DEFAULT_RETEST_DATE))))
            .andExpect(jsonPath("$.[*].containerId").value(hasItem(DEFAULT_CONTAINER_ID)))
            .andExpect(jsonPath("$.[*].batchNo").value(hasItem(DEFAULT_BATCH_NO)))
            .andExpect(jsonPath("$.[*].mfgBatchNo").value(hasItem(DEFAULT_MFG_BATCH_NO)))
            .andExpect(jsonPath("$.[*].lotNoStr").value(hasItem(DEFAULT_LOT_NO_STR)))
            .andExpect(jsonPath("$.[*].binNumber").value(hasItem(DEFAULT_BIN_NUMBER)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].quantityOnHandTotal").value(hasItem(DEFAULT_QUANTITY_ON_HAND_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].availableToPromiseTotal").value(hasItem(DEFAULT_AVAILABLE_TO_PROMISE_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].accountingQuantityTotal").value(hasItem(DEFAULT_ACCOUNTING_QUANTITY_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].oldQuantityOnHand").value(hasItem(DEFAULT_OLD_QUANTITY_ON_HAND.intValue())))
            .andExpect(jsonPath("$.[*].oldAvailableToPromise").value(hasItem(DEFAULT_OLD_AVAILABLE_TO_PROMISE.intValue())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].softIdentifier").value(hasItem(DEFAULT_SOFT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].activationNumber").value(hasItem(DEFAULT_ACTIVATION_NUMBER)))
            .andExpect(jsonPath("$.[*].activationValidTrue").value(hasItem(sameInstant(DEFAULT_ACTIVATION_VALID_TRUE))))
            .andExpect(jsonPath("$.[*].unitCost").value(hasItem(DEFAULT_UNIT_COST.intValue())));
    }
    
    @Test
    @Transactional
    public void getInventoryItem() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get the inventoryItem
        restInventoryItemMockMvc.perform(get("/api/inventory-items/{id}", inventoryItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryItem.getId().intValue()))
            .andExpect(jsonPath("$.receivedDate").value(sameInstant(DEFAULT_RECEIVED_DATE)))
            .andExpect(jsonPath("$.manufacturedDate").value(sameInstant(DEFAULT_MANUFACTURED_DATE)))
            .andExpect(jsonPath("$.expiryDate").value(sameInstant(DEFAULT_EXPIRY_DATE)))
            .andExpect(jsonPath("$.retestDate").value(sameInstant(DEFAULT_RETEST_DATE)))
            .andExpect(jsonPath("$.containerId").value(DEFAULT_CONTAINER_ID))
            .andExpect(jsonPath("$.batchNo").value(DEFAULT_BATCH_NO))
            .andExpect(jsonPath("$.mfgBatchNo").value(DEFAULT_MFG_BATCH_NO))
            .andExpect(jsonPath("$.lotNoStr").value(DEFAULT_LOT_NO_STR))
            .andExpect(jsonPath("$.binNumber").value(DEFAULT_BIN_NUMBER))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.quantityOnHandTotal").value(DEFAULT_QUANTITY_ON_HAND_TOTAL.intValue()))
            .andExpect(jsonPath("$.availableToPromiseTotal").value(DEFAULT_AVAILABLE_TO_PROMISE_TOTAL.intValue()))
            .andExpect(jsonPath("$.accountingQuantityTotal").value(DEFAULT_ACCOUNTING_QUANTITY_TOTAL.intValue()))
            .andExpect(jsonPath("$.oldQuantityOnHand").value(DEFAULT_OLD_QUANTITY_ON_HAND.intValue()))
            .andExpect(jsonPath("$.oldAvailableToPromise").value(DEFAULT_OLD_AVAILABLE_TO_PROMISE.intValue()))
            .andExpect(jsonPath("$.serialNumber").value(DEFAULT_SERIAL_NUMBER))
            .andExpect(jsonPath("$.softIdentifier").value(DEFAULT_SOFT_IDENTIFIER))
            .andExpect(jsonPath("$.activationNumber").value(DEFAULT_ACTIVATION_NUMBER))
            .andExpect(jsonPath("$.activationValidTrue").value(sameInstant(DEFAULT_ACTIVATION_VALID_TRUE)))
            .andExpect(jsonPath("$.unitCost").value(DEFAULT_UNIT_COST.intValue()));
    }


    @Test
    @Transactional
    public void getInventoryItemsByIdFiltering() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        Long id = inventoryItem.getId();

        defaultInventoryItemShouldBeFound("id.equals=" + id);
        defaultInventoryItemShouldNotBeFound("id.notEquals=" + id);

        defaultInventoryItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInventoryItemShouldNotBeFound("id.greaterThan=" + id);

        defaultInventoryItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInventoryItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByReceivedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where receivedDate equals to DEFAULT_RECEIVED_DATE
        defaultInventoryItemShouldBeFound("receivedDate.equals=" + DEFAULT_RECEIVED_DATE);

        // Get all the inventoryItemList where receivedDate equals to UPDATED_RECEIVED_DATE
        defaultInventoryItemShouldNotBeFound("receivedDate.equals=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByReceivedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where receivedDate not equals to DEFAULT_RECEIVED_DATE
        defaultInventoryItemShouldNotBeFound("receivedDate.notEquals=" + DEFAULT_RECEIVED_DATE);

        // Get all the inventoryItemList where receivedDate not equals to UPDATED_RECEIVED_DATE
        defaultInventoryItemShouldBeFound("receivedDate.notEquals=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByReceivedDateIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where receivedDate in DEFAULT_RECEIVED_DATE or UPDATED_RECEIVED_DATE
        defaultInventoryItemShouldBeFound("receivedDate.in=" + DEFAULT_RECEIVED_DATE + "," + UPDATED_RECEIVED_DATE);

        // Get all the inventoryItemList where receivedDate equals to UPDATED_RECEIVED_DATE
        defaultInventoryItemShouldNotBeFound("receivedDate.in=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByReceivedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where receivedDate is not null
        defaultInventoryItemShouldBeFound("receivedDate.specified=true");

        // Get all the inventoryItemList where receivedDate is null
        defaultInventoryItemShouldNotBeFound("receivedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByReceivedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where receivedDate is greater than or equal to DEFAULT_RECEIVED_DATE
        defaultInventoryItemShouldBeFound("receivedDate.greaterThanOrEqual=" + DEFAULT_RECEIVED_DATE);

        // Get all the inventoryItemList where receivedDate is greater than or equal to UPDATED_RECEIVED_DATE
        defaultInventoryItemShouldNotBeFound("receivedDate.greaterThanOrEqual=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByReceivedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where receivedDate is less than or equal to DEFAULT_RECEIVED_DATE
        defaultInventoryItemShouldBeFound("receivedDate.lessThanOrEqual=" + DEFAULT_RECEIVED_DATE);

        // Get all the inventoryItemList where receivedDate is less than or equal to SMALLER_RECEIVED_DATE
        defaultInventoryItemShouldNotBeFound("receivedDate.lessThanOrEqual=" + SMALLER_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByReceivedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where receivedDate is less than DEFAULT_RECEIVED_DATE
        defaultInventoryItemShouldNotBeFound("receivedDate.lessThan=" + DEFAULT_RECEIVED_DATE);

        // Get all the inventoryItemList where receivedDate is less than UPDATED_RECEIVED_DATE
        defaultInventoryItemShouldBeFound("receivedDate.lessThan=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByReceivedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where receivedDate is greater than DEFAULT_RECEIVED_DATE
        defaultInventoryItemShouldNotBeFound("receivedDate.greaterThan=" + DEFAULT_RECEIVED_DATE);

        // Get all the inventoryItemList where receivedDate is greater than SMALLER_RECEIVED_DATE
        defaultInventoryItemShouldBeFound("receivedDate.greaterThan=" + SMALLER_RECEIVED_DATE);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByManufacturedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where manufacturedDate equals to DEFAULT_MANUFACTURED_DATE
        defaultInventoryItemShouldBeFound("manufacturedDate.equals=" + DEFAULT_MANUFACTURED_DATE);

        // Get all the inventoryItemList where manufacturedDate equals to UPDATED_MANUFACTURED_DATE
        defaultInventoryItemShouldNotBeFound("manufacturedDate.equals=" + UPDATED_MANUFACTURED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByManufacturedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where manufacturedDate not equals to DEFAULT_MANUFACTURED_DATE
        defaultInventoryItemShouldNotBeFound("manufacturedDate.notEquals=" + DEFAULT_MANUFACTURED_DATE);

        // Get all the inventoryItemList where manufacturedDate not equals to UPDATED_MANUFACTURED_DATE
        defaultInventoryItemShouldBeFound("manufacturedDate.notEquals=" + UPDATED_MANUFACTURED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByManufacturedDateIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where manufacturedDate in DEFAULT_MANUFACTURED_DATE or UPDATED_MANUFACTURED_DATE
        defaultInventoryItemShouldBeFound("manufacturedDate.in=" + DEFAULT_MANUFACTURED_DATE + "," + UPDATED_MANUFACTURED_DATE);

        // Get all the inventoryItemList where manufacturedDate equals to UPDATED_MANUFACTURED_DATE
        defaultInventoryItemShouldNotBeFound("manufacturedDate.in=" + UPDATED_MANUFACTURED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByManufacturedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where manufacturedDate is not null
        defaultInventoryItemShouldBeFound("manufacturedDate.specified=true");

        // Get all the inventoryItemList where manufacturedDate is null
        defaultInventoryItemShouldNotBeFound("manufacturedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByManufacturedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where manufacturedDate is greater than or equal to DEFAULT_MANUFACTURED_DATE
        defaultInventoryItemShouldBeFound("manufacturedDate.greaterThanOrEqual=" + DEFAULT_MANUFACTURED_DATE);

        // Get all the inventoryItemList where manufacturedDate is greater than or equal to UPDATED_MANUFACTURED_DATE
        defaultInventoryItemShouldNotBeFound("manufacturedDate.greaterThanOrEqual=" + UPDATED_MANUFACTURED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByManufacturedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where manufacturedDate is less than or equal to DEFAULT_MANUFACTURED_DATE
        defaultInventoryItemShouldBeFound("manufacturedDate.lessThanOrEqual=" + DEFAULT_MANUFACTURED_DATE);

        // Get all the inventoryItemList where manufacturedDate is less than or equal to SMALLER_MANUFACTURED_DATE
        defaultInventoryItemShouldNotBeFound("manufacturedDate.lessThanOrEqual=" + SMALLER_MANUFACTURED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByManufacturedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where manufacturedDate is less than DEFAULT_MANUFACTURED_DATE
        defaultInventoryItemShouldNotBeFound("manufacturedDate.lessThan=" + DEFAULT_MANUFACTURED_DATE);

        // Get all the inventoryItemList where manufacturedDate is less than UPDATED_MANUFACTURED_DATE
        defaultInventoryItemShouldBeFound("manufacturedDate.lessThan=" + UPDATED_MANUFACTURED_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByManufacturedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where manufacturedDate is greater than DEFAULT_MANUFACTURED_DATE
        defaultInventoryItemShouldNotBeFound("manufacturedDate.greaterThan=" + DEFAULT_MANUFACTURED_DATE);

        // Get all the inventoryItemList where manufacturedDate is greater than SMALLER_MANUFACTURED_DATE
        defaultInventoryItemShouldBeFound("manufacturedDate.greaterThan=" + SMALLER_MANUFACTURED_DATE);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByExpiryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where expiryDate equals to DEFAULT_EXPIRY_DATE
        defaultInventoryItemShouldBeFound("expiryDate.equals=" + DEFAULT_EXPIRY_DATE);

        // Get all the inventoryItemList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultInventoryItemShouldNotBeFound("expiryDate.equals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByExpiryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where expiryDate not equals to DEFAULT_EXPIRY_DATE
        defaultInventoryItemShouldNotBeFound("expiryDate.notEquals=" + DEFAULT_EXPIRY_DATE);

        // Get all the inventoryItemList where expiryDate not equals to UPDATED_EXPIRY_DATE
        defaultInventoryItemShouldBeFound("expiryDate.notEquals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByExpiryDateIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where expiryDate in DEFAULT_EXPIRY_DATE or UPDATED_EXPIRY_DATE
        defaultInventoryItemShouldBeFound("expiryDate.in=" + DEFAULT_EXPIRY_DATE + "," + UPDATED_EXPIRY_DATE);

        // Get all the inventoryItemList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultInventoryItemShouldNotBeFound("expiryDate.in=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByExpiryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where expiryDate is not null
        defaultInventoryItemShouldBeFound("expiryDate.specified=true");

        // Get all the inventoryItemList where expiryDate is null
        defaultInventoryItemShouldNotBeFound("expiryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByExpiryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where expiryDate is greater than or equal to DEFAULT_EXPIRY_DATE
        defaultInventoryItemShouldBeFound("expiryDate.greaterThanOrEqual=" + DEFAULT_EXPIRY_DATE);

        // Get all the inventoryItemList where expiryDate is greater than or equal to UPDATED_EXPIRY_DATE
        defaultInventoryItemShouldNotBeFound("expiryDate.greaterThanOrEqual=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByExpiryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where expiryDate is less than or equal to DEFAULT_EXPIRY_DATE
        defaultInventoryItemShouldBeFound("expiryDate.lessThanOrEqual=" + DEFAULT_EXPIRY_DATE);

        // Get all the inventoryItemList where expiryDate is less than or equal to SMALLER_EXPIRY_DATE
        defaultInventoryItemShouldNotBeFound("expiryDate.lessThanOrEqual=" + SMALLER_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByExpiryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where expiryDate is less than DEFAULT_EXPIRY_DATE
        defaultInventoryItemShouldNotBeFound("expiryDate.lessThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the inventoryItemList where expiryDate is less than UPDATED_EXPIRY_DATE
        defaultInventoryItemShouldBeFound("expiryDate.lessThan=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByExpiryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where expiryDate is greater than DEFAULT_EXPIRY_DATE
        defaultInventoryItemShouldNotBeFound("expiryDate.greaterThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the inventoryItemList where expiryDate is greater than SMALLER_EXPIRY_DATE
        defaultInventoryItemShouldBeFound("expiryDate.greaterThan=" + SMALLER_EXPIRY_DATE);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByRetestDateIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where retestDate equals to DEFAULT_RETEST_DATE
        defaultInventoryItemShouldBeFound("retestDate.equals=" + DEFAULT_RETEST_DATE);

        // Get all the inventoryItemList where retestDate equals to UPDATED_RETEST_DATE
        defaultInventoryItemShouldNotBeFound("retestDate.equals=" + UPDATED_RETEST_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByRetestDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where retestDate not equals to DEFAULT_RETEST_DATE
        defaultInventoryItemShouldNotBeFound("retestDate.notEquals=" + DEFAULT_RETEST_DATE);

        // Get all the inventoryItemList where retestDate not equals to UPDATED_RETEST_DATE
        defaultInventoryItemShouldBeFound("retestDate.notEquals=" + UPDATED_RETEST_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByRetestDateIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where retestDate in DEFAULT_RETEST_DATE or UPDATED_RETEST_DATE
        defaultInventoryItemShouldBeFound("retestDate.in=" + DEFAULT_RETEST_DATE + "," + UPDATED_RETEST_DATE);

        // Get all the inventoryItemList where retestDate equals to UPDATED_RETEST_DATE
        defaultInventoryItemShouldNotBeFound("retestDate.in=" + UPDATED_RETEST_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByRetestDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where retestDate is not null
        defaultInventoryItemShouldBeFound("retestDate.specified=true");

        // Get all the inventoryItemList where retestDate is null
        defaultInventoryItemShouldNotBeFound("retestDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByRetestDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where retestDate is greater than or equal to DEFAULT_RETEST_DATE
        defaultInventoryItemShouldBeFound("retestDate.greaterThanOrEqual=" + DEFAULT_RETEST_DATE);

        // Get all the inventoryItemList where retestDate is greater than or equal to UPDATED_RETEST_DATE
        defaultInventoryItemShouldNotBeFound("retestDate.greaterThanOrEqual=" + UPDATED_RETEST_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByRetestDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where retestDate is less than or equal to DEFAULT_RETEST_DATE
        defaultInventoryItemShouldBeFound("retestDate.lessThanOrEqual=" + DEFAULT_RETEST_DATE);

        // Get all the inventoryItemList where retestDate is less than or equal to SMALLER_RETEST_DATE
        defaultInventoryItemShouldNotBeFound("retestDate.lessThanOrEqual=" + SMALLER_RETEST_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByRetestDateIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where retestDate is less than DEFAULT_RETEST_DATE
        defaultInventoryItemShouldNotBeFound("retestDate.lessThan=" + DEFAULT_RETEST_DATE);

        // Get all the inventoryItemList where retestDate is less than UPDATED_RETEST_DATE
        defaultInventoryItemShouldBeFound("retestDate.lessThan=" + UPDATED_RETEST_DATE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByRetestDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where retestDate is greater than DEFAULT_RETEST_DATE
        defaultInventoryItemShouldNotBeFound("retestDate.greaterThan=" + DEFAULT_RETEST_DATE);

        // Get all the inventoryItemList where retestDate is greater than SMALLER_RETEST_DATE
        defaultInventoryItemShouldBeFound("retestDate.greaterThan=" + SMALLER_RETEST_DATE);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByContainerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where containerId equals to DEFAULT_CONTAINER_ID
        defaultInventoryItemShouldBeFound("containerId.equals=" + DEFAULT_CONTAINER_ID);

        // Get all the inventoryItemList where containerId equals to UPDATED_CONTAINER_ID
        defaultInventoryItemShouldNotBeFound("containerId.equals=" + UPDATED_CONTAINER_ID);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByContainerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where containerId not equals to DEFAULT_CONTAINER_ID
        defaultInventoryItemShouldNotBeFound("containerId.notEquals=" + DEFAULT_CONTAINER_ID);

        // Get all the inventoryItemList where containerId not equals to UPDATED_CONTAINER_ID
        defaultInventoryItemShouldBeFound("containerId.notEquals=" + UPDATED_CONTAINER_ID);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByContainerIdIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where containerId in DEFAULT_CONTAINER_ID or UPDATED_CONTAINER_ID
        defaultInventoryItemShouldBeFound("containerId.in=" + DEFAULT_CONTAINER_ID + "," + UPDATED_CONTAINER_ID);

        // Get all the inventoryItemList where containerId equals to UPDATED_CONTAINER_ID
        defaultInventoryItemShouldNotBeFound("containerId.in=" + UPDATED_CONTAINER_ID);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByContainerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where containerId is not null
        defaultInventoryItemShouldBeFound("containerId.specified=true");

        // Get all the inventoryItemList where containerId is null
        defaultInventoryItemShouldNotBeFound("containerId.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoryItemsByContainerIdContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where containerId contains DEFAULT_CONTAINER_ID
        defaultInventoryItemShouldBeFound("containerId.contains=" + DEFAULT_CONTAINER_ID);

        // Get all the inventoryItemList where containerId contains UPDATED_CONTAINER_ID
        defaultInventoryItemShouldNotBeFound("containerId.contains=" + UPDATED_CONTAINER_ID);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByContainerIdNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where containerId does not contain DEFAULT_CONTAINER_ID
        defaultInventoryItemShouldNotBeFound("containerId.doesNotContain=" + DEFAULT_CONTAINER_ID);

        // Get all the inventoryItemList where containerId does not contain UPDATED_CONTAINER_ID
        defaultInventoryItemShouldBeFound("containerId.doesNotContain=" + UPDATED_CONTAINER_ID);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByBatchNoIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where batchNo equals to DEFAULT_BATCH_NO
        defaultInventoryItemShouldBeFound("batchNo.equals=" + DEFAULT_BATCH_NO);

        // Get all the inventoryItemList where batchNo equals to UPDATED_BATCH_NO
        defaultInventoryItemShouldNotBeFound("batchNo.equals=" + UPDATED_BATCH_NO);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByBatchNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where batchNo not equals to DEFAULT_BATCH_NO
        defaultInventoryItemShouldNotBeFound("batchNo.notEquals=" + DEFAULT_BATCH_NO);

        // Get all the inventoryItemList where batchNo not equals to UPDATED_BATCH_NO
        defaultInventoryItemShouldBeFound("batchNo.notEquals=" + UPDATED_BATCH_NO);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByBatchNoIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where batchNo in DEFAULT_BATCH_NO or UPDATED_BATCH_NO
        defaultInventoryItemShouldBeFound("batchNo.in=" + DEFAULT_BATCH_NO + "," + UPDATED_BATCH_NO);

        // Get all the inventoryItemList where batchNo equals to UPDATED_BATCH_NO
        defaultInventoryItemShouldNotBeFound("batchNo.in=" + UPDATED_BATCH_NO);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByBatchNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where batchNo is not null
        defaultInventoryItemShouldBeFound("batchNo.specified=true");

        // Get all the inventoryItemList where batchNo is null
        defaultInventoryItemShouldNotBeFound("batchNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoryItemsByBatchNoContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where batchNo contains DEFAULT_BATCH_NO
        defaultInventoryItemShouldBeFound("batchNo.contains=" + DEFAULT_BATCH_NO);

        // Get all the inventoryItemList where batchNo contains UPDATED_BATCH_NO
        defaultInventoryItemShouldNotBeFound("batchNo.contains=" + UPDATED_BATCH_NO);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByBatchNoNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where batchNo does not contain DEFAULT_BATCH_NO
        defaultInventoryItemShouldNotBeFound("batchNo.doesNotContain=" + DEFAULT_BATCH_NO);

        // Get all the inventoryItemList where batchNo does not contain UPDATED_BATCH_NO
        defaultInventoryItemShouldBeFound("batchNo.doesNotContain=" + UPDATED_BATCH_NO);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByMfgBatchNoIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where mfgBatchNo equals to DEFAULT_MFG_BATCH_NO
        defaultInventoryItemShouldBeFound("mfgBatchNo.equals=" + DEFAULT_MFG_BATCH_NO);

        // Get all the inventoryItemList where mfgBatchNo equals to UPDATED_MFG_BATCH_NO
        defaultInventoryItemShouldNotBeFound("mfgBatchNo.equals=" + UPDATED_MFG_BATCH_NO);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByMfgBatchNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where mfgBatchNo not equals to DEFAULT_MFG_BATCH_NO
        defaultInventoryItemShouldNotBeFound("mfgBatchNo.notEquals=" + DEFAULT_MFG_BATCH_NO);

        // Get all the inventoryItemList where mfgBatchNo not equals to UPDATED_MFG_BATCH_NO
        defaultInventoryItemShouldBeFound("mfgBatchNo.notEquals=" + UPDATED_MFG_BATCH_NO);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByMfgBatchNoIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where mfgBatchNo in DEFAULT_MFG_BATCH_NO or UPDATED_MFG_BATCH_NO
        defaultInventoryItemShouldBeFound("mfgBatchNo.in=" + DEFAULT_MFG_BATCH_NO + "," + UPDATED_MFG_BATCH_NO);

        // Get all the inventoryItemList where mfgBatchNo equals to UPDATED_MFG_BATCH_NO
        defaultInventoryItemShouldNotBeFound("mfgBatchNo.in=" + UPDATED_MFG_BATCH_NO);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByMfgBatchNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where mfgBatchNo is not null
        defaultInventoryItemShouldBeFound("mfgBatchNo.specified=true");

        // Get all the inventoryItemList where mfgBatchNo is null
        defaultInventoryItemShouldNotBeFound("mfgBatchNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoryItemsByMfgBatchNoContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where mfgBatchNo contains DEFAULT_MFG_BATCH_NO
        defaultInventoryItemShouldBeFound("mfgBatchNo.contains=" + DEFAULT_MFG_BATCH_NO);

        // Get all the inventoryItemList where mfgBatchNo contains UPDATED_MFG_BATCH_NO
        defaultInventoryItemShouldNotBeFound("mfgBatchNo.contains=" + UPDATED_MFG_BATCH_NO);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByMfgBatchNoNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where mfgBatchNo does not contain DEFAULT_MFG_BATCH_NO
        defaultInventoryItemShouldNotBeFound("mfgBatchNo.doesNotContain=" + DEFAULT_MFG_BATCH_NO);

        // Get all the inventoryItemList where mfgBatchNo does not contain UPDATED_MFG_BATCH_NO
        defaultInventoryItemShouldBeFound("mfgBatchNo.doesNotContain=" + UPDATED_MFG_BATCH_NO);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByLotNoStrIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where lotNoStr equals to DEFAULT_LOT_NO_STR
        defaultInventoryItemShouldBeFound("lotNoStr.equals=" + DEFAULT_LOT_NO_STR);

        // Get all the inventoryItemList where lotNoStr equals to UPDATED_LOT_NO_STR
        defaultInventoryItemShouldNotBeFound("lotNoStr.equals=" + UPDATED_LOT_NO_STR);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByLotNoStrIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where lotNoStr not equals to DEFAULT_LOT_NO_STR
        defaultInventoryItemShouldNotBeFound("lotNoStr.notEquals=" + DEFAULT_LOT_NO_STR);

        // Get all the inventoryItemList where lotNoStr not equals to UPDATED_LOT_NO_STR
        defaultInventoryItemShouldBeFound("lotNoStr.notEquals=" + UPDATED_LOT_NO_STR);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByLotNoStrIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where lotNoStr in DEFAULT_LOT_NO_STR or UPDATED_LOT_NO_STR
        defaultInventoryItemShouldBeFound("lotNoStr.in=" + DEFAULT_LOT_NO_STR + "," + UPDATED_LOT_NO_STR);

        // Get all the inventoryItemList where lotNoStr equals to UPDATED_LOT_NO_STR
        defaultInventoryItemShouldNotBeFound("lotNoStr.in=" + UPDATED_LOT_NO_STR);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByLotNoStrIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where lotNoStr is not null
        defaultInventoryItemShouldBeFound("lotNoStr.specified=true");

        // Get all the inventoryItemList where lotNoStr is null
        defaultInventoryItemShouldNotBeFound("lotNoStr.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoryItemsByLotNoStrContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where lotNoStr contains DEFAULT_LOT_NO_STR
        defaultInventoryItemShouldBeFound("lotNoStr.contains=" + DEFAULT_LOT_NO_STR);

        // Get all the inventoryItemList where lotNoStr contains UPDATED_LOT_NO_STR
        defaultInventoryItemShouldNotBeFound("lotNoStr.contains=" + UPDATED_LOT_NO_STR);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByLotNoStrNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where lotNoStr does not contain DEFAULT_LOT_NO_STR
        defaultInventoryItemShouldNotBeFound("lotNoStr.doesNotContain=" + DEFAULT_LOT_NO_STR);

        // Get all the inventoryItemList where lotNoStr does not contain UPDATED_LOT_NO_STR
        defaultInventoryItemShouldBeFound("lotNoStr.doesNotContain=" + UPDATED_LOT_NO_STR);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByBinNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where binNumber equals to DEFAULT_BIN_NUMBER
        defaultInventoryItemShouldBeFound("binNumber.equals=" + DEFAULT_BIN_NUMBER);

        // Get all the inventoryItemList where binNumber equals to UPDATED_BIN_NUMBER
        defaultInventoryItemShouldNotBeFound("binNumber.equals=" + UPDATED_BIN_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByBinNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where binNumber not equals to DEFAULT_BIN_NUMBER
        defaultInventoryItemShouldNotBeFound("binNumber.notEquals=" + DEFAULT_BIN_NUMBER);

        // Get all the inventoryItemList where binNumber not equals to UPDATED_BIN_NUMBER
        defaultInventoryItemShouldBeFound("binNumber.notEquals=" + UPDATED_BIN_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByBinNumberIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where binNumber in DEFAULT_BIN_NUMBER or UPDATED_BIN_NUMBER
        defaultInventoryItemShouldBeFound("binNumber.in=" + DEFAULT_BIN_NUMBER + "," + UPDATED_BIN_NUMBER);

        // Get all the inventoryItemList where binNumber equals to UPDATED_BIN_NUMBER
        defaultInventoryItemShouldNotBeFound("binNumber.in=" + UPDATED_BIN_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByBinNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where binNumber is not null
        defaultInventoryItemShouldBeFound("binNumber.specified=true");

        // Get all the inventoryItemList where binNumber is null
        defaultInventoryItemShouldNotBeFound("binNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoryItemsByBinNumberContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where binNumber contains DEFAULT_BIN_NUMBER
        defaultInventoryItemShouldBeFound("binNumber.contains=" + DEFAULT_BIN_NUMBER);

        // Get all the inventoryItemList where binNumber contains UPDATED_BIN_NUMBER
        defaultInventoryItemShouldNotBeFound("binNumber.contains=" + UPDATED_BIN_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByBinNumberNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where binNumber does not contain DEFAULT_BIN_NUMBER
        defaultInventoryItemShouldNotBeFound("binNumber.doesNotContain=" + DEFAULT_BIN_NUMBER);

        // Get all the inventoryItemList where binNumber does not contain UPDATED_BIN_NUMBER
        defaultInventoryItemShouldBeFound("binNumber.doesNotContain=" + UPDATED_BIN_NUMBER);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where comments equals to DEFAULT_COMMENTS
        defaultInventoryItemShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the inventoryItemList where comments equals to UPDATED_COMMENTS
        defaultInventoryItemShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where comments not equals to DEFAULT_COMMENTS
        defaultInventoryItemShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the inventoryItemList where comments not equals to UPDATED_COMMENTS
        defaultInventoryItemShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultInventoryItemShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the inventoryItemList where comments equals to UPDATED_COMMENTS
        defaultInventoryItemShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where comments is not null
        defaultInventoryItemShouldBeFound("comments.specified=true");

        // Get all the inventoryItemList where comments is null
        defaultInventoryItemShouldNotBeFound("comments.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoryItemsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where comments contains DEFAULT_COMMENTS
        defaultInventoryItemShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the inventoryItemList where comments contains UPDATED_COMMENTS
        defaultInventoryItemShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where comments does not contain DEFAULT_COMMENTS
        defaultInventoryItemShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the inventoryItemList where comments does not contain UPDATED_COMMENTS
        defaultInventoryItemShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByQuantityOnHandTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where quantityOnHandTotal equals to DEFAULT_QUANTITY_ON_HAND_TOTAL
        defaultInventoryItemShouldBeFound("quantityOnHandTotal.equals=" + DEFAULT_QUANTITY_ON_HAND_TOTAL);

        // Get all the inventoryItemList where quantityOnHandTotal equals to UPDATED_QUANTITY_ON_HAND_TOTAL
        defaultInventoryItemShouldNotBeFound("quantityOnHandTotal.equals=" + UPDATED_QUANTITY_ON_HAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByQuantityOnHandTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where quantityOnHandTotal not equals to DEFAULT_QUANTITY_ON_HAND_TOTAL
        defaultInventoryItemShouldNotBeFound("quantityOnHandTotal.notEquals=" + DEFAULT_QUANTITY_ON_HAND_TOTAL);

        // Get all the inventoryItemList where quantityOnHandTotal not equals to UPDATED_QUANTITY_ON_HAND_TOTAL
        defaultInventoryItemShouldBeFound("quantityOnHandTotal.notEquals=" + UPDATED_QUANTITY_ON_HAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByQuantityOnHandTotalIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where quantityOnHandTotal in DEFAULT_QUANTITY_ON_HAND_TOTAL or UPDATED_QUANTITY_ON_HAND_TOTAL
        defaultInventoryItemShouldBeFound("quantityOnHandTotal.in=" + DEFAULT_QUANTITY_ON_HAND_TOTAL + "," + UPDATED_QUANTITY_ON_HAND_TOTAL);

        // Get all the inventoryItemList where quantityOnHandTotal equals to UPDATED_QUANTITY_ON_HAND_TOTAL
        defaultInventoryItemShouldNotBeFound("quantityOnHandTotal.in=" + UPDATED_QUANTITY_ON_HAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByQuantityOnHandTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where quantityOnHandTotal is not null
        defaultInventoryItemShouldBeFound("quantityOnHandTotal.specified=true");

        // Get all the inventoryItemList where quantityOnHandTotal is null
        defaultInventoryItemShouldNotBeFound("quantityOnHandTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByQuantityOnHandTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where quantityOnHandTotal is greater than or equal to DEFAULT_QUANTITY_ON_HAND_TOTAL
        defaultInventoryItemShouldBeFound("quantityOnHandTotal.greaterThanOrEqual=" + DEFAULT_QUANTITY_ON_HAND_TOTAL);

        // Get all the inventoryItemList where quantityOnHandTotal is greater than or equal to UPDATED_QUANTITY_ON_HAND_TOTAL
        defaultInventoryItemShouldNotBeFound("quantityOnHandTotal.greaterThanOrEqual=" + UPDATED_QUANTITY_ON_HAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByQuantityOnHandTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where quantityOnHandTotal is less than or equal to DEFAULT_QUANTITY_ON_HAND_TOTAL
        defaultInventoryItemShouldBeFound("quantityOnHandTotal.lessThanOrEqual=" + DEFAULT_QUANTITY_ON_HAND_TOTAL);

        // Get all the inventoryItemList where quantityOnHandTotal is less than or equal to SMALLER_QUANTITY_ON_HAND_TOTAL
        defaultInventoryItemShouldNotBeFound("quantityOnHandTotal.lessThanOrEqual=" + SMALLER_QUANTITY_ON_HAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByQuantityOnHandTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where quantityOnHandTotal is less than DEFAULT_QUANTITY_ON_HAND_TOTAL
        defaultInventoryItemShouldNotBeFound("quantityOnHandTotal.lessThan=" + DEFAULT_QUANTITY_ON_HAND_TOTAL);

        // Get all the inventoryItemList where quantityOnHandTotal is less than UPDATED_QUANTITY_ON_HAND_TOTAL
        defaultInventoryItemShouldBeFound("quantityOnHandTotal.lessThan=" + UPDATED_QUANTITY_ON_HAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByQuantityOnHandTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where quantityOnHandTotal is greater than DEFAULT_QUANTITY_ON_HAND_TOTAL
        defaultInventoryItemShouldNotBeFound("quantityOnHandTotal.greaterThan=" + DEFAULT_QUANTITY_ON_HAND_TOTAL);

        // Get all the inventoryItemList where quantityOnHandTotal is greater than SMALLER_QUANTITY_ON_HAND_TOTAL
        defaultInventoryItemShouldBeFound("quantityOnHandTotal.greaterThan=" + SMALLER_QUANTITY_ON_HAND_TOTAL);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByAvailableToPromiseTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where availableToPromiseTotal equals to DEFAULT_AVAILABLE_TO_PROMISE_TOTAL
        defaultInventoryItemShouldBeFound("availableToPromiseTotal.equals=" + DEFAULT_AVAILABLE_TO_PROMISE_TOTAL);

        // Get all the inventoryItemList where availableToPromiseTotal equals to UPDATED_AVAILABLE_TO_PROMISE_TOTAL
        defaultInventoryItemShouldNotBeFound("availableToPromiseTotal.equals=" + UPDATED_AVAILABLE_TO_PROMISE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByAvailableToPromiseTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where availableToPromiseTotal not equals to DEFAULT_AVAILABLE_TO_PROMISE_TOTAL
        defaultInventoryItemShouldNotBeFound("availableToPromiseTotal.notEquals=" + DEFAULT_AVAILABLE_TO_PROMISE_TOTAL);

        // Get all the inventoryItemList where availableToPromiseTotal not equals to UPDATED_AVAILABLE_TO_PROMISE_TOTAL
        defaultInventoryItemShouldBeFound("availableToPromiseTotal.notEquals=" + UPDATED_AVAILABLE_TO_PROMISE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByAvailableToPromiseTotalIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where availableToPromiseTotal in DEFAULT_AVAILABLE_TO_PROMISE_TOTAL or UPDATED_AVAILABLE_TO_PROMISE_TOTAL
        defaultInventoryItemShouldBeFound("availableToPromiseTotal.in=" + DEFAULT_AVAILABLE_TO_PROMISE_TOTAL + "," + UPDATED_AVAILABLE_TO_PROMISE_TOTAL);

        // Get all the inventoryItemList where availableToPromiseTotal equals to UPDATED_AVAILABLE_TO_PROMISE_TOTAL
        defaultInventoryItemShouldNotBeFound("availableToPromiseTotal.in=" + UPDATED_AVAILABLE_TO_PROMISE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByAvailableToPromiseTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where availableToPromiseTotal is not null
        defaultInventoryItemShouldBeFound("availableToPromiseTotal.specified=true");

        // Get all the inventoryItemList where availableToPromiseTotal is null
        defaultInventoryItemShouldNotBeFound("availableToPromiseTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByAvailableToPromiseTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where availableToPromiseTotal is greater than or equal to DEFAULT_AVAILABLE_TO_PROMISE_TOTAL
        defaultInventoryItemShouldBeFound("availableToPromiseTotal.greaterThanOrEqual=" + DEFAULT_AVAILABLE_TO_PROMISE_TOTAL);

        // Get all the inventoryItemList where availableToPromiseTotal is greater than or equal to UPDATED_AVAILABLE_TO_PROMISE_TOTAL
        defaultInventoryItemShouldNotBeFound("availableToPromiseTotal.greaterThanOrEqual=" + UPDATED_AVAILABLE_TO_PROMISE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByAvailableToPromiseTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where availableToPromiseTotal is less than or equal to DEFAULT_AVAILABLE_TO_PROMISE_TOTAL
        defaultInventoryItemShouldBeFound("availableToPromiseTotal.lessThanOrEqual=" + DEFAULT_AVAILABLE_TO_PROMISE_TOTAL);

        // Get all the inventoryItemList where availableToPromiseTotal is less than or equal to SMALLER_AVAILABLE_TO_PROMISE_TOTAL
        defaultInventoryItemShouldNotBeFound("availableToPromiseTotal.lessThanOrEqual=" + SMALLER_AVAILABLE_TO_PROMISE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByAvailableToPromiseTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where availableToPromiseTotal is less than DEFAULT_AVAILABLE_TO_PROMISE_TOTAL
        defaultInventoryItemShouldNotBeFound("availableToPromiseTotal.lessThan=" + DEFAULT_AVAILABLE_TO_PROMISE_TOTAL);

        // Get all the inventoryItemList where availableToPromiseTotal is less than UPDATED_AVAILABLE_TO_PROMISE_TOTAL
        defaultInventoryItemShouldBeFound("availableToPromiseTotal.lessThan=" + UPDATED_AVAILABLE_TO_PROMISE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByAvailableToPromiseTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where availableToPromiseTotal is greater than DEFAULT_AVAILABLE_TO_PROMISE_TOTAL
        defaultInventoryItemShouldNotBeFound("availableToPromiseTotal.greaterThan=" + DEFAULT_AVAILABLE_TO_PROMISE_TOTAL);

        // Get all the inventoryItemList where availableToPromiseTotal is greater than SMALLER_AVAILABLE_TO_PROMISE_TOTAL
        defaultInventoryItemShouldBeFound("availableToPromiseTotal.greaterThan=" + SMALLER_AVAILABLE_TO_PROMISE_TOTAL);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByAccountingQuantityTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where accountingQuantityTotal equals to DEFAULT_ACCOUNTING_QUANTITY_TOTAL
        defaultInventoryItemShouldBeFound("accountingQuantityTotal.equals=" + DEFAULT_ACCOUNTING_QUANTITY_TOTAL);

        // Get all the inventoryItemList where accountingQuantityTotal equals to UPDATED_ACCOUNTING_QUANTITY_TOTAL
        defaultInventoryItemShouldNotBeFound("accountingQuantityTotal.equals=" + UPDATED_ACCOUNTING_QUANTITY_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByAccountingQuantityTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where accountingQuantityTotal not equals to DEFAULT_ACCOUNTING_QUANTITY_TOTAL
        defaultInventoryItemShouldNotBeFound("accountingQuantityTotal.notEquals=" + DEFAULT_ACCOUNTING_QUANTITY_TOTAL);

        // Get all the inventoryItemList where accountingQuantityTotal not equals to UPDATED_ACCOUNTING_QUANTITY_TOTAL
        defaultInventoryItemShouldBeFound("accountingQuantityTotal.notEquals=" + UPDATED_ACCOUNTING_QUANTITY_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByAccountingQuantityTotalIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where accountingQuantityTotal in DEFAULT_ACCOUNTING_QUANTITY_TOTAL or UPDATED_ACCOUNTING_QUANTITY_TOTAL
        defaultInventoryItemShouldBeFound("accountingQuantityTotal.in=" + DEFAULT_ACCOUNTING_QUANTITY_TOTAL + "," + UPDATED_ACCOUNTING_QUANTITY_TOTAL);

        // Get all the inventoryItemList where accountingQuantityTotal equals to UPDATED_ACCOUNTING_QUANTITY_TOTAL
        defaultInventoryItemShouldNotBeFound("accountingQuantityTotal.in=" + UPDATED_ACCOUNTING_QUANTITY_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByAccountingQuantityTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where accountingQuantityTotal is not null
        defaultInventoryItemShouldBeFound("accountingQuantityTotal.specified=true");

        // Get all the inventoryItemList where accountingQuantityTotal is null
        defaultInventoryItemShouldNotBeFound("accountingQuantityTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByAccountingQuantityTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where accountingQuantityTotal is greater than or equal to DEFAULT_ACCOUNTING_QUANTITY_TOTAL
        defaultInventoryItemShouldBeFound("accountingQuantityTotal.greaterThanOrEqual=" + DEFAULT_ACCOUNTING_QUANTITY_TOTAL);

        // Get all the inventoryItemList where accountingQuantityTotal is greater than or equal to UPDATED_ACCOUNTING_QUANTITY_TOTAL
        defaultInventoryItemShouldNotBeFound("accountingQuantityTotal.greaterThanOrEqual=" + UPDATED_ACCOUNTING_QUANTITY_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByAccountingQuantityTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where accountingQuantityTotal is less than or equal to DEFAULT_ACCOUNTING_QUANTITY_TOTAL
        defaultInventoryItemShouldBeFound("accountingQuantityTotal.lessThanOrEqual=" + DEFAULT_ACCOUNTING_QUANTITY_TOTAL);

        // Get all the inventoryItemList where accountingQuantityTotal is less than or equal to SMALLER_ACCOUNTING_QUANTITY_TOTAL
        defaultInventoryItemShouldNotBeFound("accountingQuantityTotal.lessThanOrEqual=" + SMALLER_ACCOUNTING_QUANTITY_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByAccountingQuantityTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where accountingQuantityTotal is less than DEFAULT_ACCOUNTING_QUANTITY_TOTAL
        defaultInventoryItemShouldNotBeFound("accountingQuantityTotal.lessThan=" + DEFAULT_ACCOUNTING_QUANTITY_TOTAL);

        // Get all the inventoryItemList where accountingQuantityTotal is less than UPDATED_ACCOUNTING_QUANTITY_TOTAL
        defaultInventoryItemShouldBeFound("accountingQuantityTotal.lessThan=" + UPDATED_ACCOUNTING_QUANTITY_TOTAL);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByAccountingQuantityTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where accountingQuantityTotal is greater than DEFAULT_ACCOUNTING_QUANTITY_TOTAL
        defaultInventoryItemShouldNotBeFound("accountingQuantityTotal.greaterThan=" + DEFAULT_ACCOUNTING_QUANTITY_TOTAL);

        // Get all the inventoryItemList where accountingQuantityTotal is greater than SMALLER_ACCOUNTING_QUANTITY_TOTAL
        defaultInventoryItemShouldBeFound("accountingQuantityTotal.greaterThan=" + SMALLER_ACCOUNTING_QUANTITY_TOTAL);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByOldQuantityOnHandIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where oldQuantityOnHand equals to DEFAULT_OLD_QUANTITY_ON_HAND
        defaultInventoryItemShouldBeFound("oldQuantityOnHand.equals=" + DEFAULT_OLD_QUANTITY_ON_HAND);

        // Get all the inventoryItemList where oldQuantityOnHand equals to UPDATED_OLD_QUANTITY_ON_HAND
        defaultInventoryItemShouldNotBeFound("oldQuantityOnHand.equals=" + UPDATED_OLD_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByOldQuantityOnHandIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where oldQuantityOnHand not equals to DEFAULT_OLD_QUANTITY_ON_HAND
        defaultInventoryItemShouldNotBeFound("oldQuantityOnHand.notEquals=" + DEFAULT_OLD_QUANTITY_ON_HAND);

        // Get all the inventoryItemList where oldQuantityOnHand not equals to UPDATED_OLD_QUANTITY_ON_HAND
        defaultInventoryItemShouldBeFound("oldQuantityOnHand.notEquals=" + UPDATED_OLD_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByOldQuantityOnHandIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where oldQuantityOnHand in DEFAULT_OLD_QUANTITY_ON_HAND or UPDATED_OLD_QUANTITY_ON_HAND
        defaultInventoryItemShouldBeFound("oldQuantityOnHand.in=" + DEFAULT_OLD_QUANTITY_ON_HAND + "," + UPDATED_OLD_QUANTITY_ON_HAND);

        // Get all the inventoryItemList where oldQuantityOnHand equals to UPDATED_OLD_QUANTITY_ON_HAND
        defaultInventoryItemShouldNotBeFound("oldQuantityOnHand.in=" + UPDATED_OLD_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByOldQuantityOnHandIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where oldQuantityOnHand is not null
        defaultInventoryItemShouldBeFound("oldQuantityOnHand.specified=true");

        // Get all the inventoryItemList where oldQuantityOnHand is null
        defaultInventoryItemShouldNotBeFound("oldQuantityOnHand.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByOldQuantityOnHandIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where oldQuantityOnHand is greater than or equal to DEFAULT_OLD_QUANTITY_ON_HAND
        defaultInventoryItemShouldBeFound("oldQuantityOnHand.greaterThanOrEqual=" + DEFAULT_OLD_QUANTITY_ON_HAND);

        // Get all the inventoryItemList where oldQuantityOnHand is greater than or equal to UPDATED_OLD_QUANTITY_ON_HAND
        defaultInventoryItemShouldNotBeFound("oldQuantityOnHand.greaterThanOrEqual=" + UPDATED_OLD_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByOldQuantityOnHandIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where oldQuantityOnHand is less than or equal to DEFAULT_OLD_QUANTITY_ON_HAND
        defaultInventoryItemShouldBeFound("oldQuantityOnHand.lessThanOrEqual=" + DEFAULT_OLD_QUANTITY_ON_HAND);

        // Get all the inventoryItemList where oldQuantityOnHand is less than or equal to SMALLER_OLD_QUANTITY_ON_HAND
        defaultInventoryItemShouldNotBeFound("oldQuantityOnHand.lessThanOrEqual=" + SMALLER_OLD_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByOldQuantityOnHandIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where oldQuantityOnHand is less than DEFAULT_OLD_QUANTITY_ON_HAND
        defaultInventoryItemShouldNotBeFound("oldQuantityOnHand.lessThan=" + DEFAULT_OLD_QUANTITY_ON_HAND);

        // Get all the inventoryItemList where oldQuantityOnHand is less than UPDATED_OLD_QUANTITY_ON_HAND
        defaultInventoryItemShouldBeFound("oldQuantityOnHand.lessThan=" + UPDATED_OLD_QUANTITY_ON_HAND);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByOldQuantityOnHandIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where oldQuantityOnHand is greater than DEFAULT_OLD_QUANTITY_ON_HAND
        defaultInventoryItemShouldNotBeFound("oldQuantityOnHand.greaterThan=" + DEFAULT_OLD_QUANTITY_ON_HAND);

        // Get all the inventoryItemList where oldQuantityOnHand is greater than SMALLER_OLD_QUANTITY_ON_HAND
        defaultInventoryItemShouldBeFound("oldQuantityOnHand.greaterThan=" + SMALLER_OLD_QUANTITY_ON_HAND);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByOldAvailableToPromiseIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where oldAvailableToPromise equals to DEFAULT_OLD_AVAILABLE_TO_PROMISE
        defaultInventoryItemShouldBeFound("oldAvailableToPromise.equals=" + DEFAULT_OLD_AVAILABLE_TO_PROMISE);

        // Get all the inventoryItemList where oldAvailableToPromise equals to UPDATED_OLD_AVAILABLE_TO_PROMISE
        defaultInventoryItemShouldNotBeFound("oldAvailableToPromise.equals=" + UPDATED_OLD_AVAILABLE_TO_PROMISE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByOldAvailableToPromiseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where oldAvailableToPromise not equals to DEFAULT_OLD_AVAILABLE_TO_PROMISE
        defaultInventoryItemShouldNotBeFound("oldAvailableToPromise.notEquals=" + DEFAULT_OLD_AVAILABLE_TO_PROMISE);

        // Get all the inventoryItemList where oldAvailableToPromise not equals to UPDATED_OLD_AVAILABLE_TO_PROMISE
        defaultInventoryItemShouldBeFound("oldAvailableToPromise.notEquals=" + UPDATED_OLD_AVAILABLE_TO_PROMISE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByOldAvailableToPromiseIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where oldAvailableToPromise in DEFAULT_OLD_AVAILABLE_TO_PROMISE or UPDATED_OLD_AVAILABLE_TO_PROMISE
        defaultInventoryItemShouldBeFound("oldAvailableToPromise.in=" + DEFAULT_OLD_AVAILABLE_TO_PROMISE + "," + UPDATED_OLD_AVAILABLE_TO_PROMISE);

        // Get all the inventoryItemList where oldAvailableToPromise equals to UPDATED_OLD_AVAILABLE_TO_PROMISE
        defaultInventoryItemShouldNotBeFound("oldAvailableToPromise.in=" + UPDATED_OLD_AVAILABLE_TO_PROMISE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByOldAvailableToPromiseIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where oldAvailableToPromise is not null
        defaultInventoryItemShouldBeFound("oldAvailableToPromise.specified=true");

        // Get all the inventoryItemList where oldAvailableToPromise is null
        defaultInventoryItemShouldNotBeFound("oldAvailableToPromise.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByOldAvailableToPromiseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where oldAvailableToPromise is greater than or equal to DEFAULT_OLD_AVAILABLE_TO_PROMISE
        defaultInventoryItemShouldBeFound("oldAvailableToPromise.greaterThanOrEqual=" + DEFAULT_OLD_AVAILABLE_TO_PROMISE);

        // Get all the inventoryItemList where oldAvailableToPromise is greater than or equal to UPDATED_OLD_AVAILABLE_TO_PROMISE
        defaultInventoryItemShouldNotBeFound("oldAvailableToPromise.greaterThanOrEqual=" + UPDATED_OLD_AVAILABLE_TO_PROMISE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByOldAvailableToPromiseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where oldAvailableToPromise is less than or equal to DEFAULT_OLD_AVAILABLE_TO_PROMISE
        defaultInventoryItemShouldBeFound("oldAvailableToPromise.lessThanOrEqual=" + DEFAULT_OLD_AVAILABLE_TO_PROMISE);

        // Get all the inventoryItemList where oldAvailableToPromise is less than or equal to SMALLER_OLD_AVAILABLE_TO_PROMISE
        defaultInventoryItemShouldNotBeFound("oldAvailableToPromise.lessThanOrEqual=" + SMALLER_OLD_AVAILABLE_TO_PROMISE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByOldAvailableToPromiseIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where oldAvailableToPromise is less than DEFAULT_OLD_AVAILABLE_TO_PROMISE
        defaultInventoryItemShouldNotBeFound("oldAvailableToPromise.lessThan=" + DEFAULT_OLD_AVAILABLE_TO_PROMISE);

        // Get all the inventoryItemList where oldAvailableToPromise is less than UPDATED_OLD_AVAILABLE_TO_PROMISE
        defaultInventoryItemShouldBeFound("oldAvailableToPromise.lessThan=" + UPDATED_OLD_AVAILABLE_TO_PROMISE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByOldAvailableToPromiseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where oldAvailableToPromise is greater than DEFAULT_OLD_AVAILABLE_TO_PROMISE
        defaultInventoryItemShouldNotBeFound("oldAvailableToPromise.greaterThan=" + DEFAULT_OLD_AVAILABLE_TO_PROMISE);

        // Get all the inventoryItemList where oldAvailableToPromise is greater than SMALLER_OLD_AVAILABLE_TO_PROMISE
        defaultInventoryItemShouldBeFound("oldAvailableToPromise.greaterThan=" + SMALLER_OLD_AVAILABLE_TO_PROMISE);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsBySerialNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where serialNumber equals to DEFAULT_SERIAL_NUMBER
        defaultInventoryItemShouldBeFound("serialNumber.equals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the inventoryItemList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultInventoryItemShouldNotBeFound("serialNumber.equals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsBySerialNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where serialNumber not equals to DEFAULT_SERIAL_NUMBER
        defaultInventoryItemShouldNotBeFound("serialNumber.notEquals=" + DEFAULT_SERIAL_NUMBER);

        // Get all the inventoryItemList where serialNumber not equals to UPDATED_SERIAL_NUMBER
        defaultInventoryItemShouldBeFound("serialNumber.notEquals=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsBySerialNumberIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where serialNumber in DEFAULT_SERIAL_NUMBER or UPDATED_SERIAL_NUMBER
        defaultInventoryItemShouldBeFound("serialNumber.in=" + DEFAULT_SERIAL_NUMBER + "," + UPDATED_SERIAL_NUMBER);

        // Get all the inventoryItemList where serialNumber equals to UPDATED_SERIAL_NUMBER
        defaultInventoryItemShouldNotBeFound("serialNumber.in=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsBySerialNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where serialNumber is not null
        defaultInventoryItemShouldBeFound("serialNumber.specified=true");

        // Get all the inventoryItemList where serialNumber is null
        defaultInventoryItemShouldNotBeFound("serialNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoryItemsBySerialNumberContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where serialNumber contains DEFAULT_SERIAL_NUMBER
        defaultInventoryItemShouldBeFound("serialNumber.contains=" + DEFAULT_SERIAL_NUMBER);

        // Get all the inventoryItemList where serialNumber contains UPDATED_SERIAL_NUMBER
        defaultInventoryItemShouldNotBeFound("serialNumber.contains=" + UPDATED_SERIAL_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsBySerialNumberNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where serialNumber does not contain DEFAULT_SERIAL_NUMBER
        defaultInventoryItemShouldNotBeFound("serialNumber.doesNotContain=" + DEFAULT_SERIAL_NUMBER);

        // Get all the inventoryItemList where serialNumber does not contain UPDATED_SERIAL_NUMBER
        defaultInventoryItemShouldBeFound("serialNumber.doesNotContain=" + UPDATED_SERIAL_NUMBER);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsBySoftIdentifierIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where softIdentifier equals to DEFAULT_SOFT_IDENTIFIER
        defaultInventoryItemShouldBeFound("softIdentifier.equals=" + DEFAULT_SOFT_IDENTIFIER);

        // Get all the inventoryItemList where softIdentifier equals to UPDATED_SOFT_IDENTIFIER
        defaultInventoryItemShouldNotBeFound("softIdentifier.equals=" + UPDATED_SOFT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsBySoftIdentifierIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where softIdentifier not equals to DEFAULT_SOFT_IDENTIFIER
        defaultInventoryItemShouldNotBeFound("softIdentifier.notEquals=" + DEFAULT_SOFT_IDENTIFIER);

        // Get all the inventoryItemList where softIdentifier not equals to UPDATED_SOFT_IDENTIFIER
        defaultInventoryItemShouldBeFound("softIdentifier.notEquals=" + UPDATED_SOFT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsBySoftIdentifierIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where softIdentifier in DEFAULT_SOFT_IDENTIFIER or UPDATED_SOFT_IDENTIFIER
        defaultInventoryItemShouldBeFound("softIdentifier.in=" + DEFAULT_SOFT_IDENTIFIER + "," + UPDATED_SOFT_IDENTIFIER);

        // Get all the inventoryItemList where softIdentifier equals to UPDATED_SOFT_IDENTIFIER
        defaultInventoryItemShouldNotBeFound("softIdentifier.in=" + UPDATED_SOFT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsBySoftIdentifierIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where softIdentifier is not null
        defaultInventoryItemShouldBeFound("softIdentifier.specified=true");

        // Get all the inventoryItemList where softIdentifier is null
        defaultInventoryItemShouldNotBeFound("softIdentifier.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoryItemsBySoftIdentifierContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where softIdentifier contains DEFAULT_SOFT_IDENTIFIER
        defaultInventoryItemShouldBeFound("softIdentifier.contains=" + DEFAULT_SOFT_IDENTIFIER);

        // Get all the inventoryItemList where softIdentifier contains UPDATED_SOFT_IDENTIFIER
        defaultInventoryItemShouldNotBeFound("softIdentifier.contains=" + UPDATED_SOFT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsBySoftIdentifierNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where softIdentifier does not contain DEFAULT_SOFT_IDENTIFIER
        defaultInventoryItemShouldNotBeFound("softIdentifier.doesNotContain=" + DEFAULT_SOFT_IDENTIFIER);

        // Get all the inventoryItemList where softIdentifier does not contain UPDATED_SOFT_IDENTIFIER
        defaultInventoryItemShouldBeFound("softIdentifier.doesNotContain=" + UPDATED_SOFT_IDENTIFIER);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByActivationNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where activationNumber equals to DEFAULT_ACTIVATION_NUMBER
        defaultInventoryItemShouldBeFound("activationNumber.equals=" + DEFAULT_ACTIVATION_NUMBER);

        // Get all the inventoryItemList where activationNumber equals to UPDATED_ACTIVATION_NUMBER
        defaultInventoryItemShouldNotBeFound("activationNumber.equals=" + UPDATED_ACTIVATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByActivationNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where activationNumber not equals to DEFAULT_ACTIVATION_NUMBER
        defaultInventoryItemShouldNotBeFound("activationNumber.notEquals=" + DEFAULT_ACTIVATION_NUMBER);

        // Get all the inventoryItemList where activationNumber not equals to UPDATED_ACTIVATION_NUMBER
        defaultInventoryItemShouldBeFound("activationNumber.notEquals=" + UPDATED_ACTIVATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByActivationNumberIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where activationNumber in DEFAULT_ACTIVATION_NUMBER or UPDATED_ACTIVATION_NUMBER
        defaultInventoryItemShouldBeFound("activationNumber.in=" + DEFAULT_ACTIVATION_NUMBER + "," + UPDATED_ACTIVATION_NUMBER);

        // Get all the inventoryItemList where activationNumber equals to UPDATED_ACTIVATION_NUMBER
        defaultInventoryItemShouldNotBeFound("activationNumber.in=" + UPDATED_ACTIVATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByActivationNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where activationNumber is not null
        defaultInventoryItemShouldBeFound("activationNumber.specified=true");

        // Get all the inventoryItemList where activationNumber is null
        defaultInventoryItemShouldNotBeFound("activationNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoryItemsByActivationNumberContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where activationNumber contains DEFAULT_ACTIVATION_NUMBER
        defaultInventoryItemShouldBeFound("activationNumber.contains=" + DEFAULT_ACTIVATION_NUMBER);

        // Get all the inventoryItemList where activationNumber contains UPDATED_ACTIVATION_NUMBER
        defaultInventoryItemShouldNotBeFound("activationNumber.contains=" + UPDATED_ACTIVATION_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByActivationNumberNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where activationNumber does not contain DEFAULT_ACTIVATION_NUMBER
        defaultInventoryItemShouldNotBeFound("activationNumber.doesNotContain=" + DEFAULT_ACTIVATION_NUMBER);

        // Get all the inventoryItemList where activationNumber does not contain UPDATED_ACTIVATION_NUMBER
        defaultInventoryItemShouldBeFound("activationNumber.doesNotContain=" + UPDATED_ACTIVATION_NUMBER);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByActivationValidTrueIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where activationValidTrue equals to DEFAULT_ACTIVATION_VALID_TRUE
        defaultInventoryItemShouldBeFound("activationValidTrue.equals=" + DEFAULT_ACTIVATION_VALID_TRUE);

        // Get all the inventoryItemList where activationValidTrue equals to UPDATED_ACTIVATION_VALID_TRUE
        defaultInventoryItemShouldNotBeFound("activationValidTrue.equals=" + UPDATED_ACTIVATION_VALID_TRUE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByActivationValidTrueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where activationValidTrue not equals to DEFAULT_ACTIVATION_VALID_TRUE
        defaultInventoryItemShouldNotBeFound("activationValidTrue.notEquals=" + DEFAULT_ACTIVATION_VALID_TRUE);

        // Get all the inventoryItemList where activationValidTrue not equals to UPDATED_ACTIVATION_VALID_TRUE
        defaultInventoryItemShouldBeFound("activationValidTrue.notEquals=" + UPDATED_ACTIVATION_VALID_TRUE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByActivationValidTrueIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where activationValidTrue in DEFAULT_ACTIVATION_VALID_TRUE or UPDATED_ACTIVATION_VALID_TRUE
        defaultInventoryItemShouldBeFound("activationValidTrue.in=" + DEFAULT_ACTIVATION_VALID_TRUE + "," + UPDATED_ACTIVATION_VALID_TRUE);

        // Get all the inventoryItemList where activationValidTrue equals to UPDATED_ACTIVATION_VALID_TRUE
        defaultInventoryItemShouldNotBeFound("activationValidTrue.in=" + UPDATED_ACTIVATION_VALID_TRUE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByActivationValidTrueIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where activationValidTrue is not null
        defaultInventoryItemShouldBeFound("activationValidTrue.specified=true");

        // Get all the inventoryItemList where activationValidTrue is null
        defaultInventoryItemShouldNotBeFound("activationValidTrue.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByActivationValidTrueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where activationValidTrue is greater than or equal to DEFAULT_ACTIVATION_VALID_TRUE
        defaultInventoryItemShouldBeFound("activationValidTrue.greaterThanOrEqual=" + DEFAULT_ACTIVATION_VALID_TRUE);

        // Get all the inventoryItemList where activationValidTrue is greater than or equal to UPDATED_ACTIVATION_VALID_TRUE
        defaultInventoryItemShouldNotBeFound("activationValidTrue.greaterThanOrEqual=" + UPDATED_ACTIVATION_VALID_TRUE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByActivationValidTrueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where activationValidTrue is less than or equal to DEFAULT_ACTIVATION_VALID_TRUE
        defaultInventoryItemShouldBeFound("activationValidTrue.lessThanOrEqual=" + DEFAULT_ACTIVATION_VALID_TRUE);

        // Get all the inventoryItemList where activationValidTrue is less than or equal to SMALLER_ACTIVATION_VALID_TRUE
        defaultInventoryItemShouldNotBeFound("activationValidTrue.lessThanOrEqual=" + SMALLER_ACTIVATION_VALID_TRUE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByActivationValidTrueIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where activationValidTrue is less than DEFAULT_ACTIVATION_VALID_TRUE
        defaultInventoryItemShouldNotBeFound("activationValidTrue.lessThan=" + DEFAULT_ACTIVATION_VALID_TRUE);

        // Get all the inventoryItemList where activationValidTrue is less than UPDATED_ACTIVATION_VALID_TRUE
        defaultInventoryItemShouldBeFound("activationValidTrue.lessThan=" + UPDATED_ACTIVATION_VALID_TRUE);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByActivationValidTrueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where activationValidTrue is greater than DEFAULT_ACTIVATION_VALID_TRUE
        defaultInventoryItemShouldNotBeFound("activationValidTrue.greaterThan=" + DEFAULT_ACTIVATION_VALID_TRUE);

        // Get all the inventoryItemList where activationValidTrue is greater than SMALLER_ACTIVATION_VALID_TRUE
        defaultInventoryItemShouldBeFound("activationValidTrue.greaterThan=" + SMALLER_ACTIVATION_VALID_TRUE);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByUnitCostIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where unitCost equals to DEFAULT_UNIT_COST
        defaultInventoryItemShouldBeFound("unitCost.equals=" + DEFAULT_UNIT_COST);

        // Get all the inventoryItemList where unitCost equals to UPDATED_UNIT_COST
        defaultInventoryItemShouldNotBeFound("unitCost.equals=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByUnitCostIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where unitCost not equals to DEFAULT_UNIT_COST
        defaultInventoryItemShouldNotBeFound("unitCost.notEquals=" + DEFAULT_UNIT_COST);

        // Get all the inventoryItemList where unitCost not equals to UPDATED_UNIT_COST
        defaultInventoryItemShouldBeFound("unitCost.notEquals=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByUnitCostIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where unitCost in DEFAULT_UNIT_COST or UPDATED_UNIT_COST
        defaultInventoryItemShouldBeFound("unitCost.in=" + DEFAULT_UNIT_COST + "," + UPDATED_UNIT_COST);

        // Get all the inventoryItemList where unitCost equals to UPDATED_UNIT_COST
        defaultInventoryItemShouldNotBeFound("unitCost.in=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByUnitCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where unitCost is not null
        defaultInventoryItemShouldBeFound("unitCost.specified=true");

        // Get all the inventoryItemList where unitCost is null
        defaultInventoryItemShouldNotBeFound("unitCost.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByUnitCostIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where unitCost is greater than or equal to DEFAULT_UNIT_COST
        defaultInventoryItemShouldBeFound("unitCost.greaterThanOrEqual=" + DEFAULT_UNIT_COST);

        // Get all the inventoryItemList where unitCost is greater than or equal to UPDATED_UNIT_COST
        defaultInventoryItemShouldNotBeFound("unitCost.greaterThanOrEqual=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByUnitCostIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where unitCost is less than or equal to DEFAULT_UNIT_COST
        defaultInventoryItemShouldBeFound("unitCost.lessThanOrEqual=" + DEFAULT_UNIT_COST);

        // Get all the inventoryItemList where unitCost is less than or equal to SMALLER_UNIT_COST
        defaultInventoryItemShouldNotBeFound("unitCost.lessThanOrEqual=" + SMALLER_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByUnitCostIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where unitCost is less than DEFAULT_UNIT_COST
        defaultInventoryItemShouldNotBeFound("unitCost.lessThan=" + DEFAULT_UNIT_COST);

        // Get all the inventoryItemList where unitCost is less than UPDATED_UNIT_COST
        defaultInventoryItemShouldBeFound("unitCost.lessThan=" + UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void getAllInventoryItemsByUnitCostIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);

        // Get all the inventoryItemList where unitCost is greater than DEFAULT_UNIT_COST
        defaultInventoryItemShouldNotBeFound("unitCost.greaterThan=" + DEFAULT_UNIT_COST);

        // Get all the inventoryItemList where unitCost is greater than SMALLER_UNIT_COST
        defaultInventoryItemShouldBeFound("unitCost.greaterThan=" + SMALLER_UNIT_COST);
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByInventoryItemTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);
        InventoryItemType inventoryItemType = InventoryItemTypeResourceIT.createEntity(em);
        em.persist(inventoryItemType);
        em.flush();
        inventoryItem.setInventoryItemType(inventoryItemType);
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Long inventoryItemTypeId = inventoryItemType.getId();

        // Get all the inventoryItemList where inventoryItemType equals to inventoryItemTypeId
        defaultInventoryItemShouldBeFound("inventoryItemTypeId.equals=" + inventoryItemTypeId);

        // Get all the inventoryItemList where inventoryItemType equals to inventoryItemTypeId + 1
        defaultInventoryItemShouldNotBeFound("inventoryItemTypeId.equals=" + (inventoryItemTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        inventoryItem.setProduct(product);
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Long productId = product.getId();

        // Get all the inventoryItemList where product equals to productId
        defaultInventoryItemShouldBeFound("productId.equals=" + productId);

        // Get all the inventoryItemList where product equals to productId + 1
        defaultInventoryItemShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryItemsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Party supplier = PartyResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        inventoryItem.setSupplier(supplier);
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Long supplierId = supplier.getId();

        // Get all the inventoryItemList where supplier equals to supplierId
        defaultInventoryItemShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the inventoryItemList where supplier equals to supplierId + 1
        defaultInventoryItemShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByOwnerPartyIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Party ownerParty = PartyResourceIT.createEntity(em);
        em.persist(ownerParty);
        em.flush();
        inventoryItem.setOwnerParty(ownerParty);
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Long ownerPartyId = ownerParty.getId();

        // Get all the inventoryItemList where ownerParty equals to ownerPartyId
        defaultInventoryItemShouldBeFound("ownerPartyId.equals=" + ownerPartyId);

        // Get all the inventoryItemList where ownerParty equals to ownerPartyId + 1
        defaultInventoryItemShouldNotBeFound("ownerPartyId.equals=" + (ownerPartyId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        inventoryItem.setStatus(status);
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Long statusId = status.getId();

        // Get all the inventoryItemList where status equals to statusId
        defaultInventoryItemShouldBeFound("statusId.equals=" + statusId);

        // Get all the inventoryItemList where status equals to statusId + 1
        defaultInventoryItemShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByFacilityIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Facility facility = FacilityResourceIT.createEntity(em);
        em.persist(facility);
        em.flush();
        inventoryItem.setFacility(facility);
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Long facilityId = facility.getId();

        // Get all the inventoryItemList where facility equals to facilityId
        defaultInventoryItemShouldBeFound("facilityId.equals=" + facilityId);

        // Get all the inventoryItemList where facility equals to facilityId + 1
        defaultInventoryItemShouldNotBeFound("facilityId.equals=" + (facilityId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByUomIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Uom uom = UomResourceIT.createEntity(em);
        em.persist(uom);
        em.flush();
        inventoryItem.setUom(uom);
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Long uomId = uom.getId();

        // Get all the inventoryItemList where uom equals to uomId
        defaultInventoryItemShouldBeFound("uomId.equals=" + uomId);

        // Get all the inventoryItemList where uom equals to uomId + 1
        defaultInventoryItemShouldNotBeFound("uomId.equals=" + (uomId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByCurrencyUomIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Uom currencyUom = UomResourceIT.createEntity(em);
        em.persist(currencyUom);
        em.flush();
        inventoryItem.setCurrencyUom(currencyUom);
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Long currencyUomId = currencyUom.getId();

        // Get all the inventoryItemList where currencyUom equals to currencyUomId
        defaultInventoryItemShouldBeFound("currencyUomId.equals=" + currencyUomId);

        // Get all the inventoryItemList where currencyUom equals to currencyUomId + 1
        defaultInventoryItemShouldNotBeFound("currencyUomId.equals=" + (currencyUomId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryItemsByLotIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Lot lot = LotResourceIT.createEntity(em);
        em.persist(lot);
        em.flush();
        inventoryItem.setLot(lot);
        inventoryItemRepository.saveAndFlush(inventoryItem);
        Long lotId = lot.getId();

        // Get all the inventoryItemList where lot equals to lotId
        defaultInventoryItemShouldBeFound("lotId.equals=" + lotId);

        // Get all the inventoryItemList where lot equals to lotId + 1
        defaultInventoryItemShouldNotBeFound("lotId.equals=" + (lotId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInventoryItemShouldBeFound(String filter) throws Exception {
        restInventoryItemMockMvc.perform(get("/api/inventory-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].receivedDate").value(hasItem(sameInstant(DEFAULT_RECEIVED_DATE))))
            .andExpect(jsonPath("$.[*].manufacturedDate").value(hasItem(sameInstant(DEFAULT_MANUFACTURED_DATE))))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(sameInstant(DEFAULT_EXPIRY_DATE))))
            .andExpect(jsonPath("$.[*].retestDate").value(hasItem(sameInstant(DEFAULT_RETEST_DATE))))
            .andExpect(jsonPath("$.[*].containerId").value(hasItem(DEFAULT_CONTAINER_ID)))
            .andExpect(jsonPath("$.[*].batchNo").value(hasItem(DEFAULT_BATCH_NO)))
            .andExpect(jsonPath("$.[*].mfgBatchNo").value(hasItem(DEFAULT_MFG_BATCH_NO)))
            .andExpect(jsonPath("$.[*].lotNoStr").value(hasItem(DEFAULT_LOT_NO_STR)))
            .andExpect(jsonPath("$.[*].binNumber").value(hasItem(DEFAULT_BIN_NUMBER)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].quantityOnHandTotal").value(hasItem(DEFAULT_QUANTITY_ON_HAND_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].availableToPromiseTotal").value(hasItem(DEFAULT_AVAILABLE_TO_PROMISE_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].accountingQuantityTotal").value(hasItem(DEFAULT_ACCOUNTING_QUANTITY_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].oldQuantityOnHand").value(hasItem(DEFAULT_OLD_QUANTITY_ON_HAND.intValue())))
            .andExpect(jsonPath("$.[*].oldAvailableToPromise").value(hasItem(DEFAULT_OLD_AVAILABLE_TO_PROMISE.intValue())))
            .andExpect(jsonPath("$.[*].serialNumber").value(hasItem(DEFAULT_SERIAL_NUMBER)))
            .andExpect(jsonPath("$.[*].softIdentifier").value(hasItem(DEFAULT_SOFT_IDENTIFIER)))
            .andExpect(jsonPath("$.[*].activationNumber").value(hasItem(DEFAULT_ACTIVATION_NUMBER)))
            .andExpect(jsonPath("$.[*].activationValidTrue").value(hasItem(sameInstant(DEFAULT_ACTIVATION_VALID_TRUE))))
            .andExpect(jsonPath("$.[*].unitCost").value(hasItem(DEFAULT_UNIT_COST.intValue())));

        // Check, that the count call also returns 1
        restInventoryItemMockMvc.perform(get("/api/inventory-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInventoryItemShouldNotBeFound(String filter) throws Exception {
        restInventoryItemMockMvc.perform(get("/api/inventory-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventoryItemMockMvc.perform(get("/api/inventory-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryItem() throws Exception {
        // Get the inventoryItem
        restInventoryItemMockMvc.perform(get("/api/inventory-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryItem() throws Exception {
        // Initialize the database
        inventoryItemService.save(inventoryItem);

        int databaseSizeBeforeUpdate = inventoryItemRepository.findAll().size();

        // Update the inventoryItem
        InventoryItem updatedInventoryItem = inventoryItemRepository.findById(inventoryItem.getId()).get();
        // Disconnect from session so that the updates on updatedInventoryItem are not directly saved in db
        em.detach(updatedInventoryItem);
        updatedInventoryItem
            .receivedDate(UPDATED_RECEIVED_DATE)
            .manufacturedDate(UPDATED_MANUFACTURED_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .retestDate(UPDATED_RETEST_DATE)
            .containerId(UPDATED_CONTAINER_ID)
            .batchNo(UPDATED_BATCH_NO)
            .mfgBatchNo(UPDATED_MFG_BATCH_NO)
            .lotNoStr(UPDATED_LOT_NO_STR)
            .binNumber(UPDATED_BIN_NUMBER)
            .comments(UPDATED_COMMENTS)
            .quantityOnHandTotal(UPDATED_QUANTITY_ON_HAND_TOTAL)
            .availableToPromiseTotal(UPDATED_AVAILABLE_TO_PROMISE_TOTAL)
            .accountingQuantityTotal(UPDATED_ACCOUNTING_QUANTITY_TOTAL)
            .oldQuantityOnHand(UPDATED_OLD_QUANTITY_ON_HAND)
            .oldAvailableToPromise(UPDATED_OLD_AVAILABLE_TO_PROMISE)
            .serialNumber(UPDATED_SERIAL_NUMBER)
            .softIdentifier(UPDATED_SOFT_IDENTIFIER)
            .activationNumber(UPDATED_ACTIVATION_NUMBER)
            .activationValidTrue(UPDATED_ACTIVATION_VALID_TRUE)
            .unitCost(UPDATED_UNIT_COST);

        restInventoryItemMockMvc.perform(put("/api/inventory-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventoryItem)))
            .andExpect(status().isOk());

        // Validate the InventoryItem in the database
        List<InventoryItem> inventoryItemList = inventoryItemRepository.findAll();
        assertThat(inventoryItemList).hasSize(databaseSizeBeforeUpdate);
        InventoryItem testInventoryItem = inventoryItemList.get(inventoryItemList.size() - 1);
        assertThat(testInventoryItem.getReceivedDate()).isEqualTo(UPDATED_RECEIVED_DATE);
        assertThat(testInventoryItem.getManufacturedDate()).isEqualTo(UPDATED_MANUFACTURED_DATE);
        assertThat(testInventoryItem.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testInventoryItem.getRetestDate()).isEqualTo(UPDATED_RETEST_DATE);
        assertThat(testInventoryItem.getContainerId()).isEqualTo(UPDATED_CONTAINER_ID);
        assertThat(testInventoryItem.getBatchNo()).isEqualTo(UPDATED_BATCH_NO);
        assertThat(testInventoryItem.getMfgBatchNo()).isEqualTo(UPDATED_MFG_BATCH_NO);
        assertThat(testInventoryItem.getLotNoStr()).isEqualTo(UPDATED_LOT_NO_STR);
        assertThat(testInventoryItem.getBinNumber()).isEqualTo(UPDATED_BIN_NUMBER);
        assertThat(testInventoryItem.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testInventoryItem.getQuantityOnHandTotal()).isEqualTo(UPDATED_QUANTITY_ON_HAND_TOTAL);
        assertThat(testInventoryItem.getAvailableToPromiseTotal()).isEqualTo(UPDATED_AVAILABLE_TO_PROMISE_TOTAL);
        assertThat(testInventoryItem.getAccountingQuantityTotal()).isEqualTo(UPDATED_ACCOUNTING_QUANTITY_TOTAL);
        assertThat(testInventoryItem.getOldQuantityOnHand()).isEqualTo(UPDATED_OLD_QUANTITY_ON_HAND);
        assertThat(testInventoryItem.getOldAvailableToPromise()).isEqualTo(UPDATED_OLD_AVAILABLE_TO_PROMISE);
        assertThat(testInventoryItem.getSerialNumber()).isEqualTo(UPDATED_SERIAL_NUMBER);
        assertThat(testInventoryItem.getSoftIdentifier()).isEqualTo(UPDATED_SOFT_IDENTIFIER);
        assertThat(testInventoryItem.getActivationNumber()).isEqualTo(UPDATED_ACTIVATION_NUMBER);
        assertThat(testInventoryItem.getActivationValidTrue()).isEqualTo(UPDATED_ACTIVATION_VALID_TRUE);
        assertThat(testInventoryItem.getUnitCost()).isEqualTo(UPDATED_UNIT_COST);
    }

    @Test
    @Transactional
    public void updateNonExistingInventoryItem() throws Exception {
        int databaseSizeBeforeUpdate = inventoryItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryItemMockMvc.perform(put("/api/inventory-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryItem)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryItem in the database
        List<InventoryItem> inventoryItemList = inventoryItemRepository.findAll();
        assertThat(inventoryItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventoryItem() throws Exception {
        // Initialize the database
        inventoryItemService.save(inventoryItem);

        int databaseSizeBeforeDelete = inventoryItemRepository.findAll().size();

        // Delete the inventoryItem
        restInventoryItemMockMvc.perform(delete("/api/inventory-items/{id}", inventoryItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InventoryItem> inventoryItemList = inventoryItemRepository.findAll();
        assertThat(inventoryItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
