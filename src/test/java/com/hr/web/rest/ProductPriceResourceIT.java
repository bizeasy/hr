package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ProductPrice;
import com.hr.domain.Product;
import com.hr.domain.ProductPriceType;
import com.hr.domain.ProductPricePurpose;
import com.hr.domain.Uom;
import com.hr.repository.ProductPriceRepository;
import com.hr.service.ProductPriceService;
import com.hr.service.dto.ProductPriceCriteria;
import com.hr.service.ProductPriceQueryService;

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
 * Integration tests for the {@link ProductPriceResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductPriceResourceIT {

    private static final ZonedDateTime DEFAULT_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FROM_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_THRU_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);
    private static final BigDecimal SMALLER_PRICE = new BigDecimal(1 - 1);

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

    @Autowired
    private ProductPriceRepository productPriceRepository;

    @Autowired
    private ProductPriceService productPriceService;

    @Autowired
    private ProductPriceQueryService productPriceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductPriceMockMvc;

    private ProductPrice productPrice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductPrice createEntity(EntityManager em) {
        ProductPrice productPrice = new ProductPrice()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .price(DEFAULT_PRICE)
            .cgst(DEFAULT_CGST)
            .igst(DEFAULT_IGST)
            .sgst(DEFAULT_SGST)
            .totalPrice(DEFAULT_TOTAL_PRICE);
        return productPrice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductPrice createUpdatedEntity(EntityManager em) {
        ProductPrice productPrice = new ProductPrice()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .price(UPDATED_PRICE)
            .cgst(UPDATED_CGST)
            .igst(UPDATED_IGST)
            .sgst(UPDATED_SGST)
            .totalPrice(UPDATED_TOTAL_PRICE);
        return productPrice;
    }

    @BeforeEach
    public void initTest() {
        productPrice = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductPrice() throws Exception {
        int databaseSizeBeforeCreate = productPriceRepository.findAll().size();
        // Create the ProductPrice
        restProductPriceMockMvc.perform(post("/api/product-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productPrice)))
            .andExpect(status().isCreated());

        // Validate the ProductPrice in the database
        List<ProductPrice> productPriceList = productPriceRepository.findAll();
        assertThat(productPriceList).hasSize(databaseSizeBeforeCreate + 1);
        ProductPrice testProductPrice = productPriceList.get(productPriceList.size() - 1);
        assertThat(testProductPrice.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testProductPrice.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testProductPrice.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProductPrice.getCgst()).isEqualTo(DEFAULT_CGST);
        assertThat(testProductPrice.getIgst()).isEqualTo(DEFAULT_IGST);
        assertThat(testProductPrice.getSgst()).isEqualTo(DEFAULT_SGST);
        assertThat(testProductPrice.getTotalPrice()).isEqualTo(DEFAULT_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void createProductPriceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productPriceRepository.findAll().size();

        // Create the ProductPrice with an existing ID
        productPrice.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductPriceMockMvc.perform(post("/api/product-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productPrice)))
            .andExpect(status().isBadRequest());

        // Validate the ProductPrice in the database
        List<ProductPrice> productPriceList = productPriceRepository.findAll();
        assertThat(productPriceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductPrices() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList
        restProductPriceMockMvc.perform(get("/api/product-prices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].cgst").value(hasItem(DEFAULT_CGST.intValue())))
            .andExpect(jsonPath("$.[*].igst").value(hasItem(DEFAULT_IGST.intValue())))
            .andExpect(jsonPath("$.[*].sgst").value(hasItem(DEFAULT_SGST.intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())));
    }
    
    @Test
    @Transactional
    public void getProductPrice() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get the productPrice
        restProductPriceMockMvc.perform(get("/api/product-prices/{id}", productPrice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productPrice.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(sameInstant(DEFAULT_FROM_DATE)))
            .andExpect(jsonPath("$.thruDate").value(sameInstant(DEFAULT_THRU_DATE)))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.cgst").value(DEFAULT_CGST.intValue()))
            .andExpect(jsonPath("$.igst").value(DEFAULT_IGST.intValue()))
            .andExpect(jsonPath("$.sgst").value(DEFAULT_SGST.intValue()))
            .andExpect(jsonPath("$.totalPrice").value(DEFAULT_TOTAL_PRICE.intValue()));
    }


    @Test
    @Transactional
    public void getProductPricesByIdFiltering() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        Long id = productPrice.getId();

        defaultProductPriceShouldBeFound("id.equals=" + id);
        defaultProductPriceShouldNotBeFound("id.notEquals=" + id);

        defaultProductPriceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductPriceShouldNotBeFound("id.greaterThan=" + id);

        defaultProductPriceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductPriceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductPricesByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where fromDate equals to DEFAULT_FROM_DATE
        defaultProductPriceShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the productPriceList where fromDate equals to UPDATED_FROM_DATE
        defaultProductPriceShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where fromDate not equals to DEFAULT_FROM_DATE
        defaultProductPriceShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the productPriceList where fromDate not equals to UPDATED_FROM_DATE
        defaultProductPriceShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultProductPriceShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the productPriceList where fromDate equals to UPDATED_FROM_DATE
        defaultProductPriceShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where fromDate is not null
        defaultProductPriceShouldBeFound("fromDate.specified=true");

        // Get all the productPriceList where fromDate is null
        defaultProductPriceShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductPricesByFromDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where fromDate is greater than or equal to DEFAULT_FROM_DATE
        defaultProductPriceShouldBeFound("fromDate.greaterThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the productPriceList where fromDate is greater than or equal to UPDATED_FROM_DATE
        defaultProductPriceShouldNotBeFound("fromDate.greaterThanOrEqual=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByFromDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where fromDate is less than or equal to DEFAULT_FROM_DATE
        defaultProductPriceShouldBeFound("fromDate.lessThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the productPriceList where fromDate is less than or equal to SMALLER_FROM_DATE
        defaultProductPriceShouldNotBeFound("fromDate.lessThanOrEqual=" + SMALLER_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByFromDateIsLessThanSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where fromDate is less than DEFAULT_FROM_DATE
        defaultProductPriceShouldNotBeFound("fromDate.lessThan=" + DEFAULT_FROM_DATE);

        // Get all the productPriceList where fromDate is less than UPDATED_FROM_DATE
        defaultProductPriceShouldBeFound("fromDate.lessThan=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByFromDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where fromDate is greater than DEFAULT_FROM_DATE
        defaultProductPriceShouldNotBeFound("fromDate.greaterThan=" + DEFAULT_FROM_DATE);

        // Get all the productPriceList where fromDate is greater than SMALLER_FROM_DATE
        defaultProductPriceShouldBeFound("fromDate.greaterThan=" + SMALLER_FROM_DATE);
    }


    @Test
    @Transactional
    public void getAllProductPricesByThruDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where thruDate equals to DEFAULT_THRU_DATE
        defaultProductPriceShouldBeFound("thruDate.equals=" + DEFAULT_THRU_DATE);

        // Get all the productPriceList where thruDate equals to UPDATED_THRU_DATE
        defaultProductPriceShouldNotBeFound("thruDate.equals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByThruDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where thruDate not equals to DEFAULT_THRU_DATE
        defaultProductPriceShouldNotBeFound("thruDate.notEquals=" + DEFAULT_THRU_DATE);

        // Get all the productPriceList where thruDate not equals to UPDATED_THRU_DATE
        defaultProductPriceShouldBeFound("thruDate.notEquals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByThruDateIsInShouldWork() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where thruDate in DEFAULT_THRU_DATE or UPDATED_THRU_DATE
        defaultProductPriceShouldBeFound("thruDate.in=" + DEFAULT_THRU_DATE + "," + UPDATED_THRU_DATE);

        // Get all the productPriceList where thruDate equals to UPDATED_THRU_DATE
        defaultProductPriceShouldNotBeFound("thruDate.in=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByThruDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where thruDate is not null
        defaultProductPriceShouldBeFound("thruDate.specified=true");

        // Get all the productPriceList where thruDate is null
        defaultProductPriceShouldNotBeFound("thruDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductPricesByThruDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where thruDate is greater than or equal to DEFAULT_THRU_DATE
        defaultProductPriceShouldBeFound("thruDate.greaterThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the productPriceList where thruDate is greater than or equal to UPDATED_THRU_DATE
        defaultProductPriceShouldNotBeFound("thruDate.greaterThanOrEqual=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByThruDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where thruDate is less than or equal to DEFAULT_THRU_DATE
        defaultProductPriceShouldBeFound("thruDate.lessThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the productPriceList where thruDate is less than or equal to SMALLER_THRU_DATE
        defaultProductPriceShouldNotBeFound("thruDate.lessThanOrEqual=" + SMALLER_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByThruDateIsLessThanSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where thruDate is less than DEFAULT_THRU_DATE
        defaultProductPriceShouldNotBeFound("thruDate.lessThan=" + DEFAULT_THRU_DATE);

        // Get all the productPriceList where thruDate is less than UPDATED_THRU_DATE
        defaultProductPriceShouldBeFound("thruDate.lessThan=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByThruDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where thruDate is greater than DEFAULT_THRU_DATE
        defaultProductPriceShouldNotBeFound("thruDate.greaterThan=" + DEFAULT_THRU_DATE);

        // Get all the productPriceList where thruDate is greater than SMALLER_THRU_DATE
        defaultProductPriceShouldBeFound("thruDate.greaterThan=" + SMALLER_THRU_DATE);
    }


    @Test
    @Transactional
    public void getAllProductPricesByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where price equals to DEFAULT_PRICE
        defaultProductPriceShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the productPriceList where price equals to UPDATED_PRICE
        defaultProductPriceShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where price not equals to DEFAULT_PRICE
        defaultProductPriceShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the productPriceList where price not equals to UPDATED_PRICE
        defaultProductPriceShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultProductPriceShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the productPriceList where price equals to UPDATED_PRICE
        defaultProductPriceShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where price is not null
        defaultProductPriceShouldBeFound("price.specified=true");

        // Get all the productPriceList where price is null
        defaultProductPriceShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductPricesByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where price is greater than or equal to DEFAULT_PRICE
        defaultProductPriceShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the productPriceList where price is greater than or equal to UPDATED_PRICE
        defaultProductPriceShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where price is less than or equal to DEFAULT_PRICE
        defaultProductPriceShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the productPriceList where price is less than or equal to SMALLER_PRICE
        defaultProductPriceShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where price is less than DEFAULT_PRICE
        defaultProductPriceShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the productPriceList where price is less than UPDATED_PRICE
        defaultProductPriceShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where price is greater than DEFAULT_PRICE
        defaultProductPriceShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the productPriceList where price is greater than SMALLER_PRICE
        defaultProductPriceShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }


    @Test
    @Transactional
    public void getAllProductPricesByCgstIsEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where cgst equals to DEFAULT_CGST
        defaultProductPriceShouldBeFound("cgst.equals=" + DEFAULT_CGST);

        // Get all the productPriceList where cgst equals to UPDATED_CGST
        defaultProductPriceShouldNotBeFound("cgst.equals=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesByCgstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where cgst not equals to DEFAULT_CGST
        defaultProductPriceShouldNotBeFound("cgst.notEquals=" + DEFAULT_CGST);

        // Get all the productPriceList where cgst not equals to UPDATED_CGST
        defaultProductPriceShouldBeFound("cgst.notEquals=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesByCgstIsInShouldWork() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where cgst in DEFAULT_CGST or UPDATED_CGST
        defaultProductPriceShouldBeFound("cgst.in=" + DEFAULT_CGST + "," + UPDATED_CGST);

        // Get all the productPriceList where cgst equals to UPDATED_CGST
        defaultProductPriceShouldNotBeFound("cgst.in=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesByCgstIsNullOrNotNull() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where cgst is not null
        defaultProductPriceShouldBeFound("cgst.specified=true");

        // Get all the productPriceList where cgst is null
        defaultProductPriceShouldNotBeFound("cgst.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductPricesByCgstIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where cgst is greater than or equal to DEFAULT_CGST
        defaultProductPriceShouldBeFound("cgst.greaterThanOrEqual=" + DEFAULT_CGST);

        // Get all the productPriceList where cgst is greater than or equal to UPDATED_CGST
        defaultProductPriceShouldNotBeFound("cgst.greaterThanOrEqual=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesByCgstIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where cgst is less than or equal to DEFAULT_CGST
        defaultProductPriceShouldBeFound("cgst.lessThanOrEqual=" + DEFAULT_CGST);

        // Get all the productPriceList where cgst is less than or equal to SMALLER_CGST
        defaultProductPriceShouldNotBeFound("cgst.lessThanOrEqual=" + SMALLER_CGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesByCgstIsLessThanSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where cgst is less than DEFAULT_CGST
        defaultProductPriceShouldNotBeFound("cgst.lessThan=" + DEFAULT_CGST);

        // Get all the productPriceList where cgst is less than UPDATED_CGST
        defaultProductPriceShouldBeFound("cgst.lessThan=" + UPDATED_CGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesByCgstIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where cgst is greater than DEFAULT_CGST
        defaultProductPriceShouldNotBeFound("cgst.greaterThan=" + DEFAULT_CGST);

        // Get all the productPriceList where cgst is greater than SMALLER_CGST
        defaultProductPriceShouldBeFound("cgst.greaterThan=" + SMALLER_CGST);
    }


    @Test
    @Transactional
    public void getAllProductPricesByIgstIsEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where igst equals to DEFAULT_IGST
        defaultProductPriceShouldBeFound("igst.equals=" + DEFAULT_IGST);

        // Get all the productPriceList where igst equals to UPDATED_IGST
        defaultProductPriceShouldNotBeFound("igst.equals=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesByIgstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where igst not equals to DEFAULT_IGST
        defaultProductPriceShouldNotBeFound("igst.notEquals=" + DEFAULT_IGST);

        // Get all the productPriceList where igst not equals to UPDATED_IGST
        defaultProductPriceShouldBeFound("igst.notEquals=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesByIgstIsInShouldWork() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where igst in DEFAULT_IGST or UPDATED_IGST
        defaultProductPriceShouldBeFound("igst.in=" + DEFAULT_IGST + "," + UPDATED_IGST);

        // Get all the productPriceList where igst equals to UPDATED_IGST
        defaultProductPriceShouldNotBeFound("igst.in=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesByIgstIsNullOrNotNull() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where igst is not null
        defaultProductPriceShouldBeFound("igst.specified=true");

        // Get all the productPriceList where igst is null
        defaultProductPriceShouldNotBeFound("igst.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductPricesByIgstIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where igst is greater than or equal to DEFAULT_IGST
        defaultProductPriceShouldBeFound("igst.greaterThanOrEqual=" + DEFAULT_IGST);

        // Get all the productPriceList where igst is greater than or equal to UPDATED_IGST
        defaultProductPriceShouldNotBeFound("igst.greaterThanOrEqual=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesByIgstIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where igst is less than or equal to DEFAULT_IGST
        defaultProductPriceShouldBeFound("igst.lessThanOrEqual=" + DEFAULT_IGST);

        // Get all the productPriceList where igst is less than or equal to SMALLER_IGST
        defaultProductPriceShouldNotBeFound("igst.lessThanOrEqual=" + SMALLER_IGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesByIgstIsLessThanSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where igst is less than DEFAULT_IGST
        defaultProductPriceShouldNotBeFound("igst.lessThan=" + DEFAULT_IGST);

        // Get all the productPriceList where igst is less than UPDATED_IGST
        defaultProductPriceShouldBeFound("igst.lessThan=" + UPDATED_IGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesByIgstIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where igst is greater than DEFAULT_IGST
        defaultProductPriceShouldNotBeFound("igst.greaterThan=" + DEFAULT_IGST);

        // Get all the productPriceList where igst is greater than SMALLER_IGST
        defaultProductPriceShouldBeFound("igst.greaterThan=" + SMALLER_IGST);
    }


    @Test
    @Transactional
    public void getAllProductPricesBySgstIsEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where sgst equals to DEFAULT_SGST
        defaultProductPriceShouldBeFound("sgst.equals=" + DEFAULT_SGST);

        // Get all the productPriceList where sgst equals to UPDATED_SGST
        defaultProductPriceShouldNotBeFound("sgst.equals=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesBySgstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where sgst not equals to DEFAULT_SGST
        defaultProductPriceShouldNotBeFound("sgst.notEquals=" + DEFAULT_SGST);

        // Get all the productPriceList where sgst not equals to UPDATED_SGST
        defaultProductPriceShouldBeFound("sgst.notEquals=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesBySgstIsInShouldWork() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where sgst in DEFAULT_SGST or UPDATED_SGST
        defaultProductPriceShouldBeFound("sgst.in=" + DEFAULT_SGST + "," + UPDATED_SGST);

        // Get all the productPriceList where sgst equals to UPDATED_SGST
        defaultProductPriceShouldNotBeFound("sgst.in=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesBySgstIsNullOrNotNull() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where sgst is not null
        defaultProductPriceShouldBeFound("sgst.specified=true");

        // Get all the productPriceList where sgst is null
        defaultProductPriceShouldNotBeFound("sgst.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductPricesBySgstIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where sgst is greater than or equal to DEFAULT_SGST
        defaultProductPriceShouldBeFound("sgst.greaterThanOrEqual=" + DEFAULT_SGST);

        // Get all the productPriceList where sgst is greater than or equal to UPDATED_SGST
        defaultProductPriceShouldNotBeFound("sgst.greaterThanOrEqual=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesBySgstIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where sgst is less than or equal to DEFAULT_SGST
        defaultProductPriceShouldBeFound("sgst.lessThanOrEqual=" + DEFAULT_SGST);

        // Get all the productPriceList where sgst is less than or equal to SMALLER_SGST
        defaultProductPriceShouldNotBeFound("sgst.lessThanOrEqual=" + SMALLER_SGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesBySgstIsLessThanSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where sgst is less than DEFAULT_SGST
        defaultProductPriceShouldNotBeFound("sgst.lessThan=" + DEFAULT_SGST);

        // Get all the productPriceList where sgst is less than UPDATED_SGST
        defaultProductPriceShouldBeFound("sgst.lessThan=" + UPDATED_SGST);
    }

    @Test
    @Transactional
    public void getAllProductPricesBySgstIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where sgst is greater than DEFAULT_SGST
        defaultProductPriceShouldNotBeFound("sgst.greaterThan=" + DEFAULT_SGST);

        // Get all the productPriceList where sgst is greater than SMALLER_SGST
        defaultProductPriceShouldBeFound("sgst.greaterThan=" + SMALLER_SGST);
    }


    @Test
    @Transactional
    public void getAllProductPricesByTotalPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where totalPrice equals to DEFAULT_TOTAL_PRICE
        defaultProductPriceShouldBeFound("totalPrice.equals=" + DEFAULT_TOTAL_PRICE);

        // Get all the productPriceList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultProductPriceShouldNotBeFound("totalPrice.equals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByTotalPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where totalPrice not equals to DEFAULT_TOTAL_PRICE
        defaultProductPriceShouldNotBeFound("totalPrice.notEquals=" + DEFAULT_TOTAL_PRICE);

        // Get all the productPriceList where totalPrice not equals to UPDATED_TOTAL_PRICE
        defaultProductPriceShouldBeFound("totalPrice.notEquals=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByTotalPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where totalPrice in DEFAULT_TOTAL_PRICE or UPDATED_TOTAL_PRICE
        defaultProductPriceShouldBeFound("totalPrice.in=" + DEFAULT_TOTAL_PRICE + "," + UPDATED_TOTAL_PRICE);

        // Get all the productPriceList where totalPrice equals to UPDATED_TOTAL_PRICE
        defaultProductPriceShouldNotBeFound("totalPrice.in=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByTotalPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where totalPrice is not null
        defaultProductPriceShouldBeFound("totalPrice.specified=true");

        // Get all the productPriceList where totalPrice is null
        defaultProductPriceShouldNotBeFound("totalPrice.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductPricesByTotalPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where totalPrice is greater than or equal to DEFAULT_TOTAL_PRICE
        defaultProductPriceShouldBeFound("totalPrice.greaterThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the productPriceList where totalPrice is greater than or equal to UPDATED_TOTAL_PRICE
        defaultProductPriceShouldNotBeFound("totalPrice.greaterThanOrEqual=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByTotalPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where totalPrice is less than or equal to DEFAULT_TOTAL_PRICE
        defaultProductPriceShouldBeFound("totalPrice.lessThanOrEqual=" + DEFAULT_TOTAL_PRICE);

        // Get all the productPriceList where totalPrice is less than or equal to SMALLER_TOTAL_PRICE
        defaultProductPriceShouldNotBeFound("totalPrice.lessThanOrEqual=" + SMALLER_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByTotalPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where totalPrice is less than DEFAULT_TOTAL_PRICE
        defaultProductPriceShouldNotBeFound("totalPrice.lessThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the productPriceList where totalPrice is less than UPDATED_TOTAL_PRICE
        defaultProductPriceShouldBeFound("totalPrice.lessThan=" + UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void getAllProductPricesByTotalPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);

        // Get all the productPriceList where totalPrice is greater than DEFAULT_TOTAL_PRICE
        defaultProductPriceShouldNotBeFound("totalPrice.greaterThan=" + DEFAULT_TOTAL_PRICE);

        // Get all the productPriceList where totalPrice is greater than SMALLER_TOTAL_PRICE
        defaultProductPriceShouldBeFound("totalPrice.greaterThan=" + SMALLER_TOTAL_PRICE);
    }


    @Test
    @Transactional
    public void getAllProductPricesByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productPrice.setProduct(product);
        productPriceRepository.saveAndFlush(productPrice);
        Long productId = product.getId();

        // Get all the productPriceList where product equals to productId
        defaultProductPriceShouldBeFound("productId.equals=" + productId);

        // Get all the productPriceList where product equals to productId + 1
        defaultProductPriceShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllProductPricesByProductPriceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);
        ProductPriceType productPriceType = ProductPriceTypeResourceIT.createEntity(em);
        em.persist(productPriceType);
        em.flush();
        productPrice.setProductPriceType(productPriceType);
        productPriceRepository.saveAndFlush(productPrice);
        Long productPriceTypeId = productPriceType.getId();

        // Get all the productPriceList where productPriceType equals to productPriceTypeId
        defaultProductPriceShouldBeFound("productPriceTypeId.equals=" + productPriceTypeId);

        // Get all the productPriceList where productPriceType equals to productPriceTypeId + 1
        defaultProductPriceShouldNotBeFound("productPriceTypeId.equals=" + (productPriceTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllProductPricesByProductPricePurposeIsEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);
        ProductPricePurpose productPricePurpose = ProductPricePurposeResourceIT.createEntity(em);
        em.persist(productPricePurpose);
        em.flush();
        productPrice.setProductPricePurpose(productPricePurpose);
        productPriceRepository.saveAndFlush(productPrice);
        Long productPricePurposeId = productPricePurpose.getId();

        // Get all the productPriceList where productPricePurpose equals to productPricePurposeId
        defaultProductPriceShouldBeFound("productPricePurposeId.equals=" + productPricePurposeId);

        // Get all the productPriceList where productPricePurpose equals to productPricePurposeId + 1
        defaultProductPriceShouldNotBeFound("productPricePurposeId.equals=" + (productPricePurposeId + 1));
    }


    @Test
    @Transactional
    public void getAllProductPricesByCurrencyUomIsEqualToSomething() throws Exception {
        // Initialize the database
        productPriceRepository.saveAndFlush(productPrice);
        Uom currencyUom = UomResourceIT.createEntity(em);
        em.persist(currencyUom);
        em.flush();
        productPrice.setCurrencyUom(currencyUom);
        productPriceRepository.saveAndFlush(productPrice);
        Long currencyUomId = currencyUom.getId();

        // Get all the productPriceList where currencyUom equals to currencyUomId
        defaultProductPriceShouldBeFound("currencyUomId.equals=" + currencyUomId);

        // Get all the productPriceList where currencyUom equals to currencyUomId + 1
        defaultProductPriceShouldNotBeFound("currencyUomId.equals=" + (currencyUomId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductPriceShouldBeFound(String filter) throws Exception {
        restProductPriceMockMvc.perform(get("/api/product-prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productPrice.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].cgst").value(hasItem(DEFAULT_CGST.intValue())))
            .andExpect(jsonPath("$.[*].igst").value(hasItem(DEFAULT_IGST.intValue())))
            .andExpect(jsonPath("$.[*].sgst").value(hasItem(DEFAULT_SGST.intValue())))
            .andExpect(jsonPath("$.[*].totalPrice").value(hasItem(DEFAULT_TOTAL_PRICE.intValue())));

        // Check, that the count call also returns 1
        restProductPriceMockMvc.perform(get("/api/product-prices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductPriceShouldNotBeFound(String filter) throws Exception {
        restProductPriceMockMvc.perform(get("/api/product-prices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductPriceMockMvc.perform(get("/api/product-prices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductPrice() throws Exception {
        // Get the productPrice
        restProductPriceMockMvc.perform(get("/api/product-prices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductPrice() throws Exception {
        // Initialize the database
        productPriceService.save(productPrice);

        int databaseSizeBeforeUpdate = productPriceRepository.findAll().size();

        // Update the productPrice
        ProductPrice updatedProductPrice = productPriceRepository.findById(productPrice.getId()).get();
        // Disconnect from session so that the updates on updatedProductPrice are not directly saved in db
        em.detach(updatedProductPrice);
        updatedProductPrice
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .price(UPDATED_PRICE)
            .cgst(UPDATED_CGST)
            .igst(UPDATED_IGST)
            .sgst(UPDATED_SGST)
            .totalPrice(UPDATED_TOTAL_PRICE);

        restProductPriceMockMvc.perform(put("/api/product-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductPrice)))
            .andExpect(status().isOk());

        // Validate the ProductPrice in the database
        List<ProductPrice> productPriceList = productPriceRepository.findAll();
        assertThat(productPriceList).hasSize(databaseSizeBeforeUpdate);
        ProductPrice testProductPrice = productPriceList.get(productPriceList.size() - 1);
        assertThat(testProductPrice.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testProductPrice.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testProductPrice.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProductPrice.getCgst()).isEqualTo(UPDATED_CGST);
        assertThat(testProductPrice.getIgst()).isEqualTo(UPDATED_IGST);
        assertThat(testProductPrice.getSgst()).isEqualTo(UPDATED_SGST);
        assertThat(testProductPrice.getTotalPrice()).isEqualTo(UPDATED_TOTAL_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingProductPrice() throws Exception {
        int databaseSizeBeforeUpdate = productPriceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductPriceMockMvc.perform(put("/api/product-prices")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productPrice)))
            .andExpect(status().isBadRequest());

        // Validate the ProductPrice in the database
        List<ProductPrice> productPriceList = productPriceRepository.findAll();
        assertThat(productPriceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductPrice() throws Exception {
        // Initialize the database
        productPriceService.save(productPrice);

        int databaseSizeBeforeDelete = productPriceRepository.findAll().size();

        // Delete the productPrice
        restProductPriceMockMvc.perform(delete("/api/product-prices/{id}", productPrice.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductPrice> productPriceList = productPriceRepository.findAll();
        assertThat(productPriceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
