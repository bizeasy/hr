import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InvoiceComponentsPage, InvoiceDeleteDialog, InvoiceUpdatePage } from './invoice.page-object';

const expect = chai.expect;

describe('Invoice e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let invoiceComponentsPage: InvoiceComponentsPage;
  let invoiceUpdatePage: InvoiceUpdatePage;
  let invoiceDeleteDialog: InvoiceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Invoices', async () => {
    await navBarPage.goToEntity('invoice');
    invoiceComponentsPage = new InvoiceComponentsPage();
    await browser.wait(ec.visibilityOf(invoiceComponentsPage.title), 5000);
    expect(await invoiceComponentsPage.getTitle()).to.eq('hrApp.invoice.home.title');
    await browser.wait(ec.or(ec.visibilityOf(invoiceComponentsPage.entities), ec.visibilityOf(invoiceComponentsPage.noResult)), 1000);
  });

  it('should load create Invoice page', async () => {
    await invoiceComponentsPage.clickOnCreateButton();
    invoiceUpdatePage = new InvoiceUpdatePage();
    expect(await invoiceUpdatePage.getPageTitle()).to.eq('hrApp.invoice.home.createOrEditLabel');
    await invoiceUpdatePage.cancel();
  });

  it('should create and save Invoices', async () => {
    const nbButtonsBeforeCreate = await invoiceComponentsPage.countDeleteButtons();

    await invoiceComponentsPage.clickOnCreateButton();

    await promise.all([
      invoiceUpdatePage.setInvoiceDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      invoiceUpdatePage.setDueDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      invoiceUpdatePage.setPaidDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      invoiceUpdatePage.setInvoiceMessageInput('invoiceMessage'),
      invoiceUpdatePage.setReferenceNumberInput('referenceNumber'),
      invoiceUpdatePage.invoiceTypeSelectLastOption(),
      invoiceUpdatePage.partyIdFromSelectLastOption(),
      invoiceUpdatePage.partyIdToSelectLastOption(),
      invoiceUpdatePage.roleTypeSelectLastOption(),
      invoiceUpdatePage.statusSelectLastOption(),
      invoiceUpdatePage.contactMechSelectLastOption(),
    ]);

    expect(await invoiceUpdatePage.getInvoiceDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected invoiceDate value to be equals to 2000-12-31'
    );
    expect(await invoiceUpdatePage.getDueDateInput()).to.contain('2001-01-01T02:30', 'Expected dueDate value to be equals to 2000-12-31');
    expect(await invoiceUpdatePage.getPaidDateInput()).to.contain('2001-01-01T02:30', 'Expected paidDate value to be equals to 2000-12-31');
    expect(await invoiceUpdatePage.getInvoiceMessageInput()).to.eq(
      'invoiceMessage',
      'Expected InvoiceMessage value to be equals to invoiceMessage'
    );
    expect(await invoiceUpdatePage.getReferenceNumberInput()).to.eq(
      'referenceNumber',
      'Expected ReferenceNumber value to be equals to referenceNumber'
    );

    await invoiceUpdatePage.save();
    expect(await invoiceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await invoiceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Invoice', async () => {
    const nbButtonsBeforeDelete = await invoiceComponentsPage.countDeleteButtons();
    await invoiceComponentsPage.clickOnLastDeleteButton();

    invoiceDeleteDialog = new InvoiceDeleteDialog();
    expect(await invoiceDeleteDialog.getDialogTitle()).to.eq('hrApp.invoice.delete.question');
    await invoiceDeleteDialog.clickOnConfirmButton();

    expect(await invoiceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
