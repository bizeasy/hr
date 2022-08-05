import { element, by, ElementFinder } from 'protractor';

export class ProductKeywordComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-product-keyword div table .btn-danger'));
  title = element.all(by.css('sys-product-keyword div h2#page-heading span')).first();
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

export class ProductKeywordUpdatePage {
  pageTitle = element(by.id('sys-product-keyword-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  keywordInput = element(by.id('field_keyword'));
  relevancyWeightInput = element(by.id('field_relevancyWeight'));

  productSelect = element(by.id('field_product'));
  keywordTypeSelect = element(by.id('field_keywordType'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setKeywordInput(keyword: string): Promise<void> {
    await this.keywordInput.sendKeys(keyword);
  }

  async getKeywordInput(): Promise<string> {
    return await this.keywordInput.getAttribute('value');
  }

  async setRelevancyWeightInput(relevancyWeight: string): Promise<void> {
    await this.relevancyWeightInput.sendKeys(relevancyWeight);
  }

  async getRelevancyWeightInput(): Promise<string> {
    return await this.relevancyWeightInput.getAttribute('value');
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

  async keywordTypeSelectLastOption(): Promise<void> {
    await this.keywordTypeSelect.all(by.tagName('option')).last().click();
  }

  async keywordTypeSelectOption(option: string): Promise<void> {
    await this.keywordTypeSelect.sendKeys(option);
  }

  getKeywordTypeSelect(): ElementFinder {
    return this.keywordTypeSelect;
  }

  async getKeywordTypeSelectedOption(): Promise<string> {
    return await this.keywordTypeSelect.element(by.css('option:checked')).getText();
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

export class ProductKeywordDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-productKeyword-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-productKeyword'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
