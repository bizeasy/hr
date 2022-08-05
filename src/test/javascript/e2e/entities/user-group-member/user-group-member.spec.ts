import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  UserGroupMemberComponentsPage,
  /* UserGroupMemberDeleteDialog, */
  UserGroupMemberUpdatePage,
} from './user-group-member.page-object';

const expect = chai.expect;

describe('UserGroupMember e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let userGroupMemberComponentsPage: UserGroupMemberComponentsPage;
  let userGroupMemberUpdatePage: UserGroupMemberUpdatePage;
  /* let userGroupMemberDeleteDialog: UserGroupMemberDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UserGroupMembers', async () => {
    await navBarPage.goToEntity('user-group-member');
    userGroupMemberComponentsPage = new UserGroupMemberComponentsPage();
    await browser.wait(ec.visibilityOf(userGroupMemberComponentsPage.title), 5000);
    expect(await userGroupMemberComponentsPage.getTitle()).to.eq('hrApp.userGroupMember.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(userGroupMemberComponentsPage.entities), ec.visibilityOf(userGroupMemberComponentsPage.noResult)),
      1000
    );
  });

  it('should load create UserGroupMember page', async () => {
    await userGroupMemberComponentsPage.clickOnCreateButton();
    userGroupMemberUpdatePage = new UserGroupMemberUpdatePage();
    expect(await userGroupMemberUpdatePage.getPageTitle()).to.eq('hrApp.userGroupMember.home.createOrEditLabel');
    await userGroupMemberUpdatePage.cancel();
  });

  /* it('should create and save UserGroupMembers', async () => {
        const nbButtonsBeforeCreate = await userGroupMemberComponentsPage.countDeleteButtons();

        await userGroupMemberComponentsPage.clickOnCreateButton();

        await promise.all([
            userGroupMemberUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            userGroupMemberUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            userGroupMemberUpdatePage.userGroupSelectLastOption(),
            userGroupMemberUpdatePage.userSelectLastOption(),
        ]);

        expect(await userGroupMemberUpdatePage.getFromDateInput()).to.contain('2001-01-01T02:30', 'Expected fromDate value to be equals to 2000-12-31');
        expect(await userGroupMemberUpdatePage.getThruDateInput()).to.contain('2001-01-01T02:30', 'Expected thruDate value to be equals to 2000-12-31');

        await userGroupMemberUpdatePage.save();
        expect(await userGroupMemberUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await userGroupMemberComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last UserGroupMember', async () => {
        const nbButtonsBeforeDelete = await userGroupMemberComponentsPage.countDeleteButtons();
        await userGroupMemberComponentsPage.clickOnLastDeleteButton();

        userGroupMemberDeleteDialog = new UserGroupMemberDeleteDialog();
        expect(await userGroupMemberDeleteDialog.getDialogTitle())
            .to.eq('hrApp.userGroupMember.delete.question');
        await userGroupMemberDeleteDialog.clickOnConfirmButton();

        expect(await userGroupMemberComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
