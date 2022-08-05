import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SalesChannelComponentsPage, SalesChannelDeleteDialog, SalesChannelUpdatePage } from './sales-channel.page-object';

const expect = chai.expect;

describe('SalesChannel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let salesChannelComponentsPage: SalesChannelComponentsPage;
  let salesChannelUpdatePage: SalesChannelUpdatePage;
  let salesChannelDeleteDialog: SalesChannelDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SalesChannels', async () => {
    await navBarPage.goToEntity('sales-channel');
    salesChannelComponentsPage = new SalesChannelComponentsPage();
    await browser.wait(ec.visibilityOf(salesChannelComponentsPage.title), 5000);
    expect(await salesChannelComponentsPage.getTitle()).to.eq('hrApp.salesChannel.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(salesChannelComponentsPage.entities), ec.visibilityOf(salesChannelComponentsPage.noResult)),
      1000
    );
  });

  it('should load create SalesChannel page', async () => {
    await salesChannelComponentsPage.clickOnCreateButton();
    salesChannelUpdatePage = new SalesChannelUpdatePage();
    expect(await salesChannelUpdatePage.getPageTitle()).to.eq('hrApp.salesChannel.home.createOrEditLabel');
    await salesChannelUpdatePage.cancel();
  });

  it('should create and save SalesChannels', async () => {
    const nbButtonsBeforeCreate = await salesChannelComponentsPage.countDeleteButtons();

    await salesChannelComponentsPage.clickOnCreateButton();

    await promise.all([
      salesChannelUpdatePage.setNameInput('name'),
      salesChannelUpdatePage.setDescriptionInput('description'),
      salesChannelUpdatePage.setSequenceNoInput('5'),
    ]);

    expect(await salesChannelUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await salesChannelUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );
    expect(await salesChannelUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');

    await salesChannelUpdatePage.save();
    expect(await salesChannelUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await salesChannelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last SalesChannel', async () => {
    const nbButtonsBeforeDelete = await salesChannelComponentsPage.countDeleteButtons();
    await salesChannelComponentsPage.clickOnLastDeleteButton();

    salesChannelDeleteDialog = new SalesChannelDeleteDialog();
    expect(await salesChannelDeleteDialog.getDialogTitle()).to.eq('hrApp.salesChannel.delete.question');
    await salesChannelDeleteDialog.clickOnConfirmButton();

    expect(await salesChannelComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
