import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PartyResumeComponentsPage, PartyResumeDeleteDialog, PartyResumeUpdatePage } from './party-resume.page-object';
import * as path from 'path';

const expect = chai.expect;

describe('PartyResume e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let partyResumeComponentsPage: PartyResumeComponentsPage;
  let partyResumeUpdatePage: PartyResumeUpdatePage;
  let partyResumeDeleteDialog: PartyResumeDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PartyResumes', async () => {
    await navBarPage.goToEntity('party-resume');
    partyResumeComponentsPage = new PartyResumeComponentsPage();
    await browser.wait(ec.visibilityOf(partyResumeComponentsPage.title), 5000);
    expect(await partyResumeComponentsPage.getTitle()).to.eq('hrApp.partyResume.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(partyResumeComponentsPage.entities), ec.visibilityOf(partyResumeComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PartyResume page', async () => {
    await partyResumeComponentsPage.clickOnCreateButton();
    partyResumeUpdatePage = new PartyResumeUpdatePage();
    expect(await partyResumeUpdatePage.getPageTitle()).to.eq('hrApp.partyResume.home.createOrEditLabel');
    await partyResumeUpdatePage.cancel();
  });

  it('should create and save PartyResumes', async () => {
    const nbButtonsBeforeCreate = await partyResumeComponentsPage.countDeleteButtons();

    await partyResumeComponentsPage.clickOnCreateButton();

    await promise.all([
      partyResumeUpdatePage.setTextInput('text'),
      partyResumeUpdatePage.setResumeDateInput('2000-12-31'),
      partyResumeUpdatePage.setFileAttachmentInput(absolutePath),
      partyResumeUpdatePage.setAttachmentUrlInput('attachmentUrl'),
      partyResumeUpdatePage.setMimeTypeInput('mimeType'),
      partyResumeUpdatePage.partySelectLastOption(),
    ]);

    expect(await partyResumeUpdatePage.getTextInput()).to.eq('text', 'Expected Text value to be equals to text');
    expect(await partyResumeUpdatePage.getResumeDateInput()).to.eq('2000-12-31', 'Expected resumeDate value to be equals to 2000-12-31');
    expect(await partyResumeUpdatePage.getFileAttachmentInput()).to.endsWith(
      fileNameToUpload,
      'Expected FileAttachment value to be end with ' + fileNameToUpload
    );
    expect(await partyResumeUpdatePage.getAttachmentUrlInput()).to.eq(
      'attachmentUrl',
      'Expected AttachmentUrl value to be equals to attachmentUrl'
    );
    expect(await partyResumeUpdatePage.getMimeTypeInput()).to.eq('mimeType', 'Expected MimeType value to be equals to mimeType');

    await partyResumeUpdatePage.save();
    expect(await partyResumeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await partyResumeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PartyResume', async () => {
    const nbButtonsBeforeDelete = await partyResumeComponentsPage.countDeleteButtons();
    await partyResumeComponentsPage.clickOnLastDeleteButton();

    partyResumeDeleteDialog = new PartyResumeDeleteDialog();
    expect(await partyResumeDeleteDialog.getDialogTitle()).to.eq('hrApp.partyResume.delete.question');
    await partyResumeDeleteDialog.clickOnConfirmButton();

    expect(await partyResumeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
