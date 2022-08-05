import { element, by, ElementFinder } from 'protractor';

export class InventoryTransferComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-inventory-transfer div table .btn-danger'));
  title = element.all(by.css('sys-inventory-transfer div h2#page-heading span')).first();
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

export class InventoryTransferUpdatePage {
  pageTitle = element(by.id('sys-inventory-transfer-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  sentDateInput = element(by.id('field_sentDate'));
  receivedDateInput = element(by.id('field_receivedDate'));
  commentsInput = element(by.id('field_comments'));

  statusSelect = element(by.id('field_status'));
  inventoryItemSelect = element(by.id('field_inventoryItem'));
  facilitySelect = element(by.id('field_facility'));
  facilityToSelect = element(by.id('field_facilityTo'));
  issuanceSelect = element(by.id('field_issuance'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSentDateInput(sentDate: string): Promise<void> {
    await this.sentDateInput.sendKeys(sentDate);
  }

  async getSentDateInput(): Promise<string> {
    return await this.sentDateInput.getAttribute('value');
  }

  async setReceivedDateInput(receivedDate: string): Promise<void> {
    await this.receivedDateInput.sendKeys(receivedDate);
  }

  async getReceivedDateInput(): Promise<string> {
    return await this.receivedDateInput.getAttribute('value');
  }

  async setCommentsInput(comments: string): Promise<void> {
    await this.commentsInput.sendKeys(comments);
  }

  async getCommentsInput(): Promise<string> {
    return await this.commentsInput.getAttribute('value');
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

  async facilityToSelectLastOption(): Promise<void> {
    await this.facilityToSelect.all(by.tagName('option')).last().click();
  }

  async facilityToSelectOption(option: string): Promise<void> {
    await this.facilityToSelect.sendKeys(option);
  }

  getFacilityToSelect(): ElementFinder {
    return this.facilityToSelect;
  }

  async getFacilityToSelectedOption(): Promise<string> {
    return await this.facilityToSelect.element(by.css('option:checked')).getText();
  }

  async issuanceSelectLastOption(): Promise<void> {
    await this.issuanceSelect.all(by.tagName('option')).last().click();
  }

  async issuanceSelectOption(option: string): Promise<void> {
    await this.issuanceSelect.sendKeys(option);
  }

  getIssuanceSelect(): ElementFinder {
    return this.issuanceSelect;
  }

  async getIssuanceSelectedOption(): Promise<string> {
    return await this.issuanceSelect.element(by.css('option:checked')).getText();
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

export class InventoryTransferDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-inventoryTransfer-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-inventoryTransfer'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
