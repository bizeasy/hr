package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.Catalogue;
import com.hr.repository.CatalogueRepository;
import com.hr.service.CatalogueService;
import com.hr.service.dto.CatalogueCriteria;
import com.hr.service.CatalogueQueryService;

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
 * Integration tests for the {@link CatalogueResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CatalogueResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;
    private static final Integer SMALLER_SEQUENCE_NO = 1 - 1;

    private static final String DEFAULT_IMAGE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_MOBILE_IMAGE_PATH = "AAAAAAAAAA";
    private static final String UPDATED_MOBILE_IMAGE_PATH = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_IMAGE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ALT_IMAGE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_IMAGE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ALT_IMAGE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_IMAGE_3 = "AAAAAAAAAA";
    private static final String UPDATED_ALT_IMAGE_3 = "BBBBBBBBBB";

    @Autowired
    private CatalogueRepository catalogueRepository;

    @Autowired
    private CatalogueService catalogueService;

    @Autowired
    private CatalogueQueryService catalogueQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCatalogueMockMvc;

    private Catalogue catalogue;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Catalogue createEntity(EntityManager em) {
        Catalogue catalogue = new Catalogue()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .sequenceNo(DEFAULT_SEQUENCE_NO)
            .imagePath(DEFAULT_IMAGE_PATH)
            .mobileImagePath(DEFAULT_MOBILE_IMAGE_PATH)
            .altImage1(DEFAULT_ALT_IMAGE_1)
            .altImage2(DEFAULT_ALT_IMAGE_2)
            .altImage3(DEFAULT_ALT_IMAGE_3);
        return catalogue;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Catalogue createUpdatedEntity(EntityManager em) {
        Catalogue catalogue = new Catalogue()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .imagePath(UPDATED_IMAGE_PATH)
            .mobileImagePath(UPDATED_MOBILE_IMAGE_PATH)
            .altImage1(UPDATED_ALT_IMAGE_1)
            .altImage2(UPDATED_ALT_IMAGE_2)
            .altImage3(UPDATED_ALT_IMAGE_3);
        return catalogue;
    }

    @BeforeEach
    public void initTest() {
        catalogue = createEntity(em);
    }

    @Test
    @Transactional
    public void createCatalogue() throws Exception {
        int databaseSizeBeforeCreate = catalogueRepository.findAll().size();
        // Create the Catalogue
        restCatalogueMockMvc.perform(post("/api/catalogues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogue)))
            .andExpect(status().isCreated());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeCreate + 1);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCatalogue.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testCatalogue.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
        assertThat(testCatalogue.getImagePath()).isEqualTo(DEFAULT_IMAGE_PATH);
        assertThat(testCatalogue.getMobileImagePath()).isEqualTo(DEFAULT_MOBILE_IMAGE_PATH);
        assertThat(testCatalogue.getAltImage1()).isEqualTo(DEFAULT_ALT_IMAGE_1);
        assertThat(testCatalogue.getAltImage2()).isEqualTo(DEFAULT_ALT_IMAGE_2);
        assertThat(testCatalogue.getAltImage3()).isEqualTo(DEFAULT_ALT_IMAGE_3);
    }

    @Test
    @Transactional
    public void createCatalogueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catalogueRepository.findAll().size();

        // Create the Catalogue with an existing ID
        catalogue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatalogueMockMvc.perform(post("/api/catalogues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogue)))
            .andExpect(status().isBadRequest());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCatalogues() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList
        restCatalogueMockMvc.perform(get("/api/catalogues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catalogue.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].imagePath").value(hasItem(DEFAULT_IMAGE_PATH)))
            .andExpect(jsonPath("$.[*].mobileImagePath").value(hasItem(DEFAULT_MOBILE_IMAGE_PATH)))
            .andExpect(jsonPath("$.[*].altImage1").value(hasItem(DEFAULT_ALT_IMAGE_1)))
            .andExpect(jsonPath("$.[*].altImage2").value(hasItem(DEFAULT_ALT_IMAGE_2)))
            .andExpect(jsonPath("$.[*].altImage3").value(hasItem(DEFAULT_ALT_IMAGE_3)));
    }
    
    @Test
    @Transactional
    public void getCatalogue() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get the catalogue
        restCatalogueMockMvc.perform(get("/api/catalogues/{id}", catalogue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(catalogue.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO))
            .andExpect(jsonPath("$.imagePath").value(DEFAULT_IMAGE_PATH))
            .andExpect(jsonPath("$.mobileImagePath").value(DEFAULT_MOBILE_IMAGE_PATH))
            .andExpect(jsonPath("$.altImage1").value(DEFAULT_ALT_IMAGE_1))
            .andExpect(jsonPath("$.altImage2").value(DEFAULT_ALT_IMAGE_2))
            .andExpect(jsonPath("$.altImage3").value(DEFAULT_ALT_IMAGE_3));
    }


    @Test
    @Transactional
    public void getCataloguesByIdFiltering() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        Long id = catalogue.getId();

        defaultCatalogueShouldBeFound("id.equals=" + id);
        defaultCatalogueShouldNotBeFound("id.notEquals=" + id);

        defaultCatalogueShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCatalogueShouldNotBeFound("id.greaterThan=" + id);

        defaultCatalogueShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCatalogueShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllCataloguesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where name equals to DEFAULT_NAME
        defaultCatalogueShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the catalogueList where name equals to UPDATED_NAME
        defaultCatalogueShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCataloguesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where name not equals to DEFAULT_NAME
        defaultCatalogueShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the catalogueList where name not equals to UPDATED_NAME
        defaultCatalogueShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCataloguesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCatalogueShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the catalogueList where name equals to UPDATED_NAME
        defaultCatalogueShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCataloguesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where name is not null
        defaultCatalogueShouldBeFound("name.specified=true");

        // Get all the catalogueList where name is null
        defaultCatalogueShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllCataloguesByNameContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where name contains DEFAULT_NAME
        defaultCatalogueShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the catalogueList where name contains UPDATED_NAME
        defaultCatalogueShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllCataloguesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where name does not contain DEFAULT_NAME
        defaultCatalogueShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the catalogueList where name does not contain UPDATED_NAME
        defaultCatalogueShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllCataloguesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where description equals to DEFAULT_DESCRIPTION
        defaultCatalogueShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the catalogueList where description equals to UPDATED_DESCRIPTION
        defaultCatalogueShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCataloguesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where description not equals to DEFAULT_DESCRIPTION
        defaultCatalogueShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the catalogueList where description not equals to UPDATED_DESCRIPTION
        defaultCatalogueShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCataloguesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultCatalogueShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the catalogueList where description equals to UPDATED_DESCRIPTION
        defaultCatalogueShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCataloguesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where description is not null
        defaultCatalogueShouldBeFound("description.specified=true");

        // Get all the catalogueList where description is null
        defaultCatalogueShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllCataloguesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where description contains DEFAULT_DESCRIPTION
        defaultCatalogueShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the catalogueList where description contains UPDATED_DESCRIPTION
        defaultCatalogueShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllCataloguesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where description does not contain DEFAULT_DESCRIPTION
        defaultCatalogueShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the catalogueList where description does not contain UPDATED_DESCRIPTION
        defaultCatalogueShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllCataloguesBySequenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where sequenceNo equals to DEFAULT_SEQUENCE_NO
        defaultCatalogueShouldBeFound("sequenceNo.equals=" + DEFAULT_SEQUENCE_NO);

        // Get all the catalogueList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultCatalogueShouldNotBeFound("sequenceNo.equals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllCataloguesBySequenceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where sequenceNo not equals to DEFAULT_SEQUENCE_NO
        defaultCatalogueShouldNotBeFound("sequenceNo.notEquals=" + DEFAULT_SEQUENCE_NO);

        // Get all the catalogueList where sequenceNo not equals to UPDATED_SEQUENCE_NO
        defaultCatalogueShouldBeFound("sequenceNo.notEquals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllCataloguesBySequenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where sequenceNo in DEFAULT_SEQUENCE_NO or UPDATED_SEQUENCE_NO
        defaultCatalogueShouldBeFound("sequenceNo.in=" + DEFAULT_SEQUENCE_NO + "," + UPDATED_SEQUENCE_NO);

        // Get all the catalogueList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultCatalogueShouldNotBeFound("sequenceNo.in=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllCataloguesBySequenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where sequenceNo is not null
        defaultCatalogueShouldBeFound("sequenceNo.specified=true");

        // Get all the catalogueList where sequenceNo is null
        defaultCatalogueShouldNotBeFound("sequenceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllCataloguesBySequenceNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where sequenceNo is greater than or equal to DEFAULT_SEQUENCE_NO
        defaultCatalogueShouldBeFound("sequenceNo.greaterThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the catalogueList where sequenceNo is greater than or equal to UPDATED_SEQUENCE_NO
        defaultCatalogueShouldNotBeFound("sequenceNo.greaterThanOrEqual=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllCataloguesBySequenceNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where sequenceNo is less than or equal to DEFAULT_SEQUENCE_NO
        defaultCatalogueShouldBeFound("sequenceNo.lessThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the catalogueList where sequenceNo is less than or equal to SMALLER_SEQUENCE_NO
        defaultCatalogueShouldNotBeFound("sequenceNo.lessThanOrEqual=" + SMALLER_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllCataloguesBySequenceNoIsLessThanSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where sequenceNo is less than DEFAULT_SEQUENCE_NO
        defaultCatalogueShouldNotBeFound("sequenceNo.lessThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the catalogueList where sequenceNo is less than UPDATED_SEQUENCE_NO
        defaultCatalogueShouldBeFound("sequenceNo.lessThan=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllCataloguesBySequenceNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where sequenceNo is greater than DEFAULT_SEQUENCE_NO
        defaultCatalogueShouldNotBeFound("sequenceNo.greaterThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the catalogueList where sequenceNo is greater than SMALLER_SEQUENCE_NO
        defaultCatalogueShouldBeFound("sequenceNo.greaterThan=" + SMALLER_SEQUENCE_NO);
    }


    @Test
    @Transactional
    public void getAllCataloguesByImagePathIsEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where imagePath equals to DEFAULT_IMAGE_PATH
        defaultCatalogueShouldBeFound("imagePath.equals=" + DEFAULT_IMAGE_PATH);

        // Get all the catalogueList where imagePath equals to UPDATED_IMAGE_PATH
        defaultCatalogueShouldNotBeFound("imagePath.equals=" + UPDATED_IMAGE_PATH);
    }

    @Test
    @Transactional
    public void getAllCataloguesByImagePathIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where imagePath not equals to DEFAULT_IMAGE_PATH
        defaultCatalogueShouldNotBeFound("imagePath.notEquals=" + DEFAULT_IMAGE_PATH);

        // Get all the catalogueList where imagePath not equals to UPDATED_IMAGE_PATH
        defaultCatalogueShouldBeFound("imagePath.notEquals=" + UPDATED_IMAGE_PATH);
    }

    @Test
    @Transactional
    public void getAllCataloguesByImagePathIsInShouldWork() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where imagePath in DEFAULT_IMAGE_PATH or UPDATED_IMAGE_PATH
        defaultCatalogueShouldBeFound("imagePath.in=" + DEFAULT_IMAGE_PATH + "," + UPDATED_IMAGE_PATH);

        // Get all the catalogueList where imagePath equals to UPDATED_IMAGE_PATH
        defaultCatalogueShouldNotBeFound("imagePath.in=" + UPDATED_IMAGE_PATH);
    }

    @Test
    @Transactional
    public void getAllCataloguesByImagePathIsNullOrNotNull() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where imagePath is not null
        defaultCatalogueShouldBeFound("imagePath.specified=true");

        // Get all the catalogueList where imagePath is null
        defaultCatalogueShouldNotBeFound("imagePath.specified=false");
    }
                @Test
    @Transactional
    public void getAllCataloguesByImagePathContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where imagePath contains DEFAULT_IMAGE_PATH
        defaultCatalogueShouldBeFound("imagePath.contains=" + DEFAULT_IMAGE_PATH);

        // Get all the catalogueList where imagePath contains UPDATED_IMAGE_PATH
        defaultCatalogueShouldNotBeFound("imagePath.contains=" + UPDATED_IMAGE_PATH);
    }

    @Test
    @Transactional
    public void getAllCataloguesByImagePathNotContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where imagePath does not contain DEFAULT_IMAGE_PATH
        defaultCatalogueShouldNotBeFound("imagePath.doesNotContain=" + DEFAULT_IMAGE_PATH);

        // Get all the catalogueList where imagePath does not contain UPDATED_IMAGE_PATH
        defaultCatalogueShouldBeFound("imagePath.doesNotContain=" + UPDATED_IMAGE_PATH);
    }


    @Test
    @Transactional
    public void getAllCataloguesByMobileImagePathIsEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where mobileImagePath equals to DEFAULT_MOBILE_IMAGE_PATH
        defaultCatalogueShouldBeFound("mobileImagePath.equals=" + DEFAULT_MOBILE_IMAGE_PATH);

        // Get all the catalogueList where mobileImagePath equals to UPDATED_MOBILE_IMAGE_PATH
        defaultCatalogueShouldNotBeFound("mobileImagePath.equals=" + UPDATED_MOBILE_IMAGE_PATH);
    }

    @Test
    @Transactional
    public void getAllCataloguesByMobileImagePathIsNotEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where mobileImagePath not equals to DEFAULT_MOBILE_IMAGE_PATH
        defaultCatalogueShouldNotBeFound("mobileImagePath.notEquals=" + DEFAULT_MOBILE_IMAGE_PATH);

        // Get all the catalogueList where mobileImagePath not equals to UPDATED_MOBILE_IMAGE_PATH
        defaultCatalogueShouldBeFound("mobileImagePath.notEquals=" + UPDATED_MOBILE_IMAGE_PATH);
    }

    @Test
    @Transactional
    public void getAllCataloguesByMobileImagePathIsInShouldWork() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where mobileImagePath in DEFAULT_MOBILE_IMAGE_PATH or UPDATED_MOBILE_IMAGE_PATH
        defaultCatalogueShouldBeFound("mobileImagePath.in=" + DEFAULT_MOBILE_IMAGE_PATH + "," + UPDATED_MOBILE_IMAGE_PATH);

        // Get all the catalogueList where mobileImagePath equals to UPDATED_MOBILE_IMAGE_PATH
        defaultCatalogueShouldNotBeFound("mobileImagePath.in=" + UPDATED_MOBILE_IMAGE_PATH);
    }

    @Test
    @Transactional
    public void getAllCataloguesByMobileImagePathIsNullOrNotNull() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where mobileImagePath is not null
        defaultCatalogueShouldBeFound("mobileImagePath.specified=true");

        // Get all the catalogueList where mobileImagePath is null
        defaultCatalogueShouldNotBeFound("mobileImagePath.specified=false");
    }
                @Test
    @Transactional
    public void getAllCataloguesByMobileImagePathContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where mobileImagePath contains DEFAULT_MOBILE_IMAGE_PATH
        defaultCatalogueShouldBeFound("mobileImagePath.contains=" + DEFAULT_MOBILE_IMAGE_PATH);

        // Get all the catalogueList where mobileImagePath contains UPDATED_MOBILE_IMAGE_PATH
        defaultCatalogueShouldNotBeFound("mobileImagePath.contains=" + UPDATED_MOBILE_IMAGE_PATH);
    }

    @Test
    @Transactional
    public void getAllCataloguesByMobileImagePathNotContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where mobileImagePath does not contain DEFAULT_MOBILE_IMAGE_PATH
        defaultCatalogueShouldNotBeFound("mobileImagePath.doesNotContain=" + DEFAULT_MOBILE_IMAGE_PATH);

        // Get all the catalogueList where mobileImagePath does not contain UPDATED_MOBILE_IMAGE_PATH
        defaultCatalogueShouldBeFound("mobileImagePath.doesNotContain=" + UPDATED_MOBILE_IMAGE_PATH);
    }


    @Test
    @Transactional
    public void getAllCataloguesByAltImage1IsEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage1 equals to DEFAULT_ALT_IMAGE_1
        defaultCatalogueShouldBeFound("altImage1.equals=" + DEFAULT_ALT_IMAGE_1);

        // Get all the catalogueList where altImage1 equals to UPDATED_ALT_IMAGE_1
        defaultCatalogueShouldNotBeFound("altImage1.equals=" + UPDATED_ALT_IMAGE_1);
    }

    @Test
    @Transactional
    public void getAllCataloguesByAltImage1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage1 not equals to DEFAULT_ALT_IMAGE_1
        defaultCatalogueShouldNotBeFound("altImage1.notEquals=" + DEFAULT_ALT_IMAGE_1);

        // Get all the catalogueList where altImage1 not equals to UPDATED_ALT_IMAGE_1
        defaultCatalogueShouldBeFound("altImage1.notEquals=" + UPDATED_ALT_IMAGE_1);
    }

    @Test
    @Transactional
    public void getAllCataloguesByAltImage1IsInShouldWork() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage1 in DEFAULT_ALT_IMAGE_1 or UPDATED_ALT_IMAGE_1
        defaultCatalogueShouldBeFound("altImage1.in=" + DEFAULT_ALT_IMAGE_1 + "," + UPDATED_ALT_IMAGE_1);

        // Get all the catalogueList where altImage1 equals to UPDATED_ALT_IMAGE_1
        defaultCatalogueShouldNotBeFound("altImage1.in=" + UPDATED_ALT_IMAGE_1);
    }

    @Test
    @Transactional
    public void getAllCataloguesByAltImage1IsNullOrNotNull() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage1 is not null
        defaultCatalogueShouldBeFound("altImage1.specified=true");

        // Get all the catalogueList where altImage1 is null
        defaultCatalogueShouldNotBeFound("altImage1.specified=false");
    }
                @Test
    @Transactional
    public void getAllCataloguesByAltImage1ContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage1 contains DEFAULT_ALT_IMAGE_1
        defaultCatalogueShouldBeFound("altImage1.contains=" + DEFAULT_ALT_IMAGE_1);

        // Get all the catalogueList where altImage1 contains UPDATED_ALT_IMAGE_1
        defaultCatalogueShouldNotBeFound("altImage1.contains=" + UPDATED_ALT_IMAGE_1);
    }

    @Test
    @Transactional
    public void getAllCataloguesByAltImage1NotContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage1 does not contain DEFAULT_ALT_IMAGE_1
        defaultCatalogueShouldNotBeFound("altImage1.doesNotContain=" + DEFAULT_ALT_IMAGE_1);

        // Get all the catalogueList where altImage1 does not contain UPDATED_ALT_IMAGE_1
        defaultCatalogueShouldBeFound("altImage1.doesNotContain=" + UPDATED_ALT_IMAGE_1);
    }


    @Test
    @Transactional
    public void getAllCataloguesByAltImage2IsEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage2 equals to DEFAULT_ALT_IMAGE_2
        defaultCatalogueShouldBeFound("altImage2.equals=" + DEFAULT_ALT_IMAGE_2);

        // Get all the catalogueList where altImage2 equals to UPDATED_ALT_IMAGE_2
        defaultCatalogueShouldNotBeFound("altImage2.equals=" + UPDATED_ALT_IMAGE_2);
    }

    @Test
    @Transactional
    public void getAllCataloguesByAltImage2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage2 not equals to DEFAULT_ALT_IMAGE_2
        defaultCatalogueShouldNotBeFound("altImage2.notEquals=" + DEFAULT_ALT_IMAGE_2);

        // Get all the catalogueList where altImage2 not equals to UPDATED_ALT_IMAGE_2
        defaultCatalogueShouldBeFound("altImage2.notEquals=" + UPDATED_ALT_IMAGE_2);
    }

    @Test
    @Transactional
    public void getAllCataloguesByAltImage2IsInShouldWork() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage2 in DEFAULT_ALT_IMAGE_2 or UPDATED_ALT_IMAGE_2
        defaultCatalogueShouldBeFound("altImage2.in=" + DEFAULT_ALT_IMAGE_2 + "," + UPDATED_ALT_IMAGE_2);

        // Get all the catalogueList where altImage2 equals to UPDATED_ALT_IMAGE_2
        defaultCatalogueShouldNotBeFound("altImage2.in=" + UPDATED_ALT_IMAGE_2);
    }

    @Test
    @Transactional
    public void getAllCataloguesByAltImage2IsNullOrNotNull() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage2 is not null
        defaultCatalogueShouldBeFound("altImage2.specified=true");

        // Get all the catalogueList where altImage2 is null
        defaultCatalogueShouldNotBeFound("altImage2.specified=false");
    }
                @Test
    @Transactional
    public void getAllCataloguesByAltImage2ContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage2 contains DEFAULT_ALT_IMAGE_2
        defaultCatalogueShouldBeFound("altImage2.contains=" + DEFAULT_ALT_IMAGE_2);

        // Get all the catalogueList where altImage2 contains UPDATED_ALT_IMAGE_2
        defaultCatalogueShouldNotBeFound("altImage2.contains=" + UPDATED_ALT_IMAGE_2);
    }

    @Test
    @Transactional
    public void getAllCataloguesByAltImage2NotContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage2 does not contain DEFAULT_ALT_IMAGE_2
        defaultCatalogueShouldNotBeFound("altImage2.doesNotContain=" + DEFAULT_ALT_IMAGE_2);

        // Get all the catalogueList where altImage2 does not contain UPDATED_ALT_IMAGE_2
        defaultCatalogueShouldBeFound("altImage2.doesNotContain=" + UPDATED_ALT_IMAGE_2);
    }


    @Test
    @Transactional
    public void getAllCataloguesByAltImage3IsEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage3 equals to DEFAULT_ALT_IMAGE_3
        defaultCatalogueShouldBeFound("altImage3.equals=" + DEFAULT_ALT_IMAGE_3);

        // Get all the catalogueList where altImage3 equals to UPDATED_ALT_IMAGE_3
        defaultCatalogueShouldNotBeFound("altImage3.equals=" + UPDATED_ALT_IMAGE_3);
    }

    @Test
    @Transactional
    public void getAllCataloguesByAltImage3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage3 not equals to DEFAULT_ALT_IMAGE_3
        defaultCatalogueShouldNotBeFound("altImage3.notEquals=" + DEFAULT_ALT_IMAGE_3);

        // Get all the catalogueList where altImage3 not equals to UPDATED_ALT_IMAGE_3
        defaultCatalogueShouldBeFound("altImage3.notEquals=" + UPDATED_ALT_IMAGE_3);
    }

    @Test
    @Transactional
    public void getAllCataloguesByAltImage3IsInShouldWork() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage3 in DEFAULT_ALT_IMAGE_3 or UPDATED_ALT_IMAGE_3
        defaultCatalogueShouldBeFound("altImage3.in=" + DEFAULT_ALT_IMAGE_3 + "," + UPDATED_ALT_IMAGE_3);

        // Get all the catalogueList where altImage3 equals to UPDATED_ALT_IMAGE_3
        defaultCatalogueShouldNotBeFound("altImage3.in=" + UPDATED_ALT_IMAGE_3);
    }

    @Test
    @Transactional
    public void getAllCataloguesByAltImage3IsNullOrNotNull() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage3 is not null
        defaultCatalogueShouldBeFound("altImage3.specified=true");

        // Get all the catalogueList where altImage3 is null
        defaultCatalogueShouldNotBeFound("altImage3.specified=false");
    }
                @Test
    @Transactional
    public void getAllCataloguesByAltImage3ContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage3 contains DEFAULT_ALT_IMAGE_3
        defaultCatalogueShouldBeFound("altImage3.contains=" + DEFAULT_ALT_IMAGE_3);

        // Get all the catalogueList where altImage3 contains UPDATED_ALT_IMAGE_3
        defaultCatalogueShouldNotBeFound("altImage3.contains=" + UPDATED_ALT_IMAGE_3);
    }

    @Test
    @Transactional
    public void getAllCataloguesByAltImage3NotContainsSomething() throws Exception {
        // Initialize the database
        catalogueRepository.saveAndFlush(catalogue);

        // Get all the catalogueList where altImage3 does not contain DEFAULT_ALT_IMAGE_3
        defaultCatalogueShouldNotBeFound("altImage3.doesNotContain=" + DEFAULT_ALT_IMAGE_3);

        // Get all the catalogueList where altImage3 does not contain UPDATED_ALT_IMAGE_3
        defaultCatalogueShouldBeFound("altImage3.doesNotContain=" + UPDATED_ALT_IMAGE_3);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCatalogueShouldBeFound(String filter) throws Exception {
        restCatalogueMockMvc.perform(get("/api/catalogues?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(catalogue.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].imagePath").value(hasItem(DEFAULT_IMAGE_PATH)))
            .andExpect(jsonPath("$.[*].mobileImagePath").value(hasItem(DEFAULT_MOBILE_IMAGE_PATH)))
            .andExpect(jsonPath("$.[*].altImage1").value(hasItem(DEFAULT_ALT_IMAGE_1)))
            .andExpect(jsonPath("$.[*].altImage2").value(hasItem(DEFAULT_ALT_IMAGE_2)))
            .andExpect(jsonPath("$.[*].altImage3").value(hasItem(DEFAULT_ALT_IMAGE_3)));

        // Check, that the count call also returns 1
        restCatalogueMockMvc.perform(get("/api/catalogues/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCatalogueShouldNotBeFound(String filter) throws Exception {
        restCatalogueMockMvc.perform(get("/api/catalogues?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCatalogueMockMvc.perform(get("/api/catalogues/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingCatalogue() throws Exception {
        // Get the catalogue
        restCatalogueMockMvc.perform(get("/api/catalogues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCatalogue() throws Exception {
        // Initialize the database
        catalogueService.save(catalogue);

        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();

        // Update the catalogue
        Catalogue updatedCatalogue = catalogueRepository.findById(catalogue.getId()).get();
        // Disconnect from session so that the updates on updatedCatalogue are not directly saved in db
        em.detach(updatedCatalogue);
        updatedCatalogue
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .imagePath(UPDATED_IMAGE_PATH)
            .mobileImagePath(UPDATED_MOBILE_IMAGE_PATH)
            .altImage1(UPDATED_ALT_IMAGE_1)
            .altImage2(UPDATED_ALT_IMAGE_2)
            .altImage3(UPDATED_ALT_IMAGE_3);

        restCatalogueMockMvc.perform(put("/api/catalogues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCatalogue)))
            .andExpect(status().isOk());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
        Catalogue testCatalogue = catalogueList.get(catalogueList.size() - 1);
        assertThat(testCatalogue.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCatalogue.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testCatalogue.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
        assertThat(testCatalogue.getImagePath()).isEqualTo(UPDATED_IMAGE_PATH);
        assertThat(testCatalogue.getMobileImagePath()).isEqualTo(UPDATED_MOBILE_IMAGE_PATH);
        assertThat(testCatalogue.getAltImage1()).isEqualTo(UPDATED_ALT_IMAGE_1);
        assertThat(testCatalogue.getAltImage2()).isEqualTo(UPDATED_ALT_IMAGE_2);
        assertThat(testCatalogue.getAltImage3()).isEqualTo(UPDATED_ALT_IMAGE_3);
    }

    @Test
    @Transactional
    public void updateNonExistingCatalogue() throws Exception {
        int databaseSizeBeforeUpdate = catalogueRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatalogueMockMvc.perform(put("/api/catalogues")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(catalogue)))
            .andExpect(status().isBadRequest());

        // Validate the Catalogue in the database
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCatalogue() throws Exception {
        // Initialize the database
        catalogueService.save(catalogue);

        int databaseSizeBeforeDelete = catalogueRepository.findAll().size();

        // Delete the catalogue
        restCatalogueMockMvc.perform(delete("/api/catalogues/{id}", catalogue.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Catalogue> catalogueList = catalogueRepository.findAll();
        assertThat(catalogueList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
