package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.Geo;
import com.hr.domain.GeoType;
import com.hr.repository.GeoRepository;
import com.hr.service.GeoService;
import com.hr.service.dto.GeoCriteria;
import com.hr.service.GeoQueryService;

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
 * Integration tests for the {@link GeoResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GeoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_ABBREVIATION = "AAAAAAAAAA";
    private static final String UPDATED_ABBREVIATION = "BBBBBBBBBB";

    @Autowired
    private GeoRepository geoRepository;

    @Autowired
    private GeoService geoService;

    @Autowired
    private GeoQueryService geoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeoMockMvc;

    private Geo geo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Geo createEntity(EntityManager em) {
        Geo geo = new Geo()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .abbreviation(DEFAULT_ABBREVIATION);
        return geo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Geo createUpdatedEntity(EntityManager em) {
        Geo geo = new Geo()
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .abbreviation(UPDATED_ABBREVIATION);
        return geo;
    }

    @BeforeEach
    public void initTest() {
        geo = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeo() throws Exception {
        int databaseSizeBeforeCreate = geoRepository.findAll().size();
        // Create the Geo
        restGeoMockMvc.perform(post("/api/geos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geo)))
            .andExpect(status().isCreated());

        // Validate the Geo in the database
        List<Geo> geoList = geoRepository.findAll();
        assertThat(geoList).hasSize(databaseSizeBeforeCreate + 1);
        Geo testGeo = geoList.get(geoList.size() - 1);
        assertThat(testGeo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGeo.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testGeo.getAbbreviation()).isEqualTo(DEFAULT_ABBREVIATION);
    }

    @Test
    @Transactional
    public void createGeoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = geoRepository.findAll().size();

        // Create the Geo with an existing ID
        geo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeoMockMvc.perform(post("/api/geos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geo)))
            .andExpect(status().isBadRequest());

        // Validate the Geo in the database
        List<Geo> geoList = geoRepository.findAll();
        assertThat(geoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGeos() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList
        restGeoMockMvc.perform(get("/api/geos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION)));
    }
    
    @Test
    @Transactional
    public void getGeo() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get the geo
        restGeoMockMvc.perform(get("/api/geos/{id}", geo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(geo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.abbreviation").value(DEFAULT_ABBREVIATION));
    }


    @Test
    @Transactional
    public void getGeosByIdFiltering() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        Long id = geo.getId();

        defaultGeoShouldBeFound("id.equals=" + id);
        defaultGeoShouldNotBeFound("id.notEquals=" + id);

        defaultGeoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGeoShouldNotBeFound("id.greaterThan=" + id);

        defaultGeoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGeoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGeosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where name equals to DEFAULT_NAME
        defaultGeoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the geoList where name equals to UPDATED_NAME
        defaultGeoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGeosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where name not equals to DEFAULT_NAME
        defaultGeoShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the geoList where name not equals to UPDATED_NAME
        defaultGeoShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGeosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultGeoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the geoList where name equals to UPDATED_NAME
        defaultGeoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGeosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where name is not null
        defaultGeoShouldBeFound("name.specified=true");

        // Get all the geoList where name is null
        defaultGeoShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllGeosByNameContainsSomething() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where name contains DEFAULT_NAME
        defaultGeoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the geoList where name contains UPDATED_NAME
        defaultGeoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllGeosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where name does not contain DEFAULT_NAME
        defaultGeoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the geoList where name does not contain UPDATED_NAME
        defaultGeoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllGeosByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where code equals to DEFAULT_CODE
        defaultGeoShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the geoList where code equals to UPDATED_CODE
        defaultGeoShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllGeosByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where code not equals to DEFAULT_CODE
        defaultGeoShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the geoList where code not equals to UPDATED_CODE
        defaultGeoShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllGeosByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where code in DEFAULT_CODE or UPDATED_CODE
        defaultGeoShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the geoList where code equals to UPDATED_CODE
        defaultGeoShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllGeosByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where code is not null
        defaultGeoShouldBeFound("code.specified=true");

        // Get all the geoList where code is null
        defaultGeoShouldNotBeFound("code.specified=false");
    }
                @Test
    @Transactional
    public void getAllGeosByCodeContainsSomething() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where code contains DEFAULT_CODE
        defaultGeoShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the geoList where code contains UPDATED_CODE
        defaultGeoShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    public void getAllGeosByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where code does not contain DEFAULT_CODE
        defaultGeoShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the geoList where code does not contain UPDATED_CODE
        defaultGeoShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }


    @Test
    @Transactional
    public void getAllGeosByAbbreviationIsEqualToSomething() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where abbreviation equals to DEFAULT_ABBREVIATION
        defaultGeoShouldBeFound("abbreviation.equals=" + DEFAULT_ABBREVIATION);

        // Get all the geoList where abbreviation equals to UPDATED_ABBREVIATION
        defaultGeoShouldNotBeFound("abbreviation.equals=" + UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    public void getAllGeosByAbbreviationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where abbreviation not equals to DEFAULT_ABBREVIATION
        defaultGeoShouldNotBeFound("abbreviation.notEquals=" + DEFAULT_ABBREVIATION);

        // Get all the geoList where abbreviation not equals to UPDATED_ABBREVIATION
        defaultGeoShouldBeFound("abbreviation.notEquals=" + UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    public void getAllGeosByAbbreviationIsInShouldWork() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where abbreviation in DEFAULT_ABBREVIATION or UPDATED_ABBREVIATION
        defaultGeoShouldBeFound("abbreviation.in=" + DEFAULT_ABBREVIATION + "," + UPDATED_ABBREVIATION);

        // Get all the geoList where abbreviation equals to UPDATED_ABBREVIATION
        defaultGeoShouldNotBeFound("abbreviation.in=" + UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    public void getAllGeosByAbbreviationIsNullOrNotNull() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where abbreviation is not null
        defaultGeoShouldBeFound("abbreviation.specified=true");

        // Get all the geoList where abbreviation is null
        defaultGeoShouldNotBeFound("abbreviation.specified=false");
    }
                @Test
    @Transactional
    public void getAllGeosByAbbreviationContainsSomething() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where abbreviation contains DEFAULT_ABBREVIATION
        defaultGeoShouldBeFound("abbreviation.contains=" + DEFAULT_ABBREVIATION);

        // Get all the geoList where abbreviation contains UPDATED_ABBREVIATION
        defaultGeoShouldNotBeFound("abbreviation.contains=" + UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    public void getAllGeosByAbbreviationNotContainsSomething() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);

        // Get all the geoList where abbreviation does not contain DEFAULT_ABBREVIATION
        defaultGeoShouldNotBeFound("abbreviation.doesNotContain=" + DEFAULT_ABBREVIATION);

        // Get all the geoList where abbreviation does not contain UPDATED_ABBREVIATION
        defaultGeoShouldBeFound("abbreviation.doesNotContain=" + UPDATED_ABBREVIATION);
    }


    @Test
    @Transactional
    public void getAllGeosByGeoTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        geoRepository.saveAndFlush(geo);
        GeoType geoType = GeoTypeResourceIT.createEntity(em);
        em.persist(geoType);
        em.flush();
        geo.setGeoType(geoType);
        geoRepository.saveAndFlush(geo);
        Long geoTypeId = geoType.getId();

        // Get all the geoList where geoType equals to geoTypeId
        defaultGeoShouldBeFound("geoTypeId.equals=" + geoTypeId);

        // Get all the geoList where geoType equals to geoTypeId + 1
        defaultGeoShouldNotBeFound("geoTypeId.equals=" + (geoTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGeoShouldBeFound(String filter) throws Exception {
        restGeoMockMvc.perform(get("/api/geos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].abbreviation").value(hasItem(DEFAULT_ABBREVIATION)));

        // Check, that the count call also returns 1
        restGeoMockMvc.perform(get("/api/geos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGeoShouldNotBeFound(String filter) throws Exception {
        restGeoMockMvc.perform(get("/api/geos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGeoMockMvc.perform(get("/api/geos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGeo() throws Exception {
        // Get the geo
        restGeoMockMvc.perform(get("/api/geos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeo() throws Exception {
        // Initialize the database
        geoService.save(geo);

        int databaseSizeBeforeUpdate = geoRepository.findAll().size();

        // Update the geo
        Geo updatedGeo = geoRepository.findById(geo.getId()).get();
        // Disconnect from session so that the updates on updatedGeo are not directly saved in db
        em.detach(updatedGeo);
        updatedGeo
            .name(UPDATED_NAME)
            .code(UPDATED_CODE)
            .abbreviation(UPDATED_ABBREVIATION);

        restGeoMockMvc.perform(put("/api/geos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGeo)))
            .andExpect(status().isOk());

        // Validate the Geo in the database
        List<Geo> geoList = geoRepository.findAll();
        assertThat(geoList).hasSize(databaseSizeBeforeUpdate);
        Geo testGeo = geoList.get(geoList.size() - 1);
        assertThat(testGeo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGeo.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testGeo.getAbbreviation()).isEqualTo(UPDATED_ABBREVIATION);
    }

    @Test
    @Transactional
    public void updateNonExistingGeo() throws Exception {
        int databaseSizeBeforeUpdate = geoRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeoMockMvc.perform(put("/api/geos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geo)))
            .andExpect(status().isBadRequest());

        // Validate the Geo in the database
        List<Geo> geoList = geoRepository.findAll();
        assertThat(geoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGeo() throws Exception {
        // Initialize the database
        geoService.save(geo);

        int databaseSizeBeforeDelete = geoRepository.findAll().size();

        // Delete the geo
        restGeoMockMvc.perform(delete("/api/geos/{id}", geo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Geo> geoList = geoRepository.findAll();
        assertThat(geoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
