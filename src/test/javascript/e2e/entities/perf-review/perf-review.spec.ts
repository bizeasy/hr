import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PerfReviewComponentsPage, PerfReviewDeleteDialog, PerfReviewUpdatePage } from './perf-review.page-object';

const expect = chai.expect;

describe('PerfReview e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let perfReviewComponentsPage: PerfReviewComponentsPage;
  let perfReviewUpdatePage: PerfReviewUpdatePage;
  let perfReviewDeleteDialog: PerfReviewDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PerfReviews', async () => {
    await navBarPage.goToEntity('perf-review');
    perfReviewComponentsPage = new PerfReviewComponentsPage();
    await browser.wait(ec.visibilityOf(perfReviewComponentsPage.title), 5000);
    expect(await perfReviewComponentsPage.getTitle()).to.eq('hrApp.perfReview.home.title');
    await browser.wait(ec.or(ec.visibilityOf(perfReviewComponentsPage.entities), ec.visibilityOf(perfReviewComponentsPage.noResult)), 1000);
  });

  it('should load create PerfReview page', async () => {
    await perfReviewComponentsPage.clickOnCreateButton();
    perfReviewUpdatePage = new PerfReviewUpdatePage();
    expect(await perfReviewUpdatePage.getPageTitle()).to.eq('hrApp.perfReview.home.createOrEditLabel');
    await perfReviewUpdatePage.cancel();
  });

  it('should create and save PerfReviews', async () => {
    const nbButtonsBeforeCreate = await perfReviewComponentsPage.countDeleteButtons();

    await perfReviewComponentsPage.clickOnCreateButton();

    await promise.all([
      perfReviewUpdatePage.setFromDateInput('2000-12-31'),
      perfReviewUpdatePage.setThruDateInput('2000-12-31'),
      perfReviewUpdatePage.setCommentsInput('comments'),
      perfReviewUpdatePage.employeeSelectLastOption(),
      perfReviewUpdatePage.managerSelectLastOption(),
      perfReviewUpdatePage.emplPositionSelectLastOption(),
    ]);

    expect(await perfReviewUpdatePage.getFromDateInput()).to.eq('2000-12-31', 'Expected fromDate value to be equals to 2000-12-31');
    expect(await perfReviewUpdatePage.getThruDateInput()).to.eq('2000-12-31', 'Expected thruDate value to be equals to 2000-12-31');
    expect(await perfReviewUpdatePage.getCommentsInput()).to.eq('comments', 'Expected Comments value to be equals to comments');

    await perfReviewUpdatePage.save();
    expect(await perfReviewUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await perfReviewComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PerfReview', async () => {
    const nbButtonsBeforeDelete = await perfReviewComponentsPage.countDeleteButtons();
    await perfReviewComponentsPage.clickOnLastDeleteButton();

    perfReviewDeleteDialog = new PerfReviewDeleteDialog();
    expect(await perfReviewDeleteDialog.getDialogTitle()).to.eq('hrApp.perfReview.delete.question');
    await perfReviewDeleteDialog.clickOnConfirmButton();

    expect(await perfReviewComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
