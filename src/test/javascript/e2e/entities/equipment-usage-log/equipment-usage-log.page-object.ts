import { element, by, ElementFinder } from 'protractor';

export class EquipmentUsageLogComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-equipment-usage-log div table .btn-danger'));
  title = element.all(by.css('sys-equipment-usage-log div h2#page-heading span')).first();
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

export class EquipmentUsageLogUpdatePage {
  pageTitle = element(by.id('sys-equipment-usage-log-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fromTimeInput = element(by.id('field_fromTime'));
  toTimeInput = element(by.id('field_toTime'));
  remarksInput = element(by.id('field_remarks'));

  equipmentSelect = element(by.id('field_equipment'));
  cleanedBySelect = element(by.id('field_cleanedBy'));
  checkedBySelect = element(by.id('field_checkedBy'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFromTimeInput(fromTime: string): Promise<void> {
    await this.fromTimeInput.sendKeys(fromTime);
  }

  async getFromTimeInput(): Promise<string> {
    return await this.fromTimeInput.getAttribute('value');
  }

  async setToTimeInput(toTime: string): Promise<void> {
    await this.toTimeInput.sendKeys(toTime);
  }

  async getToTimeInput(): Promise<string> {
    return await this.toTimeInput.getAttribute('value');
  }

  async setRemarksInput(remarks: string): Promise<void> {
    await this.remarksInput.sendKeys(remarks);
  }

  async getRemarksInput(): Promise<string> {
    return await this.remarksInput.getAttribute('value');
  }

  async equipmentSelectLastOption(): Promise<void> {
    await this.equipmentSelect.all(by.tagName('option')).last().click();
  }

  async equipmentSelectOption(option: string): Promise<void> {
    await this.equipmentSelect.sendKeys(option);
  }

  getEquipmentSelect(): ElementFinder {
    return this.equipmentSelect;
  }

  async getEquipmentSelectedOption(): Promise<string> {
    return await this.equipmentSelect.element(by.css('option:checked')).getText();
  }

  async cleanedBySelectLastOption(): Promise<void> {
    await this.cleanedBySelect.all(by.tagName('option')).last().click();
  }

  async cleanedBySelectOption(option: string): Promise<void> {
    await this.cleanedBySelect.sendKeys(option);
  }

  getCleanedBySelect(): ElementFinder {
    return this.cleanedBySelect;
  }

  async getCleanedBySelectedOption(): Promise<string> {
    return await this.cleanedBySelect.element(by.css('option:checked')).getText();
  }

  async checkedBySelectLastOption(): Promise<void> {
    await this.checkedBySelect.all(by.tagName('option')).last().click();
  }

  async checkedBySelectOption(option: string): Promise<void> {
    await this.checkedBySelect.sendKeys(option);
  }

  getCheckedBySelect(): ElementFinder {
    return this.checkedBySelect;
  }

  async getCheckedBySelectedOption(): Promise<string> {
    return await this.checkedBySelect.element(by.css('option:checked')).getText();
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

export class EquipmentUsageLogDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-equipmentUsageLog-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-equipmentUsageLog'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
