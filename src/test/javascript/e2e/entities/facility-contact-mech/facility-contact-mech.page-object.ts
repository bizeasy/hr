import { element, by, ElementFinder } from 'protractor';

export class FacilityContactMechComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-facility-contact-mech div table .btn-danger'));
  title = element.all(by.css('sys-facility-contact-mech div h2#page-heading span')).first();
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

export class FacilityContactMechUpdatePage {
  pageTitle = element(by.id('sys-facility-contact-mech-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fromDateInput = element(by.id('field_fromDate'));
  thruDateInput = element(by.id('field_thruDate'));

  facilitySelect = element(by.id('field_facility'));
  contactMechSelect = element(by.id('field_contactMech'));
  contactMechPurposeSelect = element(by.id('field_contactMechPurpose'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFromDateInput(fromDate: string): Promise<void> {
    await this.fromDateInput.sendKeys(fromDate);
  }

  async getFromDateInput(): Promise<string> {
    return await this.fromDateInput.getAttribute('value');
  }

  async setThruDateInput(thruDate: string): Promise<void> {
    await this.thruDateInput.sendKeys(thruDate);
  }

  async getThruDateInput(): Promise<string> {
    return await this.thruDateInput.getAttribute('value');
  }

  async facilitySelectLastOption(): Promise<void> {
    await this.facilitySelect.all(by.tagName('option')).last().click();
  }

  async facilitySelectOption(option: string): Promise<void> {
    await this.facilitySelect.sendKeys(option);
  }

  getFacilitySelect(): ElementFinder {
    return this.facilitySelect;
  }

  async getFacilitySelectedOption(): Promise<string> {
    return await this.facilitySelect.element(by.css('option:checked')).getText();
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

  async contactMechPurposeSelectLastOption(): Promise<void> {
    await this.contactMechPurposeSelect.all(by.tagName('option')).last().click();
  }

  async contactMechPurposeSelectOption(option: string): Promise<void> {
    await this.contactMechPurposeSelect.sendKeys(option);
  }

  getContactMechPurposeSelect(): ElementFinder {
    return this.contactMechPurposeSelect;
  }

  async getContactMechPurposeSelectedOption(): Promise<string> {
    return await this.contactMechPurposeSelect.element(by.css('option:checked')).getText();
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

export class FacilityContactMechDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-facilityContactMech-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-facilityContactMech'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
