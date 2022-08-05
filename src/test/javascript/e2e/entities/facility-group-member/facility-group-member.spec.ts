import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  FacilityGroupMemberComponentsPage,
  FacilityGroupMemberDeleteDialog,
  FacilityGroupMemberUpdatePage,
} from './facility-group-member.page-object';

const expect = chai.expect;

describe('FacilityGroupMember e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let facilityGroupMemberComponentsPage: FacilityGroupMemberComponentsPage;
  let facilityGroupMemberUpdatePage: FacilityGroupMemberUpdatePage;
  let facilityGroupMemberDeleteDialog: FacilityGroupMemberDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FacilityGroupMembers', async () => {
    await navBarPage.goToEntity('facility-group-member');
    facilityGroupMemberComponentsPage = new FacilityGroupMemberComponentsPage();
    await browser.wait(ec.visibilityOf(facilityGroupMemberComponentsPage.title), 5000);
    expect(await facilityGroupMemberComponentsPage.getTitle()).to.eq('hrApp.facilityGroupMember.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(facilityGroupMemberComponentsPage.entities), ec.visibilityOf(facilityGroupMemberComponentsPage.noResult)),
      1000
    );
  });

  it('should load create FacilityGroupMember page', async () => {
    await facilityGroupMemberComponentsPage.clickOnCreateButton();
    facilityGroupMemberUpdatePage = new FacilityGroupMemberUpdatePage();
    expect(await facilityGroupMemberUpdatePage.getPageTitle()).to.eq('hrApp.facilityGroupMember.home.createOrEditLabel');
    await facilityGroupMemberUpdatePage.cancel();
  });

  it('should create and save FacilityGroupMembers', async () => {
    const nbButtonsBeforeCreate = await facilityGroupMemberComponentsPage.countDeleteButtons();

    await facilityGroupMemberComponentsPage.clickOnCreateButton();

    await promise.all([
      facilityGroupMemberUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      facilityGroupMemberUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      facilityGroupMemberUpdatePage.setSequenceNoInput('5'),
      facilityGroupMemberUpdatePage.facilitySelectLastOption(),
      facilityGroupMemberUpdatePage.facilityGroupSelectLastOption(),
    ]);

    expect(await facilityGroupMemberUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await facilityGroupMemberUpdatePage.getThruDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected thruDate value to be equals to 2000-12-31'
    );
    expect(await facilityGroupMemberUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');

    await facilityGroupMemberUpdatePage.save();
    expect(await facilityGroupMemberUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await facilityGroupMemberComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FacilityGroupMember', async () => {
    const nbButtonsBeforeDelete = await facilityGroupMemberComponentsPage.countDeleteButtons();
    await facilityGroupMemberComponentsPage.clickOnLastDeleteButton();

    facilityGroupMemberDeleteDialog = new FacilityGroupMemberDeleteDialog();
    expect(await facilityGroupMemberDeleteDialog.getDialogTitle()).to.eq('hrApp.facilityGroupMember.delete.question');
    await facilityGroupMemberDeleteDialog.clickOnConfirmButton();

    expect(await facilityGroupMemberComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
