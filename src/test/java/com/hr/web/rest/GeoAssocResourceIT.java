package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.GeoAssoc;
import com.hr.domain.Geo;
import com.hr.domain.GeoAssocType;
import com.hr.repository.GeoAssocRepository;
import com.hr.service.GeoAssocService;
import com.hr.service.dto.GeoAssocCriteria;
import com.hr.service.GeoAssocQueryService;

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
 * Integration tests for the {@link GeoAssocResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GeoAssocResourceIT {

    @Autowired
    private GeoAssocRepository geoAssocRepository;

    @Autowired
    private GeoAssocService geoAssocService;

    @Autowired
    private GeoAssocQueryService geoAssocQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeoAssocMockMvc;

    private GeoAssoc geoAssoc;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeoAssoc createEntity(EntityManager em) {
        GeoAssoc geoAssoc = new GeoAssoc();
        return geoAssoc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeoAssoc createUpdatedEntity(EntityManager em) {
        GeoAssoc geoAssoc = new GeoAssoc();
        return geoAssoc;
    }

    @BeforeEach
    public void initTest() {
        geoAssoc = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeoAssoc() throws Exception {
        int databaseSizeBeforeCreate = geoAssocRepository.findAll().size();
        // Create the GeoAssoc
        restGeoAssocMockMvc.perform(post("/api/geo-assocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geoAssoc)))
            .andExpect(status().isCreated());

        // Validate the GeoAssoc in the database
        List<GeoAssoc> geoAssocList = geoAssocRepository.findAll();
        assertThat(geoAssocList).hasSize(databaseSizeBeforeCreate + 1);
        GeoAssoc testGeoAssoc = geoAssocList.get(geoAssocList.size() - 1);
    }

    @Test
    @Transactional
    public void createGeoAssocWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = geoAssocRepository.findAll().size();

        // Create the GeoAssoc with an existing ID
        geoAssoc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeoAssocMockMvc.perform(post("/api/geo-assocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geoAssoc)))
            .andExpect(status().isBadRequest());

        // Validate the GeoAssoc in the database
        List<GeoAssoc> geoAssocList = geoAssocRepository.findAll();
        assertThat(geoAssocList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGeoAssocs() throws Exception {
        // Initialize the database
        geoAssocRepository.saveAndFlush(geoAssoc);

        // Get all the geoAssocList
        restGeoAssocMockMvc.perform(get("/api/geo-assocs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geoAssoc.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getGeoAssoc() throws Exception {
        // Initialize the database
        geoAssocRepository.saveAndFlush(geoAssoc);

        // Get the geoAssoc
        restGeoAssocMockMvc.perform(get("/api/geo-assocs/{id}", geoAssoc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(geoAssoc.getId().intValue()));
    }


    @Test
    @Transactional
    public void getGeoAssocsByIdFiltering() throws Exception {
        // Initialize the database
        geoAssocRepository.saveAndFlush(geoAssoc);

        Long id = geoAssoc.getId();

        defaultGeoAssocShouldBeFound("id.equals=" + id);
        defaultGeoAssocShouldNotBeFound("id.notEquals=" + id);

        defaultGeoAssocShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGeoAssocShouldNotBeFound("id.greaterThan=" + id);

        defaultGeoAssocShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGeoAssocShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGeoAssocsByGeoIsEqualToSomething() throws Exception {
        // Initialize the database
        geoAssocRepository.saveAndFlush(geoAssoc);
        Geo geo = GeoResourceIT.createEntity(em);
        em.persist(geo);
        em.flush();
        geoAssoc.setGeo(geo);
        geoAssocRepository.saveAndFlush(geoAssoc);
        Long geoId = geo.getId();

        // Get all the geoAssocList where geo equals to geoId
        defaultGeoAssocShouldBeFound("geoId.equals=" + geoId);

        // Get all the geoAssocList where geo equals to geoId + 1
        defaultGeoAssocShouldNotBeFound("geoId.equals=" + (geoId + 1));
    }


    @Test
    @Transactional
    public void getAllGeoAssocsByGeoToIsEqualToSomething() throws Exception {
        // Initialize the database
        geoAssocRepository.saveAndFlush(geoAssoc);
        Geo geoTo = GeoResourceIT.createEntity(em);
        em.persist(geoTo);
        em.flush();
        geoAssoc.setGeoTo(geoTo);
        geoAssocRepository.saveAndFlush(geoAssoc);
        Long geoToId = geoTo.getId();

        // Get all the geoAssocList where geoTo equals to geoToId
        defaultGeoAssocShouldBeFound("geoToId.equals=" + geoToId);

        // Get all the geoAssocList where geoTo equals to geoToId + 1
        defaultGeoAssocShouldNotBeFound("geoToId.equals=" + (geoToId + 1));
    }


    @Test
    @Transactional
    public void getAllGeoAssocsByGeoAssocTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        geoAssocRepository.saveAndFlush(geoAssoc);
        GeoAssocType geoAssocType = GeoAssocTypeResourceIT.createEntity(em);
        em.persist(geoAssocType);
        em.flush();
        geoAssoc.setGeoAssocType(geoAssocType);
        geoAssocRepository.saveAndFlush(geoAssoc);
        Long geoAssocTypeId = geoAssocType.getId();

        // Get all the geoAssocList where geoAssocType equals to geoAssocTypeId
        defaultGeoAssocShouldBeFound("geoAssocTypeId.equals=" + geoAssocTypeId);

        // Get all the geoAssocList where geoAssocType equals to geoAssocTypeId + 1
        defaultGeoAssocShouldNotBeFound("geoAssocTypeId.equals=" + (geoAssocTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGeoAssocShouldBeFound(String filter) throws Exception {
        restGeoAssocMockMvc.perform(get("/api/geo-assocs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geoAssoc.getId().intValue())));

        // Check, that the count call also returns 1
        restGeoAssocMockMvc.perform(get("/api/geo-assocs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGeoAssocShouldNotBeFound(String filter) throws Exception {
        restGeoAssocMockMvc.perform(get("/api/geo-assocs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGeoAssocMockMvc.perform(get("/api/geo-assocs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGeoAssoc() throws Exception {
        // Get the geoAssoc
        restGeoAssocMockMvc.perform(get("/api/geo-assocs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeoAssoc() throws Exception {
        // Initialize the database
        geoAssocService.save(geoAssoc);

        int databaseSizeBeforeUpdate = geoAssocRepository.findAll().size();

        // Update the geoAssoc
        GeoAssoc updatedGeoAssoc = geoAssocRepository.findById(geoAssoc.getId()).get();
        // Disconnect from session so that the updates on updatedGeoAssoc are not directly saved in db
        em.detach(updatedGeoAssoc);

        restGeoAssocMockMvc.perform(put("/api/geo-assocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGeoAssoc)))
            .andExpect(status().isOk());

        // Validate the GeoAssoc in the database
        List<GeoAssoc> geoAssocList = geoAssocRepository.findAll();
        assertThat(geoAssocList).hasSize(databaseSizeBeforeUpdate);
        GeoAssoc testGeoAssoc = geoAssocList.get(geoAssocList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingGeoAssoc() throws Exception {
        int databaseSizeBeforeUpdate = geoAssocRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeoAssocMockMvc.perform(put("/api/geo-assocs")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geoAssoc)))
            .andExpect(status().isBadRequest());

        // Validate the GeoAssoc in the database
        List<GeoAssoc> geoAssocList = geoAssocRepository.findAll();
        assertThat(geoAssocList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGeoAssoc() throws Exception {
        // Initialize the database
        geoAssocService.save(geoAssoc);

        int databaseSizeBeforeDelete = geoAssocRepository.findAll().size();

        // Delete the geoAssoc
        restGeoAssocMockMvc.perform(delete("/api/geo-assocs/{id}", geoAssoc.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GeoAssoc> geoAssocList = geoAssocRepository.findAll();
        assertThat(geoAssocList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
