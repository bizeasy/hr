import { element, by, ElementFinder } from 'protractor';

export class EmplPositionTypeRateComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-empl-position-type-rate div table .btn-danger'));
  title = element.all(by.css('sys-empl-position-type-rate div h2#page-heading span')).first();
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

export class EmplPositionTypeRateUpdatePage {
  pageTitle = element(by.id('sys-empl-position-type-rate-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  rateAmountInput = element(by.id('field_rateAmount'));
  fromDateInput = element(by.id('field_fromDate'));
  thruDateInput = element(by.id('field_thruDate'));

  rateTypeSelect = element(by.id('field_rateType'));
  emplPositionTypeSelect = element(by.id('field_emplPositionType'));
  payGradeSelect = element(by.id('field_payGrade'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setRateAmountInput(rateAmount: string): Promise<void> {
    await this.rateAmountInput.sendKeys(rateAmount);
  }

  async getRateAmountInput(): Promise<string> {
    return await this.rateAmountInput.getAttribute('value');
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

  async rateTypeSelectLastOption(): Promise<void> {
    await this.rateTypeSelect.all(by.tagName('option')).last().click();
  }

  async rateTypeSelectOption(option: string): Promise<void> {
    await this.rateTypeSelect.sendKeys(option);
  }

  getRateTypeSelect(): ElementFinder {
    return this.rateTypeSelect;
  }

  async getRateTypeSelectedOption(): Promise<string> {
    return await this.rateTypeSelect.element(by.css('option:checked')).getText();
  }

  async emplPositionTypeSelectLastOption(): Promise<void> {
    await this.emplPositionTypeSelect.all(by.tagName('option')).last().click();
  }

  async emplPositionTypeSelectOption(option: string): Promise<void> {
    await this.emplPositionTypeSelect.sendKeys(option);
  }

  getEmplPositionTypeSelect(): ElementFinder {
    return this.emplPositionTypeSelect;
  }

  async getEmplPositionTypeSelectedOption(): Promise<string> {
    return await this.emplPositionTypeSelect.element(by.css('option:checked')).getText();
  }

  async payGradeSelectLastOption(): Promise<void> {
    await this.payGradeSelect.all(by.tagName('option')).last().click();
  }

  async payGradeSelectOption(option: string): Promise<void> {
    await this.payGradeSelect.sendKeys(option);
  }

  getPayGradeSelect(): ElementFinder {
    return this.payGradeSelect;
  }

  async getPayGradeSelectedOption(): Promise<string> {
    return await this.payGradeSelect.element(by.css('option:checked')).getText();
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

export class EmplPositionTypeRateDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-emplPositionTypeRate-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-emplPositionTypeRate'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
