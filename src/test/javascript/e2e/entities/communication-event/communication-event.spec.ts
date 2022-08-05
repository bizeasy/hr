import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CommunicationEventComponentsPage,
  CommunicationEventDeleteDialog,
  CommunicationEventUpdatePage,
} from './communication-event.page-object';

const expect = chai.expect;

describe('CommunicationEvent e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let communicationEventComponentsPage: CommunicationEventComponentsPage;
  let communicationEventUpdatePage: CommunicationEventUpdatePage;
  let communicationEventDeleteDialog: CommunicationEventDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CommunicationEvents', async () => {
    await navBarPage.goToEntity('communication-event');
    communicationEventComponentsPage = new CommunicationEventComponentsPage();
    await browser.wait(ec.visibilityOf(communicationEventComponentsPage.title), 5000);
    expect(await communicationEventComponentsPage.getTitle()).to.eq('hrApp.communicationEvent.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(communicationEventComponentsPage.entities), ec.visibilityOf(communicationEventComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CommunicationEvent page', async () => {
    await communicationEventComponentsPage.clickOnCreateButton();
    communicationEventUpdatePage = new CommunicationEventUpdatePage();
    expect(await communicationEventUpdatePage.getPageTitle()).to.eq('hrApp.communicationEvent.home.createOrEditLabel');
    await communicationEventUpdatePage.cancel();
  });

  it('should create and save CommunicationEvents', async () => {
    const nbButtonsBeforeCreate = await communicationEventComponentsPage.countDeleteButtons();

    await communicationEventComponentsPage.clickOnCreateButton();

    await promise.all([
      communicationEventUpdatePage.setEntryDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      communicationEventUpdatePage.setSubjectInput('subject'),
      communicationEventUpdatePage.setContentInput('content'),
      communicationEventUpdatePage.setFromStringInput('fromString'),
      communicationEventUpdatePage.setToStringInput('toString'),
      communicationEventUpdatePage.setCcStringInput('ccString'),
      communicationEventUpdatePage.setMessageInput('message'),
      communicationEventUpdatePage.setDateStartedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      communicationEventUpdatePage.setDateEndedInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      communicationEventUpdatePage.setInfoInput('info'),
      communicationEventUpdatePage.communicationEventTypeSelectLastOption(),
      communicationEventUpdatePage.statusSelectLastOption(),
      communicationEventUpdatePage.contactMechTypeSelectLastOption(),
      communicationEventUpdatePage.contactMechFromSelectLastOption(),
      communicationEventUpdatePage.contactMechToSelectLastOption(),
      communicationEventUpdatePage.fromPartySelectLastOption(),
      communicationEventUpdatePage.toPartySelectLastOption(),
    ]);

    expect(await communicationEventUpdatePage.getEntryDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected entryDate value to be equals to 2000-12-31'
    );
    expect(await communicationEventUpdatePage.getSubjectInput()).to.eq('subject', 'Expected Subject value to be equals to subject');
    expect(await communicationEventUpdatePage.getContentInput()).to.eq('content', 'Expected Content value to be equals to content');
    expect(await communicationEventUpdatePage.getFromStringInput()).to.eq(
      'fromString',
      'Expected FromString value to be equals to fromString'
    );
    expect(await communicationEventUpdatePage.getToStringInput()).to.eq('toString', 'Expected ToString value to be equals to toString');
    expect(await communicationEventUpdatePage.getCcStringInput()).to.eq('ccString', 'Expected CcString value to be equals to ccString');
    expect(await communicationEventUpdatePage.getMessageInput()).to.eq('message', 'Expected Message value to be equals to message');
    expect(await communicationEventUpdatePage.getDateStartedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateStarted value to be equals to 2000-12-31'
    );
    expect(await communicationEventUpdatePage.getDateEndedInput()).to.contain(
      '2001-01-01T02:30',
      'Expected dateEnded value to be equals to 2000-12-31'
    );
    expect(await communicationEventUpdatePage.getInfoInput()).to.eq('info', 'Expected Info value to be equals to info');

    await communicationEventUpdatePage.save();
    expect(await communicationEventUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await communicationEventComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CommunicationEvent', async () => {
    const nbButtonsBeforeDelete = await communicationEventComponentsPage.countDeleteButtons();
    await communicationEventComponentsPage.clickOnLastDeleteButton();

    communicationEventDeleteDialog = new CommunicationEventDeleteDialog();
    expect(await communicationEventDeleteDialog.getDialogTitle()).to.eq('hrApp.communicationEvent.delete.question');
    await communicationEventDeleteDialog.clickOnConfirmButton();

    expect(await communicationEventComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
