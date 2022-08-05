import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PartyClassificationTypeComponentsPage,
  PartyClassificationTypeDeleteDialog,
  PartyClassificationTypeUpdatePage,
} from './party-classification-type.page-object';

const expect = chai.expect;

describe('PartyClassificationType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let partyClassificationTypeComponentsPage: PartyClassificationTypeComponentsPage;
  let partyClassificationTypeUpdatePage: PartyClassificationTypeUpdatePage;
  let partyClassificationTypeDeleteDialog: PartyClassificationTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PartyClassificationTypes', async () => {
    await navBarPage.goToEntity('party-classification-type');
    partyClassificationTypeComponentsPage = new PartyClassificationTypeComponentsPage();
    await browser.wait(ec.visibilityOf(partyClassificationTypeComponentsPage.title), 5000);
    expect(await partyClassificationTypeComponentsPage.getTitle()).to.eq('hrApp.partyClassificationType.home.title');
    await browser.wait(
      ec.or(
        ec.visibilityOf(partyClassificationTypeComponentsPage.entities),
        ec.visibilityOf(partyClassificationTypeComponentsPage.noResult)
      ),
      1000
    );
  });

  it('should load create PartyClassificationType page', async () => {
    await partyClassificationTypeComponentsPage.clickOnCreateButton();
    partyClassificationTypeUpdatePage = new PartyClassificationTypeUpdatePage();
    expect(await partyClassificationTypeUpdatePage.getPageTitle()).to.eq('hrApp.partyClassificationType.home.createOrEditLabel');
    await partyClassificationTypeUpdatePage.cancel();
  });

  it('should create and save PartyClassificationTypes', async () => {
    const nbButtonsBeforeCreate = await partyClassificationTypeComponentsPage.countDeleteButtons();

    await partyClassificationTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      partyClassificationTypeUpdatePage.setNameInput('name'),
      partyClassificationTypeUpdatePage.setDescriptionInput('description'),
    ]);

    expect(await partyClassificationTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await partyClassificationTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await partyClassificationTypeUpdatePage.save();
    expect(await partyClassificationTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await partyClassificationTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PartyClassificationType', async () => {
    const nbButtonsBeforeDelete = await partyClassificationTypeComponentsPage.countDeleteButtons();
    await partyClassificationTypeComponentsPage.clickOnLastDeleteButton();

    partyClassificationTypeDeleteDialog = new PartyClassificationTypeDeleteDialog();
    expect(await partyClassificationTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.partyClassificationType.delete.question');
    await partyClassificationTypeDeleteDialog.clickOnConfirmButton();

    expect(await partyClassificationTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
