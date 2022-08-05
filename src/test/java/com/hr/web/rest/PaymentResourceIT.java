package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.Payment;
import com.hr.domain.PaymentType;
import com.hr.domain.PaymentMethodType;
import com.hr.domain.Status;
import com.hr.domain.PaymentMethod;
import com.hr.domain.PaymentGatewayResponse;
import com.hr.domain.Party;
import com.hr.domain.RoleType;
import com.hr.domain.Uom;
import com.hr.repository.PaymentRepository;
import com.hr.service.PaymentService;
import com.hr.service.dto.PaymentCriteria;
import com.hr.service.PaymentQueryService;

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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PaymentResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PaymentResourceIT {

    private static final Instant DEFAULT_EFFECTIVE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_EFFECTIVE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_PAYMENT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PAYMENT_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PAYMENT_REF_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_REF_NUMBER = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    private static final String DEFAULT_PAYMENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_MIHPAY_ID = "AAAAAAAAAA";
    private static final String UPDATED_MIHPAY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PRODUCT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_PRODUCT_INFO = "BBBBBBBBBB";

    private static final String DEFAULT_TXN_ID = "AAAAAAAAAA";
    private static final String UPDATED_TXN_ID = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ACTUAL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACTUAL_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_ACTUAL_AMOUNT = new BigDecimal(1 - 1);

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private PaymentQueryService paymentQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentMockMvc;

    private Payment payment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createEntity(EntityManager em) {
        Payment payment = new Payment()
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .paymentDate(DEFAULT_PAYMENT_DATE)
            .paymentRefNumber(DEFAULT_PAYMENT_REF_NUMBER)
            .amount(DEFAULT_AMOUNT)
            .paymentStatus(DEFAULT_PAYMENT_STATUS)
            .mihpayId(DEFAULT_MIHPAY_ID)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE)
            .productInfo(DEFAULT_PRODUCT_INFO)
            .txnId(DEFAULT_TXN_ID)
            .actualAmount(DEFAULT_ACTUAL_AMOUNT);
        return payment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Payment createUpdatedEntity(EntityManager em) {
        Payment payment = new Payment()
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentRefNumber(UPDATED_PAYMENT_REF_NUMBER)
            .amount(UPDATED_AMOUNT)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .mihpayId(UPDATED_MIHPAY_ID)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .productInfo(UPDATED_PRODUCT_INFO)
            .txnId(UPDATED_TXN_ID)
            .actualAmount(UPDATED_ACTUAL_AMOUNT);
        return payment;
    }

    @BeforeEach
    public void initTest() {
        payment = createEntity(em);
    }

    @Test
    @Transactional
    public void createPayment() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();
        // Create the Payment
        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isCreated());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate + 1);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testPayment.getPaymentDate()).isEqualTo(DEFAULT_PAYMENT_DATE);
        assertThat(testPayment.getPaymentRefNumber()).isEqualTo(DEFAULT_PAYMENT_REF_NUMBER);
        assertThat(testPayment.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testPayment.getPaymentStatus()).isEqualTo(DEFAULT_PAYMENT_STATUS);
        assertThat(testPayment.getMihpayId()).isEqualTo(DEFAULT_MIHPAY_ID);
        assertThat(testPayment.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPayment.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPayment.getProductInfo()).isEqualTo(DEFAULT_PRODUCT_INFO);
        assertThat(testPayment.getTxnId()).isEqualTo(DEFAULT_TXN_ID);
        assertThat(testPayment.getActualAmount()).isEqualTo(DEFAULT_ACTUAL_AMOUNT);
    }

    @Test
    @Transactional
    public void createPaymentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = paymentRepository.findAll().size();

        // Create the Payment with an existing ID
        payment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentMockMvc.perform(post("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPayments() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentRefNumber").value(hasItem(DEFAULT_PAYMENT_REF_NUMBER)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS)))
            .andExpect(jsonPath("$.[*].mihpayId").value(hasItem(DEFAULT_MIHPAY_ID)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].productInfo").value(hasItem(DEFAULT_PRODUCT_INFO)))
            .andExpect(jsonPath("$.[*].txnId").value(hasItem(DEFAULT_TXN_ID)))
            .andExpect(jsonPath("$.[*].actualAmount").value(hasItem(DEFAULT_ACTUAL_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getPayment() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", payment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(payment.getId().intValue()))
            .andExpect(jsonPath("$.effectiveDate").value(DEFAULT_EFFECTIVE_DATE.toString()))
            .andExpect(jsonPath("$.paymentDate").value(DEFAULT_PAYMENT_DATE.toString()))
            .andExpect(jsonPath("$.paymentRefNumber").value(DEFAULT_PAYMENT_REF_NUMBER))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.paymentStatus").value(DEFAULT_PAYMENT_STATUS))
            .andExpect(jsonPath("$.mihpayId").value(DEFAULT_MIHPAY_ID))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.productInfo").value(DEFAULT_PRODUCT_INFO))
            .andExpect(jsonPath("$.txnId").value(DEFAULT_TXN_ID))
            .andExpect(jsonPath("$.actualAmount").value(DEFAULT_ACTUAL_AMOUNT.intValue()));
    }


    @Test
    @Transactional
    public void getPaymentsByIdFiltering() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        Long id = payment.getId();

        defaultPaymentShouldBeFound("id.equals=" + id);
        defaultPaymentShouldNotBeFound("id.notEquals=" + id);

        defaultPaymentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPaymentShouldNotBeFound("id.greaterThan=" + id);

        defaultPaymentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPaymentShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPaymentsByEffectiveDateIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where effectiveDate equals to DEFAULT_EFFECTIVE_DATE
        defaultPaymentShouldBeFound("effectiveDate.equals=" + DEFAULT_EFFECTIVE_DATE);

        // Get all the paymentList where effectiveDate equals to UPDATED_EFFECTIVE_DATE
        defaultPaymentShouldNotBeFound("effectiveDate.equals=" + UPDATED_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByEffectiveDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where effectiveDate not equals to DEFAULT_EFFECTIVE_DATE
        defaultPaymentShouldNotBeFound("effectiveDate.notEquals=" + DEFAULT_EFFECTIVE_DATE);

        // Get all the paymentList where effectiveDate not equals to UPDATED_EFFECTIVE_DATE
        defaultPaymentShouldBeFound("effectiveDate.notEquals=" + UPDATED_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByEffectiveDateIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where effectiveDate in DEFAULT_EFFECTIVE_DATE or UPDATED_EFFECTIVE_DATE
        defaultPaymentShouldBeFound("effectiveDate.in=" + DEFAULT_EFFECTIVE_DATE + "," + UPDATED_EFFECTIVE_DATE);

        // Get all the paymentList where effectiveDate equals to UPDATED_EFFECTIVE_DATE
        defaultPaymentShouldNotBeFound("effectiveDate.in=" + UPDATED_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByEffectiveDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where effectiveDate is not null
        defaultPaymentShouldBeFound("effectiveDate.specified=true");

        // Get all the paymentList where effectiveDate is null
        defaultPaymentShouldNotBeFound("effectiveDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentDateIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentDate equals to DEFAULT_PAYMENT_DATE
        defaultPaymentShouldBeFound("paymentDate.equals=" + DEFAULT_PAYMENT_DATE);

        // Get all the paymentList where paymentDate equals to UPDATED_PAYMENT_DATE
        defaultPaymentShouldNotBeFound("paymentDate.equals=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentDate not equals to DEFAULT_PAYMENT_DATE
        defaultPaymentShouldNotBeFound("paymentDate.notEquals=" + DEFAULT_PAYMENT_DATE);

        // Get all the paymentList where paymentDate not equals to UPDATED_PAYMENT_DATE
        defaultPaymentShouldBeFound("paymentDate.notEquals=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentDateIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentDate in DEFAULT_PAYMENT_DATE or UPDATED_PAYMENT_DATE
        defaultPaymentShouldBeFound("paymentDate.in=" + DEFAULT_PAYMENT_DATE + "," + UPDATED_PAYMENT_DATE);

        // Get all the paymentList where paymentDate equals to UPDATED_PAYMENT_DATE
        defaultPaymentShouldNotBeFound("paymentDate.in=" + UPDATED_PAYMENT_DATE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentDate is not null
        defaultPaymentShouldBeFound("paymentDate.specified=true");

        // Get all the paymentList where paymentDate is null
        defaultPaymentShouldNotBeFound("paymentDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentRefNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentRefNumber equals to DEFAULT_PAYMENT_REF_NUMBER
        defaultPaymentShouldBeFound("paymentRefNumber.equals=" + DEFAULT_PAYMENT_REF_NUMBER);

        // Get all the paymentList where paymentRefNumber equals to UPDATED_PAYMENT_REF_NUMBER
        defaultPaymentShouldNotBeFound("paymentRefNumber.equals=" + UPDATED_PAYMENT_REF_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentRefNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentRefNumber not equals to DEFAULT_PAYMENT_REF_NUMBER
        defaultPaymentShouldNotBeFound("paymentRefNumber.notEquals=" + DEFAULT_PAYMENT_REF_NUMBER);

        // Get all the paymentList where paymentRefNumber not equals to UPDATED_PAYMENT_REF_NUMBER
        defaultPaymentShouldBeFound("paymentRefNumber.notEquals=" + UPDATED_PAYMENT_REF_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentRefNumberIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentRefNumber in DEFAULT_PAYMENT_REF_NUMBER or UPDATED_PAYMENT_REF_NUMBER
        defaultPaymentShouldBeFound("paymentRefNumber.in=" + DEFAULT_PAYMENT_REF_NUMBER + "," + UPDATED_PAYMENT_REF_NUMBER);

        // Get all the paymentList where paymentRefNumber equals to UPDATED_PAYMENT_REF_NUMBER
        defaultPaymentShouldNotBeFound("paymentRefNumber.in=" + UPDATED_PAYMENT_REF_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentRefNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentRefNumber is not null
        defaultPaymentShouldBeFound("paymentRefNumber.specified=true");

        // Get all the paymentList where paymentRefNumber is null
        defaultPaymentShouldNotBeFound("paymentRefNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsByPaymentRefNumberContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentRefNumber contains DEFAULT_PAYMENT_REF_NUMBER
        defaultPaymentShouldBeFound("paymentRefNumber.contains=" + DEFAULT_PAYMENT_REF_NUMBER);

        // Get all the paymentList where paymentRefNumber contains UPDATED_PAYMENT_REF_NUMBER
        defaultPaymentShouldNotBeFound("paymentRefNumber.contains=" + UPDATED_PAYMENT_REF_NUMBER);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentRefNumberNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentRefNumber does not contain DEFAULT_PAYMENT_REF_NUMBER
        defaultPaymentShouldNotBeFound("paymentRefNumber.doesNotContain=" + DEFAULT_PAYMENT_REF_NUMBER);

        // Get all the paymentList where paymentRefNumber does not contain UPDATED_PAYMENT_REF_NUMBER
        defaultPaymentShouldBeFound("paymentRefNumber.doesNotContain=" + UPDATED_PAYMENT_REF_NUMBER);
    }


    @Test
    @Transactional
    public void getAllPaymentsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where amount equals to DEFAULT_AMOUNT
        defaultPaymentShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the paymentList where amount equals to UPDATED_AMOUNT
        defaultPaymentShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where amount not equals to DEFAULT_AMOUNT
        defaultPaymentShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the paymentList where amount not equals to UPDATED_AMOUNT
        defaultPaymentShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultPaymentShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the paymentList where amount equals to UPDATED_AMOUNT
        defaultPaymentShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where amount is not null
        defaultPaymentShouldBeFound("amount.specified=true");

        // Get all the paymentList where amount is null
        defaultPaymentShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultPaymentShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the paymentList where amount is greater than or equal to UPDATED_AMOUNT
        defaultPaymentShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where amount is less than or equal to DEFAULT_AMOUNT
        defaultPaymentShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the paymentList where amount is less than or equal to SMALLER_AMOUNT
        defaultPaymentShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where amount is less than DEFAULT_AMOUNT
        defaultPaymentShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the paymentList where amount is less than UPDATED_AMOUNT
        defaultPaymentShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where amount is greater than DEFAULT_AMOUNT
        defaultPaymentShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the paymentList where amount is greater than SMALLER_AMOUNT
        defaultPaymentShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllPaymentsByPaymentStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentStatus equals to DEFAULT_PAYMENT_STATUS
        defaultPaymentShouldBeFound("paymentStatus.equals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the paymentList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultPaymentShouldNotBeFound("paymentStatus.equals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentStatus not equals to DEFAULT_PAYMENT_STATUS
        defaultPaymentShouldNotBeFound("paymentStatus.notEquals=" + DEFAULT_PAYMENT_STATUS);

        // Get all the paymentList where paymentStatus not equals to UPDATED_PAYMENT_STATUS
        defaultPaymentShouldBeFound("paymentStatus.notEquals=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentStatusIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentStatus in DEFAULT_PAYMENT_STATUS or UPDATED_PAYMENT_STATUS
        defaultPaymentShouldBeFound("paymentStatus.in=" + DEFAULT_PAYMENT_STATUS + "," + UPDATED_PAYMENT_STATUS);

        // Get all the paymentList where paymentStatus equals to UPDATED_PAYMENT_STATUS
        defaultPaymentShouldNotBeFound("paymentStatus.in=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentStatus is not null
        defaultPaymentShouldBeFound("paymentStatus.specified=true");

        // Get all the paymentList where paymentStatus is null
        defaultPaymentShouldNotBeFound("paymentStatus.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsByPaymentStatusContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentStatus contains DEFAULT_PAYMENT_STATUS
        defaultPaymentShouldBeFound("paymentStatus.contains=" + DEFAULT_PAYMENT_STATUS);

        // Get all the paymentList where paymentStatus contains UPDATED_PAYMENT_STATUS
        defaultPaymentShouldNotBeFound("paymentStatus.contains=" + UPDATED_PAYMENT_STATUS);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPaymentStatusNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where paymentStatus does not contain DEFAULT_PAYMENT_STATUS
        defaultPaymentShouldNotBeFound("paymentStatus.doesNotContain=" + DEFAULT_PAYMENT_STATUS);

        // Get all the paymentList where paymentStatus does not contain UPDATED_PAYMENT_STATUS
        defaultPaymentShouldBeFound("paymentStatus.doesNotContain=" + UPDATED_PAYMENT_STATUS);
    }


    @Test
    @Transactional
    public void getAllPaymentsByMihpayIdIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where mihpayId equals to DEFAULT_MIHPAY_ID
        defaultPaymentShouldBeFound("mihpayId.equals=" + DEFAULT_MIHPAY_ID);

        // Get all the paymentList where mihpayId equals to UPDATED_MIHPAY_ID
        defaultPaymentShouldNotBeFound("mihpayId.equals=" + UPDATED_MIHPAY_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentsByMihpayIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where mihpayId not equals to DEFAULT_MIHPAY_ID
        defaultPaymentShouldNotBeFound("mihpayId.notEquals=" + DEFAULT_MIHPAY_ID);

        // Get all the paymentList where mihpayId not equals to UPDATED_MIHPAY_ID
        defaultPaymentShouldBeFound("mihpayId.notEquals=" + UPDATED_MIHPAY_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentsByMihpayIdIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where mihpayId in DEFAULT_MIHPAY_ID or UPDATED_MIHPAY_ID
        defaultPaymentShouldBeFound("mihpayId.in=" + DEFAULT_MIHPAY_ID + "," + UPDATED_MIHPAY_ID);

        // Get all the paymentList where mihpayId equals to UPDATED_MIHPAY_ID
        defaultPaymentShouldNotBeFound("mihpayId.in=" + UPDATED_MIHPAY_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentsByMihpayIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where mihpayId is not null
        defaultPaymentShouldBeFound("mihpayId.specified=true");

        // Get all the paymentList where mihpayId is null
        defaultPaymentShouldNotBeFound("mihpayId.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsByMihpayIdContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where mihpayId contains DEFAULT_MIHPAY_ID
        defaultPaymentShouldBeFound("mihpayId.contains=" + DEFAULT_MIHPAY_ID);

        // Get all the paymentList where mihpayId contains UPDATED_MIHPAY_ID
        defaultPaymentShouldNotBeFound("mihpayId.contains=" + UPDATED_MIHPAY_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentsByMihpayIdNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where mihpayId does not contain DEFAULT_MIHPAY_ID
        defaultPaymentShouldNotBeFound("mihpayId.doesNotContain=" + DEFAULT_MIHPAY_ID);

        // Get all the paymentList where mihpayId does not contain UPDATED_MIHPAY_ID
        defaultPaymentShouldBeFound("mihpayId.doesNotContain=" + UPDATED_MIHPAY_ID);
    }


    @Test
    @Transactional
    public void getAllPaymentsByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where email equals to DEFAULT_EMAIL
        defaultPaymentShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the paymentList where email equals to UPDATED_EMAIL
        defaultPaymentShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPaymentsByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where email not equals to DEFAULT_EMAIL
        defaultPaymentShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the paymentList where email not equals to UPDATED_EMAIL
        defaultPaymentShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPaymentsByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultPaymentShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the paymentList where email equals to UPDATED_EMAIL
        defaultPaymentShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPaymentsByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where email is not null
        defaultPaymentShouldBeFound("email.specified=true");

        // Get all the paymentList where email is null
        defaultPaymentShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsByEmailContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where email contains DEFAULT_EMAIL
        defaultPaymentShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the paymentList where email contains UPDATED_EMAIL
        defaultPaymentShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPaymentsByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where email does not contain DEFAULT_EMAIL
        defaultPaymentShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the paymentList where email does not contain UPDATED_EMAIL
        defaultPaymentShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllPaymentsByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where phone equals to DEFAULT_PHONE
        defaultPaymentShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the paymentList where phone equals to UPDATED_PHONE
        defaultPaymentShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where phone not equals to DEFAULT_PHONE
        defaultPaymentShouldNotBeFound("phone.notEquals=" + DEFAULT_PHONE);

        // Get all the paymentList where phone not equals to UPDATED_PHONE
        defaultPaymentShouldBeFound("phone.notEquals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultPaymentShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the paymentList where phone equals to UPDATED_PHONE
        defaultPaymentShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where phone is not null
        defaultPaymentShouldBeFound("phone.specified=true");

        // Get all the paymentList where phone is null
        defaultPaymentShouldNotBeFound("phone.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsByPhoneContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where phone contains DEFAULT_PHONE
        defaultPaymentShouldBeFound("phone.contains=" + DEFAULT_PHONE);

        // Get all the paymentList where phone contains UPDATED_PHONE
        defaultPaymentShouldNotBeFound("phone.contains=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllPaymentsByPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where phone does not contain DEFAULT_PHONE
        defaultPaymentShouldNotBeFound("phone.doesNotContain=" + DEFAULT_PHONE);

        // Get all the paymentList where phone does not contain UPDATED_PHONE
        defaultPaymentShouldBeFound("phone.doesNotContain=" + UPDATED_PHONE);
    }


    @Test
    @Transactional
    public void getAllPaymentsByProductInfoIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where productInfo equals to DEFAULT_PRODUCT_INFO
        defaultPaymentShouldBeFound("productInfo.equals=" + DEFAULT_PRODUCT_INFO);

        // Get all the paymentList where productInfo equals to UPDATED_PRODUCT_INFO
        defaultPaymentShouldNotBeFound("productInfo.equals=" + UPDATED_PRODUCT_INFO);
    }

    @Test
    @Transactional
    public void getAllPaymentsByProductInfoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where productInfo not equals to DEFAULT_PRODUCT_INFO
        defaultPaymentShouldNotBeFound("productInfo.notEquals=" + DEFAULT_PRODUCT_INFO);

        // Get all the paymentList where productInfo not equals to UPDATED_PRODUCT_INFO
        defaultPaymentShouldBeFound("productInfo.notEquals=" + UPDATED_PRODUCT_INFO);
    }

    @Test
    @Transactional
    public void getAllPaymentsByProductInfoIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where productInfo in DEFAULT_PRODUCT_INFO or UPDATED_PRODUCT_INFO
        defaultPaymentShouldBeFound("productInfo.in=" + DEFAULT_PRODUCT_INFO + "," + UPDATED_PRODUCT_INFO);

        // Get all the paymentList where productInfo equals to UPDATED_PRODUCT_INFO
        defaultPaymentShouldNotBeFound("productInfo.in=" + UPDATED_PRODUCT_INFO);
    }

    @Test
    @Transactional
    public void getAllPaymentsByProductInfoIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where productInfo is not null
        defaultPaymentShouldBeFound("productInfo.specified=true");

        // Get all the paymentList where productInfo is null
        defaultPaymentShouldNotBeFound("productInfo.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsByProductInfoContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where productInfo contains DEFAULT_PRODUCT_INFO
        defaultPaymentShouldBeFound("productInfo.contains=" + DEFAULT_PRODUCT_INFO);

        // Get all the paymentList where productInfo contains UPDATED_PRODUCT_INFO
        defaultPaymentShouldNotBeFound("productInfo.contains=" + UPDATED_PRODUCT_INFO);
    }

    @Test
    @Transactional
    public void getAllPaymentsByProductInfoNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where productInfo does not contain DEFAULT_PRODUCT_INFO
        defaultPaymentShouldNotBeFound("productInfo.doesNotContain=" + DEFAULT_PRODUCT_INFO);

        // Get all the paymentList where productInfo does not contain UPDATED_PRODUCT_INFO
        defaultPaymentShouldBeFound("productInfo.doesNotContain=" + UPDATED_PRODUCT_INFO);
    }


    @Test
    @Transactional
    public void getAllPaymentsByTxnIdIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where txnId equals to DEFAULT_TXN_ID
        defaultPaymentShouldBeFound("txnId.equals=" + DEFAULT_TXN_ID);

        // Get all the paymentList where txnId equals to UPDATED_TXN_ID
        defaultPaymentShouldNotBeFound("txnId.equals=" + UPDATED_TXN_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTxnIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where txnId not equals to DEFAULT_TXN_ID
        defaultPaymentShouldNotBeFound("txnId.notEquals=" + DEFAULT_TXN_ID);

        // Get all the paymentList where txnId not equals to UPDATED_TXN_ID
        defaultPaymentShouldBeFound("txnId.notEquals=" + UPDATED_TXN_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTxnIdIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where txnId in DEFAULT_TXN_ID or UPDATED_TXN_ID
        defaultPaymentShouldBeFound("txnId.in=" + DEFAULT_TXN_ID + "," + UPDATED_TXN_ID);

        // Get all the paymentList where txnId equals to UPDATED_TXN_ID
        defaultPaymentShouldNotBeFound("txnId.in=" + UPDATED_TXN_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTxnIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where txnId is not null
        defaultPaymentShouldBeFound("txnId.specified=true");

        // Get all the paymentList where txnId is null
        defaultPaymentShouldNotBeFound("txnId.specified=false");
    }
                @Test
    @Transactional
    public void getAllPaymentsByTxnIdContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where txnId contains DEFAULT_TXN_ID
        defaultPaymentShouldBeFound("txnId.contains=" + DEFAULT_TXN_ID);

        // Get all the paymentList where txnId contains UPDATED_TXN_ID
        defaultPaymentShouldNotBeFound("txnId.contains=" + UPDATED_TXN_ID);
    }

    @Test
    @Transactional
    public void getAllPaymentsByTxnIdNotContainsSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where txnId does not contain DEFAULT_TXN_ID
        defaultPaymentShouldNotBeFound("txnId.doesNotContain=" + DEFAULT_TXN_ID);

        // Get all the paymentList where txnId does not contain UPDATED_TXN_ID
        defaultPaymentShouldBeFound("txnId.doesNotContain=" + UPDATED_TXN_ID);
    }


    @Test
    @Transactional
    public void getAllPaymentsByActualAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where actualAmount equals to DEFAULT_ACTUAL_AMOUNT
        defaultPaymentShouldBeFound("actualAmount.equals=" + DEFAULT_ACTUAL_AMOUNT);

        // Get all the paymentList where actualAmount equals to UPDATED_ACTUAL_AMOUNT
        defaultPaymentShouldNotBeFound("actualAmount.equals=" + UPDATED_ACTUAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByActualAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where actualAmount not equals to DEFAULT_ACTUAL_AMOUNT
        defaultPaymentShouldNotBeFound("actualAmount.notEquals=" + DEFAULT_ACTUAL_AMOUNT);

        // Get all the paymentList where actualAmount not equals to UPDATED_ACTUAL_AMOUNT
        defaultPaymentShouldBeFound("actualAmount.notEquals=" + UPDATED_ACTUAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByActualAmountIsInShouldWork() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where actualAmount in DEFAULT_ACTUAL_AMOUNT or UPDATED_ACTUAL_AMOUNT
        defaultPaymentShouldBeFound("actualAmount.in=" + DEFAULT_ACTUAL_AMOUNT + "," + UPDATED_ACTUAL_AMOUNT);

        // Get all the paymentList where actualAmount equals to UPDATED_ACTUAL_AMOUNT
        defaultPaymentShouldNotBeFound("actualAmount.in=" + UPDATED_ACTUAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByActualAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where actualAmount is not null
        defaultPaymentShouldBeFound("actualAmount.specified=true");

        // Get all the paymentList where actualAmount is null
        defaultPaymentShouldNotBeFound("actualAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPaymentsByActualAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where actualAmount is greater than or equal to DEFAULT_ACTUAL_AMOUNT
        defaultPaymentShouldBeFound("actualAmount.greaterThanOrEqual=" + DEFAULT_ACTUAL_AMOUNT);

        // Get all the paymentList where actualAmount is greater than or equal to UPDATED_ACTUAL_AMOUNT
        defaultPaymentShouldNotBeFound("actualAmount.greaterThanOrEqual=" + UPDATED_ACTUAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByActualAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where actualAmount is less than or equal to DEFAULT_ACTUAL_AMOUNT
        defaultPaymentShouldBeFound("actualAmount.lessThanOrEqual=" + DEFAULT_ACTUAL_AMOUNT);

        // Get all the paymentList where actualAmount is less than or equal to SMALLER_ACTUAL_AMOUNT
        defaultPaymentShouldNotBeFound("actualAmount.lessThanOrEqual=" + SMALLER_ACTUAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByActualAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where actualAmount is less than DEFAULT_ACTUAL_AMOUNT
        defaultPaymentShouldNotBeFound("actualAmount.lessThan=" + DEFAULT_ACTUAL_AMOUNT);

        // Get all the paymentList where actualAmount is less than UPDATED_ACTUAL_AMOUNT
        defaultPaymentShouldBeFound("actualAmount.lessThan=" + UPDATED_ACTUAL_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllPaymentsByActualAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);

        // Get all the paymentList where actualAmount is greater than DEFAULT_ACTUAL_AMOUNT
        defaultPaymentShouldNotBeFound("actualAmount.greaterThan=" + DEFAULT_ACTUAL_AMOUNT);

        // Get all the paymentList where actualAmount is greater than SMALLER_ACTUAL_AMOUNT
        defaultPaymentShouldBeFound("actualAmount.greaterThan=" + SMALLER_ACTUAL_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllPaymentsByPaymentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        PaymentType paymentType = PaymentTypeResourceIT.createEntity(em);
        em.persist(paymentType);
        em.flush();
        payment.setPaymentType(paymentType);
        paymentRepository.saveAndFlush(payment);
        Long paymentTypeId = paymentType.getId();

        // Get all the paymentList where paymentType equals to paymentTypeId
        defaultPaymentShouldBeFound("paymentTypeId.equals=" + paymentTypeId);

        // Get all the paymentList where paymentType equals to paymentTypeId + 1
        defaultPaymentShouldNotBeFound("paymentTypeId.equals=" + (paymentTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllPaymentsByPaymentMethodTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        PaymentMethodType paymentMethodType = PaymentMethodTypeResourceIT.createEntity(em);
        em.persist(paymentMethodType);
        em.flush();
        payment.setPaymentMethodType(paymentMethodType);
        paymentRepository.saveAndFlush(payment);
        Long paymentMethodTypeId = paymentMethodType.getId();

        // Get all the paymentList where paymentMethodType equals to paymentMethodTypeId
        defaultPaymentShouldBeFound("paymentMethodTypeId.equals=" + paymentMethodTypeId);

        // Get all the paymentList where paymentMethodType equals to paymentMethodTypeId + 1
        defaultPaymentShouldNotBeFound("paymentMethodTypeId.equals=" + (paymentMethodTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllPaymentsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        payment.setStatus(status);
        paymentRepository.saveAndFlush(payment);
        Long statusId = status.getId();

        // Get all the paymentList where status equals to statusId
        defaultPaymentShouldBeFound("statusId.equals=" + statusId);

        // Get all the paymentList where status equals to statusId + 1
        defaultPaymentShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }


    @Test
    @Transactional
    public void getAllPaymentsByPaymentMethodIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        PaymentMethod paymentMethod = PaymentMethodResourceIT.createEntity(em);
        em.persist(paymentMethod);
        em.flush();
        payment.setPaymentMethod(paymentMethod);
        paymentRepository.saveAndFlush(payment);
        Long paymentMethodId = paymentMethod.getId();

        // Get all the paymentList where paymentMethod equals to paymentMethodId
        defaultPaymentShouldBeFound("paymentMethodId.equals=" + paymentMethodId);

        // Get all the paymentList where paymentMethod equals to paymentMethodId + 1
        defaultPaymentShouldNotBeFound("paymentMethodId.equals=" + (paymentMethodId + 1));
    }


    @Test
    @Transactional
    public void getAllPaymentsByPaymentGatewayResponseIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        PaymentGatewayResponse paymentGatewayResponse = PaymentGatewayResponseResourceIT.createEntity(em);
        em.persist(paymentGatewayResponse);
        em.flush();
        payment.setPaymentGatewayResponse(paymentGatewayResponse);
        paymentRepository.saveAndFlush(payment);
        Long paymentGatewayResponseId = paymentGatewayResponse.getId();

        // Get all the paymentList where paymentGatewayResponse equals to paymentGatewayResponseId
        defaultPaymentShouldBeFound("paymentGatewayResponseId.equals=" + paymentGatewayResponseId);

        // Get all the paymentList where paymentGatewayResponse equals to paymentGatewayResponseId + 1
        defaultPaymentShouldNotBeFound("paymentGatewayResponseId.equals=" + (paymentGatewayResponseId + 1));
    }


    @Test
    @Transactional
    public void getAllPaymentsByPartyIdFromIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        Party partyIdFrom = PartyResourceIT.createEntity(em);
        em.persist(partyIdFrom);
        em.flush();
        payment.setPartyIdFrom(partyIdFrom);
        paymentRepository.saveAndFlush(payment);
        Long partyIdFromId = partyIdFrom.getId();

        // Get all the paymentList where partyIdFrom equals to partyIdFromId
        defaultPaymentShouldBeFound("partyIdFromId.equals=" + partyIdFromId);

        // Get all the paymentList where partyIdFrom equals to partyIdFromId + 1
        defaultPaymentShouldNotBeFound("partyIdFromId.equals=" + (partyIdFromId + 1));
    }


    @Test
    @Transactional
    public void getAllPaymentsByPartyIdToIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        Party partyIdTo = PartyResourceIT.createEntity(em);
        em.persist(partyIdTo);
        em.flush();
        payment.setPartyIdTo(partyIdTo);
        paymentRepository.saveAndFlush(payment);
        Long partyIdToId = partyIdTo.getId();

        // Get all the paymentList where partyIdTo equals to partyIdToId
        defaultPaymentShouldBeFound("partyIdToId.equals=" + partyIdToId);

        // Get all the paymentList where partyIdTo equals to partyIdToId + 1
        defaultPaymentShouldNotBeFound("partyIdToId.equals=" + (partyIdToId + 1));
    }


    @Test
    @Transactional
    public void getAllPaymentsByRoleTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        RoleType roleType = RoleTypeResourceIT.createEntity(em);
        em.persist(roleType);
        em.flush();
        payment.setRoleType(roleType);
        paymentRepository.saveAndFlush(payment);
        Long roleTypeId = roleType.getId();

        // Get all the paymentList where roleType equals to roleTypeId
        defaultPaymentShouldBeFound("roleTypeId.equals=" + roleTypeId);

        // Get all the paymentList where roleType equals to roleTypeId + 1
        defaultPaymentShouldNotBeFound("roleTypeId.equals=" + (roleTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllPaymentsByCurrencyUomIsEqualToSomething() throws Exception {
        // Initialize the database
        paymentRepository.saveAndFlush(payment);
        Uom currencyUom = UomResourceIT.createEntity(em);
        em.persist(currencyUom);
        em.flush();
        payment.setCurrencyUom(currencyUom);
        paymentRepository.saveAndFlush(payment);
        Long currencyUomId = currencyUom.getId();

        // Get all the paymentList where currencyUom equals to currencyUomId
        defaultPaymentShouldBeFound("currencyUomId.equals=" + currencyUomId);

        // Get all the paymentList where currencyUom equals to currencyUomId + 1
        defaultPaymentShouldNotBeFound("currencyUomId.equals=" + (currencyUomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPaymentShouldBeFound(String filter) throws Exception {
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(payment.getId().intValue())))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(DEFAULT_EFFECTIVE_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentDate").value(hasItem(DEFAULT_PAYMENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].paymentRefNumber").value(hasItem(DEFAULT_PAYMENT_REF_NUMBER)))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].paymentStatus").value(hasItem(DEFAULT_PAYMENT_STATUS)))
            .andExpect(jsonPath("$.[*].mihpayId").value(hasItem(DEFAULT_MIHPAY_ID)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].productInfo").value(hasItem(DEFAULT_PRODUCT_INFO)))
            .andExpect(jsonPath("$.[*].txnId").value(hasItem(DEFAULT_TXN_ID)))
            .andExpect(jsonPath("$.[*].actualAmount").value(hasItem(DEFAULT_ACTUAL_AMOUNT.intValue())));

        // Check, that the count call also returns 1
        restPaymentMockMvc.perform(get("/api/payments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPaymentShouldNotBeFound(String filter) throws Exception {
        restPaymentMockMvc.perform(get("/api/payments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPaymentMockMvc.perform(get("/api/payments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPayment() throws Exception {
        // Get the payment
        restPaymentMockMvc.perform(get("/api/payments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayment() throws Exception {
        // Initialize the database
        paymentService.save(payment);

        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // Update the payment
        Payment updatedPayment = paymentRepository.findById(payment.getId()).get();
        // Disconnect from session so that the updates on updatedPayment are not directly saved in db
        em.detach(updatedPayment);
        updatedPayment
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .paymentDate(UPDATED_PAYMENT_DATE)
            .paymentRefNumber(UPDATED_PAYMENT_REF_NUMBER)
            .amount(UPDATED_AMOUNT)
            .paymentStatus(UPDATED_PAYMENT_STATUS)
            .mihpayId(UPDATED_MIHPAY_ID)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE)
            .productInfo(UPDATED_PRODUCT_INFO)
            .txnId(UPDATED_TXN_ID)
            .actualAmount(UPDATED_ACTUAL_AMOUNT);

        restPaymentMockMvc.perform(put("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPayment)))
            .andExpect(status().isOk());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
        Payment testPayment = paymentList.get(paymentList.size() - 1);
        assertThat(testPayment.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testPayment.getPaymentDate()).isEqualTo(UPDATED_PAYMENT_DATE);
        assertThat(testPayment.getPaymentRefNumber()).isEqualTo(UPDATED_PAYMENT_REF_NUMBER);
        assertThat(testPayment.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testPayment.getPaymentStatus()).isEqualTo(UPDATED_PAYMENT_STATUS);
        assertThat(testPayment.getMihpayId()).isEqualTo(UPDATED_MIHPAY_ID);
        assertThat(testPayment.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPayment.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPayment.getProductInfo()).isEqualTo(UPDATED_PRODUCT_INFO);
        assertThat(testPayment.getTxnId()).isEqualTo(UPDATED_TXN_ID);
        assertThat(testPayment.getActualAmount()).isEqualTo(UPDATED_ACTUAL_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPayment() throws Exception {
        int databaseSizeBeforeUpdate = paymentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentMockMvc.perform(put("/api/payments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(payment)))
            .andExpect(status().isBadRequest());

        // Validate the Payment in the database
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePayment() throws Exception {
        // Initialize the database
        paymentService.save(payment);

        int databaseSizeBeforeDelete = paymentRepository.findAll().size();

        // Delete the payment
        restPaymentMockMvc.perform(delete("/api/payments/{id}", payment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Payment> paymentList = paymentRepository.findAll();
        assertThat(paymentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
