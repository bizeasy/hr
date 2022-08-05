import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  WorkEffortInventoryProducedComponentsPage,
  WorkEffortInventoryProducedDeleteDialog,
  WorkEffortInventoryProducedUpdatePage,
} from './work-effort-inventory-produced.page-object';

const expect = chai.expect;

describe('WorkEffortInventoryProduced e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let workEffortInventoryProducedComponentsPage: WorkEffortInventoryProducedComponentsPage;
  let workEffortInventoryProducedUpdatePage: WorkEffortInventoryProducedUpdatePage;
  let workEffortInventoryProducedDeleteDialog: WorkEffortInventoryProducedDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load WorkEffortInventoryProduceds', async () => {
    await navBarPage.goToEntity('work-effort-inventory-produced');
    workEffortInventoryProducedComponentsPage = new WorkEffortInventoryProducedComponentsPage();
    await browser.wait(ec.visibilityOf(workEffortInventoryProducedComponentsPage.title), 5000);
    expect(await workEffortInventoryProducedComponentsPage.getTitle()).to.eq('hrApp.workEffortInventoryProduced.home.title');
    await browser.wait(
      ec.or(
        ec.visibilityOf(workEffortInventoryProducedComponentsPage.entities),
        ec.visibilityOf(workEffortInventoryProducedComponentsPage.noResult)
      ),
      1000
    );
  });

  it('should load create WorkEffortInventoryProduced page', async () => {
    await workEffortInventoryProducedComponentsPage.clickOnCreateButton();
    workEffortInventoryProducedUpdatePage = new WorkEffortInventoryProducedUpdatePage();
    expect(await workEffortInventoryProducedUpdatePage.getPageTitle()).to.eq('hrApp.workEffortInventoryProduced.home.createOrEditLabel');
    await workEffortInventoryProducedUpdatePage.cancel();
  });

  it('should create and save WorkEffortInventoryProduceds', async () => {
    const nbButtonsBeforeCreate = await workEffortInventoryProducedComponentsPage.countDeleteButtons();

    await workEffortInventoryProducedComponentsPage.clickOnCreateButton();

    await promise.all([
      workEffortInventoryProducedUpdatePage.setQuantityInput('5'),
      workEffortInventoryProducedUpdatePage.workEffortSelectLastOption(),
      workEffortInventoryProducedUpdatePage.inventoryItemSelectLastOption(),
      workEffortInventoryProducedUpdatePage.statusSelectLastOption(),
    ]);

    expect(await workEffortInventoryProducedUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');

    await workEffortInventoryProducedUpdatePage.save();
    expect(await workEffortInventoryProducedUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await workEffortInventoryProducedComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last WorkEffortInventoryProduced', async () => {
    const nbButtonsBeforeDelete = await workEffortInventoryProducedComponentsPage.countDeleteButtons();
    await workEffortInventoryProducedComponentsPage.clickOnLastDeleteButton();

    workEffortInventoryProducedDeleteDialog = new WorkEffortInventoryProducedDeleteDialog();
    expect(await workEffortInventoryProducedDeleteDialog.getDialogTitle()).to.eq('hrApp.workEffortInventoryProduced.delete.question');
    await workEffortInventoryProducedDeleteDialog.clickOnConfirmButton();

    expect(await workEffortInventoryProducedComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
