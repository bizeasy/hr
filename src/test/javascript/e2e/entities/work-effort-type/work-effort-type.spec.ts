import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { WorkEffortTypeComponentsPage, WorkEffortTypeDeleteDialog, WorkEffortTypeUpdatePage } from './work-effort-type.page-object';

const expect = chai.expect;

describe('WorkEffortType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let workEffortTypeComponentsPage: WorkEffortTypeComponentsPage;
  let workEffortTypeUpdatePage: WorkEffortTypeUpdatePage;
  let workEffortTypeDeleteDialog: WorkEffortTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load WorkEffortTypes', async () => {
    await navBarPage.goToEntity('work-effort-type');
    workEffortTypeComponentsPage = new WorkEffortTypeComponentsPage();
    await browser.wait(ec.visibilityOf(workEffortTypeComponentsPage.title), 5000);
    expect(await workEffortTypeComponentsPage.getTitle()).to.eq('hrApp.workEffortType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(workEffortTypeComponentsPage.entities), ec.visibilityOf(workEffortTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create WorkEffortType page', async () => {
    await workEffortTypeComponentsPage.clickOnCreateButton();
    workEffortTypeUpdatePage = new WorkEffortTypeUpdatePage();
    expect(await workEffortTypeUpdatePage.getPageTitle()).to.eq('hrApp.workEffortType.home.createOrEditLabel');
    await workEffortTypeUpdatePage.cancel();
  });

  it('should create and save WorkEffortTypes', async () => {
    const nbButtonsBeforeCreate = await workEffortTypeComponentsPage.countDeleteButtons();

    await workEffortTypeComponentsPage.clickOnCreateButton();

    await promise.all([workEffortTypeUpdatePage.setNameInput('name'), workEffortTypeUpdatePage.setDescriptionInput('description')]);

    expect(await workEffortTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await workEffortTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await workEffortTypeUpdatePage.save();
    expect(await workEffortTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await workEffortTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last WorkEffortType', async () => {
    const nbButtonsBeforeDelete = await workEffortTypeComponentsPage.countDeleteButtons();
    await workEffortTypeComponentsPage.clickOnLastDeleteButton();

    workEffortTypeDeleteDialog = new WorkEffortTypeDeleteDialog();
    expect(await workEffortTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.workEffortType.delete.question');
    await workEffortTypeDeleteDialog.clickOnConfirmButton();

    expect(await workEffortTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
