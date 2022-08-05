package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.Facility;
import com.hr.domain.FacilityType;
import com.hr.domain.Facility;
import com.hr.domain.Party;
import com.hr.domain.FacilityGroup;
import com.hr.domain.Uom;
import com.hr.domain.GeoPoint;
import com.hr.domain.InventoryItemType;
import com.hr.domain.Status;
import com.hr.domain.User;
import com.hr.repository.FacilityRepository;
import com.hr.service.FacilityService;
import com.hr.service.dto.FacilityCriteria;
import com.hr.service.FacilityQueryService;

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
 * Integration tests for the {@link FacilityResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class FacilityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FACILITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_FACILITY_CODE = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_FACILITY_SIZE = new BigDecimal(1);
    private static final BigDecimal UPDATED_FACILITY_SIZE = new BigDecimal(2);
    private static final BigDecimal SMALLER_FACILITY_SIZE = new BigDecimal(1 - 1);

    private static final String DEFAULT_COST_CENTER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COST_CENTER_CODE = "BBBBBBBBBB";

    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private FacilityQueryService facilityQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFacilityMockMvc;

    private Facility facility;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facility createEntity(EntityManager em) {
        Facility facility = new Facility()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .facilityCode(DEFAULT_FACILITY_CODE)
            .facilitySize(DEFAULT_FACILITY_SIZE)
            .costCenterCode(DEFAULT_COST_CENTER_CODE);
        return facility;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Facility createUpdatedEntity(EntityManager em) {
        Facility facility = new Facility()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .facilityCode(UPDATED_FACILITY_CODE)
            .facilitySize(UPDATED_FACILITY_SIZE)
            .costCenterCode(UPDATED_COST_CENTER_CODE);
        return facility;
    }

    @BeforeEach
    public void initTest() {
        facility = createEntity(em);
    }

    @Test
    @Transactional
    public void createFacility() throws Exception {
        int databaseSizeBeforeCreate = facilityRepository.findAll().size();
        // Create the Facility
        restFacilityMockMvc.perform(post("/api/facilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facility)))
            .andExpect(status().isCreated());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeCreate + 1);
        Facility testFacility = facilityList.get(facilityList.size() - 1);
        assertThat(testFacility.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testFacility.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFacility.getFacilityCode()).isEqualTo(DEFAULT_FACILITY_CODE);
        assertThat(testFacility.getFacilitySize()).isEqualTo(DEFAULT_FACILITY_SIZE);
        assertThat(testFacility.getCostCenterCode()).isEqualTo(DEFAULT_COST_CENTER_CODE);
    }

    @Test
    @Transactional
    public void createFacilityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = facilityRepository.findAll().size();

        // Create the Facility with an existing ID
        facility.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFacilityMockMvc.perform(post("/api/facilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facility)))
            .andExpect(status().isBadRequest());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFacilities() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList
        restFacilityMockMvc.perform(get("/api/facilities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facility.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].facilityCode").value(hasItem(DEFAULT_FACILITY_CODE)))
            .andExpect(jsonPath("$.[*].facilitySize").value(hasItem(DEFAULT_FACILITY_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].costCenterCode").value(hasItem(DEFAULT_COST_CENTER_CODE)));
    }
    
    @Test
    @Transactional
    public void getFacility() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get the facility
        restFacilityMockMvc.perform(get("/api/facilities/{id}", facility.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(facility.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.facilityCode").value(DEFAULT_FACILITY_CODE))
            .andExpect(jsonPath("$.facilitySize").value(DEFAULT_FACILITY_SIZE.intValue()))
            .andExpect(jsonPath("$.costCenterCode").value(DEFAULT_COST_CENTER_CODE));
    }


    @Test
    @Transactional
    public void getFacilitiesByIdFiltering() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        Long id = facility.getId();

        defaultFacilityShouldBeFound("id.equals=" + id);
        defaultFacilityShouldNotBeFound("id.notEquals=" + id);

        defaultFacilityShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultFacilityShouldNotBeFound("id.greaterThan=" + id);

        defaultFacilityShouldBeFound("id.lessThanOrEqual=" + id);
        defaultFacilityShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllFacilitiesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where name equals to DEFAULT_NAME
        defaultFacilityShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the facilityList where name equals to UPDATED_NAME
        defaultFacilityShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where name not equals to DEFAULT_NAME
        defaultFacilityShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the facilityList where name not equals to UPDATED_NAME
        defaultFacilityShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where name in DEFAULT_NAME or UPDATED_NAME
        defaultFacilityShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the facilityList where name equals to UPDATED_NAME
        defaultFacilityShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where name is not null
        defaultFacilityShouldBeFound("name.specified=true");

        // Get all the facilityList where name is null
        defaultFacilityShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllFacilitiesByNameContainsSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where name contains DEFAULT_NAME
        defaultFacilityShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the facilityList where name contains UPDATED_NAME
        defaultFacilityShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where name does not contain DEFAULT_NAME
        defaultFacilityShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the facilityList where name does not contain UPDATED_NAME
        defaultFacilityShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllFacilitiesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where description equals to DEFAULT_DESCRIPTION
        defaultFacilityShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the facilityList where description equals to UPDATED_DESCRIPTION
        defaultFacilityShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where description not equals to DEFAULT_DESCRIPTION
        defaultFacilityShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the facilityList where description not equals to UPDATED_DESCRIPTION
        defaultFacilityShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultFacilityShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the facilityList where description equals to UPDATED_DESCRIPTION
        defaultFacilityShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where description is not null
        defaultFacilityShouldBeFound("description.specified=true");

        // Get all the facilityList where description is null
        defaultFacilityShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllFacilitiesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where description contains DEFAULT_DESCRIPTION
        defaultFacilityShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the facilityList where description contains UPDATED_DESCRIPTION
        defaultFacilityShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where description does not contain DEFAULT_DESCRIPTION
        defaultFacilityShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the facilityList where description does not contain UPDATED_DESCRIPTION
        defaultFacilityShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllFacilitiesByFacilityCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where facilityCode equals to DEFAULT_FACILITY_CODE
        defaultFacilityShouldBeFound("facilityCode.equals=" + DEFAULT_FACILITY_CODE);

        // Get all the facilityList where facilityCode equals to UPDATED_FACILITY_CODE
        defaultFacilityShouldNotBeFound("facilityCode.equals=" + UPDATED_FACILITY_CODE);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByFacilityCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where facilityCode not equals to DEFAULT_FACILITY_CODE
        defaultFacilityShouldNotBeFound("facilityCode.notEquals=" + DEFAULT_FACILITY_CODE);

        // Get all the facilityList where facilityCode not equals to UPDATED_FACILITY_CODE
        defaultFacilityShouldBeFound("facilityCode.notEquals=" + UPDATED_FACILITY_CODE);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByFacilityCodeIsInShouldWork() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where facilityCode in DEFAULT_FACILITY_CODE or UPDATED_FACILITY_CODE
        defaultFacilityShouldBeFound("facilityCode.in=" + DEFAULT_FACILITY_CODE + "," + UPDATED_FACILITY_CODE);

        // Get all the facilityList where facilityCode equals to UPDATED_FACILITY_CODE
        defaultFacilityShouldNotBeFound("facilityCode.in=" + UPDATED_FACILITY_CODE);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByFacilityCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where facilityCode is not null
        defaultFacilityShouldBeFound("facilityCode.specified=true");

        // Get all the facilityList where facilityCode is null
        defaultFacilityShouldNotBeFound("facilityCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllFacilitiesByFacilityCodeContainsSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where facilityCode contains DEFAULT_FACILITY_CODE
        defaultFacilityShouldBeFound("facilityCode.contains=" + DEFAULT_FACILITY_CODE);

        // Get all the facilityList where facilityCode contains UPDATED_FACILITY_CODE
        defaultFacilityShouldNotBeFound("facilityCode.contains=" + UPDATED_FACILITY_CODE);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByFacilityCodeNotContainsSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where facilityCode does not contain DEFAULT_FACILITY_CODE
        defaultFacilityShouldNotBeFound("facilityCode.doesNotContain=" + DEFAULT_FACILITY_CODE);

        // Get all the facilityList where facilityCode does not contain UPDATED_FACILITY_CODE
        defaultFacilityShouldBeFound("facilityCode.doesNotContain=" + UPDATED_FACILITY_CODE);
    }


    @Test
    @Transactional
    public void getAllFacilitiesByFacilitySizeIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where facilitySize equals to DEFAULT_FACILITY_SIZE
        defaultFacilityShouldBeFound("facilitySize.equals=" + DEFAULT_FACILITY_SIZE);

        // Get all the facilityList where facilitySize equals to UPDATED_FACILITY_SIZE
        defaultFacilityShouldNotBeFound("facilitySize.equals=" + UPDATED_FACILITY_SIZE);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByFacilitySizeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where facilitySize not equals to DEFAULT_FACILITY_SIZE
        defaultFacilityShouldNotBeFound("facilitySize.notEquals=" + DEFAULT_FACILITY_SIZE);

        // Get all the facilityList where facilitySize not equals to UPDATED_FACILITY_SIZE
        defaultFacilityShouldBeFound("facilitySize.notEquals=" + UPDATED_FACILITY_SIZE);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByFacilitySizeIsInShouldWork() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where facilitySize in DEFAULT_FACILITY_SIZE or UPDATED_FACILITY_SIZE
        defaultFacilityShouldBeFound("facilitySize.in=" + DEFAULT_FACILITY_SIZE + "," + UPDATED_FACILITY_SIZE);

        // Get all the facilityList where facilitySize equals to UPDATED_FACILITY_SIZE
        defaultFacilityShouldNotBeFound("facilitySize.in=" + UPDATED_FACILITY_SIZE);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByFacilitySizeIsNullOrNotNull() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where facilitySize is not null
        defaultFacilityShouldBeFound("facilitySize.specified=true");

        // Get all the facilityList where facilitySize is null
        defaultFacilityShouldNotBeFound("facilitySize.specified=false");
    }

    @Test
    @Transactional
    public void getAllFacilitiesByFacilitySizeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where facilitySize is greater than or equal to DEFAULT_FACILITY_SIZE
        defaultFacilityShouldBeFound("facilitySize.greaterThanOrEqual=" + DEFAULT_FACILITY_SIZE);

        // Get all the facilityList where facilitySize is greater than or equal to UPDATED_FACILITY_SIZE
        defaultFacilityShouldNotBeFound("facilitySize.greaterThanOrEqual=" + UPDATED_FACILITY_SIZE);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByFacilitySizeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where facilitySize is less than or equal to DEFAULT_FACILITY_SIZE
        defaultFacilityShouldBeFound("facilitySize.lessThanOrEqual=" + DEFAULT_FACILITY_SIZE);

        // Get all the facilityList where facilitySize is less than or equal to SMALLER_FACILITY_SIZE
        defaultFacilityShouldNotBeFound("facilitySize.lessThanOrEqual=" + SMALLER_FACILITY_SIZE);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByFacilitySizeIsLessThanSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where facilitySize is less than DEFAULT_FACILITY_SIZE
        defaultFacilityShouldNotBeFound("facilitySize.lessThan=" + DEFAULT_FACILITY_SIZE);

        // Get all the facilityList where facilitySize is less than UPDATED_FACILITY_SIZE
        defaultFacilityShouldBeFound("facilitySize.lessThan=" + UPDATED_FACILITY_SIZE);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByFacilitySizeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where facilitySize is greater than DEFAULT_FACILITY_SIZE
        defaultFacilityShouldNotBeFound("facilitySize.greaterThan=" + DEFAULT_FACILITY_SIZE);

        // Get all the facilityList where facilitySize is greater than SMALLER_FACILITY_SIZE
        defaultFacilityShouldBeFound("facilitySize.greaterThan=" + SMALLER_FACILITY_SIZE);
    }


    @Test
    @Transactional
    public void getAllFacilitiesByCostCenterCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where costCenterCode equals to DEFAULT_COST_CENTER_CODE
        defaultFacilityShouldBeFound("costCenterCode.equals=" + DEFAULT_COST_CENTER_CODE);

        // Get all the facilityList where costCenterCode equals to UPDATED_COST_CENTER_CODE
        defaultFacilityShouldNotBeFound("costCenterCode.equals=" + UPDATED_COST_CENTER_CODE);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByCostCenterCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where costCenterCode not equals to DEFAULT_COST_CENTER_CODE
        defaultFacilityShouldNotBeFound("costCenterCode.notEquals=" + DEFAULT_COST_CENTER_CODE);

        // Get all the facilityList where costCenterCode not equals to UPDATED_COST_CENTER_CODE
        defaultFacilityShouldBeFound("costCenterCode.notEquals=" + UPDATED_COST_CENTER_CODE);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByCostCenterCodeIsInShouldWork() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where costCenterCode in DEFAULT_COST_CENTER_CODE or UPDATED_COST_CENTER_CODE
        defaultFacilityShouldBeFound("costCenterCode.in=" + DEFAULT_COST_CENTER_CODE + "," + UPDATED_COST_CENTER_CODE);

        // Get all the facilityList where costCenterCode equals to UPDATED_COST_CENTER_CODE
        defaultFacilityShouldNotBeFound("costCenterCode.in=" + UPDATED_COST_CENTER_CODE);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByCostCenterCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where costCenterCode is not null
        defaultFacilityShouldBeFound("costCenterCode.specified=true");

        // Get all the facilityList where costCenterCode is null
        defaultFacilityShouldNotBeFound("costCenterCode.specified=false");
    }
                @Test
    @Transactional
    public void getAllFacilitiesByCostCenterCodeContainsSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where costCenterCode contains DEFAULT_COST_CENTER_CODE
        defaultFacilityShouldBeFound("costCenterCode.contains=" + DEFAULT_COST_CENTER_CODE);

        // Get all the facilityList where costCenterCode contains UPDATED_COST_CENTER_CODE
        defaultFacilityShouldNotBeFound("costCenterCode.contains=" + UPDATED_COST_CENTER_CODE);
    }

    @Test
    @Transactional
    public void getAllFacilitiesByCostCenterCodeNotContainsSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);

        // Get all the facilityList where costCenterCode does not contain DEFAULT_COST_CENTER_CODE
        defaultFacilityShouldNotBeFound("costCenterCode.doesNotContain=" + DEFAULT_COST_CENTER_CODE);

        // Get all the facilityList where costCenterCode does not contain UPDATED_COST_CENTER_CODE
        defaultFacilityShouldBeFound("costCenterCode.doesNotContain=" + UPDATED_COST_CENTER_CODE);
    }


    @Test
    @Transactional
    public void getAllFacilitiesByFacilityTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);
        FacilityType facilityType = FacilityTypeResourceIT.createEntity(em);
        em.persist(facilityType);
        em.flush();
        facility.setFacilityType(facilityType);
        facilityRepository.saveAndFlush(facility);
        Long facilityTypeId = facilityType.getId();

        // Get all the facilityList where facilityType equals to facilityTypeId
        defaultFacilityShouldBeFound("facilityTypeId.equals=" + facilityTypeId);

        // Get all the facilityList where facilityType equals to facilityTypeId + 1
        defaultFacilityShouldNotBeFound("facilityTypeId.equals=" + (facilityTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllFacilitiesByParentFacilityIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);
        Facility parentFacility = FacilityResourceIT.createEntity(em);
        em.persist(parentFacility);
        em.flush();
        facility.setParentFacility(parentFacility);
        facilityRepository.saveAndFlush(facility);
        Long parentFacilityId = parentFacility.getId();

        // Get all the facilityList where parentFacility equals to parentFacilityId
        defaultFacilityShouldBeFound("parentFacilityId.equals=" + parentFacilityId);

        // Get all the facilityList where parentFacility equals to parentFacilityId + 1
        defaultFacilityShouldNotBeFound("parentFacilityId.equals=" + (parentFacilityId + 1));
    }


    @Test
    @Transactional
    public void getAllFacilitiesByOwnerPartyIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);
        Party ownerParty = PartyResourceIT.createEntity(em);
        em.persist(ownerParty);
        em.flush();
        facility.setOwnerParty(ownerParty);
        facilityRepository.saveAndFlush(facility);
        Long ownerPartyId = ownerParty.getId();

        // Get all the facilityList where ownerParty equals to ownerPartyId
        defaultFacilityShouldBeFound("ownerPartyId.equals=" + ownerPartyId);

        // Get all the facilityList where ownerParty equals to ownerPartyId + 1
        defaultFacilityShouldNotBeFound("ownerPartyId.equals=" + (ownerPartyId + 1));
    }


    @Test
    @Transactional
    public void getAllFacilitiesByFacilityGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);
        FacilityGroup facilityGroup = FacilityGroupResourceIT.createEntity(em);
        em.persist(facilityGroup);
        em.flush();
        facility.setFacilityGroup(facilityGroup);
        facilityRepository.saveAndFlush(facility);
        Long facilityGroupId = facilityGroup.getId();

        // Get all the facilityList where facilityGroup equals to facilityGroupId
        defaultFacilityShouldBeFound("facilityGroupId.equals=" + facilityGroupId);

        // Get all the facilityList where facilityGroup equals to facilityGroupId + 1
        defaultFacilityShouldNotBeFound("facilityGroupId.equals=" + (facilityGroupId + 1));
    }


    @Test
    @Transactional
    public void getAllFacilitiesBySizeUomIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);
        Uom sizeUom = UomResourceIT.createEntity(em);
        em.persist(sizeUom);
        em.flush();
        facility.setSizeUom(sizeUom);
        facilityRepository.saveAndFlush(facility);
        Long sizeUomId = sizeUom.getId();

        // Get all the facilityList where sizeUom equals to sizeUomId
        defaultFacilityShouldBeFound("sizeUomId.equals=" + sizeUomId);

        // Get all the facilityList where sizeUom equals to sizeUomId + 1
        defaultFacilityShouldNotBeFound("sizeUomId.equals=" + (sizeUomId + 1));
    }


    @Test
    @Transactional
    public void getAllFacilitiesByGeoPointIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);
        GeoPoint geoPoint = GeoPointResourceIT.createEntity(em);
        em.persist(geoPoint);
        em.flush();
        facility.setGeoPoint(geoPoint);
        facilityRepository.saveAndFlush(facility);
        Long geoPointId = geoPoint.getId();

        // Get all the facilityList where geoPoint equals to geoPointId
        defaultFacilityShouldBeFound("geoPointId.equals=" + geoPointId);

        // Get all the facilityList where geoPoint equals to geoPointId + 1
        defaultFacilityShouldNotBeFound("geoPointId.equals=" + (geoPointId + 1));
    }


    @Test
    @Transactional
    public void getAllFacilitiesByInventoryItemTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);
        InventoryItemType inventoryItemType = InventoryItemTypeResourceIT.createEntity(em);
        em.persist(inventoryItemType);
        em.flush();
        facility.setInventoryItemType(inventoryItemType);
        facilityRepository.saveAndFlush(facility);
        Long inventoryItemTypeId = inventoryItemType.getId();

        // Get all the facilityList where inventoryItemType equals to inventoryItemTypeId
        defaultFacilityShouldBeFound("inventoryItemTypeId.equals=" + inventoryItemTypeId);

        // Get all the facilityList where inventoryItemType equals to inventoryItemTypeId + 1
        defaultFacilityShouldNotBeFound("inventoryItemTypeId.equals=" + (inventoryItemTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllFacilitiesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        facility.setStatus(status);
        facilityRepository.saveAndFlush(facility);
        Long statusId = status.getId();

        // Get all the facilityList where status equals to statusId
        defaultFacilityShouldBeFound("statusId.equals=" + statusId);

        // Get all the facilityList where status equals to statusId + 1
        defaultFacilityShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }


    @Test
    @Transactional
    public void getAllFacilitiesByUsageStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);
        Status usageStatus = StatusResourceIT.createEntity(em);
        em.persist(usageStatus);
        em.flush();
        facility.setUsageStatus(usageStatus);
        facilityRepository.saveAndFlush(facility);
        Long usageStatusId = usageStatus.getId();

        // Get all the facilityList where usageStatus equals to usageStatusId
        defaultFacilityShouldBeFound("usageStatusId.equals=" + usageStatusId);

        // Get all the facilityList where usageStatus equals to usageStatusId + 1
        defaultFacilityShouldNotBeFound("usageStatusId.equals=" + (usageStatusId + 1));
    }


    @Test
    @Transactional
    public void getAllFacilitiesByReviewedByIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);
        User reviewedBy = UserResourceIT.createEntity(em);
        em.persist(reviewedBy);
        em.flush();
        facility.setReviewedBy(reviewedBy);
        facilityRepository.saveAndFlush(facility);
        Long reviewedById = reviewedBy.getId();

        // Get all the facilityList where reviewedBy equals to reviewedById
        defaultFacilityShouldBeFound("reviewedById.equals=" + reviewedById);

        // Get all the facilityList where reviewedBy equals to reviewedById + 1
        defaultFacilityShouldNotBeFound("reviewedById.equals=" + (reviewedById + 1));
    }


    @Test
    @Transactional
    public void getAllFacilitiesByApprovedByIsEqualToSomething() throws Exception {
        // Initialize the database
        facilityRepository.saveAndFlush(facility);
        User approvedBy = UserResourceIT.createEntity(em);
        em.persist(approvedBy);
        em.flush();
        facility.setApprovedBy(approvedBy);
        facilityRepository.saveAndFlush(facility);
        Long approvedById = approvedBy.getId();

        // Get all the facilityList where approvedBy equals to approvedById
        defaultFacilityShouldBeFound("approvedById.equals=" + approvedById);

        // Get all the facilityList where approvedBy equals to approvedById + 1
        defaultFacilityShouldNotBeFound("approvedById.equals=" + (approvedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFacilityShouldBeFound(String filter) throws Exception {
        restFacilityMockMvc.perform(get("/api/facilities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(facility.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].facilityCode").value(hasItem(DEFAULT_FACILITY_CODE)))
            .andExpect(jsonPath("$.[*].facilitySize").value(hasItem(DEFAULT_FACILITY_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].costCenterCode").value(hasItem(DEFAULT_COST_CENTER_CODE)));

        // Check, that the count call also returns 1
        restFacilityMockMvc.perform(get("/api/facilities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFacilityShouldNotBeFound(String filter) throws Exception {
        restFacilityMockMvc.perform(get("/api/facilities?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFacilityMockMvc.perform(get("/api/facilities/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingFacility() throws Exception {
        // Get the facility
        restFacilityMockMvc.perform(get("/api/facilities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFacility() throws Exception {
        // Initialize the database
        facilityService.save(facility);

        int databaseSizeBeforeUpdate = facilityRepository.findAll().size();

        // Update the facility
        Facility updatedFacility = facilityRepository.findById(facility.getId()).get();
        // Disconnect from session so that the updates on updatedFacility are not directly saved in db
        em.detach(updatedFacility);
        updatedFacility
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .facilityCode(UPDATED_FACILITY_CODE)
            .facilitySize(UPDATED_FACILITY_SIZE)
            .costCenterCode(UPDATED_COST_CENTER_CODE);

        restFacilityMockMvc.perform(put("/api/facilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFacility)))
            .andExpect(status().isOk());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeUpdate);
        Facility testFacility = facilityList.get(facilityList.size() - 1);
        assertThat(testFacility.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testFacility.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFacility.getFacilityCode()).isEqualTo(UPDATED_FACILITY_CODE);
        assertThat(testFacility.getFacilitySize()).isEqualTo(UPDATED_FACILITY_SIZE);
        assertThat(testFacility.getCostCenterCode()).isEqualTo(UPDATED_COST_CENTER_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingFacility() throws Exception {
        int databaseSizeBeforeUpdate = facilityRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFacilityMockMvc.perform(put("/api/facilities")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(facility)))
            .andExpect(status().isBadRequest());

        // Validate the Facility in the database
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFacility() throws Exception {
        // Initialize the database
        facilityService.save(facility);

        int databaseSizeBeforeDelete = facilityRepository.findAll().size();

        // Delete the facility
        restFacilityMockMvc.perform(delete("/api/facilities/{id}", facility.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Facility> facilityList = facilityRepository.findAll();
        assertThat(facilityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
