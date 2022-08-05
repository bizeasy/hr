import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PartyComponentsPage, PartyDeleteDialog, PartyUpdatePage } from './party.page-object';

const expect = chai.expect;

describe('Party e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let partyComponentsPage: PartyComponentsPage;
  let partyUpdatePage: PartyUpdatePage;
  let partyDeleteDialog: PartyDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Parties', async () => {
    await navBarPage.goToEntity('party');
    partyComponentsPage = new PartyComponentsPage();
    await browser.wait(ec.visibilityOf(partyComponentsPage.title), 5000);
    expect(await partyComponentsPage.getTitle()).to.eq('hrApp.party.home.title');
    await browser.wait(ec.or(ec.visibilityOf(partyComponentsPage.entities), ec.visibilityOf(partyComponentsPage.noResult)), 1000);
  });

  it('should load create Party page', async () => {
    await partyComponentsPage.clickOnCreateButton();
    partyUpdatePage = new PartyUpdatePage();
    expect(await partyUpdatePage.getPageTitle()).to.eq('hrApp.party.home.createOrEditLabel');
    await partyUpdatePage.cancel();
  });

  it('should create and save Parties', async () => {
    const nbButtonsBeforeCreate = await partyComponentsPage.countDeleteButtons();

    await partyComponentsPage.clickOnCreateButton();

    await promise.all([
      partyUpdatePage.setOrganisationNameInput('organisationName'),
      partyUpdatePage.setOrganisationShortNameInput('organisationShortName'),
      partyUpdatePage.setSalutationInput('salutation'),
      partyUpdatePage.setFirstNameInput('firstName'),
      partyUpdatePage.setMiddleNameInput('middleName'),
      partyUpdatePage.setLastNameInput('lastName'),
      partyUpdatePage.genderSelectLastOption(),
      partyUpdatePage.setBirthDateInput('2000-12-31'),
      partyUpdatePage.setPrimaryPhoneInput('primaryPhone'),
      partyUpdatePage.setPrimaryEmailInput('primaryEmail'),
      partyUpdatePage.setLogoImageUrlInput('logoImageUrl'),
      partyUpdatePage.setProfileImageUrlInput('profileImageUrl'),
      partyUpdatePage.setNotesInput('notes'),
      partyUpdatePage.setBirthdateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      partyUpdatePage.setDateOfJoiningInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      partyUpdatePage.setTrainingCompletedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      partyUpdatePage.setJdApprovedOnInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      partyUpdatePage.setEmployeeIdInput('employeeId'),
      partyUpdatePage.setAuthStringInput('authString'),
      partyUpdatePage.setUserGroupStringInput('userGroupString'),
      partyUpdatePage.setTanNoInput('tanNo'),
      partyUpdatePage.setPanNoInput('panNo'),
      partyUpdatePage.setGstNoInput('gstNo'),
      partyUpdatePage.setAadharNoInput('aadharNo'),
      partyUpdatePage.userSelectLastOption(),
      partyUpdatePage.partyTypeSelectLastOption(),
      partyUpdatePage.statusSelectLastOption(),
    ]);

    const selectedIsOrganisation = partyUpdatePage.getIsOrganisationInput();
    if (await selectedIsOrganisation.isSelected()) {
      await partyUpdatePage.getIsOrganisationInput().click();
      expect(await partyUpdatePage.getIsOrganisationInput().isSelected(), 'Expected isOrganisation not to be selected').to.be.false;
    } else {
      await partyUpdatePage.getIsOrganisationInput().click();
      expect(await partyUpdatePage.getIsOrganisationInput().isSelected(), 'Expected isOrganisation to be selected').to.be.true;
    }
    expect(await partyUpdatePage.getOrganisationNameInput()).to.eq(
      'organisationName',
      'Expected OrganisationName value to be equals to organisationName'
    );
    expect(await partyUpdatePage.getOrganisationShortNameInput()).to.eq(
      'organisationShortName',
      'Expected OrganisationShortName value to be equals to organisationShortName'
    );
    expect(await partyUpdatePage.getSalutationInput()).to.eq('salutation', 'Expected Salutation value to be equals to salutation');
    expect(await partyUpdatePage.getFirstNameInput()).to.eq('firstName', 'Expected FirstName value to be equals to firstName');
    expect(await partyUpdatePage.getMiddleNameInput()).to.eq('middleName', 'Expected MiddleName value to be equals to middleName');
    expect(await partyUpdatePage.getLastNameInput()).to.eq('lastName', 'Expected LastName value to be equals to lastName');
    expect(await partyUpdatePage.getBirthDateInput()).to.eq('2000-12-31', 'Expected birthDate value to be equals to 2000-12-31');
    expect(await partyUpdatePage.getPrimaryPhoneInput()).to.eq('primaryPhone', 'Expected PrimaryPhone value to be equals to primaryPhone');
    expect(await partyUpdatePage.getPrimaryEmailInput()).to.eq('primaryEmail', 'Expected PrimaryEmail value to be equals to primaryEmail');
    const selectedIsTemporaryPassword = partyUpdatePage.getIsTemporaryPasswordInput();
    if (await selectedIsTemporaryPassword.isSelected()) {
      await partyUpdatePage.getIsTemporaryPasswordInput().click();
      expect(await partyUpdatePage.getIsTemporaryPasswordInput().isSelected(), 'Expected isTemporaryPassword not to be selected').to.be
        .false;
    } else {
      await partyUpdatePage.getIsTemporaryPasswordInput().click();
      expect(await partyUpdatePage.getIsTemporaryPasswordInput().isSelected(), 'Expected isTemporaryPassword to be selected').to.be.true;
    }
    expect(await partyUpdatePage.getLogoImageUrlInput()).to.eq('logoImageUrl', 'Expected LogoImageUrl value to be equals to logoImageUrl');
    expect(await partyUpdatePage.getProfileImageUrlInput()).to.eq(
      'profileImageUrl',
      'Expected ProfileImageUrl value to be equals to profileImageUrl'
    );
    expect(await partyUpdatePage.getNotesInput()).to.eq('notes', 'Expected Notes value to be equals to notes');
    expect(await partyUpdatePage.getBirthdateInput()).to.contain('2001-01-01T02:30', 'Expected birthdate value to be equals to 2000-12-31');
    expect(await partyUpdatePage.getDateOfJoiningInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateOfJoining value to be equals to 2000-12-31'
    );
    expect(await partyUpdatePage.getTrainingCompletedDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected trainingCompletedDate value to be equals to 2000-12-31'
    );
    expect(await partyUpdatePage.getJdApprovedOnInput()).to.contain(
      '2001-01-01T02:30',
      'Expected jdApprovedOn value to be equals to 2000-12-31'
    );
    expect(await partyUpdatePage.getEmployeeIdInput()).to.eq('employeeId', 'Expected EmployeeId value to be equals to employeeId');
    expect(await partyUpdatePage.getAuthStringInput()).to.eq('authString', 'Expected AuthString value to be equals to authString');
    expect(await partyUpdatePage.getUserGroupStringInput()).to.eq(
      'userGroupString',
      'Expected UserGroupString value to be equals to userGroupString'
    );
    expect(await partyUpdatePage.getTanNoInput()).to.eq('tanNo', 'Expected TanNo value to be equals to tanNo');
    expect(await partyUpdatePage.getPanNoInput()).to.eq('panNo', 'Expected PanNo value to be equals to panNo');
    expect(await partyUpdatePage.getGstNoInput()).to.eq('gstNo', 'Expected GstNo value to be equals to gstNo');
    expect(await partyUpdatePage.getAadharNoInput()).to.eq('aadharNo', 'Expected AadharNo value to be equals to aadharNo');

    await partyUpdatePage.save();
    expect(await partyUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await partyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Party', async () => {
    const nbButtonsBeforeDelete = await partyComponentsPage.countDeleteButtons();
    await partyComponentsPage.clickOnLastDeleteButton();

    partyDeleteDialog = new PartyDeleteDialog();
    expect(await partyDeleteDialog.getDialogTitle()).to.eq('hrApp.party.delete.question');
    await partyDeleteDialog.clickOnConfirmButton();

    expect(await partyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
