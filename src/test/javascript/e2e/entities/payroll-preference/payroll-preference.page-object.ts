import { element, by, ElementFinder } from 'protractor';

export class PayrollPreferenceComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-payroll-preference div table .btn-danger'));
  title = element.all(by.css('sys-payroll-preference div h2#page-heading span')).first();
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

export class PayrollPreferenceUpdatePage {
  pageTitle = element(by.id('sys-payroll-preference-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  fromDateInput = element(by.id('field_fromDate'));
  thruDateInput = element(by.id('field_thruDate'));
  sequenceNoInput = element(by.id('field_sequenceNo'));
  percentageInput = element(by.id('field_percentage'));
  flatAmountInput = element(by.id('field_flatAmount'));
  accountNumberInput = element(by.id('field_accountNumber'));
  bankNameInput = element(by.id('field_bankName'));
  ifscCodeInput = element(by.id('field_ifscCode'));
  branchInput = element(by.id('field_branch'));

  employeeSelect = element(by.id('field_employee'));
  deductionTypeSelect = element(by.id('field_deductionType'));
  paymentMethodTypeSelect = element(by.id('field_paymentMethodType'));
  periodTypeSelect = element(by.id('field_periodType'));

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

  async setSequenceNoInput(sequenceNo: string): Promise<void> {
    await this.sequenceNoInput.sendKeys(sequenceNo);
  }

  async getSequenceNoInput(): Promise<string> {
    return await this.sequenceNoInput.getAttribute('value');
  }

  async setPercentageInput(percentage: string): Promise<void> {
    await this.percentageInput.sendKeys(percentage);
  }

  async getPercentageInput(): Promise<string> {
    return await this.percentageInput.getAttribute('value');
  }

  async setFlatAmountInput(flatAmount: string): Promise<void> {
    await this.flatAmountInput.sendKeys(flatAmount);
  }

  async getFlatAmountInput(): Promise<string> {
    return await this.flatAmountInput.getAttribute('value');
  }

  async setAccountNumberInput(accountNumber: string): Promise<void> {
    await this.accountNumberInput.sendKeys(accountNumber);
  }

  async getAccountNumberInput(): Promise<string> {
    return await this.accountNumberInput.getAttribute('value');
  }

  async setBankNameInput(bankName: string): Promise<void> {
    await this.bankNameInput.sendKeys(bankName);
  }

  async getBankNameInput(): Promise<string> {
    return await this.bankNameInput.getAttribute('value');
  }

  async setIfscCodeInput(ifscCode: string): Promise<void> {
    await this.ifscCodeInput.sendKeys(ifscCode);
  }

  async getIfscCodeInput(): Promise<string> {
    return await this.ifscCodeInput.getAttribute('value');
  }

  async setBranchInput(branch: string): Promise<void> {
    await this.branchInput.sendKeys(branch);
  }

  async getBranchInput(): Promise<string> {
    return await this.branchInput.getAttribute('value');
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

  async deductionTypeSelectLastOption(): Promise<void> {
    await this.deductionTypeSelect.all(by.tagName('option')).last().click();
  }

  async deductionTypeSelectOption(option: string): Promise<void> {
    await this.deductionTypeSelect.sendKeys(option);
  }

  getDeductionTypeSelect(): ElementFinder {
    return this.deductionTypeSelect;
  }

  async getDeductionTypeSelectedOption(): Promise<string> {
    return await this.deductionTypeSelect.element(by.css('option:checked')).getText();
  }

  async paymentMethodTypeSelectLastOption(): Promise<void> {
    await this.paymentMethodTypeSelect.all(by.tagName('option')).last().click();
  }

  async paymentMethodTypeSelectOption(option: string): Promise<void> {
    await this.paymentMethodTypeSelect.sendKeys(option);
  }

  getPaymentMethodTypeSelect(): ElementFinder {
    return this.paymentMethodTypeSelect;
  }

  async getPaymentMethodTypeSelectedOption(): Promise<string> {
    return await this.paymentMethodTypeSelect.element(by.css('option:checked')).getText();
  }

  async periodTypeSelectLastOption(): Promise<void> {
    await this.periodTypeSelect.all(by.tagName('option')).last().click();
  }

  async periodTypeSelectOption(option: string): Promise<void> {
    await this.periodTypeSelect.sendKeys(option);
  }

  getPeriodTypeSelect(): ElementFinder {
    return this.periodTypeSelect;
  }

  async getPeriodTypeSelectedOption(): Promise<string> {
    return await this.periodTypeSelect.element(by.css('option:checked')).getText();
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

export class PayrollPreferenceDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-payrollPreference-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-payrollPreference'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
