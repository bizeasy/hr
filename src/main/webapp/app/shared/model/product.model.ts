import { Moment } from 'moment';
import { IProductType } from 'app/shared/model/product-type.model';
import { IUom } from 'app/shared/model/uom.model';
import { IProductCategory } from 'app/shared/model/product-category.model';
import { IInventoryItemType } from 'app/shared/model/inventory-item-type.model';
import { ITaxSlab } from 'app/shared/model/tax-slab.model';

export interface IProduct {
  id?: number;
  name?: string;
  internalName?: string;
  brandName?: string;
  variant?: string;
  sku?: string;
  introductionDate?: Moment;
  releaseDate?: Moment;
  quantityIncluded?: number;
  piecesIncluded?: number;
  weight?: number;
  height?: number;
  width?: number;
  depth?: number;
  smallImageUrl?: string;
  mediumImageUrl?: string;
  largeImageUrl?: string;
  detailImageUrl?: string;
  originalImageUrl?: string;
  quantity?: number;
  cgst?: number;
  igst?: number;
  sgst?: number;
  price?: number;
  cgstPercentage?: number;
  igstPercentage?: number;
  sgstPercentage?: number;
  totalPrice?: number;
  description?: string;
  longDescription?: string;
  info?: any;
  isVirtual?: boolean;
  isVariant?: boolean;
  requireInventory?: boolean;
  returnable?: boolean;
  isPopular?: boolean;
  changeControlNo?: string;
  retestDuration?: number;
  expiryDuration?: number;
  validationType?: string;
  shelfLife?: number;
  productType?: IProductType;
  quantityUom?: IUom;
  weightUom?: IUom;
  sizeUom?: IUom;
  uom?: IUom;
  primaryProductCategory?: IProductCategory;
  virtualProduct?: IProduct;
  inventoryItemType?: IInventoryItemType;
  taxSlab?: ITaxSlab;
  shelfLifeUom?: IUom;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public name?: string,
    public internalName?: string,
    public brandName?: string,
    public variant?: string,
    public sku?: string,
    public introductionDate?: Moment,
    public releaseDate?: Moment,
    public quantityIncluded?: number,
    public piecesIncluded?: number,
    public weight?: number,
    public height?: number,
    public width?: number,
    public depth?: number,
    public smallImageUrl?: string,
    public mediumImageUrl?: string,
    public largeImageUrl?: string,
    public detailImageUrl?: string,
    public originalImageUrl?: string,
    public quantity?: number,
    public cgst?: number,
    public igst?: number,
    public sgst?: number,
    public price?: number,
    public cgstPercentage?: number,
    public igstPercentage?: number,
    public sgstPercentage?: number,
    public totalPrice?: number,
    public description?: string,
    public longDescription?: string,
    public info?: any,
    public isVirtual?: boolean,
    public isVariant?: boolean,
    public requireInventory?: boolean,
    public returnable?: boolean,
    public isPopular?: boolean,
    public changeControlNo?: string,
    public retestDuration?: number,
    public expiryDuration?: number,
    public validationType?: string,
    public shelfLife?: number,
    public productType?: IProductType,
    public quantityUom?: IUom,
    public weightUom?: IUom,
    public sizeUom?: IUom,
    public uom?: IUom,
    public primaryProductCategory?: IProductCategory,
    public virtualProduct?: IProduct,
    public inventoryItemType?: IInventoryItemType,
    public taxSlab?: ITaxSlab,
    public shelfLifeUom?: IUom
  ) {
    this.isVirtual = this.isVirtual || false;
    this.isVariant = this.isVariant || false;
    this.requireInventory = this.requireInventory || false;
    this.returnable = this.returnable || false;
    this.isPopular = this.isPopular || false;
  }
}
