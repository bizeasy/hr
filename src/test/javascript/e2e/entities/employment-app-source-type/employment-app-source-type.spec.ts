import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  EmploymentAppSourceTypeComponentsPage,
  EmploymentAppSourceTypeDeleteDialog,
  EmploymentAppSourceTypeUpdatePage,
} from './employment-app-source-type.page-object';

const expect = chai.expect;

describe('EmploymentAppSourceType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employmentAppSourceTypeComponentsPage: EmploymentAppSourceTypeComponentsPage;
  let employmentAppSourceTypeUpdatePage: EmploymentAppSourceTypeUpdatePage;
  let employmentAppSourceTypeDeleteDialog: EmploymentAppSourceTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmploymentAppSourceTypes', async () => {
    await navBarPage.goToEntity('employment-app-source-type');
    employmentAppSourceTypeComponentsPage = new EmploymentAppSourceTypeComponentsPage();
    await browser.wait(ec.visibilityOf(employmentAppSourceTypeComponentsPage.title), 5000);
    expect(await employmentAppSourceTypeComponentsPage.getTitle()).to.eq('hrApp.employmentAppSourceType.home.title');
    await browser.wait(
      ec.or(
        ec.visibilityOf(employmentAppSourceTypeComponentsPage.entities),
        ec.visibilityOf(employmentAppSourceTypeComponentsPage.noResult)
      ),
      1000
    );
  });

  it('should load create EmploymentAppSourceType page', async () => {
    await employmentAppSourceTypeComponentsPage.clickOnCreateButton();
    employmentAppSourceTypeUpdatePage = new EmploymentAppSourceTypeUpdatePage();
    expect(await employmentAppSourceTypeUpdatePage.getPageTitle()).to.eq('hrApp.employmentAppSourceType.home.createOrEditLabel');
    await employmentAppSourceTypeUpdatePage.cancel();
  });

  it('should create and save EmploymentAppSourceTypes', async () => {
    const nbButtonsBeforeCreate = await employmentAppSourceTypeComponentsPage.countDeleteButtons();

    await employmentAppSourceTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      employmentAppSourceTypeUpdatePage.setNameInput('name'),
      employmentAppSourceTypeUpdatePage.setDescriptionInput('description'),
    ]);

    expect(await employmentAppSourceTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await employmentAppSourceTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await employmentAppSourceTypeUpdatePage.save();
    expect(await employmentAppSourceTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await employmentAppSourceTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EmploymentAppSourceType', async () => {
    const nbButtonsBeforeDelete = await employmentAppSourceTypeComponentsPage.countDeleteButtons();
    await employmentAppSourceTypeComponentsPage.clickOnLastDeleteButton();

    employmentAppSourceTypeDeleteDialog = new EmploymentAppSourceTypeDeleteDialog();
    expect(await employmentAppSourceTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.employmentAppSourceType.delete.question');
    await employmentAppSourceTypeDeleteDialog.clickOnConfirmButton();

    expect(await employmentAppSourceTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
