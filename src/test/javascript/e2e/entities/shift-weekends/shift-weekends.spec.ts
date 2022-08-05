import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ShiftWeekendsComponentsPage, ShiftWeekendsDeleteDialog, ShiftWeekendsUpdatePage } from './shift-weekends.page-object';

const expect = chai.expect;

describe('ShiftWeekends e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let shiftWeekendsComponentsPage: ShiftWeekendsComponentsPage;
  let shiftWeekendsUpdatePage: ShiftWeekendsUpdatePage;
  let shiftWeekendsDeleteDialog: ShiftWeekendsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ShiftWeekends', async () => {
    await navBarPage.goToEntity('shift-weekends');
    shiftWeekendsComponentsPage = new ShiftWeekendsComponentsPage();
    await browser.wait(ec.visibilityOf(shiftWeekendsComponentsPage.title), 5000);
    expect(await shiftWeekendsComponentsPage.getTitle()).to.eq('hrApp.shiftWeekends.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(shiftWeekendsComponentsPage.entities), ec.visibilityOf(shiftWeekendsComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ShiftWeekends page', async () => {
    await shiftWeekendsComponentsPage.clickOnCreateButton();
    shiftWeekendsUpdatePage = new ShiftWeekendsUpdatePage();
    expect(await shiftWeekendsUpdatePage.getPageTitle()).to.eq('hrApp.shiftWeekends.home.createOrEditLabel');
    await shiftWeekendsUpdatePage.cancel();
  });

  it('should create and save ShiftWeekends', async () => {
    const nbButtonsBeforeCreate = await shiftWeekendsComponentsPage.countDeleteButtons();

    await shiftWeekendsComponentsPage.clickOnCreateButton();

    await promise.all([
      shiftWeekendsUpdatePage.setFromDateInput('2000-12-31'),
      shiftWeekendsUpdatePage.setThruDateInput('2000-12-31'),
      shiftWeekendsUpdatePage.shiftSelectLastOption(),
    ]);

    expect(await shiftWeekendsUpdatePage.getFromDateInput()).to.eq('2000-12-31', 'Expected fromDate value to be equals to 2000-12-31');
    expect(await shiftWeekendsUpdatePage.getThruDateInput()).to.eq('2000-12-31', 'Expected thruDate value to be equals to 2000-12-31');

    await shiftWeekendsUpdatePage.save();
    expect(await shiftWeekendsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await shiftWeekendsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ShiftWeekends', async () => {
    const nbButtonsBeforeDelete = await shiftWeekendsComponentsPage.countDeleteButtons();
    await shiftWeekendsComponentsPage.clickOnLastDeleteButton();

    shiftWeekendsDeleteDialog = new ShiftWeekendsDeleteDialog();
    expect(await shiftWeekendsDeleteDialog.getDialogTitle()).to.eq('hrApp.shiftWeekends.delete.question');
    await shiftWeekendsDeleteDialog.clickOnConfirmButton();

    expect(await shiftWeekendsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
