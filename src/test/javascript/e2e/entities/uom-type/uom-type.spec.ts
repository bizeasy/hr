import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UomTypeComponentsPage, UomTypeDeleteDialog, UomTypeUpdatePage } from './uom-type.page-object';

const expect = chai.expect;

describe('UomType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let uomTypeComponentsPage: UomTypeComponentsPage;
  let uomTypeUpdatePage: UomTypeUpdatePage;
  let uomTypeDeleteDialog: UomTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UomTypes', async () => {
    await navBarPage.goToEntity('uom-type');
    uomTypeComponentsPage = new UomTypeComponentsPage();
    await browser.wait(ec.visibilityOf(uomTypeComponentsPage.title), 5000);
    expect(await uomTypeComponentsPage.getTitle()).to.eq('hrApp.uomType.home.title');
    await browser.wait(ec.or(ec.visibilityOf(uomTypeComponentsPage.entities), ec.visibilityOf(uomTypeComponentsPage.noResult)), 1000);
  });

  it('should load create UomType page', async () => {
    await uomTypeComponentsPage.clickOnCreateButton();
    uomTypeUpdatePage = new UomTypeUpdatePage();
    expect(await uomTypeUpdatePage.getPageTitle()).to.eq('hrApp.uomType.home.createOrEditLabel');
    await uomTypeUpdatePage.cancel();
  });

  it('should create and save UomTypes', async () => {
    const nbButtonsBeforeCreate = await uomTypeComponentsPage.countDeleteButtons();

    await uomTypeComponentsPage.clickOnCreateButton();

    await promise.all([uomTypeUpdatePage.setNameInput('name'), uomTypeUpdatePage.setDescriptionInput('description')]);

    expect(await uomTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await uomTypeUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

    await uomTypeUpdatePage.save();
    expect(await uomTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await uomTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last UomType', async () => {
    const nbButtonsBeforeDelete = await uomTypeComponentsPage.countDeleteButtons();
    await uomTypeComponentsPage.clickOnLastDeleteButton();

    uomTypeDeleteDialog = new UomTypeDeleteDialog();
    expect(await uomTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.uomType.delete.question');
    await uomTypeDeleteDialog.clickOnConfirmButton();

    expect(await uomTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
