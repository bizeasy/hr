import { element, by, ElementFinder } from 'protractor';

export class UserGroupAuthorityComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-user-group-authority div table .btn-danger'));
  title = element.all(by.css('sys-user-group-authority div h2#page-heading span')).first();
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

export class UserGroupAuthorityUpdatePage {
  pageTitle = element(by.id('sys-user-group-authority-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  authorityInput = element(by.id('field_authority'));

  userGroupSelect = element(by.id('field_userGroup'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setAuthorityInput(authority: string): Promise<void> {
    await this.authorityInput.sendKeys(authority);
  }

  async getAuthorityInput(): Promise<string> {
    return await this.authorityInput.getAttribute('value');
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

export class UserGroupAuthorityDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-userGroupAuthority-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-userGroupAuthority'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
