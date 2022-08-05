import { element, by, ElementFinder } from 'protractor';

export class WorkEffortAssocComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-work-effort-assoc div table .btn-danger'));
  title = element.all(by.css('sys-work-effort-assoc div h2#page-heading span')).first();
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

export class WorkEffortAssocUpdatePage {
  pageTitle = element(by.id('sys-work-effort-assoc-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  sequenceNoInput = element(by.id('field_sequenceNo'));
  fromDateInput = element(by.id('field_fromDate'));
  thruDateInput = element(by.id('field_thruDate'));

  typeSelect = element(by.id('field_type'));
  weIdFromSelect = element(by.id('field_weIdFrom'));
  weIdToSelect = element(by.id('field_weIdTo'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSequenceNoInput(sequenceNo: string): Promise<void> {
    await this.sequenceNoInput.sendKeys(sequenceNo);
  }

  async getSequenceNoInput(): Promise<string> {
    return await this.sequenceNoInput.getAttribute('value');
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

  async typeSelectLastOption(): Promise<void> {
    await this.typeSelect.all(by.tagName('option')).last().click();
  }

  async typeSelectOption(option: string): Promise<void> {
    await this.typeSelect.sendKeys(option);
  }

  getTypeSelect(): ElementFinder {
    return this.typeSelect;
  }

  async getTypeSelectedOption(): Promise<string> {
    return await this.typeSelect.element(by.css('option:checked')).getText();
  }

  async weIdFromSelectLastOption(): Promise<void> {
    await this.weIdFromSelect.all(by.tagName('option')).last().click();
  }

  async weIdFromSelectOption(option: string): Promise<void> {
    await this.weIdFromSelect.sendKeys(option);
  }

  getWeIdFromSelect(): ElementFinder {
    return this.weIdFromSelect;
  }

  async getWeIdFromSelectedOption(): Promise<string> {
    return await this.weIdFromSelect.element(by.css('option:checked')).getText();
  }

  async weIdToSelectLastOption(): Promise<void> {
    await this.weIdToSelect.all(by.tagName('option')).last().click();
  }

  async weIdToSelectOption(option: string): Promise<void> {
    await this.weIdToSelect.sendKeys(option);
  }

  getWeIdToSelect(): ElementFinder {
    return this.weIdToSelect;
  }

  async getWeIdToSelectedOption(): Promise<string> {
    return await this.weIdToSelect.element(by.css('option:checked')).getText();
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

export class WorkEffortAssocDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-workEffortAssoc-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-workEffortAssoc'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
