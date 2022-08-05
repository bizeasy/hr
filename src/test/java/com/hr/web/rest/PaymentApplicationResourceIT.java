package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PaymentApplication;
import com.hr.domain.Payment;
import com.hr.domain.Invoice;
import com.hr.domain.InvoiceItem;
import com.hr.domain.Order;
import com.hr.domain.OrderItem;
import com.hr.repository.PaymentApplicationRepository;
import com.hr.service.PaymentApplicationService;
import com.hr.service.dto.PaymentApplicationCriteria;
import com.hr.service.PaymentApplicationQueryService;

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
 * Integration tests for the {@link PaymentApplicationResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentApplicationResourceIT {

    private static final Double DEFAULT_AMOUNT_APPLIED = 1D;
    private static final Double UPDATED_AMOUNT_APPLIED = 2D;
    private static final Double SMALLER_AMOUNT_APPLIED = 1D - 1D;

    @Autowired
    private PaymentApplicationRepository paymentApplicationRepository;

    @Autowired
    private PaymentApplicationService paymentApplicationService;

    @Autowired
    private PaymentApplicationQueryService paymentApplicationQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentApplicationMockMvc;

    private PaymentApplication paymentApplication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentApplication createEntity(EntityManager em) {
        PaymentApplication paymentApplication = new PaymentApplication()
            .amountApplied(DEFAULT_AMOUNT_APPLIED);
        return paymentApplication;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentApplication createUpdatedEntity(EntityManager em) {
        PaymentApplication paymentApplication = new PaymentApplication()
            .amountApplied(UPDATED_AMOUNT_APPLIED);
        return paymentApplication;
    }

    @BeforeEach
    public void initTest() {
        paymentApplication = createEntity(em);
    }

    @Test
    @Transactional
    public void createPaymentApplication() throws Exception {
        int databaseSizeBeforeCreate = paymentApplicationRepository.findAll().size();
        // Create the PaymentApplication
        restPaymentApplicationMockMvc.perform(post("/api/payment-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentApplication)))
            .andExpect(status().isCreated());

        // Validate the PaymentApplication in the database
        List<PaymentApplication> paymentApplicationList = paymentApplicationRepository.findAll();
        assertThat(paymentApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentApplication testPaymentApplication = paymentApplicationList.get(paymentApplicationList.size() - 1);
        assertThat(testPaymentApplication.getAmountApplied()).isEqualTo(DEFAULT_AMOUNT_APPLIED);
    }

    @Test
    @Transactional
    public void createPaymentApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentApplicationRepository.findAll().size();

        // Create the PaymentApplication with an existing ID
        paymentApplication.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentApplicationMockMvc.perform(post("/api/payment-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentApplication)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentApplication in the database
        List<PaymentApplication> paymentApplicationList = paymentApplicationRepository.findAll();
        assertThat(paymentApplicationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPaymentApplications() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);

        // Get all the paymentApplicationList
        restPaymentApplicationMockMvc.perform(get("/api/payment-applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountApplied").value(hasItem(DEFAULT_AMOUNT_APPLIED.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getPaymentApplication() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);

        // Get the paymentApplication
        restPaymentApplicationMockMvc.perform(get("/api/payment-applications/{id}", paymentApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentApplication.getId().intValue()))
            .andExpect(jsonPath("$.amountApplied").value(DEFAULT_AMOUNT_APPLIED.doubleValue()));
    }


    @Test
    @Transactional
    public void getPaymentApplicationsByIdFiltering() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);

        Long id = paymentApplication.getId();

        defaultPaymentApplicationShouldBeFound("id.equals=" + id);
        defaultPaymentApplicationShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentApplicationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentApplicationShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentApplicationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentApplicationShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPaymentApplicationsByAmountAppliedIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);

        // Get all the paymentApplicationList where amountApplied equals to DEFAULT_AMOUNT_APPLIED
        defaultPaymentApplicationShouldBeFound("amountApplied.equals=" + DEFAULT_AMOUNT_APPLIED);

        // Get all the paymentApplicationList where amountApplied equals to UPDATED_AMOUNT_APPLIED
        defaultPaymentApplicationShouldNotBeFound("amountApplied.equals=" + UPDATED_AMOUNT_APPLIED);
    }

    @Test
    @Transactional
    public void getAllPaymentApplicationsByAmountAppliedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);

        // Get all the paymentApplicationList where amountApplied not equals to DEFAULT_AMOUNT_APPLIED
        defaultPaymentApplicationShouldNotBeFound("amountApplied.notEquals=" + DEFAULT_AMOUNT_APPLIED);

        // Get all the paymentApplicationList where amountApplied not equals to UPDATED_AMOUNT_APPLIED
        defaultPaymentApplicationShouldBeFound("amountApplied.notEquals=" + UPDATED_AMOUNT_APPLIED);
    }

    @Test
    @Transactional
    public void getAllPaymentApplicationsByAmountAppliedIsInShouldWork() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);

        // Get all the paymentApplicationList where amountApplied in DEFAULT_AMOUNT_APPLIED or UPDATED_AMOUNT_APPLIED
        defaultPaymentApplicationShouldBeFound("amountApplied.in=" + DEFAULT_AMOUNT_APPLIED + "," + UPDATED_AMOUNT_APPLIED);

        // Get all the paymentApplicationList where amountApplied equals to UPDATED_AMOUNT_APPLIED
        defaultPaymentApplicationShouldNotBeFound("amountApplied.in=" + UPDATED_AMOUNT_APPLIED);
    }

    @Test
    @Transactional
    public void getAllPaymentApplicationsByAmountAppliedIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);

        // Get all the paymentApplicationList where amountApplied is not null
        defaultPaymentApplicationShouldBeFound("amountApplied.specified=true");

        // Get all the paymentApplicationList where amountApplied is null
        defaultPaymentApplicationShouldNotBeFound("amountApplied.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentApplicationsByAmountAppliedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);

        // Get all the paymentApplicationList where amountApplied is greater than or equal to DEFAULT_AMOUNT_APPLIED
        defaultPaymentApplicationShouldBeFound("amountApplied.greaterThanOrEqual=" + DEFAULT_AMOUNT_APPLIED);

        // Get all the paymentApplicationList where amountApplied is greater than or equal to UPDATED_AMOUNT_APPLIED
        defaultPaymentApplicationShouldNotBeFound("amountApplied.greaterThanOrEqual=" + UPDATED_AMOUNT_APPLIED);
    }

    @Test
    @Transactional
    public void getAllPaymentApplicationsByAmountAppliedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);

        // Get all the paymentApplicationList where amountApplied is less than or equal to DEFAULT_AMOUNT_APPLIED
        defaultPaymentApplicationShouldBeFound("amountApplied.lessThanOrEqual=" + DEFAULT_AMOUNT_APPLIED);

        // Get all the paymentApplicationList where amountApplied is less than or equal to SMALLER_AMOUNT_APPLIED
        defaultPaymentApplicationShouldNotBeFound("amountApplied.lessThanOrEqual=" + SMALLER_AMOUNT_APPLIED);
    }

    @Test
    @Transactional
    public void getAllPaymentApplicationsByAmountAppliedIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);

        // Get all the paymentApplicationList where amountApplied is less than DEFAULT_AMOUNT_APPLIED
        defaultPaymentApplicationShouldNotBeFound("amountApplied.lessThan=" + DEFAULT_AMOUNT_APPLIED);

        // Get all the paymentApplicationList where amountApplied is less than UPDATED_AMOUNT_APPLIED
        defaultPaymentApplicationShouldBeFound("amountApplied.lessThan=" + UPDATED_AMOUNT_APPLIED);
    }

    @Test
    @Transactional
    public void getAllPaymentApplicationsByAmountAppliedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);

        // Get all the paymentApplicationList where amountApplied is greater than DEFAULT_AMOUNT_APPLIED
        defaultPaymentApplicationShouldNotBeFound("amountApplied.greaterThan=" + DEFAULT_AMOUNT_APPLIED);

        // Get all the paymentApplicationList where amountApplied is greater than SMALLER_AMOUNT_APPLIED
        defaultPaymentApplicationShouldBeFound("amountApplied.greaterThan=" + SMALLER_AMOUNT_APPLIED);
    }


    @Test
    @Transactional
    public void getAllPaymentApplicationsByPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);
        Payment payment = PaymentResourceIT.createEntity(em);
        em.persist(payment);
        em.flush();
        paymentApplication.setPayment(payment);
        paymentApplicationRepository.saveAndFlush(paymentApplication);
        Long paymentId = payment.getId();

        // Get all the paymentApplicationList where payment equals to paymentId
        defaultPaymentApplicationShouldBeFound("paymentId.equals=" + paymentId);

        // Get all the paymentApplicationList where payment equals to paymentId + 1
        defaultPaymentApplicationShouldNotBeFound("paymentId.equals=" + (paymentId + 1));
    }


    @Test
    @Transactional
    public void getAllPaymentApplicationsByInvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);
        Invoice invoice = InvoiceResourceIT.createEntity(em);
        em.persist(invoice);
        em.flush();
        paymentApplication.setInvoice(invoice);
        paymentApplicationRepository.saveAndFlush(paymentApplication);
        Long invoiceId = invoice.getId();

        // Get all the paymentApplicationList where invoice equals to invoiceId
        defaultPaymentApplicationShouldBeFound("invoiceId.equals=" + invoiceId);

        // Get all the paymentApplicationList where invoice equals to invoiceId + 1
        defaultPaymentApplicationShouldNotBeFound("invoiceId.equals=" + (invoiceId + 1));
    }


    @Test
    @Transactional
    public void getAllPaymentApplicationsByInvoiceItemIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);
        InvoiceItem invoiceItem = InvoiceItemResourceIT.createEntity(em);
        em.persist(invoiceItem);
        em.flush();
        paymentApplication.setInvoiceItem(invoiceItem);
        paymentApplicationRepository.saveAndFlush(paymentApplication);
        Long invoiceItemId = invoiceItem.getId();

        // Get all the paymentApplicationList where invoiceItem equals to invoiceItemId
        defaultPaymentApplicationShouldBeFound("invoiceItemId.equals=" + invoiceItemId);

        // Get all the paymentApplicationList where invoiceItem equals to invoiceItemId + 1
        defaultPaymentApplicationShouldNotBeFound("invoiceItemId.equals=" + (invoiceItemId + 1));
    }


    @Test
    @Transactional
    public void getAllPaymentApplicationsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);
        Order order = OrderResourceIT.createEntity(em);
        em.persist(order);
        em.flush();
        paymentApplication.setOrder(order);
        paymentApplicationRepository.saveAndFlush(paymentApplication);
        Long orderId = order.getId();

        // Get all the paymentApplicationList where order equals to orderId
        defaultPaymentApplicationShouldBeFound("orderId.equals=" + orderId);

        // Get all the paymentApplicationList where order equals to orderId + 1
        defaultPaymentApplicationShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }


    @Test
    @Transactional
    public void getAllPaymentApplicationsByOrderItemIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);
        OrderItem orderItem = OrderItemResourceIT.createEntity(em);
        em.persist(orderItem);
        em.flush();
        paymentApplication.setOrderItem(orderItem);
        paymentApplicationRepository.saveAndFlush(paymentApplication);
        Long orderItemId = orderItem.getId();

        // Get all the paymentApplicationList where orderItem equals to orderItemId
        defaultPaymentApplicationShouldBeFound("orderItemId.equals=" + orderItemId);

        // Get all the paymentApplicationList where orderItem equals to orderItemId + 1
        defaultPaymentApplicationShouldNotBeFound("orderItemId.equals=" + (orderItemId + 1));
    }


    @Test
    @Transactional
    public void getAllPaymentApplicationsByToPaymentIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentApplicationRepository.saveAndFlush(paymentApplication);
        Payment toPayment = PaymentResourceIT.createEntity(em);
        em.persist(toPayment);
        em.flush();
        paymentApplication.setToPayment(toPayment);
        paymentApplicationRepository.saveAndFlush(paymentApplication);
        Long toPaymentId = toPayment.getId();

        // Get all the paymentApplicationList where toPayment equals to toPaymentId
        defaultPaymentApplicationShouldBeFound("toPaymentId.equals=" + toPaymentId);

        // Get all the paymentApplicationList where toPayment equals to toPaymentId + 1
        defaultPaymentApplicationShouldNotBeFound("toPaymentId.equals=" + (toPaymentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentApplicationShouldBeFound(String filter) throws Exception {
        restPaymentApplicationMockMvc.perform(get("/api/payment-applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].amountApplied").value(hasItem(DEFAULT_AMOUNT_APPLIED.doubleValue())));

        // Check, that the count call also returns 1
        restPaymentApplicationMockMvc.perform(get("/api/payment-applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentApplicationShouldNotBeFound(String filter) throws Exception {
        restPaymentApplicationMockMvc.perform(get("/api/payment-applications?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentApplicationMockMvc.perform(get("/api/payment-applications/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPaymentApplication() throws Exception {
        // Get the paymentApplication
        restPaymentApplicationMockMvc.perform(get("/api/payment-applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePaymentApplication() throws Exception {
        // Initialize the database
        paymentApplicationService.save(paymentApplication);

        int databaseSizeBeforeUpdate = paymentApplicationRepository.findAll().size();

        // Update the paymentApplication
        PaymentApplication updatedPaymentApplication = paymentApplicationRepository.findById(paymentApplication.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentApplication are not directly saved in db
        em.detach(updatedPaymentApplication);
        updatedPaymentApplication
            .amountApplied(UPDATED_AMOUNT_APPLIED);

        restPaymentApplicationMockMvc.perform(put("/api/payment-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPaymentApplication)))
            .andExpect(status().isOk());

        // Validate the PaymentApplication in the database
        List<PaymentApplication> paymentApplicationList = paymentApplicationRepository.findAll();
        assertThat(paymentApplicationList).hasSize(databaseSizeBeforeUpdate);
        PaymentApplication testPaymentApplication = paymentApplicationList.get(paymentApplicationList.size() - 1);
        assertThat(testPaymentApplication.getAmountApplied()).isEqualTo(UPDATED_AMOUNT_APPLIED);
    }

    @Test
    @Transactional
    public void updateNonExistingPaymentApplication() throws Exception {
        int databaseSizeBeforeUpdate = paymentApplicationRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentApplicationMockMvc.perform(put("/api/payment-applications")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(paymentApplication)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentApplication in the database
        List<PaymentApplication> paymentApplicationList = paymentApplicationRepository.findAll();
        assertThat(paymentApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePaymentApplication() throws Exception {
        // Initialize the database
        paymentApplicationService.save(paymentApplication);

        int databaseSizeBeforeDelete = paymentApplicationRepository.findAll().size();

        // Delete the paymentApplication
        restPaymentApplicationMockMvc.perform(delete("/api/payment-applications/{id}", paymentApplication.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentApplication> paymentApplicationList = paymentApplicationRepository.findAll();
        assertThat(paymentApplicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
