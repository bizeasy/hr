import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrderStatusComponentsPage, OrderStatusDeleteDialog, OrderStatusUpdatePage } from './order-status.page-object';

const expect = chai.expect;

describe('OrderStatus e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let orderStatusComponentsPage: OrderStatusComponentsPage;
  let orderStatusUpdatePage: OrderStatusUpdatePage;
  let orderStatusDeleteDialog: OrderStatusDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OrderStatuses', async () => {
    await navBarPage.goToEntity('order-status');
    orderStatusComponentsPage = new OrderStatusComponentsPage();
    await browser.wait(ec.visibilityOf(orderStatusComponentsPage.title), 5000);
    expect(await orderStatusComponentsPage.getTitle()).to.eq('hrApp.orderStatus.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(orderStatusComponentsPage.entities), ec.visibilityOf(orderStatusComponentsPage.noResult)),
      1000
    );
  });

  it('should load create OrderStatus page', async () => {
    await orderStatusComponentsPage.clickOnCreateButton();
    orderStatusUpdatePage = new OrderStatusUpdatePage();
    expect(await orderStatusUpdatePage.getPageTitle()).to.eq('hrApp.orderStatus.home.createOrEditLabel');
    await orderStatusUpdatePage.cancel();
  });

  it('should create and save OrderStatuses', async () => {
    const nbButtonsBeforeCreate = await orderStatusComponentsPage.countDeleteButtons();

    await orderStatusComponentsPage.clickOnCreateButton();

    await promise.all([
      orderStatusUpdatePage.setStatusDateTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      orderStatusUpdatePage.setSequenceNoInput('5'),
      orderStatusUpdatePage.statusSelectLastOption(),
      orderStatusUpdatePage.orderSelectLastOption(),
      orderStatusUpdatePage.reasonSelectLastOption(),
    ]);

    expect(await orderStatusUpdatePage.getStatusDateTimeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected statusDateTime value to be equals to 2000-12-31'
    );
    expect(await orderStatusUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');

    await orderStatusUpdatePage.save();
    expect(await orderStatusUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await orderStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last OrderStatus', async () => {
    const nbButtonsBeforeDelete = await orderStatusComponentsPage.countDeleteButtons();
    await orderStatusComponentsPage.clickOnLastDeleteButton();

    orderStatusDeleteDialog = new OrderStatusDeleteDialog();
    expect(await orderStatusDeleteDialog.getDialogTitle()).to.eq('hrApp.orderStatus.delete.question');
    await orderStatusDeleteDialog.clickOnConfirmButton();

    expect(await orderStatusComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
