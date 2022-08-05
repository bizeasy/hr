import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrderTermComponentsPage, OrderTermDeleteDialog, OrderTermUpdatePage } from './order-term.page-object';

const expect = chai.expect;

describe('OrderTerm e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let orderTermComponentsPage: OrderTermComponentsPage;
  let orderTermUpdatePage: OrderTermUpdatePage;
  let orderTermDeleteDialog: OrderTermDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OrderTerms', async () => {
    await navBarPage.goToEntity('order-term');
    orderTermComponentsPage = new OrderTermComponentsPage();
    await browser.wait(ec.visibilityOf(orderTermComponentsPage.title), 5000);
    expect(await orderTermComponentsPage.getTitle()).to.eq('hrApp.orderTerm.home.title');
    await browser.wait(ec.or(ec.visibilityOf(orderTermComponentsPage.entities), ec.visibilityOf(orderTermComponentsPage.noResult)), 1000);
  });

  it('should load create OrderTerm page', async () => {
    await orderTermComponentsPage.clickOnCreateButton();
    orderTermUpdatePage = new OrderTermUpdatePage();
    expect(await orderTermUpdatePage.getPageTitle()).to.eq('hrApp.orderTerm.home.createOrEditLabel');
    await orderTermUpdatePage.cancel();
  });

  it('should create and save OrderTerms', async () => {
    const nbButtonsBeforeCreate = await orderTermComponentsPage.countDeleteButtons();

    await orderTermComponentsPage.clickOnCreateButton();

    await promise.all([
      orderTermUpdatePage.setSequenceNoInput('5'),
      orderTermUpdatePage.setNameInput('name'),
      orderTermUpdatePage.setDetailInput('detail'),
      orderTermUpdatePage.setTermValueInput('5'),
      orderTermUpdatePage.setTermDaysInput('5'),
      orderTermUpdatePage.setTextValueInput('textValue'),
      orderTermUpdatePage.orderSelectLastOption(),
      orderTermUpdatePage.itemSelectLastOption(),
      orderTermUpdatePage.termSelectLastOption(),
      orderTermUpdatePage.typeSelectLastOption(),
    ]);

    expect(await orderTermUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');
    expect(await orderTermUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await orderTermUpdatePage.getDetailInput()).to.eq('detail', 'Expected Detail value to be equals to detail');
    expect(await orderTermUpdatePage.getTermValueInput()).to.eq('5', 'Expected termValue value to be equals to 5');
    expect(await orderTermUpdatePage.getTermDaysInput()).to.eq('5', 'Expected termDays value to be equals to 5');
    expect(await orderTermUpdatePage.getTextValueInput()).to.eq('textValue', 'Expected TextValue value to be equals to textValue');

    await orderTermUpdatePage.save();
    expect(await orderTermUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await orderTermComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last OrderTerm', async () => {
    const nbButtonsBeforeDelete = await orderTermComponentsPage.countDeleteButtons();
    await orderTermComponentsPage.clickOnLastDeleteButton();

    orderTermDeleteDialog = new OrderTermDeleteDialog();
    expect(await orderTermDeleteDialog.getDialogTitle()).to.eq('hrApp.orderTerm.delete.question');
    await orderTermDeleteDialog.clickOnConfirmButton();

    expect(await orderTermComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
