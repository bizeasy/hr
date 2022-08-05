import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InvoiceTypeComponentsPage, InvoiceTypeDeleteDialog, InvoiceTypeUpdatePage } from './invoice-type.page-object';

const expect = chai.expect;

describe('InvoiceType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let invoiceTypeComponentsPage: InvoiceTypeComponentsPage;
  let invoiceTypeUpdatePage: InvoiceTypeUpdatePage;
  let invoiceTypeDeleteDialog: InvoiceTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load InvoiceTypes', async () => {
    await navBarPage.goToEntity('invoice-type');
    invoiceTypeComponentsPage = new InvoiceTypeComponentsPage();
    await browser.wait(ec.visibilityOf(invoiceTypeComponentsPage.title), 5000);
    expect(await invoiceTypeComponentsPage.getTitle()).to.eq('hrApp.invoiceType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(invoiceTypeComponentsPage.entities), ec.visibilityOf(invoiceTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create InvoiceType page', async () => {
    await invoiceTypeComponentsPage.clickOnCreateButton();
    invoiceTypeUpdatePage = new InvoiceTypeUpdatePage();
    expect(await invoiceTypeUpdatePage.getPageTitle()).to.eq('hrApp.invoiceType.home.createOrEditLabel');
    await invoiceTypeUpdatePage.cancel();
  });

  it('should create and save InvoiceTypes', async () => {
    const nbButtonsBeforeCreate = await invoiceTypeComponentsPage.countDeleteButtons();

    await invoiceTypeComponentsPage.clickOnCreateButton();

    await promise.all([invoiceTypeUpdatePage.setNameInput('name'), invoiceTypeUpdatePage.setDescriptionInput('description')]);

    expect(await invoiceTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await invoiceTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await invoiceTypeUpdatePage.save();
    expect(await invoiceTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await invoiceTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last InvoiceType', async () => {
    const nbButtonsBeforeDelete = await invoiceTypeComponentsPage.countDeleteButtons();
    await invoiceTypeComponentsPage.clickOnLastDeleteButton();

    invoiceTypeDeleteDialog = new InvoiceTypeDeleteDialog();
    expect(await invoiceTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.invoiceType.delete.question');
    await invoiceTypeDeleteDialog.clickOnConfirmButton();

    expect(await invoiceTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
