package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.InventoryItemType;
import com.hr.repository.InventoryItemTypeRepository;

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
 * Integration tests for the {@link InventoryItemTypeResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InventoryItemTypeResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private InventoryItemTypeRepository inventoryItemTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryItemTypeMockMvc;

    private InventoryItemType inventoryItemType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryItemType createEntity(EntityManager em) {
        InventoryItemType inventoryItemType = new InventoryItemType()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return inventoryItemType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryItemType createUpdatedEntity(EntityManager em) {
        InventoryItemType inventoryItemType = new InventoryItemType()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return inventoryItemType;
    }

    @BeforeEach
    public void initTest() {
        inventoryItemType = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventoryItemType() throws Exception {
        int databaseSizeBeforeCreate = inventoryItemTypeRepository.findAll().size();
        // Create the InventoryItemType
        restInventoryItemTypeMockMvc.perform(post("/api/inventory-item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryItemType)))
            .andExpect(status().isCreated());

        // Validate the InventoryItemType in the database
        List<InventoryItemType> inventoryItemTypeList = inventoryItemTypeRepository.findAll();
        assertThat(inventoryItemTypeList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryItemType testInventoryItemType = inventoryItemTypeList.get(inventoryItemTypeList.size() - 1);
        assertThat(testInventoryItemType.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testInventoryItemType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createInventoryItemTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryItemTypeRepository.findAll().size();

        // Create the InventoryItemType with an existing ID
        inventoryItemType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryItemTypeMockMvc.perform(post("/api/inventory-item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryItemType)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryItemType in the database
        List<InventoryItemType> inventoryItemTypeList = inventoryItemTypeRepository.findAll();
        assertThat(inventoryItemTypeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInventoryItemTypes() throws Exception {
        // Initialize the database
        inventoryItemTypeRepository.saveAndFlush(inventoryItemType);

        // Get all the inventoryItemTypeList
        restInventoryItemTypeMockMvc.perform(get("/api/inventory-item-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryItemType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getInventoryItemType() throws Exception {
        // Initialize the database
        inventoryItemTypeRepository.saveAndFlush(inventoryItemType);

        // Get the inventoryItemType
        restInventoryItemTypeMockMvc.perform(get("/api/inventory-item-types/{id}", inventoryItemType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryItemType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }
    @Test
    @Transactional
    public void getNonExistingInventoryItemType() throws Exception {
        // Get the inventoryItemType
        restInventoryItemTypeMockMvc.perform(get("/api/inventory-item-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryItemType() throws Exception {
        // Initialize the database
        inventoryItemTypeRepository.saveAndFlush(inventoryItemType);

        int databaseSizeBeforeUpdate = inventoryItemTypeRepository.findAll().size();

        // Update the inventoryItemType
        InventoryItemType updatedInventoryItemType = inventoryItemTypeRepository.findById(inventoryItemType.getId()).get();
        // Disconnect from session so that the updates on updatedInventoryItemType are not directly saved in db
        em.detach(updatedInventoryItemType);
        updatedInventoryItemType
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restInventoryItemTypeMockMvc.perform(put("/api/inventory-item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventoryItemType)))
            .andExpect(status().isOk());

        // Validate the InventoryItemType in the database
        List<InventoryItemType> inventoryItemTypeList = inventoryItemTypeRepository.findAll();
        assertThat(inventoryItemTypeList).hasSize(databaseSizeBeforeUpdate);
        InventoryItemType testInventoryItemType = inventoryItemTypeList.get(inventoryItemTypeList.size() - 1);
        assertThat(testInventoryItemType.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testInventoryItemType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingInventoryItemType() throws Exception {
        int databaseSizeBeforeUpdate = inventoryItemTypeRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryItemTypeMockMvc.perform(put("/api/inventory-item-types")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryItemType)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryItemType in the database
        List<InventoryItemType> inventoryItemTypeList = inventoryItemTypeRepository.findAll();
        assertThat(inventoryItemTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventoryItemType() throws Exception {
        // Initialize the database
        inventoryItemTypeRepository.saveAndFlush(inventoryItemType);

        int databaseSizeBeforeDelete = inventoryItemTypeRepository.findAll().size();

        // Delete the inventoryItemType
        restInventoryItemTypeMockMvc.perform(delete("/api/inventory-item-types/{id}", inventoryItemType.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InventoryItemType> inventoryItemTypeList = inventoryItemTypeRepository.findAll();
        assertThat(inventoryItemTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
