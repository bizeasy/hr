package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.WorkEffortInventoryAssign;
import com.hr.domain.WorkEffort;
import com.hr.domain.InventoryItem;
import com.hr.domain.Status;
import com.hr.repository.WorkEffortInventoryAssignRepository;
import com.hr.service.WorkEffortInventoryAssignService;
import com.hr.service.dto.WorkEffortInventoryAssignCriteria;
import com.hr.service.WorkEffortInventoryAssignQueryService;

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
 * Integration tests for the {@link WorkEffortInventoryAssignResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WorkEffortInventoryAssignResourceIT {

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;
    private static final Double SMALLER_QUANTITY = 1D - 1D;

    @Autowired
    private WorkEffortInventoryAssignRepository workEffortInventoryAssignRepository;

    @Autowired
    private WorkEffortInventoryAssignService workEffortInventoryAssignService;

    @Autowired
    private WorkEffortInventoryAssignQueryService workEffortInventoryAssignQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkEffortInventoryAssignMockMvc;

    private WorkEffortInventoryAssign workEffortInventoryAssign;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffortInventoryAssign createEntity(EntityManager em) {
        WorkEffortInventoryAssign workEffortInventoryAssign = new WorkEffortInventoryAssign()
            .quantity(DEFAULT_QUANTITY);
        return workEffortInventoryAssign;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkEffortInventoryAssign createUpdatedEntity(EntityManager em) {
        WorkEffortInventoryAssign workEffortInventoryAssign = new WorkEffortInventoryAssign()
            .quantity(UPDATED_QUANTITY);
        return workEffortInventoryAssign;
    }

    @BeforeEach
    public void initTest() {
        workEffortInventoryAssign = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkEffortInventoryAssign() throws Exception {
        int databaseSizeBeforeCreate = workEffortInventoryAssignRepository.findAll().size();
        // Create the WorkEffortInventoryAssign
        restWorkEffortInventoryAssignMockMvc.perform(post("/api/work-effort-inventory-assigns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortInventoryAssign)))
            .andExpect(status().isCreated());

        // Validate the WorkEffortInventoryAssign in the database
        List<WorkEffortInventoryAssign> workEffortInventoryAssignList = workEffortInventoryAssignRepository.findAll();
        assertThat(workEffortInventoryAssignList).hasSize(databaseSizeBeforeCreate + 1);
        WorkEffortInventoryAssign testWorkEffortInventoryAssign = workEffortInventoryAssignList.get(workEffortInventoryAssignList.size() - 1);
        assertThat(testWorkEffortInventoryAssign.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
    }

    @Test
    @Transactional
    public void createWorkEffortInventoryAssignWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workEffortInventoryAssignRepository.findAll().size();

        // Create the WorkEffortInventoryAssign with an existing ID
        workEffortInventoryAssign.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkEffortInventoryAssignMockMvc.perform(post("/api/work-effort-inventory-assigns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortInventoryAssign)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffortInventoryAssign in the database
        List<WorkEffortInventoryAssign> workEffortInventoryAssignList = workEffortInventoryAssignRepository.findAll();
        assertThat(workEffortInventoryAssignList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkEffortInventoryAssigns() throws Exception {
        // Initialize the database
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);

        // Get all the workEffortInventoryAssignList
        restWorkEffortInventoryAssignMockMvc.perform(get("/api/work-effort-inventory-assigns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEffortInventoryAssign.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getWorkEffortInventoryAssign() throws Exception {
        // Initialize the database
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);

        // Get the workEffortInventoryAssign
        restWorkEffortInventoryAssignMockMvc.perform(get("/api/work-effort-inventory-assigns/{id}", workEffortInventoryAssign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workEffortInventoryAssign.getId().intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()));
    }


    @Test
    @Transactional
    public void getWorkEffortInventoryAssignsByIdFiltering() throws Exception {
        // Initialize the database
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);

        Long id = workEffortInventoryAssign.getId();

        defaultWorkEffortInventoryAssignShouldBeFound("id.equals=" + id);
        defaultWorkEffortInventoryAssignShouldNotBeFound("id.notEquals=" + id);

        defaultWorkEffortInventoryAssignShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultWorkEffortInventoryAssignShouldNotBeFound("id.greaterThan=" + id);

        defaultWorkEffortInventoryAssignShouldBeFound("id.lessThanOrEqual=" + id);
        defaultWorkEffortInventoryAssignShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllWorkEffortInventoryAssignsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);

        // Get all the workEffortInventoryAssignList where quantity equals to DEFAULT_QUANTITY
        defaultWorkEffortInventoryAssignShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the workEffortInventoryAssignList where quantity equals to UPDATED_QUANTITY
        defaultWorkEffortInventoryAssignShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortInventoryAssignsByQuantityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);

        // Get all the workEffortInventoryAssignList where quantity not equals to DEFAULT_QUANTITY
        defaultWorkEffortInventoryAssignShouldNotBeFound("quantity.notEquals=" + DEFAULT_QUANTITY);

        // Get all the workEffortInventoryAssignList where quantity not equals to UPDATED_QUANTITY
        defaultWorkEffortInventoryAssignShouldBeFound("quantity.notEquals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortInventoryAssignsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);

        // Get all the workEffortInventoryAssignList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultWorkEffortInventoryAssignShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the workEffortInventoryAssignList where quantity equals to UPDATED_QUANTITY
        defaultWorkEffortInventoryAssignShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortInventoryAssignsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);

        // Get all the workEffortInventoryAssignList where quantity is not null
        defaultWorkEffortInventoryAssignShouldBeFound("quantity.specified=true");

        // Get all the workEffortInventoryAssignList where quantity is null
        defaultWorkEffortInventoryAssignShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllWorkEffortInventoryAssignsByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);

        // Get all the workEffortInventoryAssignList where quantity is greater than or equal to DEFAULT_QUANTITY
        defaultWorkEffortInventoryAssignShouldBeFound("quantity.greaterThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the workEffortInventoryAssignList where quantity is greater than or equal to UPDATED_QUANTITY
        defaultWorkEffortInventoryAssignShouldNotBeFound("quantity.greaterThanOrEqual=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortInventoryAssignsByQuantityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);

        // Get all the workEffortInventoryAssignList where quantity is less than or equal to DEFAULT_QUANTITY
        defaultWorkEffortInventoryAssignShouldBeFound("quantity.lessThanOrEqual=" + DEFAULT_QUANTITY);

        // Get all the workEffortInventoryAssignList where quantity is less than or equal to SMALLER_QUANTITY
        defaultWorkEffortInventoryAssignShouldNotBeFound("quantity.lessThanOrEqual=" + SMALLER_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortInventoryAssignsByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);

        // Get all the workEffortInventoryAssignList where quantity is less than DEFAULT_QUANTITY
        defaultWorkEffortInventoryAssignShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the workEffortInventoryAssignList where quantity is less than UPDATED_QUANTITY
        defaultWorkEffortInventoryAssignShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllWorkEffortInventoryAssignsByQuantityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);

        // Get all the workEffortInventoryAssignList where quantity is greater than DEFAULT_QUANTITY
        defaultWorkEffortInventoryAssignShouldNotBeFound("quantity.greaterThan=" + DEFAULT_QUANTITY);

        // Get all the workEffortInventoryAssignList where quantity is greater than SMALLER_QUANTITY
        defaultWorkEffortInventoryAssignShouldBeFound("quantity.greaterThan=" + SMALLER_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllWorkEffortInventoryAssignsByWorkEffortIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);
        WorkEffort workEffort = WorkEffortResourceIT.createEntity(em);
        em.persist(workEffort);
        em.flush();
        workEffortInventoryAssign.setWorkEffort(workEffort);
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);
        Long workEffortId = workEffort.getId();

        // Get all the workEffortInventoryAssignList where workEffort equals to workEffortId
        defaultWorkEffortInventoryAssignShouldBeFound("workEffortId.equals=" + workEffortId);

        // Get all the workEffortInventoryAssignList where workEffort equals to workEffortId + 1
        defaultWorkEffortInventoryAssignShouldNotBeFound("workEffortId.equals=" + (workEffortId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkEffortInventoryAssignsByInventoryItemIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);
        InventoryItem inventoryItem = InventoryItemResourceIT.createEntity(em);
        em.persist(inventoryItem);
        em.flush();
        workEffortInventoryAssign.setInventoryItem(inventoryItem);
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);
        Long inventoryItemId = inventoryItem.getId();

        // Get all the workEffortInventoryAssignList where inventoryItem equals to inventoryItemId
        defaultWorkEffortInventoryAssignShouldBeFound("inventoryItemId.equals=" + inventoryItemId);

        // Get all the workEffortInventoryAssignList where inventoryItem equals to inventoryItemId + 1
        defaultWorkEffortInventoryAssignShouldNotBeFound("inventoryItemId.equals=" + (inventoryItemId + 1));
    }


    @Test
    @Transactional
    public void getAllWorkEffortInventoryAssignsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        workEffortInventoryAssign.setStatus(status);
        workEffortInventoryAssignRepository.saveAndFlush(workEffortInventoryAssign);
        Long statusId = status.getId();

        // Get all the workEffortInventoryAssignList where status equals to statusId
        defaultWorkEffortInventoryAssignShouldBeFound("statusId.equals=" + statusId);

        // Get all the workEffortInventoryAssignList where status equals to statusId + 1
        defaultWorkEffortInventoryAssignShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWorkEffortInventoryAssignShouldBeFound(String filter) throws Exception {
        restWorkEffortInventoryAssignMockMvc.perform(get("/api/work-effort-inventory-assigns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workEffortInventoryAssign.getId().intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())));

        // Check, that the count call also returns 1
        restWorkEffortInventoryAssignMockMvc.perform(get("/api/work-effort-inventory-assigns/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWorkEffortInventoryAssignShouldNotBeFound(String filter) throws Exception {
        restWorkEffortInventoryAssignMockMvc.perform(get("/api/work-effort-inventory-assigns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWorkEffortInventoryAssignMockMvc.perform(get("/api/work-effort-inventory-assigns/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingWorkEffortInventoryAssign() throws Exception {
        // Get the workEffortInventoryAssign
        restWorkEffortInventoryAssignMockMvc.perform(get("/api/work-effort-inventory-assigns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkEffortInventoryAssign() throws Exception {
        // Initialize the database
        workEffortInventoryAssignService.save(workEffortInventoryAssign);

        int databaseSizeBeforeUpdate = workEffortInventoryAssignRepository.findAll().size();

        // Update the workEffortInventoryAssign
        WorkEffortInventoryAssign updatedWorkEffortInventoryAssign = workEffortInventoryAssignRepository.findById(workEffortInventoryAssign.getId()).get();
        // Disconnect from session so that the updates on updatedWorkEffortInventoryAssign are not directly saved in db
        em.detach(updatedWorkEffortInventoryAssign);
        updatedWorkEffortInventoryAssign
            .quantity(UPDATED_QUANTITY);

        restWorkEffortInventoryAssignMockMvc.perform(put("/api/work-effort-inventory-assigns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkEffortInventoryAssign)))
            .andExpect(status().isOk());

        // Validate the WorkEffortInventoryAssign in the database
        List<WorkEffortInventoryAssign> workEffortInventoryAssignList = workEffortInventoryAssignRepository.findAll();
        assertThat(workEffortInventoryAssignList).hasSize(databaseSizeBeforeUpdate);
        WorkEffortInventoryAssign testWorkEffortInventoryAssign = workEffortInventoryAssignList.get(workEffortInventoryAssignList.size() - 1);
        assertThat(testWorkEffortInventoryAssign.getQuantity()).isEqualTo(UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkEffortInventoryAssign() throws Exception {
        int databaseSizeBeforeUpdate = workEffortInventoryAssignRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkEffortInventoryAssignMockMvc.perform(put("/api/work-effort-inventory-assigns")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(workEffortInventoryAssign)))
            .andExpect(status().isBadRequest());

        // Validate the WorkEffortInventoryAssign in the database
        List<WorkEffortInventoryAssign> workEffortInventoryAssignList = workEffortInventoryAssignRepository.findAll();
        assertThat(workEffortInventoryAssignList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkEffortInventoryAssign() throws Exception {
        // Initialize the database
        workEffortInventoryAssignService.save(workEffortInventoryAssign);

        int databaseSizeBeforeDelete = workEffortInventoryAssignRepository.findAll().size();

        // Delete the workEffortInventoryAssign
        restWorkEffortInventoryAssignMockMvc.perform(delete("/api/work-effort-inventory-assigns/{id}", workEffortInventoryAssign.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkEffortInventoryAssign> workEffortInventoryAssignList = workEffortInventoryAssignRepository.findAll();
        assertThat(workEffortInventoryAssignList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
