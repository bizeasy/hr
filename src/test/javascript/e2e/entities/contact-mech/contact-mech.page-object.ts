import { element, by, ElementFinder } from 'protractor';

export class ContactMechComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-contact-mech div table .btn-danger'));
  title = element.all(by.css('sys-contact-mech div h2#page-heading span')).first();
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

export class ContactMechUpdatePage {
  pageTitle = element(by.id('sys-contact-mech-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  infoStringInput = element(by.id('field_infoString'));

  contactMechTypeSelect = element(by.id('field_contactMechType'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setInfoStringInput(infoString: string): Promise<void> {
    await this.infoStringInput.sendKeys(infoString);
  }

  async getInfoStringInput(): Promise<string> {
    return await this.infoStringInput.getAttribute('value');
  }

  async contactMechTypeSelectLastOption(): Promise<void> {
    await this.contactMechTypeSelect.all(by.tagName('option')).last().click();
  }

  async contactMechTypeSelectOption(option: string): Promise<void> {
    await this.contactMechTypeSelect.sendKeys(option);
  }

  getContactMechTypeSelect(): ElementFinder {
    return this.contactMechTypeSelect;
  }

  async getContactMechTypeSelectedOption(): Promise<string> {
    return await this.contactMechTypeSelect.element(by.css('option:checked')).getText();
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

export class ContactMechDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-contactMech-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-contactMech'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
