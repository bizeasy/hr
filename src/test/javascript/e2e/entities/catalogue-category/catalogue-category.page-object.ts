import { element, by, ElementFinder } from 'protractor';

export class CatalogueCategoryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-catalogue-category div table .btn-danger'));
  title = element.all(by.css('sys-catalogue-category div h2#page-heading span')).first();
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

export class CatalogueCategoryUpdatePage {
  pageTitle = element(by.id('sys-catalogue-category-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  sequenceNoInput = element(by.id('field_sequenceNo'));

  catalogueSelect = element(by.id('field_catalogue'));
  productCategorySelect = element(by.id('field_productCategory'));
  catalogueCategoryTypeSelect = element(by.id('field_catalogueCategoryType'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSequenceNoInput(sequenceNo: string): Promise<void> {
    await this.sequenceNoInput.sendKeys(sequenceNo);
  }

  async getSequenceNoInput(): Promise<string> {
    return await this.sequenceNoInput.getAttribute('value');
  }

  async catalogueSelectLastOption(): Promise<void> {
    await this.catalogueSelect.all(by.tagName('option')).last().click();
  }

  async catalogueSelectOption(option: string): Promise<void> {
    await this.catalogueSelect.sendKeys(option);
  }

  getCatalogueSelect(): ElementFinder {
    return this.catalogueSelect;
  }

  async getCatalogueSelectedOption(): Promise<string> {
    return await this.catalogueSelect.element(by.css('option:checked')).getText();
  }

  async productCategorySelectLastOption(): Promise<void> {
    await this.productCategorySelect.all(by.tagName('option')).last().click();
  }

  async productCategorySelectOption(option: string): Promise<void> {
    await this.productCategorySelect.sendKeys(option);
  }

  getProductCategorySelect(): ElementFinder {
    return this.productCategorySelect;
  }

  async getProductCategorySelectedOption(): Promise<string> {
    return await this.productCategorySelect.element(by.css('option:checked')).getText();
  }

  async catalogueCategoryTypeSelectLastOption(): Promise<void> {
    await this.catalogueCategoryTypeSelect.all(by.tagName('option')).last().click();
  }

  async catalogueCategoryTypeSelectOption(option: string): Promise<void> {
    await this.catalogueCategoryTypeSelect.sendKeys(option);
  }

  getCatalogueCategoryTypeSelect(): ElementFinder {
    return this.catalogueCategoryTypeSelect;
  }

  async getCatalogueCategoryTypeSelectedOption(): Promise<string> {
    return await this.catalogueCategoryTypeSelect.element(by.css('option:checked')).getText();
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

export class CatalogueCategoryDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-catalogueCategory-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-catalogueCategory'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
