import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PayrollPreferenceComponentsPage,
  PayrollPreferenceDeleteDialog,
  PayrollPreferenceUpdatePage,
} from './payroll-preference.page-object';

const expect = chai.expect;

describe('PayrollPreference e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let payrollPreferenceComponentsPage: PayrollPreferenceComponentsPage;
  let payrollPreferenceUpdatePage: PayrollPreferenceUpdatePage;
  let payrollPreferenceDeleteDialog: PayrollPreferenceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PayrollPreferences', async () => {
    await navBarPage.goToEntity('payroll-preference');
    payrollPreferenceComponentsPage = new PayrollPreferenceComponentsPage();
    await browser.wait(ec.visibilityOf(payrollPreferenceComponentsPage.title), 5000);
    expect(await payrollPreferenceComponentsPage.getTitle()).to.eq('hrApp.payrollPreference.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(payrollPreferenceComponentsPage.entities), ec.visibilityOf(payrollPreferenceComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PayrollPreference page', async () => {
    await payrollPreferenceComponentsPage.clickOnCreateButton();
    payrollPreferenceUpdatePage = new PayrollPreferenceUpdatePage();
    expect(await payrollPreferenceUpdatePage.getPageTitle()).to.eq('hrApp.payrollPreference.home.createOrEditLabel');
    await payrollPreferenceUpdatePage.cancel();
  });

  it('should create and save PayrollPreferences', async () => {
    const nbButtonsBeforeCreate = await payrollPreferenceComponentsPage.countDeleteButtons();

    await payrollPreferenceComponentsPage.clickOnCreateButton();

    await promise.all([
      payrollPreferenceUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      payrollPreferenceUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      payrollPreferenceUpdatePage.setSequenceNoInput('5'),
      payrollPreferenceUpdatePage.setPercentageInput('5'),
      payrollPreferenceUpdatePage.setFlatAmountInput('5'),
      payrollPreferenceUpdatePage.setAccountNumberInput('accountNumber'),
      payrollPreferenceUpdatePage.setBankNameInput('bankName'),
      payrollPreferenceUpdatePage.setIfscCodeInput('ifscCode'),
      payrollPreferenceUpdatePage.setBranchInput('branch'),
      payrollPreferenceUpdatePage.employeeSelectLastOption(),
      payrollPreferenceUpdatePage.deductionTypeSelectLastOption(),
      payrollPreferenceUpdatePage.paymentMethodTypeSelectLastOption(),
      payrollPreferenceUpdatePage.periodTypeSelectLastOption(),
    ]);

    expect(await payrollPreferenceUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await payrollPreferenceUpdatePage.getThruDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected thruDate value to be equals to 2000-12-31'
    );
    expect(await payrollPreferenceUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');
    expect(await payrollPreferenceUpdatePage.getPercentageInput()).to.eq('5', 'Expected percentage value to be equals to 5');
    expect(await payrollPreferenceUpdatePage.getFlatAmountInput()).to.eq('5', 'Expected flatAmount value to be equals to 5');
    expect(await payrollPreferenceUpdatePage.getAccountNumberInput()).to.eq(
      'accountNumber',
      'Expected AccountNumber value to be equals to accountNumber'
    );
    expect(await payrollPreferenceUpdatePage.getBankNameInput()).to.eq('bankName', 'Expected BankName value to be equals to bankName');
    expect(await payrollPreferenceUpdatePage.getIfscCodeInput()).to.eq('ifscCode', 'Expected IfscCode value to be equals to ifscCode');
    expect(await payrollPreferenceUpdatePage.getBranchInput()).to.eq('branch', 'Expected Branch value to be equals to branch');

    await payrollPreferenceUpdatePage.save();
    expect(await payrollPreferenceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await payrollPreferenceComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PayrollPreference', async () => {
    const nbButtonsBeforeDelete = await payrollPreferenceComponentsPage.countDeleteButtons();
    await payrollPreferenceComponentsPage.clickOnLastDeleteButton();

    payrollPreferenceDeleteDialog = new PayrollPreferenceDeleteDialog();
    expect(await payrollPreferenceDeleteDialog.getDialogTitle()).to.eq('hrApp.payrollPreference.delete.question');
    await payrollPreferenceDeleteDialog.clickOnConfirmButton();

    expect(await payrollPreferenceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
