import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  EmplPositionTypeRateComponentsPage,
  EmplPositionTypeRateDeleteDialog,
  EmplPositionTypeRateUpdatePage,
} from './empl-position-type-rate.page-object';

const expect = chai.expect;

describe('EmplPositionTypeRate e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let emplPositionTypeRateComponentsPage: EmplPositionTypeRateComponentsPage;
  let emplPositionTypeRateUpdatePage: EmplPositionTypeRateUpdatePage;
  let emplPositionTypeRateDeleteDialog: EmplPositionTypeRateDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmplPositionTypeRates', async () => {
    await navBarPage.goToEntity('empl-position-type-rate');
    emplPositionTypeRateComponentsPage = new EmplPositionTypeRateComponentsPage();
    await browser.wait(ec.visibilityOf(emplPositionTypeRateComponentsPage.title), 5000);
    expect(await emplPositionTypeRateComponentsPage.getTitle()).to.eq('hrApp.emplPositionTypeRate.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(emplPositionTypeRateComponentsPage.entities), ec.visibilityOf(emplPositionTypeRateComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EmplPositionTypeRate page', async () => {
    await emplPositionTypeRateComponentsPage.clickOnCreateButton();
    emplPositionTypeRateUpdatePage = new EmplPositionTypeRateUpdatePage();
    expect(await emplPositionTypeRateUpdatePage.getPageTitle()).to.eq('hrApp.emplPositionTypeRate.home.createOrEditLabel');
    await emplPositionTypeRateUpdatePage.cancel();
  });

  it('should create and save EmplPositionTypeRates', async () => {
    const nbButtonsBeforeCreate = await emplPositionTypeRateComponentsPage.countDeleteButtons();

    await emplPositionTypeRateComponentsPage.clickOnCreateButton();

    await promise.all([
      emplPositionTypeRateUpdatePage.setRateAmountInput('5'),
      emplPositionTypeRateUpdatePage.setFromDateInput('2000-12-31'),
      emplPositionTypeRateUpdatePage.setThruDateInput('2000-12-31'),
      emplPositionTypeRateUpdatePage.rateTypeSelectLastOption(),
      emplPositionTypeRateUpdatePage.emplPositionTypeSelectLastOption(),
      emplPositionTypeRateUpdatePage.payGradeSelectLastOption(),
    ]);

    expect(await emplPositionTypeRateUpdatePage.getRateAmountInput()).to.eq('5', 'Expected rateAmount value to be equals to 5');
    expect(await emplPositionTypeRateUpdatePage.getFromDateInput()).to.eq(
      '2000-12-31',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await emplPositionTypeRateUpdatePage.getThruDateInput()).to.eq(
      '2000-12-31',
      'Expected thruDate value to be equals to 2000-12-31'
    );

    await emplPositionTypeRateUpdatePage.save();
    expect(await emplPositionTypeRateUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await emplPositionTypeRateComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EmplPositionTypeRate', async () => {
    const nbButtonsBeforeDelete = await emplPositionTypeRateComponentsPage.countDeleteButtons();
    await emplPositionTypeRateComponentsPage.clickOnLastDeleteButton();

    emplPositionTypeRateDeleteDialog = new EmplPositionTypeRateDeleteDialog();
    expect(await emplPositionTypeRateDeleteDialog.getDialogTitle()).to.eq('hrApp.emplPositionTypeRate.delete.question');
    await emplPositionTypeRateDeleteDialog.clickOnConfirmButton();

    expect(await emplPositionTypeRateComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
