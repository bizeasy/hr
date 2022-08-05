import { element, by, ElementFinder } from 'protractor';

export class EmploymentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-employment div table .btn-danger'));
  title = element.all(by.css('sys-employment div h2#page-heading span')).first();
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

export class EmploymentUpdatePage {
  pageTitle = element(by.id('sys-employment-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fromDateInput = element(by.id('field_fromDate'));
  thruDateInput = element(by.id('field_thruDate'));

  terminationReasonSelect = element(by.id('field_terminationReason'));
  terminationTypeSelect = element(by.id('field_terminationType'));
  employeeSelect = element(by.id('field_employee'));
  fromPartySelect = element(by.id('field_fromParty'));
  roleTypeFromSelect = element(by.id('field_roleTypeFrom'));
  roleTypeToSelect = element(by.id('field_roleTypeTo'));

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

  async terminationReasonSelectLastOption(): Promise<void> {
    await this.terminationReasonSelect.all(by.tagName('option')).last().click();
  }

  async terminationReasonSelectOption(option: string): Promise<void> {
    await this.terminationReasonSelect.sendKeys(option);
  }

  getTerminationReasonSelect(): ElementFinder {
    return this.terminationReasonSelect;
  }

  async getTerminationReasonSelectedOption(): Promise<string> {
    return await this.terminationReasonSelect.element(by.css('option:checked')).getText();
  }

  async terminationTypeSelectLastOption(): Promise<void> {
    await this.terminationTypeSelect.all(by.tagName('option')).last().click();
  }

  async terminationTypeSelectOption(option: string): Promise<void> {
    await this.terminationTypeSelect.sendKeys(option);
  }

  getTerminationTypeSelect(): ElementFinder {
    return this.terminationTypeSelect;
  }

  async getTerminationTypeSelectedOption(): Promise<string> {
    return await this.terminationTypeSelect.element(by.css('option:checked')).getText();
  }

  async employeeSelectLastOption(): Promise<void> {
    await this.employeeSelect.all(by.tagName('option')).last().click();
  }

  async employeeSelectOption(option: string): Promise<void> {
    await this.employeeSelect.sendKeys(option);
  }

  getEmployeeSelect(): ElementFinder {
    return this.employeeSelect;
  }

  async getEmployeeSelectedOption(): Promise<string> {
    return await this.employeeSelect.element(by.css('option:checked')).getText();
  }

  async fromPartySelectLastOption(): Promise<void> {
    await this.fromPartySelect.all(by.tagName('option')).last().click();
  }

  async fromPartySelectOption(option: string): Promise<void> {
    await this.fromPartySelect.sendKeys(option);
  }

  getFromPartySelect(): ElementFinder {
    return this.fromPartySelect;
  }

  async getFromPartySelectedOption(): Promise<string> {
    return await this.fromPartySelect.element(by.css('option:checked')).getText();
  }

  async roleTypeFromSelectLastOption(): Promise<void> {
    await this.roleTypeFromSelect.all(by.tagName('option')).last().click();
  }

  async roleTypeFromSelectOption(option: string): Promise<void> {
    await this.roleTypeFromSelect.sendKeys(option);
  }

  getRoleTypeFromSelect(): ElementFinder {
    return this.roleTypeFromSelect;
  }

  async getRoleTypeFromSelectedOption(): Promise<string> {
    return await this.roleTypeFromSelect.element(by.css('option:checked')).getText();
  }

  async roleTypeToSelectLastOption(): Promise<void> {
    await this.roleTypeToSelect.all(by.tagName('option')).last().click();
  }

  async roleTypeToSelectOption(option: string): Promise<void> {
    await this.roleTypeToSelect.sendKeys(option);
  }

  getRoleTypeToSelect(): ElementFinder {
    return this.roleTypeToSelect;
  }

  async getRoleTypeToSelectedOption(): Promise<string> {
    return await this.roleTypeToSelect.element(by.css('option:checked')).getText();
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

export class EmploymentDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-employment-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-employment'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
