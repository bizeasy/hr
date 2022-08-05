import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { WorkEffortStatusComponentsPage, WorkEffortStatusDeleteDialog, WorkEffortStatusUpdatePage } from './work-effort-status.page-object';

const expect = chai.expect;

describe('WorkEffortStatus e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let workEffortStatusComponentsPage: WorkEffortStatusComponentsPage;
  let workEffortStatusUpdatePage: WorkEffortStatusUpdatePage;
  let workEffortStatusDeleteDialog: WorkEffortStatusDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load WorkEffortStatuses', async () => {
    await navBarPage.goToEntity('work-effort-status');
    workEffortStatusComponentsPage = new WorkEffortStatusComponentsPage();
    await browser.wait(ec.visibilityOf(workEffortStatusComponentsPage.title), 5000);
    expect(await workEffortStatusComponentsPage.getTitle()).to.eq('hrApp.workEffortStatus.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(workEffortStatusComponentsPage.entities), ec.visibilityOf(workEffortStatusComponentsPage.noResult)),
      1000
    );
  });

  it('should load create WorkEffortStatus page', async () => {
    await workEffortStatusComponentsPage.clickOnCreateButton();
    workEffortStatusUpdatePage = new WorkEffortStatusUpdatePage();
    expect(await workEffortStatusUpdatePage.getPageTitle()).to.eq('hrApp.workEffortStatus.home.createOrEditLabel');
    await workEffortStatusUpdatePage.cancel();
  });

  it('should create and save WorkEffortStatuses', async () => {
    const nbButtonsBeforeCreate = await workEffortStatusComponentsPage.countDeleteButtons();

    await workEffortStatusComponentsPage.clickOnCreateButton();

    await promise.all([
      workEffortStatusUpdatePage.setReasonInput('reason'),
      workEffortStatusUpdatePage.workEffortSelectLastOption(),
      workEffortStatusUpdatePage.statusSelectLastOption(),
    ]);

    expect(await workEffortStatusUpdatePage.getReasonInput()).to.eq('reason', 'Expected Reason value to be equals to reason');

    await workEffortStatusUpdatePage.save();
    expect(await workEffortStatusUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await workEffortStatusComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last WorkEffortStatus', async () => {
    const nbButtonsBeforeDelete = await workEffortStatusComponentsPage.countDeleteButtons();
    await workEffortStatusComponentsPage.clickOnLastDeleteButton();

    workEffortStatusDeleteDialog = new WorkEffortStatusDeleteDialog();
    expect(await workEffortStatusDeleteDialog.getDialogTitle()).to.eq('hrApp.workEffortStatus.delete.question');
    await workEffortStatusDeleteDialog.clickOnConfirmButton();

    expect(await workEffortStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
