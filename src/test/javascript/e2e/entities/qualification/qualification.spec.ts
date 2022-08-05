import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { QualificationComponentsPage, QualificationDeleteDialog, QualificationUpdatePage } from './qualification.page-object';

const expect = chai.expect;

describe('Qualification e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let qualificationComponentsPage: QualificationComponentsPage;
  let qualificationUpdatePage: QualificationUpdatePage;
  let qualificationDeleteDialog: QualificationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Qualifications', async () => {
    await navBarPage.goToEntity('qualification');
    qualificationComponentsPage = new QualificationComponentsPage();
    await browser.wait(ec.visibilityOf(qualificationComponentsPage.title), 5000);
    expect(await qualificationComponentsPage.getTitle()).to.eq('hrApp.qualification.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(qualificationComponentsPage.entities), ec.visibilityOf(qualificationComponentsPage.noResult)),
      1000
    );
  });

  it('should load create Qualification page', async () => {
    await qualificationComponentsPage.clickOnCreateButton();
    qualificationUpdatePage = new QualificationUpdatePage();
    expect(await qualificationUpdatePage.getPageTitle()).to.eq('hrApp.qualification.home.createOrEditLabel');
    await qualificationUpdatePage.cancel();
  });

  it('should create and save Qualifications', async () => {
    const nbButtonsBeforeCreate = await qualificationComponentsPage.countDeleteButtons();

    await qualificationComponentsPage.clickOnCreateButton();

    await promise.all([qualificationUpdatePage.setNameInput('name'), qualificationUpdatePage.setDescriptionInput('description')]);

    expect(await qualificationUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await qualificationUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await qualificationUpdatePage.save();
    expect(await qualificationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await qualificationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Qualification', async () => {
    const nbButtonsBeforeDelete = await qualificationComponentsPage.countDeleteButtons();
    await qualificationComponentsPage.clickOnLastDeleteButton();

    qualificationDeleteDialog = new QualificationDeleteDialog();
    expect(await qualificationDeleteDialog.getDialogTitle()).to.eq('hrApp.qualification.delete.question');
    await qualificationDeleteDialog.clickOnConfirmButton();

    expect(await qualificationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
