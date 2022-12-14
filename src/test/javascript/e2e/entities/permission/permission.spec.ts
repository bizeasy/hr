import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PermissionComponentsPage, PermissionDeleteDialog, PermissionUpdatePage } from './permission.page-object';

const expect = chai.expect;

describe('Permission e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let permissionComponentsPage: PermissionComponentsPage;
  let permissionUpdatePage: PermissionUpdatePage;
  let permissionDeleteDialog: PermissionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Permissions', async () => {
    await navBarPage.goToEntity('permission');
    permissionComponentsPage = new PermissionComponentsPage();
    await browser.wait(ec.visibilityOf(permissionComponentsPage.title), 5000);
    expect(await permissionComponentsPage.getTitle()).to.eq('hrApp.permission.home.title');
    await browser.wait(ec.or(ec.visibilityOf(permissionComponentsPage.entities), ec.visibilityOf(permissionComponentsPage.noResult)), 1000);
  });

  it('should load create Permission page', async () => {
    await permissionComponentsPage.clickOnCreateButton();
    permissionUpdatePage = new PermissionUpdatePage();
    expect(await permissionUpdatePage.getPageTitle()).to.eq('hrApp.permission.home.createOrEditLabel');
    await permissionUpdatePage.cancel();
  });

  it('should create and save Permissions', async () => {
    const nbButtonsBeforeCreate = await permissionComponentsPage.countDeleteButtons();

    await permissionComponentsPage.clickOnCreateButton();

    await promise.all([permissionUpdatePage.setNameInput('name'), permissionUpdatePage.setDescriptionInput('description')]);

    expect(await permissionUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await permissionUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

    await permissionUpdatePage.save();
    expect(await permissionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await permissionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Permission', async () => {
    const nbButtonsBeforeDelete = await permissionComponentsPage.countDeleteButtons();
    await permissionComponentsPage.clickOnLastDeleteButton();

    permissionDeleteDialog = new PermissionDeleteDialog();
    expect(await permissionDeleteDialog.getDialogTitle()).to.eq('hrApp.permission.delete.question');
    await permissionDeleteDialog.clickOnConfirmButton();

    expect(await permissionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
