import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InterviewGradeComponentsPage, InterviewGradeDeleteDialog, InterviewGradeUpdatePage } from './interview-grade.page-object';

const expect = chai.expect;

describe('InterviewGrade e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let interviewGradeComponentsPage: InterviewGradeComponentsPage;
  let interviewGradeUpdatePage: InterviewGradeUpdatePage;
  let interviewGradeDeleteDialog: InterviewGradeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load InterviewGrades', async () => {
    await navBarPage.goToEntity('interview-grade');
    interviewGradeComponentsPage = new InterviewGradeComponentsPage();
    await browser.wait(ec.visibilityOf(interviewGradeComponentsPage.title), 5000);
    expect(await interviewGradeComponentsPage.getTitle()).to.eq('hrApp.interviewGrade.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(interviewGradeComponentsPage.entities), ec.visibilityOf(interviewGradeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create InterviewGrade page', async () => {
    await interviewGradeComponentsPage.clickOnCreateButton();
    interviewGradeUpdatePage = new InterviewGradeUpdatePage();
    expect(await interviewGradeUpdatePage.getPageTitle()).to.eq('hrApp.interviewGrade.home.createOrEditLabel');
    await interviewGradeUpdatePage.cancel();
  });

  it('should create and save InterviewGrades', async () => {
    const nbButtonsBeforeCreate = await interviewGradeComponentsPage.countDeleteButtons();

    await interviewGradeComponentsPage.clickOnCreateButton();

    await promise.all([interviewGradeUpdatePage.setNameInput('name'), interviewGradeUpdatePage.setDescriptionInput('description')]);

    expect(await interviewGradeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await interviewGradeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await interviewGradeUpdatePage.save();
    expect(await interviewGradeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await interviewGradeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last InterviewGrade', async () => {
    const nbButtonsBeforeDelete = await interviewGradeComponentsPage.countDeleteButtons();
    await interviewGradeComponentsPage.clickOnLastDeleteButton();

    interviewGradeDeleteDialog = new InterviewGradeDeleteDialog();
    expect(await interviewGradeDeleteDialog.getDialogTitle()).to.eq('hrApp.interviewGrade.delete.question');
    await interviewGradeDeleteDialog.clickOnConfirmButton();

    expect(await interviewGradeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
