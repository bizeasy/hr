import { element, by, ElementFinder } from 'protractor';

export class CatalogueComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-catalogue div table .btn-danger'));
  title = element.all(by.css('sys-catalogue div h2#page-heading span')).first();
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

export class CatalogueUpdatePage {
  pageTitle = element(by.id('sys-catalogue-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  sequenceNoInput = element(by.id('field_sequenceNo'));
  imagePathInput = element(by.id('field_imagePath'));
  mobileImagePathInput = element(by.id('field_mobileImagePath'));
  altImage1Input = element(by.id('field_altImage1'));
  altImage2Input = element(by.id('field_altImage2'));
  altImage3Input = element(by.id('field_altImage3'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNameInput(name: string): Promise<void> {
    await this.nameInput.sendKeys(name);
  }

  async getNameInput(): Promise<string> {
    return await this.nameInput.getAttribute('value');
  }

  async setDescriptionInput(description: string): Promise<void> {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput(): Promise<string> {
    return await this.descriptionInput.getAttribute('value');
  }

  async setSequenceNoInput(sequenceNo: string): Promise<void> {
    await this.sequenceNoInput.sendKeys(sequenceNo);
  }

  async getSequenceNoInput(): Promise<string> {
    return await this.sequenceNoInput.getAttribute('value');
  }

  async setImagePathInput(imagePath: string): Promise<void> {
    await this.imagePathInput.sendKeys(imagePath);
  }

  async getImagePathInput(): Promise<string> {
    return await this.imagePathInput.getAttribute('value');
  }

  async setMobileImagePathInput(mobileImagePath: string): Promise<void> {
    await this.mobileImagePathInput.sendKeys(mobileImagePath);
  }

  async getMobileImagePathInput(): Promise<string> {
    return await this.mobileImagePathInput.getAttribute('value');
  }

  async setAltImage1Input(altImage1: string): Promise<void> {
    await this.altImage1Input.sendKeys(altImage1);
  }

  async getAltImage1Input(): Promise<string> {
    return await this.altImage1Input.getAttribute('value');
  }

  async setAltImage2Input(altImage2: string): Promise<void> {
    await this.altImage2Input.sendKeys(altImage2);
  }

  async getAltImage2Input(): Promise<string> {
    return await this.altImage2Input.getAttribute('value');
  }

  async setAltImage3Input(altImage3: string): Promise<void> {
    await this.altImage3Input.sendKeys(altImage3);
  }

  async getAltImage3Input(): Promise<string> {
    return await this.altImage3Input.getAttribute('value');
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

export class CatalogueDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-catalogue-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-catalogue'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
