import { element, by, ElementFinder } from 'protractor';

export class ShiftHolidaysComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-shift-holidays div table .btn-danger'));
  title = element.all(by.css('sys-shift-holidays div h2#page-heading span')).first();
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

export class ShiftHolidaysUpdatePage {
  pageTitle = element(by.id('sys-shift-holidays-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  dayOfheWeekSelect = element(by.id('field_dayOfheWeek'));
  monthlyPaidLeavesInput = element(by.id('field_monthlyPaidLeaves'));
  yearlyPaidLeavesInput = element(by.id('field_yearlyPaidLeaves'));

  shiftSelect = element(by.id('field_shift'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDayOfheWeekSelect(dayOfheWeek: string): Promise<void> {
    await this.dayOfheWeekSelect.sendKeys(dayOfheWeek);
  }

  async getDayOfheWeekSelect(): Promise<string> {
    return await this.dayOfheWeekSelect.element(by.css('option:checked')).getText();
  }

  async dayOfheWeekSelectLastOption(): Promise<void> {
    await this.dayOfheWeekSelect.all(by.tagName('option')).last().click();
  }

  async setMonthlyPaidLeavesInput(monthlyPaidLeaves: string): Promise<void> {
    await this.monthlyPaidLeavesInput.sendKeys(monthlyPaidLeaves);
  }

  async getMonthlyPaidLeavesInput(): Promise<string> {
    return await this.monthlyPaidLeavesInput.getAttribute('value');
  }

  async setYearlyPaidLeavesInput(yearlyPaidLeaves: string): Promise<void> {
    await this.yearlyPaidLeavesInput.sendKeys(yearlyPaidLeaves);
  }

  async getYearlyPaidLeavesInput(): Promise<string> {
    return await this.yearlyPaidLeavesInput.getAttribute('value');
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

export class ShiftHolidaysDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-shiftHolidays-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-shiftHolidays'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
