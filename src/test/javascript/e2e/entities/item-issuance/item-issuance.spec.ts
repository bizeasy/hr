import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ItemIssuanceComponentsPage, ItemIssuanceDeleteDialog, ItemIssuanceUpdatePage } from './item-issuance.page-object';

const expect = chai.expect;

describe('ItemIssuance e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let itemIssuanceComponentsPage: ItemIssuanceComponentsPage;
  let itemIssuanceUpdatePage: ItemIssuanceUpdatePage;
  let itemIssuanceDeleteDialog: ItemIssuanceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ItemIssuances', async () => {
    await navBarPage.goToEntity('item-issuance');
    itemIssuanceComponentsPage = new ItemIssuanceComponentsPage();
    await browser.wait(ec.visibilityOf(itemIssuanceComponentsPage.title), 5000);
    expect(await itemIssuanceComponentsPage.getTitle()).to.eq('hrApp.itemIssuance.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(itemIssuanceComponentsPage.entities), ec.visibilityOf(itemIssuanceComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ItemIssuance page', async () => {
    await itemIssuanceComponentsPage.clickOnCreateButton();
    itemIssuanceUpdatePage = new ItemIssuanceUpdatePage();
    expect(await itemIssuanceUpdatePage.getPageTitle()).to.eq('hrApp.itemIssuance.home.createOrEditLabel');
    await itemIssuanceUpdatePage.cancel();
  });

  it('should create and save ItemIssuances', async () => {
    const nbButtonsBeforeCreate = await itemIssuanceComponentsPage.countDeleteButtons();

    await itemIssuanceComponentsPage.clickOnCreateButton();

    await promise.all([
      itemIssuanceUpdatePage.setMessageInput('message'),
      itemIssuanceUpdatePage.setIssuedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      itemIssuanceUpdatePage.setIssuedByInput('issuedBy'),
      itemIssuanceUpdatePage.setQuantityInput('5'),
      itemIssuanceUpdatePage.setCancelQuantityInput('5'),
      itemIssuanceUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      itemIssuanceUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      itemIssuanceUpdatePage.setEquipmentIdInput('equipmentId'),
      itemIssuanceUpdatePage.orderSelectLastOption(),
      itemIssuanceUpdatePage.orderItemSelectLastOption(),
      itemIssuanceUpdatePage.inventoryItemSelectLastOption(),
      itemIssuanceUpdatePage.issuedByUserLoginSelectLastOption(),
      itemIssuanceUpdatePage.varianceReasonSelectLastOption(),
      itemIssuanceUpdatePage.facilitySelectLastOption(),
      itemIssuanceUpdatePage.statusSelectLastOption(),
    ]);

    expect(await itemIssuanceUpdatePage.getMessageInput()).to.eq('message', 'Expected Message value to be equals to message');
    expect(await itemIssuanceUpdatePage.getIssuedDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected issuedDate value to be equals to 2000-12-31'
    );
    expect(await itemIssuanceUpdatePage.getIssuedByInput()).to.eq('issuedBy', 'Expected IssuedBy value to be equals to issuedBy');
    expect(await itemIssuanceUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await itemIssuanceUpdatePage.getCancelQuantityInput()).to.eq('5', 'Expected cancelQuantity value to be equals to 5');
    expect(await itemIssuanceUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await itemIssuanceUpdatePage.getThruDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected thruDate value to be equals to 2000-12-31'
    );
    expect(await itemIssuanceUpdatePage.getEquipmentIdInput()).to.eq(
      'equipmentId',
      'Expected EquipmentId value to be equals to equipmentId'
    );

    await itemIssuanceUpdatePage.save();
    expect(await itemIssuanceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await itemIssuanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ItemIssuance', async () => {
    const nbButtonsBeforeDelete = await itemIssuanceComponentsPage.countDeleteButtons();
    await itemIssuanceComponentsPage.clickOnLastDeleteButton();

    itemIssuanceDeleteDialog = new ItemIssuanceDeleteDialog();
    expect(await itemIssuanceDeleteDialog.getDialogTitle()).to.eq('hrApp.itemIssuance.delete.question');
    await itemIssuanceDeleteDialog.clickOnConfirmButton();

    expect(await itemIssuanceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
