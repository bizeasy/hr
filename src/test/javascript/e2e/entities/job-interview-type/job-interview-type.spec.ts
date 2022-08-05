import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { JobInterviewTypeComponentsPage, JobInterviewTypeDeleteDialog, JobInterviewTypeUpdatePage } from './job-interview-type.page-object';

const expect = chai.expect;

describe('JobInterviewType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let jobInterviewTypeComponentsPage: JobInterviewTypeComponentsPage;
  let jobInterviewTypeUpdatePage: JobInterviewTypeUpdatePage;
  let jobInterviewTypeDeleteDialog: JobInterviewTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load JobInterviewTypes', async () => {
    await navBarPage.goToEntity('job-interview-type');
    jobInterviewTypeComponentsPage = new JobInterviewTypeComponentsPage();
    await browser.wait(ec.visibilityOf(jobInterviewTypeComponentsPage.title), 5000);
    expect(await jobInterviewTypeComponentsPage.getTitle()).to.eq('hrApp.jobInterviewType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(jobInterviewTypeComponentsPage.entities), ec.visibilityOf(jobInterviewTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create JobInterviewType page', async () => {
    await jobInterviewTypeComponentsPage.clickOnCreateButton();
    jobInterviewTypeUpdatePage = new JobInterviewTypeUpdatePage();
    expect(await jobInterviewTypeUpdatePage.getPageTitle()).to.eq('hrApp.jobInterviewType.home.createOrEditLabel');
    await jobInterviewTypeUpdatePage.cancel();
  });

  it('should create and save JobInterviewTypes', async () => {
    const nbButtonsBeforeCreate = await jobInterviewTypeComponentsPage.countDeleteButtons();

    await jobInterviewTypeComponentsPage.clickOnCreateButton();

    await promise.all([jobInterviewTypeUpdatePage.setNameInput('name'), jobInterviewTypeUpdatePage.setDescriptionInput('description')]);

    expect(await jobInterviewTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await jobInterviewTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await jobInterviewTypeUpdatePage.save();
    expect(await jobInterviewTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await jobInterviewTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last JobInterviewType', async () => {
    const nbButtonsBeforeDelete = await jobInterviewTypeComponentsPage.countDeleteButtons();
    await jobInterviewTypeComponentsPage.clickOnLastDeleteButton();

    jobInterviewTypeDeleteDialog = new JobInterviewTypeDeleteDialog();
    expect(await jobInterviewTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.jobInterviewType.delete.question');
    await jobInterviewTypeDeleteDialog.clickOnConfirmButton();

    expect(await jobInterviewTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
