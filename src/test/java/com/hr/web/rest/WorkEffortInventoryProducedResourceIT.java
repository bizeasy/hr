package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.WorkEffortInventoryProduced;
import com.hr.domain.WorkEffort;
import com.hr.domain.InventoryItem;
import com.hr.domain.Status;
import com.hr.repository.WorkEffortInventoryProducedRepository;
import com.hr.service.WorkEffortInventoryProducedService;
import com.hr.service.dto.WorkEffortInventoryProducedCriteria;
import com.hr.service.WorkEffortInventoryProducedQueryService;

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
 * Integration tests for the {@link WorkEffortInventoryProducedResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkEffortInventoryProducedResourceIT {

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;
    private static final Double SMALLER_QUANTITY = 1D - 1D;

    @Autowired
    private WorkEffortInventoryProducedRepository workEffortInventoryProducedRepository;

    @Autowired
    private WorkEffortInventoryProducedService workEffortInventoryProducedService;

    @Autowired
    private WorkEffortInventoryProducedQueryService workEffortInventoryProducedQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkEffortInventoryProducedMockMvc;

    private WorkEffortInventoryProduced workEffortInventoryProduced;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffortInventoryProduced createEntity(EntityManager em) {
        WorkEffortInventoryProduced workEffortInventoryProduced = new WorkEffortInventoryProduced()
            .quantity(DEFAULT_QUANTITY);
        return workEffortInventoryProduced;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffortInventoryProduced createUpdatedEntity(EntityManager em) {
        WorkEffortInventoryProduced workEffortInventoryProduced = new WorkEffortInventoryProduced()
            .quantity(UPDATED_QUANTITY);
        return workEffortInventoryProduced;
    }

    @BeforeEach
    public void initTest() {
        workEffortInventoryProduced = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkEffortInventoryProduced() throws Exception {
        int databaseSizeBeforeCreate = workEffortInventoryProducedRepository.findAll().size();
        // Create the WorkEffortInventoryProduced
        restWorkEffortInventoryProducedMockMvc.perform(post("/api/work-effort-inventory-produceds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortInventoryProduced)))
            .andExpect(status().isCreated());

        // Validate the WorkEffortInventoryProduced in the database
        List<WorkEffortInventoryProduced> workEffortInventoryProducedList = workEffortInventoryProducedRepository.findAll();
        assertThat(workEffortInventoryProducedList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEffortInventoryProduced testWorkEffortInventoryProduced = workEffortInventoryProducedList.get(workEffortInventoryProducedList.size() - 1);
        assertThat(testWorkEffortInventoryProduced.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createWorkEffortInventoryProducedWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workEffortInventoryProducedRepository.findAll().size();

        // Create the WorkEffortInventoryProduced with an existing ID
        workEffortInventoryProduced.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkEffortInventoryProducedMockMvc.perform(post("/api/work-effort-inventory-produceds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortInventoryProduced)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffortInventoryProduced in the database
        List<WorkEffortInventoryProduced> workEffortInventoryProducedList = workEffortInventoryProducedRepository.findAll();
        assertThat(workEffortInventoryProducedList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkEffortInventoryProduceds() throws Exception {
        // Initialize the database
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);

        // Get all the workEffortInventoryProducedList
        restWorkEffortInventoryProducedMockMvc.perform(get("/api/work-effort-inventory-produceds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEffortInventoryProduced.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getWorkEffortInventoryProduced() throws Exception {
        // Initialize the database
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);

        // Get the workEffortInventoryProduced
        restWorkEffortInventoryProducedMockMvc.perform(get("/api/work-effort-inventory-produceds/{id}", workEffortInventoryProduced.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workEffortInventoryProduced.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()));
    }


    @Test
    @Transactional
    public void getWorkEffortInventoryProducedsByIdFiltering() throws Exception {
        // Initialize the database
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);

        Long id = workEffortInventoryProduced.getId();

        defaultWorkEffortInventoryProducedShouldBeFound("id.equals=" + id);
        defaultWorkEffortInventoryProducedShouldNotBeFound("id.notEquals=" + id);

        defaultWorkEffortInventoryProducedShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkEffortInventoryProducedShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkEffortInventoryProducedShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkEffortInventoryProducedShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWorkEffortInventoryProducedsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);

        // Get all the workEffortInventoryProducedList where quantity equals to DEFAULT_QUANTITY
        defaultWorkEffortInventoryProducedShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the workEffortInventoryProducedList where quantity equals to UPDATED_QUANTITY
        defaultWorkEffortInventoryProducedShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortInventoryProducedsByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);

        // Get all the workEffortInventoryProducedList where quantity not equals to DEFAULT_QUANTITY
        defaultWorkEffortInventoryProducedShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the workEffortInventoryProducedList where quantity not equals to UPDATED_QUANTITY
        defaultWorkEffortInventoryProducedShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortInventoryProducedsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);

        // Get all the workEffortInventoryProducedList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultWorkEffortInventoryProducedShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the workEffortInventoryProducedList where quantity equals to UPDATED_QUANTITY
        defaultWorkEffortInventoryProducedShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortInventoryProducedsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);

        // Get all the workEffortInventoryProducedList where quantity is not null
        defaultWorkEffortInventoryProducedShouldBeFound("quantity.specified=true");

        // Get all the workEffortInventoryProducedList where quantity is null
        defaultWorkEffortInventoryProducedShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkEffortInventoryProducedsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);

        // Get all the workEffortInventoryProducedList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultWorkEffortInventoryProducedShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the workEffortInventoryProducedList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultWorkEffortInventoryProducedShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortInventoryProducedsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);

        // Get all the workEffortInventoryProducedList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultWorkEffortInventoryProducedShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the workEffortInventoryProducedList where quantity is less than or equal to SMALLER_QUANTITY
        defaultWorkEffortInventoryProducedShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortInventoryProducedsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);

        // Get all the workEffortInventoryProducedList where quantity is less than DEFAULT_QUANTITY
        defaultWorkEffortInventoryProducedShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the workEffortInventoryProducedList where quantity is less than UPDATED_QUANTITY
        defaultWorkEffortInventoryProducedShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortInventoryProducedsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);

        // Get all the workEffortInventoryProducedList where quantity is greater than DEFAULT_QUANTITY
        defaultWorkEffortInventoryProducedShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the workEffortInventoryProducedList where quantity is greater than SMALLER_QUANTITY
        defaultWorkEffortInventoryProducedShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllWorkEffortInventoryProducedsByWorkEffortIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);
        WorkEffort workEffort = WorkEffortResourceIT.createEntity(em);
        em.persist(workEffort);
        em.flush();
        workEffortInventoryProduced.setWorkEffort(workEffort);
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);
        Long workEffortId = workEffort.getId();

        // Get all the workEffortInventoryProducedList where workEffort equals to workEffortId
        defaultWorkEffortInventoryProducedShouldBeFound("workEffortId.equals=" + workEffortId);

        // Get all the workEffortInventoryProducedList where workEffort equals to workEffortId + 1
        defaultWorkEffortInventoryProducedShouldNotBeFound("workEffortId.equals=" + (workEffortId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkEffortInventoryProducedsByInventoryItemIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);
        InventoryItem inventoryItem = InventoryItemResourceIT.createEntity(em);
        em.persist(inventoryItem);
        em.flush();
        workEffortInventoryProduced.setInventoryItem(inventoryItem);
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);
        Long inventoryItemId = inventoryItem.getId();

        // Get all the workEffortInventoryProducedList where inventoryItem equals to inventoryItemId
        defaultWorkEffortInventoryProducedShouldBeFound("inventoryItemId.equals=" + inventoryItemId);

        // Get all the workEffortInventoryProducedList where inventoryItem equals to inventoryItemId + 1
        defaultWorkEffortInventoryProducedShouldNotBeFound("inventoryItemId.equals=" + (inventoryItemId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkEffortInventoryProducedsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        workEffortInventoryProduced.setStatus(status);
        workEffortInventoryProducedRepository.saveAndFlush(workEffortInventoryProduced);
        Long statusId = status.getId();

        // Get all the workEffortInventoryProducedList where status equals to statusId
        defaultWorkEffortInventoryProducedShouldBeFound("statusId.equals=" + statusId);

        // Get all the workEffortInventoryProducedList where status equals to statusId + 1
        defaultWorkEffortInventoryProducedShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkEffortInventoryProducedShouldBeFound(String filter) throws Exception {
        restWorkEffortInventoryProducedMockMvc.perform(get("/api/work-effort-inventory-produceds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEffortInventoryProduced.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));

        // Check, that the count call also returns 1
        restWorkEffortInventoryProducedMockMvc.perform(get("/api/work-effort-inventory-produceds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkEffortInventoryProducedShouldNotBeFound(String filter) throws Exception {
        restWorkEffortInventoryProducedMockMvc.perform(get("/api/work-effort-inventory-produceds?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkEffortInventoryProducedMockMvc.perform(get("/api/work-effort-inventory-produceds/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWorkEffortInventoryProduced() throws Exception {
        // Get the workEffortInventoryProduced
        restWorkEffortInventoryProducedMockMvc.perform(get("/api/work-effort-inventory-produceds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkEffortInventoryProduced() throws Exception {
        // Initialize the database
        workEffortInventoryProducedService.save(workEffortInventoryProduced);

        int databaseSizeBeforeUpdate = workEffortInventoryProducedRepository.findAll().size();

        // Update the workEffortInventoryProduced
        WorkEffortInventoryProduced updatedWorkEffortInventoryProduced = workEffortInventoryProducedRepository.findById(workEffortInventoryProduced.getId()).get();
        // Disconnect from session so that the updates on updatedWorkEffortInventoryProduced are not directly saved in db
        em.detach(updatedWorkEffortInventoryProduced);
        updatedWorkEffortInventoryProduced
            .quantity(UPDATED_QUANTITY);

        restWorkEffortInventoryProducedMockMvc.perform(put("/api/work-effort-inventory-produceds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkEffortInventoryProduced)))
            .andExpect(status().isOk());

        // Validate the WorkEffortInventoryProduced in the database
        List<WorkEffortInventoryProduced> workEffortInventoryProducedList = workEffortInventoryProducedRepository.findAll();
        assertThat(workEffortInventoryProducedList).hasSize(databaseSizeBeforeUpdate);
        WorkEffortInventoryProduced testWorkEffortInventoryProduced = workEffortInventoryProducedList.get(workEffortInventoryProducedList.size() - 1);
        assertThat(testWorkEffortInventoryProduced.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkEffortInventoryProduced() throws Exception {
        int databaseSizeBeforeUpdate = workEffortInventoryProducedRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkEffortInventoryProducedMockMvc.perform(put("/api/work-effort-inventory-produceds")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortInventoryProduced)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffortInventoryProduced in the database
        List<WorkEffortInventoryProduced> workEffortInventoryProducedList = workEffortInventoryProducedRepository.findAll();
        assertThat(workEffortInventoryProducedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkEffortInventoryProduced() throws Exception {
        // Initialize the database
        workEffortInventoryProducedService.save(workEffortInventoryProduced);

        int databaseSizeBeforeDelete = workEffortInventoryProducedRepository.findAll().size();

        // Delete the workEffortInventoryProduced
        restWorkEffortInventoryProducedMockMvc.perform(delete("/api/work-effort-inventory-produceds/{id}", workEffortInventoryProduced.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkEffortInventoryProduced> workEffortInventoryProducedList = workEffortInventoryProducedRepository.findAll();
        assertThat(workEffortInventoryProducedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
