import { element, by, ElementFinder } from 'protractor';

export class JobRequisitionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-job-requisition div table .btn-danger'));
  title = element.all(by.css('sys-job-requisition div h2#page-heading span')).first();
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

export class JobRequisitionUpdatePage {
  pageTitle = element(by.id('sys-job-requisition-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  durationInput = element(by.id('field_duration'));
  ageInput = element(by.id('field_age'));
  genderSelect = element(by.id('field_gender'));
  experienceMonthsInput = element(by.id('field_experienceMonths'));
  experienceYearsInput = element(by.id('field_experienceYears'));
  qualificationStrInput = element(by.id('field_qualificationStr'));
  noOfResourceInput = element(by.id('field_noOfResource'));
  requiredOnDateInput = element(by.id('field_requiredOnDate'));

  qualificiationSelect = element(by.id('field_qualificiation'));
  skillTypeSelect = element(by.id('field_skillType'));
  jobLocationSelect = element(by.id('field_jobLocation'));
  examTypeSelect = element(by.id('field_examType'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setDurationInput(duration: string): Promise<void> {
    await this.durationInput.sendKeys(duration);
  }

  async getDurationInput(): Promise<string> {
    return await this.durationInput.getAttribute('value');
  }

  async setAgeInput(age: string): Promise<void> {
    await this.ageInput.sendKeys(age);
  }

  async getAgeInput(): Promise<string> {
    return await this.ageInput.getAttribute('value');
  }

  async setGenderSelect(gender: string): Promise<void> {
    await this.genderSelect.sendKeys(gender);
  }

  async getGenderSelect(): Promise<string> {
    return await this.genderSelect.element(by.css('option:checked')).getText();
  }

  async genderSelectLastOption(): Promise<void> {
    await this.genderSelect.all(by.tagName('option')).last().click();
  }

  async setExperienceMonthsInput(experienceMonths: string): Promise<void> {
    await this.experienceMonthsInput.sendKeys(experienceMonths);
  }

  async getExperienceMonthsInput(): Promise<string> {
    return await this.experienceMonthsInput.getAttribute('value');
  }

  async setExperienceYearsInput(experienceYears: string): Promise<void> {
    await this.experienceYearsInput.sendKeys(experienceYears);
  }

  async getExperienceYearsInput(): Promise<string> {
    return await this.experienceYearsInput.getAttribute('value');
  }

  async setQualificationStrInput(qualificationStr: string): Promise<void> {
    await this.qualificationStrInput.sendKeys(qualificationStr);
  }

  async getQualificationStrInput(): Promise<string> {
    return await this.qualificationStrInput.getAttribute('value');
  }

  async setNoOfResourceInput(noOfResource: string): Promise<void> {
    await this.noOfResourceInput.sendKeys(noOfResource);
  }

  async getNoOfResourceInput(): Promise<string> {
    return await this.noOfResourceInput.getAttribute('value');
  }

  async setRequiredOnDateInput(requiredOnDate: string): Promise<void> {
    await this.requiredOnDateInput.sendKeys(requiredOnDate);
  }

  async getRequiredOnDateInput(): Promise<string> {
    return await this.requiredOnDateInput.getAttribute('value');
  }

  async qualificiationSelectLastOption(): Promise<void> {
    await this.qualificiationSelect.all(by.tagName('option')).last().click();
  }

  async qualificiationSelectOption(option: string): Promise<void> {
    await this.qualificiationSelect.sendKeys(option);
  }

  getQualificiationSelect(): ElementFinder {
    return this.qualificiationSelect;
  }

  async getQualificiationSelectedOption(): Promise<string> {
    return await this.qualificiationSelect.element(by.css('option:checked')).getText();
  }

  async skillTypeSelectLastOption(): Promise<void> {
    await this.skillTypeSelect.all(by.tagName('option')).last().click();
  }

  async skillTypeSelectOption(option: string): Promise<void> {
    await this.skillTypeSelect.sendKeys(option);
  }

  getSkillTypeSelect(): ElementFinder {
    return this.skillTypeSelect;
  }

  async getSkillTypeSelectedOption(): Promise<string> {
    return await this.skillTypeSelect.element(by.css('option:checked')).getText();
  }

  async jobLocationSelectLastOption(): Promise<void> {
    await this.jobLocationSelect.all(by.tagName('option')).last().click();
  }

  async jobLocationSelectOption(option: string): Promise<void> {
    await this.jobLocationSelect.sendKeys(option);
  }

  getJobLocationSelect(): ElementFinder {
    return this.jobLocationSelect;
  }

  async getJobLocationSelectedOption(): Promise<string> {
    return await this.jobLocationSelect.element(by.css('option:checked')).getText();
  }

  async examTypeSelectLastOption(): Promise<void> {
    await this.examTypeSelect.all(by.tagName('option')).last().click();
  }

  async examTypeSelectOption(option: string): Promise<void> {
    await this.examTypeSelect.sendKeys(option);
  }

  getExamTypeSelect(): ElementFinder {
    return this.examTypeSelect;
  }

  async getExamTypeSelectedOption(): Promise<string> {
    return await this.examTypeSelect.element(by.css('option:checked')).getText();
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

export class JobRequisitionDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-jobRequisition-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-jobRequisition'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
