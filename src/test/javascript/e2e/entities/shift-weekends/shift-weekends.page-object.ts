import { element, by, ElementFinder } from 'protractor';

export class ShiftWeekendsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-shift-weekends div table .btn-danger'));
  title = element.all(by.css('sys-shift-weekends div h2#page-heading span')).first();
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

export class ShiftWeekendsUpdatePage {
  pageTitle = element(by.id('sys-shift-weekends-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fromDateInput = element(by.id('field_fromDate'));
  thruDateInput = element(by.id('field_thruDate'));

  shiftSelect = element(by.id('field_shift'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFromDateInput(fromDate: string): Promise<void> {
    await this.fromDateInput.sendKeys(fromDate);
  }

  async getFromDateInput(): Promise<string> {
    return await this.fromDateInput.getAttribute('value');
  }

  async setThruDateInput(thruDate: string): Promise<void> {
    await this.thruDateInput.sendKeys(thruDate);
  }

  async getThruDateInput(): Promise<string> {
    return await this.thruDateInput.getAttribute('value');
  }

  async shiftSelectLastOption(): Promise<void> {
    await this.shiftSelect.all(by.tagName('option')).last().click();
  }

  async shiftSelectOption(option: string): Promise<void> {
    await this.shiftSelect.sendKeys(option);
  }

  getShiftSelect(): ElementFinder {
    return this.shiftSelect;
  }

  async getShiftSelectedOption(): Promise<string> {
    return await this.shiftSelect.element(by.css('option:checked')).getText();
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

export class ShiftWeekendsDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-shiftWeekends-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-shiftWeekends'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
