import { element, by, ElementFinder } from 'protractor';

export class LotComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-lot div table .btn-danger'));
  title = element.all(by.css('sys-lot div h2#page-heading span')).first();
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

export class LotUpdatePage {
  pageTitle = element(by.id('sys-lot-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  creationDateInput = element(by.id('field_creationDate'));
  quantityInput = element(by.id('field_quantity'));
  expirationDateInput = element(by.id('field_expirationDate'));
  retestDateInput = element(by.id('field_retestDate'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setCreationDateInput(creationDate: string): Promise<void> {
    await this.creationDateInput.sendKeys(creationDate);
  }

  async getCreationDateInput(): Promise<string> {
    return await this.creationDateInput.getAttribute('value');
  }

  async setQuantityInput(quantity: string): Promise<void> {
    await this.quantityInput.sendKeys(quantity);
  }

  async getQuantityInput(): Promise<string> {
    return await this.quantityInput.getAttribute('value');
  }

  async setExpirationDateInput(expirationDate: string): Promise<void> {
    await this.expirationDateInput.sendKeys(expirationDate);
  }

  async getExpirationDateInput(): Promise<string> {
    return await this.expirationDateInput.getAttribute('value');
  }

  async setRetestDateInput(retestDate: string): Promise<void> {
    await this.retestDateInput.sendKeys(retestDate);
  }

  async getRetestDateInput(): Promise<string> {
    return await this.retestDateInput.getAttribute('value');
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

export class LotDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-lot-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-lot'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
