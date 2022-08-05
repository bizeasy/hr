import { element, by, ElementFinder } from 'protractor';

export class TermComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-term div table .btn-danger'));
  title = element.all(by.css('sys-term div h2#page-heading span')).first();
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

export class TermUpdatePage {
  pageTitle = element(by.id('sys-term-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  termDetailInput = element(by.id('field_termDetail'));
  termValueInput = element(by.id('field_termValue'));
  termDaysInput = element(by.id('field_termDays'));
  textValueInput = element(by.id('field_textValue'));

  termTypeSelect = element(by.id('field_termType'));

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

  async setTermDetailInput(termDetail: string): Promise<void> {
    await this.termDetailInput.sendKeys(termDetail);
  }

  async getTermDetailInput(): Promise<string> {
    return await this.termDetailInput.getAttribute('value');
  }

  async setTermValueInput(termValue: string): Promise<void> {
    await this.termValueInput.sendKeys(termValue);
  }

  async getTermValueInput(): Promise<string> {
    return await this.termValueInput.getAttribute('value');
  }

  async setTermDaysInput(termDays: string): Promise<void> {
    await this.termDaysInput.sendKeys(termDays);
  }

  async getTermDaysInput(): Promise<string> {
    return await this.termDaysInput.getAttribute('value');
  }

  async setTextValueInput(textValue: string): Promise<void> {
    await this.textValueInput.sendKeys(textValue);
  }

  async getTextValueInput(): Promise<string> {
    return await this.textValueInput.getAttribute('value');
  }

  async termTypeSelectLastOption(): Promise<void> {
    await this.termTypeSelect.all(by.tagName('option')).last().click();
  }

  async termTypeSelectOption(option: string): Promise<void> {
    await this.termTypeSelect.sendKeys(option);
  }

  getTermTypeSelect(): ElementFinder {
    return this.termTypeSelect;
  }

  async getTermTypeSelectedOption(): Promise<string> {
    return await this.termTypeSelect.element(by.css('option:checked')).getText();
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

export class TermDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-term-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-term'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
