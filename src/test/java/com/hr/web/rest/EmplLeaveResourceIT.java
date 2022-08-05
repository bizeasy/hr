package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.EmplLeave;
import com.hr.repository.EmplLeaveRepository;

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
 * Integration tests for the {@link EmplLeaveResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmplLeaveResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_THRU_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THRU_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private EmplLeaveRepository emplLeaveRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmplLeaveMockMvc;

    private EmplLeave emplLeave;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplLeave createEntity(EntityManager em) {
        EmplLeave emplLeave = new EmplLeave()
            .description(DEFAULT_DESCRIPTION)
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE);
        return emplLeave;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplLeave createUpdatedEntity(EntityManager em) {
        EmplLeave emplLeave = new EmplLeave()
            .description(UPDATED_DESCRIPTION)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);
        return emplLeave;
    }

    @BeforeEach
    public void initTest() {
        emplLeave = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmplLeave() throws Exception {
        int databaseSizeBeforeCreate = emplLeaveRepository.findAll().size();
        // Create the EmplLeave
        restEmplLeaveMockMvc.perform(post("/api/empl-leaves")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplLeave)))
            .andExpect(status().isCreated());

        // Validate the EmplLeave in the database
        List<EmplLeave> emplLeaveList = emplLeaveRepository.findAll();
        assertThat(emplLeaveList).hasSize(databaseSizeBeforeCreate + 1);
        EmplLeave testEmplLeave = emplLeaveList.get(emplLeaveList.size() - 1);
        assertThat(testEmplLeave.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testEmplLeave.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testEmplLeave.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
    }

    @Test
    @Transactional
    public void createEmplLeaveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emplLeaveRepository.findAll().size();

        // Create the EmplLeave with an existing ID
        emplLeave.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmplLeaveMockMvc.perform(post("/api/empl-leaves")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplLeave)))
            .andExpect(status().isBadRequest());

        // Validate the EmplLeave in the database
        List<EmplLeave> emplLeaveList = emplLeaveRepository.findAll();
        assertThat(emplLeaveList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmplLeaves() throws Exception {
        // Initialize the database
        emplLeaveRepository.saveAndFlush(emplLeave);

        // Get all the emplLeaveList
        restEmplLeaveMockMvc.perform(get("/api/empl-leaves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emplLeave.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getEmplLeave() throws Exception {
        // Initialize the database
        emplLeaveRepository.saveAndFlush(emplLeave);

        // Get the emplLeave
        restEmplLeaveMockMvc.perform(get("/api/empl-leaves/{id}", emplLeave.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emplLeave.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingEmplLeave() throws Exception {
        // Get the emplLeave
        restEmplLeaveMockMvc.perform(get("/api/empl-leaves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmplLeave() throws Exception {
        // Initialize the database
        emplLeaveRepository.saveAndFlush(emplLeave);

        int databaseSizeBeforeUpdate = emplLeaveRepository.findAll().size();

        // Update the emplLeave
        EmplLeave updatedEmplLeave = emplLeaveRepository.findById(emplLeave.getId()).get();
        // Disconnect from session so that the updates on updatedEmplLeave are not directly saved in db
        em.detach(updatedEmplLeave);
        updatedEmplLeave
            .description(UPDATED_DESCRIPTION)
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);

        restEmplLeaveMockMvc.perform(put("/api/empl-leaves")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmplLeave)))
            .andExpect(status().isOk());

        // Validate the EmplLeave in the database
        List<EmplLeave> emplLeaveList = emplLeaveRepository.findAll();
        assertThat(emplLeaveList).hasSize(databaseSizeBeforeUpdate);
        EmplLeave testEmplLeave = emplLeaveList.get(emplLeaveList.size() - 1);
        assertThat(testEmplLeave.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testEmplLeave.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testEmplLeave.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingEmplLeave() throws Exception {
        int databaseSizeBeforeUpdate = emplLeaveRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmplLeaveMockMvc.perform(put("/api/empl-leaves")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplLeave)))
            .andExpect(status().isBadRequest());

        // Validate the EmplLeave in the database
        List<EmplLeave> emplLeaveList = emplLeaveRepository.findAll();
        assertThat(emplLeaveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmplLeave() throws Exception {
        // Initialize the database
        emplLeaveRepository.saveAndFlush(emplLeave);

        int databaseSizeBeforeDelete = emplLeaveRepository.findAll().size();

        // Delete the emplLeave
        restEmplLeaveMockMvc.perform(delete("/api/empl-leaves/{id}", emplLeave.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmplLeave> emplLeaveList = emplLeaveRepository.findAll();
        assertThat(emplLeaveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
