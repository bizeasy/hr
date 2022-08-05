package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.GeoPoint;
import com.hr.repository.GeoPointRepository;
import com.hr.service.GeoPointService;
import com.hr.service.dto.GeoPointCriteria;
import com.hr.service.GeoPointQueryService;

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
 * Integration tests for the {@link GeoPointResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class GeoPointResourceIT {

    private static final BigDecimal DEFAULT_LATITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LATITUDE = new BigDecimal(2);
    private static final BigDecimal SMALLER_LATITUDE = new BigDecimal(1 - 1);

    private static final BigDecimal DEFAULT_LONGITUDE = new BigDecimal(1);
    private static final BigDecimal UPDATED_LONGITUDE = new BigDecimal(2);
    private static final BigDecimal SMALLER_LONGITUDE = new BigDecimal(1 - 1);

    @Autowired
    private GeoPointRepository geoPointRepository;

    @Autowired
    private GeoPointService geoPointService;

    @Autowired
    private GeoPointQueryService geoPointQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restGeoPointMockMvc;

    private GeoPoint geoPoint;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeoPoint createEntity(EntityManager em) {
        GeoPoint geoPoint = new GeoPoint()
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return geoPoint;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeoPoint createUpdatedEntity(EntityManager em) {
        GeoPoint geoPoint = new GeoPoint()
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        return geoPoint;
    }

    @BeforeEach
    public void initTest() {
        geoPoint = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeoPoint() throws Exception {
        int databaseSizeBeforeCreate = geoPointRepository.findAll().size();
        // Create the GeoPoint
        restGeoPointMockMvc.perform(post("/api/geo-points")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geoPoint)))
            .andExpect(status().isCreated());

        // Validate the GeoPoint in the database
        List<GeoPoint> geoPointList = geoPointRepository.findAll();
        assertThat(geoPointList).hasSize(databaseSizeBeforeCreate + 1);
        GeoPoint testGeoPoint = geoPointList.get(geoPointList.size() - 1);
        assertThat(testGeoPoint.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testGeoPoint.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void createGeoPointWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = geoPointRepository.findAll().size();

        // Create the GeoPoint with an existing ID
        geoPoint.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeoPointMockMvc.perform(post("/api/geo-points")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geoPoint)))
            .andExpect(status().isBadRequest());

        // Validate the GeoPoint in the database
        List<GeoPoint> geoPointList = geoPointRepository.findAll();
        assertThat(geoPointList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllGeoPoints() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList
        restGeoPointMockMvc.perform(get("/api/geo-points?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geoPoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())));
    }
    
    @Test
    @Transactional
    public void getGeoPoint() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get the geoPoint
        restGeoPointMockMvc.perform(get("/api/geo-points/{id}", geoPoint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(geoPoint.getId().intValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.intValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.intValue()));
    }


    @Test
    @Transactional
    public void getGeoPointsByIdFiltering() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        Long id = geoPoint.getId();

        defaultGeoPointShouldBeFound("id.equals=" + id);
        defaultGeoPointShouldNotBeFound("id.notEquals=" + id);

        defaultGeoPointShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultGeoPointShouldNotBeFound("id.greaterThan=" + id);

        defaultGeoPointShouldBeFound("id.lessThanOrEqual=" + id);
        defaultGeoPointShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllGeoPointsByLatitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList where latitude equals to DEFAULT_LATITUDE
        defaultGeoPointShouldBeFound("latitude.equals=" + DEFAULT_LATITUDE);

        // Get all the geoPointList where latitude equals to UPDATED_LATITUDE
        defaultGeoPointShouldNotBeFound("latitude.equals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllGeoPointsByLatitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList where latitude not equals to DEFAULT_LATITUDE
        defaultGeoPointShouldNotBeFound("latitude.notEquals=" + DEFAULT_LATITUDE);

        // Get all the geoPointList where latitude not equals to UPDATED_LATITUDE
        defaultGeoPointShouldBeFound("latitude.notEquals=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllGeoPointsByLatitudeIsInShouldWork() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList where latitude in DEFAULT_LATITUDE or UPDATED_LATITUDE
        defaultGeoPointShouldBeFound("latitude.in=" + DEFAULT_LATITUDE + "," + UPDATED_LATITUDE);

        // Get all the geoPointList where latitude equals to UPDATED_LATITUDE
        defaultGeoPointShouldNotBeFound("latitude.in=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllGeoPointsByLatitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList where latitude is not null
        defaultGeoPointShouldBeFound("latitude.specified=true");

        // Get all the geoPointList where latitude is null
        defaultGeoPointShouldNotBeFound("latitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllGeoPointsByLatitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList where latitude is greater than or equal to DEFAULT_LATITUDE
        defaultGeoPointShouldBeFound("latitude.greaterThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the geoPointList where latitude is greater than or equal to UPDATED_LATITUDE
        defaultGeoPointShouldNotBeFound("latitude.greaterThanOrEqual=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllGeoPointsByLatitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList where latitude is less than or equal to DEFAULT_LATITUDE
        defaultGeoPointShouldBeFound("latitude.lessThanOrEqual=" + DEFAULT_LATITUDE);

        // Get all the geoPointList where latitude is less than or equal to SMALLER_LATITUDE
        defaultGeoPointShouldNotBeFound("latitude.lessThanOrEqual=" + SMALLER_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllGeoPointsByLatitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList where latitude is less than DEFAULT_LATITUDE
        defaultGeoPointShouldNotBeFound("latitude.lessThan=" + DEFAULT_LATITUDE);

        // Get all the geoPointList where latitude is less than UPDATED_LATITUDE
        defaultGeoPointShouldBeFound("latitude.lessThan=" + UPDATED_LATITUDE);
    }

    @Test
    @Transactional
    public void getAllGeoPointsByLatitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList where latitude is greater than DEFAULT_LATITUDE
        defaultGeoPointShouldNotBeFound("latitude.greaterThan=" + DEFAULT_LATITUDE);

        // Get all the geoPointList where latitude is greater than SMALLER_LATITUDE
        defaultGeoPointShouldBeFound("latitude.greaterThan=" + SMALLER_LATITUDE);
    }


    @Test
    @Transactional
    public void getAllGeoPointsByLongitudeIsEqualToSomething() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList where longitude equals to DEFAULT_LONGITUDE
        defaultGeoPointShouldBeFound("longitude.equals=" + DEFAULT_LONGITUDE);

        // Get all the geoPointList where longitude equals to UPDATED_LONGITUDE
        defaultGeoPointShouldNotBeFound("longitude.equals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllGeoPointsByLongitudeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList where longitude not equals to DEFAULT_LONGITUDE
        defaultGeoPointShouldNotBeFound("longitude.notEquals=" + DEFAULT_LONGITUDE);

        // Get all the geoPointList where longitude not equals to UPDATED_LONGITUDE
        defaultGeoPointShouldBeFound("longitude.notEquals=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllGeoPointsByLongitudeIsInShouldWork() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList where longitude in DEFAULT_LONGITUDE or UPDATED_LONGITUDE
        defaultGeoPointShouldBeFound("longitude.in=" + DEFAULT_LONGITUDE + "," + UPDATED_LONGITUDE);

        // Get all the geoPointList where longitude equals to UPDATED_LONGITUDE
        defaultGeoPointShouldNotBeFound("longitude.in=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllGeoPointsByLongitudeIsNullOrNotNull() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList where longitude is not null
        defaultGeoPointShouldBeFound("longitude.specified=true");

        // Get all the geoPointList where longitude is null
        defaultGeoPointShouldNotBeFound("longitude.specified=false");
    }

    @Test
    @Transactional
    public void getAllGeoPointsByLongitudeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList where longitude is greater than or equal to DEFAULT_LONGITUDE
        defaultGeoPointShouldBeFound("longitude.greaterThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the geoPointList where longitude is greater than or equal to UPDATED_LONGITUDE
        defaultGeoPointShouldNotBeFound("longitude.greaterThanOrEqual=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllGeoPointsByLongitudeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList where longitude is less than or equal to DEFAULT_LONGITUDE
        defaultGeoPointShouldBeFound("longitude.lessThanOrEqual=" + DEFAULT_LONGITUDE);

        // Get all the geoPointList where longitude is less than or equal to SMALLER_LONGITUDE
        defaultGeoPointShouldNotBeFound("longitude.lessThanOrEqual=" + SMALLER_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllGeoPointsByLongitudeIsLessThanSomething() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList where longitude is less than DEFAULT_LONGITUDE
        defaultGeoPointShouldNotBeFound("longitude.lessThan=" + DEFAULT_LONGITUDE);

        // Get all the geoPointList where longitude is less than UPDATED_LONGITUDE
        defaultGeoPointShouldBeFound("longitude.lessThan=" + UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void getAllGeoPointsByLongitudeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        geoPointRepository.saveAndFlush(geoPoint);

        // Get all the geoPointList where longitude is greater than DEFAULT_LONGITUDE
        defaultGeoPointShouldNotBeFound("longitude.greaterThan=" + DEFAULT_LONGITUDE);

        // Get all the geoPointList where longitude is greater than SMALLER_LONGITUDE
        defaultGeoPointShouldBeFound("longitude.greaterThan=" + SMALLER_LONGITUDE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultGeoPointShouldBeFound(String filter) throws Exception {
        restGeoPointMockMvc.perform(get("/api/geo-points?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geoPoint.getId().intValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.intValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.intValue())));

        // Check, that the count call also returns 1
        restGeoPointMockMvc.perform(get("/api/geo-points/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultGeoPointShouldNotBeFound(String filter) throws Exception {
        restGeoPointMockMvc.perform(get("/api/geo-points?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restGeoPointMockMvc.perform(get("/api/geo-points/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingGeoPoint() throws Exception {
        // Get the geoPoint
        restGeoPointMockMvc.perform(get("/api/geo-points/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeoPoint() throws Exception {
        // Initialize the database
        geoPointService.save(geoPoint);

        int databaseSizeBeforeUpdate = geoPointRepository.findAll().size();

        // Update the geoPoint
        GeoPoint updatedGeoPoint = geoPointRepository.findById(geoPoint.getId()).get();
        // Disconnect from session so that the updates on updatedGeoPoint are not directly saved in db
        em.detach(updatedGeoPoint);
        updatedGeoPoint
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        restGeoPointMockMvc.perform(put("/api/geo-points")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedGeoPoint)))
            .andExpect(status().isOk());

        // Validate the GeoPoint in the database
        List<GeoPoint> geoPointList = geoPointRepository.findAll();
        assertThat(geoPointList).hasSize(databaseSizeBeforeUpdate);
        GeoPoint testGeoPoint = geoPointList.get(geoPointList.size() - 1);
        assertThat(testGeoPoint.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testGeoPoint.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingGeoPoint() throws Exception {
        int databaseSizeBeforeUpdate = geoPointRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGeoPointMockMvc.perform(put("/api/geo-points")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(geoPoint)))
            .andExpect(status().isBadRequest());

        // Validate the GeoPoint in the database
        List<GeoPoint> geoPointList = geoPointRepository.findAll();
        assertThat(geoPointList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGeoPoint() throws Exception {
        // Initialize the database
        geoPointService.save(geoPoint);

        int databaseSizeBeforeDelete = geoPointRepository.findAll().size();

        // Delete the geoPoint
        restGeoPointMockMvc.perform(delete("/api/geo-points/{id}", geoPoint.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GeoPoint> geoPointList = geoPointRepository.findAll();
        assertThat(geoPointList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
