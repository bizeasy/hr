import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductFacilityComponentsPage, ProductFacilityDeleteDialog, ProductFacilityUpdatePage } from './product-facility.page-object';

const expect = chai.expect;

describe('ProductFacility e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productFacilityComponentsPage: ProductFacilityComponentsPage;
  let productFacilityUpdatePage: ProductFacilityUpdatePage;
  let productFacilityDeleteDialog: ProductFacilityDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductFacilities', async () => {
    await navBarPage.goToEntity('product-facility');
    productFacilityComponentsPage = new ProductFacilityComponentsPage();
    await browser.wait(ec.visibilityOf(productFacilityComponentsPage.title), 5000);
    expect(await productFacilityComponentsPage.getTitle()).to.eq('hrApp.productFacility.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(productFacilityComponentsPage.entities), ec.visibilityOf(productFacilityComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ProductFacility page', async () => {
    await productFacilityComponentsPage.clickOnCreateButton();
    productFacilityUpdatePage = new ProductFacilityUpdatePage();
    expect(await productFacilityUpdatePage.getPageTitle()).to.eq('hrApp.productFacility.home.createOrEditLabel');
    await productFacilityUpdatePage.cancel();
  });

  it('should create and save ProductFacilities', async () => {
    const nbButtonsBeforeCreate = await productFacilityComponentsPage.countDeleteButtons();

    await productFacilityComponentsPage.clickOnCreateButton();

    await promise.all([
      productFacilityUpdatePage.setMinimumStockInput('5'),
      productFacilityUpdatePage.setReorderQtyInput('5'),
      productFacilityUpdatePage.setDaysToShipInput('5'),
      productFacilityUpdatePage.setLastInventoryCountInput('5'),
      productFacilityUpdatePage.productSelectLastOption(),
      productFacilityUpdatePage.facilitySelectLastOption(),
    ]);

    expect(await productFacilityUpdatePage.getMinimumStockInput()).to.eq('5', 'Expected minimumStock value to be equals to 5');
    expect(await productFacilityUpdatePage.getReorderQtyInput()).to.eq('5', 'Expected reorderQty value to be equals to 5');
    expect(await productFacilityUpdatePage.getDaysToShipInput()).to.eq('5', 'Expected daysToShip value to be equals to 5');
    expect(await productFacilityUpdatePage.getLastInventoryCountInput()).to.eq('5', 'Expected lastInventoryCount value to be equals to 5');

    await productFacilityUpdatePage.save();
    expect(await productFacilityUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productFacilityComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProductFacility', async () => {
    const nbButtonsBeforeDelete = await productFacilityComponentsPage.countDeleteButtons();
    await productFacilityComponentsPage.clickOnLastDeleteButton();

    productFacilityDeleteDialog = new ProductFacilityDeleteDialog();
    expect(await productFacilityDeleteDialog.getDialogTitle()).to.eq('hrApp.productFacility.delete.question');
    await productFacilityDeleteDialog.clickOnConfirmButton();

    expect(await productFacilityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
