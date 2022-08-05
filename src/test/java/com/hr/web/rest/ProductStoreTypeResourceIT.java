package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ProductStoreType;
import com.hr.repository.ProductStoreTypeRepository;

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
 * Integration tests for the {@link ProductStoreTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductStoreTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProductStoreTypeRepository productStoreTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductStoreTypeMockMvc;

    private ProductStoreType productStoreType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductStoreType createEntity(EntityManager em) {
        ProductStoreType productStoreType = new ProductStoreType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return productStoreType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductStoreType createUpdatedEntity(EntityManager em) {
        ProductStoreType productStoreType = new ProductStoreType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return productStoreType;
    }

    @BeforeEach
    public void initTest() {
        productStoreType = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductStoreType() throws Exception {
        int databaseSizeBeforeCreate = productStoreTypeRepository.findAll().size();
        // Create the ProductStoreType
        restProductStoreTypeMockMvc.perform(post("/api/product-store-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStoreType)))
            .andExpect(status().isCreated());

        // Validate the ProductStoreType in the database
        List<ProductStoreType> productStoreTypeList = productStoreTypeRepository.findAll();
        assertThat(productStoreTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductStoreType testProductStoreType = productStoreTypeList.get(productStoreTypeList.size() - 1);
        assertThat(testProductStoreType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductStoreType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProductStoreTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productStoreTypeRepository.findAll().size();

        // Create the ProductStoreType with an existing ID
        productStoreType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductStoreTypeMockMvc.perform(post("/api/product-store-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStoreType)))
            .andExpect(status().isBadRequest());

        // Validate the ProductStoreType in the database
        List<ProductStoreType> productStoreTypeList = productStoreTypeRepository.findAll();
        assertThat(productStoreTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductStoreTypes() throws Exception {
        // Initialize the database
        productStoreTypeRepository.saveAndFlush(productStoreType);

        // Get all the productStoreTypeList
        restProductStoreTypeMockMvc.perform(get("/api/product-store-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productStoreType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getProductStoreType() throws Exception {
        // Initialize the database
        productStoreTypeRepository.saveAndFlush(productStoreType);

        // Get the productStoreType
        restProductStoreTypeMockMvc.perform(get("/api/product-store-types/{id}", productStoreType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productStoreType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingProductStoreType() throws Exception {
        // Get the productStoreType
        restProductStoreTypeMockMvc.perform(get("/api/product-store-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductStoreType() throws Exception {
        // Initialize the database
        productStoreTypeRepository.saveAndFlush(productStoreType);

        int databaseSizeBeforeUpdate = productStoreTypeRepository.findAll().size();

        // Update the productStoreType
        ProductStoreType updatedProductStoreType = productStoreTypeRepository.findById(productStoreType.getId()).get();
        // Disconnect from session so that the updates on updatedProductStoreType are not directly saved in db
        em.detach(updatedProductStoreType);
        updatedProductStoreType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProductStoreTypeMockMvc.perform(put("/api/product-store-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductStoreType)))
            .andExpect(status().isOk());

        // Validate the ProductStoreType in the database
        List<ProductStoreType> productStoreTypeList = productStoreTypeRepository.findAll();
        assertThat(productStoreTypeList).hasSize(databaseSizeBeforeUpdate);
        ProductStoreType testProductStoreType = productStoreTypeList.get(productStoreTypeList.size() - 1);
        assertThat(testProductStoreType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductStoreType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProductStoreType() throws Exception {
        int databaseSizeBeforeUpdate = productStoreTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductStoreTypeMockMvc.perform(put("/api/product-store-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStoreType)))
            .andExpect(status().isBadRequest());

        // Validate the ProductStoreType in the database
        List<ProductStoreType> productStoreTypeList = productStoreTypeRepository.findAll();
        assertThat(productStoreTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductStoreType() throws Exception {
        // Initialize the database
        productStoreTypeRepository.saveAndFlush(productStoreType);

        int databaseSizeBeforeDelete = productStoreTypeRepository.findAll().size();

        // Delete the productStoreType
        restProductStoreTypeMockMvc.perform(delete("/api/product-store-types/{id}", productStoreType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductStoreType> productStoreTypeList = productStoreTypeRepository.findAll();
        assertThat(productStoreTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
