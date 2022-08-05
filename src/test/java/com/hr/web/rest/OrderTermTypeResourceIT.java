package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.OrderTermType;
import com.hr.repository.OrderTermTypeRepository;

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
 * Integration tests for the {@link OrderTermTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrderTermTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OrderTermTypeRepository orderTermTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderTermTypeMockMvc;

    private OrderTermType orderTermType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderTermType createEntity(EntityManager em) {
        OrderTermType orderTermType = new OrderTermType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return orderTermType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderTermType createUpdatedEntity(EntityManager em) {
        OrderTermType orderTermType = new OrderTermType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return orderTermType;
    }

    @BeforeEach
    public void initTest() {
        orderTermType = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderTermType() throws Exception {
        int databaseSizeBeforeCreate = orderTermTypeRepository.findAll().size();
        // Create the OrderTermType
        restOrderTermTypeMockMvc.perform(post("/api/order-term-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTermType)))
            .andExpect(status().isCreated());

        // Validate the OrderTermType in the database
        List<OrderTermType> orderTermTypeList = orderTermTypeRepository.findAll();
        assertThat(orderTermTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OrderTermType testOrderTermType = orderTermTypeList.get(orderTermTypeList.size() - 1);
        assertThat(testOrderTermType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrderTermType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOrderTermTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderTermTypeRepository.findAll().size();

        // Create the OrderTermType with an existing ID
        orderTermType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderTermTypeMockMvc.perform(post("/api/order-term-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTermType)))
            .andExpect(status().isBadRequest());

        // Validate the OrderTermType in the database
        List<OrderTermType> orderTermTypeList = orderTermTypeRepository.findAll();
        assertThat(orderTermTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderTermTypes() throws Exception {
        // Initialize the database
        orderTermTypeRepository.saveAndFlush(orderTermType);

        // Get all the orderTermTypeList
        restOrderTermTypeMockMvc.perform(get("/api/order-term-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderTermType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getOrderTermType() throws Exception {
        // Initialize the database
        orderTermTypeRepository.saveAndFlush(orderTermType);

        // Get the orderTermType
        restOrderTermTypeMockMvc.perform(get("/api/order-term-types/{id}", orderTermType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderTermType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingOrderTermType() throws Exception {
        // Get the orderTermType
        restOrderTermTypeMockMvc.perform(get("/api/order-term-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderTermType() throws Exception {
        // Initialize the database
        orderTermTypeRepository.saveAndFlush(orderTermType);

        int databaseSizeBeforeUpdate = orderTermTypeRepository.findAll().size();

        // Update the orderTermType
        OrderTermType updatedOrderTermType = orderTermTypeRepository.findById(orderTermType.getId()).get();
        // Disconnect from session so that the updates on updatedOrderTermType are not directly saved in db
        em.detach(updatedOrderTermType);
        updatedOrderTermType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restOrderTermTypeMockMvc.perform(put("/api/order-term-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderTermType)))
            .andExpect(status().isOk());

        // Validate the OrderTermType in the database
        List<OrderTermType> orderTermTypeList = orderTermTypeRepository.findAll();
        assertThat(orderTermTypeList).hasSize(databaseSizeBeforeUpdate);
        OrderTermType testOrderTermType = orderTermTypeList.get(orderTermTypeList.size() - 1);
        assertThat(testOrderTermType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrderTermType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderTermType() throws Exception {
        int databaseSizeBeforeUpdate = orderTermTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderTermTypeMockMvc.perform(put("/api/order-term-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderTermType)))
            .andExpect(status().isBadRequest());

        // Validate the OrderTermType in the database
        List<OrderTermType> orderTermTypeList = orderTermTypeRepository.findAll();
        assertThat(orderTermTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderTermType() throws Exception {
        // Initialize the database
        orderTermTypeRepository.saveAndFlush(orderTermType);

        int databaseSizeBeforeDelete = orderTermTypeRepository.findAll().size();

        // Delete the orderTermType
        restOrderTermTypeMockMvc.perform(delete("/api/order-term-types/{id}", orderTermType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderTermType> orderTermTypeList = orderTermTypeRepository.findAll();
        assertThat(orderTermTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
