package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ProductStore;
import com.hr.domain.ProductStoreType;
import com.hr.domain.ProductStore;
import com.hr.domain.Party;
import com.hr.domain.PostalAddress;
import com.hr.repository.ProductStoreRepository;
import com.hr.service.ProductStoreService;
import com.hr.service.dto.ProductStoreCriteria;
import com.hr.service.ProductStoreQueryService;

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
 * Integration tests for the {@link ProductStoreResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductStoreResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SUBTITLE = "AAAAAAAAAA";
    private static final String UPDATED_SUBTITLE = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    @Autowired
    private ProductStoreRepository productStoreRepository;

    @Autowired
    private ProductStoreService productStoreService;

    @Autowired
    private ProductStoreQueryService productStoreQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductStoreMockMvc;

    private ProductStore productStore;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductStore createEntity(EntityManager em) {
        ProductStore productStore = new ProductStore()
            .name(DEFAULT_NAME)
            .title(DEFAULT_TITLE)
            .subtitle(DEFAULT_SUBTITLE)
            .imageUrl(DEFAULT_IMAGE_URL)
            .comments(DEFAULT_COMMENTS)
            .code(DEFAULT_CODE);
        return productStore;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductStore createUpdatedEntity(EntityManager em) {
        ProductStore productStore = new ProductStore()
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .subtitle(UPDATED_SUBTITLE)
            .imageUrl(UPDATED_IMAGE_URL)
            .comments(UPDATED_COMMENTS)
            .code(UPDATED_CODE);
        return productStore;
    }

    @BeforeEach
    public void initTest() {
        productStore = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductStore() throws Exception {
        int databaseSizeBeforeCreate = productStoreRepository.findAll().size();
        // Create the ProductStore
        restProductStoreMockMvc.perform(post("/api/product-stores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStore)))
            .andExpect(status().isCreated());

        // Validate the ProductStore in the database
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeCreate + 1);
        ProductStore testProductStore = productStoreList.get(productStoreList.size() - 1);
        assertThat(testProductStore.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductStore.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testProductStore.getSubtitle()).isEqualTo(DEFAULT_SUBTITLE);
        assertThat(testProductStore.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testProductStore.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testProductStore.getCode()).isEqualTo(DEFAULT_CODE);
    }

    @Test
    @Transactional
    public void createProductStoreWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productStoreRepository.findAll().size();

        // Create the ProductStore with an existing ID
        productStore.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductStoreMockMvc.perform(post("/api/product-stores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStore)))
            .andExpect(status().isBadRequest());

        // Validate the ProductStore in the database
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductStores() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList
        restProductStoreMockMvc.perform(get("/api/product-stores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productStore.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].subtitle").value(hasItem(DEFAULT_SUBTITLE)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));
    }
    
    @Test
    @Transactional
    public void getProductStore() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get the productStore
        restProductStoreMockMvc.perform(get("/api/product-stores/{id}", productStore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productStore.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.subtitle").value(DEFAULT_SUBTITLE))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE));
    }


    @Test
    @Transactional
    public void getProductStoresByIdFiltering() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        Long id = productStore.getId();

        defaultProductStoreShouldBeFound("id.equals=" + id);
        defaultProductStoreShouldNotBeFound("id.notEquals=" + id);

        defaultProductStoreShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductStoreShouldNotBeFound("id.greaterThan=" + id);

        defaultProductStoreShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductStoreShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductStoresByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where name equals to DEFAULT_NAME
        defaultProductStoreShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productStoreList where name equals to UPDATED_NAME
        defaultProductStoreShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductStoresByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where name not equals to DEFAULT_NAME
        defaultProductStoreShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productStoreList where name not equals to UPDATED_NAME
        defaultProductStoreShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductStoresByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductStoreShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productStoreList where name equals to UPDATED_NAME
        defaultProductStoreShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductStoresByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where name is not null
        defaultProductStoreShouldBeFound("name.specified=true");

        // Get all the productStoreList where name is null
        defaultProductStoreShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductStoresByNameContainsSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where name contains DEFAULT_NAME
        defaultProductStoreShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productStoreList where name contains UPDATED_NAME
        defaultProductStoreShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductStoresByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where name does not contain DEFAULT_NAME
        defaultProductStoreShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productStoreList where name does not contain UPDATED_NAME
        defaultProductStoreShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductStoresByTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where title equals to DEFAULT_TITLE
        defaultProductStoreShouldBeFound("title.equals=" + DEFAULT_TITLE);

        // Get all the productStoreList where title equals to UPDATED_TITLE
        defaultProductStoreShouldNotBeFound("title.equals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllProductStoresByTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where title not equals to DEFAULT_TITLE
        defaultProductStoreShouldNotBeFound("title.notEquals=" + DEFAULT_TITLE);

        // Get all the productStoreList where title not equals to UPDATED_TITLE
        defaultProductStoreShouldBeFound("title.notEquals=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllProductStoresByTitleIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where title in DEFAULT_TITLE or UPDATED_TITLE
        defaultProductStoreShouldBeFound("title.in=" + DEFAULT_TITLE + "," + UPDATED_TITLE);

        // Get all the productStoreList where title equals to UPDATED_TITLE
        defaultProductStoreShouldNotBeFound("title.in=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllProductStoresByTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where title is not null
        defaultProductStoreShouldBeFound("title.specified=true");

        // Get all the productStoreList where title is null
        defaultProductStoreShouldNotBeFound("title.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductStoresByTitleContainsSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where title contains DEFAULT_TITLE
        defaultProductStoreShouldBeFound("title.contains=" + DEFAULT_TITLE);

        // Get all the productStoreList where title contains UPDATED_TITLE
        defaultProductStoreShouldNotBeFound("title.contains=" + UPDATED_TITLE);
    }

    @Test
    @Transactional
    public void getAllProductStoresByTitleNotContainsSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where title does not contain DEFAULT_TITLE
        defaultProductStoreShouldNotBeFound("title.doesNotContain=" + DEFAULT_TITLE);

        // Get all the productStoreList where title does not contain UPDATED_TITLE
        defaultProductStoreShouldBeFound("title.doesNotContain=" + UPDATED_TITLE);
    }


    @Test
    @Transactional
    public void getAllProductStoresBySubtitleIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where subtitle equals to DEFAULT_SUBTITLE
        defaultProductStoreShouldBeFound("subtitle.equals=" + DEFAULT_SUBTITLE);

        // Get all the productStoreList where subtitle equals to UPDATED_SUBTITLE
        defaultProductStoreShouldNotBeFound("subtitle.equals=" + UPDATED_SUBTITLE);
    }

    @Test
    @Transactional
    public void getAllProductStoresBySubtitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where subtitle not equals to DEFAULT_SUBTITLE
        defaultProductStoreShouldNotBeFound("subtitle.notEquals=" + DEFAULT_SUBTITLE);

        // Get all the productStoreList where subtitle not equals to UPDATED_SUBTITLE
        defaultProductStoreShouldBeFound("subtitle.notEquals=" + UPDATED_SUBTITLE);
    }

    @Test
    @Transactional
    public void getAllProductStoresBySubtitleIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where subtitle in DEFAULT_SUBTITLE or UPDATED_SUBTITLE
        defaultProductStoreShouldBeFound("subtitle.in=" + DEFAULT_SUBTITLE + "," + UPDATED_SUBTITLE);

        // Get all the productStoreList where subtitle equals to UPDATED_SUBTITLE
        defaultProductStoreShouldNotBeFound("subtitle.in=" + UPDATED_SUBTITLE);
    }

    @Test
    @Transactional
    public void getAllProductStoresBySubtitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where subtitle is not null
        defaultProductStoreShouldBeFound("subtitle.specified=true");

        // Get all the productStoreList where subtitle is null
        defaultProductStoreShouldNotBeFound("subtitle.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductStoresBySubtitleContainsSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where subtitle contains DEFAULT_SUBTITLE
        defaultProductStoreShouldBeFound("subtitle.contains=" + DEFAULT_SUBTITLE);

        // Get all the productStoreList where subtitle contains UPDATED_SUBTITLE
        defaultProductStoreShouldNotBeFound("subtitle.contains=" + UPDATED_SUBTITLE);
    }

    @Test
    @Transactional
    public void getAllProductStoresBySubtitleNotContainsSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where subtitle does not contain DEFAULT_SUBTITLE
        defaultProductStoreShouldNotBeFound("subtitle.doesNotContain=" + DEFAULT_SUBTITLE);

        // Get all the productStoreList where subtitle does not contain UPDATED_SUBTITLE
        defaultProductStoreShouldBeFound("subtitle.doesNotContain=" + UPDATED_SUBTITLE);
    }


    @Test
    @Transactional
    public void getAllProductStoresByImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where imageUrl equals to DEFAULT_IMAGE_URL
        defaultProductStoreShouldBeFound("imageUrl.equals=" + DEFAULT_IMAGE_URL);

        // Get all the productStoreList where imageUrl equals to UPDATED_IMAGE_URL
        defaultProductStoreShouldNotBeFound("imageUrl.equals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductStoresByImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where imageUrl not equals to DEFAULT_IMAGE_URL
        defaultProductStoreShouldNotBeFound("imageUrl.notEquals=" + DEFAULT_IMAGE_URL);

        // Get all the productStoreList where imageUrl not equals to UPDATED_IMAGE_URL
        defaultProductStoreShouldBeFound("imageUrl.notEquals=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductStoresByImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where imageUrl in DEFAULT_IMAGE_URL or UPDATED_IMAGE_URL
        defaultProductStoreShouldBeFound("imageUrl.in=" + DEFAULT_IMAGE_URL + "," + UPDATED_IMAGE_URL);

        // Get all the productStoreList where imageUrl equals to UPDATED_IMAGE_URL
        defaultProductStoreShouldNotBeFound("imageUrl.in=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductStoresByImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where imageUrl is not null
        defaultProductStoreShouldBeFound("imageUrl.specified=true");

        // Get all the productStoreList where imageUrl is null
        defaultProductStoreShouldNotBeFound("imageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductStoresByImageUrlContainsSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where imageUrl contains DEFAULT_IMAGE_URL
        defaultProductStoreShouldBeFound("imageUrl.contains=" + DEFAULT_IMAGE_URL);

        // Get all the productStoreList where imageUrl contains UPDATED_IMAGE_URL
        defaultProductStoreShouldNotBeFound("imageUrl.contains=" + UPDATED_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductStoresByImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where imageUrl does not contain DEFAULT_IMAGE_URL
        defaultProductStoreShouldNotBeFound("imageUrl.doesNotContain=" + DEFAULT_IMAGE_URL);

        // Get all the productStoreList where imageUrl does not contain UPDATED_IMAGE_URL
        defaultProductStoreShouldBeFound("imageUrl.doesNotContain=" + UPDATED_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllProductStoresByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where comments equals to DEFAULT_COMMENTS
        defaultProductStoreShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the productStoreList where comments equals to UPDATED_COMMENTS
        defaultProductStoreShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllProductStoresByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where comments not equals to DEFAULT_COMMENTS
        defaultProductStoreShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the productStoreList where comments not equals to UPDATED_COMMENTS
        defaultProductStoreShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllProductStoresByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultProductStoreShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the productStoreList where comments equals to UPDATED_COMMENTS
        defaultProductStoreShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllProductStoresByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where comments is not null
        defaultProductStoreShouldBeFound("comments.specified=true");

        // Get all the productStoreList where comments is null
        defaultProductStoreShouldNotBeFound("comments.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductStoresByCommentsContainsSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where comments contains DEFAULT_COMMENTS
        defaultProductStoreShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the productStoreList where comments contains UPDATED_COMMENTS
        defaultProductStoreShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllProductStoresByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where comments does not contain DEFAULT_COMMENTS
        defaultProductStoreShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the productStoreList where comments does not contain UPDATED_COMMENTS
        defaultProductStoreShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllProductStoresByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where code equals to DEFAULT_CODE
        defaultProductStoreShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the productStoreList where code equals to UPDATED_CODE
        defaultProductStoreShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProductStoresByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where code not equals to DEFAULT_CODE
        defaultProductStoreShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the productStoreList where code not equals to UPDATED_CODE
        defaultProductStoreShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProductStoresByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProductStoreShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the productStoreList where code equals to UPDATED_CODE
        defaultProductStoreShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProductStoresByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where code is not null
        defaultProductStoreShouldBeFound("code.specified=true");

        // Get all the productStoreList where code is null
        defaultProductStoreShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductStoresByCodeContainsSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where code contains DEFAULT_CODE
        defaultProductStoreShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the productStoreList where code contains UPDATED_CODE
        defaultProductStoreShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllProductStoresByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList where code does not contain DEFAULT_CODE
        defaultProductStoreShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the productStoreList where code does not contain UPDATED_CODE
        defaultProductStoreShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllProductStoresByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);
        ProductStoreType type = ProductStoreTypeResourceIT.createEntity(em);
        em.persist(type);
        em.flush();
        productStore.setType(type);
        productStoreRepository.saveAndFlush(productStore);
        Long typeId = type.getId();

        // Get all the productStoreList where type equals to typeId
        defaultProductStoreShouldBeFound("typeId.equals=" + typeId);

        // Get all the productStoreList where type equals to typeId + 1
        defaultProductStoreShouldNotBeFound("typeId.equals=" + (typeId + 1));
    }


    @Test
    @Transactional
    public void getAllProductStoresByParentIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);
        ProductStore parent = ProductStoreResourceIT.createEntity(em);
        em.persist(parent);
        em.flush();
        productStore.setParent(parent);
        productStoreRepository.saveAndFlush(productStore);
        Long parentId = parent.getId();

        // Get all the productStoreList where parent equals to parentId
        defaultProductStoreShouldBeFound("parentId.equals=" + parentId);

        // Get all the productStoreList where parent equals to parentId + 1
        defaultProductStoreShouldNotBeFound("parentId.equals=" + (parentId + 1));
    }


    @Test
    @Transactional
    public void getAllProductStoresByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);
        Party owner = PartyResourceIT.createEntity(em);
        em.persist(owner);
        em.flush();
        productStore.setOwner(owner);
        productStoreRepository.saveAndFlush(productStore);
        Long ownerId = owner.getId();

        // Get all the productStoreList where owner equals to ownerId
        defaultProductStoreShouldBeFound("ownerId.equals=" + ownerId);

        // Get all the productStoreList where owner equals to ownerId + 1
        defaultProductStoreShouldNotBeFound("ownerId.equals=" + (ownerId + 1));
    }


    @Test
    @Transactional
    public void getAllProductStoresByPostalAddressIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);
        PostalAddress postalAddress = PostalAddressResourceIT.createEntity(em);
        em.persist(postalAddress);
        em.flush();
        productStore.setPostalAddress(postalAddress);
        productStoreRepository.saveAndFlush(productStore);
        Long postalAddressId = postalAddress.getId();

        // Get all the productStoreList where postalAddress equals to postalAddressId
        defaultProductStoreShouldBeFound("postalAddressId.equals=" + postalAddressId);

        // Get all the productStoreList where postalAddress equals to postalAddressId + 1
        defaultProductStoreShouldNotBeFound("postalAddressId.equals=" + (postalAddressId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductStoreShouldBeFound(String filter) throws Exception {
        restProductStoreMockMvc.perform(get("/api/product-stores?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productStore.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].subtitle").value(hasItem(DEFAULT_SUBTITLE)))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)));

        // Check, that the count call also returns 1
        restProductStoreMockMvc.perform(get("/api/product-stores/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductStoreShouldNotBeFound(String filter) throws Exception {
        restProductStoreMockMvc.perform(get("/api/product-stores?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductStoreMockMvc.perform(get("/api/product-stores/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductStore() throws Exception {
        // Get the productStore
        restProductStoreMockMvc.perform(get("/api/product-stores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductStore() throws Exception {
        // Initialize the database
        productStoreService.save(productStore);

        int databaseSizeBeforeUpdate = productStoreRepository.findAll().size();

        // Update the productStore
        ProductStore updatedProductStore = productStoreRepository.findById(productStore.getId()).get();
        // Disconnect from session so that the updates on updatedProductStore are not directly saved in db
        em.detach(updatedProductStore);
        updatedProductStore
            .name(UPDATED_NAME)
            .title(UPDATED_TITLE)
            .subtitle(UPDATED_SUBTITLE)
            .imageUrl(UPDATED_IMAGE_URL)
            .comments(UPDATED_COMMENTS)
            .code(UPDATED_CODE);

        restProductStoreMockMvc.perform(put("/api/product-stores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductStore)))
            .andExpect(status().isOk());

        // Validate the ProductStore in the database
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeUpdate);
        ProductStore testProductStore = productStoreList.get(productStoreList.size() - 1);
        assertThat(testProductStore.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductStore.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testProductStore.getSubtitle()).isEqualTo(UPDATED_SUBTITLE);
        assertThat(testProductStore.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testProductStore.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testProductStore.getCode()).isEqualTo(UPDATED_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductStore() throws Exception {
        int databaseSizeBeforeUpdate = productStoreRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductStoreMockMvc.perform(put("/api/product-stores")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStore)))
            .andExpect(status().isBadRequest());

        // Validate the ProductStore in the database
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductStore() throws Exception {
        // Initialize the database
        productStoreService.save(productStore);

        int databaseSizeBeforeDelete = productStoreRepository.findAll().size();

        // Delete the productStore
        restProductStoreMockMvc.perform(delete("/api/product-stores/{id}", productStore.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
