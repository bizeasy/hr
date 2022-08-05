import { element, by, ElementFinder } from 'protractor';

export class AttendanceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-attendance div table .btn-danger'));
  title = element.all(by.css('sys-attendance div h2#page-heading span')).first();
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

export class AttendanceUpdatePage {
  pageTitle = element(by.id('sys-attendance-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  punchInInput = element(by.id('field_punchIn'));
  punchOutInput = element(by.id('field_punchOut'));

  employeeSelect = element(by.id('field_employee'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setPunchInInput(punchIn: string): Promise<void> {
    await this.punchInInput.sendKeys(punchIn);
  }

  async getPunchInInput(): Promise<string> {
    return await this.punchInInput.getAttribute('value');
  }

  async setPunchOutInput(punchOut: string): Promise<void> {
    await this.punchOutInput.sendKeys(punchOut);
  }

  async getPunchOutInput(): Promise<string> {
    return await this.punchOutInput.getAttribute('value');
  }

  async employeeSelectLastOption(): Promise<void> {
    await this.employeeSelect.all(by.tagName('option')).last().click();
  }

  async employeeSelectOption(option: string): Promise<void> {
    await this.employeeSelect.sendKeys(option);
  }

  getEmployeeSelect(): ElementFinder {
    return this.employeeSelect;
  }

  async getEmployeeSelectedOption(): Promise<string> {
    return await this.employeeSelect.element(by.css('option:checked')).getText();
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

export class AttendanceDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-attendance-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-attendance'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
