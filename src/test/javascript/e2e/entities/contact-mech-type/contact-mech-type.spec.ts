import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContactMechTypeComponentsPage, ContactMechTypeDeleteDialog, ContactMechTypeUpdatePage } from './contact-mech-type.page-object';

const expect = chai.expect;

describe('ContactMechType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contactMechTypeComponentsPage: ContactMechTypeComponentsPage;
  let contactMechTypeUpdatePage: ContactMechTypeUpdatePage;
  let contactMechTypeDeleteDialog: ContactMechTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ContactMechTypes', async () => {
    await navBarPage.goToEntity('contact-mech-type');
    contactMechTypeComponentsPage = new ContactMechTypeComponentsPage();
    await browser.wait(ec.visibilityOf(contactMechTypeComponentsPage.title), 5000);
    expect(await contactMechTypeComponentsPage.getTitle()).to.eq('hrApp.contactMechType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(contactMechTypeComponentsPage.entities), ec.visibilityOf(contactMechTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ContactMechType page', async () => {
    await contactMechTypeComponentsPage.clickOnCreateButton();
    contactMechTypeUpdatePage = new ContactMechTypeUpdatePage();
    expect(await contactMechTypeUpdatePage.getPageTitle()).to.eq('hrApp.contactMechType.home.createOrEditLabel');
    await contactMechTypeUpdatePage.cancel();
  });

  it('should create and save ContactMechTypes', async () => {
    const nbButtonsBeforeCreate = await contactMechTypeComponentsPage.countDeleteButtons();

    await contactMechTypeComponentsPage.clickOnCreateButton();

    await promise.all([contactMechTypeUpdatePage.setNameInput('name'), contactMechTypeUpdatePage.setDescriptionInput('description')]);

    expect(await contactMechTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await contactMechTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await contactMechTypeUpdatePage.save();
    expect(await contactMechTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await contactMechTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ContactMechType', async () => {
    const nbButtonsBeforeDelete = await contactMechTypeComponentsPage.countDeleteButtons();
    await contactMechTypeComponentsPage.clickOnLastDeleteButton();

    contactMechTypeDeleteDialog = new ContactMechTypeDeleteDialog();
    expect(await contactMechTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.contactMechType.delete.question');
    await contactMechTypeDeleteDialog.clickOnConfirmButton();

    expect(await contactMechTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
