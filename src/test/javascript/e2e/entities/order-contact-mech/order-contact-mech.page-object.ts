import { element, by, ElementFinder } from 'protractor';

export class OrderContactMechComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-order-contact-mech div table .btn-danger'));
  title = element.all(by.css('sys-order-contact-mech div h2#page-heading span')).first();
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

export class OrderContactMechUpdatePage {
  pageTitle = element(by.id('sys-order-contact-mech-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fromDateInput = element(by.id('field_fromDate'));
  thruDateInput = element(by.id('field_thruDate'));

  orderSelect = element(by.id('field_order'));
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

  async orderSelectLastOption(): Promise<void> {
    await this.orderSelect.all(by.tagName('option')).last().click();
  }

  async orderSelectOption(option: string): Promise<void> {
    await this.orderSelect.sendKeys(option);
  }

  getOrderSelect(): ElementFinder {
    return this.orderSelect;
  }

  async getOrderSelectedOption(): Promise<string> {
    return await this.orderSelect.element(by.css('option:checked')).getText();
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

export class OrderContactMechDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-orderContactMech-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-orderContactMech'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
