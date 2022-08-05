package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.PerfReviewItem;
import com.hr.repository.PerfReviewItemRepository;

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
 * Integration tests for the {@link PerfReviewItemResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PerfReviewItemResourceIT {

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private PerfReviewItemRepository perfReviewItemRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPerfReviewItemMockMvc;

    private PerfReviewItem perfReviewItem;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfReviewItem createEntity(EntityManager em) {
        PerfReviewItem perfReviewItem = new PerfReviewItem()
            .sequenceNo(DEFAULT_SEQUENCE_NO)
            .comments(DEFAULT_COMMENTS);
        return perfReviewItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PerfReviewItem createUpdatedEntity(EntityManager em) {
        PerfReviewItem perfReviewItem = new PerfReviewItem()
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .comments(UPDATED_COMMENTS);
        return perfReviewItem;
    }

    @BeforeEach
    public void initTest() {
        perfReviewItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createPerfReviewItem() throws Exception {
        int databaseSizeBeforeCreate = perfReviewItemRepository.findAll().size();
        // Create the PerfReviewItem
        restPerfReviewItemMockMvc.perform(post("/api/perf-review-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfReviewItem)))
            .andExpect(status().isCreated());

        // Validate the PerfReviewItem in the database
        List<PerfReviewItem> perfReviewItemList = perfReviewItemRepository.findAll();
        assertThat(perfReviewItemList).hasSize(databaseSizeBeforeCreate + 1);
        PerfReviewItem testPerfReviewItem = perfReviewItemList.get(perfReviewItemList.size() - 1);
        assertThat(testPerfReviewItem.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
        assertThat(testPerfReviewItem.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createPerfReviewItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = perfReviewItemRepository.findAll().size();

        // Create the PerfReviewItem with an existing ID
        perfReviewItem.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPerfReviewItemMockMvc.perform(post("/api/perf-review-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfReviewItem)))
            .andExpect(status().isBadRequest());

        // Validate the PerfReviewItem in the database
        List<PerfReviewItem> perfReviewItemList = perfReviewItemRepository.findAll();
        assertThat(perfReviewItemList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPerfReviewItems() throws Exception {
        // Initialize the database
        perfReviewItemRepository.saveAndFlush(perfReviewItem);

        // Get all the perfReviewItemList
        restPerfReviewItemMockMvc.perform(get("/api/perf-review-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(perfReviewItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
    
    @Test
    @Transactional
    public void getPerfReviewItem() throws Exception {
        // Initialize the database
        perfReviewItemRepository.saveAndFlush(perfReviewItem);

        // Get the perfReviewItem
        restPerfReviewItemMockMvc.perform(get("/api/perf-review-items/{id}", perfReviewItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(perfReviewItem.getId().intValue()))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }
    @Test
    @Transactional
    public void getNonExistingPerfReviewItem() throws Exception {
        // Get the perfReviewItem
        restPerfReviewItemMockMvc.perform(get("/api/perf-review-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePerfReviewItem() throws Exception {
        // Initialize the database
        perfReviewItemRepository.saveAndFlush(perfReviewItem);

        int databaseSizeBeforeUpdate = perfReviewItemRepository.findAll().size();

        // Update the perfReviewItem
        PerfReviewItem updatedPerfReviewItem = perfReviewItemRepository.findById(perfReviewItem.getId()).get();
        // Disconnect from session so that the updates on updatedPerfReviewItem are not directly saved in db
        em.detach(updatedPerfReviewItem);
        updatedPerfReviewItem
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .comments(UPDATED_COMMENTS);

        restPerfReviewItemMockMvc.perform(put("/api/perf-review-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPerfReviewItem)))
            .andExpect(status().isOk());

        // Validate the PerfReviewItem in the database
        List<PerfReviewItem> perfReviewItemList = perfReviewItemRepository.findAll();
        assertThat(perfReviewItemList).hasSize(databaseSizeBeforeUpdate);
        PerfReviewItem testPerfReviewItem = perfReviewItemList.get(perfReviewItemList.size() - 1);
        assertThat(testPerfReviewItem.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
        assertThat(testPerfReviewItem.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingPerfReviewItem() throws Exception {
        int databaseSizeBeforeUpdate = perfReviewItemRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPerfReviewItemMockMvc.perform(put("/api/perf-review-items")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(perfReviewItem)))
            .andExpect(status().isBadRequest());

        // Validate the PerfReviewItem in the database
        List<PerfReviewItem> perfReviewItemList = perfReviewItemRepository.findAll();
        assertThat(perfReviewItemList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePerfReviewItem() throws Exception {
        // Initialize the database
        perfReviewItemRepository.saveAndFlush(perfReviewItem);

        int databaseSizeBeforeDelete = perfReviewItemRepository.findAll().size();

        // Delete the perfReviewItem
        restPerfReviewItemMockMvc.perform(delete("/api/perf-review-items/{id}", perfReviewItem.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PerfReviewItem> perfReviewItemList = perfReviewItemRepository.findAll();
        assertThat(perfReviewItemList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
