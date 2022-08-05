import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InvoiceItemTypeComponentsPage, InvoiceItemTypeDeleteDialog, InvoiceItemTypeUpdatePage } from './invoice-item-type.page-object';

const expect = chai.expect;

describe('InvoiceItemType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let invoiceItemTypeComponentsPage: InvoiceItemTypeComponentsPage;
  let invoiceItemTypeUpdatePage: InvoiceItemTypeUpdatePage;
  let invoiceItemTypeDeleteDialog: InvoiceItemTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load InvoiceItemTypes', async () => {
    await navBarPage.goToEntity('invoice-item-type');
    invoiceItemTypeComponentsPage = new InvoiceItemTypeComponentsPage();
    await browser.wait(ec.visibilityOf(invoiceItemTypeComponentsPage.title), 5000);
    expect(await invoiceItemTypeComponentsPage.getTitle()).to.eq('hrApp.invoiceItemType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(invoiceItemTypeComponentsPage.entities), ec.visibilityOf(invoiceItemTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create InvoiceItemType page', async () => {
    await invoiceItemTypeComponentsPage.clickOnCreateButton();
    invoiceItemTypeUpdatePage = new InvoiceItemTypeUpdatePage();
    expect(await invoiceItemTypeUpdatePage.getPageTitle()).to.eq('hrApp.invoiceItemType.home.createOrEditLabel');
    await invoiceItemTypeUpdatePage.cancel();
  });

  it('should create and save InvoiceItemTypes', async () => {
    const nbButtonsBeforeCreate = await invoiceItemTypeComponentsPage.countDeleteButtons();

    await invoiceItemTypeComponentsPage.clickOnCreateButton();

    await promise.all([invoiceItemTypeUpdatePage.setNameInput('name'), invoiceItemTypeUpdatePage.setDescriptionInput('description')]);

    expect(await invoiceItemTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await invoiceItemTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await invoiceItemTypeUpdatePage.save();
    expect(await invoiceItemTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await invoiceItemTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last InvoiceItemType', async () => {
    const nbButtonsBeforeDelete = await invoiceItemTypeComponentsPage.countDeleteButtons();
    await invoiceItemTypeComponentsPage.clickOnLastDeleteButton();

    invoiceItemTypeDeleteDialog = new InvoiceItemTypeDeleteDialog();
    expect(await invoiceItemTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.invoiceItemType.delete.question');
    await invoiceItemTypeDeleteDialog.clickOnConfirmButton();

    expect(await invoiceItemTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
