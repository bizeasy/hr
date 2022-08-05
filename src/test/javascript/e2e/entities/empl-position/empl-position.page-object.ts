import { element, by, ElementFinder } from 'protractor';

export class EmplPositionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-empl-position div table .btn-danger'));
  title = element.all(by.css('sys-empl-position div h2#page-heading span')).first();
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

export class EmplPositionUpdatePage {
  pageTitle = element(by.id('sys-empl-position-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  maxBudgetInput = element(by.id('field_maxBudget'));
  minBudgetInput = element(by.id('field_minBudget'));
  estimatedFromDateInput = element(by.id('field_estimatedFromDate'));
  estimatedThruDateInput = element(by.id('field_estimatedThruDate'));
  paidJobInput = element(by.id('field_paidJob'));
  isFulltimeInput = element(by.id('field_isFulltime'));
  isTemporaryInput = element(by.id('field_isTemporary'));
  actualFromDateInput = element(by.id('field_actualFromDate'));
  actualThruDateInput = element(by.id('field_actualThruDate'));

  typeSelect = element(by.id('field_type'));
  partySelect = element(by.id('field_party'));
  statusSelect = element(by.id('field_status'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setMaxBudgetInput(maxBudget: string): Promise<void> {
    await this.maxBudgetInput.sendKeys(maxBudget);
  }

  async getMaxBudgetInput(): Promise<string> {
    return await this.maxBudgetInput.getAttribute('value');
  }

  async setMinBudgetInput(minBudget: string): Promise<void> {
    await this.minBudgetInput.sendKeys(minBudget);
  }

  async getMinBudgetInput(): Promise<string> {
    return await this.minBudgetInput.getAttribute('value');
  }

  async setEstimatedFromDateInput(estimatedFromDate: string): Promise<void> {
    await this.estimatedFromDateInput.sendKeys(estimatedFromDate);
  }

  async getEstimatedFromDateInput(): Promise<string> {
    return await this.estimatedFromDateInput.getAttribute('value');
  }

  async setEstimatedThruDateInput(estimatedThruDate: string): Promise<void> {
    await this.estimatedThruDateInput.sendKeys(estimatedThruDate);
  }

  async getEstimatedThruDateInput(): Promise<string> {
    return await this.estimatedThruDateInput.getAttribute('value');
  }

  getPaidJobInput(): ElementFinder {
    return this.paidJobInput;
  }

  getIsFulltimeInput(): ElementFinder {
    return this.isFulltimeInput;
  }

  getIsTemporaryInput(): ElementFinder {
    return this.isTemporaryInput;
  }

  async setActualFromDateInput(actualFromDate: string): Promise<void> {
    await this.actualFromDateInput.sendKeys(actualFromDate);
  }

  async getActualFromDateInput(): Promise<string> {
    return await this.actualFromDateInput.getAttribute('value');
  }

  async setActualThruDateInput(actualThruDate: string): Promise<void> {
    await this.actualThruDateInput.sendKeys(actualThruDate);
  }

  async getActualThruDateInput(): Promise<string> {
    return await this.actualThruDateInput.getAttribute('value');
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

  async partySelectLastOption(): Promise<void> {
    await this.partySelect.all(by.tagName('option')).last().click();
  }

  async partySelectOption(option: string): Promise<void> {
    await this.partySelect.sendKeys(option);
  }

  getPartySelect(): ElementFinder {
    return this.partySelect;
  }

  async getPartySelectedOption(): Promise<string> {
    return await this.partySelect.element(by.css('option:checked')).getText();
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

export class EmplPositionDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-emplPosition-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-emplPosition'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
