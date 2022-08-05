package com.hr.web.rest;

import com.hr.HrApp;
import com.hr.domain.Party;
import com.hr.domain.User;
import com.hr.domain.PartyType;
import com.hr.domain.Status;
import com.hr.repository.PartyRepository;
import com.hr.service.PartyService;
import com.hr.service.dto.PartyCriteria;
import com.hr.service.PartyQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.hr.domain.enumeration.Gender;
/**
 * Integration tests for the {@link PartyResource} REST controller.
 */
@SpringBootTest(classes = HrApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyResourceIT {

    private static final Boolean DEFAULT_IS_ORGANISATION = false;
    private static final Boolean UPDATED_IS_ORGANISATION = true;

    private static final String DEFAULT_ORGANISATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISATION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ORGANISATION_SHORT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ORGANISATION_SHORT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SALUTATION = "AAAAAAAAAA";
    private static final String UPDATED_SALUTATION = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_BIRTH_DATE = LocalDate.ofEpochDay(-1L);

    private static final String DEFAULT_PRIMARY_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMARY_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_PRIMARY_EMAIL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_TEMPORARY_PASSWORD = false;
    private static final Boolean UPDATED_IS_TEMPORARY_PASSWORD = true;

    private static final String DEFAULT_LOGO_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_LOGO_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_IMAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final Instant DEFAULT_BIRTHDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_BIRTHDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_OF_JOINING = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_JOINING = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TRAINING_COMPLETED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TRAINING_COMPLETED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_JD_APPROVED_ON = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_JD_APPROVED_ON = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMPLOYEE_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_AUTH_STRING = "AAAAAAAAAA";
    private static final String UPDATED_AUTH_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_USER_GROUP_STRING = "AAAAAAAAAA";
    private static final String UPDATED_USER_GROUP_STRING = "BBBBBBBBBB";

    private static final String DEFAULT_TAN_NO = "AAAAAAAAAA";
    private static final String UPDATED_TAN_NO = "BBBBBBBBBB";

    private static final String DEFAULT_PAN_NO = "AAAAAAAAAA";
    private static final String UPDATED_PAN_NO = "BBBBBBBBBB";

    private static final String DEFAULT_GST_NO = "AAAAAAAAAA";
    private static final String UPDATED_GST_NO = "BBBBBBBBBB";

    private static final String DEFAULT_AADHAR_NO = "AAAAAAAAAA";
    private static final String UPDATED_AADHAR_NO = "BBBBBBBBBB";

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private PartyService partyService;

    @Autowired
    private PartyQueryService partyQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyMockMvc;

    private Party party;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Party createEntity(EntityManager em) {
        Party party = new Party()
            .isOrganisation(DEFAULT_IS_ORGANISATION)
            .organisationName(DEFAULT_ORGANISATION_NAME)
            .organisationShortName(DEFAULT_ORGANISATION_SHORT_NAME)
            .salutation(DEFAULT_SALUTATION)
            .firstName(DEFAULT_FIRST_NAME)
            .middleName(DEFAULT_MIDDLE_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .gender(DEFAULT_GENDER)
            .birthDate(DEFAULT_BIRTH_DATE)
            .primaryPhone(DEFAULT_PRIMARY_PHONE)
            .primaryEmail(DEFAULT_PRIMARY_EMAIL)
            .isTemporaryPassword(DEFAULT_IS_TEMPORARY_PASSWORD)
            .logoImageUrl(DEFAULT_LOGO_IMAGE_URL)
            .profileImageUrl(DEFAULT_PROFILE_IMAGE_URL)
            .notes(DEFAULT_NOTES)
            .birthdate(DEFAULT_BIRTHDATE)
            .dateOfJoining(DEFAULT_DATE_OF_JOINING)
            .trainingCompletedDate(DEFAULT_TRAINING_COMPLETED_DATE)
            .jdApprovedOn(DEFAULT_JD_APPROVED_ON)
            .employeeId(DEFAULT_EMPLOYEE_ID)
            .authString(DEFAULT_AUTH_STRING)
            .userGroupString(DEFAULT_USER_GROUP_STRING)
            .tanNo(DEFAULT_TAN_NO)
            .panNo(DEFAULT_PAN_NO)
            .gstNo(DEFAULT_GST_NO)
            .aadharNo(DEFAULT_AADHAR_NO);
        return party;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Party createUpdatedEntity(EntityManager em) {
        Party party = new Party()
            .isOrganisation(UPDATED_IS_ORGANISATION)
            .organisationName(UPDATED_ORGANISATION_NAME)
            .organisationShortName(UPDATED_ORGANISATION_SHORT_NAME)
            .salutation(UPDATED_SALUTATION)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .primaryPhone(UPDATED_PRIMARY_PHONE)
            .primaryEmail(UPDATED_PRIMARY_EMAIL)
            .isTemporaryPassword(UPDATED_IS_TEMPORARY_PASSWORD)
            .logoImageUrl(UPDATED_LOGO_IMAGE_URL)
            .profileImageUrl(UPDATED_PROFILE_IMAGE_URL)
            .notes(UPDATED_NOTES)
            .birthdate(UPDATED_BIRTHDATE)
            .dateOfJoining(UPDATED_DATE_OF_JOINING)
            .trainingCompletedDate(UPDATED_TRAINING_COMPLETED_DATE)
            .jdApprovedOn(UPDATED_JD_APPROVED_ON)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .authString(UPDATED_AUTH_STRING)
            .userGroupString(UPDATED_USER_GROUP_STRING)
            .tanNo(UPDATED_TAN_NO)
            .panNo(UPDATED_PAN_NO)
            .gstNo(UPDATED_GST_NO)
            .aadharNo(UPDATED_AADHAR_NO);
        return party;
    }

    @BeforeEach
    public void initTest() {
        party = createEntity(em);
    }

    @Test
    @Transactional
    public void createParty() throws Exception {
        int databaseSizeBeforeCreate = partyRepository.findAll().size();
        // Create the Party
        restPartyMockMvc.perform(post("/api/parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(party)))
            .andExpect(status().isCreated());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeCreate + 1);
        Party testParty = partyList.get(partyList.size() - 1);
        assertThat(testParty.isIsOrganisation()).isEqualTo(DEFAULT_IS_ORGANISATION);
        assertThat(testParty.getOrganisationName()).isEqualTo(DEFAULT_ORGANISATION_NAME);
        assertThat(testParty.getOrganisationShortName()).isEqualTo(DEFAULT_ORGANISATION_SHORT_NAME);
        assertThat(testParty.getSalutation()).isEqualTo(DEFAULT_SALUTATION);
        assertThat(testParty.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testParty.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testParty.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testParty.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testParty.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testParty.getPrimaryPhone()).isEqualTo(DEFAULT_PRIMARY_PHONE);
        assertThat(testParty.getPrimaryEmail()).isEqualTo(DEFAULT_PRIMARY_EMAIL);
        assertThat(testParty.isIsTemporaryPassword()).isEqualTo(DEFAULT_IS_TEMPORARY_PASSWORD);
        assertThat(testParty.getLogoImageUrl()).isEqualTo(DEFAULT_LOGO_IMAGE_URL);
        assertThat(testParty.getProfileImageUrl()).isEqualTo(DEFAULT_PROFILE_IMAGE_URL);
        assertThat(testParty.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testParty.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testParty.getDateOfJoining()).isEqualTo(DEFAULT_DATE_OF_JOINING);
        assertThat(testParty.getTrainingCompletedDate()).isEqualTo(DEFAULT_TRAINING_COMPLETED_DATE);
        assertThat(testParty.getJdApprovedOn()).isEqualTo(DEFAULT_JD_APPROVED_ON);
        assertThat(testParty.getEmployeeId()).isEqualTo(DEFAULT_EMPLOYEE_ID);
        assertThat(testParty.getAuthString()).isEqualTo(DEFAULT_AUTH_STRING);
        assertThat(testParty.getUserGroupString()).isEqualTo(DEFAULT_USER_GROUP_STRING);
        assertThat(testParty.getTanNo()).isEqualTo(DEFAULT_TAN_NO);
        assertThat(testParty.getPanNo()).isEqualTo(DEFAULT_PAN_NO);
        assertThat(testParty.getGstNo()).isEqualTo(DEFAULT_GST_NO);
        assertThat(testParty.getAadharNo()).isEqualTo(DEFAULT_AADHAR_NO);
    }

    @Test
    @Transactional
    public void createPartyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyRepository.findAll().size();

        // Create the Party with an existing ID
        party.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyMockMvc.perform(post("/api/parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(party)))
            .andExpect(status().isBadRequest());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllParties() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList
        restPartyMockMvc.perform(get("/api/parties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(party.getId().intValue())))
            .andExpect(jsonPath("$.[*].isOrganisation").value(hasItem(DEFAULT_IS_ORGANISATION.booleanValue())))
            .andExpect(jsonPath("$.[*].organisationName").value(hasItem(DEFAULT_ORGANISATION_NAME)))
            .andExpect(jsonPath("$.[*].organisationShortName").value(hasItem(DEFAULT_ORGANISATION_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].salutation").value(hasItem(DEFAULT_SALUTATION)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].primaryPhone").value(hasItem(DEFAULT_PRIMARY_PHONE)))
            .andExpect(jsonPath("$.[*].primaryEmail").value(hasItem(DEFAULT_PRIMARY_EMAIL)))
            .andExpect(jsonPath("$.[*].isTemporaryPassword").value(hasItem(DEFAULT_IS_TEMPORARY_PASSWORD.booleanValue())))
            .andExpect(jsonPath("$.[*].logoImageUrl").value(hasItem(DEFAULT_LOGO_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].profileImageUrl").value(hasItem(DEFAULT_PROFILE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].dateOfJoining").value(hasItem(DEFAULT_DATE_OF_JOINING.toString())))
            .andExpect(jsonPath("$.[*].trainingCompletedDate").value(hasItem(DEFAULT_TRAINING_COMPLETED_DATE.toString())))
            .andExpect(jsonPath("$.[*].jdApprovedOn").value(hasItem(DEFAULT_JD_APPROVED_ON.toString())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].authString").value(hasItem(DEFAULT_AUTH_STRING.toString())))
            .andExpect(jsonPath("$.[*].userGroupString").value(hasItem(DEFAULT_USER_GROUP_STRING.toString())))
            .andExpect(jsonPath("$.[*].tanNo").value(hasItem(DEFAULT_TAN_NO)))
            .andExpect(jsonPath("$.[*].panNo").value(hasItem(DEFAULT_PAN_NO)))
            .andExpect(jsonPath("$.[*].gstNo").value(hasItem(DEFAULT_GST_NO)))
            .andExpect(jsonPath("$.[*].aadharNo").value(hasItem(DEFAULT_AADHAR_NO)));
    }
    
    @Test
    @Transactional
    public void getParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get the party
        restPartyMockMvc.perform(get("/api/parties/{id}", party.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(party.getId().intValue()))
            .andExpect(jsonPath("$.isOrganisation").value(DEFAULT_IS_ORGANISATION.booleanValue()))
            .andExpect(jsonPath("$.organisationName").value(DEFAULT_ORGANISATION_NAME))
            .andExpect(jsonPath("$.organisationShortName").value(DEFAULT_ORGANISATION_SHORT_NAME))
            .andExpect(jsonPath("$.salutation").value(DEFAULT_SALUTATION))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.primaryPhone").value(DEFAULT_PRIMARY_PHONE))
            .andExpect(jsonPath("$.primaryEmail").value(DEFAULT_PRIMARY_EMAIL))
            .andExpect(jsonPath("$.isTemporaryPassword").value(DEFAULT_IS_TEMPORARY_PASSWORD.booleanValue()))
            .andExpect(jsonPath("$.logoImageUrl").value(DEFAULT_LOGO_IMAGE_URL))
            .andExpect(jsonPath("$.profileImageUrl").value(DEFAULT_PROFILE_IMAGE_URL))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()))
            .andExpect(jsonPath("$.dateOfJoining").value(DEFAULT_DATE_OF_JOINING.toString()))
            .andExpect(jsonPath("$.trainingCompletedDate").value(DEFAULT_TRAINING_COMPLETED_DATE.toString()))
            .andExpect(jsonPath("$.jdApprovedOn").value(DEFAULT_JD_APPROVED_ON.toString()))
            .andExpect(jsonPath("$.employeeId").value(DEFAULT_EMPLOYEE_ID))
            .andExpect(jsonPath("$.authString").value(DEFAULT_AUTH_STRING.toString()))
            .andExpect(jsonPath("$.userGroupString").value(DEFAULT_USER_GROUP_STRING.toString()))
            .andExpect(jsonPath("$.tanNo").value(DEFAULT_TAN_NO))
            .andExpect(jsonPath("$.panNo").value(DEFAULT_PAN_NO))
            .andExpect(jsonPath("$.gstNo").value(DEFAULT_GST_NO))
            .andExpect(jsonPath("$.aadharNo").value(DEFAULT_AADHAR_NO));
    }


    @Test
    @Transactional
    public void getPartiesByIdFiltering() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        Long id = party.getId();

        defaultPartyShouldBeFound("id.equals=" + id);
        defaultPartyShouldNotBeFound("id.notEquals=" + id);

        defaultPartyShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPartyShouldNotBeFound("id.greaterThan=" + id);

        defaultPartyShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPartyShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPartiesByIsOrganisationIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where isOrganisation equals to DEFAULT_IS_ORGANISATION
        defaultPartyShouldBeFound("isOrganisation.equals=" + DEFAULT_IS_ORGANISATION);

        // Get all the partyList where isOrganisation equals to UPDATED_IS_ORGANISATION
        defaultPartyShouldNotBeFound("isOrganisation.equals=" + UPDATED_IS_ORGANISATION);
    }

    @Test
    @Transactional
    public void getAllPartiesByIsOrganisationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where isOrganisation not equals to DEFAULT_IS_ORGANISATION
        defaultPartyShouldNotBeFound("isOrganisation.notEquals=" + DEFAULT_IS_ORGANISATION);

        // Get all the partyList where isOrganisation not equals to UPDATED_IS_ORGANISATION
        defaultPartyShouldBeFound("isOrganisation.notEquals=" + UPDATED_IS_ORGANISATION);
    }

    @Test
    @Transactional
    public void getAllPartiesByIsOrganisationIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where isOrganisation in DEFAULT_IS_ORGANISATION or UPDATED_IS_ORGANISATION
        defaultPartyShouldBeFound("isOrganisation.in=" + DEFAULT_IS_ORGANISATION + "," + UPDATED_IS_ORGANISATION);

        // Get all the partyList where isOrganisation equals to UPDATED_IS_ORGANISATION
        defaultPartyShouldNotBeFound("isOrganisation.in=" + UPDATED_IS_ORGANISATION);
    }

    @Test
    @Transactional
    public void getAllPartiesByIsOrganisationIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where isOrganisation is not null
        defaultPartyShouldBeFound("isOrganisation.specified=true");

        // Get all the partyList where isOrganisation is null
        defaultPartyShouldNotBeFound("isOrganisation.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartiesByOrganisationNameIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where organisationName equals to DEFAULT_ORGANISATION_NAME
        defaultPartyShouldBeFound("organisationName.equals=" + DEFAULT_ORGANISATION_NAME);

        // Get all the partyList where organisationName equals to UPDATED_ORGANISATION_NAME
        defaultPartyShouldNotBeFound("organisationName.equals=" + UPDATED_ORGANISATION_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByOrganisationNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where organisationName not equals to DEFAULT_ORGANISATION_NAME
        defaultPartyShouldNotBeFound("organisationName.notEquals=" + DEFAULT_ORGANISATION_NAME);

        // Get all the partyList where organisationName not equals to UPDATED_ORGANISATION_NAME
        defaultPartyShouldBeFound("organisationName.notEquals=" + UPDATED_ORGANISATION_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByOrganisationNameIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where organisationName in DEFAULT_ORGANISATION_NAME or UPDATED_ORGANISATION_NAME
        defaultPartyShouldBeFound("organisationName.in=" + DEFAULT_ORGANISATION_NAME + "," + UPDATED_ORGANISATION_NAME);

        // Get all the partyList where organisationName equals to UPDATED_ORGANISATION_NAME
        defaultPartyShouldNotBeFound("organisationName.in=" + UPDATED_ORGANISATION_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByOrganisationNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where organisationName is not null
        defaultPartyShouldBeFound("organisationName.specified=true");

        // Get all the partyList where organisationName is null
        defaultPartyShouldNotBeFound("organisationName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesByOrganisationNameContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where organisationName contains DEFAULT_ORGANISATION_NAME
        defaultPartyShouldBeFound("organisationName.contains=" + DEFAULT_ORGANISATION_NAME);

        // Get all the partyList where organisationName contains UPDATED_ORGANISATION_NAME
        defaultPartyShouldNotBeFound("organisationName.contains=" + UPDATED_ORGANISATION_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByOrganisationNameNotContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where organisationName does not contain DEFAULT_ORGANISATION_NAME
        defaultPartyShouldNotBeFound("organisationName.doesNotContain=" + DEFAULT_ORGANISATION_NAME);

        // Get all the partyList where organisationName does not contain UPDATED_ORGANISATION_NAME
        defaultPartyShouldBeFound("organisationName.doesNotContain=" + UPDATED_ORGANISATION_NAME);
    }


    @Test
    @Transactional
    public void getAllPartiesByOrganisationShortNameIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where organisationShortName equals to DEFAULT_ORGANISATION_SHORT_NAME
        defaultPartyShouldBeFound("organisationShortName.equals=" + DEFAULT_ORGANISATION_SHORT_NAME);

        // Get all the partyList where organisationShortName equals to UPDATED_ORGANISATION_SHORT_NAME
        defaultPartyShouldNotBeFound("organisationShortName.equals=" + UPDATED_ORGANISATION_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByOrganisationShortNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where organisationShortName not equals to DEFAULT_ORGANISATION_SHORT_NAME
        defaultPartyShouldNotBeFound("organisationShortName.notEquals=" + DEFAULT_ORGANISATION_SHORT_NAME);

        // Get all the partyList where organisationShortName not equals to UPDATED_ORGANISATION_SHORT_NAME
        defaultPartyShouldBeFound("organisationShortName.notEquals=" + UPDATED_ORGANISATION_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByOrganisationShortNameIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where organisationShortName in DEFAULT_ORGANISATION_SHORT_NAME or UPDATED_ORGANISATION_SHORT_NAME
        defaultPartyShouldBeFound("organisationShortName.in=" + DEFAULT_ORGANISATION_SHORT_NAME + "," + UPDATED_ORGANISATION_SHORT_NAME);

        // Get all the partyList where organisationShortName equals to UPDATED_ORGANISATION_SHORT_NAME
        defaultPartyShouldNotBeFound("organisationShortName.in=" + UPDATED_ORGANISATION_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByOrganisationShortNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where organisationShortName is not null
        defaultPartyShouldBeFound("organisationShortName.specified=true");

        // Get all the partyList where organisationShortName is null
        defaultPartyShouldNotBeFound("organisationShortName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesByOrganisationShortNameContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where organisationShortName contains DEFAULT_ORGANISATION_SHORT_NAME
        defaultPartyShouldBeFound("organisationShortName.contains=" + DEFAULT_ORGANISATION_SHORT_NAME);

        // Get all the partyList where organisationShortName contains UPDATED_ORGANISATION_SHORT_NAME
        defaultPartyShouldNotBeFound("organisationShortName.contains=" + UPDATED_ORGANISATION_SHORT_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByOrganisationShortNameNotContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where organisationShortName does not contain DEFAULT_ORGANISATION_SHORT_NAME
        defaultPartyShouldNotBeFound("organisationShortName.doesNotContain=" + DEFAULT_ORGANISATION_SHORT_NAME);

        // Get all the partyList where organisationShortName does not contain UPDATED_ORGANISATION_SHORT_NAME
        defaultPartyShouldBeFound("organisationShortName.doesNotContain=" + UPDATED_ORGANISATION_SHORT_NAME);
    }


    @Test
    @Transactional
    public void getAllPartiesBySalutationIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where salutation equals to DEFAULT_SALUTATION
        defaultPartyShouldBeFound("salutation.equals=" + DEFAULT_SALUTATION);

        // Get all the partyList where salutation equals to UPDATED_SALUTATION
        defaultPartyShouldNotBeFound("salutation.equals=" + UPDATED_SALUTATION);
    }

    @Test
    @Transactional
    public void getAllPartiesBySalutationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where salutation not equals to DEFAULT_SALUTATION
        defaultPartyShouldNotBeFound("salutation.notEquals=" + DEFAULT_SALUTATION);

        // Get all the partyList where salutation not equals to UPDATED_SALUTATION
        defaultPartyShouldBeFound("salutation.notEquals=" + UPDATED_SALUTATION);
    }

    @Test
    @Transactional
    public void getAllPartiesBySalutationIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where salutation in DEFAULT_SALUTATION or UPDATED_SALUTATION
        defaultPartyShouldBeFound("salutation.in=" + DEFAULT_SALUTATION + "," + UPDATED_SALUTATION);

        // Get all the partyList where salutation equals to UPDATED_SALUTATION
        defaultPartyShouldNotBeFound("salutation.in=" + UPDATED_SALUTATION);
    }

    @Test
    @Transactional
    public void getAllPartiesBySalutationIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where salutation is not null
        defaultPartyShouldBeFound("salutation.specified=true");

        // Get all the partyList where salutation is null
        defaultPartyShouldNotBeFound("salutation.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesBySalutationContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where salutation contains DEFAULT_SALUTATION
        defaultPartyShouldBeFound("salutation.contains=" + DEFAULT_SALUTATION);

        // Get all the partyList where salutation contains UPDATED_SALUTATION
        defaultPartyShouldNotBeFound("salutation.contains=" + UPDATED_SALUTATION);
    }

    @Test
    @Transactional
    public void getAllPartiesBySalutationNotContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where salutation does not contain DEFAULT_SALUTATION
        defaultPartyShouldNotBeFound("salutation.doesNotContain=" + DEFAULT_SALUTATION);

        // Get all the partyList where salutation does not contain UPDATED_SALUTATION
        defaultPartyShouldBeFound("salutation.doesNotContain=" + UPDATED_SALUTATION);
    }


    @Test
    @Transactional
    public void getAllPartiesByFirstNameIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where firstName equals to DEFAULT_FIRST_NAME
        defaultPartyShouldBeFound("firstName.equals=" + DEFAULT_FIRST_NAME);

        // Get all the partyList where firstName equals to UPDATED_FIRST_NAME
        defaultPartyShouldNotBeFound("firstName.equals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByFirstNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where firstName not equals to DEFAULT_FIRST_NAME
        defaultPartyShouldNotBeFound("firstName.notEquals=" + DEFAULT_FIRST_NAME);

        // Get all the partyList where firstName not equals to UPDATED_FIRST_NAME
        defaultPartyShouldBeFound("firstName.notEquals=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByFirstNameIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where firstName in DEFAULT_FIRST_NAME or UPDATED_FIRST_NAME
        defaultPartyShouldBeFound("firstName.in=" + DEFAULT_FIRST_NAME + "," + UPDATED_FIRST_NAME);

        // Get all the partyList where firstName equals to UPDATED_FIRST_NAME
        defaultPartyShouldNotBeFound("firstName.in=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByFirstNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where firstName is not null
        defaultPartyShouldBeFound("firstName.specified=true");

        // Get all the partyList where firstName is null
        defaultPartyShouldNotBeFound("firstName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesByFirstNameContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where firstName contains DEFAULT_FIRST_NAME
        defaultPartyShouldBeFound("firstName.contains=" + DEFAULT_FIRST_NAME);

        // Get all the partyList where firstName contains UPDATED_FIRST_NAME
        defaultPartyShouldNotBeFound("firstName.contains=" + UPDATED_FIRST_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByFirstNameNotContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where firstName does not contain DEFAULT_FIRST_NAME
        defaultPartyShouldNotBeFound("firstName.doesNotContain=" + DEFAULT_FIRST_NAME);

        // Get all the partyList where firstName does not contain UPDATED_FIRST_NAME
        defaultPartyShouldBeFound("firstName.doesNotContain=" + UPDATED_FIRST_NAME);
    }


    @Test
    @Transactional
    public void getAllPartiesByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultPartyShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the partyList where middleName equals to UPDATED_MIDDLE_NAME
        defaultPartyShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByMiddleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where middleName not equals to DEFAULT_MIDDLE_NAME
        defaultPartyShouldNotBeFound("middleName.notEquals=" + DEFAULT_MIDDLE_NAME);

        // Get all the partyList where middleName not equals to UPDATED_MIDDLE_NAME
        defaultPartyShouldBeFound("middleName.notEquals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultPartyShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the partyList where middleName equals to UPDATED_MIDDLE_NAME
        defaultPartyShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where middleName is not null
        defaultPartyShouldBeFound("middleName.specified=true");

        // Get all the partyList where middleName is null
        defaultPartyShouldNotBeFound("middleName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesByMiddleNameContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where middleName contains DEFAULT_MIDDLE_NAME
        defaultPartyShouldBeFound("middleName.contains=" + DEFAULT_MIDDLE_NAME);

        // Get all the partyList where middleName contains UPDATED_MIDDLE_NAME
        defaultPartyShouldNotBeFound("middleName.contains=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByMiddleNameNotContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where middleName does not contain DEFAULT_MIDDLE_NAME
        defaultPartyShouldNotBeFound("middleName.doesNotContain=" + DEFAULT_MIDDLE_NAME);

        // Get all the partyList where middleName does not contain UPDATED_MIDDLE_NAME
        defaultPartyShouldBeFound("middleName.doesNotContain=" + UPDATED_MIDDLE_NAME);
    }


    @Test
    @Transactional
    public void getAllPartiesByLastNameIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where lastName equals to DEFAULT_LAST_NAME
        defaultPartyShouldBeFound("lastName.equals=" + DEFAULT_LAST_NAME);

        // Get all the partyList where lastName equals to UPDATED_LAST_NAME
        defaultPartyShouldNotBeFound("lastName.equals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByLastNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where lastName not equals to DEFAULT_LAST_NAME
        defaultPartyShouldNotBeFound("lastName.notEquals=" + DEFAULT_LAST_NAME);

        // Get all the partyList where lastName not equals to UPDATED_LAST_NAME
        defaultPartyShouldBeFound("lastName.notEquals=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByLastNameIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where lastName in DEFAULT_LAST_NAME or UPDATED_LAST_NAME
        defaultPartyShouldBeFound("lastName.in=" + DEFAULT_LAST_NAME + "," + UPDATED_LAST_NAME);

        // Get all the partyList where lastName equals to UPDATED_LAST_NAME
        defaultPartyShouldNotBeFound("lastName.in=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByLastNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where lastName is not null
        defaultPartyShouldBeFound("lastName.specified=true");

        // Get all the partyList where lastName is null
        defaultPartyShouldNotBeFound("lastName.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesByLastNameContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where lastName contains DEFAULT_LAST_NAME
        defaultPartyShouldBeFound("lastName.contains=" + DEFAULT_LAST_NAME);

        // Get all the partyList where lastName contains UPDATED_LAST_NAME
        defaultPartyShouldNotBeFound("lastName.contains=" + UPDATED_LAST_NAME);
    }

    @Test
    @Transactional
    public void getAllPartiesByLastNameNotContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where lastName does not contain DEFAULT_LAST_NAME
        defaultPartyShouldNotBeFound("lastName.doesNotContain=" + DEFAULT_LAST_NAME);

        // Get all the partyList where lastName does not contain UPDATED_LAST_NAME
        defaultPartyShouldBeFound("lastName.doesNotContain=" + UPDATED_LAST_NAME);
    }


    @Test
    @Transactional
    public void getAllPartiesByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where gender equals to DEFAULT_GENDER
        defaultPartyShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the partyList where gender equals to UPDATED_GENDER
        defaultPartyShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllPartiesByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where gender not equals to DEFAULT_GENDER
        defaultPartyShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the partyList where gender not equals to UPDATED_GENDER
        defaultPartyShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllPartiesByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultPartyShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the partyList where gender equals to UPDATED_GENDER
        defaultPartyShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllPartiesByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where gender is not null
        defaultPartyShouldBeFound("gender.specified=true");

        // Get all the partyList where gender is null
        defaultPartyShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartiesByBirthDateIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where birthDate equals to DEFAULT_BIRTH_DATE
        defaultPartyShouldBeFound("birthDate.equals=" + DEFAULT_BIRTH_DATE);

        // Get all the partyList where birthDate equals to UPDATED_BIRTH_DATE
        defaultPartyShouldNotBeFound("birthDate.equals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllPartiesByBirthDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where birthDate not equals to DEFAULT_BIRTH_DATE
        defaultPartyShouldNotBeFound("birthDate.notEquals=" + DEFAULT_BIRTH_DATE);

        // Get all the partyList where birthDate not equals to UPDATED_BIRTH_DATE
        defaultPartyShouldBeFound("birthDate.notEquals=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllPartiesByBirthDateIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where birthDate in DEFAULT_BIRTH_DATE or UPDATED_BIRTH_DATE
        defaultPartyShouldBeFound("birthDate.in=" + DEFAULT_BIRTH_DATE + "," + UPDATED_BIRTH_DATE);

        // Get all the partyList where birthDate equals to UPDATED_BIRTH_DATE
        defaultPartyShouldNotBeFound("birthDate.in=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllPartiesByBirthDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where birthDate is not null
        defaultPartyShouldBeFound("birthDate.specified=true");

        // Get all the partyList where birthDate is null
        defaultPartyShouldNotBeFound("birthDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartiesByBirthDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where birthDate is greater than or equal to DEFAULT_BIRTH_DATE
        defaultPartyShouldBeFound("birthDate.greaterThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the partyList where birthDate is greater than or equal to UPDATED_BIRTH_DATE
        defaultPartyShouldNotBeFound("birthDate.greaterThanOrEqual=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllPartiesByBirthDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where birthDate is less than or equal to DEFAULT_BIRTH_DATE
        defaultPartyShouldBeFound("birthDate.lessThanOrEqual=" + DEFAULT_BIRTH_DATE);

        // Get all the partyList where birthDate is less than or equal to SMALLER_BIRTH_DATE
        defaultPartyShouldNotBeFound("birthDate.lessThanOrEqual=" + SMALLER_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllPartiesByBirthDateIsLessThanSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where birthDate is less than DEFAULT_BIRTH_DATE
        defaultPartyShouldNotBeFound("birthDate.lessThan=" + DEFAULT_BIRTH_DATE);

        // Get all the partyList where birthDate is less than UPDATED_BIRTH_DATE
        defaultPartyShouldBeFound("birthDate.lessThan=" + UPDATED_BIRTH_DATE);
    }

    @Test
    @Transactional
    public void getAllPartiesByBirthDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where birthDate is greater than DEFAULT_BIRTH_DATE
        defaultPartyShouldNotBeFound("birthDate.greaterThan=" + DEFAULT_BIRTH_DATE);

        // Get all the partyList where birthDate is greater than SMALLER_BIRTH_DATE
        defaultPartyShouldBeFound("birthDate.greaterThan=" + SMALLER_BIRTH_DATE);
    }


    @Test
    @Transactional
    public void getAllPartiesByPrimaryPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where primaryPhone equals to DEFAULT_PRIMARY_PHONE
        defaultPartyShouldBeFound("primaryPhone.equals=" + DEFAULT_PRIMARY_PHONE);

        // Get all the partyList where primaryPhone equals to UPDATED_PRIMARY_PHONE
        defaultPartyShouldNotBeFound("primaryPhone.equals=" + UPDATED_PRIMARY_PHONE);
    }

    @Test
    @Transactional
    public void getAllPartiesByPrimaryPhoneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where primaryPhone not equals to DEFAULT_PRIMARY_PHONE
        defaultPartyShouldNotBeFound("primaryPhone.notEquals=" + DEFAULT_PRIMARY_PHONE);

        // Get all the partyList where primaryPhone not equals to UPDATED_PRIMARY_PHONE
        defaultPartyShouldBeFound("primaryPhone.notEquals=" + UPDATED_PRIMARY_PHONE);
    }

    @Test
    @Transactional
    public void getAllPartiesByPrimaryPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where primaryPhone in DEFAULT_PRIMARY_PHONE or UPDATED_PRIMARY_PHONE
        defaultPartyShouldBeFound("primaryPhone.in=" + DEFAULT_PRIMARY_PHONE + "," + UPDATED_PRIMARY_PHONE);

        // Get all the partyList where primaryPhone equals to UPDATED_PRIMARY_PHONE
        defaultPartyShouldNotBeFound("primaryPhone.in=" + UPDATED_PRIMARY_PHONE);
    }

    @Test
    @Transactional
    public void getAllPartiesByPrimaryPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where primaryPhone is not null
        defaultPartyShouldBeFound("primaryPhone.specified=true");

        // Get all the partyList where primaryPhone is null
        defaultPartyShouldNotBeFound("primaryPhone.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesByPrimaryPhoneContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where primaryPhone contains DEFAULT_PRIMARY_PHONE
        defaultPartyShouldBeFound("primaryPhone.contains=" + DEFAULT_PRIMARY_PHONE);

        // Get all the partyList where primaryPhone contains UPDATED_PRIMARY_PHONE
        defaultPartyShouldNotBeFound("primaryPhone.contains=" + UPDATED_PRIMARY_PHONE);
    }

    @Test
    @Transactional
    public void getAllPartiesByPrimaryPhoneNotContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where primaryPhone does not contain DEFAULT_PRIMARY_PHONE
        defaultPartyShouldNotBeFound("primaryPhone.doesNotContain=" + DEFAULT_PRIMARY_PHONE);

        // Get all the partyList where primaryPhone does not contain UPDATED_PRIMARY_PHONE
        defaultPartyShouldBeFound("primaryPhone.doesNotContain=" + UPDATED_PRIMARY_PHONE);
    }


    @Test
    @Transactional
    public void getAllPartiesByPrimaryEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where primaryEmail equals to DEFAULT_PRIMARY_EMAIL
        defaultPartyShouldBeFound("primaryEmail.equals=" + DEFAULT_PRIMARY_EMAIL);

        // Get all the partyList where primaryEmail equals to UPDATED_PRIMARY_EMAIL
        defaultPartyShouldNotBeFound("primaryEmail.equals=" + UPDATED_PRIMARY_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPartiesByPrimaryEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where primaryEmail not equals to DEFAULT_PRIMARY_EMAIL
        defaultPartyShouldNotBeFound("primaryEmail.notEquals=" + DEFAULT_PRIMARY_EMAIL);

        // Get all the partyList where primaryEmail not equals to UPDATED_PRIMARY_EMAIL
        defaultPartyShouldBeFound("primaryEmail.notEquals=" + UPDATED_PRIMARY_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPartiesByPrimaryEmailIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where primaryEmail in DEFAULT_PRIMARY_EMAIL or UPDATED_PRIMARY_EMAIL
        defaultPartyShouldBeFound("primaryEmail.in=" + DEFAULT_PRIMARY_EMAIL + "," + UPDATED_PRIMARY_EMAIL);

        // Get all the partyList where primaryEmail equals to UPDATED_PRIMARY_EMAIL
        defaultPartyShouldNotBeFound("primaryEmail.in=" + UPDATED_PRIMARY_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPartiesByPrimaryEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where primaryEmail is not null
        defaultPartyShouldBeFound("primaryEmail.specified=true");

        // Get all the partyList where primaryEmail is null
        defaultPartyShouldNotBeFound("primaryEmail.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesByPrimaryEmailContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where primaryEmail contains DEFAULT_PRIMARY_EMAIL
        defaultPartyShouldBeFound("primaryEmail.contains=" + DEFAULT_PRIMARY_EMAIL);

        // Get all the partyList where primaryEmail contains UPDATED_PRIMARY_EMAIL
        defaultPartyShouldNotBeFound("primaryEmail.contains=" + UPDATED_PRIMARY_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPartiesByPrimaryEmailNotContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where primaryEmail does not contain DEFAULT_PRIMARY_EMAIL
        defaultPartyShouldNotBeFound("primaryEmail.doesNotContain=" + DEFAULT_PRIMARY_EMAIL);

        // Get all the partyList where primaryEmail does not contain UPDATED_PRIMARY_EMAIL
        defaultPartyShouldBeFound("primaryEmail.doesNotContain=" + UPDATED_PRIMARY_EMAIL);
    }


    @Test
    @Transactional
    public void getAllPartiesByIsTemporaryPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where isTemporaryPassword equals to DEFAULT_IS_TEMPORARY_PASSWORD
        defaultPartyShouldBeFound("isTemporaryPassword.equals=" + DEFAULT_IS_TEMPORARY_PASSWORD);

        // Get all the partyList where isTemporaryPassword equals to UPDATED_IS_TEMPORARY_PASSWORD
        defaultPartyShouldNotBeFound("isTemporaryPassword.equals=" + UPDATED_IS_TEMPORARY_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllPartiesByIsTemporaryPasswordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where isTemporaryPassword not equals to DEFAULT_IS_TEMPORARY_PASSWORD
        defaultPartyShouldNotBeFound("isTemporaryPassword.notEquals=" + DEFAULT_IS_TEMPORARY_PASSWORD);

        // Get all the partyList where isTemporaryPassword not equals to UPDATED_IS_TEMPORARY_PASSWORD
        defaultPartyShouldBeFound("isTemporaryPassword.notEquals=" + UPDATED_IS_TEMPORARY_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllPartiesByIsTemporaryPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where isTemporaryPassword in DEFAULT_IS_TEMPORARY_PASSWORD or UPDATED_IS_TEMPORARY_PASSWORD
        defaultPartyShouldBeFound("isTemporaryPassword.in=" + DEFAULT_IS_TEMPORARY_PASSWORD + "," + UPDATED_IS_TEMPORARY_PASSWORD);

        // Get all the partyList where isTemporaryPassword equals to UPDATED_IS_TEMPORARY_PASSWORD
        defaultPartyShouldNotBeFound("isTemporaryPassword.in=" + UPDATED_IS_TEMPORARY_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllPartiesByIsTemporaryPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where isTemporaryPassword is not null
        defaultPartyShouldBeFound("isTemporaryPassword.specified=true");

        // Get all the partyList where isTemporaryPassword is null
        defaultPartyShouldNotBeFound("isTemporaryPassword.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartiesByLogoImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where logoImageUrl equals to DEFAULT_LOGO_IMAGE_URL
        defaultPartyShouldBeFound("logoImageUrl.equals=" + DEFAULT_LOGO_IMAGE_URL);

        // Get all the partyList where logoImageUrl equals to UPDATED_LOGO_IMAGE_URL
        defaultPartyShouldNotBeFound("logoImageUrl.equals=" + UPDATED_LOGO_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPartiesByLogoImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where logoImageUrl not equals to DEFAULT_LOGO_IMAGE_URL
        defaultPartyShouldNotBeFound("logoImageUrl.notEquals=" + DEFAULT_LOGO_IMAGE_URL);

        // Get all the partyList where logoImageUrl not equals to UPDATED_LOGO_IMAGE_URL
        defaultPartyShouldBeFound("logoImageUrl.notEquals=" + UPDATED_LOGO_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPartiesByLogoImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where logoImageUrl in DEFAULT_LOGO_IMAGE_URL or UPDATED_LOGO_IMAGE_URL
        defaultPartyShouldBeFound("logoImageUrl.in=" + DEFAULT_LOGO_IMAGE_URL + "," + UPDATED_LOGO_IMAGE_URL);

        // Get all the partyList where logoImageUrl equals to UPDATED_LOGO_IMAGE_URL
        defaultPartyShouldNotBeFound("logoImageUrl.in=" + UPDATED_LOGO_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPartiesByLogoImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where logoImageUrl is not null
        defaultPartyShouldBeFound("logoImageUrl.specified=true");

        // Get all the partyList where logoImageUrl is null
        defaultPartyShouldNotBeFound("logoImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesByLogoImageUrlContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where logoImageUrl contains DEFAULT_LOGO_IMAGE_URL
        defaultPartyShouldBeFound("logoImageUrl.contains=" + DEFAULT_LOGO_IMAGE_URL);

        // Get all the partyList where logoImageUrl contains UPDATED_LOGO_IMAGE_URL
        defaultPartyShouldNotBeFound("logoImageUrl.contains=" + UPDATED_LOGO_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPartiesByLogoImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where logoImageUrl does not contain DEFAULT_LOGO_IMAGE_URL
        defaultPartyShouldNotBeFound("logoImageUrl.doesNotContain=" + DEFAULT_LOGO_IMAGE_URL);

        // Get all the partyList where logoImageUrl does not contain UPDATED_LOGO_IMAGE_URL
        defaultPartyShouldBeFound("logoImageUrl.doesNotContain=" + UPDATED_LOGO_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllPartiesByProfileImageUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where profileImageUrl equals to DEFAULT_PROFILE_IMAGE_URL
        defaultPartyShouldBeFound("profileImageUrl.equals=" + DEFAULT_PROFILE_IMAGE_URL);

        // Get all the partyList where profileImageUrl equals to UPDATED_PROFILE_IMAGE_URL
        defaultPartyShouldNotBeFound("profileImageUrl.equals=" + UPDATED_PROFILE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPartiesByProfileImageUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where profileImageUrl not equals to DEFAULT_PROFILE_IMAGE_URL
        defaultPartyShouldNotBeFound("profileImageUrl.notEquals=" + DEFAULT_PROFILE_IMAGE_URL);

        // Get all the partyList where profileImageUrl not equals to UPDATED_PROFILE_IMAGE_URL
        defaultPartyShouldBeFound("profileImageUrl.notEquals=" + UPDATED_PROFILE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPartiesByProfileImageUrlIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where profileImageUrl in DEFAULT_PROFILE_IMAGE_URL or UPDATED_PROFILE_IMAGE_URL
        defaultPartyShouldBeFound("profileImageUrl.in=" + DEFAULT_PROFILE_IMAGE_URL + "," + UPDATED_PROFILE_IMAGE_URL);

        // Get all the partyList where profileImageUrl equals to UPDATED_PROFILE_IMAGE_URL
        defaultPartyShouldNotBeFound("profileImageUrl.in=" + UPDATED_PROFILE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPartiesByProfileImageUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where profileImageUrl is not null
        defaultPartyShouldBeFound("profileImageUrl.specified=true");

        // Get all the partyList where profileImageUrl is null
        defaultPartyShouldNotBeFound("profileImageUrl.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesByProfileImageUrlContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where profileImageUrl contains DEFAULT_PROFILE_IMAGE_URL
        defaultPartyShouldBeFound("profileImageUrl.contains=" + DEFAULT_PROFILE_IMAGE_URL);

        // Get all the partyList where profileImageUrl contains UPDATED_PROFILE_IMAGE_URL
        defaultPartyShouldNotBeFound("profileImageUrl.contains=" + UPDATED_PROFILE_IMAGE_URL);
    }

    @Test
    @Transactional
    public void getAllPartiesByProfileImageUrlNotContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where profileImageUrl does not contain DEFAULT_PROFILE_IMAGE_URL
        defaultPartyShouldNotBeFound("profileImageUrl.doesNotContain=" + DEFAULT_PROFILE_IMAGE_URL);

        // Get all the partyList where profileImageUrl does not contain UPDATED_PROFILE_IMAGE_URL
        defaultPartyShouldBeFound("profileImageUrl.doesNotContain=" + UPDATED_PROFILE_IMAGE_URL);
    }


    @Test
    @Transactional
    public void getAllPartiesByNotesIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where notes equals to DEFAULT_NOTES
        defaultPartyShouldBeFound("notes.equals=" + DEFAULT_NOTES);

        // Get all the partyList where notes equals to UPDATED_NOTES
        defaultPartyShouldNotBeFound("notes.equals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllPartiesByNotesIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where notes not equals to DEFAULT_NOTES
        defaultPartyShouldNotBeFound("notes.notEquals=" + DEFAULT_NOTES);

        // Get all the partyList where notes not equals to UPDATED_NOTES
        defaultPartyShouldBeFound("notes.notEquals=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllPartiesByNotesIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where notes in DEFAULT_NOTES or UPDATED_NOTES
        defaultPartyShouldBeFound("notes.in=" + DEFAULT_NOTES + "," + UPDATED_NOTES);

        // Get all the partyList where notes equals to UPDATED_NOTES
        defaultPartyShouldNotBeFound("notes.in=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllPartiesByNotesIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where notes is not null
        defaultPartyShouldBeFound("notes.specified=true");

        // Get all the partyList where notes is null
        defaultPartyShouldNotBeFound("notes.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesByNotesContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where notes contains DEFAULT_NOTES
        defaultPartyShouldBeFound("notes.contains=" + DEFAULT_NOTES);

        // Get all the partyList where notes contains UPDATED_NOTES
        defaultPartyShouldNotBeFound("notes.contains=" + UPDATED_NOTES);
    }

    @Test
    @Transactional
    public void getAllPartiesByNotesNotContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where notes does not contain DEFAULT_NOTES
        defaultPartyShouldNotBeFound("notes.doesNotContain=" + DEFAULT_NOTES);

        // Get all the partyList where notes does not contain UPDATED_NOTES
        defaultPartyShouldBeFound("notes.doesNotContain=" + UPDATED_NOTES);
    }


    @Test
    @Transactional
    public void getAllPartiesByBirthdateIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where birthdate equals to DEFAULT_BIRTHDATE
        defaultPartyShouldBeFound("birthdate.equals=" + DEFAULT_BIRTHDATE);

        // Get all the partyList where birthdate equals to UPDATED_BIRTHDATE
        defaultPartyShouldNotBeFound("birthdate.equals=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllPartiesByBirthdateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where birthdate not equals to DEFAULT_BIRTHDATE
        defaultPartyShouldNotBeFound("birthdate.notEquals=" + DEFAULT_BIRTHDATE);

        // Get all the partyList where birthdate not equals to UPDATED_BIRTHDATE
        defaultPartyShouldBeFound("birthdate.notEquals=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllPartiesByBirthdateIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where birthdate in DEFAULT_BIRTHDATE or UPDATED_BIRTHDATE
        defaultPartyShouldBeFound("birthdate.in=" + DEFAULT_BIRTHDATE + "," + UPDATED_BIRTHDATE);

        // Get all the partyList where birthdate equals to UPDATED_BIRTHDATE
        defaultPartyShouldNotBeFound("birthdate.in=" + UPDATED_BIRTHDATE);
    }

    @Test
    @Transactional
    public void getAllPartiesByBirthdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where birthdate is not null
        defaultPartyShouldBeFound("birthdate.specified=true");

        // Get all the partyList where birthdate is null
        defaultPartyShouldNotBeFound("birthdate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartiesByDateOfJoiningIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where dateOfJoining equals to DEFAULT_DATE_OF_JOINING
        defaultPartyShouldBeFound("dateOfJoining.equals=" + DEFAULT_DATE_OF_JOINING);

        // Get all the partyList where dateOfJoining equals to UPDATED_DATE_OF_JOINING
        defaultPartyShouldNotBeFound("dateOfJoining.equals=" + UPDATED_DATE_OF_JOINING);
    }

    @Test
    @Transactional
    public void getAllPartiesByDateOfJoiningIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where dateOfJoining not equals to DEFAULT_DATE_OF_JOINING
        defaultPartyShouldNotBeFound("dateOfJoining.notEquals=" + DEFAULT_DATE_OF_JOINING);

        // Get all the partyList where dateOfJoining not equals to UPDATED_DATE_OF_JOINING
        defaultPartyShouldBeFound("dateOfJoining.notEquals=" + UPDATED_DATE_OF_JOINING);
    }

    @Test
    @Transactional
    public void getAllPartiesByDateOfJoiningIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where dateOfJoining in DEFAULT_DATE_OF_JOINING or UPDATED_DATE_OF_JOINING
        defaultPartyShouldBeFound("dateOfJoining.in=" + DEFAULT_DATE_OF_JOINING + "," + UPDATED_DATE_OF_JOINING);

        // Get all the partyList where dateOfJoining equals to UPDATED_DATE_OF_JOINING
        defaultPartyShouldNotBeFound("dateOfJoining.in=" + UPDATED_DATE_OF_JOINING);
    }

    @Test
    @Transactional
    public void getAllPartiesByDateOfJoiningIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where dateOfJoining is not null
        defaultPartyShouldBeFound("dateOfJoining.specified=true");

        // Get all the partyList where dateOfJoining is null
        defaultPartyShouldNotBeFound("dateOfJoining.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartiesByTrainingCompletedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where trainingCompletedDate equals to DEFAULT_TRAINING_COMPLETED_DATE
        defaultPartyShouldBeFound("trainingCompletedDate.equals=" + DEFAULT_TRAINING_COMPLETED_DATE);

        // Get all the partyList where trainingCompletedDate equals to UPDATED_TRAINING_COMPLETED_DATE
        defaultPartyShouldNotBeFound("trainingCompletedDate.equals=" + UPDATED_TRAINING_COMPLETED_DATE);
    }

    @Test
    @Transactional
    public void getAllPartiesByTrainingCompletedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where trainingCompletedDate not equals to DEFAULT_TRAINING_COMPLETED_DATE
        defaultPartyShouldNotBeFound("trainingCompletedDate.notEquals=" + DEFAULT_TRAINING_COMPLETED_DATE);

        // Get all the partyList where trainingCompletedDate not equals to UPDATED_TRAINING_COMPLETED_DATE
        defaultPartyShouldBeFound("trainingCompletedDate.notEquals=" + UPDATED_TRAINING_COMPLETED_DATE);
    }

    @Test
    @Transactional
    public void getAllPartiesByTrainingCompletedDateIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where trainingCompletedDate in DEFAULT_TRAINING_COMPLETED_DATE or UPDATED_TRAINING_COMPLETED_DATE
        defaultPartyShouldBeFound("trainingCompletedDate.in=" + DEFAULT_TRAINING_COMPLETED_DATE + "," + UPDATED_TRAINING_COMPLETED_DATE);

        // Get all the partyList where trainingCompletedDate equals to UPDATED_TRAINING_COMPLETED_DATE
        defaultPartyShouldNotBeFound("trainingCompletedDate.in=" + UPDATED_TRAINING_COMPLETED_DATE);
    }

    @Test
    @Transactional
    public void getAllPartiesByTrainingCompletedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where trainingCompletedDate is not null
        defaultPartyShouldBeFound("trainingCompletedDate.specified=true");

        // Get all the partyList where trainingCompletedDate is null
        defaultPartyShouldNotBeFound("trainingCompletedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartiesByJdApprovedOnIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where jdApprovedOn equals to DEFAULT_JD_APPROVED_ON
        defaultPartyShouldBeFound("jdApprovedOn.equals=" + DEFAULT_JD_APPROVED_ON);

        // Get all the partyList where jdApprovedOn equals to UPDATED_JD_APPROVED_ON
        defaultPartyShouldNotBeFound("jdApprovedOn.equals=" + UPDATED_JD_APPROVED_ON);
    }

    @Test
    @Transactional
    public void getAllPartiesByJdApprovedOnIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where jdApprovedOn not equals to DEFAULT_JD_APPROVED_ON
        defaultPartyShouldNotBeFound("jdApprovedOn.notEquals=" + DEFAULT_JD_APPROVED_ON);

        // Get all the partyList where jdApprovedOn not equals to UPDATED_JD_APPROVED_ON
        defaultPartyShouldBeFound("jdApprovedOn.notEquals=" + UPDATED_JD_APPROVED_ON);
    }

    @Test
    @Transactional
    public void getAllPartiesByJdApprovedOnIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where jdApprovedOn in DEFAULT_JD_APPROVED_ON or UPDATED_JD_APPROVED_ON
        defaultPartyShouldBeFound("jdApprovedOn.in=" + DEFAULT_JD_APPROVED_ON + "," + UPDATED_JD_APPROVED_ON);

        // Get all the partyList where jdApprovedOn equals to UPDATED_JD_APPROVED_ON
        defaultPartyShouldNotBeFound("jdApprovedOn.in=" + UPDATED_JD_APPROVED_ON);
    }

    @Test
    @Transactional
    public void getAllPartiesByJdApprovedOnIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where jdApprovedOn is not null
        defaultPartyShouldBeFound("jdApprovedOn.specified=true");

        // Get all the partyList where jdApprovedOn is null
        defaultPartyShouldNotBeFound("jdApprovedOn.specified=false");
    }

    @Test
    @Transactional
    public void getAllPartiesByEmployeeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where employeeId equals to DEFAULT_EMPLOYEE_ID
        defaultPartyShouldBeFound("employeeId.equals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the partyList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultPartyShouldNotBeFound("employeeId.equals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    public void getAllPartiesByEmployeeIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where employeeId not equals to DEFAULT_EMPLOYEE_ID
        defaultPartyShouldNotBeFound("employeeId.notEquals=" + DEFAULT_EMPLOYEE_ID);

        // Get all the partyList where employeeId not equals to UPDATED_EMPLOYEE_ID
        defaultPartyShouldBeFound("employeeId.notEquals=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    public void getAllPartiesByEmployeeIdIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where employeeId in DEFAULT_EMPLOYEE_ID or UPDATED_EMPLOYEE_ID
        defaultPartyShouldBeFound("employeeId.in=" + DEFAULT_EMPLOYEE_ID + "," + UPDATED_EMPLOYEE_ID);

        // Get all the partyList where employeeId equals to UPDATED_EMPLOYEE_ID
        defaultPartyShouldNotBeFound("employeeId.in=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    public void getAllPartiesByEmployeeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where employeeId is not null
        defaultPartyShouldBeFound("employeeId.specified=true");

        // Get all the partyList where employeeId is null
        defaultPartyShouldNotBeFound("employeeId.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesByEmployeeIdContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where employeeId contains DEFAULT_EMPLOYEE_ID
        defaultPartyShouldBeFound("employeeId.contains=" + DEFAULT_EMPLOYEE_ID);

        // Get all the partyList where employeeId contains UPDATED_EMPLOYEE_ID
        defaultPartyShouldNotBeFound("employeeId.contains=" + UPDATED_EMPLOYEE_ID);
    }

    @Test
    @Transactional
    public void getAllPartiesByEmployeeIdNotContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where employeeId does not contain DEFAULT_EMPLOYEE_ID
        defaultPartyShouldNotBeFound("employeeId.doesNotContain=" + DEFAULT_EMPLOYEE_ID);

        // Get all the partyList where employeeId does not contain UPDATED_EMPLOYEE_ID
        defaultPartyShouldBeFound("employeeId.doesNotContain=" + UPDATED_EMPLOYEE_ID);
    }


    @Test
    @Transactional
    public void getAllPartiesByTanNoIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where tanNo equals to DEFAULT_TAN_NO
        defaultPartyShouldBeFound("tanNo.equals=" + DEFAULT_TAN_NO);

        // Get all the partyList where tanNo equals to UPDATED_TAN_NO
        defaultPartyShouldNotBeFound("tanNo.equals=" + UPDATED_TAN_NO);
    }

    @Test
    @Transactional
    public void getAllPartiesByTanNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where tanNo not equals to DEFAULT_TAN_NO
        defaultPartyShouldNotBeFound("tanNo.notEquals=" + DEFAULT_TAN_NO);

        // Get all the partyList where tanNo not equals to UPDATED_TAN_NO
        defaultPartyShouldBeFound("tanNo.notEquals=" + UPDATED_TAN_NO);
    }

    @Test
    @Transactional
    public void getAllPartiesByTanNoIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where tanNo in DEFAULT_TAN_NO or UPDATED_TAN_NO
        defaultPartyShouldBeFound("tanNo.in=" + DEFAULT_TAN_NO + "," + UPDATED_TAN_NO);

        // Get all the partyList where tanNo equals to UPDATED_TAN_NO
        defaultPartyShouldNotBeFound("tanNo.in=" + UPDATED_TAN_NO);
    }

    @Test
    @Transactional
    public void getAllPartiesByTanNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where tanNo is not null
        defaultPartyShouldBeFound("tanNo.specified=true");

        // Get all the partyList where tanNo is null
        defaultPartyShouldNotBeFound("tanNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesByTanNoContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where tanNo contains DEFAULT_TAN_NO
        defaultPartyShouldBeFound("tanNo.contains=" + DEFAULT_TAN_NO);

        // Get all the partyList where tanNo contains UPDATED_TAN_NO
        defaultPartyShouldNotBeFound("tanNo.contains=" + UPDATED_TAN_NO);
    }

    @Test
    @Transactional
    public void getAllPartiesByTanNoNotContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where tanNo does not contain DEFAULT_TAN_NO
        defaultPartyShouldNotBeFound("tanNo.doesNotContain=" + DEFAULT_TAN_NO);

        // Get all the partyList where tanNo does not contain UPDATED_TAN_NO
        defaultPartyShouldBeFound("tanNo.doesNotContain=" + UPDATED_TAN_NO);
    }


    @Test
    @Transactional
    public void getAllPartiesByPanNoIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where panNo equals to DEFAULT_PAN_NO
        defaultPartyShouldBeFound("panNo.equals=" + DEFAULT_PAN_NO);

        // Get all the partyList where panNo equals to UPDATED_PAN_NO
        defaultPartyShouldNotBeFound("panNo.equals=" + UPDATED_PAN_NO);
    }

    @Test
    @Transactional
    public void getAllPartiesByPanNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where panNo not equals to DEFAULT_PAN_NO
        defaultPartyShouldNotBeFound("panNo.notEquals=" + DEFAULT_PAN_NO);

        // Get all the partyList where panNo not equals to UPDATED_PAN_NO
        defaultPartyShouldBeFound("panNo.notEquals=" + UPDATED_PAN_NO);
    }

    @Test
    @Transactional
    public void getAllPartiesByPanNoIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where panNo in DEFAULT_PAN_NO or UPDATED_PAN_NO
        defaultPartyShouldBeFound("panNo.in=" + DEFAULT_PAN_NO + "," + UPDATED_PAN_NO);

        // Get all the partyList where panNo equals to UPDATED_PAN_NO
        defaultPartyShouldNotBeFound("panNo.in=" + UPDATED_PAN_NO);
    }

    @Test
    @Transactional
    public void getAllPartiesByPanNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where panNo is not null
        defaultPartyShouldBeFound("panNo.specified=true");

        // Get all the partyList where panNo is null
        defaultPartyShouldNotBeFound("panNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesByPanNoContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where panNo contains DEFAULT_PAN_NO
        defaultPartyShouldBeFound("panNo.contains=" + DEFAULT_PAN_NO);

        // Get all the partyList where panNo contains UPDATED_PAN_NO
        defaultPartyShouldNotBeFound("panNo.contains=" + UPDATED_PAN_NO);
    }

    @Test
    @Transactional
    public void getAllPartiesByPanNoNotContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where panNo does not contain DEFAULT_PAN_NO
        defaultPartyShouldNotBeFound("panNo.doesNotContain=" + DEFAULT_PAN_NO);

        // Get all the partyList where panNo does not contain UPDATED_PAN_NO
        defaultPartyShouldBeFound("panNo.doesNotContain=" + UPDATED_PAN_NO);
    }


    @Test
    @Transactional
    public void getAllPartiesByGstNoIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where gstNo equals to DEFAULT_GST_NO
        defaultPartyShouldBeFound("gstNo.equals=" + DEFAULT_GST_NO);

        // Get all the partyList where gstNo equals to UPDATED_GST_NO
        defaultPartyShouldNotBeFound("gstNo.equals=" + UPDATED_GST_NO);
    }

    @Test
    @Transactional
    public void getAllPartiesByGstNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where gstNo not equals to DEFAULT_GST_NO
        defaultPartyShouldNotBeFound("gstNo.notEquals=" + DEFAULT_GST_NO);

        // Get all the partyList where gstNo not equals to UPDATED_GST_NO
        defaultPartyShouldBeFound("gstNo.notEquals=" + UPDATED_GST_NO);
    }

    @Test
    @Transactional
    public void getAllPartiesByGstNoIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where gstNo in DEFAULT_GST_NO or UPDATED_GST_NO
        defaultPartyShouldBeFound("gstNo.in=" + DEFAULT_GST_NO + "," + UPDATED_GST_NO);

        // Get all the partyList where gstNo equals to UPDATED_GST_NO
        defaultPartyShouldNotBeFound("gstNo.in=" + UPDATED_GST_NO);
    }

    @Test
    @Transactional
    public void getAllPartiesByGstNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where gstNo is not null
        defaultPartyShouldBeFound("gstNo.specified=true");

        // Get all the partyList where gstNo is null
        defaultPartyShouldNotBeFound("gstNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesByGstNoContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where gstNo contains DEFAULT_GST_NO
        defaultPartyShouldBeFound("gstNo.contains=" + DEFAULT_GST_NO);

        // Get all the partyList where gstNo contains UPDATED_GST_NO
        defaultPartyShouldNotBeFound("gstNo.contains=" + UPDATED_GST_NO);
    }

    @Test
    @Transactional
    public void getAllPartiesByGstNoNotContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where gstNo does not contain DEFAULT_GST_NO
        defaultPartyShouldNotBeFound("gstNo.doesNotContain=" + DEFAULT_GST_NO);

        // Get all the partyList where gstNo does not contain UPDATED_GST_NO
        defaultPartyShouldBeFound("gstNo.doesNotContain=" + UPDATED_GST_NO);
    }


    @Test
    @Transactional
    public void getAllPartiesByAadharNoIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where aadharNo equals to DEFAULT_AADHAR_NO
        defaultPartyShouldBeFound("aadharNo.equals=" + DEFAULT_AADHAR_NO);

        // Get all the partyList where aadharNo equals to UPDATED_AADHAR_NO
        defaultPartyShouldNotBeFound("aadharNo.equals=" + UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    public void getAllPartiesByAadharNoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where aadharNo not equals to DEFAULT_AADHAR_NO
        defaultPartyShouldNotBeFound("aadharNo.notEquals=" + DEFAULT_AADHAR_NO);

        // Get all the partyList where aadharNo not equals to UPDATED_AADHAR_NO
        defaultPartyShouldBeFound("aadharNo.notEquals=" + UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    public void getAllPartiesByAadharNoIsInShouldWork() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where aadharNo in DEFAULT_AADHAR_NO or UPDATED_AADHAR_NO
        defaultPartyShouldBeFound("aadharNo.in=" + DEFAULT_AADHAR_NO + "," + UPDATED_AADHAR_NO);

        // Get all the partyList where aadharNo equals to UPDATED_AADHAR_NO
        defaultPartyShouldNotBeFound("aadharNo.in=" + UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    public void getAllPartiesByAadharNoIsNullOrNotNull() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where aadharNo is not null
        defaultPartyShouldBeFound("aadharNo.specified=true");

        // Get all the partyList where aadharNo is null
        defaultPartyShouldNotBeFound("aadharNo.specified=false");
    }
                @Test
    @Transactional
    public void getAllPartiesByAadharNoContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where aadharNo contains DEFAULT_AADHAR_NO
        defaultPartyShouldBeFound("aadharNo.contains=" + DEFAULT_AADHAR_NO);

        // Get all the partyList where aadharNo contains UPDATED_AADHAR_NO
        defaultPartyShouldNotBeFound("aadharNo.contains=" + UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    public void getAllPartiesByAadharNoNotContainsSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList where aadharNo does not contain DEFAULT_AADHAR_NO
        defaultPartyShouldNotBeFound("aadharNo.doesNotContain=" + DEFAULT_AADHAR_NO);

        // Get all the partyList where aadharNo does not contain UPDATED_AADHAR_NO
        defaultPartyShouldBeFound("aadharNo.doesNotContain=" + UPDATED_AADHAR_NO);
    }


    @Test
    @Transactional
    public void getAllPartiesByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        party.setUser(user);
        partyRepository.saveAndFlush(party);
        Long userId = user.getId();

        // Get all the partyList where user equals to userId
        defaultPartyShouldBeFound("userId.equals=" + userId);

        // Get all the partyList where user equals to userId + 1
        defaultPartyShouldNotBeFound("userId.equals=" + (userId + 1));
    }


    @Test
    @Transactional
    public void getAllPartiesByPartyTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);
        PartyType partyType = PartyTypeResourceIT.createEntity(em);
        em.persist(partyType);
        em.flush();
        party.setPartyType(partyType);
        partyRepository.saveAndFlush(party);
        Long partyTypeId = partyType.getId();

        // Get all the partyList where partyType equals to partyTypeId
        defaultPartyShouldBeFound("partyTypeId.equals=" + partyTypeId);

        // Get all the partyList where partyType equals to partyTypeId + 1
        defaultPartyShouldNotBeFound("partyTypeId.equals=" + (partyTypeId + 1));
    }


    @Test
    @Transactional
    public void getAllPartiesByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);
        Status status = StatusResourceIT.createEntity(em);
        em.persist(status);
        em.flush();
        party.setStatus(status);
        partyRepository.saveAndFlush(party);
        Long statusId = status.getId();

        // Get all the partyList where status equals to statusId
        defaultPartyShouldBeFound("statusId.equals=" + statusId);

        // Get all the partyList where status equals to statusId + 1
        defaultPartyShouldNotBeFound("statusId.equals=" + (statusId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPartyShouldBeFound(String filter) throws Exception {
        restPartyMockMvc.perform(get("/api/parties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(party.getId().intValue())))
            .andExpect(jsonPath("$.[*].isOrganisation").value(hasItem(DEFAULT_IS_ORGANISATION.booleanValue())))
            .andExpect(jsonPath("$.[*].organisationName").value(hasItem(DEFAULT_ORGANISATION_NAME)))
            .andExpect(jsonPath("$.[*].organisationShortName").value(hasItem(DEFAULT_ORGANISATION_SHORT_NAME)))
            .andExpect(jsonPath("$.[*].salutation").value(hasItem(DEFAULT_SALUTATION)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].primaryPhone").value(hasItem(DEFAULT_PRIMARY_PHONE)))
            .andExpect(jsonPath("$.[*].primaryEmail").value(hasItem(DEFAULT_PRIMARY_EMAIL)))
            .andExpect(jsonPath("$.[*].isTemporaryPassword").value(hasItem(DEFAULT_IS_TEMPORARY_PASSWORD.booleanValue())))
            .andExpect(jsonPath("$.[*].logoImageUrl").value(hasItem(DEFAULT_LOGO_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].profileImageUrl").value(hasItem(DEFAULT_PROFILE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].dateOfJoining").value(hasItem(DEFAULT_DATE_OF_JOINING.toString())))
            .andExpect(jsonPath("$.[*].trainingCompletedDate").value(hasItem(DEFAULT_TRAINING_COMPLETED_DATE.toString())))
            .andExpect(jsonPath("$.[*].jdApprovedOn").value(hasItem(DEFAULT_JD_APPROVED_ON.toString())))
            .andExpect(jsonPath("$.[*].employeeId").value(hasItem(DEFAULT_EMPLOYEE_ID)))
            .andExpect(jsonPath("$.[*].authString").value(hasItem(DEFAULT_AUTH_STRING.toString())))
            .andExpect(jsonPath("$.[*].userGroupString").value(hasItem(DEFAULT_USER_GROUP_STRING.toString())))
            .andExpect(jsonPath("$.[*].tanNo").value(hasItem(DEFAULT_TAN_NO)))
            .andExpect(jsonPath("$.[*].panNo").value(hasItem(DEFAULT_PAN_NO)))
            .andExpect(jsonPath("$.[*].gstNo").value(hasItem(DEFAULT_GST_NO)))
            .andExpect(jsonPath("$.[*].aadharNo").value(hasItem(DEFAULT_AADHAR_NO)));

        // Check, that the count call also returns 1
        restPartyMockMvc.perform(get("/api/parties/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPartyShouldNotBeFound(String filter) throws Exception {
        restPartyMockMvc.perform(get("/api/parties?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPartyMockMvc.perform(get("/api/parties/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingParty() throws Exception {
        // Get the party
        restPartyMockMvc.perform(get("/api/parties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParty() throws Exception {
        // Initialize the database
        partyService.save(party);

        int databaseSizeBeforeUpdate = partyRepository.findAll().size();

        // Update the party
        Party updatedParty = partyRepository.findById(party.getId()).get();
        // Disconnect from session so that the updates on updatedParty are not directly saved in db
        em.detach(updatedParty);
        updatedParty
            .isOrganisation(UPDATED_IS_ORGANISATION)
            .organisationName(UPDATED_ORGANISATION_NAME)
            .organisationShortName(UPDATED_ORGANISATION_SHORT_NAME)
            .salutation(UPDATED_SALUTATION)
            .firstName(UPDATED_FIRST_NAME)
            .middleName(UPDATED_MIDDLE_NAME)
            .lastName(UPDATED_LAST_NAME)
            .gender(UPDATED_GENDER)
            .birthDate(UPDATED_BIRTH_DATE)
            .primaryPhone(UPDATED_PRIMARY_PHONE)
            .primaryEmail(UPDATED_PRIMARY_EMAIL)
            .isTemporaryPassword(UPDATED_IS_TEMPORARY_PASSWORD)
            .logoImageUrl(UPDATED_LOGO_IMAGE_URL)
            .profileImageUrl(UPDATED_PROFILE_IMAGE_URL)
            .notes(UPDATED_NOTES)
            .birthdate(UPDATED_BIRTHDATE)
            .dateOfJoining(UPDATED_DATE_OF_JOINING)
            .trainingCompletedDate(UPDATED_TRAINING_COMPLETED_DATE)
            .jdApprovedOn(UPDATED_JD_APPROVED_ON)
            .employeeId(UPDATED_EMPLOYEE_ID)
            .authString(UPDATED_AUTH_STRING)
            .userGroupString(UPDATED_USER_GROUP_STRING)
            .tanNo(UPDATED_TAN_NO)
            .panNo(UPDATED_PAN_NO)
            .gstNo(UPDATED_GST_NO)
            .aadharNo(UPDATED_AADHAR_NO);

        restPartyMockMvc.perform(put("/api/parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedParty)))
            .andExpect(status().isOk());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeUpdate);
        Party testParty = partyList.get(partyList.size() - 1);
        assertThat(testParty.isIsOrganisation()).isEqualTo(UPDATED_IS_ORGANISATION);
        assertThat(testParty.getOrganisationName()).isEqualTo(UPDATED_ORGANISATION_NAME);
        assertThat(testParty.getOrganisationShortName()).isEqualTo(UPDATED_ORGANISATION_SHORT_NAME);
        assertThat(testParty.getSalutation()).isEqualTo(UPDATED_SALUTATION);
        assertThat(testParty.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testParty.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testParty.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testParty.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testParty.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testParty.getPrimaryPhone()).isEqualTo(UPDATED_PRIMARY_PHONE);
        assertThat(testParty.getPrimaryEmail()).isEqualTo(UPDATED_PRIMARY_EMAIL);
        assertThat(testParty.isIsTemporaryPassword()).isEqualTo(UPDATED_IS_TEMPORARY_PASSWORD);
        assertThat(testParty.getLogoImageUrl()).isEqualTo(UPDATED_LOGO_IMAGE_URL);
        assertThat(testParty.getProfileImageUrl()).isEqualTo(UPDATED_PROFILE_IMAGE_URL);
        assertThat(testParty.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testParty.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testParty.getDateOfJoining()).isEqualTo(UPDATED_DATE_OF_JOINING);
        assertThat(testParty.getTrainingCompletedDate()).isEqualTo(UPDATED_TRAINING_COMPLETED_DATE);
        assertThat(testParty.getJdApprovedOn()).isEqualTo(UPDATED_JD_APPROVED_ON);
        assertThat(testParty.getEmployeeId()).isEqualTo(UPDATED_EMPLOYEE_ID);
        assertThat(testParty.getAuthString()).isEqualTo(UPDATED_AUTH_STRING);
        assertThat(testParty.getUserGroupString()).isEqualTo(UPDATED_USER_GROUP_STRING);
        assertThat(testParty.getTanNo()).isEqualTo(UPDATED_TAN_NO);
        assertThat(testParty.getPanNo()).isEqualTo(UPDATED_PAN_NO);
        assertThat(testParty.getGstNo()).isEqualTo(UPDATED_GST_NO);
        assertThat(testParty.getAadharNo()).isEqualTo(UPDATED_AADHAR_NO);
    }

    @Test
    @Transactional
    public void updateNonExistingParty() throws Exception {
        int databaseSizeBeforeUpdate = partyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyMockMvc.perform(put("/api/parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(party)))
            .andExpect(status().isBadRequest());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParty() throws Exception {
        // Initialize the database
        partyService.save(party);

        int databaseSizeBeforeDelete = partyRepository.findAll().size();

        // Delete the party
        restPartyMockMvc.perform(delete("/api/parties/{id}", party.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
