import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  InventoryItemVarianceComponentsPage,
  InventoryItemVarianceDeleteDialog,
  InventoryItemVarianceUpdatePage,
} from './inventory-item-variance.page-object';

const expect = chai.expect;

describe('InventoryItemVariance e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let inventoryItemVarianceComponentsPage: InventoryItemVarianceComponentsPage;
  let inventoryItemVarianceUpdatePage: InventoryItemVarianceUpdatePage;
  let inventoryItemVarianceDeleteDialog: InventoryItemVarianceDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load InventoryItemVariances', async () => {
    await navBarPage.goToEntity('inventory-item-variance');
    inventoryItemVarianceComponentsPage = new InventoryItemVarianceComponentsPage();
    await browser.wait(ec.visibilityOf(inventoryItemVarianceComponentsPage.title), 5000);
    expect(await inventoryItemVarianceComponentsPage.getTitle()).to.eq('hrApp.inventoryItemVariance.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(inventoryItemVarianceComponentsPage.entities), ec.visibilityOf(inventoryItemVarianceComponentsPage.noResult)),
      1000
    );
  });

  it('should load create InventoryItemVariance page', async () => {
    await inventoryItemVarianceComponentsPage.clickOnCreateButton();
    inventoryItemVarianceUpdatePage = new InventoryItemVarianceUpdatePage();
    expect(await inventoryItemVarianceUpdatePage.getPageTitle()).to.eq('hrApp.inventoryItemVariance.home.createOrEditLabel');
    await inventoryItemVarianceUpdatePage.cancel();
  });

  it('should create and save InventoryItemVariances', async () => {
    const nbButtonsBeforeCreate = await inventoryItemVarianceComponentsPage.countDeleteButtons();

    await inventoryItemVarianceComponentsPage.clickOnCreateButton();

    await promise.all([
      inventoryItemVarianceUpdatePage.setVarianceReasonInput('varianceReason'),
      inventoryItemVarianceUpdatePage.setAtpVarInput('5'),
      inventoryItemVarianceUpdatePage.setQohVarInput('5'),
      inventoryItemVarianceUpdatePage.setCommentsInput('comments'),
      inventoryItemVarianceUpdatePage.inventoryItemSelectLastOption(),
      inventoryItemVarianceUpdatePage.reasonSelectLastOption(),
    ]);

    expect(await inventoryItemVarianceUpdatePage.getVarianceReasonInput()).to.eq(
      'varianceReason',
      'Expected VarianceReason value to be equals to varianceReason'
    );
    expect(await inventoryItemVarianceUpdatePage.getAtpVarInput()).to.eq('5', 'Expected atpVar value to be equals to 5');
    expect(await inventoryItemVarianceUpdatePage.getQohVarInput()).to.eq('5', 'Expected qohVar value to be equals to 5');
    expect(await inventoryItemVarianceUpdatePage.getCommentsInput()).to.eq('comments', 'Expected Comments value to be equals to comments');

    await inventoryItemVarianceUpdatePage.save();
    expect(await inventoryItemVarianceUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await inventoryItemVarianceComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last InventoryItemVariance', async () => {
    const nbButtonsBeforeDelete = await inventoryItemVarianceComponentsPage.countDeleteButtons();
    await inventoryItemVarianceComponentsPage.clickOnLastDeleteButton();

    inventoryItemVarianceDeleteDialog = new InventoryItemVarianceDeleteDialog();
    expect(await inventoryItemVarianceDeleteDialog.getDialogTitle()).to.eq('hrApp.inventoryItemVariance.delete.question');
    await inventoryItemVarianceDeleteDialog.clickOnConfirmButton();

    expect(await inventoryItemVarianceComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
