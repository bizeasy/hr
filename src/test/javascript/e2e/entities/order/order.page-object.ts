import { element, by, ElementFinder } from 'protractor';

export class OrderComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-order div table .btn-danger'));
  title = element.all(by.css('sys-order div h2#page-heading span')).first();
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

export class OrderUpdatePage {
  pageTitle = element(by.id('sys-order-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  externalIdInput = element(by.id('field_externalId'));
  orderDateInput = element(by.id('field_orderDate'));
  priorityInput = element(by.id('field_priority'));
  entryDateInput = element(by.id('field_entryDate'));
  isRushOrderInput = element(by.id('field_isRushOrder'));
  needsInventoryIssuanceInput = element(by.id('field_needsInventoryIssuance'));
  remainingSubTotalInput = element(by.id('field_remainingSubTotal'));
  grandTotalInput = element(by.id('field_grandTotal'));
  hasRateContractInput = element(by.id('field_hasRateContract'));
  gotQuoteFromVendorsInput = element(by.id('field_gotQuoteFromVendors'));
  vendorReasonInput = element(by.id('field_vendorReason'));
  expectedDeliveryDateInput = element(by.id('field_expectedDeliveryDate'));
  insuranceRespInput = element(by.id('field_insuranceResp'));
  transportRespInput = element(by.id('field_transportResp'));
  unloadingRespInput = element(by.id('field_unloadingResp'));

  orderTypeSelect = element(by.id('field_orderType'));
  salesChannelSelect = element(by.id('field_salesChannel'));
  partySelect = element(by.id('field_party'));
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

  async setExternalIdInput(externalId: string): Promise<void> {
    await this.externalIdInput.sendKeys(externalId);
  }

  async getExternalIdInput(): Promise<string> {
    return await this.externalIdInput.getAttribute('value');
  }

  async setOrderDateInput(orderDate: string): Promise<void> {
    await this.orderDateInput.sendKeys(orderDate);
  }

  async getOrderDateInput(): Promise<string> {
    return await this.orderDateInput.getAttribute('value');
  }

  async setPriorityInput(priority: string): Promise<void> {
    await this.priorityInput.sendKeys(priority);
  }

  async getPriorityInput(): Promise<string> {
    return await this.priorityInput.getAttribute('value');
  }

  async setEntryDateInput(entryDate: string): Promise<void> {
    await this.entryDateInput.sendKeys(entryDate);
  }

  async getEntryDateInput(): Promise<string> {
    return await this.entryDateInput.getAttribute('value');
  }

  getIsRushOrderInput(): ElementFinder {
    return this.isRushOrderInput;
  }

  getNeedsInventoryIssuanceInput(): ElementFinder {
    return this.needsInventoryIssuanceInput;
  }

  async setRemainingSubTotalInput(remainingSubTotal: string): Promise<void> {
    await this.remainingSubTotalInput.sendKeys(remainingSubTotal);
  }

  async getRemainingSubTotalInput(): Promise<string> {
    return await this.remainingSubTotalInput.getAttribute('value');
  }

  async setGrandTotalInput(grandTotal: string): Promise<void> {
    await this.grandTotalInput.sendKeys(grandTotal);
  }

  async getGrandTotalInput(): Promise<string> {
    return await this.grandTotalInput.getAttribute('value');
  }

  getHasRateContractInput(): ElementFinder {
    return this.hasRateContractInput;
  }

  getGotQuoteFromVendorsInput(): ElementFinder {
    return this.gotQuoteFromVendorsInput;
  }

  async setVendorReasonInput(vendorReason: string): Promise<void> {
    await this.vendorReasonInput.sendKeys(vendorReason);
  }

  async getVendorReasonInput(): Promise<string> {
    return await this.vendorReasonInput.getAttribute('value');
  }

  async setExpectedDeliveryDateInput(expectedDeliveryDate: string): Promise<void> {
    await this.expectedDeliveryDateInput.sendKeys(expectedDeliveryDate);
  }

  async getExpectedDeliveryDateInput(): Promise<string> {
    return await this.expectedDeliveryDateInput.getAttribute('value');
  }

  async setInsuranceRespInput(insuranceResp: string): Promise<void> {
    await this.insuranceRespInput.sendKeys(insuranceResp);
  }

  async getInsuranceRespInput(): Promise<string> {
    return await this.insuranceRespInput.getAttribute('value');
  }

  async setTransportRespInput(transportResp: string): Promise<void> {
    await this.transportRespInput.sendKeys(transportResp);
  }

  async getTransportRespInput(): Promise<string> {
    return await this.transportRespInput.getAttribute('value');
  }

  async setUnloadingRespInput(unloadingResp: string): Promise<void> {
    await this.unloadingRespInput.sendKeys(unloadingResp);
  }

  async getUnloadingRespInput(): Promise<string> {
    return await this.unloadingRespInput.getAttribute('value');
  }

  async orderTypeSelectLastOption(): Promise<void> {
    await this.orderTypeSelect.all(by.tagName('option')).last().click();
  }

  async orderTypeSelectOption(option: string): Promise<void> {
    await this.orderTypeSelect.sendKeys(option);
  }

  getOrderTypeSelect(): ElementFinder {
    return this.orderTypeSelect;
  }

  async getOrderTypeSelectedOption(): Promise<string> {
    return await this.orderTypeSelect.element(by.css('option:checked')).getText();
  }

  async salesChannelSelectLastOption(): Promise<void> {
    await this.salesChannelSelect.all(by.tagName('option')).last().click();
  }

  async salesChannelSelectOption(option: string): Promise<void> {
    await this.salesChannelSelect.sendKeys(option);
  }

  getSalesChannelSelect(): ElementFinder {
    return this.salesChannelSelect;
  }

  async getSalesChannelSelectedOption(): Promise<string> {
    return await this.salesChannelSelect.element(by.css('option:checked')).getText();
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

export class OrderDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-order-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-order'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
