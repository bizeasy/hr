import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { JobRequisitionComponentsPage, JobRequisitionDeleteDialog, JobRequisitionUpdatePage } from './job-requisition.page-object';

const expect = chai.expect;

describe('JobRequisition e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let jobRequisitionComponentsPage: JobRequisitionComponentsPage;
  let jobRequisitionUpdatePage: JobRequisitionUpdatePage;
  let jobRequisitionDeleteDialog: JobRequisitionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load JobRequisitions', async () => {
    await navBarPage.goToEntity('job-requisition');
    jobRequisitionComponentsPage = new JobRequisitionComponentsPage();
    await browser.wait(ec.visibilityOf(jobRequisitionComponentsPage.title), 5000);
    expect(await jobRequisitionComponentsPage.getTitle()).to.eq('hrApp.jobRequisition.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(jobRequisitionComponentsPage.entities), ec.visibilityOf(jobRequisitionComponentsPage.noResult)),
      1000
    );
  });

  it('should load create JobRequisition page', async () => {
    await jobRequisitionComponentsPage.clickOnCreateButton();
    jobRequisitionUpdatePage = new JobRequisitionUpdatePage();
    expect(await jobRequisitionUpdatePage.getPageTitle()).to.eq('hrApp.jobRequisition.home.createOrEditLabel');
    await jobRequisitionUpdatePage.cancel();
  });

  it('should create and save JobRequisitions', async () => {
    const nbButtonsBeforeCreate = await jobRequisitionComponentsPage.countDeleteButtons();

    await jobRequisitionComponentsPage.clickOnCreateButton();

    await promise.all([
      jobRequisitionUpdatePage.setDurationInput('PT12S'),
      jobRequisitionUpdatePage.setAgeInput('5'),
      jobRequisitionUpdatePage.genderSelectLastOption(),
      jobRequisitionUpdatePage.setExperienceMonthsInput('5'),
      jobRequisitionUpdatePage.setExperienceYearsInput('5'),
      jobRequisitionUpdatePage.setQualificationStrInput('qualificationStr'),
      jobRequisitionUpdatePage.setNoOfResourceInput('5'),
      jobRequisitionUpdatePage.setRequiredOnDateInput('2000-12-31'),
      jobRequisitionUpdatePage.qualificiationSelectLastOption(),
      jobRequisitionUpdatePage.skillTypeSelectLastOption(),
      jobRequisitionUpdatePage.jobLocationSelectLastOption(),
      jobRequisitionUpdatePage.examTypeSelectLastOption(),
    ]);

    expect(await jobRequisitionUpdatePage.getDurationInput()).to.contain('12', 'Expected duration value to be equals to 12');
    expect(await jobRequisitionUpdatePage.getAgeInput()).to.eq('5', 'Expected age value to be equals to 5');
    expect(await jobRequisitionUpdatePage.getExperienceMonthsInput()).to.eq('5', 'Expected experienceMonths value to be equals to 5');
    expect(await jobRequisitionUpdatePage.getExperienceYearsInput()).to.eq('5', 'Expected experienceYears value to be equals to 5');
    expect(await jobRequisitionUpdatePage.getQualificationStrInput()).to.eq(
      'qualificationStr',
      'Expected QualificationStr value to be equals to qualificationStr'
    );
    expect(await jobRequisitionUpdatePage.getNoOfResourceInput()).to.eq('5', 'Expected noOfResource value to be equals to 5');
    expect(await jobRequisitionUpdatePage.getRequiredOnDateInput()).to.eq(
      '2000-12-31',
      'Expected requiredOnDate value to be equals to 2000-12-31'
    );

    await jobRequisitionUpdatePage.save();
    expect(await jobRequisitionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await jobRequisitionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last JobRequisition', async () => {
    const nbButtonsBeforeDelete = await jobRequisitionComponentsPage.countDeleteButtons();
    await jobRequisitionComponentsPage.clickOnLastDeleteButton();

    jobRequisitionDeleteDialog = new JobRequisitionDeleteDialog();
    expect(await jobRequisitionDeleteDialog.getDialogTitle()).to.eq('hrApp.jobRequisition.delete.question');
    await jobRequisitionDeleteDialog.clickOnConfirmButton();

    expect(await jobRequisitionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
