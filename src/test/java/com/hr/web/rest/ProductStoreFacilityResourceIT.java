package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ProductStoreFacility;
import com.hr.domain.ProductStore;
import com.hr.domain.Facility;
import com.hr.repository.ProductStoreFacilityRepository;
import com.hr.service.ProductStoreFacilityService;
import com.hr.service.dto.ProductStoreFacilityCriteria;
import com.hr.service.ProductStoreFacilityQueryService;

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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductStoreFacilityResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductStoreFacilityResourceIT {

    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_THRU_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_THRU_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;
    private static final Integer SMALLER_SEQUENCE_NO = 1 - 1;

    @Autowired
    private ProductStoreFacilityRepository productStoreFacilityRepository;

    @Autowired
    private ProductStoreFacilityService productStoreFacilityService;

    @Autowired
    private ProductStoreFacilityQueryService productStoreFacilityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductStoreFacilityMockMvc;

    private ProductStoreFacility productStoreFacility;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductStoreFacility createEntity(EntityManager em) {
        ProductStoreFacility productStoreFacility = new ProductStoreFacility()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .sequenceNo(DEFAULT_SEQUENCE_NO);
        // Add required entity
        ProductStore productStore;
        if (TestUtil.findAll(em, ProductStore.class).isEmpty()) {
            productStore = ProductStoreResourceIT.createEntity(em);
            em.persist(productStore);
            em.flush();
        } else {
            productStore = TestUtil.findAll(em, ProductStore.class).get(0);
        }
        productStoreFacility.setProductStore(productStore);
        // Add required entity
        Facility facility;
        if (TestUtil.findAll(em, Facility.class).isEmpty()) {
            facility = FacilityResourceIT.createEntity(em);
            em.persist(facility);
            em.flush();
        } else {
            facility = TestUtil.findAll(em, Facility.class).get(0);
        }
        productStoreFacility.setFacility(facility);
        return productStoreFacility;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductStoreFacility createUpdatedEntity(EntityManager em) {
        ProductStoreFacility productStoreFacility = new ProductStoreFacility()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .sequenceNo(UPDATED_SEQUENCE_NO);
        // Add required entity
        ProductStore productStore;
        if (TestUtil.findAll(em, ProductStore.class).isEmpty()) {
            productStore = ProductStoreResourceIT.createUpdatedEntity(em);
            em.persist(productStore);
            em.flush();
        } else {
            productStore = TestUtil.findAll(em, ProductStore.class).get(0);
        }
        productStoreFacility.setProductStore(productStore);
        // Add required entity
        Facility facility;
        if (TestUtil.findAll(em, Facility.class).isEmpty()) {
            facility = FacilityResourceIT.createUpdatedEntity(em);
            em.persist(facility);
            em.flush();
        } else {
            facility = TestUtil.findAll(em, Facility.class).get(0);
        }
        productStoreFacility.setFacility(facility);
        return productStoreFacility;
    }

    @BeforeEach
    public void initTest() {
        productStoreFacility = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductStoreFacility() throws Exception {
        int databaseSizeBeforeCreate = productStoreFacilityRepository.findAll().size();
        // Create the ProductStoreFacility
        restProductStoreFacilityMockMvc.perform(post("/api/product-store-facilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStoreFacility)))
            .andExpect(status().isCreated());

        // Validate the ProductStoreFacility in the database
        List<ProductStoreFacility> productStoreFacilityList = productStoreFacilityRepository.findAll();
        assertThat(productStoreFacilityList).hasSize(databaseSizeBeforeCreate + 1);
        ProductStoreFacility testProductStoreFacility = productStoreFacilityList.get(productStoreFacilityList.size() - 1);
        assertThat(testProductStoreFacility.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testProductStoreFacility.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testProductStoreFacility.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void createProductStoreFacilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productStoreFacilityRepository.findAll().size();

        // Create the ProductStoreFacility with an existing ID
        productStoreFacility.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductStoreFacilityMockMvc.perform(post("/api/product-store-facilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStoreFacility)))
            .andExpect(status().isBadRequest());

        // Validate the ProductStoreFacility in the database
        List<ProductStoreFacility> productStoreFacilityList = productStoreFacilityRepository.findAll();
        assertThat(productStoreFacilityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductStoreFacilities() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList
        restProductStoreFacilityMockMvc.perform(get("/api/product-store-facilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productStoreFacility.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)));
    }
    
    @Test
    @Transactional
    public void getProductStoreFacility() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get the productStoreFacility
        restProductStoreFacilityMockMvc.perform(get("/api/product-store-facilities/{id}", productStoreFacility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productStoreFacility.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO));
    }


    @Test
    @Transactional
    public void getProductStoreFacilitiesByIdFiltering() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        Long id = productStoreFacility.getId();

        defaultProductStoreFacilityShouldBeFound("id.equals=" + id);
        defaultProductStoreFacilityShouldNotBeFound("id.notEquals=" + id);

        defaultProductStoreFacilityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductStoreFacilityShouldNotBeFound("id.greaterThan=" + id);

        defaultProductStoreFacilityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductStoreFacilityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductStoreFacilitiesByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList where fromDate equals to DEFAULT_FROM_DATE
        defaultProductStoreFacilityShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the productStoreFacilityList where fromDate equals to UPDATED_FROM_DATE
        defaultProductStoreFacilityShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllProductStoreFacilitiesByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList where fromDate not equals to DEFAULT_FROM_DATE
        defaultProductStoreFacilityShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the productStoreFacilityList where fromDate not equals to UPDATED_FROM_DATE
        defaultProductStoreFacilityShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllProductStoreFacilitiesByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultProductStoreFacilityShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the productStoreFacilityList where fromDate equals to UPDATED_FROM_DATE
        defaultProductStoreFacilityShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllProductStoreFacilitiesByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList where fromDate is not null
        defaultProductStoreFacilityShouldBeFound("fromDate.specified=true");

        // Get all the productStoreFacilityList where fromDate is null
        defaultProductStoreFacilityShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductStoreFacilitiesByThruDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList where thruDate equals to DEFAULT_THRU_DATE
        defaultProductStoreFacilityShouldBeFound("thruDate.equals=" + DEFAULT_THRU_DATE);

        // Get all the productStoreFacilityList where thruDate equals to UPDATED_THRU_DATE
        defaultProductStoreFacilityShouldNotBeFound("thruDate.equals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllProductStoreFacilitiesByThruDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList where thruDate not equals to DEFAULT_THRU_DATE
        defaultProductStoreFacilityShouldNotBeFound("thruDate.notEquals=" + DEFAULT_THRU_DATE);

        // Get all the productStoreFacilityList where thruDate not equals to UPDATED_THRU_DATE
        defaultProductStoreFacilityShouldBeFound("thruDate.notEquals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllProductStoreFacilitiesByThruDateIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList where thruDate in DEFAULT_THRU_DATE or UPDATED_THRU_DATE
        defaultProductStoreFacilityShouldBeFound("thruDate.in=" + DEFAULT_THRU_DATE + "," + UPDATED_THRU_DATE);

        // Get all the productStoreFacilityList where thruDate equals to UPDATED_THRU_DATE
        defaultProductStoreFacilityShouldNotBeFound("thruDate.in=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllProductStoreFacilitiesByThruDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList where thruDate is not null
        defaultProductStoreFacilityShouldBeFound("thruDate.specified=true");

        // Get all the productStoreFacilityList where thruDate is null
        defaultProductStoreFacilityShouldNotBeFound("thruDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductStoreFacilitiesBySequenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList where sequenceNo equals to DEFAULT_SEQUENCE_NO
        defaultProductStoreFacilityShouldBeFound("sequenceNo.equals=" + DEFAULT_SEQUENCE_NO);

        // Get all the productStoreFacilityList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultProductStoreFacilityShouldNotBeFound("sequenceNo.equals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductStoreFacilitiesBySequenceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList where sequenceNo not equals to DEFAULT_SEQUENCE_NO
        defaultProductStoreFacilityShouldNotBeFound("sequenceNo.notEquals=" + DEFAULT_SEQUENCE_NO);

        // Get all the productStoreFacilityList where sequenceNo not equals to UPDATED_SEQUENCE_NO
        defaultProductStoreFacilityShouldBeFound("sequenceNo.notEquals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductStoreFacilitiesBySequenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList where sequenceNo in DEFAULT_SEQUENCE_NO or UPDATED_SEQUENCE_NO
        defaultProductStoreFacilityShouldBeFound("sequenceNo.in=" + DEFAULT_SEQUENCE_NO + "," + UPDATED_SEQUENCE_NO);

        // Get all the productStoreFacilityList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultProductStoreFacilityShouldNotBeFound("sequenceNo.in=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductStoreFacilitiesBySequenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList where sequenceNo is not null
        defaultProductStoreFacilityShouldBeFound("sequenceNo.specified=true");

        // Get all the productStoreFacilityList where sequenceNo is null
        defaultProductStoreFacilityShouldNotBeFound("sequenceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductStoreFacilitiesBySequenceNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList where sequenceNo is greater than or equal to DEFAULT_SEQUENCE_NO
        defaultProductStoreFacilityShouldBeFound("sequenceNo.greaterThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the productStoreFacilityList where sequenceNo is greater than or equal to UPDATED_SEQUENCE_NO
        defaultProductStoreFacilityShouldNotBeFound("sequenceNo.greaterThanOrEqual=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductStoreFacilitiesBySequenceNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList where sequenceNo is less than or equal to DEFAULT_SEQUENCE_NO
        defaultProductStoreFacilityShouldBeFound("sequenceNo.lessThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the productStoreFacilityList where sequenceNo is less than or equal to SMALLER_SEQUENCE_NO
        defaultProductStoreFacilityShouldNotBeFound("sequenceNo.lessThanOrEqual=" + SMALLER_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductStoreFacilitiesBySequenceNoIsLessThanSomething() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList where sequenceNo is less than DEFAULT_SEQUENCE_NO
        defaultProductStoreFacilityShouldNotBeFound("sequenceNo.lessThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the productStoreFacilityList where sequenceNo is less than UPDATED_SEQUENCE_NO
        defaultProductStoreFacilityShouldBeFound("sequenceNo.lessThan=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductStoreFacilitiesBySequenceNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);

        // Get all the productStoreFacilityList where sequenceNo is greater than DEFAULT_SEQUENCE_NO
        defaultProductStoreFacilityShouldNotBeFound("sequenceNo.greaterThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the productStoreFacilityList where sequenceNo is greater than SMALLER_SEQUENCE_NO
        defaultProductStoreFacilityShouldBeFound("sequenceNo.greaterThan=" + SMALLER_SEQUENCE_NO);
    }


    @Test
    @Transactional
    public void getAllProductStoreFacilitiesByProductStoreIsEqualToSomething() throws Exception {
        // Get already existing entity
        ProductStore productStore = productStoreFacility.getProductStore();
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);
        Long productStoreId = productStore.getId();

        // Get all the productStoreFacilityList where productStore equals to productStoreId
        defaultProductStoreFacilityShouldBeFound("productStoreId.equals=" + productStoreId);

        // Get all the productStoreFacilityList where productStore equals to productStoreId + 1
        defaultProductStoreFacilityShouldNotBeFound("productStoreId.equals=" + (productStoreId + 1));
    }


    @Test
    @Transactional
    public void getAllProductStoreFacilitiesByFacilityIsEqualToSomething() throws Exception {
        // Get already existing entity
        Facility facility = productStoreFacility.getFacility();
        productStoreFacilityRepository.saveAndFlush(productStoreFacility);
        Long facilityId = facility.getId();

        // Get all the productStoreFacilityList where facility equals to facilityId
        defaultProductStoreFacilityShouldBeFound("facilityId.equals=" + facilityId);

        // Get all the productStoreFacilityList where facility equals to facilityId + 1
        defaultProductStoreFacilityShouldNotBeFound("facilityId.equals=" + (facilityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductStoreFacilityShouldBeFound(String filter) throws Exception {
        restProductStoreFacilityMockMvc.perform(get("/api/product-store-facilities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productStoreFacility.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)));

        // Check, that the count call also returns 1
        restProductStoreFacilityMockMvc.perform(get("/api/product-store-facilities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductStoreFacilityShouldNotBeFound(String filter) throws Exception {
        restProductStoreFacilityMockMvc.perform(get("/api/product-store-facilities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductStoreFacilityMockMvc.perform(get("/api/product-store-facilities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductStoreFacility() throws Exception {
        // Get the productStoreFacility
        restProductStoreFacilityMockMvc.perform(get("/api/product-store-facilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductStoreFacility() throws Exception {
        // Initialize the database
        productStoreFacilityService.save(productStoreFacility);

        int databaseSizeBeforeUpdate = productStoreFacilityRepository.findAll().size();

        // Update the productStoreFacility
        ProductStoreFacility updatedProductStoreFacility = productStoreFacilityRepository.findById(productStoreFacility.getId()).get();
        // Disconnect from session so that the updates on updatedProductStoreFacility are not directly saved in db
        em.detach(updatedProductStoreFacility);
        updatedProductStoreFacility
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .sequenceNo(UPDATED_SEQUENCE_NO);

        restProductStoreFacilityMockMvc.perform(put("/api/product-store-facilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductStoreFacility)))
            .andExpect(status().isOk());

        // Validate the ProductStoreFacility in the database
        List<ProductStoreFacility> productStoreFacilityList = productStoreFacilityRepository.findAll();
        assertThat(productStoreFacilityList).hasSize(databaseSizeBeforeUpdate);
        ProductStoreFacility testProductStoreFacility = productStoreFacilityList.get(productStoreFacilityList.size() - 1);
        assertThat(testProductStoreFacility.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testProductStoreFacility.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testProductStoreFacility.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void updateNonExistingProductStoreFacility() throws Exception {
        int databaseSizeBeforeUpdate = productStoreFacilityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductStoreFacilityMockMvc.perform(put("/api/product-store-facilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStoreFacility)))
            .andExpect(status().isBadRequest());

        // Validate the ProductStoreFacility in the database
        List<ProductStoreFacility> productStoreFacilityList = productStoreFacilityRepository.findAll();
        assertThat(productStoreFacilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductStoreFacility() throws Exception {
        // Initialize the database
        productStoreFacilityService.save(productStoreFacility);

        int databaseSizeBeforeDelete = productStoreFacilityRepository.findAll().size();

        // Delete the productStoreFacility
        restProductStoreFacilityMockMvc.perform(delete("/api/product-store-facilities/{id}", productStoreFacility.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductStoreFacility> productStoreFacilityList = productStoreFacilityRepository.findAll();
        assertThat(productStoreFacilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
