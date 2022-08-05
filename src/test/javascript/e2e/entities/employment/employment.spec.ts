import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmploymentComponentsPage, EmploymentDeleteDialog, EmploymentUpdatePage } from './employment.page-object';

const expect = chai.expect;

describe('Employment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employmentComponentsPage: EmploymentComponentsPage;
  let employmentUpdatePage: EmploymentUpdatePage;
  let employmentDeleteDialog: EmploymentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Employments', async () => {
    await navBarPage.goToEntity('employment');
    employmentComponentsPage = new EmploymentComponentsPage();
    await browser.wait(ec.visibilityOf(employmentComponentsPage.title), 5000);
    expect(await employmentComponentsPage.getTitle()).to.eq('hrApp.employment.home.title');
    await browser.wait(ec.or(ec.visibilityOf(employmentComponentsPage.entities), ec.visibilityOf(employmentComponentsPage.noResult)), 1000);
  });

  it('should load create Employment page', async () => {
    await employmentComponentsPage.clickOnCreateButton();
    employmentUpdatePage = new EmploymentUpdatePage();
    expect(await employmentUpdatePage.getPageTitle()).to.eq('hrApp.employment.home.createOrEditLabel');
    await employmentUpdatePage.cancel();
  });

  it('should create and save Employments', async () => {
    const nbButtonsBeforeCreate = await employmentComponentsPage.countDeleteButtons();

    await employmentComponentsPage.clickOnCreateButton();

    await promise.all([
      employmentUpdatePage.setFromDateInput('2000-12-31'),
      employmentUpdatePage.setThruDateInput('2000-12-31'),
      employmentUpdatePage.terminationReasonSelectLastOption(),
      employmentUpdatePage.terminationTypeSelectLastOption(),
      employmentUpdatePage.employeeSelectLastOption(),
      employmentUpdatePage.fromPartySelectLastOption(),
      employmentUpdatePage.roleTypeFromSelectLastOption(),
      employmentUpdatePage.roleTypeToSelectLastOption(),
    ]);

    expect(await employmentUpdatePage.getFromDateInput()).to.eq('2000-12-31', 'Expected fromDate value to be equals to 2000-12-31');
    expect(await employmentUpdatePage.getThruDateInput()).to.eq('2000-12-31', 'Expected thruDate value to be equals to 2000-12-31');

    await employmentUpdatePage.save();
    expect(await employmentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await employmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Employment', async () => {
    const nbButtonsBeforeDelete = await employmentComponentsPage.countDeleteButtons();
    await employmentComponentsPage.clickOnLastDeleteButton();

    employmentDeleteDialog = new EmploymentDeleteDialog();
    expect(await employmentDeleteDialog.getDialogTitle()).to.eq('hrApp.employment.delete.question');
    await employmentDeleteDialog.clickOnConfirmButton();

    expect(await employmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
