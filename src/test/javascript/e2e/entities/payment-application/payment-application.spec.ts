import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PaymentApplicationComponentsPage,
  PaymentApplicationDeleteDialog,
  PaymentApplicationUpdatePage,
} from './payment-application.page-object';

const expect = chai.expect;

describe('PaymentApplication e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paymentApplicationComponentsPage: PaymentApplicationComponentsPage;
  let paymentApplicationUpdatePage: PaymentApplicationUpdatePage;
  let paymentApplicationDeleteDialog: PaymentApplicationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PaymentApplications', async () => {
    await navBarPage.goToEntity('payment-application');
    paymentApplicationComponentsPage = new PaymentApplicationComponentsPage();
    await browser.wait(ec.visibilityOf(paymentApplicationComponentsPage.title), 5000);
    expect(await paymentApplicationComponentsPage.getTitle()).to.eq('hrApp.paymentApplication.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(paymentApplicationComponentsPage.entities), ec.visibilityOf(paymentApplicationComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PaymentApplication page', async () => {
    await paymentApplicationComponentsPage.clickOnCreateButton();
    paymentApplicationUpdatePage = new PaymentApplicationUpdatePage();
    expect(await paymentApplicationUpdatePage.getPageTitle()).to.eq('hrApp.paymentApplication.home.createOrEditLabel');
    await paymentApplicationUpdatePage.cancel();
  });

  it('should create and save PaymentApplications', async () => {
    const nbButtonsBeforeCreate = await paymentApplicationComponentsPage.countDeleteButtons();

    await paymentApplicationComponentsPage.clickOnCreateButton();

    await promise.all([
      paymentApplicationUpdatePage.setAmountAppliedInput('5'),
      paymentApplicationUpdatePage.paymentSelectLastOption(),
      paymentApplicationUpdatePage.invoiceSelectLastOption(),
      paymentApplicationUpdatePage.invoiceItemSelectLastOption(),
      paymentApplicationUpdatePage.orderSelectLastOption(),
      paymentApplicationUpdatePage.orderItemSelectLastOption(),
      paymentApplicationUpdatePage.toPaymentSelectLastOption(),
    ]);

    expect(await paymentApplicationUpdatePage.getAmountAppliedInput()).to.eq('5', 'Expected amountApplied value to be equals to 5');

    await paymentApplicationUpdatePage.save();
    expect(await paymentApplicationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await paymentApplicationComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PaymentApplication', async () => {
    const nbButtonsBeforeDelete = await paymentApplicationComponentsPage.countDeleteButtons();
    await paymentApplicationComponentsPage.clickOnLastDeleteButton();

    paymentApplicationDeleteDialog = new PaymentApplicationDeleteDialog();
    expect(await paymentApplicationDeleteDialog.getDialogTitle()).to.eq('hrApp.paymentApplication.delete.question');
    await paymentApplicationDeleteDialog.clickOnConfirmButton();

    expect(await paymentApplicationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
