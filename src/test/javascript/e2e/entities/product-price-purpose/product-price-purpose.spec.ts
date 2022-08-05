import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ProductPricePurposeComponentsPage,
  ProductPricePurposeDeleteDialog,
  ProductPricePurposeUpdatePage,
} from './product-price-purpose.page-object';

const expect = chai.expect;

describe('ProductPricePurpose e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productPricePurposeComponentsPage: ProductPricePurposeComponentsPage;
  let productPricePurposeUpdatePage: ProductPricePurposeUpdatePage;
  let productPricePurposeDeleteDialog: ProductPricePurposeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductPricePurposes', async () => {
    await navBarPage.goToEntity('product-price-purpose');
    productPricePurposeComponentsPage = new ProductPricePurposeComponentsPage();
    await browser.wait(ec.visibilityOf(productPricePurposeComponentsPage.title), 5000);
    expect(await productPricePurposeComponentsPage.getTitle()).to.eq('hrApp.productPricePurpose.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(productPricePurposeComponentsPage.entities), ec.visibilityOf(productPricePurposeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ProductPricePurpose page', async () => {
    await productPricePurposeComponentsPage.clickOnCreateButton();
    productPricePurposeUpdatePage = new ProductPricePurposeUpdatePage();
    expect(await productPricePurposeUpdatePage.getPageTitle()).to.eq('hrApp.productPricePurpose.home.createOrEditLabel');
    await productPricePurposeUpdatePage.cancel();
  });

  it('should create and save ProductPricePurposes', async () => {
    const nbButtonsBeforeCreate = await productPricePurposeComponentsPage.countDeleteButtons();

    await productPricePurposeComponentsPage.clickOnCreateButton();

    await promise.all([
      productPricePurposeUpdatePage.setNameInput('name'),
      productPricePurposeUpdatePage.setDescriptionInput('description'),
    ]);

    expect(await productPricePurposeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await productPricePurposeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await productPricePurposeUpdatePage.save();
    expect(await productPricePurposeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productPricePurposeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProductPricePurpose', async () => {
    const nbButtonsBeforeDelete = await productPricePurposeComponentsPage.countDeleteButtons();
    await productPricePurposeComponentsPage.clickOnLastDeleteButton();

    productPricePurposeDeleteDialog = new ProductPricePurposeDeleteDialog();
    expect(await productPricePurposeDeleteDialog.getDialogTitle()).to.eq('hrApp.productPricePurpose.delete.question');
    await productPricePurposeDeleteDialog.clickOnConfirmButton();

    expect(await productPricePurposeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
