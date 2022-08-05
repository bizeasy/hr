import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RoleTypeComponentsPage, RoleTypeDeleteDialog, RoleTypeUpdatePage } from './role-type.page-object';

const expect = chai.expect;

describe('RoleType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let roleTypeComponentsPage: RoleTypeComponentsPage;
  let roleTypeUpdatePage: RoleTypeUpdatePage;
  let roleTypeDeleteDialog: RoleTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RoleTypes', async () => {
    await navBarPage.goToEntity('role-type');
    roleTypeComponentsPage = new RoleTypeComponentsPage();
    await browser.wait(ec.visibilityOf(roleTypeComponentsPage.title), 5000);
    expect(await roleTypeComponentsPage.getTitle()).to.eq('hrApp.roleType.home.title');
    await browser.wait(ec.or(ec.visibilityOf(roleTypeComponentsPage.entities), ec.visibilityOf(roleTypeComponentsPage.noResult)), 1000);
  });

  it('should load create RoleType page', async () => {
    await roleTypeComponentsPage.clickOnCreateButton();
    roleTypeUpdatePage = new RoleTypeUpdatePage();
    expect(await roleTypeUpdatePage.getPageTitle()).to.eq('hrApp.roleType.home.createOrEditLabel');
    await roleTypeUpdatePage.cancel();
  });

  it('should create and save RoleTypes', async () => {
    const nbButtonsBeforeCreate = await roleTypeComponentsPage.countDeleteButtons();

    await roleTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      roleTypeUpdatePage.setNameInput('name'),
      roleTypeUpdatePage.setDescriptionInput('description'),
      roleTypeUpdatePage.parentSelectLastOption(),
    ]);

    expect(await roleTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await roleTypeUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

    await roleTypeUpdatePage.save();
    expect(await roleTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await roleTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last RoleType', async () => {
    const nbButtonsBeforeDelete = await roleTypeComponentsPage.countDeleteButtons();
    await roleTypeComponentsPage.clickOnLastDeleteButton();

    roleTypeDeleteDialog = new RoleTypeDeleteDialog();
    expect(await roleTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.roleType.delete.question');
    await roleTypeDeleteDialog.clickOnConfirmButton();

    expect(await roleTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
