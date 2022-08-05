package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ProductFacility;
import com.hr.domain.Product;
import com.hr.domain.Facility;
import com.hr.repository.ProductFacilityRepository;
import com.hr.service.ProductFacilityService;
import com.hr.service.dto.ProductFacilityCriteria;
import com.hr.service.ProductFacilityQueryService;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductFacilityResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductFacilityResourceIT {

    private static final BigDecimal DEFAULT_MINIMUM_STOCK = new BigDecimal(1);
    private static final BigDecimal UPDATED_MINIMUM_STOCK = new BigDecimal(2);
    private static final BigDecimal SMALLER_MINIMUM_STOCK = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_REORDER_QTY = new BigDecimal(1);
    private static final BigDecimal UPDATED_REORDER_QTY = new BigDecimal(2);
    private static final BigDecimal SMALLER_REORDER_QTY = new BigDecimal(1 - 1);

    private static final Integer DEFAULT_DAYS_TO_SHIP = 1;
    private static final Integer UPDATED_DAYS_TO_SHIP = 2;
    private static final Integer SMALLER_DAYS_TO_SHIP = 1 - 1;

    private static final BigDecimal DEFAULT_LAST_INVENTORY_COUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_LAST_INVENTORY_COUNT = new BigDecimal(2);
    private static final BigDecimal SMALLER_LAST_INVENTORY_COUNT = new BigDecimal(1 - 1);

    @Autowired
    private ProductFacilityRepository productFacilityRepository;

    @Autowired
    private ProductFacilityService productFacilityService;

    @Autowired
    private ProductFacilityQueryService productFacilityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductFacilityMockMvc;

    private ProductFacility productFacility;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductFacility createEntity(EntityManager em) {
        ProductFacility productFacility = new ProductFacility()
            .minimumStock(DEFAULT_MINIMUM_STOCK)
            .reorderQty(DEFAULT_REORDER_QTY)
            .daysToShip(DEFAULT_DAYS_TO_SHIP)
            .lastInventoryCount(DEFAULT_LAST_INVENTORY_COUNT);
        return productFacility;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductFacility createUpdatedEntity(EntityManager em) {
        ProductFacility productFacility = new ProductFacility()
            .minimumStock(UPDATED_MINIMUM_STOCK)
            .reorderQty(UPDATED_REORDER_QTY)
            .daysToShip(UPDATED_DAYS_TO_SHIP)
            .lastInventoryCount(UPDATED_LAST_INVENTORY_COUNT);
        return productFacility;
    }

    @BeforeEach
    public void initTest() {
        productFacility = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductFacility() throws Exception {
        int databaseSizeBeforeCreate = productFacilityRepository.findAll().size();
        // Create the ProductFacility
        restProductFacilityMockMvc.perform(post("/api/product-facilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productFacility)))
            .andExpect(status().isCreated());

        // Validate the ProductFacility in the database
        List<ProductFacility> productFacilityList = productFacilityRepository.findAll();
        assertThat(productFacilityList).hasSize(databaseSizeBeforeCreate + 1);
        ProductFacility testProductFacility = productFacilityList.get(productFacilityList.size() - 1);
        assertThat(testProductFacility.getMinimumStock()).isEqualTo(DEFAULT_MINIMUM_STOCK);
        assertThat(testProductFacility.getReorderQty()).isEqualTo(DEFAULT_REORDER_QTY);
        assertThat(testProductFacility.getDaysToShip()).isEqualTo(DEFAULT_DAYS_TO_SHIP);
        assertThat(testProductFacility.getLastInventoryCount()).isEqualTo(DEFAULT_LAST_INVENTORY_COUNT);
    }

    @Test
    @Transactional
    public void createProductFacilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productFacilityRepository.findAll().size();

        // Create the ProductFacility with an existing ID
        productFacility.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductFacilityMockMvc.perform(post("/api/product-facilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productFacility)))
            .andExpect(status().isBadRequest());

        // Validate the ProductFacility in the database
        List<ProductFacility> productFacilityList = productFacilityRepository.findAll();
        assertThat(productFacilityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductFacilities() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList
        restProductFacilityMockMvc.perform(get("/api/product-facilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productFacility.getId().intValue())))
            .andExpect(jsonPath("$.[*].minimumStock").value(hasItem(DEFAULT_MINIMUM_STOCK.intValue())))
            .andExpect(jsonPath("$.[*].reorderQty").value(hasItem(DEFAULT_REORDER_QTY.intValue())))
            .andExpect(jsonPath("$.[*].daysToShip").value(hasItem(DEFAULT_DAYS_TO_SHIP)))
            .andExpect(jsonPath("$.[*].lastInventoryCount").value(hasItem(DEFAULT_LAST_INVENTORY_COUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getProductFacility() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get the productFacility
        restProductFacilityMockMvc.perform(get("/api/product-facilities/{id}", productFacility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productFacility.getId().intValue()))
            .andExpect(jsonPath("$.minimumStock").value(DEFAULT_MINIMUM_STOCK.intValue()))
            .andExpect(jsonPath("$.reorderQty").value(DEFAULT_REORDER_QTY.intValue()))
            .andExpect(jsonPath("$.daysToShip").value(DEFAULT_DAYS_TO_SHIP))
            .andExpect(jsonPath("$.lastInventoryCount").value(DEFAULT_LAST_INVENTORY_COUNT.intValue()));
    }


    @Test
    @Transactional
    public void getProductFacilitiesByIdFiltering() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        Long id = productFacility.getId();

        defaultProductFacilityShouldBeFound("id.equals=" + id);
        defaultProductFacilityShouldNotBeFound("id.notEquals=" + id);

        defaultProductFacilityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductFacilityShouldNotBeFound("id.greaterThan=" + id);

        defaultProductFacilityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductFacilityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllProductFacilitiesByMinimumStockIsEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where minimumStock equals to DEFAULT_MINIMUM_STOCK
        defaultProductFacilityShouldBeFound("minimumStock.equals=" + DEFAULT_MINIMUM_STOCK);

        // Get all the productFacilityList where minimumStock equals to UPDATED_MINIMUM_STOCK
        defaultProductFacilityShouldNotBeFound("minimumStock.equals=" + UPDATED_MINIMUM_STOCK);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByMinimumStockIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where minimumStock not equals to DEFAULT_MINIMUM_STOCK
        defaultProductFacilityShouldNotBeFound("minimumStock.notEquals=" + DEFAULT_MINIMUM_STOCK);

        // Get all the productFacilityList where minimumStock not equals to UPDATED_MINIMUM_STOCK
        defaultProductFacilityShouldBeFound("minimumStock.notEquals=" + UPDATED_MINIMUM_STOCK);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByMinimumStockIsInShouldWork() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where minimumStock in DEFAULT_MINIMUM_STOCK or UPDATED_MINIMUM_STOCK
        defaultProductFacilityShouldBeFound("minimumStock.in=" + DEFAULT_MINIMUM_STOCK + "," + UPDATED_MINIMUM_STOCK);

        // Get all the productFacilityList where minimumStock equals to UPDATED_MINIMUM_STOCK
        defaultProductFacilityShouldNotBeFound("minimumStock.in=" + UPDATED_MINIMUM_STOCK);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByMinimumStockIsNullOrNotNull() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where minimumStock is not null
        defaultProductFacilityShouldBeFound("minimumStock.specified=true");

        // Get all the productFacilityList where minimumStock is null
        defaultProductFacilityShouldNotBeFound("minimumStock.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByMinimumStockIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where minimumStock is greater than or equal to DEFAULT_MINIMUM_STOCK
        defaultProductFacilityShouldBeFound("minimumStock.greaterThanOrEqual=" + DEFAULT_MINIMUM_STOCK);

        // Get all the productFacilityList where minimumStock is greater than or equal to UPDATED_MINIMUM_STOCK
        defaultProductFacilityShouldNotBeFound("minimumStock.greaterThanOrEqual=" + UPDATED_MINIMUM_STOCK);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByMinimumStockIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where minimumStock is less than or equal to DEFAULT_MINIMUM_STOCK
        defaultProductFacilityShouldBeFound("minimumStock.lessThanOrEqual=" + DEFAULT_MINIMUM_STOCK);

        // Get all the productFacilityList where minimumStock is less than or equal to SMALLER_MINIMUM_STOCK
        defaultProductFacilityShouldNotBeFound("minimumStock.lessThanOrEqual=" + SMALLER_MINIMUM_STOCK);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByMinimumStockIsLessThanSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where minimumStock is less than DEFAULT_MINIMUM_STOCK
        defaultProductFacilityShouldNotBeFound("minimumStock.lessThan=" + DEFAULT_MINIMUM_STOCK);

        // Get all the productFacilityList where minimumStock is less than UPDATED_MINIMUM_STOCK
        defaultProductFacilityShouldBeFound("minimumStock.lessThan=" + UPDATED_MINIMUM_STOCK);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByMinimumStockIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where minimumStock is greater than DEFAULT_MINIMUM_STOCK
        defaultProductFacilityShouldNotBeFound("minimumStock.greaterThan=" + DEFAULT_MINIMUM_STOCK);

        // Get all the productFacilityList where minimumStock is greater than SMALLER_MINIMUM_STOCK
        defaultProductFacilityShouldBeFound("minimumStock.greaterThan=" + SMALLER_MINIMUM_STOCK);
    }


    @Test
    @Transactional
    public void getAllProductFacilitiesByReorderQtyIsEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where reorderQty equals to DEFAULT_REORDER_QTY
        defaultProductFacilityShouldBeFound("reorderQty.equals=" + DEFAULT_REORDER_QTY);

        // Get all the productFacilityList where reorderQty equals to UPDATED_REORDER_QTY
        defaultProductFacilityShouldNotBeFound("reorderQty.equals=" + UPDATED_REORDER_QTY);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByReorderQtyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where reorderQty not equals to DEFAULT_REORDER_QTY
        defaultProductFacilityShouldNotBeFound("reorderQty.notEquals=" + DEFAULT_REORDER_QTY);

        // Get all the productFacilityList where reorderQty not equals to UPDATED_REORDER_QTY
        defaultProductFacilityShouldBeFound("reorderQty.notEquals=" + UPDATED_REORDER_QTY);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByReorderQtyIsInShouldWork() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where reorderQty in DEFAULT_REORDER_QTY or UPDATED_REORDER_QTY
        defaultProductFacilityShouldBeFound("reorderQty.in=" + DEFAULT_REORDER_QTY + "," + UPDATED_REORDER_QTY);

        // Get all the productFacilityList where reorderQty equals to UPDATED_REORDER_QTY
        defaultProductFacilityShouldNotBeFound("reorderQty.in=" + UPDATED_REORDER_QTY);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByReorderQtyIsNullOrNotNull() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where reorderQty is not null
        defaultProductFacilityShouldBeFound("reorderQty.specified=true");

        // Get all the productFacilityList where reorderQty is null
        defaultProductFacilityShouldNotBeFound("reorderQty.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByReorderQtyIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where reorderQty is greater than or equal to DEFAULT_REORDER_QTY
        defaultProductFacilityShouldBeFound("reorderQty.greaterThanOrEqual=" + DEFAULT_REORDER_QTY);

        // Get all the productFacilityList where reorderQty is greater than or equal to UPDATED_REORDER_QTY
        defaultProductFacilityShouldNotBeFound("reorderQty.greaterThanOrEqual=" + UPDATED_REORDER_QTY);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByReorderQtyIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where reorderQty is less than or equal to DEFAULT_REORDER_QTY
        defaultProductFacilityShouldBeFound("reorderQty.lessThanOrEqual=" + DEFAULT_REORDER_QTY);

        // Get all the productFacilityList where reorderQty is less than or equal to SMALLER_REORDER_QTY
        defaultProductFacilityShouldNotBeFound("reorderQty.lessThanOrEqual=" + SMALLER_REORDER_QTY);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByReorderQtyIsLessThanSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where reorderQty is less than DEFAULT_REORDER_QTY
        defaultProductFacilityShouldNotBeFound("reorderQty.lessThan=" + DEFAULT_REORDER_QTY);

        // Get all the productFacilityList where reorderQty is less than UPDATED_REORDER_QTY
        defaultProductFacilityShouldBeFound("reorderQty.lessThan=" + UPDATED_REORDER_QTY);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByReorderQtyIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where reorderQty is greater than DEFAULT_REORDER_QTY
        defaultProductFacilityShouldNotBeFound("reorderQty.greaterThan=" + DEFAULT_REORDER_QTY);

        // Get all the productFacilityList where reorderQty is greater than SMALLER_REORDER_QTY
        defaultProductFacilityShouldBeFound("reorderQty.greaterThan=" + SMALLER_REORDER_QTY);
    }


    @Test
    @Transactional
    public void getAllProductFacilitiesByDaysToShipIsEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where daysToShip equals to DEFAULT_DAYS_TO_SHIP
        defaultProductFacilityShouldBeFound("daysToShip.equals=" + DEFAULT_DAYS_TO_SHIP);

        // Get all the productFacilityList where daysToShip equals to UPDATED_DAYS_TO_SHIP
        defaultProductFacilityShouldNotBeFound("daysToShip.equals=" + UPDATED_DAYS_TO_SHIP);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByDaysToShipIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where daysToShip not equals to DEFAULT_DAYS_TO_SHIP
        defaultProductFacilityShouldNotBeFound("daysToShip.notEquals=" + DEFAULT_DAYS_TO_SHIP);

        // Get all the productFacilityList where daysToShip not equals to UPDATED_DAYS_TO_SHIP
        defaultProductFacilityShouldBeFound("daysToShip.notEquals=" + UPDATED_DAYS_TO_SHIP);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByDaysToShipIsInShouldWork() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where daysToShip in DEFAULT_DAYS_TO_SHIP or UPDATED_DAYS_TO_SHIP
        defaultProductFacilityShouldBeFound("daysToShip.in=" + DEFAULT_DAYS_TO_SHIP + "," + UPDATED_DAYS_TO_SHIP);

        // Get all the productFacilityList where daysToShip equals to UPDATED_DAYS_TO_SHIP
        defaultProductFacilityShouldNotBeFound("daysToShip.in=" + UPDATED_DAYS_TO_SHIP);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByDaysToShipIsNullOrNotNull() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where daysToShip is not null
        defaultProductFacilityShouldBeFound("daysToShip.specified=true");

        // Get all the productFacilityList where daysToShip is null
        defaultProductFacilityShouldNotBeFound("daysToShip.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByDaysToShipIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where daysToShip is greater than or equal to DEFAULT_DAYS_TO_SHIP
        defaultProductFacilityShouldBeFound("daysToShip.greaterThanOrEqual=" + DEFAULT_DAYS_TO_SHIP);

        // Get all the productFacilityList where daysToShip is greater than or equal to UPDATED_DAYS_TO_SHIP
        defaultProductFacilityShouldNotBeFound("daysToShip.greaterThanOrEqual=" + UPDATED_DAYS_TO_SHIP);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByDaysToShipIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where daysToShip is less than or equal to DEFAULT_DAYS_TO_SHIP
        defaultProductFacilityShouldBeFound("daysToShip.lessThanOrEqual=" + DEFAULT_DAYS_TO_SHIP);

        // Get all the productFacilityList where daysToShip is less than or equal to SMALLER_DAYS_TO_SHIP
        defaultProductFacilityShouldNotBeFound("daysToShip.lessThanOrEqual=" + SMALLER_DAYS_TO_SHIP);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByDaysToShipIsLessThanSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where daysToShip is less than DEFAULT_DAYS_TO_SHIP
        defaultProductFacilityShouldNotBeFound("daysToShip.lessThan=" + DEFAULT_DAYS_TO_SHIP);

        // Get all the productFacilityList where daysToShip is less than UPDATED_DAYS_TO_SHIP
        defaultProductFacilityShouldBeFound("daysToShip.lessThan=" + UPDATED_DAYS_TO_SHIP);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByDaysToShipIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where daysToShip is greater than DEFAULT_DAYS_TO_SHIP
        defaultProductFacilityShouldNotBeFound("daysToShip.greaterThan=" + DEFAULT_DAYS_TO_SHIP);

        // Get all the productFacilityList where daysToShip is greater than SMALLER_DAYS_TO_SHIP
        defaultProductFacilityShouldBeFound("daysToShip.greaterThan=" + SMALLER_DAYS_TO_SHIP);
    }


    @Test
    @Transactional
    public void getAllProductFacilitiesByLastInventoryCountIsEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where lastInventoryCount equals to DEFAULT_LAST_INVENTORY_COUNT
        defaultProductFacilityShouldBeFound("lastInventoryCount.equals=" + DEFAULT_LAST_INVENTORY_COUNT);

        // Get all the productFacilityList where lastInventoryCount equals to UPDATED_LAST_INVENTORY_COUNT
        defaultProductFacilityShouldNotBeFound("lastInventoryCount.equals=" + UPDATED_LAST_INVENTORY_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByLastInventoryCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where lastInventoryCount not equals to DEFAULT_LAST_INVENTORY_COUNT
        defaultProductFacilityShouldNotBeFound("lastInventoryCount.notEquals=" + DEFAULT_LAST_INVENTORY_COUNT);

        // Get all the productFacilityList where lastInventoryCount not equals to UPDATED_LAST_INVENTORY_COUNT
        defaultProductFacilityShouldBeFound("lastInventoryCount.notEquals=" + UPDATED_LAST_INVENTORY_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByLastInventoryCountIsInShouldWork() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where lastInventoryCount in DEFAULT_LAST_INVENTORY_COUNT or UPDATED_LAST_INVENTORY_COUNT
        defaultProductFacilityShouldBeFound("lastInventoryCount.in=" + DEFAULT_LAST_INVENTORY_COUNT + "," + UPDATED_LAST_INVENTORY_COUNT);

        // Get all the productFacilityList where lastInventoryCount equals to UPDATED_LAST_INVENTORY_COUNT
        defaultProductFacilityShouldNotBeFound("lastInventoryCount.in=" + UPDATED_LAST_INVENTORY_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByLastInventoryCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where lastInventoryCount is not null
        defaultProductFacilityShouldBeFound("lastInventoryCount.specified=true");

        // Get all the productFacilityList where lastInventoryCount is null
        defaultProductFacilityShouldNotBeFound("lastInventoryCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByLastInventoryCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where lastInventoryCount is greater than or equal to DEFAULT_LAST_INVENTORY_COUNT
        defaultProductFacilityShouldBeFound("lastInventoryCount.greaterThanOrEqual=" + DEFAULT_LAST_INVENTORY_COUNT);

        // Get all the productFacilityList where lastInventoryCount is greater than or equal to UPDATED_LAST_INVENTORY_COUNT
        defaultProductFacilityShouldNotBeFound("lastInventoryCount.greaterThanOrEqual=" + UPDATED_LAST_INVENTORY_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByLastInventoryCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where lastInventoryCount is less than or equal to DEFAULT_LAST_INVENTORY_COUNT
        defaultProductFacilityShouldBeFound("lastInventoryCount.lessThanOrEqual=" + DEFAULT_LAST_INVENTORY_COUNT);

        // Get all the productFacilityList where lastInventoryCount is less than or equal to SMALLER_LAST_INVENTORY_COUNT
        defaultProductFacilityShouldNotBeFound("lastInventoryCount.lessThanOrEqual=" + SMALLER_LAST_INVENTORY_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByLastInventoryCountIsLessThanSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where lastInventoryCount is less than DEFAULT_LAST_INVENTORY_COUNT
        defaultProductFacilityShouldNotBeFound("lastInventoryCount.lessThan=" + DEFAULT_LAST_INVENTORY_COUNT);

        // Get all the productFacilityList where lastInventoryCount is less than UPDATED_LAST_INVENTORY_COUNT
        defaultProductFacilityShouldBeFound("lastInventoryCount.lessThan=" + UPDATED_LAST_INVENTORY_COUNT);
    }

    @Test
    @Transactional
    public void getAllProductFacilitiesByLastInventoryCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);

        // Get all the productFacilityList where lastInventoryCount is greater than DEFAULT_LAST_INVENTORY_COUNT
        defaultProductFacilityShouldNotBeFound("lastInventoryCount.greaterThan=" + DEFAULT_LAST_INVENTORY_COUNT);

        // Get all the productFacilityList where lastInventoryCount is greater than SMALLER_LAST_INVENTORY_COUNT
        defaultProductFacilityShouldBeFound("lastInventoryCount.greaterThan=" + SMALLER_LAST_INVENTORY_COUNT);
    }


    @Test
    @Transactional
    public void getAllProductFacilitiesByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productFacility.setProduct(product);
        productFacilityRepository.saveAndFlush(productFacility);
        Long productId = product.getId();

        // Get all the productFacilityList where product equals to productId
        defaultProductFacilityShouldBeFound("productId.equals=" + productId);

        // Get all the productFacilityList where product equals to productId + 1
        defaultProductFacilityShouldNotBeFound("productId.equals=" + (productId + 1));
    }


    @Test
    @Transactional
    public void getAllProductFacilitiesByFacilityIsEqualToSomething() throws Exception {
        // Initialize the database
        productFacilityRepository.saveAndFlush(productFacility);
        Facility facility = FacilityResourceIT.createEntity(em);
        em.persist(facility);
        em.flush();
        productFacility.setFacility(facility);
        productFacilityRepository.saveAndFlush(productFacility);
        Long facilityId = facility.getId();

        // Get all the productFacilityList where facility equals to facilityId
        defaultProductFacilityShouldBeFound("facilityId.equals=" + facilityId);

        // Get all the productFacilityList where facility equals to facilityId + 1
        defaultProductFacilityShouldNotBeFound("facilityId.equals=" + (facilityId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductFacilityShouldBeFound(String filter) throws Exception {
        restProductFacilityMockMvc.perform(get("/api/product-facilities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productFacility.getId().intValue())))
            .andExpect(jsonPath("$.[*].minimumStock").value(hasItem(DEFAULT_MINIMUM_STOCK.intValue())))
            .andExpect(jsonPath("$.[*].reorderQty").value(hasItem(DEFAULT_REORDER_QTY.intValue())))
            .andExpect(jsonPath("$.[*].daysToShip").value(hasItem(DEFAULT_DAYS_TO_SHIP)))
            .andExpect(jsonPath("$.[*].lastInventoryCount").value(hasItem(DEFAULT_LAST_INVENTORY_COUNT.intValue())));

        // Check, that the count call also returns 1
        restProductFacilityMockMvc.perform(get("/api/product-facilities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductFacilityShouldNotBeFound(String filter) throws Exception {
        restProductFacilityMockMvc.perform(get("/api/product-facilities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductFacilityMockMvc.perform(get("/api/product-facilities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingProductFacility() throws Exception {
        // Get the productFacility
        restProductFacilityMockMvc.perform(get("/api/product-facilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductFacility() throws Exception {
        // Initialize the database
        productFacilityService.save(productFacility);

        int databaseSizeBeforeUpdate = productFacilityRepository.findAll().size();

        // Update the productFacility
        ProductFacility updatedProductFacility = productFacilityRepository.findById(productFacility.getId()).get();
        // Disconnect from session so that the updates on updatedProductFacility are not directly saved in db
        em.detach(updatedProductFacility);
        updatedProductFacility
            .minimumStock(UPDATED_MINIMUM_STOCK)
            .reorderQty(UPDATED_REORDER_QTY)
            .daysToShip(UPDATED_DAYS_TO_SHIP)
            .lastInventoryCount(UPDATED_LAST_INVENTORY_COUNT);

        restProductFacilityMockMvc.perform(put("/api/product-facilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductFacility)))
            .andExpect(status().isOk());

        // Validate the ProductFacility in the database
        List<ProductFacility> productFacilityList = productFacilityRepository.findAll();
        assertThat(productFacilityList).hasSize(databaseSizeBeforeUpdate);
        ProductFacility testProductFacility = productFacilityList.get(productFacilityList.size() - 1);
        assertThat(testProductFacility.getMinimumStock()).isEqualTo(UPDATED_MINIMUM_STOCK);
        assertThat(testProductFacility.getReorderQty()).isEqualTo(UPDATED_REORDER_QTY);
        assertThat(testProductFacility.getDaysToShip()).isEqualTo(UPDATED_DAYS_TO_SHIP);
        assertThat(testProductFacility.getLastInventoryCount()).isEqualTo(UPDATED_LAST_INVENTORY_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingProductFacility() throws Exception {
        int databaseSizeBeforeUpdate = productFacilityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductFacilityMockMvc.perform(put("/api/product-facilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productFacility)))
            .andExpect(status().isBadRequest());

        // Validate the ProductFacility in the database
        List<ProductFacility> productFacilityList = productFacilityRepository.findAll();
        assertThat(productFacilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductFacility() throws Exception {
        // Initialize the database
        productFacilityService.save(productFacility);

        int databaseSizeBeforeDelete = productFacilityRepository.findAll().size();

        // Delete the productFacility
        restProductFacilityMockMvc.perform(delete("/api/product-facilities/{id}", productFacility.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductFacility> productFacilityList = productFacilityRepository.findAll();
        assertThat(productFacilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
