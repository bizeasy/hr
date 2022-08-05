import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PerfReviewItemComponentsPage, PerfReviewItemDeleteDialog, PerfReviewItemUpdatePage } from './perf-review-item.page-object';

const expect = chai.expect;

describe('PerfReviewItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let perfReviewItemComponentsPage: PerfReviewItemComponentsPage;
  let perfReviewItemUpdatePage: PerfReviewItemUpdatePage;
  let perfReviewItemDeleteDialog: PerfReviewItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PerfReviewItems', async () => {
    await navBarPage.goToEntity('perf-review-item');
    perfReviewItemComponentsPage = new PerfReviewItemComponentsPage();
    await browser.wait(ec.visibilityOf(perfReviewItemComponentsPage.title), 5000);
    expect(await perfReviewItemComponentsPage.getTitle()).to.eq('hrApp.perfReviewItem.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(perfReviewItemComponentsPage.entities), ec.visibilityOf(perfReviewItemComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PerfReviewItem page', async () => {
    await perfReviewItemComponentsPage.clickOnCreateButton();
    perfReviewItemUpdatePage = new PerfReviewItemUpdatePage();
    expect(await perfReviewItemUpdatePage.getPageTitle()).to.eq('hrApp.perfReviewItem.home.createOrEditLabel');
    await perfReviewItemUpdatePage.cancel();
  });

  it('should create and save PerfReviewItems', async () => {
    const nbButtonsBeforeCreate = await perfReviewItemComponentsPage.countDeleteButtons();

    await perfReviewItemComponentsPage.clickOnCreateButton();

    await promise.all([
      perfReviewItemUpdatePage.setSequenceNoInput('5'),
      perfReviewItemUpdatePage.setCommentsInput('comments'),
      perfReviewItemUpdatePage.perfReviewSelectLastOption(),
      perfReviewItemUpdatePage.perfRatingTypeSelectLastOption(),
      perfReviewItemUpdatePage.typeSelectLastOption(),
      perfReviewItemUpdatePage.employeeSelectLastOption(),
    ]);

    expect(await perfReviewItemUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');
    expect(await perfReviewItemUpdatePage.getCommentsInput()).to.eq('comments', 'Expected Comments value to be equals to comments');

    await perfReviewItemUpdatePage.save();
    expect(await perfReviewItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await perfReviewItemComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PerfReviewItem', async () => {
    const nbButtonsBeforeDelete = await perfReviewItemComponentsPage.countDeleteButtons();
    await perfReviewItemComponentsPage.clickOnLastDeleteButton();

    perfReviewItemDeleteDialog = new PerfReviewItemDeleteDialog();
    expect(await perfReviewItemDeleteDialog.getDialogTitle()).to.eq('hrApp.perfReviewItem.delete.question');
    await perfReviewItemDeleteDialog.clickOnConfirmButton();

    expect(await perfReviewItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
