import { element, by, ElementFinder } from 'protractor';

export class ProductCategoryComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-product-category div table .btn-danger'));
  title = element.all(by.css('sys-product-category div h2#page-heading span')).first();
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

export class ProductCategoryUpdatePage {
  pageTitle = element(by.id('sys-product-category-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  longDescriptionInput = element(by.id('field_longDescription'));
  attributeInput = element(by.id('field_attribute'));
  sequenceNoInput = element(by.id('field_sequenceNo'));
  imageUrlInput = element(by.id('field_imageUrl'));
  altImageUrlInput = element(by.id('field_altImageUrl'));
  infoInput = element(by.id('field_info'));

  productCategoryTypeSelect = element(by.id('field_productCategoryType'));
  parentSelect = element(by.id('field_parent'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setLongDescriptionInput(longDescription: string): Promise<void> {
    await this.longDescriptionInput.sendKeys(longDescription);
  }

  async getLongDescriptionInput(): Promise<string> {
    return await this.longDescriptionInput.getAttribute('value');
  }

  async setAttributeInput(attribute: string): Promise<void> {
    await this.attributeInput.sendKeys(attribute);
  }

  async getAttributeInput(): Promise<string> {
    return await this.attributeInput.getAttribute('value');
  }

  async setSequenceNoInput(sequenceNo: string): Promise<void> {
    await this.sequenceNoInput.sendKeys(sequenceNo);
  }

  async getSequenceNoInput(): Promise<string> {
    return await this.sequenceNoInput.getAttribute('value');
  }

  async setImageUrlInput(imageUrl: string): Promise<void> {
    await this.imageUrlInput.sendKeys(imageUrl);
  }

  async getImageUrlInput(): Promise<string> {
    return await this.imageUrlInput.getAttribute('value');
  }

  async setAltImageUrlInput(altImageUrl: string): Promise<void> {
    await this.altImageUrlInput.sendKeys(altImageUrl);
  }

  async getAltImageUrlInput(): Promise<string> {
    return await this.altImageUrlInput.getAttribute('value');
  }

  async setInfoInput(info: string): Promise<void> {
    await this.infoInput.sendKeys(info);
  }

  async getInfoInput(): Promise<string> {
    return await this.infoInput.getAttribute('value');
  }

  async productCategoryTypeSelectLastOption(): Promise<void> {
    await this.productCategoryTypeSelect.all(by.tagName('option')).last().click();
  }

  async productCategoryTypeSelectOption(option: string): Promise<void> {
    await this.productCategoryTypeSelect.sendKeys(option);
  }

  getProductCategoryTypeSelect(): ElementFinder {
    return this.productCategoryTypeSelect;
  }

  async getProductCategoryTypeSelectedOption(): Promise<string> {
    return await this.productCategoryTypeSelect.element(by.css('option:checked')).getText();
  }

  async parentSelectLastOption(): Promise<void> {
    await this.parentSelect.all(by.tagName('option')).last().click();
  }

  async parentSelectOption(option: string): Promise<void> {
    await this.parentSelect.sendKeys(option);
  }

  getParentSelect(): ElementFinder {
    return this.parentSelect;
  }

  async getParentSelectedOption(): Promise<string> {
    return await this.parentSelect.element(by.css('option:checked')).getText();
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

export class ProductCategoryDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-productCategory-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-productCategory'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
