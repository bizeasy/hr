import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TermTypeComponentsPage, TermTypeDeleteDialog, TermTypeUpdatePage } from './term-type.page-object';

const expect = chai.expect;

describe('TermType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let termTypeComponentsPage: TermTypeComponentsPage;
  let termTypeUpdatePage: TermTypeUpdatePage;
  let termTypeDeleteDialog: TermTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TermTypes', async () => {
    await navBarPage.goToEntity('term-type');
    termTypeComponentsPage = new TermTypeComponentsPage();
    await browser.wait(ec.visibilityOf(termTypeComponentsPage.title), 5000);
    expect(await termTypeComponentsPage.getTitle()).to.eq('hrApp.termType.home.title');
    await browser.wait(ec.or(ec.visibilityOf(termTypeComponentsPage.entities), ec.visibilityOf(termTypeComponentsPage.noResult)), 1000);
  });

  it('should load create TermType page', async () => {
    await termTypeComponentsPage.clickOnCreateButton();
    termTypeUpdatePage = new TermTypeUpdatePage();
    expect(await termTypeUpdatePage.getPageTitle()).to.eq('hrApp.termType.home.createOrEditLabel');
    await termTypeUpdatePage.cancel();
  });

  it('should create and save TermTypes', async () => {
    const nbButtonsBeforeCreate = await termTypeComponentsPage.countDeleteButtons();

    await termTypeComponentsPage.clickOnCreateButton();

    await promise.all([termTypeUpdatePage.setNameInput('name'), termTypeUpdatePage.setDescriptionInput('description')]);

    expect(await termTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await termTypeUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

    await termTypeUpdatePage.save();
    expect(await termTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await termTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TermType', async () => {
    const nbButtonsBeforeDelete = await termTypeComponentsPage.countDeleteButtons();
    await termTypeComponentsPage.clickOnLastDeleteButton();

    termTypeDeleteDialog = new TermTypeDeleteDialog();
    expect(await termTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.termType.delete.question');
    await termTypeDeleteDialog.clickOnConfirmButton();

    expect(await termTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
