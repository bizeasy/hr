import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ShiftComponentsPage, ShiftDeleteDialog, ShiftUpdatePage } from './shift.page-object';

const expect = chai.expect;

describe('Shift e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let shiftComponentsPage: ShiftComponentsPage;
  let shiftUpdatePage: ShiftUpdatePage;
  let shiftDeleteDialog: ShiftDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Shifts', async () => {
    await navBarPage.goToEntity('shift');
    shiftComponentsPage = new ShiftComponentsPage();
    await browser.wait(ec.visibilityOf(shiftComponentsPage.title), 5000);
    expect(await shiftComponentsPage.getTitle()).to.eq('hrApp.shift.home.title');
    await browser.wait(ec.or(ec.visibilityOf(shiftComponentsPage.entities), ec.visibilityOf(shiftComponentsPage.noResult)), 1000);
  });

  it('should load create Shift page', async () => {
    await shiftComponentsPage.clickOnCreateButton();
    shiftUpdatePage = new ShiftUpdatePage();
    expect(await shiftUpdatePage.getPageTitle()).to.eq('hrApp.shift.home.createOrEditLabel');
    await shiftUpdatePage.cancel();
  });

  it('should create and save Shifts', async () => {
    const nbButtonsBeforeCreate = await shiftComponentsPage.countDeleteButtons();

    await shiftComponentsPage.clickOnCreateButton();

    await promise.all([
      shiftUpdatePage.setNameInput('name'),
      shiftUpdatePage.setFromTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      shiftUpdatePage.setToTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      shiftUpdatePage.setWorkingHrsInput('5'),
      shiftUpdatePage.setMonthlyPaidLeavesInput('5'),
    ]);

    expect(await shiftUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await shiftUpdatePage.getFromTimeInput()).to.contain('2001-01-01T02:30', 'Expected fromTime value to be equals to 2000-12-31');
    expect(await shiftUpdatePage.getToTimeInput()).to.contain('2001-01-01T02:30', 'Expected toTime value to be equals to 2000-12-31');
    expect(await shiftUpdatePage.getWorkingHrsInput()).to.eq('5', 'Expected workingHrs value to be equals to 5');
    expect(await shiftUpdatePage.getMonthlyPaidLeavesInput()).to.eq('5', 'Expected monthlyPaidLeaves value to be equals to 5');

    await shiftUpdatePage.save();
    expect(await shiftUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await shiftComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Shift', async () => {
    const nbButtonsBeforeDelete = await shiftComponentsPage.countDeleteButtons();
    await shiftComponentsPage.clickOnLastDeleteButton();

    shiftDeleteDialog = new ShiftDeleteDialog();
    expect(await shiftDeleteDialog.getDialogTitle()).to.eq('hrApp.shift.delete.question');
    await shiftDeleteDialog.clickOnConfirmButton();

    expect(await shiftComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
