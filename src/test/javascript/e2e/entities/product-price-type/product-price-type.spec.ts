import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductPriceTypeComponentsPage, ProductPriceTypeDeleteDialog, ProductPriceTypeUpdatePage } from './product-price-type.page-object';

const expect = chai.expect;

describe('ProductPriceType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productPriceTypeComponentsPage: ProductPriceTypeComponentsPage;
  let productPriceTypeUpdatePage: ProductPriceTypeUpdatePage;
  let productPriceTypeDeleteDialog: ProductPriceTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductPriceTypes', async () => {
    await navBarPage.goToEntity('product-price-type');
    productPriceTypeComponentsPage = new ProductPriceTypeComponentsPage();
    await browser.wait(ec.visibilityOf(productPriceTypeComponentsPage.title), 5000);
    expect(await productPriceTypeComponentsPage.getTitle()).to.eq('hrApp.productPriceType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(productPriceTypeComponentsPage.entities), ec.visibilityOf(productPriceTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ProductPriceType page', async () => {
    await productPriceTypeComponentsPage.clickOnCreateButton();
    productPriceTypeUpdatePage = new ProductPriceTypeUpdatePage();
    expect(await productPriceTypeUpdatePage.getPageTitle()).to.eq('hrApp.productPriceType.home.createOrEditLabel');
    await productPriceTypeUpdatePage.cancel();
  });

  it('should create and save ProductPriceTypes', async () => {
    const nbButtonsBeforeCreate = await productPriceTypeComponentsPage.countDeleteButtons();

    await productPriceTypeComponentsPage.clickOnCreateButton();

    await promise.all([productPriceTypeUpdatePage.setNameInput('name'), productPriceTypeUpdatePage.setDescriptionInput('description')]);

    expect(await productPriceTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await productPriceTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await productPriceTypeUpdatePage.save();
    expect(await productPriceTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productPriceTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProductPriceType', async () => {
    const nbButtonsBeforeDelete = await productPriceTypeComponentsPage.countDeleteButtons();
    await productPriceTypeComponentsPage.clickOnLastDeleteButton();

    productPriceTypeDeleteDialog = new ProductPriceTypeDeleteDialog();
    expect(await productPriceTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.productPriceType.delete.question');
    await productPriceTypeDeleteDialog.clickOnConfirmButton();

    expect(await productPriceTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
