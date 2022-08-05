import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PaymentComponentsPage, PaymentDeleteDialog, PaymentUpdatePage } from './payment.page-object';

const expect = chai.expect;

describe('Payment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paymentComponentsPage: PaymentComponentsPage;
  let paymentUpdatePage: PaymentUpdatePage;
  let paymentDeleteDialog: PaymentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Payments', async () => {
    await navBarPage.goToEntity('payment');
    paymentComponentsPage = new PaymentComponentsPage();
    await browser.wait(ec.visibilityOf(paymentComponentsPage.title), 5000);
    expect(await paymentComponentsPage.getTitle()).to.eq('hrApp.payment.home.title');
    await browser.wait(ec.or(ec.visibilityOf(paymentComponentsPage.entities), ec.visibilityOf(paymentComponentsPage.noResult)), 1000);
  });

  it('should load create Payment page', async () => {
    await paymentComponentsPage.clickOnCreateButton();
    paymentUpdatePage = new PaymentUpdatePage();
    expect(await paymentUpdatePage.getPageTitle()).to.eq('hrApp.payment.home.createOrEditLabel');
    await paymentUpdatePage.cancel();
  });

  it('should create and save Payments', async () => {
    const nbButtonsBeforeCreate = await paymentComponentsPage.countDeleteButtons();

    await paymentComponentsPage.clickOnCreateButton();

    await promise.all([
      paymentUpdatePage.setEffectiveDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      paymentUpdatePage.setPaymentDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      paymentUpdatePage.setPaymentRefNumberInput('paymentRefNumber'),
      paymentUpdatePage.setAmountInput('5'),
      paymentUpdatePage.setPaymentStatusInput('paymentStatus'),
      paymentUpdatePage.setMihpayIdInput('mihpayId'),
      paymentUpdatePage.setEmailInput('email'),
      paymentUpdatePage.setPhoneInput('phone'),
      paymentUpdatePage.setProductInfoInput('productInfo'),
      paymentUpdatePage.setTxnIdInput('txnId'),
      paymentUpdatePage.setActualAmountInput('5'),
      paymentUpdatePage.paymentTypeSelectLastOption(),
      paymentUpdatePage.paymentMethodTypeSelectLastOption(),
      paymentUpdatePage.statusSelectLastOption(),
      paymentUpdatePage.paymentMethodSelectLastOption(),
      paymentUpdatePage.paymentGatewayResponseSelectLastOption(),
      paymentUpdatePage.partyIdFromSelectLastOption(),
      paymentUpdatePage.partyIdToSelectLastOption(),
      paymentUpdatePage.roleTypeSelectLastOption(),
      paymentUpdatePage.currencyUomSelectLastOption(),
    ]);

    expect(await paymentUpdatePage.getEffectiveDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected effectiveDate value to be equals to 2000-12-31'
    );
    expect(await paymentUpdatePage.getPaymentDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected paymentDate value to be equals to 2000-12-31'
    );
    expect(await paymentUpdatePage.getPaymentRefNumberInput()).to.eq(
      'paymentRefNumber',
      'Expected PaymentRefNumber value to be equals to paymentRefNumber'
    );
    expect(await paymentUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
    expect(await paymentUpdatePage.getPaymentStatusInput()).to.eq(
      'paymentStatus',
      'Expected PaymentStatus value to be equals to paymentStatus'
    );
    expect(await paymentUpdatePage.getMihpayIdInput()).to.eq('mihpayId', 'Expected MihpayId value to be equals to mihpayId');
    expect(await paymentUpdatePage.getEmailInput()).to.eq('email', 'Expected Email value to be equals to email');
    expect(await paymentUpdatePage.getPhoneInput()).to.eq('phone', 'Expected Phone value to be equals to phone');
    expect(await paymentUpdatePage.getProductInfoInput()).to.eq('productInfo', 'Expected ProductInfo value to be equals to productInfo');
    expect(await paymentUpdatePage.getTxnIdInput()).to.eq('txnId', 'Expected TxnId value to be equals to txnId');
    expect(await paymentUpdatePage.getActualAmountInput()).to.eq('5', 'Expected actualAmount value to be equals to 5');

    await paymentUpdatePage.save();
    expect(await paymentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await paymentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Payment', async () => {
    const nbButtonsBeforeDelete = await paymentComponentsPage.countDeleteButtons();
    await paymentComponentsPage.clickOnLastDeleteButton();

    paymentDeleteDialog = new PaymentDeleteDialog();
    expect(await paymentDeleteDialog.getDialogTitle()).to.eq('hrApp.payment.delete.question');
    await paymentDeleteDialog.clickOnConfirmButton();

    expect(await paymentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
