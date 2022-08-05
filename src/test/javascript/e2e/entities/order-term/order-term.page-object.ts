import { element, by, ElementFinder } from 'protractor';

export class OrderTermComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-order-term div table .btn-danger'));
  title = element.all(by.css('sys-order-term div h2#page-heading span')).first();
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

export class OrderTermUpdatePage {
  pageTitle = element(by.id('sys-order-term-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  sequenceNoInput = element(by.id('field_sequenceNo'));
  nameInput = element(by.id('field_name'));
  detailInput = element(by.id('field_detail'));
  termValueInput = element(by.id('field_termValue'));
  termDaysInput = element(by.id('field_termDays'));
  textValueInput = element(by.id('field_textValue'));

  orderSelect = element(by.id('field_order'));
  itemSelect = element(by.id('field_item'));
  termSelect = element(by.id('field_term'));
  typeSelect = element(by.id('field_type'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setSequenceNoInput(sequenceNo: string): Promise<void> {
    await this.sequenceNoInput.sendKeys(sequenceNo);
  }

  async getSequenceNoInput(): Promise<string> {
    return await this.sequenceNoInput.getAttribute('value');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setDetailInput(detail: string): Promise<void> {
    await this.detailInput.sendKeys(detail);
  }

  async getDetailInput(): Promise<string> {
    return await this.detailInput.getAttribute('value');
  }

  async setTermValueInput(termValue: string): Promise<void> {
    await this.termValueInput.sendKeys(termValue);
  }

  async getTermValueInput(): Promise<string> {
    return await this.termValueInput.getAttribute('value');
  }

  async setTermDaysInput(termDays: string): Promise<void> {
    await this.termDaysInput.sendKeys(termDays);
  }

  async getTermDaysInput(): Promise<string> {
    return await this.termDaysInput.getAttribute('value');
  }

  async setTextValueInput(textValue: string): Promise<void> {
    await this.textValueInput.sendKeys(textValue);
  }

  async getTextValueInput(): Promise<string> {
    return await this.textValueInput.getAttribute('value');
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

  async itemSelectLastOption(): Promise<void> {
    await this.itemSelect.all(by.tagName('option')).last().click();
  }

  async itemSelectOption(option: string): Promise<void> {
    await this.itemSelect.sendKeys(option);
  }

  getItemSelect(): ElementFinder {
    return this.itemSelect;
  }

  async getItemSelectedOption(): Promise<string> {
    return await this.itemSelect.element(by.css('option:checked')).getText();
  }

  async termSelectLastOption(): Promise<void> {
    await this.termSelect.all(by.tagName('option')).last().click();
  }

  async termSelectOption(option: string): Promise<void> {
    await this.termSelect.sendKeys(option);
  }

  getTermSelect(): ElementFinder {
    return this.termSelect;
  }

  async getTermSelectedOption(): Promise<string> {
    return await this.termSelect.element(by.css('option:checked')).getText();
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

export class OrderTermDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-orderTerm-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-orderTerm'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
