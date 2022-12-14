import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FacilityComponentsPage, FacilityDeleteDialog, FacilityUpdatePage } from './facility.page-object';

const expect = chai.expect;

describe('Facility e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let facilityComponentsPage: FacilityComponentsPage;
  let facilityUpdatePage: FacilityUpdatePage;
  let facilityDeleteDialog: FacilityDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Facilities', async () => {
    await navBarPage.goToEntity('facility');
    facilityComponentsPage = new FacilityComponentsPage();
    await browser.wait(ec.visibilityOf(facilityComponentsPage.title), 5000);
    expect(await facilityComponentsPage.getTitle()).to.eq('hrApp.facility.home.title');
    await browser.wait(ec.or(ec.visibilityOf(facilityComponentsPage.entities), ec.visibilityOf(facilityComponentsPage.noResult)), 1000);
  });

  it('should load create Facility page', async () => {
    await facilityComponentsPage.clickOnCreateButton();
    facilityUpdatePage = new FacilityUpdatePage();
    expect(await facilityUpdatePage.getPageTitle()).to.eq('hrApp.facility.home.createOrEditLabel');
    await facilityUpdatePage.cancel();
  });

  it('should create and save Facilities', async () => {
    const nbButtonsBeforeCreate = await facilityComponentsPage.countDeleteButtons();

    await facilityComponentsPage.clickOnCreateButton();

    await promise.all([
      facilityUpdatePage.setNameInput('name'),
      facilityUpdatePage.setDescriptionInput('description'),
      facilityUpdatePage.setFacilityCodeInput('facilityCode'),
      facilityUpdatePage.setFacilitySizeInput('5'),
      facilityUpdatePage.setCostCenterCodeInput('costCenterCode'),
      facilityUpdatePage.facilityTypeSelectLastOption(),
      facilityUpdatePage.parentFacilitySelectLastOption(),
      facilityUpdatePage.ownerPartySelectLastOption(),
      facilityUpdatePage.facilityGroupSelectLastOption(),
      facilityUpdatePage.sizeUomSelectLastOption(),
      facilityUpdatePage.geoPointSelectLastOption(),
      facilityUpdatePage.inventoryItemTypeSelectLastOption(),
      facilityUpdatePage.statusSelectLastOption(),
      facilityUpdatePage.usageStatusSelectLastOption(),
      facilityUpdatePage.reviewedBySelectLastOption(),
      facilityUpdatePage.approvedBySelectLastOption(),
    ]);

    expect(await facilityUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await facilityUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await facilityUpdatePage.getFacilityCodeInput()).to.eq(
      'facilityCode',
      'Expected FacilityCode value to be equals to facilityCode'
    );
    expect(await facilityUpdatePage.getFacilitySizeInput()).to.eq('5', 'Expected facilitySize value to be equals to 5');
    expect(await facilityUpdatePage.getCostCenterCodeInput()).to.eq(
      'costCenterCode',
      'Expected CostCenterCode value to be equals to costCenterCode'
    );

    await facilityUpdatePage.save();
    expect(await facilityUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await facilityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Facility', async () => {
    const nbButtonsBeforeDelete = await facilityComponentsPage.countDeleteButtons();
    await facilityComponentsPage.clickOnLastDeleteButton();

    facilityDeleteDialog = new FacilityDeleteDialog();
    expect(await facilityDeleteDialog.getDialogTitle()).to.eq('hrApp.facility.delete.question');
    await facilityDeleteDialog.clickOnConfirmButton();

    expect(await facilityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
