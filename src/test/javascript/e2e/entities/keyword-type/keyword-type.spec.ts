import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { KeywordTypeComponentsPage, KeywordTypeDeleteDialog, KeywordTypeUpdatePage } from './keyword-type.page-object';

const expect = chai.expect;

describe('KeywordType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let keywordTypeComponentsPage: KeywordTypeComponentsPage;
  let keywordTypeUpdatePage: KeywordTypeUpdatePage;
  let keywordTypeDeleteDialog: KeywordTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load KeywordTypes', async () => {
    await navBarPage.goToEntity('keyword-type');
    keywordTypeComponentsPage = new KeywordTypeComponentsPage();
    await browser.wait(ec.visibilityOf(keywordTypeComponentsPage.title), 5000);
    expect(await keywordTypeComponentsPage.getTitle()).to.eq('hrApp.keywordType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(keywordTypeComponentsPage.entities), ec.visibilityOf(keywordTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create KeywordType page', async () => {
    await keywordTypeComponentsPage.clickOnCreateButton();
    keywordTypeUpdatePage = new KeywordTypeUpdatePage();
    expect(await keywordTypeUpdatePage.getPageTitle()).to.eq('hrApp.keywordType.home.createOrEditLabel');
    await keywordTypeUpdatePage.cancel();
  });

  it('should create and save KeywordTypes', async () => {
    const nbButtonsBeforeCreate = await keywordTypeComponentsPage.countDeleteButtons();

    await keywordTypeComponentsPage.clickOnCreateButton();

    await promise.all([keywordTypeUpdatePage.setNameInput('name')]);

    expect(await keywordTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');

    await keywordTypeUpdatePage.save();
    expect(await keywordTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await keywordTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last KeywordType', async () => {
    const nbButtonsBeforeDelete = await keywordTypeComponentsPage.countDeleteButtons();
    await keywordTypeComponentsPage.clickOnLastDeleteButton();

    keywordTypeDeleteDialog = new KeywordTypeDeleteDialog();
    expect(await keywordTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.keywordType.delete.question');
    await keywordTypeDeleteDialog.clickOnConfirmButton();

    expect(await keywordTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
