import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PartyContactMechComponentsPage, PartyContactMechDeleteDialog, PartyContactMechUpdatePage } from './party-contact-mech.page-object';

const expect = chai.expect;

describe('PartyContactMech e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let partyContactMechComponentsPage: PartyContactMechComponentsPage;
  let partyContactMechUpdatePage: PartyContactMechUpdatePage;
  let partyContactMechDeleteDialog: PartyContactMechDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PartyContactMeches', async () => {
    await navBarPage.goToEntity('party-contact-mech');
    partyContactMechComponentsPage = new PartyContactMechComponentsPage();
    await browser.wait(ec.visibilityOf(partyContactMechComponentsPage.title), 5000);
    expect(await partyContactMechComponentsPage.getTitle()).to.eq('hrApp.partyContactMech.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(partyContactMechComponentsPage.entities), ec.visibilityOf(partyContactMechComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PartyContactMech page', async () => {
    await partyContactMechComponentsPage.clickOnCreateButton();
    partyContactMechUpdatePage = new PartyContactMechUpdatePage();
    expect(await partyContactMechUpdatePage.getPageTitle()).to.eq('hrApp.partyContactMech.home.createOrEditLabel');
    await partyContactMechUpdatePage.cancel();
  });

  it('should create and save PartyContactMeches', async () => {
    const nbButtonsBeforeCreate = await partyContactMechComponentsPage.countDeleteButtons();

    await partyContactMechComponentsPage.clickOnCreateButton();

    await promise.all([
      partyContactMechUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      partyContactMechUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      partyContactMechUpdatePage.partySelectLastOption(),
      partyContactMechUpdatePage.contactMechSelectLastOption(),
      partyContactMechUpdatePage.contactMechPurposeSelectLastOption(),
    ]);

    expect(await partyContactMechUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await partyContactMechUpdatePage.getThruDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected thruDate value to be equals to 2000-12-31'
    );

    await partyContactMechUpdatePage.save();
    expect(await partyContactMechUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await partyContactMechComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PartyContactMech', async () => {
    const nbButtonsBeforeDelete = await partyContactMechComponentsPage.countDeleteButtons();
    await partyContactMechComponentsPage.clickOnLastDeleteButton();

    partyContactMechDeleteDialog = new PartyContactMechDeleteDialog();
    expect(await partyContactMechDeleteDialog.getDialogTitle()).to.eq('hrApp.partyContactMech.delete.question');
    await partyContactMechDeleteDialog.clickOnConfirmButton();

    expect(await partyContactMechComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
