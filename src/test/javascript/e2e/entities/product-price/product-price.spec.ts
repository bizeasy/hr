import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductPriceComponentsPage, ProductPriceDeleteDialog, ProductPriceUpdatePage } from './product-price.page-object';

const expect = chai.expect;

describe('ProductPrice e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productPriceComponentsPage: ProductPriceComponentsPage;
  let productPriceUpdatePage: ProductPriceUpdatePage;
  let productPriceDeleteDialog: ProductPriceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductPrices', async () => {
    await navBarPage.goToEntity('product-price');
    productPriceComponentsPage = new ProductPriceComponentsPage();
    await browser.wait(ec.visibilityOf(productPriceComponentsPage.title), 5000);
    expect(await productPriceComponentsPage.getTitle()).to.eq('hrApp.productPrice.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(productPriceComponentsPage.entities), ec.visibilityOf(productPriceComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ProductPrice page', async () => {
    await productPriceComponentsPage.clickOnCreateButton();
    productPriceUpdatePage = new ProductPriceUpdatePage();
    expect(await productPriceUpdatePage.getPageTitle()).to.eq('hrApp.productPrice.home.createOrEditLabel');
    await productPriceUpdatePage.cancel();
  });

  it('should create and save ProductPrices', async () => {
    const nbButtonsBeforeCreate = await productPriceComponentsPage.countDeleteButtons();

    await productPriceComponentsPage.clickOnCreateButton();

    await promise.all([
      productPriceUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      productPriceUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      productPriceUpdatePage.setPriceInput('5'),
      productPriceUpdatePage.setCgstInput('5'),
      productPriceUpdatePage.setIgstInput('5'),
      productPriceUpdatePage.setSgstInput('5'),
      productPriceUpdatePage.setTotalPriceInput('5'),
      productPriceUpdatePage.productSelectLastOption(),
      productPriceUpdatePage.productPriceTypeSelectLastOption(),
      productPriceUpdatePage.productPricePurposeSelectLastOption(),
      productPriceUpdatePage.currencyUomSelectLastOption(),
    ]);

    expect(await productPriceUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await productPriceUpdatePage.getThruDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected thruDate value to be equals to 2000-12-31'
    );
    expect(await productPriceUpdatePage.getPriceInput()).to.eq('5', 'Expected price value to be equals to 5');
    expect(await productPriceUpdatePage.getCgstInput()).to.eq('5', 'Expected cgst value to be equals to 5');
    expect(await productPriceUpdatePage.getIgstInput()).to.eq('5', 'Expected igst value to be equals to 5');
    expect(await productPriceUpdatePage.getSgstInput()).to.eq('5', 'Expected sgst value to be equals to 5');
    expect(await productPriceUpdatePage.getTotalPriceInput()).to.eq('5', 'Expected totalPrice value to be equals to 5');

    await productPriceUpdatePage.save();
    expect(await productPriceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productPriceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ProductPrice', async () => {
    const nbButtonsBeforeDelete = await productPriceComponentsPage.countDeleteButtons();
    await productPriceComponentsPage.clickOnLastDeleteButton();

    productPriceDeleteDialog = new ProductPriceDeleteDialog();
    expect(await productPriceDeleteDialog.getDialogTitle()).to.eq('hrApp.productPrice.delete.question');
    await productPriceDeleteDialog.clickOnConfirmButton();

    expect(await productPriceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
