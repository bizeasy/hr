import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TermComponentsPage, TermDeleteDialog, TermUpdatePage } from './term.page-object';

const expect = chai.expect;

describe('Term e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let termComponentsPage: TermComponentsPage;
  let termUpdatePage: TermUpdatePage;
  let termDeleteDialog: TermDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Terms', async () => {
    await navBarPage.goToEntity('term');
    termComponentsPage = new TermComponentsPage();
    await browser.wait(ec.visibilityOf(termComponentsPage.title), 5000);
    expect(await termComponentsPage.getTitle()).to.eq('hrApp.term.home.title');
    await browser.wait(ec.or(ec.visibilityOf(termComponentsPage.entities), ec.visibilityOf(termComponentsPage.noResult)), 1000);
  });

  it('should load create Term page', async () => {
    await termComponentsPage.clickOnCreateButton();
    termUpdatePage = new TermUpdatePage();
    expect(await termUpdatePage.getPageTitle()).to.eq('hrApp.term.home.createOrEditLabel');
    await termUpdatePage.cancel();
  });

  it('should create and save Terms', async () => {
    const nbButtonsBeforeCreate = await termComponentsPage.countDeleteButtons();

    await termComponentsPage.clickOnCreateButton();

    await promise.all([
      termUpdatePage.setNameInput('name'),
      termUpdatePage.setDescriptionInput('description'),
      termUpdatePage.setTermDetailInput('termDetail'),
      termUpdatePage.setTermValueInput('5'),
      termUpdatePage.setTermDaysInput('5'),
      termUpdatePage.setTextValueInput('textValue'),
      termUpdatePage.termTypeSelectLastOption(),
    ]);

    expect(await termUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await termUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await termUpdatePage.getTermDetailInput()).to.eq('termDetail', 'Expected TermDetail value to be equals to termDetail');
    expect(await termUpdatePage.getTermValueInput()).to.eq('5', 'Expected termValue value to be equals to 5');
    expect(await termUpdatePage.getTermDaysInput()).to.eq('5', 'Expected termDays value to be equals to 5');
    expect(await termUpdatePage.getTextValueInput()).to.eq('textValue', 'Expected TextValue value to be equals to textValue');

    await termUpdatePage.save();
    expect(await termUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await termComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Term', async () => {
    const nbButtonsBeforeDelete = await termComponentsPage.countDeleteButtons();
    await termComponentsPage.clickOnLastDeleteButton();

    termDeleteDialog = new TermDeleteDialog();
    expect(await termDeleteDialog.getDialogTitle()).to.eq('hrApp.term.delete.question');
    await termDeleteDialog.clickOnConfirmButton();

    expect(await termComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
