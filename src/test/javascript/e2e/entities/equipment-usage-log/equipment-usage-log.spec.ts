import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  EquipmentUsageLogComponentsPage,
  EquipmentUsageLogDeleteDialog,
  EquipmentUsageLogUpdatePage,
} from './equipment-usage-log.page-object';

const expect = chai.expect;

describe('EquipmentUsageLog e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let equipmentUsageLogComponentsPage: EquipmentUsageLogComponentsPage;
  let equipmentUsageLogUpdatePage: EquipmentUsageLogUpdatePage;
  let equipmentUsageLogDeleteDialog: EquipmentUsageLogDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EquipmentUsageLogs', async () => {
    await navBarPage.goToEntity('equipment-usage-log');
    equipmentUsageLogComponentsPage = new EquipmentUsageLogComponentsPage();
    await browser.wait(ec.visibilityOf(equipmentUsageLogComponentsPage.title), 5000);
    expect(await equipmentUsageLogComponentsPage.getTitle()).to.eq('hrApp.equipmentUsageLog.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(equipmentUsageLogComponentsPage.entities), ec.visibilityOf(equipmentUsageLogComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EquipmentUsageLog page', async () => {
    await equipmentUsageLogComponentsPage.clickOnCreateButton();
    equipmentUsageLogUpdatePage = new EquipmentUsageLogUpdatePage();
    expect(await equipmentUsageLogUpdatePage.getPageTitle()).to.eq('hrApp.equipmentUsageLog.home.createOrEditLabel');
    await equipmentUsageLogUpdatePage.cancel();
  });

  it('should create and save EquipmentUsageLogs', async () => {
    const nbButtonsBeforeCreate = await equipmentUsageLogComponentsPage.countDeleteButtons();

    await equipmentUsageLogComponentsPage.clickOnCreateButton();

    await promise.all([
      equipmentUsageLogUpdatePage.setFromTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      equipmentUsageLogUpdatePage.setToTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      equipmentUsageLogUpdatePage.setRemarksInput('remarks'),
      equipmentUsageLogUpdatePage.equipmentSelectLastOption(),
      equipmentUsageLogUpdatePage.cleanedBySelectLastOption(),
      equipmentUsageLogUpdatePage.checkedBySelectLastOption(),
    ]);

    expect(await equipmentUsageLogUpdatePage.getFromTimeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromTime value to be equals to 2000-12-31'
    );
    expect(await equipmentUsageLogUpdatePage.getToTimeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected toTime value to be equals to 2000-12-31'
    );
    expect(await equipmentUsageLogUpdatePage.getRemarksInput()).to.eq('remarks', 'Expected Remarks value to be equals to remarks');

    await equipmentUsageLogUpdatePage.save();
    expect(await equipmentUsageLogUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await equipmentUsageLogComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EquipmentUsageLog', async () => {
    const nbButtonsBeforeDelete = await equipmentUsageLogComponentsPage.countDeleteButtons();
    await equipmentUsageLogComponentsPage.clickOnLastDeleteButton();

    equipmentUsageLogDeleteDialog = new EquipmentUsageLogDeleteDialog();
    expect(await equipmentUsageLogDeleteDialog.getDialogTitle()).to.eq('hrApp.equipmentUsageLog.delete.question');
    await equipmentUsageLogDeleteDialog.clickOnConfirmButton();

    expect(await equipmentUsageLogComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
