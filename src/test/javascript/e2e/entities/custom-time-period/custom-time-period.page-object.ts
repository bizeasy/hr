import { element, by, ElementFinder } from 'protractor';

export class CustomTimePeriodComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-custom-time-period div table .btn-danger'));
  title = element.all(by.css('sys-custom-time-period div h2#page-heading span')).first();
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

export class CustomTimePeriodUpdatePage {
  pageTitle = element(by.id('sys-custom-time-period-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fromDateInput = element(by.id('field_fromDate'));
  thruDateInput = element(by.id('field_thruDate'));
  isClosedInput = element(by.id('field_isClosed'));
  periodNameInput = element(by.id('field_periodName'));
  periodNumInput = element(by.id('field_periodNum'));

  periodTypeSelect = element(by.id('field_periodType'));
  organisationPartySelect = element(by.id('field_organisationParty'));
  parentSelect = element(by.id('field_parent'));

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

  getIsClosedInput(): ElementFinder {
    return this.isClosedInput;
  }

  async setPeriodNameInput(periodName: string): Promise<void> {
    await this.periodNameInput.sendKeys(periodName);
  }

  async getPeriodNameInput(): Promise<string> {
    return await this.periodNameInput.getAttribute('value');
  }

  async setPeriodNumInput(periodNum: string): Promise<void> {
    await this.periodNumInput.sendKeys(periodNum);
  }

  async getPeriodNumInput(): Promise<string> {
    return await this.periodNumInput.getAttribute('value');
  }

  async periodTypeSelectLastOption(): Promise<void> {
    await this.periodTypeSelect.all(by.tagName('option')).last().click();
  }

  async periodTypeSelectOption(option: string): Promise<void> {
    await this.periodTypeSelect.sendKeys(option);
  }

  getPeriodTypeSelect(): ElementFinder {
    return this.periodTypeSelect;
  }

  async getPeriodTypeSelectedOption(): Promise<string> {
    return await this.periodTypeSelect.element(by.css('option:checked')).getText();
  }

  async organisationPartySelectLastOption(): Promise<void> {
    await this.organisationPartySelect.all(by.tagName('option')).last().click();
  }

  async organisationPartySelectOption(option: string): Promise<void> {
    await this.organisationPartySelect.sendKeys(option);
  }

  getOrganisationPartySelect(): ElementFinder {
    return this.organisationPartySelect;
  }

  async getOrganisationPartySelectedOption(): Promise<string> {
    return await this.organisationPartySelect.element(by.css('option:checked')).getText();
  }

  async parentSelectLastOption(): Promise<void> {
    await this.parentSelect.all(by.tagName('option')).last().click();
  }

  async parentSelectOption(option: string): Promise<void> {
    await this.parentSelect.sendKeys(option);
  }

  getParentSelect(): ElementFinder {
    return this.parentSelect;
  }

  async getParentSelectedOption(): Promise<string> {
    return await this.parentSelect.element(by.css('option:checked')).getText();
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

export class CustomTimePeriodDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-customTimePeriod-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-customTimePeriod'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
