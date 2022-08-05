import { element, by, ElementFinder } from 'protractor';

export class PeriodTypeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-period-type div table .btn-danger'));
  title = element.all(by.css('sys-period-type div h2#page-heading span')).first();
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

export class PeriodTypeUpdatePage {
  pageTitle = element(by.id('sys-period-type-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  periodLengthInput = element(by.id('field_periodLength'));

  uomSelect = element(by.id('field_uom'));

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

  async setPeriodLengthInput(periodLength: string): Promise<void> {
    await this.periodLengthInput.sendKeys(periodLength);
  }

  async getPeriodLengthInput(): Promise<string> {
    return await this.periodLengthInput.getAttribute('value');
  }

  async uomSelectLastOption(): Promise<void> {
    await this.uomSelect.all(by.tagName('option')).last().click();
  }

  async uomSelectOption(option: string): Promise<void> {
    await this.uomSelect.sendKeys(option);
  }

  getUomSelect(): ElementFinder {
    return this.uomSelect;
  }

  async getUomSelectedOption(): Promise<string> {
    return await this.uomSelect.element(by.css('option:checked')).getText();
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

export class PeriodTypeDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-periodType-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-periodType'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
