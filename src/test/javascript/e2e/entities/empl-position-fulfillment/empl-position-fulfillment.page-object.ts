import { element, by, ElementFinder } from 'protractor';

export class EmplPositionFulfillmentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-empl-position-fulfillment div table .btn-danger'));
  title = element.all(by.css('sys-empl-position-fulfillment div h2#page-heading span')).first();
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

export class EmplPositionFulfillmentUpdatePage {
  pageTitle = element(by.id('sys-empl-position-fulfillment-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fromDateInput = element(by.id('field_fromDate'));
  thruDateInput = element(by.id('field_thruDate'));
  commentsInput = element(by.id('field_comments'));

  emplPositionSelect = element(by.id('field_emplPosition'));
  partySelect = element(by.id('field_party'));
  reportingToSelect = element(by.id('field_reportingTo'));
  managedBySelect = element(by.id('field_managedBy'));

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

  async setCommentsInput(comments: string): Promise<void> {
    await this.commentsInput.sendKeys(comments);
  }

  async getCommentsInput(): Promise<string> {
    return await this.commentsInput.getAttribute('value');
  }

  async emplPositionSelectLastOption(): Promise<void> {
    await this.emplPositionSelect.all(by.tagName('option')).last().click();
  }

  async emplPositionSelectOption(option: string): Promise<void> {
    await this.emplPositionSelect.sendKeys(option);
  }

  getEmplPositionSelect(): ElementFinder {
    return this.emplPositionSelect;
  }

  async getEmplPositionSelectedOption(): Promise<string> {
    return await this.emplPositionSelect.element(by.css('option:checked')).getText();
  }

  async partySelectLastOption(): Promise<void> {
    await this.partySelect.all(by.tagName('option')).last().click();
  }

  async partySelectOption(option: string): Promise<void> {
    await this.partySelect.sendKeys(option);
  }

  getPartySelect(): ElementFinder {
    return this.partySelect;
  }

  async getPartySelectedOption(): Promise<string> {
    return await this.partySelect.element(by.css('option:checked')).getText();
  }

  async reportingToSelectLastOption(): Promise<void> {
    await this.reportingToSelect.all(by.tagName('option')).last().click();
  }

  async reportingToSelectOption(option: string): Promise<void> {
    await this.reportingToSelect.sendKeys(option);
  }

  getReportingToSelect(): ElementFinder {
    return this.reportingToSelect;
  }

  async getReportingToSelectedOption(): Promise<string> {
    return await this.reportingToSelect.element(by.css('option:checked')).getText();
  }

  async managedBySelectLastOption(): Promise<void> {
    await this.managedBySelect.all(by.tagName('option')).last().click();
  }

  async managedBySelectOption(option: string): Promise<void> {
    await this.managedBySelect.sendKeys(option);
  }

  getManagedBySelect(): ElementFinder {
    return this.managedBySelect;
  }

  async getManagedBySelectedOption(): Promise<string> {
    return await this.managedBySelect.element(by.css('option:checked')).getText();
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

export class EmplPositionFulfillmentDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-emplPositionFulfillment-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-emplPositionFulfillment'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
