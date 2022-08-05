import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TaxAuthorityComponentsPage, TaxAuthorityDeleteDialog, TaxAuthorityUpdatePage } from './tax-authority.page-object';

const expect = chai.expect;

describe('TaxAuthority e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let taxAuthorityComponentsPage: TaxAuthorityComponentsPage;
  let taxAuthorityUpdatePage: TaxAuthorityUpdatePage;
  let taxAuthorityDeleteDialog: TaxAuthorityDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TaxAuthorities', async () => {
    await navBarPage.goToEntity('tax-authority');
    taxAuthorityComponentsPage = new TaxAuthorityComponentsPage();
    await browser.wait(ec.visibilityOf(taxAuthorityComponentsPage.title), 5000);
    expect(await taxAuthorityComponentsPage.getTitle()).to.eq('hrApp.taxAuthority.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(taxAuthorityComponentsPage.entities), ec.visibilityOf(taxAuthorityComponentsPage.noResult)),
      1000
    );
  });

  it('should load create TaxAuthority page', async () => {
    await taxAuthorityComponentsPage.clickOnCreateButton();
    taxAuthorityUpdatePage = new TaxAuthorityUpdatePage();
    expect(await taxAuthorityUpdatePage.getPageTitle()).to.eq('hrApp.taxAuthority.home.createOrEditLabel');
    await taxAuthorityUpdatePage.cancel();
  });

  it('should create and save TaxAuthorities', async () => {
    const nbButtonsBeforeCreate = await taxAuthorityComponentsPage.countDeleteButtons();

    await taxAuthorityComponentsPage.clickOnCreateButton();

    await promise.all([taxAuthorityUpdatePage.setNameInput('name')]);

    expect(await taxAuthorityUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');

    await taxAuthorityUpdatePage.save();
    expect(await taxAuthorityUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await taxAuthorityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TaxAuthority', async () => {
    const nbButtonsBeforeDelete = await taxAuthorityComponentsPage.countDeleteButtons();
    await taxAuthorityComponentsPage.clickOnLastDeleteButton();

    taxAuthorityDeleteDialog = new TaxAuthorityDeleteDialog();
    expect(await taxAuthorityDeleteDialog.getDialogTitle()).to.eq('hrApp.taxAuthority.delete.question');
    await taxAuthorityDeleteDialog.clickOnConfirmButton();

    expect(await taxAuthorityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
