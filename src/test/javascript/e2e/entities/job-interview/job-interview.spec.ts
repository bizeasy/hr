import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { JobInterviewComponentsPage, JobInterviewDeleteDialog, JobInterviewUpdatePage } from './job-interview.page-object';

const expect = chai.expect;

describe('JobInterview e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let jobInterviewComponentsPage: JobInterviewComponentsPage;
  let jobInterviewUpdatePage: JobInterviewUpdatePage;
  let jobInterviewDeleteDialog: JobInterviewDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load JobInterviews', async () => {
    await navBarPage.goToEntity('job-interview');
    jobInterviewComponentsPage = new JobInterviewComponentsPage();
    await browser.wait(ec.visibilityOf(jobInterviewComponentsPage.title), 5000);
    expect(await jobInterviewComponentsPage.getTitle()).to.eq('hrApp.jobInterview.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(jobInterviewComponentsPage.entities), ec.visibilityOf(jobInterviewComponentsPage.noResult)),
      1000
    );
  });

  it('should load create JobInterview page', async () => {
    await jobInterviewComponentsPage.clickOnCreateButton();
    jobInterviewUpdatePage = new JobInterviewUpdatePage();
    expect(await jobInterviewUpdatePage.getPageTitle()).to.eq('hrApp.jobInterview.home.createOrEditLabel');
    await jobInterviewUpdatePage.cancel();
  });

  it('should create and save JobInterviews', async () => {
    const nbButtonsBeforeCreate = await jobInterviewComponentsPage.countDeleteButtons();

    await jobInterviewComponentsPage.clickOnCreateButton();

    await promise.all([
      jobInterviewUpdatePage.setRemarksInput('remarks'),
      jobInterviewUpdatePage.setInterviewDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      jobInterviewUpdatePage.intervieweeSelectLastOption(),
      jobInterviewUpdatePage.interviewerSelectLastOption(),
      jobInterviewUpdatePage.typeSelectLastOption(),
      jobInterviewUpdatePage.jobRequisitionSelectLastOption(),
      jobInterviewUpdatePage.gradeSecuredSelectLastOption(),
      jobInterviewUpdatePage.resultSelectLastOption(),
    ]);

    expect(await jobInterviewUpdatePage.getRemarksInput()).to.eq('remarks', 'Expected Remarks value to be equals to remarks');
    expect(await jobInterviewUpdatePage.getInterviewDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected interviewDate value to be equals to 2000-12-31'
    );

    await jobInterviewUpdatePage.save();
    expect(await jobInterviewUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await jobInterviewComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last JobInterview', async () => {
    const nbButtonsBeforeDelete = await jobInterviewComponentsPage.countDeleteButtons();
    await jobInterviewComponentsPage.clickOnLastDeleteButton();

    jobInterviewDeleteDialog = new JobInterviewDeleteDialog();
    expect(await jobInterviewDeleteDialog.getDialogTitle()).to.eq('hrApp.jobInterview.delete.question');
    await jobInterviewDeleteDialog.clickOnConfirmButton();

    expect(await jobInterviewComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
