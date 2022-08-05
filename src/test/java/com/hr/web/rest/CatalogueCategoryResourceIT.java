package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.CatalogueCategory;
import com.hr.domain.Catalogue;
import com.hr.domain.ProductCategory;
import com.hr.domain.CatalogueCategoryType;
import com.hr.repository.CatalogueCategoryRepository;
import com.hr.service.CatalogueCategoryService;
import com.hr.service.dto.CatalogueCategoryCriteria;
import com.hr.service.CatalogueCategoryQueryService;

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
 * Integration tests for the {@link CatalogueCategoryResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatalogueCategoryResourceIT {

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;
    private static final Integer SMALLER_SEQUENCE_NO = 1 - 1;

    @Autowired
    private CatalogueCategoryRepository catalogueCategoryRepository;

    @Autowired
    private CatalogueCategoryService catalogueCategoryService;

    @Autowired
    private CatalogueCategoryQueryService catalogueCategoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatalogueCategoryMockMvc;

    private CatalogueCategory catalogueCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatalogueCategory createEntity(EntityManager em) {
        CatalogueCategory catalogueCategory = new CatalogueCategory()
            .sequenceNo(DEFAULT_SEQUENCE_NO);
        return catalogueCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CatalogueCategory createUpdatedEntity(EntityManager em) {
        CatalogueCategory catalogueCategory = new CatalogueCategory()
            .sequenceNo(UPDATED_SEQUENCE_NO);
        return catalogueCategory;
    }

    @BeforeEach
    public void initTest() {
        catalogueCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatalogueCategory() throws Exception {
        int databaseSizeBeforeCreate = catalogueCategoryRepository.findAll().size();
        // Create the CatalogueCategory
        restCatalogueCategoryMockMvc.perform(post("/api/catalogue-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogueCategory)))
            .andExpect(status().isCreated());

        // Validate the CatalogueCategory in the database
        List<CatalogueCategory> catalogueCategoryList = catalogueCategoryRepository.findAll();
        assertThat(catalogueCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        CatalogueCategory testCatalogueCategory = catalogueCategoryList.get(catalogueCategoryList.size() - 1);
        assertThat(testCatalogueCategory.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void createCatalogueCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catalogueCategoryRepository.findAll().size();

        // Create the CatalogueCategory with an existing ID
        catalogueCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatalogueCategoryMockMvc.perform(post("/api/catalogue-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogueCategory)))
            .andExpect(status().isBadRequest());

        // Validate the CatalogueCategory in the database
        List<CatalogueCategory> catalogueCategoryList = catalogueCategoryRepository.findAll();
        assertThat(catalogueCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCatalogueCategories() throws Exception {
        // Initialize the database
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);

        // Get all the catalogueCategoryList
        restCatalogueCategoryMockMvc.perform(get("/api/catalogue-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catalogueCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)));
    }
    
    @Test
    @Transactional
    public void getCatalogueCategory() throws Exception {
        // Initialize the database
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);

        // Get the catalogueCategory
        restCatalogueCategoryMockMvc.perform(get("/api/catalogue-categories/{id}", catalogueCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catalogueCategory.getId().intValue()))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO));
    }


    @Test
    @Transactional
    public void getCatalogueCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);

        Long id = catalogueCategory.getId();

        defaultCatalogueCategoryShouldBeFound("id.equals=" + id);
        defaultCatalogueCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultCatalogueCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatalogueCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultCatalogueCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatalogueCategoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCatalogueCategoriesBySequenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);

        // Get all the catalogueCategoryList where sequenceNo equals to DEFAULT_SEQUENCE_NO
        defaultCatalogueCategoryShouldBeFound("sequenceNo.equals=" + DEFAULT_SEQUENCE_NO);

        // Get all the catalogueCategoryList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultCatalogueCategoryShouldNotBeFound("sequenceNo.equals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllCatalogueCategoriesBySequenceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);

        // Get all the catalogueCategoryList where sequenceNo not equals to DEFAULT_SEQUENCE_NO
        defaultCatalogueCategoryShouldNotBeFound("sequenceNo.notEquals=" + DEFAULT_SEQUENCE_NO);

        // Get all the catalogueCategoryList where sequenceNo not equals to UPDATED_SEQUENCE_NO
        defaultCatalogueCategoryShouldBeFound("sequenceNo.notEquals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllCatalogueCategoriesBySequenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);

        // Get all the catalogueCategoryList where sequenceNo in DEFAULT_SEQUENCE_NO or UPDATED_SEQUENCE_NO
        defaultCatalogueCategoryShouldBeFound("sequenceNo.in=" + DEFAULT_SEQUENCE_NO + "," + UPDATED_SEQUENCE_NO);

        // Get all the catalogueCategoryList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultCatalogueCategoryShouldNotBeFound("sequenceNo.in=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllCatalogueCategoriesBySequenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);

        // Get all the catalogueCategoryList where sequenceNo is not null
        defaultCatalogueCategoryShouldBeFound("sequenceNo.specified=true");

        // Get all the catalogueCategoryList where sequenceNo is null
        defaultCatalogueCategoryShouldNotBeFound("sequenceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCatalogueCategoriesBySequenceNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);

        // Get all the catalogueCategoryList where sequenceNo is greater than or equal to DEFAULT_SEQUENCE_NO
        defaultCatalogueCategoryShouldBeFound("sequenceNo.greaterThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the catalogueCategoryList where sequenceNo is greater than or equal to UPDATED_SEQUENCE_NO
        defaultCatalogueCategoryShouldNotBeFound("sequenceNo.greaterThanOrEqual=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllCatalogueCategoriesBySequenceNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);

        // Get all the catalogueCategoryList where sequenceNo is less than or equal to DEFAULT_SEQUENCE_NO
        defaultCatalogueCategoryShouldBeFound("sequenceNo.lessThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the catalogueCategoryList where sequenceNo is less than or equal to SMALLER_SEQUENCE_NO
        defaultCatalogueCategoryShouldNotBeFound("sequenceNo.lessThanOrEqual=" + SMALLER_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllCatalogueCategoriesBySequenceNoIsLessThanSomething() throws Exception {
        // Initialize the database
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);

        // Get all the catalogueCategoryList where sequenceNo is less than DEFAULT_SEQUENCE_NO
        defaultCatalogueCategoryShouldNotBeFound("sequenceNo.lessThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the catalogueCategoryList where sequenceNo is less than UPDATED_SEQUENCE_NO
        defaultCatalogueCategoryShouldBeFound("sequenceNo.lessThan=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllCatalogueCategoriesBySequenceNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);

        // Get all the catalogueCategoryList where sequenceNo is greater than DEFAULT_SEQUENCE_NO
        defaultCatalogueCategoryShouldNotBeFound("sequenceNo.greaterThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the catalogueCategoryList where sequenceNo is greater than SMALLER_SEQUENCE_NO
        defaultCatalogueCategoryShouldBeFound("sequenceNo.greaterThan=" + SMALLER_SEQUENCE_NO);
    }


    @Test
    @Transactional
    public void getAllCatalogueCategoriesByCatalogueIsEqualToSomething() throws Exception {
        // Initialize the database
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);
        Catalogue catalogue = CatalogueResourceIT.createEntity(em);
        em.persist(catalogue);
        em.flush();
        catalogueCategory.setCatalogue(catalogue);
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);
        Long catalogueId = catalogue.getId();

        // Get all the catalogueCategoryList where catalogue equals to catalogueId
        defaultCatalogueCategoryShouldBeFound("catalogueId.equals=" + catalogueId);

        // Get all the catalogueCategoryList where catalogue equals to catalogueId + 1
        defaultCatalogueCategoryShouldNotBeFound("catalogueId.equals=" + (catalogueId + 1));
    }


    @Test
    @Transactional
    public void getAllCatalogueCategoriesByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);
        ProductCategory productCategory = ProductCategoryResourceIT.createEntity(em);
        em.persist(productCategory);
        em.flush();
        catalogueCategory.setProductCategory(productCategory);
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);
        Long productCategoryId = productCategory.getId();

        // Get all the catalogueCategoryList where productCategory equals to productCategoryId
        defaultCatalogueCategoryShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the catalogueCategoryList where productCategory equals to productCategoryId + 1
        defaultCatalogueCategoryShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllCatalogueCategoriesByCatalogueCategoryTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);
        CatalogueCategoryType catalogueCategoryType = CatalogueCategoryTypeResourceIT.createEntity(em);
        em.persist(catalogueCategoryType);
        em.flush();
        catalogueCategory.setCatalogueCategoryType(catalogueCategoryType);
        catalogueCategoryRepository.saveAndFlush(catalogueCategory);
        Long catalogueCategoryTypeId = catalogueCategoryType.getId();

        // Get all the catalogueCategoryList where catalogueCategoryType equals to catalogueCategoryTypeId
        defaultCatalogueCategoryShouldBeFound("catalogueCategoryTypeId.equals=" + catalogueCategoryTypeId);

        // Get all the catalogueCategoryList where catalogueCategoryType equals to catalogueCategoryTypeId + 1
        defaultCatalogueCategoryShouldNotBeFound("catalogueCategoryTypeId.equals=" + (catalogueCategoryTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatalogueCategoryShouldBeFound(String filter) throws Exception {
        restCatalogueCategoryMockMvc.perform(get("/api/catalogue-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catalogueCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)));

        // Check, that the count call also returns 1
        restCatalogueCategoryMockMvc.perform(get("/api/catalogue-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatalogueCategoryShouldNotBeFound(String filter) throws Exception {
        restCatalogueCategoryMockMvc.perform(get("/api/catalogue-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatalogueCategoryMockMvc.perform(get("/api/catalogue-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatalogueCategory() throws Exception {
        // Get the catalogueCategory
        restCatalogueCategoryMockMvc.perform(get("/api/catalogue-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatalogueCategory() throws Exception {
        // Initialize the database
        catalogueCategoryService.save(catalogueCategory);

        int databaseSizeBeforeUpdate = catalogueCategoryRepository.findAll().size();

        // Update the catalogueCategory
        CatalogueCategory updatedCatalogueCategory = catalogueCategoryRepository.findById(catalogueCategory.getId()).get();
        // Disconnect from session so that the updates on updatedCatalogueCategory are not directly saved in db
        em.detach(updatedCatalogueCategory);
        updatedCatalogueCategory
            .sequenceNo(UPDATED_SEQUENCE_NO);

        restCatalogueCategoryMockMvc.perform(put("/api/catalogue-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatalogueCategory)))
            .andExpect(status().isOk());

        // Validate the CatalogueCategory in the database
        List<CatalogueCategory> catalogueCategoryList = catalogueCategoryRepository.findAll();
        assertThat(catalogueCategoryList).hasSize(databaseSizeBeforeUpdate);
        CatalogueCategory testCatalogueCategory = catalogueCategoryList.get(catalogueCategoryList.size() - 1);
        assertThat(testCatalogueCategory.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void updateNonExistingCatalogueCategory() throws Exception {
        int databaseSizeBeforeUpdate = catalogueCategoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatalogueCategoryMockMvc.perform(put("/api/catalogue-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogueCategory)))
            .andExpect(status().isBadRequest());

        // Validate the CatalogueCategory in the database
        List<CatalogueCategory> catalogueCategoryList = catalogueCategoryRepository.findAll();
        assertThat(catalogueCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatalogueCategory() throws Exception {
        // Initialize the database
        catalogueCategoryService.save(catalogueCategory);

        int databaseSizeBeforeDelete = catalogueCategoryRepository.findAll().size();

        // Delete the catalogueCategory
        restCatalogueCategoryMockMvc.perform(delete("/api/catalogue-categories/{id}", catalogueCategory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CatalogueCategory> catalogueCategoryList = catalogueCategoryRepository.findAll();
        assertThat(catalogueCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
