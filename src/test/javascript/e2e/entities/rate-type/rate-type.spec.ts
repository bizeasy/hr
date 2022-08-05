import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { RateTypeComponentsPage, RateTypeDeleteDialog, RateTypeUpdatePage } from './rate-type.page-object';

const expect = chai.expect;

describe('RateType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let rateTypeComponentsPage: RateTypeComponentsPage;
  let rateTypeUpdatePage: RateTypeUpdatePage;
  let rateTypeDeleteDialog: RateTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load RateTypes', async () => {
    await navBarPage.goToEntity('rate-type');
    rateTypeComponentsPage = new RateTypeComponentsPage();
    await browser.wait(ec.visibilityOf(rateTypeComponentsPage.title), 5000);
    expect(await rateTypeComponentsPage.getTitle()).to.eq('hrApp.rateType.home.title');
    await browser.wait(ec.or(ec.visibilityOf(rateTypeComponentsPage.entities), ec.visibilityOf(rateTypeComponentsPage.noResult)), 1000);
  });

  it('should load create RateType page', async () => {
    await rateTypeComponentsPage.clickOnCreateButton();
    rateTypeUpdatePage = new RateTypeUpdatePage();
    expect(await rateTypeUpdatePage.getPageTitle()).to.eq('hrApp.rateType.home.createOrEditLabel');
    await rateTypeUpdatePage.cancel();
  });

  it('should create and save RateTypes', async () => {
    const nbButtonsBeforeCreate = await rateTypeComponentsPage.countDeleteButtons();

    await rateTypeComponentsPage.clickOnCreateButton();

    await promise.all([rateTypeUpdatePage.setNameInput('name'), rateTypeUpdatePage.setDescriptionInput('description')]);

    expect(await rateTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await rateTypeUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

    await rateTypeUpdatePage.save();
    expect(await rateTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await rateTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last RateType', async () => {
    const nbButtonsBeforeDelete = await rateTypeComponentsPage.countDeleteButtons();
    await rateTypeComponentsPage.clickOnLastDeleteButton();

    rateTypeDeleteDialog = new RateTypeDeleteDialog();
    expect(await rateTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.rateType.delete.question');
    await rateTypeDeleteDialog.clickOnConfirmButton();

    expect(await rateTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
