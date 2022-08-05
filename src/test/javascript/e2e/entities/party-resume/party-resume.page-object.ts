import { element, by, ElementFinder } from 'protractor';

export class PartyResumeComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-party-resume div table .btn-danger'));
  title = element.all(by.css('sys-party-resume div h2#page-heading span')).first();
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

export class PartyResumeUpdatePage {
  pageTitle = element(by.id('sys-party-resume-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  textInput = element(by.id('field_text'));
  resumeDateInput = element(by.id('field_resumeDate'));
  fileAttachmentInput = element(by.id('file_fileAttachment'));
  attachmentUrlInput = element(by.id('field_attachmentUrl'));
  mimeTypeInput = element(by.id('field_mimeType'));

  partySelect = element(by.id('field_party'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setTextInput(text: string): Promise<void> {
    await this.textInput.sendKeys(text);
  }

  async getTextInput(): Promise<string> {
    return await this.textInput.getAttribute('value');
  }

  async setResumeDateInput(resumeDate: string): Promise<void> {
    await this.resumeDateInput.sendKeys(resumeDate);
  }

  async getResumeDateInput(): Promise<string> {
    return await this.resumeDateInput.getAttribute('value');
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

export class PartyResumeDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-partyResume-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-partyResume'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
