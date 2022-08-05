import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PayGradeComponentsPage, PayGradeDeleteDialog, PayGradeUpdatePage } from './pay-grade.page-object';

const expect = chai.expect;

describe('PayGrade e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let payGradeComponentsPage: PayGradeComponentsPage;
  let payGradeUpdatePage: PayGradeUpdatePage;
  let payGradeDeleteDialog: PayGradeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PayGrades', async () => {
    await navBarPage.goToEntity('pay-grade');
    payGradeComponentsPage = new PayGradeComponentsPage();
    await browser.wait(ec.visibilityOf(payGradeComponentsPage.title), 5000);
    expect(await payGradeComponentsPage.getTitle()).to.eq('hrApp.payGrade.home.title');
    await browser.wait(ec.or(ec.visibilityOf(payGradeComponentsPage.entities), ec.visibilityOf(payGradeComponentsPage.noResult)), 1000);
  });

  it('should load create PayGrade page', async () => {
    await payGradeComponentsPage.clickOnCreateButton();
    payGradeUpdatePage = new PayGradeUpdatePage();
    expect(await payGradeUpdatePage.getPageTitle()).to.eq('hrApp.payGrade.home.createOrEditLabel');
    await payGradeUpdatePage.cancel();
  });

  it('should create and save PayGrades', async () => {
    const nbButtonsBeforeCreate = await payGradeComponentsPage.countDeleteButtons();

    await payGradeComponentsPage.clickOnCreateButton();

    await promise.all([payGradeUpdatePage.setNameInput('name'), payGradeUpdatePage.setDescriptionInput('description')]);

    expect(await payGradeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await payGradeUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

    await payGradeUpdatePage.save();
    expect(await payGradeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await payGradeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PayGrade', async () => {
    const nbButtonsBeforeDelete = await payGradeComponentsPage.countDeleteButtons();
    await payGradeComponentsPage.clickOnLastDeleteButton();

    payGradeDeleteDialog = new PayGradeDeleteDialog();
    expect(await payGradeDeleteDialog.getDialogTitle()).to.eq('hrApp.payGrade.delete.question');
    await payGradeDeleteDialog.clickOnConfirmButton();

    expect(await payGradeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
