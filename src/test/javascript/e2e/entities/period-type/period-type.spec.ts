import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PeriodTypeComponentsPage, PeriodTypeDeleteDialog, PeriodTypeUpdatePage } from './period-type.page-object';

const expect = chai.expect;

describe('PeriodType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let periodTypeComponentsPage: PeriodTypeComponentsPage;
  let periodTypeUpdatePage: PeriodTypeUpdatePage;
  let periodTypeDeleteDialog: PeriodTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PeriodTypes', async () => {
    await navBarPage.goToEntity('period-type');
    periodTypeComponentsPage = new PeriodTypeComponentsPage();
    await browser.wait(ec.visibilityOf(periodTypeComponentsPage.title), 5000);
    expect(await periodTypeComponentsPage.getTitle()).to.eq('hrApp.periodType.home.title');
    await browser.wait(ec.or(ec.visibilityOf(periodTypeComponentsPage.entities), ec.visibilityOf(periodTypeComponentsPage.noResult)), 1000);
  });

  it('should load create PeriodType page', async () => {
    await periodTypeComponentsPage.clickOnCreateButton();
    periodTypeUpdatePage = new PeriodTypeUpdatePage();
    expect(await periodTypeUpdatePage.getPageTitle()).to.eq('hrApp.periodType.home.createOrEditLabel');
    await periodTypeUpdatePage.cancel();
  });

  it('should create and save PeriodTypes', async () => {
    const nbButtonsBeforeCreate = await periodTypeComponentsPage.countDeleteButtons();

    await periodTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      periodTypeUpdatePage.setNameInput('name'),
      periodTypeUpdatePage.setDescriptionInput('description'),
      periodTypeUpdatePage.setPeriodLengthInput('5'),
      periodTypeUpdatePage.uomSelectLastOption(),
    ]);

    expect(await periodTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await periodTypeUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await periodTypeUpdatePage.getPeriodLengthInput()).to.eq('5', 'Expected periodLength value to be equals to 5');

    await periodTypeUpdatePage.save();
    expect(await periodTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await periodTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PeriodType', async () => {
    const nbButtonsBeforeDelete = await periodTypeComponentsPage.countDeleteButtons();
    await periodTypeComponentsPage.clickOnLastDeleteButton();

    periodTypeDeleteDialog = new PeriodTypeDeleteDialog();
    expect(await periodTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.periodType.delete.question');
    await periodTypeDeleteDialog.clickOnConfirmButton();

    expect(await periodTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
