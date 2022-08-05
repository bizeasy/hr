package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.SalesChannel;
import com.hr.repository.SalesChannelRepository;

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
 * Integration tests for the {@link SalesChannelResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class SalesChannelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;

    @Autowired
    private SalesChannelRepository salesChannelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSalesChannelMockMvc;

    private SalesChannel salesChannel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesChannel createEntity(EntityManager em) {
        SalesChannel salesChannel = new SalesChannel()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .sequenceNo(DEFAULT_SEQUENCE_NO);
        return salesChannel;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SalesChannel createUpdatedEntity(EntityManager em) {
        SalesChannel salesChannel = new SalesChannel()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .sequenceNo(UPDATED_SEQUENCE_NO);
        return salesChannel;
    }

    @BeforeEach
    public void initTest() {
        salesChannel = createEntity(em);
    }

    @Test
    @Transactional
    public void createSalesChannel() throws Exception {
        int databaseSizeBeforeCreate = salesChannelRepository.findAll().size();
        // Create the SalesChannel
        restSalesChannelMockMvc.perform(post("/api/sales-channels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesChannel)))
            .andExpect(status().isCreated());

        // Validate the SalesChannel in the database
        List<SalesChannel> salesChannelList = salesChannelRepository.findAll();
        assertThat(salesChannelList).hasSize(databaseSizeBeforeCreate + 1);
        SalesChannel testSalesChannel = salesChannelList.get(salesChannelList.size() - 1);
        assertThat(testSalesChannel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSalesChannel.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testSalesChannel.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void createSalesChannelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = salesChannelRepository.findAll().size();

        // Create the SalesChannel with an existing ID
        salesChannel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSalesChannelMockMvc.perform(post("/api/sales-channels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesChannel)))
            .andExpect(status().isBadRequest());

        // Validate the SalesChannel in the database
        List<SalesChannel> salesChannelList = salesChannelRepository.findAll();
        assertThat(salesChannelList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSalesChannels() throws Exception {
        // Initialize the database
        salesChannelRepository.saveAndFlush(salesChannel);

        // Get all the salesChannelList
        restSalesChannelMockMvc.perform(get("/api/sales-channels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(salesChannel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)));
    }
    
    @Test
    @Transactional
    public void getSalesChannel() throws Exception {
        // Initialize the database
        salesChannelRepository.saveAndFlush(salesChannel);

        // Get the salesChannel
        restSalesChannelMockMvc.perform(get("/api/sales-channels/{id}", salesChannel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(salesChannel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO));
    }
    @Test
    @Transactional
    public void getNonExistingSalesChannel() throws Exception {
        // Get the salesChannel
        restSalesChannelMockMvc.perform(get("/api/sales-channels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSalesChannel() throws Exception {
        // Initialize the database
        salesChannelRepository.saveAndFlush(salesChannel);

        int databaseSizeBeforeUpdate = salesChannelRepository.findAll().size();

        // Update the salesChannel
        SalesChannel updatedSalesChannel = salesChannelRepository.findById(salesChannel.getId()).get();
        // Disconnect from session so that the updates on updatedSalesChannel are not directly saved in db
        em.detach(updatedSalesChannel);
        updatedSalesChannel
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .sequenceNo(UPDATED_SEQUENCE_NO);

        restSalesChannelMockMvc.perform(put("/api/sales-channels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedSalesChannel)))
            .andExpect(status().isOk());

        // Validate the SalesChannel in the database
        List<SalesChannel> salesChannelList = salesChannelRepository.findAll();
        assertThat(salesChannelList).hasSize(databaseSizeBeforeUpdate);
        SalesChannel testSalesChannel = salesChannelList.get(salesChannelList.size() - 1);
        assertThat(testSalesChannel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSalesChannel.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testSalesChannel.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void updateNonExistingSalesChannel() throws Exception {
        int databaseSizeBeforeUpdate = salesChannelRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSalesChannelMockMvc.perform(put("/api/sales-channels")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(salesChannel)))
            .andExpect(status().isBadRequest());

        // Validate the SalesChannel in the database
        List<SalesChannel> salesChannelList = salesChannelRepository.findAll();
        assertThat(salesChannelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSalesChannel() throws Exception {
        // Initialize the database
        salesChannelRepository.saveAndFlush(salesChannel);

        int databaseSizeBeforeDelete = salesChannelRepository.findAll().size();

        // Delete the salesChannel
        restSalesChannelMockMvc.perform(delete("/api/sales-channels/{id}", salesChannel.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SalesChannel> salesChannelList = salesChannelRepository.findAll();
        assertThat(salesChannelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
