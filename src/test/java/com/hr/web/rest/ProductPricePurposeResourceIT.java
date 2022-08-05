package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ProductPricePurpose;
import com.hr.repository.ProductPricePurposeRepository;

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
 * Integration tests for the {@link ProductPricePurposeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductPricePurposeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ProductPricePurposeRepository productPricePurposeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductPricePurposeMockMvc;

    private ProductPricePurpose productPricePurpose;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductPricePurpose createEntity(EntityManager em) {
        ProductPricePurpose productPricePurpose = new ProductPricePurpose()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return productPricePurpose;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductPricePurpose createUpdatedEntity(EntityManager em) {
        ProductPricePurpose productPricePurpose = new ProductPricePurpose()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return productPricePurpose;
    }

    @BeforeEach
    public void initTest() {
        productPricePurpose = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductPricePurpose() throws Exception {
        int databaseSizeBeforeCreate = productPricePurposeRepository.findAll().size();
        // Create the ProductPricePurpose
        restProductPricePurposeMockMvc.perform(post("/api/product-price-purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productPricePurpose)))
            .andExpect(status().isCreated());

        // Validate the ProductPricePurpose in the database
        List<ProductPricePurpose> productPricePurposeList = productPricePurposeRepository.findAll();
        assertThat(productPricePurposeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductPricePurpose testProductPricePurpose = productPricePurposeList.get(productPricePurposeList.size() - 1);
        assertThat(testProductPricePurpose.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductPricePurpose.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createProductPricePurposeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productPricePurposeRepository.findAll().size();

        // Create the ProductPricePurpose with an existing ID
        productPricePurpose.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductPricePurposeMockMvc.perform(post("/api/product-price-purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productPricePurpose)))
            .andExpect(status().isBadRequest());

        // Validate the ProductPricePurpose in the database
        List<ProductPricePurpose> productPricePurposeList = productPricePurposeRepository.findAll();
        assertThat(productPricePurposeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductPricePurposes() throws Exception {
        // Initialize the database
        productPricePurposeRepository.saveAndFlush(productPricePurpose);

        // Get all the productPricePurposeList
        restProductPricePurposeMockMvc.perform(get("/api/product-price-purposes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productPricePurpose.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getProductPricePurpose() throws Exception {
        // Initialize the database
        productPricePurposeRepository.saveAndFlush(productPricePurpose);

        // Get the productPricePurpose
        restProductPricePurposeMockMvc.perform(get("/api/product-price-purposes/{id}", productPricePurpose.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productPricePurpose.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingProductPricePurpose() throws Exception {
        // Get the productPricePurpose
        restProductPricePurposeMockMvc.perform(get("/api/product-price-purposes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductPricePurpose() throws Exception {
        // Initialize the database
        productPricePurposeRepository.saveAndFlush(productPricePurpose);

        int databaseSizeBeforeUpdate = productPricePurposeRepository.findAll().size();

        // Update the productPricePurpose
        ProductPricePurpose updatedProductPricePurpose = productPricePurposeRepository.findById(productPricePurpose.getId()).get();
        // Disconnect from session so that the updates on updatedProductPricePurpose are not directly saved in db
        em.detach(updatedProductPricePurpose);
        updatedProductPricePurpose
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restProductPricePurposeMockMvc.perform(put("/api/product-price-purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductPricePurpose)))
            .andExpect(status().isOk());

        // Validate the ProductPricePurpose in the database
        List<ProductPricePurpose> productPricePurposeList = productPricePurposeRepository.findAll();
        assertThat(productPricePurposeList).hasSize(databaseSizeBeforeUpdate);
        ProductPricePurpose testProductPricePurpose = productPricePurposeList.get(productPricePurposeList.size() - 1);
        assertThat(testProductPricePurpose.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductPricePurpose.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingProductPricePurpose() throws Exception {
        int databaseSizeBeforeUpdate = productPricePurposeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductPricePurposeMockMvc.perform(put("/api/product-price-purposes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productPricePurpose)))
            .andExpect(status().isBadRequest());

        // Validate the ProductPricePurpose in the database
        List<ProductPricePurpose> productPricePurposeList = productPricePurposeRepository.findAll();
        assertThat(productPricePurposeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductPricePurpose() throws Exception {
        // Initialize the database
        productPricePurposeRepository.saveAndFlush(productPricePurpose);

        int databaseSizeBeforeDelete = productPricePurposeRepository.findAll().size();

        // Delete the productPricePurpose
        restProductPricePurposeMockMvc.perform(delete("/api/product-price-purposes/{id}", productPricePurpose.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductPricePurpose> productPricePurposeList = productPricePurposeRepository.findAll();
        assertThat(productPricePurposeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
