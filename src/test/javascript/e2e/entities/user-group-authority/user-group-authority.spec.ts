import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  UserGroupAuthorityComponentsPage,
  /* UserGroupAuthorityDeleteDialog, */
  UserGroupAuthorityUpdatePage,
} from './user-group-authority.page-object';

const expect = chai.expect;

describe('UserGroupAuthority e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let userGroupAuthorityComponentsPage: UserGroupAuthorityComponentsPage;
  let userGroupAuthorityUpdatePage: UserGroupAuthorityUpdatePage;
  /* let userGroupAuthorityDeleteDialog: UserGroupAuthorityDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UserGroupAuthorities', async () => {
    await navBarPage.goToEntity('user-group-authority');
    userGroupAuthorityComponentsPage = new UserGroupAuthorityComponentsPage();
    await browser.wait(ec.visibilityOf(userGroupAuthorityComponentsPage.title), 5000);
    expect(await userGroupAuthorityComponentsPage.getTitle()).to.eq('hrApp.userGroupAuthority.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(userGroupAuthorityComponentsPage.entities), ec.visibilityOf(userGroupAuthorityComponentsPage.noResult)),
      1000
    );
  });

  it('should load create UserGroupAuthority page', async () => {
    await userGroupAuthorityComponentsPage.clickOnCreateButton();
    userGroupAuthorityUpdatePage = new UserGroupAuthorityUpdatePage();
    expect(await userGroupAuthorityUpdatePage.getPageTitle()).to.eq('hrApp.userGroupAuthority.home.createOrEditLabel');
    await userGroupAuthorityUpdatePage.cancel();
  });

  /* it('should create and save UserGroupAuthorities', async () => {
        const nbButtonsBeforeCreate = await userGroupAuthorityComponentsPage.countDeleteButtons();

        await userGroupAuthorityComponentsPage.clickOnCreateButton();

        await promise.all([
            userGroupAuthorityUpdatePage.setAuthorityInput('authority'),
            userGroupAuthorityUpdatePage.userGroupSelectLastOption(),
        ]);

        expect(await userGroupAuthorityUpdatePage.getAuthorityInput()).to.eq('authority', 'Expected Authority value to be equals to authority');

        await userGroupAuthorityUpdatePage.save();
        expect(await userGroupAuthorityUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await userGroupAuthorityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last UserGroupAuthority', async () => {
        const nbButtonsBeforeDelete = await userGroupAuthorityComponentsPage.countDeleteButtons();
        await userGroupAuthorityComponentsPage.clickOnLastDeleteButton();

        userGroupAuthorityDeleteDialog = new UserGroupAuthorityDeleteDialog();
        expect(await userGroupAuthorityDeleteDialog.getDialogTitle())
            .to.eq('hrApp.userGroupAuthority.delete.question');
        await userGroupAuthorityDeleteDialog.clickOnConfirmButton();

        expect(await userGroupAuthorityComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
