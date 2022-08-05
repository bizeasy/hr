import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { WorkEffortAssocComponentsPage, WorkEffortAssocDeleteDialog, WorkEffortAssocUpdatePage } from './work-effort-assoc.page-object';

const expect = chai.expect;

describe('WorkEffortAssoc e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let workEffortAssocComponentsPage: WorkEffortAssocComponentsPage;
  let workEffortAssocUpdatePage: WorkEffortAssocUpdatePage;
  let workEffortAssocDeleteDialog: WorkEffortAssocDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load WorkEffortAssocs', async () => {
    await navBarPage.goToEntity('work-effort-assoc');
    workEffortAssocComponentsPage = new WorkEffortAssocComponentsPage();
    await browser.wait(ec.visibilityOf(workEffortAssocComponentsPage.title), 5000);
    expect(await workEffortAssocComponentsPage.getTitle()).to.eq('hrApp.workEffortAssoc.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(workEffortAssocComponentsPage.entities), ec.visibilityOf(workEffortAssocComponentsPage.noResult)),
      1000
    );
  });

  it('should load create WorkEffortAssoc page', async () => {
    await workEffortAssocComponentsPage.clickOnCreateButton();
    workEffortAssocUpdatePage = new WorkEffortAssocUpdatePage();
    expect(await workEffortAssocUpdatePage.getPageTitle()).to.eq('hrApp.workEffortAssoc.home.createOrEditLabel');
    await workEffortAssocUpdatePage.cancel();
  });

  it('should create and save WorkEffortAssocs', async () => {
    const nbButtonsBeforeCreate = await workEffortAssocComponentsPage.countDeleteButtons();

    await workEffortAssocComponentsPage.clickOnCreateButton();

    await promise.all([
      workEffortAssocUpdatePage.setSequenceNoInput('5'),
      workEffortAssocUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      workEffortAssocUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      workEffortAssocUpdatePage.typeSelectLastOption(),
      workEffortAssocUpdatePage.weIdFromSelectLastOption(),
      workEffortAssocUpdatePage.weIdToSelectLastOption(),
    ]);

    expect(await workEffortAssocUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');
    expect(await workEffortAssocUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await workEffortAssocUpdatePage.getThruDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected thruDate value to be equals to 2000-12-31'
    );

    await workEffortAssocUpdatePage.save();
    expect(await workEffortAssocUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await workEffortAssocComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last WorkEffortAssoc', async () => {
    const nbButtonsBeforeDelete = await workEffortAssocComponentsPage.countDeleteButtons();
    await workEffortAssocComponentsPage.clickOnLastDeleteButton();

    workEffortAssocDeleteDialog = new WorkEffortAssocDeleteDialog();
    expect(await workEffortAssocDeleteDialog.getDialogTitle()).to.eq('hrApp.workEffortAssoc.delete.question');
    await workEffortAssocDeleteDialog.clickOnConfirmButton();

    expect(await workEffortAssocComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
