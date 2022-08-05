import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PartyRoleComponentsPage, PartyRoleDeleteDialog, PartyRoleUpdatePage } from './party-role.page-object';

const expect = chai.expect;

describe('PartyRole e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let partyRoleComponentsPage: PartyRoleComponentsPage;
  let partyRoleUpdatePage: PartyRoleUpdatePage;
  let partyRoleDeleteDialog: PartyRoleDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PartyRoles', async () => {
    await navBarPage.goToEntity('party-role');
    partyRoleComponentsPage = new PartyRoleComponentsPage();
    await browser.wait(ec.visibilityOf(partyRoleComponentsPage.title), 5000);
    expect(await partyRoleComponentsPage.getTitle()).to.eq('hrApp.partyRole.home.title');
    await browser.wait(ec.or(ec.visibilityOf(partyRoleComponentsPage.entities), ec.visibilityOf(partyRoleComponentsPage.noResult)), 1000);
  });

  it('should load create PartyRole page', async () => {
    await partyRoleComponentsPage.clickOnCreateButton();
    partyRoleUpdatePage = new PartyRoleUpdatePage();
    expect(await partyRoleUpdatePage.getPageTitle()).to.eq('hrApp.partyRole.home.createOrEditLabel');
    await partyRoleUpdatePage.cancel();
  });

  it('should create and save PartyRoles', async () => {
    const nbButtonsBeforeCreate = await partyRoleComponentsPage.countDeleteButtons();

    await partyRoleComponentsPage.clickOnCreateButton();

    await promise.all([partyRoleUpdatePage.partySelectLastOption(), partyRoleUpdatePage.roleTypeSelectLastOption()]);

    await partyRoleUpdatePage.save();
    expect(await partyRoleUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await partyRoleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PartyRole', async () => {
    const nbButtonsBeforeDelete = await partyRoleComponentsPage.countDeleteButtons();
    await partyRoleComponentsPage.clickOnLastDeleteButton();

    partyRoleDeleteDialog = new PartyRoleDeleteDialog();
    expect(await partyRoleDeleteDialog.getDialogTitle()).to.eq('hrApp.partyRole.delete.question');
    await partyRoleDeleteDialog.clickOnConfirmButton();

    expect(await partyRoleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
