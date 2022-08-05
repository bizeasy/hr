import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DeductionTypeComponentsPage, DeductionTypeDeleteDialog, DeductionTypeUpdatePage } from './deduction-type.page-object';

const expect = chai.expect;

describe('DeductionType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let deductionTypeComponentsPage: DeductionTypeComponentsPage;
  let deductionTypeUpdatePage: DeductionTypeUpdatePage;
  let deductionTypeDeleteDialog: DeductionTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load DeductionTypes', async () => {
    await navBarPage.goToEntity('deduction-type');
    deductionTypeComponentsPage = new DeductionTypeComponentsPage();
    await browser.wait(ec.visibilityOf(deductionTypeComponentsPage.title), 5000);
    expect(await deductionTypeComponentsPage.getTitle()).to.eq('hrApp.deductionType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(deductionTypeComponentsPage.entities), ec.visibilityOf(deductionTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create DeductionType page', async () => {
    await deductionTypeComponentsPage.clickOnCreateButton();
    deductionTypeUpdatePage = new DeductionTypeUpdatePage();
    expect(await deductionTypeUpdatePage.getPageTitle()).to.eq('hrApp.deductionType.home.createOrEditLabel');
    await deductionTypeUpdatePage.cancel();
  });

  it('should create and save DeductionTypes', async () => {
    const nbButtonsBeforeCreate = await deductionTypeComponentsPage.countDeleteButtons();

    await deductionTypeComponentsPage.clickOnCreateButton();

    await promise.all([deductionTypeUpdatePage.setNameInput('name'), deductionTypeUpdatePage.setDescriptionInput('description')]);

    expect(await deductionTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await deductionTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await deductionTypeUpdatePage.save();
    expect(await deductionTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await deductionTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last DeductionType', async () => {
    const nbButtonsBeforeDelete = await deductionTypeComponentsPage.countDeleteButtons();
    await deductionTypeComponentsPage.clickOnLastDeleteButton();

    deductionTypeDeleteDialog = new DeductionTypeDeleteDialog();
    expect(await deductionTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.deductionType.delete.question');
    await deductionTypeDeleteDialog.clickOnConfirmButton();

    expect(await deductionTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
