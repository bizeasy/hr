import { element, by, ElementFinder } from 'protractor';

export class PostalAddressComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-postal-address div table .btn-danger'));
  title = element.all(by.css('sys-postal-address div h2#page-heading span')).first();
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

export class PostalAddressUpdatePage {
  pageTitle = element(by.id('sys-postal-address-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  toNameInput = element(by.id('field_toName'));
  address1Input = element(by.id('field_address1'));
  address2Input = element(by.id('field_address2'));
  cityInput = element(by.id('field_city'));
  landmarkInput = element(by.id('field_landmark'));
  postalCodeInput = element(by.id('field_postalCode'));
  isDefaultInput = element(by.id('field_isDefault'));
  customAddressTypeInput = element(by.id('field_customAddressType'));
  stateStrInput = element(by.id('field_stateStr'));
  countryStrInput = element(by.id('field_countryStr'));
  isIndianAddressInput = element(by.id('field_isIndianAddress'));
  noteInput = element(by.id('field_note'));
  directionsInput = element(by.id('field_directions'));

  stateSelect = element(by.id('field_state'));
  pincodeSelect = element(by.id('field_pincode'));
  countrySelect = element(by.id('field_country'));
  contactMechSelect = element(by.id('field_contactMech'));
  geoPointSelect = element(by.id('field_geoPoint'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setToNameInput(toName: string): Promise<void> {
    await this.toNameInput.sendKeys(toName);
  }

  async getToNameInput(): Promise<string> {
    return await this.toNameInput.getAttribute('value');
  }

  async setAddress1Input(address1: string): Promise<void> {
    await this.address1Input.sendKeys(address1);
  }

  async getAddress1Input(): Promise<string> {
    return await this.address1Input.getAttribute('value');
  }

  async setAddress2Input(address2: string): Promise<void> {
    await this.address2Input.sendKeys(address2);
  }

  async getAddress2Input(): Promise<string> {
    return await this.address2Input.getAttribute('value');
  }

  async setCityInput(city: string): Promise<void> {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput(): Promise<string> {
    return await this.cityInput.getAttribute('value');
  }

  async setLandmarkInput(landmark: string): Promise<void> {
    await this.landmarkInput.sendKeys(landmark);
  }

  async getLandmarkInput(): Promise<string> {
    return await this.landmarkInput.getAttribute('value');
  }

  async setPostalCodeInput(postalCode: string): Promise<void> {
    await this.postalCodeInput.sendKeys(postalCode);
  }

  async getPostalCodeInput(): Promise<string> {
    return await this.postalCodeInput.getAttribute('value');
  }

  getIsDefaultInput(): ElementFinder {
    return this.isDefaultInput;
  }

  async setCustomAddressTypeInput(customAddressType: string): Promise<void> {
    await this.customAddressTypeInput.sendKeys(customAddressType);
  }

  async getCustomAddressTypeInput(): Promise<string> {
    return await this.customAddressTypeInput.getAttribute('value');
  }

  async setStateStrInput(stateStr: string): Promise<void> {
    await this.stateStrInput.sendKeys(stateStr);
  }

  async getStateStrInput(): Promise<string> {
    return await this.stateStrInput.getAttribute('value');
  }

  async setCountryStrInput(countryStr: string): Promise<void> {
    await this.countryStrInput.sendKeys(countryStr);
  }

  async getCountryStrInput(): Promise<string> {
    return await this.countryStrInput.getAttribute('value');
  }

  getIsIndianAddressInput(): ElementFinder {
    return this.isIndianAddressInput;
  }

  async setNoteInput(note: string): Promise<void> {
    await this.noteInput.sendKeys(note);
  }

  async getNoteInput(): Promise<string> {
    return await this.noteInput.getAttribute('value');
  }

  async setDirectionsInput(directions: string): Promise<void> {
    await this.directionsInput.sendKeys(directions);
  }

  async getDirectionsInput(): Promise<string> {
    return await this.directionsInput.getAttribute('value');
  }

  async stateSelectLastOption(): Promise<void> {
    await this.stateSelect.all(by.tagName('option')).last().click();
  }

  async stateSelectOption(option: string): Promise<void> {
    await this.stateSelect.sendKeys(option);
  }

  getStateSelect(): ElementFinder {
    return this.stateSelect;
  }

  async getStateSelectedOption(): Promise<string> {
    return await this.stateSelect.element(by.css('option:checked')).getText();
  }

  async pincodeSelectLastOption(): Promise<void> {
    await this.pincodeSelect.all(by.tagName('option')).last().click();
  }

  async pincodeSelectOption(option: string): Promise<void> {
    await this.pincodeSelect.sendKeys(option);
  }

  getPincodeSelect(): ElementFinder {
    return this.pincodeSelect;
  }

  async getPincodeSelectedOption(): Promise<string> {
    return await this.pincodeSelect.element(by.css('option:checked')).getText();
  }

  async countrySelectLastOption(): Promise<void> {
    await this.countrySelect.all(by.tagName('option')).last().click();
  }

  async countrySelectOption(option: string): Promise<void> {
    await this.countrySelect.sendKeys(option);
  }

  getCountrySelect(): ElementFinder {
    return this.countrySelect;
  }

  async getCountrySelectedOption(): Promise<string> {
    return await this.countrySelect.element(by.css('option:checked')).getText();
  }

  async contactMechSelectLastOption(): Promise<void> {
    await this.contactMechSelect.all(by.tagName('option')).last().click();
  }

  async contactMechSelectOption(option: string): Promise<void> {
    await this.contactMechSelect.sendKeys(option);
  }

  getContactMechSelect(): ElementFinder {
    return this.contactMechSelect;
  }

  async getContactMechSelectedOption(): Promise<string> {
    return await this.contactMechSelect.element(by.css('option:checked')).getText();
  }

  async geoPointSelectLastOption(): Promise<void> {
    await this.geoPointSelect.all(by.tagName('option')).last().click();
  }

  async geoPointSelectOption(option: string): Promise<void> {
    await this.geoPointSelect.sendKeys(option);
  }

  getGeoPointSelect(): ElementFinder {
    return this.geoPointSelect;
  }

  async getGeoPointSelectedOption(): Promise<string> {
    return await this.geoPointSelect.element(by.css('option:checked')).getText();
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

export class PostalAddressDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-postalAddress-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-postalAddress'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
