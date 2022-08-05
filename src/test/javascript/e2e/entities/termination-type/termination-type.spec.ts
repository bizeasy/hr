import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TerminationTypeComponentsPage, TerminationTypeDeleteDialog, TerminationTypeUpdatePage } from './termination-type.page-object';

const expect = chai.expect;

describe('TerminationType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let terminationTypeComponentsPage: TerminationTypeComponentsPage;
  let terminationTypeUpdatePage: TerminationTypeUpdatePage;
  let terminationTypeDeleteDialog: TerminationTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TerminationTypes', async () => {
    await navBarPage.goToEntity('termination-type');
    terminationTypeComponentsPage = new TerminationTypeComponentsPage();
    await browser.wait(ec.visibilityOf(terminationTypeComponentsPage.title), 5000);
    expect(await terminationTypeComponentsPage.getTitle()).to.eq('hrApp.terminationType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(terminationTypeComponentsPage.entities), ec.visibilityOf(terminationTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create TerminationType page', async () => {
    await terminationTypeComponentsPage.clickOnCreateButton();
    terminationTypeUpdatePage = new TerminationTypeUpdatePage();
    expect(await terminationTypeUpdatePage.getPageTitle()).to.eq('hrApp.terminationType.home.createOrEditLabel');
    await terminationTypeUpdatePage.cancel();
  });

  it('should create and save TerminationTypes', async () => {
    const nbButtonsBeforeCreate = await terminationTypeComponentsPage.countDeleteButtons();

    await terminationTypeComponentsPage.clickOnCreateButton();

    await promise.all([terminationTypeUpdatePage.setNameInput('name'), terminationTypeUpdatePage.setDescriptionInput('description')]);

    expect(await terminationTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await terminationTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await terminationTypeUpdatePage.save();
    expect(await terminationTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await terminationTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last TerminationType', async () => {
    const nbButtonsBeforeDelete = await terminationTypeComponentsPage.countDeleteButtons();
    await terminationTypeComponentsPage.clickOnLastDeleteButton();

    terminationTypeDeleteDialog = new TerminationTypeDeleteDialog();
    expect(await terminationTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.terminationType.delete.question');
    await terminationTypeDeleteDialog.clickOnConfirmButton();

    expect(await terminationTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
