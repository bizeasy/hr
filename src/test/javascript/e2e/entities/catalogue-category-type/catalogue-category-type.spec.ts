import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CatalogueCategoryTypeComponentsPage,
  CatalogueCategoryTypeDeleteDialog,
  CatalogueCategoryTypeUpdatePage,
} from './catalogue-category-type.page-object';

const expect = chai.expect;

describe('CatalogueCategoryType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let catalogueCategoryTypeComponentsPage: CatalogueCategoryTypeComponentsPage;
  let catalogueCategoryTypeUpdatePage: CatalogueCategoryTypeUpdatePage;
  let catalogueCategoryTypeDeleteDialog: CatalogueCategoryTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CatalogueCategoryTypes', async () => {
    await navBarPage.goToEntity('catalogue-category-type');
    catalogueCategoryTypeComponentsPage = new CatalogueCategoryTypeComponentsPage();
    await browser.wait(ec.visibilityOf(catalogueCategoryTypeComponentsPage.title), 5000);
    expect(await catalogueCategoryTypeComponentsPage.getTitle()).to.eq('hrApp.catalogueCategoryType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(catalogueCategoryTypeComponentsPage.entities), ec.visibilityOf(catalogueCategoryTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CatalogueCategoryType page', async () => {
    await catalogueCategoryTypeComponentsPage.clickOnCreateButton();
    catalogueCategoryTypeUpdatePage = new CatalogueCategoryTypeUpdatePage();
    expect(await catalogueCategoryTypeUpdatePage.getPageTitle()).to.eq('hrApp.catalogueCategoryType.home.createOrEditLabel');
    await catalogueCategoryTypeUpdatePage.cancel();
  });

  it('should create and save CatalogueCategoryTypes', async () => {
    const nbButtonsBeforeCreate = await catalogueCategoryTypeComponentsPage.countDeleteButtons();

    await catalogueCategoryTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      catalogueCategoryTypeUpdatePage.setNameInput('name'),
      catalogueCategoryTypeUpdatePage.setDescriptionInput('description'),
    ]);

    expect(await catalogueCategoryTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await catalogueCategoryTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await catalogueCategoryTypeUpdatePage.save();
    expect(await catalogueCategoryTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await catalogueCategoryTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CatalogueCategoryType', async () => {
    const nbButtonsBeforeDelete = await catalogueCategoryTypeComponentsPage.countDeleteButtons();
    await catalogueCategoryTypeComponentsPage.clickOnLastDeleteButton();

    catalogueCategoryTypeDeleteDialog = new CatalogueCategoryTypeDeleteDialog();
    expect(await catalogueCategoryTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.catalogueCategoryType.delete.question');
    await catalogueCategoryTypeDeleteDialog.clickOnConfirmButton();

    expect(await catalogueCategoryTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
