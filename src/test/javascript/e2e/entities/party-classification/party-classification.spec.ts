import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PartyClassificationComponentsPage,
  PartyClassificationDeleteDialog,
  PartyClassificationUpdatePage,
} from './party-classification.page-object';

const expect = chai.expect;

describe('PartyClassification e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let partyClassificationComponentsPage: PartyClassificationComponentsPage;
  let partyClassificationUpdatePage: PartyClassificationUpdatePage;
  let partyClassificationDeleteDialog: PartyClassificationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PartyClassifications', async () => {
    await navBarPage.goToEntity('party-classification');
    partyClassificationComponentsPage = new PartyClassificationComponentsPage();
    await browser.wait(ec.visibilityOf(partyClassificationComponentsPage.title), 5000);
    expect(await partyClassificationComponentsPage.getTitle()).to.eq('hrApp.partyClassification.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(partyClassificationComponentsPage.entities), ec.visibilityOf(partyClassificationComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PartyClassification page', async () => {
    await partyClassificationComponentsPage.clickOnCreateButton();
    partyClassificationUpdatePage = new PartyClassificationUpdatePage();
    expect(await partyClassificationUpdatePage.getPageTitle()).to.eq('hrApp.partyClassification.home.createOrEditLabel');
    await partyClassificationUpdatePage.cancel();
  });

  it('should create and save PartyClassifications', async () => {
    const nbButtonsBeforeCreate = await partyClassificationComponentsPage.countDeleteButtons();

    await partyClassificationComponentsPage.clickOnCreateButton();

    await promise.all([
      partyClassificationUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      partyClassificationUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      partyClassificationUpdatePage.partySelectLastOption(),
      partyClassificationUpdatePage.classificationGroupSelectLastOption(),
    ]);

    expect(await partyClassificationUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await partyClassificationUpdatePage.getThruDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected thruDate value to be equals to 2000-12-31'
    );

    await partyClassificationUpdatePage.save();
    expect(await partyClassificationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await partyClassificationComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PartyClassification', async () => {
    const nbButtonsBeforeDelete = await partyClassificationComponentsPage.countDeleteButtons();
    await partyClassificationComponentsPage.clickOnLastDeleteButton();

    partyClassificationDeleteDialog = new PartyClassificationDeleteDialog();
    expect(await partyClassificationDeleteDialog.getDialogTitle()).to.eq('hrApp.partyClassification.delete.question');
    await partyClassificationDeleteDialog.clickOnConfirmButton();

    expect(await partyClassificationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
