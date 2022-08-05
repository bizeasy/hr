import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmplLeaveComponentsPage, EmplLeaveDeleteDialog, EmplLeaveUpdatePage } from './empl-leave.page-object';

const expect = chai.expect;

describe('EmplLeave e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let emplLeaveComponentsPage: EmplLeaveComponentsPage;
  let emplLeaveUpdatePage: EmplLeaveUpdatePage;
  let emplLeaveDeleteDialog: EmplLeaveDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmplLeaves', async () => {
    await navBarPage.goToEntity('empl-leave');
    emplLeaveComponentsPage = new EmplLeaveComponentsPage();
    await browser.wait(ec.visibilityOf(emplLeaveComponentsPage.title), 5000);
    expect(await emplLeaveComponentsPage.getTitle()).to.eq('hrApp.emplLeave.home.title');
    await browser.wait(ec.or(ec.visibilityOf(emplLeaveComponentsPage.entities), ec.visibilityOf(emplLeaveComponentsPage.noResult)), 1000);
  });

  it('should load create EmplLeave page', async () => {
    await emplLeaveComponentsPage.clickOnCreateButton();
    emplLeaveUpdatePage = new EmplLeaveUpdatePage();
    expect(await emplLeaveUpdatePage.getPageTitle()).to.eq('hrApp.emplLeave.home.createOrEditLabel');
    await emplLeaveUpdatePage.cancel();
  });

  it('should create and save EmplLeaves', async () => {
    const nbButtonsBeforeCreate = await emplLeaveComponentsPage.countDeleteButtons();

    await emplLeaveComponentsPage.clickOnCreateButton();

    await promise.all([
      emplLeaveUpdatePage.setDescriptionInput('description'),
      emplLeaveUpdatePage.setFromDateInput('2000-12-31'),
      emplLeaveUpdatePage.setThruDateInput('2000-12-31'),
      emplLeaveUpdatePage.employeeSelectLastOption(),
      emplLeaveUpdatePage.approverSelectLastOption(),
      emplLeaveUpdatePage.leaveTypeSelectLastOption(),
      emplLeaveUpdatePage.reasonSelectLastOption(),
      emplLeaveUpdatePage.statusSelectLastOption(),
    ]);

    expect(await emplLeaveUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await emplLeaveUpdatePage.getFromDateInput()).to.eq('2000-12-31', 'Expected fromDate value to be equals to 2000-12-31');
    expect(await emplLeaveUpdatePage.getThruDateInput()).to.eq('2000-12-31', 'Expected thruDate value to be equals to 2000-12-31');

    await emplLeaveUpdatePage.save();
    expect(await emplLeaveUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await emplLeaveComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last EmplLeave', async () => {
    const nbButtonsBeforeDelete = await emplLeaveComponentsPage.countDeleteButtons();
    await emplLeaveComponentsPage.clickOnLastDeleteButton();

    emplLeaveDeleteDialog = new EmplLeaveDeleteDialog();
    expect(await emplLeaveDeleteDialog.getDialogTitle()).to.eq('hrApp.emplLeave.delete.question');
    await emplLeaveDeleteDialog.clickOnConfirmButton();

    expect(await emplLeaveComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
