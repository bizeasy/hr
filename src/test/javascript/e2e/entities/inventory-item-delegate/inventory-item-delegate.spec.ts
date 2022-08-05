import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  InventoryItemDelegateComponentsPage,
  InventoryItemDelegateDeleteDialog,
  InventoryItemDelegateUpdatePage,
} from './inventory-item-delegate.page-object';

const expect = chai.expect;

describe('InventoryItemDelegate e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let inventoryItemDelegateComponentsPage: InventoryItemDelegateComponentsPage;
  let inventoryItemDelegateUpdatePage: InventoryItemDelegateUpdatePage;
  let inventoryItemDelegateDeleteDialog: InventoryItemDelegateDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load InventoryItemDelegates', async () => {
    await navBarPage.goToEntity('inventory-item-delegate');
    inventoryItemDelegateComponentsPage = new InventoryItemDelegateComponentsPage();
    await browser.wait(ec.visibilityOf(inventoryItemDelegateComponentsPage.title), 5000);
    expect(await inventoryItemDelegateComponentsPage.getTitle()).to.eq('hrApp.inventoryItemDelegate.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(inventoryItemDelegateComponentsPage.entities), ec.visibilityOf(inventoryItemDelegateComponentsPage.noResult)),
      1000
    );
  });

  it('should load create InventoryItemDelegate page', async () => {
    await inventoryItemDelegateComponentsPage.clickOnCreateButton();
    inventoryItemDelegateUpdatePage = new InventoryItemDelegateUpdatePage();
    expect(await inventoryItemDelegateUpdatePage.getPageTitle()).to.eq('hrApp.inventoryItemDelegate.home.createOrEditLabel');
    await inventoryItemDelegateUpdatePage.cancel();
  });

  it('should create and save InventoryItemDelegates', async () => {
    const nbButtonsBeforeCreate = await inventoryItemDelegateComponentsPage.countDeleteButtons();

    await inventoryItemDelegateComponentsPage.clickOnCreateButton();

    await promise.all([
      inventoryItemDelegateUpdatePage.setSequenceNoInput('5'),
      inventoryItemDelegateUpdatePage.setEffectiveDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      inventoryItemDelegateUpdatePage.setQuantityOnHandDiffInput('5'),
      inventoryItemDelegateUpdatePage.setAvailableToPromiseDiffInput('5'),
      inventoryItemDelegateUpdatePage.setAccountingQuantityDiffInput('5'),
      inventoryItemDelegateUpdatePage.setUnitCostInput('5'),
      inventoryItemDelegateUpdatePage.setRemarksInput('remarks'),
      inventoryItemDelegateUpdatePage.invoiceSelectLastOption(),
      inventoryItemDelegateUpdatePage.invoiceItemSelectLastOption(),
      inventoryItemDelegateUpdatePage.orderSelectLastOption(),
      inventoryItemDelegateUpdatePage.orderItemSelectLastOption(),
      inventoryItemDelegateUpdatePage.itemIssuanceSelectLastOption(),
      inventoryItemDelegateUpdatePage.inventoryTransferSelectLastOption(),
      inventoryItemDelegateUpdatePage.inventoryItemVarianceSelectLastOption(),
      inventoryItemDelegateUpdatePage.inventoryItemSelectLastOption(),
    ]);

    expect(await inventoryItemDelegateUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');
    expect(await inventoryItemDelegateUpdatePage.getEffectiveDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected effectiveDate value to be equals to 2000-12-31'
    );
    expect(await inventoryItemDelegateUpdatePage.getQuantityOnHandDiffInput()).to.eq(
      '5',
      'Expected quantityOnHandDiff value to be equals to 5'
    );
    expect(await inventoryItemDelegateUpdatePage.getAvailableToPromiseDiffInput()).to.eq(
      '5',
      'Expected availableToPromiseDiff value to be equals to 5'
    );
    expect(await inventoryItemDelegateUpdatePage.getAccountingQuantityDiffInput()).to.eq(
      '5',
      'Expected accountingQuantityDiff value to be equals to 5'
    );
    expect(await inventoryItemDelegateUpdatePage.getUnitCostInput()).to.eq('5', 'Expected unitCost value to be equals to 5');
    expect(await inventoryItemDelegateUpdatePage.getRemarksInput()).to.eq('remarks', 'Expected Remarks value to be equals to remarks');

    await inventoryItemDelegateUpdatePage.save();
    expect(await inventoryItemDelegateUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await inventoryItemDelegateComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last InventoryItemDelegate', async () => {
    const nbButtonsBeforeDelete = await inventoryItemDelegateComponentsPage.countDeleteButtons();
    await inventoryItemDelegateComponentsPage.clickOnLastDeleteButton();

    inventoryItemDelegateDeleteDialog = new InventoryItemDelegateDeleteDialog();
    expect(await inventoryItemDelegateDeleteDialog.getDialogTitle()).to.eq('hrApp.inventoryItemDelegate.delete.question');
    await inventoryItemDelegateDeleteDialog.clickOnConfirmButton();

    expect(await inventoryItemDelegateComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
