import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ReasonTypeComponentsPage, ReasonTypeDeleteDialog, ReasonTypeUpdatePage } from './reason-type.page-object';

const expect = chai.expect;

describe('ReasonType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let reasonTypeComponentsPage: ReasonTypeComponentsPage;
  let reasonTypeUpdatePage: ReasonTypeUpdatePage;
  let reasonTypeDeleteDialog: ReasonTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ReasonTypes', async () => {
    await navBarPage.goToEntity('reason-type');
    reasonTypeComponentsPage = new ReasonTypeComponentsPage();
    await browser.wait(ec.visibilityOf(reasonTypeComponentsPage.title), 5000);
    expect(await reasonTypeComponentsPage.getTitle()).to.eq('hrApp.reasonType.home.title');
    await browser.wait(ec.or(ec.visibilityOf(reasonTypeComponentsPage.entities), ec.visibilityOf(reasonTypeComponentsPage.noResult)), 1000);
  });

  it('should load create ReasonType page', async () => {
    await reasonTypeComponentsPage.clickOnCreateButton();
    reasonTypeUpdatePage = new ReasonTypeUpdatePage();
    expect(await reasonTypeUpdatePage.getPageTitle()).to.eq('hrApp.reasonType.home.createOrEditLabel');
    await reasonTypeUpdatePage.cancel();
  });

  it('should create and save ReasonTypes', async () => {
    const nbButtonsBeforeCreate = await reasonTypeComponentsPage.countDeleteButtons();

    await reasonTypeComponentsPage.clickOnCreateButton();

    await promise.all([reasonTypeUpdatePage.setNameInput('name'), reasonTypeUpdatePage.setDescriptionInput('description')]);

    expect(await reasonTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await reasonTypeUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

    await reasonTypeUpdatePage.save();
    expect(await reasonTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await reasonTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ReasonType', async () => {
    const nbButtonsBeforeDelete = await reasonTypeComponentsPage.countDeleteButtons();
    await reasonTypeComponentsPage.clickOnLastDeleteButton();

    reasonTypeDeleteDialog = new ReasonTypeDeleteDialog();
    expect(await reasonTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.reasonType.delete.question');
    await reasonTypeDeleteDialog.clickOnConfirmButton();

    expect(await reasonTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
