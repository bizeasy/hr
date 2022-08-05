import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  EmplPositionFulfillmentComponentsPage,
  EmplPositionFulfillmentDeleteDialog,
  EmplPositionFulfillmentUpdatePage,
} from './empl-position-fulfillment.page-object';

const expect = chai.expect;

describe('EmplPositionFulfillment e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let emplPositionFulfillmentComponentsPage: EmplPositionFulfillmentComponentsPage;
  let emplPositionFulfillmentUpdatePage: EmplPositionFulfillmentUpdatePage;
  let emplPositionFulfillmentDeleteDialog: EmplPositionFulfillmentDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmplPositionFulfillments', async () => {
    await navBarPage.goToEntity('empl-position-fulfillment');
    emplPositionFulfillmentComponentsPage = new EmplPositionFulfillmentComponentsPage();
    await browser.wait(ec.visibilityOf(emplPositionFulfillmentComponentsPage.title), 5000);
    expect(await emplPositionFulfillmentComponentsPage.getTitle()).to.eq('hrApp.emplPositionFulfillment.home.title');
    await browser.wait(
      ec.or(
        ec.visibilityOf(emplPositionFulfillmentComponentsPage.entities),
        ec.visibilityOf(emplPositionFulfillmentComponentsPage.noResult)
      ),
      1000
    );
  });

  it('should load create EmplPositionFulfillment page', async () => {
    await emplPositionFulfillmentComponentsPage.clickOnCreateButton();
    emplPositionFulfillmentUpdatePage = new EmplPositionFulfillmentUpdatePage();
    expect(await emplPositionFulfillmentUpdatePage.getPageTitle()).to.eq('hrApp.emplPositionFulfillment.home.createOrEditLabel');
    await emplPositionFulfillmentUpdatePage.cancel();
  });

  it('should create and save EmplPositionFulfillments', async () => {
    const nbButtonsBeforeCreate = await emplPositionFulfillmentComponentsPage.countDeleteButtons();

    await emplPositionFulfillmentComponentsPage.clickOnCreateButton();

    await promise.all([
      emplPositionFulfillmentUpdatePage.setFromDateInput('2000-12-31'),
      emplPositionFulfillmentUpdatePage.setThruDateInput('2000-12-31'),
      emplPositionFulfillmentUpdatePage.setCommentsInput('comments'),
      emplPositionFulfillmentUpdatePage.emplPositionSelectLastOption(),
      emplPositionFulfillmentUpdatePage.partySelectLastOption(),
      emplPositionFulfillmentUpdatePage.reportingToSelectLastOption(),
      emplPositionFulfillmentUpdatePage.managedBySelectLastOption(),
    ]);

    expect(await emplPositionFulfillmentUpdatePage.getFromDateInput()).to.eq(
      '2000-12-31',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await emplPositionFulfillmentUpdatePage.getThruDateInput()).to.eq(
      '2000-12-31',
      'Expected thruDate value to be equals to 2000-12-31'
    );
    expect(await emplPositionFulfillmentUpdatePage.getCommentsInput()).to.eq(
      'comments',
      'Expected Comments value to be equals to comments'
    );

    await emplPositionFulfillmentUpdatePage.save();
    expect(await emplPositionFulfillmentUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await emplPositionFulfillmentComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EmplPositionFulfillment', async () => {
    const nbButtonsBeforeDelete = await emplPositionFulfillmentComponentsPage.countDeleteButtons();
    await emplPositionFulfillmentComponentsPage.clickOnLastDeleteButton();

    emplPositionFulfillmentDeleteDialog = new EmplPositionFulfillmentDeleteDialog();
    expect(await emplPositionFulfillmentDeleteDialog.getDialogTitle()).to.eq('hrApp.emplPositionFulfillment.delete.question');
    await emplPositionFulfillmentDeleteDialog.clickOnConfirmButton();

    expect(await emplPositionFulfillmentComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
