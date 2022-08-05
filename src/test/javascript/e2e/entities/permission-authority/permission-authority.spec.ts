import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PermissionAuthorityComponentsPage,
  PermissionAuthorityDeleteDialog,
  PermissionAuthorityUpdatePage,
} from './permission-authority.page-object';

const expect = chai.expect;

describe('PermissionAuthority e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let permissionAuthorityComponentsPage: PermissionAuthorityComponentsPage;
  let permissionAuthorityUpdatePage: PermissionAuthorityUpdatePage;
  let permissionAuthorityDeleteDialog: PermissionAuthorityDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PermissionAuthorities', async () => {
    await navBarPage.goToEntity('permission-authority');
    permissionAuthorityComponentsPage = new PermissionAuthorityComponentsPage();
    await browser.wait(ec.visibilityOf(permissionAuthorityComponentsPage.title), 5000);
    expect(await permissionAuthorityComponentsPage.getTitle()).to.eq('hrApp.permissionAuthority.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(permissionAuthorityComponentsPage.entities), ec.visibilityOf(permissionAuthorityComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PermissionAuthority page', async () => {
    await permissionAuthorityComponentsPage.clickOnCreateButton();
    permissionAuthorityUpdatePage = new PermissionAuthorityUpdatePage();
    expect(await permissionAuthorityUpdatePage.getPageTitle()).to.eq('hrApp.permissionAuthority.home.createOrEditLabel');
    await permissionAuthorityUpdatePage.cancel();
  });

  it('should create and save PermissionAuthorities', async () => {
    const nbButtonsBeforeCreate = await permissionAuthorityComponentsPage.countDeleteButtons();

    await permissionAuthorityComponentsPage.clickOnCreateButton();

    await promise.all([permissionAuthorityUpdatePage.setAuthorityInput('authority')]);

    expect(await permissionAuthorityUpdatePage.getAuthorityInput()).to.eq(
      'authority',
      'Expected Authority value to be equals to authority'
    );

    await permissionAuthorityUpdatePage.save();
    expect(await permissionAuthorityUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await permissionAuthorityComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PermissionAuthority', async () => {
    const nbButtonsBeforeDelete = await permissionAuthorityComponentsPage.countDeleteButtons();
    await permissionAuthorityComponentsPage.clickOnLastDeleteButton();

    permissionAuthorityDeleteDialog = new PermissionAuthorityDeleteDialog();
    expect(await permissionAuthorityDeleteDialog.getDialogTitle()).to.eq('hrApp.permissionAuthority.delete.question');
    await permissionAuthorityDeleteDialog.clickOnConfirmButton();

    expect(await permissionAuthorityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
