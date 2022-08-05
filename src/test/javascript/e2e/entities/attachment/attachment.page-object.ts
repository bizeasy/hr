import { element, by, ElementFinder } from 'protractor';

export class AttachmentComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-attachment div table .btn-danger'));
  title = element.all(by.css('sys-attachment div h2#page-heading span')).first();
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

export class AttachmentUpdatePage {
  pageTitle = element(by.id('sys-attachment-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  fileAttachmentInput = element(by.id('file_fileAttachment'));
  attachmentUrlInput = element(by.id('field_attachmentUrl'));
  mimeTypeInput = element(by.id('field_mimeType'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setFileAttachmentInput(fileAttachment: string): Promise<void> {
    await this.fileAttachmentInput.sendKeys(fileAttachment);
  }

  async getFileAttachmentInput(): Promise<string> {
    return await this.fileAttachmentInput.getAttribute('value');
  }

  async setAttachmentUrlInput(attachmentUrl: string): Promise<void> {
    await this.attachmentUrlInput.sendKeys(attachmentUrl);
  }

  async getAttachmentUrlInput(): Promise<string> {
    return await this.attachmentUrlInput.getAttribute('value');
  }

  async setMimeTypeInput(mimeType: string): Promise<void> {
    await this.mimeTypeInput.sendKeys(mimeType);
  }

  async getMimeTypeInput(): Promise<string> {
    return await this.mimeTypeInput.getAttribute('value');
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

export class AttachmentDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-attachment-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-attachment'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
