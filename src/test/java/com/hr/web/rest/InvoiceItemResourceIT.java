package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.InvoiceItem;
import com.hr.domain.Invoice;
import com.hr.domain.InvoiceItemType;
import com.hr.domain.InventoryItem;
import com.hr.domain.Product;
import com.hr.domain.TaxAuthorityRateType;
import com.hr.repository.InvoiceItemRepository;
import com.hr.service.InvoiceItemService;
import com.hr.service.dto.InvoiceItemCriteria;
import com.hr.service.InvoiceItemQueryService;

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
 * Integration tests for the {@link InvoiceItemResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InvoiceItemResourceIT {

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;
    private static final Integer SMALLER_SEQUENCE_NO = 1 - 1;

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_AMOUNT = new BigDecimal(1 - 1);

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Autowired
    private InvoiceItemService invoiceItemService;

    @Autowired
    private InvoiceItemQueryService invoiceItemQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInvoiceItemMockMvc;

    private InvoiceItem invoiceItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceItem createEntity(EntityManager em) {
        InvoiceItem invoiceItem = new InvoiceItem()
            .sequenceNo(DEFAULT_SEQUENCE_NO)
            .quantity(DEFAULT_QUANTITY)
            .amount(DEFAULT_AMOUNT);
        return invoiceItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InvoiceItem createUpdatedEntity(EntityManager em) {
        InvoiceItem invoiceItem = new InvoiceItem()
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .quantity(UPDATED_QUANTITY)
            .amount(UPDATED_AMOUNT);
        return invoiceItem;
    }

    @BeforeEach
    public void initTest() {
        invoiceItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createInvoiceItem() throws Exception {
        int databaseSizeBeforeCreate = invoiceItemRepository.findAll().size();
        // Create the InvoiceItem
        restInvoiceItemMockMvc.perform(post("/api/invoice-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceItem)))
            .andExpect(status().isCreated());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeCreate + 1);
        InvoiceItem testInvoiceItem = invoiceItemList.get(invoiceItemList.size() - 1);
        assertThat(testInvoiceItem.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
        assertThat(testInvoiceItem.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testInvoiceItem.getAmount()).isEqualTo(DEFAULT_AMOUNT);
    }

    @Test
    @Transactional
    public void createInvoiceItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = invoiceItemRepository.findAll().size();

        // Create the InvoiceItem with an existing ID
        invoiceItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInvoiceItemMockMvc.perform(post("/api/invoice-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceItem)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInvoiceItems() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList
        restInvoiceItemMockMvc.perform(get("/api/invoice-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getInvoiceItem() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get the invoiceItem
        restInvoiceItemMockMvc.perform(get("/api/invoice-items/{id}", invoiceItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(invoiceItem.getId().intValue()))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.intValue()));
    }


    @Test
    @Transactional
    public void getInvoiceItemsByIdFiltering() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        Long id = invoiceItem.getId();

        defaultInvoiceItemShouldBeFound("id.equals=" + id);
        defaultInvoiceItemShouldNotBeFound("id.notEquals=" + id);

        defaultInvoiceItemShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInvoiceItemShouldNotBeFound("id.greaterThan=" + id);

        defaultInvoiceItemShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInvoiceItemShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInvoiceItemsBySequenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where sequenceNo equals to DEFAULT_SEQUENCE_NO
        defaultInvoiceItemShouldBeFound("sequenceNo.equals=" + DEFAULT_SEQUENCE_NO);

        // Get all the invoiceItemList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultInvoiceItemShouldNotBeFound("sequenceNo.equals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsBySequenceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where sequenceNo not equals to DEFAULT_SEQUENCE_NO
        defaultInvoiceItemShouldNotBeFound("sequenceNo.notEquals=" + DEFAULT_SEQUENCE_NO);

        // Get all the invoiceItemList where sequenceNo not equals to UPDATED_SEQUENCE_NO
        defaultInvoiceItemShouldBeFound("sequenceNo.notEquals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsBySequenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where sequenceNo in DEFAULT_SEQUENCE_NO or UPDATED_SEQUENCE_NO
        defaultInvoiceItemShouldBeFound("sequenceNo.in=" + DEFAULT_SEQUENCE_NO + "," + UPDATED_SEQUENCE_NO);

        // Get all the invoiceItemList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultInvoiceItemShouldNotBeFound("sequenceNo.in=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsBySequenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where sequenceNo is not null
        defaultInvoiceItemShouldBeFound("sequenceNo.specified=true");

        // Get all the invoiceItemList where sequenceNo is null
        defaultInvoiceItemShouldNotBeFound("sequenceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsBySequenceNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where sequenceNo is greater than or equal to DEFAULT_SEQUENCE_NO
        defaultInvoiceItemShouldBeFound("sequenceNo.greaterThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the invoiceItemList where sequenceNo is greater than or equal to UPDATED_SEQUENCE_NO
        defaultInvoiceItemShouldNotBeFound("sequenceNo.greaterThanOrEqual=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsBySequenceNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where sequenceNo is less than or equal to DEFAULT_SEQUENCE_NO
        defaultInvoiceItemShouldBeFound("sequenceNo.lessThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the invoiceItemList where sequenceNo is less than or equal to SMALLER_SEQUENCE_NO
        defaultInvoiceItemShouldNotBeFound("sequenceNo.lessThanOrEqual=" + SMALLER_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsBySequenceNoIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where sequenceNo is less than DEFAULT_SEQUENCE_NO
        defaultInvoiceItemShouldNotBeFound("sequenceNo.lessThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the invoiceItemList where sequenceNo is less than UPDATED_SEQUENCE_NO
        defaultInvoiceItemShouldBeFound("sequenceNo.lessThan=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsBySequenceNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where sequenceNo is greater than DEFAULT_SEQUENCE_NO
        defaultInvoiceItemShouldNotBeFound("sequenceNo.greaterThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the invoiceItemList where sequenceNo is greater than SMALLER_SEQUENCE_NO
        defaultInvoiceItemShouldBeFound("sequenceNo.greaterThan=" + SMALLER_SEQUENCE_NO);
    }


    @Test
    @Transactional
    public void getAllInvoiceItemsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where quantity equals to DEFAULT_QUANTITY
        defaultInvoiceItemShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the invoiceItemList where quantity equals to UPDATED_QUANTITY
        defaultInvoiceItemShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where quantity not equals to DEFAULT_QUANTITY
        defaultInvoiceItemShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the invoiceItemList where quantity not equals to UPDATED_QUANTITY
        defaultInvoiceItemShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultInvoiceItemShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the invoiceItemList where quantity equals to UPDATED_QUANTITY
        defaultInvoiceItemShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where quantity is not null
        defaultInvoiceItemShouldBeFound("quantity.specified=true");

        // Get all the invoiceItemList where quantity is null
        defaultInvoiceItemShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultInvoiceItemShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the invoiceItemList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultInvoiceItemShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultInvoiceItemShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the invoiceItemList where quantity is less than or equal to SMALLER_QUANTITY
        defaultInvoiceItemShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where quantity is less than DEFAULT_QUANTITY
        defaultInvoiceItemShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the invoiceItemList where quantity is less than UPDATED_QUANTITY
        defaultInvoiceItemShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where quantity is greater than DEFAULT_QUANTITY
        defaultInvoiceItemShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the invoiceItemList where quantity is greater than SMALLER_QUANTITY
        defaultInvoiceItemShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllInvoiceItemsByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where amount equals to DEFAULT_AMOUNT
        defaultInvoiceItemShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the invoiceItemList where amount equals to UPDATED_AMOUNT
        defaultInvoiceItemShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsByAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where amount not equals to DEFAULT_AMOUNT
        defaultInvoiceItemShouldNotBeFound("amount.notEquals=" + DEFAULT_AMOUNT);

        // Get all the invoiceItemList where amount not equals to UPDATED_AMOUNT
        defaultInvoiceItemShouldBeFound("amount.notEquals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultInvoiceItemShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the invoiceItemList where amount equals to UPDATED_AMOUNT
        defaultInvoiceItemShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where amount is not null
        defaultInvoiceItemShouldBeFound("amount.specified=true");

        // Get all the invoiceItemList where amount is null
        defaultInvoiceItemShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsByAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where amount is greater than or equal to DEFAULT_AMOUNT
        defaultInvoiceItemShouldBeFound("amount.greaterThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the invoiceItemList where amount is greater than or equal to UPDATED_AMOUNT
        defaultInvoiceItemShouldNotBeFound("amount.greaterThanOrEqual=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsByAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where amount is less than or equal to DEFAULT_AMOUNT
        defaultInvoiceItemShouldBeFound("amount.lessThanOrEqual=" + DEFAULT_AMOUNT);

        // Get all the invoiceItemList where amount is less than or equal to SMALLER_AMOUNT
        defaultInvoiceItemShouldNotBeFound("amount.lessThanOrEqual=" + SMALLER_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsByAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where amount is less than DEFAULT_AMOUNT
        defaultInvoiceItemShouldNotBeFound("amount.lessThan=" + DEFAULT_AMOUNT);

        // Get all the invoiceItemList where amount is less than UPDATED_AMOUNT
        defaultInvoiceItemShouldBeFound("amount.lessThan=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllInvoiceItemsByAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);

        // Get all the invoiceItemList where amount is greater than DEFAULT_AMOUNT
        defaultInvoiceItemShouldNotBeFound("amount.greaterThan=" + DEFAULT_AMOUNT);

        // Get all the invoiceItemList where amount is greater than SMALLER_AMOUNT
        defaultInvoiceItemShouldBeFound("amount.greaterThan=" + SMALLER_AMOUNT);
    }


    @Test
    @Transactional
    public void getAllInvoiceItemsByInvoiceIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);
        Invoice invoice = InvoiceResourceIT.createEntity(em);
        em.persist(invoice);
        em.flush();
        invoiceItem.setInvoice(invoice);
        invoiceItemRepository.saveAndFlush(invoiceItem);
        Long invoiceId = invoice.getId();

        // Get all the invoiceItemList where invoice equals to invoiceId
        defaultInvoiceItemShouldBeFound("invoiceId.equals=" + invoiceId);

        // Get all the invoiceItemList where invoice equals to invoiceId + 1
        defaultInvoiceItemShouldNotBeFound("invoiceId.equals=" + (invoiceId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoiceItemsByInvoiceItemTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);
        InvoiceItemType invoiceItemType = InvoiceItemTypeResourceIT.createEntity(em);
        em.persist(invoiceItemType);
        em.flush();
        invoiceItem.setInvoiceItemType(invoiceItemType);
        invoiceItemRepository.saveAndFlush(invoiceItem);
        Long invoiceItemTypeId = invoiceItemType.getId();

        // Get all the invoiceItemList where invoiceItemType equals to invoiceItemTypeId
        defaultInvoiceItemShouldBeFound("invoiceItemTypeId.equals=" + invoiceItemTypeId);

        // Get all the invoiceItemList where invoiceItemType equals to invoiceItemTypeId + 1
        defaultInvoiceItemShouldNotBeFound("invoiceItemTypeId.equals=" + (invoiceItemTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoiceItemsByFromInventoryItemIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);
        InventoryItem fromInventoryItem = InventoryItemResourceIT.createEntity(em);
        em.persist(fromInventoryItem);
        em.flush();
        invoiceItem.setFromInventoryItem(fromInventoryItem);
        invoiceItemRepository.saveAndFlush(invoiceItem);
        Long fromInventoryItemId = fromInventoryItem.getId();

        // Get all the invoiceItemList where fromInventoryItem equals to fromInventoryItemId
        defaultInvoiceItemShouldBeFound("fromInventoryItemId.equals=" + fromInventoryItemId);

        // Get all the invoiceItemList where fromInventoryItem equals to fromInventoryItemId + 1
        defaultInvoiceItemShouldNotBeFound("fromInventoryItemId.equals=" + (fromInventoryItemId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoiceItemsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        invoiceItem.setProduct(product);
        invoiceItemRepository.saveAndFlush(invoiceItem);
        Long productId = product.getId();

        // Get all the invoiceItemList where product equals to productId
        defaultInvoiceItemShouldBeFound("productId.equals=" + productId);

        // Get all the invoiceItemList where product equals to productId + 1
        defaultInvoiceItemShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllInvoiceItemsByTaxAuthRateIsEqualToSomething() throws Exception {
        // Initialize the database
        invoiceItemRepository.saveAndFlush(invoiceItem);
        TaxAuthorityRateType taxAuthRate = TaxAuthorityRateTypeResourceIT.createEntity(em);
        em.persist(taxAuthRate);
        em.flush();
        invoiceItem.setTaxAuthRate(taxAuthRate);
        invoiceItemRepository.saveAndFlush(invoiceItem);
        Long taxAuthRateId = taxAuthRate.getId();

        // Get all the invoiceItemList where taxAuthRate equals to taxAuthRateId
        defaultInvoiceItemShouldBeFound("taxAuthRateId.equals=" + taxAuthRateId);

        // Get all the invoiceItemList where taxAuthRate equals to taxAuthRateId + 1
        defaultInvoiceItemShouldNotBeFound("taxAuthRateId.equals=" + (taxAuthRateId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInvoiceItemShouldBeFound(String filter) throws Exception {
        restInvoiceItemMockMvc.perform(get("/api/invoice-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(invoiceItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.intValue())));

        // Check, that the count call also returns 1
        restInvoiceItemMockMvc.perform(get("/api/invoice-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInvoiceItemShouldNotBeFound(String filter) throws Exception {
        restInvoiceItemMockMvc.perform(get("/api/invoice-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInvoiceItemMockMvc.perform(get("/api/invoice-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInvoiceItem() throws Exception {
        // Get the invoiceItem
        restInvoiceItemMockMvc.perform(get("/api/invoice-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInvoiceItem() throws Exception {
        // Initialize the database
        invoiceItemService.save(invoiceItem);

        int databaseSizeBeforeUpdate = invoiceItemRepository.findAll().size();

        // Update the invoiceItem
        InvoiceItem updatedInvoiceItem = invoiceItemRepository.findById(invoiceItem.getId()).get();
        // Disconnect from session so that the updates on updatedInvoiceItem are not directly saved in db
        em.detach(updatedInvoiceItem);
        updatedInvoiceItem
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .quantity(UPDATED_QUANTITY)
            .amount(UPDATED_AMOUNT);

        restInvoiceItemMockMvc.perform(put("/api/invoice-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInvoiceItem)))
            .andExpect(status().isOk());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeUpdate);
        InvoiceItem testInvoiceItem = invoiceItemList.get(invoiceItemList.size() - 1);
        assertThat(testInvoiceItem.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
        assertThat(testInvoiceItem.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testInvoiceItem.getAmount()).isEqualTo(UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingInvoiceItem() throws Exception {
        int databaseSizeBeforeUpdate = invoiceItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInvoiceItemMockMvc.perform(put("/api/invoice-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(invoiceItem)))
            .andExpect(status().isBadRequest());

        // Validate the InvoiceItem in the database
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInvoiceItem() throws Exception {
        // Initialize the database
        invoiceItemService.save(invoiceItem);

        int databaseSizeBeforeDelete = invoiceItemRepository.findAll().size();

        // Delete the invoiceItem
        restInvoiceItemMockMvc.perform(delete("/api/invoice-items/{id}", invoiceItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InvoiceItem> invoiceItemList = invoiceItemRepository.findAll();
        assertThat(invoiceItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
