import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ShiftHolidaysComponentsPage, ShiftHolidaysDeleteDialog, ShiftHolidaysUpdatePage } from './shift-holidays.page-object';

const expect = chai.expect;

describe('ShiftHolidays e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let shiftHolidaysComponentsPage: ShiftHolidaysComponentsPage;
  let shiftHolidaysUpdatePage: ShiftHolidaysUpdatePage;
  let shiftHolidaysDeleteDialog: ShiftHolidaysDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ShiftHolidays', async () => {
    await navBarPage.goToEntity('shift-holidays');
    shiftHolidaysComponentsPage = new ShiftHolidaysComponentsPage();
    await browser.wait(ec.visibilityOf(shiftHolidaysComponentsPage.title), 5000);
    expect(await shiftHolidaysComponentsPage.getTitle()).to.eq('hrApp.shiftHolidays.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(shiftHolidaysComponentsPage.entities), ec.visibilityOf(shiftHolidaysComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ShiftHolidays page', async () => {
    await shiftHolidaysComponentsPage.clickOnCreateButton();
    shiftHolidaysUpdatePage = new ShiftHolidaysUpdatePage();
    expect(await shiftHolidaysUpdatePage.getPageTitle()).to.eq('hrApp.shiftHolidays.home.createOrEditLabel');
    await shiftHolidaysUpdatePage.cancel();
  });

  it('should create and save ShiftHolidays', async () => {
    const nbButtonsBeforeCreate = await shiftHolidaysComponentsPage.countDeleteButtons();

    await shiftHolidaysComponentsPage.clickOnCreateButton();

    await promise.all([
      shiftHolidaysUpdatePage.dayOfheWeekSelectLastOption(),
      shiftHolidaysUpdatePage.setMonthlyPaidLeavesInput('5'),
      shiftHolidaysUpdatePage.setYearlyPaidLeavesInput('5'),
      shiftHolidaysUpdatePage.shiftSelectLastOption(),
    ]);

    expect(await shiftHolidaysUpdatePage.getMonthlyPaidLeavesInput()).to.eq('5', 'Expected monthlyPaidLeaves value to be equals to 5');
    expect(await shiftHolidaysUpdatePage.getYearlyPaidLeavesInput()).to.eq('5', 'Expected yearlyPaidLeaves value to be equals to 5');

    await shiftHolidaysUpdatePage.save();
    expect(await shiftHolidaysUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await shiftHolidaysComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ShiftHolidays', async () => {
    const nbButtonsBeforeDelete = await shiftHolidaysComponentsPage.countDeleteButtons();
    await shiftHolidaysComponentsPage.clickOnLastDeleteButton();

    shiftHolidaysDeleteDialog = new ShiftHolidaysDeleteDialog();
    expect(await shiftHolidaysDeleteDialog.getDialogTitle()).to.eq('hrApp.shiftHolidays.delete.question');
    await shiftHolidaysDeleteDialog.clickOnConfirmButton();

    expect(await shiftHolidaysComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
