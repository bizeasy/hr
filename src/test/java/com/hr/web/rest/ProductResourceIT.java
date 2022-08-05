package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.Product;
import com.hr.domain.ProductType;
import com.hr.domain.Uom;
import com.hr.domain.ProductCategory;
import com.hr.domain.Product;
import com.hr.domain.InventoryItemType;
import com.hr.domain.TaxSlab;
import com.hr.repository.ProductRepository;
import com.hr.service.ProductService;
import com.hr.service.dto.ProductCriteria;
import com.hr.service.ProductQueryService;

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
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.hr.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BRAND_NAME = "AAAAAAAAAA";
    private static final String UPDATED_BRAND_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VARIANT = "AAAAAAAAAA";
    private static final String UPDATED_VARIANT = "BBBBBBBBBB";

    private static final String DEFAULT_SKU = "AAAAAAAAAA";
    private static final String UPDATED_SKU = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_INTRODUCTION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_INTRODUCTION_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_INTRODUCTION_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_RELEASE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_RELEASE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_RELEASE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final BigDecimal DEFAULT_QUANTITY_INCLUDED = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY_INCLUDED = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY_INCLUDED = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_PIECES_INCLUDED = 1;
    private static final Integer UPDATED_PIECES_INCLUDED = 2;
    private static final Integer SMALLER_PIECES_INCLUDED = 1 - 1;

    private static final BigDecimal DEFAULT_WEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_WEIGHT = new BigDecimal(2);
    private static final BigDecimal SMALLER_WEIGHT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_HEIGHT = new BigDecimal(1);
    private static final BigDecimal UPDATED_HEIGHT = new BigDecimal(2);
    private static final BigDecimal SMALLER_HEIGHT = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_WIDTH = new BigDecimal(1);
    private static final BigDecimal UPDATED_WIDTH = new BigDecimal(2);
    private static final BigDecimal SMALLER_WIDTH = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_DEPTH = new BigDecimal(1);
    private static final BigDecimal UPDATED_DEPTH = new BigDecimal(2);
    private static final BigDecimal SMALLER_DEPTH = new BigDecimal(1 - 1);

    private static final String DEFAULT_SMALL_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SMALL_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_MEDIUM_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_MEDIUM_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LARGE_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_LARGE_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DETAIL_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_DETAIL_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAL_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_IMAGE_URL = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_QUANTITY = new BigDecimal(1);
    private static final BigDecimal UPDATED_QUANTITY = new BigDecimal(2);
    private static final BigDecimal SMALLER_QUANTITY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CGST = new BigDecimal(1);
    private static final BigDecimal UPDATED_CGST = new BigDecimal(2);
    private static final BigDecimal SMALLER_CGST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_IGST = new BigDecimal(1);
    private static final BigDecimal UPDATED_IGST = new BigDecimal(2);
    private static final BigDecimal SMALLER_IGST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SGST = new BigDecimal(1);
    private static final BigDecimal UPDATED_SGST = new BigDecimal(2);
    private static final BigDecimal SMALLER_SGST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_CGST_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_CGST_PERCENTAGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_CGST_PERCENTAGE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_IGST_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_IGST_PERCENTAGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_IGST_PERCENTAGE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SGST_PERCENTAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SGST_PERCENTAGE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SGST_PERCENTAGE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_PRICE = new BigDecimal(1 - 1);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LONG_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_LONG_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_INFO = "AAAAAAAAAA";
    private static final String UPDATED_INFO = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VIRTUAL = false;
    private static final Boolean UPDATED_IS_VIRTUAL = true;

    private static final Boolean DEFAULT_IS_VARIANT = false;
    private static final Boolean UPDATED_IS_VARIANT = true;

    private static final Boolean DEFAULT_REQUIRE_INVENTORY = false;
    private static final Boolean UPDATED_REQUIRE_INVENTORY = true;

    private static final Boolean DEFAULT_RETURNABLE = false;
    private static final Boolean UPDATED_RETURNABLE = true;

    private static final Boolean DEFAULT_IS_POPULAR = false;
    private static final Boolean UPDATED_IS_POPULAR = true;

    private static final String DEFAULT_CHANGE_CONTROL_NO = "AAAAAAAAAA";
    private static final String UPDATED_CHANGE_CONTROL_NO = "BBBBBBBBBB";

    private static final Duration DEFAULT_RETEST_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_RETEST_DURATION = Duration.ofHours(12);
    private static final Duration SMALLER_RETEST_DURATION = Duration.ofHours(5);

    private static final Duration DEFAULT_EXPIRY_DURATION = Duration.ofHours(6);
    private static final Duration UPDATED_EXPIRY_DURATION = Duration.ofHours(12);
    private static final Duration SMALLER_EXPIRY_DURATION = Duration.ofHours(5);

    private static final String DEFAULT_VALIDATION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_VALIDATION_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_SHELF_LIFE = 1;
    private static final Integer UPDATED_SHELF_LIFE = 2;
    private static final Integer SMALLER_SHELF_LIFE = 1 - 1;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductQueryService productQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMockMvc;

    private Product product;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .name(DEFAULT_NAME)
            .internalName(DEFAULT_INTERNAL_NAME)
            .brandName(DEFAULT_BRAND_NAME)
            .variant(DEFAULT_VARIANT)
            .sku(DEFAULT_SKU)
            .introductionDate(DEFAULT_INTRODUCTION_DATE)
            .releaseDate(DEFAULT_RELEASE_DATE)
            .quantityIncluded(DEFAULT_QUANTITY_INCLUDED)
            .piecesIncluded(DEFAULT_PIECES_INCLUDED)
            .weight(DEFAULT_WEIGHT)
            .height(DEFAULT_HEIGHT)
            .width(DEFAULT_WIDTH)
            .depth(DEFAULT_DEPTH)
            .smallImageUrl(DEFAULT_SMALL_IMAGE_URL)
            .mediumImageUrl(DEFAULT_MEDIUM_IMAGE_URL)
            .largeImageUrl(DEFAULT_LARGE_IMAGE_URL)
            .detailImageUrl(DEFAULT_DETAIL_IMAGE_URL)
            .originalImageUrl(DEFAULT_ORIGINAL_IMAGE_URL)
            .quantity(DEFAULT_QUANTITY)
            .cgst(DEFAULT_CGST)
            .igst(DEFAULT_IGST)
            .sgst(DEFAULT_SGST)
            .price(DEFAULT_PRICE)
            .cgstPercentage(DEFAULT_CGST_PERCENTAGE)
            .igstPercentage(DEFAULT_IGST_PERCENTAGE)
            .sgstPercentage(DEFAULT_SGST_PERCENTAGE)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .description(DEFAULT_DESCRIPTION)
            .longDescription(DEFAULT_LONG_DESCRIPTION)
            .info(DEFAULT_INFO)
            .isVirtual(DEFAULT_IS_VIRTUAL)
            .isVariant(DEFAULT_IS_VARIANT)
            .requireInventory(DEFAULT_REQUIRE_INVENTORY)
            .returnable(DEFAULT_RETURNABLE)
            .isPopular(DEFAULT_IS_POPULAR)
            .changeControlNo(DEFAULT_CHANGE_CONTROL_NO)
            .retestDuration(DEFAULT_RETEST_DURATION)
            .expiryDuration(DEFAULT_EXPIRY_DURATION)
            .validationType(DEFAULT_VALIDATION_TYPE)
            .shelfLife(DEFAULT_SHELF_LIFE);
        return product;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .name(UPDATED_NAME)
            .internalName(UPDATED_INTERNAL_NAME)
            .brandName(UPDATED_BRAND_NAME)
            .variant(UPDATED_VARIANT)
            .sku(UPDATED_SKU)
            .introductionDate(UPDATED_INTRODUCTION_DATE)
            .releaseDate(UPDATED_RELEASE_DATE)
            .quantityIncluded(UPDATED_QUANTITY_INCLUDED)
            .piecesIncluded(UPDATED_PIECES_INCLUDED)
            .weight(UPDATED_WEIGHT)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .depth(UPDATED_DEPTH)
            .smallImageUrl(UPDATED_SMALL_IMAGE_URL)
            .mediumImageUrl(UPDATED_MEDIUM_IMAGE_URL)
            .largeImageUrl(UPDATED_LARGE_IMAGE_URL)
            .detailImageUrl(UPDATED_DETAIL_IMAGE_URL)
            .originalImageUrl(UPDATED_ORIGINAL_IMAGE_URL)
            .quantity(UPDATED_QUANTITY)
            .cgst(UPDATED_CGST)
            .igst(UPDATED_IGST)
            .sgst(UPDATED_SGST)
            .price(UPDATED_PRICE)
            .cgstPercentage(UPDATED_CGST_PERCENTAGE)
            .igstPercentage(UPDATED_IGST_PERCENTAGE)
            .sgstPercentage(UPDATED_SGST_PERCENTAGE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .description(UPDATED_DESCRIPTION)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .info(UPDATED_INFO)
            .isVirtual(UPDATED_IS_VIRTUAL)
            .isVariant(UPDATED_IS_VARIANT)
            .requireInventory(UPDATED_REQUIRE_INVENTORY)
            .returnable(UPDATED_RETURNABLE)
            .isPopular(UPDATED_IS_POPULAR)
            .changeControlNo(UPDATED_CHANGE_CONTROL_NO)
            .retestDuration(UPDATED_RETEST_DURATION)
            .expiryDuration(UPDATED_EXPIRY_DURATION)
            .validationType(UPDATED_VALIDATION_TYPE)
            .shelfLife(UPDATED_SHELF_LIFE);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();
        // Create the Product
        restProductMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getInternalName()).isEqualTo(DEFAULT_INTERNAL_NAME);
        assertThat(testProduct.getBrandName()).isEqualTo(DEFAULT_BRAND_NAME);
        assertThat(testProduct.getVariant()).isEqualTo(DEFAULT_VARIANT);
        assertThat(testProduct.getSku()).isEqualTo(DEFAULT_SKU);
        assertThat(testProduct.getIntroductionDate()).isEqualTo(DEFAULT_INTRODUCTION_DATE);
        assertThat(testProduct.getReleaseDate()).isEqualTo(DEFAULT_RELEASE_DATE);
        assertThat(testProduct.getQuantityIncluded()).isEqualTo(DEFAULT_QUANTITY_INCLUDED);
        assertThat(testProduct.getPiecesIncluded()).isEqualTo(DEFAULT_PIECES_INCLUDED);
        assertThat(testProduct.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testProduct.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testProduct.getWidth()).isEqualTo(DEFAULT_WIDTH);
        assertThat(testProduct.getDepth()).isEqualTo(DEFAULT_DEPTH);
        assertThat(testProduct.getSmallImageUrl()).isEqualTo(DEFAULT_SMALL_IMAGE_URL);
        assertThat(testProduct.getMediumImageUrl()).isEqualTo(DEFAULT_MEDIUM_IMAGE_URL);
        assertThat(testProduct.getLargeImageUrl()).isEqualTo(DEFAULT_LARGE_IMAGE_URL);
        assertThat(testProduct.getDetailImageUrl()).isEqualTo(DEFAULT_DETAIL_IMAGE_URL);
        assertThat(testProduct.getOriginalImageUrl()).isEqualTo(DEFAULT_ORIGINAL_IMAGE_URL);
        assertThat(testProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProduct.getCgst()).isEqualTo(DEFAULT_CGST);
        assertThat(testProduct.getIgst()).isEqualTo(DEFAULT_IGST);
        assertThat(testProduct.getSgst()).isEqualTo(DEFAULT_SGST);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.getCgstPercentage()).isEqualTo(DEFAULT_CGST_PERCENTAGE);
        assertThat(testProduct.getIgstPercentage()).isEqualTo(DEFAULT_IGST_PERCENTAGE);
        assertThat(testProduct.getSgstPercentage()).isEqualTo(DEFAULT_SGST_PERCENTAGE);
        assertThat(testProduct.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testProduct.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProduct.getLongDescription()).isEqualTo(DEFAULT_LONG_DESCRIPTION);
        assertThat(testProduct.getInfo()).isEqualTo(DEFAULT_INFO);
        assertThat(testProduct.isIsVirtual()).isEqualTo(DEFAULT_IS_VIRTUAL);
        assertThat(testProduct.isIsVariant()).isEqualTo(DEFAULT_IS_VARIANT);
        assertThat(testProduct.isRequireInventory()).isEqualTo(DEFAULT_REQUIRE_INVENTORY);
        assertThat(testProduct.isReturnable()).isEqualTo(DEFAULT_RETURNABLE);
        assertThat(testProduct.isIsPopular()).isEqualTo(DEFAULT_IS_POPULAR);
        assertThat(testProduct.getChangeControlNo()).isEqualTo(DEFAULT_CHANGE_CONTROL_NO);
        assertThat(testProduct.getRetestDuration()).isEqualTo(DEFAULT_RETEST_DURATION);
        assertThat(testProduct.getExpiryDuration()).isEqualTo(DEFAULT_EXPIRY_DURATION);
        assertThat(testProduct.getValidationType()).isEqualTo(DEFAULT_VALIDATION_TYPE);
        assertThat(testProduct.getShelfLife()).isEqualTo(DEFAULT_SHELF_LIFE);
    }

    @Test
    @Transactional
    public void createProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product with an existing ID
        product.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc.perform(post("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].internalName").value(hasItem(DEFAULT_INTERNAL_NAME)))
            .andExpect(jsonPath("$.[*].brandName").value(hasItem(DEFAULT_BRAND_NAME)))
            .andExpect(jsonPath("$.[*].variant").value(hasItem(DEFAULT_VARIANT)))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU)))
            .andExpect(jsonPath("$.[*].introductionDate").value(hasItem(sameInstant(DEFAULT_INTRODUCTION_DATE))))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(sameInstant(DEFAULT_RELEASE_DATE))))
            .andExpect(jsonPath("$.[*].quantityIncluded").value(hasItem(DEFAULT_QUANTITY_INCLUDED.intValue())))
            .andExpect(jsonPath("$.[*].piecesIncluded").value(hasItem(DEFAULT_PIECES_INCLUDED)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.intValue())))
            .andExpect(jsonPath("$.[*].depth").value(hasItem(DEFAULT_DEPTH.intValue())))
            .andExpect(jsonPath("$.[*].smallImageUrl").value(hasItem(DEFAULT_SMALL_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].mediumImageUrl").value(hasItem(DEFAULT_MEDIUM_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].largeImageUrl").value(hasItem(DEFAULT_LARGE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].detailImageUrl").value(hasItem(DEFAULT_DETAIL_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].originalImageUrl").value(hasItem(DEFAULT_ORIGINAL_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].cgst").value(hasItem(DEFAULT_CGST.intValue())))
            .andExpect(jsonPath("$.[*].igst").value(hasItem(DEFAULT_IGST.intValue())))
            .andExpect(jsonPath("$.[*].sgst").value(hasItem(DEFAULT_SGST.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].cgstPercentage").value(hasItem(DEFAULT_CGST_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].igstPercentage").value(hasItem(DEFAULT_IGST_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].sgstPercentage").value(hasItem(DEFAULT_SGST_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO.toString())))
            .andExpect(jsonPath("$.[*].isVirtual").value(hasItem(DEFAULT_IS_VIRTUAL.booleanValue())))
            .andExpect(jsonPath("$.[*].isVariant").value(hasItem(DEFAULT_IS_VARIANT.booleanValue())))
            .andExpect(jsonPath("$.[*].requireInventory").value(hasItem(DEFAULT_REQUIRE_INVENTORY.booleanValue())))
            .andExpect(jsonPath("$.[*].returnable").value(hasItem(DEFAULT_RETURNABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].isPopular").value(hasItem(DEFAULT_IS_POPULAR.booleanValue())))
            .andExpect(jsonPath("$.[*].changeControlNo").value(hasItem(DEFAULT_CHANGE_CONTROL_NO)))
            .andExpect(jsonPath("$.[*].retestDuration").value(hasItem(DEFAULT_RETEST_DURATION.toString())))
            .andExpect(jsonPath("$.[*].expiryDuration").value(hasItem(DEFAULT_EXPIRY_DURATION.toString())))
            .andExpect(jsonPath("$.[*].validationType").value(hasItem(DEFAULT_VALIDATION_TYPE)))
            .andExpect(jsonPath("$.[*].shelfLife").value(hasItem(DEFAULT_SHELF_LIFE)));
    }
    
    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.internalName").value(DEFAULT_INTERNAL_NAME))
            .andExpect(jsonPath("$.brandName").value(DEFAULT_BRAND_NAME))
            .andExpect(jsonPath("$.variant").value(DEFAULT_VARIANT))
            .andExpect(jsonPath("$.sku").value(DEFAULT_SKU))
            .andExpect(jsonPath("$.introductionDate").value(sameInstant(DEFAULT_INTRODUCTION_DATE)))
            .andExpect(jsonPath("$.releaseDate").value(sameInstant(DEFAULT_RELEASE_DATE)))
            .andExpect(jsonPath("$.quantityIncluded").value(DEFAULT_QUANTITY_INCLUDED.intValue()))
            .andExpect(jsonPath("$.piecesIncluded").value(DEFAULT_PIECES_INCLUDED))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.intValue()))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.intValue()))
            .andExpect(jsonPath("$.width").value(DEFAULT_WIDTH.intValue()))
            .andExpect(jsonPath("$.depth").value(DEFAULT_DEPTH.intValue()))
            .andExpect(jsonPath("$.smallImageUrl").value(DEFAULT_SMALL_IMAGE_URL))
            .andExpect(jsonPath("$.mediumImageUrl").value(DEFAULT_MEDIUM_IMAGE_URL))
            .andExpect(jsonPath("$.largeImageUrl").value(DEFAULT_LARGE_IMAGE_URL))
            .andExpect(jsonPath("$.detailImageUrl").value(DEFAULT_DETAIL_IMAGE_URL))
            .andExpect(jsonPath("$.originalImageUrl").value(DEFAULT_ORIGINAL_IMAGE_URL))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.cgst").value(DEFAULT_CGST.intValue()))
            .andExpect(jsonPath("$.igst").value(DEFAULT_IGST.intValue()))
            .andExpect(jsonPath("$.sgst").value(DEFAULT_SGST.intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.cgstPercentage").value(DEFAULT_CGST_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.igstPercentage").value(DEFAULT_IGST_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.sgstPercentage").value(DEFAULT_SGST_PERCENTAGE.intValue()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.longDescription").value(DEFAULT_LONG_DESCRIPTION))
            .andExpect(jsonPath("$.info").value(DEFAULT_INFO.toString()))
            .andExpect(jsonPath("$.isVirtual").value(DEFAULT_IS_VIRTUAL.booleanValue()))
            .andExpect(jsonPath("$.isVariant").value(DEFAULT_IS_VARIANT.booleanValue()))
            .andExpect(jsonPath("$.requireInventory").value(DEFAULT_REQUIRE_INVENTORY.booleanValue()))
            .andExpect(jsonPath("$.returnable").value(DEFAULT_RETURNABLE.booleanValue()))
            .andExpect(jsonPath("$.isPopular").value(DEFAULT_IS_POPULAR.booleanValue()))
            .andExpect(jsonPath("$.changeControlNo").value(DEFAULT_CHANGE_CONTROL_NO))
            .andExpect(jsonPath("$.retestDuration").value(DEFAULT_RETEST_DURATION.toString()))
            .andExpect(jsonPath("$.expiryDuration").value(DEFAULT_EXPIRY_DURATION.toString()))
            .andExpect(jsonPath("$.validationType").value(DEFAULT_VALIDATION_TYPE))
            .andExpect(jsonPath("$.shelfLife").value(DEFAULT_SHELF_LIFE));
    }


    @Test
    @Transactional
    public void getProductsByIdFiltering() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        Long id = product.getId();

        defaultProductShouldBeFound("id.equals=" + id);
        defaultProductShouldNotBeFound("id.notEquals=" + id);

        defaultProductShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductShouldNotBeFound("id.greaterThan=" + id);

        defaultProductShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name equals to DEFAULT_NAME
        defaultProductShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productList where name equals to UPDATED_NAME
        defaultProductShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name not equals to DEFAULT_NAME
        defaultProductShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productList where name not equals to UPDATED_NAME
        defaultProductShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productList where name equals to UPDATED_NAME
        defaultProductShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name is not null
        defaultProductShouldBeFound("name.specified=true");

        // Get all the productList where name is null
        defaultProductShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByNameContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name contains DEFAULT_NAME
        defaultProductShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productList where name contains UPDATED_NAME
        defaultProductShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name does not contain DEFAULT_NAME
        defaultProductShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productList where name does not contain UPDATED_NAME
        defaultProductShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllProductsByInternalNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where internalName equals to DEFAULT_INTERNAL_NAME
        defaultProductShouldBeFound("internalName.equals=" + DEFAULT_INTERNAL_NAME);

        // Get all the productList where internalName equals to UPDATED_INTERNAL_NAME
        defaultProductShouldNotBeFound("internalName.equals=" + UPDATED_INTERNAL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByInternalNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where internalName not equals to DEFAULT_INTERNAL_NAME
        defaultProductShouldNotBeFound("internalName.notEquals=" + DEFAULT_INTERNAL_NAME);

        // Get all the productList where internalName not equals to UPDATED_INTERNAL_NAME
        defaultProductShouldBeFound("internalName.notEquals=" + UPDATED_INTERNAL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByInternalNameIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where internalName in DEFAULT_INTERNAL_NAME or UPDATED_INTERNAL_NAME
        defaultProductShouldBeFound("internalName.in=" + DEFAULT_INTERNAL_NAME + "," + UPDATED_INTERNAL_NAME);

        // Get all the productList where internalName equals to UPDATED_INTERNAL_NAME
        defaultProductShouldNotBeFound("internalName.in=" + UPDATED_INTERNAL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByInternalNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where internalName is not null
        defaultProductShouldBeFound("internalName.specified=true");

        // Get all the productList where internalName is null
        defaultProductShouldNotBeFound("internalName.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByInternalNameContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where internalName contains DEFAULT_INTERNAL_NAME
        defaultProductShouldBeFound("internalName.contains=" + DEFAULT_INTERNAL_NAME);

        // Get all the productList where internalName contains UPDATED_INTERNAL_NAME
        defaultProductShouldNotBeFound("internalName.contains=" + UPDATED_INTERNAL_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByInternalNameNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where internalName does not contain DEFAULT_INTERNAL_NAME
        defaultProductShouldNotBeFound("internalName.doesNotContain=" + DEFAULT_INTERNAL_NAME);

        // Get all the productList where internalName does not contain UPDATED_INTERNAL_NAME
        defaultProductShouldBeFound("internalName.doesNotContain=" + UPDATED_INTERNAL_NAME);
    }


    @Test
    @Transactional
    public void getAllProductsByBrandNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where brandName equals to DEFAULT_BRAND_NAME
        defaultProductShouldBeFound("brandName.equals=" + DEFAULT_BRAND_NAME);

        // Get all the productList where brandName equals to UPDATED_BRAND_NAME
        defaultProductShouldNotBeFound("brandName.equals=" + UPDATED_BRAND_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByBrandNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where brandName not equals to DEFAULT_BRAND_NAME
        defaultProductShouldNotBeFound("brandName.notEquals=" + DEFAULT_BRAND_NAME);

        // Get all the productList where brandName not equals to UPDATED_BRAND_NAME
        defaultProductShouldBeFound("brandName.notEquals=" + UPDATED_BRAND_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByBrandNameIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where brandName in DEFAULT_BRAND_NAME or UPDATED_BRAND_NAME
        defaultProductShouldBeFound("brandName.in=" + DEFAULT_BRAND_NAME + "," + UPDATED_BRAND_NAME);

        // Get all the productList where brandName equals to UPDATED_BRAND_NAME
        defaultProductShouldNotBeFound("brandName.in=" + UPDATED_BRAND_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByBrandNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where brandName is not null
        defaultProductShouldBeFound("brandName.specified=true");

        // Get all the productList where brandName is null
        defaultProductShouldNotBeFound("brandName.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByBrandNameContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where brandName contains DEFAULT_BRAND_NAME
        defaultProductShouldBeFound("brandName.contains=" + DEFAULT_BRAND_NAME);

        // Get all the productList where brandName contains UPDATED_BRAND_NAME
        defaultProductShouldNotBeFound("brandName.contains=" + UPDATED_BRAND_NAME);
    }

    @Test
    @Transactional
    public void getAllProductsByBrandNameNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where brandName does not contain DEFAULT_BRAND_NAME
        defaultProductShouldNotBeFound("brandName.doesNotContain=" + DEFAULT_BRAND_NAME);

        // Get all the productList where brandName does not contain UPDATED_BRAND_NAME
        defaultProductShouldBeFound("brandName.doesNotContain=" + UPDATED_BRAND_NAME);
    }


    @Test
    @Transactional
    public void getAllProductsByVariantIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where variant equals to DEFAULT_VARIANT
        defaultProductShouldBeFound("variant.equals=" + DEFAULT_VARIANT);

        // Get all the productList where variant equals to UPDATED_VARIANT
        defaultProductShouldNotBeFound("variant.equals=" + UPDATED_VARIANT);
    }

    @Test
    @Transactional
    public void getAllProductsByVariantIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where variant not equals to DEFAULT_VARIANT
        defaultProductShouldNotBeFound("variant.notEquals=" + DEFAULT_VARIANT);

        // Get all the productList where variant not equals to UPDATED_VARIANT
        defaultProductShouldBeFound("variant.notEquals=" + UPDATED_VARIANT);
    }

    @Test
    @Transactional
    public void getAllProductsByVariantIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where variant in DEFAULT_VARIANT or UPDATED_VARIANT
        defaultProductShouldBeFound("variant.in=" + DEFAULT_VARIANT + "," + UPDATED_VARIANT);

        // Get all the productList where variant equals to UPDATED_VARIANT
        defaultProductShouldNotBeFound("variant.in=" + UPDATED_VARIANT);
    }

    @Test
    @Transactional
    public void getAllProductsByVariantIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where variant is not null
        defaultProductShouldBeFound("variant.specified=true");

        // Get all the productList where variant is null
        defaultProductShouldNotBeFound("variant.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByVariantContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where variant contains DEFAULT_VARIANT
        defaultProductShouldBeFound("variant.contains=" + DEFAULT_VARIANT);

        // Get all the productList where variant contains UPDATED_VARIANT
        defaultProductShouldNotBeFound("variant.contains=" + UPDATED_VARIANT);
    }

    @Test
    @Transactional
    public void getAllProductsByVariantNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where variant does not contain DEFAULT_VARIANT
        defaultProductShouldNotBeFound("variant.doesNotContain=" + DEFAULT_VARIANT);

        // Get all the productList where variant does not contain UPDATED_VARIANT
        defaultProductShouldBeFound("variant.doesNotContain=" + UPDATED_VARIANT);
    }


    @Test
    @Transactional
    public void getAllProductsBySkuIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sku equals to DEFAULT_SKU
        defaultProductShouldBeFound("sku.equals=" + DEFAULT_SKU);

        // Get all the productList where sku equals to UPDATED_SKU
        defaultProductShouldNotBeFound("sku.equals=" + UPDATED_SKU);
    }

    @Test
    @Transactional
    public void getAllProductsBySkuIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sku not equals to DEFAULT_SKU
        defaultProductShouldNotBeFound("sku.notEquals=" + DEFAULT_SKU);

        // Get all the productList where sku not equals to UPDATED_SKU
        defaultProductShouldBeFound("sku.notEquals=" + UPDATED_SKU);
    }

    @Test
    @Transactional
    public void getAllProductsBySkuIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sku in DEFAULT_SKU or UPDATED_SKU
        defaultProductShouldBeFound("sku.in=" + DEFAULT_SKU + "," + UPDATED_SKU);

        // Get all the productList where sku equals to UPDATED_SKU
        defaultProductShouldNotBeFound("sku.in=" + UPDATED_SKU);
    }

    @Test
    @Transactional
    public void getAllProductsBySkuIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sku is not null
        defaultProductShouldBeFound("sku.specified=true");

        // Get all the productList where sku is null
        defaultProductShouldNotBeFound("sku.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsBySkuContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sku contains DEFAULT_SKU
        defaultProductShouldBeFound("sku.contains=" + DEFAULT_SKU);

        // Get all the productList where sku contains UPDATED_SKU
        defaultProductShouldNotBeFound("sku.contains=" + UPDATED_SKU);
    }

    @Test
    @Transactional
    public void getAllProductsBySkuNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sku does not contain DEFAULT_SKU
        defaultProductShouldNotBeFound("sku.doesNotContain=" + DEFAULT_SKU);

        // Get all the productList where sku does not contain UPDATED_SKU
        defaultProductShouldBeFound("sku.doesNotContain=" + UPDATED_SKU);
    }


    @Test
    @Transactional
    public void getAllProductsByIntroductionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where introductionDate equals to DEFAULT_INTRODUCTION_DATE
        defaultProductShouldBeFound("introductionDate.equals=" + DEFAULT_INTRODUCTION_DATE);

        // Get all the productList where introductionDate equals to UPDATED_INTRODUCTION_DATE
        defaultProductShouldNotBeFound("introductionDate.equals=" + UPDATED_INTRODUCTION_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByIntroductionDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where introductionDate not equals to DEFAULT_INTRODUCTION_DATE
        defaultProductShouldNotBeFound("introductionDate.notEquals=" + DEFAULT_INTRODUCTION_DATE);

        // Get all the productList where introductionDate not equals to UPDATED_INTRODUCTION_DATE
        defaultProductShouldBeFound("introductionDate.notEquals=" + UPDATED_INTRODUCTION_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByIntroductionDateIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where introductionDate in DEFAULT_INTRODUCTION_DATE or UPDATED_INTRODUCTION_DATE
        defaultProductShouldBeFound("introductionDate.in=" + DEFAULT_INTRODUCTION_DATE + "," + UPDATED_INTRODUCTION_DATE);

        // Get all the productList where introductionDate equals to UPDATED_INTRODUCTION_DATE
        defaultProductShouldNotBeFound("introductionDate.in=" + UPDATED_INTRODUCTION_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByIntroductionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where introductionDate is not null
        defaultProductShouldBeFound("introductionDate.specified=true");

        // Get all the productList where introductionDate is null
        defaultProductShouldNotBeFound("introductionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByIntroductionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where introductionDate is greater than or equal to DEFAULT_INTRODUCTION_DATE
        defaultProductShouldBeFound("introductionDate.greaterThanOrEqual=" + DEFAULT_INTRODUCTION_DATE);

        // Get all the productList where introductionDate is greater than or equal to UPDATED_INTRODUCTION_DATE
        defaultProductShouldNotBeFound("introductionDate.greaterThanOrEqual=" + UPDATED_INTRODUCTION_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByIntroductionDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where introductionDate is less than or equal to DEFAULT_INTRODUCTION_DATE
        defaultProductShouldBeFound("introductionDate.lessThanOrEqual=" + DEFAULT_INTRODUCTION_DATE);

        // Get all the productList where introductionDate is less than or equal to SMALLER_INTRODUCTION_DATE
        defaultProductShouldNotBeFound("introductionDate.lessThanOrEqual=" + SMALLER_INTRODUCTION_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByIntroductionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where introductionDate is less than DEFAULT_INTRODUCTION_DATE
        defaultProductShouldNotBeFound("introductionDate.lessThan=" + DEFAULT_INTRODUCTION_DATE);

        // Get all the productList where introductionDate is less than UPDATED_INTRODUCTION_DATE
        defaultProductShouldBeFound("introductionDate.lessThan=" + UPDATED_INTRODUCTION_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByIntroductionDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where introductionDate is greater than DEFAULT_INTRODUCTION_DATE
        defaultProductShouldNotBeFound("introductionDate.greaterThan=" + DEFAULT_INTRODUCTION_DATE);

        // Get all the productList where introductionDate is greater than SMALLER_INTRODUCTION_DATE
        defaultProductShouldBeFound("introductionDate.greaterThan=" + SMALLER_INTRODUCTION_DATE);
    }


    @Test
    @Transactional
    public void getAllProductsByReleaseDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where releaseDate equals to DEFAULT_RELEASE_DATE
        defaultProductShouldBeFound("releaseDate.equals=" + DEFAULT_RELEASE_DATE);

        // Get all the productList where releaseDate equals to UPDATED_RELEASE_DATE
        defaultProductShouldNotBeFound("releaseDate.equals=" + UPDATED_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByReleaseDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where releaseDate not equals to DEFAULT_RELEASE_DATE
        defaultProductShouldNotBeFound("releaseDate.notEquals=" + DEFAULT_RELEASE_DATE);

        // Get all the productList where releaseDate not equals to UPDATED_RELEASE_DATE
        defaultProductShouldBeFound("releaseDate.notEquals=" + UPDATED_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByReleaseDateIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where releaseDate in DEFAULT_RELEASE_DATE or UPDATED_RELEASE_DATE
        defaultProductShouldBeFound("releaseDate.in=" + DEFAULT_RELEASE_DATE + "," + UPDATED_RELEASE_DATE);

        // Get all the productList where releaseDate equals to UPDATED_RELEASE_DATE
        defaultProductShouldNotBeFound("releaseDate.in=" + UPDATED_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByReleaseDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where releaseDate is not null
        defaultProductShouldBeFound("releaseDate.specified=true");

        // Get all the productList where releaseDate is null
        defaultProductShouldNotBeFound("releaseDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByReleaseDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where releaseDate is greater than or equal to DEFAULT_RELEASE_DATE
        defaultProductShouldBeFound("releaseDate.greaterThanOrEqual=" + DEFAULT_RELEASE_DATE);

        // Get all the productList where releaseDate is greater than or equal to UPDATED_RELEASE_DATE
        defaultProductShouldNotBeFound("releaseDate.greaterThanOrEqual=" + UPDATED_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByReleaseDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where releaseDate is less than or equal to DEFAULT_RELEASE_DATE
        defaultProductShouldBeFound("releaseDate.lessThanOrEqual=" + DEFAULT_RELEASE_DATE);

        // Get all the productList where releaseDate is less than or equal to SMALLER_RELEASE_DATE
        defaultProductShouldNotBeFound("releaseDate.lessThanOrEqual=" + SMALLER_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByReleaseDateIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where releaseDate is less than DEFAULT_RELEASE_DATE
        defaultProductShouldNotBeFound("releaseDate.lessThan=" + DEFAULT_RELEASE_DATE);

        // Get all the productList where releaseDate is less than UPDATED_RELEASE_DATE
        defaultProductShouldBeFound("releaseDate.lessThan=" + UPDATED_RELEASE_DATE);
    }

    @Test
    @Transactional
    public void getAllProductsByReleaseDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where releaseDate is greater than DEFAULT_RELEASE_DATE
        defaultProductShouldNotBeFound("releaseDate.greaterThan=" + DEFAULT_RELEASE_DATE);

        // Get all the productList where releaseDate is greater than SMALLER_RELEASE_DATE
        defaultProductShouldBeFound("releaseDate.greaterThan=" + SMALLER_RELEASE_DATE);
    }


    @Test
    @Transactional
    public void getAllProductsByQuantityIncludedIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantityIncluded equals to DEFAULT_QUANTITY_INCLUDED
        defaultProductShouldBeFound("quantityIncluded.equals=" + DEFAULT_QUANTITY_INCLUDED);

        // Get all the productList where quantityIncluded equals to UPDATED_QUANTITY_INCLUDED
        defaultProductShouldNotBeFound("quantityIncluded.equals=" + UPDATED_QUANTITY_INCLUDED);
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIncludedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantityIncluded not equals to DEFAULT_QUANTITY_INCLUDED
        defaultProductShouldNotBeFound("quantityIncluded.notEquals=" + DEFAULT_QUANTITY_INCLUDED);

        // Get all the productList where quantityIncluded not equals to UPDATED_QUANTITY_INCLUDED
        defaultProductShouldBeFound("quantityIncluded.notEquals=" + UPDATED_QUANTITY_INCLUDED);
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIncludedIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantityIncluded in DEFAULT_QUANTITY_INCLUDED or UPDATED_QUANTITY_INCLUDED
        defaultProductShouldBeFound("quantityIncluded.in=" + DEFAULT_QUANTITY_INCLUDED + "," + UPDATED_QUANTITY_INCLUDED);

        // Get all the productList where quantityIncluded equals to UPDATED_QUANTITY_INCLUDED
        defaultProductShouldNotBeFound("quantityIncluded.in=" + UPDATED_QUANTITY_INCLUDED);
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIncludedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantityIncluded is not null
        defaultProductShouldBeFound("quantityIncluded.specified=true");

        // Get all the productList where quantityIncluded is null
        defaultProductShouldNotBeFound("quantityIncluded.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIncludedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantityIncluded is greater than or equal to DEFAULT_QUANTITY_INCLUDED
        defaultProductShouldBeFound("quantityIncluded.greaterThanOrEqual=" + DEFAULT_QUANTITY_INCLUDED);

        // Get all the productList where quantityIncluded is greater than or equal to UPDATED_QUANTITY_INCLUDED
        defaultProductShouldNotBeFound("quantityIncluded.greaterThanOrEqual=" + UPDATED_QUANTITY_INCLUDED);
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIncludedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantityIncluded is less than or equal to DEFAULT_QUANTITY_INCLUDED
        defaultProductShouldBeFound("quantityIncluded.lessThanOrEqual=" + DEFAULT_QUANTITY_INCLUDED);

        // Get all the productList where quantityIncluded is less than or equal to SMALLER_QUANTITY_INCLUDED
        defaultProductShouldNotBeFound("quantityIncluded.lessThanOrEqual=" + SMALLER_QUANTITY_INCLUDED);
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIncludedIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantityIncluded is less than DEFAULT_QUANTITY_INCLUDED
        defaultProductShouldNotBeFound("quantityIncluded.lessThan=" + DEFAULT_QUANTITY_INCLUDED);

        // Get all the productList where quantityIncluded is less than UPDATED_QUANTITY_INCLUDED
        defaultProductShouldBeFound("quantityIncluded.lessThan=" + UPDATED_QUANTITY_INCLUDED);
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIncludedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantityIncluded is greater than DEFAULT_QUANTITY_INCLUDED
        defaultProductShouldNotBeFound("quantityIncluded.greaterThan=" + DEFAULT_QUANTITY_INCLUDED);

        // Get all the productList where quantityIncluded is greater than SMALLER_QUANTITY_INCLUDED
        defaultProductShouldBeFound("quantityIncluded.greaterThan=" + SMALLER_QUANTITY_INCLUDED);
    }


    @Test
    @Transactional
    public void getAllProductsByPiecesIncludedIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where piecesIncluded equals to DEFAULT_PIECES_INCLUDED
        defaultProductShouldBeFound("piecesIncluded.equals=" + DEFAULT_PIECES_INCLUDED);

        // Get all the productList where piecesIncluded equals to UPDATED_PIECES_INCLUDED
        defaultProductShouldNotBeFound("piecesIncluded.equals=" + UPDATED_PIECES_INCLUDED);
    }

    @Test
    @Transactional
    public void getAllProductsByPiecesIncludedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where piecesIncluded not equals to DEFAULT_PIECES_INCLUDED
        defaultProductShouldNotBeFound("piecesIncluded.notEquals=" + DEFAULT_PIECES_INCLUDED);

        // Get all the productList where piecesIncluded not equals to UPDATED_PIECES_INCLUDED
        defaultProductShouldBeFound("piecesIncluded.notEquals=" + UPDATED_PIECES_INCLUDED);
    }

    @Test
    @Transactional
    public void getAllProductsByPiecesIncludedIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where piecesIncluded in DEFAULT_PIECES_INCLUDED or UPDATED_PIECES_INCLUDED
        defaultProductShouldBeFound("piecesIncluded.in=" + DEFAULT_PIECES_INCLUDED + "," + UPDATED_PIECES_INCLUDED);

        // Get all the productList where piecesIncluded equals to UPDATED_PIECES_INCLUDED
        defaultProductShouldNotBeFound("piecesIncluded.in=" + UPDATED_PIECES_INCLUDED);
    }

    @Test
    @Transactional
    public void getAllProductsByPiecesIncludedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where piecesIncluded is not null
        defaultProductShouldBeFound("piecesIncluded.specified=true");

        // Get all the productList where piecesIncluded is null
        defaultProductShouldNotBeFound("piecesIncluded.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByPiecesIncludedIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where piecesIncluded is greater than or equal to DEFAULT_PIECES_INCLUDED
        defaultProductShouldBeFound("piecesIncluded.greaterThanOrEqual=" + DEFAULT_PIECES_INCLUDED);

        // Get all the productList where piecesIncluded is greater than or equal to UPDATED_PIECES_INCLUDED
        defaultProductShouldNotBeFound("piecesIncluded.greaterThanOrEqual=" + UPDATED_PIECES_INCLUDED);
    }

    @Test
    @Transactional
    public void getAllProductsByPiecesIncludedIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where piecesIncluded is less than or equal to DEFAULT_PIECES_INCLUDED
        defaultProductShouldBeFound("piecesIncluded.lessThanOrEqual=" + DEFAULT_PIECES_INCLUDED);

        // Get all the productList where piecesIncluded is less than or equal to SMALLER_PIECES_INCLUDED
        defaultProductShouldNotBeFound("piecesIncluded.lessThanOrEqual=" + SMALLER_PIECES_INCLUDED);
    }

    @Test
    @Transactional
    public void getAllProductsByPiecesIncludedIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where piecesIncluded is less than DEFAULT_PIECES_INCLUDED
        defaultProductShouldNotBeFound("piecesIncluded.lessThan=" + DEFAULT_PIECES_INCLUDED);

        // Get all the productList where piecesIncluded is less than UPDATED_PIECES_INCLUDED
        defaultProductShouldBeFound("piecesIncluded.lessThan=" + UPDATED_PIECES_INCLUDED);
    }

    @Test
    @Transactional
    public void getAllProductsByPiecesIncludedIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where piecesIncluded is greater than DEFAULT_PIECES_INCLUDED
        defaultProductShouldNotBeFound("piecesIncluded.greaterThan=" + DEFAULT_PIECES_INCLUDED);

        // Get all the productList where piecesIncluded is greater than SMALLER_PIECES_INCLUDED
        defaultProductShouldBeFound("piecesIncluded.greaterThan=" + SMALLER_PIECES_INCLUDED);
    }


    @Test
    @Transactional
    public void getAllProductsByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where weight equals to DEFAULT_WEIGHT
        defaultProductShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the productList where weight equals to UPDATED_WEIGHT
        defaultProductShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllProductsByWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where weight not equals to DEFAULT_WEIGHT
        defaultProductShouldNotBeFound("weight.notEquals=" + DEFAULT_WEIGHT);

        // Get all the productList where weight not equals to UPDATED_WEIGHT
        defaultProductShouldBeFound("weight.notEquals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllProductsByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultProductShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the productList where weight equals to UPDATED_WEIGHT
        defaultProductShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllProductsByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where weight is not null
        defaultProductShouldBeFound("weight.specified=true");

        // Get all the productList where weight is null
        defaultProductShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultProductShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the productList where weight is greater than or equal to UPDATED_WEIGHT
        defaultProductShouldNotBeFound("weight.greaterThanOrEqual=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllProductsByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where weight is less than or equal to DEFAULT_WEIGHT
        defaultProductShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the productList where weight is less than or equal to SMALLER_WEIGHT
        defaultProductShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllProductsByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where weight is less than DEFAULT_WEIGHT
        defaultProductShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the productList where weight is less than UPDATED_WEIGHT
        defaultProductShouldBeFound("weight.lessThan=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllProductsByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where weight is greater than DEFAULT_WEIGHT
        defaultProductShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the productList where weight is greater than SMALLER_WEIGHT
        defaultProductShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllProductsByHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where height equals to DEFAULT_HEIGHT
        defaultProductShouldBeFound("height.equals=" + DEFAULT_HEIGHT);

        // Get all the productList where height equals to UPDATED_HEIGHT
        defaultProductShouldNotBeFound("height.equals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllProductsByHeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where height not equals to DEFAULT_HEIGHT
        defaultProductShouldNotBeFound("height.notEquals=" + DEFAULT_HEIGHT);

        // Get all the productList where height not equals to UPDATED_HEIGHT
        defaultProductShouldBeFound("height.notEquals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllProductsByHeightIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where height in DEFAULT_HEIGHT or UPDATED_HEIGHT
        defaultProductShouldBeFound("height.in=" + DEFAULT_HEIGHT + "," + UPDATED_HEIGHT);

        // Get all the productList where height equals to UPDATED_HEIGHT
        defaultProductShouldNotBeFound("height.in=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllProductsByHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where height is not null
        defaultProductShouldBeFound("height.specified=true");

        // Get all the productList where height is null
        defaultProductShouldNotBeFound("height.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where height is greater than or equal to DEFAULT_HEIGHT
        defaultProductShouldBeFound("height.greaterThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the productList where height is greater than or equal to UPDATED_HEIGHT
        defaultProductShouldNotBeFound("height.greaterThanOrEqual=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllProductsByHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where height is less than or equal to DEFAULT_HEIGHT
        defaultProductShouldBeFound("height.lessThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the productList where height is less than or equal to SMALLER_HEIGHT
        defaultProductShouldNotBeFound("height.lessThanOrEqual=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllProductsByHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where height is less than DEFAULT_HEIGHT
        defaultProductShouldNotBeFound("height.lessThan=" + DEFAULT_HEIGHT);

        // Get all the productList where height is less than UPDATED_HEIGHT
        defaultProductShouldBeFound("height.lessThan=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllProductsByHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where height is greater than DEFAULT_HEIGHT
        defaultProductShouldNotBeFound("height.greaterThan=" + DEFAULT_HEIGHT);

        // Get all the productList where height is greater than SMALLER_HEIGHT
        defaultProductShouldBeFound("height.greaterThan=" + SMALLER_HEIGHT);
    }


    @Test
    @Transactional
    public void getAllProductsByWidthIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where width equals to DEFAULT_WIDTH
        defaultProductShouldBeFound("width.equals=" + DEFAULT_WIDTH);

        // Get all the productList where width equals to UPDATED_WIDTH
        defaultProductShouldNotBeFound("width.equals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllProductsByWidthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where width not equals to DEFAULT_WIDTH
        defaultProductShouldNotBeFound("width.notEquals=" + DEFAULT_WIDTH);

        // Get all the productList where width not equals to UPDATED_WIDTH
        defaultProductShouldBeFound("width.notEquals=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllProductsByWidthIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where width in DEFAULT_WIDTH or UPDATED_WIDTH
        defaultProductShouldBeFound("width.in=" + DEFAULT_WIDTH + "," + UPDATED_WIDTH);

        // Get all the productList where width equals to UPDATED_WIDTH
        defaultProductShouldNotBeFound("width.in=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllProductsByWidthIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where width is not null
        defaultProductShouldBeFound("width.specified=true");

        // Get all the productList where width is null
        defaultProductShouldNotBeFound("width.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByWidthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where width is greater than or equal to DEFAULT_WIDTH
        defaultProductShouldBeFound("width.greaterThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the productList where width is greater than or equal to UPDATED_WIDTH
        defaultProductShouldNotBeFound("width.greaterThanOrEqual=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllProductsByWidthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where width is less than or equal to DEFAULT_WIDTH
        defaultProductShouldBeFound("width.lessThanOrEqual=" + DEFAULT_WIDTH);

        // Get all the productList where width is less than or equal to SMALLER_WIDTH
        defaultProductShouldNotBeFound("width.lessThanOrEqual=" + SMALLER_WIDTH);
    }

    @Test
    @Transactional
    public void getAllProductsByWidthIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where width is less than DEFAULT_WIDTH
        defaultProductShouldNotBeFound("width.lessThan=" + DEFAULT_WIDTH);

        // Get all the productList where width is less than UPDATED_WIDTH
        defaultProductShouldBeFound("width.lessThan=" + UPDATED_WIDTH);
    }

    @Test
    @Transactional
    public void getAllProductsByWidthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where width is greater than DEFAULT_WIDTH
        defaultProductShouldNotBeFound("width.greaterThan=" + DEFAULT_WIDTH);

        // Get all the productList where width is greater than SMALLER_WIDTH
        defaultProductShouldBeFound("width.greaterThan=" + SMALLER_WIDTH);
    }


    @Test
    @Transactional
    public void getAllProductsByDepthIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where depth equals to DEFAULT_DEPTH
        defaultProductShouldBeFound("depth.equals=" + DEFAULT_DEPTH);

        // Get all the productList where depth equals to UPDATED_DEPTH
        defaultProductShouldNotBeFound("depth.equals=" + UPDATED_DEPTH);
    }

    @Test
    @Transactional
    public void getAllProductsByDepthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where depth not equals to DEFAULT_DEPTH
        defaultProductShouldNotBeFound("depth.notEquals=" + DEFAULT_DEPTH);

        // Get all the productList where depth not equals to UPDATED_DEPTH
        defaultProductShouldBeFound("depth.notEquals=" + UPDATED_DEPTH);
    }

    @Test
    @Transactional
    public void getAllProductsByDepthIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where depth in DEFAULT_DEPTH or UPDATED_DEPTH
        defaultProductShouldBeFound("depth.in=" + DEFAULT_DEPTH + "," + UPDATED_DEPTH);

        // Get all the productList where depth equals to UPDATED_DEPTH
        defaultProductShouldNotBeFound("depth.in=" + UPDATED_DEPTH);
    }

    @Test
    @Transactional
    public void getAllProductsByDepthIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where depth is not null
        defaultProductShouldBeFound("depth.specified=true");

        // Get all the productList where depth is null
        defaultProductShouldNotBeFound("depth.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByDepthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where depth is greater than or equal to DEFAULT_DEPTH
        defaultProductShouldBeFound("depth.greaterThanOrEqual=" + DEFAULT_DEPTH);

        // Get all the productList where depth is greater than or equal to UPDATED_DEPTH
        defaultProductShouldNotBeFound("depth.greaterThanOrEqual=" + UPDATED_DEPTH);
    }

    @Test
    @Transactional
    public void getAllProductsByDepthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where depth is less than or equal to DEFAULT_DEPTH
        defaultProductShouldBeFound("depth.lessThanOrEqual=" + DEFAULT_DEPTH);

        // Get all the productList where depth is less than or equal to SMALLER_DEPTH
        defaultProductShouldNotBeFound("depth.lessThanOrEqual=" + SMALLER_DEPTH);
    }

    @Test
    @Transactional
    public void getAllProductsByDepthIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where depth is less than DEFAULT_DEPTH
        defaultProductShouldNotBeFound("depth.lessThan=" + DEFAULT_DEPTH);

        // Get all the productList where depth is less than UPDATED_DEPTH
        defaultProductShouldBeFound("depth.lessThan=" + UPDATED_DEPTH);
    }

    @Test
    @Transactional
    public void getAllProductsByDepthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where depth is greater than DEFAULT_DEPTH
        defaultProductShouldNotBeFound("depth.greaterThan=" + DEFAULT_DEPTH);

        // Get all the productList where depth is greater than SMALLER_DEPTH
        defaultProductShouldBeFound("depth.greaterThan=" + SMALLER_DEPTH);
    }


    @Test
    @Transactional
    public void getAllProductsBySmallImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where smallImageUrl equals to DEFAULT_SMALL_IMAGE_URL
        defaultProductShouldBeFound("smallImageUrl.equals=" + DEFAULT_SMALL_IMAGE_URL);

        // Get all the productList where smallImageUrl equals to UPDATED_SMALL_IMAGE_URL
        defaultProductShouldNotBeFound("smallImageUrl.equals=" + UPDATED_SMALL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsBySmallImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where smallImageUrl not equals to DEFAULT_SMALL_IMAGE_URL
        defaultProductShouldNotBeFound("smallImageUrl.notEquals=" + DEFAULT_SMALL_IMAGE_URL);

        // Get all the productList where smallImageUrl not equals to UPDATED_SMALL_IMAGE_URL
        defaultProductShouldBeFound("smallImageUrl.notEquals=" + UPDATED_SMALL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsBySmallImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where smallImageUrl in DEFAULT_SMALL_IMAGE_URL or UPDATED_SMALL_IMAGE_URL
        defaultProductShouldBeFound("smallImageUrl.in=" + DEFAULT_SMALL_IMAGE_URL + "," + UPDATED_SMALL_IMAGE_URL);

        // Get all the productList where smallImageUrl equals to UPDATED_SMALL_IMAGE_URL
        defaultProductShouldNotBeFound("smallImageUrl.in=" + UPDATED_SMALL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsBySmallImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where smallImageUrl is not null
        defaultProductShouldBeFound("smallImageUrl.specified=true");

        // Get all the productList where smallImageUrl is null
        defaultProductShouldNotBeFound("smallImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsBySmallImageUrlContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where smallImageUrl contains DEFAULT_SMALL_IMAGE_URL
        defaultProductShouldBeFound("smallImageUrl.contains=" + DEFAULT_SMALL_IMAGE_URL);

        // Get all the productList where smallImageUrl contains UPDATED_SMALL_IMAGE_URL
        defaultProductShouldNotBeFound("smallImageUrl.contains=" + UPDATED_SMALL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsBySmallImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where smallImageUrl does not contain DEFAULT_SMALL_IMAGE_URL
        defaultProductShouldNotBeFound("smallImageUrl.doesNotContain=" + DEFAULT_SMALL_IMAGE_URL);

        // Get all the productList where smallImageUrl does not contain UPDATED_SMALL_IMAGE_URL
        defaultProductShouldBeFound("smallImageUrl.doesNotContain=" + UPDATED_SMALL_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllProductsByMediumImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mediumImageUrl equals to DEFAULT_MEDIUM_IMAGE_URL
        defaultProductShouldBeFound("mediumImageUrl.equals=" + DEFAULT_MEDIUM_IMAGE_URL);

        // Get all the productList where mediumImageUrl equals to UPDATED_MEDIUM_IMAGE_URL
        defaultProductShouldNotBeFound("mediumImageUrl.equals=" + UPDATED_MEDIUM_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByMediumImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mediumImageUrl not equals to DEFAULT_MEDIUM_IMAGE_URL
        defaultProductShouldNotBeFound("mediumImageUrl.notEquals=" + DEFAULT_MEDIUM_IMAGE_URL);

        // Get all the productList where mediumImageUrl not equals to UPDATED_MEDIUM_IMAGE_URL
        defaultProductShouldBeFound("mediumImageUrl.notEquals=" + UPDATED_MEDIUM_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByMediumImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mediumImageUrl in DEFAULT_MEDIUM_IMAGE_URL or UPDATED_MEDIUM_IMAGE_URL
        defaultProductShouldBeFound("mediumImageUrl.in=" + DEFAULT_MEDIUM_IMAGE_URL + "," + UPDATED_MEDIUM_IMAGE_URL);

        // Get all the productList where mediumImageUrl equals to UPDATED_MEDIUM_IMAGE_URL
        defaultProductShouldNotBeFound("mediumImageUrl.in=" + UPDATED_MEDIUM_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByMediumImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mediumImageUrl is not null
        defaultProductShouldBeFound("mediumImageUrl.specified=true");

        // Get all the productList where mediumImageUrl is null
        defaultProductShouldNotBeFound("mediumImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByMediumImageUrlContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mediumImageUrl contains DEFAULT_MEDIUM_IMAGE_URL
        defaultProductShouldBeFound("mediumImageUrl.contains=" + DEFAULT_MEDIUM_IMAGE_URL);

        // Get all the productList where mediumImageUrl contains UPDATED_MEDIUM_IMAGE_URL
        defaultProductShouldNotBeFound("mediumImageUrl.contains=" + UPDATED_MEDIUM_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByMediumImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mediumImageUrl does not contain DEFAULT_MEDIUM_IMAGE_URL
        defaultProductShouldNotBeFound("mediumImageUrl.doesNotContain=" + DEFAULT_MEDIUM_IMAGE_URL);

        // Get all the productList where mediumImageUrl does not contain UPDATED_MEDIUM_IMAGE_URL
        defaultProductShouldBeFound("mediumImageUrl.doesNotContain=" + UPDATED_MEDIUM_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllProductsByLargeImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where largeImageUrl equals to DEFAULT_LARGE_IMAGE_URL
        defaultProductShouldBeFound("largeImageUrl.equals=" + DEFAULT_LARGE_IMAGE_URL);

        // Get all the productList where largeImageUrl equals to UPDATED_LARGE_IMAGE_URL
        defaultProductShouldNotBeFound("largeImageUrl.equals=" + UPDATED_LARGE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByLargeImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where largeImageUrl not equals to DEFAULT_LARGE_IMAGE_URL
        defaultProductShouldNotBeFound("largeImageUrl.notEquals=" + DEFAULT_LARGE_IMAGE_URL);

        // Get all the productList where largeImageUrl not equals to UPDATED_LARGE_IMAGE_URL
        defaultProductShouldBeFound("largeImageUrl.notEquals=" + UPDATED_LARGE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByLargeImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where largeImageUrl in DEFAULT_LARGE_IMAGE_URL or UPDATED_LARGE_IMAGE_URL
        defaultProductShouldBeFound("largeImageUrl.in=" + DEFAULT_LARGE_IMAGE_URL + "," + UPDATED_LARGE_IMAGE_URL);

        // Get all the productList where largeImageUrl equals to UPDATED_LARGE_IMAGE_URL
        defaultProductShouldNotBeFound("largeImageUrl.in=" + UPDATED_LARGE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByLargeImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where largeImageUrl is not null
        defaultProductShouldBeFound("largeImageUrl.specified=true");

        // Get all the productList where largeImageUrl is null
        defaultProductShouldNotBeFound("largeImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByLargeImageUrlContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where largeImageUrl contains DEFAULT_LARGE_IMAGE_URL
        defaultProductShouldBeFound("largeImageUrl.contains=" + DEFAULT_LARGE_IMAGE_URL);

        // Get all the productList where largeImageUrl contains UPDATED_LARGE_IMAGE_URL
        defaultProductShouldNotBeFound("largeImageUrl.contains=" + UPDATED_LARGE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByLargeImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where largeImageUrl does not contain DEFAULT_LARGE_IMAGE_URL
        defaultProductShouldNotBeFound("largeImageUrl.doesNotContain=" + DEFAULT_LARGE_IMAGE_URL);

        // Get all the productList where largeImageUrl does not contain UPDATED_LARGE_IMAGE_URL
        defaultProductShouldBeFound("largeImageUrl.doesNotContain=" + UPDATED_LARGE_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllProductsByDetailImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where detailImageUrl equals to DEFAULT_DETAIL_IMAGE_URL
        defaultProductShouldBeFound("detailImageUrl.equals=" + DEFAULT_DETAIL_IMAGE_URL);

        // Get all the productList where detailImageUrl equals to UPDATED_DETAIL_IMAGE_URL
        defaultProductShouldNotBeFound("detailImageUrl.equals=" + UPDATED_DETAIL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByDetailImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where detailImageUrl not equals to DEFAULT_DETAIL_IMAGE_URL
        defaultProductShouldNotBeFound("detailImageUrl.notEquals=" + DEFAULT_DETAIL_IMAGE_URL);

        // Get all the productList where detailImageUrl not equals to UPDATED_DETAIL_IMAGE_URL
        defaultProductShouldBeFound("detailImageUrl.notEquals=" + UPDATED_DETAIL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByDetailImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where detailImageUrl in DEFAULT_DETAIL_IMAGE_URL or UPDATED_DETAIL_IMAGE_URL
        defaultProductShouldBeFound("detailImageUrl.in=" + DEFAULT_DETAIL_IMAGE_URL + "," + UPDATED_DETAIL_IMAGE_URL);

        // Get all the productList where detailImageUrl equals to UPDATED_DETAIL_IMAGE_URL
        defaultProductShouldNotBeFound("detailImageUrl.in=" + UPDATED_DETAIL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByDetailImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where detailImageUrl is not null
        defaultProductShouldBeFound("detailImageUrl.specified=true");

        // Get all the productList where detailImageUrl is null
        defaultProductShouldNotBeFound("detailImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByDetailImageUrlContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where detailImageUrl contains DEFAULT_DETAIL_IMAGE_URL
        defaultProductShouldBeFound("detailImageUrl.contains=" + DEFAULT_DETAIL_IMAGE_URL);

        // Get all the productList where detailImageUrl contains UPDATED_DETAIL_IMAGE_URL
        defaultProductShouldNotBeFound("detailImageUrl.contains=" + UPDATED_DETAIL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByDetailImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where detailImageUrl does not contain DEFAULT_DETAIL_IMAGE_URL
        defaultProductShouldNotBeFound("detailImageUrl.doesNotContain=" + DEFAULT_DETAIL_IMAGE_URL);

        // Get all the productList where detailImageUrl does not contain UPDATED_DETAIL_IMAGE_URL
        defaultProductShouldBeFound("detailImageUrl.doesNotContain=" + UPDATED_DETAIL_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllProductsByOriginalImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where originalImageUrl equals to DEFAULT_ORIGINAL_IMAGE_URL
        defaultProductShouldBeFound("originalImageUrl.equals=" + DEFAULT_ORIGINAL_IMAGE_URL);

        // Get all the productList where originalImageUrl equals to UPDATED_ORIGINAL_IMAGE_URL
        defaultProductShouldNotBeFound("originalImageUrl.equals=" + UPDATED_ORIGINAL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByOriginalImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where originalImageUrl not equals to DEFAULT_ORIGINAL_IMAGE_URL
        defaultProductShouldNotBeFound("originalImageUrl.notEquals=" + DEFAULT_ORIGINAL_IMAGE_URL);

        // Get all the productList where originalImageUrl not equals to UPDATED_ORIGINAL_IMAGE_URL
        defaultProductShouldBeFound("originalImageUrl.notEquals=" + UPDATED_ORIGINAL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByOriginalImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where originalImageUrl in DEFAULT_ORIGINAL_IMAGE_URL or UPDATED_ORIGINAL_IMAGE_URL
        defaultProductShouldBeFound("originalImageUrl.in=" + DEFAULT_ORIGINAL_IMAGE_URL + "," + UPDATED_ORIGINAL_IMAGE_URL);

        // Get all the productList where originalImageUrl equals to UPDATED_ORIGINAL_IMAGE_URL
        defaultProductShouldNotBeFound("originalImageUrl.in=" + UPDATED_ORIGINAL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByOriginalImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where originalImageUrl is not null
        defaultProductShouldBeFound("originalImageUrl.specified=true");

        // Get all the productList where originalImageUrl is null
        defaultProductShouldNotBeFound("originalImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByOriginalImageUrlContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where originalImageUrl contains DEFAULT_ORIGINAL_IMAGE_URL
        defaultProductShouldBeFound("originalImageUrl.contains=" + DEFAULT_ORIGINAL_IMAGE_URL);

        // Get all the productList where originalImageUrl contains UPDATED_ORIGINAL_IMAGE_URL
        defaultProductShouldNotBeFound("originalImageUrl.contains=" + UPDATED_ORIGINAL_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllProductsByOriginalImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where originalImageUrl does not contain DEFAULT_ORIGINAL_IMAGE_URL
        defaultProductShouldNotBeFound("originalImageUrl.doesNotContain=" + DEFAULT_ORIGINAL_IMAGE_URL);

        // Get all the productList where originalImageUrl does not contain UPDATED_ORIGINAL_IMAGE_URL
        defaultProductShouldBeFound("originalImageUrl.doesNotContain=" + UPDATED_ORIGINAL_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllProductsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantity equals to DEFAULT_QUANTITY
        defaultProductShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the productList where quantity equals to UPDATED_QUANTITY
        defaultProductShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantity not equals to DEFAULT_QUANTITY
        defaultProductShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the productList where quantity not equals to UPDATED_QUANTITY
        defaultProductShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultProductShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the productList where quantity equals to UPDATED_QUANTITY
        defaultProductShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantity is not null
        defaultProductShouldBeFound("quantity.specified=true");

        // Get all the productList where quantity is null
        defaultProductShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultProductShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the productList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultProductShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultProductShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the productList where quantity is less than or equal to SMALLER_QUANTITY
        defaultProductShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantity is less than DEFAULT_QUANTITY
        defaultProductShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the productList where quantity is less than UPDATED_QUANTITY
        defaultProductShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllProductsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where quantity is greater than DEFAULT_QUANTITY
        defaultProductShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the productList where quantity is greater than SMALLER_QUANTITY
        defaultProductShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllProductsByCgstIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where cgst equals to DEFAULT_CGST
        defaultProductShouldBeFound("cgst.equals=" + DEFAULT_CGST);

        // Get all the productList where cgst equals to UPDATED_CGST
        defaultProductShouldNotBeFound("cgst.equals=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllProductsByCgstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where cgst not equals to DEFAULT_CGST
        defaultProductShouldNotBeFound("cgst.notEquals=" + DEFAULT_CGST);

        // Get all the productList where cgst not equals to UPDATED_CGST
        defaultProductShouldBeFound("cgst.notEquals=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllProductsByCgstIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where cgst in DEFAULT_CGST or UPDATED_CGST
        defaultProductShouldBeFound("cgst.in=" + DEFAULT_CGST + "," + UPDATED_CGST);

        // Get all the productList where cgst equals to UPDATED_CGST
        defaultProductShouldNotBeFound("cgst.in=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllProductsByCgstIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where cgst is not null
        defaultProductShouldBeFound("cgst.specified=true");

        // Get all the productList where cgst is null
        defaultProductShouldNotBeFound("cgst.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByCgstIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where cgst is greater than or equal to DEFAULT_CGST
        defaultProductShouldBeFound("cgst.greaterThanOrEqual=" + DEFAULT_CGST);

        // Get all the productList where cgst is greater than or equal to UPDATED_CGST
        defaultProductShouldNotBeFound("cgst.greaterThanOrEqual=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllProductsByCgstIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where cgst is less than or equal to DEFAULT_CGST
        defaultProductShouldBeFound("cgst.lessThanOrEqual=" + DEFAULT_CGST);

        // Get all the productList where cgst is less than or equal to SMALLER_CGST
        defaultProductShouldNotBeFound("cgst.lessThanOrEqual=" + SMALLER_CGST);
    }

    @Test
    @Transactional
    public void getAllProductsByCgstIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where cgst is less than DEFAULT_CGST
        defaultProductShouldNotBeFound("cgst.lessThan=" + DEFAULT_CGST);

        // Get all the productList where cgst is less than UPDATED_CGST
        defaultProductShouldBeFound("cgst.lessThan=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllProductsByCgstIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where cgst is greater than DEFAULT_CGST
        defaultProductShouldNotBeFound("cgst.greaterThan=" + DEFAULT_CGST);

        // Get all the productList where cgst is greater than SMALLER_CGST
        defaultProductShouldBeFound("cgst.greaterThan=" + SMALLER_CGST);
    }


    @Test
    @Transactional
    public void getAllProductsByIgstIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where igst equals to DEFAULT_IGST
        defaultProductShouldBeFound("igst.equals=" + DEFAULT_IGST);

        // Get all the productList where igst equals to UPDATED_IGST
        defaultProductShouldNotBeFound("igst.equals=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllProductsByIgstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where igst not equals to DEFAULT_IGST
        defaultProductShouldNotBeFound("igst.notEquals=" + DEFAULT_IGST);

        // Get all the productList where igst not equals to UPDATED_IGST
        defaultProductShouldBeFound("igst.notEquals=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllProductsByIgstIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where igst in DEFAULT_IGST or UPDATED_IGST
        defaultProductShouldBeFound("igst.in=" + DEFAULT_IGST + "," + UPDATED_IGST);

        // Get all the productList where igst equals to UPDATED_IGST
        defaultProductShouldNotBeFound("igst.in=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllProductsByIgstIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where igst is not null
        defaultProductShouldBeFound("igst.specified=true");

        // Get all the productList where igst is null
        defaultProductShouldNotBeFound("igst.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByIgstIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where igst is greater than or equal to DEFAULT_IGST
        defaultProductShouldBeFound("igst.greaterThanOrEqual=" + DEFAULT_IGST);

        // Get all the productList where igst is greater than or equal to UPDATED_IGST
        defaultProductShouldNotBeFound("igst.greaterThanOrEqual=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllProductsByIgstIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where igst is less than or equal to DEFAULT_IGST
        defaultProductShouldBeFound("igst.lessThanOrEqual=" + DEFAULT_IGST);

        // Get all the productList where igst is less than or equal to SMALLER_IGST
        defaultProductShouldNotBeFound("igst.lessThanOrEqual=" + SMALLER_IGST);
    }

    @Test
    @Transactional
    public void getAllProductsByIgstIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where igst is less than DEFAULT_IGST
        defaultProductShouldNotBeFound("igst.lessThan=" + DEFAULT_IGST);

        // Get all the productList where igst is less than UPDATED_IGST
        defaultProductShouldBeFound("igst.lessThan=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllProductsByIgstIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where igst is greater than DEFAULT_IGST
        defaultProductShouldNotBeFound("igst.greaterThan=" + DEFAULT_IGST);

        // Get all the productList where igst is greater than SMALLER_IGST
        defaultProductShouldBeFound("igst.greaterThan=" + SMALLER_IGST);
    }


    @Test
    @Transactional
    public void getAllProductsBySgstIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sgst equals to DEFAULT_SGST
        defaultProductShouldBeFound("sgst.equals=" + DEFAULT_SGST);

        // Get all the productList where sgst equals to UPDATED_SGST
        defaultProductShouldNotBeFound("sgst.equals=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllProductsBySgstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sgst not equals to DEFAULT_SGST
        defaultProductShouldNotBeFound("sgst.notEquals=" + DEFAULT_SGST);

        // Get all the productList where sgst not equals to UPDATED_SGST
        defaultProductShouldBeFound("sgst.notEquals=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllProductsBySgstIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sgst in DEFAULT_SGST or UPDATED_SGST
        defaultProductShouldBeFound("sgst.in=" + DEFAULT_SGST + "," + UPDATED_SGST);

        // Get all the productList where sgst equals to UPDATED_SGST
        defaultProductShouldNotBeFound("sgst.in=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllProductsBySgstIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sgst is not null
        defaultProductShouldBeFound("sgst.specified=true");

        // Get all the productList where sgst is null
        defaultProductShouldNotBeFound("sgst.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsBySgstIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sgst is greater than or equal to DEFAULT_SGST
        defaultProductShouldBeFound("sgst.greaterThanOrEqual=" + DEFAULT_SGST);

        // Get all the productList where sgst is greater than or equal to UPDATED_SGST
        defaultProductShouldNotBeFound("sgst.greaterThanOrEqual=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllProductsBySgstIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sgst is less than or equal to DEFAULT_SGST
        defaultProductShouldBeFound("sgst.lessThanOrEqual=" + DEFAULT_SGST);

        // Get all the productList where sgst is less than or equal to SMALLER_SGST
        defaultProductShouldNotBeFound("sgst.lessThanOrEqual=" + SMALLER_SGST);
    }

    @Test
    @Transactional
    public void getAllProductsBySgstIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sgst is less than DEFAULT_SGST
        defaultProductShouldNotBeFound("sgst.lessThan=" + DEFAULT_SGST);

        // Get all the productList where sgst is less than UPDATED_SGST
        defaultProductShouldBeFound("sgst.lessThan=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllProductsBySgstIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sgst is greater than DEFAULT_SGST
        defaultProductShouldNotBeFound("sgst.greaterThan=" + DEFAULT_SGST);

        // Get all the productList where sgst is greater than SMALLER_SGST
        defaultProductShouldBeFound("sgst.greaterThan=" + SMALLER_SGST);
    }


    @Test
    @Transactional
    public void getAllProductsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price equals to DEFAULT_PRICE
        defaultProductShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the productList where price equals to UPDATED_PRICE
        defaultProductShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price not equals to DEFAULT_PRICE
        defaultProductShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the productList where price not equals to UPDATED_PRICE
        defaultProductShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultProductShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the productList where price equals to UPDATED_PRICE
        defaultProductShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is not null
        defaultProductShouldBeFound("price.specified=true");

        // Get all the productList where price is null
        defaultProductShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is greater than or equal to DEFAULT_PRICE
        defaultProductShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the productList where price is greater than or equal to UPDATED_PRICE
        defaultProductShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is less than or equal to DEFAULT_PRICE
        defaultProductShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the productList where price is less than or equal to SMALLER_PRICE
        defaultProductShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is less than DEFAULT_PRICE
        defaultProductShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the productList where price is less than UPDATED_PRICE
        defaultProductShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is greater than DEFAULT_PRICE
        defaultProductShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the productList where price is greater than SMALLER_PRICE
        defaultProductShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllProductsByCgstPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where cgstPercentage equals to DEFAULT_CGST_PERCENTAGE
        defaultProductShouldBeFound("cgstPercentage.equals=" + DEFAULT_CGST_PERCENTAGE);

        // Get all the productList where cgstPercentage equals to UPDATED_CGST_PERCENTAGE
        defaultProductShouldNotBeFound("cgstPercentage.equals=" + UPDATED_CGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByCgstPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where cgstPercentage not equals to DEFAULT_CGST_PERCENTAGE
        defaultProductShouldNotBeFound("cgstPercentage.notEquals=" + DEFAULT_CGST_PERCENTAGE);

        // Get all the productList where cgstPercentage not equals to UPDATED_CGST_PERCENTAGE
        defaultProductShouldBeFound("cgstPercentage.notEquals=" + UPDATED_CGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByCgstPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where cgstPercentage in DEFAULT_CGST_PERCENTAGE or UPDATED_CGST_PERCENTAGE
        defaultProductShouldBeFound("cgstPercentage.in=" + DEFAULT_CGST_PERCENTAGE + "," + UPDATED_CGST_PERCENTAGE);

        // Get all the productList where cgstPercentage equals to UPDATED_CGST_PERCENTAGE
        defaultProductShouldNotBeFound("cgstPercentage.in=" + UPDATED_CGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByCgstPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where cgstPercentage is not null
        defaultProductShouldBeFound("cgstPercentage.specified=true");

        // Get all the productList where cgstPercentage is null
        defaultProductShouldNotBeFound("cgstPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByCgstPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where cgstPercentage is greater than or equal to DEFAULT_CGST_PERCENTAGE
        defaultProductShouldBeFound("cgstPercentage.greaterThanOrEqual=" + DEFAULT_CGST_PERCENTAGE);

        // Get all the productList where cgstPercentage is greater than or equal to UPDATED_CGST_PERCENTAGE
        defaultProductShouldNotBeFound("cgstPercentage.greaterThanOrEqual=" + UPDATED_CGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByCgstPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where cgstPercentage is less than or equal to DEFAULT_CGST_PERCENTAGE
        defaultProductShouldBeFound("cgstPercentage.lessThanOrEqual=" + DEFAULT_CGST_PERCENTAGE);

        // Get all the productList where cgstPercentage is less than or equal to SMALLER_CGST_PERCENTAGE
        defaultProductShouldNotBeFound("cgstPercentage.lessThanOrEqual=" + SMALLER_CGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByCgstPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where cgstPercentage is less than DEFAULT_CGST_PERCENTAGE
        defaultProductShouldNotBeFound("cgstPercentage.lessThan=" + DEFAULT_CGST_PERCENTAGE);

        // Get all the productList where cgstPercentage is less than UPDATED_CGST_PERCENTAGE
        defaultProductShouldBeFound("cgstPercentage.lessThan=" + UPDATED_CGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByCgstPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where cgstPercentage is greater than DEFAULT_CGST_PERCENTAGE
        defaultProductShouldNotBeFound("cgstPercentage.greaterThan=" + DEFAULT_CGST_PERCENTAGE);

        // Get all the productList where cgstPercentage is greater than SMALLER_CGST_PERCENTAGE
        defaultProductShouldBeFound("cgstPercentage.greaterThan=" + SMALLER_CGST_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllProductsByIgstPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where igstPercentage equals to DEFAULT_IGST_PERCENTAGE
        defaultProductShouldBeFound("igstPercentage.equals=" + DEFAULT_IGST_PERCENTAGE);

        // Get all the productList where igstPercentage equals to UPDATED_IGST_PERCENTAGE
        defaultProductShouldNotBeFound("igstPercentage.equals=" + UPDATED_IGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByIgstPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where igstPercentage not equals to DEFAULT_IGST_PERCENTAGE
        defaultProductShouldNotBeFound("igstPercentage.notEquals=" + DEFAULT_IGST_PERCENTAGE);

        // Get all the productList where igstPercentage not equals to UPDATED_IGST_PERCENTAGE
        defaultProductShouldBeFound("igstPercentage.notEquals=" + UPDATED_IGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByIgstPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where igstPercentage in DEFAULT_IGST_PERCENTAGE or UPDATED_IGST_PERCENTAGE
        defaultProductShouldBeFound("igstPercentage.in=" + DEFAULT_IGST_PERCENTAGE + "," + UPDATED_IGST_PERCENTAGE);

        // Get all the productList where igstPercentage equals to UPDATED_IGST_PERCENTAGE
        defaultProductShouldNotBeFound("igstPercentage.in=" + UPDATED_IGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByIgstPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where igstPercentage is not null
        defaultProductShouldBeFound("igstPercentage.specified=true");

        // Get all the productList where igstPercentage is null
        defaultProductShouldNotBeFound("igstPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByIgstPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where igstPercentage is greater than or equal to DEFAULT_IGST_PERCENTAGE
        defaultProductShouldBeFound("igstPercentage.greaterThanOrEqual=" + DEFAULT_IGST_PERCENTAGE);

        // Get all the productList where igstPercentage is greater than or equal to UPDATED_IGST_PERCENTAGE
        defaultProductShouldNotBeFound("igstPercentage.greaterThanOrEqual=" + UPDATED_IGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByIgstPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where igstPercentage is less than or equal to DEFAULT_IGST_PERCENTAGE
        defaultProductShouldBeFound("igstPercentage.lessThanOrEqual=" + DEFAULT_IGST_PERCENTAGE);

        // Get all the productList where igstPercentage is less than or equal to SMALLER_IGST_PERCENTAGE
        defaultProductShouldNotBeFound("igstPercentage.lessThanOrEqual=" + SMALLER_IGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByIgstPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where igstPercentage is less than DEFAULT_IGST_PERCENTAGE
        defaultProductShouldNotBeFound("igstPercentage.lessThan=" + DEFAULT_IGST_PERCENTAGE);

        // Get all the productList where igstPercentage is less than UPDATED_IGST_PERCENTAGE
        defaultProductShouldBeFound("igstPercentage.lessThan=" + UPDATED_IGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsByIgstPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where igstPercentage is greater than DEFAULT_IGST_PERCENTAGE
        defaultProductShouldNotBeFound("igstPercentage.greaterThan=" + DEFAULT_IGST_PERCENTAGE);

        // Get all the productList where igstPercentage is greater than SMALLER_IGST_PERCENTAGE
        defaultProductShouldBeFound("igstPercentage.greaterThan=" + SMALLER_IGST_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllProductsBySgstPercentageIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sgstPercentage equals to DEFAULT_SGST_PERCENTAGE
        defaultProductShouldBeFound("sgstPercentage.equals=" + DEFAULT_SGST_PERCENTAGE);

        // Get all the productList where sgstPercentage equals to UPDATED_SGST_PERCENTAGE
        defaultProductShouldNotBeFound("sgstPercentage.equals=" + UPDATED_SGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsBySgstPercentageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sgstPercentage not equals to DEFAULT_SGST_PERCENTAGE
        defaultProductShouldNotBeFound("sgstPercentage.notEquals=" + DEFAULT_SGST_PERCENTAGE);

        // Get all the productList where sgstPercentage not equals to UPDATED_SGST_PERCENTAGE
        defaultProductShouldBeFound("sgstPercentage.notEquals=" + UPDATED_SGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsBySgstPercentageIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sgstPercentage in DEFAULT_SGST_PERCENTAGE or UPDATED_SGST_PERCENTAGE
        defaultProductShouldBeFound("sgstPercentage.in=" + DEFAULT_SGST_PERCENTAGE + "," + UPDATED_SGST_PERCENTAGE);

        // Get all the productList where sgstPercentage equals to UPDATED_SGST_PERCENTAGE
        defaultProductShouldNotBeFound("sgstPercentage.in=" + UPDATED_SGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsBySgstPercentageIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sgstPercentage is not null
        defaultProductShouldBeFound("sgstPercentage.specified=true");

        // Get all the productList where sgstPercentage is null
        defaultProductShouldNotBeFound("sgstPercentage.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsBySgstPercentageIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sgstPercentage is greater than or equal to DEFAULT_SGST_PERCENTAGE
        defaultProductShouldBeFound("sgstPercentage.greaterThanOrEqual=" + DEFAULT_SGST_PERCENTAGE);

        // Get all the productList where sgstPercentage is greater than or equal to UPDATED_SGST_PERCENTAGE
        defaultProductShouldNotBeFound("sgstPercentage.greaterThanOrEqual=" + UPDATED_SGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsBySgstPercentageIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sgstPercentage is less than or equal to DEFAULT_SGST_PERCENTAGE
        defaultProductShouldBeFound("sgstPercentage.lessThanOrEqual=" + DEFAULT_SGST_PERCENTAGE);

        // Get all the productList where sgstPercentage is less than or equal to SMALLER_SGST_PERCENTAGE
        defaultProductShouldNotBeFound("sgstPercentage.lessThanOrEqual=" + SMALLER_SGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsBySgstPercentageIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sgstPercentage is less than DEFAULT_SGST_PERCENTAGE
        defaultProductShouldNotBeFound("sgstPercentage.lessThan=" + DEFAULT_SGST_PERCENTAGE);

        // Get all the productList where sgstPercentage is less than UPDATED_SGST_PERCENTAGE
        defaultProductShouldBeFound("sgstPercentage.lessThan=" + UPDATED_SGST_PERCENTAGE);
    }

    @Test
    @Transactional
    public void getAllProductsBySgstPercentageIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sgstPercentage is greater than DEFAULT_SGST_PERCENTAGE
        defaultProductShouldNotBeFound("sgstPercentage.greaterThan=" + DEFAULT_SGST_PERCENTAGE);

        // Get all the productList where sgstPercentage is greater than SMALLER_SGST_PERCENTAGE
        defaultProductShouldBeFound("sgstPercentage.greaterThan=" + SMALLER_SGST_PERCENTAGE);
    }


    @Test
    @Transactional
    public void getAllProductsByTotalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where totalPrice equals to DEFAULT_TOTAL_PRICE
        defaultProductShouldBeFound("totalPrice.equals=" + DEFAULT_TOTAL_PRICE);

        // Get all the productList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultProductShouldNotBeFound("totalPrice.equals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where totalPrice not equals to DEFAULT_TOTAL_PRICE
        defaultProductShouldNotBeFound("totalPrice.notEquals=" + DEFAULT_TOTAL_PRICE);

        // Get all the productList where totalPrice not equals to UPDATED_TOTAL_PRICE
        defaultProductShouldBeFound("totalPrice.notEquals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where totalPrice in DEFAULT_TOTAL_PRICE or UPDATED_TOTAL_PRICE
        defaultProductShouldBeFound("totalPrice.in=" + DEFAULT_TOTAL_PRICE + "," + UPDATED_TOTAL_PRICE);

        // Get all the productList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultProductShouldNotBeFound("totalPrice.in=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where totalPrice is not null
        defaultProductShouldBeFound("totalPrice.specified=true");

        // Get all the productList where totalPrice is null
        defaultProductShouldNotBeFound("totalPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByTotalPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where totalPrice is greater than or equal to DEFAULT_TOTAL_PRICE
        defaultProductShouldBeFound("totalPrice.greaterThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the productList where totalPrice is greater than or equal to UPDATED_TOTAL_PRICE
        defaultProductShouldNotBeFound("totalPrice.greaterThanOrEqual=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where totalPrice is less than or equal to DEFAULT_TOTAL_PRICE
        defaultProductShouldBeFound("totalPrice.lessThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the productList where totalPrice is less than or equal to SMALLER_TOTAL_PRICE
        defaultProductShouldNotBeFound("totalPrice.lessThanOrEqual=" + SMALLER_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where totalPrice is less than DEFAULT_TOTAL_PRICE
        defaultProductShouldNotBeFound("totalPrice.lessThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the productList where totalPrice is less than UPDATED_TOTAL_PRICE
        defaultProductShouldBeFound("totalPrice.lessThan=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductsByTotalPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where totalPrice is greater than DEFAULT_TOTAL_PRICE
        defaultProductShouldNotBeFound("totalPrice.greaterThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the productList where totalPrice is greater than SMALLER_TOTAL_PRICE
        defaultProductShouldBeFound("totalPrice.greaterThan=" + SMALLER_TOTAL_PRICE);
    }


    @Test
    @Transactional
    public void getAllProductsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where description equals to DEFAULT_DESCRIPTION
        defaultProductShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the productList where description equals to UPDATED_DESCRIPTION
        defaultProductShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where description not equals to DEFAULT_DESCRIPTION
        defaultProductShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the productList where description not equals to UPDATED_DESCRIPTION
        defaultProductShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultProductShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the productList where description equals to UPDATED_DESCRIPTION
        defaultProductShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where description is not null
        defaultProductShouldBeFound("description.specified=true");

        // Get all the productList where description is null
        defaultProductShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where description contains DEFAULT_DESCRIPTION
        defaultProductShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the productList where description contains UPDATED_DESCRIPTION
        defaultProductShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where description does not contain DEFAULT_DESCRIPTION
        defaultProductShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the productList where description does not contain UPDATED_DESCRIPTION
        defaultProductShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllProductsByLongDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where longDescription equals to DEFAULT_LONG_DESCRIPTION
        defaultProductShouldBeFound("longDescription.equals=" + DEFAULT_LONG_DESCRIPTION);

        // Get all the productList where longDescription equals to UPDATED_LONG_DESCRIPTION
        defaultProductShouldNotBeFound("longDescription.equals=" + UPDATED_LONG_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByLongDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where longDescription not equals to DEFAULT_LONG_DESCRIPTION
        defaultProductShouldNotBeFound("longDescription.notEquals=" + DEFAULT_LONG_DESCRIPTION);

        // Get all the productList where longDescription not equals to UPDATED_LONG_DESCRIPTION
        defaultProductShouldBeFound("longDescription.notEquals=" + UPDATED_LONG_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByLongDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where longDescription in DEFAULT_LONG_DESCRIPTION or UPDATED_LONG_DESCRIPTION
        defaultProductShouldBeFound("longDescription.in=" + DEFAULT_LONG_DESCRIPTION + "," + UPDATED_LONG_DESCRIPTION);

        // Get all the productList where longDescription equals to UPDATED_LONG_DESCRIPTION
        defaultProductShouldNotBeFound("longDescription.in=" + UPDATED_LONG_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByLongDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where longDescription is not null
        defaultProductShouldBeFound("longDescription.specified=true");

        // Get all the productList where longDescription is null
        defaultProductShouldNotBeFound("longDescription.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByLongDescriptionContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where longDescription contains DEFAULT_LONG_DESCRIPTION
        defaultProductShouldBeFound("longDescription.contains=" + DEFAULT_LONG_DESCRIPTION);

        // Get all the productList where longDescription contains UPDATED_LONG_DESCRIPTION
        defaultProductShouldNotBeFound("longDescription.contains=" + UPDATED_LONG_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllProductsByLongDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where longDescription does not contain DEFAULT_LONG_DESCRIPTION
        defaultProductShouldNotBeFound("longDescription.doesNotContain=" + DEFAULT_LONG_DESCRIPTION);

        // Get all the productList where longDescription does not contain UPDATED_LONG_DESCRIPTION
        defaultProductShouldBeFound("longDescription.doesNotContain=" + UPDATED_LONG_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllProductsByIsVirtualIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isVirtual equals to DEFAULT_IS_VIRTUAL
        defaultProductShouldBeFound("isVirtual.equals=" + DEFAULT_IS_VIRTUAL);

        // Get all the productList where isVirtual equals to UPDATED_IS_VIRTUAL
        defaultProductShouldNotBeFound("isVirtual.equals=" + UPDATED_IS_VIRTUAL);
    }

    @Test
    @Transactional
    public void getAllProductsByIsVirtualIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isVirtual not equals to DEFAULT_IS_VIRTUAL
        defaultProductShouldNotBeFound("isVirtual.notEquals=" + DEFAULT_IS_VIRTUAL);

        // Get all the productList where isVirtual not equals to UPDATED_IS_VIRTUAL
        defaultProductShouldBeFound("isVirtual.notEquals=" + UPDATED_IS_VIRTUAL);
    }

    @Test
    @Transactional
    public void getAllProductsByIsVirtualIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isVirtual in DEFAULT_IS_VIRTUAL or UPDATED_IS_VIRTUAL
        defaultProductShouldBeFound("isVirtual.in=" + DEFAULT_IS_VIRTUAL + "," + UPDATED_IS_VIRTUAL);

        // Get all the productList where isVirtual equals to UPDATED_IS_VIRTUAL
        defaultProductShouldNotBeFound("isVirtual.in=" + UPDATED_IS_VIRTUAL);
    }

    @Test
    @Transactional
    public void getAllProductsByIsVirtualIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isVirtual is not null
        defaultProductShouldBeFound("isVirtual.specified=true");

        // Get all the productList where isVirtual is null
        defaultProductShouldNotBeFound("isVirtual.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByIsVariantIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isVariant equals to DEFAULT_IS_VARIANT
        defaultProductShouldBeFound("isVariant.equals=" + DEFAULT_IS_VARIANT);

        // Get all the productList where isVariant equals to UPDATED_IS_VARIANT
        defaultProductShouldNotBeFound("isVariant.equals=" + UPDATED_IS_VARIANT);
    }

    @Test
    @Transactional
    public void getAllProductsByIsVariantIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isVariant not equals to DEFAULT_IS_VARIANT
        defaultProductShouldNotBeFound("isVariant.notEquals=" + DEFAULT_IS_VARIANT);

        // Get all the productList where isVariant not equals to UPDATED_IS_VARIANT
        defaultProductShouldBeFound("isVariant.notEquals=" + UPDATED_IS_VARIANT);
    }

    @Test
    @Transactional
    public void getAllProductsByIsVariantIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isVariant in DEFAULT_IS_VARIANT or UPDATED_IS_VARIANT
        defaultProductShouldBeFound("isVariant.in=" + DEFAULT_IS_VARIANT + "," + UPDATED_IS_VARIANT);

        // Get all the productList where isVariant equals to UPDATED_IS_VARIANT
        defaultProductShouldNotBeFound("isVariant.in=" + UPDATED_IS_VARIANT);
    }

    @Test
    @Transactional
    public void getAllProductsByIsVariantIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isVariant is not null
        defaultProductShouldBeFound("isVariant.specified=true");

        // Get all the productList where isVariant is null
        defaultProductShouldNotBeFound("isVariant.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByRequireInventoryIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where requireInventory equals to DEFAULT_REQUIRE_INVENTORY
        defaultProductShouldBeFound("requireInventory.equals=" + DEFAULT_REQUIRE_INVENTORY);

        // Get all the productList where requireInventory equals to UPDATED_REQUIRE_INVENTORY
        defaultProductShouldNotBeFound("requireInventory.equals=" + UPDATED_REQUIRE_INVENTORY);
    }

    @Test
    @Transactional
    public void getAllProductsByRequireInventoryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where requireInventory not equals to DEFAULT_REQUIRE_INVENTORY
        defaultProductShouldNotBeFound("requireInventory.notEquals=" + DEFAULT_REQUIRE_INVENTORY);

        // Get all the productList where requireInventory not equals to UPDATED_REQUIRE_INVENTORY
        defaultProductShouldBeFound("requireInventory.notEquals=" + UPDATED_REQUIRE_INVENTORY);
    }

    @Test
    @Transactional
    public void getAllProductsByRequireInventoryIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where requireInventory in DEFAULT_REQUIRE_INVENTORY or UPDATED_REQUIRE_INVENTORY
        defaultProductShouldBeFound("requireInventory.in=" + DEFAULT_REQUIRE_INVENTORY + "," + UPDATED_REQUIRE_INVENTORY);

        // Get all the productList where requireInventory equals to UPDATED_REQUIRE_INVENTORY
        defaultProductShouldNotBeFound("requireInventory.in=" + UPDATED_REQUIRE_INVENTORY);
    }

    @Test
    @Transactional
    public void getAllProductsByRequireInventoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where requireInventory is not null
        defaultProductShouldBeFound("requireInventory.specified=true");

        // Get all the productList where requireInventory is null
        defaultProductShouldNotBeFound("requireInventory.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByReturnableIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where returnable equals to DEFAULT_RETURNABLE
        defaultProductShouldBeFound("returnable.equals=" + DEFAULT_RETURNABLE);

        // Get all the productList where returnable equals to UPDATED_RETURNABLE
        defaultProductShouldNotBeFound("returnable.equals=" + UPDATED_RETURNABLE);
    }

    @Test
    @Transactional
    public void getAllProductsByReturnableIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where returnable not equals to DEFAULT_RETURNABLE
        defaultProductShouldNotBeFound("returnable.notEquals=" + DEFAULT_RETURNABLE);

        // Get all the productList where returnable not equals to UPDATED_RETURNABLE
        defaultProductShouldBeFound("returnable.notEquals=" + UPDATED_RETURNABLE);
    }

    @Test
    @Transactional
    public void getAllProductsByReturnableIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where returnable in DEFAULT_RETURNABLE or UPDATED_RETURNABLE
        defaultProductShouldBeFound("returnable.in=" + DEFAULT_RETURNABLE + "," + UPDATED_RETURNABLE);

        // Get all the productList where returnable equals to UPDATED_RETURNABLE
        defaultProductShouldNotBeFound("returnable.in=" + UPDATED_RETURNABLE);
    }

    @Test
    @Transactional
    public void getAllProductsByReturnableIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where returnable is not null
        defaultProductShouldBeFound("returnable.specified=true");

        // Get all the productList where returnable is null
        defaultProductShouldNotBeFound("returnable.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByIsPopularIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isPopular equals to DEFAULT_IS_POPULAR
        defaultProductShouldBeFound("isPopular.equals=" + DEFAULT_IS_POPULAR);

        // Get all the productList where isPopular equals to UPDATED_IS_POPULAR
        defaultProductShouldNotBeFound("isPopular.equals=" + UPDATED_IS_POPULAR);
    }

    @Test
    @Transactional
    public void getAllProductsByIsPopularIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isPopular not equals to DEFAULT_IS_POPULAR
        defaultProductShouldNotBeFound("isPopular.notEquals=" + DEFAULT_IS_POPULAR);

        // Get all the productList where isPopular not equals to UPDATED_IS_POPULAR
        defaultProductShouldBeFound("isPopular.notEquals=" + UPDATED_IS_POPULAR);
    }

    @Test
    @Transactional
    public void getAllProductsByIsPopularIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isPopular in DEFAULT_IS_POPULAR or UPDATED_IS_POPULAR
        defaultProductShouldBeFound("isPopular.in=" + DEFAULT_IS_POPULAR + "," + UPDATED_IS_POPULAR);

        // Get all the productList where isPopular equals to UPDATED_IS_POPULAR
        defaultProductShouldNotBeFound("isPopular.in=" + UPDATED_IS_POPULAR);
    }

    @Test
    @Transactional
    public void getAllProductsByIsPopularIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isPopular is not null
        defaultProductShouldBeFound("isPopular.specified=true");

        // Get all the productList where isPopular is null
        defaultProductShouldNotBeFound("isPopular.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByChangeControlNoIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where changeControlNo equals to DEFAULT_CHANGE_CONTROL_NO
        defaultProductShouldBeFound("changeControlNo.equals=" + DEFAULT_CHANGE_CONTROL_NO);

        // Get all the productList where changeControlNo equals to UPDATED_CHANGE_CONTROL_NO
        defaultProductShouldNotBeFound("changeControlNo.equals=" + UPDATED_CHANGE_CONTROL_NO);
    }

    @Test
    @Transactional
    public void getAllProductsByChangeControlNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where changeControlNo not equals to DEFAULT_CHANGE_CONTROL_NO
        defaultProductShouldNotBeFound("changeControlNo.notEquals=" + DEFAULT_CHANGE_CONTROL_NO);

        // Get all the productList where changeControlNo not equals to UPDATED_CHANGE_CONTROL_NO
        defaultProductShouldBeFound("changeControlNo.notEquals=" + UPDATED_CHANGE_CONTROL_NO);
    }

    @Test
    @Transactional
    public void getAllProductsByChangeControlNoIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where changeControlNo in DEFAULT_CHANGE_CONTROL_NO or UPDATED_CHANGE_CONTROL_NO
        defaultProductShouldBeFound("changeControlNo.in=" + DEFAULT_CHANGE_CONTROL_NO + "," + UPDATED_CHANGE_CONTROL_NO);

        // Get all the productList where changeControlNo equals to UPDATED_CHANGE_CONTROL_NO
        defaultProductShouldNotBeFound("changeControlNo.in=" + UPDATED_CHANGE_CONTROL_NO);
    }

    @Test
    @Transactional
    public void getAllProductsByChangeControlNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where changeControlNo is not null
        defaultProductShouldBeFound("changeControlNo.specified=true");

        // Get all the productList where changeControlNo is null
        defaultProductShouldNotBeFound("changeControlNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByChangeControlNoContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where changeControlNo contains DEFAULT_CHANGE_CONTROL_NO
        defaultProductShouldBeFound("changeControlNo.contains=" + DEFAULT_CHANGE_CONTROL_NO);

        // Get all the productList where changeControlNo contains UPDATED_CHANGE_CONTROL_NO
        defaultProductShouldNotBeFound("changeControlNo.contains=" + UPDATED_CHANGE_CONTROL_NO);
    }

    @Test
    @Transactional
    public void getAllProductsByChangeControlNoNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where changeControlNo does not contain DEFAULT_CHANGE_CONTROL_NO
        defaultProductShouldNotBeFound("changeControlNo.doesNotContain=" + DEFAULT_CHANGE_CONTROL_NO);

        // Get all the productList where changeControlNo does not contain UPDATED_CHANGE_CONTROL_NO
        defaultProductShouldBeFound("changeControlNo.doesNotContain=" + UPDATED_CHANGE_CONTROL_NO);
    }


    @Test
    @Transactional
    public void getAllProductsByRetestDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where retestDuration equals to DEFAULT_RETEST_DURATION
        defaultProductShouldBeFound("retestDuration.equals=" + DEFAULT_RETEST_DURATION);

        // Get all the productList where retestDuration equals to UPDATED_RETEST_DURATION
        defaultProductShouldNotBeFound("retestDuration.equals=" + UPDATED_RETEST_DURATION);
    }

    @Test
    @Transactional
    public void getAllProductsByRetestDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where retestDuration not equals to DEFAULT_RETEST_DURATION
        defaultProductShouldNotBeFound("retestDuration.notEquals=" + DEFAULT_RETEST_DURATION);

        // Get all the productList where retestDuration not equals to UPDATED_RETEST_DURATION
        defaultProductShouldBeFound("retestDuration.notEquals=" + UPDATED_RETEST_DURATION);
    }

    @Test
    @Transactional
    public void getAllProductsByRetestDurationIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where retestDuration in DEFAULT_RETEST_DURATION or UPDATED_RETEST_DURATION
        defaultProductShouldBeFound("retestDuration.in=" + DEFAULT_RETEST_DURATION + "," + UPDATED_RETEST_DURATION);

        // Get all the productList where retestDuration equals to UPDATED_RETEST_DURATION
        defaultProductShouldNotBeFound("retestDuration.in=" + UPDATED_RETEST_DURATION);
    }

    @Test
    @Transactional
    public void getAllProductsByRetestDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where retestDuration is not null
        defaultProductShouldBeFound("retestDuration.specified=true");

        // Get all the productList where retestDuration is null
        defaultProductShouldNotBeFound("retestDuration.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByRetestDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where retestDuration is greater than or equal to DEFAULT_RETEST_DURATION
        defaultProductShouldBeFound("retestDuration.greaterThanOrEqual=" + DEFAULT_RETEST_DURATION);

        // Get all the productList where retestDuration is greater than or equal to UPDATED_RETEST_DURATION
        defaultProductShouldNotBeFound("retestDuration.greaterThanOrEqual=" + UPDATED_RETEST_DURATION);
    }

    @Test
    @Transactional
    public void getAllProductsByRetestDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where retestDuration is less than or equal to DEFAULT_RETEST_DURATION
        defaultProductShouldBeFound("retestDuration.lessThanOrEqual=" + DEFAULT_RETEST_DURATION);

        // Get all the productList where retestDuration is less than or equal to SMALLER_RETEST_DURATION
        defaultProductShouldNotBeFound("retestDuration.lessThanOrEqual=" + SMALLER_RETEST_DURATION);
    }

    @Test
    @Transactional
    public void getAllProductsByRetestDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where retestDuration is less than DEFAULT_RETEST_DURATION
        defaultProductShouldNotBeFound("retestDuration.lessThan=" + DEFAULT_RETEST_DURATION);

        // Get all the productList where retestDuration is less than UPDATED_RETEST_DURATION
        defaultProductShouldBeFound("retestDuration.lessThan=" + UPDATED_RETEST_DURATION);
    }

    @Test
    @Transactional
    public void getAllProductsByRetestDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where retestDuration is greater than DEFAULT_RETEST_DURATION
        defaultProductShouldNotBeFound("retestDuration.greaterThan=" + DEFAULT_RETEST_DURATION);

        // Get all the productList where retestDuration is greater than SMALLER_RETEST_DURATION
        defaultProductShouldBeFound("retestDuration.greaterThan=" + SMALLER_RETEST_DURATION);
    }


    @Test
    @Transactional
    public void getAllProductsByExpiryDurationIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where expiryDuration equals to DEFAULT_EXPIRY_DURATION
        defaultProductShouldBeFound("expiryDuration.equals=" + DEFAULT_EXPIRY_DURATION);

        // Get all the productList where expiryDuration equals to UPDATED_EXPIRY_DURATION
        defaultProductShouldNotBeFound("expiryDuration.equals=" + UPDATED_EXPIRY_DURATION);
    }

    @Test
    @Transactional
    public void getAllProductsByExpiryDurationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where expiryDuration not equals to DEFAULT_EXPIRY_DURATION
        defaultProductShouldNotBeFound("expiryDuration.notEquals=" + DEFAULT_EXPIRY_DURATION);

        // Get all the productList where expiryDuration not equals to UPDATED_EXPIRY_DURATION
        defaultProductShouldBeFound("expiryDuration.notEquals=" + UPDATED_EXPIRY_DURATION);
    }

    @Test
    @Transactional
    public void getAllProductsByExpiryDurationIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where expiryDuration in DEFAULT_EXPIRY_DURATION or UPDATED_EXPIRY_DURATION
        defaultProductShouldBeFound("expiryDuration.in=" + DEFAULT_EXPIRY_DURATION + "," + UPDATED_EXPIRY_DURATION);

        // Get all the productList where expiryDuration equals to UPDATED_EXPIRY_DURATION
        defaultProductShouldNotBeFound("expiryDuration.in=" + UPDATED_EXPIRY_DURATION);
    }

    @Test
    @Transactional
    public void getAllProductsByExpiryDurationIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where expiryDuration is not null
        defaultProductShouldBeFound("expiryDuration.specified=true");

        // Get all the productList where expiryDuration is null
        defaultProductShouldNotBeFound("expiryDuration.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByExpiryDurationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where expiryDuration is greater than or equal to DEFAULT_EXPIRY_DURATION
        defaultProductShouldBeFound("expiryDuration.greaterThanOrEqual=" + DEFAULT_EXPIRY_DURATION);

        // Get all the productList where expiryDuration is greater than or equal to UPDATED_EXPIRY_DURATION
        defaultProductShouldNotBeFound("expiryDuration.greaterThanOrEqual=" + UPDATED_EXPIRY_DURATION);
    }

    @Test
    @Transactional
    public void getAllProductsByExpiryDurationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where expiryDuration is less than or equal to DEFAULT_EXPIRY_DURATION
        defaultProductShouldBeFound("expiryDuration.lessThanOrEqual=" + DEFAULT_EXPIRY_DURATION);

        // Get all the productList where expiryDuration is less than or equal to SMALLER_EXPIRY_DURATION
        defaultProductShouldNotBeFound("expiryDuration.lessThanOrEqual=" + SMALLER_EXPIRY_DURATION);
    }

    @Test
    @Transactional
    public void getAllProductsByExpiryDurationIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where expiryDuration is less than DEFAULT_EXPIRY_DURATION
        defaultProductShouldNotBeFound("expiryDuration.lessThan=" + DEFAULT_EXPIRY_DURATION);

        // Get all the productList where expiryDuration is less than UPDATED_EXPIRY_DURATION
        defaultProductShouldBeFound("expiryDuration.lessThan=" + UPDATED_EXPIRY_DURATION);
    }

    @Test
    @Transactional
    public void getAllProductsByExpiryDurationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where expiryDuration is greater than DEFAULT_EXPIRY_DURATION
        defaultProductShouldNotBeFound("expiryDuration.greaterThan=" + DEFAULT_EXPIRY_DURATION);

        // Get all the productList where expiryDuration is greater than SMALLER_EXPIRY_DURATION
        defaultProductShouldBeFound("expiryDuration.greaterThan=" + SMALLER_EXPIRY_DURATION);
    }


    @Test
    @Transactional
    public void getAllProductsByValidationTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where validationType equals to DEFAULT_VALIDATION_TYPE
        defaultProductShouldBeFound("validationType.equals=" + DEFAULT_VALIDATION_TYPE);

        // Get all the productList where validationType equals to UPDATED_VALIDATION_TYPE
        defaultProductShouldNotBeFound("validationType.equals=" + UPDATED_VALIDATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductsByValidationTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where validationType not equals to DEFAULT_VALIDATION_TYPE
        defaultProductShouldNotBeFound("validationType.notEquals=" + DEFAULT_VALIDATION_TYPE);

        // Get all the productList where validationType not equals to UPDATED_VALIDATION_TYPE
        defaultProductShouldBeFound("validationType.notEquals=" + UPDATED_VALIDATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductsByValidationTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where validationType in DEFAULT_VALIDATION_TYPE or UPDATED_VALIDATION_TYPE
        defaultProductShouldBeFound("validationType.in=" + DEFAULT_VALIDATION_TYPE + "," + UPDATED_VALIDATION_TYPE);

        // Get all the productList where validationType equals to UPDATED_VALIDATION_TYPE
        defaultProductShouldNotBeFound("validationType.in=" + UPDATED_VALIDATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductsByValidationTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where validationType is not null
        defaultProductShouldBeFound("validationType.specified=true");

        // Get all the productList where validationType is null
        defaultProductShouldNotBeFound("validationType.specified=false");
    }
                @Test
    @Transactional
    public void getAllProductsByValidationTypeContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where validationType contains DEFAULT_VALIDATION_TYPE
        defaultProductShouldBeFound("validationType.contains=" + DEFAULT_VALIDATION_TYPE);

        // Get all the productList where validationType contains UPDATED_VALIDATION_TYPE
        defaultProductShouldNotBeFound("validationType.contains=" + UPDATED_VALIDATION_TYPE);
    }

    @Test
    @Transactional
    public void getAllProductsByValidationTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where validationType does not contain DEFAULT_VALIDATION_TYPE
        defaultProductShouldNotBeFound("validationType.doesNotContain=" + DEFAULT_VALIDATION_TYPE);

        // Get all the productList where validationType does not contain UPDATED_VALIDATION_TYPE
        defaultProductShouldBeFound("validationType.doesNotContain=" + UPDATED_VALIDATION_TYPE);
    }


    @Test
    @Transactional
    public void getAllProductsByShelfLifeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shelfLife equals to DEFAULT_SHELF_LIFE
        defaultProductShouldBeFound("shelfLife.equals=" + DEFAULT_SHELF_LIFE);

        // Get all the productList where shelfLife equals to UPDATED_SHELF_LIFE
        defaultProductShouldNotBeFound("shelfLife.equals=" + UPDATED_SHELF_LIFE);
    }

    @Test
    @Transactional
    public void getAllProductsByShelfLifeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shelfLife not equals to DEFAULT_SHELF_LIFE
        defaultProductShouldNotBeFound("shelfLife.notEquals=" + DEFAULT_SHELF_LIFE);

        // Get all the productList where shelfLife not equals to UPDATED_SHELF_LIFE
        defaultProductShouldBeFound("shelfLife.notEquals=" + UPDATED_SHELF_LIFE);
    }

    @Test
    @Transactional
    public void getAllProductsByShelfLifeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shelfLife in DEFAULT_SHELF_LIFE or UPDATED_SHELF_LIFE
        defaultProductShouldBeFound("shelfLife.in=" + DEFAULT_SHELF_LIFE + "," + UPDATED_SHELF_LIFE);

        // Get all the productList where shelfLife equals to UPDATED_SHELF_LIFE
        defaultProductShouldNotBeFound("shelfLife.in=" + UPDATED_SHELF_LIFE);
    }

    @Test
    @Transactional
    public void getAllProductsByShelfLifeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shelfLife is not null
        defaultProductShouldBeFound("shelfLife.specified=true");

        // Get all the productList where shelfLife is null
        defaultProductShouldNotBeFound("shelfLife.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductsByShelfLifeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shelfLife is greater than or equal to DEFAULT_SHELF_LIFE
        defaultProductShouldBeFound("shelfLife.greaterThanOrEqual=" + DEFAULT_SHELF_LIFE);

        // Get all the productList where shelfLife is greater than or equal to UPDATED_SHELF_LIFE
        defaultProductShouldNotBeFound("shelfLife.greaterThanOrEqual=" + UPDATED_SHELF_LIFE);
    }

    @Test
    @Transactional
    public void getAllProductsByShelfLifeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shelfLife is less than or equal to DEFAULT_SHELF_LIFE
        defaultProductShouldBeFound("shelfLife.lessThanOrEqual=" + DEFAULT_SHELF_LIFE);

        // Get all the productList where shelfLife is less than or equal to SMALLER_SHELF_LIFE
        defaultProductShouldNotBeFound("shelfLife.lessThanOrEqual=" + SMALLER_SHELF_LIFE);
    }

    @Test
    @Transactional
    public void getAllProductsByShelfLifeIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shelfLife is less than DEFAULT_SHELF_LIFE
        defaultProductShouldNotBeFound("shelfLife.lessThan=" + DEFAULT_SHELF_LIFE);

        // Get all the productList where shelfLife is less than UPDATED_SHELF_LIFE
        defaultProductShouldBeFound("shelfLife.lessThan=" + UPDATED_SHELF_LIFE);
    }

    @Test
    @Transactional
    public void getAllProductsByShelfLifeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shelfLife is greater than DEFAULT_SHELF_LIFE
        defaultProductShouldNotBeFound("shelfLife.greaterThan=" + DEFAULT_SHELF_LIFE);

        // Get all the productList where shelfLife is greater than SMALLER_SHELF_LIFE
        defaultProductShouldBeFound("shelfLife.greaterThan=" + SMALLER_SHELF_LIFE);
    }


    @Test
    @Transactional
    public void getAllProductsByProductTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        ProductType productType = ProductTypeResourceIT.createEntity(em);
        em.persist(productType);
        em.flush();
        product.setProductType(productType);
        productRepository.saveAndFlush(product);
        Long productTypeId = productType.getId();

        // Get all the productList where productType equals to productTypeId
        defaultProductShouldBeFound("productTypeId.equals=" + productTypeId);

        // Get all the productList where productType equals to productTypeId + 1
        defaultProductShouldNotBeFound("productTypeId.equals=" + (productTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByQuantityUomIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        Uom quantityUom = UomResourceIT.createEntity(em);
        em.persist(quantityUom);
        em.flush();
        product.setQuantityUom(quantityUom);
        productRepository.saveAndFlush(product);
        Long quantityUomId = quantityUom.getId();

        // Get all the productList where quantityUom equals to quantityUomId
        defaultProductShouldBeFound("quantityUomId.equals=" + quantityUomId);

        // Get all the productList where quantityUom equals to quantityUomId + 1
        defaultProductShouldNotBeFound("quantityUomId.equals=" + (quantityUomId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByWeightUomIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        Uom weightUom = UomResourceIT.createEntity(em);
        em.persist(weightUom);
        em.flush();
        product.setWeightUom(weightUom);
        productRepository.saveAndFlush(product);
        Long weightUomId = weightUom.getId();

        // Get all the productList where weightUom equals to weightUomId
        defaultProductShouldBeFound("weightUomId.equals=" + weightUomId);

        // Get all the productList where weightUom equals to weightUomId + 1
        defaultProductShouldNotBeFound("weightUomId.equals=" + (weightUomId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsBySizeUomIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        Uom sizeUom = UomResourceIT.createEntity(em);
        em.persist(sizeUom);
        em.flush();
        product.setSizeUom(sizeUom);
        productRepository.saveAndFlush(product);
        Long sizeUomId = sizeUom.getId();

        // Get all the productList where sizeUom equals to sizeUomId
        defaultProductShouldBeFound("sizeUomId.equals=" + sizeUomId);

        // Get all the productList where sizeUom equals to sizeUomId + 1
        defaultProductShouldNotBeFound("sizeUomId.equals=" + (sizeUomId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByUomIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        Uom uom = UomResourceIT.createEntity(em);
        em.persist(uom);
        em.flush();
        product.setUom(uom);
        productRepository.saveAndFlush(product);
        Long uomId = uom.getId();

        // Get all the productList where uom equals to uomId
        defaultProductShouldBeFound("uomId.equals=" + uomId);

        // Get all the productList where uom equals to uomId + 1
        defaultProductShouldNotBeFound("uomId.equals=" + (uomId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByPrimaryProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        ProductCategory primaryProductCategory = ProductCategoryResourceIT.createEntity(em);
        em.persist(primaryProductCategory);
        em.flush();
        product.setPrimaryProductCategory(primaryProductCategory);
        productRepository.saveAndFlush(product);
        Long primaryProductCategoryId = primaryProductCategory.getId();

        // Get all the productList where primaryProductCategory equals to primaryProductCategoryId
        defaultProductShouldBeFound("primaryProductCategoryId.equals=" + primaryProductCategoryId);

        // Get all the productList where primaryProductCategory equals to primaryProductCategoryId + 1
        defaultProductShouldNotBeFound("primaryProductCategoryId.equals=" + (primaryProductCategoryId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByVirtualProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        Product virtualProduct = ProductResourceIT.createEntity(em);
        em.persist(virtualProduct);
        em.flush();
        product.setVirtualProduct(virtualProduct);
        productRepository.saveAndFlush(product);
        Long virtualProductId = virtualProduct.getId();

        // Get all the productList where virtualProduct equals to virtualProductId
        defaultProductShouldBeFound("virtualProductId.equals=" + virtualProductId);

        // Get all the productList where virtualProduct equals to virtualProductId + 1
        defaultProductShouldNotBeFound("virtualProductId.equals=" + (virtualProductId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByInventoryItemTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        InventoryItemType inventoryItemType = InventoryItemTypeResourceIT.createEntity(em);
        em.persist(inventoryItemType);
        em.flush();
        product.setInventoryItemType(inventoryItemType);
        productRepository.saveAndFlush(product);
        Long inventoryItemTypeId = inventoryItemType.getId();

        // Get all the productList where inventoryItemType equals to inventoryItemTypeId
        defaultProductShouldBeFound("inventoryItemTypeId.equals=" + inventoryItemTypeId);

        // Get all the productList where inventoryItemType equals to inventoryItemTypeId + 1
        defaultProductShouldNotBeFound("inventoryItemTypeId.equals=" + (inventoryItemTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByTaxSlabIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        TaxSlab taxSlab = TaxSlabResourceIT.createEntity(em);
        em.persist(taxSlab);
        em.flush();
        product.setTaxSlab(taxSlab);
        productRepository.saveAndFlush(product);
        Long taxSlabId = taxSlab.getId();

        // Get all the productList where taxSlab equals to taxSlabId
        defaultProductShouldBeFound("taxSlabId.equals=" + taxSlabId);

        // Get all the productList where taxSlab equals to taxSlabId + 1
        defaultProductShouldNotBeFound("taxSlabId.equals=" + (taxSlabId + 1));
    }


    @Test
    @Transactional
    public void getAllProductsByShelfLifeUomIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        Uom shelfLifeUom = UomResourceIT.createEntity(em);
        em.persist(shelfLifeUom);
        em.flush();
        product.setShelfLifeUom(shelfLifeUom);
        productRepository.saveAndFlush(product);
        Long shelfLifeUomId = shelfLifeUom.getId();

        // Get all the productList where shelfLifeUom equals to shelfLifeUomId
        defaultProductShouldBeFound("shelfLifeUomId.equals=" + shelfLifeUomId);

        // Get all the productList where shelfLifeUom equals to shelfLifeUomId + 1
        defaultProductShouldNotBeFound("shelfLifeUomId.equals=" + (shelfLifeUomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductShouldBeFound(String filter) throws Exception {
        restProductMockMvc.perform(get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].internalName").value(hasItem(DEFAULT_INTERNAL_NAME)))
            .andExpect(jsonPath("$.[*].brandName").value(hasItem(DEFAULT_BRAND_NAME)))
            .andExpect(jsonPath("$.[*].variant").value(hasItem(DEFAULT_VARIANT)))
            .andExpect(jsonPath("$.[*].sku").value(hasItem(DEFAULT_SKU)))
            .andExpect(jsonPath("$.[*].introductionDate").value(hasItem(sameInstant(DEFAULT_INTRODUCTION_DATE))))
            .andExpect(jsonPath("$.[*].releaseDate").value(hasItem(sameInstant(DEFAULT_RELEASE_DATE))))
            .andExpect(jsonPath("$.[*].quantityIncluded").value(hasItem(DEFAULT_QUANTITY_INCLUDED.intValue())))
            .andExpect(jsonPath("$.[*].piecesIncluded").value(hasItem(DEFAULT_PIECES_INCLUDED)))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.intValue())))
            .andExpect(jsonPath("$.[*].width").value(hasItem(DEFAULT_WIDTH.intValue())))
            .andExpect(jsonPath("$.[*].depth").value(hasItem(DEFAULT_DEPTH.intValue())))
            .andExpect(jsonPath("$.[*].smallImageUrl").value(hasItem(DEFAULT_SMALL_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].mediumImageUrl").value(hasItem(DEFAULT_MEDIUM_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].largeImageUrl").value(hasItem(DEFAULT_LARGE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].detailImageUrl").value(hasItem(DEFAULT_DETAIL_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].originalImageUrl").value(hasItem(DEFAULT_ORIGINAL_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].cgst").value(hasItem(DEFAULT_CGST.intValue())))
            .andExpect(jsonPath("$.[*].igst").value(hasItem(DEFAULT_IGST.intValue())))
            .andExpect(jsonPath("$.[*].sgst").value(hasItem(DEFAULT_SGST.intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].cgstPercentage").value(hasItem(DEFAULT_CGST_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].igstPercentage").value(hasItem(DEFAULT_IGST_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].sgstPercentage").value(hasItem(DEFAULT_SGST_PERCENTAGE.intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].longDescription").value(hasItem(DEFAULT_LONG_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].info").value(hasItem(DEFAULT_INFO.toString())))
            .andExpect(jsonPath("$.[*].isVirtual").value(hasItem(DEFAULT_IS_VIRTUAL.booleanValue())))
            .andExpect(jsonPath("$.[*].isVariant").value(hasItem(DEFAULT_IS_VARIANT.booleanValue())))
            .andExpect(jsonPath("$.[*].requireInventory").value(hasItem(DEFAULT_REQUIRE_INVENTORY.booleanValue())))
            .andExpect(jsonPath("$.[*].returnable").value(hasItem(DEFAULT_RETURNABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].isPopular").value(hasItem(DEFAULT_IS_POPULAR.booleanValue())))
            .andExpect(jsonPath("$.[*].changeControlNo").value(hasItem(DEFAULT_CHANGE_CONTROL_NO)))
            .andExpect(jsonPath("$.[*].retestDuration").value(hasItem(DEFAULT_RETEST_DURATION.toString())))
            .andExpect(jsonPath("$.[*].expiryDuration").value(hasItem(DEFAULT_EXPIRY_DURATION.toString())))
            .andExpect(jsonPath("$.[*].validationType").value(hasItem(DEFAULT_VALIDATION_TYPE)))
            .andExpect(jsonPath("$.[*].shelfLife").value(hasItem(DEFAULT_SHELF_LIFE)));

        // Check, that the count call also returns 1
        restProductMockMvc.perform(get("/api/products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductShouldNotBeFound(String filter) throws Exception {
        restProductMockMvc.perform(get("/api/products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductMockMvc.perform(get("/api/products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productService.save(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .name(UPDATED_NAME)
            .internalName(UPDATED_INTERNAL_NAME)
            .brandName(UPDATED_BRAND_NAME)
            .variant(UPDATED_VARIANT)
            .sku(UPDATED_SKU)
            .introductionDate(UPDATED_INTRODUCTION_DATE)
            .releaseDate(UPDATED_RELEASE_DATE)
            .quantityIncluded(UPDATED_QUANTITY_INCLUDED)
            .piecesIncluded(UPDATED_PIECES_INCLUDED)
            .weight(UPDATED_WEIGHT)
            .height(UPDATED_HEIGHT)
            .width(UPDATED_WIDTH)
            .depth(UPDATED_DEPTH)
            .smallImageUrl(UPDATED_SMALL_IMAGE_URL)
            .mediumImageUrl(UPDATED_MEDIUM_IMAGE_URL)
            .largeImageUrl(UPDATED_LARGE_IMAGE_URL)
            .detailImageUrl(UPDATED_DETAIL_IMAGE_URL)
            .originalImageUrl(UPDATED_ORIGINAL_IMAGE_URL)
            .quantity(UPDATED_QUANTITY)
            .cgst(UPDATED_CGST)
            .igst(UPDATED_IGST)
            .sgst(UPDATED_SGST)
            .price(UPDATED_PRICE)
            .cgstPercentage(UPDATED_CGST_PERCENTAGE)
            .igstPercentage(UPDATED_IGST_PERCENTAGE)
            .sgstPercentage(UPDATED_SGST_PERCENTAGE)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .description(UPDATED_DESCRIPTION)
            .longDescription(UPDATED_LONG_DESCRIPTION)
            .info(UPDATED_INFO)
            .isVirtual(UPDATED_IS_VIRTUAL)
            .isVariant(UPDATED_IS_VARIANT)
            .requireInventory(UPDATED_REQUIRE_INVENTORY)
            .returnable(UPDATED_RETURNABLE)
            .isPopular(UPDATED_IS_POPULAR)
            .changeControlNo(UPDATED_CHANGE_CONTROL_NO)
            .retestDuration(UPDATED_RETEST_DURATION)
            .expiryDuration(UPDATED_EXPIRY_DURATION)
            .validationType(UPDATED_VALIDATION_TYPE)
            .shelfLife(UPDATED_SHELF_LIFE);

        restProductMockMvc.perform(put("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProduct)))
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getInternalName()).isEqualTo(UPDATED_INTERNAL_NAME);
        assertThat(testProduct.getBrandName()).isEqualTo(UPDATED_BRAND_NAME);
        assertThat(testProduct.getVariant()).isEqualTo(UPDATED_VARIANT);
        assertThat(testProduct.getSku()).isEqualTo(UPDATED_SKU);
        assertThat(testProduct.getIntroductionDate()).isEqualTo(UPDATED_INTRODUCTION_DATE);
        assertThat(testProduct.getReleaseDate()).isEqualTo(UPDATED_RELEASE_DATE);
        assertThat(testProduct.getQuantityIncluded()).isEqualTo(UPDATED_QUANTITY_INCLUDED);
        assertThat(testProduct.getPiecesIncluded()).isEqualTo(UPDATED_PIECES_INCLUDED);
        assertThat(testProduct.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testProduct.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testProduct.getWidth()).isEqualTo(UPDATED_WIDTH);
        assertThat(testProduct.getDepth()).isEqualTo(UPDATED_DEPTH);
        assertThat(testProduct.getSmallImageUrl()).isEqualTo(UPDATED_SMALL_IMAGE_URL);
        assertThat(testProduct.getMediumImageUrl()).isEqualTo(UPDATED_MEDIUM_IMAGE_URL);
        assertThat(testProduct.getLargeImageUrl()).isEqualTo(UPDATED_LARGE_IMAGE_URL);
        assertThat(testProduct.getDetailImageUrl()).isEqualTo(UPDATED_DETAIL_IMAGE_URL);
        assertThat(testProduct.getOriginalImageUrl()).isEqualTo(UPDATED_ORIGINAL_IMAGE_URL);
        assertThat(testProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProduct.getCgst()).isEqualTo(UPDATED_CGST);
        assertThat(testProduct.getIgst()).isEqualTo(UPDATED_IGST);
        assertThat(testProduct.getSgst()).isEqualTo(UPDATED_SGST);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduct.getCgstPercentage()).isEqualTo(UPDATED_CGST_PERCENTAGE);
        assertThat(testProduct.getIgstPercentage()).isEqualTo(UPDATED_IGST_PERCENTAGE);
        assertThat(testProduct.getSgstPercentage()).isEqualTo(UPDATED_SGST_PERCENTAGE);
        assertThat(testProduct.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testProduct.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProduct.getLongDescription()).isEqualTo(UPDATED_LONG_DESCRIPTION);
        assertThat(testProduct.getInfo()).isEqualTo(UPDATED_INFO);
        assertThat(testProduct.isIsVirtual()).isEqualTo(UPDATED_IS_VIRTUAL);
        assertThat(testProduct.isIsVariant()).isEqualTo(UPDATED_IS_VARIANT);
        assertThat(testProduct.isRequireInventory()).isEqualTo(UPDATED_REQUIRE_INVENTORY);
        assertThat(testProduct.isReturnable()).isEqualTo(UPDATED_RETURNABLE);
        assertThat(testProduct.isIsPopular()).isEqualTo(UPDATED_IS_POPULAR);
        assertThat(testProduct.getChangeControlNo()).isEqualTo(UPDATED_CHANGE_CONTROL_NO);
        assertThat(testProduct.getRetestDuration()).isEqualTo(UPDATED_RETEST_DURATION);
        assertThat(testProduct.getExpiryDuration()).isEqualTo(UPDATED_EXPIRY_DURATION);
        assertThat(testProduct.getValidationType()).isEqualTo(UPDATED_VALIDATION_TYPE);
        assertThat(testProduct.getShelfLife()).isEqualTo(UPDATED_SHELF_LIFE);
    }

    @Test
    @Transactional
    public void updateNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc.perform(put("/api/products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(product)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productService.save(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
