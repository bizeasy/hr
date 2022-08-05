import { element, by, ElementFinder } from 'protractor';

export class InventoryItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-inventory-item div table .btn-danger'));
  title = element.all(by.css('sys-inventory-item div h2#page-heading span')).first();
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

export class InventoryItemUpdatePage {
  pageTitle = element(by.id('sys-inventory-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  receivedDateInput = element(by.id('field_receivedDate'));
  manufacturedDateInput = element(by.id('field_manufacturedDate'));
  expiryDateInput = element(by.id('field_expiryDate'));
  retestDateInput = element(by.id('field_retestDate'));
  containerIdInput = element(by.id('field_containerId'));
  batchNoInput = element(by.id('field_batchNo'));
  mfgBatchNoInput = element(by.id('field_mfgBatchNo'));
  lotNoStrInput = element(by.id('field_lotNoStr'));
  binNumberInput = element(by.id('field_binNumber'));
  commentsInput = element(by.id('field_comments'));
  quantityOnHandTotalInput = element(by.id('field_quantityOnHandTotal'));
  availableToPromiseTotalInput = element(by.id('field_availableToPromiseTotal'));
  accountingQuantityTotalInput = element(by.id('field_accountingQuantityTotal'));
  oldQuantityOnHandInput = element(by.id('field_oldQuantityOnHand'));
  oldAvailableToPromiseInput = element(by.id('field_oldAvailableToPromise'));
  serialNumberInput = element(by.id('field_serialNumber'));
  softIdentifierInput = element(by.id('field_softIdentifier'));
  activationNumberInput = element(by.id('field_activationNumber'));
  activationValidTrueInput = element(by.id('field_activationValidTrue'));
  unitCostInput = element(by.id('field_unitCost'));

  inventoryItemTypeSelect = element(by.id('field_inventoryItemType'));
  productSelect = element(by.id('field_product'));
  supplierSelect = element(by.id('field_supplier'));
  ownerPartySelect = element(by.id('field_ownerParty'));
  statusSelect = element(by.id('field_status'));
  facilitySelect = element(by.id('field_facility'));
  uomSelect = element(by.id('field_uom'));
  currencyUomSelect = element(by.id('field_currencyUom'));
  lotSelect = element(by.id('field_lot'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setReceivedDateInput(receivedDate: string): Promise<void> {
    await this.receivedDateInput.sendKeys(receivedDate);
  }

  async getReceivedDateInput(): Promise<string> {
    return await this.receivedDateInput.getAttribute('value');
  }

  async setManufacturedDateInput(manufacturedDate: string): Promise<void> {
    await this.manufacturedDateInput.sendKeys(manufacturedDate);
  }

  async getManufacturedDateInput(): Promise<string> {
    return await this.manufacturedDateInput.getAttribute('value');
  }

  async setExpiryDateInput(expiryDate: string): Promise<void> {
    await this.expiryDateInput.sendKeys(expiryDate);
  }

  async getExpiryDateInput(): Promise<string> {
    return await this.expiryDateInput.getAttribute('value');
  }

  async setRetestDateInput(retestDate: string): Promise<void> {
    await this.retestDateInput.sendKeys(retestDate);
  }

  async getRetestDateInput(): Promise<string> {
    return await this.retestDateInput.getAttribute('value');
  }

  async setContainerIdInput(containerId: string): Promise<void> {
    await this.containerIdInput.sendKeys(containerId);
  }

  async getContainerIdInput(): Promise<string> {
    return await this.containerIdInput.getAttribute('value');
  }

  async setBatchNoInput(batchNo: string): Promise<void> {
    await this.batchNoInput.sendKeys(batchNo);
  }

  async getBatchNoInput(): Promise<string> {
    return await this.batchNoInput.getAttribute('value');
  }

  async setMfgBatchNoInput(mfgBatchNo: string): Promise<void> {
    await this.mfgBatchNoInput.sendKeys(mfgBatchNo);
  }

  async getMfgBatchNoInput(): Promise<string> {
    return await this.mfgBatchNoInput.getAttribute('value');
  }

  async setLotNoStrInput(lotNoStr: string): Promise<void> {
    await this.lotNoStrInput.sendKeys(lotNoStr);
  }

  async getLotNoStrInput(): Promise<string> {
    return await this.lotNoStrInput.getAttribute('value');
  }

  async setBinNumberInput(binNumber: string): Promise<void> {
    await this.binNumberInput.sendKeys(binNumber);
  }

  async getBinNumberInput(): Promise<string> {
    return await this.binNumberInput.getAttribute('value');
  }

  async setCommentsInput(comments: string): Promise<void> {
    await this.commentsInput.sendKeys(comments);
  }

  async getCommentsInput(): Promise<string> {
    return await this.commentsInput.getAttribute('value');
  }

  async setQuantityOnHandTotalInput(quantityOnHandTotal: string): Promise<void> {
    await this.quantityOnHandTotalInput.sendKeys(quantityOnHandTotal);
  }

  async getQuantityOnHandTotalInput(): Promise<string> {
    return await this.quantityOnHandTotalInput.getAttribute('value');
  }

  async setAvailableToPromiseTotalInput(availableToPromiseTotal: string): Promise<void> {
    await this.availableToPromiseTotalInput.sendKeys(availableToPromiseTotal);
  }

  async getAvailableToPromiseTotalInput(): Promise<string> {
    return await this.availableToPromiseTotalInput.getAttribute('value');
  }

  async setAccountingQuantityTotalInput(accountingQuantityTotal: string): Promise<void> {
    await this.accountingQuantityTotalInput.sendKeys(accountingQuantityTotal);
  }

  async getAccountingQuantityTotalInput(): Promise<string> {
    return await this.accountingQuantityTotalInput.getAttribute('value');
  }

  async setOldQuantityOnHandInput(oldQuantityOnHand: string): Promise<void> {
    await this.oldQuantityOnHandInput.sendKeys(oldQuantityOnHand);
  }

  async getOldQuantityOnHandInput(): Promise<string> {
    return await this.oldQuantityOnHandInput.getAttribute('value');
  }

  async setOldAvailableToPromiseInput(oldAvailableToPromise: string): Promise<void> {
    await this.oldAvailableToPromiseInput.sendKeys(oldAvailableToPromise);
  }

  async getOldAvailableToPromiseInput(): Promise<string> {
    return await this.oldAvailableToPromiseInput.getAttribute('value');
  }

  async setSerialNumberInput(serialNumber: string): Promise<void> {
    await this.serialNumberInput.sendKeys(serialNumber);
  }

  async getSerialNumberInput(): Promise<string> {
    return await this.serialNumberInput.getAttribute('value');
  }

  async setSoftIdentifierInput(softIdentifier: string): Promise<void> {
    await this.softIdentifierInput.sendKeys(softIdentifier);
  }

  async getSoftIdentifierInput(): Promise<string> {
    return await this.softIdentifierInput.getAttribute('value');
  }

  async setActivationNumberInput(activationNumber: string): Promise<void> {
    await this.activationNumberInput.sendKeys(activationNumber);
  }

  async getActivationNumberInput(): Promise<string> {
    return await this.activationNumberInput.getAttribute('value');
  }

  async setActivationValidTrueInput(activationValidTrue: string): Promise<void> {
    await this.activationValidTrueInput.sendKeys(activationValidTrue);
  }

  async getActivationValidTrueInput(): Promise<string> {
    return await this.activationValidTrueInput.getAttribute('value');
  }

  async setUnitCostInput(unitCost: string): Promise<void> {
    await this.unitCostInput.sendKeys(unitCost);
  }

  async getUnitCostInput(): Promise<string> {
    return await this.unitCostInput.getAttribute('value');
  }

  async inventoryItemTypeSelectLastOption(): Promise<void> {
    await this.inventoryItemTypeSelect.all(by.tagName('option')).last().click();
  }

  async inventoryItemTypeSelectOption(option: string): Promise<void> {
    await this.inventoryItemTypeSelect.sendKeys(option);
  }

  getInventoryItemTypeSelect(): ElementFinder {
    return this.inventoryItemTypeSelect;
  }

  async getInventoryItemTypeSelectedOption(): Promise<string> {
    return await this.inventoryItemTypeSelect.element(by.css('option:checked')).getText();
  }

  async productSelectLastOption(): Promise<void> {
    await this.productSelect.all(by.tagName('option')).last().click();
  }

  async productSelectOption(option: string): Promise<void> {
    await this.productSelect.sendKeys(option);
  }

  getProductSelect(): ElementFinder {
    return this.productSelect;
  }

  async getProductSelectedOption(): Promise<string> {
    return await this.productSelect.element(by.css('option:checked')).getText();
  }

  async supplierSelectLastOption(): Promise<void> {
    await this.supplierSelect.all(by.tagName('option')).last().click();
  }

  async supplierSelectOption(option: string): Promise<void> {
    await this.supplierSelect.sendKeys(option);
  }

  getSupplierSelect(): ElementFinder {
    return this.supplierSelect;
  }

  async getSupplierSelectedOption(): Promise<string> {
    return await this.supplierSelect.element(by.css('option:checked')).getText();
  }

  async ownerPartySelectLastOption(): Promise<void> {
    await this.ownerPartySelect.all(by.tagName('option')).last().click();
  }

  async ownerPartySelectOption(option: string): Promise<void> {
    await this.ownerPartySelect.sendKeys(option);
  }

  getOwnerPartySelect(): ElementFinder {
    return this.ownerPartySelect;
  }

  async getOwnerPartySelectedOption(): Promise<string> {
    return await this.ownerPartySelect.element(by.css('option:checked')).getText();
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

  async uomSelectLastOption(): Promise<void> {
    await this.uomSelect.all(by.tagName('option')).last().click();
  }

  async uomSelectOption(option: string): Promise<void> {
    await this.uomSelect.sendKeys(option);
  }

  getUomSelect(): ElementFinder {
    return this.uomSelect;
  }

  async getUomSelectedOption(): Promise<string> {
    return await this.uomSelect.element(by.css('option:checked')).getText();
  }

  async currencyUomSelectLastOption(): Promise<void> {
    await this.currencyUomSelect.all(by.tagName('option')).last().click();
  }

  async currencyUomSelectOption(option: string): Promise<void> {
    await this.currencyUomSelect.sendKeys(option);
  }

  getCurrencyUomSelect(): ElementFinder {
    return this.currencyUomSelect;
  }

  async getCurrencyUomSelectedOption(): Promise<string> {
    return await this.currencyUomSelect.element(by.css('option:checked')).getText();
  }

  async lotSelectLastOption(): Promise<void> {
    await this.lotSelect.all(by.tagName('option')).last().click();
  }

  async lotSelectOption(option: string): Promise<void> {
    await this.lotSelect.sendKeys(option);
  }

  getLotSelect(): ElementFinder {
    return this.lotSelect;
  }

  async getLotSelectedOption(): Promise<string> {
    return await this.lotSelect.element(by.css('option:checked')).getText();
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

export class InventoryItemDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-inventoryItem-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-inventoryItem'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
