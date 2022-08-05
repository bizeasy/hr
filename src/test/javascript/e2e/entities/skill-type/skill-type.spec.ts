import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SkillTypeComponentsPage, SkillTypeDeleteDialog, SkillTypeUpdatePage } from './skill-type.page-object';

const expect = chai.expect;

describe('SkillType e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let skillTypeComponentsPage: SkillTypeComponentsPage;
  let skillTypeUpdatePage: SkillTypeUpdatePage;
  let skillTypeDeleteDialog: SkillTypeDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load SkillTypes', async () => {
    await navBarPage.goToEntity('skill-type');
    skillTypeComponentsPage = new SkillTypeComponentsPage();
    await browser.wait(ec.visibilityOf(skillTypeComponentsPage.title), 5000);
    expect(await skillTypeComponentsPage.getTitle()).to.eq('hrApp.skillType.home.title');
    await browser.wait(ec.or(ec.visibilityOf(skillTypeComponentsPage.entities), ec.visibilityOf(skillTypeComponentsPage.noResult)), 1000);
  });

  it('should load create SkillType page', async () => {
    await skillTypeComponentsPage.clickOnCreateButton();
    skillTypeUpdatePage = new SkillTypeUpdatePage();
    expect(await skillTypeUpdatePage.getPageTitle()).to.eq('hrApp.skillType.home.createOrEditLabel');
    await skillTypeUpdatePage.cancel();
  });

  it('should create and save SkillTypes', async () => {
    const nbButtonsBeforeCreate = await skillTypeComponentsPage.countDeleteButtons();

    await skillTypeComponentsPage.clickOnCreateButton();

    await promise.all([skillTypeUpdatePage.setNameInput('name'), skillTypeUpdatePage.setDescriptionInput('description')]);

    expect(await skillTypeUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await skillTypeUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');

    await skillTypeUpdatePage.save();
    expect(await skillTypeUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await skillTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last SkillType', async () => {
    const nbButtonsBeforeDelete = await skillTypeComponentsPage.countDeleteButtons();
    await skillTypeComponentsPage.clickOnLastDeleteButton();

    skillTypeDeleteDialog = new SkillTypeDeleteDialog();
    expect(await skillTypeDeleteDialog.getDialogTitle()).to.eq('hrApp.skillType.delete.question');
    await skillTypeDeleteDialog.clickOnConfirmButton();

    expect(await skillTypeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
