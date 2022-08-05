import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CustomTimePeriodComponentsPage, CustomTimePeriodDeleteDialog, CustomTimePeriodUpdatePage } from './custom-time-period.page-object';

const expect = chai.expect;

describe('CustomTimePeriod e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let customTimePeriodComponentsPage: CustomTimePeriodComponentsPage;
  let customTimePeriodUpdatePage: CustomTimePeriodUpdatePage;
  let customTimePeriodDeleteDialog: CustomTimePeriodDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CustomTimePeriods', async () => {
    await navBarPage.goToEntity('custom-time-period');
    customTimePeriodComponentsPage = new CustomTimePeriodComponentsPage();
    await browser.wait(ec.visibilityOf(customTimePeriodComponentsPage.title), 5000);
    expect(await customTimePeriodComponentsPage.getTitle()).to.eq('hrApp.customTimePeriod.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(customTimePeriodComponentsPage.entities), ec.visibilityOf(customTimePeriodComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CustomTimePeriod page', async () => {
    await customTimePeriodComponentsPage.clickOnCreateButton();
    customTimePeriodUpdatePage = new CustomTimePeriodUpdatePage();
    expect(await customTimePeriodUpdatePage.getPageTitle()).to.eq('hrApp.customTimePeriod.home.createOrEditLabel');
    await customTimePeriodUpdatePage.cancel();
  });

  it('should create and save CustomTimePeriods', async () => {
    const nbButtonsBeforeCreate = await customTimePeriodComponentsPage.countDeleteButtons();

    await customTimePeriodComponentsPage.clickOnCreateButton();

    await promise.all([
      customTimePeriodUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      customTimePeriodUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      customTimePeriodUpdatePage.setPeriodNameInput('periodName'),
      customTimePeriodUpdatePage.setPeriodNumInput('5'),
      customTimePeriodUpdatePage.periodTypeSelectLastOption(),
      customTimePeriodUpdatePage.organisationPartySelectLastOption(),
      customTimePeriodUpdatePage.parentSelectLastOption(),
    ]);

    expect(await customTimePeriodUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await customTimePeriodUpdatePage.getThruDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected thruDate value to be equals to 2000-12-31'
    );
    const selectedIsClosed = customTimePeriodUpdatePage.getIsClosedInput();
    if (await selectedIsClosed.isSelected()) {
      await customTimePeriodUpdatePage.getIsClosedInput().click();
      expect(await customTimePeriodUpdatePage.getIsClosedInput().isSelected(), 'Expected isClosed not to be selected').to.be.false;
    } else {
      await customTimePeriodUpdatePage.getIsClosedInput().click();
      expect(await customTimePeriodUpdatePage.getIsClosedInput().isSelected(), 'Expected isClosed to be selected').to.be.true;
    }
    expect(await customTimePeriodUpdatePage.getPeriodNameInput()).to.eq(
      'periodName',
      'Expected PeriodName value to be equals to periodName'
    );
    expect(await customTimePeriodUpdatePage.getPeriodNumInput()).to.eq('5', 'Expected periodNum value to be equals to 5');

    await customTimePeriodUpdatePage.save();
    expect(await customTimePeriodUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await customTimePeriodComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CustomTimePeriod', async () => {
    const nbButtonsBeforeDelete = await customTimePeriodComponentsPage.countDeleteButtons();
    await customTimePeriodComponentsPage.clickOnLastDeleteButton();

    customTimePeriodDeleteDialog = new CustomTimePeriodDeleteDialog();
    expect(await customTimePeriodDeleteDialog.getDialogTitle()).to.eq('hrApp.customTimePeriod.delete.question');
    await customTimePeriodDeleteDialog.clickOnConfirmButton();

    expect(await customTimePeriodComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
