import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ContactMechPurposeComponentsPage,
  ContactMechPurposeDeleteDialog,
  ContactMechPurposeUpdatePage,
} from './contact-mech-purpose.page-object';

const expect = chai.expect;

describe('ContactMechPurpose e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contactMechPurposeComponentsPage: ContactMechPurposeComponentsPage;
  let contactMechPurposeUpdatePage: ContactMechPurposeUpdatePage;
  let contactMechPurposeDeleteDialog: ContactMechPurposeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ContactMechPurposes', async () => {
    await navBarPage.goToEntity('contact-mech-purpose');
    contactMechPurposeComponentsPage = new ContactMechPurposeComponentsPage();
    await browser.wait(ec.visibilityOf(contactMechPurposeComponentsPage.title), 5000);
    expect(await contactMechPurposeComponentsPage.getTitle()).to.eq('hrApp.contactMechPurpose.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(contactMechPurposeComponentsPage.entities), ec.visibilityOf(contactMechPurposeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ContactMechPurpose page', async () => {
    await contactMechPurposeComponentsPage.clickOnCreateButton();
    contactMechPurposeUpdatePage = new ContactMechPurposeUpdatePage();
    expect(await contactMechPurposeUpdatePage.getPageTitle()).to.eq('hrApp.contactMechPurpose.home.createOrEditLabel');
    await contactMechPurposeUpdatePage.cancel();
  });

  it('should create and save ContactMechPurposes', async () => {
    const nbButtonsBeforeCreate = await contactMechPurposeComponentsPage.countDeleteButtons();

    await contactMechPurposeComponentsPage.clickOnCreateButton();

    await promise.all([contactMechPurposeUpdatePage.setNameInput('name'), contactMechPurposeUpdatePage.setDescriptionInput('description')]);

    expect(await contactMechPurposeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await contactMechPurposeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await contactMechPurposeUpdatePage.save();
    expect(await contactMechPurposeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await contactMechPurposeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ContactMechPurpose', async () => {
    const nbButtonsBeforeDelete = await contactMechPurposeComponentsPage.countDeleteButtons();
    await contactMechPurposeComponentsPage.clickOnLastDeleteButton();

    contactMechPurposeDeleteDialog = new ContactMechPurposeDeleteDialog();
    expect(await contactMechPurposeDeleteDialog.getDialogTitle()).to.eq('hrApp.contactMechPurpose.delete.question');
    await contactMechPurposeDeleteDialog.clickOnConfirmButton();

    expect(await contactMechPurposeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
