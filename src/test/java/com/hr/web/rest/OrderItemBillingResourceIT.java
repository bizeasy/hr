package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.OrderItemBilling;
import com.hr.domain.OrderItem;
import com.hr.domain.InvoiceItem;
import com.hr.repository.OrderItemBillingRepository;
import com.hr.service.OrderItemBillingService;
import com.hr.service.dto.OrderItemBillingCriteria;
import com.hr.service.OrderItemBillingQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrderItemBillingResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrderItemBillingResourceIT {

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    @Autowired
    private OrderItemBillingRepository orderItemBillingRepository;

    @Autowired
    private OrderItemBillingService orderItemBillingService;

    @Autowired
    private OrderItemBillingQueryService orderItemBillingQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderItemBillingMockMvc;

    private OrderItemBilling orderItemBilling;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderItemBilling createEntity(EntityManager em) {
        OrderItemBilling orderItemBilling = new OrderItemBilling()
            .quantity(DEFAULT_QUANTITY)
            .amount(DEFAULT_AMOUNT);
        return orderItemBilling;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderItemBilling createUpdatedEntity(EntityManager em) {
        OrderItemBilling orderItemBilling = new OrderItemBilling()
            .quantity(UPDATED_QUANTITY)
            .amount(UPDATED_AMOUNT);
        return orderItemBilling;
    }

    @BeforeEach
    public void initTest() {
        orderItemBilling = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderItemBilling() throws Exception {
        int databaseSizeBeforeCreate = orderItemBillingRepository.findAll().size();
        // Create the OrderItemBilling
        restOrderItemBillingMockMvc.perform(post("/api/order-item-billings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderItemBilling)))
            .andExpect(status().isCreated());

        // Validate the OrderItemBilling in the database
        List<OrderItemBilling> orderItemBillingList = orderItemBillingRepository.findAll();
        assertThat(orderItemBillingList).hasSize(databaseSizeBeforeCreate + 1);
        OrderItemBilling testOrderItemBilling = orderItemBillingList.get(orderItemBillingList.size() - 1);
        assertThat(testOrderItemBilling.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testOrderItemBilling.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createOrderItemBillingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderItemBillingRepository.findAll().size();

        // Create the OrderItemBilling with an existing ID
        orderItemBilling.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderItemBillingMockMvc.perform(post("/api/order-item-billings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderItemBilling)))
            .andExpect(status().isBadRequest());

        // Validate the OrderItemBilling in the database
        List<OrderItemBilling> orderItemBillingList = orderItemBillingRepository.findAll();
        assertThat(orderItemBillingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderItemBillings() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList
        restOrderItemBillingMockMvc.perform(get("/api/order-item-billings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderItemBilling.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getOrderItemBilling() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get the orderItemBilling
        restOrderItemBillingMockMvc.perform(get("/api/order-item-billings/{id}", orderItemBilling.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderItemBilling.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }


    @Test
    @Transactional
    public void getOrderItemBillingsByIdFiltering() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        Long id = orderItemBilling.getId();

        defaultOrderItemBillingShouldBeFound("id.equals=" + id);
        defaultOrderItemBillingShouldNotBeFound("id.notEquals=" + id);

        defaultOrderItemBillingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrderItemBillingShouldNotBeFound("id.greaterThan=" + id);

        defaultOrderItemBillingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrderItemBillingShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOrderItemBillingsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList where quantity equals to DEFAULT_QUANTITY
        defaultOrderItemBillingShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the orderItemBillingList where quantity equals to UPDATED_QUANTITY
        defaultOrderItemBillingShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemBillingsByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList where quantity not equals to DEFAULT_QUANTITY
        defaultOrderItemBillingShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the orderItemBillingList where quantity not equals to UPDATED_QUANTITY
        defaultOrderItemBillingShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemBillingsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultOrderItemBillingShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the orderItemBillingList where quantity equals to UPDATED_QUANTITY
        defaultOrderItemBillingShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemBillingsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList where quantity is not null
        defaultOrderItemBillingShouldBeFound("quantity.specified=true");

        // Get all the orderItemBillingList where quantity is null
        defaultOrderItemBillingShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemBillingsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultOrderItemBillingShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the orderItemBillingList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultOrderItemBillingShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemBillingsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultOrderItemBillingShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the orderItemBillingList where quantity is less than or equal to SMALLER_QUANTITY
        defaultOrderItemBillingShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemBillingsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList where quantity is less than DEFAULT_QUANTITY
        defaultOrderItemBillingShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the orderItemBillingList where quantity is less than UPDATED_QUANTITY
        defaultOrderItemBillingShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllOrderItemBillingsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList where quantity is greater than DEFAULT_QUANTITY
        defaultOrderItemBillingShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the orderItemBillingList where quantity is greater than SMALLER_QUANTITY
        defaultOrderItemBillingShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllOrderItemBillingsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList where amount equals to DEFAULT_AMOUNT
        defaultOrderItemBillingShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the orderItemBillingList where amount equals to UPDATED_AMOUNT
        defaultOrderItemBillingShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderItemBillingsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList where amount not equals to DEFAULT_AMOUNT
        defaultOrderItemBillingShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the orderItemBillingList where amount not equals to UPDATED_AMOUNT
        defaultOrderItemBillingShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderItemBillingsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultOrderItemBillingShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the orderItemBillingList where amount equals to UPDATED_AMOUNT
        defaultOrderItemBillingShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderItemBillingsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList where amount is not null
        defaultOrderItemBillingShouldBeFound("amount.specified=true");

        // Get all the orderItemBillingList where amount is null
        defaultOrderItemBillingShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderItemBillingsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultOrderItemBillingShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the orderItemBillingList where amount is greater than or equal to UPDATED_AMOUNT
        defaultOrderItemBillingShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderItemBillingsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList where amount is less than or equal to DEFAULT_AMOUNT
        defaultOrderItemBillingShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the orderItemBillingList where amount is less than or equal to SMALLER_AMOUNT
        defaultOrderItemBillingShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderItemBillingsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList where amount is less than DEFAULT_AMOUNT
        defaultOrderItemBillingShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the orderItemBillingList where amount is less than UPDATED_AMOUNT
        defaultOrderItemBillingShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllOrderItemBillingsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);

        // Get all the orderItemBillingList where amount is greater than DEFAULT_AMOUNT
        defaultOrderItemBillingShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the orderItemBillingList where amount is greater than SMALLER_AMOUNT
        defaultOrderItemBillingShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllOrderItemBillingsByOrderItemIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);
        OrderItem orderItem = OrderItemResourceIT.createEntity(em);
        em.persist(orderItem);
        em.flush();
        orderItemBilling.setOrderItem(orderItem);
        orderItemBillingRepository.saveAndFlush(orderItemBilling);
        Long orderItemId = orderItem.getId();

        // Get all the orderItemBillingList where orderItem equals to orderItemId
        defaultOrderItemBillingShouldBeFound("orderItemId.equals=" + orderItemId);

        // Get all the orderItemBillingList where orderItem equals to orderItemId + 1
        defaultOrderItemBillingShouldNotBeFound("orderItemId.equals=" + (orderItemId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderItemBillingsByInvoiceItemIsEqualToSomething() throws Exception {
        // Initialize the database
        orderItemBillingRepository.saveAndFlush(orderItemBilling);
        InvoiceItem invoiceItem = InvoiceItemResourceIT.createEntity(em);
        em.persist(invoiceItem);
        em.flush();
        orderItemBilling.setInvoiceItem(invoiceItem);
        orderItemBillingRepository.saveAndFlush(orderItemBilling);
        Long invoiceItemId = invoiceItem.getId();

        // Get all the orderItemBillingList where invoiceItem equals to invoiceItemId
        defaultOrderItemBillingShouldBeFound("invoiceItemId.equals=" + invoiceItemId);

        // Get all the orderItemBillingList where invoiceItem equals to invoiceItemId + 1
        defaultOrderItemBillingShouldNotBeFound("invoiceItemId.equals=" + (invoiceItemId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrderItemBillingShouldBeFound(String filter) throws Exception {
        restOrderItemBillingMockMvc.perform(get("/api/order-item-billings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderItemBilling.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));

        // Check, that the count call also returns 1
        restOrderItemBillingMockMvc.perform(get("/api/order-item-billings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrderItemBillingShouldNotBeFound(String filter) throws Exception {
        restOrderItemBillingMockMvc.perform(get("/api/order-item-billings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrderItemBillingMockMvc.perform(get("/api/order-item-billings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOrderItemBilling() throws Exception {
        // Get the orderItemBilling
        restOrderItemBillingMockMvc.perform(get("/api/order-item-billings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderItemBilling() throws Exception {
        // Initialize the database
        orderItemBillingService.save(orderItemBilling);

        int databaseSizeBeforeUpdate = orderItemBillingRepository.findAll().size();

        // Update the orderItemBilling
        OrderItemBilling updatedOrderItemBilling = orderItemBillingRepository.findById(orderItemBilling.getId()).get();
        // Disconnect from session so that the updates on updatedOrderItemBilling are not directly saved in db
        em.detach(updatedOrderItemBilling);
        updatedOrderItemBilling
            .quantity(UPDATED_QUANTITY)
            .amount(UPDATED_AMOUNT);

        restOrderItemBillingMockMvc.perform(put("/api/order-item-billings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderItemBilling)))
            .andExpect(status().isOk());

        // Validate the OrderItemBilling in the database
        List<OrderItemBilling> orderItemBillingList = orderItemBillingRepository.findAll();
        assertThat(orderItemBillingList).hasSize(databaseSizeBeforeUpdate);
        OrderItemBilling testOrderItemBilling = orderItemBillingList.get(orderItemBillingList.size() - 1);
        assertThat(testOrderItemBilling.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testOrderItemBilling.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderItemBilling() throws Exception {
        int databaseSizeBeforeUpdate = orderItemBillingRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderItemBillingMockMvc.perform(put("/api/order-item-billings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderItemBilling)))
            .andExpect(status().isBadRequest());

        // Validate the OrderItemBilling in the database
        List<OrderItemBilling> orderItemBillingList = orderItemBillingRepository.findAll();
        assertThat(orderItemBillingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderItemBilling() throws Exception {
        // Initialize the database
        orderItemBillingService.save(orderItemBilling);

        int databaseSizeBeforeDelete = orderItemBillingRepository.findAll().size();

        // Delete the orderItemBilling
        restOrderItemBillingMockMvc.perform(delete("/api/order-item-billings/{id}", orderItemBilling.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderItemBilling> orderItemBillingList = orderItemBillingRepository.findAll();
        assertThat(orderItemBillingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
