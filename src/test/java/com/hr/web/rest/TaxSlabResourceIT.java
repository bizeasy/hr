package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.TaxSlab;
import com.hr.repository.TaxSlabRepository;

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
 * Integration tests for the {@link TaxSlabResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaxSlabResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_RATE = new BigDecimal(1);
    private static final BigDecimal UPDATED_RATE = new BigDecimal(2);

    @Autowired
    private TaxSlabRepository taxSlabRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaxSlabMockMvc;

    private TaxSlab taxSlab;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxSlab createEntity(EntityManager em) {
        TaxSlab taxSlab = new TaxSlab()
            .name(DEFAULT_NAME)
            .rate(DEFAULT_RATE);
        return taxSlab;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxSlab createUpdatedEntity(EntityManager em) {
        TaxSlab taxSlab = new TaxSlab()
            .name(UPDATED_NAME)
            .rate(UPDATED_RATE);
        return taxSlab;
    }

    @BeforeEach
    public void initTest() {
        taxSlab = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaxSlab() throws Exception {
        int databaseSizeBeforeCreate = taxSlabRepository.findAll().size();
        // Create the TaxSlab
        restTaxSlabMockMvc.perform(post("/api/tax-slabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxSlab)))
            .andExpect(status().isCreated());

        // Validate the TaxSlab in the database
        List<TaxSlab> taxSlabList = taxSlabRepository.findAll();
        assertThat(taxSlabList).hasSize(databaseSizeBeforeCreate + 1);
        TaxSlab testTaxSlab = taxSlabList.get(taxSlabList.size() - 1);
        assertThat(testTaxSlab.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTaxSlab.getRate()).isEqualTo(DEFAULT_RATE);
    }

    @Test
    @Transactional
    public void createTaxSlabWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxSlabRepository.findAll().size();

        // Create the TaxSlab with an existing ID
        taxSlab.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxSlabMockMvc.perform(post("/api/tax-slabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxSlab)))
            .andExpect(status().isBadRequest());

        // Validate the TaxSlab in the database
        List<TaxSlab> taxSlabList = taxSlabRepository.findAll();
        assertThat(taxSlabList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTaxSlabs() throws Exception {
        // Initialize the database
        taxSlabRepository.saveAndFlush(taxSlab);

        // Get all the taxSlabList
        restTaxSlabMockMvc.perform(get("/api/tax-slabs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxSlab.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.intValue())));
    }
    
    @Test
    @Transactional
    public void getTaxSlab() throws Exception {
        // Initialize the database
        taxSlabRepository.saveAndFlush(taxSlab);

        // Get the taxSlab
        restTaxSlabMockMvc.perform(get("/api/tax-slabs/{id}", taxSlab.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taxSlab.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingTaxSlab() throws Exception {
        // Get the taxSlab
        restTaxSlabMockMvc.perform(get("/api/tax-slabs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxSlab() throws Exception {
        // Initialize the database
        taxSlabRepository.saveAndFlush(taxSlab);

        int databaseSizeBeforeUpdate = taxSlabRepository.findAll().size();

        // Update the taxSlab
        TaxSlab updatedTaxSlab = taxSlabRepository.findById(taxSlab.getId()).get();
        // Disconnect from session so that the updates on updatedTaxSlab are not directly saved in db
        em.detach(updatedTaxSlab);
        updatedTaxSlab
            .name(UPDATED_NAME)
            .rate(UPDATED_RATE);

        restTaxSlabMockMvc.perform(put("/api/tax-slabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaxSlab)))
            .andExpect(status().isOk());

        // Validate the TaxSlab in the database
        List<TaxSlab> taxSlabList = taxSlabRepository.findAll();
        assertThat(taxSlabList).hasSize(databaseSizeBeforeUpdate);
        TaxSlab testTaxSlab = taxSlabList.get(taxSlabList.size() - 1);
        assertThat(testTaxSlab.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTaxSlab.getRate()).isEqualTo(UPDATED_RATE);
    }

    @Test
    @Transactional
    public void updateNonExistingTaxSlab() throws Exception {
        int databaseSizeBeforeUpdate = taxSlabRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxSlabMockMvc.perform(put("/api/tax-slabs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxSlab)))
            .andExpect(status().isBadRequest());

        // Validate the TaxSlab in the database
        List<TaxSlab> taxSlabList = taxSlabRepository.findAll();
        assertThat(taxSlabList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaxSlab() throws Exception {
        // Initialize the database
        taxSlabRepository.saveAndFlush(taxSlab);

        int databaseSizeBeforeDelete = taxSlabRepository.findAll().size();

        // Delete the taxSlab
        restTaxSlabMockMvc.perform(delete("/api/tax-slabs/{id}", taxSlab.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaxSlab> taxSlabList = taxSlabRepository.findAll();
        assertThat(taxSlabList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
