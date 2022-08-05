import { element, by, ElementFinder } from 'protractor';

export class CommunicationEventComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-communication-event div table .btn-danger'));
  title = element.all(by.css('sys-communication-event div h2#page-heading span')).first();
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

export class CommunicationEventUpdatePage {
  pageTitle = element(by.id('sys-communication-event-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  entryDateInput = element(by.id('field_entryDate'));
  subjectInput = element(by.id('field_subject'));
  contentInput = element(by.id('field_content'));
  fromStringInput = element(by.id('field_fromString'));
  toStringInput = element(by.id('field_toString'));
  ccStringInput = element(by.id('field_ccString'));
  messageInput = element(by.id('field_message'));
  dateStartedInput = element(by.id('field_dateStarted'));
  dateEndedInput = element(by.id('field_dateEnded'));
  infoInput = element(by.id('field_info'));

  communicationEventTypeSelect = element(by.id('field_communicationEventType'));
  statusSelect = element(by.id('field_status'));
  contactMechTypeSelect = element(by.id('field_contactMechType'));
  contactMechFromSelect = element(by.id('field_contactMechFrom'));
  contactMechToSelect = element(by.id('field_contactMechTo'));
  fromPartySelect = element(by.id('field_fromParty'));
  toPartySelect = element(by.id('field_toParty'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setEntryDateInput(entryDate: string): Promise<void> {
    await this.entryDateInput.sendKeys(entryDate);
  }

  async getEntryDateInput(): Promise<string> {
    return await this.entryDateInput.getAttribute('value');
  }

  async setSubjectInput(subject: string): Promise<void> {
    await this.subjectInput.sendKeys(subject);
  }

  async getSubjectInput(): Promise<string> {
    return await this.subjectInput.getAttribute('value');
  }

  async setContentInput(content: string): Promise<void> {
    await this.contentInput.sendKeys(content);
  }

  async getContentInput(): Promise<string> {
    return await this.contentInput.getAttribute('value');
  }

  async setFromStringInput(fromString: string): Promise<void> {
    await this.fromStringInput.sendKeys(fromString);
  }

  async getFromStringInput(): Promise<string> {
    return await this.fromStringInput.getAttribute('value');
  }

  async setToStringInput(toString: string): Promise<void> {
    await this.toStringInput.sendKeys(toString);
  }

  async getToStringInput(): Promise<string> {
    return await this.toStringInput.getAttribute('value');
  }

  async setCcStringInput(ccString: string): Promise<void> {
    await this.ccStringInput.sendKeys(ccString);
  }

  async getCcStringInput(): Promise<string> {
    return await this.ccStringInput.getAttribute('value');
  }

  async setMessageInput(message: string): Promise<void> {
    await this.messageInput.sendKeys(message);
  }

  async getMessageInput(): Promise<string> {
    return await this.messageInput.getAttribute('value');
  }

  async setDateStartedInput(dateStarted: string): Promise<void> {
    await this.dateStartedInput.sendKeys(dateStarted);
  }

  async getDateStartedInput(): Promise<string> {
    return await this.dateStartedInput.getAttribute('value');
  }

  async setDateEndedInput(dateEnded: string): Promise<void> {
    await this.dateEndedInput.sendKeys(dateEnded);
  }

  async getDateEndedInput(): Promise<string> {
    return await this.dateEndedInput.getAttribute('value');
  }

  async setInfoInput(info: string): Promise<void> {
    await this.infoInput.sendKeys(info);
  }

  async getInfoInput(): Promise<string> {
    return await this.infoInput.getAttribute('value');
  }

  async communicationEventTypeSelectLastOption(): Promise<void> {
    await this.communicationEventTypeSelect.all(by.tagName('option')).last().click();
  }

  async communicationEventTypeSelectOption(option: string): Promise<void> {
    await this.communicationEventTypeSelect.sendKeys(option);
  }

  getCommunicationEventTypeSelect(): ElementFinder {
    return this.communicationEventTypeSelect;
  }

  async getCommunicationEventTypeSelectedOption(): Promise<string> {
    return await this.communicationEventTypeSelect.element(by.css('option:checked')).getText();
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

  async contactMechTypeSelectLastOption(): Promise<void> {
    await this.contactMechTypeSelect.all(by.tagName('option')).last().click();
  }

  async contactMechTypeSelectOption(option: string): Promise<void> {
    await this.contactMechTypeSelect.sendKeys(option);
  }

  getContactMechTypeSelect(): ElementFinder {
    return this.contactMechTypeSelect;
  }

  async getContactMechTypeSelectedOption(): Promise<string> {
    return await this.contactMechTypeSelect.element(by.css('option:checked')).getText();
  }

  async contactMechFromSelectLastOption(): Promise<void> {
    await this.contactMechFromSelect.all(by.tagName('option')).last().click();
  }

  async contactMechFromSelectOption(option: string): Promise<void> {
    await this.contactMechFromSelect.sendKeys(option);
  }

  getContactMechFromSelect(): ElementFinder {
    return this.contactMechFromSelect;
  }

  async getContactMechFromSelectedOption(): Promise<string> {
    return await this.contactMechFromSelect.element(by.css('option:checked')).getText();
  }

  async contactMechToSelectLastOption(): Promise<void> {
    await this.contactMechToSelect.all(by.tagName('option')).last().click();
  }

  async contactMechToSelectOption(option: string): Promise<void> {
    await this.contactMechToSelect.sendKeys(option);
  }

  getContactMechToSelect(): ElementFinder {
    return this.contactMechToSelect;
  }

  async getContactMechToSelectedOption(): Promise<string> {
    return await this.contactMechToSelect.element(by.css('option:checked')).getText();
  }

  async fromPartySelectLastOption(): Promise<void> {
    await this.fromPartySelect.all(by.tagName('option')).last().click();
  }

  async fromPartySelectOption(option: string): Promise<void> {
    await this.fromPartySelect.sendKeys(option);
  }

  getFromPartySelect(): ElementFinder {
    return this.fromPartySelect;
  }

  async getFromPartySelectedOption(): Promise<string> {
    return await this.fromPartySelect.element(by.css('option:checked')).getText();
  }

  async toPartySelectLastOption(): Promise<void> {
    await this.toPartySelect.all(by.tagName('option')).last().click();
  }

  async toPartySelectOption(option: string): Promise<void> {
    await this.toPartySelect.sendKeys(option);
  }

  getToPartySelect(): ElementFinder {
    return this.toPartySelect;
  }

  async getToPartySelectedOption(): Promise<string> {
    return await this.toPartySelect.element(by.css('option:checked')).getText();
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

export class CommunicationEventDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-communicationEvent-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-communicationEvent'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
