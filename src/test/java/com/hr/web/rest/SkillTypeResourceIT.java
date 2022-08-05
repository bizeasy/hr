package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.SkillType;
import com.hr.repository.SkillTypeRepository;

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
 * Integration tests for the {@link SkillTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SkillTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private SkillTypeRepository skillTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSkillTypeMockMvc;

    private SkillType skillType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillType createEntity(EntityManager em) {
        SkillType skillType = new SkillType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return skillType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillType createUpdatedEntity(EntityManager em) {
        SkillType skillType = new SkillType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return skillType;
    }

    @BeforeEach
    public void initTest() {
        skillType = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkillType() throws Exception {
        int databaseSizeBeforeCreate = skillTypeRepository.findAll().size();
        // Create the SkillType
        restSkillTypeMockMvc.perform(post("/api/skill-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(skillType)))
            .andExpect(status().isCreated());

        // Validate the SkillType in the database
        List<SkillType> skillTypeList = skillTypeRepository.findAll();
        assertThat(skillTypeList).hasSize(databaseSizeBeforeCreate + 1);
        SkillType testSkillType = skillTypeList.get(skillTypeList.size() - 1);
        assertThat(testSkillType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSkillType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createSkillTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillTypeRepository.findAll().size();

        // Create the SkillType with an existing ID
        skillType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillTypeMockMvc.perform(post("/api/skill-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(skillType)))
            .andExpect(status().isBadRequest());

        // Validate the SkillType in the database
        List<SkillType> skillTypeList = skillTypeRepository.findAll();
        assertThat(skillTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSkillTypes() throws Exception {
        // Initialize the database
        skillTypeRepository.saveAndFlush(skillType);

        // Get all the skillTypeList
        restSkillTypeMockMvc.perform(get("/api/skill-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getSkillType() throws Exception {
        // Initialize the database
        skillTypeRepository.saveAndFlush(skillType);

        // Get the skillType
        restSkillTypeMockMvc.perform(get("/api/skill-types/{id}", skillType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(skillType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingSkillType() throws Exception {
        // Get the skillType
        restSkillTypeMockMvc.perform(get("/api/skill-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkillType() throws Exception {
        // Initialize the database
        skillTypeRepository.saveAndFlush(skillType);

        int databaseSizeBeforeUpdate = skillTypeRepository.findAll().size();

        // Update the skillType
        SkillType updatedSkillType = skillTypeRepository.findById(skillType.getId()).get();
        // Disconnect from session so that the updates on updatedSkillType are not directly saved in db
        em.detach(updatedSkillType);
        updatedSkillType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restSkillTypeMockMvc.perform(put("/api/skill-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSkillType)))
            .andExpect(status().isOk());

        // Validate the SkillType in the database
        List<SkillType> skillTypeList = skillTypeRepository.findAll();
        assertThat(skillTypeList).hasSize(databaseSizeBeforeUpdate);
        SkillType testSkillType = skillTypeList.get(skillTypeList.size() - 1);
        assertThat(testSkillType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSkillType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingSkillType() throws Exception {
        int databaseSizeBeforeUpdate = skillTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillTypeMockMvc.perform(put("/api/skill-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(skillType)))
            .andExpect(status().isBadRequest());

        // Validate the SkillType in the database
        List<SkillType> skillTypeList = skillTypeRepository.findAll();
        assertThat(skillTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSkillType() throws Exception {
        // Initialize the database
        skillTypeRepository.saveAndFlush(skillType);

        int databaseSizeBeforeDelete = skillTypeRepository.findAll().size();

        // Delete the skillType
        restSkillTypeMockMvc.perform(delete("/api/skill-types/{id}", skillType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SkillType> skillTypeList = skillTypeRepository.findAll();
        assertThat(skillTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
