import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmploymentAppComponentsPage, EmploymentAppDeleteDialog, EmploymentAppUpdatePage } from './employment-app.page-object';

const expect = chai.expect;

describe('EmploymentApp e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let employmentAppComponentsPage: EmploymentAppComponentsPage;
  let employmentAppUpdatePage: EmploymentAppUpdatePage;
  let employmentAppDeleteDialog: EmploymentAppDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmploymentApps', async () => {
    await navBarPage.goToEntity('employment-app');
    employmentAppComponentsPage = new EmploymentAppComponentsPage();
    await browser.wait(ec.visibilityOf(employmentAppComponentsPage.title), 5000);
    expect(await employmentAppComponentsPage.getTitle()).to.eq('hrApp.employmentApp.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(employmentAppComponentsPage.entities), ec.visibilityOf(employmentAppComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EmploymentApp page', async () => {
    await employmentAppComponentsPage.clickOnCreateButton();
    employmentAppUpdatePage = new EmploymentAppUpdatePage();
    expect(await employmentAppUpdatePage.getPageTitle()).to.eq('hrApp.employmentApp.home.createOrEditLabel');
    await employmentAppUpdatePage.cancel();
  });

  it('should create and save EmploymentApps', async () => {
    const nbButtonsBeforeCreate = await employmentAppComponentsPage.countDeleteButtons();

    await employmentAppComponentsPage.clickOnCreateButton();

    await promise.all([
      employmentAppUpdatePage.setApplicationDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      employmentAppUpdatePage.emplPositionSelectLastOption(),
      employmentAppUpdatePage.statusSelectLastOption(),
      employmentAppUpdatePage.sourceSelectLastOption(),
      employmentAppUpdatePage.applyingPartySelectLastOption(),
      employmentAppUpdatePage.referredByPartySelectLastOption(),
      employmentAppUpdatePage.approverPartySelectLastOption(),
      employmentAppUpdatePage.jobRequisitionSelectLastOption(),
    ]);

    expect(await employmentAppUpdatePage.getApplicationDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected applicationDate value to be equals to 2000-12-31'
    );

    await employmentAppUpdatePage.save();
    expect(await employmentAppUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await employmentAppComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last EmploymentApp', async () => {
    const nbButtonsBeforeDelete = await employmentAppComponentsPage.countDeleteButtons();
    await employmentAppComponentsPage.clickOnLastDeleteButton();

    employmentAppDeleteDialog = new EmploymentAppDeleteDialog();
    expect(await employmentAppDeleteDialog.getDialogTitle()).to.eq('hrApp.employmentApp.delete.question');
    await employmentAppDeleteDialog.clickOnConfirmButton();

    expect(await employmentAppComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
