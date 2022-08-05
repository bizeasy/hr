import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UomComponentsPage, UomDeleteDialog, UomUpdatePage } from './uom.page-object';

const expect = chai.expect;

describe('Uom e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let uomComponentsPage: UomComponentsPage;
  let uomUpdatePage: UomUpdatePage;
  let uomDeleteDialog: UomDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Uoms', async () => {
    await navBarPage.goToEntity('uom');
    uomComponentsPage = new UomComponentsPage();
    await browser.wait(ec.visibilityOf(uomComponentsPage.title), 5000);
    expect(await uomComponentsPage.getTitle()).to.eq('hrApp.uom.home.title');
    await browser.wait(ec.or(ec.visibilityOf(uomComponentsPage.entities), ec.visibilityOf(uomComponentsPage.noResult)), 1000);
  });

  it('should load create Uom page', async () => {
    await uomComponentsPage.clickOnCreateButton();
    uomUpdatePage = new UomUpdatePage();
    expect(await uomUpdatePage.getPageTitle()).to.eq('hrApp.uom.home.createOrEditLabel');
    await uomUpdatePage.cancel();
  });

  it('should create and save Uoms', async () => {
    const nbButtonsBeforeCreate = await uomComponentsPage.countDeleteButtons();

    await uomComponentsPage.clickOnCreateButton();

    await promise.all([
      uomUpdatePage.setNameInput('name'),
      uomUpdatePage.setDescriptionInput('description'),
      uomUpdatePage.setSequenceNoInput('5'),
      uomUpdatePage.setAbbreviationInput('abbreviation'),
      uomUpdatePage.uomTypeSelectLastOption(),
    ]);

    expect(await uomUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await uomUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await uomUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');
    expect(await uomUpdatePage.getAbbreviationInput()).to.eq('abbreviation', 'Expected Abbreviation value to be equals to abbreviation');

    await uomUpdatePage.save();
    expect(await uomUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await uomComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Uom', async () => {
    const nbButtonsBeforeDelete = await uomComponentsPage.countDeleteButtons();
    await uomComponentsPage.clickOnLastDeleteButton();

    uomDeleteDialog = new UomDeleteDialog();
    expect(await uomDeleteDialog.getDialogTitle()).to.eq('hrApp.uom.delete.question');
    await uomDeleteDialog.clickOnConfirmButton();

    expect(await uomComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
