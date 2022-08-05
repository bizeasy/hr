package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.Term;
import com.hr.repository.TermRepository;

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
 * Integration tests for the {@link TermResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TermResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TERM_DETAIL = "AAAAAAAAAA";
    private static final String UPDATED_TERM_DETAIL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TERM_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TERM_VALUE = new BigDecimal(2);

    private static final Integer DEFAULT_TERM_DAYS = 1;
    private static final Integer UPDATED_TERM_DAYS = 2;

    private static final String DEFAULT_TEXT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_TEXT_VALUE = "BBBBBBBBBB";

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTermMockMvc;

    private Term term;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Term createEntity(EntityManager em) {
        Term term = new Term()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .termDetail(DEFAULT_TERM_DETAIL)
            .termValue(DEFAULT_TERM_VALUE)
            .termDays(DEFAULT_TERM_DAYS)
            .textValue(DEFAULT_TEXT_VALUE);
        return term;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Term createUpdatedEntity(EntityManager em) {
        Term term = new Term()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .termDetail(UPDATED_TERM_DETAIL)
            .termValue(UPDATED_TERM_VALUE)
            .termDays(UPDATED_TERM_DAYS)
            .textValue(UPDATED_TEXT_VALUE);
        return term;
    }

    @BeforeEach
    public void initTest() {
        term = createEntity(em);
    }

    @Test
    @Transactional
    public void createTerm() throws Exception {
        int databaseSizeBeforeCreate = termRepository.findAll().size();
        // Create the Term
        restTermMockMvc.perform(post("/api/terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(term)))
            .andExpect(status().isCreated());

        // Validate the Term in the database
        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeCreate + 1);
        Term testTerm = termList.get(termList.size() - 1);
        assertThat(testTerm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTerm.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTerm.getTermDetail()).isEqualTo(DEFAULT_TERM_DETAIL);
        assertThat(testTerm.getTermValue()).isEqualTo(DEFAULT_TERM_VALUE);
        assertThat(testTerm.getTermDays()).isEqualTo(DEFAULT_TERM_DAYS);
        assertThat(testTerm.getTextValue()).isEqualTo(DEFAULT_TEXT_VALUE);
    }

    @Test
    @Transactional
    public void createTermWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = termRepository.findAll().size();

        // Create the Term with an existing ID
        term.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTermMockMvc.perform(post("/api/terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(term)))
            .andExpect(status().isBadRequest());

        // Validate the Term in the database
        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTerms() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get all the termList
        restTermMockMvc.perform(get("/api/terms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(term.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].termDetail").value(hasItem(DEFAULT_TERM_DETAIL)))
            .andExpect(jsonPath("$.[*].termValue").value(hasItem(DEFAULT_TERM_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].termDays").value(hasItem(DEFAULT_TERM_DAYS)))
            .andExpect(jsonPath("$.[*].textValue").value(hasItem(DEFAULT_TEXT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getTerm() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        // Get the term
        restTermMockMvc.perform(get("/api/terms/{id}", term.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(term.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.termDetail").value(DEFAULT_TERM_DETAIL))
            .andExpect(jsonPath("$.termValue").value(DEFAULT_TERM_VALUE.intValue()))
            .andExpect(jsonPath("$.termDays").value(DEFAULT_TERM_DAYS))
            .andExpect(jsonPath("$.textValue").value(DEFAULT_TEXT_VALUE));
    }
    @Test
    @Transactional
    public void getNonExistingTerm() throws Exception {
        // Get the term
        restTermMockMvc.perform(get("/api/terms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTerm() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        int databaseSizeBeforeUpdate = termRepository.findAll().size();

        // Update the term
        Term updatedTerm = termRepository.findById(term.getId()).get();
        // Disconnect from session so that the updates on updatedTerm are not directly saved in db
        em.detach(updatedTerm);
        updatedTerm
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .termDetail(UPDATED_TERM_DETAIL)
            .termValue(UPDATED_TERM_VALUE)
            .termDays(UPDATED_TERM_DAYS)
            .textValue(UPDATED_TEXT_VALUE);

        restTermMockMvc.perform(put("/api/terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTerm)))
            .andExpect(status().isOk());

        // Validate the Term in the database
        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeUpdate);
        Term testTerm = termList.get(termList.size() - 1);
        assertThat(testTerm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTerm.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTerm.getTermDetail()).isEqualTo(UPDATED_TERM_DETAIL);
        assertThat(testTerm.getTermValue()).isEqualTo(UPDATED_TERM_VALUE);
        assertThat(testTerm.getTermDays()).isEqualTo(UPDATED_TERM_DAYS);
        assertThat(testTerm.getTextValue()).isEqualTo(UPDATED_TEXT_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingTerm() throws Exception {
        int databaseSizeBeforeUpdate = termRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTermMockMvc.perform(put("/api/terms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(term)))
            .andExpect(status().isBadRequest());

        // Validate the Term in the database
        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTerm() throws Exception {
        // Initialize the database
        termRepository.saveAndFlush(term);

        int databaseSizeBeforeDelete = termRepository.findAll().size();

        // Delete the term
        restTermMockMvc.perform(delete("/api/terms/{id}", term.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Term> termList = termRepository.findAll();
        assertThat(termList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
