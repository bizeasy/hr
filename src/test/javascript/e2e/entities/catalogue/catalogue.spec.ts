import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CatalogueComponentsPage, CatalogueDeleteDialog, CatalogueUpdatePage } from './catalogue.page-object';

const expect = chai.expect;

describe('Catalogue e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let catalogueComponentsPage: CatalogueComponentsPage;
  let catalogueUpdatePage: CatalogueUpdatePage;
  let catalogueDeleteDialog: CatalogueDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Catalogues', async () => {
    await navBarPage.goToEntity('catalogue');
    catalogueComponentsPage = new CatalogueComponentsPage();
    await browser.wait(ec.visibilityOf(catalogueComponentsPage.title), 5000);
    expect(await catalogueComponentsPage.getTitle()).to.eq('hrApp.catalogue.home.title');
    await browser.wait(ec.or(ec.visibilityOf(catalogueComponentsPage.entities), ec.visibilityOf(catalogueComponentsPage.noResult)), 1000);
  });

  it('should load create Catalogue page', async () => {
    await catalogueComponentsPage.clickOnCreateButton();
    catalogueUpdatePage = new CatalogueUpdatePage();
    expect(await catalogueUpdatePage.getPageTitle()).to.eq('hrApp.catalogue.home.createOrEditLabel');
    await catalogueUpdatePage.cancel();
  });

  it('should create and save Catalogues', async () => {
    const nbButtonsBeforeCreate = await catalogueComponentsPage.countDeleteButtons();

    await catalogueComponentsPage.clickOnCreateButton();

    await promise.all([
      catalogueUpdatePage.setNameInput('name'),
      catalogueUpdatePage.setDescriptionInput('description'),
      catalogueUpdatePage.setSequenceNoInput('5'),
      catalogueUpdatePage.setImagePathInput('imagePath'),
      catalogueUpdatePage.setMobileImagePathInput('mobileImagePath'),
      catalogueUpdatePage.setAltImage1Input('altImage1'),
      catalogueUpdatePage.setAltImage2Input('altImage2'),
      catalogueUpdatePage.setAltImage3Input('altImage3'),
    ]);

    expect(await catalogueUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await catalogueUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await catalogueUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');
    expect(await catalogueUpdatePage.getImagePathInput()).to.eq('imagePath', 'Expected ImagePath value to be equals to imagePath');
    expect(await catalogueUpdatePage.getMobileImagePathInput()).to.eq(
      'mobileImagePath',
      'Expected MobileImagePath value to be equals to mobileImagePath'
    );
    expect(await catalogueUpdatePage.getAltImage1Input()).to.eq('altImage1', 'Expected AltImage1 value to be equals to altImage1');
    expect(await catalogueUpdatePage.getAltImage2Input()).to.eq('altImage2', 'Expected AltImage2 value to be equals to altImage2');
    expect(await catalogueUpdatePage.getAltImage3Input()).to.eq('altImage3', 'Expected AltImage3 value to be equals to altImage3');

    await catalogueUpdatePage.save();
    expect(await catalogueUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await catalogueComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Catalogue', async () => {
    const nbButtonsBeforeDelete = await catalogueComponentsPage.countDeleteButtons();
    await catalogueComponentsPage.clickOnLastDeleteButton();

    catalogueDeleteDialog = new CatalogueDeleteDialog();
    expect(await catalogueDeleteDialog.getDialogTitle()).to.eq('hrApp.catalogue.delete.question');
    await catalogueDeleteDialog.clickOnConfirmButton();

    expect(await catalogueComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
