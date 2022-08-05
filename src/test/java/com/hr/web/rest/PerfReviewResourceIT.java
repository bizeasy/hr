package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PerfReview;
import com.hr.repository.PerfReviewRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PerfReviewResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PerfReviewResourceIT {

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_THRU_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THRU_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private PerfReviewRepository perfReviewRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerfReviewMockMvc;

    private PerfReview perfReview;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfReview createEntity(EntityManager em) {
        PerfReview perfReview = new PerfReview()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .comments(DEFAULT_COMMENTS);
        return perfReview;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfReview createUpdatedEntity(EntityManager em) {
        PerfReview perfReview = new PerfReview()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .comments(UPDATED_COMMENTS);
        return perfReview;
    }

    @BeforeEach
    public void initTest() {
        perfReview = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerfReview() throws Exception {
        int databaseSizeBeforeCreate = perfReviewRepository.findAll().size();
        // Create the PerfReview
        restPerfReviewMockMvc.perform(post("/api/perf-reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfReview)))
            .andExpect(status().isCreated());

        // Validate the PerfReview in the database
        List<PerfReview> perfReviewList = perfReviewRepository.findAll();
        assertThat(perfReviewList).hasSize(databaseSizeBeforeCreate + 1);
        PerfReview testPerfReview = perfReviewList.get(perfReviewList.size() - 1);
        assertThat(testPerfReview.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testPerfReview.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testPerfReview.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createPerfReviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perfReviewRepository.findAll().size();

        // Create the PerfReview with an existing ID
        perfReview.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfReviewMockMvc.perform(post("/api/perf-reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfReview)))
            .andExpect(status().isBadRequest());

        // Validate the PerfReview in the database
        List<PerfReview> perfReviewList = perfReviewRepository.findAll();
        assertThat(perfReviewList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPerfReviews() throws Exception {
        // Initialize the database
        perfReviewRepository.saveAndFlush(perfReview);

        // Get all the perfReviewList
        restPerfReviewMockMvc.perform(get("/api/perf-reviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfReview.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
    
    @Test
    @Transactional
    public void getPerfReview() throws Exception {
        // Initialize the database
        perfReviewRepository.saveAndFlush(perfReview);

        // Get the perfReview
        restPerfReviewMockMvc.perform(get("/api/perf-reviews/{id}", perfReview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(perfReview.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }
    @Test
    @Transactional
    public void getNonExistingPerfReview() throws Exception {
        // Get the perfReview
        restPerfReviewMockMvc.perform(get("/api/perf-reviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerfReview() throws Exception {
        // Initialize the database
        perfReviewRepository.saveAndFlush(perfReview);

        int databaseSizeBeforeUpdate = perfReviewRepository.findAll().size();

        // Update the perfReview
        PerfReview updatedPerfReview = perfReviewRepository.findById(perfReview.getId()).get();
        // Disconnect from session so that the updates on updatedPerfReview are not directly saved in db
        em.detach(updatedPerfReview);
        updatedPerfReview
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .comments(UPDATED_COMMENTS);

        restPerfReviewMockMvc.perform(put("/api/perf-reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerfReview)))
            .andExpect(status().isOk());

        // Validate the PerfReview in the database
        List<PerfReview> perfReviewList = perfReviewRepository.findAll();
        assertThat(perfReviewList).hasSize(databaseSizeBeforeUpdate);
        PerfReview testPerfReview = perfReviewList.get(perfReviewList.size() - 1);
        assertThat(testPerfReview.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testPerfReview.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testPerfReview.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingPerfReview() throws Exception {
        int databaseSizeBeforeUpdate = perfReviewRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfReviewMockMvc.perform(put("/api/perf-reviews")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfReview)))
            .andExpect(status().isBadRequest());

        // Validate the PerfReview in the database
        List<PerfReview> perfReviewList = perfReviewRepository.findAll();
        assertThat(perfReviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePerfReview() throws Exception {
        // Initialize the database
        perfReviewRepository.saveAndFlush(perfReview);

        int databaseSizeBeforeDelete = perfReviewRepository.findAll().size();

        // Delete the perfReview
        restPerfReviewMockMvc.perform(delete("/api/perf-reviews/{id}", perfReview.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PerfReview> perfReviewList = perfReviewRepository.findAll();
        assertThat(perfReviewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
