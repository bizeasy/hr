import { element, by, ElementFinder } from 'protractor';

export class WorkEffortStatusComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-work-effort-status div table .btn-danger'));
  title = element.all(by.css('sys-work-effort-status div h2#page-heading span')).first();
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

export class WorkEffortStatusUpdatePage {
  pageTitle = element(by.id('sys-work-effort-status-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  reasonInput = element(by.id('field_reason'));

  workEffortSelect = element(by.id('field_workEffort'));
  statusSelect = element(by.id('field_status'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setReasonInput(reason: string): Promise<void> {
    await this.reasonInput.sendKeys(reason);
  }

  async getReasonInput(): Promise<string> {
    return await this.reasonInput.getAttribute('value');
  }

  async workEffortSelectLastOption(): Promise<void> {
    await this.workEffortSelect.all(by.tagName('option')).last().click();
  }

  async workEffortSelectOption(option: string): Promise<void> {
    await this.workEffortSelect.sendKeys(option);
  }

  getWorkEffortSelect(): ElementFinder {
    return this.workEffortSelect;
  }

  async getWorkEffortSelectedOption(): Promise<string> {
    return await this.workEffortSelect.element(by.css('option:checked')).getText();
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

export class WorkEffortStatusDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-workEffortStatus-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-workEffortStatus'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
