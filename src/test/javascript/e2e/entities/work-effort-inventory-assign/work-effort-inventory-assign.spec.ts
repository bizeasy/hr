import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  WorkEffortInventoryAssignComponentsPage,
  WorkEffortInventoryAssignDeleteDialog,
  WorkEffortInventoryAssignUpdatePage,
} from './work-effort-inventory-assign.page-object';

const expect = chai.expect;

describe('WorkEffortInventoryAssign e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let workEffortInventoryAssignComponentsPage: WorkEffortInventoryAssignComponentsPage;
  let workEffortInventoryAssignUpdatePage: WorkEffortInventoryAssignUpdatePage;
  let workEffortInventoryAssignDeleteDialog: WorkEffortInventoryAssignDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load WorkEffortInventoryAssigns', async () => {
    await navBarPage.goToEntity('work-effort-inventory-assign');
    workEffortInventoryAssignComponentsPage = new WorkEffortInventoryAssignComponentsPage();
    await browser.wait(ec.visibilityOf(workEffortInventoryAssignComponentsPage.title), 5000);
    expect(await workEffortInventoryAssignComponentsPage.getTitle()).to.eq('hrApp.workEffortInventoryAssign.home.title');
    await browser.wait(
      ec.or(
        ec.visibilityOf(workEffortInventoryAssignComponentsPage.entities),
        ec.visibilityOf(workEffortInventoryAssignComponentsPage.noResult)
      ),
      1000
    );
  });

  it('should load create WorkEffortInventoryAssign page', async () => {
    await workEffortInventoryAssignComponentsPage.clickOnCreateButton();
    workEffortInventoryAssignUpdatePage = new WorkEffortInventoryAssignUpdatePage();
    expect(await workEffortInventoryAssignUpdatePage.getPageTitle()).to.eq('hrApp.workEffortInventoryAssign.home.createOrEditLabel');
    await workEffortInventoryAssignUpdatePage.cancel();
  });

  it('should create and save WorkEffortInventoryAssigns', async () => {
    const nbButtonsBeforeCreate = await workEffortInventoryAssignComponentsPage.countDeleteButtons();

    await workEffortInventoryAssignComponentsPage.clickOnCreateButton();

    await promise.all([
      workEffortInventoryAssignUpdatePage.setQuantityInput('5'),
      workEffortInventoryAssignUpdatePage.workEffortSelectLastOption(),
      workEffortInventoryAssignUpdatePage.inventoryItemSelectLastOption(),
      workEffortInventoryAssignUpdatePage.statusSelectLastOption(),
    ]);

    expect(await workEffortInventoryAssignUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');

    await workEffortInventoryAssignUpdatePage.save();
    expect(await workEffortInventoryAssignUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await workEffortInventoryAssignComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last WorkEffortInventoryAssign', async () => {
    const nbButtonsBeforeDelete = await workEffortInventoryAssignComponentsPage.countDeleteButtons();
    await workEffortInventoryAssignComponentsPage.clickOnLastDeleteButton();

    workEffortInventoryAssignDeleteDialog = new WorkEffortInventoryAssignDeleteDialog();
    expect(await workEffortInventoryAssignDeleteDialog.getDialogTitle()).to.eq('hrApp.workEffortInventoryAssign.delete.question');
    await workEffortInventoryAssignDeleteDialog.clickOnConfirmButton();

    expect(await workEffortInventoryAssignComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
