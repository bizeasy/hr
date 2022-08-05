import { element, by, ElementFinder } from 'protractor';

export class InventoryItemVarianceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-inventory-item-variance div table .btn-danger'));
  title = element.all(by.css('sys-inventory-item-variance div h2#page-heading span')).first();
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

export class InventoryItemVarianceUpdatePage {
  pageTitle = element(by.id('sys-inventory-item-variance-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  varianceReasonInput = element(by.id('field_varianceReason'));
  atpVarInput = element(by.id('field_atpVar'));
  qohVarInput = element(by.id('field_qohVar'));
  commentsInput = element(by.id('field_comments'));

  inventoryItemSelect = element(by.id('field_inventoryItem'));
  reasonSelect = element(by.id('field_reason'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setVarianceReasonInput(varianceReason: string): Promise<void> {
    await this.varianceReasonInput.sendKeys(varianceReason);
  }

  async getVarianceReasonInput(): Promise<string> {
    return await this.varianceReasonInput.getAttribute('value');
  }

  async setAtpVarInput(atpVar: string): Promise<void> {
    await this.atpVarInput.sendKeys(atpVar);
  }

  async getAtpVarInput(): Promise<string> {
    return await this.atpVarInput.getAttribute('value');
  }

  async setQohVarInput(qohVar: string): Promise<void> {
    await this.qohVarInput.sendKeys(qohVar);
  }

  async getQohVarInput(): Promise<string> {
    return await this.qohVarInput.getAttribute('value');
  }

  async setCommentsInput(comments: string): Promise<void> {
    await this.commentsInput.sendKeys(comments);
  }

  async getCommentsInput(): Promise<string> {
    return await this.commentsInput.getAttribute('value');
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

  async reasonSelectLastOption(): Promise<void> {
    await this.reasonSelect.all(by.tagName('option')).last().click();
  }

  async reasonSelectOption(option: string): Promise<void> {
    await this.reasonSelect.sendKeys(option);
  }

  getReasonSelect(): ElementFinder {
    return this.reasonSelect;
  }

  async getReasonSelectedOption(): Promise<string> {
    return await this.reasonSelect.element(by.css('option:checked')).getText();
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

export class InventoryItemVarianceDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-inventoryItemVariance-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-inventoryItemVariance'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
