import { element, by, ElementFinder } from 'protractor';

export class OrderItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-order-item div table .btn-danger'));
  title = element.all(by.css('sys-order-item div h2#page-heading span')).first();
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

export class OrderItemUpdatePage {
  pageTitle = element(by.id('sys-order-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  sequenceNoInput = element(by.id('field_sequenceNo'));
  quantityInput = element(by.id('field_quantity'));
  cancelQuantityInput = element(by.id('field_cancelQuantity'));
  selectedAmountInput = element(by.id('field_selectedAmount'));
  unitPriceInput = element(by.id('field_unitPrice'));
  unitListPriceInput = element(by.id('field_unitListPrice'));
  cgstInput = element(by.id('field_cgst'));
  igstInput = element(by.id('field_igst'));
  sgstInput = element(by.id('field_sgst'));
  cgstPercentageInput = element(by.id('field_cgstPercentage'));
  igstPercentageInput = element(by.id('field_igstPercentage'));
  sgstPercentageInput = element(by.id('field_sgstPercentage'));
  totalPriceInput = element(by.id('field_totalPrice'));
  isModifiedPriceInput = element(by.id('field_isModifiedPrice'));
  estimatedShipDateInput = element(by.id('field_estimatedShipDate'));
  estimatedDeliveryDateInput = element(by.id('field_estimatedDeliveryDate'));
  shipBeforeDateInput = element(by.id('field_shipBeforeDate'));
  shipAfterDateInput = element(by.id('field_shipAfterDate'));

  orderSelect = element(by.id('field_order'));
  orderItemTypeSelect = element(by.id('field_orderItemType'));
  fromInventoryItemSelect = element(by.id('field_fromInventoryItem'));
  productSelect = element(by.id('field_product'));
  supplierProductSelect = element(by.id('field_supplierProduct'));
  statusSelect = element(by.id('field_status'));
  taxAuthRateSelect = element(by.id('field_taxAuthRate'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSequenceNoInput(sequenceNo: string): Promise<void> {
    await this.sequenceNoInput.sendKeys(sequenceNo);
  }

  async getSequenceNoInput(): Promise<string> {
    return await this.sequenceNoInput.getAttribute('value');
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

  async setSelectedAmountInput(selectedAmount: string): Promise<void> {
    await this.selectedAmountInput.sendKeys(selectedAmount);
  }

  async getSelectedAmountInput(): Promise<string> {
    return await this.selectedAmountInput.getAttribute('value');
  }

  async setUnitPriceInput(unitPrice: string): Promise<void> {
    await this.unitPriceInput.sendKeys(unitPrice);
  }

  async getUnitPriceInput(): Promise<string> {
    return await this.unitPriceInput.getAttribute('value');
  }

  async setUnitListPriceInput(unitListPrice: string): Promise<void> {
    await this.unitListPriceInput.sendKeys(unitListPrice);
  }

  async getUnitListPriceInput(): Promise<string> {
    return await this.unitListPriceInput.getAttribute('value');
  }

  async setCgstInput(cgst: string): Promise<void> {
    await this.cgstInput.sendKeys(cgst);
  }

  async getCgstInput(): Promise<string> {
    return await this.cgstInput.getAttribute('value');
  }

  async setIgstInput(igst: string): Promise<void> {
    await this.igstInput.sendKeys(igst);
  }

  async getIgstInput(): Promise<string> {
    return await this.igstInput.getAttribute('value');
  }

  async setSgstInput(sgst: string): Promise<void> {
    await this.sgstInput.sendKeys(sgst);
  }

  async getSgstInput(): Promise<string> {
    return await this.sgstInput.getAttribute('value');
  }

  async setCgstPercentageInput(cgstPercentage: string): Promise<void> {
    await this.cgstPercentageInput.sendKeys(cgstPercentage);
  }

  async getCgstPercentageInput(): Promise<string> {
    return await this.cgstPercentageInput.getAttribute('value');
  }

  async setIgstPercentageInput(igstPercentage: string): Promise<void> {
    await this.igstPercentageInput.sendKeys(igstPercentage);
  }

  async getIgstPercentageInput(): Promise<string> {
    return await this.igstPercentageInput.getAttribute('value');
  }

  async setSgstPercentageInput(sgstPercentage: string): Promise<void> {
    await this.sgstPercentageInput.sendKeys(sgstPercentage);
  }

  async getSgstPercentageInput(): Promise<string> {
    return await this.sgstPercentageInput.getAttribute('value');
  }

  async setTotalPriceInput(totalPrice: string): Promise<void> {
    await this.totalPriceInput.sendKeys(totalPrice);
  }

  async getTotalPriceInput(): Promise<string> {
    return await this.totalPriceInput.getAttribute('value');
  }

  getIsModifiedPriceInput(): ElementFinder {
    return this.isModifiedPriceInput;
  }

  async setEstimatedShipDateInput(estimatedShipDate: string): Promise<void> {
    await this.estimatedShipDateInput.sendKeys(estimatedShipDate);
  }

  async getEstimatedShipDateInput(): Promise<string> {
    return await this.estimatedShipDateInput.getAttribute('value');
  }

  async setEstimatedDeliveryDateInput(estimatedDeliveryDate: string): Promise<void> {
    await this.estimatedDeliveryDateInput.sendKeys(estimatedDeliveryDate);
  }

  async getEstimatedDeliveryDateInput(): Promise<string> {
    return await this.estimatedDeliveryDateInput.getAttribute('value');
  }

  async setShipBeforeDateInput(shipBeforeDate: string): Promise<void> {
    await this.shipBeforeDateInput.sendKeys(shipBeforeDate);
  }

  async getShipBeforeDateInput(): Promise<string> {
    return await this.shipBeforeDateInput.getAttribute('value');
  }

  async setShipAfterDateInput(shipAfterDate: string): Promise<void> {
    await this.shipAfterDateInput.sendKeys(shipAfterDate);
  }

  async getShipAfterDateInput(): Promise<string> {
    return await this.shipAfterDateInput.getAttribute('value');
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

  async orderItemTypeSelectLastOption(): Promise<void> {
    await this.orderItemTypeSelect.all(by.tagName('option')).last().click();
  }

  async orderItemTypeSelectOption(option: string): Promise<void> {
    await this.orderItemTypeSelect.sendKeys(option);
  }

  getOrderItemTypeSelect(): ElementFinder {
    return this.orderItemTypeSelect;
  }

  async getOrderItemTypeSelectedOption(): Promise<string> {
    return await this.orderItemTypeSelect.element(by.css('option:checked')).getText();
  }

  async fromInventoryItemSelectLastOption(): Promise<void> {
    await this.fromInventoryItemSelect.all(by.tagName('option')).last().click();
  }

  async fromInventoryItemSelectOption(option: string): Promise<void> {
    await this.fromInventoryItemSelect.sendKeys(option);
  }

  getFromInventoryItemSelect(): ElementFinder {
    return this.fromInventoryItemSelect;
  }

  async getFromInventoryItemSelectedOption(): Promise<string> {
    return await this.fromInventoryItemSelect.element(by.css('option:checked')).getText();
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

  async supplierProductSelectLastOption(): Promise<void> {
    await this.supplierProductSelect.all(by.tagName('option')).last().click();
  }

  async supplierProductSelectOption(option: string): Promise<void> {
    await this.supplierProductSelect.sendKeys(option);
  }

  getSupplierProductSelect(): ElementFinder {
    return this.supplierProductSelect;
  }

  async getSupplierProductSelectedOption(): Promise<string> {
    return await this.supplierProductSelect.element(by.css('option:checked')).getText();
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

  async taxAuthRateSelectLastOption(): Promise<void> {
    await this.taxAuthRateSelect.all(by.tagName('option')).last().click();
  }

  async taxAuthRateSelectOption(option: string): Promise<void> {
    await this.taxAuthRateSelect.sendKeys(option);
  }

  getTaxAuthRateSelect(): ElementFinder {
    return this.taxAuthRateSelect;
  }

  async getTaxAuthRateSelectedOption(): Promise<string> {
    return await this.taxAuthRateSelect.element(by.css('option:checked')).getText();
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

export class OrderItemDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-orderItem-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-orderItem'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
