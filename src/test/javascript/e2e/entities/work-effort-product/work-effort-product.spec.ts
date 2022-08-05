import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  WorkEffortProductComponentsPage,
  WorkEffortProductDeleteDialog,
  WorkEffortProductUpdatePage,
} from './work-effort-product.page-object';

const expect = chai.expect;

describe('WorkEffortProduct e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let workEffortProductComponentsPage: WorkEffortProductComponentsPage;
  let workEffortProductUpdatePage: WorkEffortProductUpdatePage;
  let workEffortProductDeleteDialog: WorkEffortProductDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load WorkEffortProducts', async () => {
    await navBarPage.goToEntity('work-effort-product');
    workEffortProductComponentsPage = new WorkEffortProductComponentsPage();
    await browser.wait(ec.visibilityOf(workEffortProductComponentsPage.title), 5000);
    expect(await workEffortProductComponentsPage.getTitle()).to.eq('hrApp.workEffortProduct.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(workEffortProductComponentsPage.entities), ec.visibilityOf(workEffortProductComponentsPage.noResult)),
      1000
    );
  });

  it('should load create WorkEffortProduct page', async () => {
    await workEffortProductComponentsPage.clickOnCreateButton();
    workEffortProductUpdatePage = new WorkEffortProductUpdatePage();
    expect(await workEffortProductUpdatePage.getPageTitle()).to.eq('hrApp.workEffortProduct.home.createOrEditLabel');
    await workEffortProductUpdatePage.cancel();
  });

  it('should create and save WorkEffortProducts', async () => {
    const nbButtonsBeforeCreate = await workEffortProductComponentsPage.countDeleteButtons();

    await workEffortProductComponentsPage.clickOnCreateButton();

    await promise.all([
      workEffortProductUpdatePage.setSequenceNoInput('5'),
      workEffortProductUpdatePage.setQuantityInput('5'),
      workEffortProductUpdatePage.workEffortSelectLastOption(),
      workEffortProductUpdatePage.productSelectLastOption(),
    ]);

    expect(await workEffortProductUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');
    expect(await workEffortProductUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');

    await workEffortProductUpdatePage.save();
    expect(await workEffortProductUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await workEffortProductComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last WorkEffortProduct', async () => {
    const nbButtonsBeforeDelete = await workEffortProductComponentsPage.countDeleteButtons();
    await workEffortProductComponentsPage.clickOnLastDeleteButton();

    workEffortProductDeleteDialog = new WorkEffortProductDeleteDialog();
    expect(await workEffortProductDeleteDialog.getDialogTitle()).to.eq('hrApp.workEffortProduct.delete.question');
    await workEffortProductDeleteDialog.clickOnConfirmButton();

    expect(await workEffortProductComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
