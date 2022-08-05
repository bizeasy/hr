package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.OrderItemType;
import com.hr.repository.OrderItemTypeRepository;

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
 * Integration tests for the {@link OrderItemTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrderItemTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private OrderItemTypeRepository orderItemTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderItemTypeMockMvc;

    private OrderItemType orderItemType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderItemType createEntity(EntityManager em) {
        OrderItemType orderItemType = new OrderItemType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return orderItemType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderItemType createUpdatedEntity(EntityManager em) {
        OrderItemType orderItemType = new OrderItemType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return orderItemType;
    }

    @BeforeEach
    public void initTest() {
        orderItemType = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderItemType() throws Exception {
        int databaseSizeBeforeCreate = orderItemTypeRepository.findAll().size();
        // Create the OrderItemType
        restOrderItemTypeMockMvc.perform(post("/api/order-item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderItemType)))
            .andExpect(status().isCreated());

        // Validate the OrderItemType in the database
        List<OrderItemType> orderItemTypeList = orderItemTypeRepository.findAll();
        assertThat(orderItemTypeList).hasSize(databaseSizeBeforeCreate + 1);
        OrderItemType testOrderItemType = orderItemTypeList.get(orderItemTypeList.size() - 1);
        assertThat(testOrderItemType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrderItemType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createOrderItemTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderItemTypeRepository.findAll().size();

        // Create the OrderItemType with an existing ID
        orderItemType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderItemTypeMockMvc.perform(post("/api/order-item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderItemType)))
            .andExpect(status().isBadRequest());

        // Validate the OrderItemType in the database
        List<OrderItemType> orderItemTypeList = orderItemTypeRepository.findAll();
        assertThat(orderItemTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderItemTypes() throws Exception {
        // Initialize the database
        orderItemTypeRepository.saveAndFlush(orderItemType);

        // Get all the orderItemTypeList
        restOrderItemTypeMockMvc.perform(get("/api/order-item-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderItemType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getOrderItemType() throws Exception {
        // Initialize the database
        orderItemTypeRepository.saveAndFlush(orderItemType);

        // Get the orderItemType
        restOrderItemTypeMockMvc.perform(get("/api/order-item-types/{id}", orderItemType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderItemType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingOrderItemType() throws Exception {
        // Get the orderItemType
        restOrderItemTypeMockMvc.perform(get("/api/order-item-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderItemType() throws Exception {
        // Initialize the database
        orderItemTypeRepository.saveAndFlush(orderItemType);

        int databaseSizeBeforeUpdate = orderItemTypeRepository.findAll().size();

        // Update the orderItemType
        OrderItemType updatedOrderItemType = orderItemTypeRepository.findById(orderItemType.getId()).get();
        // Disconnect from session so that the updates on updatedOrderItemType are not directly saved in db
        em.detach(updatedOrderItemType);
        updatedOrderItemType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restOrderItemTypeMockMvc.perform(put("/api/order-item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderItemType)))
            .andExpect(status().isOk());

        // Validate the OrderItemType in the database
        List<OrderItemType> orderItemTypeList = orderItemTypeRepository.findAll();
        assertThat(orderItemTypeList).hasSize(databaseSizeBeforeUpdate);
        OrderItemType testOrderItemType = orderItemTypeList.get(orderItemTypeList.size() - 1);
        assertThat(testOrderItemType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrderItemType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderItemType() throws Exception {
        int databaseSizeBeforeUpdate = orderItemTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderItemTypeMockMvc.perform(put("/api/order-item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderItemType)))
            .andExpect(status().isBadRequest());

        // Validate the OrderItemType in the database
        List<OrderItemType> orderItemTypeList = orderItemTypeRepository.findAll();
        assertThat(orderItemTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderItemType() throws Exception {
        // Initialize the database
        orderItemTypeRepository.saveAndFlush(orderItemType);

        int databaseSizeBeforeDelete = orderItemTypeRepository.findAll().size();

        // Delete the orderItemType
        restOrderItemTypeMockMvc.perform(delete("/api/order-item-types/{id}", orderItemType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderItemType> orderItemTypeList = orderItemTypeRepository.findAll();
        assertThat(orderItemTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
