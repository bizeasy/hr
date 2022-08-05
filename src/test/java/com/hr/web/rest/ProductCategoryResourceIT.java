package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ProductCategory;
import com.hr.domain.ProductCategoryType;
import com.hr.domain.ProductCategory;
import com.hr.repository.ProductCategoryRepository;
import com.hr.service.ProductCategoryService;
import com.hr.service.dto.ProductCategoryCriteria;
import com.hr.service.ProductCategoryQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductCategoryResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ATTRIBUTE = "AAAAAAAAAA";
    private static final String UPDATED_ATTRIBUTE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;
    private static final Integer SMALLER_SEQUENCE_NO = 1 - 1;

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ALT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_ALT_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductCategoryQueryService productCategoryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductCategoryMockMvc;

    private ProductCategory productCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategory createEntity(EntityManager em) {
        ProductCategory productCategory = new ProductCategory()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .longDescription(DEFAULT_LONG_DESCRIPTION)
            .attribute(DEFAULT_ATTRIBUTE)
            .sequenceNo(DEFAULT_SEQUENCE_NO)
            .imageUrl(DEFAULT_IMAGE_URL)
            .altImageUrl(DEFAULT_ALT_IMAGE_URL)
            .info(DEFAULT_INFO);
        return productCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategory createUpdatedEntity(EntityManager em) {
        ProductCategory productCategory = new ProductCategory()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .attribute(UPDATED_ATTRIBUTE)
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .imageUrl(UPDATED_IMAGE_URL)
            .altImageUrl(UPDATED_ALT_IMAGE_URL)
            .info(UPDATED_INFO);
        return productCategory;
    }

    @BeforeEach
    public void initTest() {
        productCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductCategory() throws Exception {
        int databaseSizeBeforeCreate = productCategoryRepository.findAll().size();
        // Create the ProductCategory
        restProductCategoryMockMvc.perform(post("/api/product-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategory)))
            .andExpect(status().isCreated());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ProductCategory testProductCategory = productCategoryList.get(productCategoryList.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductCategory.getLongDescription()).isEqualTo(DEFAULT_LONG_DESCRIPTION);
        assertThat(testProductCategory.getAttribute()).isEqualTo(DEFAULT_ATTRIBUTE);
        assertThat(testProductCategory.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
        assertThat(testProductCategory.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testProductCategory.getAltImageUrl()).isEqualTo(DEFAULT_ALT_IMAGE_URL);
        assertThat(testProductCategory.getInfo()).isEqualTo(DEFAULT_INFO);
    }

    @Test
    @Transactional
    public void createProductCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productCategoryRepository.findAll().size();

        // Create the ProductCategory with an existing ID
        productCategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductCategoryMockMvc.perform(post("/api/product-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategory)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductCategories() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList
        restProductCategoryMockMvc.perform(get("/api/product-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attribute").value(hasItem(DEFAULT_ATTRIBUTE)))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].altImageUrl").value(hasItem(DEFAULT_ALT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO.toString())));
    }
    
    @Test
    @Transactional
    public void getProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get the productCategory
        restProductCategoryMockMvc.perform(get("/api/product-categories/{id}", productCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION))
            .andExpect(jsonPath("$.attribute").value(DEFAULT_ATTRIBUTE))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.altImageUrl").value(DEFAULT_ALT_IMAGE_URL))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO.toString()));
    }


    @Test
    @Transactional
    public void getProductCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        Long id = productCategory.getId();

        defaultProductCategoryShouldBeFound("id.equals=" + id);
        defaultProductCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultProductCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultProductCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductCategoryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name equals to DEFAULT_NAME
        defaultProductCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productCategoryList where name equals to UPDATED_NAME
        defaultProductCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name not equals to DEFAULT_NAME
        defaultProductCategoryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productCategoryList where name not equals to UPDATED_NAME
        defaultProductCategoryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productCategoryList where name equals to UPDATED_NAME
        defaultProductCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name is not null
        defaultProductCategoryShouldBeFound("name.specified=true");

        // Get all the productCategoryList where name is null
        defaultProductCategoryShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name contains DEFAULT_NAME
        defaultProductCategoryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productCategoryList where name contains UPDATED_NAME
        defaultProductCategoryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name does not contain DEFAULT_NAME
        defaultProductCategoryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productCategoryList where name does not contain UPDATED_NAME
        defaultProductCategoryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where description equals to DEFAULT_DESCRIPTION
        defaultProductCategoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the productCategoryList where description equals to UPDATED_DESCRIPTION
        defaultProductCategoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where description not equals to DEFAULT_DESCRIPTION
        defaultProductCategoryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the productCategoryList where description not equals to UPDATED_DESCRIPTION
        defaultProductCategoryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProductCategoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the productCategoryList where description equals to UPDATED_DESCRIPTION
        defaultProductCategoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where description is not null
        defaultProductCategoryShouldBeFound("description.specified=true");

        // Get all the productCategoryList where description is null
        defaultProductCategoryShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where description contains DEFAULT_DESCRIPTION
        defaultProductCategoryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the productCategoryList where description contains UPDATED_DESCRIPTION
        defaultProductCategoryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where description does not contain DEFAULT_DESCRIPTION
        defaultProductCategoryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the productCategoryList where description does not contain UPDATED_DESCRIPTION
        defaultProductCategoryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByLongDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where longDescription equals to DEFAULT_LONG_DESCRIPTION
        defaultProductCategoryShouldBeFound("longDescription.equals=" + DEFAULT_LONG_DESCRIPTION);

        // Get all the productCategoryList where longDescription equals to UPDATED_LONG_DESCRIPTION
        defaultProductCategoryShouldNotBeFound("longDescription.equals=" + UPDATED_LONG_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByLongDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where longDescription not equals to DEFAULT_LONG_DESCRIPTION
        defaultProductCategoryShouldNotBeFound("longDescription.notEquals=" + DEFAULT_LONG_DESCRIPTION);

        // Get all the productCategoryList where longDescription not equals to UPDATED_LONG_DESCRIPTION
        defaultProductCategoryShouldBeFound("longDescription.notEquals=" + UPDATED_LONG_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByLongDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where longDescription in DEFAULT_LONG_DESCRIPTION or UPDATED_LONG_DESCRIPTION
        defaultProductCategoryShouldBeFound("longDescription.in=" + DEFAULT_LONG_DESCRIPTION + "," + UPDATED_LONG_DESCRIPTION);

        // Get all the productCategoryList where longDescription equals to UPDATED_LONG_DESCRIPTION
        defaultProductCategoryShouldNotBeFound("longDescription.in=" + UPDATED_LONG_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByLongDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where longDescription is not null
        defaultProductCategoryShouldBeFound("longDescription.specified=true");

        // Get all the productCategoryList where longDescription is null
        defaultProductCategoryShouldNotBeFound("longDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoriesByLongDescriptionContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where longDescription contains DEFAULT_LONG_DESCRIPTION
        defaultProductCategoryShouldBeFound("longDescription.contains=" + DEFAULT_LONG_DESCRIPTION);

        // Get all the productCategoryList where longDescription contains UPDATED_LONG_DESCRIPTION
        defaultProductCategoryShouldNotBeFound("longDescription.contains=" + UPDATED_LONG_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByLongDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where longDescription does not contain DEFAULT_LONG_DESCRIPTION
        defaultProductCategoryShouldNotBeFound("longDescription.doesNotContain=" + DEFAULT_LONG_DESCRIPTION);

        // Get all the productCategoryList where longDescription does not contain UPDATED_LONG_DESCRIPTION
        defaultProductCategoryShouldBeFound("longDescription.doesNotContain=" + UPDATED_LONG_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByAttributeIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where attribute equals to DEFAULT_ATTRIBUTE
        defaultProductCategoryShouldBeFound("attribute.equals=" + DEFAULT_ATTRIBUTE);

        // Get all the productCategoryList where attribute equals to UPDATED_ATTRIBUTE
        defaultProductCategoryShouldNotBeFound("attribute.equals=" + UPDATED_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByAttributeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where attribute not equals to DEFAULT_ATTRIBUTE
        defaultProductCategoryShouldNotBeFound("attribute.notEquals=" + DEFAULT_ATTRIBUTE);

        // Get all the productCategoryList where attribute not equals to UPDATED_ATTRIBUTE
        defaultProductCategoryShouldBeFound("attribute.notEquals=" + UPDATED_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByAttributeIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where attribute in DEFAULT_ATTRIBUTE or UPDATED_ATTRIBUTE
        defaultProductCategoryShouldBeFound("attribute.in=" + DEFAULT_ATTRIBUTE + "," + UPDATED_ATTRIBUTE);

        // Get all the productCategoryList where attribute equals to UPDATED_ATTRIBUTE
        defaultProductCategoryShouldNotBeFound("attribute.in=" + UPDATED_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByAttributeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where attribute is not null
        defaultProductCategoryShouldBeFound("attribute.specified=true");

        // Get all the productCategoryList where attribute is null
        defaultProductCategoryShouldNotBeFound("attribute.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoriesByAttributeContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where attribute contains DEFAULT_ATTRIBUTE
        defaultProductCategoryShouldBeFound("attribute.contains=" + DEFAULT_ATTRIBUTE);

        // Get all the productCategoryList where attribute contains UPDATED_ATTRIBUTE
        defaultProductCategoryShouldNotBeFound("attribute.contains=" + UPDATED_ATTRIBUTE);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByAttributeNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where attribute does not contain DEFAULT_ATTRIBUTE
        defaultProductCategoryShouldNotBeFound("attribute.doesNotContain=" + DEFAULT_ATTRIBUTE);

        // Get all the productCategoryList where attribute does not contain UPDATED_ATTRIBUTE
        defaultProductCategoryShouldBeFound("attribute.doesNotContain=" + UPDATED_ATTRIBUTE);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesBySequenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sequenceNo equals to DEFAULT_SEQUENCE_NO
        defaultProductCategoryShouldBeFound("sequenceNo.equals=" + DEFAULT_SEQUENCE_NO);

        // Get all the productCategoryList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultProductCategoryShouldNotBeFound("sequenceNo.equals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesBySequenceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sequenceNo not equals to DEFAULT_SEQUENCE_NO
        defaultProductCategoryShouldNotBeFound("sequenceNo.notEquals=" + DEFAULT_SEQUENCE_NO);

        // Get all the productCategoryList where sequenceNo not equals to UPDATED_SEQUENCE_NO
        defaultProductCategoryShouldBeFound("sequenceNo.notEquals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesBySequenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sequenceNo in DEFAULT_SEQUENCE_NO or UPDATED_SEQUENCE_NO
        defaultProductCategoryShouldBeFound("sequenceNo.in=" + DEFAULT_SEQUENCE_NO + "," + UPDATED_SEQUENCE_NO);

        // Get all the productCategoryList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultProductCategoryShouldNotBeFound("sequenceNo.in=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesBySequenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sequenceNo is not null
        defaultProductCategoryShouldBeFound("sequenceNo.specified=true");

        // Get all the productCategoryList where sequenceNo is null
        defaultProductCategoryShouldNotBeFound("sequenceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductCategoriesBySequenceNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sequenceNo is greater than or equal to DEFAULT_SEQUENCE_NO
        defaultProductCategoryShouldBeFound("sequenceNo.greaterThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the productCategoryList where sequenceNo is greater than or equal to UPDATED_SEQUENCE_NO
        defaultProductCategoryShouldNotBeFound("sequenceNo.greaterThanOrEqual=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesBySequenceNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sequenceNo is less than or equal to DEFAULT_SEQUENCE_NO
        defaultProductCategoryShouldBeFound("sequenceNo.lessThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the productCategoryList where sequenceNo is less than or equal to SMALLER_SEQUENCE_NO
        defaultProductCategoryShouldNotBeFound("sequenceNo.lessThanOrEqual=" + SMALLER_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesBySequenceNoIsLessThanSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sequenceNo is less than DEFAULT_SEQUENCE_NO
        defaultProductCategoryShouldNotBeFound("sequenceNo.lessThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the productCategoryList where sequenceNo is less than UPDATED_SEQUENCE_NO
        defaultProductCategoryShouldBeFound("sequenceNo.lessThan=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesBySequenceNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sequenceNo is greater than DEFAULT_SEQUENCE_NO
        defaultProductCategoryShouldNotBeFound("sequenceNo.greaterThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the productCategoryList where sequenceNo is greater than SMALLER_SEQUENCE_NO
        defaultProductCategoryShouldBeFound("sequenceNo.greaterThan=" + SMALLER_SEQUENCE_NO);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultProductCategoryShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the productCategoryList where imageUrl equals to UPDATED_IMAGE_URL
        defaultProductCategoryShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultProductCategoryShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the productCategoryList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultProductCategoryShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultProductCategoryShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the productCategoryList where imageUrl equals to UPDATED_IMAGE_URL
        defaultProductCategoryShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where imageUrl is not null
        defaultProductCategoryShouldBeFound("imageUrl.specified=true");

        // Get all the productCategoryList where imageUrl is null
        defaultProductCategoryShouldNotBeFound("imageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoriesByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where imageUrl contains DEFAULT_IMAGE_URL
        defaultProductCategoryShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the productCategoryList where imageUrl contains UPDATED_IMAGE_URL
        defaultProductCategoryShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultProductCategoryShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the productCategoryList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultProductCategoryShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByAltImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where altImageUrl equals to DEFAULT_ALT_IMAGE_URL
        defaultProductCategoryShouldBeFound("altImageUrl.equals=" + DEFAULT_ALT_IMAGE_URL);

        // Get all the productCategoryList where altImageUrl equals to UPDATED_ALT_IMAGE_URL
        defaultProductCategoryShouldNotBeFound("altImageUrl.equals=" + UPDATED_ALT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByAltImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where altImageUrl not equals to DEFAULT_ALT_IMAGE_URL
        defaultProductCategoryShouldNotBeFound("altImageUrl.notEquals=" + DEFAULT_ALT_IMAGE_URL);

        // Get all the productCategoryList where altImageUrl not equals to UPDATED_ALT_IMAGE_URL
        defaultProductCategoryShouldBeFound("altImageUrl.notEquals=" + UPDATED_ALT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByAltImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where altImageUrl in DEFAULT_ALT_IMAGE_URL or UPDATED_ALT_IMAGE_URL
        defaultProductCategoryShouldBeFound("altImageUrl.in=" + DEFAULT_ALT_IMAGE_URL + "," + UPDATED_ALT_IMAGE_URL);

        // Get all the productCategoryList where altImageUrl equals to UPDATED_ALT_IMAGE_URL
        defaultProductCategoryShouldNotBeFound("altImageUrl.in=" + UPDATED_ALT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByAltImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where altImageUrl is not null
        defaultProductCategoryShouldBeFound("altImageUrl.specified=true");

        // Get all the productCategoryList where altImageUrl is null
        defaultProductCategoryShouldNotBeFound("altImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductCategoriesByAltImageUrlContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where altImageUrl contains DEFAULT_ALT_IMAGE_URL
        defaultProductCategoryShouldBeFound("altImageUrl.contains=" + DEFAULT_ALT_IMAGE_URL);

        // Get all the productCategoryList where altImageUrl contains UPDATED_ALT_IMAGE_URL
        defaultProductCategoryShouldNotBeFound("altImageUrl.contains=" + UPDATED_ALT_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductCategoriesByAltImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where altImageUrl does not contain DEFAULT_ALT_IMAGE_URL
        defaultProductCategoryShouldNotBeFound("altImageUrl.doesNotContain=" + DEFAULT_ALT_IMAGE_URL);

        // Get all the productCategoryList where altImageUrl does not contain UPDATED_ALT_IMAGE_URL
        defaultProductCategoryShouldBeFound("altImageUrl.doesNotContain=" + UPDATED_ALT_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByProductCategoryTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);
        ProductCategoryType productCategoryType = ProductCategoryTypeResourceIT.createEntity(em);
        em.persist(productCategoryType);
        em.flush();
        productCategory.setProductCategoryType(productCategoryType);
        productCategoryRepository.saveAndFlush(productCategory);
        Long productCategoryTypeId = productCategoryType.getId();

        // Get all the productCategoryList where productCategoryType equals to productCategoryTypeId
        defaultProductCategoryShouldBeFound("productCategoryTypeId.equals=" + productCategoryTypeId);

        // Get all the productCategoryList where productCategoryType equals to productCategoryTypeId + 1
        defaultProductCategoryShouldNotBeFound("productCategoryTypeId.equals=" + (productCategoryTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllProductCategoriesByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);
        ProductCategory parent = ProductCategoryResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        productCategory.setParent(parent);
        productCategoryRepository.saveAndFlush(productCategory);
        Long parentId = parent.getId();

        // Get all the productCategoryList where parent equals to parentId
        defaultProductCategoryShouldBeFound("parentId.equals=" + parentId);

        // Get all the productCategoryList where parent equals to parentId + 1
        defaultProductCategoryShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductCategoryShouldBeFound(String filter) throws Exception {
        restProductCategoryMockMvc.perform(get("/api/product-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].attribute").value(hasItem(DEFAULT_ATTRIBUTE)))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].altImageUrl").value(hasItem(DEFAULT_ALT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO.toString())));

        // Check, that the count call also returns 1
        restProductCategoryMockMvc.perform(get("/api/product-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductCategoryShouldNotBeFound(String filter) throws Exception {
        restProductCategoryMockMvc.perform(get("/api/product-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductCategoryMockMvc.perform(get("/api/product-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductCategory() throws Exception {
        // Get the productCategory
        restProductCategoryMockMvc.perform(get("/api/product-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductCategory() throws Exception {
        // Initialize the database
        productCategoryService.save(productCategory);

        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();

        // Update the productCategory
        ProductCategory updatedProductCategory = productCategoryRepository.findById(productCategory.getId()).get();
        // Disconnect from session so that the updates on updatedProductCategory are not directly saved in db
        em.detach(updatedProductCategory);
        updatedProductCategory
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .attribute(UPDATED_ATTRIBUTE)
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .imageUrl(UPDATED_IMAGE_URL)
            .altImageUrl(UPDATED_ALT_IMAGE_URL)
            .info(UPDATED_INFO);

        restProductCategoryMockMvc.perform(put("/api/product-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductCategory)))
            .andExpect(status().isOk());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
        ProductCategory testProductCategory = productCategoryList.get(productCategoryList.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductCategory.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testProductCategory.getAttribute()).isEqualTo(UPDATED_ATTRIBUTE);
        assertThat(testProductCategory.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
        assertThat(testProductCategory.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testProductCategory.getAltImageUrl()).isEqualTo(UPDATED_ALT_IMAGE_URL);
        assertThat(testProductCategory.getInfo()).isEqualTo(UPDATED_INFO);
    }

    @Test
    @Transactional
    public void updateNonExistingProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCategoryMockMvc.perform(put("/api/product-categories")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productCategory)))
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductCategory() throws Exception {
        // Initialize the database
        productCategoryService.save(productCategory);

        int databaseSizeBeforeDelete = productCategoryRepository.findAll().size();

        // Delete the productCategory
        restProductCategoryMockMvc.perform(delete("/api/product-categories/{id}", productCategory.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
