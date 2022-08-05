import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmplLeaveTypeComponentsPage, EmplLeaveTypeDeleteDialog, EmplLeaveTypeUpdatePage } from './empl-leave-type.page-object';

const expect = chai.expect;

describe('EmplLeaveType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let emplLeaveTypeComponentsPage: EmplLeaveTypeComponentsPage;
  let emplLeaveTypeUpdatePage: EmplLeaveTypeUpdatePage;
  let emplLeaveTypeDeleteDialog: EmplLeaveTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmplLeaveTypes', async () => {
    await navBarPage.goToEntity('empl-leave-type');
    emplLeaveTypeComponentsPage = new EmplLeaveTypeComponentsPage();
    await browser.wait(ec.visibilityOf(emplLeaveTypeComponentsPage.title), 5000);
    expect(await emplLeaveTypeComponentsPage.getTitle()).to.eq('hrApp.emplLeaveType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(emplLeaveTypeComponentsPage.entities), ec.visibilityOf(emplLeaveTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EmplLeaveType page', async () => {
    await emplLeaveTypeComponentsPage.clickOnCreateButton();
    emplLeaveTypeUpdatePage = new EmplLeaveTypeUpdatePage();
    expect(await emplLeaveTypeUpdatePage.getPageTitle()).to.eq('hrApp.emplLeaveType.home.createOrEditLabel');
    await emplLeaveTypeUpdatePage.cancel();
  });

  it('should create and save EmplLeaveTypes', async () => {
    const nbButtonsBeforeCreate = await emplLeaveTypeComponentsPage.countDeleteButtons();

    await emplLeaveTypeComponentsPage.clickOnCreateButton();

    await promise.all([emplLeaveTypeUpdatePage.setNameInput('name'), emplLeaveTypeUpdatePage.setDescriptionInput('description')]);

    expect(await emplLeaveTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await emplLeaveTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await emplLeaveTypeUpdatePage.save();
    expect(await emplLeaveTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await emplLeaveTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last EmplLeaveType', async () => {
    const nbButtonsBeforeDelete = await emplLeaveTypeComponentsPage.countDeleteButtons();
    await emplLeaveTypeComponentsPage.clickOnLastDeleteButton();

    emplLeaveTypeDeleteDialog = new EmplLeaveTypeDeleteDialog();
    expect(await emplLeaveTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.emplLeaveType.delete.question');
    await emplLeaveTypeDeleteDialog.clickOnConfirmButton();

    expect(await emplLeaveTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
