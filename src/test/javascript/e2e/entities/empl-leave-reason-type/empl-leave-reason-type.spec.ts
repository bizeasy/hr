import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  EmplLeaveReasonTypeComponentsPage,
  EmplLeaveReasonTypeDeleteDialog,
  EmplLeaveReasonTypeUpdatePage,
} from './empl-leave-reason-type.page-object';

const expect = chai.expect;

describe('EmplLeaveReasonType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let emplLeaveReasonTypeComponentsPage: EmplLeaveReasonTypeComponentsPage;
  let emplLeaveReasonTypeUpdatePage: EmplLeaveReasonTypeUpdatePage;
  let emplLeaveReasonTypeDeleteDialog: EmplLeaveReasonTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmplLeaveReasonTypes', async () => {
    await navBarPage.goToEntity('empl-leave-reason-type');
    emplLeaveReasonTypeComponentsPage = new EmplLeaveReasonTypeComponentsPage();
    await browser.wait(ec.visibilityOf(emplLeaveReasonTypeComponentsPage.title), 5000);
    expect(await emplLeaveReasonTypeComponentsPage.getTitle()).to.eq('hrApp.emplLeaveReasonType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(emplLeaveReasonTypeComponentsPage.entities), ec.visibilityOf(emplLeaveReasonTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EmplLeaveReasonType page', async () => {
    await emplLeaveReasonTypeComponentsPage.clickOnCreateButton();
    emplLeaveReasonTypeUpdatePage = new EmplLeaveReasonTypeUpdatePage();
    expect(await emplLeaveReasonTypeUpdatePage.getPageTitle()).to.eq('hrApp.emplLeaveReasonType.home.createOrEditLabel');
    await emplLeaveReasonTypeUpdatePage.cancel();
  });

  it('should create and save EmplLeaveReasonTypes', async () => {
    const nbButtonsBeforeCreate = await emplLeaveReasonTypeComponentsPage.countDeleteButtons();

    await emplLeaveReasonTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      emplLeaveReasonTypeUpdatePage.setNameInput('name'),
      emplLeaveReasonTypeUpdatePage.setDescriptionInput('description'),
    ]);

    expect(await emplLeaveReasonTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await emplLeaveReasonTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await emplLeaveReasonTypeUpdatePage.save();
    expect(await emplLeaveReasonTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await emplLeaveReasonTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EmplLeaveReasonType', async () => {
    const nbButtonsBeforeDelete = await emplLeaveReasonTypeComponentsPage.countDeleteButtons();
    await emplLeaveReasonTypeComponentsPage.clickOnLastDeleteButton();

    emplLeaveReasonTypeDeleteDialog = new EmplLeaveReasonTypeDeleteDialog();
    expect(await emplLeaveReasonTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.emplLeaveReasonType.delete.question');
    await emplLeaveReasonTypeDeleteDialog.clickOnConfirmButton();

    expect(await emplLeaveReasonTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
