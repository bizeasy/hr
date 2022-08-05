package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.OrderStatus;
import com.hr.domain.Status;
import com.hr.domain.Order;
import com.hr.domain.Reason;
import com.hr.repository.OrderStatusRepository;
import com.hr.service.OrderStatusService;
import com.hr.service.dto.OrderStatusCriteria;
import com.hr.service.OrderStatusQueryService;

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
 * Integration tests for the {@link OrderStatusResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OrderStatusResourceIT {

    private static final ZonedDateTime DEFAULT_STATUS_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_STATUS_DATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_STATUS_DATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final Integer DEFAULT_SEQUENCE_NO = 1;
    private static final Integer UPDATED_SEQUENCE_NO = 2;
    private static final Integer SMALLER_SEQUENCE_NO = 1 - 1;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private OrderStatusService orderStatusService;

    @Autowired
    private OrderStatusQueryService orderStatusQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOrderStatusMockMvc;

    private OrderStatus orderStatus;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderStatus createEntity(EntityManager em) {
        OrderStatus orderStatus = new OrderStatus()
            .statusDateTime(DEFAULT_STATUS_DATE_TIME)
            .sequenceNo(DEFAULT_SEQUENCE_NO);
        return orderStatus;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderStatus createUpdatedEntity(EntityManager em) {
        OrderStatus orderStatus = new OrderStatus()
            .statusDateTime(UPDATED_STATUS_DATE_TIME)
            .sequenceNo(UPDATED_SEQUENCE_NO);
        return orderStatus;
    }

    @BeforeEach
    public void initTest() {
        orderStatus = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderStatus() throws Exception {
        int databaseSizeBeforeCreate = orderStatusRepository.findAll().size();
        // Create the OrderStatus
        restOrderStatusMockMvc.perform(post("/api/order-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderStatus)))
            .andExpect(status().isCreated());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeCreate + 1);
        OrderStatus testOrderStatus = orderStatusList.get(orderStatusList.size() - 1);
        assertThat(testOrderStatus.getStatusDateTime()).isEqualTo(DEFAULT_STATUS_DATE_TIME);
        assertThat(testOrderStatus.getSequenceNo()).isEqualTo(DEFAULT_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void createOrderStatusWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderStatusRepository.findAll().size();

        // Create the OrderStatus with an existing ID
        orderStatus.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderStatusMockMvc.perform(post("/api/order-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderStatus)))
            .andExpect(status().isBadRequest());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderStatuses() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList
        restOrderStatusMockMvc.perform(get("/api/order-statuses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusDateTime").value(hasItem(sameInstant(DEFAULT_STATUS_DATE_TIME))))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)));
    }
    
    @Test
    @Transactional
    public void getOrderStatus() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get the orderStatus
        restOrderStatusMockMvc.perform(get("/api/order-statuses/{id}", orderStatus.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(orderStatus.getId().intValue()))
            .andExpect(jsonPath("$.statusDateTime").value(sameInstant(DEFAULT_STATUS_DATE_TIME)))
            .andExpect(jsonPath("$.sequenceNo").value(DEFAULT_SEQUENCE_NO));
    }


    @Test
    @Transactional
    public void getOrderStatusesByIdFiltering() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        Long id = orderStatus.getId();

        defaultOrderStatusShouldBeFound("id.equals=" + id);
        defaultOrderStatusShouldNotBeFound("id.notEquals=" + id);

        defaultOrderStatusShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOrderStatusShouldNotBeFound("id.greaterThan=" + id);

        defaultOrderStatusShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOrderStatusShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOrderStatusesByStatusDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where statusDateTime equals to DEFAULT_STATUS_DATE_TIME
        defaultOrderStatusShouldBeFound("statusDateTime.equals=" + DEFAULT_STATUS_DATE_TIME);

        // Get all the orderStatusList where statusDateTime equals to UPDATED_STATUS_DATE_TIME
        defaultOrderStatusShouldNotBeFound("statusDateTime.equals=" + UPDATED_STATUS_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByStatusDateTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where statusDateTime not equals to DEFAULT_STATUS_DATE_TIME
        defaultOrderStatusShouldNotBeFound("statusDateTime.notEquals=" + DEFAULT_STATUS_DATE_TIME);

        // Get all the orderStatusList where statusDateTime not equals to UPDATED_STATUS_DATE_TIME
        defaultOrderStatusShouldBeFound("statusDateTime.notEquals=" + UPDATED_STATUS_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByStatusDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where statusDateTime in DEFAULT_STATUS_DATE_TIME or UPDATED_STATUS_DATE_TIME
        defaultOrderStatusShouldBeFound("statusDateTime.in=" + DEFAULT_STATUS_DATE_TIME + "," + UPDATED_STATUS_DATE_TIME);

        // Get all the orderStatusList where statusDateTime equals to UPDATED_STATUS_DATE_TIME
        defaultOrderStatusShouldNotBeFound("statusDateTime.in=" + UPDATED_STATUS_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByStatusDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where statusDateTime is not null
        defaultOrderStatusShouldBeFound("statusDateTime.specified=true");

        // Get all the orderStatusList where statusDateTime is null
        defaultOrderStatusShouldNotBeFound("statusDateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByStatusDateTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where statusDateTime is greater than or equal to DEFAULT_STATUS_DATE_TIME
        defaultOrderStatusShouldBeFound("statusDateTime.greaterThanOrEqual=" + DEFAULT_STATUS_DATE_TIME);

        // Get all the orderStatusList where statusDateTime is greater than or equal to UPDATED_STATUS_DATE_TIME
        defaultOrderStatusShouldNotBeFound("statusDateTime.greaterThanOrEqual=" + UPDATED_STATUS_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByStatusDateTimeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where statusDateTime is less than or equal to DEFAULT_STATUS_DATE_TIME
        defaultOrderStatusShouldBeFound("statusDateTime.lessThanOrEqual=" + DEFAULT_STATUS_DATE_TIME);

        // Get all the orderStatusList where statusDateTime is less than or equal to SMALLER_STATUS_DATE_TIME
        defaultOrderStatusShouldNotBeFound("statusDateTime.lessThanOrEqual=" + SMALLER_STATUS_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByStatusDateTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where statusDateTime is less than DEFAULT_STATUS_DATE_TIME
        defaultOrderStatusShouldNotBeFound("statusDateTime.lessThan=" + DEFAULT_STATUS_DATE_TIME);

        // Get all the orderStatusList where statusDateTime is less than UPDATED_STATUS_DATE_TIME
        defaultOrderStatusShouldBeFound("statusDateTime.lessThan=" + UPDATED_STATUS_DATE_TIME);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesByStatusDateTimeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where statusDateTime is greater than DEFAULT_STATUS_DATE_TIME
        defaultOrderStatusShouldNotBeFound("statusDateTime.greaterThan=" + DEFAULT_STATUS_DATE_TIME);

        // Get all the orderStatusList where statusDateTime is greater than SMALLER_STATUS_DATE_TIME
        defaultOrderStatusShouldBeFound("statusDateTime.greaterThan=" + SMALLER_STATUS_DATE_TIME);
    }


    @Test
    @Transactional
    public void getAllOrderStatusesBySequenceNoIsEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where sequenceNo equals to DEFAULT_SEQUENCE_NO
        defaultOrderStatusShouldBeFound("sequenceNo.equals=" + DEFAULT_SEQUENCE_NO);

        // Get all the orderStatusList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultOrderStatusShouldNotBeFound("sequenceNo.equals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesBySequenceNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where sequenceNo not equals to DEFAULT_SEQUENCE_NO
        defaultOrderStatusShouldNotBeFound("sequenceNo.notEquals=" + DEFAULT_SEQUENCE_NO);

        // Get all the orderStatusList where sequenceNo not equals to UPDATED_SEQUENCE_NO
        defaultOrderStatusShouldBeFound("sequenceNo.notEquals=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesBySequenceNoIsInShouldWork() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where sequenceNo in DEFAULT_SEQUENCE_NO or UPDATED_SEQUENCE_NO
        defaultOrderStatusShouldBeFound("sequenceNo.in=" + DEFAULT_SEQUENCE_NO + "," + UPDATED_SEQUENCE_NO);

        // Get all the orderStatusList where sequenceNo equals to UPDATED_SEQUENCE_NO
        defaultOrderStatusShouldNotBeFound("sequenceNo.in=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesBySequenceNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where sequenceNo is not null
        defaultOrderStatusShouldBeFound("sequenceNo.specified=true");

        // Get all the orderStatusList where sequenceNo is null
        defaultOrderStatusShouldNotBeFound("sequenceNo.specified=false");
    }

    @Test
    @Transactional
    public void getAllOrderStatusesBySequenceNoIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where sequenceNo is greater than or equal to DEFAULT_SEQUENCE_NO
        defaultOrderStatusShouldBeFound("sequenceNo.greaterThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the orderStatusList where sequenceNo is greater than or equal to UPDATED_SEQUENCE_NO
        defaultOrderStatusShouldNotBeFound("sequenceNo.greaterThanOrEqual=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesBySequenceNoIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where sequenceNo is less than or equal to DEFAULT_SEQUENCE_NO
        defaultOrderStatusShouldBeFound("sequenceNo.lessThanOrEqual=" + DEFAULT_SEQUENCE_NO);

        // Get all the orderStatusList where sequenceNo is less than or equal to SMALLER_SEQUENCE_NO
        defaultOrderStatusShouldNotBeFound("sequenceNo.lessThanOrEqual=" + SMALLER_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesBySequenceNoIsLessThanSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where sequenceNo is less than DEFAULT_SEQUENCE_NO
        defaultOrderStatusShouldNotBeFound("sequenceNo.lessThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the orderStatusList where sequenceNo is less than UPDATED_SEQUENCE_NO
        defaultOrderStatusShouldBeFound("sequenceNo.lessThan=" + UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void getAllOrderStatusesBySequenceNoIsGreaterThanSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);

        // Get all the orderStatusList where sequenceNo is greater than DEFAULT_SEQUENCE_NO
        defaultOrderStatusShouldNotBeFound("sequenceNo.greaterThan=" + DEFAULT_SEQUENCE_NO);

        // Get all the orderStatusList where sequenceNo is greater than SMALLER_SEQUENCE_NO
        defaultOrderStatusShouldBeFound("sequenceNo.greaterThan=" + SMALLER_SEQUENCE_NO);
    }


    @Test
    @Transactional
    public void getAllOrderStatusesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        orderStatus.setStatus(status);
        orderStatusRepository.saveAndFlush(orderStatus);
        Long statusId = status.getId();

        // Get all the orderStatusList where status equals to statusId
        defaultOrderStatusShouldBeFound("statusId.equals=" + statusId);

        // Get all the orderStatusList where status equals to statusId + 1
        defaultOrderStatusShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderStatusesByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);
        Order order = OrderResourceIT.createEntity(em);
        em.persist(order);
        em.flush();
        orderStatus.setOrder(order);
        orderStatusRepository.saveAndFlush(orderStatus);
        Long orderId = order.getId();

        // Get all the orderStatusList where order equals to orderId
        defaultOrderStatusShouldBeFound("orderId.equals=" + orderId);

        // Get all the orderStatusList where order equals to orderId + 1
        defaultOrderStatusShouldNotBeFound("orderId.equals=" + (orderId + 1));
    }


    @Test
    @Transactional
    public void getAllOrderStatusesByReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        orderStatusRepository.saveAndFlush(orderStatus);
        Reason reason = ReasonResourceIT.createEntity(em);
        em.persist(reason);
        em.flush();
        orderStatus.setReason(reason);
        orderStatusRepository.saveAndFlush(orderStatus);
        Long reasonId = reason.getId();

        // Get all the orderStatusList where reason equals to reasonId
        defaultOrderStatusShouldBeFound("reasonId.equals=" + reasonId);

        // Get all the orderStatusList where reason equals to reasonId + 1
        defaultOrderStatusShouldNotBeFound("reasonId.equals=" + (reasonId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOrderStatusShouldBeFound(String filter) throws Exception {
        restOrderStatusMockMvc.perform(get("/api/order-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderStatus.getId().intValue())))
            .andExpect(jsonPath("$.[*].statusDateTime").value(hasItem(sameInstant(DEFAULT_STATUS_DATE_TIME))))
            .andExpect(jsonPath("$.[*].sequenceNo").value(hasItem(DEFAULT_SEQUENCE_NO)));

        // Check, that the count call also returns 1
        restOrderStatusMockMvc.perform(get("/api/order-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOrderStatusShouldNotBeFound(String filter) throws Exception {
        restOrderStatusMockMvc.perform(get("/api/order-statuses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOrderStatusMockMvc.perform(get("/api/order-statuses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOrderStatus() throws Exception {
        // Get the orderStatus
        restOrderStatusMockMvc.perform(get("/api/order-statuses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderStatus() throws Exception {
        // Initialize the database
        orderStatusService.save(orderStatus);

        int databaseSizeBeforeUpdate = orderStatusRepository.findAll().size();

        // Update the orderStatus
        OrderStatus updatedOrderStatus = orderStatusRepository.findById(orderStatus.getId()).get();
        // Disconnect from session so that the updates on updatedOrderStatus are not directly saved in db
        em.detach(updatedOrderStatus);
        updatedOrderStatus
            .statusDateTime(UPDATED_STATUS_DATE_TIME)
            .sequenceNo(UPDATED_SEQUENCE_NO);

        restOrderStatusMockMvc.perform(put("/api/order-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderStatus)))
            .andExpect(status().isOk());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeUpdate);
        OrderStatus testOrderStatus = orderStatusList.get(orderStatusList.size() - 1);
        assertThat(testOrderStatus.getStatusDateTime()).isEqualTo(UPDATED_STATUS_DATE_TIME);
        assertThat(testOrderStatus.getSequenceNo()).isEqualTo(UPDATED_SEQUENCE_NO);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderStatus() throws Exception {
        int databaseSizeBeforeUpdate = orderStatusRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderStatusMockMvc.perform(put("/api/order-statuses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(orderStatus)))
            .andExpect(status().isBadRequest());

        // Validate the OrderStatus in the database
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderStatus() throws Exception {
        // Initialize the database
        orderStatusService.save(orderStatus);

        int databaseSizeBeforeDelete = orderStatusRepository.findAll().size();

        // Delete the orderStatus
        restOrderStatusMockMvc.perform(delete("/api/order-statuses/{id}", orderStatus.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderStatus> orderStatusList = orderStatusRepository.findAll();
        assertThat(orderStatusList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
