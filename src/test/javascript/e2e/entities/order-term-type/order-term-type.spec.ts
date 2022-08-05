import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrderTermTypeComponentsPage, OrderTermTypeDeleteDialog, OrderTermTypeUpdatePage } from './order-term-type.page-object';

const expect = chai.expect;

describe('OrderTermType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let orderTermTypeComponentsPage: OrderTermTypeComponentsPage;
  let orderTermTypeUpdatePage: OrderTermTypeUpdatePage;
  let orderTermTypeDeleteDialog: OrderTermTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OrderTermTypes', async () => {
    await navBarPage.goToEntity('order-term-type');
    orderTermTypeComponentsPage = new OrderTermTypeComponentsPage();
    await browser.wait(ec.visibilityOf(orderTermTypeComponentsPage.title), 5000);
    expect(await orderTermTypeComponentsPage.getTitle()).to.eq('hrApp.orderTermType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(orderTermTypeComponentsPage.entities), ec.visibilityOf(orderTermTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create OrderTermType page', async () => {
    await orderTermTypeComponentsPage.clickOnCreateButton();
    orderTermTypeUpdatePage = new OrderTermTypeUpdatePage();
    expect(await orderTermTypeUpdatePage.getPageTitle()).to.eq('hrApp.orderTermType.home.createOrEditLabel');
    await orderTermTypeUpdatePage.cancel();
  });

  it('should create and save OrderTermTypes', async () => {
    const nbButtonsBeforeCreate = await orderTermTypeComponentsPage.countDeleteButtons();

    await orderTermTypeComponentsPage.clickOnCreateButton();

    await promise.all([orderTermTypeUpdatePage.setNameInput('name'), orderTermTypeUpdatePage.setDescriptionInput('description')]);

    expect(await orderTermTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await orderTermTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await orderTermTypeUpdatePage.save();
    expect(await orderTermTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await orderTermTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last OrderTermType', async () => {
    const nbButtonsBeforeDelete = await orderTermTypeComponentsPage.countDeleteButtons();
    await orderTermTypeComponentsPage.clickOnLastDeleteButton();

    orderTermTypeDeleteDialog = new OrderTermTypeDeleteDialog();
    expect(await orderTermTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.orderTermType.delete.question');
    await orderTermTypeDeleteDialog.clickOnConfirmButton();

    expect(await orderTermTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
