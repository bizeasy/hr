import { element, by, ElementFinder } from 'protractor';

export class PerfReviewItemComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-perf-review-item div table .btn-danger'));
  title = element.all(by.css('sys-perf-review-item div h2#page-heading span')).first();
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

export class PerfReviewItemUpdatePage {
  pageTitle = element(by.id('sys-perf-review-item-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  sequenceNoInput = element(by.id('field_sequenceNo'));
  commentsInput = element(by.id('field_comments'));

  perfReviewSelect = element(by.id('field_perfReview'));
  perfRatingTypeSelect = element(by.id('field_perfRatingType'));
  typeSelect = element(by.id('field_type'));
  employeeSelect = element(by.id('field_employee'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSequenceNoInput(sequenceNo: string): Promise<void> {
    await this.sequenceNoInput.sendKeys(sequenceNo);
  }

  async getSequenceNoInput(): Promise<string> {
    return await this.sequenceNoInput.getAttribute('value');
  }

  async setCommentsInput(comments: string): Promise<void> {
    await this.commentsInput.sendKeys(comments);
  }

  async getCommentsInput(): Promise<string> {
    return await this.commentsInput.getAttribute('value');
  }

  async perfReviewSelectLastOption(): Promise<void> {
    await this.perfReviewSelect.all(by.tagName('option')).last().click();
  }

  async perfReviewSelectOption(option: string): Promise<void> {
    await this.perfReviewSelect.sendKeys(option);
  }

  getPerfReviewSelect(): ElementFinder {
    return this.perfReviewSelect;
  }

  async getPerfReviewSelectedOption(): Promise<string> {
    return await this.perfReviewSelect.element(by.css('option:checked')).getText();
  }

  async perfRatingTypeSelectLastOption(): Promise<void> {
    await this.perfRatingTypeSelect.all(by.tagName('option')).last().click();
  }

  async perfRatingTypeSelectOption(option: string): Promise<void> {
    await this.perfRatingTypeSelect.sendKeys(option);
  }

  getPerfRatingTypeSelect(): ElementFinder {
    return this.perfRatingTypeSelect;
  }

  async getPerfRatingTypeSelectedOption(): Promise<string> {
    return await this.perfRatingTypeSelect.element(by.css('option:checked')).getText();
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

export class PerfReviewItemDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-perfReviewItem-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-perfReviewItem'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
