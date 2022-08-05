import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { SupplierProductService } from 'app/entities/supplier-product/supplier-product.service';
import { ISupplierProduct, SupplierProduct } from 'app/shared/model/supplier-product.model';

describe('Service Tests', () => {
  describe('SupplierProduct Service', () => {
    let injector: TestBed;
    let service: SupplierProductService;
    let httpMock: HttpTestingController;
    let elemDefault: ISupplierProduct;
    let expectedResult: ISupplierProduct | ISupplierProduct[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(SupplierProductService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new SupplierProduct(0, currentDate, currentDate, 0, 0, 0, 'AAAAAAA', 0, 0, 0, 0, 0, 'AAAAAAA', 'AAAAAAA', 'AAAAAAA');
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            thruDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a SupplierProduct', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            thruDate: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            thruDate: currentDate,
          },
          returnedFromService
        );

        service.create(new SupplierProduct()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a SupplierProduct', () => {
        const returnedFromService = Object.assign(
          {
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            thruDate: currentDate.format(DATE_TIME_FORMAT),
            minOrderQty: 1,
            lastPrice: 1,
            shippingPrice: 1,
            supplierProductId: 'BBBBBB',
            leadTimeDays: 1,
            cgst: 1,
            igst: 1,
            sgst: 1,
            totalPrice: 1,
            comments: 'BBBBBB',
            supplierProductName: 'BBBBBB',
            manufacturerName: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            thruDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of SupplierProduct', () => {
        const returnedFromService = Object.assign(
          {
            fromDate: currentDate.format(DATE_TIME_FORMAT),
            thruDate: currentDate.format(DATE_TIME_FORMAT),
            minOrderQty: 1,
            lastPrice: 1,
            shippingPrice: 1,
            supplierProductId: 'BBBBBB',
            leadTimeDays: 1,
            cgst: 1,
            igst: 1,
            sgst: 1,
            totalPrice: 1,
            comments: 'BBBBBB',
            supplierProductName: 'BBBBBB',
            manufacturerName: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            fromDate: currentDate,
            thruDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a SupplierProduct', () => {
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
