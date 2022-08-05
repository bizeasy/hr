import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DeductionComponentsPage, DeductionDeleteDialog, DeductionUpdatePage } from './deduction.page-object';

const expect = chai.expect;

describe('Deduction e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let deductionComponentsPage: DeductionComponentsPage;
  let deductionUpdatePage: DeductionUpdatePage;
  let deductionDeleteDialog: DeductionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Deductions', async () => {
    await navBarPage.goToEntity('deduction');
    deductionComponentsPage = new DeductionComponentsPage();
    await browser.wait(ec.visibilityOf(deductionComponentsPage.title), 5000);
    expect(await deductionComponentsPage.getTitle()).to.eq('hrApp.deduction.home.title');
    await browser.wait(ec.or(ec.visibilityOf(deductionComponentsPage.entities), ec.visibilityOf(deductionComponentsPage.noResult)), 1000);
  });

  it('should load create Deduction page', async () => {
    await deductionComponentsPage.clickOnCreateButton();
    deductionUpdatePage = new DeductionUpdatePage();
    expect(await deductionUpdatePage.getPageTitle()).to.eq('hrApp.deduction.home.createOrEditLabel');
    await deductionUpdatePage.cancel();
  });

  it('should create and save Deductions', async () => {
    const nbButtonsBeforeCreate = await deductionComponentsPage.countDeleteButtons();

    await deductionComponentsPage.clickOnCreateButton();

    await promise.all([deductionUpdatePage.setAmountInput('5'), deductionUpdatePage.typeSelectLastOption()]);

    expect(await deductionUpdatePage.getAmountInput()).to.eq('5', 'Expected amount value to be equals to 5');

    await deductionUpdatePage.save();
    expect(await deductionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await deductionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Deduction', async () => {
    const nbButtonsBeforeDelete = await deductionComponentsPage.countDeleteButtons();
    await deductionComponentsPage.clickOnLastDeleteButton();

    deductionDeleteDialog = new DeductionDeleteDialog();
    expect(await deductionDeleteDialog.getDialogTitle()).to.eq('hrApp.deduction.delete.question');
    await deductionDeleteDialog.clickOnConfirmButton();

    expect(await deductionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
