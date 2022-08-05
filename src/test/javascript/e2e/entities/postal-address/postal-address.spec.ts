import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PostalAddressComponentsPage, PostalAddressDeleteDialog, PostalAddressUpdatePage } from './postal-address.page-object';

const expect = chai.expect;

describe('PostalAddress e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let postalAddressComponentsPage: PostalAddressComponentsPage;
  let postalAddressUpdatePage: PostalAddressUpdatePage;
  let postalAddressDeleteDialog: PostalAddressDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PostalAddresses', async () => {
    await navBarPage.goToEntity('postal-address');
    postalAddressComponentsPage = new PostalAddressComponentsPage();
    await browser.wait(ec.visibilityOf(postalAddressComponentsPage.title), 5000);
    expect(await postalAddressComponentsPage.getTitle()).to.eq('hrApp.postalAddress.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(postalAddressComponentsPage.entities), ec.visibilityOf(postalAddressComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PostalAddress page', async () => {
    await postalAddressComponentsPage.clickOnCreateButton();
    postalAddressUpdatePage = new PostalAddressUpdatePage();
    expect(await postalAddressUpdatePage.getPageTitle()).to.eq('hrApp.postalAddress.home.createOrEditLabel');
    await postalAddressUpdatePage.cancel();
  });

  it('should create and save PostalAddresses', async () => {
    const nbButtonsBeforeCreate = await postalAddressComponentsPage.countDeleteButtons();

    await postalAddressComponentsPage.clickOnCreateButton();

    await promise.all([
      postalAddressUpdatePage.setToNameInput('toName'),
      postalAddressUpdatePage.setAddress1Input('address1'),
      postalAddressUpdatePage.setAddress2Input('address2'),
      postalAddressUpdatePage.setCityInput('city'),
      postalAddressUpdatePage.setLandmarkInput('landmark'),
      postalAddressUpdatePage.setPostalCodeInput('postalCode'),
      postalAddressUpdatePage.setCustomAddressTypeInput('customAddressType'),
      postalAddressUpdatePage.setStateStrInput('stateStr'),
      postalAddressUpdatePage.setCountryStrInput('countryStr'),
      postalAddressUpdatePage.setNoteInput('note'),
      postalAddressUpdatePage.setDirectionsInput('directions'),
      postalAddressUpdatePage.stateSelectLastOption(),
      postalAddressUpdatePage.pincodeSelectLastOption(),
      postalAddressUpdatePage.countrySelectLastOption(),
      postalAddressUpdatePage.contactMechSelectLastOption(),
      postalAddressUpdatePage.geoPointSelectLastOption(),
    ]);

    expect(await postalAddressUpdatePage.getToNameInput()).to.eq('toName', 'Expected ToName value to be equals to toName');
    expect(await postalAddressUpdatePage.getAddress1Input()).to.eq('address1', 'Expected Address1 value to be equals to address1');
    expect(await postalAddressUpdatePage.getAddress2Input()).to.eq('address2', 'Expected Address2 value to be equals to address2');
    expect(await postalAddressUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');
    expect(await postalAddressUpdatePage.getLandmarkInput()).to.eq('landmark', 'Expected Landmark value to be equals to landmark');
    expect(await postalAddressUpdatePage.getPostalCodeInput()).to.eq('postalCode', 'Expected PostalCode value to be equals to postalCode');
    const selectedIsDefault = postalAddressUpdatePage.getIsDefaultInput();
    if (await selectedIsDefault.isSelected()) {
      await postalAddressUpdatePage.getIsDefaultInput().click();
      expect(await postalAddressUpdatePage.getIsDefaultInput().isSelected(), 'Expected isDefault not to be selected').to.be.false;
    } else {
      await postalAddressUpdatePage.getIsDefaultInput().click();
      expect(await postalAddressUpdatePage.getIsDefaultInput().isSelected(), 'Expected isDefault to be selected').to.be.true;
    }
    expect(await postalAddressUpdatePage.getCustomAddressTypeInput()).to.eq(
      'customAddressType',
      'Expected CustomAddressType value to be equals to customAddressType'
    );
    expect(await postalAddressUpdatePage.getStateStrInput()).to.eq('stateStr', 'Expected StateStr value to be equals to stateStr');
    expect(await postalAddressUpdatePage.getCountryStrInput()).to.eq('countryStr', 'Expected CountryStr value to be equals to countryStr');
    const selectedIsIndianAddress = postalAddressUpdatePage.getIsIndianAddressInput();
    if (await selectedIsIndianAddress.isSelected()) {
      await postalAddressUpdatePage.getIsIndianAddressInput().click();
      expect(await postalAddressUpdatePage.getIsIndianAddressInput().isSelected(), 'Expected isIndianAddress not to be selected').to.be
        .false;
    } else {
      await postalAddressUpdatePage.getIsIndianAddressInput().click();
      expect(await postalAddressUpdatePage.getIsIndianAddressInput().isSelected(), 'Expected isIndianAddress to be selected').to.be.true;
    }
    expect(await postalAddressUpdatePage.getNoteInput()).to.eq('note', 'Expected Note value to be equals to note');
    expect(await postalAddressUpdatePage.getDirectionsInput()).to.eq('directions', 'Expected Directions value to be equals to directions');

    await postalAddressUpdatePage.save();
    expect(await postalAddressUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await postalAddressComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last PostalAddress', async () => {
    const nbButtonsBeforeDelete = await postalAddressComponentsPage.countDeleteButtons();
    await postalAddressComponentsPage.clickOnLastDeleteButton();

    postalAddressDeleteDialog = new PostalAddressDeleteDialog();
    expect(await postalAddressDeleteDialog.getDialogTitle()).to.eq('hrApp.postalAddress.delete.question');
    await postalAddressDeleteDialog.clickOnConfirmButton();

    expect(await postalAddressComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
