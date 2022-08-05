import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ExamTypeComponentsPage, ExamTypeDeleteDialog, ExamTypeUpdatePage } from './exam-type.page-object';

const expect = chai.expect;

describe('ExamType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let examTypeComponentsPage: ExamTypeComponentsPage;
  let examTypeUpdatePage: ExamTypeUpdatePage;
  let examTypeDeleteDialog: ExamTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ExamTypes', async () => {
    await navBarPage.goToEntity('exam-type');
    examTypeComponentsPage = new ExamTypeComponentsPage();
    await browser.wait(ec.visibilityOf(examTypeComponentsPage.title), 5000);
    expect(await examTypeComponentsPage.getTitle()).to.eq('hrApp.examType.home.title');
    await browser.wait(ec.or(ec.visibilityOf(examTypeComponentsPage.entities), ec.visibilityOf(examTypeComponentsPage.noResult)), 1000);
  });

  it('should load create ExamType page', async () => {
    await examTypeComponentsPage.clickOnCreateButton();
    examTypeUpdatePage = new ExamTypeUpdatePage();
    expect(await examTypeUpdatePage.getPageTitle()).to.eq('hrApp.examType.home.createOrEditLabel');
    await examTypeUpdatePage.cancel();
  });

  it('should create and save ExamTypes', async () => {
    const nbButtonsBeforeCreate = await examTypeComponentsPage.countDeleteButtons();

    await examTypeComponentsPage.clickOnCreateButton();

    await promise.all([examTypeUpdatePage.setNameInput('name'), examTypeUpdatePage.setDescriptionInput('description')]);

    expect(await examTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await examTypeUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

    await examTypeUpdatePage.save();
    expect(await examTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await examTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ExamType', async () => {
    const nbButtonsBeforeDelete = await examTypeComponentsPage.countDeleteButtons();
    await examTypeComponentsPage.clickOnLastDeleteButton();

    examTypeDeleteDialog = new ExamTypeDeleteDialog();
    expect(await examTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.examType.delete.question');
    await examTypeDeleteDialog.clickOnConfirmButton();

    expect(await examTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
