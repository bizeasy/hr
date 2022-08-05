import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PerfReviewItemTypeComponentsPage,
  PerfReviewItemTypeDeleteDialog,
  PerfReviewItemTypeUpdatePage,
} from './perf-review-item-type.page-object';

const expect = chai.expect;

describe('PerfReviewItemType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let perfReviewItemTypeComponentsPage: PerfReviewItemTypeComponentsPage;
  let perfReviewItemTypeUpdatePage: PerfReviewItemTypeUpdatePage;
  let perfReviewItemTypeDeleteDialog: PerfReviewItemTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PerfReviewItemTypes', async () => {
    await navBarPage.goToEntity('perf-review-item-type');
    perfReviewItemTypeComponentsPage = new PerfReviewItemTypeComponentsPage();
    await browser.wait(ec.visibilityOf(perfReviewItemTypeComponentsPage.title), 5000);
    expect(await perfReviewItemTypeComponentsPage.getTitle()).to.eq('hrApp.perfReviewItemType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(perfReviewItemTypeComponentsPage.entities), ec.visibilityOf(perfReviewItemTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PerfReviewItemType page', async () => {
    await perfReviewItemTypeComponentsPage.clickOnCreateButton();
    perfReviewItemTypeUpdatePage = new PerfReviewItemTypeUpdatePage();
    expect(await perfReviewItemTypeUpdatePage.getPageTitle()).to.eq('hrApp.perfReviewItemType.home.createOrEditLabel');
    await perfReviewItemTypeUpdatePage.cancel();
  });

  it('should create and save PerfReviewItemTypes', async () => {
    const nbButtonsBeforeCreate = await perfReviewItemTypeComponentsPage.countDeleteButtons();

    await perfReviewItemTypeComponentsPage.clickOnCreateButton();

    await promise.all([perfReviewItemTypeUpdatePage.setNameInput('name'), perfReviewItemTypeUpdatePage.setDescriptionInput('description')]);

    expect(await perfReviewItemTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await perfReviewItemTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await perfReviewItemTypeUpdatePage.save();
    expect(await perfReviewItemTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await perfReviewItemTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PerfReviewItemType', async () => {
    const nbButtonsBeforeDelete = await perfReviewItemTypeComponentsPage.countDeleteButtons();
    await perfReviewItemTypeComponentsPage.clickOnLastDeleteButton();

    perfReviewItemTypeDeleteDialog = new PerfReviewItemTypeDeleteDialog();
    expect(await perfReviewItemTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.perfReviewItemType.delete.question');
    await perfReviewItemTypeDeleteDialog.clickOnConfirmButton();

    expect(await perfReviewItemTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
