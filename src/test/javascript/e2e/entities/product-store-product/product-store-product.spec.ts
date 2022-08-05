import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ProductStoreProductComponentsPage,
  /* ProductStoreProductDeleteDialog, */
  ProductStoreProductUpdatePage,
} from './product-store-product.page-object';

const expect = chai.expect;

describe('ProductStoreProduct e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productStoreProductComponentsPage: ProductStoreProductComponentsPage;
  let productStoreProductUpdatePage: ProductStoreProductUpdatePage;
  /* let productStoreProductDeleteDialog: ProductStoreProductDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductStoreProducts', async () => {
    await navBarPage.goToEntity('product-store-product');
    productStoreProductComponentsPage = new ProductStoreProductComponentsPage();
    await browser.wait(ec.visibilityOf(productStoreProductComponentsPage.title), 5000);
    expect(await productStoreProductComponentsPage.getTitle()).to.eq('hrApp.productStoreProduct.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(productStoreProductComponentsPage.entities), ec.visibilityOf(productStoreProductComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ProductStoreProduct page', async () => {
    await productStoreProductComponentsPage.clickOnCreateButton();
    productStoreProductUpdatePage = new ProductStoreProductUpdatePage();
    expect(await productStoreProductUpdatePage.getPageTitle()).to.eq('hrApp.productStoreProduct.home.createOrEditLabel');
    await productStoreProductUpdatePage.cancel();
  });

  /* it('should create and save ProductStoreProducts', async () => {
        const nbButtonsBeforeCreate = await productStoreProductComponentsPage.countDeleteButtons();

        await productStoreProductComponentsPage.clickOnCreateButton();

        await promise.all([
            productStoreProductUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            productStoreProductUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            productStoreProductUpdatePage.setSequenceNoInput('5'),
            productStoreProductUpdatePage.productStoreSelectLastOption(),
            productStoreProductUpdatePage.productSelectLastOption(),
        ]);

        expect(await productStoreProductUpdatePage.getFromDateInput()).to.contain('2001-01-01T02:30', 'Expected fromDate value to be equals to 2000-12-31');
        expect(await productStoreProductUpdatePage.getThruDateInput()).to.contain('2001-01-01T02:30', 'Expected thruDate value to be equals to 2000-12-31');
        expect(await productStoreProductUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');

        await productStoreProductUpdatePage.save();
        expect(await productStoreProductUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await productStoreProductComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last ProductStoreProduct', async () => {
        const nbButtonsBeforeDelete = await productStoreProductComponentsPage.countDeleteButtons();
        await productStoreProductComponentsPage.clickOnLastDeleteButton();

        productStoreProductDeleteDialog = new ProductStoreProductDeleteDialog();
        expect(await productStoreProductDeleteDialog.getDialogTitle())
            .to.eq('hrApp.productStoreProduct.delete.question');
        await productStoreProductDeleteDialog.clickOnConfirmButton();

        expect(await productStoreProductComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
