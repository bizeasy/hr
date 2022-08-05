import { element, by, ElementFinder } from 'protractor';

export class JobInterviewComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-job-interview div table .btn-danger'));
  title = element.all(by.css('sys-job-interview div h2#page-heading span')).first();
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

export class JobInterviewUpdatePage {
  pageTitle = element(by.id('sys-job-interview-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  remarksInput = element(by.id('field_remarks'));
  interviewDateInput = element(by.id('field_interviewDate'));

  intervieweeSelect = element(by.id('field_interviewee'));
  interviewerSelect = element(by.id('field_interviewer'));
  typeSelect = element(by.id('field_type'));
  jobRequisitionSelect = element(by.id('field_jobRequisition'));
  gradeSecuredSelect = element(by.id('field_gradeSecured'));
  resultSelect = element(by.id('field_result'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setRemarksInput(remarks: string): Promise<void> {
    await this.remarksInput.sendKeys(remarks);
  }

  async getRemarksInput(): Promise<string> {
    return await this.remarksInput.getAttribute('value');
  }

  async setInterviewDateInput(interviewDate: string): Promise<void> {
    await this.interviewDateInput.sendKeys(interviewDate);
  }

  async getInterviewDateInput(): Promise<string> {
    return await this.interviewDateInput.getAttribute('value');
  }

  async intervieweeSelectLastOption(): Promise<void> {
    await this.intervieweeSelect.all(by.tagName('option')).last().click();
  }

  async intervieweeSelectOption(option: string): Promise<void> {
    await this.intervieweeSelect.sendKeys(option);
  }

  getIntervieweeSelect(): ElementFinder {
    return this.intervieweeSelect;
  }

  async getIntervieweeSelectedOption(): Promise<string> {
    return await this.intervieweeSelect.element(by.css('option:checked')).getText();
  }

  async interviewerSelectLastOption(): Promise<void> {
    await this.interviewerSelect.all(by.tagName('option')).last().click();
  }

  async interviewerSelectOption(option: string): Promise<void> {
    await this.interviewerSelect.sendKeys(option);
  }

  getInterviewerSelect(): ElementFinder {
    return this.interviewerSelect;
  }

  async getInterviewerSelectedOption(): Promise<string> {
    return await this.interviewerSelect.element(by.css('option:checked')).getText();
  }

  async typeSelectLastOption(): Promise<void> {
    await this.typeSelect.all(by.tagName('option')).last().click();
  }

  async typeSelectOption(option: string): Promise<void> {
    await this.typeSelect.sendKeys(option);
  }

  getTypeSelect(): ElementFinder {
    return this.typeSelect;
  }

  async getTypeSelectedOption(): Promise<string> {
    return await this.typeSelect.element(by.css('option:checked')).getText();
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

  async gradeSecuredSelectLastOption(): Promise<void> {
    await this.gradeSecuredSelect.all(by.tagName('option')).last().click();
  }

  async gradeSecuredSelectOption(option: string): Promise<void> {
    await this.gradeSecuredSelect.sendKeys(option);
  }

  getGradeSecuredSelect(): ElementFinder {
    return this.gradeSecuredSelect;
  }

  async getGradeSecuredSelectedOption(): Promise<string> {
    return await this.gradeSecuredSelect.element(by.css('option:checked')).getText();
  }

  async resultSelectLastOption(): Promise<void> {
    await this.resultSelect.all(by.tagName('option')).last().click();
  }

  async resultSelectOption(option: string): Promise<void> {
    await this.resultSelect.sendKeys(option);
  }

  getResultSelect(): ElementFinder {
    return this.resultSelect;
  }

  async getResultSelectedOption(): Promise<string> {
    return await this.resultSelect.element(by.css('option:checked')).getText();
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

export class JobInterviewDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-jobInterview-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-jobInterview'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
