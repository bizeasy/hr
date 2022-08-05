import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { OrderContactMechComponentsPage, OrderContactMechDeleteDialog, OrderContactMechUpdatePage } from './order-contact-mech.page-object';

const expect = chai.expect;

describe('OrderContactMech e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let orderContactMechComponentsPage: OrderContactMechComponentsPage;
  let orderContactMechUpdatePage: OrderContactMechUpdatePage;
  let orderContactMechDeleteDialog: OrderContactMechDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OrderContactMeches', async () => {
    await navBarPage.goToEntity('order-contact-mech');
    orderContactMechComponentsPage = new OrderContactMechComponentsPage();
    await browser.wait(ec.visibilityOf(orderContactMechComponentsPage.title), 5000);
    expect(await orderContactMechComponentsPage.getTitle()).to.eq('hrApp.orderContactMech.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(orderContactMechComponentsPage.entities), ec.visibilityOf(orderContactMechComponentsPage.noResult)),
      1000
    );
  });

  it('should load create OrderContactMech page', async () => {
    await orderContactMechComponentsPage.clickOnCreateButton();
    orderContactMechUpdatePage = new OrderContactMechUpdatePage();
    expect(await orderContactMechUpdatePage.getPageTitle()).to.eq('hrApp.orderContactMech.home.createOrEditLabel');
    await orderContactMechUpdatePage.cancel();
  });

  it('should create and save OrderContactMeches', async () => {
    const nbButtonsBeforeCreate = await orderContactMechComponentsPage.countDeleteButtons();

    await orderContactMechComponentsPage.clickOnCreateButton();

    await promise.all([
      orderContactMechUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      orderContactMechUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      orderContactMechUpdatePage.orderSelectLastOption(),
      orderContactMechUpdatePage.contactMechSelectLastOption(),
      orderContactMechUpdatePage.contactMechPurposeSelectLastOption(),
    ]);

    expect(await orderContactMechUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await orderContactMechUpdatePage.getThruDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected thruDate value to be equals to 2000-12-31'
    );

    await orderContactMechUpdatePage.save();
    expect(await orderContactMechUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await orderContactMechComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last OrderContactMech', async () => {
    const nbButtonsBeforeDelete = await orderContactMechComponentsPage.countDeleteButtons();
    await orderContactMechComponentsPage.clickOnLastDeleteButton();

    orderContactMechDeleteDialog = new OrderContactMechDeleteDialog();
    expect(await orderContactMechDeleteDialog.getDialogTitle()).to.eq('hrApp.orderContactMech.delete.question');
    await orderContactMechDeleteDialog.clickOnConfirmButton();

    expect(await orderContactMechComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
