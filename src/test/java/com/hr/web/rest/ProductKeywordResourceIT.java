package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ProductKeyword;
import com.hr.repository.ProductKeywordRepository;

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
 * Integration tests for the {@link ProductKeywordResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductKeywordResourceIT {

    private static final String DEFAULT_KEYWORD = "AAAAAAAAAA";
    private static final String UPDATED_KEYWORD = "BBBBBBBBBB";

    private static final Integer DEFAULT_RELEVANCY_WEIGHT = 1;
    private static final Integer UPDATED_RELEVANCY_WEIGHT = 2;

    @Autowired
    private ProductKeywordRepository productKeywordRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductKeywordMockMvc;

    private ProductKeyword productKeyword;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductKeyword createEntity(EntityManager em) {
        ProductKeyword productKeyword = new ProductKeyword()
            .keyword(DEFAULT_KEYWORD)
            .relevancyWeight(DEFAULT_RELEVANCY_WEIGHT);
        return productKeyword;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductKeyword createUpdatedEntity(EntityManager em) {
        ProductKeyword productKeyword = new ProductKeyword()
            .keyword(UPDATED_KEYWORD)
            .relevancyWeight(UPDATED_RELEVANCY_WEIGHT);
        return productKeyword;
    }

    @BeforeEach
    public void initTest() {
        productKeyword = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductKeyword() throws Exception {
        int databaseSizeBeforeCreate = productKeywordRepository.findAll().size();
        // Create the ProductKeyword
        restProductKeywordMockMvc.perform(post("/api/product-keywords")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productKeyword)))
            .andExpect(status().isCreated());

        // Validate the ProductKeyword in the database
        List<ProductKeyword> productKeywordList = productKeywordRepository.findAll();
        assertThat(productKeywordList).hasSize(databaseSizeBeforeCreate + 1);
        ProductKeyword testProductKeyword = productKeywordList.get(productKeywordList.size() - 1);
        assertThat(testProductKeyword.getKeyword()).isEqualTo(DEFAULT_KEYWORD);
        assertThat(testProductKeyword.getRelevancyWeight()).isEqualTo(DEFAULT_RELEVANCY_WEIGHT);
    }

    @Test
    @Transactional
    public void createProductKeywordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productKeywordRepository.findAll().size();

        // Create the ProductKeyword with an existing ID
        productKeyword.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductKeywordMockMvc.perform(post("/api/product-keywords")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productKeyword)))
            .andExpect(status().isBadRequest());

        // Validate the ProductKeyword in the database
        List<ProductKeyword> productKeywordList = productKeywordRepository.findAll();
        assertThat(productKeywordList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductKeywords() throws Exception {
        // Initialize the database
        productKeywordRepository.saveAndFlush(productKeyword);

        // Get all the productKeywordList
        restProductKeywordMockMvc.perform(get("/api/product-keywords?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productKeyword.getId().intValue())))
            .andExpect(jsonPath("$.[*].keyword").value(hasItem(DEFAULT_KEYWORD)))
            .andExpect(jsonPath("$.[*].relevancyWeight").value(hasItem(DEFAULT_RELEVANCY_WEIGHT)));
    }
    
    @Test
    @Transactional
    public void getProductKeyword() throws Exception {
        // Initialize the database
        productKeywordRepository.saveAndFlush(productKeyword);

        // Get the productKeyword
        restProductKeywordMockMvc.perform(get("/api/product-keywords/{id}", productKeyword.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productKeyword.getId().intValue()))
            .andExpect(jsonPath("$.keyword").value(DEFAULT_KEYWORD))
            .andExpect(jsonPath("$.relevancyWeight").value(DEFAULT_RELEVANCY_WEIGHT));
    }
    @Test
    @Transactional
    public void getNonExistingProductKeyword() throws Exception {
        // Get the productKeyword
        restProductKeywordMockMvc.perform(get("/api/product-keywords/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductKeyword() throws Exception {
        // Initialize the database
        productKeywordRepository.saveAndFlush(productKeyword);

        int databaseSizeBeforeUpdate = productKeywordRepository.findAll().size();

        // Update the productKeyword
        ProductKeyword updatedProductKeyword = productKeywordRepository.findById(productKeyword.getId()).get();
        // Disconnect from session so that the updates on updatedProductKeyword are not directly saved in db
        em.detach(updatedProductKeyword);
        updatedProductKeyword
            .keyword(UPDATED_KEYWORD)
            .relevancyWeight(UPDATED_RELEVANCY_WEIGHT);

        restProductKeywordMockMvc.perform(put("/api/product-keywords")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductKeyword)))
            .andExpect(status().isOk());

        // Validate the ProductKeyword in the database
        List<ProductKeyword> productKeywordList = productKeywordRepository.findAll();
        assertThat(productKeywordList).hasSize(databaseSizeBeforeUpdate);
        ProductKeyword testProductKeyword = productKeywordList.get(productKeywordList.size() - 1);
        assertThat(testProductKeyword.getKeyword()).isEqualTo(UPDATED_KEYWORD);
        assertThat(testProductKeyword.getRelevancyWeight()).isEqualTo(UPDATED_RELEVANCY_WEIGHT);
    }

    @Test
    @Transactional
    public void updateNonExistingProductKeyword() throws Exception {
        int databaseSizeBeforeUpdate = productKeywordRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductKeywordMockMvc.perform(put("/api/product-keywords")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productKeyword)))
            .andExpect(status().isBadRequest());

        // Validate the ProductKeyword in the database
        List<ProductKeyword> productKeywordList = productKeywordRepository.findAll();
        assertThat(productKeywordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductKeyword() throws Exception {
        // Initialize the database
        productKeywordRepository.saveAndFlush(productKeyword);

        int databaseSizeBeforeDelete = productKeywordRepository.findAll().size();

        // Delete the productKeyword
        restProductKeywordMockMvc.perform(delete("/api/product-keywords/{id}", productKeyword.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductKeyword> productKeywordList = productKeywordRepository.findAll();
        assertThat(productKeywordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
