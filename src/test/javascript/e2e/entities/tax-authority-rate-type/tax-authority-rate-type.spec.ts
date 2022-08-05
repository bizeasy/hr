import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  TaxAuthorityRateTypeComponentsPage,
  TaxAuthorityRateTypeDeleteDialog,
  TaxAuthorityRateTypeUpdatePage,
} from './tax-authority-rate-type.page-object';

const expect = chai.expect;

describe('TaxAuthorityRateType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let taxAuthorityRateTypeComponentsPage: TaxAuthorityRateTypeComponentsPage;
  let taxAuthorityRateTypeUpdatePage: TaxAuthorityRateTypeUpdatePage;
  let taxAuthorityRateTypeDeleteDialog: TaxAuthorityRateTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TaxAuthorityRateTypes', async () => {
    await navBarPage.goToEntity('tax-authority-rate-type');
    taxAuthorityRateTypeComponentsPage = new TaxAuthorityRateTypeComponentsPage();
    await browser.wait(ec.visibilityOf(taxAuthorityRateTypeComponentsPage.title), 5000);
    expect(await taxAuthorityRateTypeComponentsPage.getTitle()).to.eq('hrApp.taxAuthorityRateType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(taxAuthorityRateTypeComponentsPage.entities), ec.visibilityOf(taxAuthorityRateTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create TaxAuthorityRateType page', async () => {
    await taxAuthorityRateTypeComponentsPage.clickOnCreateButton();
    taxAuthorityRateTypeUpdatePage = new TaxAuthorityRateTypeUpdatePage();
    expect(await taxAuthorityRateTypeUpdatePage.getPageTitle()).to.eq('hrApp.taxAuthorityRateType.home.createOrEditLabel');
    await taxAuthorityRateTypeUpdatePage.cancel();
  });

  it('should create and save TaxAuthorityRateTypes', async () => {
    const nbButtonsBeforeCreate = await taxAuthorityRateTypeComponentsPage.countDeleteButtons();

    await taxAuthorityRateTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      taxAuthorityRateTypeUpdatePage.setNameInput('name'),
      taxAuthorityRateTypeUpdatePage.setDescriptionInput('description'),
      taxAuthorityRateTypeUpdatePage.taxAuthoritySelectLastOption(),
      taxAuthorityRateTypeUpdatePage.taxSlabSelectLastOption(),
    ]);

    expect(await taxAuthorityRateTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await taxAuthorityRateTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await taxAuthorityRateTypeUpdatePage.save();
    expect(await taxAuthorityRateTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await taxAuthorityRateTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TaxAuthorityRateType', async () => {
    const nbButtonsBeforeDelete = await taxAuthorityRateTypeComponentsPage.countDeleteButtons();
    await taxAuthorityRateTypeComponentsPage.clickOnLastDeleteButton();

    taxAuthorityRateTypeDeleteDialog = new TaxAuthorityRateTypeDeleteDialog();
    expect(await taxAuthorityRateTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.taxAuthorityRateType.delete.question');
    await taxAuthorityRateTypeDeleteDialog.clickOnConfirmButton();

    expect(await taxAuthorityRateTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
