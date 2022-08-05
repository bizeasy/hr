import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ProductStoreFacilityComponentsPage,
  /* ProductStoreFacilityDeleteDialog, */
  ProductStoreFacilityUpdatePage,
} from './product-store-facility.page-object';

const expect = chai.expect;

describe('ProductStoreFacility e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productStoreFacilityComponentsPage: ProductStoreFacilityComponentsPage;
  let productStoreFacilityUpdatePage: ProductStoreFacilityUpdatePage;
  /* let productStoreFacilityDeleteDialog: ProductStoreFacilityDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductStoreFacilities', async () => {
    await navBarPage.goToEntity('product-store-facility');
    productStoreFacilityComponentsPage = new ProductStoreFacilityComponentsPage();
    await browser.wait(ec.visibilityOf(productStoreFacilityComponentsPage.title), 5000);
    expect(await productStoreFacilityComponentsPage.getTitle()).to.eq('hrApp.productStoreFacility.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(productStoreFacilityComponentsPage.entities), ec.visibilityOf(productStoreFacilityComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ProductStoreFacility page', async () => {
    await productStoreFacilityComponentsPage.clickOnCreateButton();
    productStoreFacilityUpdatePage = new ProductStoreFacilityUpdatePage();
    expect(await productStoreFacilityUpdatePage.getPageTitle()).to.eq('hrApp.productStoreFacility.home.createOrEditLabel');
    await productStoreFacilityUpdatePage.cancel();
  });

  /* it('should create and save ProductStoreFacilities', async () => {
        const nbButtonsBeforeCreate = await productStoreFacilityComponentsPage.countDeleteButtons();

        await productStoreFacilityComponentsPage.clickOnCreateButton();

        await promise.all([
            productStoreFacilityUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            productStoreFacilityUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            productStoreFacilityUpdatePage.setSequenceNoInput('5'),
            productStoreFacilityUpdatePage.productStoreSelectLastOption(),
            productStoreFacilityUpdatePage.facilitySelectLastOption(),
        ]);

        expect(await productStoreFacilityUpdatePage.getFromDateInput()).to.contain('2001-01-01T02:30', 'Expected fromDate value to be equals to 2000-12-31');
        expect(await productStoreFacilityUpdatePage.getThruDateInput()).to.contain('2001-01-01T02:30', 'Expected thruDate value to be equals to 2000-12-31');
        expect(await productStoreFacilityUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');

        await productStoreFacilityUpdatePage.save();
        expect(await productStoreFacilityUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await productStoreFacilityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last ProductStoreFacility', async () => {
        const nbButtonsBeforeDelete = await productStoreFacilityComponentsPage.countDeleteButtons();
        await productStoreFacilityComponentsPage.clickOnLastDeleteButton();

        productStoreFacilityDeleteDialog = new ProductStoreFacilityDeleteDialog();
        expect(await productStoreFacilityDeleteDialog.getDialogTitle())
            .to.eq('hrApp.productStoreFacility.delete.question');
        await productStoreFacilityDeleteDialog.clickOnConfirmButton();

        expect(await productStoreFacilityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
