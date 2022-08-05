package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.TermType;
import com.hr.repository.TermTypeRepository;

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
 * Integration tests for the {@link TermTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TermTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private TermTypeRepository termTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTermTypeMockMvc;

    private TermType termType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TermType createEntity(EntityManager em) {
        TermType termType = new TermType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return termType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TermType createUpdatedEntity(EntityManager em) {
        TermType termType = new TermType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return termType;
    }

    @BeforeEach
    public void initTest() {
        termType = createEntity(em);
    }

    @Test
    @Transactional
    public void createTermType() throws Exception {
        int databaseSizeBeforeCreate = termTypeRepository.findAll().size();
        // Create the TermType
        restTermTypeMockMvc.perform(post("/api/term-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(termType)))
            .andExpect(status().isCreated());

        // Validate the TermType in the database
        List<TermType> termTypeList = termTypeRepository.findAll();
        assertThat(termTypeList).hasSize(databaseSizeBeforeCreate + 1);
        TermType testTermType = termTypeList.get(termTypeList.size() - 1);
        assertThat(testTermType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTermType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createTermTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = termTypeRepository.findAll().size();

        // Create the TermType with an existing ID
        termType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTermTypeMockMvc.perform(post("/api/term-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(termType)))
            .andExpect(status().isBadRequest());

        // Validate the TermType in the database
        List<TermType> termTypeList = termTypeRepository.findAll();
        assertThat(termTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTermTypes() throws Exception {
        // Initialize the database
        termTypeRepository.saveAndFlush(termType);

        // Get all the termTypeList
        restTermTypeMockMvc.perform(get("/api/term-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(termType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getTermType() throws Exception {
        // Initialize the database
        termTypeRepository.saveAndFlush(termType);

        // Get the termType
        restTermTypeMockMvc.perform(get("/api/term-types/{id}", termType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(termType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingTermType() throws Exception {
        // Get the termType
        restTermTypeMockMvc.perform(get("/api/term-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTermType() throws Exception {
        // Initialize the database
        termTypeRepository.saveAndFlush(termType);

        int databaseSizeBeforeUpdate = termTypeRepository.findAll().size();

        // Update the termType
        TermType updatedTermType = termTypeRepository.findById(termType.getId()).get();
        // Disconnect from session so that the updates on updatedTermType are not directly saved in db
        em.detach(updatedTermType);
        updatedTermType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restTermTypeMockMvc.perform(put("/api/term-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTermType)))
            .andExpect(status().isOk());

        // Validate the TermType in the database
        List<TermType> termTypeList = termTypeRepository.findAll();
        assertThat(termTypeList).hasSize(databaseSizeBeforeUpdate);
        TermType testTermType = termTypeList.get(termTypeList.size() - 1);
        assertThat(testTermType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTermType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingTermType() throws Exception {
        int databaseSizeBeforeUpdate = termTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermTypeMockMvc.perform(put("/api/term-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(termType)))
            .andExpect(status().isBadRequest());

        // Validate the TermType in the database
        List<TermType> termTypeList = termTypeRepository.findAll();
        assertThat(termTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTermType() throws Exception {
        // Initialize the database
        termTypeRepository.saveAndFlush(termType);

        int databaseSizeBeforeDelete = termTypeRepository.findAll().size();

        // Delete the termType
        restTermTypeMockMvc.perform(delete("/api/term-types/{id}", termType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TermType> termTypeList = termTypeRepository.findAll();
        assertThat(termTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
