package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.Order;
import com.hr.domain.OrderType;
import com.hr.domain.SalesChannel;
import com.hr.domain.Party;
import com.hr.domain.Status;
import com.hr.repository.OrderRepository;
import com.hr.service.OrderService;
import com.hr.service.dto.OrderCriteria;
import com.hr.service.OrderQueryService;

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
 * Integration tests for the {@link OrderResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrderResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EXTERNAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_ID = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_ORDER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ORDER_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ORDER_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Integer DEFAULT_PRIORITY = 10;
    private static final Integer UPDATED_PRIORITY = 9;
    private static final Integer SMALLER_PRIORITY = 10 - 1;

    private static final ZonedDateTime DEFAULT_ENTRY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_ENTRY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_ENTRY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Boolean DEFAULT_IS_RUSH_ORDER = false;
    private static final Boolean UPDATED_IS_RUSH_ORDER = true;

    private static final Boolean DEFAULT_NEEDS_INVENTORY_ISSUANCE = false;
    private static final Boolean UPDATED_NEEDS_INVENTORY_ISSUANCE = true;

    private static final BigDecimal DEFAULT_REMAINING_SUB_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_REMAINING_SUB_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_REMAINING_SUB_TOTAL = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_GRAND_TOTAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_GRAND_TOTAL = new BigDecimal(2);
    private static final BigDecimal SMALLER_GRAND_TOTAL = new BigDecimal(1 - 1);

    private static final Boolean DEFAULT_HAS_RATE_CONTRACT = false;
    private static final Boolean UPDATED_HAS_RATE_CONTRACT = true;

    private static final Boolean DEFAULT_GOT_QUOTE_FROM_VENDORS = false;
    private static final Boolean UPDATED_GOT_QUOTE_FROM_VENDORS = true;

    private static final String DEFAULT_VENDOR_REASON = "AAAAAAAAAA";
    private static final String UPDATED_VENDOR_REASON = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EXPECTED_DELIVERY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPECTED_DELIVERY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_EXPECTED_DELIVERY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_INSURANCE_RESP = "AAAAAAAAAA";
    private static final String UPDATED_INSURANCE_RESP = "BBBBBBBBBB";

    private static final String DEFAULT_TRANSPORT_RESP = "AAAAAAAAAA";
    private static final String UPDATED_TRANSPORT_RESP = "BBBBBBBBBB";

    private static final String DEFAULT_UNLOADING_RESP = "AAAAAAAAAA";
    private static final String UPDATED_UNLOADING_RESP = "BBBBBBBBBB";

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderQueryService orderQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderMockMvc;

    private Order order;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createEntity(EntityManager em) {
        Order order = new Order()
            .name(DEFAULT_NAME)
            .externalId(DEFAULT_EXTERNAL_ID)
            .orderDate(DEFAULT_ORDER_DATE)
            .priority(DEFAULT_PRIORITY)
            .entryDate(DEFAULT_ENTRY_DATE)
            .isRushOrder(DEFAULT_IS_RUSH_ORDER)
            .needsInventoryIssuance(DEFAULT_NEEDS_INVENTORY_ISSUANCE)
            .remainingSubTotal(DEFAULT_REMAINING_SUB_TOTAL)
            .grandTotal(DEFAULT_GRAND_TOTAL)
            .hasRateContract(DEFAULT_HAS_RATE_CONTRACT)
            .gotQuoteFromVendors(DEFAULT_GOT_QUOTE_FROM_VENDORS)
            .vendorReason(DEFAULT_VENDOR_REASON)
            .expectedDeliveryDate(DEFAULT_EXPECTED_DELIVERY_DATE)
            .insuranceResp(DEFAULT_INSURANCE_RESP)
            .transportResp(DEFAULT_TRANSPORT_RESP)
            .unloadingResp(DEFAULT_UNLOADING_RESP);
        return order;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Order createUpdatedEntity(EntityManager em) {
        Order order = new Order()
            .name(UPDATED_NAME)
            .externalId(UPDATED_EXTERNAL_ID)
            .orderDate(UPDATED_ORDER_DATE)
            .priority(UPDATED_PRIORITY)
            .entryDate(UPDATED_ENTRY_DATE)
            .isRushOrder(UPDATED_IS_RUSH_ORDER)
            .needsInventoryIssuance(UPDATED_NEEDS_INVENTORY_ISSUANCE)
            .remainingSubTotal(UPDATED_REMAINING_SUB_TOTAL)
            .grandTotal(UPDATED_GRAND_TOTAL)
            .hasRateContract(UPDATED_HAS_RATE_CONTRACT)
            .gotQuoteFromVendors(UPDATED_GOT_QUOTE_FROM_VENDORS)
            .vendorReason(UPDATED_VENDOR_REASON)
            .expectedDeliveryDate(UPDATED_EXPECTED_DELIVERY_DATE)
            .insuranceResp(UPDATED_INSURANCE_RESP)
            .transportResp(UPDATED_TRANSPORT_RESP)
            .unloadingResp(UPDATED_UNLOADING_RESP);
        return order;
    }

    @BeforeEach
    public void initTest() {
        order = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrder() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();
        // Create the Order
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isCreated());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate + 1);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrder.getExternalId()).isEqualTo(DEFAULT_EXTERNAL_ID);
        assertThat(testOrder.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testOrder.getPriority()).isEqualTo(DEFAULT_PRIORITY);
        assertThat(testOrder.getEntryDate()).isEqualTo(DEFAULT_ENTRY_DATE);
        assertThat(testOrder.isIsRushOrder()).isEqualTo(DEFAULT_IS_RUSH_ORDER);
        assertThat(testOrder.isNeedsInventoryIssuance()).isEqualTo(DEFAULT_NEEDS_INVENTORY_ISSUANCE);
        assertThat(testOrder.getRemainingSubTotal()).isEqualTo(DEFAULT_REMAINING_SUB_TOTAL);
        assertThat(testOrder.getGrandTotal()).isEqualTo(DEFAULT_GRAND_TOTAL);
        assertThat(testOrder.isHasRateContract()).isEqualTo(DEFAULT_HAS_RATE_CONTRACT);
        assertThat(testOrder.isGotQuoteFromVendors()).isEqualTo(DEFAULT_GOT_QUOTE_FROM_VENDORS);
        assertThat(testOrder.getVendorReason()).isEqualTo(DEFAULT_VENDOR_REASON);
        assertThat(testOrder.getExpectedDeliveryDate()).isEqualTo(DEFAULT_EXPECTED_DELIVERY_DATE);
        assertThat(testOrder.getInsuranceResp()).isEqualTo(DEFAULT_INSURANCE_RESP);
        assertThat(testOrder.getTransportResp()).isEqualTo(DEFAULT_TRANSPORT_RESP);
        assertThat(testOrder.getUnloadingResp()).isEqualTo(DEFAULT_UNLOADING_RESP);
    }

    @Test
    @Transactional
    public void createOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderRepository.findAll().size();

        // Create the Order with an existing ID
        order.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderMockMvc.perform(post("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrders() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList
        restOrderMockMvc.perform(get("/api/orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID)))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(sameInstant(DEFAULT_ORDER_DATE))))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].entryDate").value(hasItem(sameInstant(DEFAULT_ENTRY_DATE))))
            .andExpect(jsonPath("$.[*].isRushOrder").value(hasItem(DEFAULT_IS_RUSH_ORDER.booleanValue())))
            .andExpect(jsonPath("$.[*].needsInventoryIssuance").value(hasItem(DEFAULT_NEEDS_INVENTORY_ISSUANCE.booleanValue())))
            .andExpect(jsonPath("$.[*].remainingSubTotal").value(hasItem(DEFAULT_REMAINING_SUB_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].grandTotal").value(hasItem(DEFAULT_GRAND_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].hasRateContract").value(hasItem(DEFAULT_HAS_RATE_CONTRACT.booleanValue())))
            .andExpect(jsonPath("$.[*].gotQuoteFromVendors").value(hasItem(DEFAULT_GOT_QUOTE_FROM_VENDORS.booleanValue())))
            .andExpect(jsonPath("$.[*].vendorReason").value(hasItem(DEFAULT_VENDOR_REASON)))
            .andExpect(jsonPath("$.[*].expectedDeliveryDate").value(hasItem(sameInstant(DEFAULT_EXPECTED_DELIVERY_DATE))))
            .andExpect(jsonPath("$.[*].insuranceResp").value(hasItem(DEFAULT_INSURANCE_RESP)))
            .andExpect(jsonPath("$.[*].transportResp").value(hasItem(DEFAULT_TRANSPORT_RESP)))
            .andExpect(jsonPath("$.[*].unloadingResp").value(hasItem(DEFAULT_UNLOADING_RESP)));
    }
    
    @Test
    @Transactional
    public void getOrder() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", order.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(order.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.externalId").value(DEFAULT_EXTERNAL_ID))
            .andExpect(jsonPath("$.orderDate").value(sameInstant(DEFAULT_ORDER_DATE)))
            .andExpect(jsonPath("$.priority").value(DEFAULT_PRIORITY))
            .andExpect(jsonPath("$.entryDate").value(sameInstant(DEFAULT_ENTRY_DATE)))
            .andExpect(jsonPath("$.isRushOrder").value(DEFAULT_IS_RUSH_ORDER.booleanValue()))
            .andExpect(jsonPath("$.needsInventoryIssuance").value(DEFAULT_NEEDS_INVENTORY_ISSUANCE.booleanValue()))
            .andExpect(jsonPath("$.remainingSubTotal").value(DEFAULT_REMAINING_SUB_TOTAL.intValue()))
            .andExpect(jsonPath("$.grandTotal").value(DEFAULT_GRAND_TOTAL.intValue()))
            .andExpect(jsonPath("$.hasRateContract").value(DEFAULT_HAS_RATE_CONTRACT.booleanValue()))
            .andExpect(jsonPath("$.gotQuoteFromVendors").value(DEFAULT_GOT_QUOTE_FROM_VENDORS.booleanValue()))
            .andExpect(jsonPath("$.vendorReason").value(DEFAULT_VENDOR_REASON))
            .andExpect(jsonPath("$.expectedDeliveryDate").value(sameInstant(DEFAULT_EXPECTED_DELIVERY_DATE)))
            .andExpect(jsonPath("$.insuranceResp").value(DEFAULT_INSURANCE_RESP))
            .andExpect(jsonPath("$.transportResp").value(DEFAULT_TRANSPORT_RESP))
            .andExpect(jsonPath("$.unloadingResp").value(DEFAULT_UNLOADING_RESP));
    }


    @Test
    @Transactional
    public void getOrdersByIdFiltering() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        Long id = order.getId();

        defaultOrderShouldBeFound("id.equals=" + id);
        defaultOrderShouldNotBeFound("id.notEquals=" + id);

        defaultOrderShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrderShouldNotBeFound("id.greaterThan=" + id);

        defaultOrderShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrderShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOrdersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where name equals to DEFAULT_NAME
        defaultOrderShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the orderList where name equals to UPDATED_NAME
        defaultOrderShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOrdersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where name not equals to DEFAULT_NAME
        defaultOrderShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the orderList where name not equals to UPDATED_NAME
        defaultOrderShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOrdersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOrderShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the orderList where name equals to UPDATED_NAME
        defaultOrderShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOrdersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where name is not null
        defaultOrderShouldBeFound("name.specified=true");

        // Get all the orderList where name is null
        defaultOrderShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrdersByNameContainsSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where name contains DEFAULT_NAME
        defaultOrderShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the orderList where name contains UPDATED_NAME
        defaultOrderShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOrdersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where name does not contain DEFAULT_NAME
        defaultOrderShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the orderList where name does not contain UPDATED_NAME
        defaultOrderShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllOrdersByExternalIdIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where externalId equals to DEFAULT_EXTERNAL_ID
        defaultOrderShouldBeFound("externalId.equals=" + DEFAULT_EXTERNAL_ID);

        // Get all the orderList where externalId equals to UPDATED_EXTERNAL_ID
        defaultOrderShouldNotBeFound("externalId.equals=" + UPDATED_EXTERNAL_ID);
    }

    @Test
    @Transactional
    public void getAllOrdersByExternalIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where externalId not equals to DEFAULT_EXTERNAL_ID
        defaultOrderShouldNotBeFound("externalId.notEquals=" + DEFAULT_EXTERNAL_ID);

        // Get all the orderList where externalId not equals to UPDATED_EXTERNAL_ID
        defaultOrderShouldBeFound("externalId.notEquals=" + UPDATED_EXTERNAL_ID);
    }

    @Test
    @Transactional
    public void getAllOrdersByExternalIdIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where externalId in DEFAULT_EXTERNAL_ID or UPDATED_EXTERNAL_ID
        defaultOrderShouldBeFound("externalId.in=" + DEFAULT_EXTERNAL_ID + "," + UPDATED_EXTERNAL_ID);

        // Get all the orderList where externalId equals to UPDATED_EXTERNAL_ID
        defaultOrderShouldNotBeFound("externalId.in=" + UPDATED_EXTERNAL_ID);
    }

    @Test
    @Transactional
    public void getAllOrdersByExternalIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where externalId is not null
        defaultOrderShouldBeFound("externalId.specified=true");

        // Get all the orderList where externalId is null
        defaultOrderShouldNotBeFound("externalId.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrdersByExternalIdContainsSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where externalId contains DEFAULT_EXTERNAL_ID
        defaultOrderShouldBeFound("externalId.contains=" + DEFAULT_EXTERNAL_ID);

        // Get all the orderList where externalId contains UPDATED_EXTERNAL_ID
        defaultOrderShouldNotBeFound("externalId.contains=" + UPDATED_EXTERNAL_ID);
    }

    @Test
    @Transactional
    public void getAllOrdersByExternalIdNotContainsSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where externalId does not contain DEFAULT_EXTERNAL_ID
        defaultOrderShouldNotBeFound("externalId.doesNotContain=" + DEFAULT_EXTERNAL_ID);

        // Get all the orderList where externalId does not contain UPDATED_EXTERNAL_ID
        defaultOrderShouldBeFound("externalId.doesNotContain=" + UPDATED_EXTERNAL_ID);
    }


    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where orderDate equals to DEFAULT_ORDER_DATE
        defaultOrderShouldBeFound("orderDate.equals=" + DEFAULT_ORDER_DATE);

        // Get all the orderList where orderDate equals to UPDATED_ORDER_DATE
        defaultOrderShouldNotBeFound("orderDate.equals=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where orderDate not equals to DEFAULT_ORDER_DATE
        defaultOrderShouldNotBeFound("orderDate.notEquals=" + DEFAULT_ORDER_DATE);

        // Get all the orderList where orderDate not equals to UPDATED_ORDER_DATE
        defaultOrderShouldBeFound("orderDate.notEquals=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where orderDate in DEFAULT_ORDER_DATE or UPDATED_ORDER_DATE
        defaultOrderShouldBeFound("orderDate.in=" + DEFAULT_ORDER_DATE + "," + UPDATED_ORDER_DATE);

        // Get all the orderList where orderDate equals to UPDATED_ORDER_DATE
        defaultOrderShouldNotBeFound("orderDate.in=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where orderDate is not null
        defaultOrderShouldBeFound("orderDate.specified=true");

        // Get all the orderList where orderDate is null
        defaultOrderShouldNotBeFound("orderDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where orderDate is greater than or equal to DEFAULT_ORDER_DATE
        defaultOrderShouldBeFound("orderDate.greaterThanOrEqual=" + DEFAULT_ORDER_DATE);

        // Get all the orderList where orderDate is greater than or equal to UPDATED_ORDER_DATE
        defaultOrderShouldNotBeFound("orderDate.greaterThanOrEqual=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where orderDate is less than or equal to DEFAULT_ORDER_DATE
        defaultOrderShouldBeFound("orderDate.lessThanOrEqual=" + DEFAULT_ORDER_DATE);

        // Get all the orderList where orderDate is less than or equal to SMALLER_ORDER_DATE
        defaultOrderShouldNotBeFound("orderDate.lessThanOrEqual=" + SMALLER_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsLessThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where orderDate is less than DEFAULT_ORDER_DATE
        defaultOrderShouldNotBeFound("orderDate.lessThan=" + DEFAULT_ORDER_DATE);

        // Get all the orderList where orderDate is less than UPDATED_ORDER_DATE
        defaultOrderShouldBeFound("orderDate.lessThan=" + UPDATED_ORDER_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByOrderDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where orderDate is greater than DEFAULT_ORDER_DATE
        defaultOrderShouldNotBeFound("orderDate.greaterThan=" + DEFAULT_ORDER_DATE);

        // Get all the orderList where orderDate is greater than SMALLER_ORDER_DATE
        defaultOrderShouldBeFound("orderDate.greaterThan=" + SMALLER_ORDER_DATE);
    }


    @Test
    @Transactional
    public void getAllOrdersByPriorityIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where priority equals to DEFAULT_PRIORITY
        defaultOrderShouldBeFound("priority.equals=" + DEFAULT_PRIORITY);

        // Get all the orderList where priority equals to UPDATED_PRIORITY
        defaultOrderShouldNotBeFound("priority.equals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllOrdersByPriorityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where priority not equals to DEFAULT_PRIORITY
        defaultOrderShouldNotBeFound("priority.notEquals=" + DEFAULT_PRIORITY);

        // Get all the orderList where priority not equals to UPDATED_PRIORITY
        defaultOrderShouldBeFound("priority.notEquals=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllOrdersByPriorityIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where priority in DEFAULT_PRIORITY or UPDATED_PRIORITY
        defaultOrderShouldBeFound("priority.in=" + DEFAULT_PRIORITY + "," + UPDATED_PRIORITY);

        // Get all the orderList where priority equals to UPDATED_PRIORITY
        defaultOrderShouldNotBeFound("priority.in=" + UPDATED_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllOrdersByPriorityIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where priority is not null
        defaultOrderShouldBeFound("priority.specified=true");

        // Get all the orderList where priority is null
        defaultOrderShouldNotBeFound("priority.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByPriorityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where priority is greater than or equal to DEFAULT_PRIORITY
        defaultOrderShouldBeFound("priority.greaterThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the orderList where priority is greater than or equal to (DEFAULT_PRIORITY + 1)
        defaultOrderShouldNotBeFound("priority.greaterThanOrEqual=" + (DEFAULT_PRIORITY + 1));
    }

    @Test
    @Transactional
    public void getAllOrdersByPriorityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where priority is less than or equal to DEFAULT_PRIORITY
        defaultOrderShouldBeFound("priority.lessThanOrEqual=" + DEFAULT_PRIORITY);

        // Get all the orderList where priority is less than or equal to SMALLER_PRIORITY
        defaultOrderShouldNotBeFound("priority.lessThanOrEqual=" + SMALLER_PRIORITY);
    }

    @Test
    @Transactional
    public void getAllOrdersByPriorityIsLessThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where priority is less than DEFAULT_PRIORITY
        defaultOrderShouldNotBeFound("priority.lessThan=" + DEFAULT_PRIORITY);

        // Get all the orderList where priority is less than (DEFAULT_PRIORITY + 1)
        defaultOrderShouldBeFound("priority.lessThan=" + (DEFAULT_PRIORITY + 1));
    }

    @Test
    @Transactional
    public void getAllOrdersByPriorityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where priority is greater than DEFAULT_PRIORITY
        defaultOrderShouldNotBeFound("priority.greaterThan=" + DEFAULT_PRIORITY);

        // Get all the orderList where priority is greater than SMALLER_PRIORITY
        defaultOrderShouldBeFound("priority.greaterThan=" + SMALLER_PRIORITY);
    }


    @Test
    @Transactional
    public void getAllOrdersByEntryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where entryDate equals to DEFAULT_ENTRY_DATE
        defaultOrderShouldBeFound("entryDate.equals=" + DEFAULT_ENTRY_DATE);

        // Get all the orderList where entryDate equals to UPDATED_ENTRY_DATE
        defaultOrderShouldNotBeFound("entryDate.equals=" + UPDATED_ENTRY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByEntryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where entryDate not equals to DEFAULT_ENTRY_DATE
        defaultOrderShouldNotBeFound("entryDate.notEquals=" + DEFAULT_ENTRY_DATE);

        // Get all the orderList where entryDate not equals to UPDATED_ENTRY_DATE
        defaultOrderShouldBeFound("entryDate.notEquals=" + UPDATED_ENTRY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByEntryDateIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where entryDate in DEFAULT_ENTRY_DATE or UPDATED_ENTRY_DATE
        defaultOrderShouldBeFound("entryDate.in=" + DEFAULT_ENTRY_DATE + "," + UPDATED_ENTRY_DATE);

        // Get all the orderList where entryDate equals to UPDATED_ENTRY_DATE
        defaultOrderShouldNotBeFound("entryDate.in=" + UPDATED_ENTRY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByEntryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where entryDate is not null
        defaultOrderShouldBeFound("entryDate.specified=true");

        // Get all the orderList where entryDate is null
        defaultOrderShouldNotBeFound("entryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByEntryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where entryDate is greater than or equal to DEFAULT_ENTRY_DATE
        defaultOrderShouldBeFound("entryDate.greaterThanOrEqual=" + DEFAULT_ENTRY_DATE);

        // Get all the orderList where entryDate is greater than or equal to UPDATED_ENTRY_DATE
        defaultOrderShouldNotBeFound("entryDate.greaterThanOrEqual=" + UPDATED_ENTRY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByEntryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where entryDate is less than or equal to DEFAULT_ENTRY_DATE
        defaultOrderShouldBeFound("entryDate.lessThanOrEqual=" + DEFAULT_ENTRY_DATE);

        // Get all the orderList where entryDate is less than or equal to SMALLER_ENTRY_DATE
        defaultOrderShouldNotBeFound("entryDate.lessThanOrEqual=" + SMALLER_ENTRY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByEntryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where entryDate is less than DEFAULT_ENTRY_DATE
        defaultOrderShouldNotBeFound("entryDate.lessThan=" + DEFAULT_ENTRY_DATE);

        // Get all the orderList where entryDate is less than UPDATED_ENTRY_DATE
        defaultOrderShouldBeFound("entryDate.lessThan=" + UPDATED_ENTRY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByEntryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where entryDate is greater than DEFAULT_ENTRY_DATE
        defaultOrderShouldNotBeFound("entryDate.greaterThan=" + DEFAULT_ENTRY_DATE);

        // Get all the orderList where entryDate is greater than SMALLER_ENTRY_DATE
        defaultOrderShouldBeFound("entryDate.greaterThan=" + SMALLER_ENTRY_DATE);
    }


    @Test
    @Transactional
    public void getAllOrdersByIsRushOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where isRushOrder equals to DEFAULT_IS_RUSH_ORDER
        defaultOrderShouldBeFound("isRushOrder.equals=" + DEFAULT_IS_RUSH_ORDER);

        // Get all the orderList where isRushOrder equals to UPDATED_IS_RUSH_ORDER
        defaultOrderShouldNotBeFound("isRushOrder.equals=" + UPDATED_IS_RUSH_ORDER);
    }

    @Test
    @Transactional
    public void getAllOrdersByIsRushOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where isRushOrder not equals to DEFAULT_IS_RUSH_ORDER
        defaultOrderShouldNotBeFound("isRushOrder.notEquals=" + DEFAULT_IS_RUSH_ORDER);

        // Get all the orderList where isRushOrder not equals to UPDATED_IS_RUSH_ORDER
        defaultOrderShouldBeFound("isRushOrder.notEquals=" + UPDATED_IS_RUSH_ORDER);
    }

    @Test
    @Transactional
    public void getAllOrdersByIsRushOrderIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where isRushOrder in DEFAULT_IS_RUSH_ORDER or UPDATED_IS_RUSH_ORDER
        defaultOrderShouldBeFound("isRushOrder.in=" + DEFAULT_IS_RUSH_ORDER + "," + UPDATED_IS_RUSH_ORDER);

        // Get all the orderList where isRushOrder equals to UPDATED_IS_RUSH_ORDER
        defaultOrderShouldNotBeFound("isRushOrder.in=" + UPDATED_IS_RUSH_ORDER);
    }

    @Test
    @Transactional
    public void getAllOrdersByIsRushOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where isRushOrder is not null
        defaultOrderShouldBeFound("isRushOrder.specified=true");

        // Get all the orderList where isRushOrder is null
        defaultOrderShouldNotBeFound("isRushOrder.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByNeedsInventoryIssuanceIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where needsInventoryIssuance equals to DEFAULT_NEEDS_INVENTORY_ISSUANCE
        defaultOrderShouldBeFound("needsInventoryIssuance.equals=" + DEFAULT_NEEDS_INVENTORY_ISSUANCE);

        // Get all the orderList where needsInventoryIssuance equals to UPDATED_NEEDS_INVENTORY_ISSUANCE
        defaultOrderShouldNotBeFound("needsInventoryIssuance.equals=" + UPDATED_NEEDS_INVENTORY_ISSUANCE);
    }

    @Test
    @Transactional
    public void getAllOrdersByNeedsInventoryIssuanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where needsInventoryIssuance not equals to DEFAULT_NEEDS_INVENTORY_ISSUANCE
        defaultOrderShouldNotBeFound("needsInventoryIssuance.notEquals=" + DEFAULT_NEEDS_INVENTORY_ISSUANCE);

        // Get all the orderList where needsInventoryIssuance not equals to UPDATED_NEEDS_INVENTORY_ISSUANCE
        defaultOrderShouldBeFound("needsInventoryIssuance.notEquals=" + UPDATED_NEEDS_INVENTORY_ISSUANCE);
    }

    @Test
    @Transactional
    public void getAllOrdersByNeedsInventoryIssuanceIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where needsInventoryIssuance in DEFAULT_NEEDS_INVENTORY_ISSUANCE or UPDATED_NEEDS_INVENTORY_ISSUANCE
        defaultOrderShouldBeFound("needsInventoryIssuance.in=" + DEFAULT_NEEDS_INVENTORY_ISSUANCE + "," + UPDATED_NEEDS_INVENTORY_ISSUANCE);

        // Get all the orderList where needsInventoryIssuance equals to UPDATED_NEEDS_INVENTORY_ISSUANCE
        defaultOrderShouldNotBeFound("needsInventoryIssuance.in=" + UPDATED_NEEDS_INVENTORY_ISSUANCE);
    }

    @Test
    @Transactional
    public void getAllOrdersByNeedsInventoryIssuanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where needsInventoryIssuance is not null
        defaultOrderShouldBeFound("needsInventoryIssuance.specified=true");

        // Get all the orderList where needsInventoryIssuance is null
        defaultOrderShouldNotBeFound("needsInventoryIssuance.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByRemainingSubTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where remainingSubTotal equals to DEFAULT_REMAINING_SUB_TOTAL
        defaultOrderShouldBeFound("remainingSubTotal.equals=" + DEFAULT_REMAINING_SUB_TOTAL);

        // Get all the orderList where remainingSubTotal equals to UPDATED_REMAINING_SUB_TOTAL
        defaultOrderShouldNotBeFound("remainingSubTotal.equals=" + UPDATED_REMAINING_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersByRemainingSubTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where remainingSubTotal not equals to DEFAULT_REMAINING_SUB_TOTAL
        defaultOrderShouldNotBeFound("remainingSubTotal.notEquals=" + DEFAULT_REMAINING_SUB_TOTAL);

        // Get all the orderList where remainingSubTotal not equals to UPDATED_REMAINING_SUB_TOTAL
        defaultOrderShouldBeFound("remainingSubTotal.notEquals=" + UPDATED_REMAINING_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersByRemainingSubTotalIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where remainingSubTotal in DEFAULT_REMAINING_SUB_TOTAL or UPDATED_REMAINING_SUB_TOTAL
        defaultOrderShouldBeFound("remainingSubTotal.in=" + DEFAULT_REMAINING_SUB_TOTAL + "," + UPDATED_REMAINING_SUB_TOTAL);

        // Get all the orderList where remainingSubTotal equals to UPDATED_REMAINING_SUB_TOTAL
        defaultOrderShouldNotBeFound("remainingSubTotal.in=" + UPDATED_REMAINING_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersByRemainingSubTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where remainingSubTotal is not null
        defaultOrderShouldBeFound("remainingSubTotal.specified=true");

        // Get all the orderList where remainingSubTotal is null
        defaultOrderShouldNotBeFound("remainingSubTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByRemainingSubTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where remainingSubTotal is greater than or equal to DEFAULT_REMAINING_SUB_TOTAL
        defaultOrderShouldBeFound("remainingSubTotal.greaterThanOrEqual=" + DEFAULT_REMAINING_SUB_TOTAL);

        // Get all the orderList where remainingSubTotal is greater than or equal to UPDATED_REMAINING_SUB_TOTAL
        defaultOrderShouldNotBeFound("remainingSubTotal.greaterThanOrEqual=" + UPDATED_REMAINING_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersByRemainingSubTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where remainingSubTotal is less than or equal to DEFAULT_REMAINING_SUB_TOTAL
        defaultOrderShouldBeFound("remainingSubTotal.lessThanOrEqual=" + DEFAULT_REMAINING_SUB_TOTAL);

        // Get all the orderList where remainingSubTotal is less than or equal to SMALLER_REMAINING_SUB_TOTAL
        defaultOrderShouldNotBeFound("remainingSubTotal.lessThanOrEqual=" + SMALLER_REMAINING_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersByRemainingSubTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where remainingSubTotal is less than DEFAULT_REMAINING_SUB_TOTAL
        defaultOrderShouldNotBeFound("remainingSubTotal.lessThan=" + DEFAULT_REMAINING_SUB_TOTAL);

        // Get all the orderList where remainingSubTotal is less than UPDATED_REMAINING_SUB_TOTAL
        defaultOrderShouldBeFound("remainingSubTotal.lessThan=" + UPDATED_REMAINING_SUB_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersByRemainingSubTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where remainingSubTotal is greater than DEFAULT_REMAINING_SUB_TOTAL
        defaultOrderShouldNotBeFound("remainingSubTotal.greaterThan=" + DEFAULT_REMAINING_SUB_TOTAL);

        // Get all the orderList where remainingSubTotal is greater than SMALLER_REMAINING_SUB_TOTAL
        defaultOrderShouldBeFound("remainingSubTotal.greaterThan=" + SMALLER_REMAINING_SUB_TOTAL);
    }


    @Test
    @Transactional
    public void getAllOrdersByGrandTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where grandTotal equals to DEFAULT_GRAND_TOTAL
        defaultOrderShouldBeFound("grandTotal.equals=" + DEFAULT_GRAND_TOTAL);

        // Get all the orderList where grandTotal equals to UPDATED_GRAND_TOTAL
        defaultOrderShouldNotBeFound("grandTotal.equals=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersByGrandTotalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where grandTotal not equals to DEFAULT_GRAND_TOTAL
        defaultOrderShouldNotBeFound("grandTotal.notEquals=" + DEFAULT_GRAND_TOTAL);

        // Get all the orderList where grandTotal not equals to UPDATED_GRAND_TOTAL
        defaultOrderShouldBeFound("grandTotal.notEquals=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersByGrandTotalIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where grandTotal in DEFAULT_GRAND_TOTAL or UPDATED_GRAND_TOTAL
        defaultOrderShouldBeFound("grandTotal.in=" + DEFAULT_GRAND_TOTAL + "," + UPDATED_GRAND_TOTAL);

        // Get all the orderList where grandTotal equals to UPDATED_GRAND_TOTAL
        defaultOrderShouldNotBeFound("grandTotal.in=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersByGrandTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where grandTotal is not null
        defaultOrderShouldBeFound("grandTotal.specified=true");

        // Get all the orderList where grandTotal is null
        defaultOrderShouldNotBeFound("grandTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByGrandTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where grandTotal is greater than or equal to DEFAULT_GRAND_TOTAL
        defaultOrderShouldBeFound("grandTotal.greaterThanOrEqual=" + DEFAULT_GRAND_TOTAL);

        // Get all the orderList where grandTotal is greater than or equal to UPDATED_GRAND_TOTAL
        defaultOrderShouldNotBeFound("grandTotal.greaterThanOrEqual=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersByGrandTotalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where grandTotal is less than or equal to DEFAULT_GRAND_TOTAL
        defaultOrderShouldBeFound("grandTotal.lessThanOrEqual=" + DEFAULT_GRAND_TOTAL);

        // Get all the orderList where grandTotal is less than or equal to SMALLER_GRAND_TOTAL
        defaultOrderShouldNotBeFound("grandTotal.lessThanOrEqual=" + SMALLER_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersByGrandTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where grandTotal is less than DEFAULT_GRAND_TOTAL
        defaultOrderShouldNotBeFound("grandTotal.lessThan=" + DEFAULT_GRAND_TOTAL);

        // Get all the orderList where grandTotal is less than UPDATED_GRAND_TOTAL
        defaultOrderShouldBeFound("grandTotal.lessThan=" + UPDATED_GRAND_TOTAL);
    }

    @Test
    @Transactional
    public void getAllOrdersByGrandTotalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where grandTotal is greater than DEFAULT_GRAND_TOTAL
        defaultOrderShouldNotBeFound("grandTotal.greaterThan=" + DEFAULT_GRAND_TOTAL);

        // Get all the orderList where grandTotal is greater than SMALLER_GRAND_TOTAL
        defaultOrderShouldBeFound("grandTotal.greaterThan=" + SMALLER_GRAND_TOTAL);
    }


    @Test
    @Transactional
    public void getAllOrdersByHasRateContractIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where hasRateContract equals to DEFAULT_HAS_RATE_CONTRACT
        defaultOrderShouldBeFound("hasRateContract.equals=" + DEFAULT_HAS_RATE_CONTRACT);

        // Get all the orderList where hasRateContract equals to UPDATED_HAS_RATE_CONTRACT
        defaultOrderShouldNotBeFound("hasRateContract.equals=" + UPDATED_HAS_RATE_CONTRACT);
    }

    @Test
    @Transactional
    public void getAllOrdersByHasRateContractIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where hasRateContract not equals to DEFAULT_HAS_RATE_CONTRACT
        defaultOrderShouldNotBeFound("hasRateContract.notEquals=" + DEFAULT_HAS_RATE_CONTRACT);

        // Get all the orderList where hasRateContract not equals to UPDATED_HAS_RATE_CONTRACT
        defaultOrderShouldBeFound("hasRateContract.notEquals=" + UPDATED_HAS_RATE_CONTRACT);
    }

    @Test
    @Transactional
    public void getAllOrdersByHasRateContractIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where hasRateContract in DEFAULT_HAS_RATE_CONTRACT or UPDATED_HAS_RATE_CONTRACT
        defaultOrderShouldBeFound("hasRateContract.in=" + DEFAULT_HAS_RATE_CONTRACT + "," + UPDATED_HAS_RATE_CONTRACT);

        // Get all the orderList where hasRateContract equals to UPDATED_HAS_RATE_CONTRACT
        defaultOrderShouldNotBeFound("hasRateContract.in=" + UPDATED_HAS_RATE_CONTRACT);
    }

    @Test
    @Transactional
    public void getAllOrdersByHasRateContractIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where hasRateContract is not null
        defaultOrderShouldBeFound("hasRateContract.specified=true");

        // Get all the orderList where hasRateContract is null
        defaultOrderShouldNotBeFound("hasRateContract.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByGotQuoteFromVendorsIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where gotQuoteFromVendors equals to DEFAULT_GOT_QUOTE_FROM_VENDORS
        defaultOrderShouldBeFound("gotQuoteFromVendors.equals=" + DEFAULT_GOT_QUOTE_FROM_VENDORS);

        // Get all the orderList where gotQuoteFromVendors equals to UPDATED_GOT_QUOTE_FROM_VENDORS
        defaultOrderShouldNotBeFound("gotQuoteFromVendors.equals=" + UPDATED_GOT_QUOTE_FROM_VENDORS);
    }

    @Test
    @Transactional
    public void getAllOrdersByGotQuoteFromVendorsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where gotQuoteFromVendors not equals to DEFAULT_GOT_QUOTE_FROM_VENDORS
        defaultOrderShouldNotBeFound("gotQuoteFromVendors.notEquals=" + DEFAULT_GOT_QUOTE_FROM_VENDORS);

        // Get all the orderList where gotQuoteFromVendors not equals to UPDATED_GOT_QUOTE_FROM_VENDORS
        defaultOrderShouldBeFound("gotQuoteFromVendors.notEquals=" + UPDATED_GOT_QUOTE_FROM_VENDORS);
    }

    @Test
    @Transactional
    public void getAllOrdersByGotQuoteFromVendorsIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where gotQuoteFromVendors in DEFAULT_GOT_QUOTE_FROM_VENDORS or UPDATED_GOT_QUOTE_FROM_VENDORS
        defaultOrderShouldBeFound("gotQuoteFromVendors.in=" + DEFAULT_GOT_QUOTE_FROM_VENDORS + "," + UPDATED_GOT_QUOTE_FROM_VENDORS);

        // Get all the orderList where gotQuoteFromVendors equals to UPDATED_GOT_QUOTE_FROM_VENDORS
        defaultOrderShouldNotBeFound("gotQuoteFromVendors.in=" + UPDATED_GOT_QUOTE_FROM_VENDORS);
    }

    @Test
    @Transactional
    public void getAllOrdersByGotQuoteFromVendorsIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where gotQuoteFromVendors is not null
        defaultOrderShouldBeFound("gotQuoteFromVendors.specified=true");

        // Get all the orderList where gotQuoteFromVendors is null
        defaultOrderShouldNotBeFound("gotQuoteFromVendors.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByVendorReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where vendorReason equals to DEFAULT_VENDOR_REASON
        defaultOrderShouldBeFound("vendorReason.equals=" + DEFAULT_VENDOR_REASON);

        // Get all the orderList where vendorReason equals to UPDATED_VENDOR_REASON
        defaultOrderShouldNotBeFound("vendorReason.equals=" + UPDATED_VENDOR_REASON);
    }

    @Test
    @Transactional
    public void getAllOrdersByVendorReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where vendorReason not equals to DEFAULT_VENDOR_REASON
        defaultOrderShouldNotBeFound("vendorReason.notEquals=" + DEFAULT_VENDOR_REASON);

        // Get all the orderList where vendorReason not equals to UPDATED_VENDOR_REASON
        defaultOrderShouldBeFound("vendorReason.notEquals=" + UPDATED_VENDOR_REASON);
    }

    @Test
    @Transactional
    public void getAllOrdersByVendorReasonIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where vendorReason in DEFAULT_VENDOR_REASON or UPDATED_VENDOR_REASON
        defaultOrderShouldBeFound("vendorReason.in=" + DEFAULT_VENDOR_REASON + "," + UPDATED_VENDOR_REASON);

        // Get all the orderList where vendorReason equals to UPDATED_VENDOR_REASON
        defaultOrderShouldNotBeFound("vendorReason.in=" + UPDATED_VENDOR_REASON);
    }

    @Test
    @Transactional
    public void getAllOrdersByVendorReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where vendorReason is not null
        defaultOrderShouldBeFound("vendorReason.specified=true");

        // Get all the orderList where vendorReason is null
        defaultOrderShouldNotBeFound("vendorReason.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrdersByVendorReasonContainsSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where vendorReason contains DEFAULT_VENDOR_REASON
        defaultOrderShouldBeFound("vendorReason.contains=" + DEFAULT_VENDOR_REASON);

        // Get all the orderList where vendorReason contains UPDATED_VENDOR_REASON
        defaultOrderShouldNotBeFound("vendorReason.contains=" + UPDATED_VENDOR_REASON);
    }

    @Test
    @Transactional
    public void getAllOrdersByVendorReasonNotContainsSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where vendorReason does not contain DEFAULT_VENDOR_REASON
        defaultOrderShouldNotBeFound("vendorReason.doesNotContain=" + DEFAULT_VENDOR_REASON);

        // Get all the orderList where vendorReason does not contain UPDATED_VENDOR_REASON
        defaultOrderShouldBeFound("vendorReason.doesNotContain=" + UPDATED_VENDOR_REASON);
    }


    @Test
    @Transactional
    public void getAllOrdersByExpectedDeliveryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where expectedDeliveryDate equals to DEFAULT_EXPECTED_DELIVERY_DATE
        defaultOrderShouldBeFound("expectedDeliveryDate.equals=" + DEFAULT_EXPECTED_DELIVERY_DATE);

        // Get all the orderList where expectedDeliveryDate equals to UPDATED_EXPECTED_DELIVERY_DATE
        defaultOrderShouldNotBeFound("expectedDeliveryDate.equals=" + UPDATED_EXPECTED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByExpectedDeliveryDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where expectedDeliveryDate not equals to DEFAULT_EXPECTED_DELIVERY_DATE
        defaultOrderShouldNotBeFound("expectedDeliveryDate.notEquals=" + DEFAULT_EXPECTED_DELIVERY_DATE);

        // Get all the orderList where expectedDeliveryDate not equals to UPDATED_EXPECTED_DELIVERY_DATE
        defaultOrderShouldBeFound("expectedDeliveryDate.notEquals=" + UPDATED_EXPECTED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByExpectedDeliveryDateIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where expectedDeliveryDate in DEFAULT_EXPECTED_DELIVERY_DATE or UPDATED_EXPECTED_DELIVERY_DATE
        defaultOrderShouldBeFound("expectedDeliveryDate.in=" + DEFAULT_EXPECTED_DELIVERY_DATE + "," + UPDATED_EXPECTED_DELIVERY_DATE);

        // Get all the orderList where expectedDeliveryDate equals to UPDATED_EXPECTED_DELIVERY_DATE
        defaultOrderShouldNotBeFound("expectedDeliveryDate.in=" + UPDATED_EXPECTED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByExpectedDeliveryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where expectedDeliveryDate is not null
        defaultOrderShouldBeFound("expectedDeliveryDate.specified=true");

        // Get all the orderList where expectedDeliveryDate is null
        defaultOrderShouldNotBeFound("expectedDeliveryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrdersByExpectedDeliveryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where expectedDeliveryDate is greater than or equal to DEFAULT_EXPECTED_DELIVERY_DATE
        defaultOrderShouldBeFound("expectedDeliveryDate.greaterThanOrEqual=" + DEFAULT_EXPECTED_DELIVERY_DATE);

        // Get all the orderList where expectedDeliveryDate is greater than or equal to UPDATED_EXPECTED_DELIVERY_DATE
        defaultOrderShouldNotBeFound("expectedDeliveryDate.greaterThanOrEqual=" + UPDATED_EXPECTED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByExpectedDeliveryDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where expectedDeliveryDate is less than or equal to DEFAULT_EXPECTED_DELIVERY_DATE
        defaultOrderShouldBeFound("expectedDeliveryDate.lessThanOrEqual=" + DEFAULT_EXPECTED_DELIVERY_DATE);

        // Get all the orderList where expectedDeliveryDate is less than or equal to SMALLER_EXPECTED_DELIVERY_DATE
        defaultOrderShouldNotBeFound("expectedDeliveryDate.lessThanOrEqual=" + SMALLER_EXPECTED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByExpectedDeliveryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where expectedDeliveryDate is less than DEFAULT_EXPECTED_DELIVERY_DATE
        defaultOrderShouldNotBeFound("expectedDeliveryDate.lessThan=" + DEFAULT_EXPECTED_DELIVERY_DATE);

        // Get all the orderList where expectedDeliveryDate is less than UPDATED_EXPECTED_DELIVERY_DATE
        defaultOrderShouldBeFound("expectedDeliveryDate.lessThan=" + UPDATED_EXPECTED_DELIVERY_DATE);
    }

    @Test
    @Transactional
    public void getAllOrdersByExpectedDeliveryDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where expectedDeliveryDate is greater than DEFAULT_EXPECTED_DELIVERY_DATE
        defaultOrderShouldNotBeFound("expectedDeliveryDate.greaterThan=" + DEFAULT_EXPECTED_DELIVERY_DATE);

        // Get all the orderList where expectedDeliveryDate is greater than SMALLER_EXPECTED_DELIVERY_DATE
        defaultOrderShouldBeFound("expectedDeliveryDate.greaterThan=" + SMALLER_EXPECTED_DELIVERY_DATE);
    }


    @Test
    @Transactional
    public void getAllOrdersByInsuranceRespIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where insuranceResp equals to DEFAULT_INSURANCE_RESP
        defaultOrderShouldBeFound("insuranceResp.equals=" + DEFAULT_INSURANCE_RESP);

        // Get all the orderList where insuranceResp equals to UPDATED_INSURANCE_RESP
        defaultOrderShouldNotBeFound("insuranceResp.equals=" + UPDATED_INSURANCE_RESP);
    }

    @Test
    @Transactional
    public void getAllOrdersByInsuranceRespIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where insuranceResp not equals to DEFAULT_INSURANCE_RESP
        defaultOrderShouldNotBeFound("insuranceResp.notEquals=" + DEFAULT_INSURANCE_RESP);

        // Get all the orderList where insuranceResp not equals to UPDATED_INSURANCE_RESP
        defaultOrderShouldBeFound("insuranceResp.notEquals=" + UPDATED_INSURANCE_RESP);
    }

    @Test
    @Transactional
    public void getAllOrdersByInsuranceRespIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where insuranceResp in DEFAULT_INSURANCE_RESP or UPDATED_INSURANCE_RESP
        defaultOrderShouldBeFound("insuranceResp.in=" + DEFAULT_INSURANCE_RESP + "," + UPDATED_INSURANCE_RESP);

        // Get all the orderList where insuranceResp equals to UPDATED_INSURANCE_RESP
        defaultOrderShouldNotBeFound("insuranceResp.in=" + UPDATED_INSURANCE_RESP);
    }

    @Test
    @Transactional
    public void getAllOrdersByInsuranceRespIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where insuranceResp is not null
        defaultOrderShouldBeFound("insuranceResp.specified=true");

        // Get all the orderList where insuranceResp is null
        defaultOrderShouldNotBeFound("insuranceResp.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrdersByInsuranceRespContainsSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where insuranceResp contains DEFAULT_INSURANCE_RESP
        defaultOrderShouldBeFound("insuranceResp.contains=" + DEFAULT_INSURANCE_RESP);

        // Get all the orderList where insuranceResp contains UPDATED_INSURANCE_RESP
        defaultOrderShouldNotBeFound("insuranceResp.contains=" + UPDATED_INSURANCE_RESP);
    }

    @Test
    @Transactional
    public void getAllOrdersByInsuranceRespNotContainsSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where insuranceResp does not contain DEFAULT_INSURANCE_RESP
        defaultOrderShouldNotBeFound("insuranceResp.doesNotContain=" + DEFAULT_INSURANCE_RESP);

        // Get all the orderList where insuranceResp does not contain UPDATED_INSURANCE_RESP
        defaultOrderShouldBeFound("insuranceResp.doesNotContain=" + UPDATED_INSURANCE_RESP);
    }


    @Test
    @Transactional
    public void getAllOrdersByTransportRespIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where transportResp equals to DEFAULT_TRANSPORT_RESP
        defaultOrderShouldBeFound("transportResp.equals=" + DEFAULT_TRANSPORT_RESP);

        // Get all the orderList where transportResp equals to UPDATED_TRANSPORT_RESP
        defaultOrderShouldNotBeFound("transportResp.equals=" + UPDATED_TRANSPORT_RESP);
    }

    @Test
    @Transactional
    public void getAllOrdersByTransportRespIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where transportResp not equals to DEFAULT_TRANSPORT_RESP
        defaultOrderShouldNotBeFound("transportResp.notEquals=" + DEFAULT_TRANSPORT_RESP);

        // Get all the orderList where transportResp not equals to UPDATED_TRANSPORT_RESP
        defaultOrderShouldBeFound("transportResp.notEquals=" + UPDATED_TRANSPORT_RESP);
    }

    @Test
    @Transactional
    public void getAllOrdersByTransportRespIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where transportResp in DEFAULT_TRANSPORT_RESP or UPDATED_TRANSPORT_RESP
        defaultOrderShouldBeFound("transportResp.in=" + DEFAULT_TRANSPORT_RESP + "," + UPDATED_TRANSPORT_RESP);

        // Get all the orderList where transportResp equals to UPDATED_TRANSPORT_RESP
        defaultOrderShouldNotBeFound("transportResp.in=" + UPDATED_TRANSPORT_RESP);
    }

    @Test
    @Transactional
    public void getAllOrdersByTransportRespIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where transportResp is not null
        defaultOrderShouldBeFound("transportResp.specified=true");

        // Get all the orderList where transportResp is null
        defaultOrderShouldNotBeFound("transportResp.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrdersByTransportRespContainsSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where transportResp contains DEFAULT_TRANSPORT_RESP
        defaultOrderShouldBeFound("transportResp.contains=" + DEFAULT_TRANSPORT_RESP);

        // Get all the orderList where transportResp contains UPDATED_TRANSPORT_RESP
        defaultOrderShouldNotBeFound("transportResp.contains=" + UPDATED_TRANSPORT_RESP);
    }

    @Test
    @Transactional
    public void getAllOrdersByTransportRespNotContainsSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where transportResp does not contain DEFAULT_TRANSPORT_RESP
        defaultOrderShouldNotBeFound("transportResp.doesNotContain=" + DEFAULT_TRANSPORT_RESP);

        // Get all the orderList where transportResp does not contain UPDATED_TRANSPORT_RESP
        defaultOrderShouldBeFound("transportResp.doesNotContain=" + UPDATED_TRANSPORT_RESP);
    }


    @Test
    @Transactional
    public void getAllOrdersByUnloadingRespIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where unloadingResp equals to DEFAULT_UNLOADING_RESP
        defaultOrderShouldBeFound("unloadingResp.equals=" + DEFAULT_UNLOADING_RESP);

        // Get all the orderList where unloadingResp equals to UPDATED_UNLOADING_RESP
        defaultOrderShouldNotBeFound("unloadingResp.equals=" + UPDATED_UNLOADING_RESP);
    }

    @Test
    @Transactional
    public void getAllOrdersByUnloadingRespIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where unloadingResp not equals to DEFAULT_UNLOADING_RESP
        defaultOrderShouldNotBeFound("unloadingResp.notEquals=" + DEFAULT_UNLOADING_RESP);

        // Get all the orderList where unloadingResp not equals to UPDATED_UNLOADING_RESP
        defaultOrderShouldBeFound("unloadingResp.notEquals=" + UPDATED_UNLOADING_RESP);
    }

    @Test
    @Transactional
    public void getAllOrdersByUnloadingRespIsInShouldWork() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where unloadingResp in DEFAULT_UNLOADING_RESP or UPDATED_UNLOADING_RESP
        defaultOrderShouldBeFound("unloadingResp.in=" + DEFAULT_UNLOADING_RESP + "," + UPDATED_UNLOADING_RESP);

        // Get all the orderList where unloadingResp equals to UPDATED_UNLOADING_RESP
        defaultOrderShouldNotBeFound("unloadingResp.in=" + UPDATED_UNLOADING_RESP);
    }

    @Test
    @Transactional
    public void getAllOrdersByUnloadingRespIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where unloadingResp is not null
        defaultOrderShouldBeFound("unloadingResp.specified=true");

        // Get all the orderList where unloadingResp is null
        defaultOrderShouldNotBeFound("unloadingResp.specified=false");
    }
                @Test
    @Transactional
    public void getAllOrdersByUnloadingRespContainsSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where unloadingResp contains DEFAULT_UNLOADING_RESP
        defaultOrderShouldBeFound("unloadingResp.contains=" + DEFAULT_UNLOADING_RESP);

        // Get all the orderList where unloadingResp contains UPDATED_UNLOADING_RESP
        defaultOrderShouldNotBeFound("unloadingResp.contains=" + UPDATED_UNLOADING_RESP);
    }

    @Test
    @Transactional
    public void getAllOrdersByUnloadingRespNotContainsSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);

        // Get all the orderList where unloadingResp does not contain DEFAULT_UNLOADING_RESP
        defaultOrderShouldNotBeFound("unloadingResp.doesNotContain=" + DEFAULT_UNLOADING_RESP);

        // Get all the orderList where unloadingResp does not contain UPDATED_UNLOADING_RESP
        defaultOrderShouldBeFound("unloadingResp.doesNotContain=" + UPDATED_UNLOADING_RESP);
    }


    @Test
    @Transactional
    public void getAllOrdersByOrderTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);
        OrderType orderType = OrderTypeResourceIT.createEntity(em);
        em.persist(orderType);
        em.flush();
        order.setOrderType(orderType);
        orderRepository.saveAndFlush(order);
        Long orderTypeId = orderType.getId();

        // Get all the orderList where orderType equals to orderTypeId
        defaultOrderShouldBeFound("orderTypeId.equals=" + orderTypeId);

        // Get all the orderList where orderType equals to orderTypeId + 1
        defaultOrderShouldNotBeFound("orderTypeId.equals=" + (orderTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersBySalesChannelIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);
        SalesChannel salesChannel = SalesChannelResourceIT.createEntity(em);
        em.persist(salesChannel);
        em.flush();
        order.setSalesChannel(salesChannel);
        orderRepository.saveAndFlush(order);
        Long salesChannelId = salesChannel.getId();

        // Get all the orderList where salesChannel equals to salesChannelId
        defaultOrderShouldBeFound("salesChannelId.equals=" + salesChannelId);

        // Get all the orderList where salesChannel equals to salesChannelId + 1
        defaultOrderShouldNotBeFound("salesChannelId.equals=" + (salesChannelId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByPartyIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);
        Party party = PartyResourceIT.createEntity(em);
        em.persist(party);
        em.flush();
        order.setParty(party);
        orderRepository.saveAndFlush(order);
        Long partyId = party.getId();

        // Get all the orderList where party equals to partyId
        defaultOrderShouldBeFound("partyId.equals=" + partyId);

        // Get all the orderList where party equals to partyId + 1
        defaultOrderShouldNotBeFound("partyId.equals=" + (partyId + 1));
    }


    @Test
    @Transactional
    public void getAllOrdersByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        orderRepository.saveAndFlush(order);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        order.setStatus(status);
        orderRepository.saveAndFlush(order);
        Long statusId = status.getId();

        // Get all the orderList where status equals to statusId
        defaultOrderShouldBeFound("statusId.equals=" + statusId);

        // Get all the orderList where status equals to statusId + 1
        defaultOrderShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrderShouldBeFound(String filter) throws Exception {
        restOrderMockMvc.perform(get("/api/orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(order.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].externalId").value(hasItem(DEFAULT_EXTERNAL_ID)))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(sameInstant(DEFAULT_ORDER_DATE))))
            .andExpect(jsonPath("$.[*].priority").value(hasItem(DEFAULT_PRIORITY)))
            .andExpect(jsonPath("$.[*].entryDate").value(hasItem(sameInstant(DEFAULT_ENTRY_DATE))))
            .andExpect(jsonPath("$.[*].isRushOrder").value(hasItem(DEFAULT_IS_RUSH_ORDER.booleanValue())))
            .andExpect(jsonPath("$.[*].needsInventoryIssuance").value(hasItem(DEFAULT_NEEDS_INVENTORY_ISSUANCE.booleanValue())))
            .andExpect(jsonPath("$.[*].remainingSubTotal").value(hasItem(DEFAULT_REMAINING_SUB_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].grandTotal").value(hasItem(DEFAULT_GRAND_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].hasRateContract").value(hasItem(DEFAULT_HAS_RATE_CONTRACT.booleanValue())))
            .andExpect(jsonPath("$.[*].gotQuoteFromVendors").value(hasItem(DEFAULT_GOT_QUOTE_FROM_VENDORS.booleanValue())))
            .andExpect(jsonPath("$.[*].vendorReason").value(hasItem(DEFAULT_VENDOR_REASON)))
            .andExpect(jsonPath("$.[*].expectedDeliveryDate").value(hasItem(sameInstant(DEFAULT_EXPECTED_DELIVERY_DATE))))
            .andExpect(jsonPath("$.[*].insuranceResp").value(hasItem(DEFAULT_INSURANCE_RESP)))
            .andExpect(jsonPath("$.[*].transportResp").value(hasItem(DEFAULT_TRANSPORT_RESP)))
            .andExpect(jsonPath("$.[*].unloadingResp").value(hasItem(DEFAULT_UNLOADING_RESP)));

        // Check, that the count call also returns 1
        restOrderMockMvc.perform(get("/api/orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrderShouldNotBeFound(String filter) throws Exception {
        restOrderMockMvc.perform(get("/api/orders?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrderMockMvc.perform(get("/api/orders/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOrder() throws Exception {
        // Get the order
        restOrderMockMvc.perform(get("/api/orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrder() throws Exception {
        // Initialize the database
        orderService.save(order);

        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // Update the order
        Order updatedOrder = orderRepository.findById(order.getId()).get();
        // Disconnect from session so that the updates on updatedOrder are not directly saved in db
        em.detach(updatedOrder);
        updatedOrder
            .name(UPDATED_NAME)
            .externalId(UPDATED_EXTERNAL_ID)
            .orderDate(UPDATED_ORDER_DATE)
            .priority(UPDATED_PRIORITY)
            .entryDate(UPDATED_ENTRY_DATE)
            .isRushOrder(UPDATED_IS_RUSH_ORDER)
            .needsInventoryIssuance(UPDATED_NEEDS_INVENTORY_ISSUANCE)
            .remainingSubTotal(UPDATED_REMAINING_SUB_TOTAL)
            .grandTotal(UPDATED_GRAND_TOTAL)
            .hasRateContract(UPDATED_HAS_RATE_CONTRACT)
            .gotQuoteFromVendors(UPDATED_GOT_QUOTE_FROM_VENDORS)
            .vendorReason(UPDATED_VENDOR_REASON)
            .expectedDeliveryDate(UPDATED_EXPECTED_DELIVERY_DATE)
            .insuranceResp(UPDATED_INSURANCE_RESP)
            .transportResp(UPDATED_TRANSPORT_RESP)
            .unloadingResp(UPDATED_UNLOADING_RESP);

        restOrderMockMvc.perform(put("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrder)))
            .andExpect(status().isOk());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
        Order testOrder = orderList.get(orderList.size() - 1);
        assertThat(testOrder.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrder.getExternalId()).isEqualTo(UPDATED_EXTERNAL_ID);
        assertThat(testOrder.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testOrder.getPriority()).isEqualTo(UPDATED_PRIORITY);
        assertThat(testOrder.getEntryDate()).isEqualTo(UPDATED_ENTRY_DATE);
        assertThat(testOrder.isIsRushOrder()).isEqualTo(UPDATED_IS_RUSH_ORDER);
        assertThat(testOrder.isNeedsInventoryIssuance()).isEqualTo(UPDATED_NEEDS_INVENTORY_ISSUANCE);
        assertThat(testOrder.getRemainingSubTotal()).isEqualTo(UPDATED_REMAINING_SUB_TOTAL);
        assertThat(testOrder.getGrandTotal()).isEqualTo(UPDATED_GRAND_TOTAL);
        assertThat(testOrder.isHasRateContract()).isEqualTo(UPDATED_HAS_RATE_CONTRACT);
        assertThat(testOrder.isGotQuoteFromVendors()).isEqualTo(UPDATED_GOT_QUOTE_FROM_VENDORS);
        assertThat(testOrder.getVendorReason()).isEqualTo(UPDATED_VENDOR_REASON);
        assertThat(testOrder.getExpectedDeliveryDate()).isEqualTo(UPDATED_EXPECTED_DELIVERY_DATE);
        assertThat(testOrder.getInsuranceResp()).isEqualTo(UPDATED_INSURANCE_RESP);
        assertThat(testOrder.getTransportResp()).isEqualTo(UPDATED_TRANSPORT_RESP);
        assertThat(testOrder.getUnloadingResp()).isEqualTo(UPDATED_UNLOADING_RESP);
    }

    @Test
    @Transactional
    public void updateNonExistingOrder() throws Exception {
        int databaseSizeBeforeUpdate = orderRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderMockMvc.perform(put("/api/orders")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(order)))
            .andExpect(status().isBadRequest());

        // Validate the Order in the database
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrder() throws Exception {
        // Initialize the database
        orderService.save(order);

        int databaseSizeBeforeDelete = orderRepository.findAll().size();

        // Delete the order
        restOrderMockMvc.perform(delete("/api/orders/{id}", order.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Order> orderList = orderRepository.findAll();
        assertThat(orderList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
