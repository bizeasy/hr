import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  WorkEffortAssocTypeComponentsPage,
  WorkEffortAssocTypeDeleteDialog,
  WorkEffortAssocTypeUpdatePage,
} from './work-effort-assoc-type.page-object';

const expect = chai.expect;

describe('WorkEffortAssocType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let workEffortAssocTypeComponentsPage: WorkEffortAssocTypeComponentsPage;
  let workEffortAssocTypeUpdatePage: WorkEffortAssocTypeUpdatePage;
  let workEffortAssocTypeDeleteDialog: WorkEffortAssocTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load WorkEffortAssocTypes', async () => {
    await navBarPage.goToEntity('work-effort-assoc-type');
    workEffortAssocTypeComponentsPage = new WorkEffortAssocTypeComponentsPage();
    await browser.wait(ec.visibilityOf(workEffortAssocTypeComponentsPage.title), 5000);
    expect(await workEffortAssocTypeComponentsPage.getTitle()).to.eq('hrApp.workEffortAssocType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(workEffortAssocTypeComponentsPage.entities), ec.visibilityOf(workEffortAssocTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create WorkEffortAssocType page', async () => {
    await workEffortAssocTypeComponentsPage.clickOnCreateButton();
    workEffortAssocTypeUpdatePage = new WorkEffortAssocTypeUpdatePage();
    expect(await workEffortAssocTypeUpdatePage.getPageTitle()).to.eq('hrApp.workEffortAssocType.home.createOrEditLabel');
    await workEffortAssocTypeUpdatePage.cancel();
  });

  it('should create and save WorkEffortAssocTypes', async () => {
    const nbButtonsBeforeCreate = await workEffortAssocTypeComponentsPage.countDeleteButtons();

    await workEffortAssocTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      workEffortAssocTypeUpdatePage.setNameInput('name'),
      workEffortAssocTypeUpdatePage.setDescriptionInput('description'),
    ]);

    expect(await workEffortAssocTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await workEffortAssocTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await workEffortAssocTypeUpdatePage.save();
    expect(await workEffortAssocTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await workEffortAssocTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last WorkEffortAssocType', async () => {
    const nbButtonsBeforeDelete = await workEffortAssocTypeComponentsPage.countDeleteButtons();
    await workEffortAssocTypeComponentsPage.clickOnLastDeleteButton();

    workEffortAssocTypeDeleteDialog = new WorkEffortAssocTypeDeleteDialog();
    expect(await workEffortAssocTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.workEffortAssocType.delete.question');
    await workEffortAssocTypeDeleteDialog.clickOnConfirmButton();

    expect(await workEffortAssocTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
