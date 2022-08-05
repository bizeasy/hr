import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PublicHolidaysComponentsPage, PublicHolidaysDeleteDialog, PublicHolidaysUpdatePage } from './public-holidays.page-object';

const expect = chai.expect;

describe('PublicHolidays e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let publicHolidaysComponentsPage: PublicHolidaysComponentsPage;
  let publicHolidaysUpdatePage: PublicHolidaysUpdatePage;
  let publicHolidaysDeleteDialog: PublicHolidaysDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PublicHolidays', async () => {
    await navBarPage.goToEntity('public-holidays');
    publicHolidaysComponentsPage = new PublicHolidaysComponentsPage();
    await browser.wait(ec.visibilityOf(publicHolidaysComponentsPage.title), 5000);
    expect(await publicHolidaysComponentsPage.getTitle()).to.eq('hrApp.publicHolidays.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(publicHolidaysComponentsPage.entities), ec.visibilityOf(publicHolidaysComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PublicHolidays page', async () => {
    await publicHolidaysComponentsPage.clickOnCreateButton();
    publicHolidaysUpdatePage = new PublicHolidaysUpdatePage();
    expect(await publicHolidaysUpdatePage.getPageTitle()).to.eq('hrApp.publicHolidays.home.createOrEditLabel');
    await publicHolidaysUpdatePage.cancel();
  });

  it('should create and save PublicHolidays', async () => {
    const nbButtonsBeforeCreate = await publicHolidaysComponentsPage.countDeleteButtons();

    await publicHolidaysComponentsPage.clickOnCreateButton();

    await promise.all([
      publicHolidaysUpdatePage.setNameInput('name'),
      publicHolidaysUpdatePage.setFromDateInput('2000-12-31'),
      publicHolidaysUpdatePage.setThruDateInput('2000-12-31'),
      publicHolidaysUpdatePage.setNoOfHolidaysInput('5'),
      publicHolidaysUpdatePage.typeSelectLastOption(),
    ]);

    expect(await publicHolidaysUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await publicHolidaysUpdatePage.getFromDateInput()).to.eq('2000-12-31', 'Expected fromDate value to be equals to 2000-12-31');
    expect(await publicHolidaysUpdatePage.getThruDateInput()).to.eq('2000-12-31', 'Expected thruDate value to be equals to 2000-12-31');
    expect(await publicHolidaysUpdatePage.getNoOfHolidaysInput()).to.eq('5', 'Expected noOfHolidays value to be equals to 5');

    await publicHolidaysUpdatePage.save();
    expect(await publicHolidaysUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await publicHolidaysComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PublicHolidays', async () => {
    const nbButtonsBeforeDelete = await publicHolidaysComponentsPage.countDeleteButtons();
    await publicHolidaysComponentsPage.clickOnLastDeleteButton();

    publicHolidaysDeleteDialog = new PublicHolidaysDeleteDialog();
    expect(await publicHolidaysDeleteDialog.getDialogTitle()).to.eq('hrApp.publicHolidays.delete.question');
    await publicHolidaysDeleteDialog.clickOnConfirmButton();

    expect(await publicHolidaysComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
