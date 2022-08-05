package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.EmplPositionGroup;
import com.hr.repository.EmplPositionGroupRepository;

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
 * Integration tests for the {@link EmplPositionGroupResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmplPositionGroupResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private EmplPositionGroupRepository emplPositionGroupRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmplPositionGroupMockMvc;

    private EmplPositionGroup emplPositionGroup;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplPositionGroup createEntity(EntityManager em) {
        EmplPositionGroup emplPositionGroup = new EmplPositionGroup()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return emplPositionGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmplPositionGroup createUpdatedEntity(EntityManager em) {
        EmplPositionGroup emplPositionGroup = new EmplPositionGroup()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return emplPositionGroup;
    }

    @BeforeEach
    public void initTest() {
        emplPositionGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmplPositionGroup() throws Exception {
        int databaseSizeBeforeCreate = emplPositionGroupRepository.findAll().size();
        // Create the EmplPositionGroup
        restEmplPositionGroupMockMvc.perform(post("/api/empl-position-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPositionGroup)))
            .andExpect(status().isCreated());

        // Validate the EmplPositionGroup in the database
        List<EmplPositionGroup> emplPositionGroupList = emplPositionGroupRepository.findAll();
        assertThat(emplPositionGroupList).hasSize(databaseSizeBeforeCreate + 1);
        EmplPositionGroup testEmplPositionGroup = emplPositionGroupList.get(emplPositionGroupList.size() - 1);
        assertThat(testEmplPositionGroup.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEmplPositionGroup.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createEmplPositionGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = emplPositionGroupRepository.findAll().size();

        // Create the EmplPositionGroup with an existing ID
        emplPositionGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmplPositionGroupMockMvc.perform(post("/api/empl-position-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPositionGroup)))
            .andExpect(status().isBadRequest());

        // Validate the EmplPositionGroup in the database
        List<EmplPositionGroup> emplPositionGroupList = emplPositionGroupRepository.findAll();
        assertThat(emplPositionGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmplPositionGroups() throws Exception {
        // Initialize the database
        emplPositionGroupRepository.saveAndFlush(emplPositionGroup);

        // Get all the emplPositionGroupList
        restEmplPositionGroupMockMvc.perform(get("/api/empl-position-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(emplPositionGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getEmplPositionGroup() throws Exception {
        // Initialize the database
        emplPositionGroupRepository.saveAndFlush(emplPositionGroup);

        // Get the emplPositionGroup
        restEmplPositionGroupMockMvc.perform(get("/api/empl-position-groups/{id}", emplPositionGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(emplPositionGroup.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingEmplPositionGroup() throws Exception {
        // Get the emplPositionGroup
        restEmplPositionGroupMockMvc.perform(get("/api/empl-position-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmplPositionGroup() throws Exception {
        // Initialize the database
        emplPositionGroupRepository.saveAndFlush(emplPositionGroup);

        int databaseSizeBeforeUpdate = emplPositionGroupRepository.findAll().size();

        // Update the emplPositionGroup
        EmplPositionGroup updatedEmplPositionGroup = emplPositionGroupRepository.findById(emplPositionGroup.getId()).get();
        // Disconnect from session so that the updates on updatedEmplPositionGroup are not directly saved in db
        em.detach(updatedEmplPositionGroup);
        updatedEmplPositionGroup
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restEmplPositionGroupMockMvc.perform(put("/api/empl-position-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmplPositionGroup)))
            .andExpect(status().isOk());

        // Validate the EmplPositionGroup in the database
        List<EmplPositionGroup> emplPositionGroupList = emplPositionGroupRepository.findAll();
        assertThat(emplPositionGroupList).hasSize(databaseSizeBeforeUpdate);
        EmplPositionGroup testEmplPositionGroup = emplPositionGroupList.get(emplPositionGroupList.size() - 1);
        assertThat(testEmplPositionGroup.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEmplPositionGroup.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingEmplPositionGroup() throws Exception {
        int databaseSizeBeforeUpdate = emplPositionGroupRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmplPositionGroupMockMvc.perform(put("/api/empl-position-groups")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(emplPositionGroup)))
            .andExpect(status().isBadRequest());

        // Validate the EmplPositionGroup in the database
        List<EmplPositionGroup> emplPositionGroupList = emplPositionGroupRepository.findAll();
        assertThat(emplPositionGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmplPositionGroup() throws Exception {
        // Initialize the database
        emplPositionGroupRepository.saveAndFlush(emplPositionGroup);

        int databaseSizeBeforeDelete = emplPositionGroupRepository.findAll().size();

        // Delete the emplPositionGroup
        restEmplPositionGroupMockMvc.perform(delete("/api/empl-position-groups/{id}", emplPositionGroup.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmplPositionGroup> emplPositionGroupList = emplPositionGroupRepository.findAll();
        assertThat(emplPositionGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
