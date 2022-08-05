import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InvoiceItemComponentsPage, InvoiceItemDeleteDialog, InvoiceItemUpdatePage } from './invoice-item.page-object';

const expect = chai.expect;

describe('InvoiceItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let invoiceItemComponentsPage: InvoiceItemComponentsPage;
  let invoiceItemUpdatePage: InvoiceItemUpdatePage;
  let invoiceItemDeleteDialog: InvoiceItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load InvoiceItems', async () => {
    await navBarPage.goToEntity('invoice-item');
    invoiceItemComponentsPage = new InvoiceItemComponentsPage();
    await browser.wait(ec.visibilityOf(invoiceItemComponentsPage.title), 5000);
    expect(await invoiceItemComponentsPage.getTitle()).to.eq('hrApp.invoiceItem.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(invoiceItemComponentsPage.entities), ec.visibilityOf(invoiceItemComponentsPage.noResult)),
      1000
    );
  });

  it('should load create InvoiceItem page', async () => {
    await invoiceItemComponentsPage.clickOnCreateButton();
    invoiceItemUpdatePage = new InvoiceItemUpdatePage();
    expect(await invoiceItemUpdatePage.getPageTitle()).to.eq('hrApp.invoiceItem.home.createOrEditLabel');
    await invoiceItemUpdatePage.cancel();
  });

  it('should create and save InvoiceItems', async () => {
    const nbButtonsBeforeCreate = await invoiceItemComponentsPage.countDeleteButtons();

    await invoiceItemComponentsPage.clickOnCreateButton();

    await promise.all([
      invoiceItemUpdatePage.setSequenceNoInput('5'),
      invoiceItemUpdatePage.setQuantityInput('5'),
      invoiceItemUpdatePage.setAmountInput('5'),
      invoiceItemUpdatePage.invoiceSelectLastOption(),
      invoiceItemUpdatePage.invoiceItemTypeSelectLastOption(),
      invoiceItemUpdatePage.fromInventoryItemSelectLastOption(),
      invoiceItemUpdatePage.productSelectLastOption(),
      invoiceItemUpdatePage.taxAuthRateSelectLastOption(),
    ]);

    expect(await invoiceItemUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');
    expect(await invoiceItemUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await invoiceItemUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');

    await invoiceItemUpdatePage.save();
    expect(await invoiceItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await invoiceItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last InvoiceItem', async () => {
    const nbButtonsBeforeDelete = await invoiceItemComponentsPage.countDeleteButtons();
    await invoiceItemComponentsPage.clickOnLastDeleteButton();

    invoiceItemDeleteDialog = new InvoiceItemDeleteDialog();
    expect(await invoiceItemDeleteDialog.getDialogTitle()).to.eq('hrApp.invoiceItem.delete.question');
    await invoiceItemDeleteDialog.clickOnConfirmButton();

    expect(await invoiceItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
