import { element, by, ElementFinder } from 'protractor';

export class WorkEffortInventoryAssignComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-work-effort-inventory-assign div table .btn-danger'));
  title = element.all(by.css('sys-work-effort-inventory-assign div h2#page-heading span')).first();
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

export class WorkEffortInventoryAssignUpdatePage {
  pageTitle = element(by.id('sys-work-effort-inventory-assign-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  quantityInput = element(by.id('field_quantity'));

  workEffortSelect = element(by.id('field_workEffort'));
  inventoryItemSelect = element(by.id('field_inventoryItem'));
  statusSelect = element(by.id('field_status'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setQuantityInput(quantity: string): Promise<void> {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput(): Promise<string> {
    return await this.quantityInput.getAttribute('value');
  }

  async workEffortSelectLastOption(): Promise<void> {
    await this.workEffortSelect.all(by.tagName('option')).last().click();
  }

  async workEffortSelectOption(option: string): Promise<void> {
    await this.workEffortSelect.sendKeys(option);
  }

  getWorkEffortSelect(): ElementFinder {
    return this.workEffortSelect;
  }

  async getWorkEffortSelectedOption(): Promise<string> {
    return await this.workEffortSelect.element(by.css('option:checked')).getText();
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

export class WorkEffortInventoryAssignDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-workEffortInventoryAssign-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-workEffortInventoryAssign'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
