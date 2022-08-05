import { element, by, ElementFinder } from 'protractor';

export class PartyRoleComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-party-role div table .btn-danger'));
  title = element.all(by.css('sys-party-role div h2#page-heading span')).first();
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

export class PartyRoleUpdatePage {
  pageTitle = element(by.id('sys-party-role-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  partySelect = element(by.id('field_party'));
  roleTypeSelect = element(by.id('field_roleType'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
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

  async roleTypeSelectLastOption(): Promise<void> {
    await this.roleTypeSelect.all(by.tagName('option')).last().click();
  }

  async roleTypeSelectOption(option: string): Promise<void> {
    await this.roleTypeSelect.sendKeys(option);
  }

  getRoleTypeSelect(): ElementFinder {
    return this.roleTypeSelect;
  }

  async getRoleTypeSelectedOption(): Promise<string> {
    return await this.roleTypeSelect.element(by.css('option:checked')).getText();
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

export class PartyRoleDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-partyRole-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-partyRole'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
