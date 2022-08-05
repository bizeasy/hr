import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ReasonComponentsPage, ReasonDeleteDialog, ReasonUpdatePage } from './reason.page-object';

const expect = chai.expect;

describe('Reason e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let reasonComponentsPage: ReasonComponentsPage;
  let reasonUpdatePage: ReasonUpdatePage;
  let reasonDeleteDialog: ReasonDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Reasons', async () => {
    await navBarPage.goToEntity('reason');
    reasonComponentsPage = new ReasonComponentsPage();
    await browser.wait(ec.visibilityOf(reasonComponentsPage.title), 5000);
    expect(await reasonComponentsPage.getTitle()).to.eq('hrApp.reason.home.title');
    await browser.wait(ec.or(ec.visibilityOf(reasonComponentsPage.entities), ec.visibilityOf(reasonComponentsPage.noResult)), 1000);
  });

  it('should load create Reason page', async () => {
    await reasonComponentsPage.clickOnCreateButton();
    reasonUpdatePage = new ReasonUpdatePage();
    expect(await reasonUpdatePage.getPageTitle()).to.eq('hrApp.reason.home.createOrEditLabel');
    await reasonUpdatePage.cancel();
  });

  it('should create and save Reasons', async () => {
    const nbButtonsBeforeCreate = await reasonComponentsPage.countDeleteButtons();

    await reasonComponentsPage.clickOnCreateButton();

    await promise.all([
      reasonUpdatePage.setNameInput('name'),
      reasonUpdatePage.setDescriptionInput('description'),
      reasonUpdatePage.reasonTypeSelectLastOption(),
    ]);

    expect(await reasonUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await reasonUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

    await reasonUpdatePage.save();
    expect(await reasonUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await reasonComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Reason', async () => {
    const nbButtonsBeforeDelete = await reasonComponentsPage.countDeleteButtons();
    await reasonComponentsPage.clickOnLastDeleteButton();

    reasonDeleteDialog = new ReasonDeleteDialog();
    expect(await reasonDeleteDialog.getDialogTitle()).to.eq('hrApp.reason.delete.question');
    await reasonDeleteDialog.clickOnConfirmButton();

    expect(await reasonComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
