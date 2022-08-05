import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EmplPositionTypeComponentsPage, EmplPositionTypeDeleteDialog, EmplPositionTypeUpdatePage } from './empl-position-type.page-object';

const expect = chai.expect;

describe('EmplPositionType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let emplPositionTypeComponentsPage: EmplPositionTypeComponentsPage;
  let emplPositionTypeUpdatePage: EmplPositionTypeUpdatePage;
  let emplPositionTypeDeleteDialog: EmplPositionTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmplPositionTypes', async () => {
    await navBarPage.goToEntity('empl-position-type');
    emplPositionTypeComponentsPage = new EmplPositionTypeComponentsPage();
    await browser.wait(ec.visibilityOf(emplPositionTypeComponentsPage.title), 5000);
    expect(await emplPositionTypeComponentsPage.getTitle()).to.eq('hrApp.emplPositionType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(emplPositionTypeComponentsPage.entities), ec.visibilityOf(emplPositionTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EmplPositionType page', async () => {
    await emplPositionTypeComponentsPage.clickOnCreateButton();
    emplPositionTypeUpdatePage = new EmplPositionTypeUpdatePage();
    expect(await emplPositionTypeUpdatePage.getPageTitle()).to.eq('hrApp.emplPositionType.home.createOrEditLabel');
    await emplPositionTypeUpdatePage.cancel();
  });

  it('should create and save EmplPositionTypes', async () => {
    const nbButtonsBeforeCreate = await emplPositionTypeComponentsPage.countDeleteButtons();

    await emplPositionTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      emplPositionTypeUpdatePage.setNameInput('name'),
      emplPositionTypeUpdatePage.setDescriptionInput('description'),
      emplPositionTypeUpdatePage.groupSelectLastOption(),
    ]);

    expect(await emplPositionTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await emplPositionTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await emplPositionTypeUpdatePage.save();
    expect(await emplPositionTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await emplPositionTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EmplPositionType', async () => {
    const nbButtonsBeforeDelete = await emplPositionTypeComponentsPage.countDeleteButtons();
    await emplPositionTypeComponentsPage.clickOnLastDeleteButton();

    emplPositionTypeDeleteDialog = new EmplPositionTypeDeleteDialog();
    expect(await emplPositionTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.emplPositionType.delete.question');
    await emplPositionTypeDeleteDialog.clickOnConfirmButton();

    expect(await emplPositionTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
