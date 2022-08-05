import { element, by, ElementFinder } from 'protractor';

export class ProductStoreUserGroupComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-product-store-user-group div table .btn-danger'));
  title = element.all(by.css('sys-product-store-user-group div h2#page-heading span')).first();
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

export class ProductStoreUserGroupUpdatePage {
  pageTitle = element(by.id('sys-product-store-user-group-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  userGroupSelect = element(by.id('field_userGroup'));
  userSelect = element(by.id('field_user'));
  partySelect = element(by.id('field_party'));
  productStoreSelect = element(by.id('field_productStore'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async userGroupSelectLastOption(): Promise<void> {
    await this.userGroupSelect.all(by.tagName('option')).last().click();
  }

  async userGroupSelectOption(option: string): Promise<void> {
    await this.userGroupSelect.sendKeys(option);
  }

  getUserGroupSelect(): ElementFinder {
    return this.userGroupSelect;
  }

  async getUserGroupSelectedOption(): Promise<string> {
    return await this.userGroupSelect.element(by.css('option:checked')).getText();
  }

  async userSelectLastOption(): Promise<void> {
    await this.userSelect.all(by.tagName('option')).last().click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
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

  async productStoreSelectLastOption(): Promise<void> {
    await this.productStoreSelect.all(by.tagName('option')).last().click();
  }

  async productStoreSelectOption(option: string): Promise<void> {
    await this.productStoreSelect.sendKeys(option);
  }

  getProductStoreSelect(): ElementFinder {
    return this.productStoreSelect;
  }

  async getProductStoreSelectedOption(): Promise<string> {
    return await this.productStoreSelect.element(by.css('option:checked')).getText();
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

export class ProductStoreUserGroupDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-productStoreUserGroup-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-productStoreUserGroup'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
