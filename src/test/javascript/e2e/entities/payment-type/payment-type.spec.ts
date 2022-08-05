import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PaymentTypeComponentsPage, PaymentTypeDeleteDialog, PaymentTypeUpdatePage } from './payment-type.page-object';

const expect = chai.expect;

describe('PaymentType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paymentTypeComponentsPage: PaymentTypeComponentsPage;
  let paymentTypeUpdatePage: PaymentTypeUpdatePage;
  let paymentTypeDeleteDialog: PaymentTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PaymentTypes', async () => {
    await navBarPage.goToEntity('payment-type');
    paymentTypeComponentsPage = new PaymentTypeComponentsPage();
    await browser.wait(ec.visibilityOf(paymentTypeComponentsPage.title), 5000);
    expect(await paymentTypeComponentsPage.getTitle()).to.eq('hrApp.paymentType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(paymentTypeComponentsPage.entities), ec.visibilityOf(paymentTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PaymentType page', async () => {
    await paymentTypeComponentsPage.clickOnCreateButton();
    paymentTypeUpdatePage = new PaymentTypeUpdatePage();
    expect(await paymentTypeUpdatePage.getPageTitle()).to.eq('hrApp.paymentType.home.createOrEditLabel');
    await paymentTypeUpdatePage.cancel();
  });

  it('should create and save PaymentTypes', async () => {
    const nbButtonsBeforeCreate = await paymentTypeComponentsPage.countDeleteButtons();

    await paymentTypeComponentsPage.clickOnCreateButton();

    await promise.all([paymentTypeUpdatePage.setNameInput('name'), paymentTypeUpdatePage.setDescriptionInput('description')]);

    expect(await paymentTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await paymentTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await paymentTypeUpdatePage.save();
    expect(await paymentTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await paymentTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PaymentType', async () => {
    const nbButtonsBeforeDelete = await paymentTypeComponentsPage.countDeleteButtons();
    await paymentTypeComponentsPage.clickOnLastDeleteButton();

    paymentTypeDeleteDialog = new PaymentTypeDeleteDialog();
    expect(await paymentTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.paymentType.delete.question');
    await paymentTypeDeleteDialog.clickOnConfirmButton();

    expect(await paymentTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
