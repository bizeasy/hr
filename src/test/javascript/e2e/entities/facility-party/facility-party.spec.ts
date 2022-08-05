import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FacilityPartyComponentsPage, FacilityPartyDeleteDialog, FacilityPartyUpdatePage } from './facility-party.page-object';

const expect = chai.expect;

describe('FacilityParty e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let facilityPartyComponentsPage: FacilityPartyComponentsPage;
  let facilityPartyUpdatePage: FacilityPartyUpdatePage;
  let facilityPartyDeleteDialog: FacilityPartyDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FacilityParties', async () => {
    await navBarPage.goToEntity('facility-party');
    facilityPartyComponentsPage = new FacilityPartyComponentsPage();
    await browser.wait(ec.visibilityOf(facilityPartyComponentsPage.title), 5000);
    expect(await facilityPartyComponentsPage.getTitle()).to.eq('hrApp.facilityParty.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(facilityPartyComponentsPage.entities), ec.visibilityOf(facilityPartyComponentsPage.noResult)),
      1000
    );
  });

  it('should load create FacilityParty page', async () => {
    await facilityPartyComponentsPage.clickOnCreateButton();
    facilityPartyUpdatePage = new FacilityPartyUpdatePage();
    expect(await facilityPartyUpdatePage.getPageTitle()).to.eq('hrApp.facilityParty.home.createOrEditLabel');
    await facilityPartyUpdatePage.cancel();
  });

  it('should create and save FacilityParties', async () => {
    const nbButtonsBeforeCreate = await facilityPartyComponentsPage.countDeleteButtons();

    await facilityPartyComponentsPage.clickOnCreateButton();

    await promise.all([
      facilityPartyUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      facilityPartyUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      facilityPartyUpdatePage.facilitySelectLastOption(),
      facilityPartyUpdatePage.partySelectLastOption(),
      facilityPartyUpdatePage.roleTypeSelectLastOption(),
    ]);

    expect(await facilityPartyUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await facilityPartyUpdatePage.getThruDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected thruDate value to be equals to 2000-12-31'
    );

    await facilityPartyUpdatePage.save();
    expect(await facilityPartyUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await facilityPartyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last FacilityParty', async () => {
    const nbButtonsBeforeDelete = await facilityPartyComponentsPage.countDeleteButtons();
    await facilityPartyComponentsPage.clickOnLastDeleteButton();

    facilityPartyDeleteDialog = new FacilityPartyDeleteDialog();
    expect(await facilityPartyDeleteDialog.getDialogTitle()).to.eq('hrApp.facilityParty.delete.question');
    await facilityPartyDeleteDialog.clickOnConfirmButton();

    expect(await facilityPartyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
