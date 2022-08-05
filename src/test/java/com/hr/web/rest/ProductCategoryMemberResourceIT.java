package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ProductCategoryMember;
import com.hr.domain.Product;
import com.hr.domain.ProductCategory;
import com.hr.repository.ProductCategoryMemberRepository;
import com.hr.service.ProductCategoryMemberService;
import com.hr.service.dto.ProductCategoryMemberCriteria;
import com.hr.service.ProductCategoryMemberQueryService;

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
 * Integration tests for the {@link ProductCategoryMemberResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductCategoryMemberResourceIT {

    private static final ZonedDateTime DEFAULT_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FROM_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_THRU_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;
    private static final Integer SMALLER_SEQUENCE_NO = 1 - 1;

    @Autowired
    private ProductCategoryMemberRepository productCategoryMemberRepository;

    @Autowired
    private ProductCategoryMemberService productCategoryMemberService;

    @Autowired
    private ProductCategoryMemberQueryService productCategoryMemberQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductCategoryMemberMockMvc;

    private ProductCategoryMember productCategoryMember;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategoryMember createEntity(EntityManager em) {
        ProductCategoryMember productCategoryMember = new ProductCategoryMember()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .sequenceNo(DEFAULT_SEQUENCE_NO);
        return productCategoryMember;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategoryMember createUpdatedEntity(EntityManager em) {
        ProductCategoryMember productCategoryMember = new ProductCategoryMember()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .sequenceNo(UPDATED_SEQUENCE_NO);
        return productCategoryMember;
    }

    @BeforeEach
    public void initTest() {
        productCategoryMember = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductCategoryMember() throws Exception {
        int databaseSizeBeforeCreate = productCategoryMemberRepository.findAll().size();
        // Create the ProductCategoryMember
        restProductCategoryMemberMockMvc.perform(post("/api/product-category-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryMember)))
            .andExpect(status().isCreated());

        // Validate the ProductCategoryMember in the database
        List<ProductCategoryMember> productCategoryMemberList = productCategoryMemberRepository.findAll();
        assertThat(productCategoryMemberList).hasSize(databaseSizeBeforeCreate + 1);
        ProductCategoryMember testProductCategoryMember = productCategoryMemberList.get(productCategoryMemberList.size() - 1);
        assertThat(testProductCategoryMember.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testProductCategoryMember.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testProductCategoryMember.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void createProductCategoryMemberWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productCategoryMemberRepository.findAll().size();

        // Create the ProductCategoryMember with an existing ID
        productCategoryMember.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductCategoryMemberMockMvc.perform(post("/api/product-category-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryMember)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryMember in the database
        List<ProductCategoryMember> productCategoryMemberList = productCategoryMemberRepository.findAll();
        assertThat(productCategoryMemberList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductCategoryMembers() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList
        restProductCategoryMemberMockMvc.perform(get("/api/product-category-members?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategoryMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)));
    }
    
    @Test
    @Transactional
    public void getProductCategoryMember() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get the productCategoryMember
        restProductCategoryMemberMockMvc.perform(get("/api/product-category-members/{id}", productCategoryMember.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productCategoryMember.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(sameInstant(DEFAULT_FROM_DATE)))
            .andExpect(jsonPath("$.thruDate").value(sameInstant(DEFAULT_THRU_DATE)))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO));
    }


    @Test
    @Transactional
    public void getProductCategoryMembersByIdFiltering() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        Long id = productCategoryMember.getId();

        defaultProductCategoryMemberShouldBeFound("id.equals=" + id);
        defaultProductCategoryMemberShouldNotBeFound("id.notEquals=" + id);

        defaultProductCategoryMemberShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductCategoryMemberShouldNotBeFound("id.greaterThan=" + id);

        defaultProductCategoryMemberShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductCategoryMemberShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductCategoryMembersByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where fromDate equals to DEFAULT_FROM_DATE
        defaultProductCategoryMemberShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the productCategoryMemberList where fromDate equals to UPDATED_FROM_DATE
        defaultProductCategoryMemberShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where fromDate not equals to DEFAULT_FROM_DATE
        defaultProductCategoryMemberShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the productCategoryMemberList where fromDate not equals to UPDATED_FROM_DATE
        defaultProductCategoryMemberShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultProductCategoryMemberShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the productCategoryMemberList where fromDate equals to UPDATED_FROM_DATE
        defaultProductCategoryMemberShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where fromDate is not null
        defaultProductCategoryMemberShouldBeFound("fromDate.specified=true");

        // Get all the productCategoryMemberList where fromDate is null
        defaultProductCategoryMemberShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersByFromDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where fromDate is greater than or equal to DEFAULT_FROM_DATE
        defaultProductCategoryMemberShouldBeFound("fromDate.greaterThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the productCategoryMemberList where fromDate is greater than or equal to UPDATED_FROM_DATE
        defaultProductCategoryMemberShouldNotBeFound("fromDate.greaterThanOrEqual=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersByFromDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where fromDate is less than or equal to DEFAULT_FROM_DATE
        defaultProductCategoryMemberShouldBeFound("fromDate.lessThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the productCategoryMemberList where fromDate is less than or equal to SMALLER_FROM_DATE
        defaultProductCategoryMemberShouldNotBeFound("fromDate.lessThanOrEqual=" + SMALLER_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersByFromDateIsLessThanSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where fromDate is less than DEFAULT_FROM_DATE
        defaultProductCategoryMemberShouldNotBeFound("fromDate.lessThan=" + DEFAULT_FROM_DATE);

        // Get all the productCategoryMemberList where fromDate is less than UPDATED_FROM_DATE
        defaultProductCategoryMemberShouldBeFound("fromDate.lessThan=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersByFromDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where fromDate is greater than DEFAULT_FROM_DATE
        defaultProductCategoryMemberShouldNotBeFound("fromDate.greaterThan=" + DEFAULT_FROM_DATE);

        // Get all the productCategoryMemberList where fromDate is greater than SMALLER_FROM_DATE
        defaultProductCategoryMemberShouldBeFound("fromDate.greaterThan=" + SMALLER_FROM_DATE);
    }


    @Test
    @Transactional
    public void getAllProductCategoryMembersByThruDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where thruDate equals to DEFAULT_THRU_DATE
        defaultProductCategoryMemberShouldBeFound("thruDate.equals=" + DEFAULT_THRU_DATE);

        // Get all the productCategoryMemberList where thruDate equals to UPDATED_THRU_DATE
        defaultProductCategoryMemberShouldNotBeFound("thruDate.equals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersByThruDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where thruDate not equals to DEFAULT_THRU_DATE
        defaultProductCategoryMemberShouldNotBeFound("thruDate.notEquals=" + DEFAULT_THRU_DATE);

        // Get all the productCategoryMemberList where thruDate not equals to UPDATED_THRU_DATE
        defaultProductCategoryMemberShouldBeFound("thruDate.notEquals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersByThruDateIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where thruDate in DEFAULT_THRU_DATE or UPDATED_THRU_DATE
        defaultProductCategoryMemberShouldBeFound("thruDate.in=" + DEFAULT_THRU_DATE + "," + UPDATED_THRU_DATE);

        // Get all the productCategoryMemberList where thruDate equals to UPDATED_THRU_DATE
        defaultProductCategoryMemberShouldNotBeFound("thruDate.in=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersByThruDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where thruDate is not null
        defaultProductCategoryMemberShouldBeFound("thruDate.specified=true");

        // Get all the productCategoryMemberList where thruDate is null
        defaultProductCategoryMemberShouldNotBeFound("thruDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersByThruDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where thruDate is greater than or equal to DEFAULT_THRU_DATE
        defaultProductCategoryMemberShouldBeFound("thruDate.greaterThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the productCategoryMemberList where thruDate is greater than or equal to UPDATED_THRU_DATE
        defaultProductCategoryMemberShouldNotBeFound("thruDate.greaterThanOrEqual=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersByThruDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where thruDate is less than or equal to DEFAULT_THRU_DATE
        defaultProductCategoryMemberShouldBeFound("thruDate.lessThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the productCategoryMemberList where thruDate is less than or equal to SMALLER_THRU_DATE
        defaultProductCategoryMemberShouldNotBeFound("thruDate.lessThanOrEqual=" + SMALLER_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersByThruDateIsLessThanSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where thruDate is less than DEFAULT_THRU_DATE
        defaultProductCategoryMemberShouldNotBeFound("thruDate.lessThan=" + DEFAULT_THRU_DATE);

        // Get all the productCategoryMemberList where thruDate is less than UPDATED_THRU_DATE
        defaultProductCategoryMemberShouldBeFound("thruDate.lessThan=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersByThruDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where thruDate is greater than DEFAULT_THRU_DATE
        defaultProductCategoryMemberShouldNotBeFound("thruDate.greaterThan=" + DEFAULT_THRU_DATE);

        // Get all the productCategoryMemberList where thruDate is greater than SMALLER_THRU_DATE
        defaultProductCategoryMemberShouldBeFound("thruDate.greaterThan=" + SMALLER_THRU_DATE);
    }


    @Test
    @Transactional
    public void getAllProductCategoryMembersBySequenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where sequenceNo equals to DEFAULT_SEQUENCE_NO
        defaultProductCategoryMemberShouldBeFound("sequenceNo.equals=" + DEFAULT_SEQUENCE_NO);

        // Get all the productCategoryMemberList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultProductCategoryMemberShouldNotBeFound("sequenceNo.equals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersBySequenceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where sequenceNo not equals to DEFAULT_SEQUENCE_NO
        defaultProductCategoryMemberShouldNotBeFound("sequenceNo.notEquals=" + DEFAULT_SEQUENCE_NO);

        // Get all the productCategoryMemberList where sequenceNo not equals to UPDATED_SEQUENCE_NO
        defaultProductCategoryMemberShouldBeFound("sequenceNo.notEquals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersBySequenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where sequenceNo in DEFAULT_SEQUENCE_NO or UPDATED_SEQUENCE_NO
        defaultProductCategoryMemberShouldBeFound("sequenceNo.in=" + DEFAULT_SEQUENCE_NO + "," + UPDATED_SEQUENCE_NO);

        // Get all the productCategoryMemberList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultProductCategoryMemberShouldNotBeFound("sequenceNo.in=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersBySequenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where sequenceNo is not null
        defaultProductCategoryMemberShouldBeFound("sequenceNo.specified=true");

        // Get all the productCategoryMemberList where sequenceNo is null
        defaultProductCategoryMemberShouldNotBeFound("sequenceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersBySequenceNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where sequenceNo is greater than or equal to DEFAULT_SEQUENCE_NO
        defaultProductCategoryMemberShouldBeFound("sequenceNo.greaterThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the productCategoryMemberList where sequenceNo is greater than or equal to UPDATED_SEQUENCE_NO
        defaultProductCategoryMemberShouldNotBeFound("sequenceNo.greaterThanOrEqual=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersBySequenceNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where sequenceNo is less than or equal to DEFAULT_SEQUENCE_NO
        defaultProductCategoryMemberShouldBeFound("sequenceNo.lessThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the productCategoryMemberList where sequenceNo is less than or equal to SMALLER_SEQUENCE_NO
        defaultProductCategoryMemberShouldNotBeFound("sequenceNo.lessThanOrEqual=" + SMALLER_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersBySequenceNoIsLessThanSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where sequenceNo is less than DEFAULT_SEQUENCE_NO
        defaultProductCategoryMemberShouldNotBeFound("sequenceNo.lessThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the productCategoryMemberList where sequenceNo is less than UPDATED_SEQUENCE_NO
        defaultProductCategoryMemberShouldBeFound("sequenceNo.lessThan=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductCategoryMembersBySequenceNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);

        // Get all the productCategoryMemberList where sequenceNo is greater than DEFAULT_SEQUENCE_NO
        defaultProductCategoryMemberShouldNotBeFound("sequenceNo.greaterThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the productCategoryMemberList where sequenceNo is greater than SMALLER_SEQUENCE_NO
        defaultProductCategoryMemberShouldBeFound("sequenceNo.greaterThan=" + SMALLER_SEQUENCE_NO);
    }


    @Test
    @Transactional
    public void getAllProductCategoryMembersByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productCategoryMember.setProduct(product);
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);
        Long productId = product.getId();

        // Get all the productCategoryMemberList where product equals to productId
        defaultProductCategoryMemberShouldBeFound("productId.equals=" + productId);

        // Get all the productCategoryMemberList where product equals to productId + 1
        defaultProductCategoryMemberShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllProductCategoryMembersByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);
        ProductCategory productCategory = ProductCategoryResourceIT.createEntity(em);
        em.persist(productCategory);
        em.flush();
        productCategoryMember.setProductCategory(productCategory);
        productCategoryMemberRepository.saveAndFlush(productCategoryMember);
        Long productCategoryId = productCategory.getId();

        // Get all the productCategoryMemberList where productCategory equals to productCategoryId
        defaultProductCategoryMemberShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the productCategoryMemberList where productCategory equals to productCategoryId + 1
        defaultProductCategoryMemberShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductCategoryMemberShouldBeFound(String filter) throws Exception {
        restProductCategoryMemberMockMvc.perform(get("/api/product-category-members?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategoryMember.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)));

        // Check, that the count call also returns 1
        restProductCategoryMemberMockMvc.perform(get("/api/product-category-members/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductCategoryMemberShouldNotBeFound(String filter) throws Exception {
        restProductCategoryMemberMockMvc.perform(get("/api/product-category-members?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductCategoryMemberMockMvc.perform(get("/api/product-category-members/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductCategoryMember() throws Exception {
        // Get the productCategoryMember
        restProductCategoryMemberMockMvc.perform(get("/api/product-category-members/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductCategoryMember() throws Exception {
        // Initialize the database
        productCategoryMemberService.save(productCategoryMember);

        int databaseSizeBeforeUpdate = productCategoryMemberRepository.findAll().size();

        // Update the productCategoryMember
        ProductCategoryMember updatedProductCategoryMember = productCategoryMemberRepository.findById(productCategoryMember.getId()).get();
        // Disconnect from session so that the updates on updatedProductCategoryMember are not directly saved in db
        em.detach(updatedProductCategoryMember);
        updatedProductCategoryMember
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .sequenceNo(UPDATED_SEQUENCE_NO);

        restProductCategoryMemberMockMvc.perform(put("/api/product-category-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductCategoryMember)))
            .andExpect(status().isOk());

        // Validate the ProductCategoryMember in the database
        List<ProductCategoryMember> productCategoryMemberList = productCategoryMemberRepository.findAll();
        assertThat(productCategoryMemberList).hasSize(databaseSizeBeforeUpdate);
        ProductCategoryMember testProductCategoryMember = productCategoryMemberList.get(productCategoryMemberList.size() - 1);
        assertThat(testProductCategoryMember.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testProductCategoryMember.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testProductCategoryMember.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void updateNonExistingProductCategoryMember() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryMemberRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCategoryMemberMockMvc.perform(put("/api/product-category-members")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryMember)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryMember in the database
        List<ProductCategoryMember> productCategoryMemberList = productCategoryMemberRepository.findAll();
        assertThat(productCategoryMemberList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductCategoryMember() throws Exception {
        // Initialize the database
        productCategoryMemberService.save(productCategoryMember);

        int databaseSizeBeforeDelete = productCategoryMemberRepository.findAll().size();

        // Delete the productCategoryMember
        restProductCategoryMemberMockMvc.perform(delete("/api/product-category-members/{id}", productCategoryMember.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductCategoryMember> productCategoryMemberList = productCategoryMemberRepository.findAll();
        assertThat(productCategoryMemberList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
