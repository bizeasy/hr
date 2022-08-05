import { element, by, ElementFinder } from 'protractor';

export class EmploymentAppComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-employment-app div table .btn-danger'));
  title = element.all(by.css('sys-employment-app div h2#page-heading span')).first();
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

export class EmploymentAppUpdatePage {
  pageTitle = element(by.id('sys-employment-app-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  applicationDateInput = element(by.id('field_applicationDate'));

  emplPositionSelect = element(by.id('field_emplPosition'));
  statusSelect = element(by.id('field_status'));
  sourceSelect = element(by.id('field_source'));
  applyingPartySelect = element(by.id('field_applyingParty'));
  referredByPartySelect = element(by.id('field_referredByParty'));
  approverPartySelect = element(by.id('field_approverParty'));
  jobRequisitionSelect = element(by.id('field_jobRequisition'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setApplicationDateInput(applicationDate: string): Promise<void> {
    await this.applicationDateInput.sendKeys(applicationDate);
  }

  async getApplicationDateInput(): Promise<string> {
    return await this.applicationDateInput.getAttribute('value');
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

  async sourceSelectLastOption(): Promise<void> {
    await this.sourceSelect.all(by.tagName('option')).last().click();
  }

  async sourceSelectOption(option: string): Promise<void> {
    await this.sourceSelect.sendKeys(option);
  }

  getSourceSelect(): ElementFinder {
    return this.sourceSelect;
  }

  async getSourceSelectedOption(): Promise<string> {
    return await this.sourceSelect.element(by.css('option:checked')).getText();
  }

  async applyingPartySelectLastOption(): Promise<void> {
    await this.applyingPartySelect.all(by.tagName('option')).last().click();
  }

  async applyingPartySelectOption(option: string): Promise<void> {
    await this.applyingPartySelect.sendKeys(option);
  }

  getApplyingPartySelect(): ElementFinder {
    return this.applyingPartySelect;
  }

  async getApplyingPartySelectedOption(): Promise<string> {
    return await this.applyingPartySelect.element(by.css('option:checked')).getText();
  }

  async referredByPartySelectLastOption(): Promise<void> {
    await this.referredByPartySelect.all(by.tagName('option')).last().click();
  }

  async referredByPartySelectOption(option: string): Promise<void> {
    await this.referredByPartySelect.sendKeys(option);
  }

  getReferredByPartySelect(): ElementFinder {
    return this.referredByPartySelect;
  }

  async getReferredByPartySelectedOption(): Promise<string> {
    return await this.referredByPartySelect.element(by.css('option:checked')).getText();
  }

  async approverPartySelectLastOption(): Promise<void> {
    await this.approverPartySelect.all(by.tagName('option')).last().click();
  }

  async approverPartySelectOption(option: string): Promise<void> {
    await this.approverPartySelect.sendKeys(option);
  }

  getApproverPartySelect(): ElementFinder {
    return this.approverPartySelect;
  }

  async getApproverPartySelectedOption(): Promise<string> {
    return await this.approverPartySelect.element(by.css('option:checked')).getText();
  }

  async jobRequisitionSelectLastOption(): Promise<void> {
    await this.jobRequisitionSelect.all(by.tagName('option')).last().click();
  }

  async jobRequisitionSelectOption(option: string): Promise<void> {
    await this.jobRequisitionSelect.sendKeys(option);
  }

  getJobRequisitionSelect(): ElementFinder {
    return this.jobRequisitionSelect;
  }

  async getJobRequisitionSelectedOption(): Promise<string> {
    return await this.jobRequisitionSelect.element(by.css('option:checked')).getText();
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

export class EmploymentAppDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-employmentApp-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-employmentApp'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
