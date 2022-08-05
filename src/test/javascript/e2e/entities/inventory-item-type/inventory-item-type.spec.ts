import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  InventoryItemTypeComponentsPage,
  InventoryItemTypeDeleteDialog,
  InventoryItemTypeUpdatePage,
} from './inventory-item-type.page-object';

const expect = chai.expect;

describe('InventoryItemType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let inventoryItemTypeComponentsPage: InventoryItemTypeComponentsPage;
  let inventoryItemTypeUpdatePage: InventoryItemTypeUpdatePage;
  let inventoryItemTypeDeleteDialog: InventoryItemTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load InventoryItemTypes', async () => {
    await navBarPage.goToEntity('inventory-item-type');
    inventoryItemTypeComponentsPage = new InventoryItemTypeComponentsPage();
    await browser.wait(ec.visibilityOf(inventoryItemTypeComponentsPage.title), 5000);
    expect(await inventoryItemTypeComponentsPage.getTitle()).to.eq('hrApp.inventoryItemType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(inventoryItemTypeComponentsPage.entities), ec.visibilityOf(inventoryItemTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create InventoryItemType page', async () => {
    await inventoryItemTypeComponentsPage.clickOnCreateButton();
    inventoryItemTypeUpdatePage = new InventoryItemTypeUpdatePage();
    expect(await inventoryItemTypeUpdatePage.getPageTitle()).to.eq('hrApp.inventoryItemType.home.createOrEditLabel');
    await inventoryItemTypeUpdatePage.cancel();
  });

  it('should create and save InventoryItemTypes', async () => {
    const nbButtonsBeforeCreate = await inventoryItemTypeComponentsPage.countDeleteButtons();

    await inventoryItemTypeComponentsPage.clickOnCreateButton();

    await promise.all([inventoryItemTypeUpdatePage.setNameInput('name'), inventoryItemTypeUpdatePage.setDescriptionInput('description')]);

    expect(await inventoryItemTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await inventoryItemTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await inventoryItemTypeUpdatePage.save();
    expect(await inventoryItemTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await inventoryItemTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last InventoryItemType', async () => {
    const nbButtonsBeforeDelete = await inventoryItemTypeComponentsPage.countDeleteButtons();
    await inventoryItemTypeComponentsPage.clickOnLastDeleteButton();

    inventoryItemTypeDeleteDialog = new InventoryItemTypeDeleteDialog();
    expect(await inventoryItemTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.inventoryItemType.delete.question');
    await inventoryItemTypeDeleteDialog.clickOnConfirmButton();

    expect(await inventoryItemTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
