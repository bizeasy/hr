import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductKeywordComponentsPage, ProductKeywordDeleteDialog, ProductKeywordUpdatePage } from './product-keyword.page-object';

const expect = chai.expect;

describe('ProductKeyword e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productKeywordComponentsPage: ProductKeywordComponentsPage;
  let productKeywordUpdatePage: ProductKeywordUpdatePage;
  let productKeywordDeleteDialog: ProductKeywordDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductKeywords', async () => {
    await navBarPage.goToEntity('product-keyword');
    productKeywordComponentsPage = new ProductKeywordComponentsPage();
    await browser.wait(ec.visibilityOf(productKeywordComponentsPage.title), 5000);
    expect(await productKeywordComponentsPage.getTitle()).to.eq('hrApp.productKeyword.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(productKeywordComponentsPage.entities), ec.visibilityOf(productKeywordComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ProductKeyword page', async () => {
    await productKeywordComponentsPage.clickOnCreateButton();
    productKeywordUpdatePage = new ProductKeywordUpdatePage();
    expect(await productKeywordUpdatePage.getPageTitle()).to.eq('hrApp.productKeyword.home.createOrEditLabel');
    await productKeywordUpdatePage.cancel();
  });

  it('should create and save ProductKeywords', async () => {
    const nbButtonsBeforeCreate = await productKeywordComponentsPage.countDeleteButtons();

    await productKeywordComponentsPage.clickOnCreateButton();

    await promise.all([
      productKeywordUpdatePage.setKeywordInput('keyword'),
      productKeywordUpdatePage.setRelevancyWeightInput('5'),
      productKeywordUpdatePage.productSelectLastOption(),
      productKeywordUpdatePage.keywordTypeSelectLastOption(),
    ]);

    expect(await productKeywordUpdatePage.getKeywordInput()).to.eq('keyword', 'Expected Keyword value to be equals to keyword');
    expect(await productKeywordUpdatePage.getRelevancyWeightInput()).to.eq('5', 'Expected relevancyWeight value to be equals to 5');

    await productKeywordUpdatePage.save();
    expect(await productKeywordUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productKeywordComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProductKeyword', async () => {
    const nbButtonsBeforeDelete = await productKeywordComponentsPage.countDeleteButtons();
    await productKeywordComponentsPage.clickOnLastDeleteButton();

    productKeywordDeleteDialog = new ProductKeywordDeleteDialog();
    expect(await productKeywordDeleteDialog.getDialogTitle()).to.eq('hrApp.productKeyword.delete.question');
    await productKeywordDeleteDialog.clickOnConfirmButton();

    expect(await productKeywordComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
