import { element, by, ElementFinder } from 'protractor';

export class EmplLeaveComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-empl-leave div table .btn-danger'));
  title = element.all(by.css('sys-empl-leave div h2#page-heading span')).first();
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

export class EmplLeaveUpdatePage {
  pageTitle = element(by.id('sys-empl-leave-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  descriptionInput = element(by.id('field_description'));
  fromDateInput = element(by.id('field_fromDate'));
  thruDateInput = element(by.id('field_thruDate'));

  employeeSelect = element(by.id('field_employee'));
  approverSelect = element(by.id('field_approver'));
  leaveTypeSelect = element(by.id('field_leaveType'));
  reasonSelect = element(by.id('field_reason'));
  statusSelect = element(by.id('field_status'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
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

  async approverSelectLastOption(): Promise<void> {
    await this.approverSelect.all(by.tagName('option')).last().click();
  }

  async approverSelectOption(option: string): Promise<void> {
    await this.approverSelect.sendKeys(option);
  }

  getApproverSelect(): ElementFinder {
    return this.approverSelect;
  }

  async getApproverSelectedOption(): Promise<string> {
    return await this.approverSelect.element(by.css('option:checked')).getText();
  }

  async leaveTypeSelectLastOption(): Promise<void> {
    await this.leaveTypeSelect.all(by.tagName('option')).last().click();
  }

  async leaveTypeSelectOption(option: string): Promise<void> {
    await this.leaveTypeSelect.sendKeys(option);
  }

  getLeaveTypeSelect(): ElementFinder {
    return this.leaveTypeSelect;
  }

  async getLeaveTypeSelectedOption(): Promise<string> {
    return await this.leaveTypeSelect.element(by.css('option:checked')).getText();
  }

  async reasonSelectLastOption(): Promise<void> {
    await this.reasonSelect.all(by.tagName('option')).last().click();
  }

  async reasonSelectOption(option: string): Promise<void> {
    await this.reasonSelect.sendKeys(option);
  }

  getReasonSelect(): ElementFinder {
    return this.reasonSelect;
  }

  async getReasonSelectedOption(): Promise<string> {
    return await this.reasonSelect.element(by.css('option:checked')).getText();
  }

  async statusSelectLastOption(): Promise<void> {
    await this.statusSelect.all(by.tagName('option')).last().click();
  }

  async statusSelectOption(option: string): Promise<void> {
    await this.statusSelect.sendKeys(option);
  }

  getStatusSelect(): ElementFinder {
    return this.statusSelect;
  }

  async getStatusSelectedOption(): Promise<string> {
    return await this.statusSelect.element(by.css('option:checked')).getText();
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

export class EmplLeaveDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-emplLeave-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-emplLeave'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
