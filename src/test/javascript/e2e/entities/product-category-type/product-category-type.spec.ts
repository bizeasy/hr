import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ProductCategoryTypeComponentsPage,
  ProductCategoryTypeDeleteDialog,
  ProductCategoryTypeUpdatePage,
} from './product-category-type.page-object';

const expect = chai.expect;

describe('ProductCategoryType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productCategoryTypeComponentsPage: ProductCategoryTypeComponentsPage;
  let productCategoryTypeUpdatePage: ProductCategoryTypeUpdatePage;
  let productCategoryTypeDeleteDialog: ProductCategoryTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductCategoryTypes', async () => {
    await navBarPage.goToEntity('product-category-type');
    productCategoryTypeComponentsPage = new ProductCategoryTypeComponentsPage();
    await browser.wait(ec.visibilityOf(productCategoryTypeComponentsPage.title), 5000);
    expect(await productCategoryTypeComponentsPage.getTitle()).to.eq('hrApp.productCategoryType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(productCategoryTypeComponentsPage.entities), ec.visibilityOf(productCategoryTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ProductCategoryType page', async () => {
    await productCategoryTypeComponentsPage.clickOnCreateButton();
    productCategoryTypeUpdatePage = new ProductCategoryTypeUpdatePage();
    expect(await productCategoryTypeUpdatePage.getPageTitle()).to.eq('hrApp.productCategoryType.home.createOrEditLabel');
    await productCategoryTypeUpdatePage.cancel();
  });

  it('should create and save ProductCategoryTypes', async () => {
    const nbButtonsBeforeCreate = await productCategoryTypeComponentsPage.countDeleteButtons();

    await productCategoryTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      productCategoryTypeUpdatePage.setNameInput('name'),
      productCategoryTypeUpdatePage.setDescriptionInput('description'),
    ]);

    expect(await productCategoryTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await productCategoryTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await productCategoryTypeUpdatePage.save();
    expect(await productCategoryTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productCategoryTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProductCategoryType', async () => {
    const nbButtonsBeforeDelete = await productCategoryTypeComponentsPage.countDeleteButtons();
    await productCategoryTypeComponentsPage.clickOnLastDeleteButton();

    productCategoryTypeDeleteDialog = new ProductCategoryTypeDeleteDialog();
    expect(await productCategoryTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.productCategoryType.delete.question');
    await productCategoryTypeDeleteDialog.clickOnConfirmButton();

    expect(await productCategoryTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
