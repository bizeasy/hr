package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.InventoryItemVariance;
import com.hr.domain.InventoryItem;
import com.hr.domain.Reason;
import com.hr.repository.InventoryItemVarianceRepository;
import com.hr.service.InventoryItemVarianceService;
import com.hr.service.dto.InventoryItemVarianceCriteria;
import com.hr.service.InventoryItemVarianceQueryService;

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
 * Integration tests for the {@link InventoryItemVarianceResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class InventoryItemVarianceResourceIT {

    private static final String DEFAULT_VARIANCE_REASON = "AAAAAAAAAA";
    private static final String UPDATED_VARIANCE_REASON = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ATP_VAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_ATP_VAR = new BigDecimal(2);
    private static final BigDecimal SMALLER_ATP_VAR = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_QOH_VAR = new BigDecimal(1);
    private static final BigDecimal UPDATED_QOH_VAR = new BigDecimal(2);
    private static final BigDecimal SMALLER_QOH_VAR = new BigDecimal(1 - 1);

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    @Autowired
    private InventoryItemVarianceRepository inventoryItemVarianceRepository;

    @Autowired
    private InventoryItemVarianceService inventoryItemVarianceService;

    @Autowired
    private InventoryItemVarianceQueryService inventoryItemVarianceQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInventoryItemVarianceMockMvc;

    private InventoryItemVariance inventoryItemVariance;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryItemVariance createEntity(EntityManager em) {
        InventoryItemVariance inventoryItemVariance = new InventoryItemVariance()
            .varianceReason(DEFAULT_VARIANCE_REASON)
            .atpVar(DEFAULT_ATP_VAR)
            .qohVar(DEFAULT_QOH_VAR)
            .comments(DEFAULT_COMMENTS);
        return inventoryItemVariance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static InventoryItemVariance createUpdatedEntity(EntityManager em) {
        InventoryItemVariance inventoryItemVariance = new InventoryItemVariance()
            .varianceReason(UPDATED_VARIANCE_REASON)
            .atpVar(UPDATED_ATP_VAR)
            .qohVar(UPDATED_QOH_VAR)
            .comments(UPDATED_COMMENTS);
        return inventoryItemVariance;
    }

    @BeforeEach
    public void initTest() {
        inventoryItemVariance = createEntity(em);
    }

    @Test
    @Transactional
    public void createInventoryItemVariance() throws Exception {
        int databaseSizeBeforeCreate = inventoryItemVarianceRepository.findAll().size();
        // Create the InventoryItemVariance
        restInventoryItemVarianceMockMvc.perform(post("/api/inventory-item-variances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryItemVariance)))
            .andExpect(status().isCreated());

        // Validate the InventoryItemVariance in the database
        List<InventoryItemVariance> inventoryItemVarianceList = inventoryItemVarianceRepository.findAll();
        assertThat(inventoryItemVarianceList).hasSize(databaseSizeBeforeCreate + 1);
        InventoryItemVariance testInventoryItemVariance = inventoryItemVarianceList.get(inventoryItemVarianceList.size() - 1);
        assertThat(testInventoryItemVariance.getVarianceReason()).isEqualTo(DEFAULT_VARIANCE_REASON);
        assertThat(testInventoryItemVariance.getAtpVar()).isEqualTo(DEFAULT_ATP_VAR);
        assertThat(testInventoryItemVariance.getQohVar()).isEqualTo(DEFAULT_QOH_VAR);
        assertThat(testInventoryItemVariance.getComments()).isEqualTo(DEFAULT_COMMENTS);
    }

    @Test
    @Transactional
    public void createInventoryItemVarianceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inventoryItemVarianceRepository.findAll().size();

        // Create the InventoryItemVariance with an existing ID
        inventoryItemVariance.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInventoryItemVarianceMockMvc.perform(post("/api/inventory-item-variances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryItemVariance)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryItemVariance in the database
        List<InventoryItemVariance> inventoryItemVarianceList = inventoryItemVarianceRepository.findAll();
        assertThat(inventoryItemVarianceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInventoryItemVariances() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList
        restInventoryItemVarianceMockMvc.perform(get("/api/inventory-item-variances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryItemVariance.getId().intValue())))
            .andExpect(jsonPath("$.[*].varianceReason").value(hasItem(DEFAULT_VARIANCE_REASON)))
            .andExpect(jsonPath("$.[*].atpVar").value(hasItem(DEFAULT_ATP_VAR.intValue())))
            .andExpect(jsonPath("$.[*].qohVar").value(hasItem(DEFAULT_QOH_VAR.intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));
    }
    
    @Test
    @Transactional
    public void getInventoryItemVariance() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get the inventoryItemVariance
        restInventoryItemVarianceMockMvc.perform(get("/api/inventory-item-variances/{id}", inventoryItemVariance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(inventoryItemVariance.getId().intValue()))
            .andExpect(jsonPath("$.varianceReason").value(DEFAULT_VARIANCE_REASON))
            .andExpect(jsonPath("$.atpVar").value(DEFAULT_ATP_VAR.intValue()))
            .andExpect(jsonPath("$.qohVar").value(DEFAULT_QOH_VAR.intValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS));
    }


    @Test
    @Transactional
    public void getInventoryItemVariancesByIdFiltering() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        Long id = inventoryItemVariance.getId();

        defaultInventoryItemVarianceShouldBeFound("id.equals=" + id);
        defaultInventoryItemVarianceShouldNotBeFound("id.notEquals=" + id);

        defaultInventoryItemVarianceShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultInventoryItemVarianceShouldNotBeFound("id.greaterThan=" + id);

        defaultInventoryItemVarianceShouldBeFound("id.lessThanOrEqual=" + id);
        defaultInventoryItemVarianceShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllInventoryItemVariancesByVarianceReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where varianceReason equals to DEFAULT_VARIANCE_REASON
        defaultInventoryItemVarianceShouldBeFound("varianceReason.equals=" + DEFAULT_VARIANCE_REASON);

        // Get all the inventoryItemVarianceList where varianceReason equals to UPDATED_VARIANCE_REASON
        defaultInventoryItemVarianceShouldNotBeFound("varianceReason.equals=" + UPDATED_VARIANCE_REASON);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByVarianceReasonIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where varianceReason not equals to DEFAULT_VARIANCE_REASON
        defaultInventoryItemVarianceShouldNotBeFound("varianceReason.notEquals=" + DEFAULT_VARIANCE_REASON);

        // Get all the inventoryItemVarianceList where varianceReason not equals to UPDATED_VARIANCE_REASON
        defaultInventoryItemVarianceShouldBeFound("varianceReason.notEquals=" + UPDATED_VARIANCE_REASON);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByVarianceReasonIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where varianceReason in DEFAULT_VARIANCE_REASON or UPDATED_VARIANCE_REASON
        defaultInventoryItemVarianceShouldBeFound("varianceReason.in=" + DEFAULT_VARIANCE_REASON + "," + UPDATED_VARIANCE_REASON);

        // Get all the inventoryItemVarianceList where varianceReason equals to UPDATED_VARIANCE_REASON
        defaultInventoryItemVarianceShouldNotBeFound("varianceReason.in=" + UPDATED_VARIANCE_REASON);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByVarianceReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where varianceReason is not null
        defaultInventoryItemVarianceShouldBeFound("varianceReason.specified=true");

        // Get all the inventoryItemVarianceList where varianceReason is null
        defaultInventoryItemVarianceShouldNotBeFound("varianceReason.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoryItemVariancesByVarianceReasonContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where varianceReason contains DEFAULT_VARIANCE_REASON
        defaultInventoryItemVarianceShouldBeFound("varianceReason.contains=" + DEFAULT_VARIANCE_REASON);

        // Get all the inventoryItemVarianceList where varianceReason contains UPDATED_VARIANCE_REASON
        defaultInventoryItemVarianceShouldNotBeFound("varianceReason.contains=" + UPDATED_VARIANCE_REASON);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByVarianceReasonNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where varianceReason does not contain DEFAULT_VARIANCE_REASON
        defaultInventoryItemVarianceShouldNotBeFound("varianceReason.doesNotContain=" + DEFAULT_VARIANCE_REASON);

        // Get all the inventoryItemVarianceList where varianceReason does not contain UPDATED_VARIANCE_REASON
        defaultInventoryItemVarianceShouldBeFound("varianceReason.doesNotContain=" + UPDATED_VARIANCE_REASON);
    }


    @Test
    @Transactional
    public void getAllInventoryItemVariancesByAtpVarIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where atpVar equals to DEFAULT_ATP_VAR
        defaultInventoryItemVarianceShouldBeFound("atpVar.equals=" + DEFAULT_ATP_VAR);

        // Get all the inventoryItemVarianceList where atpVar equals to UPDATED_ATP_VAR
        defaultInventoryItemVarianceShouldNotBeFound("atpVar.equals=" + UPDATED_ATP_VAR);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByAtpVarIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where atpVar not equals to DEFAULT_ATP_VAR
        defaultInventoryItemVarianceShouldNotBeFound("atpVar.notEquals=" + DEFAULT_ATP_VAR);

        // Get all the inventoryItemVarianceList where atpVar not equals to UPDATED_ATP_VAR
        defaultInventoryItemVarianceShouldBeFound("atpVar.notEquals=" + UPDATED_ATP_VAR);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByAtpVarIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where atpVar in DEFAULT_ATP_VAR or UPDATED_ATP_VAR
        defaultInventoryItemVarianceShouldBeFound("atpVar.in=" + DEFAULT_ATP_VAR + "," + UPDATED_ATP_VAR);

        // Get all the inventoryItemVarianceList where atpVar equals to UPDATED_ATP_VAR
        defaultInventoryItemVarianceShouldNotBeFound("atpVar.in=" + UPDATED_ATP_VAR);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByAtpVarIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where atpVar is not null
        defaultInventoryItemVarianceShouldBeFound("atpVar.specified=true");

        // Get all the inventoryItemVarianceList where atpVar is null
        defaultInventoryItemVarianceShouldNotBeFound("atpVar.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByAtpVarIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where atpVar is greater than or equal to DEFAULT_ATP_VAR
        defaultInventoryItemVarianceShouldBeFound("atpVar.greaterThanOrEqual=" + DEFAULT_ATP_VAR);

        // Get all the inventoryItemVarianceList where atpVar is greater than or equal to UPDATED_ATP_VAR
        defaultInventoryItemVarianceShouldNotBeFound("atpVar.greaterThanOrEqual=" + UPDATED_ATP_VAR);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByAtpVarIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where atpVar is less than or equal to DEFAULT_ATP_VAR
        defaultInventoryItemVarianceShouldBeFound("atpVar.lessThanOrEqual=" + DEFAULT_ATP_VAR);

        // Get all the inventoryItemVarianceList where atpVar is less than or equal to SMALLER_ATP_VAR
        defaultInventoryItemVarianceShouldNotBeFound("atpVar.lessThanOrEqual=" + SMALLER_ATP_VAR);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByAtpVarIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where atpVar is less than DEFAULT_ATP_VAR
        defaultInventoryItemVarianceShouldNotBeFound("atpVar.lessThan=" + DEFAULT_ATP_VAR);

        // Get all the inventoryItemVarianceList where atpVar is less than UPDATED_ATP_VAR
        defaultInventoryItemVarianceShouldBeFound("atpVar.lessThan=" + UPDATED_ATP_VAR);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByAtpVarIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where atpVar is greater than DEFAULT_ATP_VAR
        defaultInventoryItemVarianceShouldNotBeFound("atpVar.greaterThan=" + DEFAULT_ATP_VAR);

        // Get all the inventoryItemVarianceList where atpVar is greater than SMALLER_ATP_VAR
        defaultInventoryItemVarianceShouldBeFound("atpVar.greaterThan=" + SMALLER_ATP_VAR);
    }


    @Test
    @Transactional
    public void getAllInventoryItemVariancesByQohVarIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where qohVar equals to DEFAULT_QOH_VAR
        defaultInventoryItemVarianceShouldBeFound("qohVar.equals=" + DEFAULT_QOH_VAR);

        // Get all the inventoryItemVarianceList where qohVar equals to UPDATED_QOH_VAR
        defaultInventoryItemVarianceShouldNotBeFound("qohVar.equals=" + UPDATED_QOH_VAR);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByQohVarIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where qohVar not equals to DEFAULT_QOH_VAR
        defaultInventoryItemVarianceShouldNotBeFound("qohVar.notEquals=" + DEFAULT_QOH_VAR);

        // Get all the inventoryItemVarianceList where qohVar not equals to UPDATED_QOH_VAR
        defaultInventoryItemVarianceShouldBeFound("qohVar.notEquals=" + UPDATED_QOH_VAR);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByQohVarIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where qohVar in DEFAULT_QOH_VAR or UPDATED_QOH_VAR
        defaultInventoryItemVarianceShouldBeFound("qohVar.in=" + DEFAULT_QOH_VAR + "," + UPDATED_QOH_VAR);

        // Get all the inventoryItemVarianceList where qohVar equals to UPDATED_QOH_VAR
        defaultInventoryItemVarianceShouldNotBeFound("qohVar.in=" + UPDATED_QOH_VAR);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByQohVarIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where qohVar is not null
        defaultInventoryItemVarianceShouldBeFound("qohVar.specified=true");

        // Get all the inventoryItemVarianceList where qohVar is null
        defaultInventoryItemVarianceShouldNotBeFound("qohVar.specified=false");
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByQohVarIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where qohVar is greater than or equal to DEFAULT_QOH_VAR
        defaultInventoryItemVarianceShouldBeFound("qohVar.greaterThanOrEqual=" + DEFAULT_QOH_VAR);

        // Get all the inventoryItemVarianceList where qohVar is greater than or equal to UPDATED_QOH_VAR
        defaultInventoryItemVarianceShouldNotBeFound("qohVar.greaterThanOrEqual=" + UPDATED_QOH_VAR);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByQohVarIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where qohVar is less than or equal to DEFAULT_QOH_VAR
        defaultInventoryItemVarianceShouldBeFound("qohVar.lessThanOrEqual=" + DEFAULT_QOH_VAR);

        // Get all the inventoryItemVarianceList where qohVar is less than or equal to SMALLER_QOH_VAR
        defaultInventoryItemVarianceShouldNotBeFound("qohVar.lessThanOrEqual=" + SMALLER_QOH_VAR);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByQohVarIsLessThanSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where qohVar is less than DEFAULT_QOH_VAR
        defaultInventoryItemVarianceShouldNotBeFound("qohVar.lessThan=" + DEFAULT_QOH_VAR);

        // Get all the inventoryItemVarianceList where qohVar is less than UPDATED_QOH_VAR
        defaultInventoryItemVarianceShouldBeFound("qohVar.lessThan=" + UPDATED_QOH_VAR);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByQohVarIsGreaterThanSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where qohVar is greater than DEFAULT_QOH_VAR
        defaultInventoryItemVarianceShouldNotBeFound("qohVar.greaterThan=" + DEFAULT_QOH_VAR);

        // Get all the inventoryItemVarianceList where qohVar is greater than SMALLER_QOH_VAR
        defaultInventoryItemVarianceShouldBeFound("qohVar.greaterThan=" + SMALLER_QOH_VAR);
    }


    @Test
    @Transactional
    public void getAllInventoryItemVariancesByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where comments equals to DEFAULT_COMMENTS
        defaultInventoryItemVarianceShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the inventoryItemVarianceList where comments equals to UPDATED_COMMENTS
        defaultInventoryItemVarianceShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where comments not equals to DEFAULT_COMMENTS
        defaultInventoryItemVarianceShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the inventoryItemVarianceList where comments not equals to UPDATED_COMMENTS
        defaultInventoryItemVarianceShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultInventoryItemVarianceShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the inventoryItemVarianceList where comments equals to UPDATED_COMMENTS
        defaultInventoryItemVarianceShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where comments is not null
        defaultInventoryItemVarianceShouldBeFound("comments.specified=true");

        // Get all the inventoryItemVarianceList where comments is null
        defaultInventoryItemVarianceShouldNotBeFound("comments.specified=false");
    }
                @Test
    @Transactional
    public void getAllInventoryItemVariancesByCommentsContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where comments contains DEFAULT_COMMENTS
        defaultInventoryItemVarianceShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the inventoryItemVarianceList where comments contains UPDATED_COMMENTS
        defaultInventoryItemVarianceShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllInventoryItemVariancesByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);

        // Get all the inventoryItemVarianceList where comments does not contain DEFAULT_COMMENTS
        defaultInventoryItemVarianceShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the inventoryItemVarianceList where comments does not contain UPDATED_COMMENTS
        defaultInventoryItemVarianceShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllInventoryItemVariancesByInventoryItemIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);
        InventoryItem inventoryItem = InventoryItemResourceIT.createEntity(em);
        em.persist(inventoryItem);
        em.flush();
        inventoryItemVariance.setInventoryItem(inventoryItem);
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);
        Long inventoryItemId = inventoryItem.getId();

        // Get all the inventoryItemVarianceList where inventoryItem equals to inventoryItemId
        defaultInventoryItemVarianceShouldBeFound("inventoryItemId.equals=" + inventoryItemId);

        // Get all the inventoryItemVarianceList where inventoryItem equals to inventoryItemId + 1
        defaultInventoryItemVarianceShouldNotBeFound("inventoryItemId.equals=" + (inventoryItemId + 1));
    }


    @Test
    @Transactional
    public void getAllInventoryItemVariancesByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);
        Reason reason = ReasonResourceIT.createEntity(em);
        em.persist(reason);
        em.flush();
        inventoryItemVariance.setReason(reason);
        inventoryItemVarianceRepository.saveAndFlush(inventoryItemVariance);
        Long reasonId = reason.getId();

        // Get all the inventoryItemVarianceList where reason equals to reasonId
        defaultInventoryItemVarianceShouldBeFound("reasonId.equals=" + reasonId);

        // Get all the inventoryItemVarianceList where reason equals to reasonId + 1
        defaultInventoryItemVarianceShouldNotBeFound("reasonId.equals=" + (reasonId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultInventoryItemVarianceShouldBeFound(String filter) throws Exception {
        restInventoryItemVarianceMockMvc.perform(get("/api/inventory-item-variances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inventoryItemVariance.getId().intValue())))
            .andExpect(jsonPath("$.[*].varianceReason").value(hasItem(DEFAULT_VARIANCE_REASON)))
            .andExpect(jsonPath("$.[*].atpVar").value(hasItem(DEFAULT_ATP_VAR.intValue())))
            .andExpect(jsonPath("$.[*].qohVar").value(hasItem(DEFAULT_QOH_VAR.intValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)));

        // Check, that the count call also returns 1
        restInventoryItemVarianceMockMvc.perform(get("/api/inventory-item-variances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultInventoryItemVarianceShouldNotBeFound(String filter) throws Exception {
        restInventoryItemVarianceMockMvc.perform(get("/api/inventory-item-variances?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restInventoryItemVarianceMockMvc.perform(get("/api/inventory-item-variances/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingInventoryItemVariance() throws Exception {
        // Get the inventoryItemVariance
        restInventoryItemVarianceMockMvc.perform(get("/api/inventory-item-variances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInventoryItemVariance() throws Exception {
        // Initialize the database
        inventoryItemVarianceService.save(inventoryItemVariance);

        int databaseSizeBeforeUpdate = inventoryItemVarianceRepository.findAll().size();

        // Update the inventoryItemVariance
        InventoryItemVariance updatedInventoryItemVariance = inventoryItemVarianceRepository.findById(inventoryItemVariance.getId()).get();
        // Disconnect from session so that the updates on updatedInventoryItemVariance are not directly saved in db
        em.detach(updatedInventoryItemVariance);
        updatedInventoryItemVariance
            .varianceReason(UPDATED_VARIANCE_REASON)
            .atpVar(UPDATED_ATP_VAR)
            .qohVar(UPDATED_QOH_VAR)
            .comments(UPDATED_COMMENTS);

        restInventoryItemVarianceMockMvc.perform(put("/api/inventory-item-variances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedInventoryItemVariance)))
            .andExpect(status().isOk());

        // Validate the InventoryItemVariance in the database
        List<InventoryItemVariance> inventoryItemVarianceList = inventoryItemVarianceRepository.findAll();
        assertThat(inventoryItemVarianceList).hasSize(databaseSizeBeforeUpdate);
        InventoryItemVariance testInventoryItemVariance = inventoryItemVarianceList.get(inventoryItemVarianceList.size() - 1);
        assertThat(testInventoryItemVariance.getVarianceReason()).isEqualTo(UPDATED_VARIANCE_REASON);
        assertThat(testInventoryItemVariance.getAtpVar()).isEqualTo(UPDATED_ATP_VAR);
        assertThat(testInventoryItemVariance.getQohVar()).isEqualTo(UPDATED_QOH_VAR);
        assertThat(testInventoryItemVariance.getComments()).isEqualTo(UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void updateNonExistingInventoryItemVariance() throws Exception {
        int databaseSizeBeforeUpdate = inventoryItemVarianceRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInventoryItemVarianceMockMvc.perform(put("/api/inventory-item-variances")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(inventoryItemVariance)))
            .andExpect(status().isBadRequest());

        // Validate the InventoryItemVariance in the database
        List<InventoryItemVariance> inventoryItemVarianceList = inventoryItemVarianceRepository.findAll();
        assertThat(inventoryItemVarianceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInventoryItemVariance() throws Exception {
        // Initialize the database
        inventoryItemVarianceService.save(inventoryItemVariance);

        int databaseSizeBeforeDelete = inventoryItemVarianceRepository.findAll().size();

        // Delete the inventoryItemVariance
        restInventoryItemVarianceMockMvc.perform(delete("/api/inventory-item-variances/{id}", inventoryItemVariance.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<InventoryItemVariance> inventoryItemVarianceList = inventoryItemVarianceRepository.findAll();
        assertThat(inventoryItemVarianceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
