import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductCategoryComponentsPage, ProductCategoryDeleteDialog, ProductCategoryUpdatePage } from './product-category.page-object';

const expect = chai.expect;

describe('ProductCategory e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productCategoryComponentsPage: ProductCategoryComponentsPage;
  let productCategoryUpdatePage: ProductCategoryUpdatePage;
  let productCategoryDeleteDialog: ProductCategoryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductCategories', async () => {
    await navBarPage.goToEntity('product-category');
    productCategoryComponentsPage = new ProductCategoryComponentsPage();
    await browser.wait(ec.visibilityOf(productCategoryComponentsPage.title), 5000);
    expect(await productCategoryComponentsPage.getTitle()).to.eq('hrApp.productCategory.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(productCategoryComponentsPage.entities), ec.visibilityOf(productCategoryComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ProductCategory page', async () => {
    await productCategoryComponentsPage.clickOnCreateButton();
    productCategoryUpdatePage = new ProductCategoryUpdatePage();
    expect(await productCategoryUpdatePage.getPageTitle()).to.eq('hrApp.productCategory.home.createOrEditLabel');
    await productCategoryUpdatePage.cancel();
  });

  it('should create and save ProductCategories', async () => {
    const nbButtonsBeforeCreate = await productCategoryComponentsPage.countDeleteButtons();

    await productCategoryComponentsPage.clickOnCreateButton();

    await promise.all([
      productCategoryUpdatePage.setNameInput('name'),
      productCategoryUpdatePage.setDescriptionInput('description'),
      productCategoryUpdatePage.setLongDescriptionInput('longDescription'),
      productCategoryUpdatePage.setAttributeInput('attribute'),
      productCategoryUpdatePage.setSequenceNoInput('5'),
      productCategoryUpdatePage.setImageUrlInput('imageUrl'),
      productCategoryUpdatePage.setAltImageUrlInput('altImageUrl'),
      productCategoryUpdatePage.setInfoInput('info'),
      productCategoryUpdatePage.productCategoryTypeSelectLastOption(),
      productCategoryUpdatePage.parentSelectLastOption(),
    ]);

    expect(await productCategoryUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await productCategoryUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await productCategoryUpdatePage.getLongDescriptionInput()).to.eq(
      'longDescription',
      'Expected LongDescription value to be equals to longDescription'
    );
    expect(await productCategoryUpdatePage.getAttributeInput()).to.eq('attribute', 'Expected Attribute value to be equals to attribute');
    expect(await productCategoryUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');
    expect(await productCategoryUpdatePage.getImageUrlInput()).to.eq('imageUrl', 'Expected ImageUrl value to be equals to imageUrl');
    expect(await productCategoryUpdatePage.getAltImageUrlInput()).to.eq(
      'altImageUrl',
      'Expected AltImageUrl value to be equals to altImageUrl'
    );
    expect(await productCategoryUpdatePage.getInfoInput()).to.eq('info', 'Expected Info value to be equals to info');

    await productCategoryUpdatePage.save();
    expect(await productCategoryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productCategoryComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProductCategory', async () => {
    const nbButtonsBeforeDelete = await productCategoryComponentsPage.countDeleteButtons();
    await productCategoryComponentsPage.clickOnLastDeleteButton();

    productCategoryDeleteDialog = new ProductCategoryDeleteDialog();
    expect(await productCategoryDeleteDialog.getDialogTitle()).to.eq('hrApp.productCategory.delete.question');
    await productCategoryDeleteDialog.clickOnConfirmButton();

    expect(await productCategoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
