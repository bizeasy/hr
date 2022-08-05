import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContactMechComponentsPage, ContactMechDeleteDialog, ContactMechUpdatePage } from './contact-mech.page-object';

const expect = chai.expect;

describe('ContactMech e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contactMechComponentsPage: ContactMechComponentsPage;
  let contactMechUpdatePage: ContactMechUpdatePage;
  let contactMechDeleteDialog: ContactMechDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ContactMeches', async () => {
    await navBarPage.goToEntity('contact-mech');
    contactMechComponentsPage = new ContactMechComponentsPage();
    await browser.wait(ec.visibilityOf(contactMechComponentsPage.title), 5000);
    expect(await contactMechComponentsPage.getTitle()).to.eq('hrApp.contactMech.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(contactMechComponentsPage.entities), ec.visibilityOf(contactMechComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ContactMech page', async () => {
    await contactMechComponentsPage.clickOnCreateButton();
    contactMechUpdatePage = new ContactMechUpdatePage();
    expect(await contactMechUpdatePage.getPageTitle()).to.eq('hrApp.contactMech.home.createOrEditLabel');
    await contactMechUpdatePage.cancel();
  });

  it('should create and save ContactMeches', async () => {
    const nbButtonsBeforeCreate = await contactMechComponentsPage.countDeleteButtons();

    await contactMechComponentsPage.clickOnCreateButton();

    await promise.all([contactMechUpdatePage.setInfoStringInput('infoString'), contactMechUpdatePage.contactMechTypeSelectLastOption()]);

    expect(await contactMechUpdatePage.getInfoStringInput()).to.eq('infoString', 'Expected InfoString value to be equals to infoString');

    await contactMechUpdatePage.save();
    expect(await contactMechUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await contactMechComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ContactMech', async () => {
    const nbButtonsBeforeDelete = await contactMechComponentsPage.countDeleteButtons();
    await contactMechComponentsPage.clickOnLastDeleteButton();

    contactMechDeleteDialog = new ContactMechDeleteDialog();
    expect(await contactMechDeleteDialog.getDialogTitle()).to.eq('hrApp.contactMech.delete.question');
    await contactMechDeleteDialog.clickOnConfirmButton();

    expect(await contactMechComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
