import { element, by, ElementFinder } from 'protractor';

export class PerfReviewComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-perf-review div table .btn-danger'));
  title = element.all(by.css('sys-perf-review div h2#page-heading span')).first();
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

export class PerfReviewUpdatePage {
  pageTitle = element(by.id('sys-perf-review-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fromDateInput = element(by.id('field_fromDate'));
  thruDateInput = element(by.id('field_thruDate'));
  commentsInput = element(by.id('field_comments'));

  employeeSelect = element(by.id('field_employee'));
  managerSelect = element(by.id('field_manager'));
  emplPositionSelect = element(by.id('field_emplPosition'));

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

  async setCommentsInput(comments: string): Promise<void> {
    await this.commentsInput.sendKeys(comments);
  }

  async getCommentsInput(): Promise<string> {
    return await this.commentsInput.getAttribute('value');
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

  async managerSelectLastOption(): Promise<void> {
    await this.managerSelect.all(by.tagName('option')).last().click();
  }

  async managerSelectOption(option: string): Promise<void> {
    await this.managerSelect.sendKeys(option);
  }

  getManagerSelect(): ElementFinder {
    return this.managerSelect;
  }

  async getManagerSelectedOption(): Promise<string> {
    return await this.managerSelect.element(by.css('option:checked')).getText();
  }

  async emplPositionSelectLastOption(): Promise<void> {
    await this.emplPositionSelect.all(by.tagName('option')).last().click();
  }

  async emplPositionSelectOption(option: string): Promise<void> {
    await this.emplPositionSelect.sendKeys(option);
  }

  getEmplPositionSelect(): ElementFinder {
    return this.emplPositionSelect;
  }

  async getEmplPositionSelectedOption(): Promise<string> {
    return await this.emplPositionSelect.element(by.css('option:checked')).getText();
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

export class PerfReviewDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-perfReview-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-perfReview'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
