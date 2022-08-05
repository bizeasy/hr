import { element, by, ElementFinder } from 'protractor';

export class OrderStatusComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-order-status div table .btn-danger'));
  title = element.all(by.css('sys-order-status div h2#page-heading span')).first();
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

export class OrderStatusUpdatePage {
  pageTitle = element(by.id('sys-order-status-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  statusDateTimeInput = element(by.id('field_statusDateTime'));
  sequenceNoInput = element(by.id('field_sequenceNo'));

  statusSelect = element(by.id('field_status'));
  orderSelect = element(by.id('field_order'));
  reasonSelect = element(by.id('field_reason'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setStatusDateTimeInput(statusDateTime: string): Promise<void> {
    await this.statusDateTimeInput.sendKeys(statusDateTime);
  }

  async getStatusDateTimeInput(): Promise<string> {
    return await this.statusDateTimeInput.getAttribute('value');
  }

  async setSequenceNoInput(sequenceNo: string): Promise<void> {
    await this.sequenceNoInput.sendKeys(sequenceNo);
  }

  async getSequenceNoInput(): Promise<string> {
    return await this.sequenceNoInput.getAttribute('value');
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

  async orderSelectLastOption(): Promise<void> {
    await this.orderSelect.all(by.tagName('option')).last().click();
  }

  async orderSelectOption(option: string): Promise<void> {
    await this.orderSelect.sendKeys(option);
  }

  getOrderSelect(): ElementFinder {
    return this.orderSelect;
  }

  async getOrderSelectedOption(): Promise<string> {
    return await this.orderSelect.element(by.css('option:checked')).getText();
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

export class OrderStatusDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-orderStatus-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-orderStatus'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
