import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContentTypeComponentsPage, ContentTypeDeleteDialog, ContentTypeUpdatePage } from './content-type.page-object';

const expect = chai.expect;

describe('ContentType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contentTypeComponentsPage: ContentTypeComponentsPage;
  let contentTypeUpdatePage: ContentTypeUpdatePage;
  let contentTypeDeleteDialog: ContentTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ContentTypes', async () => {
    await navBarPage.goToEntity('content-type');
    contentTypeComponentsPage = new ContentTypeComponentsPage();
    await browser.wait(ec.visibilityOf(contentTypeComponentsPage.title), 5000);
    expect(await contentTypeComponentsPage.getTitle()).to.eq('hrApp.contentType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(contentTypeComponentsPage.entities), ec.visibilityOf(contentTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ContentType page', async () => {
    await contentTypeComponentsPage.clickOnCreateButton();
    contentTypeUpdatePage = new ContentTypeUpdatePage();
    expect(await contentTypeUpdatePage.getPageTitle()).to.eq('hrApp.contentType.home.createOrEditLabel');
    await contentTypeUpdatePage.cancel();
  });

  it('should create and save ContentTypes', async () => {
    const nbButtonsBeforeCreate = await contentTypeComponentsPage.countDeleteButtons();

    await contentTypeComponentsPage.clickOnCreateButton();

    await promise.all([contentTypeUpdatePage.setNameInput('name')]);

    expect(await contentTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');

    await contentTypeUpdatePage.save();
    expect(await contentTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await contentTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ContentType', async () => {
    const nbButtonsBeforeDelete = await contentTypeComponentsPage.countDeleteButtons();
    await contentTypeComponentsPage.clickOnLastDeleteButton();

    contentTypeDeleteDialog = new ContentTypeDeleteDialog();
    expect(await contentTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.contentType.delete.question');
    await contentTypeDeleteDialog.clickOnConfirmButton();

    expect(await contentTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
