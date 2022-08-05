package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.SupplierProduct;
import com.hr.domain.Product;
import com.hr.domain.Party;
import com.hr.domain.Uom;
import com.hr.repository.SupplierProductRepository;
import com.hr.service.SupplierProductService;
import com.hr.service.dto.SupplierProductCriteria;
import com.hr.service.SupplierProductQueryService;

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
import java.math.BigDecimal;
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
 * Integration tests for the {@link SupplierProductResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SupplierProductResourceIT {

    private static final ZonedDateTime DEFAULT_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FROM_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_THRU_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final BigDecimal DEFAULT_MIN_ORDER_QTY = new BigDecimal(1);
    private static final BigDecimal UPDATED_MIN_ORDER_QTY = new BigDecimal(2);
    private static final BigDecimal SMALLER_MIN_ORDER_QTY = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_LAST_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LAST_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_LAST_PRICE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SHIPPING_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_SHIPPING_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_SHIPPING_PRICE = new BigDecimal(1 - 1);

    private static final String DEFAULT_SUPPLIER_PRODUCT_ID = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_PRODUCT_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_LEAD_TIME_DAYS = 1;
    private static final Integer UPDATED_LEAD_TIME_DAYS = 2;
    private static final Integer SMALLER_LEAD_TIME_DAYS = 1 - 1;

    private static final BigDecimal DEFAULT_CGST = new BigDecimal(1);
    private static final BigDecimal UPDATED_CGST = new BigDecimal(2);
    private static final BigDecimal SMALLER_CGST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_IGST = new BigDecimal(1);
    private static final BigDecimal UPDATED_IGST = new BigDecimal(2);
    private static final BigDecimal SMALLER_IGST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_SGST = new BigDecimal(1);
    private static final BigDecimal UPDATED_SGST = new BigDecimal(2);
    private static final BigDecimal SMALLER_SGST = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_TOTAL_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_TOTAL_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_TOTAL_PRICE = new BigDecimal(1 - 1);

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final String DEFAULT_SUPPLIER_PRODUCT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SUPPLIER_PRODUCT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MANUFACTURER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER_NAME = "BBBBBBBBBB";

    @Autowired
    private SupplierProductRepository supplierProductRepository;

    @Autowired
    private SupplierProductService supplierProductService;

    @Autowired
    private SupplierProductQueryService supplierProductQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupplierProductMockMvc;

    private SupplierProduct supplierProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierProduct createEntity(EntityManager em) {
        SupplierProduct supplierProduct = new SupplierProduct()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .minOrderQty(DEFAULT_MIN_ORDER_QTY)
            .lastPrice(DEFAULT_LAST_PRICE)
            .shippingPrice(DEFAULT_SHIPPING_PRICE)
            .supplierProductId(DEFAULT_SUPPLIER_PRODUCT_ID)
            .leadTimeDays(DEFAULT_LEAD_TIME_DAYS)
            .cgst(DEFAULT_CGST)
            .igst(DEFAULT_IGST)
            .sgst(DEFAULT_SGST)
            .totalPrice(DEFAULT_TOTAL_PRICE)
            .comments(DEFAULT_COMMENTS)
            .supplierProductName(DEFAULT_SUPPLIER_PRODUCT_NAME)
            .manufacturerName(DEFAULT_MANUFACTURER_NAME);
        return supplierProduct;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SupplierProduct createUpdatedEntity(EntityManager em) {
        SupplierProduct supplierProduct = new SupplierProduct()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .minOrderQty(UPDATED_MIN_ORDER_QTY)
            .lastPrice(UPDATED_LAST_PRICE)
            .shippingPrice(UPDATED_SHIPPING_PRICE)
            .supplierProductId(UPDATED_SUPPLIER_PRODUCT_ID)
            .leadTimeDays(UPDATED_LEAD_TIME_DAYS)
            .cgst(UPDATED_CGST)
            .igst(UPDATED_IGST)
            .sgst(UPDATED_SGST)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .comments(UPDATED_COMMENTS)
            .supplierProductName(UPDATED_SUPPLIER_PRODUCT_NAME)
            .manufacturerName(UPDATED_MANUFACTURER_NAME);
        return supplierProduct;
    }

    @BeforeEach
    public void initTest() {
        supplierProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupplierProduct() throws Exception {
        int databaseSizeBeforeCreate = supplierProductRepository.findAll().size();
        // Create the SupplierProduct
        restSupplierProductMockMvc.perform(post("/api/supplier-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supplierProduct)))
            .andExpect(status().isCreated());

        // Validate the SupplierProduct in the database
        List<SupplierProduct> supplierProductList = supplierProductRepository.findAll();
        assertThat(supplierProductList).hasSize(databaseSizeBeforeCreate + 1);
        SupplierProduct testSupplierProduct = supplierProductList.get(supplierProductList.size() - 1);
        assertThat(testSupplierProduct.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testSupplierProduct.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testSupplierProduct.getMinOrderQty()).isEqualTo(DEFAULT_MIN_ORDER_QTY);
        assertThat(testSupplierProduct.getLastPrice()).isEqualTo(DEFAULT_LAST_PRICE);
        assertThat(testSupplierProduct.getShippingPrice()).isEqualTo(DEFAULT_SHIPPING_PRICE);
        assertThat(testSupplierProduct.getSupplierProductId()).isEqualTo(DEFAULT_SUPPLIER_PRODUCT_ID);
        assertThat(testSupplierProduct.getLeadTimeDays()).isEqualTo(DEFAULT_LEAD_TIME_DAYS);
        assertThat(testSupplierProduct.getCgst()).isEqualTo(DEFAULT_CGST);
        assertThat(testSupplierProduct.getIgst()).isEqualTo(DEFAULT_IGST);
        assertThat(testSupplierProduct.getSgst()).isEqualTo(DEFAULT_SGST);
        assertThat(testSupplierProduct.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
        assertThat(testSupplierProduct.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testSupplierProduct.getSupplierProductName()).isEqualTo(DEFAULT_SUPPLIER_PRODUCT_NAME);
        assertThat(testSupplierProduct.getManufacturerName()).isEqualTo(DEFAULT_MANUFACTURER_NAME);
    }

    @Test
    @Transactional
    public void createSupplierProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supplierProductRepository.findAll().size();

        // Create the SupplierProduct with an existing ID
        supplierProduct.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupplierProductMockMvc.perform(post("/api/supplier-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supplierProduct)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierProduct in the database
        List<SupplierProduct> supplierProductList = supplierProductRepository.findAll();
        assertThat(supplierProductList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSupplierProducts() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList
        restSupplierProductMockMvc.perform(get("/api/supplier-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))))
            .andExpect(jsonPath("$.[*].minOrderQty").value(hasItem(DEFAULT_MIN_ORDER_QTY.intValue())))
            .andExpect(jsonPath("$.[*].lastPrice").value(hasItem(DEFAULT_LAST_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].shippingPrice").value(hasItem(DEFAULT_SHIPPING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].supplierProductId").value(hasItem(DEFAULT_SUPPLIER_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].leadTimeDays").value(hasItem(DEFAULT_LEAD_TIME_DAYS)))
            .andExpect(jsonPath("$.[*].cgst").value(hasItem(DEFAULT_CGST.intValue())))
            .andExpect(jsonPath("$.[*].igst").value(hasItem(DEFAULT_IGST.intValue())))
            .andExpect(jsonPath("$.[*].sgst").value(hasItem(DEFAULT_SGST.intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].supplierProductName").value(hasItem(DEFAULT_SUPPLIER_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].manufacturerName").value(hasItem(DEFAULT_MANUFACTURER_NAME)));
    }
    
    @Test
    @Transactional
    public void getSupplierProduct() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get the supplierProduct
        restSupplierProductMockMvc.perform(get("/api/supplier-products/{id}", supplierProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supplierProduct.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(sameInstant(DEFAULT_FROM_DATE)))
            .andExpect(jsonPath("$.thruDate").value(sameInstant(DEFAULT_THRU_DATE)))
            .andExpect(jsonPath("$.minOrderQty").value(DEFAULT_MIN_ORDER_QTY.intValue()))
            .andExpect(jsonPath("$.lastPrice").value(DEFAULT_LAST_PRICE.intValue()))
            .andExpect(jsonPath("$.shippingPrice").value(DEFAULT_SHIPPING_PRICE.intValue()))
            .andExpect(jsonPath("$.supplierProductId").value(DEFAULT_SUPPLIER_PRODUCT_ID))
            .andExpect(jsonPath("$.leadTimeDays").value(DEFAULT_LEAD_TIME_DAYS))
            .andExpect(jsonPath("$.cgst").value(DEFAULT_CGST.intValue()))
            .andExpect(jsonPath("$.igst").value(DEFAULT_IGST.intValue()))
            .andExpect(jsonPath("$.sgst").value(DEFAULT_SGST.intValue()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.intValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.supplierProductName").value(DEFAULT_SUPPLIER_PRODUCT_NAME))
            .andExpect(jsonPath("$.manufacturerName").value(DEFAULT_MANUFACTURER_NAME));
    }


    @Test
    @Transactional
    public void getSupplierProductsByIdFiltering() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        Long id = supplierProduct.getId();

        defaultSupplierProductShouldBeFound("id.equals=" + id);
        defaultSupplierProductShouldNotBeFound("id.notEquals=" + id);

        defaultSupplierProductShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultSupplierProductShouldNotBeFound("id.greaterThan=" + id);

        defaultSupplierProductShouldBeFound("id.lessThanOrEqual=" + id);
        defaultSupplierProductShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllSupplierProductsByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where fromDate equals to DEFAULT_FROM_DATE
        defaultSupplierProductShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the supplierProductList where fromDate equals to UPDATED_FROM_DATE
        defaultSupplierProductShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where fromDate not equals to DEFAULT_FROM_DATE
        defaultSupplierProductShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the supplierProductList where fromDate not equals to UPDATED_FROM_DATE
        defaultSupplierProductShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultSupplierProductShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the supplierProductList where fromDate equals to UPDATED_FROM_DATE
        defaultSupplierProductShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where fromDate is not null
        defaultSupplierProductShouldBeFound("fromDate.specified=true");

        // Get all the supplierProductList where fromDate is null
        defaultSupplierProductShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByFromDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where fromDate is greater than or equal to DEFAULT_FROM_DATE
        defaultSupplierProductShouldBeFound("fromDate.greaterThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the supplierProductList where fromDate is greater than or equal to UPDATED_FROM_DATE
        defaultSupplierProductShouldNotBeFound("fromDate.greaterThanOrEqual=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByFromDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where fromDate is less than or equal to DEFAULT_FROM_DATE
        defaultSupplierProductShouldBeFound("fromDate.lessThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the supplierProductList where fromDate is less than or equal to SMALLER_FROM_DATE
        defaultSupplierProductShouldNotBeFound("fromDate.lessThanOrEqual=" + SMALLER_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByFromDateIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where fromDate is less than DEFAULT_FROM_DATE
        defaultSupplierProductShouldNotBeFound("fromDate.lessThan=" + DEFAULT_FROM_DATE);

        // Get all the supplierProductList where fromDate is less than UPDATED_FROM_DATE
        defaultSupplierProductShouldBeFound("fromDate.lessThan=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByFromDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where fromDate is greater than DEFAULT_FROM_DATE
        defaultSupplierProductShouldNotBeFound("fromDate.greaterThan=" + DEFAULT_FROM_DATE);

        // Get all the supplierProductList where fromDate is greater than SMALLER_FROM_DATE
        defaultSupplierProductShouldBeFound("fromDate.greaterThan=" + SMALLER_FROM_DATE);
    }


    @Test
    @Transactional
    public void getAllSupplierProductsByThruDateIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where thruDate equals to DEFAULT_THRU_DATE
        defaultSupplierProductShouldBeFound("thruDate.equals=" + DEFAULT_THRU_DATE);

        // Get all the supplierProductList where thruDate equals to UPDATED_THRU_DATE
        defaultSupplierProductShouldNotBeFound("thruDate.equals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByThruDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where thruDate not equals to DEFAULT_THRU_DATE
        defaultSupplierProductShouldNotBeFound("thruDate.notEquals=" + DEFAULT_THRU_DATE);

        // Get all the supplierProductList where thruDate not equals to UPDATED_THRU_DATE
        defaultSupplierProductShouldBeFound("thruDate.notEquals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByThruDateIsInShouldWork() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where thruDate in DEFAULT_THRU_DATE or UPDATED_THRU_DATE
        defaultSupplierProductShouldBeFound("thruDate.in=" + DEFAULT_THRU_DATE + "," + UPDATED_THRU_DATE);

        // Get all the supplierProductList where thruDate equals to UPDATED_THRU_DATE
        defaultSupplierProductShouldNotBeFound("thruDate.in=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByThruDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where thruDate is not null
        defaultSupplierProductShouldBeFound("thruDate.specified=true");

        // Get all the supplierProductList where thruDate is null
        defaultSupplierProductShouldNotBeFound("thruDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByThruDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where thruDate is greater than or equal to DEFAULT_THRU_DATE
        defaultSupplierProductShouldBeFound("thruDate.greaterThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the supplierProductList where thruDate is greater than or equal to UPDATED_THRU_DATE
        defaultSupplierProductShouldNotBeFound("thruDate.greaterThanOrEqual=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByThruDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where thruDate is less than or equal to DEFAULT_THRU_DATE
        defaultSupplierProductShouldBeFound("thruDate.lessThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the supplierProductList where thruDate is less than or equal to SMALLER_THRU_DATE
        defaultSupplierProductShouldNotBeFound("thruDate.lessThanOrEqual=" + SMALLER_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByThruDateIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where thruDate is less than DEFAULT_THRU_DATE
        defaultSupplierProductShouldNotBeFound("thruDate.lessThan=" + DEFAULT_THRU_DATE);

        // Get all the supplierProductList where thruDate is less than UPDATED_THRU_DATE
        defaultSupplierProductShouldBeFound("thruDate.lessThan=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByThruDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where thruDate is greater than DEFAULT_THRU_DATE
        defaultSupplierProductShouldNotBeFound("thruDate.greaterThan=" + DEFAULT_THRU_DATE);

        // Get all the supplierProductList where thruDate is greater than SMALLER_THRU_DATE
        defaultSupplierProductShouldBeFound("thruDate.greaterThan=" + SMALLER_THRU_DATE);
    }


    @Test
    @Transactional
    public void getAllSupplierProductsByMinOrderQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where minOrderQty equals to DEFAULT_MIN_ORDER_QTY
        defaultSupplierProductShouldBeFound("minOrderQty.equals=" + DEFAULT_MIN_ORDER_QTY);

        // Get all the supplierProductList where minOrderQty equals to UPDATED_MIN_ORDER_QTY
        defaultSupplierProductShouldNotBeFound("minOrderQty.equals=" + UPDATED_MIN_ORDER_QTY);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByMinOrderQtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where minOrderQty not equals to DEFAULT_MIN_ORDER_QTY
        defaultSupplierProductShouldNotBeFound("minOrderQty.notEquals=" + DEFAULT_MIN_ORDER_QTY);

        // Get all the supplierProductList where minOrderQty not equals to UPDATED_MIN_ORDER_QTY
        defaultSupplierProductShouldBeFound("minOrderQty.notEquals=" + UPDATED_MIN_ORDER_QTY);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByMinOrderQtyIsInShouldWork() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where minOrderQty in DEFAULT_MIN_ORDER_QTY or UPDATED_MIN_ORDER_QTY
        defaultSupplierProductShouldBeFound("minOrderQty.in=" + DEFAULT_MIN_ORDER_QTY + "," + UPDATED_MIN_ORDER_QTY);

        // Get all the supplierProductList where minOrderQty equals to UPDATED_MIN_ORDER_QTY
        defaultSupplierProductShouldNotBeFound("minOrderQty.in=" + UPDATED_MIN_ORDER_QTY);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByMinOrderQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where minOrderQty is not null
        defaultSupplierProductShouldBeFound("minOrderQty.specified=true");

        // Get all the supplierProductList where minOrderQty is null
        defaultSupplierProductShouldNotBeFound("minOrderQty.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByMinOrderQtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where minOrderQty is greater than or equal to DEFAULT_MIN_ORDER_QTY
        defaultSupplierProductShouldBeFound("minOrderQty.greaterThanOrEqual=" + DEFAULT_MIN_ORDER_QTY);

        // Get all the supplierProductList where minOrderQty is greater than or equal to UPDATED_MIN_ORDER_QTY
        defaultSupplierProductShouldNotBeFound("minOrderQty.greaterThanOrEqual=" + UPDATED_MIN_ORDER_QTY);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByMinOrderQtyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where minOrderQty is less than or equal to DEFAULT_MIN_ORDER_QTY
        defaultSupplierProductShouldBeFound("minOrderQty.lessThanOrEqual=" + DEFAULT_MIN_ORDER_QTY);

        // Get all the supplierProductList where minOrderQty is less than or equal to SMALLER_MIN_ORDER_QTY
        defaultSupplierProductShouldNotBeFound("minOrderQty.lessThanOrEqual=" + SMALLER_MIN_ORDER_QTY);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByMinOrderQtyIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where minOrderQty is less than DEFAULT_MIN_ORDER_QTY
        defaultSupplierProductShouldNotBeFound("minOrderQty.lessThan=" + DEFAULT_MIN_ORDER_QTY);

        // Get all the supplierProductList where minOrderQty is less than UPDATED_MIN_ORDER_QTY
        defaultSupplierProductShouldBeFound("minOrderQty.lessThan=" + UPDATED_MIN_ORDER_QTY);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByMinOrderQtyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where minOrderQty is greater than DEFAULT_MIN_ORDER_QTY
        defaultSupplierProductShouldNotBeFound("minOrderQty.greaterThan=" + DEFAULT_MIN_ORDER_QTY);

        // Get all the supplierProductList where minOrderQty is greater than SMALLER_MIN_ORDER_QTY
        defaultSupplierProductShouldBeFound("minOrderQty.greaterThan=" + SMALLER_MIN_ORDER_QTY);
    }


    @Test
    @Transactional
    public void getAllSupplierProductsByLastPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where lastPrice equals to DEFAULT_LAST_PRICE
        defaultSupplierProductShouldBeFound("lastPrice.equals=" + DEFAULT_LAST_PRICE);

        // Get all the supplierProductList where lastPrice equals to UPDATED_LAST_PRICE
        defaultSupplierProductShouldNotBeFound("lastPrice.equals=" + UPDATED_LAST_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByLastPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where lastPrice not equals to DEFAULT_LAST_PRICE
        defaultSupplierProductShouldNotBeFound("lastPrice.notEquals=" + DEFAULT_LAST_PRICE);

        // Get all the supplierProductList where lastPrice not equals to UPDATED_LAST_PRICE
        defaultSupplierProductShouldBeFound("lastPrice.notEquals=" + UPDATED_LAST_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByLastPriceIsInShouldWork() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where lastPrice in DEFAULT_LAST_PRICE or UPDATED_LAST_PRICE
        defaultSupplierProductShouldBeFound("lastPrice.in=" + DEFAULT_LAST_PRICE + "," + UPDATED_LAST_PRICE);

        // Get all the supplierProductList where lastPrice equals to UPDATED_LAST_PRICE
        defaultSupplierProductShouldNotBeFound("lastPrice.in=" + UPDATED_LAST_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByLastPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where lastPrice is not null
        defaultSupplierProductShouldBeFound("lastPrice.specified=true");

        // Get all the supplierProductList where lastPrice is null
        defaultSupplierProductShouldNotBeFound("lastPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByLastPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where lastPrice is greater than or equal to DEFAULT_LAST_PRICE
        defaultSupplierProductShouldBeFound("lastPrice.greaterThanOrEqual=" + DEFAULT_LAST_PRICE);

        // Get all the supplierProductList where lastPrice is greater than or equal to UPDATED_LAST_PRICE
        defaultSupplierProductShouldNotBeFound("lastPrice.greaterThanOrEqual=" + UPDATED_LAST_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByLastPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where lastPrice is less than or equal to DEFAULT_LAST_PRICE
        defaultSupplierProductShouldBeFound("lastPrice.lessThanOrEqual=" + DEFAULT_LAST_PRICE);

        // Get all the supplierProductList where lastPrice is less than or equal to SMALLER_LAST_PRICE
        defaultSupplierProductShouldNotBeFound("lastPrice.lessThanOrEqual=" + SMALLER_LAST_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByLastPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where lastPrice is less than DEFAULT_LAST_PRICE
        defaultSupplierProductShouldNotBeFound("lastPrice.lessThan=" + DEFAULT_LAST_PRICE);

        // Get all the supplierProductList where lastPrice is less than UPDATED_LAST_PRICE
        defaultSupplierProductShouldBeFound("lastPrice.lessThan=" + UPDATED_LAST_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByLastPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where lastPrice is greater than DEFAULT_LAST_PRICE
        defaultSupplierProductShouldNotBeFound("lastPrice.greaterThan=" + DEFAULT_LAST_PRICE);

        // Get all the supplierProductList where lastPrice is greater than SMALLER_LAST_PRICE
        defaultSupplierProductShouldBeFound("lastPrice.greaterThan=" + SMALLER_LAST_PRICE);
    }


    @Test
    @Transactional
    public void getAllSupplierProductsByShippingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where shippingPrice equals to DEFAULT_SHIPPING_PRICE
        defaultSupplierProductShouldBeFound("shippingPrice.equals=" + DEFAULT_SHIPPING_PRICE);

        // Get all the supplierProductList where shippingPrice equals to UPDATED_SHIPPING_PRICE
        defaultSupplierProductShouldNotBeFound("shippingPrice.equals=" + UPDATED_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByShippingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where shippingPrice not equals to DEFAULT_SHIPPING_PRICE
        defaultSupplierProductShouldNotBeFound("shippingPrice.notEquals=" + DEFAULT_SHIPPING_PRICE);

        // Get all the supplierProductList where shippingPrice not equals to UPDATED_SHIPPING_PRICE
        defaultSupplierProductShouldBeFound("shippingPrice.notEquals=" + UPDATED_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByShippingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where shippingPrice in DEFAULT_SHIPPING_PRICE or UPDATED_SHIPPING_PRICE
        defaultSupplierProductShouldBeFound("shippingPrice.in=" + DEFAULT_SHIPPING_PRICE + "," + UPDATED_SHIPPING_PRICE);

        // Get all the supplierProductList where shippingPrice equals to UPDATED_SHIPPING_PRICE
        defaultSupplierProductShouldNotBeFound("shippingPrice.in=" + UPDATED_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByShippingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where shippingPrice is not null
        defaultSupplierProductShouldBeFound("shippingPrice.specified=true");

        // Get all the supplierProductList where shippingPrice is null
        defaultSupplierProductShouldNotBeFound("shippingPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByShippingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where shippingPrice is greater than or equal to DEFAULT_SHIPPING_PRICE
        defaultSupplierProductShouldBeFound("shippingPrice.greaterThanOrEqual=" + DEFAULT_SHIPPING_PRICE);

        // Get all the supplierProductList where shippingPrice is greater than or equal to UPDATED_SHIPPING_PRICE
        defaultSupplierProductShouldNotBeFound("shippingPrice.greaterThanOrEqual=" + UPDATED_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByShippingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where shippingPrice is less than or equal to DEFAULT_SHIPPING_PRICE
        defaultSupplierProductShouldBeFound("shippingPrice.lessThanOrEqual=" + DEFAULT_SHIPPING_PRICE);

        // Get all the supplierProductList where shippingPrice is less than or equal to SMALLER_SHIPPING_PRICE
        defaultSupplierProductShouldNotBeFound("shippingPrice.lessThanOrEqual=" + SMALLER_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByShippingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where shippingPrice is less than DEFAULT_SHIPPING_PRICE
        defaultSupplierProductShouldNotBeFound("shippingPrice.lessThan=" + DEFAULT_SHIPPING_PRICE);

        // Get all the supplierProductList where shippingPrice is less than UPDATED_SHIPPING_PRICE
        defaultSupplierProductShouldBeFound("shippingPrice.lessThan=" + UPDATED_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByShippingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where shippingPrice is greater than DEFAULT_SHIPPING_PRICE
        defaultSupplierProductShouldNotBeFound("shippingPrice.greaterThan=" + DEFAULT_SHIPPING_PRICE);

        // Get all the supplierProductList where shippingPrice is greater than SMALLER_SHIPPING_PRICE
        defaultSupplierProductShouldBeFound("shippingPrice.greaterThan=" + SMALLER_SHIPPING_PRICE);
    }


    @Test
    @Transactional
    public void getAllSupplierProductsBySupplierProductIdIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where supplierProductId equals to DEFAULT_SUPPLIER_PRODUCT_ID
        defaultSupplierProductShouldBeFound("supplierProductId.equals=" + DEFAULT_SUPPLIER_PRODUCT_ID);

        // Get all the supplierProductList where supplierProductId equals to UPDATED_SUPPLIER_PRODUCT_ID
        defaultSupplierProductShouldNotBeFound("supplierProductId.equals=" + UPDATED_SUPPLIER_PRODUCT_ID);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsBySupplierProductIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where supplierProductId not equals to DEFAULT_SUPPLIER_PRODUCT_ID
        defaultSupplierProductShouldNotBeFound("supplierProductId.notEquals=" + DEFAULT_SUPPLIER_PRODUCT_ID);

        // Get all the supplierProductList where supplierProductId not equals to UPDATED_SUPPLIER_PRODUCT_ID
        defaultSupplierProductShouldBeFound("supplierProductId.notEquals=" + UPDATED_SUPPLIER_PRODUCT_ID);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsBySupplierProductIdIsInShouldWork() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where supplierProductId in DEFAULT_SUPPLIER_PRODUCT_ID or UPDATED_SUPPLIER_PRODUCT_ID
        defaultSupplierProductShouldBeFound("supplierProductId.in=" + DEFAULT_SUPPLIER_PRODUCT_ID + "," + UPDATED_SUPPLIER_PRODUCT_ID);

        // Get all the supplierProductList where supplierProductId equals to UPDATED_SUPPLIER_PRODUCT_ID
        defaultSupplierProductShouldNotBeFound("supplierProductId.in=" + UPDATED_SUPPLIER_PRODUCT_ID);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsBySupplierProductIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where supplierProductId is not null
        defaultSupplierProductShouldBeFound("supplierProductId.specified=true");

        // Get all the supplierProductList where supplierProductId is null
        defaultSupplierProductShouldNotBeFound("supplierProductId.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupplierProductsBySupplierProductIdContainsSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where supplierProductId contains DEFAULT_SUPPLIER_PRODUCT_ID
        defaultSupplierProductShouldBeFound("supplierProductId.contains=" + DEFAULT_SUPPLIER_PRODUCT_ID);

        // Get all the supplierProductList where supplierProductId contains UPDATED_SUPPLIER_PRODUCT_ID
        defaultSupplierProductShouldNotBeFound("supplierProductId.contains=" + UPDATED_SUPPLIER_PRODUCT_ID);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsBySupplierProductIdNotContainsSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where supplierProductId does not contain DEFAULT_SUPPLIER_PRODUCT_ID
        defaultSupplierProductShouldNotBeFound("supplierProductId.doesNotContain=" + DEFAULT_SUPPLIER_PRODUCT_ID);

        // Get all the supplierProductList where supplierProductId does not contain UPDATED_SUPPLIER_PRODUCT_ID
        defaultSupplierProductShouldBeFound("supplierProductId.doesNotContain=" + UPDATED_SUPPLIER_PRODUCT_ID);
    }


    @Test
    @Transactional
    public void getAllSupplierProductsByLeadTimeDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where leadTimeDays equals to DEFAULT_LEAD_TIME_DAYS
        defaultSupplierProductShouldBeFound("leadTimeDays.equals=" + DEFAULT_LEAD_TIME_DAYS);

        // Get all the supplierProductList where leadTimeDays equals to UPDATED_LEAD_TIME_DAYS
        defaultSupplierProductShouldNotBeFound("leadTimeDays.equals=" + UPDATED_LEAD_TIME_DAYS);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByLeadTimeDaysIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where leadTimeDays not equals to DEFAULT_LEAD_TIME_DAYS
        defaultSupplierProductShouldNotBeFound("leadTimeDays.notEquals=" + DEFAULT_LEAD_TIME_DAYS);

        // Get all the supplierProductList where leadTimeDays not equals to UPDATED_LEAD_TIME_DAYS
        defaultSupplierProductShouldBeFound("leadTimeDays.notEquals=" + UPDATED_LEAD_TIME_DAYS);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByLeadTimeDaysIsInShouldWork() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where leadTimeDays in DEFAULT_LEAD_TIME_DAYS or UPDATED_LEAD_TIME_DAYS
        defaultSupplierProductShouldBeFound("leadTimeDays.in=" + DEFAULT_LEAD_TIME_DAYS + "," + UPDATED_LEAD_TIME_DAYS);

        // Get all the supplierProductList where leadTimeDays equals to UPDATED_LEAD_TIME_DAYS
        defaultSupplierProductShouldNotBeFound("leadTimeDays.in=" + UPDATED_LEAD_TIME_DAYS);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByLeadTimeDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where leadTimeDays is not null
        defaultSupplierProductShouldBeFound("leadTimeDays.specified=true");

        // Get all the supplierProductList where leadTimeDays is null
        defaultSupplierProductShouldNotBeFound("leadTimeDays.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByLeadTimeDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where leadTimeDays is greater than or equal to DEFAULT_LEAD_TIME_DAYS
        defaultSupplierProductShouldBeFound("leadTimeDays.greaterThanOrEqual=" + DEFAULT_LEAD_TIME_DAYS);

        // Get all the supplierProductList where leadTimeDays is greater than or equal to UPDATED_LEAD_TIME_DAYS
        defaultSupplierProductShouldNotBeFound("leadTimeDays.greaterThanOrEqual=" + UPDATED_LEAD_TIME_DAYS);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByLeadTimeDaysIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where leadTimeDays is less than or equal to DEFAULT_LEAD_TIME_DAYS
        defaultSupplierProductShouldBeFound("leadTimeDays.lessThanOrEqual=" + DEFAULT_LEAD_TIME_DAYS);

        // Get all the supplierProductList where leadTimeDays is less than or equal to SMALLER_LEAD_TIME_DAYS
        defaultSupplierProductShouldNotBeFound("leadTimeDays.lessThanOrEqual=" + SMALLER_LEAD_TIME_DAYS);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByLeadTimeDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where leadTimeDays is less than DEFAULT_LEAD_TIME_DAYS
        defaultSupplierProductShouldNotBeFound("leadTimeDays.lessThan=" + DEFAULT_LEAD_TIME_DAYS);

        // Get all the supplierProductList where leadTimeDays is less than UPDATED_LEAD_TIME_DAYS
        defaultSupplierProductShouldBeFound("leadTimeDays.lessThan=" + UPDATED_LEAD_TIME_DAYS);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByLeadTimeDaysIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where leadTimeDays is greater than DEFAULT_LEAD_TIME_DAYS
        defaultSupplierProductShouldNotBeFound("leadTimeDays.greaterThan=" + DEFAULT_LEAD_TIME_DAYS);

        // Get all the supplierProductList where leadTimeDays is greater than SMALLER_LEAD_TIME_DAYS
        defaultSupplierProductShouldBeFound("leadTimeDays.greaterThan=" + SMALLER_LEAD_TIME_DAYS);
    }


    @Test
    @Transactional
    public void getAllSupplierProductsByCgstIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where cgst equals to DEFAULT_CGST
        defaultSupplierProductShouldBeFound("cgst.equals=" + DEFAULT_CGST);

        // Get all the supplierProductList where cgst equals to UPDATED_CGST
        defaultSupplierProductShouldNotBeFound("cgst.equals=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByCgstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where cgst not equals to DEFAULT_CGST
        defaultSupplierProductShouldNotBeFound("cgst.notEquals=" + DEFAULT_CGST);

        // Get all the supplierProductList where cgst not equals to UPDATED_CGST
        defaultSupplierProductShouldBeFound("cgst.notEquals=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByCgstIsInShouldWork() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where cgst in DEFAULT_CGST or UPDATED_CGST
        defaultSupplierProductShouldBeFound("cgst.in=" + DEFAULT_CGST + "," + UPDATED_CGST);

        // Get all the supplierProductList where cgst equals to UPDATED_CGST
        defaultSupplierProductShouldNotBeFound("cgst.in=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByCgstIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where cgst is not null
        defaultSupplierProductShouldBeFound("cgst.specified=true");

        // Get all the supplierProductList where cgst is null
        defaultSupplierProductShouldNotBeFound("cgst.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByCgstIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where cgst is greater than or equal to DEFAULT_CGST
        defaultSupplierProductShouldBeFound("cgst.greaterThanOrEqual=" + DEFAULT_CGST);

        // Get all the supplierProductList where cgst is greater than or equal to UPDATED_CGST
        defaultSupplierProductShouldNotBeFound("cgst.greaterThanOrEqual=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByCgstIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where cgst is less than or equal to DEFAULT_CGST
        defaultSupplierProductShouldBeFound("cgst.lessThanOrEqual=" + DEFAULT_CGST);

        // Get all the supplierProductList where cgst is less than or equal to SMALLER_CGST
        defaultSupplierProductShouldNotBeFound("cgst.lessThanOrEqual=" + SMALLER_CGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByCgstIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where cgst is less than DEFAULT_CGST
        defaultSupplierProductShouldNotBeFound("cgst.lessThan=" + DEFAULT_CGST);

        // Get all the supplierProductList where cgst is less than UPDATED_CGST
        defaultSupplierProductShouldBeFound("cgst.lessThan=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByCgstIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where cgst is greater than DEFAULT_CGST
        defaultSupplierProductShouldNotBeFound("cgst.greaterThan=" + DEFAULT_CGST);

        // Get all the supplierProductList where cgst is greater than SMALLER_CGST
        defaultSupplierProductShouldBeFound("cgst.greaterThan=" + SMALLER_CGST);
    }


    @Test
    @Transactional
    public void getAllSupplierProductsByIgstIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where igst equals to DEFAULT_IGST
        defaultSupplierProductShouldBeFound("igst.equals=" + DEFAULT_IGST);

        // Get all the supplierProductList where igst equals to UPDATED_IGST
        defaultSupplierProductShouldNotBeFound("igst.equals=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByIgstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where igst not equals to DEFAULT_IGST
        defaultSupplierProductShouldNotBeFound("igst.notEquals=" + DEFAULT_IGST);

        // Get all the supplierProductList where igst not equals to UPDATED_IGST
        defaultSupplierProductShouldBeFound("igst.notEquals=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByIgstIsInShouldWork() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where igst in DEFAULT_IGST or UPDATED_IGST
        defaultSupplierProductShouldBeFound("igst.in=" + DEFAULT_IGST + "," + UPDATED_IGST);

        // Get all the supplierProductList where igst equals to UPDATED_IGST
        defaultSupplierProductShouldNotBeFound("igst.in=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByIgstIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where igst is not null
        defaultSupplierProductShouldBeFound("igst.specified=true");

        // Get all the supplierProductList where igst is null
        defaultSupplierProductShouldNotBeFound("igst.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByIgstIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where igst is greater than or equal to DEFAULT_IGST
        defaultSupplierProductShouldBeFound("igst.greaterThanOrEqual=" + DEFAULT_IGST);

        // Get all the supplierProductList where igst is greater than or equal to UPDATED_IGST
        defaultSupplierProductShouldNotBeFound("igst.greaterThanOrEqual=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByIgstIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where igst is less than or equal to DEFAULT_IGST
        defaultSupplierProductShouldBeFound("igst.lessThanOrEqual=" + DEFAULT_IGST);

        // Get all the supplierProductList where igst is less than or equal to SMALLER_IGST
        defaultSupplierProductShouldNotBeFound("igst.lessThanOrEqual=" + SMALLER_IGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByIgstIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where igst is less than DEFAULT_IGST
        defaultSupplierProductShouldNotBeFound("igst.lessThan=" + DEFAULT_IGST);

        // Get all the supplierProductList where igst is less than UPDATED_IGST
        defaultSupplierProductShouldBeFound("igst.lessThan=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByIgstIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where igst is greater than DEFAULT_IGST
        defaultSupplierProductShouldNotBeFound("igst.greaterThan=" + DEFAULT_IGST);

        // Get all the supplierProductList where igst is greater than SMALLER_IGST
        defaultSupplierProductShouldBeFound("igst.greaterThan=" + SMALLER_IGST);
    }


    @Test
    @Transactional
    public void getAllSupplierProductsBySgstIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where sgst equals to DEFAULT_SGST
        defaultSupplierProductShouldBeFound("sgst.equals=" + DEFAULT_SGST);

        // Get all the supplierProductList where sgst equals to UPDATED_SGST
        defaultSupplierProductShouldNotBeFound("sgst.equals=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsBySgstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where sgst not equals to DEFAULT_SGST
        defaultSupplierProductShouldNotBeFound("sgst.notEquals=" + DEFAULT_SGST);

        // Get all the supplierProductList where sgst not equals to UPDATED_SGST
        defaultSupplierProductShouldBeFound("sgst.notEquals=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsBySgstIsInShouldWork() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where sgst in DEFAULT_SGST or UPDATED_SGST
        defaultSupplierProductShouldBeFound("sgst.in=" + DEFAULT_SGST + "," + UPDATED_SGST);

        // Get all the supplierProductList where sgst equals to UPDATED_SGST
        defaultSupplierProductShouldNotBeFound("sgst.in=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsBySgstIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where sgst is not null
        defaultSupplierProductShouldBeFound("sgst.specified=true");

        // Get all the supplierProductList where sgst is null
        defaultSupplierProductShouldNotBeFound("sgst.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierProductsBySgstIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where sgst is greater than or equal to DEFAULT_SGST
        defaultSupplierProductShouldBeFound("sgst.greaterThanOrEqual=" + DEFAULT_SGST);

        // Get all the supplierProductList where sgst is greater than or equal to UPDATED_SGST
        defaultSupplierProductShouldNotBeFound("sgst.greaterThanOrEqual=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsBySgstIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where sgst is less than or equal to DEFAULT_SGST
        defaultSupplierProductShouldBeFound("sgst.lessThanOrEqual=" + DEFAULT_SGST);

        // Get all the supplierProductList where sgst is less than or equal to SMALLER_SGST
        defaultSupplierProductShouldNotBeFound("sgst.lessThanOrEqual=" + SMALLER_SGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsBySgstIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where sgst is less than DEFAULT_SGST
        defaultSupplierProductShouldNotBeFound("sgst.lessThan=" + DEFAULT_SGST);

        // Get all the supplierProductList where sgst is less than UPDATED_SGST
        defaultSupplierProductShouldBeFound("sgst.lessThan=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsBySgstIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where sgst is greater than DEFAULT_SGST
        defaultSupplierProductShouldNotBeFound("sgst.greaterThan=" + DEFAULT_SGST);

        // Get all the supplierProductList where sgst is greater than SMALLER_SGST
        defaultSupplierProductShouldBeFound("sgst.greaterThan=" + SMALLER_SGST);
    }


    @Test
    @Transactional
    public void getAllSupplierProductsByTotalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where totalPrice equals to DEFAULT_TOTAL_PRICE
        defaultSupplierProductShouldBeFound("totalPrice.equals=" + DEFAULT_TOTAL_PRICE);

        // Get all the supplierProductList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultSupplierProductShouldNotBeFound("totalPrice.equals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByTotalPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where totalPrice not equals to DEFAULT_TOTAL_PRICE
        defaultSupplierProductShouldNotBeFound("totalPrice.notEquals=" + DEFAULT_TOTAL_PRICE);

        // Get all the supplierProductList where totalPrice not equals to UPDATED_TOTAL_PRICE
        defaultSupplierProductShouldBeFound("totalPrice.notEquals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByTotalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where totalPrice in DEFAULT_TOTAL_PRICE or UPDATED_TOTAL_PRICE
        defaultSupplierProductShouldBeFound("totalPrice.in=" + DEFAULT_TOTAL_PRICE + "," + UPDATED_TOTAL_PRICE);

        // Get all the supplierProductList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultSupplierProductShouldNotBeFound("totalPrice.in=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByTotalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where totalPrice is not null
        defaultSupplierProductShouldBeFound("totalPrice.specified=true");

        // Get all the supplierProductList where totalPrice is null
        defaultSupplierProductShouldNotBeFound("totalPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByTotalPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where totalPrice is greater than or equal to DEFAULT_TOTAL_PRICE
        defaultSupplierProductShouldBeFound("totalPrice.greaterThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the supplierProductList where totalPrice is greater than or equal to UPDATED_TOTAL_PRICE
        defaultSupplierProductShouldNotBeFound("totalPrice.greaterThanOrEqual=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByTotalPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where totalPrice is less than or equal to DEFAULT_TOTAL_PRICE
        defaultSupplierProductShouldBeFound("totalPrice.lessThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the supplierProductList where totalPrice is less than or equal to SMALLER_TOTAL_PRICE
        defaultSupplierProductShouldNotBeFound("totalPrice.lessThanOrEqual=" + SMALLER_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByTotalPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where totalPrice is less than DEFAULT_TOTAL_PRICE
        defaultSupplierProductShouldNotBeFound("totalPrice.lessThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the supplierProductList where totalPrice is less than UPDATED_TOTAL_PRICE
        defaultSupplierProductShouldBeFound("totalPrice.lessThan=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByTotalPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where totalPrice is greater than DEFAULT_TOTAL_PRICE
        defaultSupplierProductShouldNotBeFound("totalPrice.greaterThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the supplierProductList where totalPrice is greater than SMALLER_TOTAL_PRICE
        defaultSupplierProductShouldBeFound("totalPrice.greaterThan=" + SMALLER_TOTAL_PRICE);
    }


    @Test
    @Transactional
    public void getAllSupplierProductsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where comments equals to DEFAULT_COMMENTS
        defaultSupplierProductShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the supplierProductList where comments equals to UPDATED_COMMENTS
        defaultSupplierProductShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where comments not equals to DEFAULT_COMMENTS
        defaultSupplierProductShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the supplierProductList where comments not equals to UPDATED_COMMENTS
        defaultSupplierProductShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultSupplierProductShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the supplierProductList where comments equals to UPDATED_COMMENTS
        defaultSupplierProductShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where comments is not null
        defaultSupplierProductShouldBeFound("comments.specified=true");

        // Get all the supplierProductList where comments is null
        defaultSupplierProductShouldNotBeFound("comments.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupplierProductsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where comments contains DEFAULT_COMMENTS
        defaultSupplierProductShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the supplierProductList where comments contains UPDATED_COMMENTS
        defaultSupplierProductShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where comments does not contain DEFAULT_COMMENTS
        defaultSupplierProductShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the supplierProductList where comments does not contain UPDATED_COMMENTS
        defaultSupplierProductShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllSupplierProductsBySupplierProductNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where supplierProductName equals to DEFAULT_SUPPLIER_PRODUCT_NAME
        defaultSupplierProductShouldBeFound("supplierProductName.equals=" + DEFAULT_SUPPLIER_PRODUCT_NAME);

        // Get all the supplierProductList where supplierProductName equals to UPDATED_SUPPLIER_PRODUCT_NAME
        defaultSupplierProductShouldNotBeFound("supplierProductName.equals=" + UPDATED_SUPPLIER_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsBySupplierProductNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where supplierProductName not equals to DEFAULT_SUPPLIER_PRODUCT_NAME
        defaultSupplierProductShouldNotBeFound("supplierProductName.notEquals=" + DEFAULT_SUPPLIER_PRODUCT_NAME);

        // Get all the supplierProductList where supplierProductName not equals to UPDATED_SUPPLIER_PRODUCT_NAME
        defaultSupplierProductShouldBeFound("supplierProductName.notEquals=" + UPDATED_SUPPLIER_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsBySupplierProductNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where supplierProductName in DEFAULT_SUPPLIER_PRODUCT_NAME or UPDATED_SUPPLIER_PRODUCT_NAME
        defaultSupplierProductShouldBeFound("supplierProductName.in=" + DEFAULT_SUPPLIER_PRODUCT_NAME + "," + UPDATED_SUPPLIER_PRODUCT_NAME);

        // Get all the supplierProductList where supplierProductName equals to UPDATED_SUPPLIER_PRODUCT_NAME
        defaultSupplierProductShouldNotBeFound("supplierProductName.in=" + UPDATED_SUPPLIER_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsBySupplierProductNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where supplierProductName is not null
        defaultSupplierProductShouldBeFound("supplierProductName.specified=true");

        // Get all the supplierProductList where supplierProductName is null
        defaultSupplierProductShouldNotBeFound("supplierProductName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupplierProductsBySupplierProductNameContainsSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where supplierProductName contains DEFAULT_SUPPLIER_PRODUCT_NAME
        defaultSupplierProductShouldBeFound("supplierProductName.contains=" + DEFAULT_SUPPLIER_PRODUCT_NAME);

        // Get all the supplierProductList where supplierProductName contains UPDATED_SUPPLIER_PRODUCT_NAME
        defaultSupplierProductShouldNotBeFound("supplierProductName.contains=" + UPDATED_SUPPLIER_PRODUCT_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsBySupplierProductNameNotContainsSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where supplierProductName does not contain DEFAULT_SUPPLIER_PRODUCT_NAME
        defaultSupplierProductShouldNotBeFound("supplierProductName.doesNotContain=" + DEFAULT_SUPPLIER_PRODUCT_NAME);

        // Get all the supplierProductList where supplierProductName does not contain UPDATED_SUPPLIER_PRODUCT_NAME
        defaultSupplierProductShouldBeFound("supplierProductName.doesNotContain=" + UPDATED_SUPPLIER_PRODUCT_NAME);
    }


    @Test
    @Transactional
    public void getAllSupplierProductsByManufacturerNameIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where manufacturerName equals to DEFAULT_MANUFACTURER_NAME
        defaultSupplierProductShouldBeFound("manufacturerName.equals=" + DEFAULT_MANUFACTURER_NAME);

        // Get all the supplierProductList where manufacturerName equals to UPDATED_MANUFACTURER_NAME
        defaultSupplierProductShouldNotBeFound("manufacturerName.equals=" + UPDATED_MANUFACTURER_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByManufacturerNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where manufacturerName not equals to DEFAULT_MANUFACTURER_NAME
        defaultSupplierProductShouldNotBeFound("manufacturerName.notEquals=" + DEFAULT_MANUFACTURER_NAME);

        // Get all the supplierProductList where manufacturerName not equals to UPDATED_MANUFACTURER_NAME
        defaultSupplierProductShouldBeFound("manufacturerName.notEquals=" + UPDATED_MANUFACTURER_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByManufacturerNameIsInShouldWork() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where manufacturerName in DEFAULT_MANUFACTURER_NAME or UPDATED_MANUFACTURER_NAME
        defaultSupplierProductShouldBeFound("manufacturerName.in=" + DEFAULT_MANUFACTURER_NAME + "," + UPDATED_MANUFACTURER_NAME);

        // Get all the supplierProductList where manufacturerName equals to UPDATED_MANUFACTURER_NAME
        defaultSupplierProductShouldNotBeFound("manufacturerName.in=" + UPDATED_MANUFACTURER_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByManufacturerNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where manufacturerName is not null
        defaultSupplierProductShouldBeFound("manufacturerName.specified=true");

        // Get all the supplierProductList where manufacturerName is null
        defaultSupplierProductShouldNotBeFound("manufacturerName.specified=false");
    }
                @Test
    @Transactional
    public void getAllSupplierProductsByManufacturerNameContainsSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where manufacturerName contains DEFAULT_MANUFACTURER_NAME
        defaultSupplierProductShouldBeFound("manufacturerName.contains=" + DEFAULT_MANUFACTURER_NAME);

        // Get all the supplierProductList where manufacturerName contains UPDATED_MANUFACTURER_NAME
        defaultSupplierProductShouldNotBeFound("manufacturerName.contains=" + UPDATED_MANUFACTURER_NAME);
    }

    @Test
    @Transactional
    public void getAllSupplierProductsByManufacturerNameNotContainsSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);

        // Get all the supplierProductList where manufacturerName does not contain DEFAULT_MANUFACTURER_NAME
        defaultSupplierProductShouldNotBeFound("manufacturerName.doesNotContain=" + DEFAULT_MANUFACTURER_NAME);

        // Get all the supplierProductList where manufacturerName does not contain UPDATED_MANUFACTURER_NAME
        defaultSupplierProductShouldBeFound("manufacturerName.doesNotContain=" + UPDATED_MANUFACTURER_NAME);
    }


    @Test
    @Transactional
    public void getAllSupplierProductsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        supplierProduct.setProduct(product);
        supplierProductRepository.saveAndFlush(supplierProduct);
        Long productId = product.getId();

        // Get all the supplierProductList where product equals to productId
        defaultSupplierProductShouldBeFound("productId.equals=" + productId);

        // Get all the supplierProductList where product equals to productId + 1
        defaultSupplierProductShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplierProductsBySupplierIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);
        Party supplier = PartyResourceIT.createEntity(em);
        em.persist(supplier);
        em.flush();
        supplierProduct.setSupplier(supplier);
        supplierProductRepository.saveAndFlush(supplierProduct);
        Long supplierId = supplier.getId();

        // Get all the supplierProductList where supplier equals to supplierId
        defaultSupplierProductShouldBeFound("supplierId.equals=" + supplierId);

        // Get all the supplierProductList where supplier equals to supplierId + 1
        defaultSupplierProductShouldNotBeFound("supplierId.equals=" + (supplierId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplierProductsByQuantityUomIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);
        Uom quantityUom = UomResourceIT.createEntity(em);
        em.persist(quantityUom);
        em.flush();
        supplierProduct.setQuantityUom(quantityUom);
        supplierProductRepository.saveAndFlush(supplierProduct);
        Long quantityUomId = quantityUom.getId();

        // Get all the supplierProductList where quantityUom equals to quantityUomId
        defaultSupplierProductShouldBeFound("quantityUomId.equals=" + quantityUomId);

        // Get all the supplierProductList where quantityUom equals to quantityUomId + 1
        defaultSupplierProductShouldNotBeFound("quantityUomId.equals=" + (quantityUomId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplierProductsByCurrencyUomIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);
        Uom currencyUom = UomResourceIT.createEntity(em);
        em.persist(currencyUom);
        em.flush();
        supplierProduct.setCurrencyUom(currencyUom);
        supplierProductRepository.saveAndFlush(supplierProduct);
        Long currencyUomId = currencyUom.getId();

        // Get all the supplierProductList where currencyUom equals to currencyUomId
        defaultSupplierProductShouldBeFound("currencyUomId.equals=" + currencyUomId);

        // Get all the supplierProductList where currencyUom equals to currencyUomId + 1
        defaultSupplierProductShouldNotBeFound("currencyUomId.equals=" + (currencyUomId + 1));
    }


    @Test
    @Transactional
    public void getAllSupplierProductsByManufacturerIsEqualToSomething() throws Exception {
        // Initialize the database
        supplierProductRepository.saveAndFlush(supplierProduct);
        Party manufacturer = PartyResourceIT.createEntity(em);
        em.persist(manufacturer);
        em.flush();
        supplierProduct.setManufacturer(manufacturer);
        supplierProductRepository.saveAndFlush(supplierProduct);
        Long manufacturerId = manufacturer.getId();

        // Get all the supplierProductList where manufacturer equals to manufacturerId
        defaultSupplierProductShouldBeFound("manufacturerId.equals=" + manufacturerId);

        // Get all the supplierProductList where manufacturer equals to manufacturerId + 1
        defaultSupplierProductShouldNotBeFound("manufacturerId.equals=" + (manufacturerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSupplierProductShouldBeFound(String filter) throws Exception {
        restSupplierProductMockMvc.perform(get("/api/supplier-products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supplierProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))))
            .andExpect(jsonPath("$.[*].minOrderQty").value(hasItem(DEFAULT_MIN_ORDER_QTY.intValue())))
            .andExpect(jsonPath("$.[*].lastPrice").value(hasItem(DEFAULT_LAST_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].shippingPrice").value(hasItem(DEFAULT_SHIPPING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].supplierProductId").value(hasItem(DEFAULT_SUPPLIER_PRODUCT_ID)))
            .andExpect(jsonPath("$.[*].leadTimeDays").value(hasItem(DEFAULT_LEAD_TIME_DAYS)))
            .andExpect(jsonPath("$.[*].cgst").value(hasItem(DEFAULT_CGST.intValue())))
            .andExpect(jsonPath("$.[*].igst").value(hasItem(DEFAULT_IGST.intValue())))
            .andExpect(jsonPath("$.[*].sgst").value(hasItem(DEFAULT_SGST.intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].supplierProductName").value(hasItem(DEFAULT_SUPPLIER_PRODUCT_NAME)))
            .andExpect(jsonPath("$.[*].manufacturerName").value(hasItem(DEFAULT_MANUFACTURER_NAME)));

        // Check, that the count call also returns 1
        restSupplierProductMockMvc.perform(get("/api/supplier-products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSupplierProductShouldNotBeFound(String filter) throws Exception {
        restSupplierProductMockMvc.perform(get("/api/supplier-products?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSupplierProductMockMvc.perform(get("/api/supplier-products/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingSupplierProduct() throws Exception {
        // Get the supplierProduct
        restSupplierProductMockMvc.perform(get("/api/supplier-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupplierProduct() throws Exception {
        // Initialize the database
        supplierProductService.save(supplierProduct);

        int databaseSizeBeforeUpdate = supplierProductRepository.findAll().size();

        // Update the supplierProduct
        SupplierProduct updatedSupplierProduct = supplierProductRepository.findById(supplierProduct.getId()).get();
        // Disconnect from session so that the updates on updatedSupplierProduct are not directly saved in db
        em.detach(updatedSupplierProduct);
        updatedSupplierProduct
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .minOrderQty(UPDATED_MIN_ORDER_QTY)
            .lastPrice(UPDATED_LAST_PRICE)
            .shippingPrice(UPDATED_SHIPPING_PRICE)
            .supplierProductId(UPDATED_SUPPLIER_PRODUCT_ID)
            .leadTimeDays(UPDATED_LEAD_TIME_DAYS)
            .cgst(UPDATED_CGST)
            .igst(UPDATED_IGST)
            .sgst(UPDATED_SGST)
            .totalPrice(UPDATED_TOTAL_PRICE)
            .comments(UPDATED_COMMENTS)
            .supplierProductName(UPDATED_SUPPLIER_PRODUCT_NAME)
            .manufacturerName(UPDATED_MANUFACTURER_NAME);

        restSupplierProductMockMvc.perform(put("/api/supplier-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSupplierProduct)))
            .andExpect(status().isOk());

        // Validate the SupplierProduct in the database
        List<SupplierProduct> supplierProductList = supplierProductRepository.findAll();
        assertThat(supplierProductList).hasSize(databaseSizeBeforeUpdate);
        SupplierProduct testSupplierProduct = supplierProductList.get(supplierProductList.size() - 1);
        assertThat(testSupplierProduct.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testSupplierProduct.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testSupplierProduct.getMinOrderQty()).isEqualTo(UPDATED_MIN_ORDER_QTY);
        assertThat(testSupplierProduct.getLastPrice()).isEqualTo(UPDATED_LAST_PRICE);
        assertThat(testSupplierProduct.getShippingPrice()).isEqualTo(UPDATED_SHIPPING_PRICE);
        assertThat(testSupplierProduct.getSupplierProductId()).isEqualTo(UPDATED_SUPPLIER_PRODUCT_ID);
        assertThat(testSupplierProduct.getLeadTimeDays()).isEqualTo(UPDATED_LEAD_TIME_DAYS);
        assertThat(testSupplierProduct.getCgst()).isEqualTo(UPDATED_CGST);
        assertThat(testSupplierProduct.getIgst()).isEqualTo(UPDATED_IGST);
        assertThat(testSupplierProduct.getSgst()).isEqualTo(UPDATED_SGST);
        assertThat(testSupplierProduct.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
        assertThat(testSupplierProduct.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testSupplierProduct.getSupplierProductName()).isEqualTo(UPDATED_SUPPLIER_PRODUCT_NAME);
        assertThat(testSupplierProduct.getManufacturerName()).isEqualTo(UPDATED_MANUFACTURER_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingSupplierProduct() throws Exception {
        int databaseSizeBeforeUpdate = supplierProductRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupplierProductMockMvc.perform(put("/api/supplier-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supplierProduct)))
            .andExpect(status().isBadRequest());

        // Validate the SupplierProduct in the database
        List<SupplierProduct> supplierProductList = supplierProductRepository.findAll();
        assertThat(supplierProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupplierProduct() throws Exception {
        // Initialize the database
        supplierProductService.save(supplierProduct);

        int databaseSizeBeforeDelete = supplierProductRepository.findAll().size();

        // Delete the supplierProduct
        restSupplierProductMockMvc.perform(delete("/api/supplier-products/{id}", supplierProduct.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SupplierProduct> supplierProductList = supplierProductRepository.findAll();
        assertThat(supplierProductList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
