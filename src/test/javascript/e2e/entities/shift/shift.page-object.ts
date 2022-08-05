import { element, by, ElementFinder } from 'protractor';

export class ShiftComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-shift div table .btn-danger'));
  title = element.all(by.css('sys-shift div h2#page-heading span')).first();
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

export class ShiftUpdatePage {
  pageTitle = element(by.id('sys-shift-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  fromTimeInput = element(by.id('field_fromTime'));
  toTimeInput = element(by.id('field_toTime'));
  workingHrsInput = element(by.id('field_workingHrs'));
  monthlyPaidLeavesInput = element(by.id('field_monthlyPaidLeaves'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setFromTimeInput(fromTime: string): Promise<void> {
    await this.fromTimeInput.sendKeys(fromTime);
  }

  async getFromTimeInput(): Promise<string> {
    return await this.fromTimeInput.getAttribute('value');
  }

  async setToTimeInput(toTime: string): Promise<void> {
    await this.toTimeInput.sendKeys(toTime);
  }

  async getToTimeInput(): Promise<string> {
    return await this.toTimeInput.getAttribute('value');
  }

  async setWorkingHrsInput(workingHrs: string): Promise<void> {
    await this.workingHrsInput.sendKeys(workingHrs);
  }

  async getWorkingHrsInput(): Promise<string> {
    return await this.workingHrsInput.getAttribute('value');
  }

  async setMonthlyPaidLeavesInput(monthlyPaidLeaves: string): Promise<void> {
    await this.monthlyPaidLeavesInput.sendKeys(monthlyPaidLeaves);
  }

  async getMonthlyPaidLeavesInput(): Promise<string> {
    return await this.monthlyPaidLeavesInput.getAttribute('value');
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

export class ShiftDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-shift-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-shift'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
