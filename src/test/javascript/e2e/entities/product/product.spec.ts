import { browser, ExpectedConditions as ec, protractor, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProductComponentsPage, ProductDeleteDialog, ProductUpdatePage } from './product.page-object';

const expect = chai.expect;

describe('Product e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let productComponentsPage: ProductComponentsPage;
  let productUpdatePage: ProductUpdatePage;
  let productDeleteDialog: ProductDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Products', async () => {
    await navBarPage.goToEntity('product');
    productComponentsPage = new ProductComponentsPage();
    await browser.wait(ec.visibilityOf(productComponentsPage.title), 5000);
    expect(await productComponentsPage.getTitle()).to.eq('hrApp.product.home.title');
    await browser.wait(ec.or(ec.visibilityOf(productComponentsPage.entities), ec.visibilityOf(productComponentsPage.noResult)), 1000);
  });

  it('should load create Product page', async () => {
    await productComponentsPage.clickOnCreateButton();
    productUpdatePage = new ProductUpdatePage();
    expect(await productUpdatePage.getPageTitle()).to.eq('hrApp.product.home.createOrEditLabel');
    await productUpdatePage.cancel();
  });

  it('should create and save Products', async () => {
    const nbButtonsBeforeCreate = await productComponentsPage.countDeleteButtons();

    await productComponentsPage.clickOnCreateButton();

    await promise.all([
      productUpdatePage.setNameInput('name'),
      productUpdatePage.setInternalNameInput('internalName'),
      productUpdatePage.setBrandNameInput('brandName'),
      productUpdatePage.setVariantInput('variant'),
      productUpdatePage.setSkuInput('sku'),
      productUpdatePage.setIntroductionDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      productUpdatePage.setReleaseDateInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
      productUpdatePage.setQuantityIncludedInput('5'),
      productUpdatePage.setPiecesIncludedInput('5'),
      productUpdatePage.setWeightInput('5'),
      productUpdatePage.setHeightInput('5'),
      productUpdatePage.setWidthInput('5'),
      productUpdatePage.setDepthInput('5'),
      productUpdatePage.setSmallImageUrlInput('smallImageUrl'),
      productUpdatePage.setMediumImageUrlInput('mediumImageUrl'),
      productUpdatePage.setLargeImageUrlInput('largeImageUrl'),
      productUpdatePage.setDetailImageUrlInput('detailImageUrl'),
      productUpdatePage.setOriginalImageUrlInput('originalImageUrl'),
      productUpdatePage.setQuantityInput('5'),
      productUpdatePage.setCgstInput('5'),
      productUpdatePage.setIgstInput('5'),
      productUpdatePage.setSgstInput('5'),
      productUpdatePage.setPriceInput('5'),
      productUpdatePage.setCgstPercentageInput('5'),
      productUpdatePage.setIgstPercentageInput('5'),
      productUpdatePage.setSgstPercentageInput('5'),
      productUpdatePage.setTotalPriceInput('5'),
      productUpdatePage.setDescriptionInput('description'),
      productUpdatePage.setLongDescriptionInput('longDescription'),
      productUpdatePage.setInfoInput('info'),
      productUpdatePage.setChangeControlNoInput('changeControlNo'),
      productUpdatePage.setRetestDurationInput('PT12S'),
      productUpdatePage.setExpiryDurationInput('PT12S'),
      productUpdatePage.setValidationTypeInput('validationType'),
      productUpdatePage.setShelfLifeInput('5'),
      productUpdatePage.productTypeSelectLastOption(),
      productUpdatePage.quantityUomSelectLastOption(),
      productUpdatePage.weightUomSelectLastOption(),
      productUpdatePage.sizeUomSelectLastOption(),
      productUpdatePage.uomSelectLastOption(),
      productUpdatePage.primaryProductCategorySelectLastOption(),
      productUpdatePage.virtualProductSelectLastOption(),
      productUpdatePage.inventoryItemTypeSelectLastOption(),
      productUpdatePage.taxSlabSelectLastOption(),
      productUpdatePage.shelfLifeUomSelectLastOption(),
    ]);

    expect(await productUpdatePage.getNameInput()).to.eq('name', 'Expected Name value to be equals to name');
    expect(await productUpdatePage.getInternalNameInput()).to.eq(
      'internalName',
      'Expected InternalName value to be equals to internalName'
    );
    expect(await productUpdatePage.getBrandNameInput()).to.eq('brandName', 'Expected BrandName value to be equals to brandName');
    expect(await productUpdatePage.getVariantInput()).to.eq('variant', 'Expected Variant value to be equals to variant');
    expect(await productUpdatePage.getSkuInput()).to.eq('sku', 'Expected Sku value to be equals to sku');
    expect(await productUpdatePage.getIntroductionDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected introductionDate value to be equals to 2000-12-31'
    );
    expect(await productUpdatePage.getReleaseDateInput()).to.contain(
      '2001-01-01T02:30',
      'Expected releaseDate value to be equals to 2000-12-31'
    );
    expect(await productUpdatePage.getQuantityIncludedInput()).to.eq('5', 'Expected quantityIncluded value to be equals to 5');
    expect(await productUpdatePage.getPiecesIncludedInput()).to.eq('5', 'Expected piecesIncluded value to be equals to 5');
    expect(await productUpdatePage.getWeightInput()).to.eq('5', 'Expected weight value to be equals to 5');
    expect(await productUpdatePage.getHeightInput()).to.eq('5', 'Expected height value to be equals to 5');
    expect(await productUpdatePage.getWidthInput()).to.eq('5', 'Expected width value to be equals to 5');
    expect(await productUpdatePage.getDepthInput()).to.eq('5', 'Expected depth value to be equals to 5');
    expect(await productUpdatePage.getSmallImageUrlInput()).to.eq(
      'smallImageUrl',
      'Expected SmallImageUrl value to be equals to smallImageUrl'
    );
    expect(await productUpdatePage.getMediumImageUrlInput()).to.eq(
      'mediumImageUrl',
      'Expected MediumImageUrl value to be equals to mediumImageUrl'
    );
    expect(await productUpdatePage.getLargeImageUrlInput()).to.eq(
      'largeImageUrl',
      'Expected LargeImageUrl value to be equals to largeImageUrl'
    );
    expect(await productUpdatePage.getDetailImageUrlInput()).to.eq(
      'detailImageUrl',
      'Expected DetailImageUrl value to be equals to detailImageUrl'
    );
    expect(await productUpdatePage.getOriginalImageUrlInput()).to.eq(
      'originalImageUrl',
      'Expected OriginalImageUrl value to be equals to originalImageUrl'
    );
    expect(await productUpdatePage.getQuantityInput()).to.eq('5', 'Expected quantity value to be equals to 5');
    expect(await productUpdatePage.getCgstInput()).to.eq('5', 'Expected cgst value to be equals to 5');
    expect(await productUpdatePage.getIgstInput()).to.eq('5', 'Expected igst value to be equals to 5');
    expect(await productUpdatePage.getSgstInput()).to.eq('5', 'Expected sgst value to be equals to 5');
    expect(await productUpdatePage.getPriceInput()).to.eq('5', 'Expected price value to be equals to 5');
    expect(await productUpdatePage.getCgstPercentageInput()).to.eq('5', 'Expected cgstPercentage value to be equals to 5');
    expect(await productUpdatePage.getIgstPercentageInput()).to.eq('5', 'Expected igstPercentage value to be equals to 5');
    expect(await productUpdatePage.getSgstPercentageInput()).to.eq('5', 'Expected sgstPercentage value to be equals to 5');
    expect(await productUpdatePage.getTotalPriceInput()).to.eq('5', 'Expected totalPrice value to be equals to 5');
    expect(await productUpdatePage.getDescriptionInput()).to.eq('description', 'Expected Description value to be equals to description');
    expect(await productUpdatePage.getLongDescriptionInput()).to.eq(
      'longDescription',
      'Expected LongDescription value to be equals to longDescription'
    );
    expect(await productUpdatePage.getInfoInput()).to.eq('info', 'Expected Info value to be equals to info');
    const selectedIsVirtual = productUpdatePage.getIsVirtualInput();
    if (await selectedIsVirtual.isSelected()) {
      await productUpdatePage.getIsVirtualInput().click();
      expect(await productUpdatePage.getIsVirtualInput().isSelected(), 'Expected isVirtual not to be selected').to.be.false;
    } else {
      await productUpdatePage.getIsVirtualInput().click();
      expect(await productUpdatePage.getIsVirtualInput().isSelected(), 'Expected isVirtual to be selected').to.be.true;
    }
    const selectedIsVariant = productUpdatePage.getIsVariantInput();
    if (await selectedIsVariant.isSelected()) {
      await productUpdatePage.getIsVariantInput().click();
      expect(await productUpdatePage.getIsVariantInput().isSelected(), 'Expected isVariant not to be selected').to.be.false;
    } else {
      await productUpdatePage.getIsVariantInput().click();
      expect(await productUpdatePage.getIsVariantInput().isSelected(), 'Expected isVariant to be selected').to.be.true;
    }
    const selectedRequireInventory = productUpdatePage.getRequireInventoryInput();
    if (await selectedRequireInventory.isSelected()) {
      await productUpdatePage.getRequireInventoryInput().click();
      expect(await productUpdatePage.getRequireInventoryInput().isSelected(), 'Expected requireInventory not to be selected').to.be.false;
    } else {
      await productUpdatePage.getRequireInventoryInput().click();
      expect(await productUpdatePage.getRequireInventoryInput().isSelected(), 'Expected requireInventory to be selected').to.be.true;
    }
    const selectedReturnable = productUpdatePage.getReturnableInput();
    if (await selectedReturnable.isSelected()) {
      await productUpdatePage.getReturnableInput().click();
      expect(await productUpdatePage.getReturnableInput().isSelected(), 'Expected returnable not to be selected').to.be.false;
    } else {
      await productUpdatePage.getReturnableInput().click();
      expect(await productUpdatePage.getReturnableInput().isSelected(), 'Expected returnable to be selected').to.be.true;
    }
    const selectedIsPopular = productUpdatePage.getIsPopularInput();
    if (await selectedIsPopular.isSelected()) {
      await productUpdatePage.getIsPopularInput().click();
      expect(await productUpdatePage.getIsPopularInput().isSelected(), 'Expected isPopular not to be selected').to.be.false;
    } else {
      await productUpdatePage.getIsPopularInput().click();
      expect(await productUpdatePage.getIsPopularInput().isSelected(), 'Expected isPopular to be selected').to.be.true;
    }
    expect(await productUpdatePage.getChangeControlNoInput()).to.eq(
      'changeControlNo',
      'Expected ChangeControlNo value to be equals to changeControlNo'
    );
    expect(await productUpdatePage.getRetestDurationInput()).to.contain('12', 'Expected retestDuration value to be equals to 12');
    expect(await productUpdatePage.getExpiryDurationInput()).to.contain('12', 'Expected expiryDuration value to be equals to 12');
    expect(await productUpdatePage.getValidationTypeInput()).to.eq(
      'validationType',
      'Expected ValidationType value to be equals to validationType'
    );
    expect(await productUpdatePage.getShelfLifeInput()).to.eq('5', 'Expected shelfLife value to be equals to 5');

    await productUpdatePage.save();
    expect(await productUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await productComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Product', async () => {
    const nbButtonsBeforeDelete = await productComponentsPage.countDeleteButtons();
    await productComponentsPage.clickOnLastDeleteButton();

    productDeleteDialog = new ProductDeleteDialog();
    expect(await productDeleteDialog.getDialogTitle()).to.eq('hrApp.product.delete.question');
    await productDeleteDialog.clickOnConfirmButton();

    expect(await productComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
