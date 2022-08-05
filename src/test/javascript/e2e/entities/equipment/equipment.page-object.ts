import { element, by, ElementFinder } from 'protractor';

export class EquipmentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-equipment div table .btn-danger'));
  title = element.all(by.css('sys-equipment div h2#page-heading span')).first();
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

export class EquipmentUpdatePage {
  pageTitle = element(by.id('sys-equipment-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  equipmentNumberInput = element(by.id('field_equipmentNumber'));
  minCapacityRangeInput = element(by.id('field_minCapacityRange'));
  maxCapacityRangeInput = element(by.id('field_maxCapacityRange'));
  minOperationalRangeInput = element(by.id('field_minOperationalRange'));
  maxOperationalRangeInput = element(by.id('field_maxOperationalRange'));
  lastCalibratedDateInput = element(by.id('field_lastCalibratedDate'));
  calibrationDueDateInput = element(by.id('field_calibrationDueDate'));
  changeControlNoInput = element(by.id('field_changeControlNo'));

  equipmentTypeSelect = element(by.id('field_equipmentType'));
  statusSelect = element(by.id('field_status'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setEquipmentNumberInput(equipmentNumber: string): Promise<void> {
    await this.equipmentNumberInput.sendKeys(equipmentNumber);
  }

  async getEquipmentNumberInput(): Promise<string> {
    return await this.equipmentNumberInput.getAttribute('value');
  }

  async setMinCapacityRangeInput(minCapacityRange: string): Promise<void> {
    await this.minCapacityRangeInput.sendKeys(minCapacityRange);
  }

  async getMinCapacityRangeInput(): Promise<string> {
    return await this.minCapacityRangeInput.getAttribute('value');
  }

  async setMaxCapacityRangeInput(maxCapacityRange: string): Promise<void> {
    await this.maxCapacityRangeInput.sendKeys(maxCapacityRange);
  }

  async getMaxCapacityRangeInput(): Promise<string> {
    return await this.maxCapacityRangeInput.getAttribute('value');
  }

  async setMinOperationalRangeInput(minOperationalRange: string): Promise<void> {
    await this.minOperationalRangeInput.sendKeys(minOperationalRange);
  }

  async getMinOperationalRangeInput(): Promise<string> {
    return await this.minOperationalRangeInput.getAttribute('value');
  }

  async setMaxOperationalRangeInput(maxOperationalRange: string): Promise<void> {
    await this.maxOperationalRangeInput.sendKeys(maxOperationalRange);
  }

  async getMaxOperationalRangeInput(): Promise<string> {
    return await this.maxOperationalRangeInput.getAttribute('value');
  }

  async setLastCalibratedDateInput(lastCalibratedDate: string): Promise<void> {
    await this.lastCalibratedDateInput.sendKeys(lastCalibratedDate);
  }

  async getLastCalibratedDateInput(): Promise<string> {
    return await this.lastCalibratedDateInput.getAttribute('value');
  }

  async setCalibrationDueDateInput(calibrationDueDate: string): Promise<void> {
    await this.calibrationDueDateInput.sendKeys(calibrationDueDate);
  }

  async getCalibrationDueDateInput(): Promise<string> {
    return await this.calibrationDueDateInput.getAttribute('value');
  }

  async setChangeControlNoInput(changeControlNo: string): Promise<void> {
    await this.changeControlNoInput.sendKeys(changeControlNo);
  }

  async getChangeControlNoInput(): Promise<string> {
    return await this.changeControlNoInput.getAttribute('value');
  }

  async equipmentTypeSelectLastOption(): Promise<void> {
    await this.equipmentTypeSelect.all(by.tagName('option')).last().click();
  }

  async equipmentTypeSelectOption(option: string): Promise<void> {
    await this.equipmentTypeSelect.sendKeys(option);
  }

  getEquipmentTypeSelect(): ElementFinder {
    return this.equipmentTypeSelect;
  }

  async getEquipmentTypeSelectedOption(): Promise<string> {
    return await this.equipmentTypeSelect.element(by.css('option:checked')).getText();
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

export class EquipmentDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-equipment-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-equipment'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
