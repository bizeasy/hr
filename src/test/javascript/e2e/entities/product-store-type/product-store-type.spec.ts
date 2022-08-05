import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductStoreTypeComponentsPage, ProductStoreTypeDeleteDialog, ProductStoreTypeUpdatePage } from './product-store-type.page-object';

const expect = chai.expect;

describe('ProductStoreType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productStoreTypeComponentsPage: ProductStoreTypeComponentsPage;
  let productStoreTypeUpdatePage: ProductStoreTypeUpdatePage;
  let productStoreTypeDeleteDialog: ProductStoreTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductStoreTypes', async () => {
    await navBarPage.goToEntity('product-store-type');
    productStoreTypeComponentsPage = new ProductStoreTypeComponentsPage();
    await browser.wait(ec.visibilityOf(productStoreTypeComponentsPage.title), 5000);
    expect(await productStoreTypeComponentsPage.getTitle()).to.eq('hrApp.productStoreType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(productStoreTypeComponentsPage.entities), ec.visibilityOf(productStoreTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ProductStoreType page', async () => {
    await productStoreTypeComponentsPage.clickOnCreateButton();
    productStoreTypeUpdatePage = new ProductStoreTypeUpdatePage();
    expect(await productStoreTypeUpdatePage.getPageTitle()).to.eq('hrApp.productStoreType.home.createOrEditLabel');
    await productStoreTypeUpdatePage.cancel();
  });

  it('should create and save ProductStoreTypes', async () => {
    const nbButtonsBeforeCreate = await productStoreTypeComponentsPage.countDeleteButtons();

    await productStoreTypeComponentsPage.clickOnCreateButton();

    await promise.all([productStoreTypeUpdatePage.setNameInput('name'), productStoreTypeUpdatePage.setDescriptionInput('description')]);

    expect(await productStoreTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await productStoreTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await productStoreTypeUpdatePage.save();
    expect(await productStoreTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productStoreTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProductStoreType', async () => {
    const nbButtonsBeforeDelete = await productStoreTypeComponentsPage.countDeleteButtons();
    await productStoreTypeComponentsPage.clickOnLastDeleteButton();

    productStoreTypeDeleteDialog = new ProductStoreTypeDeleteDialog();
    expect(await productStoreTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.productStoreType.delete.question');
    await productStoreTypeDeleteDialog.clickOnConfirmButton();

    expect(await productStoreTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
