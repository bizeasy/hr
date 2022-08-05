import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CatalogueCategoryComponentsPage,
  CatalogueCategoryDeleteDialog,
  CatalogueCategoryUpdatePage,
} from './catalogue-category.page-object';

const expect = chai.expect;

describe('CatalogueCategory e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let catalogueCategoryComponentsPage: CatalogueCategoryComponentsPage;
  let catalogueCategoryUpdatePage: CatalogueCategoryUpdatePage;
  let catalogueCategoryDeleteDialog: CatalogueCategoryDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CatalogueCategories', async () => {
    await navBarPage.goToEntity('catalogue-category');
    catalogueCategoryComponentsPage = new CatalogueCategoryComponentsPage();
    await browser.wait(ec.visibilityOf(catalogueCategoryComponentsPage.title), 5000);
    expect(await catalogueCategoryComponentsPage.getTitle()).to.eq('hrApp.catalogueCategory.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(catalogueCategoryComponentsPage.entities), ec.visibilityOf(catalogueCategoryComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CatalogueCategory page', async () => {
    await catalogueCategoryComponentsPage.clickOnCreateButton();
    catalogueCategoryUpdatePage = new CatalogueCategoryUpdatePage();
    expect(await catalogueCategoryUpdatePage.getPageTitle()).to.eq('hrApp.catalogueCategory.home.createOrEditLabel');
    await catalogueCategoryUpdatePage.cancel();
  });

  it('should create and save CatalogueCategories', async () => {
    const nbButtonsBeforeCreate = await catalogueCategoryComponentsPage.countDeleteButtons();

    await catalogueCategoryComponentsPage.clickOnCreateButton();

    await promise.all([
      catalogueCategoryUpdatePage.setSequenceNoInput('5'),
      catalogueCategoryUpdatePage.catalogueSelectLastOption(),
      catalogueCategoryUpdatePage.productCategorySelectLastOption(),
      catalogueCategoryUpdatePage.catalogueCategoryTypeSelectLastOption(),
    ]);

    expect(await catalogueCategoryUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');

    await catalogueCategoryUpdatePage.save();
    expect(await catalogueCategoryUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await catalogueCategoryComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CatalogueCategory', async () => {
    const nbButtonsBeforeDelete = await catalogueCategoryComponentsPage.countDeleteButtons();
    await catalogueCategoryComponentsPage.clickOnLastDeleteButton();

    catalogueCategoryDeleteDialog = new CatalogueCategoryDeleteDialog();
    expect(await catalogueCategoryDeleteDialog.getDialogTitle()).to.eq('hrApp.catalogueCategory.delete.question');
    await catalogueCategoryDeleteDialog.clickOnConfirmButton();

    expect(await catalogueCategoryComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
