package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.OrderContactMech;
import com.hr.domain.Order;
import com.hr.domain.ContactMech;
import com.hr.domain.ContactMechPurpose;
import com.hr.repository.OrderContactMechRepository;
import com.hr.service.OrderContactMechService;
import com.hr.service.dto.OrderContactMechCriteria;
import com.hr.service.OrderContactMechQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.hr.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrderContactMechResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrderContactMechResourceIT {

    private static final ZonedDateTime DEFAULT_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_FROM_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_FROM_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_THRU_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_THRU_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private OrderContactMechRepository orderContactMechRepository;

    @Autowired
    private OrderContactMechService orderContactMechService;

    @Autowired
    private OrderContactMechQueryService orderContactMechQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderContactMechMockMvc;

    private OrderContactMech orderContactMech;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderContactMech createEntity(EntityManager em) {
        OrderContactMech orderContactMech = new OrderContactMech()
            .fromDate(DEFAULT_FROM_DATE)
            .thruDate(DEFAULT_THRU_DATE);
        return orderContactMech;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderContactMech createUpdatedEntity(EntityManager em) {
        OrderContactMech orderContactMech = new OrderContactMech()
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);
        return orderContactMech;
    }

    @BeforeEach
    public void initTest() {
        orderContactMech = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderContactMech() throws Exception {
        int databaseSizeBeforeCreate = orderContactMechRepository.findAll().size();
        // Create the OrderContactMech
        restOrderContactMechMockMvc.perform(post("/api/order-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderContactMech)))
            .andExpect(status().isCreated());

        // Validate the OrderContactMech in the database
        List<OrderContactMech> orderContactMechList = orderContactMechRepository.findAll();
        assertThat(orderContactMechList).hasSize(databaseSizeBeforeCreate + 1);
        OrderContactMech testOrderContactMech = orderContactMechList.get(orderContactMechList.size() - 1);
        assertThat(testOrderContactMech.getFromDate()).isEqualTo(DEFAULT_FROM_DATE);
        assertThat(testOrderContactMech.getThruDate()).isEqualTo(DEFAULT_THRU_DATE);
    }

    @Test
    @Transactional
    public void createOrderContactMechWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderContactMechRepository.findAll().size();

        // Create the OrderContactMech with an existing ID
        orderContactMech.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderContactMechMockMvc.perform(post("/api/order-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderContactMech)))
            .andExpect(status().isBadRequest());

        // Validate the OrderContactMech in the database
        List<OrderContactMech> orderContactMechList = orderContactMechRepository.findAll();
        assertThat(orderContactMechList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderContactMeches() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList
        restOrderContactMechMockMvc.perform(get("/api/order-contact-meches?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderContactMech.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))));
    }
    
    @Test
    @Transactional
    public void getOrderContactMech() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get the orderContactMech
        restOrderContactMechMockMvc.perform(get("/api/order-contact-meches/{id}", orderContactMech.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderContactMech.getId().intValue()))
            .andExpect(jsonPath("$.fromDate").value(sameInstant(DEFAULT_FROM_DATE)))
            .andExpect(jsonPath("$.thruDate").value(sameInstant(DEFAULT_THRU_DATE)));
    }


    @Test
    @Transactional
    public void getOrderContactMechesByIdFiltering() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        Long id = orderContactMech.getId();

        defaultOrderContactMechShouldBeFound("id.equals=" + id);
        defaultOrderContactMechShouldNotBeFound("id.notEquals=" + id);

        defaultOrderContactMechShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrderContactMechShouldNotBeFound("id.greaterThan=" + id);

        defaultOrderContactMechShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrderContactMechShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOrderContactMechesByFromDateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList where fromDate equals to DEFAULT_FROM_DATE
        defaultOrderContactMechShouldBeFound("fromDate.equals=" + DEFAULT_FROM_DATE);

        // Get all the orderContactMechList where fromDate equals to UPDATED_FROM_DATE
        defaultOrderContactMechShouldNotBeFound("fromDate.equals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderContactMechesByFromDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList where fromDate not equals to DEFAULT_FROM_DATE
        defaultOrderContactMechShouldNotBeFound("fromDate.notEquals=" + DEFAULT_FROM_DATE);

        // Get all the orderContactMechList where fromDate not equals to UPDATED_FROM_DATE
        defaultOrderContactMechShouldBeFound("fromDate.notEquals=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderContactMechesByFromDateIsInShouldWork() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList where fromDate in DEFAULT_FROM_DATE or UPDATED_FROM_DATE
        defaultOrderContactMechShouldBeFound("fromDate.in=" + DEFAULT_FROM_DATE + "," + UPDATED_FROM_DATE);

        // Get all the orderContactMechList where fromDate equals to UPDATED_FROM_DATE
        defaultOrderContactMechShouldNotBeFound("fromDate.in=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderContactMechesByFromDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList where fromDate is not null
        defaultOrderContactMechShouldBeFound("fromDate.specified=true");

        // Get all the orderContactMechList where fromDate is null
        defaultOrderContactMechShouldNotBeFound("fromDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderContactMechesByFromDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList where fromDate is greater than or equal to DEFAULT_FROM_DATE
        defaultOrderContactMechShouldBeFound("fromDate.greaterThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the orderContactMechList where fromDate is greater than or equal to UPDATED_FROM_DATE
        defaultOrderContactMechShouldNotBeFound("fromDate.greaterThanOrEqual=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderContactMechesByFromDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList where fromDate is less than or equal to DEFAULT_FROM_DATE
        defaultOrderContactMechShouldBeFound("fromDate.lessThanOrEqual=" + DEFAULT_FROM_DATE);

        // Get all the orderContactMechList where fromDate is less than or equal to SMALLER_FROM_DATE
        defaultOrderContactMechShouldNotBeFound("fromDate.lessThanOrEqual=" + SMALLER_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderContactMechesByFromDateIsLessThanSomething() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList where fromDate is less than DEFAULT_FROM_DATE
        defaultOrderContactMechShouldNotBeFound("fromDate.lessThan=" + DEFAULT_FROM_DATE);

        // Get all the orderContactMechList where fromDate is less than UPDATED_FROM_DATE
        defaultOrderContactMechShouldBeFound("fromDate.lessThan=" + UPDATED_FROM_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderContactMechesByFromDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList where fromDate is greater than DEFAULT_FROM_DATE
        defaultOrderContactMechShouldNotBeFound("fromDate.greaterThan=" + DEFAULT_FROM_DATE);

        // Get all the orderContactMechList where fromDate is greater than SMALLER_FROM_DATE
        defaultOrderContactMechShouldBeFound("fromDate.greaterThan=" + SMALLER_FROM_DATE);
    }


    @Test
    @Transactional
    public void getAllOrderContactMechesByThruDateIsEqualToSomething() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList where thruDate equals to DEFAULT_THRU_DATE
        defaultOrderContactMechShouldBeFound("thruDate.equals=" + DEFAULT_THRU_DATE);

        // Get all the orderContactMechList where thruDate equals to UPDATED_THRU_DATE
        defaultOrderContactMechShouldNotBeFound("thruDate.equals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderContactMechesByThruDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList where thruDate not equals to DEFAULT_THRU_DATE
        defaultOrderContactMechShouldNotBeFound("thruDate.notEquals=" + DEFAULT_THRU_DATE);

        // Get all the orderContactMechList where thruDate not equals to UPDATED_THRU_DATE
        defaultOrderContactMechShouldBeFound("thruDate.notEquals=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderContactMechesByThruDateIsInShouldWork() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList where thruDate in DEFAULT_THRU_DATE or UPDATED_THRU_DATE
        defaultOrderContactMechShouldBeFound("thruDate.in=" + DEFAULT_THRU_DATE + "," + UPDATED_THRU_DATE);

        // Get all the orderContactMechList where thruDate equals to UPDATED_THRU_DATE
        defaultOrderContactMechShouldNotBeFound("thruDate.in=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderContactMechesByThruDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList where thruDate is not null
        defaultOrderContactMechShouldBeFound("thruDate.specified=true");

        // Get all the orderContactMechList where thruDate is null
        defaultOrderContactMechShouldNotBeFound("thruDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderContactMechesByThruDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList where thruDate is greater than or equal to DEFAULT_THRU_DATE
        defaultOrderContactMechShouldBeFound("thruDate.greaterThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the orderContactMechList where thruDate is greater than or equal to UPDATED_THRU_DATE
        defaultOrderContactMechShouldNotBeFound("thruDate.greaterThanOrEqual=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderContactMechesByThruDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList where thruDate is less than or equal to DEFAULT_THRU_DATE
        defaultOrderContactMechShouldBeFound("thruDate.lessThanOrEqual=" + DEFAULT_THRU_DATE);

        // Get all the orderContactMechList where thruDate is less than or equal to SMALLER_THRU_DATE
        defaultOrderContactMechShouldNotBeFound("thruDate.lessThanOrEqual=" + SMALLER_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderContactMechesByThruDateIsLessThanSomething() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList where thruDate is less than DEFAULT_THRU_DATE
        defaultOrderContactMechShouldNotBeFound("thruDate.lessThan=" + DEFAULT_THRU_DATE);

        // Get all the orderContactMechList where thruDate is less than UPDATED_THRU_DATE
        defaultOrderContactMechShouldBeFound("thruDate.lessThan=" + UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void getAllOrderContactMechesByThruDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);

        // Get all the orderContactMechList where thruDate is greater than DEFAULT_THRU_DATE
        defaultOrderContactMechShouldNotBeFound("thruDate.greaterThan=" + DEFAULT_THRU_DATE);

        // Get all the orderContactMechList where thruDate is greater than SMALLER_THRU_DATE
        defaultOrderContactMechShouldBeFound("thruDate.greaterThan=" + SMALLER_THRU_DATE);
    }


    @Test
    @Transactional
    public void getAllOrderContactMechesByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);
        Order order = OrderResourceIT.createEntity(em);
        em.persist(order);
        em.flush();
        orderContactMech.setOrder(order);
        orderContactMechRepository.saveAndFlush(orderContactMech);
        Long orderId = order.getId();

        // Get all the orderContactMechList where order equals to orderId
        defaultOrderContactMechShouldBeFound("orderId.equals=" + orderId);

        // Get all the orderContactMechList where order equals to orderId + 1
        defaultOrderContactMechShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderContactMechesByContactMechIsEqualToSomething() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);
        ContactMech contactMech = ContactMechResourceIT.createEntity(em);
        em.persist(contactMech);
        em.flush();
        orderContactMech.setContactMech(contactMech);
        orderContactMechRepository.saveAndFlush(orderContactMech);
        Long contactMechId = contactMech.getId();

        // Get all the orderContactMechList where contactMech equals to contactMechId
        defaultOrderContactMechShouldBeFound("contactMechId.equals=" + contactMechId);

        // Get all the orderContactMechList where contactMech equals to contactMechId + 1
        defaultOrderContactMechShouldNotBeFound("contactMechId.equals=" + (contactMechId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderContactMechesByContactMechPurposeIsEqualToSomething() throws Exception {
        // Initialize the database
        orderContactMechRepository.saveAndFlush(orderContactMech);
        ContactMechPurpose contactMechPurpose = ContactMechPurposeResourceIT.createEntity(em);
        em.persist(contactMechPurpose);
        em.flush();
        orderContactMech.setContactMechPurpose(contactMechPurpose);
        orderContactMechRepository.saveAndFlush(orderContactMech);
        Long contactMechPurposeId = contactMechPurpose.getId();

        // Get all the orderContactMechList where contactMechPurpose equals to contactMechPurposeId
        defaultOrderContactMechShouldBeFound("contactMechPurposeId.equals=" + contactMechPurposeId);

        // Get all the orderContactMechList where contactMechPurpose equals to contactMechPurposeId + 1
        defaultOrderContactMechShouldNotBeFound("contactMechPurposeId.equals=" + (contactMechPurposeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrderContactMechShouldBeFound(String filter) throws Exception {
        restOrderContactMechMockMvc.perform(get("/api/order-contact-meches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderContactMech.getId().intValue())))
            .andExpect(jsonPath("$.[*].fromDate").value(hasItem(sameInstant(DEFAULT_FROM_DATE))))
            .andExpect(jsonPath("$.[*].thruDate").value(hasItem(sameInstant(DEFAULT_THRU_DATE))));

        // Check, that the count call also returns 1
        restOrderContactMechMockMvc.perform(get("/api/order-contact-meches/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrderContactMechShouldNotBeFound(String filter) throws Exception {
        restOrderContactMechMockMvc.perform(get("/api/order-contact-meches?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrderContactMechMockMvc.perform(get("/api/order-contact-meches/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOrderContactMech() throws Exception {
        // Get the orderContactMech
        restOrderContactMechMockMvc.perform(get("/api/order-contact-meches/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderContactMech() throws Exception {
        // Initialize the database
        orderContactMechService.save(orderContactMech);

        int databaseSizeBeforeUpdate = orderContactMechRepository.findAll().size();

        // Update the orderContactMech
        OrderContactMech updatedOrderContactMech = orderContactMechRepository.findById(orderContactMech.getId()).get();
        // Disconnect from session so that the updates on updatedOrderContactMech are not directly saved in db
        em.detach(updatedOrderContactMech);
        updatedOrderContactMech
            .fromDate(UPDATED_FROM_DATE)
            .thruDate(UPDATED_THRU_DATE);

        restOrderContactMechMockMvc.perform(put("/api/order-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderContactMech)))
            .andExpect(status().isOk());

        // Validate the OrderContactMech in the database
        List<OrderContactMech> orderContactMechList = orderContactMechRepository.findAll();
        assertThat(orderContactMechList).hasSize(databaseSizeBeforeUpdate);
        OrderContactMech testOrderContactMech = orderContactMechList.get(orderContactMechList.size() - 1);
        assertThat(testOrderContactMech.getFromDate()).isEqualTo(UPDATED_FROM_DATE);
        assertThat(testOrderContactMech.getThruDate()).isEqualTo(UPDATED_THRU_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderContactMech() throws Exception {
        int databaseSizeBeforeUpdate = orderContactMechRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderContactMechMockMvc.perform(put("/api/order-contact-meches")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderContactMech)))
            .andExpect(status().isBadRequest());

        // Validate the OrderContactMech in the database
        List<OrderContactMech> orderContactMechList = orderContactMechRepository.findAll();
        assertThat(orderContactMechList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderContactMech() throws Exception {
        // Initialize the database
        orderContactMechService.save(orderContactMech);

        int databaseSizeBeforeDelete = orderContactMechRepository.findAll().size();

        // Delete the orderContactMech
        restOrderContactMechMockMvc.perform(delete("/api/order-contact-meches/{id}", orderContactMech.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderContactMech> orderContactMechList = orderContactMechRepository.findAll();
        assertThat(orderContactMechList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
