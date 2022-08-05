import { element, by, ElementFinder } from 'protractor';

export class TimeSheetComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-time-sheet div table .btn-danger'));
  title = element.all(by.css('sys-time-sheet div h2#page-heading span')).first();
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

export class TimeSheetUpdatePage {
  pageTitle = element(by.id('sys-time-sheet-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  overTimeDaysInput = element(by.id('field_overTimeDays'));
  leavesInput = element(by.id('field_leaves'));
  unappliedLeavesInput = element(by.id('field_unappliedLeaves'));
  halfDaysInput = element(by.id('field_halfDays'));
  totalWorkingHoursInput = element(by.id('field_totalWorkingHours'));

  timePeriodSelect = element(by.id('field_timePeriod'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setOverTimeDaysInput(overTimeDays: string): Promise<void> {
    await this.overTimeDaysInput.sendKeys(overTimeDays);
  }

  async getOverTimeDaysInput(): Promise<string> {
    return await this.overTimeDaysInput.getAttribute('value');
  }

  async setLeavesInput(leaves: string): Promise<void> {
    await this.leavesInput.sendKeys(leaves);
  }

  async getLeavesInput(): Promise<string> {
    return await this.leavesInput.getAttribute('value');
  }

  async setUnappliedLeavesInput(unappliedLeaves: string): Promise<void> {
    await this.unappliedLeavesInput.sendKeys(unappliedLeaves);
  }

  async getUnappliedLeavesInput(): Promise<string> {
    return await this.unappliedLeavesInput.getAttribute('value');
  }

  async setHalfDaysInput(halfDays: string): Promise<void> {
    await this.halfDaysInput.sendKeys(halfDays);
  }

  async getHalfDaysInput(): Promise<string> {
    return await this.halfDaysInput.getAttribute('value');
  }

  async setTotalWorkingHoursInput(totalWorkingHours: string): Promise<void> {
    await this.totalWorkingHoursInput.sendKeys(totalWorkingHours);
  }

  async getTotalWorkingHoursInput(): Promise<string> {
    return await this.totalWorkingHoursInput.getAttribute('value');
  }

  async timePeriodSelectLastOption(): Promise<void> {
    await this.timePeriodSelect.all(by.tagName('option')).last().click();
  }

  async timePeriodSelectOption(option: string): Promise<void> {
    await this.timePeriodSelect.sendKeys(option);
  }

  getTimePeriodSelect(): ElementFinder {
    return this.timePeriodSelect;
  }

  async getTimePeriodSelectedOption(): Promise<string> {
    return await this.timePeriodSelect.element(by.css('option:checked')).getText();
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

export class TimeSheetDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-timeSheet-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-timeSheet'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
