import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { AttendanceComponentsPage, AttendanceDeleteDialog, AttendanceUpdatePage } from './attendance.page-object';

const expect = chai.expect;

describe('Attendance e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let attendanceComponentsPage: AttendanceComponentsPage;
  let attendanceUpdatePage: AttendanceUpdatePage;
  let attendanceDeleteDialog: AttendanceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Attendances', async () => {
    await navBarPage.goToEntity('attendance');
    attendanceComponentsPage = new AttendanceComponentsPage();
    await browser.wait(ec.visibilityOf(attendanceComponentsPage.title), 5000);
    expect(await attendanceComponentsPage.getTitle()).to.eq('hrApp.attendance.home.title');
    await browser.wait(ec.or(ec.visibilityOf(attendanceComponentsPage.entities), ec.visibilityOf(attendanceComponentsPage.noResult)), 1000);
  });

  it('should load create Attendance page', async () => {
    await attendanceComponentsPage.clickOnCreateButton();
    attendanceUpdatePage = new AttendanceUpdatePage();
    expect(await attendanceUpdatePage.getPageTitle()).to.eq('hrApp.attendance.home.createOrEditLabel');
    await attendanceUpdatePage.cancel();
  });

  it('should create and save Attendances', async () => {
    const nbButtonsBeforeCreate = await attendanceComponentsPage.countDeleteButtons();

    await attendanceComponentsPage.clickOnCreateButton();

    await promise.all([
      attendanceUpdatePage.setPunchInInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      attendanceUpdatePage.setPunchOutInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      attendanceUpdatePage.employeeSelectLastOption(),
    ]);

    expect(await attendanceUpdatePage.getPunchInInput()).to.contain(
      '2001-01-01T02:30',
      'Expected punchIn value to be equals to 2000-12-31'
    );
    expect(await attendanceUpdatePage.getPunchOutInput()).to.contain(
      '2001-01-01T02:30',
      'Expected punchOut value to be equals to 2000-12-31'
    );

    await attendanceUpdatePage.save();
    expect(await attendanceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await attendanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Attendance', async () => {
    const nbButtonsBeforeDelete = await attendanceComponentsPage.countDeleteButtons();
    await attendanceComponentsPage.clickOnLastDeleteButton();

    attendanceDeleteDialog = new AttendanceDeleteDialog();
    expect(await attendanceDeleteDialog.getDialogTitle()).to.eq('hrApp.attendance.delete.question');
    await attendanceDeleteDialog.clickOnConfirmButton();

    expect(await attendanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
