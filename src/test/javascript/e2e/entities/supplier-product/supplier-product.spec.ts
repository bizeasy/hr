import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SupplierProductComponentsPage, SupplierProductDeleteDialog, SupplierProductUpdatePage } from './supplier-product.page-object';

const expect = chai.expect;

describe('SupplierProduct e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let supplierProductComponentsPage: SupplierProductComponentsPage;
  let supplierProductUpdatePage: SupplierProductUpdatePage;
  let supplierProductDeleteDialog: SupplierProductDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SupplierProducts', async () => {
    await navBarPage.goToEntity('supplier-product');
    supplierProductComponentsPage = new SupplierProductComponentsPage();
    await browser.wait(ec.visibilityOf(supplierProductComponentsPage.title), 5000);
    expect(await supplierProductComponentsPage.getTitle()).to.eq('hrApp.supplierProduct.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(supplierProductComponentsPage.entities), ec.visibilityOf(supplierProductComponentsPage.noResult)),
      1000
    );
  });

  it('should load create SupplierProduct page', async () => {
    await supplierProductComponentsPage.clickOnCreateButton();
    supplierProductUpdatePage = new SupplierProductUpdatePage();
    expect(await supplierProductUpdatePage.getPageTitle()).to.eq('hrApp.supplierProduct.home.createOrEditLabel');
    await supplierProductUpdatePage.cancel();
  });

  it('should create and save SupplierProducts', async () => {
    const nbButtonsBeforeCreate = await supplierProductComponentsPage.countDeleteButtons();

    await supplierProductComponentsPage.clickOnCreateButton();

    await promise.all([
      supplierProductUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      supplierProductUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      supplierProductUpdatePage.setMinOrderQtyInput('5'),
      supplierProductUpdatePage.setLastPriceInput('5'),
      supplierProductUpdatePage.setShippingPriceInput('5'),
      supplierProductUpdatePage.setSupplierProductIdInput('supplierProductId'),
      supplierProductUpdatePage.setLeadTimeDaysInput('5'),
      supplierProductUpdatePage.setCgstInput('5'),
      supplierProductUpdatePage.setIgstInput('5'),
      supplierProductUpdatePage.setSgstInput('5'),
      supplierProductUpdatePage.setTotalPriceInput('5'),
      supplierProductUpdatePage.setCommentsInput('comments'),
      supplierProductUpdatePage.setSupplierProductNameInput('supplierProductName'),
      supplierProductUpdatePage.setManufacturerNameInput('manufacturerName'),
      supplierProductUpdatePage.productSelectLastOption(),
      supplierProductUpdatePage.supplierSelectLastOption(),
      supplierProductUpdatePage.quantityUomSelectLastOption(),
      supplierProductUpdatePage.currencyUomSelectLastOption(),
      supplierProductUpdatePage.manufacturerSelectLastOption(),
    ]);

    expect(await supplierProductUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await supplierProductUpdatePage.getThruDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected thruDate value to be equals to 2000-12-31'
    );
    expect(await supplierProductUpdatePage.getMinOrderQtyInput()).to.eq('5', 'Expected minOrderQty value to be equals to 5');
    expect(await supplierProductUpdatePage.getLastPriceInput()).to.eq('5', 'Expected lastPrice value to be equals to 5');
    expect(await supplierProductUpdatePage.getShippingPriceInput()).to.eq('5', 'Expected shippingPrice value to be equals to 5');
    expect(await supplierProductUpdatePage.getSupplierProductIdInput()).to.eq(
      'supplierProductId',
      'Expected SupplierProductId value to be equals to supplierProductId'
    );
    expect(await supplierProductUpdatePage.getLeadTimeDaysInput()).to.eq('5', 'Expected leadTimeDays value to be equals to 5');
    expect(await supplierProductUpdatePage.getCgstInput()).to.eq('5', 'Expected cgst value to be equals to 5');
    expect(await supplierProductUpdatePage.getIgstInput()).to.eq('5', 'Expected igst value to be equals to 5');
    expect(await supplierProductUpdatePage.getSgstInput()).to.eq('5', 'Expected sgst value to be equals to 5');
    expect(await supplierProductUpdatePage.getTotalPriceInput()).to.eq('5', 'Expected totalPrice value to be equals to 5');
    expect(await supplierProductUpdatePage.getCommentsInput()).to.eq('comments', 'Expected Comments value to be equals to comments');
    expect(await supplierProductUpdatePage.getSupplierProductNameInput()).to.eq(
      'supplierProductName',
      'Expected SupplierProductName value to be equals to supplierProductName'
    );
    expect(await supplierProductUpdatePage.getManufacturerNameInput()).to.eq(
      'manufacturerName',
      'Expected ManufacturerName value to be equals to manufacturerName'
    );

    await supplierProductUpdatePage.save();
    expect(await supplierProductUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await supplierProductComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last SupplierProduct', async () => {
    const nbButtonsBeforeDelete = await supplierProductComponentsPage.countDeleteButtons();
    await supplierProductComponentsPage.clickOnLastDeleteButton();

    supplierProductDeleteDialog = new SupplierProductDeleteDialog();
    expect(await supplierProductDeleteDialog.getDialogTitle()).to.eq('hrApp.supplierProduct.delete.question');
    await supplierProductDeleteDialog.clickOnConfirmButton();

    expect(await supplierProductComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
