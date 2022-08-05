import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrderItemTypeComponentsPage, OrderItemTypeDeleteDialog, OrderItemTypeUpdatePage } from './order-item-type.page-object';

const expect = chai.expect;

describe('OrderItemType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let orderItemTypeComponentsPage: OrderItemTypeComponentsPage;
  let orderItemTypeUpdatePage: OrderItemTypeUpdatePage;
  let orderItemTypeDeleteDialog: OrderItemTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OrderItemTypes', async () => {
    await navBarPage.goToEntity('order-item-type');
    orderItemTypeComponentsPage = new OrderItemTypeComponentsPage();
    await browser.wait(ec.visibilityOf(orderItemTypeComponentsPage.title), 5000);
    expect(await orderItemTypeComponentsPage.getTitle()).to.eq('hrApp.orderItemType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(orderItemTypeComponentsPage.entities), ec.visibilityOf(orderItemTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create OrderItemType page', async () => {
    await orderItemTypeComponentsPage.clickOnCreateButton();
    orderItemTypeUpdatePage = new OrderItemTypeUpdatePage();
    expect(await orderItemTypeUpdatePage.getPageTitle()).to.eq('hrApp.orderItemType.home.createOrEditLabel');
    await orderItemTypeUpdatePage.cancel();
  });

  it('should create and save OrderItemTypes', async () => {
    const nbButtonsBeforeCreate = await orderItemTypeComponentsPage.countDeleteButtons();

    await orderItemTypeComponentsPage.clickOnCreateButton();

    await promise.all([orderItemTypeUpdatePage.setNameInput('name'), orderItemTypeUpdatePage.setDescriptionInput('description')]);

    expect(await orderItemTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await orderItemTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await orderItemTypeUpdatePage.save();
    expect(await orderItemTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await orderItemTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last OrderItemType', async () => {
    const nbButtonsBeforeDelete = await orderItemTypeComponentsPage.countDeleteButtons();
    await orderItemTypeComponentsPage.clickOnLastDeleteButton();

    orderItemTypeDeleteDialog = new OrderItemTypeDeleteDialog();
    expect(await orderItemTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.orderItemType.delete.question');
    await orderItemTypeDeleteDialog.clickOnConfirmButton();

    expect(await orderItemTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
