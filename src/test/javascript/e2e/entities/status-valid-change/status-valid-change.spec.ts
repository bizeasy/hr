import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  StatusValidChangeComponentsPage,
  StatusValidChangeDeleteDialog,
  StatusValidChangeUpdatePage,
} from './status-valid-change.page-object';

const expect = chai.expect;

describe('StatusValidChange e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let statusValidChangeComponentsPage: StatusValidChangeComponentsPage;
  let statusValidChangeUpdatePage: StatusValidChangeUpdatePage;
  let statusValidChangeDeleteDialog: StatusValidChangeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load StatusValidChanges', async () => {
    await navBarPage.goToEntity('status-valid-change');
    statusValidChangeComponentsPage = new StatusValidChangeComponentsPage();
    await browser.wait(ec.visibilityOf(statusValidChangeComponentsPage.title), 5000);
    expect(await statusValidChangeComponentsPage.getTitle()).to.eq('hrApp.statusValidChange.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(statusValidChangeComponentsPage.entities), ec.visibilityOf(statusValidChangeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create StatusValidChange page', async () => {
    await statusValidChangeComponentsPage.clickOnCreateButton();
    statusValidChangeUpdatePage = new StatusValidChangeUpdatePage();
    expect(await statusValidChangeUpdatePage.getPageTitle()).to.eq('hrApp.statusValidChange.home.createOrEditLabel');
    await statusValidChangeUpdatePage.cancel();
  });

  it('should create and save StatusValidChanges', async () => {
    const nbButtonsBeforeCreate = await statusValidChangeComponentsPage.countDeleteButtons();

    await statusValidChangeComponentsPage.clickOnCreateButton();

    await promise.all([
      statusValidChangeUpdatePage.setTransitionNameInput('transitionName'),
      statusValidChangeUpdatePage.setRulesInput('rules'),
      statusValidChangeUpdatePage.statusSelectLastOption(),
      statusValidChangeUpdatePage.statusToSelectLastOption(),
    ]);

    expect(await statusValidChangeUpdatePage.getTransitionNameInput()).to.eq(
      'transitionName',
      'Expected TransitionName value to be equals to transitionName'
    );
    expect(await statusValidChangeUpdatePage.getRulesInput()).to.eq('rules', 'Expected Rules value to be equals to rules');

    await statusValidChangeUpdatePage.save();
    expect(await statusValidChangeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await statusValidChangeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last StatusValidChange', async () => {
    const nbButtonsBeforeDelete = await statusValidChangeComponentsPage.countDeleteButtons();
    await statusValidChangeComponentsPage.clickOnLastDeleteButton();

    statusValidChangeDeleteDialog = new StatusValidChangeDeleteDialog();
    expect(await statusValidChangeDeleteDialog.getDialogTitle()).to.eq('hrApp.statusValidChange.delete.question');
    await statusValidChangeDeleteDialog.clickOnConfirmButton();

    expect(await statusValidChangeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
