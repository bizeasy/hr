import { element, by, ElementFinder } from 'protractor';

export class UomComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-uom div table .btn-danger'));
  title = element.all(by.css('sys-uom div h2#page-heading span')).first();
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

export class UomUpdatePage {
  pageTitle = element(by.id('sys-uom-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  sequenceNoInput = element(by.id('field_sequenceNo'));
  abbreviationInput = element(by.id('field_abbreviation'));

  uomTypeSelect = element(by.id('field_uomType'));

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

  async setSequenceNoInput(sequenceNo: string): Promise<void> {
    await this.sequenceNoInput.sendKeys(sequenceNo);
  }

  async getSequenceNoInput(): Promise<string> {
    return await this.sequenceNoInput.getAttribute('value');
  }

  async setAbbreviationInput(abbreviation: string): Promise<void> {
    await this.abbreviationInput.sendKeys(abbreviation);
  }

  async getAbbreviationInput(): Promise<string> {
    return await this.abbreviationInput.getAttribute('value');
  }

  async uomTypeSelectLastOption(): Promise<void> {
    await this.uomTypeSelect.all(by.tagName('option')).last().click();
  }

  async uomTypeSelectOption(option: string): Promise<void> {
    await this.uomTypeSelect.sendKeys(option);
  }

  getUomTypeSelect(): ElementFinder {
    return this.uomTypeSelect;
  }

  async getUomTypeSelectedOption(): Promise<string> {
    return await this.uomTypeSelect.element(by.css('option:checked')).getText();
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

export class UomDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-uom-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-uom'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
