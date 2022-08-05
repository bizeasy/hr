import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InventoryItemComponentsPage, InventoryItemDeleteDialog, InventoryItemUpdatePage } from './inventory-item.page-object';

const expect = chai.expect;

describe('InventoryItem e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let inventoryItemComponentsPage: InventoryItemComponentsPage;
  let inventoryItemUpdatePage: InventoryItemUpdatePage;
  let inventoryItemDeleteDialog: InventoryItemDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load InventoryItems', async () => {
    await navBarPage.goToEntity('inventory-item');
    inventoryItemComponentsPage = new InventoryItemComponentsPage();
    await browser.wait(ec.visibilityOf(inventoryItemComponentsPage.title), 5000);
    expect(await inventoryItemComponentsPage.getTitle()).to.eq('hrApp.inventoryItem.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(inventoryItemComponentsPage.entities), ec.visibilityOf(inventoryItemComponentsPage.noResult)),
      1000
    );
  });

  it('should load create InventoryItem page', async () => {
    await inventoryItemComponentsPage.clickOnCreateButton();
    inventoryItemUpdatePage = new InventoryItemUpdatePage();
    expect(await inventoryItemUpdatePage.getPageTitle()).to.eq('hrApp.inventoryItem.home.createOrEditLabel');
    await inventoryItemUpdatePage.cancel();
  });

  it('should create and save InventoryItems', async () => {
    const nbButtonsBeforeCreate = await inventoryItemComponentsPage.countDeleteButtons();

    await inventoryItemComponentsPage.clickOnCreateButton();

    await promise.all([
      inventoryItemUpdatePage.setReceivedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      inventoryItemUpdatePage.setManufacturedDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      inventoryItemUpdatePage.setExpiryDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      inventoryItemUpdatePage.setRetestDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      inventoryItemUpdatePage.setContainerIdInput('containerId'),
      inventoryItemUpdatePage.setBatchNoInput('batchNo'),
      inventoryItemUpdatePage.setMfgBatchNoInput('mfgBatchNo'),
      inventoryItemUpdatePage.setLotNoStrInput('lotNoStr'),
      inventoryItemUpdatePage.setBinNumberInput('binNumber'),
      inventoryItemUpdatePage.setCommentsInput('comments'),
      inventoryItemUpdatePage.setQuantityOnHandTotalInput('5'),
      inventoryItemUpdatePage.setAvailableToPromiseTotalInput('5'),
      inventoryItemUpdatePage.setAccountingQuantityTotalInput('5'),
      inventoryItemUpdatePage.setOldQuantityOnHandInput('5'),
      inventoryItemUpdatePage.setOldAvailableToPromiseInput('5'),
      inventoryItemUpdatePage.setSerialNumberInput('serialNumber'),
      inventoryItemUpdatePage.setSoftIdentifierInput('softIdentifier'),
      inventoryItemUpdatePage.setActivationNumberInput('activationNumber'),
      inventoryItemUpdatePage.setActivationValidTrueInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      inventoryItemUpdatePage.setUnitCostInput('5'),
      inventoryItemUpdatePage.inventoryItemTypeSelectLastOption(),
      inventoryItemUpdatePage.productSelectLastOption(),
      inventoryItemUpdatePage.supplierSelectLastOption(),
      inventoryItemUpdatePage.ownerPartySelectLastOption(),
      inventoryItemUpdatePage.statusSelectLastOption(),
      inventoryItemUpdatePage.facilitySelectLastOption(),
      inventoryItemUpdatePage.uomSelectLastOption(),
      inventoryItemUpdatePage.currencyUomSelectLastOption(),
      inventoryItemUpdatePage.lotSelectLastOption(),
    ]);

    expect(await inventoryItemUpdatePage.getReceivedDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected receivedDate value to be equals to 2000-12-31'
    );
    expect(await inventoryItemUpdatePage.getManufacturedDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected manufacturedDate value to be equals to 2000-12-31'
    );
    expect(await inventoryItemUpdatePage.getExpiryDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected expiryDate value to be equals to 2000-12-31'
    );
    expect(await inventoryItemUpdatePage.getRetestDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected retestDate value to be equals to 2000-12-31'
    );
    expect(await inventoryItemUpdatePage.getContainerIdInput()).to.eq(
      'containerId',
      'Expected ContainerId value to be equals to containerId'
    );
    expect(await inventoryItemUpdatePage.getBatchNoInput()).to.eq('batchNo', 'Expected BatchNo value to be equals to batchNo');
    expect(await inventoryItemUpdatePage.getMfgBatchNoInput()).to.eq('mfgBatchNo', 'Expected MfgBatchNo value to be equals to mfgBatchNo');
    expect(await inventoryItemUpdatePage.getLotNoStrInput()).to.eq('lotNoStr', 'Expected LotNoStr value to be equals to lotNoStr');
    expect(await inventoryItemUpdatePage.getBinNumberInput()).to.eq('binNumber', 'Expected BinNumber value to be equals to binNumber');
    expect(await inventoryItemUpdatePage.getCommentsInput()).to.eq('comments', 'Expected Comments value to be equals to comments');
    expect(await inventoryItemUpdatePage.getQuantityOnHandTotalInput()).to.eq('5', 'Expected quantityOnHandTotal value to be equals to 5');
    expect(await inventoryItemUpdatePage.getAvailableToPromiseTotalInput()).to.eq(
      '5',
      'Expected availableToPromiseTotal value to be equals to 5'
    );
    expect(await inventoryItemUpdatePage.getAccountingQuantityTotalInput()).to.eq(
      '5',
      'Expected accountingQuantityTotal value to be equals to 5'
    );
    expect(await inventoryItemUpdatePage.getOldQuantityOnHandInput()).to.eq('5', 'Expected oldQuantityOnHand value to be equals to 5');
    expect(await inventoryItemUpdatePage.getOldAvailableToPromiseInput()).to.eq(
      '5',
      'Expected oldAvailableToPromise value to be equals to 5'
    );
    expect(await inventoryItemUpdatePage.getSerialNumberInput()).to.eq(
      'serialNumber',
      'Expected SerialNumber value to be equals to serialNumber'
    );
    expect(await inventoryItemUpdatePage.getSoftIdentifierInput()).to.eq(
      'softIdentifier',
      'Expected SoftIdentifier value to be equals to softIdentifier'
    );
    expect(await inventoryItemUpdatePage.getActivationNumberInput()).to.eq(
      'activationNumber',
      'Expected ActivationNumber value to be equals to activationNumber'
    );
    expect(await inventoryItemUpdatePage.getActivationValidTrueInput()).to.contain(
      '2001-01-01T02:30',
      'Expected activationValidTrue value to be equals to 2000-12-31'
    );
    expect(await inventoryItemUpdatePage.getUnitCostInput()).to.eq('5', 'Expected unitCost value to be equals to 5');

    await inventoryItemUpdatePage.save();
    expect(await inventoryItemUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await inventoryItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last InventoryItem', async () => {
    const nbButtonsBeforeDelete = await inventoryItemComponentsPage.countDeleteButtons();
    await inventoryItemComponentsPage.clickOnLastDeleteButton();

    inventoryItemDeleteDialog = new InventoryItemDeleteDialog();
    expect(await inventoryItemDeleteDialog.getDialogTitle()).to.eq('hrApp.inventoryItem.delete.question');
    await inventoryItemDeleteDialog.clickOnConfirmButton();

    expect(await inventoryItemComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
