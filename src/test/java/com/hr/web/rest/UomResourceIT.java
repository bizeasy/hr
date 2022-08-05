package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.Uom;
import com.hr.domain.UomType;
import com.hr.repository.UomRepository;
import com.hr.service.UomService;
import com.hr.service.dto.UomCriteria;
import com.hr.service.UomQueryService;

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
 * Integration tests for the {@link UomResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UomResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;
    private static final Integer SMALLER_SEQUENCE_NO = 1 - 1;

    private static final String DEFAULT_ABBREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ABBREVIATION = "BBBBBBBBBB";

    @Autowired
    private UomRepository uomRepository;

    @Autowired
    private UomService uomService;

    @Autowired
    private UomQueryService uomQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUomMockMvc;

    private Uom uom;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uom createEntity(EntityManager em) {
        Uom uom = new Uom()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .sequenceNo(DEFAULT_SEQUENCE_NO)
            .abbreviation(DEFAULT_ABBREVIATION);
        return uom;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uom createUpdatedEntity(EntityManager em) {
        Uom uom = new Uom()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .abbreviation(UPDATED_ABBREVIATION);
        return uom;
    }

    @BeforeEach
    public void initTest() {
        uom = createEntity(em);
    }

    @Test
    @Transactional
    public void createUom() throws Exception {
        int databaseSizeBeforeCreate = uomRepository.findAll().size();
        // Create the Uom
        restUomMockMvc.perform(post("/api/uoms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uom)))
            .andExpect(status().isCreated());

        // Validate the Uom in the database
        List<Uom> uomList = uomRepository.findAll();
        assertThat(uomList).hasSize(databaseSizeBeforeCreate + 1);
        Uom testUom = uomList.get(uomList.size() - 1);
        assertThat(testUom.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUom.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUom.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
        assertThat(testUom.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);
    }

    @Test
    @Transactional
    public void createUomWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uomRepository.findAll().size();

        // Create the Uom with an existing ID
        uom.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUomMockMvc.perform(post("/api/uoms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uom)))
            .andExpect(status().isBadRequest());

        // Validate the Uom in the database
        List<Uom> uomList = uomRepository.findAll();
        assertThat(uomList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUoms() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList
        restUomMockMvc.perform(get("/api/uoms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uom.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION)));
    }
    
    @Test
    @Transactional
    public void getUom() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get the uom
        restUomMockMvc.perform(get("/api/uoms/{id}", uom.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uom.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO))
            .andExpect(jsonPath("$.abbreviation").value(DEFAULT_ABBREVIATION));
    }


    @Test
    @Transactional
    public void getUomsByIdFiltering() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        Long id = uom.getId();

        defaultUomShouldBeFound("id.equals=" + id);
        defaultUomShouldNotBeFound("id.notEquals=" + id);

        defaultUomShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUomShouldNotBeFound("id.greaterThan=" + id);

        defaultUomShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUomShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUomsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where name equals to DEFAULT_NAME
        defaultUomShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the uomList where name equals to UPDATED_NAME
        defaultUomShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUomsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where name not equals to DEFAULT_NAME
        defaultUomShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the uomList where name not equals to UPDATED_NAME
        defaultUomShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUomsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUomShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the uomList where name equals to UPDATED_NAME
        defaultUomShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUomsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where name is not null
        defaultUomShouldBeFound("name.specified=true");

        // Get all the uomList where name is null
        defaultUomShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllUomsByNameContainsSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where name contains DEFAULT_NAME
        defaultUomShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the uomList where name contains UPDATED_NAME
        defaultUomShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUomsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where name does not contain DEFAULT_NAME
        defaultUomShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the uomList where name does not contain UPDATED_NAME
        defaultUomShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllUomsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where description equals to DEFAULT_DESCRIPTION
        defaultUomShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the uomList where description equals to UPDATED_DESCRIPTION
        defaultUomShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllUomsByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where description not equals to DEFAULT_DESCRIPTION
        defaultUomShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the uomList where description not equals to UPDATED_DESCRIPTION
        defaultUomShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllUomsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultUomShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the uomList where description equals to UPDATED_DESCRIPTION
        defaultUomShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllUomsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where description is not null
        defaultUomShouldBeFound("description.specified=true");

        // Get all the uomList where description is null
        defaultUomShouldNotBeFound("description.specified=false");
    }
                @Test
    @Transactional
    public void getAllUomsByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where description contains DEFAULT_DESCRIPTION
        defaultUomShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the uomList where description contains UPDATED_DESCRIPTION
        defaultUomShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllUomsByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where description does not contain DEFAULT_DESCRIPTION
        defaultUomShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the uomList where description does not contain UPDATED_DESCRIPTION
        defaultUomShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }


    @Test
    @Transactional
    public void getAllUomsBySequenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where sequenceNo equals to DEFAULT_SEQUENCE_NO
        defaultUomShouldBeFound("sequenceNo.equals=" + DEFAULT_SEQUENCE_NO);

        // Get all the uomList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultUomShouldNotBeFound("sequenceNo.equals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllUomsBySequenceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where sequenceNo not equals to DEFAULT_SEQUENCE_NO
        defaultUomShouldNotBeFound("sequenceNo.notEquals=" + DEFAULT_SEQUENCE_NO);

        // Get all the uomList where sequenceNo not equals to UPDATED_SEQUENCE_NO
        defaultUomShouldBeFound("sequenceNo.notEquals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllUomsBySequenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where sequenceNo in DEFAULT_SEQUENCE_NO or UPDATED_SEQUENCE_NO
        defaultUomShouldBeFound("sequenceNo.in=" + DEFAULT_SEQUENCE_NO + "," + UPDATED_SEQUENCE_NO);

        // Get all the uomList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultUomShouldNotBeFound("sequenceNo.in=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllUomsBySequenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where sequenceNo is not null
        defaultUomShouldBeFound("sequenceNo.specified=true");

        // Get all the uomList where sequenceNo is null
        defaultUomShouldNotBeFound("sequenceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllUomsBySequenceNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where sequenceNo is greater than or equal to DEFAULT_SEQUENCE_NO
        defaultUomShouldBeFound("sequenceNo.greaterThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the uomList where sequenceNo is greater than or equal to UPDATED_SEQUENCE_NO
        defaultUomShouldNotBeFound("sequenceNo.greaterThanOrEqual=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllUomsBySequenceNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where sequenceNo is less than or equal to DEFAULT_SEQUENCE_NO
        defaultUomShouldBeFound("sequenceNo.lessThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the uomList where sequenceNo is less than or equal to SMALLER_SEQUENCE_NO
        defaultUomShouldNotBeFound("sequenceNo.lessThanOrEqual=" + SMALLER_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllUomsBySequenceNoIsLessThanSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where sequenceNo is less than DEFAULT_SEQUENCE_NO
        defaultUomShouldNotBeFound("sequenceNo.lessThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the uomList where sequenceNo is less than UPDATED_SEQUENCE_NO
        defaultUomShouldBeFound("sequenceNo.lessThan=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllUomsBySequenceNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where sequenceNo is greater than DEFAULT_SEQUENCE_NO
        defaultUomShouldNotBeFound("sequenceNo.greaterThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the uomList where sequenceNo is greater than SMALLER_SEQUENCE_NO
        defaultUomShouldBeFound("sequenceNo.greaterThan=" + SMALLER_SEQUENCE_NO);
    }


    @Test
    @Transactional
    public void getAllUomsByAbbreviationIsEqualToSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where abbreviation equals to DEFAULT_ABBREVIATION
        defaultUomShouldBeFound("abbreviation.equals=" + DEFAULT_ABBREVIATION);

        // Get all the uomList where abbreviation equals to UPDATED_ABBREVIATION
        defaultUomShouldNotBeFound("abbreviation.equals=" + UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    public void getAllUomsByAbbreviationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where abbreviation not equals to DEFAULT_ABBREVIATION
        defaultUomShouldNotBeFound("abbreviation.notEquals=" + DEFAULT_ABBREVIATION);

        // Get all the uomList where abbreviation not equals to UPDATED_ABBREVIATION
        defaultUomShouldBeFound("abbreviation.notEquals=" + UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    public void getAllUomsByAbbreviationIsInShouldWork() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where abbreviation in DEFAULT_ABBREVIATION or UPDATED_ABBREVIATION
        defaultUomShouldBeFound("abbreviation.in=" + DEFAULT_ABBREVIATION + "," + UPDATED_ABBREVIATION);

        // Get all the uomList where abbreviation equals to UPDATED_ABBREVIATION
        defaultUomShouldNotBeFound("abbreviation.in=" + UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    public void getAllUomsByAbbreviationIsNullOrNotNull() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where abbreviation is not null
        defaultUomShouldBeFound("abbreviation.specified=true");

        // Get all the uomList where abbreviation is null
        defaultUomShouldNotBeFound("abbreviation.specified=false");
    }
                @Test
    @Transactional
    public void getAllUomsByAbbreviationContainsSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where abbreviation contains DEFAULT_ABBREVIATION
        defaultUomShouldBeFound("abbreviation.contains=" + DEFAULT_ABBREVIATION);

        // Get all the uomList where abbreviation contains UPDATED_ABBREVIATION
        defaultUomShouldNotBeFound("abbreviation.contains=" + UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    public void getAllUomsByAbbreviationNotContainsSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);

        // Get all the uomList where abbreviation does not contain DEFAULT_ABBREVIATION
        defaultUomShouldNotBeFound("abbreviation.doesNotContain=" + DEFAULT_ABBREVIATION);

        // Get all the uomList where abbreviation does not contain UPDATED_ABBREVIATION
        defaultUomShouldBeFound("abbreviation.doesNotContain=" + UPDATED_ABBREVIATION);
    }


    @Test
    @Transactional
    public void getAllUomsByUomTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        uomRepository.saveAndFlush(uom);
        UomType uomType = UomTypeResourceIT.createEntity(em);
        em.persist(uomType);
        em.flush();
        uom.setUomType(uomType);
        uomRepository.saveAndFlush(uom);
        Long uomTypeId = uomType.getId();

        // Get all the uomList where uomType equals to uomTypeId
        defaultUomShouldBeFound("uomTypeId.equals=" + uomTypeId);

        // Get all the uomList where uomType equals to uomTypeId + 1
        defaultUomShouldNotBeFound("uomTypeId.equals=" + (uomTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUomShouldBeFound(String filter) throws Exception {
        restUomMockMvc.perform(get("/api/uoms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uom.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)))
            .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION)));

        // Check, that the count call also returns 1
        restUomMockMvc.perform(get("/api/uoms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUomShouldNotBeFound(String filter) throws Exception {
        restUomMockMvc.perform(get("/api/uoms?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUomMockMvc.perform(get("/api/uoms/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUom() throws Exception {
        // Get the uom
        restUomMockMvc.perform(get("/api/uoms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUom() throws Exception {
        // Initialize the database
        uomService.save(uom);

        int databaseSizeBeforeUpdate = uomRepository.findAll().size();

        // Update the uom
        Uom updatedUom = uomRepository.findById(uom.getId()).get();
        // Disconnect from session so that the updates on updatedUom are not directly saved in db
        em.detach(updatedUom);
        updatedUom
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .sequenceNo(UPDATED_SEQUENCE_NO)
            .abbreviation(UPDATED_ABBREVIATION);

        restUomMockMvc.perform(put("/api/uoms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUom)))
            .andExpect(status().isOk());

        // Validate the Uom in the database
        List<Uom> uomList = uomRepository.findAll();
        assertThat(uomList).hasSize(databaseSizeBeforeUpdate);
        Uom testUom = uomList.get(uomList.size() - 1);
        assertThat(testUom.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUom.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUom.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
        assertThat(testUom.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    public void updateNonExistingUom() throws Exception {
        int databaseSizeBeforeUpdate = uomRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUomMockMvc.perform(put("/api/uoms")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uom)))
            .andExpect(status().isBadRequest());

        // Validate the Uom in the database
        List<Uom> uomList = uomRepository.findAll();
        assertThat(uomList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUom() throws Exception {
        // Initialize the database
        uomService.save(uom);

        int databaseSizeBeforeDelete = uomRepository.findAll().size();

        // Delete the uom
        restUomMockMvc.perform(delete("/api/uoms/{id}", uom.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Uom> uomList = uomRepository.findAll();
        assertThat(uomList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
