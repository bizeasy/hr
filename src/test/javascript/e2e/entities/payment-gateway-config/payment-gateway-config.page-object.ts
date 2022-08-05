import { element, by, ElementFinder } from 'protractor';

export class PaymentGatewayConfigComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-payment-gateway-config div table .btn-danger'));
  title = element.all(by.css('sys-payment-gateway-config div h2#page-heading span')).first();
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

export class PaymentGatewayConfigUpdatePage {
  pageTitle = element(by.id('sys-payment-gateway-config-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  authUrlInput = element(by.id('field_authUrl'));
  releaseUrlInput = element(by.id('field_releaseUrl'));
  refundUrlInput = element(by.id('field_refundUrl'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setAuthUrlInput(authUrl: string): Promise<void> {
    await this.authUrlInput.sendKeys(authUrl);
  }

  async getAuthUrlInput(): Promise<string> {
    return await this.authUrlInput.getAttribute('value');
  }

  async setReleaseUrlInput(releaseUrl: string): Promise<void> {
    await this.releaseUrlInput.sendKeys(releaseUrl);
  }

  async getReleaseUrlInput(): Promise<string> {
    return await this.releaseUrlInput.getAttribute('value');
  }

  async setRefundUrlInput(refundUrl: string): Promise<void> {
    await this.refundUrlInput.sendKeys(refundUrl);
  }

  async getRefundUrlInput(): Promise<string> {
    return await this.refundUrlInput.getAttribute('value');
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

export class PaymentGatewayConfigDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-paymentGatewayConfig-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-paymentGatewayConfig'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
