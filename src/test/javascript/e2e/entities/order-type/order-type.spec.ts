import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrderTypeComponentsPage, OrderTypeDeleteDialog, OrderTypeUpdatePage } from './order-type.page-object';

const expect = chai.expect;

describe('OrderType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let orderTypeComponentsPage: OrderTypeComponentsPage;
  let orderTypeUpdatePage: OrderTypeUpdatePage;
  let orderTypeDeleteDialog: OrderTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OrderTypes', async () => {
    await navBarPage.goToEntity('order-type');
    orderTypeComponentsPage = new OrderTypeComponentsPage();
    await browser.wait(ec.visibilityOf(orderTypeComponentsPage.title), 5000);
    expect(await orderTypeComponentsPage.getTitle()).to.eq('hrApp.orderType.home.title');
    await browser.wait(ec.or(ec.visibilityOf(orderTypeComponentsPage.entities), ec.visibilityOf(orderTypeComponentsPage.noResult)), 1000);
  });

  it('should load create OrderType page', async () => {
    await orderTypeComponentsPage.clickOnCreateButton();
    orderTypeUpdatePage = new OrderTypeUpdatePage();
    expect(await orderTypeUpdatePage.getPageTitle()).to.eq('hrApp.orderType.home.createOrEditLabel');
    await orderTypeUpdatePage.cancel();
  });

  it('should create and save OrderTypes', async () => {
    const nbButtonsBeforeCreate = await orderTypeComponentsPage.countDeleteButtons();

    await orderTypeComponentsPage.clickOnCreateButton();

    await promise.all([orderTypeUpdatePage.setNameInput('name'), orderTypeUpdatePage.setDescriptionInput('description')]);

    expect(await orderTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await orderTypeUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

    await orderTypeUpdatePage.save();
    expect(await orderTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await orderTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last OrderType', async () => {
    const nbButtonsBeforeDelete = await orderTypeComponentsPage.countDeleteButtons();
    await orderTypeComponentsPage.clickOnLastDeleteButton();

    orderTypeDeleteDialog = new OrderTypeDeleteDialog();
    expect(await orderTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.orderType.delete.question');
    await orderTypeDeleteDialog.clickOnConfirmButton();

    expect(await orderTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
