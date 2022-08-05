import { element, by, ElementFinder } from 'protractor';

export class FacilityComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('sys-facility div table .btn-danger'));
  title = element.all(by.css('sys-facility div h2#page-heading span')).first();
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

export class FacilityUpdatePage {
  pageTitle = element(by.id('sys-facility-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  nameInput = element(by.id('field_name'));
  descriptionInput = element(by.id('field_description'));
  facilityCodeInput = element(by.id('field_facilityCode'));
  facilitySizeInput = element(by.id('field_facilitySize'));
  costCenterCodeInput = element(by.id('field_costCenterCode'));

  facilityTypeSelect = element(by.id('field_facilityType'));
  parentFacilitySelect = element(by.id('field_parentFacility'));
  ownerPartySelect = element(by.id('field_ownerParty'));
  facilityGroupSelect = element(by.id('field_facilityGroup'));
  sizeUomSelect = element(by.id('field_sizeUom'));
  geoPointSelect = element(by.id('field_geoPoint'));
  inventoryItemTypeSelect = element(by.id('field_inventoryItemType'));
  statusSelect = element(by.id('field_status'));
  usageStatusSelect = element(by.id('field_usageStatus'));
  reviewedBySelect = element(by.id('field_reviewedBy'));
  approvedBySelect = element(by.id('field_approvedBy'));

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

  async setFacilityCodeInput(facilityCode: string): Promise<void> {
    await this.facilityCodeInput.sendKeys(facilityCode);
  }

  async getFacilityCodeInput(): Promise<string> {
    return await this.facilityCodeInput.getAttribute('value');
  }

  async setFacilitySizeInput(facilitySize: string): Promise<void> {
    await this.facilitySizeInput.sendKeys(facilitySize);
  }

  async getFacilitySizeInput(): Promise<string> {
    return await this.facilitySizeInput.getAttribute('value');
  }

  async setCostCenterCodeInput(costCenterCode: string): Promise<void> {
    await this.costCenterCodeInput.sendKeys(costCenterCode);
  }

  async getCostCenterCodeInput(): Promise<string> {
    return await this.costCenterCodeInput.getAttribute('value');
  }

  async facilityTypeSelectLastOption(): Promise<void> {
    await this.facilityTypeSelect.all(by.tagName('option')).last().click();
  }

  async facilityTypeSelectOption(option: string): Promise<void> {
    await this.facilityTypeSelect.sendKeys(option);
  }

  getFacilityTypeSelect(): ElementFinder {
    return this.facilityTypeSelect;
  }

  async getFacilityTypeSelectedOption(): Promise<string> {
    return await this.facilityTypeSelect.element(by.css('option:checked')).getText();
  }

  async parentFacilitySelectLastOption(): Promise<void> {
    await this.parentFacilitySelect.all(by.tagName('option')).last().click();
  }

  async parentFacilitySelectOption(option: string): Promise<void> {
    await this.parentFacilitySelect.sendKeys(option);
  }

  getParentFacilitySelect(): ElementFinder {
    return this.parentFacilitySelect;
  }

  async getParentFacilitySelectedOption(): Promise<string> {
    return await this.parentFacilitySelect.element(by.css('option:checked')).getText();
  }

  async ownerPartySelectLastOption(): Promise<void> {
    await this.ownerPartySelect.all(by.tagName('option')).last().click();
  }

  async ownerPartySelectOption(option: string): Promise<void> {
    await this.ownerPartySelect.sendKeys(option);
  }

  getOwnerPartySelect(): ElementFinder {
    return this.ownerPartySelect;
  }

  async getOwnerPartySelectedOption(): Promise<string> {
    return await this.ownerPartySelect.element(by.css('option:checked')).getText();
  }

  async facilityGroupSelectLastOption(): Promise<void> {
    await this.facilityGroupSelect.all(by.tagName('option')).last().click();
  }

  async facilityGroupSelectOption(option: string): Promise<void> {
    await this.facilityGroupSelect.sendKeys(option);
  }

  getFacilityGroupSelect(): ElementFinder {
    return this.facilityGroupSelect;
  }

  async getFacilityGroupSelectedOption(): Promise<string> {
    return await this.facilityGroupSelect.element(by.css('option:checked')).getText();
  }

  async sizeUomSelectLastOption(): Promise<void> {
    await this.sizeUomSelect.all(by.tagName('option')).last().click();
  }

  async sizeUomSelectOption(option: string): Promise<void> {
    await this.sizeUomSelect.sendKeys(option);
  }

  getSizeUomSelect(): ElementFinder {
    return this.sizeUomSelect;
  }

  async getSizeUomSelectedOption(): Promise<string> {
    return await this.sizeUomSelect.element(by.css('option:checked')).getText();
  }

  async geoPointSelectLastOption(): Promise<void> {
    await this.geoPointSelect.all(by.tagName('option')).last().click();
  }

  async geoPointSelectOption(option: string): Promise<void> {
    await this.geoPointSelect.sendKeys(option);
  }

  getGeoPointSelect(): ElementFinder {
    return this.geoPointSelect;
  }

  async getGeoPointSelectedOption(): Promise<string> {
    return await this.geoPointSelect.element(by.css('option:checked')).getText();
  }

  async inventoryItemTypeSelectLastOption(): Promise<void> {
    await this.inventoryItemTypeSelect.all(by.tagName('option')).last().click();
  }

  async inventoryItemTypeSelectOption(option: string): Promise<void> {
    await this.inventoryItemTypeSelect.sendKeys(option);
  }

  getInventoryItemTypeSelect(): ElementFinder {
    return this.inventoryItemTypeSelect;
  }

  async getInventoryItemTypeSelectedOption(): Promise<string> {
    return await this.inventoryItemTypeSelect.element(by.css('option:checked')).getText();
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

  async usageStatusSelectLastOption(): Promise<void> {
    await this.usageStatusSelect.all(by.tagName('option')).last().click();
  }

  async usageStatusSelectOption(option: string): Promise<void> {
    await this.usageStatusSelect.sendKeys(option);
  }

  getUsageStatusSelect(): ElementFinder {
    return this.usageStatusSelect;
  }

  async getUsageStatusSelectedOption(): Promise<string> {
    return await this.usageStatusSelect.element(by.css('option:checked')).getText();
  }

  async reviewedBySelectLastOption(): Promise<void> {
    await this.reviewedBySelect.all(by.tagName('option')).last().click();
  }

  async reviewedBySelectOption(option: string): Promise<void> {
    await this.reviewedBySelect.sendKeys(option);
  }

  getReviewedBySelect(): ElementFinder {
    return this.reviewedBySelect;
  }

  async getReviewedBySelectedOption(): Promise<string> {
    return await this.reviewedBySelect.element(by.css('option:checked')).getText();
  }

  async approvedBySelectLastOption(): Promise<void> {
    await this.approvedBySelect.all(by.tagName('option')).last().click();
  }

  async approvedBySelectOption(option: string): Promise<void> {
    await this.approvedBySelect.sendKeys(option);
  }

  getApprovedBySelect(): ElementFinder {
    return this.approvedBySelect;
  }

  async getApprovedBySelectedOption(): Promise<string> {
    return await this.approvedBySelect.element(by.css('option:checked')).getText();
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

export class FacilityDeleteDialog {
  private dialogTitle = element(by.id('sys-delete-facility-heading'));
  private confirmButton = element(by.id('sys-confirm-delete-facility'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
