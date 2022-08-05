import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TaxSlabComponentsPage, TaxSlabDeleteDialog, TaxSlabUpdatePage } from './tax-slab.page-object';

const expect = chai.expect;

describe('TaxSlab e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let taxSlabComponentsPage: TaxSlabComponentsPage;
  let taxSlabUpdatePage: TaxSlabUpdatePage;
  let taxSlabDeleteDialog: TaxSlabDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TaxSlabs', async () => {
    await navBarPage.goToEntity('tax-slab');
    taxSlabComponentsPage = new TaxSlabComponentsPage();
    await browser.wait(ec.visibilityOf(taxSlabComponentsPage.title), 5000);
    expect(await taxSlabComponentsPage.getTitle()).to.eq('hrApp.taxSlab.home.title');
    await browser.wait(ec.or(ec.visibilityOf(taxSlabComponentsPage.entities), ec.visibilityOf(taxSlabComponentsPage.noResult)), 1000);
  });

  it('should load create TaxSlab page', async () => {
    await taxSlabComponentsPage.clickOnCreateButton();
    taxSlabUpdatePage = new TaxSlabUpdatePage();
    expect(await taxSlabUpdatePage.getPageTitle()).to.eq('hrApp.taxSlab.home.createOrEditLabel');
    await taxSlabUpdatePage.cancel();
  });

  it('should create and save TaxSlabs', async () => {
    const nbButtonsBeforeCreate = await taxSlabComponentsPage.countDeleteButtons();

    await taxSlabComponentsPage.clickOnCreateButton();

    await promise.all([taxSlabUpdatePage.setNameInput('name'), taxSlabUpdatePage.setRateInput('5')]);

    expect(await taxSlabUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await taxSlabUpdatePage.getRateInput()).to.eq('5', 'Expected rate value to be equals to 5');

    await taxSlabUpdatePage.save();
    expect(await taxSlabUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await taxSlabComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TaxSlab', async () => {
    const nbButtonsBeforeDelete = await taxSlabComponentsPage.countDeleteButtons();
    await taxSlabComponentsPage.clickOnLastDeleteButton();

    taxSlabDeleteDialog = new TaxSlabDeleteDialog();
    expect(await taxSlabDeleteDialog.getDialogTitle()).to.eq('hrApp.taxSlab.delete.question');
    await taxSlabDeleteDialog.clickOnConfirmButton();

    expect(await taxSlabComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
