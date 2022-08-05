package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.WorkEffortProduct;
import com.hr.domain.WorkEffort;
import com.hr.domain.Product;
import com.hr.repository.WorkEffortProductRepository;
import com.hr.service.WorkEffortProductService;
import com.hr.service.dto.WorkEffortProductCriteria;
import com.hr.service.WorkEffortProductQueryService;

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
 * Integration tests for the {@link WorkEffortProductResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkEffortProductResourceIT {

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;
    private static final Integer SMALLER_SEQUENCE_NO = 1 - 1;

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;
    private static final Double SMALLER_QUANTITY = 1D - 1D;

    @Autowired
    private WorkEffortProductRepository workEffortProductRepository;

    @Autowired
    private WorkEffortProductService workEffortProductService;

    @Autowired
    private WorkEffortProductQueryService workEffortProductQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkEffortProductMockMvc;

    private WorkEffortProduct workEffortProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffortProduct createEntity(EntityManager em) {
        WorkEffortProduct workEffortProduct = new WorkEffortProduct()
            .sequenceNo(DEFAULT_SEQUENCE_NO)
            .quantity(DEFAULT_QUANTITY);
        return workEffortProduct;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffortProduct createUpdatedEntity(EntityManager em) {
        WorkEffortProduct workEffortProduct = new WorkEffortProduct()
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .quantity(UPDATED_QUANTITY);
        return workEffortProduct;
    }

    @BeforeEach
    public void initTest() {
        workEffortProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkEffortProduct() throws Exception {
        int databaseSizeBeforeCreate = workEffortProductRepository.findAll().size();
        // Create the WorkEffortProduct
        restWorkEffortProductMockMvc.perform(post("/api/work-effort-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortProduct)))
            .andExpect(status().isCreated());

        // Validate the WorkEffortProduct in the database
        List<WorkEffortProduct> workEffortProductList = workEffortProductRepository.findAll();
        assertThat(workEffortProductList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEffortProduct testWorkEffortProduct = workEffortProductList.get(workEffortProductList.size() - 1);
        assertThat(testWorkEffortProduct.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
        assertThat(testWorkEffortProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createWorkEffortProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workEffortProductRepository.findAll().size();

        // Create the WorkEffortProduct with an existing ID
        workEffortProduct.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkEffortProductMockMvc.perform(post("/api/work-effort-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortProduct)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffortProduct in the database
        List<WorkEffortProduct> workEffortProductList = workEffortProductRepository.findAll();
        assertThat(workEffortProductList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkEffortProducts() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList
        restWorkEffortProductMockMvc.perform(get("/api/work-effort-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEffortProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getWorkEffortProduct() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get the workEffortProduct
        restWorkEffortProductMockMvc.perform(get("/api/work-effort-products/{id}", workEffortProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workEffortProduct.getId().intValue()))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()));
    }


    @Test
    @Transactional
    public void getWorkEffortProductsByIdFiltering() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        Long id = workEffortProduct.getId();

        defaultWorkEffortProductShouldBeFound("id.equals=" + id);
        defaultWorkEffortProductShouldNotBeFound("id.notEquals=" + id);

        defaultWorkEffortProductShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkEffortProductShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkEffortProductShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkEffortProductShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWorkEffortProductsBySequenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList where sequenceNo equals to DEFAULT_SEQUENCE_NO
        defaultWorkEffortProductShouldBeFound("sequenceNo.equals=" + DEFAULT_SEQUENCE_NO);

        // Get all the workEffortProductList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultWorkEffortProductShouldNotBeFound("sequenceNo.equals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllWorkEffortProductsBySequenceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList where sequenceNo not equals to DEFAULT_SEQUENCE_NO
        defaultWorkEffortProductShouldNotBeFound("sequenceNo.notEquals=" + DEFAULT_SEQUENCE_NO);

        // Get all the workEffortProductList where sequenceNo not equals to UPDATED_SEQUENCE_NO
        defaultWorkEffortProductShouldBeFound("sequenceNo.notEquals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllWorkEffortProductsBySequenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList where sequenceNo in DEFAULT_SEQUENCE_NO or UPDATED_SEQUENCE_NO
        defaultWorkEffortProductShouldBeFound("sequenceNo.in=" + DEFAULT_SEQUENCE_NO + "," + UPDATED_SEQUENCE_NO);

        // Get all the workEffortProductList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultWorkEffortProductShouldNotBeFound("sequenceNo.in=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllWorkEffortProductsBySequenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList where sequenceNo is not null
        defaultWorkEffortProductShouldBeFound("sequenceNo.specified=true");

        // Get all the workEffortProductList where sequenceNo is null
        defaultWorkEffortProductShouldNotBeFound("sequenceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkEffortProductsBySequenceNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList where sequenceNo is greater than or equal to DEFAULT_SEQUENCE_NO
        defaultWorkEffortProductShouldBeFound("sequenceNo.greaterThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the workEffortProductList where sequenceNo is greater than or equal to UPDATED_SEQUENCE_NO
        defaultWorkEffortProductShouldNotBeFound("sequenceNo.greaterThanOrEqual=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllWorkEffortProductsBySequenceNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList where sequenceNo is less than or equal to DEFAULT_SEQUENCE_NO
        defaultWorkEffortProductShouldBeFound("sequenceNo.lessThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the workEffortProductList where sequenceNo is less than or equal to SMALLER_SEQUENCE_NO
        defaultWorkEffortProductShouldNotBeFound("sequenceNo.lessThanOrEqual=" + SMALLER_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllWorkEffortProductsBySequenceNoIsLessThanSomething() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList where sequenceNo is less than DEFAULT_SEQUENCE_NO
        defaultWorkEffortProductShouldNotBeFound("sequenceNo.lessThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the workEffortProductList where sequenceNo is less than UPDATED_SEQUENCE_NO
        defaultWorkEffortProductShouldBeFound("sequenceNo.lessThan=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllWorkEffortProductsBySequenceNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList where sequenceNo is greater than DEFAULT_SEQUENCE_NO
        defaultWorkEffortProductShouldNotBeFound("sequenceNo.greaterThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the workEffortProductList where sequenceNo is greater than SMALLER_SEQUENCE_NO
        defaultWorkEffortProductShouldBeFound("sequenceNo.greaterThan=" + SMALLER_SEQUENCE_NO);
    }


    @Test
    @Transactional
    public void getAllWorkEffortProductsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList where quantity equals to DEFAULT_QUANTITY
        defaultWorkEffortProductShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the workEffortProductList where quantity equals to UPDATED_QUANTITY
        defaultWorkEffortProductShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortProductsByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList where quantity not equals to DEFAULT_QUANTITY
        defaultWorkEffortProductShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the workEffortProductList where quantity not equals to UPDATED_QUANTITY
        defaultWorkEffortProductShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortProductsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultWorkEffortProductShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the workEffortProductList where quantity equals to UPDATED_QUANTITY
        defaultWorkEffortProductShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortProductsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList where quantity is not null
        defaultWorkEffortProductShouldBeFound("quantity.specified=true");

        // Get all the workEffortProductList where quantity is null
        defaultWorkEffortProductShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkEffortProductsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultWorkEffortProductShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the workEffortProductList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultWorkEffortProductShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortProductsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultWorkEffortProductShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the workEffortProductList where quantity is less than or equal to SMALLER_QUANTITY
        defaultWorkEffortProductShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortProductsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList where quantity is less than DEFAULT_QUANTITY
        defaultWorkEffortProductShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the workEffortProductList where quantity is less than UPDATED_QUANTITY
        defaultWorkEffortProductShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortProductsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);

        // Get all the workEffortProductList where quantity is greater than DEFAULT_QUANTITY
        defaultWorkEffortProductShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the workEffortProductList where quantity is greater than SMALLER_QUANTITY
        defaultWorkEffortProductShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllWorkEffortProductsByWorkEffortIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);
        WorkEffort workEffort = WorkEffortResourceIT.createEntity(em);
        em.persist(workEffort);
        em.flush();
        workEffortProduct.setWorkEffort(workEffort);
        workEffortProductRepository.saveAndFlush(workEffortProduct);
        Long workEffortId = workEffort.getId();

        // Get all the workEffortProductList where workEffort equals to workEffortId
        defaultWorkEffortProductShouldBeFound("workEffortId.equals=" + workEffortId);

        // Get all the workEffortProductList where workEffort equals to workEffortId + 1
        defaultWorkEffortProductShouldNotBeFound("workEffortId.equals=" + (workEffortId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkEffortProductsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortProductRepository.saveAndFlush(workEffortProduct);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        workEffortProduct.setProduct(product);
        workEffortProductRepository.saveAndFlush(workEffortProduct);
        Long productId = product.getId();

        // Get all the workEffortProductList where product equals to productId
        defaultWorkEffortProductShouldBeFound("productId.equals=" + productId);

        // Get all the workEffortProductList where product equals to productId + 1
        defaultWorkEffortProductShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkEffortProductShouldBeFound(String filter) throws Exception {
        restWorkEffortProductMockMvc.perform(get("/api/work-effort-products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEffortProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));

        // Check, that the count call also returns 1
        restWorkEffortProductMockMvc.perform(get("/api/work-effort-products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkEffortProductShouldNotBeFound(String filter) throws Exception {
        restWorkEffortProductMockMvc.perform(get("/api/work-effort-products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkEffortProductMockMvc.perform(get("/api/work-effort-products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWorkEffortProduct() throws Exception {
        // Get the workEffortProduct
        restWorkEffortProductMockMvc.perform(get("/api/work-effort-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkEffortProduct() throws Exception {
        // Initialize the database
        workEffortProductService.save(workEffortProduct);

        int databaseSizeBeforeUpdate = workEffortProductRepository.findAll().size();

        // Update the workEffortProduct
        WorkEffortProduct updatedWorkEffortProduct = workEffortProductRepository.findById(workEffortProduct.getId()).get();
        // Disconnect from session so that the updates on updatedWorkEffortProduct are not directly saved in db
        em.detach(updatedWorkEffortProduct);
        updatedWorkEffortProduct
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .quantity(UPDATED_QUANTITY);

        restWorkEffortProductMockMvc.perform(put("/api/work-effort-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkEffortProduct)))
            .andExpect(status().isOk());

        // Validate the WorkEffortProduct in the database
        List<WorkEffortProduct> workEffortProductList = workEffortProductRepository.findAll();
        assertThat(workEffortProductList).hasSize(databaseSizeBeforeUpdate);
        WorkEffortProduct testWorkEffortProduct = workEffortProductList.get(workEffortProductList.size() - 1);
        assertThat(testWorkEffortProduct.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
        assertThat(testWorkEffortProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkEffortProduct() throws Exception {
        int databaseSizeBeforeUpdate = workEffortProductRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkEffortProductMockMvc.perform(put("/api/work-effort-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortProduct)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffortProduct in the database
        List<WorkEffortProduct> workEffortProductList = workEffortProductRepository.findAll();
        assertThat(workEffortProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkEffortProduct() throws Exception {
        // Initialize the database
        workEffortProductService.save(workEffortProduct);

        int databaseSizeBeforeDelete = workEffortProductRepository.findAll().size();

        // Delete the workEffortProduct
        restWorkEffortProductMockMvc.perform(delete("/api/work-effort-products/{id}", workEffortProduct.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkEffortProduct> workEffortProductList = workEffortProductRepository.findAll();
        assertThat(workEffortProductList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
