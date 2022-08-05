import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PaymentGatewayResponseComponentsPage,
  PaymentGatewayResponseDeleteDialog,
  PaymentGatewayResponseUpdatePage,
} from './payment-gateway-response.page-object';

const expect = chai.expect;

describe('PaymentGatewayResponse e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paymentGatewayResponseComponentsPage: PaymentGatewayResponseComponentsPage;
  let paymentGatewayResponseUpdatePage: PaymentGatewayResponseUpdatePage;
  let paymentGatewayResponseDeleteDialog: PaymentGatewayResponseDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PaymentGatewayResponses', async () => {
    await navBarPage.goToEntity('payment-gateway-response');
    paymentGatewayResponseComponentsPage = new PaymentGatewayResponseComponentsPage();
    await browser.wait(ec.visibilityOf(paymentGatewayResponseComponentsPage.title), 5000);
    expect(await paymentGatewayResponseComponentsPage.getTitle()).to.eq('hrApp.paymentGatewayResponse.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(paymentGatewayResponseComponentsPage.entities), ec.visibilityOf(paymentGatewayResponseComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PaymentGatewayResponse page', async () => {
    await paymentGatewayResponseComponentsPage.clickOnCreateButton();
    paymentGatewayResponseUpdatePage = new PaymentGatewayResponseUpdatePage();
    expect(await paymentGatewayResponseUpdatePage.getPageTitle()).to.eq('hrApp.paymentGatewayResponse.home.createOrEditLabel');
    await paymentGatewayResponseUpdatePage.cancel();
  });

  it('should create and save PaymentGatewayResponses', async () => {
    const nbButtonsBeforeCreate = await paymentGatewayResponseComponentsPage.countDeleteButtons();

    await paymentGatewayResponseComponentsPage.clickOnCreateButton();

    await promise.all([
      paymentGatewayResponseUpdatePage.setAmountInput('5'),
      paymentGatewayResponseUpdatePage.setReferenceNumberInput('referenceNumber'),
      paymentGatewayResponseUpdatePage.setGatewayMessageInput('gatewayMessage'),
      paymentGatewayResponseUpdatePage.paymentMethodTypeSelectLastOption(),
    ]);

    expect(await paymentGatewayResponseUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');
    expect(await paymentGatewayResponseUpdatePage.getReferenceNumberInput()).to.eq(
      'referenceNumber',
      'Expected ReferenceNumber value to be equals to referenceNumber'
    );
    expect(await paymentGatewayResponseUpdatePage.getGatewayMessageInput()).to.eq(
      'gatewayMessage',
      'Expected GatewayMessage value to be equals to gatewayMessage'
    );

    await paymentGatewayResponseUpdatePage.save();
    expect(await paymentGatewayResponseUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await paymentGatewayResponseComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PaymentGatewayResponse', async () => {
    const nbButtonsBeforeDelete = await paymentGatewayResponseComponentsPage.countDeleteButtons();
    await paymentGatewayResponseComponentsPage.clickOnLastDeleteButton();

    paymentGatewayResponseDeleteDialog = new PaymentGatewayResponseDeleteDialog();
    expect(await paymentGatewayResponseDeleteDialog.getDialogTitle()).to.eq('hrApp.paymentGatewayResponse.delete.question');
    await paymentGatewayResponseDeleteDialog.clickOnConfirmButton();

    expect(await paymentGatewayResponseComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
