import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrderItemComponentsPage, OrderItemDeleteDialog, OrderItemUpdatePage } from './order-item.page-object';

const expect = chai.expect;

describe('OrderItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let orderItemComponentsPage: OrderItemComponentsPage;
  let orderItemUpdatePage: OrderItemUpdatePage;
  let orderItemDeleteDialog: OrderItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OrderItems', async () => {
    await navBarPage.goToEntity('order-item');
    orderItemComponentsPage = new OrderItemComponentsPage();
    await browser.wait(ec.visibilityOf(orderItemComponentsPage.title), 5000);
    expect(await orderItemComponentsPage.getTitle()).to.eq('hrApp.orderItem.home.title');
    await browser.wait(ec.or(ec.visibilityOf(orderItemComponentsPage.entities), ec.visibilityOf(orderItemComponentsPage.noResult)), 1000);
  });

  it('should load create OrderItem page', async () => {
    await orderItemComponentsPage.clickOnCreateButton();
    orderItemUpdatePage = new OrderItemUpdatePage();
    expect(await orderItemUpdatePage.getPageTitle()).to.eq('hrApp.orderItem.home.createOrEditLabel');
    await orderItemUpdatePage.cancel();
  });

  it('should create and save OrderItems', async () => {
    const nbButtonsBeforeCreate = await orderItemComponentsPage.countDeleteButtons();

    await orderItemComponentsPage.clickOnCreateButton();

    await promise.all([
      orderItemUpdatePage.setSequenceNoInput('5'),
      orderItemUpdatePage.setQuantityInput('5'),
      orderItemUpdatePage.setCancelQuantityInput('5'),
      orderItemUpdatePage.setSelectedAmountInput('5'),
      orderItemUpdatePage.setUnitPriceInput('5'),
      orderItemUpdatePage.setUnitListPriceInput('5'),
      orderItemUpdatePage.setCgstInput('5'),
      orderItemUpdatePage.setIgstInput('5'),
      orderItemUpdatePage.setSgstInput('5'),
      orderItemUpdatePage.setCgstPercentageInput('5'),
      orderItemUpdatePage.setIgstPercentageInput('5'),
      orderItemUpdatePage.setSgstPercentageInput('5'),
      orderItemUpdatePage.setTotalPriceInput('5'),
      orderItemUpdatePage.setEstimatedShipDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      orderItemUpdatePage.setEstimatedDeliveryDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      orderItemUpdatePage.setShipBeforeDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      orderItemUpdatePage.setShipAfterDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      orderItemUpdatePage.orderSelectLastOption(),
      orderItemUpdatePage.orderItemTypeSelectLastOption(),
      orderItemUpdatePage.fromInventoryItemSelectLastOption(),
      orderItemUpdatePage.productSelectLastOption(),
      orderItemUpdatePage.supplierProductSelectLastOption(),
      orderItemUpdatePage.statusSelectLastOption(),
      orderItemUpdatePage.taxAuthRateSelectLastOption(),
    ]);

    expect(await orderItemUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');
    expect(await orderItemUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await orderItemUpdatePage.getCancelQuantityInput()).to.eq('5', 'Expected cancelQuantity value to be equals to 5');
    expect(await orderItemUpdatePage.getSelectedAmountInput()).to.eq('5', 'Expected selectedAmount value to be equals to 5');
    expect(await orderItemUpdatePage.getUnitPriceInput()).to.eq('5', 'Expected unitPrice value to be equals to 5');
    expect(await orderItemUpdatePage.getUnitListPriceInput()).to.eq('5', 'Expected unitListPrice value to be equals to 5');
    expect(await orderItemUpdatePage.getCgstInput()).to.eq('5', 'Expected cgst value to be equals to 5');
    expect(await orderItemUpdatePage.getIgstInput()).to.eq('5', 'Expected igst value to be equals to 5');
    expect(await orderItemUpdatePage.getSgstInput()).to.eq('5', 'Expected sgst value to be equals to 5');
    expect(await orderItemUpdatePage.getCgstPercentageInput()).to.eq('5', 'Expected cgstPercentage value to be equals to 5');
    expect(await orderItemUpdatePage.getIgstPercentageInput()).to.eq('5', 'Expected igstPercentage value to be equals to 5');
    expect(await orderItemUpdatePage.getSgstPercentageInput()).to.eq('5', 'Expected sgstPercentage value to be equals to 5');
    expect(await orderItemUpdatePage.getTotalPriceInput()).to.eq('5', 'Expected totalPrice value to be equals to 5');
    const selectedIsModifiedPrice = orderItemUpdatePage.getIsModifiedPriceInput();
    if (await selectedIsModifiedPrice.isSelected()) {
      await orderItemUpdatePage.getIsModifiedPriceInput().click();
      expect(await orderItemUpdatePage.getIsModifiedPriceInput().isSelected(), 'Expected isModifiedPrice not to be selected').to.be.false;
    } else {
      await orderItemUpdatePage.getIsModifiedPriceInput().click();
      expect(await orderItemUpdatePage.getIsModifiedPriceInput().isSelected(), 'Expected isModifiedPrice to be selected').to.be.true;
    }
    expect(await orderItemUpdatePage.getEstimatedShipDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected estimatedShipDate value to be equals to 2000-12-31'
    );
    expect(await orderItemUpdatePage.getEstimatedDeliveryDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected estimatedDeliveryDate value to be equals to 2000-12-31'
    );
    expect(await orderItemUpdatePage.getShipBeforeDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected shipBeforeDate value to be equals to 2000-12-31'
    );
    expect(await orderItemUpdatePage.getShipAfterDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected shipAfterDate value to be equals to 2000-12-31'
    );

    await orderItemUpdatePage.save();
    expect(await orderItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await orderItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last OrderItem', async () => {
    const nbButtonsBeforeDelete = await orderItemComponentsPage.countDeleteButtons();
    await orderItemComponentsPage.clickOnLastDeleteButton();

    orderItemDeleteDialog = new OrderItemDeleteDialog();
    expect(await orderItemDeleteDialog.getDialogTitle()).to.eq('hrApp.orderItem.delete.question');
    await orderItemDeleteDialog.clickOnConfirmButton();

    expect(await orderItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
