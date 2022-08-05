package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.EmplPositionFulfillment;
import com.hr.repository.EmplPositionFulfillmentRepository;

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
 * Integration tests for the {@link EmplPositionFulfillmentResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmplPositionFulfillmentResourceIT {

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_THRU_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THRU_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private EmplPositionFulfillmentRepository emplPositionFulfillmentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmplPositionFulfillmentMockMvc;

    private EmplPositionFulfillment emplPositionFulfillment;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplPositionFulfillment createEntity(EntityManager em) {
        EmplPositionFulfillment emplPositionFulfillment = new EmplPositionFulfillment()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .comments(DEFAULT_COMMENTS);
        return emplPositionFulfillment;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplPositionFulfillment createUpdatedEntity(EntityManager em) {
        EmplPositionFulfillment emplPositionFulfillment = new EmplPositionFulfillment()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .comments(UPDATED_COMMENTS);
        return emplPositionFulfillment;
    }

    @BeforeEach
    public void initTest() {
        emplPositionFulfillment = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmplPositionFulfillment() throws Exception {
        int databaseSizeBeforeCreate = emplPositionFulfillmentRepository.findAll().size();
        // Create the EmplPositionFulfillment
        restEmplPositionFulfillmentMockMvc.perform(post("/api/empl-position-fulfillments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPositionFulfillment)))
            .andExpect(status().isCreated());

        // Validate the EmplPositionFulfillment in the database
        List<EmplPositionFulfillment> emplPositionFulfillmentList = emplPositionFulfillmentRepository.findAll();
        assertThat(emplPositionFulfillmentList).hasSize(databaseSizeBeforeCreate + 1);
        EmplPositionFulfillment testEmplPositionFulfillment = emplPositionFulfillmentList.get(emplPositionFulfillmentList.size() - 1);
        assertThat(testEmplPositionFulfillment.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testEmplPositionFulfillment.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testEmplPositionFulfillment.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createEmplPositionFulfillmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emplPositionFulfillmentRepository.findAll().size();

        // Create the EmplPositionFulfillment with an existing ID
        emplPositionFulfillment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmplPositionFulfillmentMockMvc.perform(post("/api/empl-position-fulfillments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPositionFulfillment)))
            .andExpect(status().isBadRequest());

        // Validate the EmplPositionFulfillment in the database
        List<EmplPositionFulfillment> emplPositionFulfillmentList = emplPositionFulfillmentRepository.findAll();
        assertThat(emplPositionFulfillmentList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmplPositionFulfillments() throws Exception {
        // Initialize the database
        emplPositionFulfillmentRepository.saveAndFlush(emplPositionFulfillment);

        // Get all the emplPositionFulfillmentList
        restEmplPositionFulfillmentMockMvc.perform(get("/api/empl-position-fulfillments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emplPositionFulfillment.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
    
    @Test
    @Transactional
    public void getEmplPositionFulfillment() throws Exception {
        // Initialize the database
        emplPositionFulfillmentRepository.saveAndFlush(emplPositionFulfillment);

        // Get the emplPositionFulfillment
        restEmplPositionFulfillmentMockMvc.perform(get("/api/empl-position-fulfillments/{id}", emplPositionFulfillment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emplPositionFulfillment.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }
    @Test
    @Transactional
    public void getNonExistingEmplPositionFulfillment() throws Exception {
        // Get the emplPositionFulfillment
        restEmplPositionFulfillmentMockMvc.perform(get("/api/empl-position-fulfillments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmplPositionFulfillment() throws Exception {
        // Initialize the database
        emplPositionFulfillmentRepository.saveAndFlush(emplPositionFulfillment);

        int databaseSizeBeforeUpdate = emplPositionFulfillmentRepository.findAll().size();

        // Update the emplPositionFulfillment
        EmplPositionFulfillment updatedEmplPositionFulfillment = emplPositionFulfillmentRepository.findById(emplPositionFulfillment.getId()).get();
        // Disconnect from session so that the updates on updatedEmplPositionFulfillment are not directly saved in db
        em.detach(updatedEmplPositionFulfillment);
        updatedEmplPositionFulfillment
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .comments(UPDATED_COMMENTS);

        restEmplPositionFulfillmentMockMvc.perform(put("/api/empl-position-fulfillments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmplPositionFulfillment)))
            .andExpect(status().isOk());

        // Validate the EmplPositionFulfillment in the database
        List<EmplPositionFulfillment> emplPositionFulfillmentList = emplPositionFulfillmentRepository.findAll();
        assertThat(emplPositionFulfillmentList).hasSize(databaseSizeBeforeUpdate);
        EmplPositionFulfillment testEmplPositionFulfillment = emplPositionFulfillmentList.get(emplPositionFulfillmentList.size() - 1);
        assertThat(testEmplPositionFulfillment.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testEmplPositionFulfillment.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testEmplPositionFulfillment.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingEmplPositionFulfillment() throws Exception {
        int databaseSizeBeforeUpdate = emplPositionFulfillmentRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmplPositionFulfillmentMockMvc.perform(put("/api/empl-position-fulfillments")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPositionFulfillment)))
            .andExpect(status().isBadRequest());

        // Validate the EmplPositionFulfillment in the database
        List<EmplPositionFulfillment> emplPositionFulfillmentList = emplPositionFulfillmentRepository.findAll();
        assertThat(emplPositionFulfillmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmplPositionFulfillment() throws Exception {
        // Initialize the database
        emplPositionFulfillmentRepository.saveAndFlush(emplPositionFulfillment);

        int databaseSizeBeforeDelete = emplPositionFulfillmentRepository.findAll().size();

        // Delete the emplPositionFulfillment
        restEmplPositionFulfillmentMockMvc.perform(delete("/api/empl-position-fulfillments/{id}", emplPositionFulfillment.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmplPositionFulfillment> emplPositionFulfillmentList = emplPositionFulfillmentRepository.findAll();
        assertThat(emplPositionFulfillmentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
