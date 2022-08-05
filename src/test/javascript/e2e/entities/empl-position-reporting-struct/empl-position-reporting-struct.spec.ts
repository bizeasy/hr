import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  EmplPositionReportingStructComponentsPage,
  EmplPositionReportingStructDeleteDialog,
  EmplPositionReportingStructUpdatePage,
} from './empl-position-reporting-struct.page-object';

const expect = chai.expect;

describe('EmplPositionReportingStruct e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let emplPositionReportingStructComponentsPage: EmplPositionReportingStructComponentsPage;
  let emplPositionReportingStructUpdatePage: EmplPositionReportingStructUpdatePage;
  let emplPositionReportingStructDeleteDialog: EmplPositionReportingStructDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EmplPositionReportingStructs', async () => {
    await navBarPage.goToEntity('empl-position-reporting-struct');
    emplPositionReportingStructComponentsPage = new EmplPositionReportingStructComponentsPage();
    await browser.wait(ec.visibilityOf(emplPositionReportingStructComponentsPage.title), 5000);
    expect(await emplPositionReportingStructComponentsPage.getTitle()).to.eq('hrApp.emplPositionReportingStruct.home.title');
    await browser.wait(
      ec.or(
        ec.visibilityOf(emplPositionReportingStructComponentsPage.entities),
        ec.visibilityOf(emplPositionReportingStructComponentsPage.noResult)
      ),
      1000
    );
  });

  it('should load create EmplPositionReportingStruct page', async () => {
    await emplPositionReportingStructComponentsPage.clickOnCreateButton();
    emplPositionReportingStructUpdatePage = new EmplPositionReportingStructUpdatePage();
    expect(await emplPositionReportingStructUpdatePage.getPageTitle()).to.eq('hrApp.emplPositionReportingStruct.home.createOrEditLabel');
    await emplPositionReportingStructUpdatePage.cancel();
  });

  it('should create and save EmplPositionReportingStructs', async () => {
    const nbButtonsBeforeCreate = await emplPositionReportingStructComponentsPage.countDeleteButtons();

    await emplPositionReportingStructComponentsPage.clickOnCreateButton();

    await promise.all([
      emplPositionReportingStructUpdatePage.setFromDateInput('2000-12-31'),
      emplPositionReportingStructUpdatePage.setThruDateInput('2000-12-31'),
      emplPositionReportingStructUpdatePage.setCommentsInput('comments'),
    ]);

    expect(await emplPositionReportingStructUpdatePage.getFromDateInput()).to.eq(
      '2000-12-31',
      'Expected fromDate value to be equals to 2000-12-31'
    );
    expect(await emplPositionReportingStructUpdatePage.getThruDateInput()).to.eq(
      '2000-12-31',
      'Expected thruDate value to be equals to 2000-12-31'
    );
    expect(await emplPositionReportingStructUpdatePage.getCommentsInput()).to.eq(
      'comments',
      'Expected Comments value to be equals to comments'
    );

    await emplPositionReportingStructUpdatePage.save();
    expect(await emplPositionReportingStructUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await emplPositionReportingStructComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EmplPositionReportingStruct', async () => {
    const nbButtonsBeforeDelete = await emplPositionReportingStructComponentsPage.countDeleteButtons();
    await emplPositionReportingStructComponentsPage.clickOnLastDeleteButton();

    emplPositionReportingStructDeleteDialog = new EmplPositionReportingStructDeleteDialog();
    expect(await emplPositionReportingStructDeleteDialog.getDialogTitle()).to.eq('hrApp.emplPositionReportingStruct.delete.question');
    await emplPositionReportingStructDeleteDialog.clickOnConfirmButton();

    expect(await emplPositionReportingStructComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
