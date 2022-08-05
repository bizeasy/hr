import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  EmplPositionGroupComponentsPage,
  EmplPositionGroupDeleteDialog,
  EmplPositionGroupUpdatePage,
} from './empl-position-group.page-object';

const expect = chai.expect;

describe('EmplPositionGroup e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let emplPositionGroupComponentsPage: EmplPositionGroupComponentsPage;
  let emplPositionGroupUpdatePage: EmplPositionGroupUpdatePage;
  let emplPositionGroupDeleteDialog: EmplPositionGroupDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmplPositionGroups', async () => {
    await navBarPage.goToEntity('empl-position-group');
    emplPositionGroupComponentsPage = new EmplPositionGroupComponentsPage();
    await browser.wait(ec.visibilityOf(emplPositionGroupComponentsPage.title), 5000);
    expect(await emplPositionGroupComponentsPage.getTitle()).to.eq('hrApp.emplPositionGroup.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(emplPositionGroupComponentsPage.entities), ec.visibilityOf(emplPositionGroupComponentsPage.noResult)),
      1000
    );
  });

  it('should load create EmplPositionGroup page', async () => {
    await emplPositionGroupComponentsPage.clickOnCreateButton();
    emplPositionGroupUpdatePage = new EmplPositionGroupUpdatePage();
    expect(await emplPositionGroupUpdatePage.getPageTitle()).to.eq('hrApp.emplPositionGroup.home.createOrEditLabel');
    await emplPositionGroupUpdatePage.cancel();
  });

  it('should create and save EmplPositionGroups', async () => {
    const nbButtonsBeforeCreate = await emplPositionGroupComponentsPage.countDeleteButtons();

    await emplPositionGroupComponentsPage.clickOnCreateButton();

    await promise.all([emplPositionGroupUpdatePage.setNameInput('name'), emplPositionGroupUpdatePage.setDescriptionInput('description')]);

    expect(await emplPositionGroupUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await emplPositionGroupUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await emplPositionGroupUpdatePage.save();
    expect(await emplPositionGroupUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await emplPositionGroupComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EmplPositionGroup', async () => {
    const nbButtonsBeforeDelete = await emplPositionGroupComponentsPage.countDeleteButtons();
    await emplPositionGroupComponentsPage.clickOnLastDeleteButton();

    emplPositionGroupDeleteDialog = new EmplPositionGroupDeleteDialog();
    expect(await emplPositionGroupDeleteDialog.getDialogTitle()).to.eq('hrApp.emplPositionGroup.delete.question');
    await emplPositionGroupDeleteDialog.clickOnConfirmButton();

    expect(await emplPositionGroupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
