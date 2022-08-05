package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.Invoice;
import com.hr.domain.InvoiceType;
import com.hr.domain.Party;
import com.hr.domain.RoleType;
import com.hr.domain.Status;
import com.hr.domain.ContactMech;
import com.hr.repository.InvoiceRepository;
import com.hr.service.InvoiceService;
import com.hr.service.dto.InvoiceCriteria;
import com.hr.service.InvoiceQueryService;

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
 * Integration tests for the {@link InvoiceResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InvoiceResourceIT {

    private static final ZonedDateTime DEFAULT_INVOICE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_INVOICE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_INVOICE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DUE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DUE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_PAID_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_PAID_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_PAID_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final String DEFAULT_INVOICE_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_REFERENCE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REFERENCE_NUMBER = "BBBBBBBBBB";

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private InvoiceQueryService invoiceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceMockMvc;

    private Invoice invoice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .dueDate(DEFAULT_DUE_DATE)
            .paidDate(DEFAULT_PAID_DATE)
            .invoiceMessage(DEFAULT_INVOICE_MESSAGE)
            .referenceNumber(DEFAULT_REFERENCE_NUMBER);
        return invoice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Invoice createUpdatedEntity(EntityManager em) {
        Invoice invoice = new Invoice()
            .invoiceDate(UPDATED_INVOICE_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .paidDate(UPDATED_PAID_DATE)
            .invoiceMessage(UPDATED_INVOICE_MESSAGE)
            .referenceNumber(UPDATED_REFERENCE_NUMBER);
        return invoice;
    }

    @BeforeEach
    public void initTest() {
        invoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoice() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();
        // Create the Invoice
        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isCreated());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate + 1);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testInvoice.getDueDate()).isEqualTo(DEFAULT_DUE_DATE);
        assertThat(testInvoice.getPaidDate()).isEqualTo(DEFAULT_PAID_DATE);
        assertThat(testInvoice.getInvoiceMessage()).isEqualTo(DEFAULT_INVOICE_MESSAGE);
        assertThat(testInvoice.getReferenceNumber()).isEqualTo(DEFAULT_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    public void createInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceRepository.findAll().size();

        // Create the Invoice with an existing ID
        invoice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceMockMvc.perform(post("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInvoices() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(sameInstant(DEFAULT_INVOICE_DATE))))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(sameInstant(DEFAULT_DUE_DATE))))
            .andExpect(jsonPath("$.[*].paidDate").value(hasItem(sameInstant(DEFAULT_PAID_DATE))))
            .andExpect(jsonPath("$.[*].invoiceMessage").value(hasItem(DEFAULT_INVOICE_MESSAGE)))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getInvoice() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", invoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoice.getId().intValue()))
            .andExpect(jsonPath("$.invoiceDate").value(sameInstant(DEFAULT_INVOICE_DATE)))
            .andExpect(jsonPath("$.dueDate").value(sameInstant(DEFAULT_DUE_DATE)))
            .andExpect(jsonPath("$.paidDate").value(sameInstant(DEFAULT_PAID_DATE)))
            .andExpect(jsonPath("$.invoiceMessage").value(DEFAULT_INVOICE_MESSAGE))
            .andExpect(jsonPath("$.referenceNumber").value(DEFAULT_REFERENCE_NUMBER));
    }


    @Test
    @Transactional
    public void getInvoicesByIdFiltering() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        Long id = invoice.getId();

        defaultInvoiceShouldBeFound("id.equals=" + id);
        defaultInvoiceShouldNotBeFound("id.notEquals=" + id);

        defaultInvoiceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInvoiceShouldNotBeFound("id.greaterThan=" + id);

        defaultInvoiceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInvoiceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceDate equals to DEFAULT_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoiceDate.equals=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoiceDate.equals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceDate not equals to DEFAULT_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoiceDate.notEquals=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoiceDate not equals to UPDATED_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoiceDate.notEquals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceDate in DEFAULT_INVOICE_DATE or UPDATED_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoiceDate.in=" + DEFAULT_INVOICE_DATE + "," + UPDATED_INVOICE_DATE);

        // Get all the invoiceList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoiceDate.in=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceDate is not null
        defaultInvoiceShouldBeFound("invoiceDate.specified=true");

        // Get all the invoiceList where invoiceDate is null
        defaultInvoiceShouldNotBeFound("invoiceDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceDate is greater than or equal to DEFAULT_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoiceDate.greaterThanOrEqual=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoiceDate is greater than or equal to UPDATED_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoiceDate.greaterThanOrEqual=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceDate is less than or equal to DEFAULT_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoiceDate.lessThanOrEqual=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoiceDate is less than or equal to SMALLER_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoiceDate.lessThanOrEqual=" + SMALLER_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceDate is less than DEFAULT_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoiceDate.lessThan=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoiceDate is less than UPDATED_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoiceDate.lessThan=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceDate is greater than DEFAULT_INVOICE_DATE
        defaultInvoiceShouldNotBeFound("invoiceDate.greaterThan=" + DEFAULT_INVOICE_DATE);

        // Get all the invoiceList where invoiceDate is greater than SMALLER_INVOICE_DATE
        defaultInvoiceShouldBeFound("invoiceDate.greaterThan=" + SMALLER_INVOICE_DATE);
    }


    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate equals to DEFAULT_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.equals=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate equals to UPDATED_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.equals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate not equals to DEFAULT_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.notEquals=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate not equals to UPDATED_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.notEquals=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate in DEFAULT_DUE_DATE or UPDATED_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.in=" + DEFAULT_DUE_DATE + "," + UPDATED_DUE_DATE);

        // Get all the invoiceList where dueDate equals to UPDATED_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.in=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate is not null
        defaultInvoiceShouldBeFound("dueDate.specified=true");

        // Get all the invoiceList where dueDate is null
        defaultInvoiceShouldNotBeFound("dueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate is greater than or equal to DEFAULT_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.greaterThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate is greater than or equal to UPDATED_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.greaterThanOrEqual=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate is less than or equal to DEFAULT_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.lessThanOrEqual=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate is less than or equal to SMALLER_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.lessThanOrEqual=" + SMALLER_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate is less than DEFAULT_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.lessThan=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate is less than UPDATED_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.lessThan=" + UPDATED_DUE_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByDueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where dueDate is greater than DEFAULT_DUE_DATE
        defaultInvoiceShouldNotBeFound("dueDate.greaterThan=" + DEFAULT_DUE_DATE);

        // Get all the invoiceList where dueDate is greater than SMALLER_DUE_DATE
        defaultInvoiceShouldBeFound("dueDate.greaterThan=" + SMALLER_DUE_DATE);
    }


    @Test
    @Transactional
    public void getAllInvoicesByPaidDateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where paidDate equals to DEFAULT_PAID_DATE
        defaultInvoiceShouldBeFound("paidDate.equals=" + DEFAULT_PAID_DATE);

        // Get all the invoiceList where paidDate equals to UPDATED_PAID_DATE
        defaultInvoiceShouldNotBeFound("paidDate.equals=" + UPDATED_PAID_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaidDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where paidDate not equals to DEFAULT_PAID_DATE
        defaultInvoiceShouldNotBeFound("paidDate.notEquals=" + DEFAULT_PAID_DATE);

        // Get all the invoiceList where paidDate not equals to UPDATED_PAID_DATE
        defaultInvoiceShouldBeFound("paidDate.notEquals=" + UPDATED_PAID_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaidDateIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where paidDate in DEFAULT_PAID_DATE or UPDATED_PAID_DATE
        defaultInvoiceShouldBeFound("paidDate.in=" + DEFAULT_PAID_DATE + "," + UPDATED_PAID_DATE);

        // Get all the invoiceList where paidDate equals to UPDATED_PAID_DATE
        defaultInvoiceShouldNotBeFound("paidDate.in=" + UPDATED_PAID_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaidDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where paidDate is not null
        defaultInvoiceShouldBeFound("paidDate.specified=true");

        // Get all the invoiceList where paidDate is null
        defaultInvoiceShouldNotBeFound("paidDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaidDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where paidDate is greater than or equal to DEFAULT_PAID_DATE
        defaultInvoiceShouldBeFound("paidDate.greaterThanOrEqual=" + DEFAULT_PAID_DATE);

        // Get all the invoiceList where paidDate is greater than or equal to UPDATED_PAID_DATE
        defaultInvoiceShouldNotBeFound("paidDate.greaterThanOrEqual=" + UPDATED_PAID_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaidDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where paidDate is less than or equal to DEFAULT_PAID_DATE
        defaultInvoiceShouldBeFound("paidDate.lessThanOrEqual=" + DEFAULT_PAID_DATE);

        // Get all the invoiceList where paidDate is less than or equal to SMALLER_PAID_DATE
        defaultInvoiceShouldNotBeFound("paidDate.lessThanOrEqual=" + SMALLER_PAID_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaidDateIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where paidDate is less than DEFAULT_PAID_DATE
        defaultInvoiceShouldNotBeFound("paidDate.lessThan=" + DEFAULT_PAID_DATE);

        // Get all the invoiceList where paidDate is less than UPDATED_PAID_DATE
        defaultInvoiceShouldBeFound("paidDate.lessThan=" + UPDATED_PAID_DATE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByPaidDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where paidDate is greater than DEFAULT_PAID_DATE
        defaultInvoiceShouldNotBeFound("paidDate.greaterThan=" + DEFAULT_PAID_DATE);

        // Get all the invoiceList where paidDate is greater than SMALLER_PAID_DATE
        defaultInvoiceShouldBeFound("paidDate.greaterThan=" + SMALLER_PAID_DATE);
    }


    @Test
    @Transactional
    public void getAllInvoicesByInvoiceMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceMessage equals to DEFAULT_INVOICE_MESSAGE
        defaultInvoiceShouldBeFound("invoiceMessage.equals=" + DEFAULT_INVOICE_MESSAGE);

        // Get all the invoiceList where invoiceMessage equals to UPDATED_INVOICE_MESSAGE
        defaultInvoiceShouldNotBeFound("invoiceMessage.equals=" + UPDATED_INVOICE_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceMessageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceMessage not equals to DEFAULT_INVOICE_MESSAGE
        defaultInvoiceShouldNotBeFound("invoiceMessage.notEquals=" + DEFAULT_INVOICE_MESSAGE);

        // Get all the invoiceList where invoiceMessage not equals to UPDATED_INVOICE_MESSAGE
        defaultInvoiceShouldBeFound("invoiceMessage.notEquals=" + UPDATED_INVOICE_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceMessageIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceMessage in DEFAULT_INVOICE_MESSAGE or UPDATED_INVOICE_MESSAGE
        defaultInvoiceShouldBeFound("invoiceMessage.in=" + DEFAULT_INVOICE_MESSAGE + "," + UPDATED_INVOICE_MESSAGE);

        // Get all the invoiceList where invoiceMessage equals to UPDATED_INVOICE_MESSAGE
        defaultInvoiceShouldNotBeFound("invoiceMessage.in=" + UPDATED_INVOICE_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceMessage is not null
        defaultInvoiceShouldBeFound("invoiceMessage.specified=true");

        // Get all the invoiceList where invoiceMessage is null
        defaultInvoiceShouldNotBeFound("invoiceMessage.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByInvoiceMessageContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceMessage contains DEFAULT_INVOICE_MESSAGE
        defaultInvoiceShouldBeFound("invoiceMessage.contains=" + DEFAULT_INVOICE_MESSAGE);

        // Get all the invoiceList where invoiceMessage contains UPDATED_INVOICE_MESSAGE
        defaultInvoiceShouldNotBeFound("invoiceMessage.contains=" + UPDATED_INVOICE_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllInvoicesByInvoiceMessageNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where invoiceMessage does not contain DEFAULT_INVOICE_MESSAGE
        defaultInvoiceShouldNotBeFound("invoiceMessage.doesNotContain=" + DEFAULT_INVOICE_MESSAGE);

        // Get all the invoiceList where invoiceMessage does not contain UPDATED_INVOICE_MESSAGE
        defaultInvoiceShouldBeFound("invoiceMessage.doesNotContain=" + UPDATED_INVOICE_MESSAGE);
    }


    @Test
    @Transactional
    public void getAllInvoicesByReferenceNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where referenceNumber equals to DEFAULT_REFERENCE_NUMBER
        defaultInvoiceShouldBeFound("referenceNumber.equals=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the invoiceList where referenceNumber equals to UPDATED_REFERENCE_NUMBER
        defaultInvoiceShouldNotBeFound("referenceNumber.equals=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByReferenceNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where referenceNumber not equals to DEFAULT_REFERENCE_NUMBER
        defaultInvoiceShouldNotBeFound("referenceNumber.notEquals=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the invoiceList where referenceNumber not equals to UPDATED_REFERENCE_NUMBER
        defaultInvoiceShouldBeFound("referenceNumber.notEquals=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByReferenceNumberIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where referenceNumber in DEFAULT_REFERENCE_NUMBER or UPDATED_REFERENCE_NUMBER
        defaultInvoiceShouldBeFound("referenceNumber.in=" + DEFAULT_REFERENCE_NUMBER + "," + UPDATED_REFERENCE_NUMBER);

        // Get all the invoiceList where referenceNumber equals to UPDATED_REFERENCE_NUMBER
        defaultInvoiceShouldNotBeFound("referenceNumber.in=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByReferenceNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where referenceNumber is not null
        defaultInvoiceShouldBeFound("referenceNumber.specified=true");

        // Get all the invoiceList where referenceNumber is null
        defaultInvoiceShouldNotBeFound("referenceNumber.specified=false");
    }
                @Test
    @Transactional
    public void getAllInvoicesByReferenceNumberContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where referenceNumber contains DEFAULT_REFERENCE_NUMBER
        defaultInvoiceShouldBeFound("referenceNumber.contains=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the invoiceList where referenceNumber contains UPDATED_REFERENCE_NUMBER
        defaultInvoiceShouldNotBeFound("referenceNumber.contains=" + UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllInvoicesByReferenceNumberNotContainsSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);

        // Get all the invoiceList where referenceNumber does not contain DEFAULT_REFERENCE_NUMBER
        defaultInvoiceShouldNotBeFound("referenceNumber.doesNotContain=" + DEFAULT_REFERENCE_NUMBER);

        // Get all the invoiceList where referenceNumber does not contain UPDATED_REFERENCE_NUMBER
        defaultInvoiceShouldBeFound("referenceNumber.doesNotContain=" + UPDATED_REFERENCE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllInvoicesByInvoiceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);
        InvoiceType invoiceType = InvoiceTypeResourceIT.createEntity(em);
        em.persist(invoiceType);
        em.flush();
        invoice.setInvoiceType(invoiceType);
        invoiceRepository.saveAndFlush(invoice);
        Long invoiceTypeId = invoiceType.getId();

        // Get all the invoiceList where invoiceType equals to invoiceTypeId
        defaultInvoiceShouldBeFound("invoiceTypeId.equals=" + invoiceTypeId);

        // Get all the invoiceList where invoiceType equals to invoiceTypeId + 1
        defaultInvoiceShouldNotBeFound("invoiceTypeId.equals=" + (invoiceTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByPartyIdFromIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);
        Party partyIdFrom = PartyResourceIT.createEntity(em);
        em.persist(partyIdFrom);
        em.flush();
        invoice.setPartyIdFrom(partyIdFrom);
        invoiceRepository.saveAndFlush(invoice);
        Long partyIdFromId = partyIdFrom.getId();

        // Get all the invoiceList where partyIdFrom equals to partyIdFromId
        defaultInvoiceShouldBeFound("partyIdFromId.equals=" + partyIdFromId);

        // Get all the invoiceList where partyIdFrom equals to partyIdFromId + 1
        defaultInvoiceShouldNotBeFound("partyIdFromId.equals=" + (partyIdFromId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByPartyIdToIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);
        Party partyIdTo = PartyResourceIT.createEntity(em);
        em.persist(partyIdTo);
        em.flush();
        invoice.setPartyIdTo(partyIdTo);
        invoiceRepository.saveAndFlush(invoice);
        Long partyIdToId = partyIdTo.getId();

        // Get all the invoiceList where partyIdTo equals to partyIdToId
        defaultInvoiceShouldBeFound("partyIdToId.equals=" + partyIdToId);

        // Get all the invoiceList where partyIdTo equals to partyIdToId + 1
        defaultInvoiceShouldNotBeFound("partyIdToId.equals=" + (partyIdToId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByRoleTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);
        RoleType roleType = RoleTypeResourceIT.createEntity(em);
        em.persist(roleType);
        em.flush();
        invoice.setRoleType(roleType);
        invoiceRepository.saveAndFlush(invoice);
        Long roleTypeId = roleType.getId();

        // Get all the invoiceList where roleType equals to roleTypeId
        defaultInvoiceShouldBeFound("roleTypeId.equals=" + roleTypeId);

        // Get all the invoiceList where roleType equals to roleTypeId + 1
        defaultInvoiceShouldNotBeFound("roleTypeId.equals=" + (roleTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        invoice.setStatus(status);
        invoiceRepository.saveAndFlush(invoice);
        Long statusId = status.getId();

        // Get all the invoiceList where status equals to statusId
        defaultInvoiceShouldBeFound("statusId.equals=" + statusId);

        // Get all the invoiceList where status equals to statusId + 1
        defaultInvoiceShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoicesByContactMechIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceRepository.saveAndFlush(invoice);
        ContactMech contactMech = ContactMechResourceIT.createEntity(em);
        em.persist(contactMech);
        em.flush();
        invoice.setContactMech(contactMech);
        invoiceRepository.saveAndFlush(invoice);
        Long contactMechId = contactMech.getId();

        // Get all the invoiceList where contactMech equals to contactMechId
        defaultInvoiceShouldBeFound("contactMechId.equals=" + contactMechId);

        // Get all the invoiceList where contactMech equals to contactMechId + 1
        defaultInvoiceShouldNotBeFound("contactMechId.equals=" + (contactMechId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInvoiceShouldBeFound(String filter) throws Exception {
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(sameInstant(DEFAULT_INVOICE_DATE))))
            .andExpect(jsonPath("$.[*].dueDate").value(hasItem(sameInstant(DEFAULT_DUE_DATE))))
            .andExpect(jsonPath("$.[*].paidDate").value(hasItem(sameInstant(DEFAULT_PAID_DATE))))
            .andExpect(jsonPath("$.[*].invoiceMessage").value(hasItem(DEFAULT_INVOICE_MESSAGE)))
            .andExpect(jsonPath("$.[*].referenceNumber").value(hasItem(DEFAULT_REFERENCE_NUMBER)));

        // Check, that the count call also returns 1
        restInvoiceMockMvc.perform(get("/api/invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInvoiceShouldNotBeFound(String filter) throws Exception {
        restInvoiceMockMvc.perform(get("/api/invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvoiceMockMvc.perform(get("/api/invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInvoice() throws Exception {
        // Get the invoice
        restInvoiceMockMvc.perform(get("/api/invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoice() throws Exception {
        // Initialize the database
        invoiceService.save(invoice);

        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // Update the invoice
        Invoice updatedInvoice = invoiceRepository.findById(invoice.getId()).get();
        // Disconnect from session so that the updates on updatedInvoice are not directly saved in db
        em.detach(updatedInvoice);
        updatedInvoice
            .invoiceDate(UPDATED_INVOICE_DATE)
            .dueDate(UPDATED_DUE_DATE)
            .paidDate(UPDATED_PAID_DATE)
            .invoiceMessage(UPDATED_INVOICE_MESSAGE)
            .referenceNumber(UPDATED_REFERENCE_NUMBER);

        restInvoiceMockMvc.perform(put("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoice)))
            .andExpect(status().isOk());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
        Invoice testInvoice = invoiceList.get(invoiceList.size() - 1);
        assertThat(testInvoice.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testInvoice.getDueDate()).isEqualTo(UPDATED_DUE_DATE);
        assertThat(testInvoice.getPaidDate()).isEqualTo(UPDATED_PAID_DATE);
        assertThat(testInvoice.getInvoiceMessage()).isEqualTo(UPDATED_INVOICE_MESSAGE);
        assertThat(testInvoice.getReferenceNumber()).isEqualTo(UPDATED_REFERENCE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoice() throws Exception {
        int databaseSizeBeforeUpdate = invoiceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceMockMvc.perform(put("/api/invoices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoice)))
            .andExpect(status().isBadRequest());

        // Validate the Invoice in the database
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoice() throws Exception {
        // Initialize the database
        invoiceService.save(invoice);

        int databaseSizeBeforeDelete = invoiceRepository.findAll().size();

        // Delete the invoice
        restInvoiceMockMvc.perform(delete("/api/invoices/{id}", invoice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Invoice> invoiceList = invoiceRepository.findAll();
        assertThat(invoiceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
