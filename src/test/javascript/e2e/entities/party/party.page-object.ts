import { element, by, ElementFinder } from 'protractor';

export class PartyComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-party div table .btn-danger'));
  title = element.all(by.css('sys-party div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class PartyUpdatePage {
  pageTitle = element(by.id('sys-party-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  isOrganisationInput = element(by.id('field_isOrganisation'));
  organisationNameInput = element(by.id('field_organisationName'));
  organisationShortNameInput = element(by.id('field_organisationShortName'));
  salutationInput = element(by.id('field_salutation'));
  firstNameInput = element(by.id('field_firstName'));
  middleNameInput = element(by.id('field_middleName'));
  lastNameInput = element(by.id('field_lastName'));
  genderSelect = element(by.id('field_gender'));
  birthDateInput = element(by.id('field_birthDate'));
  primaryPhoneInput = element(by.id('field_primaryPhone'));
  primaryEmailInput = element(by.id('field_primaryEmail'));
  isTemporaryPasswordInput = element(by.id('field_isTemporaryPassword'));
  logoImageUrlInput = element(by.id('field_logoImageUrl'));
  profileImageUrlInput = element(by.id('field_profileImageUrl'));
  notesInput = element(by.id('field_notes'));
  birthdateInput = element(by.id('field_birthdate'));
  dateOfJoiningInput = element(by.id('field_dateOfJoining'));
  trainingCompletedDateInput = element(by.id('field_trainingCompletedDate'));
  jdApprovedOnInput = element(by.id('field_jdApprovedOn'));
  employeeIdInput = element(by.id('field_employeeId'));
  authStringInput = element(by.id('field_authString'));
  userGroupStringInput = element(by.id('field_userGroupString'));
  tanNoInput = element(by.id('field_tanNo'));
  panNoInput = element(by.id('field_panNo'));
  gstNoInput = element(by.id('field_gstNo'));
  aadharNoInput = element(by.id('field_aadharNo'));

  userSelect = element(by.id('field_user'));
  partyTypeSelect = element(by.id('field_partyType'));
  statusSelect = element(by.id('field_status'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  getIsOrganisationInput(): ElementFinder {
    return this.isOrganisationInput;
  }

  async setOrganisationNameInput(organisationName: string): Promise<void> {
    await this.organisationNameInput.sendKeys(organisationName);
  }

  async getOrganisationNameInput(): Promise<string> {
    return await this.organisationNameInput.getAttribute('value');
  }

  async setOrganisationShortNameInput(organisationShortName: string): Promise<void> {
    await this.organisationShortNameInput.sendKeys(organisationShortName);
  }

  async getOrganisationShortNameInput(): Promise<string> {
    return await this.organisationShortNameInput.getAttribute('value');
  }

  async setSalutationInput(salutation: string): Promise<void> {
    await this.salutationInput.sendKeys(salutation);
  }

  async getSalutationInput(): Promise<string> {
    return await this.salutationInput.getAttribute('value');
  }

  async setFirstNameInput(firstName: string): Promise<void> {
    await this.firstNameInput.sendKeys(firstName);
  }

  async getFirstNameInput(): Promise<string> {
    return await this.firstNameInput.getAttribute('value');
  }

  async setMiddleNameInput(middleName: string): Promise<void> {
    await this.middleNameInput.sendKeys(middleName);
  }

  async getMiddleNameInput(): Promise<string> {
    return await this.middleNameInput.getAttribute('value');
  }

  async setLastNameInput(lastName: string): Promise<void> {
    await this.lastNameInput.sendKeys(lastName);
  }

  async getLastNameInput(): Promise<string> {
    return await this.lastNameInput.getAttribute('value');
  }

  async setGenderSelect(gender: string): Promise<void> {
    await this.genderSelect.sendKeys(gender);
  }

  async getGenderSelect(): Promise<string> {
    return await this.genderSelect.element(by.css('option:checked')).getText();
  }

  async genderSelectLastOption(): Promise<void> {
    await this.genderSelect.all(by.tagName('option')).last().click();
  }

  async setBirthDateInput(birthDate: string): Promise<void> {
    await this.birthDateInput.sendKeys(birthDate);
  }

  async getBirthDateInput(): Promise<string> {
    return await this.birthDateInput.getAttribute('value');
  }

  async setPrimaryPhoneInput(primaryPhone: string): Promise<void> {
    await this.primaryPhoneInput.sendKeys(primaryPhone);
  }

  async getPrimaryPhoneInput(): Promise<string> {
    return await this.primaryPhoneInput.getAttribute('value');
  }

  async setPrimaryEmailInput(primaryEmail: string): Promise<void> {
    await this.primaryEmailInput.sendKeys(primaryEmail);
  }

  async getPrimaryEmailInput(): Promise<string> {
    return await this.primaryEmailInput.getAttribute('value');
  }

  getIsTemporaryPasswordInput(): ElementFinder {
    return this.isTemporaryPasswordInput;
  }

  async setLogoImageUrlInput(logoImageUrl: string): Promise<void> {
    await this.logoImageUrlInput.sendKeys(logoImageUrl);
  }

  async getLogoImageUrlInput(): Promise<string> {
    return await this.logoImageUrlInput.getAttribute('value');
  }

  async setProfileImageUrlInput(profileImageUrl: string): Promise<void> {
    await this.profileImageUrlInput.sendKeys(profileImageUrl);
  }

  async getProfileImageUrlInput(): Promise<string> {
    return await this.profileImageUrlInput.getAttribute('value');
  }

  async setNotesInput(notes: string): Promise<void> {
    await this.notesInput.sendKeys(notes);
  }

  async getNotesInput(): Promise<string> {
    return await this.notesInput.getAttribute('value');
  }

  async setBirthdateInput(birthdate: string): Promise<void> {
    await this.birthdateInput.sendKeys(birthdate);
  }

  async getBirthdateInput(): Promise<string> {
    return await this.birthdateInput.getAttribute('value');
  }

  async setDateOfJoiningInput(dateOfJoining: string): Promise<void> {
    await this.dateOfJoiningInput.sendKeys(dateOfJoining);
  }

  async getDateOfJoiningInput(): Promise<string> {
    return await this.dateOfJoiningInput.getAttribute('value');
  }

  async setTrainingCompletedDateInput(trainingCompletedDate: string): Promise<void> {
    await this.trainingCompletedDateInput.sendKeys(trainingCompletedDate);
  }

  async getTrainingCompletedDateInput(): Promise<string> {
    return await this.trainingCompletedDateInput.getAttribute('value');
  }

  async setJdApprovedOnInput(jdApprovedOn: string): Promise<void> {
    await this.jdApprovedOnInput.sendKeys(jdApprovedOn);
  }

  async getJdApprovedOnInput(): Promise<string> {
    return await this.jdApprovedOnInput.getAttribute('value');
  }

  async setEmployeeIdInput(employeeId: string): Promise<void> {
    await this.employeeIdInput.sendKeys(employeeId);
  }

  async getEmployeeIdInput(): Promise<string> {
    return await this.employeeIdInput.getAttribute('value');
  }

  async setAuthStringInput(authString: string): Promise<void> {
    await this.authStringInput.sendKeys(authString);
  }

  async getAuthStringInput(): Promise<string> {
    return await this.authStringInput.getAttribute('value');
  }

  async setUserGroupStringInput(userGroupString: string): Promise<void> {
    await this.userGroupStringInput.sendKeys(userGroupString);
  }

  async getUserGroupStringInput(): Promise<string> {
    return await this.userGroupStringInput.getAttribute('value');
  }

  async setTanNoInput(tanNo: string): Promise<void> {
    await this.tanNoInput.sendKeys(tanNo);
  }

  async getTanNoInput(): Promise<string> {
    return await this.tanNoInput.getAttribute('value');
  }

  async setPanNoInput(panNo: string): Promise<void> {
    await this.panNoInput.sendKeys(panNo);
  }

  async getPanNoInput(): Promise<string> {
    return await this.panNoInput.getAttribute('value');
  }

  async setGstNoInput(gstNo: string): Promise<void> {
    await this.gstNoInput.sendKeys(gstNo);
  }

  async getGstNoInput(): Promise<string> {
    return await this.gstNoInput.getAttribute('value');
  }

  async setAadharNoInput(aadharNo: string): Promise<void> {
    await this.aadharNoInput.sendKeys(aadharNo);
  }

  async getAadharNoInput(): Promise<string> {
    return await this.aadharNoInput.getAttribute('value');
  }

  async userSelectLastOption(): Promise<void> {
    await this.userSelect.all(by.tagName('option')).last().click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
  }

  async partyTypeSelectLastOption(): Promise<void> {
    await this.partyTypeSelect.all(by.tagName('option')).last().click();
  }

  async partyTypeSelectOption(option: string): Promise<void> {
    await this.partyTypeSelect.sendKeys(option);
  }

  getPartyTypeSelect(): ElementFinder {
    return this.partyTypeSelect;
  }

  async getPartyTypeSelectedOption(): Promise<string> {
    return await this.partyTypeSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption(): Promise<void> {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }

  async statusSelectOption(option: string): Promise<void> {
    await this.statusSelect.sendKeys(option);
  }

  getStatusSelect(): ElementFinder {
    return this.statusSelect;
  }

  async getStatusSelectedOption(): Promise<string> {
    return await this.statusSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class PartyDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-party-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-party'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
