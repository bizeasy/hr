package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.CatalogueCategoryType;
import com.hr.repository.CatalogueCategoryTypeRepository;

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
 * Integration tests for the {@link CatalogueCategoryTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatalogueCategoryTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private CatalogueCategoryTypeRepository catalogueCategoryTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatalogueCategoryTypeMockMvc;

    private CatalogueCategoryType catalogueCategoryType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatalogueCategoryType createEntity(EntityManager em) {
        CatalogueCategoryType catalogueCategoryType = new CatalogueCategoryType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return catalogueCategoryType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatalogueCategoryType createUpdatedEntity(EntityManager em) {
        CatalogueCategoryType catalogueCategoryType = new CatalogueCategoryType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return catalogueCategoryType;
    }

    @BeforeEach
    public void initTest() {
        catalogueCategoryType = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatalogueCategoryType() throws Exception {
        int databaseSizeBeforeCreate = catalogueCategoryTypeRepository.findAll().size();
        // Create the CatalogueCategoryType
        restCatalogueCategoryTypeMockMvc.perform(post("/api/catalogue-category-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogueCategoryType)))
            .andExpect(status().isCreated());

        // Validate the CatalogueCategoryType in the database
        List<CatalogueCategoryType> catalogueCategoryTypeList = catalogueCategoryTypeRepository.findAll();
        assertThat(catalogueCategoryTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CatalogueCategoryType testCatalogueCategoryType = catalogueCategoryTypeList.get(catalogueCategoryTypeList.size() - 1);
        assertThat(testCatalogueCategoryType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCatalogueCategoryType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createCatalogueCategoryTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catalogueCategoryTypeRepository.findAll().size();

        // Create the CatalogueCategoryType with an existing ID
        catalogueCategoryType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatalogueCategoryTypeMockMvc.perform(post("/api/catalogue-category-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogueCategoryType)))
            .andExpect(status().isBadRequest());

        // Validate the CatalogueCategoryType in the database
        List<CatalogueCategoryType> catalogueCategoryTypeList = catalogueCategoryTypeRepository.findAll();
        assertThat(catalogueCategoryTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCatalogueCategoryTypes() throws Exception {
        // Initialize the database
        catalogueCategoryTypeRepository.saveAndFlush(catalogueCategoryType);

        // Get all the catalogueCategoryTypeList
        restCatalogueCategoryTypeMockMvc.perform(get("/api/catalogue-category-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catalogueCategoryType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getCatalogueCategoryType() throws Exception {
        // Initialize the database
        catalogueCategoryTypeRepository.saveAndFlush(catalogueCategoryType);

        // Get the catalogueCategoryType
        restCatalogueCategoryTypeMockMvc.perform(get("/api/catalogue-category-types/{id}", catalogueCategoryType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catalogueCategoryType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingCatalogueCategoryType() throws Exception {
        // Get the catalogueCategoryType
        restCatalogueCategoryTypeMockMvc.perform(get("/api/catalogue-category-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatalogueCategoryType() throws Exception {
        // Initialize the database
        catalogueCategoryTypeRepository.saveAndFlush(catalogueCategoryType);

        int databaseSizeBeforeUpdate = catalogueCategoryTypeRepository.findAll().size();

        // Update the catalogueCategoryType
        CatalogueCategoryType updatedCatalogueCategoryType = catalogueCategoryTypeRepository.findById(catalogueCategoryType.getId()).get();
        // Disconnect from session so that the updates on updatedCatalogueCategoryType are not directly saved in db
        em.detach(updatedCatalogueCategoryType);
        updatedCatalogueCategoryType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restCatalogueCategoryTypeMockMvc.perform(put("/api/catalogue-category-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatalogueCategoryType)))
            .andExpect(status().isOk());

        // Validate the CatalogueCategoryType in the database
        List<CatalogueCategoryType> catalogueCategoryTypeList = catalogueCategoryTypeRepository.findAll();
        assertThat(catalogueCategoryTypeList).hasSize(databaseSizeBeforeUpdate);
        CatalogueCategoryType testCatalogueCategoryType = catalogueCategoryTypeList.get(catalogueCategoryTypeList.size() - 1);
        assertThat(testCatalogueCategoryType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCatalogueCategoryType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingCatalogueCategoryType() throws Exception {
        int databaseSizeBeforeUpdate = catalogueCategoryTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatalogueCategoryTypeMockMvc.perform(put("/api/catalogue-category-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogueCategoryType)))
            .andExpect(status().isBadRequest());

        // Validate the CatalogueCategoryType in the database
        List<CatalogueCategoryType> catalogueCategoryTypeList = catalogueCategoryTypeRepository.findAll();
        assertThat(catalogueCategoryTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatalogueCategoryType() throws Exception {
        // Initialize the database
        catalogueCategoryTypeRepository.saveAndFlush(catalogueCategoryType);

        int databaseSizeBeforeDelete = catalogueCategoryTypeRepository.findAll().size();

        // Delete the catalogueCategoryType
        restCatalogueCategoryTypeMockMvc.perform(delete("/api/catalogue-category-types/{id}", catalogueCategoryType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatalogueCategoryType> catalogueCategoryTypeList = catalogueCategoryTypeRepository.findAll();
        assertThat(catalogueCategoryTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
