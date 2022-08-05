package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.ProductStoreProduct;
import com.hr.domain.ProductStore;
import com.hr.domain.Product;
import com.hr.repository.ProductStoreProductRepository;

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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ProductStoreProductResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ProductStoreProductResourceIT {

    private static final Instant DEFAULT_FROM_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FROM_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_THRU_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_THRU_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;

    @Autowired
    private ProductStoreProductRepository productStoreProductRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductStoreProductMockMvc;

    private ProductStoreProduct productStoreProduct;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductStoreProduct createEntity(EntityManager em) {
        ProductStoreProduct productStoreProduct = new ProductStoreProduct()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE)
            .sequenceNo(DEFAULT_SEQUENCE_NO);
        // Add required entity
        ProductStore productStore;
        if (TestUtil.findAll(em, ProductStore.class).isEmpty()) {
            productStore = ProductStoreResourceIT.createEntity(em);
            em.persist(productStore);
            em.flush();
        } else {
            productStore = TestUtil.findAll(em, ProductStore.class).get(0);
        }
        productStoreProduct.setProductStore(productStore);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        productStoreProduct.setProduct(product);
        return productStoreProduct;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductStoreProduct createUpdatedEntity(EntityManager em) {
        ProductStoreProduct productStoreProduct = new ProductStoreProduct()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .sequenceNo(UPDATED_SEQUENCE_NO);
        // Add required entity
        ProductStore productStore;
        if (TestUtil.findAll(em, ProductStore.class).isEmpty()) {
            productStore = ProductStoreResourceIT.createUpdatedEntity(em);
            em.persist(productStore);
            em.flush();
        } else {
            productStore = TestUtil.findAll(em, ProductStore.class).get(0);
        }
        productStoreProduct.setProductStore(productStore);
        // Add required entity
        Product product;
        if (TestUtil.findAll(em, Product.class).isEmpty()) {
            product = ProductResourceIT.createUpdatedEntity(em);
            em.persist(product);
            em.flush();
        } else {
            product = TestUtil.findAll(em, Product.class).get(0);
        }
        productStoreProduct.setProduct(product);
        return productStoreProduct;
    }

    @BeforeEach
    public void initTest() {
        productStoreProduct = createEntity(em);
    }

    @Test
    @Transactional
    public void createProductStoreProduct() throws Exception {
        int databaseSizeBeforeCreate = productStoreProductRepository.findAll().size();
        // Create the ProductStoreProduct
        restProductStoreProductMockMvc.perform(post("/api/product-store-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStoreProduct)))
            .andExpect(status().isCreated());

        // Validate the ProductStoreProduct in the database
        List<ProductStoreProduct> productStoreProductList = productStoreProductRepository.findAll();
        assertThat(productStoreProductList).hasSize(databaseSizeBeforeCreate + 1);
        ProductStoreProduct testProductStoreProduct = productStoreProductList.get(productStoreProductList.size() - 1);
        assertThat(testProductStoreProduct.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testProductStoreProduct.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
        assertThat(testProductStoreProduct.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void createProductStoreProductWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productStoreProductRepository.findAll().size();

        // Create the ProductStoreProduct with an existing ID
        productStoreProduct.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductStoreProductMockMvc.perform(post("/api/product-store-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStoreProduct)))
            .andExpect(status().isBadRequest());

        // Validate the ProductStoreProduct in the database
        List<ProductStoreProduct> productStoreProductList = productStoreProductRepository.findAll();
        assertThat(productStoreProductList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductStoreProducts() throws Exception {
        // Initialize the database
        productStoreProductRepository.saveAndFlush(productStoreProduct);

        // Get all the productStoreProductList
        restProductStoreProductMockMvc.perform(get("/api/product-store-products?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productStoreProduct.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(DEFAULT_FROM_DATE.toString())))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(DEFAULT_THRU_DATE.toString())))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)));
    }
    
    @Test
    @Transactional
    public void getProductStoreProduct() throws Exception {
        // Initialize the database
        productStoreProductRepository.saveAndFlush(productStoreProduct);

        // Get the productStoreProduct
        restProductStoreProductMockMvc.perform(get("/api/product-store-products/{id}", productStoreProduct.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productStoreProduct.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(DEFAULT_FROM_DATE.toString()))
            .andExpect(jsonPath("$.thruDate").value(DEFAULT_THRU_DATE.toString()))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO));
    }
    @Test
    @Transactional
    public void getNonExistingProductStoreProduct() throws Exception {
        // Get the productStoreProduct
        restProductStoreProductMockMvc.perform(get("/api/product-store-products/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProductStoreProduct() throws Exception {
        // Initialize the database
        productStoreProductRepository.saveAndFlush(productStoreProduct);

        int databaseSizeBeforeUpdate = productStoreProductRepository.findAll().size();

        // Update the productStoreProduct
        ProductStoreProduct updatedProductStoreProduct = productStoreProductRepository.findById(productStoreProduct.getId()).get();
        // Disconnect from session so that the updates on updatedProductStoreProduct are not directly saved in db
        em.detach(updatedProductStoreProduct);
        updatedProductStoreProduct
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE)
            .sequenceNo(UPDATED_SEQUENCE_NO);

        restProductStoreProductMockMvc.perform(put("/api/product-store-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedProductStoreProduct)))
            .andExpect(status().isOk());

        // Validate the ProductStoreProduct in the database
        List<ProductStoreProduct> productStoreProductList = productStoreProductRepository.findAll();
        assertThat(productStoreProductList).hasSize(databaseSizeBeforeUpdate);
        ProductStoreProduct testProductStoreProduct = productStoreProductList.get(productStoreProductList.size() - 1);
        assertThat(testProductStoreProduct.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testProductStoreProduct.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
        assertThat(testProductStoreProduct.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void updateNonExistingProductStoreProduct() throws Exception {
        int databaseSizeBeforeUpdate = productStoreProductRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductStoreProductMockMvc.perform(put("/api/product-store-products")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(productStoreProduct)))
            .andExpect(status().isBadRequest());

        // Validate the ProductStoreProduct in the database
        List<ProductStoreProduct> productStoreProductList = productStoreProductRepository.findAll();
        assertThat(productStoreProductList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProductStoreProduct() throws Exception {
        // Initialize the database
        productStoreProductRepository.saveAndFlush(productStoreProduct);

        int databaseSizeBeforeDelete = productStoreProductRepository.findAll().size();

        // Delete the productStoreProduct
        restProductStoreProductMockMvc.perform(delete("/api/product-store-products/{id}", productStoreProduct.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductStoreProduct> productStoreProductList = productStoreProductRepository.findAll();
        assertThat(productStoreProductList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
