import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrderComponentsPage, OrderDeleteDialog, OrderUpdatePage } from './order.page-object';

const expect = chai.expect;

describe('Order e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let orderComponentsPage: OrderComponentsPage;
  let orderUpdatePage: OrderUpdatePage;
  let orderDeleteDialog: OrderDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Orders', async () => {
    await navBarPage.goToEntity('order');
    orderComponentsPage = new OrderComponentsPage();
    await browser.wait(ec.visibilityOf(orderComponentsPage.title), 5000);
    expect(await orderComponentsPage.getTitle()).to.eq('hrApp.order.home.title');
    await browser.wait(ec.or(ec.visibilityOf(orderComponentsPage.entities), ec.visibilityOf(orderComponentsPage.noResult)), 1000);
  });

  it('should load create Order page', async () => {
    await orderComponentsPage.clickOnCreateButton();
    orderUpdatePage = new OrderUpdatePage();
    expect(await orderUpdatePage.getPageTitle()).to.eq('hrApp.order.home.createOrEditLabel');
    await orderUpdatePage.cancel();
  });

  it('should create and save Orders', async () => {
    const nbButtonsBeforeCreate = await orderComponentsPage.countDeleteButtons();

    await orderComponentsPage.clickOnCreateButton();

    await promise.all([
      orderUpdatePage.setNameInput('name'),
      orderUpdatePage.setExternalIdInput('externalId'),
      orderUpdatePage.setOrderDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      orderUpdatePage.setPriorityInput('5'),
      orderUpdatePage.setEntryDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      orderUpdatePage.setRemainingSubTotalInput('5'),
      orderUpdatePage.setGrandTotalInput('5'),
      orderUpdatePage.setVendorReasonInput('vendorReason'),
      orderUpdatePage.setExpectedDeliveryDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      orderUpdatePage.setInsuranceRespInput('insuranceResp'),
      orderUpdatePage.setTransportRespInput('transportResp'),
      orderUpdatePage.setUnloadingRespInput('unloadingResp'),
      orderUpdatePage.orderTypeSelectLastOption(),
      orderUpdatePage.salesChannelSelectLastOption(),
      orderUpdatePage.partySelectLastOption(),
      orderUpdatePage.statusSelectLastOption(),
    ]);

    expect(await orderUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await orderUpdatePage.getExternalIdInput()).to.eq('externalId', 'Expected ExternalId value to be equals to externalId');
    expect(await orderUpdatePage.getOrderDateInput()).to.contain('2001-01-01T02:30', 'Expected orderDate value to be equals to 2000-12-31');
    expect(await orderUpdatePage.getPriorityInput()).to.eq('5', 'Expected priority value to be equals to 5');
    expect(await orderUpdatePage.getEntryDateInput()).to.contain('2001-01-01T02:30', 'Expected entryDate value to be equals to 2000-12-31');
    const selectedIsRushOrder = orderUpdatePage.getIsRushOrderInput();
    if (await selectedIsRushOrder.isSelected()) {
      await orderUpdatePage.getIsRushOrderInput().click();
      expect(await orderUpdatePage.getIsRushOrderInput().isSelected(), 'Expected isRushOrder not to be selected').to.be.false;
    } else {
      await orderUpdatePage.getIsRushOrderInput().click();
      expect(await orderUpdatePage.getIsRushOrderInput().isSelected(), 'Expected isRushOrder to be selected').to.be.true;
    }
    const selectedNeedsInventoryIssuance = orderUpdatePage.getNeedsInventoryIssuanceInput();
    if (await selectedNeedsInventoryIssuance.isSelected()) {
      await orderUpdatePage.getNeedsInventoryIssuanceInput().click();
      expect(await orderUpdatePage.getNeedsInventoryIssuanceInput().isSelected(), 'Expected needsInventoryIssuance not to be selected').to
        .be.false;
    } else {
      await orderUpdatePage.getNeedsInventoryIssuanceInput().click();
      expect(await orderUpdatePage.getNeedsInventoryIssuanceInput().isSelected(), 'Expected needsInventoryIssuance to be selected').to.be
        .true;
    }
    expect(await orderUpdatePage.getRemainingSubTotalInput()).to.eq('5', 'Expected remainingSubTotal value to be equals to 5');
    expect(await orderUpdatePage.getGrandTotalInput()).to.eq('5', 'Expected grandTotal value to be equals to 5');
    const selectedHasRateContract = orderUpdatePage.getHasRateContractInput();
    if (await selectedHasRateContract.isSelected()) {
      await orderUpdatePage.getHasRateContractInput().click();
      expect(await orderUpdatePage.getHasRateContractInput().isSelected(), 'Expected hasRateContract not to be selected').to.be.false;
    } else {
      await orderUpdatePage.getHasRateContractInput().click();
      expect(await orderUpdatePage.getHasRateContractInput().isSelected(), 'Expected hasRateContract to be selected').to.be.true;
    }
    const selectedGotQuoteFromVendors = orderUpdatePage.getGotQuoteFromVendorsInput();
    if (await selectedGotQuoteFromVendors.isSelected()) {
      await orderUpdatePage.getGotQuoteFromVendorsInput().click();
      expect(await orderUpdatePage.getGotQuoteFromVendorsInput().isSelected(), 'Expected gotQuoteFromVendors not to be selected').to.be
        .false;
    } else {
      await orderUpdatePage.getGotQuoteFromVendorsInput().click();
      expect(await orderUpdatePage.getGotQuoteFromVendorsInput().isSelected(), 'Expected gotQuoteFromVendors to be selected').to.be.true;
    }
    expect(await orderUpdatePage.getVendorReasonInput()).to.eq('vendorReason', 'Expected VendorReason value to be equals to vendorReason');
    expect(await orderUpdatePage.getExpectedDeliveryDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected expectedDeliveryDate value to be equals to 2000-12-31'
    );
    expect(await orderUpdatePage.getInsuranceRespInput()).to.eq(
      'insuranceResp',
      'Expected InsuranceResp value to be equals to insuranceResp'
    );
    expect(await orderUpdatePage.getTransportRespInput()).to.eq(
      'transportResp',
      'Expected TransportResp value to be equals to transportResp'
    );
    expect(await orderUpdatePage.getUnloadingRespInput()).to.eq(
      'unloadingResp',
      'Expected UnloadingResp value to be equals to unloadingResp'
    );

    await orderUpdatePage.save();
    expect(await orderUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await orderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Order', async () => {
    const nbButtonsBeforeDelete = await orderComponentsPage.countDeleteButtons();
    await orderComponentsPage.clickOnLastDeleteButton();

    orderDeleteDialog = new OrderDeleteDialog();
    expect(await orderDeleteDialog.getDialogTitle()).to.eq('hrApp.order.delete.question');
    await orderDeleteDialog.clickOnConfirmButton();

    expect(await orderComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
