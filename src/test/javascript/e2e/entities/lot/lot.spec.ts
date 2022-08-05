import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LotComponentsPage, LotDeleteDialog, LotUpdatePage } from './lot.page-object';

const expect = chai.expect;

describe('Lot e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let lotComponentsPage: LotComponentsPage;
  let lotUpdatePage: LotUpdatePage;
  let lotDeleteDialog: LotDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Lots', async () => {
    await navBarPage.goToEntity('lot');
    lotComponentsPage = new LotComponentsPage();
    await browser.wait(ec.visibilityOf(lotComponentsPage.title), 5000);
    expect(await lotComponentsPage.getTitle()).to.eq('hrApp.lot.home.title');
    await browser.wait(ec.or(ec.visibilityOf(lotComponentsPage.entities), ec.visibilityOf(lotComponentsPage.noResult)), 1000);
  });

  it('should load create Lot page', async () => {
    await lotComponentsPage.clickOnCreateButton();
    lotUpdatePage = new LotUpdatePage();
    expect(await lotUpdatePage.getPageTitle()).to.eq('hrApp.lot.home.createOrEditLabel');
    await lotUpdatePage.cancel();
  });

  it('should create and save Lots', async () => {
    const nbButtonsBeforeCreate = await lotComponentsPage.countDeleteButtons();

    await lotComponentsPage.clickOnCreateButton();

    await promise.all([
      lotUpdatePage.setCreationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      lotUpdatePage.setQuantityInput('5'),
      lotUpdatePage.setExpirationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      lotUpdatePage.setRetestDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
    ]);

    expect(await lotUpdatePage.getCreationDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected creationDate value to be equals to 2000-12-31'
    );
    expect(await lotUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await lotUpdatePage.getExpirationDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected expirationDate value to be equals to 2000-12-31'
    );
    expect(await lotUpdatePage.getRetestDateInput()).to.contain('2001-01-01T02:30', 'Expected retestDate value to be equals to 2000-12-31');

    await lotUpdatePage.save();
    expect(await lotUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await lotComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Lot', async () => {
    const nbButtonsBeforeDelete = await lotComponentsPage.countDeleteButtons();
    await lotComponentsPage.clickOnLastDeleteButton();

    lotDeleteDialog = new LotDeleteDialog();
    expect(await lotDeleteDialog.getDialogTitle()).to.eq('hrApp.lot.delete.question');
    await lotDeleteDialog.clickOnConfirmButton();

    expect(await lotComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
