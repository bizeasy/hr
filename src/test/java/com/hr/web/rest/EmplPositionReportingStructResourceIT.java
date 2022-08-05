package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.EmplPositionReportingStruct;
import com.hr.repository.EmplPositionReportingStructRepository;

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
 * Integration tests for the {@link EmplPositionReportingStructResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmplPositionReportingStructResourceIT {

    private static final LocalDate DEFAULT_FROM_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FROM_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_THRU_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_THRU_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private EmplPositionReportingStructRepository emplPositionReportingStructRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmplPositionReportingStructMockMvc;

    private EmplPositionReportingStruct emplPositionReportingStruct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplPositionReportingStruct createEntity(EntityManager em) {
        EmplPositionReportingStruct emplPositionReportingStruct = new EmplPositionReportingStruct()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .comments(DEFAULT_COMMENTS);
        return emplPositionReportingStruct;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplPositionReportingStruct createUpdatedEntity(EntityManager em) {
        EmplPositionReportingStruct emplPositionReportingStruct = new EmplPositionReportingStruct()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .comments(UPDATED_COMMENTS);
        return emplPositionReportingStruct;
    }

    @BeforeEach
    public void initTest() {
        emplPositionReportingStruct = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmplPositionReportingStruct() throws Exception {
        int databaseSizeBeforeCreate = emplPositionReportingStructRepository.findAll().size();
        // Create the EmplPositionReportingStruct
        restEmplPositionReportingStructMockMvc.perform(post("/api/empl-position-reporting-structs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPositionReportingStruct)))
            .andExpect(status().isCreated());

        // Validate the EmplPositionReportingStruct in the database
        List<EmplPositionReportingStruct> emplPositionReportingStructList = emplPositionReportingStructRepository.findAll();
        assertThat(emplPositionReportingStructList).hasSize(databaseSizeBeforeCreate + 1);
        EmplPositionReportingStruct testEmplPositionReportingStruct = emplPositionReportingStructList.get(emplPositionReportingStructList.size() - 1);
        assertThat(testEmplPositionReportingStruct.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testEmplPositionReportingStruct.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testEmplPositionReportingStruct.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createEmplPositionReportingStructWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emplPositionReportingStructRepository.findAll().size();

        // Create the EmplPositionReportingStruct with an existing ID
        emplPositionReportingStruct.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmplPositionReportingStructMockMvc.perform(post("/api/empl-position-reporting-structs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPositionReportingStruct)))
            .andExpect(status().isBadRequest());

        // Validate the EmplPositionReportingStruct in the database
        List<EmplPositionReportingStruct> emplPositionReportingStructList = emplPositionReportingStructRepository.findAll();
        assertThat(emplPositionReportingStructList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmplPositionReportingStructs() throws Exception {
        // Initialize the database
        emplPositionReportingStructRepository.saveAndFlush(emplPositionReportingStruct);

        // Get all the emplPositionReportingStructList
        restEmplPositionReportingStructMockMvc.perform(get("/api/empl-position-reporting-structs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emplPositionReportingStruct.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
    
    @Test
    @Transactional
    public void getEmplPositionReportingStruct() throws Exception {
        // Initialize the database
        emplPositionReportingStructRepository.saveAndFlush(emplPositionReportingStruct);

        // Get the emplPositionReportingStruct
        restEmplPositionReportingStructMockMvc.perform(get("/api/empl-position-reporting-structs/{id}", emplPositionReportingStruct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emplPositionReportingStruct.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }
    @Test
    @Transactional
    public void getNonExistingEmplPositionReportingStruct() throws Exception {
        // Get the emplPositionReportingStruct
        restEmplPositionReportingStructMockMvc.perform(get("/api/empl-position-reporting-structs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmplPositionReportingStruct() throws Exception {
        // Initialize the database
        emplPositionReportingStructRepository.saveAndFlush(emplPositionReportingStruct);

        int databaseSizeBeforeUpdate = emplPositionReportingStructRepository.findAll().size();

        // Update the emplPositionReportingStruct
        EmplPositionReportingStruct updatedEmplPositionReportingStruct = emplPositionReportingStructRepository.findById(emplPositionReportingStruct.getId()).get();
        // Disconnect from session so that the updates on updatedEmplPositionReportingStruct are not directly saved in db
        em.detach(updatedEmplPositionReportingStruct);
        updatedEmplPositionReportingStruct
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .comments(UPDATED_COMMENTS);

        restEmplPositionReportingStructMockMvc.perform(put("/api/empl-position-reporting-structs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmplPositionReportingStruct)))
            .andExpect(status().isOk());

        // Validate the EmplPositionReportingStruct in the database
        List<EmplPositionReportingStruct> emplPositionReportingStructList = emplPositionReportingStructRepository.findAll();
        assertThat(emplPositionReportingStructList).hasSize(databaseSizeBeforeUpdate);
        EmplPositionReportingStruct testEmplPositionReportingStruct = emplPositionReportingStructList.get(emplPositionReportingStructList.size() - 1);
        assertThat(testEmplPositionReportingStruct.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testEmplPositionReportingStruct.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testEmplPositionReportingStruct.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingEmplPositionReportingStruct() throws Exception {
        int databaseSizeBeforeUpdate = emplPositionReportingStructRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmplPositionReportingStructMockMvc.perform(put("/api/empl-position-reporting-structs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPositionReportingStruct)))
            .andExpect(status().isBadRequest());

        // Validate the EmplPositionReportingStruct in the database
        List<EmplPositionReportingStruct> emplPositionReportingStructList = emplPositionReportingStructRepository.findAll();
        assertThat(emplPositionReportingStructList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmplPositionReportingStruct() throws Exception {
        // Initialize the database
        emplPositionReportingStructRepository.saveAndFlush(emplPositionReportingStruct);

        int databaseSizeBeforeDelete = emplPositionReportingStructRepository.findAll().size();

        // Delete the emplPositionReportingStruct
        restEmplPositionReportingStructMockMvc.perform(delete("/api/empl-position-reporting-structs/{id}", emplPositionReportingStruct.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmplPositionReportingStruct> emplPositionReportingStructList = emplPositionReportingStructRepository.findAll();
        assertThat(emplPositionReportingStructList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
