import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CommunicationEventTypeComponentsPage,
  CommunicationEventTypeDeleteDialog,
  CommunicationEventTypeUpdatePage,
} from './communication-event-type.page-object';

const expect = chai.expect;

describe('CommunicationEventType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let communicationEventTypeComponentsPage: CommunicationEventTypeComponentsPage;
  let communicationEventTypeUpdatePage: CommunicationEventTypeUpdatePage;
  let communicationEventTypeDeleteDialog: CommunicationEventTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CommunicationEventTypes', async () => {
    await navBarPage.goToEntity('communication-event-type');
    communicationEventTypeComponentsPage = new CommunicationEventTypeComponentsPage();
    await browser.wait(ec.visibilityOf(communicationEventTypeComponentsPage.title), 5000);
    expect(await communicationEventTypeComponentsPage.getTitle()).to.eq('hrApp.communicationEventType.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(communicationEventTypeComponentsPage.entities), ec.visibilityOf(communicationEventTypeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CommunicationEventType page', async () => {
    await communicationEventTypeComponentsPage.clickOnCreateButton();
    communicationEventTypeUpdatePage = new CommunicationEventTypeUpdatePage();
    expect(await communicationEventTypeUpdatePage.getPageTitle()).to.eq('hrApp.communicationEventType.home.createOrEditLabel');
    await communicationEventTypeUpdatePage.cancel();
  });

  it('should create and save CommunicationEventTypes', async () => {
    const nbButtonsBeforeCreate = await communicationEventTypeComponentsPage.countDeleteButtons();

    await communicationEventTypeComponentsPage.clickOnCreateButton();

    await promise.all([
      communicationEventTypeUpdatePage.setNameInput('name'),
      communicationEventTypeUpdatePage.setDescriptionInput('description'),
      communicationEventTypeUpdatePage.contactMechTypeSelectLastOption(),
    ]);

    expect(await communicationEventTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await communicationEventTypeUpdatePage.getDescriptionInput()).to.eq(
      'description',
      'Expected Description value to be equals to description'
    );

    await communicationEventTypeUpdatePage.save();
    expect(await communicationEventTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await communicationEventTypeComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CommunicationEventType', async () => {
    const nbButtonsBeforeDelete = await communicationEventTypeComponentsPage.countDeleteButtons();
    await communicationEventTypeComponentsPage.clickOnLastDeleteButton();

    communicationEventTypeDeleteDialog = new CommunicationEventTypeDeleteDialog();
    expect(await communicationEventTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.communicationEventType.delete.question');
    await communicationEventTypeDeleteDialog.clickOnConfirmButton();

    expect(await communicationEventTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
