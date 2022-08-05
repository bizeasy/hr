package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ItemIssuance;
import com.hr.domain.Order;
import com.hr.domain.OrderItem;
import com.hr.domain.InventoryItem;
import com.hr.domain.User;
import com.hr.domain.Reason;
import com.hr.domain.Facility;
import com.hr.domain.Status;
import com.hr.repository.ItemIssuanceRepository;
import com.hr.service.ItemIssuanceService;
import com.hr.service.dto.ItemIssuanceCriteria;
import com.hr.service.ItemIssuanceQueryService;

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
 * Integration tests for the {@link ItemIssuanceResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ItemIssuanceResourceIT {

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ISSUED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ISSUED_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ISSUED_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_ISSUED_BY = "AAAAAAAAAA";
    private static final String UPDATED_ISSUED_BY = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CANCEL_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_CANCEL_QUANTITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_CANCEL_QUANTITY = new BigDecimal(1 - 1);

    private static final ZonedDateTime DEFAULT_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FROM_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_THRU_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_EQUIPMENT_ID = "AAAAAAAAAA";
    private static final String UPDATED_EQUIPMENT_ID = "BBBBBBBBBB";

    @Autowired
    private ItemIssuanceRepository itemIssuanceRepository;

    @Autowired
    private ItemIssuanceService itemIssuanceService;

    @Autowired
    private ItemIssuanceQueryService itemIssuanceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restItemIssuanceMockMvc;

    private ItemIssuance itemIssuance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemIssuance createEntity(EntityManager em) {
        ItemIssuance itemIssuance = new ItemIssuance()
            .message(DEFAULT_MESSAGE)
            .issuedDate(DEFAULT_ISSUED_DATE)
            .issuedBy(DEFAULT_ISSUED_BY)
            .quantity(DEFAULT_QUANTITY)
            .cancelQuantity(DEFAULT_CANCEL_QUANTITY)
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .equipmentId(DEFAULT_EQUIPMENT_ID);
        return itemIssuance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemIssuance createUpdatedEntity(EntityManager em) {
        ItemIssuance itemIssuance = new ItemIssuance()
            .message(UPDATED_MESSAGE)
            .issuedDate(UPDATED_ISSUED_DATE)
            .issuedBy(UPDATED_ISSUED_BY)
            .quantity(UPDATED_QUANTITY)
            .cancelQuantity(UPDATED_CANCEL_QUANTITY)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .equipmentId(UPDATED_EQUIPMENT_ID);
        return itemIssuance;
    }

    @BeforeEach
    public void initTest() {
        itemIssuance = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemIssuance() throws Exception {
        int databaseSizeBeforeCreate = itemIssuanceRepository.findAll().size();
        // Create the ItemIssuance
        restItemIssuanceMockMvc.perform(post("/api/item-issuances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemIssuance)))
            .andExpect(status().isCreated());

        // Validate the ItemIssuance in the database
        List<ItemIssuance> itemIssuanceList = itemIssuanceRepository.findAll();
        assertThat(itemIssuanceList).hasSize(databaseSizeBeforeCreate + 1);
        ItemIssuance testItemIssuance = itemIssuanceList.get(itemIssuanceList.size() - 1);
        assertThat(testItemIssuance.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testItemIssuance.getIssuedDate()).isEqualTo(DEFAULT_ISSUED_DATE);
        assertThat(testItemIssuance.getIssuedBy()).isEqualTo(DEFAULT_ISSUED_BY);
        assertThat(testItemIssuance.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testItemIssuance.getCancelQuantity()).isEqualTo(DEFAULT_CANCEL_QUANTITY);
        assertThat(testItemIssuance.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testItemIssuance.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testItemIssuance.getEquipmentId()).isEqualTo(DEFAULT_EQUIPMENT_ID);
    }

    @Test
    @Transactional
    public void createItemIssuanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemIssuanceRepository.findAll().size();

        // Create the ItemIssuance with an existing ID
        itemIssuance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemIssuanceMockMvc.perform(post("/api/item-issuances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemIssuance)))
            .andExpect(status().isBadRequest());

        // Validate the ItemIssuance in the database
        List<ItemIssuance> itemIssuanceList = itemIssuanceRepository.findAll();
        assertThat(itemIssuanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllItemIssuances() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList
        restItemIssuanceMockMvc.perform(get("/api/item-issuances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemIssuance.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].issuedDate").value(hasItem(sameInstant(DEFAULT_ISSUED_DATE))))
            .andExpect(jsonPath("$.[*].issuedBy").value(hasItem(DEFAULT_ISSUED_BY)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].cancelQuantity").value(hasItem(DEFAULT_CANCEL_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))))
            .andExpect(jsonPath("$.[*].equipmentId").value(hasItem(DEFAULT_EQUIPMENT_ID)));
    }
    
    @Test
    @Transactional
    public void getItemIssuance() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get the itemIssuance
        restItemIssuanceMockMvc.perform(get("/api/item-issuances/{id}", itemIssuance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(itemIssuance.getId().intValue()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.issuedDate").value(sameInstant(DEFAULT_ISSUED_DATE)))
            .andExpect(jsonPath("$.issuedBy").value(DEFAULT_ISSUED_BY))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.cancelQuantity").value(DEFAULT_CANCEL_QUANTITY.intValue()))
            .andExpect(jsonPath("$.fromDate").value(sameInstant(DEFAULT_FROM_DATE)))
            .andExpect(jsonPath("$.thruDate").value(sameInstant(DEFAULT_THRU_DATE)))
            .andExpect(jsonPath("$.equipmentId").value(DEFAULT_EQUIPMENT_ID));
    }


    @Test
    @Transactional
    public void getItemIssuancesByIdFiltering() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        Long id = itemIssuance.getId();

        defaultItemIssuanceShouldBeFound("id.equals=" + id);
        defaultItemIssuanceShouldNotBeFound("id.notEquals=" + id);

        defaultItemIssuanceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultItemIssuanceShouldNotBeFound("id.greaterThan=" + id);

        defaultItemIssuanceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultItemIssuanceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllItemIssuancesByMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where message equals to DEFAULT_MESSAGE
        defaultItemIssuanceShouldBeFound("message.equals=" + DEFAULT_MESSAGE);

        // Get all the itemIssuanceList where message equals to UPDATED_MESSAGE
        defaultItemIssuanceShouldNotBeFound("message.equals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where message not equals to DEFAULT_MESSAGE
        defaultItemIssuanceShouldNotBeFound("message.notEquals=" + DEFAULT_MESSAGE);

        // Get all the itemIssuanceList where message not equals to UPDATED_MESSAGE
        defaultItemIssuanceShouldBeFound("message.notEquals=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByMessageIsInShouldWork() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where message in DEFAULT_MESSAGE or UPDATED_MESSAGE
        defaultItemIssuanceShouldBeFound("message.in=" + DEFAULT_MESSAGE + "," + UPDATED_MESSAGE);

        // Get all the itemIssuanceList where message equals to UPDATED_MESSAGE
        defaultItemIssuanceShouldNotBeFound("message.in=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where message is not null
        defaultItemIssuanceShouldBeFound("message.specified=true");

        // Get all the itemIssuanceList where message is null
        defaultItemIssuanceShouldNotBeFound("message.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemIssuancesByMessageContainsSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where message contains DEFAULT_MESSAGE
        defaultItemIssuanceShouldBeFound("message.contains=" + DEFAULT_MESSAGE);

        // Get all the itemIssuanceList where message contains UPDATED_MESSAGE
        defaultItemIssuanceShouldNotBeFound("message.contains=" + UPDATED_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByMessageNotContainsSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where message does not contain DEFAULT_MESSAGE
        defaultItemIssuanceShouldNotBeFound("message.doesNotContain=" + DEFAULT_MESSAGE);

        // Get all the itemIssuanceList where message does not contain UPDATED_MESSAGE
        defaultItemIssuanceShouldBeFound("message.doesNotContain=" + UPDATED_MESSAGE);
    }


    @Test
    @Transactional
    public void getAllItemIssuancesByIssuedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where issuedDate equals to DEFAULT_ISSUED_DATE
        defaultItemIssuanceShouldBeFound("issuedDate.equals=" + DEFAULT_ISSUED_DATE);

        // Get all the itemIssuanceList where issuedDate equals to UPDATED_ISSUED_DATE
        defaultItemIssuanceShouldNotBeFound("issuedDate.equals=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByIssuedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where issuedDate not equals to DEFAULT_ISSUED_DATE
        defaultItemIssuanceShouldNotBeFound("issuedDate.notEquals=" + DEFAULT_ISSUED_DATE);

        // Get all the itemIssuanceList where issuedDate not equals to UPDATED_ISSUED_DATE
        defaultItemIssuanceShouldBeFound("issuedDate.notEquals=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByIssuedDateIsInShouldWork() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where issuedDate in DEFAULT_ISSUED_DATE or UPDATED_ISSUED_DATE
        defaultItemIssuanceShouldBeFound("issuedDate.in=" + DEFAULT_ISSUED_DATE + "," + UPDATED_ISSUED_DATE);

        // Get all the itemIssuanceList where issuedDate equals to UPDATED_ISSUED_DATE
        defaultItemIssuanceShouldNotBeFound("issuedDate.in=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByIssuedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where issuedDate is not null
        defaultItemIssuanceShouldBeFound("issuedDate.specified=true");

        // Get all the itemIssuanceList where issuedDate is null
        defaultItemIssuanceShouldNotBeFound("issuedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByIssuedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where issuedDate is greater than or equal to DEFAULT_ISSUED_DATE
        defaultItemIssuanceShouldBeFound("issuedDate.greaterThanOrEqual=" + DEFAULT_ISSUED_DATE);

        // Get all the itemIssuanceList where issuedDate is greater than or equal to UPDATED_ISSUED_DATE
        defaultItemIssuanceShouldNotBeFound("issuedDate.greaterThanOrEqual=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByIssuedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where issuedDate is less than or equal to DEFAULT_ISSUED_DATE
        defaultItemIssuanceShouldBeFound("issuedDate.lessThanOrEqual=" + DEFAULT_ISSUED_DATE);

        // Get all the itemIssuanceList where issuedDate is less than or equal to SMALLER_ISSUED_DATE
        defaultItemIssuanceShouldNotBeFound("issuedDate.lessThanOrEqual=" + SMALLER_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByIssuedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where issuedDate is less than DEFAULT_ISSUED_DATE
        defaultItemIssuanceShouldNotBeFound("issuedDate.lessThan=" + DEFAULT_ISSUED_DATE);

        // Get all the itemIssuanceList where issuedDate is less than UPDATED_ISSUED_DATE
        defaultItemIssuanceShouldBeFound("issuedDate.lessThan=" + UPDATED_ISSUED_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByIssuedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where issuedDate is greater than DEFAULT_ISSUED_DATE
        defaultItemIssuanceShouldNotBeFound("issuedDate.greaterThan=" + DEFAULT_ISSUED_DATE);

        // Get all the itemIssuanceList where issuedDate is greater than SMALLER_ISSUED_DATE
        defaultItemIssuanceShouldBeFound("issuedDate.greaterThan=" + SMALLER_ISSUED_DATE);
    }


    @Test
    @Transactional
    public void getAllItemIssuancesByIssuedByIsEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where issuedBy equals to DEFAULT_ISSUED_BY
        defaultItemIssuanceShouldBeFound("issuedBy.equals=" + DEFAULT_ISSUED_BY);

        // Get all the itemIssuanceList where issuedBy equals to UPDATED_ISSUED_BY
        defaultItemIssuanceShouldNotBeFound("issuedBy.equals=" + UPDATED_ISSUED_BY);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByIssuedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where issuedBy not equals to DEFAULT_ISSUED_BY
        defaultItemIssuanceShouldNotBeFound("issuedBy.notEquals=" + DEFAULT_ISSUED_BY);

        // Get all the itemIssuanceList where issuedBy not equals to UPDATED_ISSUED_BY
        defaultItemIssuanceShouldBeFound("issuedBy.notEquals=" + UPDATED_ISSUED_BY);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByIssuedByIsInShouldWork() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where issuedBy in DEFAULT_ISSUED_BY or UPDATED_ISSUED_BY
        defaultItemIssuanceShouldBeFound("issuedBy.in=" + DEFAULT_ISSUED_BY + "," + UPDATED_ISSUED_BY);

        // Get all the itemIssuanceList where issuedBy equals to UPDATED_ISSUED_BY
        defaultItemIssuanceShouldNotBeFound("issuedBy.in=" + UPDATED_ISSUED_BY);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByIssuedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where issuedBy is not null
        defaultItemIssuanceShouldBeFound("issuedBy.specified=true");

        // Get all the itemIssuanceList where issuedBy is null
        defaultItemIssuanceShouldNotBeFound("issuedBy.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemIssuancesByIssuedByContainsSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where issuedBy contains DEFAULT_ISSUED_BY
        defaultItemIssuanceShouldBeFound("issuedBy.contains=" + DEFAULT_ISSUED_BY);

        // Get all the itemIssuanceList where issuedBy contains UPDATED_ISSUED_BY
        defaultItemIssuanceShouldNotBeFound("issuedBy.contains=" + UPDATED_ISSUED_BY);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByIssuedByNotContainsSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where issuedBy does not contain DEFAULT_ISSUED_BY
        defaultItemIssuanceShouldNotBeFound("issuedBy.doesNotContain=" + DEFAULT_ISSUED_BY);

        // Get all the itemIssuanceList where issuedBy does not contain UPDATED_ISSUED_BY
        defaultItemIssuanceShouldBeFound("issuedBy.doesNotContain=" + UPDATED_ISSUED_BY);
    }


    @Test
    @Transactional
    public void getAllItemIssuancesByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where quantity equals to DEFAULT_QUANTITY
        defaultItemIssuanceShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the itemIssuanceList where quantity equals to UPDATED_QUANTITY
        defaultItemIssuanceShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where quantity not equals to DEFAULT_QUANTITY
        defaultItemIssuanceShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the itemIssuanceList where quantity not equals to UPDATED_QUANTITY
        defaultItemIssuanceShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultItemIssuanceShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the itemIssuanceList where quantity equals to UPDATED_QUANTITY
        defaultItemIssuanceShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where quantity is not null
        defaultItemIssuanceShouldBeFound("quantity.specified=true");

        // Get all the itemIssuanceList where quantity is null
        defaultItemIssuanceShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultItemIssuanceShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the itemIssuanceList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultItemIssuanceShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultItemIssuanceShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the itemIssuanceList where quantity is less than or equal to SMALLER_QUANTITY
        defaultItemIssuanceShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where quantity is less than DEFAULT_QUANTITY
        defaultItemIssuanceShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the itemIssuanceList where quantity is less than UPDATED_QUANTITY
        defaultItemIssuanceShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where quantity is greater than DEFAULT_QUANTITY
        defaultItemIssuanceShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the itemIssuanceList where quantity is greater than SMALLER_QUANTITY
        defaultItemIssuanceShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllItemIssuancesByCancelQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where cancelQuantity equals to DEFAULT_CANCEL_QUANTITY
        defaultItemIssuanceShouldBeFound("cancelQuantity.equals=" + DEFAULT_CANCEL_QUANTITY);

        // Get all the itemIssuanceList where cancelQuantity equals to UPDATED_CANCEL_QUANTITY
        defaultItemIssuanceShouldNotBeFound("cancelQuantity.equals=" + UPDATED_CANCEL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByCancelQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where cancelQuantity not equals to DEFAULT_CANCEL_QUANTITY
        defaultItemIssuanceShouldNotBeFound("cancelQuantity.notEquals=" + DEFAULT_CANCEL_QUANTITY);

        // Get all the itemIssuanceList where cancelQuantity not equals to UPDATED_CANCEL_QUANTITY
        defaultItemIssuanceShouldBeFound("cancelQuantity.notEquals=" + UPDATED_CANCEL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByCancelQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where cancelQuantity in DEFAULT_CANCEL_QUANTITY or UPDATED_CANCEL_QUANTITY
        defaultItemIssuanceShouldBeFound("cancelQuantity.in=" + DEFAULT_CANCEL_QUANTITY + "," + UPDATED_CANCEL_QUANTITY);

        // Get all the itemIssuanceList where cancelQuantity equals to UPDATED_CANCEL_QUANTITY
        defaultItemIssuanceShouldNotBeFound("cancelQuantity.in=" + UPDATED_CANCEL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByCancelQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where cancelQuantity is not null
        defaultItemIssuanceShouldBeFound("cancelQuantity.specified=true");

        // Get all the itemIssuanceList where cancelQuantity is null
        defaultItemIssuanceShouldNotBeFound("cancelQuantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByCancelQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where cancelQuantity is greater than or equal to DEFAULT_CANCEL_QUANTITY
        defaultItemIssuanceShouldBeFound("cancelQuantity.greaterThanOrEqual=" + DEFAULT_CANCEL_QUANTITY);

        // Get all the itemIssuanceList where cancelQuantity is greater than or equal to UPDATED_CANCEL_QUANTITY
        defaultItemIssuanceShouldNotBeFound("cancelQuantity.greaterThanOrEqual=" + UPDATED_CANCEL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByCancelQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where cancelQuantity is less than or equal to DEFAULT_CANCEL_QUANTITY
        defaultItemIssuanceShouldBeFound("cancelQuantity.lessThanOrEqual=" + DEFAULT_CANCEL_QUANTITY);

        // Get all the itemIssuanceList where cancelQuantity is less than or equal to SMALLER_CANCEL_QUANTITY
        defaultItemIssuanceShouldNotBeFound("cancelQuantity.lessThanOrEqual=" + SMALLER_CANCEL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByCancelQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where cancelQuantity is less than DEFAULT_CANCEL_QUANTITY
        defaultItemIssuanceShouldNotBeFound("cancelQuantity.lessThan=" + DEFAULT_CANCEL_QUANTITY);

        // Get all the itemIssuanceList where cancelQuantity is less than UPDATED_CANCEL_QUANTITY
        defaultItemIssuanceShouldBeFound("cancelQuantity.lessThan=" + UPDATED_CANCEL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByCancelQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where cancelQuantity is greater than DEFAULT_CANCEL_QUANTITY
        defaultItemIssuanceShouldNotBeFound("cancelQuantity.greaterThan=" + DEFAULT_CANCEL_QUANTITY);

        // Get all the itemIssuanceList where cancelQuantity is greater than SMALLER_CANCEL_QUANTITY
        defaultItemIssuanceShouldBeFound("cancelQuantity.greaterThan=" + SMALLER_CANCEL_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllItemIssuancesByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where fromDate equals to DEFAULT_FROM_DATE
        defaultItemIssuanceShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the itemIssuanceList where fromDate equals to UPDATED_FROM_DATE
        defaultItemIssuanceShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where fromDate not equals to DEFAULT_FROM_DATE
        defaultItemIssuanceShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the itemIssuanceList where fromDate not equals to UPDATED_FROM_DATE
        defaultItemIssuanceShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultItemIssuanceShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the itemIssuanceList where fromDate equals to UPDATED_FROM_DATE
        defaultItemIssuanceShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where fromDate is not null
        defaultItemIssuanceShouldBeFound("fromDate.specified=true");

        // Get all the itemIssuanceList where fromDate is null
        defaultItemIssuanceShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByFromDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where fromDate is greater than or equal to DEFAULT_FROM_DATE
        defaultItemIssuanceShouldBeFound("fromDate.greaterThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the itemIssuanceList where fromDate is greater than or equal to UPDATED_FROM_DATE
        defaultItemIssuanceShouldNotBeFound("fromDate.greaterThanOrEqual=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByFromDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where fromDate is less than or equal to DEFAULT_FROM_DATE
        defaultItemIssuanceShouldBeFound("fromDate.lessThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the itemIssuanceList where fromDate is less than or equal to SMALLER_FROM_DATE
        defaultItemIssuanceShouldNotBeFound("fromDate.lessThanOrEqual=" + SMALLER_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByFromDateIsLessThanSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where fromDate is less than DEFAULT_FROM_DATE
        defaultItemIssuanceShouldNotBeFound("fromDate.lessThan=" + DEFAULT_FROM_DATE);

        // Get all the itemIssuanceList where fromDate is less than UPDATED_FROM_DATE
        defaultItemIssuanceShouldBeFound("fromDate.lessThan=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByFromDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where fromDate is greater than DEFAULT_FROM_DATE
        defaultItemIssuanceShouldNotBeFound("fromDate.greaterThan=" + DEFAULT_FROM_DATE);

        // Get all the itemIssuanceList where fromDate is greater than SMALLER_FROM_DATE
        defaultItemIssuanceShouldBeFound("fromDate.greaterThan=" + SMALLER_FROM_DATE);
    }


    @Test
    @Transactional
    public void getAllItemIssuancesByThruDateIsEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where thruDate equals to DEFAULT_THRU_DATE
        defaultItemIssuanceShouldBeFound("thruDate.equals=" + DEFAULT_THRU_DATE);

        // Get all the itemIssuanceList where thruDate equals to UPDATED_THRU_DATE
        defaultItemIssuanceShouldNotBeFound("thruDate.equals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByThruDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where thruDate not equals to DEFAULT_THRU_DATE
        defaultItemIssuanceShouldNotBeFound("thruDate.notEquals=" + DEFAULT_THRU_DATE);

        // Get all the itemIssuanceList where thruDate not equals to UPDATED_THRU_DATE
        defaultItemIssuanceShouldBeFound("thruDate.notEquals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByThruDateIsInShouldWork() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where thruDate in DEFAULT_THRU_DATE or UPDATED_THRU_DATE
        defaultItemIssuanceShouldBeFound("thruDate.in=" + DEFAULT_THRU_DATE + "," + UPDATED_THRU_DATE);

        // Get all the itemIssuanceList where thruDate equals to UPDATED_THRU_DATE
        defaultItemIssuanceShouldNotBeFound("thruDate.in=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByThruDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where thruDate is not null
        defaultItemIssuanceShouldBeFound("thruDate.specified=true");

        // Get all the itemIssuanceList where thruDate is null
        defaultItemIssuanceShouldNotBeFound("thruDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByThruDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where thruDate is greater than or equal to DEFAULT_THRU_DATE
        defaultItemIssuanceShouldBeFound("thruDate.greaterThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the itemIssuanceList where thruDate is greater than or equal to UPDATED_THRU_DATE
        defaultItemIssuanceShouldNotBeFound("thruDate.greaterThanOrEqual=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByThruDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where thruDate is less than or equal to DEFAULT_THRU_DATE
        defaultItemIssuanceShouldBeFound("thruDate.lessThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the itemIssuanceList where thruDate is less than or equal to SMALLER_THRU_DATE
        defaultItemIssuanceShouldNotBeFound("thruDate.lessThanOrEqual=" + SMALLER_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByThruDateIsLessThanSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where thruDate is less than DEFAULT_THRU_DATE
        defaultItemIssuanceShouldNotBeFound("thruDate.lessThan=" + DEFAULT_THRU_DATE);

        // Get all the itemIssuanceList where thruDate is less than UPDATED_THRU_DATE
        defaultItemIssuanceShouldBeFound("thruDate.lessThan=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByThruDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where thruDate is greater than DEFAULT_THRU_DATE
        defaultItemIssuanceShouldNotBeFound("thruDate.greaterThan=" + DEFAULT_THRU_DATE);

        // Get all the itemIssuanceList where thruDate is greater than SMALLER_THRU_DATE
        defaultItemIssuanceShouldBeFound("thruDate.greaterThan=" + SMALLER_THRU_DATE);
    }


    @Test
    @Transactional
    public void getAllItemIssuancesByEquipmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where equipmentId equals to DEFAULT_EQUIPMENT_ID
        defaultItemIssuanceShouldBeFound("equipmentId.equals=" + DEFAULT_EQUIPMENT_ID);

        // Get all the itemIssuanceList where equipmentId equals to UPDATED_EQUIPMENT_ID
        defaultItemIssuanceShouldNotBeFound("equipmentId.equals=" + UPDATED_EQUIPMENT_ID);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByEquipmentIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where equipmentId not equals to DEFAULT_EQUIPMENT_ID
        defaultItemIssuanceShouldNotBeFound("equipmentId.notEquals=" + DEFAULT_EQUIPMENT_ID);

        // Get all the itemIssuanceList where equipmentId not equals to UPDATED_EQUIPMENT_ID
        defaultItemIssuanceShouldBeFound("equipmentId.notEquals=" + UPDATED_EQUIPMENT_ID);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByEquipmentIdIsInShouldWork() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where equipmentId in DEFAULT_EQUIPMENT_ID or UPDATED_EQUIPMENT_ID
        defaultItemIssuanceShouldBeFound("equipmentId.in=" + DEFAULT_EQUIPMENT_ID + "," + UPDATED_EQUIPMENT_ID);

        // Get all the itemIssuanceList where equipmentId equals to UPDATED_EQUIPMENT_ID
        defaultItemIssuanceShouldNotBeFound("equipmentId.in=" + UPDATED_EQUIPMENT_ID);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByEquipmentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where equipmentId is not null
        defaultItemIssuanceShouldBeFound("equipmentId.specified=true");

        // Get all the itemIssuanceList where equipmentId is null
        defaultItemIssuanceShouldNotBeFound("equipmentId.specified=false");
    }
                @Test
    @Transactional
    public void getAllItemIssuancesByEquipmentIdContainsSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where equipmentId contains DEFAULT_EQUIPMENT_ID
        defaultItemIssuanceShouldBeFound("equipmentId.contains=" + DEFAULT_EQUIPMENT_ID);

        // Get all the itemIssuanceList where equipmentId contains UPDATED_EQUIPMENT_ID
        defaultItemIssuanceShouldNotBeFound("equipmentId.contains=" + UPDATED_EQUIPMENT_ID);
    }

    @Test
    @Transactional
    public void getAllItemIssuancesByEquipmentIdNotContainsSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);

        // Get all the itemIssuanceList where equipmentId does not contain DEFAULT_EQUIPMENT_ID
        defaultItemIssuanceShouldNotBeFound("equipmentId.doesNotContain=" + DEFAULT_EQUIPMENT_ID);

        // Get all the itemIssuanceList where equipmentId does not contain UPDATED_EQUIPMENT_ID
        defaultItemIssuanceShouldBeFound("equipmentId.doesNotContain=" + UPDATED_EQUIPMENT_ID);
    }


    @Test
    @Transactional
    public void getAllItemIssuancesByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);
        Order order = OrderResourceIT.createEntity(em);
        em.persist(order);
        em.flush();
        itemIssuance.setOrder(order);
        itemIssuanceRepository.saveAndFlush(itemIssuance);
        Long orderId = order.getId();

        // Get all the itemIssuanceList where order equals to orderId
        defaultItemIssuanceShouldBeFound("orderId.equals=" + orderId);

        // Get all the itemIssuanceList where order equals to orderId + 1
        defaultItemIssuanceShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }


    @Test
    @Transactional
    public void getAllItemIssuancesByOrderItemIsEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);
        OrderItem orderItem = OrderItemResourceIT.createEntity(em);
        em.persist(orderItem);
        em.flush();
        itemIssuance.setOrderItem(orderItem);
        itemIssuanceRepository.saveAndFlush(itemIssuance);
        Long orderItemId = orderItem.getId();

        // Get all the itemIssuanceList where orderItem equals to orderItemId
        defaultItemIssuanceShouldBeFound("orderItemId.equals=" + orderItemId);

        // Get all the itemIssuanceList where orderItem equals to orderItemId + 1
        defaultItemIssuanceShouldNotBeFound("orderItemId.equals=" + (orderItemId + 1));
    }


    @Test
    @Transactional
    public void getAllItemIssuancesByInventoryItemIsEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);
        InventoryItem inventoryItem = InventoryItemResourceIT.createEntity(em);
        em.persist(inventoryItem);
        em.flush();
        itemIssuance.setInventoryItem(inventoryItem);
        itemIssuanceRepository.saveAndFlush(itemIssuance);
        Long inventoryItemId = inventoryItem.getId();

        // Get all the itemIssuanceList where inventoryItem equals to inventoryItemId
        defaultItemIssuanceShouldBeFound("inventoryItemId.equals=" + inventoryItemId);

        // Get all the itemIssuanceList where inventoryItem equals to inventoryItemId + 1
        defaultItemIssuanceShouldNotBeFound("inventoryItemId.equals=" + (inventoryItemId + 1));
    }


    @Test
    @Transactional
    public void getAllItemIssuancesByIssuedByUserLoginIsEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);
        User issuedByUserLogin = UserResourceIT.createEntity(em);
        em.persist(issuedByUserLogin);
        em.flush();
        itemIssuance.setIssuedByUserLogin(issuedByUserLogin);
        itemIssuanceRepository.saveAndFlush(itemIssuance);
        Long issuedByUserLoginId = issuedByUserLogin.getId();

        // Get all the itemIssuanceList where issuedByUserLogin equals to issuedByUserLoginId
        defaultItemIssuanceShouldBeFound("issuedByUserLoginId.equals=" + issuedByUserLoginId);

        // Get all the itemIssuanceList where issuedByUserLogin equals to issuedByUserLoginId + 1
        defaultItemIssuanceShouldNotBeFound("issuedByUserLoginId.equals=" + (issuedByUserLoginId + 1));
    }


    @Test
    @Transactional
    public void getAllItemIssuancesByVarianceReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);
        Reason varianceReason = ReasonResourceIT.createEntity(em);
        em.persist(varianceReason);
        em.flush();
        itemIssuance.setVarianceReason(varianceReason);
        itemIssuanceRepository.saveAndFlush(itemIssuance);
        Long varianceReasonId = varianceReason.getId();

        // Get all the itemIssuanceList where varianceReason equals to varianceReasonId
        defaultItemIssuanceShouldBeFound("varianceReasonId.equals=" + varianceReasonId);

        // Get all the itemIssuanceList where varianceReason equals to varianceReasonId + 1
        defaultItemIssuanceShouldNotBeFound("varianceReasonId.equals=" + (varianceReasonId + 1));
    }


    @Test
    @Transactional
    public void getAllItemIssuancesByFacilityIsEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);
        Facility facility = FacilityResourceIT.createEntity(em);
        em.persist(facility);
        em.flush();
        itemIssuance.setFacility(facility);
        itemIssuanceRepository.saveAndFlush(itemIssuance);
        Long facilityId = facility.getId();

        // Get all the itemIssuanceList where facility equals to facilityId
        defaultItemIssuanceShouldBeFound("facilityId.equals=" + facilityId);

        // Get all the itemIssuanceList where facility equals to facilityId + 1
        defaultItemIssuanceShouldNotBeFound("facilityId.equals=" + (facilityId + 1));
    }


    @Test
    @Transactional
    public void getAllItemIssuancesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        itemIssuanceRepository.saveAndFlush(itemIssuance);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        itemIssuance.setStatus(status);
        itemIssuanceRepository.saveAndFlush(itemIssuance);
        Long statusId = status.getId();

        // Get all the itemIssuanceList where status equals to statusId
        defaultItemIssuanceShouldBeFound("statusId.equals=" + statusId);

        // Get all the itemIssuanceList where status equals to statusId + 1
        defaultItemIssuanceShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultItemIssuanceShouldBeFound(String filter) throws Exception {
        restItemIssuanceMockMvc.perform(get("/api/item-issuances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemIssuance.getId().intValue())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].issuedDate").value(hasItem(sameInstant(DEFAULT_ISSUED_DATE))))
            .andExpect(jsonPath("$.[*].issuedBy").value(hasItem(DEFAULT_ISSUED_BY)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].cancelQuantity").value(hasItem(DEFAULT_CANCEL_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))))
            .andExpect(jsonPath("$.[*].equipmentId").value(hasItem(DEFAULT_EQUIPMENT_ID)));

        // Check, that the count call also returns 1
        restItemIssuanceMockMvc.perform(get("/api/item-issuances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultItemIssuanceShouldNotBeFound(String filter) throws Exception {
        restItemIssuanceMockMvc.perform(get("/api/item-issuances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restItemIssuanceMockMvc.perform(get("/api/item-issuances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingItemIssuance() throws Exception {
        // Get the itemIssuance
        restItemIssuanceMockMvc.perform(get("/api/item-issuances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemIssuance() throws Exception {
        // Initialize the database
        itemIssuanceService.save(itemIssuance);

        int databaseSizeBeforeUpdate = itemIssuanceRepository.findAll().size();

        // Update the itemIssuance
        ItemIssuance updatedItemIssuance = itemIssuanceRepository.findById(itemIssuance.getId()).get();
        // Disconnect from session so that the updates on updatedItemIssuance are not directly saved in db
        em.detach(updatedItemIssuance);
        updatedItemIssuance
            .message(UPDATED_MESSAGE)
            .issuedDate(UPDATED_ISSUED_DATE)
            .issuedBy(UPDATED_ISSUED_BY)
            .quantity(UPDATED_QUANTITY)
            .cancelQuantity(UPDATED_CANCEL_QUANTITY)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .equipmentId(UPDATED_EQUIPMENT_ID);

        restItemIssuanceMockMvc.perform(put("/api/item-issuances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemIssuance)))
            .andExpect(status().isOk());

        // Validate the ItemIssuance in the database
        List<ItemIssuance> itemIssuanceList = itemIssuanceRepository.findAll();
        assertThat(itemIssuanceList).hasSize(databaseSizeBeforeUpdate);
        ItemIssuance testItemIssuance = itemIssuanceList.get(itemIssuanceList.size() - 1);
        assertThat(testItemIssuance.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testItemIssuance.getIssuedDate()).isEqualTo(UPDATED_ISSUED_DATE);
        assertThat(testItemIssuance.getIssuedBy()).isEqualTo(UPDATED_ISSUED_BY);
        assertThat(testItemIssuance.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testItemIssuance.getCancelQuantity()).isEqualTo(UPDATED_CANCEL_QUANTITY);
        assertThat(testItemIssuance.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testItemIssuance.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testItemIssuance.getEquipmentId()).isEqualTo(UPDATED_EQUIPMENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingItemIssuance() throws Exception {
        int databaseSizeBeforeUpdate = itemIssuanceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemIssuanceMockMvc.perform(put("/api/item-issuances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(itemIssuance)))
            .andExpect(status().isBadRequest());

        // Validate the ItemIssuance in the database
        List<ItemIssuance> itemIssuanceList = itemIssuanceRepository.findAll();
        assertThat(itemIssuanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemIssuance() throws Exception {
        // Initialize the database
        itemIssuanceService.save(itemIssuance);

        int databaseSizeBeforeDelete = itemIssuanceRepository.findAll().size();

        // Delete the itemIssuance
        restItemIssuanceMockMvc.perform(delete("/api/item-issuances/{id}", itemIssuance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ItemIssuance> itemIssuanceList = itemIssuanceRepository.findAll();
        assertThat(itemIssuanceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
