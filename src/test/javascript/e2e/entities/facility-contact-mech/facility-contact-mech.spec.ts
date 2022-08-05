import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  FacilityContactMechComponentsPage,
  FacilityContactMechDeleteDialog,
  FacilityContactMechUpdatePage,
} from './facility-contact-mech.page-object';

const expect = chai.expect;

describe('FacilityContactMech e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let facilityContactMechComponentsPage: FacilityContactMechComponentsPage;
  let facilityContactMechUpdatePage: FacilityContactMechUpdatePage;
  let facilityContactMechDeleteDialog: FacilityContactMechDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FacilityContactMeches', async () => {
    await navBarPage.goToEntity('facility-contact-mech');
    facilityContactMechComponentsPage = new FacilityContactMechComponentsPage();
    await browser.wait(ec.visibilityOf(facilityContactMechComponentsPage.title), 5000);
    expect(await facilityContactMechComponentsPage.getTitle()).to.eq('hrApp.facilityContactMech.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(facilityContactMechComponentsPage.entities), ec.visibilityOf(facilityContactMechComponentsPage.noResult)),
      1000
    );
  });

  it('should load create FacilityContactMech page', async () => {
    await facilityContactMechComponentsPage.clickOnCreateButton();
    facilityContactMechUpdatePage = new FacilityContactMechUpdatePage();
    expect(await facilityContactMechUpdatePage.getPageTitle()).to.eq('hrApp.facilityContactMech.home.createOrEditLabel');
    await facilityContactMechUpdatePage.cancel();
  });

  it('should create and save FacilityContactMeches', async () => {
    const nbButtonsBeforeCreate = await facilityContactMechComponentsPage.countDeleteButtons();

    await facilityContactMechComponentsPage.clickOnCreateButton();

    await promise.all([
      facilityContactMechUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      facilityContactMechUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      facilityContactMechUpdatePage.facilitySelectLastOption(),
      facilityContactMechUpdatePage.contactMechSelectLastOption(),
      facilityContactMechUpdatePage.contactMechPurposeSelectLastOption(),
    ]);

    expect(await facilityContactMechUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await facilityContactMechUpdatePage.getThruDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected thruDate value to be equals to 2000-12-31'
    );

    await facilityContactMechUpdatePage.save();
    expect(await facilityContactMechUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await facilityContactMechComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FacilityContactMech', async () => {
    const nbButtonsBeforeDelete = await facilityContactMechComponentsPage.countDeleteButtons();
    await facilityContactMechComponentsPage.clickOnLastDeleteButton();

    facilityContactMechDeleteDialog = new FacilityContactMechDeleteDialog();
    expect(await facilityContactMechDeleteDialog.getDialogTitle()).to.eq('hrApp.facilityContactMech.delete.question');
    await facilityContactMechDeleteDialog.clickOnConfirmButton();

    expect(await facilityContactMechComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
