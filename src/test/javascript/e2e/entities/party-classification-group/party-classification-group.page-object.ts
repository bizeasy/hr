import { element, by, ElementFinder } from 'protractor';

export class PartyClassificationGroupComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-party-classification-group div table .btn-danger'));
  title = element.all(by.css('sys-party-classification-group div h2#page-heading span')).first();
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

export class PartyClassificationGroupUpdatePage {
  pageTitle = element(by.id('sys-party-classification-group-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));

  classificationTypeSelect = element(by.id('field_classificationType'));

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

  async classificationTypeSelectLastOption(): Promise<void> {
    await this.classificationTypeSelect.all(by.tagName('option')).last().click();
  }

  async classificationTypeSelectOption(option: string): Promise<void> {
    await this.classificationTypeSelect.sendKeys(option);
  }

  getClassificationTypeSelect(): ElementFinder {
    return this.classificationTypeSelect;
  }

  async getClassificationTypeSelectedOption(): Promise<string> {
    return await this.classificationTypeSelect.element(by.css('option:checked')).getText();
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

export class PartyClassificationGroupDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-partyClassificationGroup-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-partyClassificationGroup'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
