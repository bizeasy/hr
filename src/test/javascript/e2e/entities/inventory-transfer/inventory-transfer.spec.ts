import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  InventoryTransferComponentsPage,
  InventoryTransferDeleteDialog,
  InventoryTransferUpdatePage,
} from './inventory-transfer.page-object';

const expect = chai.expect;

describe('InventoryTransfer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let inventoryTransferComponentsPage: InventoryTransferComponentsPage;
  let inventoryTransferUpdatePage: InventoryTransferUpdatePage;
  let inventoryTransferDeleteDialog: InventoryTransferDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load InventoryTransfers', async () => {
    await navBarPage.goToEntity('inventory-transfer');
    inventoryTransferComponentsPage = new InventoryTransferComponentsPage();
    await browser.wait(ec.visibilityOf(inventoryTransferComponentsPage.title), 5000);
    expect(await inventoryTransferComponentsPage.getTitle()).to.eq('hrApp.inventoryTransfer.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(inventoryTransferComponentsPage.entities), ec.visibilityOf(inventoryTransferComponentsPage.noResult)),
      1000
    );
  });

  it('should load create InventoryTransfer page', async () => {
    await inventoryTransferComponentsPage.clickOnCreateButton();
    inventoryTransferUpdatePage = new InventoryTransferUpdatePage();
    expect(await inventoryTransferUpdatePage.getPageTitle()).to.eq('hrApp.inventoryTransfer.home.createOrEditLabel');
    await inventoryTransferUpdatePage.cancel();
  });

  it('should create and save InventoryTransfers', async () => {
    const nbButtonsBeforeCreate = await inventoryTransferComponentsPage.countDeleteButtons();

    await inventoryTransferComponentsPage.clickOnCreateButton();

    await promise.all([
      inventoryTransferUpdatePage.setSentDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      inventoryTransferUpdatePage.setReceivedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      inventoryTransferUpdatePage.setCommentsInput('comments'),
      inventoryTransferUpdatePage.statusSelectLastOption(),
      inventoryTransferUpdatePage.inventoryItemSelectLastOption(),
      inventoryTransferUpdatePage.facilitySelectLastOption(),
      inventoryTransferUpdatePage.facilityToSelectLastOption(),
      inventoryTransferUpdatePage.issuanceSelectLastOption(),
    ]);

    expect(await inventoryTransferUpdatePage.getSentDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected sentDate value to be equals to 2000-12-31'
    );
    expect(await inventoryTransferUpdatePage.getReceivedDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected receivedDate value to be equals to 2000-12-31'
    );
    expect(await inventoryTransferUpdatePage.getCommentsInput()).to.eq('comments', 'Expected Comments value to be equals to comments');

    await inventoryTransferUpdatePage.save();
    expect(await inventoryTransferUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await inventoryTransferComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last InventoryTransfer', async () => {
    const nbButtonsBeforeDelete = await inventoryTransferComponentsPage.countDeleteButtons();
    await inventoryTransferComponentsPage.clickOnLastDeleteButton();

    inventoryTransferDeleteDialog = new InventoryTransferDeleteDialog();
    expect(await inventoryTransferDeleteDialog.getDialogTitle()).to.eq('hrApp.inventoryTransfer.delete.question');
    await inventoryTransferDeleteDialog.clickOnConfirmButton();

    expect(await inventoryTransferComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
