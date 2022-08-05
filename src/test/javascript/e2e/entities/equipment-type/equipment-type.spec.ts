import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EquipmentTypeComponentsPage, EquipmentTypeDeleteDialog, EquipmentTypeUpdatePage } from './equipment-type.page-object';

const expect = chai.expect;

describe('EquipmentType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let equipmentTypeComponentsPage: EquipmentTypeComponentsPage;
  let equipmentTypeUpdatePage: EquipmentTypeUpdatePage;
  let equipmentTypeDeleteDialog: EquipmentTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EquipmentTypes', async () => {
    await navBarPage.goToEntity('equipment-type');
    equipmentTypeComponentsPage = new EquipmentTypeComponentsPage();
    await browser.wait(ec.visibilityOf(equipmentTypeComponentsPage.title), 5000);
    expect(await equipmentTypeComponentsPage.getTitle()).to.eq('hrApp.equipmentType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(equipmentTypeComponentsPage.entities), ec.visibilityOf(equipmentTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EquipmentType page', async () => {
    await equipmentTypeComponentsPage.clickOnCreateButton();
    equipmentTypeUpdatePage = new EquipmentTypeUpdatePage();
    expect(await equipmentTypeUpdatePage.getPageTitle()).to.eq('hrApp.equipmentType.home.createOrEditLabel');
    await equipmentTypeUpdatePage.cancel();
  });

  it('should create and save EquipmentTypes', async () => {
    const nbButtonsBeforeCreate = await equipmentTypeComponentsPage.countDeleteButtons();

    await equipmentTypeComponentsPage.clickOnCreateButton();

    await promise.all([equipmentTypeUpdatePage.setNameInput('name'), equipmentTypeUpdatePage.setDescriptionInput('description')]);

    expect(await equipmentTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await equipmentTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await equipmentTypeUpdatePage.save();
    expect(await equipmentTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await equipmentTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last EquipmentType', async () => {
    const nbButtonsBeforeDelete = await equipmentTypeComponentsPage.countDeleteButtons();
    await equipmentTypeComponentsPage.clickOnLastDeleteButton();

    equipmentTypeDeleteDialog = new EquipmentTypeDeleteDialog();
    expect(await equipmentTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.equipmentType.delete.question');
    await equipmentTypeDeleteDialog.clickOnConfirmButton();

    expect(await equipmentTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
