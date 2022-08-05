import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmplPositionComponentsPage, EmplPositionDeleteDialog, EmplPositionUpdatePage } from './empl-position.page-object';

const expect = chai.expect;

describe('EmplPosition e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let emplPositionComponentsPage: EmplPositionComponentsPage;
  let emplPositionUpdatePage: EmplPositionUpdatePage;
  let emplPositionDeleteDialog: EmplPositionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmplPositions', async () => {
    await navBarPage.goToEntity('empl-position');
    emplPositionComponentsPage = new EmplPositionComponentsPage();
    await browser.wait(ec.visibilityOf(emplPositionComponentsPage.title), 5000);
    expect(await emplPositionComponentsPage.getTitle()).to.eq('hrApp.emplPosition.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(emplPositionComponentsPage.entities), ec.visibilityOf(emplPositionComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EmplPosition page', async () => {
    await emplPositionComponentsPage.clickOnCreateButton();
    emplPositionUpdatePage = new EmplPositionUpdatePage();
    expect(await emplPositionUpdatePage.getPageTitle()).to.eq('hrApp.emplPosition.home.createOrEditLabel');
    await emplPositionUpdatePage.cancel();
  });

  it('should create and save EmplPositions', async () => {
    const nbButtonsBeforeCreate = await emplPositionComponentsPage.countDeleteButtons();

    await emplPositionComponentsPage.clickOnCreateButton();

    await promise.all([
      emplPositionUpdatePage.setMaxBudgetInput('5'),
      emplPositionUpdatePage.setMinBudgetInput('5'),
      emplPositionUpdatePage.setEstimatedFromDateInput('2000-12-31'),
      emplPositionUpdatePage.setEstimatedThruDateInput('2000-12-31'),
      emplPositionUpdatePage.setActualFromDateInput('2000-12-31'),
      emplPositionUpdatePage.setActualThruDateInput('2000-12-31'),
      emplPositionUpdatePage.typeSelectLastOption(),
      emplPositionUpdatePage.partySelectLastOption(),
      emplPositionUpdatePage.statusSelectLastOption(),
    ]);

    expect(await emplPositionUpdatePage.getMaxBudgetInput()).to.eq('5', 'Expected maxBudget value to be equals to 5');
    expect(await emplPositionUpdatePage.getMinBudgetInput()).to.eq('5', 'Expected minBudget value to be equals to 5');
    expect(await emplPositionUpdatePage.getEstimatedFromDateInput()).to.eq(
      '2000-12-31',
      'Expected estimatedFromDate value to be equals to 2000-12-31'
    );
    expect(await emplPositionUpdatePage.getEstimatedThruDateInput()).to.eq(
      '2000-12-31',
      'Expected estimatedThruDate value to be equals to 2000-12-31'
    );
    const selectedPaidJob = emplPositionUpdatePage.getPaidJobInput();
    if (await selectedPaidJob.isSelected()) {
      await emplPositionUpdatePage.getPaidJobInput().click();
      expect(await emplPositionUpdatePage.getPaidJobInput().isSelected(), 'Expected paidJob not to be selected').to.be.false;
    } else {
      await emplPositionUpdatePage.getPaidJobInput().click();
      expect(await emplPositionUpdatePage.getPaidJobInput().isSelected(), 'Expected paidJob to be selected').to.be.true;
    }
    const selectedIsFulltime = emplPositionUpdatePage.getIsFulltimeInput();
    if (await selectedIsFulltime.isSelected()) {
      await emplPositionUpdatePage.getIsFulltimeInput().click();
      expect(await emplPositionUpdatePage.getIsFulltimeInput().isSelected(), 'Expected isFulltime not to be selected').to.be.false;
    } else {
      await emplPositionUpdatePage.getIsFulltimeInput().click();
      expect(await emplPositionUpdatePage.getIsFulltimeInput().isSelected(), 'Expected isFulltime to be selected').to.be.true;
    }
    const selectedIsTemporary = emplPositionUpdatePage.getIsTemporaryInput();
    if (await selectedIsTemporary.isSelected()) {
      await emplPositionUpdatePage.getIsTemporaryInput().click();
      expect(await emplPositionUpdatePage.getIsTemporaryInput().isSelected(), 'Expected isTemporary not to be selected').to.be.false;
    } else {
      await emplPositionUpdatePage.getIsTemporaryInput().click();
      expect(await emplPositionUpdatePage.getIsTemporaryInput().isSelected(), 'Expected isTemporary to be selected').to.be.true;
    }
    expect(await emplPositionUpdatePage.getActualFromDateInput()).to.eq(
      '2000-12-31',
      'Expected actualFromDate value to be equals to 2000-12-31'
    );
    expect(await emplPositionUpdatePage.getActualThruDateInput()).to.eq(
      '2000-12-31',
      'Expected actualThruDate value to be equals to 2000-12-31'
    );

    await emplPositionUpdatePage.save();
    expect(await emplPositionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await emplPositionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last EmplPosition', async () => {
    const nbButtonsBeforeDelete = await emplPositionComponentsPage.countDeleteButtons();
    await emplPositionComponentsPage.clickOnLastDeleteButton();

    emplPositionDeleteDialog = new EmplPositionDeleteDialog();
    expect(await emplPositionDeleteDialog.getDialogTitle()).to.eq('hrApp.emplPosition.delete.question');
    await emplPositionDeleteDialog.clickOnConfirmButton();

    expect(await emplPositionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
