import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrderItemBillingComponentsPage, OrderItemBillingDeleteDialog, OrderItemBillingUpdatePage } from './order-item-billing.page-object';

const expect = chai.expect;

describe('OrderItemBilling e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let orderItemBillingComponentsPage: OrderItemBillingComponentsPage;
  let orderItemBillingUpdatePage: OrderItemBillingUpdatePage;
  let orderItemBillingDeleteDialog: OrderItemBillingDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OrderItemBillings', async () => {
    await navBarPage.goToEntity('order-item-billing');
    orderItemBillingComponentsPage = new OrderItemBillingComponentsPage();
    await browser.wait(ec.visibilityOf(orderItemBillingComponentsPage.title), 5000);
    expect(await orderItemBillingComponentsPage.getTitle()).to.eq('hrApp.orderItemBilling.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(orderItemBillingComponentsPage.entities), ec.visibilityOf(orderItemBillingComponentsPage.noResult)),
      1000
    );
  });

  it('should load create OrderItemBilling page', async () => {
    await orderItemBillingComponentsPage.clickOnCreateButton();
    orderItemBillingUpdatePage = new OrderItemBillingUpdatePage();
    expect(await orderItemBillingUpdatePage.getPageTitle()).to.eq('hrApp.orderItemBilling.home.createOrEditLabel');
    await orderItemBillingUpdatePage.cancel();
  });

  it('should create and save OrderItemBillings', async () => {
    const nbButtonsBeforeCreate = await orderItemBillingComponentsPage.countDeleteButtons();

    await orderItemBillingComponentsPage.clickOnCreateButton();

    await promise.all([
      orderItemBillingUpdatePage.setQuantityInput('5'),
      orderItemBillingUpdatePage.setAmountInput('5'),
      orderItemBillingUpdatePage.orderItemSelectLastOption(),
      orderItemBillingUpdatePage.invoiceItemSelectLastOption(),
    ]);

    expect(await orderItemBillingUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await orderItemBillingUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');

    await orderItemBillingUpdatePage.save();
    expect(await orderItemBillingUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await orderItemBillingComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last OrderItemBilling', async () => {
    const nbButtonsBeforeDelete = await orderItemBillingComponentsPage.countDeleteButtons();
    await orderItemBillingComponentsPage.clickOnLastDeleteButton();

    orderItemBillingDeleteDialog = new OrderItemBillingDeleteDialog();
    expect(await orderItemBillingDeleteDialog.getDialogTitle()).to.eq('hrApp.orderItemBilling.delete.question');
    await orderItemBillingDeleteDialog.clickOnConfirmButton();

    expect(await orderItemBillingComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
