import { element, by, ElementFinder } from 'protractor';

export class StatusValidChangeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-status-valid-change div table .btn-danger'));
  title = element.all(by.css('sys-status-valid-change div h2#page-heading span')).first();
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

export class StatusValidChangeUpdatePage {
  pageTitle = element(by.id('sys-status-valid-change-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  transitionNameInput = element(by.id('field_transitionName'));
  rulesInput = element(by.id('field_rules'));

  statusSelect = element(by.id('field_status'));
  statusToSelect = element(by.id('field_statusTo'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTransitionNameInput(transitionName: string): Promise<void> {
    await this.transitionNameInput.sendKeys(transitionName);
  }

  async getTransitionNameInput(): Promise<string> {
    return await this.transitionNameInput.getAttribute('value');
  }

  async setRulesInput(rules: string): Promise<void> {
    await this.rulesInput.sendKeys(rules);
  }

  async getRulesInput(): Promise<string> {
    return await this.rulesInput.getAttribute('value');
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

  async statusToSelectLastOption(): Promise<void> {
    await this.statusToSelect.all(by.tagName('option')).last().click();
  }

  async statusToSelectOption(option: string): Promise<void> {
    await this.statusToSelect.sendKeys(option);
  }

  getStatusToSelect(): ElementFinder {
    return this.statusToSelect;
  }

  async getStatusToSelectedOption(): Promise<string> {
    return await this.statusToSelect.element(by.css('option:checked')).getText();
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

export class StatusValidChangeDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-statusValidChange-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-statusValidChange'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
