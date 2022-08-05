import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PaymentMethodComponentsPage, PaymentMethodDeleteDialog, PaymentMethodUpdatePage } from './payment-method.page-object';

const expect = chai.expect;

describe('PaymentMethod e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let paymentMethodComponentsPage: PaymentMethodComponentsPage;
  let paymentMethodUpdatePage: PaymentMethodUpdatePage;
  let paymentMethodDeleteDialog: PaymentMethodDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PaymentMethods', async () => {
    await navBarPage.goToEntity('payment-method');
    paymentMethodComponentsPage = new PaymentMethodComponentsPage();
    await browser.wait(ec.visibilityOf(paymentMethodComponentsPage.title), 5000);
    expect(await paymentMethodComponentsPage.getTitle()).to.eq('hrApp.paymentMethod.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(paymentMethodComponentsPage.entities), ec.visibilityOf(paymentMethodComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PaymentMethod page', async () => {
    await paymentMethodComponentsPage.clickOnCreateButton();
    paymentMethodUpdatePage = new PaymentMethodUpdatePage();
    expect(await paymentMethodUpdatePage.getPageTitle()).to.eq('hrApp.paymentMethod.home.createOrEditLabel');
    await paymentMethodUpdatePage.cancel();
  });

  it('should create and save PaymentMethods', async () => {
    const nbButtonsBeforeCreate = await paymentMethodComponentsPage.countDeleteButtons();

    await paymentMethodComponentsPage.clickOnCreateButton();

    await promise.all([
      paymentMethodUpdatePage.setNameInput('name'),
      paymentMethodUpdatePage.setDescriptionInput('description'),
      paymentMethodUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      paymentMethodUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      paymentMethodUpdatePage.paymentMethodTypeSelectLastOption(),
      paymentMethodUpdatePage.partySelectLastOption(),
    ]);

    expect(await paymentMethodUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await paymentMethodUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await paymentMethodUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await paymentMethodUpdatePage.getThruDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected thruDate value to be equals to 2000-12-31'
    );

    await paymentMethodUpdatePage.save();
    expect(await paymentMethodUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await paymentMethodComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PaymentMethod', async () => {
    const nbButtonsBeforeDelete = await paymentMethodComponentsPage.countDeleteButtons();
    await paymentMethodComponentsPage.clickOnLastDeleteButton();

    paymentMethodDeleteDialog = new PaymentMethodDeleteDialog();
    expect(await paymentMethodDeleteDialog.getDialogTitle()).to.eq('hrApp.paymentMethod.delete.question');
    await paymentMethodDeleteDialog.clickOnConfirmButton();

    expect(await paymentMethodComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
