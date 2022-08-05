package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.StatusCategory;
import com.hr.repository.StatusCategoryRepository;
import com.hr.service.StatusCategoryService;
import com.hr.service.dto.StatusCategoryCriteria;
import com.hr.service.StatusCategoryQueryService;

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
 * Integration tests for the {@link StatusCategoryResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class StatusCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private StatusCategoryRepository statusCategoryRepository;

    @Autowired
    private StatusCategoryService statusCategoryService;

    @Autowired
    private StatusCategoryQueryService statusCategoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStatusCategoryMockMvc;

    private StatusCategory statusCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusCategory createEntity(EntityManager em) {
        StatusCategory statusCategory = new StatusCategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return statusCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StatusCategory createUpdatedEntity(EntityManager em) {
        StatusCategory statusCategory = new StatusCategory()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return statusCategory;
    }

    @BeforeEach
    public void initTest() {
        statusCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createStatusCategory() throws Exception {
        int databaseSizeBeforeCreate = statusCategoryRepository.findAll().size();
        // Create the StatusCategory
        restStatusCategoryMockMvc.perform(post("/api/status-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statusCategory)))
            .andExpect(status().isCreated());

        // Validate the StatusCategory in the database
        List<StatusCategory> statusCategoryList = statusCategoryRepository.findAll();
        assertThat(statusCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        StatusCategory testStatusCategory = statusCategoryList.get(statusCategoryList.size() - 1);
        assertThat(testStatusCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStatusCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createStatusCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = statusCategoryRepository.findAll().size();

        // Create the StatusCategory with an existing ID
        statusCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStatusCategoryMockMvc.perform(post("/api/status-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statusCategory)))
            .andExpect(status().isBadRequest());

        // Validate the StatusCategory in the database
        List<StatusCategory> statusCategoryList = statusCategoryRepository.findAll();
        assertThat(statusCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllStatusCategories() throws Exception {
        // Initialize the database
        statusCategoryRepository.saveAndFlush(statusCategory);

        // Get all the statusCategoryList
        restStatusCategoryMockMvc.perform(get("/api/status-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getStatusCategory() throws Exception {
        // Initialize the database
        statusCategoryRepository.saveAndFlush(statusCategory);

        // Get the statusCategory
        restStatusCategoryMockMvc.perform(get("/api/status-categories/{id}", statusCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(statusCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }


    @Test
    @Transactional
    public void getStatusCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        statusCategoryRepository.saveAndFlush(statusCategory);

        Long id = statusCategory.getId();

        defaultStatusCategoryShouldBeFound("id.equals=" + id);
        defaultStatusCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultStatusCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStatusCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultStatusCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStatusCategoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllStatusCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        statusCategoryRepository.saveAndFlush(statusCategory);

        // Get all the statusCategoryList where name equals to DEFAULT_NAME
        defaultStatusCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the statusCategoryList where name equals to UPDATED_NAME
        defaultStatusCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statusCategoryRepository.saveAndFlush(statusCategory);

        // Get all the statusCategoryList where name not equals to DEFAULT_NAME
        defaultStatusCategoryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the statusCategoryList where name not equals to UPDATED_NAME
        defaultStatusCategoryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        statusCategoryRepository.saveAndFlush(statusCategory);

        // Get all the statusCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStatusCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the statusCategoryList where name equals to UPDATED_NAME
        defaultStatusCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusCategoryRepository.saveAndFlush(statusCategory);

        // Get all the statusCategoryList where name is not null
        defaultStatusCategoryShouldBeFound("name.specified=true");

        // Get all the statusCategoryList where name is null
        defaultStatusCategoryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllStatusCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        statusCategoryRepository.saveAndFlush(statusCategory);

        // Get all the statusCategoryList where name contains DEFAULT_NAME
        defaultStatusCategoryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the statusCategoryList where name contains UPDATED_NAME
        defaultStatusCategoryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllStatusCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        statusCategoryRepository.saveAndFlush(statusCategory);

        // Get all the statusCategoryList where name does not contain DEFAULT_NAME
        defaultStatusCategoryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the statusCategoryList where name does not contain UPDATED_NAME
        defaultStatusCategoryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllStatusCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        statusCategoryRepository.saveAndFlush(statusCategory);

        // Get all the statusCategoryList where description equals to DEFAULT_DESCRIPTION
        defaultStatusCategoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the statusCategoryList where description equals to UPDATED_DESCRIPTION
        defaultStatusCategoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllStatusCategoriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        statusCategoryRepository.saveAndFlush(statusCategory);

        // Get all the statusCategoryList where description not equals to DEFAULT_DESCRIPTION
        defaultStatusCategoryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the statusCategoryList where description not equals to UPDATED_DESCRIPTION
        defaultStatusCategoryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllStatusCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        statusCategoryRepository.saveAndFlush(statusCategory);

        // Get all the statusCategoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultStatusCategoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the statusCategoryList where description equals to UPDATED_DESCRIPTION
        defaultStatusCategoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllStatusCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        statusCategoryRepository.saveAndFlush(statusCategory);

        // Get all the statusCategoryList where description is not null
        defaultStatusCategoryShouldBeFound("description.specified=true");

        // Get all the statusCategoryList where description is null
        defaultStatusCategoryShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllStatusCategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        statusCategoryRepository.saveAndFlush(statusCategory);

        // Get all the statusCategoryList where description contains DEFAULT_DESCRIPTION
        defaultStatusCategoryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the statusCategoryList where description contains UPDATED_DESCRIPTION
        defaultStatusCategoryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllStatusCategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        statusCategoryRepository.saveAndFlush(statusCategory);

        // Get all the statusCategoryList where description does not contain DEFAULT_DESCRIPTION
        defaultStatusCategoryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the statusCategoryList where description does not contain UPDATED_DESCRIPTION
        defaultStatusCategoryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStatusCategoryShouldBeFound(String filter) throws Exception {
        restStatusCategoryMockMvc.perform(get("/api/status-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(statusCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restStatusCategoryMockMvc.perform(get("/api/status-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStatusCategoryShouldNotBeFound(String filter) throws Exception {
        restStatusCategoryMockMvc.perform(get("/api/status-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStatusCategoryMockMvc.perform(get("/api/status-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingStatusCategory() throws Exception {
        // Get the statusCategory
        restStatusCategoryMockMvc.perform(get("/api/status-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStatusCategory() throws Exception {
        // Initialize the database
        statusCategoryService.save(statusCategory);

        int databaseSizeBeforeUpdate = statusCategoryRepository.findAll().size();

        // Update the statusCategory
        StatusCategory updatedStatusCategory = statusCategoryRepository.findById(statusCategory.getId()).get();
        // Disconnect from session so that the updates on updatedStatusCategory are not directly saved in db
        em.detach(updatedStatusCategory);
        updatedStatusCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restStatusCategoryMockMvc.perform(put("/api/status-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedStatusCategory)))
            .andExpect(status().isOk());

        // Validate the StatusCategory in the database
        List<StatusCategory> statusCategoryList = statusCategoryRepository.findAll();
        assertThat(statusCategoryList).hasSize(databaseSizeBeforeUpdate);
        StatusCategory testStatusCategory = statusCategoryList.get(statusCategoryList.size() - 1);
        assertThat(testStatusCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStatusCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingStatusCategory() throws Exception {
        int databaseSizeBeforeUpdate = statusCategoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStatusCategoryMockMvc.perform(put("/api/status-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(statusCategory)))
            .andExpect(status().isBadRequest());

        // Validate the StatusCategory in the database
        List<StatusCategory> statusCategoryList = statusCategoryRepository.findAll();
        assertThat(statusCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStatusCategory() throws Exception {
        // Initialize the database
        statusCategoryService.save(statusCategory);

        int databaseSizeBeforeDelete = statusCategoryRepository.findAll().size();

        // Delete the statusCategory
        restStatusCategoryMockMvc.perform(delete("/api/status-categories/{id}", statusCategory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StatusCategory> statusCategoryList = statusCategoryRepository.findAll();
        assertThat(statusCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
