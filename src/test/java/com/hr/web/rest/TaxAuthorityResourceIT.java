package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.TaxAuthority;
import com.hr.repository.TaxAuthorityRepository;

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
 * Integration tests for the {@link TaxAuthorityResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TaxAuthorityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TaxAuthorityRepository taxAuthorityRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTaxAuthorityMockMvc;

    private TaxAuthority taxAuthority;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxAuthority createEntity(EntityManager em) {
        TaxAuthority taxAuthority = new TaxAuthority()
            .name(DEFAULT_NAME);
        return taxAuthority;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TaxAuthority createUpdatedEntity(EntityManager em) {
        TaxAuthority taxAuthority = new TaxAuthority()
            .name(UPDATED_NAME);
        return taxAuthority;
    }

    @BeforeEach
    public void initTest() {
        taxAuthority = createEntity(em);
    }

    @Test
    @Transactional
    public void createTaxAuthority() throws Exception {
        int databaseSizeBeforeCreate = taxAuthorityRepository.findAll().size();
        // Create the TaxAuthority
        restTaxAuthorityMockMvc.perform(post("/api/tax-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxAuthority)))
            .andExpect(status().isCreated());

        // Validate the TaxAuthority in the database
        List<TaxAuthority> taxAuthorityList = taxAuthorityRepository.findAll();
        assertThat(taxAuthorityList).hasSize(databaseSizeBeforeCreate + 1);
        TaxAuthority testTaxAuthority = taxAuthorityList.get(taxAuthorityList.size() - 1);
        assertThat(testTaxAuthority.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTaxAuthorityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = taxAuthorityRepository.findAll().size();

        // Create the TaxAuthority with an existing ID
        taxAuthority.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTaxAuthorityMockMvc.perform(post("/api/tax-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxAuthority)))
            .andExpect(status().isBadRequest());

        // Validate the TaxAuthority in the database
        List<TaxAuthority> taxAuthorityList = taxAuthorityRepository.findAll();
        assertThat(taxAuthorityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTaxAuthorities() throws Exception {
        // Initialize the database
        taxAuthorityRepository.saveAndFlush(taxAuthority);

        // Get all the taxAuthorityList
        restTaxAuthorityMockMvc.perform(get("/api/tax-authorities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(taxAuthority.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getTaxAuthority() throws Exception {
        // Initialize the database
        taxAuthorityRepository.saveAndFlush(taxAuthority);

        // Get the taxAuthority
        restTaxAuthorityMockMvc.perform(get("/api/tax-authorities/{id}", taxAuthority.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(taxAuthority.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }
    @Test
    @Transactional
    public void getNonExistingTaxAuthority() throws Exception {
        // Get the taxAuthority
        restTaxAuthorityMockMvc.perform(get("/api/tax-authorities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTaxAuthority() throws Exception {
        // Initialize the database
        taxAuthorityRepository.saveAndFlush(taxAuthority);

        int databaseSizeBeforeUpdate = taxAuthorityRepository.findAll().size();

        // Update the taxAuthority
        TaxAuthority updatedTaxAuthority = taxAuthorityRepository.findById(taxAuthority.getId()).get();
        // Disconnect from session so that the updates on updatedTaxAuthority are not directly saved in db
        em.detach(updatedTaxAuthority);
        updatedTaxAuthority
            .name(UPDATED_NAME);

        restTaxAuthorityMockMvc.perform(put("/api/tax-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTaxAuthority)))
            .andExpect(status().isOk());

        // Validate the TaxAuthority in the database
        List<TaxAuthority> taxAuthorityList = taxAuthorityRepository.findAll();
        assertThat(taxAuthorityList).hasSize(databaseSizeBeforeUpdate);
        TaxAuthority testTaxAuthority = taxAuthorityList.get(taxAuthorityList.size() - 1);
        assertThat(testTaxAuthority.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTaxAuthority() throws Exception {
        int databaseSizeBeforeUpdate = taxAuthorityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTaxAuthorityMockMvc.perform(put("/api/tax-authorities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(taxAuthority)))
            .andExpect(status().isBadRequest());

        // Validate the TaxAuthority in the database
        List<TaxAuthority> taxAuthorityList = taxAuthorityRepository.findAll();
        assertThat(taxAuthorityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTaxAuthority() throws Exception {
        // Initialize the database
        taxAuthorityRepository.saveAndFlush(taxAuthority);

        int databaseSizeBeforeDelete = taxAuthorityRepository.findAll().size();

        // Delete the taxAuthority
        restTaxAuthorityMockMvc.perform(delete("/api/tax-authorities/{id}", taxAuthority.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TaxAuthority> taxAuthorityList = taxAuthorityRepository.findAll();
        assertThat(taxAuthorityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
