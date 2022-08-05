package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ProductCategoryType;
import com.hr.repository.ProductCategoryTypeRepository;

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
 * Integration tests for the {@link ProductCategoryTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductCategoryTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProductCategoryTypeRepository productCategoryTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductCategoryTypeMockMvc;

    private ProductCategoryType productCategoryType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategoryType createEntity(EntityManager em) {
        ProductCategoryType productCategoryType = new ProductCategoryType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return productCategoryType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategoryType createUpdatedEntity(EntityManager em) {
        ProductCategoryType productCategoryType = new ProductCategoryType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return productCategoryType;
    }

    @BeforeEach
    public void initTest() {
        productCategoryType = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductCategoryType() throws Exception {
        int databaseSizeBeforeCreate = productCategoryTypeRepository.findAll().size();
        // Create the ProductCategoryType
        restProductCategoryTypeMockMvc.perform(post("/api/product-category-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryType)))
            .andExpect(status().isCreated());

        // Validate the ProductCategoryType in the database
        List<ProductCategoryType> productCategoryTypeList = productCategoryTypeRepository.findAll();
        assertThat(productCategoryTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductCategoryType testProductCategoryType = productCategoryTypeList.get(productCategoryTypeList.size() - 1);
        assertThat(testProductCategoryType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductCategoryType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProductCategoryTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productCategoryTypeRepository.findAll().size();

        // Create the ProductCategoryType with an existing ID
        productCategoryType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductCategoryTypeMockMvc.perform(post("/api/product-category-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryType)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryType in the database
        List<ProductCategoryType> productCategoryTypeList = productCategoryTypeRepository.findAll();
        assertThat(productCategoryTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductCategoryTypes() throws Exception {
        // Initialize the database
        productCategoryTypeRepository.saveAndFlush(productCategoryType);

        // Get all the productCategoryTypeList
        restProductCategoryTypeMockMvc.perform(get("/api/product-category-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategoryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getProductCategoryType() throws Exception {
        // Initialize the database
        productCategoryTypeRepository.saveAndFlush(productCategoryType);

        // Get the productCategoryType
        restProductCategoryTypeMockMvc.perform(get("/api/product-category-types/{id}", productCategoryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productCategoryType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingProductCategoryType() throws Exception {
        // Get the productCategoryType
        restProductCategoryTypeMockMvc.perform(get("/api/product-category-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductCategoryType() throws Exception {
        // Initialize the database
        productCategoryTypeRepository.saveAndFlush(productCategoryType);

        int databaseSizeBeforeUpdate = productCategoryTypeRepository.findAll().size();

        // Update the productCategoryType
        ProductCategoryType updatedProductCategoryType = productCategoryTypeRepository.findById(productCategoryType.getId()).get();
        // Disconnect from session so that the updates on updatedProductCategoryType are not directly saved in db
        em.detach(updatedProductCategoryType);
        updatedProductCategoryType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProductCategoryTypeMockMvc.perform(put("/api/product-category-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductCategoryType)))
            .andExpect(status().isOk());

        // Validate the ProductCategoryType in the database
        List<ProductCategoryType> productCategoryTypeList = productCategoryTypeRepository.findAll();
        assertThat(productCategoryTypeList).hasSize(databaseSizeBeforeUpdate);
        ProductCategoryType testProductCategoryType = productCategoryTypeList.get(productCategoryTypeList.size() - 1);
        assertThat(testProductCategoryType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductCategoryType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProductCategoryType() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCategoryTypeMockMvc.perform(put("/api/product-category-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategoryType)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryType in the database
        List<ProductCategoryType> productCategoryTypeList = productCategoryTypeRepository.findAll();
        assertThat(productCategoryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductCategoryType() throws Exception {
        // Initialize the database
        productCategoryTypeRepository.saveAndFlush(productCategoryType);

        int databaseSizeBeforeDelete = productCategoryTypeRepository.findAll().size();

        // Delete the productCategoryType
        restProductCategoryTypeMockMvc.perform(delete("/api/product-category-types/{id}", productCategoryType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductCategoryType> productCategoryTypeList = productCategoryTypeRepository.findAll();
        assertThat(productCategoryTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
