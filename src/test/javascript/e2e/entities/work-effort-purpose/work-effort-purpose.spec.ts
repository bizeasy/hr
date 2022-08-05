import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  WorkEffortPurposeComponentsPage,
  WorkEffortPurposeDeleteDialog,
  WorkEffortPurposeUpdatePage,
} from './work-effort-purpose.page-object';

const expect = chai.expect;

describe('WorkEffortPurpose e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let workEffortPurposeComponentsPage: WorkEffortPurposeComponentsPage;
  let workEffortPurposeUpdatePage: WorkEffortPurposeUpdatePage;
  let workEffortPurposeDeleteDialog: WorkEffortPurposeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load WorkEffortPurposes', async () => {
    await navBarPage.goToEntity('work-effort-purpose');
    workEffortPurposeComponentsPage = new WorkEffortPurposeComponentsPage();
    await browser.wait(ec.visibilityOf(workEffortPurposeComponentsPage.title), 5000);
    expect(await workEffortPurposeComponentsPage.getTitle()).to.eq('hrApp.workEffortPurpose.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(workEffortPurposeComponentsPage.entities), ec.visibilityOf(workEffortPurposeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create WorkEffortPurpose page', async () => {
    await workEffortPurposeComponentsPage.clickOnCreateButton();
    workEffortPurposeUpdatePage = new WorkEffortPurposeUpdatePage();
    expect(await workEffortPurposeUpdatePage.getPageTitle()).to.eq('hrApp.workEffortPurpose.home.createOrEditLabel');
    await workEffortPurposeUpdatePage.cancel();
  });

  it('should create and save WorkEffortPurposes', async () => {
    const nbButtonsBeforeCreate = await workEffortPurposeComponentsPage.countDeleteButtons();

    await workEffortPurposeComponentsPage.clickOnCreateButton();

    await promise.all([workEffortPurposeUpdatePage.setNameInput('name'), workEffortPurposeUpdatePage.setDescriptionInput('description')]);

    expect(await workEffortPurposeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await workEffortPurposeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await workEffortPurposeUpdatePage.save();
    expect(await workEffortPurposeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await workEffortPurposeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last WorkEffortPurpose', async () => {
    const nbButtonsBeforeDelete = await workEffortPurposeComponentsPage.countDeleteButtons();
    await workEffortPurposeComponentsPage.clickOnLastDeleteButton();

    workEffortPurposeDeleteDialog = new WorkEffortPurposeDeleteDialog();
    expect(await workEffortPurposeDeleteDialog.getDialogTitle()).to.eq('hrApp.workEffortPurpose.delete.question');
    await workEffortPurposeDeleteDialog.clickOnConfirmButton();

    expect(await workEffortPurposeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
