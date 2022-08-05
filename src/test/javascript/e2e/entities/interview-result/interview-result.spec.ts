import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InterviewResultComponentsPage, InterviewResultDeleteDialog, InterviewResultUpdatePage } from './interview-result.page-object';

const expect = chai.expect;

describe('InterviewResult e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let interviewResultComponentsPage: InterviewResultComponentsPage;
  let interviewResultUpdatePage: InterviewResultUpdatePage;
  let interviewResultDeleteDialog: InterviewResultDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load InterviewResults', async () => {
    await navBarPage.goToEntity('interview-result');
    interviewResultComponentsPage = new InterviewResultComponentsPage();
    await browser.wait(ec.visibilityOf(interviewResultComponentsPage.title), 5000);
    expect(await interviewResultComponentsPage.getTitle()).to.eq('hrApp.interviewResult.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(interviewResultComponentsPage.entities), ec.visibilityOf(interviewResultComponentsPage.noResult)),
      1000
    );
  });

  it('should load create InterviewResult page', async () => {
    await interviewResultComponentsPage.clickOnCreateButton();
    interviewResultUpdatePage = new InterviewResultUpdatePage();
    expect(await interviewResultUpdatePage.getPageTitle()).to.eq('hrApp.interviewResult.home.createOrEditLabel');
    await interviewResultUpdatePage.cancel();
  });

  it('should create and save InterviewResults', async () => {
    const nbButtonsBeforeCreate = await interviewResultComponentsPage.countDeleteButtons();

    await interviewResultComponentsPage.clickOnCreateButton();

    await promise.all([interviewResultUpdatePage.setNameInput('name'), interviewResultUpdatePage.setDescriptionInput('description')]);

    expect(await interviewResultUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await interviewResultUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await interviewResultUpdatePage.save();
    expect(await interviewResultUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await interviewResultComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last InterviewResult', async () => {
    const nbButtonsBeforeDelete = await interviewResultComponentsPage.countDeleteButtons();
    await interviewResultComponentsPage.clickOnLastDeleteButton();

    interviewResultDeleteDialog = new InterviewResultDeleteDialog();
    expect(await interviewResultDeleteDialog.getDialogTitle()).to.eq('hrApp.interviewResult.delete.question');
    await interviewResultDeleteDialog.clickOnConfirmButton();

    expect(await interviewResultComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
