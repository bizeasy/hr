import { element, by, ElementFinder } from 'protractor';

export class WorkEffortProductComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-work-effort-product div table .btn-danger'));
  title = element.all(by.css('sys-work-effort-product div h2#page-heading span')).first();
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

export class WorkEffortProductUpdatePage {
  pageTitle = element(by.id('sys-work-effort-product-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  sequenceNoInput = element(by.id('field_sequenceNo'));
  quantityInput = element(by.id('field_quantity'));

  workEffortSelect = element(by.id('field_workEffort'));
  productSelect = element(by.id('field_product'));

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

export class WorkEffortProductDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-workEffortProduct-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-workEffortProduct'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
