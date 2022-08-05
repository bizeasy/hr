import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LocationTypeComponentsPage, LocationTypeDeleteDialog, LocationTypeUpdatePage } from './location-type.page-object';

const expect = chai.expect;

describe('LocationType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let locationTypeComponentsPage: LocationTypeComponentsPage;
  let locationTypeUpdatePage: LocationTypeUpdatePage;
  let locationTypeDeleteDialog: LocationTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LocationTypes', async () => {
    await navBarPage.goToEntity('location-type');
    locationTypeComponentsPage = new LocationTypeComponentsPage();
    await browser.wait(ec.visibilityOf(locationTypeComponentsPage.title), 5000);
    expect(await locationTypeComponentsPage.getTitle()).to.eq('hrApp.locationType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(locationTypeComponentsPage.entities), ec.visibilityOf(locationTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create LocationType page', async () => {
    await locationTypeComponentsPage.clickOnCreateButton();
    locationTypeUpdatePage = new LocationTypeUpdatePage();
    expect(await locationTypeUpdatePage.getPageTitle()).to.eq('hrApp.locationType.home.createOrEditLabel');
    await locationTypeUpdatePage.cancel();
  });

  it('should create and save LocationTypes', async () => {
    const nbButtonsBeforeCreate = await locationTypeComponentsPage.countDeleteButtons();

    await locationTypeComponentsPage.clickOnCreateButton();

    await promise.all([locationTypeUpdatePage.setNameInput('name'), locationTypeUpdatePage.setDescriptionInput('description')]);

    expect(await locationTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await locationTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await locationTypeUpdatePage.save();
    expect(await locationTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await locationTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last LocationType', async () => {
    const nbButtonsBeforeDelete = await locationTypeComponentsPage.countDeleteButtons();
    await locationTypeComponentsPage.clickOnLastDeleteButton();

    locationTypeDeleteDialog = new LocationTypeDeleteDialog();
    expect(await locationTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.locationType.delete.question');
    await locationTypeDeleteDialog.clickOnConfirmButton();

    expect(await locationTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
