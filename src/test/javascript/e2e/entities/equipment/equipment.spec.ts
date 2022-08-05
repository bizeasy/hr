import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EquipmentComponentsPage, EquipmentDeleteDialog, EquipmentUpdatePage } from './equipment.page-object';

const expect = chai.expect;

describe('Equipment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let equipmentComponentsPage: EquipmentComponentsPage;
  let equipmentUpdatePage: EquipmentUpdatePage;
  let equipmentDeleteDialog: EquipmentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Equipment', async () => {
    await navBarPage.goToEntity('equipment');
    equipmentComponentsPage = new EquipmentComponentsPage();
    await browser.wait(ec.visibilityOf(equipmentComponentsPage.title), 5000);
    expect(await equipmentComponentsPage.getTitle()).to.eq('hrApp.equipment.home.title');
    await browser.wait(ec.or(ec.visibilityOf(equipmentComponentsPage.entities), ec.visibilityOf(equipmentComponentsPage.noResult)), 1000);
  });

  it('should load create Equipment page', async () => {
    await equipmentComponentsPage.clickOnCreateButton();
    equipmentUpdatePage = new EquipmentUpdatePage();
    expect(await equipmentUpdatePage.getPageTitle()).to.eq('hrApp.equipment.home.createOrEditLabel');
    await equipmentUpdatePage.cancel();
  });

  it('should create and save Equipment', async () => {
    const nbButtonsBeforeCreate = await equipmentComponentsPage.countDeleteButtons();

    await equipmentComponentsPage.clickOnCreateButton();

    await promise.all([
      equipmentUpdatePage.setNameInput('name'),
      equipmentUpdatePage.setDescriptionInput('description'),
      equipmentUpdatePage.setEquipmentNumberInput('equipmentNumber'),
      equipmentUpdatePage.setMinCapacityRangeInput('5'),
      equipmentUpdatePage.setMaxCapacityRangeInput('5'),
      equipmentUpdatePage.setMinOperationalRangeInput('5'),
      equipmentUpdatePage.setMaxOperationalRangeInput('5'),
      equipmentUpdatePage.setLastCalibratedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      equipmentUpdatePage.setCalibrationDueDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      equipmentUpdatePage.setChangeControlNoInput('changeControlNo'),
      equipmentUpdatePage.equipmentTypeSelectLastOption(),
      equipmentUpdatePage.statusSelectLastOption(),
    ]);

    expect(await equipmentUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await equipmentUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await equipmentUpdatePage.getEquipmentNumberInput()).to.eq(
      'equipmentNumber',
      'Expected EquipmentNumber value to be equals to equipmentNumber'
    );
    expect(await equipmentUpdatePage.getMinCapacityRangeInput()).to.eq('5', 'Expected minCapacityRange value to be equals to 5');
    expect(await equipmentUpdatePage.getMaxCapacityRangeInput()).to.eq('5', 'Expected maxCapacityRange value to be equals to 5');
    expect(await equipmentUpdatePage.getMinOperationalRangeInput()).to.eq('5', 'Expected minOperationalRange value to be equals to 5');
    expect(await equipmentUpdatePage.getMaxOperationalRangeInput()).to.eq('5', 'Expected maxOperationalRange value to be equals to 5');
    expect(await equipmentUpdatePage.getLastCalibratedDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected lastCalibratedDate value to be equals to 2000-12-31'
    );
    expect(await equipmentUpdatePage.getCalibrationDueDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected calibrationDueDate value to be equals to 2000-12-31'
    );
    expect(await equipmentUpdatePage.getChangeControlNoInput()).to.eq(
      'changeControlNo',
      'Expected ChangeControlNo value to be equals to changeControlNo'
    );

    await equipmentUpdatePage.save();
    expect(await equipmentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await equipmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Equipment', async () => {
    const nbButtonsBeforeDelete = await equipmentComponentsPage.countDeleteButtons();
    await equipmentComponentsPage.clickOnLastDeleteButton();

    equipmentDeleteDialog = new EquipmentDeleteDialog();
    expect(await equipmentDeleteDialog.getDialogTitle()).to.eq('hrApp.equipment.delete.question');
    await equipmentDeleteDialog.clickOnConfirmButton();

    expect(await equipmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
