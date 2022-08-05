import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ProductStoreUserGroupComponentsPage,
  /* ProductStoreUserGroupDeleteDialog, */
  ProductStoreUserGroupUpdatePage,
} from './product-store-user-group.page-object';

const expect = chai.expect;

describe('ProductStoreUserGroup e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productStoreUserGroupComponentsPage: ProductStoreUserGroupComponentsPage;
  let productStoreUserGroupUpdatePage: ProductStoreUserGroupUpdatePage;
  /* let productStoreUserGroupDeleteDialog: ProductStoreUserGroupDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductStoreUserGroups', async () => {
    await navBarPage.goToEntity('product-store-user-group');
    productStoreUserGroupComponentsPage = new ProductStoreUserGroupComponentsPage();
    await browser.wait(ec.visibilityOf(productStoreUserGroupComponentsPage.title), 5000);
    expect(await productStoreUserGroupComponentsPage.getTitle()).to.eq('hrApp.productStoreUserGroup.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(productStoreUserGroupComponentsPage.entities), ec.visibilityOf(productStoreUserGroupComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ProductStoreUserGroup page', async () => {
    await productStoreUserGroupComponentsPage.clickOnCreateButton();
    productStoreUserGroupUpdatePage = new ProductStoreUserGroupUpdatePage();
    expect(await productStoreUserGroupUpdatePage.getPageTitle()).to.eq('hrApp.productStoreUserGroup.home.createOrEditLabel');
    await productStoreUserGroupUpdatePage.cancel();
  });

  /* it('should create and save ProductStoreUserGroups', async () => {
        const nbButtonsBeforeCreate = await productStoreUserGroupComponentsPage.countDeleteButtons();

        await productStoreUserGroupComponentsPage.clickOnCreateButton();

        await promise.all([
            productStoreUserGroupUpdatePage.userGroupSelectLastOption(),
            productStoreUserGroupUpdatePage.userSelectLastOption(),
            productStoreUserGroupUpdatePage.partySelectLastOption(),
            productStoreUserGroupUpdatePage.productStoreSelectLastOption(),
        ]);


        await productStoreUserGroupUpdatePage.save();
        expect(await productStoreUserGroupUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await productStoreUserGroupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last ProductStoreUserGroup', async () => {
        const nbButtonsBeforeDelete = await productStoreUserGroupComponentsPage.countDeleteButtons();
        await productStoreUserGroupComponentsPage.clickOnLastDeleteButton();

        productStoreUserGroupDeleteDialog = new ProductStoreUserGroupDeleteDialog();
        expect(await productStoreUserGroupDeleteDialog.getDialogTitle())
            .to.eq('hrApp.productStoreUserGroup.delete.question');
        await productStoreUserGroupDeleteDialog.clickOnConfirmButton();

        expect(await productStoreUserGroupComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
