package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.OrderItem;
import com.hr.domain.Order;
import com.hr.domain.OrderItemType;
import com.hr.domain.InventoryItem;
import com.hr.domain.Product;
import com.hr.domain.SupplierProduct;
import com.hr.domain.Status;
import com.hr.domain.TaxAuthorityRateType;
import com.hr.repository.OrderItemRepository;
import com.hr.service.OrderItemService;
import com.hr.service.dto.OrderItemCriteria;
import com.hr.service.OrderItemQueryService;

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
 * Integration tests for the {@link OrderItemResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrderItemResourceIT {

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;
    private static final Integer SMALLER_SEQUENCE_NO = 1 - 1;

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CANCEL_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_CANCEL_QUANTITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_CANCEL_QUANTITY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SELECTED_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_SELECTED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_SELECTED_AMOUNT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_UNIT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_UNIT_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_UNIT_LIST_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_UNIT_LIST_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_UNIT_LIST_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CGST = new BigDecimal(1);
    private static final BigDecimal UPDATED_CGST = new BigDecimal(2);
    private static final BigDecimal SMALLER_CGST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_IGST = new BigDecimal(1);
    private static final BigDecimal UPDATED_IGST = new BigDecimal(2);
    private static final BigDecimal SMALLER_IGST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SGST = new BigDecimal(1);
    private static final BigDecimal UPDATED_SGST = new BigDecimal(2);
    private static final BigDecimal SMALLER_SGST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CGST_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CGST_PERCENTAGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_CGST_PERCENTAGE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_IGST_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_IGST_PERCENTAGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_IGST_PERCENTAGE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SGST_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SGST_PERCENTAGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SGST_PERCENTAGE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_PRICE = new BigDecimal(1 - 1);

    private static final Boolean DEFAULT_IS_MODIFIED_PRICE = false;
    private static final Boolean UPDATED_IS_MODIFIED_PRICE = true;

    private static final ZonedDateTime DEFAULT_ESTIMATED_SHIP_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ESTIMATED_SHIP_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ESTIMATED_SHIP_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_ESTIMATED_DELIVERY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ESTIMATED_DELIVERY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ESTIMATED_DELIVERY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_SHIP_BEFORE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SHIP_BEFORE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_SHIP_BEFORE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_SHIP_AFTER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_SHIP_AFTER_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_SHIP_AFTER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private OrderItemQueryService orderItemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderItemMockMvc;

    private OrderItem orderItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderItem createEntity(EntityManager em) {
        OrderItem orderItem = new OrderItem()
            .sequenceNo(DEFAULT_SEQUENCE_NO)
            .quantity(DEFAULT_QUANTITY)
            .cancelQuantity(DEFAULT_CANCEL_QUANTITY)
            .selectedAmount(DEFAULT_SELECTED_AMOUNT)
            .unitPrice(DEFAULT_UNIT_PRICE)
            .unitListPrice(DEFAULT_UNIT_LIST_PRICE)
            .cgst(DEFAULT_CGST)
            .igst(DEFAULT_IGST)
            .sgst(DEFAULT_SGST)
            .cgstPercentage(DEFAULT_CGST_PERCENTAGE)
            .igstPercentage(DEFAULT_IGST_PERCENTAGE)
            .sgstPercentage(DEFAULT_SGST_PERCENTAGE)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .isModifiedPrice(DEFAULT_IS_MODIFIED_PRICE)
            .estimatedShipDate(DEFAULT_ESTIMATED_SHIP_DATE)
            .estimatedDeliveryDate(DEFAULT_ESTIMATED_DELIVERY_DATE)
            .shipBeforeDate(DEFAULT_SHIP_BEFORE_DATE)
            .shipAfterDate(DEFAULT_SHIP_AFTER_DATE);
        return orderItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderItem createUpdatedEntity(EntityManager em) {
        OrderItem orderItem = new OrderItem()
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .quantity(UPDATED_QUANTITY)
            .cancelQuantity(UPDATED_CANCEL_QUANTITY)
            .selectedAmount(UPDATED_SELECTED_AMOUNT)
            .unitPrice(UPDATED_UNIT_PRICE)
            .unitListPrice(UPDATED_UNIT_LIST_PRICE)
            .cgst(UPDATED_CGST)
            .igst(UPDATED_IGST)
            .sgst(UPDATED_SGST)
            .cgstPercentage(UPDATED_CGST_PERCENTAGE)
            .igstPercentage(UPDATED_IGST_PERCENTAGE)
            .sgstPercentage(UPDATED_SGST_PERCENTAGE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .isModifiedPrice(UPDATED_IS_MODIFIED_PRICE)
            .estimatedShipDate(UPDATED_ESTIMATED_SHIP_DATE)
            .estimatedDeliveryDate(UPDATED_ESTIMATED_DELIVERY_DATE)
            .shipBeforeDate(UPDATED_SHIP_BEFORE_DATE)
            .shipAfterDate(UPDATED_SHIP_AFTER_DATE);
        return orderItem;
    }

    @BeforeEach
    public void initTest() {
        orderItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderItem() throws Exception {
        int databaseSizeBeforeCreate = orderItemRepository.findAll().size();
        // Create the OrderItem
        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderItem)))
            .andExpect(status().isCreated());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeCreate + 1);
        OrderItem testOrderItem = orderItemList.get(orderItemList.size() - 1);
        assertThat(testOrderItem.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
        assertThat(testOrderItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderItem.getCancelQuantity()).isEqualTo(DEFAULT_CANCEL_QUANTITY);
        assertThat(testOrderItem.getSelectedAmount()).isEqualTo(DEFAULT_SELECTED_AMOUNT);
        assertThat(testOrderItem.getUnitPrice()).isEqualTo(DEFAULT_UNIT_PRICE);
        assertThat(testOrderItem.getUnitListPrice()).isEqualTo(DEFAULT_UNIT_LIST_PRICE);
        assertThat(testOrderItem.getCgst()).isEqualTo(DEFAULT_CGST);
        assertThat(testOrderItem.getIgst()).isEqualTo(DEFAULT_IGST);
        assertThat(testOrderItem.getSgst()).isEqualTo(DEFAULT_SGST);
        assertThat(testOrderItem.getCgstPercentage()).isEqualTo(DEFAULT_CGST_PERCENTAGE);
        assertThat(testOrderItem.getIgstPercentage()).isEqualTo(DEFAULT_IGST_PERCENTAGE);
        assertThat(testOrderItem.getSgstPercentage()).isEqualTo(DEFAULT_SGST_PERCENTAGE);
        assertThat(testOrderItem.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testOrderItem.isIsModifiedPrice()).isEqualTo(DEFAULT_IS_MODIFIED_PRICE);
        assertThat(testOrderItem.getEstimatedShipDate()).isEqualTo(DEFAULT_ESTIMATED_SHIP_DATE);
        assertThat(testOrderItem.getEstimatedDeliveryDate()).isEqualTo(DEFAULT_ESTIMATED_DELIVERY_DATE);
        assertThat(testOrderItem.getShipBeforeDate()).isEqualTo(DEFAULT_SHIP_BEFORE_DATE);
        assertThat(testOrderItem.getShipAfterDate()).isEqualTo(DEFAULT_SHIP_AFTER_DATE);
    }

    @Test
    @Transactional
    public void createOrderItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderItemRepository.findAll().size();

        // Create the OrderItem with an existing ID
        orderItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderItemMockMvc.perform(post("/api/order-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderItem)))
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderItems() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList
        restOrderItemMockMvc.perform(get("/api/order-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].cancelQuantity").value(hasItem(DEFAULT_CANCEL_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].selectedAmount").value(hasItem(DEFAULT_SELECTED_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].unitListPrice").value(hasItem(DEFAULT_UNIT_LIST_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].cgst").value(hasItem(DEFAULT_CGST.intValue())))
            .andExpect(jsonPath("$.[*].igst").value(hasItem(DEFAULT_IGST.intValue())))
            .andExpect(jsonPath("$.[*].sgst").value(hasItem(DEFAULT_SGST.intValue())))
            .andExpect(jsonPath("$.[*].cgstPercentage").value(hasItem(DEFAULT_CGST_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].igstPercentage").value(hasItem(DEFAULT_IGST_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].sgstPercentage").value(hasItem(DEFAULT_SGST_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].isModifiedPrice").value(hasItem(DEFAULT_IS_MODIFIED_PRICE.booleanValue())))
            .andExpect(jsonPath("$.[*].estimatedShipDate").value(hasItem(sameInstant(DEFAULT_ESTIMATED_SHIP_DATE))))
            .andExpect(jsonPath("$.[*].estimatedDeliveryDate").value(hasItem(sameInstant(DEFAULT_ESTIMATED_DELIVERY_DATE))))
            .andExpect(jsonPath("$.[*].shipBeforeDate").value(hasItem(sameInstant(DEFAULT_SHIP_BEFORE_DATE))))
            .andExpect(jsonPath("$.[*].shipAfterDate").value(hasItem(sameInstant(DEFAULT_SHIP_AFTER_DATE))));
    }
    
    @Test
    @Transactional
    public void getOrderItem() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get the orderItem
        restOrderItemMockMvc.perform(get("/api/order-items/{id}", orderItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderItem.getId().intValue()))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.cancelQuantity").value(DEFAULT_CANCEL_QUANTITY.intValue()))
            .andExpect(jsonPath("$.selectedAmount").value(DEFAULT_SELECTED_AMOUNT.intValue()))
            .andExpect(jsonPath("$.unitPrice").value(DEFAULT_UNIT_PRICE.intValue()))
            .andExpect(jsonPath("$.unitListPrice").value(DEFAULT_UNIT_LIST_PRICE.intValue()))
            .andExpect(jsonPath("$.cgst").value(DEFAULT_CGST.intValue()))
            .andExpect(jsonPath("$.igst").value(DEFAULT_IGST.intValue()))
            .andExpect(jsonPath("$.sgst").value(DEFAULT_SGST.intValue()))
            .andExpect(jsonPath("$.cgstPercentage").value(DEFAULT_CGST_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.igstPercentage").value(DEFAULT_IGST_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.sgstPercentage").value(DEFAULT_SGST_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.isModifiedPrice").value(DEFAULT_IS_MODIFIED_PRICE.booleanValue()))
            .andExpect(jsonPath("$.estimatedShipDate").value(sameInstant(DEFAULT_ESTIMATED_SHIP_DATE)))
            .andExpect(jsonPath("$.estimatedDeliveryDate").value(sameInstant(DEFAULT_ESTIMATED_DELIVERY_DATE)))
            .andExpect(jsonPath("$.shipBeforeDate").value(sameInstant(DEFAULT_SHIP_BEFORE_DATE)))
            .andExpect(jsonPath("$.shipAfterDate").value(sameInstant(DEFAULT_SHIP_AFTER_DATE)));
    }


    @Test
    @Transactional
    public void getOrderItemsByIdFiltering() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        Long id = orderItem.getId();

        defaultOrderItemShouldBeFound("id.equals=" + id);
        defaultOrderItemShouldNotBeFound("id.notEquals=" + id);

        defaultOrderItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrderItemShouldNotBeFound("id.greaterThan=" + id);

        defaultOrderItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrderItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOrderItemsBySequenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sequenceNo equals to DEFAULT_SEQUENCE_NO
        defaultOrderItemShouldBeFound("sequenceNo.equals=" + DEFAULT_SEQUENCE_NO);

        // Get all the orderItemList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultOrderItemShouldNotBeFound("sequenceNo.equals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySequenceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sequenceNo not equals to DEFAULT_SEQUENCE_NO
        defaultOrderItemShouldNotBeFound("sequenceNo.notEquals=" + DEFAULT_SEQUENCE_NO);

        // Get all the orderItemList where sequenceNo not equals to UPDATED_SEQUENCE_NO
        defaultOrderItemShouldBeFound("sequenceNo.notEquals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySequenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sequenceNo in DEFAULT_SEQUENCE_NO or UPDATED_SEQUENCE_NO
        defaultOrderItemShouldBeFound("sequenceNo.in=" + DEFAULT_SEQUENCE_NO + "," + UPDATED_SEQUENCE_NO);

        // Get all the orderItemList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultOrderItemShouldNotBeFound("sequenceNo.in=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySequenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sequenceNo is not null
        defaultOrderItemShouldBeFound("sequenceNo.specified=true");

        // Get all the orderItemList where sequenceNo is null
        defaultOrderItemShouldNotBeFound("sequenceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySequenceNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sequenceNo is greater than or equal to DEFAULT_SEQUENCE_NO
        defaultOrderItemShouldBeFound("sequenceNo.greaterThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the orderItemList where sequenceNo is greater than or equal to UPDATED_SEQUENCE_NO
        defaultOrderItemShouldNotBeFound("sequenceNo.greaterThanOrEqual=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySequenceNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sequenceNo is less than or equal to DEFAULT_SEQUENCE_NO
        defaultOrderItemShouldBeFound("sequenceNo.lessThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the orderItemList where sequenceNo is less than or equal to SMALLER_SEQUENCE_NO
        defaultOrderItemShouldNotBeFound("sequenceNo.lessThanOrEqual=" + SMALLER_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySequenceNoIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sequenceNo is less than DEFAULT_SEQUENCE_NO
        defaultOrderItemShouldNotBeFound("sequenceNo.lessThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the orderItemList where sequenceNo is less than UPDATED_SEQUENCE_NO
        defaultOrderItemShouldBeFound("sequenceNo.lessThan=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySequenceNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sequenceNo is greater than DEFAULT_SEQUENCE_NO
        defaultOrderItemShouldNotBeFound("sequenceNo.greaterThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the orderItemList where sequenceNo is greater than SMALLER_SEQUENCE_NO
        defaultOrderItemShouldBeFound("sequenceNo.greaterThan=" + SMALLER_SEQUENCE_NO);
    }


    @Test
    @Transactional
    public void getAllOrderItemsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where quantity equals to DEFAULT_QUANTITY
        defaultOrderItemShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the orderItemList where quantity equals to UPDATED_QUANTITY
        defaultOrderItemShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where quantity not equals to DEFAULT_QUANTITY
        defaultOrderItemShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the orderItemList where quantity not equals to UPDATED_QUANTITY
        defaultOrderItemShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultOrderItemShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the orderItemList where quantity equals to UPDATED_QUANTITY
        defaultOrderItemShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where quantity is not null
        defaultOrderItemShouldBeFound("quantity.specified=true");

        // Get all the orderItemList where quantity is null
        defaultOrderItemShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultOrderItemShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the orderItemList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultOrderItemShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultOrderItemShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the orderItemList where quantity is less than or equal to SMALLER_QUANTITY
        defaultOrderItemShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where quantity is less than DEFAULT_QUANTITY
        defaultOrderItemShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the orderItemList where quantity is less than UPDATED_QUANTITY
        defaultOrderItemShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where quantity is greater than DEFAULT_QUANTITY
        defaultOrderItemShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the orderItemList where quantity is greater than SMALLER_QUANTITY
        defaultOrderItemShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllOrderItemsByCancelQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cancelQuantity equals to DEFAULT_CANCEL_QUANTITY
        defaultOrderItemShouldBeFound("cancelQuantity.equals=" + DEFAULT_CANCEL_QUANTITY);

        // Get all the orderItemList where cancelQuantity equals to UPDATED_CANCEL_QUANTITY
        defaultOrderItemShouldNotBeFound("cancelQuantity.equals=" + UPDATED_CANCEL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCancelQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cancelQuantity not equals to DEFAULT_CANCEL_QUANTITY
        defaultOrderItemShouldNotBeFound("cancelQuantity.notEquals=" + DEFAULT_CANCEL_QUANTITY);

        // Get all the orderItemList where cancelQuantity not equals to UPDATED_CANCEL_QUANTITY
        defaultOrderItemShouldBeFound("cancelQuantity.notEquals=" + UPDATED_CANCEL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCancelQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cancelQuantity in DEFAULT_CANCEL_QUANTITY or UPDATED_CANCEL_QUANTITY
        defaultOrderItemShouldBeFound("cancelQuantity.in=" + DEFAULT_CANCEL_QUANTITY + "," + UPDATED_CANCEL_QUANTITY);

        // Get all the orderItemList where cancelQuantity equals to UPDATED_CANCEL_QUANTITY
        defaultOrderItemShouldNotBeFound("cancelQuantity.in=" + UPDATED_CANCEL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCancelQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cancelQuantity is not null
        defaultOrderItemShouldBeFound("cancelQuantity.specified=true");

        // Get all the orderItemList where cancelQuantity is null
        defaultOrderItemShouldNotBeFound("cancelQuantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCancelQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cancelQuantity is greater than or equal to DEFAULT_CANCEL_QUANTITY
        defaultOrderItemShouldBeFound("cancelQuantity.greaterThanOrEqual=" + DEFAULT_CANCEL_QUANTITY);

        // Get all the orderItemList where cancelQuantity is greater than or equal to UPDATED_CANCEL_QUANTITY
        defaultOrderItemShouldNotBeFound("cancelQuantity.greaterThanOrEqual=" + UPDATED_CANCEL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCancelQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cancelQuantity is less than or equal to DEFAULT_CANCEL_QUANTITY
        defaultOrderItemShouldBeFound("cancelQuantity.lessThanOrEqual=" + DEFAULT_CANCEL_QUANTITY);

        // Get all the orderItemList where cancelQuantity is less than or equal to SMALLER_CANCEL_QUANTITY
        defaultOrderItemShouldNotBeFound("cancelQuantity.lessThanOrEqual=" + SMALLER_CANCEL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCancelQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cancelQuantity is less than DEFAULT_CANCEL_QUANTITY
        defaultOrderItemShouldNotBeFound("cancelQuantity.lessThan=" + DEFAULT_CANCEL_QUANTITY);

        // Get all the orderItemList where cancelQuantity is less than UPDATED_CANCEL_QUANTITY
        defaultOrderItemShouldBeFound("cancelQuantity.lessThan=" + UPDATED_CANCEL_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCancelQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cancelQuantity is greater than DEFAULT_CANCEL_QUANTITY
        defaultOrderItemShouldNotBeFound("cancelQuantity.greaterThan=" + DEFAULT_CANCEL_QUANTITY);

        // Get all the orderItemList where cancelQuantity is greater than SMALLER_CANCEL_QUANTITY
        defaultOrderItemShouldBeFound("cancelQuantity.greaterThan=" + SMALLER_CANCEL_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllOrderItemsBySelectedAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where selectedAmount equals to DEFAULT_SELECTED_AMOUNT
        defaultOrderItemShouldBeFound("selectedAmount.equals=" + DEFAULT_SELECTED_AMOUNT);

        // Get all the orderItemList where selectedAmount equals to UPDATED_SELECTED_AMOUNT
        defaultOrderItemShouldNotBeFound("selectedAmount.equals=" + UPDATED_SELECTED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySelectedAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where selectedAmount not equals to DEFAULT_SELECTED_AMOUNT
        defaultOrderItemShouldNotBeFound("selectedAmount.notEquals=" + DEFAULT_SELECTED_AMOUNT);

        // Get all the orderItemList where selectedAmount not equals to UPDATED_SELECTED_AMOUNT
        defaultOrderItemShouldBeFound("selectedAmount.notEquals=" + UPDATED_SELECTED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySelectedAmountIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where selectedAmount in DEFAULT_SELECTED_AMOUNT or UPDATED_SELECTED_AMOUNT
        defaultOrderItemShouldBeFound("selectedAmount.in=" + DEFAULT_SELECTED_AMOUNT + "," + UPDATED_SELECTED_AMOUNT);

        // Get all the orderItemList where selectedAmount equals to UPDATED_SELECTED_AMOUNT
        defaultOrderItemShouldNotBeFound("selectedAmount.in=" + UPDATED_SELECTED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySelectedAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where selectedAmount is not null
        defaultOrderItemShouldBeFound("selectedAmount.specified=true");

        // Get all the orderItemList where selectedAmount is null
        defaultOrderItemShouldNotBeFound("selectedAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySelectedAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where selectedAmount is greater than or equal to DEFAULT_SELECTED_AMOUNT
        defaultOrderItemShouldBeFound("selectedAmount.greaterThanOrEqual=" + DEFAULT_SELECTED_AMOUNT);

        // Get all the orderItemList where selectedAmount is greater than or equal to UPDATED_SELECTED_AMOUNT
        defaultOrderItemShouldNotBeFound("selectedAmount.greaterThanOrEqual=" + UPDATED_SELECTED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySelectedAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where selectedAmount is less than or equal to DEFAULT_SELECTED_AMOUNT
        defaultOrderItemShouldBeFound("selectedAmount.lessThanOrEqual=" + DEFAULT_SELECTED_AMOUNT);

        // Get all the orderItemList where selectedAmount is less than or equal to SMALLER_SELECTED_AMOUNT
        defaultOrderItemShouldNotBeFound("selectedAmount.lessThanOrEqual=" + SMALLER_SELECTED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySelectedAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where selectedAmount is less than DEFAULT_SELECTED_AMOUNT
        defaultOrderItemShouldNotBeFound("selectedAmount.lessThan=" + DEFAULT_SELECTED_AMOUNT);

        // Get all the orderItemList where selectedAmount is less than UPDATED_SELECTED_AMOUNT
        defaultOrderItemShouldBeFound("selectedAmount.lessThan=" + UPDATED_SELECTED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySelectedAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where selectedAmount is greater than DEFAULT_SELECTED_AMOUNT
        defaultOrderItemShouldNotBeFound("selectedAmount.greaterThan=" + DEFAULT_SELECTED_AMOUNT);

        // Get all the orderItemList where selectedAmount is greater than SMALLER_SELECTED_AMOUNT
        defaultOrderItemShouldBeFound("selectedAmount.greaterThan=" + SMALLER_SELECTED_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllOrderItemsByUnitPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where unitPrice equals to DEFAULT_UNIT_PRICE
        defaultOrderItemShouldBeFound("unitPrice.equals=" + DEFAULT_UNIT_PRICE);

        // Get all the orderItemList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultOrderItemShouldNotBeFound("unitPrice.equals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByUnitPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where unitPrice not equals to DEFAULT_UNIT_PRICE
        defaultOrderItemShouldNotBeFound("unitPrice.notEquals=" + DEFAULT_UNIT_PRICE);

        // Get all the orderItemList where unitPrice not equals to UPDATED_UNIT_PRICE
        defaultOrderItemShouldBeFound("unitPrice.notEquals=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByUnitPriceIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where unitPrice in DEFAULT_UNIT_PRICE or UPDATED_UNIT_PRICE
        defaultOrderItemShouldBeFound("unitPrice.in=" + DEFAULT_UNIT_PRICE + "," + UPDATED_UNIT_PRICE);

        // Get all the orderItemList where unitPrice equals to UPDATED_UNIT_PRICE
        defaultOrderItemShouldNotBeFound("unitPrice.in=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByUnitPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where unitPrice is not null
        defaultOrderItemShouldBeFound("unitPrice.specified=true");

        // Get all the orderItemList where unitPrice is null
        defaultOrderItemShouldNotBeFound("unitPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsByUnitPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where unitPrice is greater than or equal to DEFAULT_UNIT_PRICE
        defaultOrderItemShouldBeFound("unitPrice.greaterThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the orderItemList where unitPrice is greater than or equal to UPDATED_UNIT_PRICE
        defaultOrderItemShouldNotBeFound("unitPrice.greaterThanOrEqual=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByUnitPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where unitPrice is less than or equal to DEFAULT_UNIT_PRICE
        defaultOrderItemShouldBeFound("unitPrice.lessThanOrEqual=" + DEFAULT_UNIT_PRICE);

        // Get all the orderItemList where unitPrice is less than or equal to SMALLER_UNIT_PRICE
        defaultOrderItemShouldNotBeFound("unitPrice.lessThanOrEqual=" + SMALLER_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByUnitPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where unitPrice is less than DEFAULT_UNIT_PRICE
        defaultOrderItemShouldNotBeFound("unitPrice.lessThan=" + DEFAULT_UNIT_PRICE);

        // Get all the orderItemList where unitPrice is less than UPDATED_UNIT_PRICE
        defaultOrderItemShouldBeFound("unitPrice.lessThan=" + UPDATED_UNIT_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByUnitPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where unitPrice is greater than DEFAULT_UNIT_PRICE
        defaultOrderItemShouldNotBeFound("unitPrice.greaterThan=" + DEFAULT_UNIT_PRICE);

        // Get all the orderItemList where unitPrice is greater than SMALLER_UNIT_PRICE
        defaultOrderItemShouldBeFound("unitPrice.greaterThan=" + SMALLER_UNIT_PRICE);
    }


    @Test
    @Transactional
    public void getAllOrderItemsByUnitListPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where unitListPrice equals to DEFAULT_UNIT_LIST_PRICE
        defaultOrderItemShouldBeFound("unitListPrice.equals=" + DEFAULT_UNIT_LIST_PRICE);

        // Get all the orderItemList where unitListPrice equals to UPDATED_UNIT_LIST_PRICE
        defaultOrderItemShouldNotBeFound("unitListPrice.equals=" + UPDATED_UNIT_LIST_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByUnitListPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where unitListPrice not equals to DEFAULT_UNIT_LIST_PRICE
        defaultOrderItemShouldNotBeFound("unitListPrice.notEquals=" + DEFAULT_UNIT_LIST_PRICE);

        // Get all the orderItemList where unitListPrice not equals to UPDATED_UNIT_LIST_PRICE
        defaultOrderItemShouldBeFound("unitListPrice.notEquals=" + UPDATED_UNIT_LIST_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByUnitListPriceIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where unitListPrice in DEFAULT_UNIT_LIST_PRICE or UPDATED_UNIT_LIST_PRICE
        defaultOrderItemShouldBeFound("unitListPrice.in=" + DEFAULT_UNIT_LIST_PRICE + "," + UPDATED_UNIT_LIST_PRICE);

        // Get all the orderItemList where unitListPrice equals to UPDATED_UNIT_LIST_PRICE
        defaultOrderItemShouldNotBeFound("unitListPrice.in=" + UPDATED_UNIT_LIST_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByUnitListPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where unitListPrice is not null
        defaultOrderItemShouldBeFound("unitListPrice.specified=true");

        // Get all the orderItemList where unitListPrice is null
        defaultOrderItemShouldNotBeFound("unitListPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsByUnitListPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where unitListPrice is greater than or equal to DEFAULT_UNIT_LIST_PRICE
        defaultOrderItemShouldBeFound("unitListPrice.greaterThanOrEqual=" + DEFAULT_UNIT_LIST_PRICE);

        // Get all the orderItemList where unitListPrice is greater than or equal to UPDATED_UNIT_LIST_PRICE
        defaultOrderItemShouldNotBeFound("unitListPrice.greaterThanOrEqual=" + UPDATED_UNIT_LIST_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByUnitListPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where unitListPrice is less than or equal to DEFAULT_UNIT_LIST_PRICE
        defaultOrderItemShouldBeFound("unitListPrice.lessThanOrEqual=" + DEFAULT_UNIT_LIST_PRICE);

        // Get all the orderItemList where unitListPrice is less than or equal to SMALLER_UNIT_LIST_PRICE
        defaultOrderItemShouldNotBeFound("unitListPrice.lessThanOrEqual=" + SMALLER_UNIT_LIST_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByUnitListPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where unitListPrice is less than DEFAULT_UNIT_LIST_PRICE
        defaultOrderItemShouldNotBeFound("unitListPrice.lessThan=" + DEFAULT_UNIT_LIST_PRICE);

        // Get all the orderItemList where unitListPrice is less than UPDATED_UNIT_LIST_PRICE
        defaultOrderItemShouldBeFound("unitListPrice.lessThan=" + UPDATED_UNIT_LIST_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByUnitListPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where unitListPrice is greater than DEFAULT_UNIT_LIST_PRICE
        defaultOrderItemShouldNotBeFound("unitListPrice.greaterThan=" + DEFAULT_UNIT_LIST_PRICE);

        // Get all the orderItemList where unitListPrice is greater than SMALLER_UNIT_LIST_PRICE
        defaultOrderItemShouldBeFound("unitListPrice.greaterThan=" + SMALLER_UNIT_LIST_PRICE);
    }


    @Test
    @Transactional
    public void getAllOrderItemsByCgstIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cgst equals to DEFAULT_CGST
        defaultOrderItemShouldBeFound("cgst.equals=" + DEFAULT_CGST);

        // Get all the orderItemList where cgst equals to UPDATED_CGST
        defaultOrderItemShouldNotBeFound("cgst.equals=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCgstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cgst not equals to DEFAULT_CGST
        defaultOrderItemShouldNotBeFound("cgst.notEquals=" + DEFAULT_CGST);

        // Get all the orderItemList where cgst not equals to UPDATED_CGST
        defaultOrderItemShouldBeFound("cgst.notEquals=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCgstIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cgst in DEFAULT_CGST or UPDATED_CGST
        defaultOrderItemShouldBeFound("cgst.in=" + DEFAULT_CGST + "," + UPDATED_CGST);

        // Get all the orderItemList where cgst equals to UPDATED_CGST
        defaultOrderItemShouldNotBeFound("cgst.in=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCgstIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cgst is not null
        defaultOrderItemShouldBeFound("cgst.specified=true");

        // Get all the orderItemList where cgst is null
        defaultOrderItemShouldNotBeFound("cgst.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCgstIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cgst is greater than or equal to DEFAULT_CGST
        defaultOrderItemShouldBeFound("cgst.greaterThanOrEqual=" + DEFAULT_CGST);

        // Get all the orderItemList where cgst is greater than or equal to UPDATED_CGST
        defaultOrderItemShouldNotBeFound("cgst.greaterThanOrEqual=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCgstIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cgst is less than or equal to DEFAULT_CGST
        defaultOrderItemShouldBeFound("cgst.lessThanOrEqual=" + DEFAULT_CGST);

        // Get all the orderItemList where cgst is less than or equal to SMALLER_CGST
        defaultOrderItemShouldNotBeFound("cgst.lessThanOrEqual=" + SMALLER_CGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCgstIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cgst is less than DEFAULT_CGST
        defaultOrderItemShouldNotBeFound("cgst.lessThan=" + DEFAULT_CGST);

        // Get all the orderItemList where cgst is less than UPDATED_CGST
        defaultOrderItemShouldBeFound("cgst.lessThan=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCgstIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cgst is greater than DEFAULT_CGST
        defaultOrderItemShouldNotBeFound("cgst.greaterThan=" + DEFAULT_CGST);

        // Get all the orderItemList where cgst is greater than SMALLER_CGST
        defaultOrderItemShouldBeFound("cgst.greaterThan=" + SMALLER_CGST);
    }


    @Test
    @Transactional
    public void getAllOrderItemsByIgstIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where igst equals to DEFAULT_IGST
        defaultOrderItemShouldBeFound("igst.equals=" + DEFAULT_IGST);

        // Get all the orderItemList where igst equals to UPDATED_IGST
        defaultOrderItemShouldNotBeFound("igst.equals=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIgstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where igst not equals to DEFAULT_IGST
        defaultOrderItemShouldNotBeFound("igst.notEquals=" + DEFAULT_IGST);

        // Get all the orderItemList where igst not equals to UPDATED_IGST
        defaultOrderItemShouldBeFound("igst.notEquals=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIgstIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where igst in DEFAULT_IGST or UPDATED_IGST
        defaultOrderItemShouldBeFound("igst.in=" + DEFAULT_IGST + "," + UPDATED_IGST);

        // Get all the orderItemList where igst equals to UPDATED_IGST
        defaultOrderItemShouldNotBeFound("igst.in=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIgstIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where igst is not null
        defaultOrderItemShouldBeFound("igst.specified=true");

        // Get all the orderItemList where igst is null
        defaultOrderItemShouldNotBeFound("igst.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIgstIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where igst is greater than or equal to DEFAULT_IGST
        defaultOrderItemShouldBeFound("igst.greaterThanOrEqual=" + DEFAULT_IGST);

        // Get all the orderItemList where igst is greater than or equal to UPDATED_IGST
        defaultOrderItemShouldNotBeFound("igst.greaterThanOrEqual=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIgstIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where igst is less than or equal to DEFAULT_IGST
        defaultOrderItemShouldBeFound("igst.lessThanOrEqual=" + DEFAULT_IGST);

        // Get all the orderItemList where igst is less than or equal to SMALLER_IGST
        defaultOrderItemShouldNotBeFound("igst.lessThanOrEqual=" + SMALLER_IGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIgstIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where igst is less than DEFAULT_IGST
        defaultOrderItemShouldNotBeFound("igst.lessThan=" + DEFAULT_IGST);

        // Get all the orderItemList where igst is less than UPDATED_IGST
        defaultOrderItemShouldBeFound("igst.lessThan=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIgstIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where igst is greater than DEFAULT_IGST
        defaultOrderItemShouldNotBeFound("igst.greaterThan=" + DEFAULT_IGST);

        // Get all the orderItemList where igst is greater than SMALLER_IGST
        defaultOrderItemShouldBeFound("igst.greaterThan=" + SMALLER_IGST);
    }


    @Test
    @Transactional
    public void getAllOrderItemsBySgstIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sgst equals to DEFAULT_SGST
        defaultOrderItemShouldBeFound("sgst.equals=" + DEFAULT_SGST);

        // Get all the orderItemList where sgst equals to UPDATED_SGST
        defaultOrderItemShouldNotBeFound("sgst.equals=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySgstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sgst not equals to DEFAULT_SGST
        defaultOrderItemShouldNotBeFound("sgst.notEquals=" + DEFAULT_SGST);

        // Get all the orderItemList where sgst not equals to UPDATED_SGST
        defaultOrderItemShouldBeFound("sgst.notEquals=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySgstIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sgst in DEFAULT_SGST or UPDATED_SGST
        defaultOrderItemShouldBeFound("sgst.in=" + DEFAULT_SGST + "," + UPDATED_SGST);

        // Get all the orderItemList where sgst equals to UPDATED_SGST
        defaultOrderItemShouldNotBeFound("sgst.in=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySgstIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sgst is not null
        defaultOrderItemShouldBeFound("sgst.specified=true");

        // Get all the orderItemList where sgst is null
        defaultOrderItemShouldNotBeFound("sgst.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySgstIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sgst is greater than or equal to DEFAULT_SGST
        defaultOrderItemShouldBeFound("sgst.greaterThanOrEqual=" + DEFAULT_SGST);

        // Get all the orderItemList where sgst is greater than or equal to UPDATED_SGST
        defaultOrderItemShouldNotBeFound("sgst.greaterThanOrEqual=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySgstIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sgst is less than or equal to DEFAULT_SGST
        defaultOrderItemShouldBeFound("sgst.lessThanOrEqual=" + DEFAULT_SGST);

        // Get all the orderItemList where sgst is less than or equal to SMALLER_SGST
        defaultOrderItemShouldNotBeFound("sgst.lessThanOrEqual=" + SMALLER_SGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySgstIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sgst is less than DEFAULT_SGST
        defaultOrderItemShouldNotBeFound("sgst.lessThan=" + DEFAULT_SGST);

        // Get all the orderItemList where sgst is less than UPDATED_SGST
        defaultOrderItemShouldBeFound("sgst.lessThan=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySgstIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sgst is greater than DEFAULT_SGST
        defaultOrderItemShouldNotBeFound("sgst.greaterThan=" + DEFAULT_SGST);

        // Get all the orderItemList where sgst is greater than SMALLER_SGST
        defaultOrderItemShouldBeFound("sgst.greaterThan=" + SMALLER_SGST);
    }


    @Test
    @Transactional
    public void getAllOrderItemsByCgstPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cgstPercentage equals to DEFAULT_CGST_PERCENTAGE
        defaultOrderItemShouldBeFound("cgstPercentage.equals=" + DEFAULT_CGST_PERCENTAGE);

        // Get all the orderItemList where cgstPercentage equals to UPDATED_CGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("cgstPercentage.equals=" + UPDATED_CGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCgstPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cgstPercentage not equals to DEFAULT_CGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("cgstPercentage.notEquals=" + DEFAULT_CGST_PERCENTAGE);

        // Get all the orderItemList where cgstPercentage not equals to UPDATED_CGST_PERCENTAGE
        defaultOrderItemShouldBeFound("cgstPercentage.notEquals=" + UPDATED_CGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCgstPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cgstPercentage in DEFAULT_CGST_PERCENTAGE or UPDATED_CGST_PERCENTAGE
        defaultOrderItemShouldBeFound("cgstPercentage.in=" + DEFAULT_CGST_PERCENTAGE + "," + UPDATED_CGST_PERCENTAGE);

        // Get all the orderItemList where cgstPercentage equals to UPDATED_CGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("cgstPercentage.in=" + UPDATED_CGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCgstPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cgstPercentage is not null
        defaultOrderItemShouldBeFound("cgstPercentage.specified=true");

        // Get all the orderItemList where cgstPercentage is null
        defaultOrderItemShouldNotBeFound("cgstPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCgstPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cgstPercentage is greater than or equal to DEFAULT_CGST_PERCENTAGE
        defaultOrderItemShouldBeFound("cgstPercentage.greaterThanOrEqual=" + DEFAULT_CGST_PERCENTAGE);

        // Get all the orderItemList where cgstPercentage is greater than or equal to UPDATED_CGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("cgstPercentage.greaterThanOrEqual=" + UPDATED_CGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCgstPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cgstPercentage is less than or equal to DEFAULT_CGST_PERCENTAGE
        defaultOrderItemShouldBeFound("cgstPercentage.lessThanOrEqual=" + DEFAULT_CGST_PERCENTAGE);

        // Get all the orderItemList where cgstPercentage is less than or equal to SMALLER_CGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("cgstPercentage.lessThanOrEqual=" + SMALLER_CGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCgstPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cgstPercentage is less than DEFAULT_CGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("cgstPercentage.lessThan=" + DEFAULT_CGST_PERCENTAGE);

        // Get all the orderItemList where cgstPercentage is less than UPDATED_CGST_PERCENTAGE
        defaultOrderItemShouldBeFound("cgstPercentage.lessThan=" + UPDATED_CGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByCgstPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where cgstPercentage is greater than DEFAULT_CGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("cgstPercentage.greaterThan=" + DEFAULT_CGST_PERCENTAGE);

        // Get all the orderItemList where cgstPercentage is greater than SMALLER_CGST_PERCENTAGE
        defaultOrderItemShouldBeFound("cgstPercentage.greaterThan=" + SMALLER_CGST_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllOrderItemsByIgstPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where igstPercentage equals to DEFAULT_IGST_PERCENTAGE
        defaultOrderItemShouldBeFound("igstPercentage.equals=" + DEFAULT_IGST_PERCENTAGE);

        // Get all the orderItemList where igstPercentage equals to UPDATED_IGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("igstPercentage.equals=" + UPDATED_IGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIgstPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where igstPercentage not equals to DEFAULT_IGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("igstPercentage.notEquals=" + DEFAULT_IGST_PERCENTAGE);

        // Get all the orderItemList where igstPercentage not equals to UPDATED_IGST_PERCENTAGE
        defaultOrderItemShouldBeFound("igstPercentage.notEquals=" + UPDATED_IGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIgstPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where igstPercentage in DEFAULT_IGST_PERCENTAGE or UPDATED_IGST_PERCENTAGE
        defaultOrderItemShouldBeFound("igstPercentage.in=" + DEFAULT_IGST_PERCENTAGE + "," + UPDATED_IGST_PERCENTAGE);

        // Get all the orderItemList where igstPercentage equals to UPDATED_IGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("igstPercentage.in=" + UPDATED_IGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIgstPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where igstPercentage is not null
        defaultOrderItemShouldBeFound("igstPercentage.specified=true");

        // Get all the orderItemList where igstPercentage is null
        defaultOrderItemShouldNotBeFound("igstPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIgstPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where igstPercentage is greater than or equal to DEFAULT_IGST_PERCENTAGE
        defaultOrderItemShouldBeFound("igstPercentage.greaterThanOrEqual=" + DEFAULT_IGST_PERCENTAGE);

        // Get all the orderItemList where igstPercentage is greater than or equal to UPDATED_IGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("igstPercentage.greaterThanOrEqual=" + UPDATED_IGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIgstPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where igstPercentage is less than or equal to DEFAULT_IGST_PERCENTAGE
        defaultOrderItemShouldBeFound("igstPercentage.lessThanOrEqual=" + DEFAULT_IGST_PERCENTAGE);

        // Get all the orderItemList where igstPercentage is less than or equal to SMALLER_IGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("igstPercentage.lessThanOrEqual=" + SMALLER_IGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIgstPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where igstPercentage is less than DEFAULT_IGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("igstPercentage.lessThan=" + DEFAULT_IGST_PERCENTAGE);

        // Get all the orderItemList where igstPercentage is less than UPDATED_IGST_PERCENTAGE
        defaultOrderItemShouldBeFound("igstPercentage.lessThan=" + UPDATED_IGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIgstPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where igstPercentage is greater than DEFAULT_IGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("igstPercentage.greaterThan=" + DEFAULT_IGST_PERCENTAGE);

        // Get all the orderItemList where igstPercentage is greater than SMALLER_IGST_PERCENTAGE
        defaultOrderItemShouldBeFound("igstPercentage.greaterThan=" + SMALLER_IGST_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllOrderItemsBySgstPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sgstPercentage equals to DEFAULT_SGST_PERCENTAGE
        defaultOrderItemShouldBeFound("sgstPercentage.equals=" + DEFAULT_SGST_PERCENTAGE);

        // Get all the orderItemList where sgstPercentage equals to UPDATED_SGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("sgstPercentage.equals=" + UPDATED_SGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySgstPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sgstPercentage not equals to DEFAULT_SGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("sgstPercentage.notEquals=" + DEFAULT_SGST_PERCENTAGE);

        // Get all the orderItemList where sgstPercentage not equals to UPDATED_SGST_PERCENTAGE
        defaultOrderItemShouldBeFound("sgstPercentage.notEquals=" + UPDATED_SGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySgstPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sgstPercentage in DEFAULT_SGST_PERCENTAGE or UPDATED_SGST_PERCENTAGE
        defaultOrderItemShouldBeFound("sgstPercentage.in=" + DEFAULT_SGST_PERCENTAGE + "," + UPDATED_SGST_PERCENTAGE);

        // Get all the orderItemList where sgstPercentage equals to UPDATED_SGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("sgstPercentage.in=" + UPDATED_SGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySgstPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sgstPercentage is not null
        defaultOrderItemShouldBeFound("sgstPercentage.specified=true");

        // Get all the orderItemList where sgstPercentage is null
        defaultOrderItemShouldNotBeFound("sgstPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySgstPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sgstPercentage is greater than or equal to DEFAULT_SGST_PERCENTAGE
        defaultOrderItemShouldBeFound("sgstPercentage.greaterThanOrEqual=" + DEFAULT_SGST_PERCENTAGE);

        // Get all the orderItemList where sgstPercentage is greater than or equal to UPDATED_SGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("sgstPercentage.greaterThanOrEqual=" + UPDATED_SGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySgstPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sgstPercentage is less than or equal to DEFAULT_SGST_PERCENTAGE
        defaultOrderItemShouldBeFound("sgstPercentage.lessThanOrEqual=" + DEFAULT_SGST_PERCENTAGE);

        // Get all the orderItemList where sgstPercentage is less than or equal to SMALLER_SGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("sgstPercentage.lessThanOrEqual=" + SMALLER_SGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySgstPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sgstPercentage is less than DEFAULT_SGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("sgstPercentage.lessThan=" + DEFAULT_SGST_PERCENTAGE);

        // Get all the orderItemList where sgstPercentage is less than UPDATED_SGST_PERCENTAGE
        defaultOrderItemShouldBeFound("sgstPercentage.lessThan=" + UPDATED_SGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsBySgstPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where sgstPercentage is greater than DEFAULT_SGST_PERCENTAGE
        defaultOrderItemShouldNotBeFound("sgstPercentage.greaterThan=" + DEFAULT_SGST_PERCENTAGE);

        // Get all the orderItemList where sgstPercentage is greater than SMALLER_SGST_PERCENTAGE
        defaultOrderItemShouldBeFound("sgstPercentage.greaterThan=" + SMALLER_SGST_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllOrderItemsByTotalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where totalPrice equals to DEFAULT_TOTAL_PRICE
        defaultOrderItemShouldBeFound("totalPrice.equals=" + DEFAULT_TOTAL_PRICE);

        // Get all the orderItemList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultOrderItemShouldNotBeFound("totalPrice.equals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByTotalPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where totalPrice not equals to DEFAULT_TOTAL_PRICE
        defaultOrderItemShouldNotBeFound("totalPrice.notEquals=" + DEFAULT_TOTAL_PRICE);

        // Get all the orderItemList where totalPrice not equals to UPDATED_TOTAL_PRICE
        defaultOrderItemShouldBeFound("totalPrice.notEquals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByTotalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where totalPrice in DEFAULT_TOTAL_PRICE or UPDATED_TOTAL_PRICE
        defaultOrderItemShouldBeFound("totalPrice.in=" + DEFAULT_TOTAL_PRICE + "," + UPDATED_TOTAL_PRICE);

        // Get all the orderItemList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultOrderItemShouldNotBeFound("totalPrice.in=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByTotalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where totalPrice is not null
        defaultOrderItemShouldBeFound("totalPrice.specified=true");

        // Get all the orderItemList where totalPrice is null
        defaultOrderItemShouldNotBeFound("totalPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsByTotalPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where totalPrice is greater than or equal to DEFAULT_TOTAL_PRICE
        defaultOrderItemShouldBeFound("totalPrice.greaterThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the orderItemList where totalPrice is greater than or equal to UPDATED_TOTAL_PRICE
        defaultOrderItemShouldNotBeFound("totalPrice.greaterThanOrEqual=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByTotalPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where totalPrice is less than or equal to DEFAULT_TOTAL_PRICE
        defaultOrderItemShouldBeFound("totalPrice.lessThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the orderItemList where totalPrice is less than or equal to SMALLER_TOTAL_PRICE
        defaultOrderItemShouldNotBeFound("totalPrice.lessThanOrEqual=" + SMALLER_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByTotalPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where totalPrice is less than DEFAULT_TOTAL_PRICE
        defaultOrderItemShouldNotBeFound("totalPrice.lessThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the orderItemList where totalPrice is less than UPDATED_TOTAL_PRICE
        defaultOrderItemShouldBeFound("totalPrice.lessThan=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByTotalPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where totalPrice is greater than DEFAULT_TOTAL_PRICE
        defaultOrderItemShouldNotBeFound("totalPrice.greaterThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the orderItemList where totalPrice is greater than SMALLER_TOTAL_PRICE
        defaultOrderItemShouldBeFound("totalPrice.greaterThan=" + SMALLER_TOTAL_PRICE);
    }


    @Test
    @Transactional
    public void getAllOrderItemsByIsModifiedPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where isModifiedPrice equals to DEFAULT_IS_MODIFIED_PRICE
        defaultOrderItemShouldBeFound("isModifiedPrice.equals=" + DEFAULT_IS_MODIFIED_PRICE);

        // Get all the orderItemList where isModifiedPrice equals to UPDATED_IS_MODIFIED_PRICE
        defaultOrderItemShouldNotBeFound("isModifiedPrice.equals=" + UPDATED_IS_MODIFIED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIsModifiedPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where isModifiedPrice not equals to DEFAULT_IS_MODIFIED_PRICE
        defaultOrderItemShouldNotBeFound("isModifiedPrice.notEquals=" + DEFAULT_IS_MODIFIED_PRICE);

        // Get all the orderItemList where isModifiedPrice not equals to UPDATED_IS_MODIFIED_PRICE
        defaultOrderItemShouldBeFound("isModifiedPrice.notEquals=" + UPDATED_IS_MODIFIED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIsModifiedPriceIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where isModifiedPrice in DEFAULT_IS_MODIFIED_PRICE or UPDATED_IS_MODIFIED_PRICE
        defaultOrderItemShouldBeFound("isModifiedPrice.in=" + DEFAULT_IS_MODIFIED_PRICE + "," + UPDATED_IS_MODIFIED_PRICE);

        // Get all the orderItemList where isModifiedPrice equals to UPDATED_IS_MODIFIED_PRICE
        defaultOrderItemShouldNotBeFound("isModifiedPrice.in=" + UPDATED_IS_MODIFIED_PRICE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByIsModifiedPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where isModifiedPrice is not null
        defaultOrderItemShouldBeFound("isModifiedPrice.specified=true");

        // Get all the orderItemList where isModifiedPrice is null
        defaultOrderItemShouldNotBeFound("isModifiedPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsByEstimatedShipDateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where estimatedShipDate equals to DEFAULT_ESTIMATED_SHIP_DATE
        defaultOrderItemShouldBeFound("estimatedShipDate.equals=" + DEFAULT_ESTIMATED_SHIP_DATE);

        // Get all the orderItemList where estimatedShipDate equals to UPDATED_ESTIMATED_SHIP_DATE
        defaultOrderItemShouldNotBeFound("estimatedShipDate.equals=" + UPDATED_ESTIMATED_SHIP_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByEstimatedShipDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where estimatedShipDate not equals to DEFAULT_ESTIMATED_SHIP_DATE
        defaultOrderItemShouldNotBeFound("estimatedShipDate.notEquals=" + DEFAULT_ESTIMATED_SHIP_DATE);

        // Get all the orderItemList where estimatedShipDate not equals to UPDATED_ESTIMATED_SHIP_DATE
        defaultOrderItemShouldBeFound("estimatedShipDate.notEquals=" + UPDATED_ESTIMATED_SHIP_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByEstimatedShipDateIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where estimatedShipDate in DEFAULT_ESTIMATED_SHIP_DATE or UPDATED_ESTIMATED_SHIP_DATE
        defaultOrderItemShouldBeFound("estimatedShipDate.in=" + DEFAULT_ESTIMATED_SHIP_DATE + "," + UPDATED_ESTIMATED_SHIP_DATE);

        // Get all the orderItemList where estimatedShipDate equals to UPDATED_ESTIMATED_SHIP_DATE
        defaultOrderItemShouldNotBeFound("estimatedShipDate.in=" + UPDATED_ESTIMATED_SHIP_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByEstimatedShipDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where estimatedShipDate is not null
        defaultOrderItemShouldBeFound("estimatedShipDate.specified=true");

        // Get all the orderItemList where estimatedShipDate is null
        defaultOrderItemShouldNotBeFound("estimatedShipDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsByEstimatedShipDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where estimatedShipDate is greater than or equal to DEFAULT_ESTIMATED_SHIP_DATE
        defaultOrderItemShouldBeFound("estimatedShipDate.greaterThanOrEqual=" + DEFAULT_ESTIMATED_SHIP_DATE);

        // Get all the orderItemList where estimatedShipDate is greater than or equal to UPDATED_ESTIMATED_SHIP_DATE
        defaultOrderItemShouldNotBeFound("estimatedShipDate.greaterThanOrEqual=" + UPDATED_ESTIMATED_SHIP_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByEstimatedShipDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where estimatedShipDate is less than or equal to DEFAULT_ESTIMATED_SHIP_DATE
        defaultOrderItemShouldBeFound("estimatedShipDate.lessThanOrEqual=" + DEFAULT_ESTIMATED_SHIP_DATE);

        // Get all the orderItemList where estimatedShipDate is less than or equal to SMALLER_ESTIMATED_SHIP_DATE
        defaultOrderItemShouldNotBeFound("estimatedShipDate.lessThanOrEqual=" + SMALLER_ESTIMATED_SHIP_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByEstimatedShipDateIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where estimatedShipDate is less than DEFAULT_ESTIMATED_SHIP_DATE
        defaultOrderItemShouldNotBeFound("estimatedShipDate.lessThan=" + DEFAULT_ESTIMATED_SHIP_DATE);

        // Get all the orderItemList where estimatedShipDate is less than UPDATED_ESTIMATED_SHIP_DATE
        defaultOrderItemShouldBeFound("estimatedShipDate.lessThan=" + UPDATED_ESTIMATED_SHIP_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByEstimatedShipDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where estimatedShipDate is greater than DEFAULT_ESTIMATED_SHIP_DATE
        defaultOrderItemShouldNotBeFound("estimatedShipDate.greaterThan=" + DEFAULT_ESTIMATED_SHIP_DATE);

        // Get all the orderItemList where estimatedShipDate is greater than SMALLER_ESTIMATED_SHIP_DATE
        defaultOrderItemShouldBeFound("estimatedShipDate.greaterThan=" + SMALLER_ESTIMATED_SHIP_DATE);
    }


    @Test
    @Transactional
    public void getAllOrderItemsByEstimatedDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where estimatedDeliveryDate equals to DEFAULT_ESTIMATED_DELIVERY_DATE
        defaultOrderItemShouldBeFound("estimatedDeliveryDate.equals=" + DEFAULT_ESTIMATED_DELIVERY_DATE);

        // Get all the orderItemList where estimatedDeliveryDate equals to UPDATED_ESTIMATED_DELIVERY_DATE
        defaultOrderItemShouldNotBeFound("estimatedDeliveryDate.equals=" + UPDATED_ESTIMATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByEstimatedDeliveryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where estimatedDeliveryDate not equals to DEFAULT_ESTIMATED_DELIVERY_DATE
        defaultOrderItemShouldNotBeFound("estimatedDeliveryDate.notEquals=" + DEFAULT_ESTIMATED_DELIVERY_DATE);

        // Get all the orderItemList where estimatedDeliveryDate not equals to UPDATED_ESTIMATED_DELIVERY_DATE
        defaultOrderItemShouldBeFound("estimatedDeliveryDate.notEquals=" + UPDATED_ESTIMATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByEstimatedDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where estimatedDeliveryDate in DEFAULT_ESTIMATED_DELIVERY_DATE or UPDATED_ESTIMATED_DELIVERY_DATE
        defaultOrderItemShouldBeFound("estimatedDeliveryDate.in=" + DEFAULT_ESTIMATED_DELIVERY_DATE + "," + UPDATED_ESTIMATED_DELIVERY_DATE);

        // Get all the orderItemList where estimatedDeliveryDate equals to UPDATED_ESTIMATED_DELIVERY_DATE
        defaultOrderItemShouldNotBeFound("estimatedDeliveryDate.in=" + UPDATED_ESTIMATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByEstimatedDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where estimatedDeliveryDate is not null
        defaultOrderItemShouldBeFound("estimatedDeliveryDate.specified=true");

        // Get all the orderItemList where estimatedDeliveryDate is null
        defaultOrderItemShouldNotBeFound("estimatedDeliveryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsByEstimatedDeliveryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where estimatedDeliveryDate is greater than or equal to DEFAULT_ESTIMATED_DELIVERY_DATE
        defaultOrderItemShouldBeFound("estimatedDeliveryDate.greaterThanOrEqual=" + DEFAULT_ESTIMATED_DELIVERY_DATE);

        // Get all the orderItemList where estimatedDeliveryDate is greater than or equal to UPDATED_ESTIMATED_DELIVERY_DATE
        defaultOrderItemShouldNotBeFound("estimatedDeliveryDate.greaterThanOrEqual=" + UPDATED_ESTIMATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByEstimatedDeliveryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where estimatedDeliveryDate is less than or equal to DEFAULT_ESTIMATED_DELIVERY_DATE
        defaultOrderItemShouldBeFound("estimatedDeliveryDate.lessThanOrEqual=" + DEFAULT_ESTIMATED_DELIVERY_DATE);

        // Get all the orderItemList where estimatedDeliveryDate is less than or equal to SMALLER_ESTIMATED_DELIVERY_DATE
        defaultOrderItemShouldNotBeFound("estimatedDeliveryDate.lessThanOrEqual=" + SMALLER_ESTIMATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByEstimatedDeliveryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where estimatedDeliveryDate is less than DEFAULT_ESTIMATED_DELIVERY_DATE
        defaultOrderItemShouldNotBeFound("estimatedDeliveryDate.lessThan=" + DEFAULT_ESTIMATED_DELIVERY_DATE);

        // Get all the orderItemList where estimatedDeliveryDate is less than UPDATED_ESTIMATED_DELIVERY_DATE
        defaultOrderItemShouldBeFound("estimatedDeliveryDate.lessThan=" + UPDATED_ESTIMATED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByEstimatedDeliveryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where estimatedDeliveryDate is greater than DEFAULT_ESTIMATED_DELIVERY_DATE
        defaultOrderItemShouldNotBeFound("estimatedDeliveryDate.greaterThan=" + DEFAULT_ESTIMATED_DELIVERY_DATE);

        // Get all the orderItemList where estimatedDeliveryDate is greater than SMALLER_ESTIMATED_DELIVERY_DATE
        defaultOrderItemShouldBeFound("estimatedDeliveryDate.greaterThan=" + SMALLER_ESTIMATED_DELIVERY_DATE);
    }


    @Test
    @Transactional
    public void getAllOrderItemsByShipBeforeDateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where shipBeforeDate equals to DEFAULT_SHIP_BEFORE_DATE
        defaultOrderItemShouldBeFound("shipBeforeDate.equals=" + DEFAULT_SHIP_BEFORE_DATE);

        // Get all the orderItemList where shipBeforeDate equals to UPDATED_SHIP_BEFORE_DATE
        defaultOrderItemShouldNotBeFound("shipBeforeDate.equals=" + UPDATED_SHIP_BEFORE_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByShipBeforeDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where shipBeforeDate not equals to DEFAULT_SHIP_BEFORE_DATE
        defaultOrderItemShouldNotBeFound("shipBeforeDate.notEquals=" + DEFAULT_SHIP_BEFORE_DATE);

        // Get all the orderItemList where shipBeforeDate not equals to UPDATED_SHIP_BEFORE_DATE
        defaultOrderItemShouldBeFound("shipBeforeDate.notEquals=" + UPDATED_SHIP_BEFORE_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByShipBeforeDateIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where shipBeforeDate in DEFAULT_SHIP_BEFORE_DATE or UPDATED_SHIP_BEFORE_DATE
        defaultOrderItemShouldBeFound("shipBeforeDate.in=" + DEFAULT_SHIP_BEFORE_DATE + "," + UPDATED_SHIP_BEFORE_DATE);

        // Get all the orderItemList where shipBeforeDate equals to UPDATED_SHIP_BEFORE_DATE
        defaultOrderItemShouldNotBeFound("shipBeforeDate.in=" + UPDATED_SHIP_BEFORE_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByShipBeforeDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where shipBeforeDate is not null
        defaultOrderItemShouldBeFound("shipBeforeDate.specified=true");

        // Get all the orderItemList where shipBeforeDate is null
        defaultOrderItemShouldNotBeFound("shipBeforeDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsByShipBeforeDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where shipBeforeDate is greater than or equal to DEFAULT_SHIP_BEFORE_DATE
        defaultOrderItemShouldBeFound("shipBeforeDate.greaterThanOrEqual=" + DEFAULT_SHIP_BEFORE_DATE);

        // Get all the orderItemList where shipBeforeDate is greater than or equal to UPDATED_SHIP_BEFORE_DATE
        defaultOrderItemShouldNotBeFound("shipBeforeDate.greaterThanOrEqual=" + UPDATED_SHIP_BEFORE_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByShipBeforeDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where shipBeforeDate is less than or equal to DEFAULT_SHIP_BEFORE_DATE
        defaultOrderItemShouldBeFound("shipBeforeDate.lessThanOrEqual=" + DEFAULT_SHIP_BEFORE_DATE);

        // Get all the orderItemList where shipBeforeDate is less than or equal to SMALLER_SHIP_BEFORE_DATE
        defaultOrderItemShouldNotBeFound("shipBeforeDate.lessThanOrEqual=" + SMALLER_SHIP_BEFORE_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByShipBeforeDateIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where shipBeforeDate is less than DEFAULT_SHIP_BEFORE_DATE
        defaultOrderItemShouldNotBeFound("shipBeforeDate.lessThan=" + DEFAULT_SHIP_BEFORE_DATE);

        // Get all the orderItemList where shipBeforeDate is less than UPDATED_SHIP_BEFORE_DATE
        defaultOrderItemShouldBeFound("shipBeforeDate.lessThan=" + UPDATED_SHIP_BEFORE_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByShipBeforeDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where shipBeforeDate is greater than DEFAULT_SHIP_BEFORE_DATE
        defaultOrderItemShouldNotBeFound("shipBeforeDate.greaterThan=" + DEFAULT_SHIP_BEFORE_DATE);

        // Get all the orderItemList where shipBeforeDate is greater than SMALLER_SHIP_BEFORE_DATE
        defaultOrderItemShouldBeFound("shipBeforeDate.greaterThan=" + SMALLER_SHIP_BEFORE_DATE);
    }


    @Test
    @Transactional
    public void getAllOrderItemsByShipAfterDateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where shipAfterDate equals to DEFAULT_SHIP_AFTER_DATE
        defaultOrderItemShouldBeFound("shipAfterDate.equals=" + DEFAULT_SHIP_AFTER_DATE);

        // Get all the orderItemList where shipAfterDate equals to UPDATED_SHIP_AFTER_DATE
        defaultOrderItemShouldNotBeFound("shipAfterDate.equals=" + UPDATED_SHIP_AFTER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByShipAfterDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where shipAfterDate not equals to DEFAULT_SHIP_AFTER_DATE
        defaultOrderItemShouldNotBeFound("shipAfterDate.notEquals=" + DEFAULT_SHIP_AFTER_DATE);

        // Get all the orderItemList where shipAfterDate not equals to UPDATED_SHIP_AFTER_DATE
        defaultOrderItemShouldBeFound("shipAfterDate.notEquals=" + UPDATED_SHIP_AFTER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByShipAfterDateIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where shipAfterDate in DEFAULT_SHIP_AFTER_DATE or UPDATED_SHIP_AFTER_DATE
        defaultOrderItemShouldBeFound("shipAfterDate.in=" + DEFAULT_SHIP_AFTER_DATE + "," + UPDATED_SHIP_AFTER_DATE);

        // Get all the orderItemList where shipAfterDate equals to UPDATED_SHIP_AFTER_DATE
        defaultOrderItemShouldNotBeFound("shipAfterDate.in=" + UPDATED_SHIP_AFTER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByShipAfterDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where shipAfterDate is not null
        defaultOrderItemShouldBeFound("shipAfterDate.specified=true");

        // Get all the orderItemList where shipAfterDate is null
        defaultOrderItemShouldNotBeFound("shipAfterDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemsByShipAfterDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where shipAfterDate is greater than or equal to DEFAULT_SHIP_AFTER_DATE
        defaultOrderItemShouldBeFound("shipAfterDate.greaterThanOrEqual=" + DEFAULT_SHIP_AFTER_DATE);

        // Get all the orderItemList where shipAfterDate is greater than or equal to UPDATED_SHIP_AFTER_DATE
        defaultOrderItemShouldNotBeFound("shipAfterDate.greaterThanOrEqual=" + UPDATED_SHIP_AFTER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByShipAfterDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where shipAfterDate is less than or equal to DEFAULT_SHIP_AFTER_DATE
        defaultOrderItemShouldBeFound("shipAfterDate.lessThanOrEqual=" + DEFAULT_SHIP_AFTER_DATE);

        // Get all the orderItemList where shipAfterDate is less than or equal to SMALLER_SHIP_AFTER_DATE
        defaultOrderItemShouldNotBeFound("shipAfterDate.lessThanOrEqual=" + SMALLER_SHIP_AFTER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByShipAfterDateIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where shipAfterDate is less than DEFAULT_SHIP_AFTER_DATE
        defaultOrderItemShouldNotBeFound("shipAfterDate.lessThan=" + DEFAULT_SHIP_AFTER_DATE);

        // Get all the orderItemList where shipAfterDate is less than UPDATED_SHIP_AFTER_DATE
        defaultOrderItemShouldBeFound("shipAfterDate.lessThan=" + UPDATED_SHIP_AFTER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderItemsByShipAfterDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);

        // Get all the orderItemList where shipAfterDate is greater than DEFAULT_SHIP_AFTER_DATE
        defaultOrderItemShouldNotBeFound("shipAfterDate.greaterThan=" + DEFAULT_SHIP_AFTER_DATE);

        // Get all the orderItemList where shipAfterDate is greater than SMALLER_SHIP_AFTER_DATE
        defaultOrderItemShouldBeFound("shipAfterDate.greaterThan=" + SMALLER_SHIP_AFTER_DATE);
    }


    @Test
    @Transactional
    public void getAllOrderItemsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);
        Order order = OrderResourceIT.createEntity(em);
        em.persist(order);
        em.flush();
        orderItem.setOrder(order);
        orderItemRepository.saveAndFlush(orderItem);
        Long orderId = order.getId();

        // Get all the orderItemList where order equals to orderId
        defaultOrderItemShouldBeFound("orderId.equals=" + orderId);

        // Get all the orderItemList where order equals to orderId + 1
        defaultOrderItemShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderItemsByOrderItemTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);
        OrderItemType orderItemType = OrderItemTypeResourceIT.createEntity(em);
        em.persist(orderItemType);
        em.flush();
        orderItem.setOrderItemType(orderItemType);
        orderItemRepository.saveAndFlush(orderItem);
        Long orderItemTypeId = orderItemType.getId();

        // Get all the orderItemList where orderItemType equals to orderItemTypeId
        defaultOrderItemShouldBeFound("orderItemTypeId.equals=" + orderItemTypeId);

        // Get all the orderItemList where orderItemType equals to orderItemTypeId + 1
        defaultOrderItemShouldNotBeFound("orderItemTypeId.equals=" + (orderItemTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderItemsByFromInventoryItemIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);
        InventoryItem fromInventoryItem = InventoryItemResourceIT.createEntity(em);
        em.persist(fromInventoryItem);
        em.flush();
        orderItem.setFromInventoryItem(fromInventoryItem);
        orderItemRepository.saveAndFlush(orderItem);
        Long fromInventoryItemId = fromInventoryItem.getId();

        // Get all the orderItemList where fromInventoryItem equals to fromInventoryItemId
        defaultOrderItemShouldBeFound("fromInventoryItemId.equals=" + fromInventoryItemId);

        // Get all the orderItemList where fromInventoryItem equals to fromInventoryItemId + 1
        defaultOrderItemShouldNotBeFound("fromInventoryItemId.equals=" + (fromInventoryItemId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderItemsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        orderItem.setProduct(product);
        orderItemRepository.saveAndFlush(orderItem);
        Long productId = product.getId();

        // Get all the orderItemList where product equals to productId
        defaultOrderItemShouldBeFound("productId.equals=" + productId);

        // Get all the orderItemList where product equals to productId + 1
        defaultOrderItemShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderItemsBySupplierProductIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);
        SupplierProduct supplierProduct = SupplierProductResourceIT.createEntity(em);
        em.persist(supplierProduct);
        em.flush();
        orderItem.setSupplierProduct(supplierProduct);
        orderItemRepository.saveAndFlush(orderItem);
        Long supplierProductId = supplierProduct.getId();

        // Get all the orderItemList where supplierProduct equals to supplierProductId
        defaultOrderItemShouldBeFound("supplierProductId.equals=" + supplierProductId);

        // Get all the orderItemList where supplierProduct equals to supplierProductId + 1
        defaultOrderItemShouldNotBeFound("supplierProductId.equals=" + (supplierProductId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderItemsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        orderItem.setStatus(status);
        orderItemRepository.saveAndFlush(orderItem);
        Long statusId = status.getId();

        // Get all the orderItemList where status equals to statusId
        defaultOrderItemShouldBeFound("statusId.equals=" + statusId);

        // Get all the orderItemList where status equals to statusId + 1
        defaultOrderItemShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderItemsByTaxAuthRateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemRepository.saveAndFlush(orderItem);
        TaxAuthorityRateType taxAuthRate = TaxAuthorityRateTypeResourceIT.createEntity(em);
        em.persist(taxAuthRate);
        em.flush();
        orderItem.setTaxAuthRate(taxAuthRate);
        orderItemRepository.saveAndFlush(orderItem);
        Long taxAuthRateId = taxAuthRate.getId();

        // Get all the orderItemList where taxAuthRate equals to taxAuthRateId
        defaultOrderItemShouldBeFound("taxAuthRateId.equals=" + taxAuthRateId);

        // Get all the orderItemList where taxAuthRate equals to taxAuthRateId + 1
        defaultOrderItemShouldNotBeFound("taxAuthRateId.equals=" + (taxAuthRateId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrderItemShouldBeFound(String filter) throws Exception {
        restOrderItemMockMvc.perform(get("/api/order-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].cancelQuantity").value(hasItem(DEFAULT_CANCEL_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].selectedAmount").value(hasItem(DEFAULT_SELECTED_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].unitPrice").value(hasItem(DEFAULT_UNIT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].unitListPrice").value(hasItem(DEFAULT_UNIT_LIST_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].cgst").value(hasItem(DEFAULT_CGST.intValue())))
            .andExpect(jsonPath("$.[*].igst").value(hasItem(DEFAULT_IGST.intValue())))
            .andExpect(jsonPath("$.[*].sgst").value(hasItem(DEFAULT_SGST.intValue())))
            .andExpect(jsonPath("$.[*].cgstPercentage").value(hasItem(DEFAULT_CGST_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].igstPercentage").value(hasItem(DEFAULT_IGST_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].sgstPercentage").value(hasItem(DEFAULT_SGST_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].isModifiedPrice").value(hasItem(DEFAULT_IS_MODIFIED_PRICE.booleanValue())))
            .andExpect(jsonPath("$.[*].estimatedShipDate").value(hasItem(sameInstant(DEFAULT_ESTIMATED_SHIP_DATE))))
            .andExpect(jsonPath("$.[*].estimatedDeliveryDate").value(hasItem(sameInstant(DEFAULT_ESTIMATED_DELIVERY_DATE))))
            .andExpect(jsonPath("$.[*].shipBeforeDate").value(hasItem(sameInstant(DEFAULT_SHIP_BEFORE_DATE))))
            .andExpect(jsonPath("$.[*].shipAfterDate").value(hasItem(sameInstant(DEFAULT_SHIP_AFTER_DATE))));

        // Check, that the count call also returns 1
        restOrderItemMockMvc.perform(get("/api/order-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrderItemShouldNotBeFound(String filter) throws Exception {
        restOrderItemMockMvc.perform(get("/api/order-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrderItemMockMvc.perform(get("/api/order-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOrderItem() throws Exception {
        // Get the orderItem
        restOrderItemMockMvc.perform(get("/api/order-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderItem() throws Exception {
        // Initialize the database
        orderItemService.save(orderItem);

        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();

        // Update the orderItem
        OrderItem updatedOrderItem = orderItemRepository.findById(orderItem.getId()).get();
        // Disconnect from session so that the updates on updatedOrderItem are not directly saved in db
        em.detach(updatedOrderItem);
        updatedOrderItem
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .quantity(UPDATED_QUANTITY)
            .cancelQuantity(UPDATED_CANCEL_QUANTITY)
            .selectedAmount(UPDATED_SELECTED_AMOUNT)
            .unitPrice(UPDATED_UNIT_PRICE)
            .unitListPrice(UPDATED_UNIT_LIST_PRICE)
            .cgst(UPDATED_CGST)
            .igst(UPDATED_IGST)
            .sgst(UPDATED_SGST)
            .cgstPercentage(UPDATED_CGST_PERCENTAGE)
            .igstPercentage(UPDATED_IGST_PERCENTAGE)
            .sgstPercentage(UPDATED_SGST_PERCENTAGE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .isModifiedPrice(UPDATED_IS_MODIFIED_PRICE)
            .estimatedShipDate(UPDATED_ESTIMATED_SHIP_DATE)
            .estimatedDeliveryDate(UPDATED_ESTIMATED_DELIVERY_DATE)
            .shipBeforeDate(UPDATED_SHIP_BEFORE_DATE)
            .shipAfterDate(UPDATED_SHIP_AFTER_DATE);

        restOrderItemMockMvc.perform(put("/api/order-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderItem)))
            .andExpect(status().isOk());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
        OrderItem testOrderItem = orderItemList.get(orderItemList.size() - 1);
        assertThat(testOrderItem.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
        assertThat(testOrderItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderItem.getCancelQuantity()).isEqualTo(UPDATED_CANCEL_QUANTITY);
        assertThat(testOrderItem.getSelectedAmount()).isEqualTo(UPDATED_SELECTED_AMOUNT);
        assertThat(testOrderItem.getUnitPrice()).isEqualTo(UPDATED_UNIT_PRICE);
        assertThat(testOrderItem.getUnitListPrice()).isEqualTo(UPDATED_UNIT_LIST_PRICE);
        assertThat(testOrderItem.getCgst()).isEqualTo(UPDATED_CGST);
        assertThat(testOrderItem.getIgst()).isEqualTo(UPDATED_IGST);
        assertThat(testOrderItem.getSgst()).isEqualTo(UPDATED_SGST);
        assertThat(testOrderItem.getCgstPercentage()).isEqualTo(UPDATED_CGST_PERCENTAGE);
        assertThat(testOrderItem.getIgstPercentage()).isEqualTo(UPDATED_IGST_PERCENTAGE);
        assertThat(testOrderItem.getSgstPercentage()).isEqualTo(UPDATED_SGST_PERCENTAGE);
        assertThat(testOrderItem.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testOrderItem.isIsModifiedPrice()).isEqualTo(UPDATED_IS_MODIFIED_PRICE);
        assertThat(testOrderItem.getEstimatedShipDate()).isEqualTo(UPDATED_ESTIMATED_SHIP_DATE);
        assertThat(testOrderItem.getEstimatedDeliveryDate()).isEqualTo(UPDATED_ESTIMATED_DELIVERY_DATE);
        assertThat(testOrderItem.getShipBeforeDate()).isEqualTo(UPDATED_SHIP_BEFORE_DATE);
        assertThat(testOrderItem.getShipAfterDate()).isEqualTo(UPDATED_SHIP_AFTER_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderItem() throws Exception {
        int databaseSizeBeforeUpdate = orderItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderItemMockMvc.perform(put("/api/order-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderItem)))
            .andExpect(status().isBadRequest());

        // Validate the OrderItem in the database
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderItem() throws Exception {
        // Initialize the database
        orderItemService.save(orderItem);

        int databaseSizeBeforeDelete = orderItemRepository.findAll().size();

        // Delete the orderItem
        restOrderItemMockMvc.perform(delete("/api/order-items/{id}", orderItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderItem> orderItemList = orderItemRepository.findAll();
        assertThat(orderItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
