import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  FacilityEquipmentComponentsPage,
  FacilityEquipmentDeleteDialog,
  FacilityEquipmentUpdatePage,
} from './facility-equipment.page-object';

const expect = chai.expect;

describe('FacilityEquipment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let facilityEquipmentComponentsPage: FacilityEquipmentComponentsPage;
  let facilityEquipmentUpdatePage: FacilityEquipmentUpdatePage;
  let facilityEquipmentDeleteDialog: FacilityEquipmentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FacilityEquipments', async () => {
    await navBarPage.goToEntity('facility-equipment');
    facilityEquipmentComponentsPage = new FacilityEquipmentComponentsPage();
    await browser.wait(ec.visibilityOf(facilityEquipmentComponentsPage.title), 5000);
    expect(await facilityEquipmentComponentsPage.getTitle()).to.eq('hrApp.facilityEquipment.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(facilityEquipmentComponentsPage.entities), ec.visibilityOf(facilityEquipmentComponentsPage.noResult)),
      1000
    );
  });

  it('should load create FacilityEquipment page', async () => {
    await facilityEquipmentComponentsPage.clickOnCreateButton();
    facilityEquipmentUpdatePage = new FacilityEquipmentUpdatePage();
    expect(await facilityEquipmentUpdatePage.getPageTitle()).to.eq('hrApp.facilityEquipment.home.createOrEditLabel');
    await facilityEquipmentUpdatePage.cancel();
  });

  it('should create and save FacilityEquipments', async () => {
    const nbButtonsBeforeCreate = await facilityEquipmentComponentsPage.countDeleteButtons();

    await facilityEquipmentComponentsPage.clickOnCreateButton();

    await promise.all([
      facilityEquipmentUpdatePage.setNameInput('name'),
      facilityEquipmentUpdatePage.setDescriptionInput('description'),
      facilityEquipmentUpdatePage.facilitySelectLastOption(),
      facilityEquipmentUpdatePage.equipmentSelectLastOption(),
    ]);

    expect(await facilityEquipmentUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await facilityEquipmentUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await facilityEquipmentUpdatePage.save();
    expect(await facilityEquipmentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await facilityEquipmentComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FacilityEquipment', async () => {
    const nbButtonsBeforeDelete = await facilityEquipmentComponentsPage.countDeleteButtons();
    await facilityEquipmentComponentsPage.clickOnLastDeleteButton();

    facilityEquipmentDeleteDialog = new FacilityEquipmentDeleteDialog();
    expect(await facilityEquipmentDeleteDialog.getDialogTitle()).to.eq('hrApp.facilityEquipment.delete.question');
    await facilityEquipmentDeleteDialog.clickOnConfirmButton();

    expect(await facilityEquipmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
