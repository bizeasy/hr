package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ProductPriceType;
import com.hr.repository.ProductPriceTypeRepository;

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
 * Integration tests for the {@link ProductPriceTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductPriceTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProductPriceTypeRepository productPriceTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductPriceTypeMockMvc;

    private ProductPriceType productPriceType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductPriceType createEntity(EntityManager em) {
        ProductPriceType productPriceType = new ProductPriceType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return productPriceType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductPriceType createUpdatedEntity(EntityManager em) {
        ProductPriceType productPriceType = new ProductPriceType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return productPriceType;
    }

    @BeforeEach
    public void initTest() {
        productPriceType = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductPriceType() throws Exception {
        int databaseSizeBeforeCreate = productPriceTypeRepository.findAll().size();
        // Create the ProductPriceType
        restProductPriceTypeMockMvc.perform(post("/api/product-price-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productPriceType)))
            .andExpect(status().isCreated());

        // Validate the ProductPriceType in the database
        List<ProductPriceType> productPriceTypeList = productPriceTypeRepository.findAll();
        assertThat(productPriceTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductPriceType testProductPriceType = productPriceTypeList.get(productPriceTypeList.size() - 1);
        assertThat(testProductPriceType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductPriceType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProductPriceTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productPriceTypeRepository.findAll().size();

        // Create the ProductPriceType with an existing ID
        productPriceType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductPriceTypeMockMvc.perform(post("/api/product-price-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productPriceType)))
            .andExpect(status().isBadRequest());

        // Validate the ProductPriceType in the database
        List<ProductPriceType> productPriceTypeList = productPriceTypeRepository.findAll();
        assertThat(productPriceTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductPriceTypes() throws Exception {
        // Initialize the database
        productPriceTypeRepository.saveAndFlush(productPriceType);

        // Get all the productPriceTypeList
        restProductPriceTypeMockMvc.perform(get("/api/product-price-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productPriceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getProductPriceType() throws Exception {
        // Initialize the database
        productPriceTypeRepository.saveAndFlush(productPriceType);

        // Get the productPriceType
        restProductPriceTypeMockMvc.perform(get("/api/product-price-types/{id}", productPriceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productPriceType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingProductPriceType() throws Exception {
        // Get the productPriceType
        restProductPriceTypeMockMvc.perform(get("/api/product-price-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductPriceType() throws Exception {
        // Initialize the database
        productPriceTypeRepository.saveAndFlush(productPriceType);

        int databaseSizeBeforeUpdate = productPriceTypeRepository.findAll().size();

        // Update the productPriceType
        ProductPriceType updatedProductPriceType = productPriceTypeRepository.findById(productPriceType.getId()).get();
        // Disconnect from session so that the updates on updatedProductPriceType are not directly saved in db
        em.detach(updatedProductPriceType);
        updatedProductPriceType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProductPriceTypeMockMvc.perform(put("/api/product-price-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductPriceType)))
            .andExpect(status().isOk());

        // Validate the ProductPriceType in the database
        List<ProductPriceType> productPriceTypeList = productPriceTypeRepository.findAll();
        assertThat(productPriceTypeList).hasSize(databaseSizeBeforeUpdate);
        ProductPriceType testProductPriceType = productPriceTypeList.get(productPriceTypeList.size() - 1);
        assertThat(testProductPriceType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductPriceType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProductPriceType() throws Exception {
        int databaseSizeBeforeUpdate = productPriceTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductPriceTypeMockMvc.perform(put("/api/product-price-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productPriceType)))
            .andExpect(status().isBadRequest());

        // Validate the ProductPriceType in the database
        List<ProductPriceType> productPriceTypeList = productPriceTypeRepository.findAll();
        assertThat(productPriceTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductPriceType() throws Exception {
        // Initialize the database
        productPriceTypeRepository.saveAndFlush(productPriceType);

        int databaseSizeBeforeDelete = productPriceTypeRepository.findAll().size();

        // Delete the productPriceType
        restProductPriceTypeMockMvc.perform(delete("/api/product-price-types/{id}", productPriceType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductPriceType> productPriceTypeList = productPriceTypeRepository.findAll();
        assertThat(productPriceTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
