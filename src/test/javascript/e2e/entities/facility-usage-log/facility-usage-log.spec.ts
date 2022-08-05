import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FacilityUsageLogComponentsPage, FacilityUsageLogDeleteDialog, FacilityUsageLogUpdatePage } from './facility-usage-log.page-object';

const expect = chai.expect;

describe('FacilityUsageLog e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let facilityUsageLogComponentsPage: FacilityUsageLogComponentsPage;
  let facilityUsageLogUpdatePage: FacilityUsageLogUpdatePage;
  let facilityUsageLogDeleteDialog: FacilityUsageLogDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FacilityUsageLogs', async () => {
    await navBarPage.goToEntity('facility-usage-log');
    facilityUsageLogComponentsPage = new FacilityUsageLogComponentsPage();
    await browser.wait(ec.visibilityOf(facilityUsageLogComponentsPage.title), 5000);
    expect(await facilityUsageLogComponentsPage.getTitle()).to.eq('hrApp.facilityUsageLog.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(facilityUsageLogComponentsPage.entities), ec.visibilityOf(facilityUsageLogComponentsPage.noResult)),
      1000
    );
  });

  it('should load create FacilityUsageLog page', async () => {
    await facilityUsageLogComponentsPage.clickOnCreateButton();
    facilityUsageLogUpdatePage = new FacilityUsageLogUpdatePage();
    expect(await facilityUsageLogUpdatePage.getPageTitle()).to.eq('hrApp.facilityUsageLog.home.createOrEditLabel');
    await facilityUsageLogUpdatePage.cancel();
  });

  it('should create and save FacilityUsageLogs', async () => {
    const nbButtonsBeforeCreate = await facilityUsageLogComponentsPage.countDeleteButtons();

    await facilityUsageLogComponentsPage.clickOnCreateButton();

    await promise.all([
      facilityUsageLogUpdatePage.setFromTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      facilityUsageLogUpdatePage.setToTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      facilityUsageLogUpdatePage.setRemarksInput('remarks'),
      facilityUsageLogUpdatePage.facilitySelectLastOption(),
      facilityUsageLogUpdatePage.cleanedBySelectLastOption(),
      facilityUsageLogUpdatePage.checkedBySelectLastOption(),
    ]);

    expect(await facilityUsageLogUpdatePage.getFromTimeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromTime value to be equals to 2000-12-31'
    );
    expect(await facilityUsageLogUpdatePage.getToTimeInput()).to.contain(
      '2001-01-01T02:30',
      'Expected toTime value to be equals to 2000-12-31'
    );
    expect(await facilityUsageLogUpdatePage.getRemarksInput()).to.eq('remarks', 'Expected Remarks value to be equals to remarks');

    await facilityUsageLogUpdatePage.save();
    expect(await facilityUsageLogUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await facilityUsageLogComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FacilityUsageLog', async () => {
    const nbButtonsBeforeDelete = await facilityUsageLogComponentsPage.countDeleteButtons();
    await facilityUsageLogComponentsPage.clickOnLastDeleteButton();

    facilityUsageLogDeleteDialog = new FacilityUsageLogDeleteDialog();
    expect(await facilityUsageLogDeleteDialog.getDialogTitle()).to.eq('hrApp.facilityUsageLog.delete.question');
    await facilityUsageLogDeleteDialog.clickOnConfirmButton();

    expect(await facilityUsageLogComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
