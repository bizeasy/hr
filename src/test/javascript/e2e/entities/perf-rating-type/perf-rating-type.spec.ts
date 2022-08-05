import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PerfRatingTypeComponentsPage, PerfRatingTypeDeleteDialog, PerfRatingTypeUpdatePage } from './perf-rating-type.page-object';

const expect = chai.expect;

describe('PerfRatingType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let perfRatingTypeComponentsPage: PerfRatingTypeComponentsPage;
  let perfRatingTypeUpdatePage: PerfRatingTypeUpdatePage;
  let perfRatingTypeDeleteDialog: PerfRatingTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PerfRatingTypes', async () => {
    await navBarPage.goToEntity('perf-rating-type');
    perfRatingTypeComponentsPage = new PerfRatingTypeComponentsPage();
    await browser.wait(ec.visibilityOf(perfRatingTypeComponentsPage.title), 5000);
    expect(await perfRatingTypeComponentsPage.getTitle()).to.eq('hrApp.perfRatingType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(perfRatingTypeComponentsPage.entities), ec.visibilityOf(perfRatingTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PerfRatingType page', async () => {
    await perfRatingTypeComponentsPage.clickOnCreateButton();
    perfRatingTypeUpdatePage = new PerfRatingTypeUpdatePage();
    expect(await perfRatingTypeUpdatePage.getPageTitle()).to.eq('hrApp.perfRatingType.home.createOrEditLabel');
    await perfRatingTypeUpdatePage.cancel();
  });

  it('should create and save PerfRatingTypes', async () => {
    const nbButtonsBeforeCreate = await perfRatingTypeComponentsPage.countDeleteButtons();

    await perfRatingTypeComponentsPage.clickOnCreateButton();

    await promise.all([perfRatingTypeUpdatePage.setNameInput('name'), perfRatingTypeUpdatePage.setDescriptionInput('description')]);

    expect(await perfRatingTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await perfRatingTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await perfRatingTypeUpdatePage.save();
    expect(await perfRatingTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await perfRatingTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PerfRatingType', async () => {
    const nbButtonsBeforeDelete = await perfRatingTypeComponentsPage.countDeleteButtons();
    await perfRatingTypeComponentsPage.clickOnLastDeleteButton();

    perfRatingTypeDeleteDialog = new PerfRatingTypeDeleteDialog();
    expect(await perfRatingTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.perfRatingType.delete.question');
    await perfRatingTypeDeleteDialog.clickOnConfirmButton();

    expect(await perfRatingTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
