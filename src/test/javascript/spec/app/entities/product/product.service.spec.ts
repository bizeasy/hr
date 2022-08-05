import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ProductService } from 'app/entities/product/product.service';
import { IProduct, Product } from 'app/shared/model/product.model';

describe('Service Tests', () => {
  describe('Product Service', () => {
    let injector: TestBed;
    let service: ProductService;
    let httpMock: HttpTestingController;
    let elemDefault: IProduct;
    let expectedResult: IProduct | IProduct[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProductService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Product(
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        currentDate,
        currentDate,
        0,
        0,
        0,
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        false,
        false,
        false,
        false,
        false,
        'AAAAAAA',
        0,
        0,
        'AAAAAAA',
        0
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            introductionDate: currentDate.format(DATE_TIME_FORMAT),
            releaseDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Product', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            introductionDate: currentDate.format(DATE_TIME_FORMAT),
            releaseDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            introductionDate: currentDate,
            releaseDate: currentDate,
          },
          returnedFromService
        );

        service.create(new Product()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Product', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            internalName: 'BBBBBB',
            brandName: 'BBBBBB',
            variant: 'BBBBBB',
            sku: 'BBBBBB',
            introductionDate: currentDate.format(DATE_TIME_FORMAT),
            releaseDate: currentDate.format(DATE_TIME_FORMAT),
            quantityIncluded: 1,
            piecesIncluded: 1,
            weight: 1,
            height: 1,
            width: 1,
            depth: 1,
            smallImageUrl: 'BBBBBB',
            mediumImageUrl: 'BBBBBB',
            largeImageUrl: 'BBBBBB',
            detailImageUrl: 'BBBBBB',
            originalImageUrl: 'BBBBBB',
            quantity: 1,
            cgst: 1,
            igst: 1,
            sgst: 1,
            price: 1,
            cgstPercentage: 1,
            igstPercentage: 1,
            sgstPercentage: 1,
            totalPrice: 1,
            description: 'BBBBBB',
            longDescription: 'BBBBBB',
            info: 'BBBBBB',
            isVirtual: true,
            isVariant: true,
            requireInventory: true,
            returnable: true,
            isPopular: true,
            changeControlNo: 'BBBBBB',
            retestDuration: 'BBBBBB',
            expiryDuration: 'BBBBBB',
            validationType: 'BBBBBB',
            shelfLife: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            introductionDate: currentDate,
            releaseDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Product', () => {
        const returnedFromService = Object.assign(
          {
            name: 'BBBBBB',
            internalName: 'BBBBBB',
            brandName: 'BBBBBB',
            variant: 'BBBBBB',
            sku: 'BBBBBB',
            introductionDate: currentDate.format(DATE_TIME_FORMAT),
            releaseDate: currentDate.format(DATE_TIME_FORMAT),
            quantityIncluded: 1,
            piecesIncluded: 1,
            weight: 1,
            height: 1,
            width: 1,
            depth: 1,
            smallImageUrl: 'BBBBBB',
            mediumImageUrl: 'BBBBBB',
            largeImageUrl: 'BBBBBB',
            detailImageUrl: 'BBBBBB',
            originalImageUrl: 'BBBBBB',
            quantity: 1,
            cgst: 1,
            igst: 1,
            sgst: 1,
            price: 1,
            cgstPercentage: 1,
            igstPercentage: 1,
            sgstPercentage: 1,
            totalPrice: 1,
            description: 'BBBBBB',
            longDescription: 'BBBBBB',
            info: 'BBBBBB',
            isVirtual: true,
            isVariant: true,
            requireInventory: true,
            returnable: true,
            isPopular: true,
            changeControlNo: 'BBBBBB',
            retestDuration: 'BBBBBB',
            expiryDuration: 'BBBBBB',
            validationType: 'BBBBBB',
            shelfLife: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            introductionDate: currentDate,
            releaseDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Product', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
