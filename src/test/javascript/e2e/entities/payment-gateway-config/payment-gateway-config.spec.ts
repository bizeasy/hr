import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PaymentGatewayConfigComponentsPage,
  PaymentGatewayConfigDeleteDialog,
  PaymentGatewayConfigUpdatePage,
} from './payment-gateway-config.page-object';

const expect = chai.expect;

describe('PaymentGatewayConfig e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paymentGatewayConfigComponentsPage: PaymentGatewayConfigComponentsPage;
  let paymentGatewayConfigUpdatePage: PaymentGatewayConfigUpdatePage;
  let paymentGatewayConfigDeleteDialog: PaymentGatewayConfigDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PaymentGatewayConfigs', async () => {
    await navBarPage.goToEntity('payment-gateway-config');
    paymentGatewayConfigComponentsPage = new PaymentGatewayConfigComponentsPage();
    await browser.wait(ec.visibilityOf(paymentGatewayConfigComponentsPage.title), 5000);
    expect(await paymentGatewayConfigComponentsPage.getTitle()).to.eq('hrApp.paymentGatewayConfig.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(paymentGatewayConfigComponentsPage.entities), ec.visibilityOf(paymentGatewayConfigComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PaymentGatewayConfig page', async () => {
    await paymentGatewayConfigComponentsPage.clickOnCreateButton();
    paymentGatewayConfigUpdatePage = new PaymentGatewayConfigUpdatePage();
    expect(await paymentGatewayConfigUpdatePage.getPageTitle()).to.eq('hrApp.paymentGatewayConfig.home.createOrEditLabel');
    await paymentGatewayConfigUpdatePage.cancel();
  });

  it('should create and save PaymentGatewayConfigs', async () => {
    const nbButtonsBeforeCreate = await paymentGatewayConfigComponentsPage.countDeleteButtons();

    await paymentGatewayConfigComponentsPage.clickOnCreateButton();

    await promise.all([
      paymentGatewayConfigUpdatePage.setNameInput('name'),
      paymentGatewayConfigUpdatePage.setAuthUrlInput('authUrl'),
      paymentGatewayConfigUpdatePage.setReleaseUrlInput('releaseUrl'),
      paymentGatewayConfigUpdatePage.setRefundUrlInput('refundUrl'),
    ]);

    expect(await paymentGatewayConfigUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await paymentGatewayConfigUpdatePage.getAuthUrlInput()).to.eq('authUrl', 'Expected AuthUrl value to be equals to authUrl');
    expect(await paymentGatewayConfigUpdatePage.getReleaseUrlInput()).to.eq(
      'releaseUrl',
      'Expected ReleaseUrl value to be equals to releaseUrl'
    );
    expect(await paymentGatewayConfigUpdatePage.getRefundUrlInput()).to.eq(
      'refundUrl',
      'Expected RefundUrl value to be equals to refundUrl'
    );

    await paymentGatewayConfigUpdatePage.save();
    expect(await paymentGatewayConfigUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await paymentGatewayConfigComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PaymentGatewayConfig', async () => {
    const nbButtonsBeforeDelete = await paymentGatewayConfigComponentsPage.countDeleteButtons();
    await paymentGatewayConfigComponentsPage.clickOnLastDeleteButton();

    paymentGatewayConfigDeleteDialog = new PaymentGatewayConfigDeleteDialog();
    expect(await paymentGatewayConfigDeleteDialog.getDialogTitle()).to.eq('hrApp.paymentGatewayConfig.delete.question');
    await paymentGatewayConfigDeleteDialog.clickOnConfirmButton();

    expect(await paymentGatewayConfigComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
