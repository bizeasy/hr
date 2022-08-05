import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ProductCategoryMemberComponentsPage,
  ProductCategoryMemberDeleteDialog,
  ProductCategoryMemberUpdatePage,
} from './product-category-member.page-object';

const expect = chai.expect;

describe('ProductCategoryMember e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productCategoryMemberComponentsPage: ProductCategoryMemberComponentsPage;
  let productCategoryMemberUpdatePage: ProductCategoryMemberUpdatePage;
  let productCategoryMemberDeleteDialog: ProductCategoryMemberDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProductCategoryMembers', async () => {
    await navBarPage.goToEntity('product-category-member');
    productCategoryMemberComponentsPage = new ProductCategoryMemberComponentsPage();
    await browser.wait(ec.visibilityOf(productCategoryMemberComponentsPage.title), 5000);
    expect(await productCategoryMemberComponentsPage.getTitle()).to.eq('hrApp.productCategoryMember.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(productCategoryMemberComponentsPage.entities), ec.visibilityOf(productCategoryMemberComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ProductCategoryMember page', async () => {
    await productCategoryMemberComponentsPage.clickOnCreateButton();
    productCategoryMemberUpdatePage = new ProductCategoryMemberUpdatePage();
    expect(await productCategoryMemberUpdatePage.getPageTitle()).to.eq('hrApp.productCategoryMember.home.createOrEditLabel');
    await productCategoryMemberUpdatePage.cancel();
  });

  it('should create and save ProductCategoryMembers', async () => {
    const nbButtonsBeforeCreate = await productCategoryMemberComponentsPage.countDeleteButtons();

    await productCategoryMemberComponentsPage.clickOnCreateButton();

    await promise.all([
      productCategoryMemberUpdatePage.setFromDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      productCategoryMemberUpdatePage.setThruDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      productCategoryMemberUpdatePage.setSequenceNoInput('5'),
      productCategoryMemberUpdatePage.productSelectLastOption(),
      productCategoryMemberUpdatePage.productCategorySelectLastOption(),
    ]);

    expect(await productCategoryMemberUpdatePage.getFromDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await productCategoryMemberUpdatePage.getThruDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected thruDate value to be equals to 2000-12-31'
    );
    expect(await productCategoryMemberUpdatePage.getSequenceNoInput()).to.eq('5', 'Expected sequenceNo value to be equals to 5');

    await productCategoryMemberUpdatePage.save();
    expect(await productCategoryMemberUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productCategoryMemberComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProductCategoryMember', async () => {
    const nbButtonsBeforeDelete = await productCategoryMemberComponentsPage.countDeleteButtons();
    await productCategoryMemberComponentsPage.clickOnLastDeleteButton();

    productCategoryMemberDeleteDialog = new ProductCategoryMemberDeleteDialog();
    expect(await productCategoryMemberDeleteDialog.getDialogTitle()).to.eq('hrApp.productCategoryMember.delete.question');
    await productCategoryMemberDeleteDialog.clickOnConfirmButton();

    expect(await productCategoryMemberComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
