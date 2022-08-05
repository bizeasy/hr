import { element, by, ElementFinder } from 'protractor';

export class InventoryItemDelegateComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-inventory-item-delegate div table .btn-danger'));
  title = element.all(by.css('sys-inventory-item-delegate div h2#page-heading span')).first();
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

export class InventoryItemDelegateUpdatePage {
  pageTitle = element(by.id('sys-inventory-item-delegate-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  sequenceNoInput = element(by.id('field_sequenceNo'));
  effectiveDateInput = element(by.id('field_effectiveDate'));
  quantityOnHandDiffInput = element(by.id('field_quantityOnHandDiff'));
  availableToPromiseDiffInput = element(by.id('field_availableToPromiseDiff'));
  accountingQuantityDiffInput = element(by.id('field_accountingQuantityDiff'));
  unitCostInput = element(by.id('field_unitCost'));
  remarksInput = element(by.id('field_remarks'));

  invoiceSelect = element(by.id('field_invoice'));
  invoiceItemSelect = element(by.id('field_invoiceItem'));
  orderSelect = element(by.id('field_order'));
  orderItemSelect = element(by.id('field_orderItem'));
  itemIssuanceSelect = element(by.id('field_itemIssuance'));
  inventoryTransferSelect = element(by.id('field_inventoryTransfer'));
  inventoryItemVarianceSelect = element(by.id('field_inventoryItemVariance'));
  inventoryItemSelect = element(by.id('field_inventoryItem'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSequenceNoInput(sequenceNo: string): Promise<void> {
    await this.sequenceNoInput.sendKeys(sequenceNo);
  }

  async getSequenceNoInput(): Promise<string> {
    return await this.sequenceNoInput.getAttribute('value');
  }

  async setEffectiveDateInput(effectiveDate: string): Promise<void> {
    await this.effectiveDateInput.sendKeys(effectiveDate);
  }

  async getEffectiveDateInput(): Promise<string> {
    return await this.effectiveDateInput.getAttribute('value');
  }

  async setQuantityOnHandDiffInput(quantityOnHandDiff: string): Promise<void> {
    await this.quantityOnHandDiffInput.sendKeys(quantityOnHandDiff);
  }

  async getQuantityOnHandDiffInput(): Promise<string> {
    return await this.quantityOnHandDiffInput.getAttribute('value');
  }

  async setAvailableToPromiseDiffInput(availableToPromiseDiff: string): Promise<void> {
    await this.availableToPromiseDiffInput.sendKeys(availableToPromiseDiff);
  }

  async getAvailableToPromiseDiffInput(): Promise<string> {
    return await this.availableToPromiseDiffInput.getAttribute('value');
  }

  async setAccountingQuantityDiffInput(accountingQuantityDiff: string): Promise<void> {
    await this.accountingQuantityDiffInput.sendKeys(accountingQuantityDiff);
  }

  async getAccountingQuantityDiffInput(): Promise<string> {
    return await this.accountingQuantityDiffInput.getAttribute('value');
  }

  async setUnitCostInput(unitCost: string): Promise<void> {
    await this.unitCostInput.sendKeys(unitCost);
  }

  async getUnitCostInput(): Promise<string> {
    return await this.unitCostInput.getAttribute('value');
  }

  async setRemarksInput(remarks: string): Promise<void> {
    await this.remarksInput.sendKeys(remarks);
  }

  async getRemarksInput(): Promise<string> {
    return await this.remarksInput.getAttribute('value');
  }

  async invoiceSelectLastOption(): Promise<void> {
    await this.invoiceSelect.all(by.tagName('option')).last().click();
  }

  async invoiceSelectOption(option: string): Promise<void> {
    await this.invoiceSelect.sendKeys(option);
  }

  getInvoiceSelect(): ElementFinder {
    return this.invoiceSelect;
  }

  async getInvoiceSelectedOption(): Promise<string> {
    return await this.invoiceSelect.element(by.css('option:checked')).getText();
  }

  async invoiceItemSelectLastOption(): Promise<void> {
    await this.invoiceItemSelect.all(by.tagName('option')).last().click();
  }

  async invoiceItemSelectOption(option: string): Promise<void> {
    await this.invoiceItemSelect.sendKeys(option);
  }

  getInvoiceItemSelect(): ElementFinder {
    return this.invoiceItemSelect;
  }

  async getInvoiceItemSelectedOption(): Promise<string> {
    return await this.invoiceItemSelect.element(by.css('option:checked')).getText();
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

  async itemIssuanceSelectLastOption(): Promise<void> {
    await this.itemIssuanceSelect.all(by.tagName('option')).last().click();
  }

  async itemIssuanceSelectOption(option: string): Promise<void> {
    await this.itemIssuanceSelect.sendKeys(option);
  }

  getItemIssuanceSelect(): ElementFinder {
    return this.itemIssuanceSelect;
  }

  async getItemIssuanceSelectedOption(): Promise<string> {
    return await this.itemIssuanceSelect.element(by.css('option:checked')).getText();
  }

  async inventoryTransferSelectLastOption(): Promise<void> {
    await this.inventoryTransferSelect.all(by.tagName('option')).last().click();
  }

  async inventoryTransferSelectOption(option: string): Promise<void> {
    await this.inventoryTransferSelect.sendKeys(option);
  }

  getInventoryTransferSelect(): ElementFinder {
    return this.inventoryTransferSelect;
  }

  async getInventoryTransferSelectedOption(): Promise<string> {
    return await this.inventoryTransferSelect.element(by.css('option:checked')).getText();
  }

  async inventoryItemVarianceSelectLastOption(): Promise<void> {
    await this.inventoryItemVarianceSelect.all(by.tagName('option')).last().click();
  }

  async inventoryItemVarianceSelectOption(option: string): Promise<void> {
    await this.inventoryItemVarianceSelect.sendKeys(option);
  }

  getInventoryItemVarianceSelect(): ElementFinder {
    return this.inventoryItemVarianceSelect;
  }

  async getInventoryItemVarianceSelectedOption(): Promise<string> {
    return await this.inventoryItemVarianceSelect.element(by.css('option:checked')).getText();
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

export class InventoryItemDelegateDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-inventoryItemDelegate-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-inventoryItemDelegate'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
