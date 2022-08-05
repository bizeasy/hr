import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TimeSheetComponentsPage, TimeSheetDeleteDialog, TimeSheetUpdatePage } from './time-sheet.page-object';

const expect = chai.expect;

describe('TimeSheet e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let timeSheetComponentsPage: TimeSheetComponentsPage;
  let timeSheetUpdatePage: TimeSheetUpdatePage;
  let timeSheetDeleteDialog: TimeSheetDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TimeSheets', async () => {
    await navBarPage.goToEntity('time-sheet');
    timeSheetComponentsPage = new TimeSheetComponentsPage();
    await browser.wait(ec.visibilityOf(timeSheetComponentsPage.title), 5000);
    expect(await timeSheetComponentsPage.getTitle()).to.eq('hrApp.timeSheet.home.title');
    await browser.wait(ec.or(ec.visibilityOf(timeSheetComponentsPage.entities), ec.visibilityOf(timeSheetComponentsPage.noResult)), 1000);
  });

  it('should load create TimeSheet page', async () => {
    await timeSheetComponentsPage.clickOnCreateButton();
    timeSheetUpdatePage = new TimeSheetUpdatePage();
    expect(await timeSheetUpdatePage.getPageTitle()).to.eq('hrApp.timeSheet.home.createOrEditLabel');
    await timeSheetUpdatePage.cancel();
  });

  it('should create and save TimeSheets', async () => {
    const nbButtonsBeforeCreate = await timeSheetComponentsPage.countDeleteButtons();

    await timeSheetComponentsPage.clickOnCreateButton();

    await promise.all([
      timeSheetUpdatePage.setOverTimeDaysInput('5'),
      timeSheetUpdatePage.setLeavesInput('5'),
      timeSheetUpdatePage.setUnappliedLeavesInput('5'),
      timeSheetUpdatePage.setHalfDaysInput('5'),
      timeSheetUpdatePage.setTotalWorkingHoursInput('5'),
      timeSheetUpdatePage.timePeriodSelectLastOption(),
    ]);

    expect(await timeSheetUpdatePage.getOverTimeDaysInput()).to.eq('5', 'Expected overTimeDays value to be equals to 5');
    expect(await timeSheetUpdatePage.getLeavesInput()).to.eq('5', 'Expected leaves value to be equals to 5');
    expect(await timeSheetUpdatePage.getUnappliedLeavesInput()).to.eq('5', 'Expected unappliedLeaves value to be equals to 5');
    expect(await timeSheetUpdatePage.getHalfDaysInput()).to.eq('5', 'Expected halfDays value to be equals to 5');
    expect(await timeSheetUpdatePage.getTotalWorkingHoursInput()).to.eq('5', 'Expected totalWorkingHours value to be equals to 5');

    await timeSheetUpdatePage.save();
    expect(await timeSheetUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await timeSheetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TimeSheet', async () => {
    const nbButtonsBeforeDelete = await timeSheetComponentsPage.countDeleteButtons();
    await timeSheetComponentsPage.clickOnLastDeleteButton();

    timeSheetDeleteDialog = new TimeSheetDeleteDialog();
    expect(await timeSheetDeleteDialog.getDialogTitle()).to.eq('hrApp.timeSheet.delete.question');
    await timeSheetDeleteDialog.clickOnConfirmButton();

    expect(await timeSheetComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
