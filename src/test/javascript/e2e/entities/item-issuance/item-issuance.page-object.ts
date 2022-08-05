import { element, by, ElementFinder } from 'protractor';

export class ItemIssuanceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-item-issuance div table .btn-danger'));
  title = element.all(by.css('sys-item-issuance div h2#page-heading span')).first();
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

export class ItemIssuanceUpdatePage {
  pageTitle = element(by.id('sys-item-issuance-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  messageInput = element(by.id('field_message'));
  issuedDateInput = element(by.id('field_issuedDate'));
  issuedByInput = element(by.id('field_issuedBy'));
  quantityInput = element(by.id('field_quantity'));
  cancelQuantityInput = element(by.id('field_cancelQuantity'));
  fromDateInput = element(by.id('field_fromDate'));
  thruDateInput = element(by.id('field_thruDate'));
  equipmentIdInput = element(by.id('field_equipmentId'));

  orderSelect = element(by.id('field_order'));
  orderItemSelect = element(by.id('field_orderItem'));
  inventoryItemSelect = element(by.id('field_inventoryItem'));
  issuedByUserLoginSelect = element(by.id('field_issuedByUserLogin'));
  varianceReasonSelect = element(by.id('field_varianceReason'));
  facilitySelect = element(by.id('field_facility'));
  statusSelect = element(by.id('field_status'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setMessageInput(message: string): Promise<void> {
    await this.messageInput.sendKeys(message);
  }

  async getMessageInput(): Promise<string> {
    return await this.messageInput.getAttribute('value');
  }

  async setIssuedDateInput(issuedDate: string): Promise<void> {
    await this.issuedDateInput.sendKeys(issuedDate);
  }

  async getIssuedDateInput(): Promise<string> {
    return await this.issuedDateInput.getAttribute('value');
  }

  async setIssuedByInput(issuedBy: string): Promise<void> {
    await this.issuedByInput.sendKeys(issuedBy);
  }

  async getIssuedByInput(): Promise<string> {
    return await this.issuedByInput.getAttribute('value');
  }

  async setQuantityInput(quantity: string): Promise<void> {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput(): Promise<string> {
    return await this.quantityInput.getAttribute('value');
  }

  async setCancelQuantityInput(cancelQuantity: string): Promise<void> {
    await this.cancelQuantityInput.sendKeys(cancelQuantity);
  }

  async getCancelQuantityInput(): Promise<string> {
    return await this.cancelQuantityInput.getAttribute('value');
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

  async setEquipmentIdInput(equipmentId: string): Promise<void> {
    await this.equipmentIdInput.sendKeys(equipmentId);
  }

  async getEquipmentIdInput(): Promise<string> {
    return await this.equipmentIdInput.getAttribute('value');
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

  async orderItemSelectLastOption(): Promise<void> {
    await this.orderItemSelect.all(by.tagName('option')).last().click();
  }

  async orderItemSelectOption(option: string): Promise<void> {
    await this.orderItemSelect.sendKeys(option);
  }

  getOrderItemSelect(): ElementFinder {
    return this.orderItemSelect;
  }

  async getOrderItemSelectedOption(): Promise<string> {
    return await this.orderItemSelect.element(by.css('option:checked')).getText();
  }

  async inventoryItemSelectLastOption(): Promise<void> {
    await this.inventoryItemSelect.all(by.tagName('option')).last().click();
  }

  async inventoryItemSelectOption(option: string): Promise<void> {
    await this.inventoryItemSelect.sendKeys(option);
  }

  getInventoryItemSelect(): ElementFinder {
    return this.inventoryItemSelect;
  }

  async getInventoryItemSelectedOption(): Promise<string> {
    return await this.inventoryItemSelect.element(by.css('option:checked')).getText();
  }

  async issuedByUserLoginSelectLastOption(): Promise<void> {
    await this.issuedByUserLoginSelect.all(by.tagName('option')).last().click();
  }

  async issuedByUserLoginSelectOption(option: string): Promise<void> {
    await this.issuedByUserLoginSelect.sendKeys(option);
  }

  getIssuedByUserLoginSelect(): ElementFinder {
    return this.issuedByUserLoginSelect;
  }

  async getIssuedByUserLoginSelectedOption(): Promise<string> {
    return await this.issuedByUserLoginSelect.element(by.css('option:checked')).getText();
  }

  async varianceReasonSelectLastOption(): Promise<void> {
    await this.varianceReasonSelect.all(by.tagName('option')).last().click();
  }

  async varianceReasonSelectOption(option: string): Promise<void> {
    await this.varianceReasonSelect.sendKeys(option);
  }

  getVarianceReasonSelect(): ElementFinder {
    return this.varianceReasonSelect;
  }

  async getVarianceReasonSelectedOption(): Promise<string> {
    return await this.varianceReasonSelect.element(by.css('option:checked')).getText();
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

export class ItemIssuanceDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-itemIssuance-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-itemIssuance'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
