import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { WorkEffortComponentsPage, WorkEffortDeleteDialog, WorkEffortUpdatePage } from './work-effort.page-object';

const expect = chai.expect;

describe('WorkEffort e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let workEffortComponentsPage: WorkEffortComponentsPage;
  let workEffortUpdatePage: WorkEffortUpdatePage;
  let workEffortDeleteDialog: WorkEffortDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load WorkEfforts', async () => {
    await navBarPage.goToEntity('work-effort');
    workEffortComponentsPage = new WorkEffortComponentsPage();
    await browser.wait(ec.visibilityOf(workEffortComponentsPage.title), 5000);
    expect(await workEffortComponentsPage.getTitle()).to.eq('hrApp.workEffort.home.title');
    await browser.wait(ec.or(ec.visibilityOf(workEffortComponentsPage.entities), ec.visibilityOf(workEffortComponentsPage.noResult)), 1000);
  });

  it('should load create WorkEffort page', async () => {
    await workEffortComponentsPage.clickOnCreateButton();
    workEffortUpdatePage = new WorkEffortUpdatePage();
    expect(await workEffortUpdatePage.getPageTitle()).to.eq('hrApp.workEffort.home.createOrEditLabel');
    await workEffortUpdatePage.cancel();
  });

  it('should create and save WorkEfforts', async () => {
    const nbButtonsBeforeCreate = await workEffortComponentsPage.countDeleteButtons();

    await workEffortComponentsPage.clickOnCreateButton();

    await promise.all([
      workEffortUpdatePage.setNameInput('name'),
      workEffortUpdatePage.setDescriptionInput('description'),
      workEffortUpdatePage.setCodeInput('code'),
      workEffortUpdatePage.setBatchSizeInput('5'),
      workEffortUpdatePage.setMinYieldRangeInput('5'),
      workEffortUpdatePage.setMaxYieldRangeInput('5'),
      workEffortUpdatePage.setPercentCompleteInput('5'),
      workEffortUpdatePage.setValidationTypeInput('validationType'),
      workEffortUpdatePage.setShelfLifeInput('5'),
      workEffortUpdatePage.setOutputQtyInput('5'),
      workEffortUpdatePage.productSelectLastOption(),
      workEffortUpdatePage.ksmSelectLastOption(),
      workEffortUpdatePage.typeSelectLastOption(),
      workEffortUpdatePage.purposeSelectLastOption(),
      workEffortUpdatePage.statusSelectLastOption(),
      workEffortUpdatePage.facilitySelectLastOption(),
      workEffortUpdatePage.shelfListUomSelectLastOption(),
      workEffortUpdatePage.productCategorySelectLastOption(),
      workEffortUpdatePage.productStoreSelectLastOption(),
    ]);

    expect(await workEffortUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await workEffortUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await workEffortUpdatePage.getCodeInput()).to.eq('code', 'Expected Code value to be equals to code');
    expect(await workEffortUpdatePage.getBatchSizeInput()).to.eq('5', 'Expected batchSize value to be equals to 5');
    expect(await workEffortUpdatePage.getMinYieldRangeInput()).to.eq('5', 'Expected minYieldRange value to be equals to 5');
    expect(await workEffortUpdatePage.getMaxYieldRangeInput()).to.eq('5', 'Expected maxYieldRange value to be equals to 5');
    expect(await workEffortUpdatePage.getPercentCompleteInput()).to.eq('5', 'Expected percentComplete value to be equals to 5');
    expect(await workEffortUpdatePage.getValidationTypeInput()).to.eq(
      'validationType',
      'Expected ValidationType value to be equals to validationType'
    );
    expect(await workEffortUpdatePage.getShelfLifeInput()).to.eq('5', 'Expected shelfLife value to be equals to 5');
    expect(await workEffortUpdatePage.getOutputQtyInput()).to.eq('5', 'Expected outputQty value to be equals to 5');

    await workEffortUpdatePage.save();
    expect(await workEffortUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await workEffortComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last WorkEffort', async () => {
    const nbButtonsBeforeDelete = await workEffortComponentsPage.countDeleteButtons();
    await workEffortComponentsPage.clickOnLastDeleteButton();

    workEffortDeleteDialog = new WorkEffortDeleteDialog();
    expect(await workEffortDeleteDialog.getDialogTitle()).to.eq('hrApp.workEffort.delete.question');
    await workEffortDeleteDialog.clickOnConfirmButton();

    expect(await workEffortComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
