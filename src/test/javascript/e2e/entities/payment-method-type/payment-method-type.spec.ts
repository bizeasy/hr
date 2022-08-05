import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PaymentMethodTypeComponentsPage,
  PaymentMethodTypeDeleteDialog,
  PaymentMethodTypeUpdatePage,
} from './payment-method-type.page-object';

const expect = chai.expect;

describe('PaymentMethodType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paymentMethodTypeComponentsPage: PaymentMethodTypeComponentsPage;
  let paymentMethodTypeUpdatePage: PaymentMethodTypeUpdatePage;
  let paymentMethodTypeDeleteDialog: PaymentMethodTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PaymentMethodTypes', async () => {
    await navBarPage.goToEntity('payment-method-type');
    paymentMethodTypeComponentsPage = new PaymentMethodTypeComponentsPage();
    await browser.wait(ec.visibilityOf(paymentMethodTypeComponentsPage.title), 5000);
    expect(await paymentMethodTypeComponentsPage.getTitle()).to.eq('hrApp.paymentMethodType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(paymentMethodTypeComponentsPage.entities), ec.visibilityOf(paymentMethodTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PaymentMethodType page', async () => {
    await paymentMethodTypeComponentsPage.clickOnCreateButton();
    paymentMethodTypeUpdatePage = new PaymentMethodTypeUpdatePage();
    expect(await paymentMethodTypeUpdatePage.getPageTitle()).to.eq('hrApp.paymentMethodType.home.createOrEditLabel');
    await paymentMethodTypeUpdatePage.cancel();
  });

  it('should create and save PaymentMethodTypes', async () => {
    const nbButtonsBeforeCreate = await paymentMethodTypeComponentsPage.countDeleteButtons();

    await paymentMethodTypeComponentsPage.clickOnCreateButton();

    await promise.all([paymentMethodTypeUpdatePage.setNameInput('name'), paymentMethodTypeUpdatePage.setDescriptionInput('description')]);

    expect(await paymentMethodTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await paymentMethodTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await paymentMethodTypeUpdatePage.save();
    expect(await paymentMethodTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await paymentMethodTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PaymentMethodType', async () => {
    const nbButtonsBeforeDelete = await paymentMethodTypeComponentsPage.countDeleteButtons();
    await paymentMethodTypeComponentsPage.clickOnLastDeleteButton();

    paymentMethodTypeDeleteDialog = new PaymentMethodTypeDeleteDialog();
    expect(await paymentMethodTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.paymentMethodType.delete.question');
    await paymentMethodTypeDeleteDialog.clickOnConfirmButton();

    expect(await paymentMethodTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
