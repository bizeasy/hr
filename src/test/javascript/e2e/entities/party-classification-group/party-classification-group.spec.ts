import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PartyClassificationGroupComponentsPage,
  PartyClassificationGroupDeleteDialog,
  PartyClassificationGroupUpdatePage,
} from './party-classification-group.page-object';

const expect = chai.expect;

describe('PartyClassificationGroup e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let partyClassificationGroupComponentsPage: PartyClassificationGroupComponentsPage;
  let partyClassificationGroupUpdatePage: PartyClassificationGroupUpdatePage;
  let partyClassificationGroupDeleteDialog: PartyClassificationGroupDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PartyClassificationGroups', async () => {
    await navBarPage.goToEntity('party-classification-group');
    partyClassificationGroupComponentsPage = new PartyClassificationGroupComponentsPage();
    await browser.wait(ec.visibilityOf(partyClassificationGroupComponentsPage.title), 5000);
    expect(await partyClassificationGroupComponentsPage.getTitle()).to.eq('hrApp.partyClassificationGroup.home.title');
    await browser.wait(
      ec.or(
        ec.visibilityOf(partyClassificationGroupComponentsPage.entities),
        ec.visibilityOf(partyClassificationGroupComponentsPage.noResult)
      ),
      1000
    );
  });

  it('should load create PartyClassificationGroup page', async () => {
    await partyClassificationGroupComponentsPage.clickOnCreateButton();
    partyClassificationGroupUpdatePage = new PartyClassificationGroupUpdatePage();
    expect(await partyClassificationGroupUpdatePage.getPageTitle()).to.eq('hrApp.partyClassificationGroup.home.createOrEditLabel');
    await partyClassificationGroupUpdatePage.cancel();
  });

  it('should create and save PartyClassificationGroups', async () => {
    const nbButtonsBeforeCreate = await partyClassificationGroupComponentsPage.countDeleteButtons();

    await partyClassificationGroupComponentsPage.clickOnCreateButton();

    await promise.all([
      partyClassificationGroupUpdatePage.setNameInput('name'),
      partyClassificationGroupUpdatePage.setDescriptionInput('description'),
      partyClassificationGroupUpdatePage.classificationTypeSelectLastOption(),
    ]);

    expect(await partyClassificationGroupUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await partyClassificationGroupUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await partyClassificationGroupUpdatePage.save();
    expect(await partyClassificationGroupUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await partyClassificationGroupComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PartyClassificationGroup', async () => {
    const nbButtonsBeforeDelete = await partyClassificationGroupComponentsPage.countDeleteButtons();
    await partyClassificationGroupComponentsPage.clickOnLastDeleteButton();

    partyClassificationGroupDeleteDialog = new PartyClassificationGroupDeleteDialog();
    expect(await partyClassificationGroupDeleteDialog.getDialogTitle()).to.eq('hrApp.partyClassificationGroup.delete.question');
    await partyClassificationGroupDeleteDialog.clickOnConfirmButton();

    expect(await partyClassificationGroupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
